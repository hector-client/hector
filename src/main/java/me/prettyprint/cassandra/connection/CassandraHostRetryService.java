package me.prettyprint.cassandra.connection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CassandraHostRetryService {
  
  private static Logger log = LoggerFactory.getLogger(CassandraHostRetryService.class);

  public static final int DEF_QUEUE_SIZE = 3;
  public static final int DEF_RETRY_DELAY = 10;
  private LinkedBlockingQueue<CassandraHost> downedHostQueue;
  
  private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
  
  private final HConnectionManager connectionManager;
  
  private ScheduledFuture sf;
  private int retryDelayInSeconds = DEF_RETRY_DELAY;
  
  public CassandraHostRetryService(HConnectionManager connectionManager,
      CassandraHostConfigurator cassandraHostConfigurator) {    
    this.connectionManager = connectionManager;
    this.retryDelayInSeconds = cassandraHostConfigurator.getRetryDownedHostsDelayInSeconds();
    downedHostQueue = new LinkedBlockingQueue<CassandraHost>(cassandraHostConfigurator.getRetryDownedHostsQueueSize());
    sf = executor.scheduleWithFixedDelay(new RetryRunner(), this.retryDelayInSeconds,this.retryDelayInSeconds, TimeUnit.SECONDS);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        shutdown();        
      }
    });
    log.info("Downed Host Retry service started with queue size {} and retry delay {}s", 
        cassandraHostConfigurator.getRetryDownedHostsQueueSize(), 
        retryDelayInSeconds);
    
  }
  
  void shutdown() {
    log.error("Downed Host retry shutdown hook called");
    if ( sf != null ) 
      sf.cancel(true);
    if ( executor != null ) 
      executor.shutdownNow();     
    log.error("Downed Host retry shutdown complete");    
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
  
  public void applyRetryDelay() {
    sf.cancel(false);
    executor.schedule(new RetryRunner(), retryDelayInSeconds, TimeUnit.SECONDS);
  }

  public void flushQueue() {
    downedHostQueue.clear();
    log.error("Downed Host retry queue flushed.");
  }
  
  

  public int getRetryDelayInSeconds() {
    return retryDelayInSeconds;
  }

  public void setRetryDelayInSeconds(int retryDelayInSeconds) {
    this.retryDelayInSeconds = retryDelayInSeconds;
  }



  class RetryRunner implements Runnable {
    
    @Override
    public void run() {
      CassandraHost cassandraHost = downedHostQueue.poll();
      if ( cassandraHost == null ) {
        log.info("Retry service fired... nothing to do.");
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
      if ( cassandraHost == null ) return false;
      TTransport tr = cassandraHost.getUseThriftFramedTransport() ? 
          new TFramedTransport(new TSocket(cassandraHost.getHost(), cassandraHost.getPort(), retryDelayInSeconds / 2)) :
            new TSocket(cassandraHost.getHost(), cassandraHost.getPort(), retryDelayInSeconds / 2);
      
      TProtocol proto = new TBinaryProtocol(tr);
      Cassandra.Client client = new Cassandra.Client(proto);
      try {
        tr.open();
        return client.describe_cluster_name() != null;
      } catch (Exception e) {
        log.error("Downed Host retry failed attempt to verify CassandraHost", e);
      } finally {
        tr.close();
      }
      return false;
    }
    
  }
  
  // TODO create callable to handle checking
  
  // perhaps wrap CassandraHost and add a lastRetryTime?
}
