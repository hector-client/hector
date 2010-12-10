package me.prettyprint.cassandra.connection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ExceptionsTranslator;
import me.prettyprint.hector.api.exceptions.HCassandraInternalException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CassandraHostRetryService extends BackgroundCassandraHostService {

  private static Logger log = LoggerFactory.getLogger(CassandraHostRetryService.class);

  public static final int DEF_QUEUE_SIZE = 3;
  public static final int DEF_RETRY_DELAY = 10;
  private final LinkedBlockingQueue<CassandraHost> downedHostQueue;
  private final ExceptionsTranslator exceptionsTranslator;

  public CassandraHostRetryService(HConnectionManager connectionManager,
      CassandraHostConfigurator cassandraHostConfigurator) {
    super(connectionManager, cassandraHostConfigurator);
    this.exceptionsTranslator = connectionManager.exceptionsTranslator;
    this.retryDelayInSeconds = cassandraHostConfigurator.getRetryDownedHostsDelayInSeconds();
    downedHostQueue = new LinkedBlockingQueue<CassandraHost>(cassandraHostConfigurator.getRetryDownedHostsQueueSize());
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

  public void add(CassandraHost cassandraHost) {
    downedHostQueue.add(cassandraHost);
    if ( log.isInfoEnabled() ) {
      log.info("Host detected as down was added to retry queue: {}", cassandraHost.getName());
    }
  }

  public boolean contains(CassandraHost cassandraHost) {
    return downedHostQueue.contains(cassandraHost);
  }

  public Set<CassandraHost> getDownedHosts() {
    return Collections.unmodifiableSet(new HashSet<CassandraHost>(downedHostQueue));
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
      CassandraHost cassandraHost = downedHostQueue.poll();
      if ( cassandraHost == null ) {
        if ( log.isDebugEnabled() ) { 
          log.debug("Retry service fired... nothing to do.");
        }
        return;
      }
      
      boolean reconnected = verifyConnection(cassandraHost);
      log.info("Downed Host retry status {} with host: {}", reconnected, cassandraHost.getName());
      if ( reconnected ) {
        //cassandraClientPool.getCluster().addHost(cassandraHost, true);
        connectionManager.addCassandraHost(cassandraHost);
      }
      if ( !reconnected && cassandraHost != null ) {
        downedHostQueue.add(cassandraHost);
      }

    }

    private boolean verifyConnection(CassandraHost cassandraHost) {
      if ( cassandraHost == null ) {
        return false;
      }
      boolean found = false;
      HThriftClient client = new HThriftClient(cassandraHost);
      try {
        
        client.open();
        found = client.getCassandra().describe_cluster_name() != null;
        client.close();              
      } catch (HectorTransportException he) {        
        log.error("Downed {} host still appears to be down: {}", cassandraHost, he.getMessage());
      } catch (Exception ex) {
                
        log.error("Downed Host retry failed attempt to verify CassandraHost", ex);
        
      } 
      return found;
    }

  }

  // TODO create callable to handle checking

  // perhaps wrap CassandraHost and add a lastRetryTime?
}
