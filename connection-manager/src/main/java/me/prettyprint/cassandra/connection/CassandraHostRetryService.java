package me.prettyprint.cassandra.connection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


import me.prettyprint.cassandra.service.ExceptionsTranslator;
import me.prettyprint.hector.api.exceptions.HCassandraInternalException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CassandraHostRetryService extends BackgroundCassandraHostService {

  private static Logger log = LoggerFactory.getLogger(CassandraHostRetryService.class);

  public static final int DEF_QUEUE_SIZE = -1;
  public static final int DEF_RETRY_DELAY = 10;
  private final LinkedBlockingQueue<HCassandraHost> downedHostQueue;
  private final ExceptionsTranslator exceptionsTranslator;

  public CassandraHostRetryService(HConnectionManager connectionManager,
      CassandraHostConfigurator cassandraHostConfigurator) {
    super(connectionManager, cassandraHostConfigurator);
    this.exceptionsTranslator = connectionManager.exceptionsTranslator;
    this.retryDelayInSeconds = cassandraHostConfigurator.getRetryDownedHostsDelayInSeconds();
    downedHostQueue = new LinkedBlockingQueue<HCassandraHost>(cassandraHostConfigurator.getRetryDownedHostsQueueSize() < 1 
        ? Integer.MAX_VALUE : cassandraHostConfigurator.getRetryDownedHostsQueueSize());
          
    sf = executor.scheduleWithFixedDelay(new RetryRunner(), this.retryDelayInSeconds,this.retryDelayInSeconds, TimeUnit.SECONDS);

    log.info("Downed Host Retry service started with queue size {} and retry delay {}s",
        cassandraHostConfigurator.getRetryDownedHostsQueueSize(),
        retryDelayInSeconds);
  }

  @Override
  void shutdown() {
    log.info("Downed Host retry shutdown hook called");
    if ( sf != null ) {
      sf.cancel(true);
    }
    if ( executor != null ) {
      executor.shutdownNow();
    }
    log.info("Downed Host retry shutdown complete");
  }

  public void add(HCassandraHost cassandraHost) {
    downedHostQueue.add(cassandraHost);
    if ( log.isInfoEnabled() ) {
      log.info("Host detected as down was added to retry queue: {}", cassandraHost.getName());
    }
  }

  public boolean contains(HCassandraHost cassandraHost) {
    return downedHostQueue.contains(cassandraHost);
  }

  public Set<HCassandraHost> getDownedHosts() {
    return Collections.unmodifiableSet(new HashSet<HCassandraHost>(downedHostQueue));
  }

  @Override
  public void applyRetryDelay() {
    sf.cancel(false);
    executor.schedule(new RetryRunner(), retryDelayInSeconds, TimeUnit.SECONDS);
  }

  public void flushQueue() {
    downedHostQueue.clear();
    log.info("Downed Host retry queue flushed.");
  }



  class RetryRunner implements Runnable {

    @Override
    public void run() {
      HCassandraHost cassandraHost = downedHostQueue.poll();
      if ( cassandraHost == null ) {
        if ( log.isDebugEnabled() ) { 
          log.debug("Retry service fired... nothing to do.");
        }
        return;
      }
      
      boolean reconnected = verifyConnection(cassandraHost);
      log.info("Downed Host retry status {} with host: {}", reconnected, cassandraHost.getName());
      if ( reconnected ) {
        reconnected = connectionManager.addCassandraHost(cassandraHost);
      }
      if ( !reconnected && cassandraHost != null ) {
        downedHostQueue.add(cassandraHost);
      }

    }

    private boolean verifyConnection(HCassandraHost cassandraHost) {
      if ( cassandraHost == null ) {
        return false;
      }
      boolean found = false;
      HThriftClient client = new HThriftClientImpl(cassandraHost);
      try {
        
        client.open();
        found = client.getCassandra().describe_cluster_name() != null;
        client.close();              
      } catch (HectorTransportException he) {        
        log.warn("Downed {} host still appears to be down: {}", cassandraHost, he.getMessage());
      } catch (Exception ex) {
                
        log.error("Downed Host retry failed attempt to verify CassandraHost", ex);
        
      } 
      return found;
    }

  }

}
