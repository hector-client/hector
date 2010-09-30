package me.prettyprint.cassandra.service;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownCassandraHostRetryService implements DownCassandraHostRetryServiceMBean {
  
  private static Logger log = LoggerFactory.getLogger(DownCassandraHostRetryService.class);

  public static final int DEF_QUEUE_SIZE = 3;
  public static final int DEF_RETRY_DELAY = 30;
  private LinkedBlockingQueue<CassandraHost> downedHostQueue;
  
  private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
  
  private final CassandraClientPool cassandraClientPool;
  
  private ScheduledFuture<CassandraHost> sf;
  private int retryDelayInSeconds = DEF_RETRY_DELAY;
  
  public DownCassandraHostRetryService(CassandraClientPool cassandraClientPool,
      CassandraHostConfigurator cassandraHostConfigurator) {    
    this.cassandraClientPool = cassandraClientPool;
    this.retryDelayInSeconds = cassandraHostConfigurator.getRetryDownedHostsDelayInSeconds();
    downedHostQueue = new LinkedBlockingQueue<CassandraHost>(cassandraHostConfigurator.getRetryDownedHostsQueueSize());
    sf = executor.schedule(new RetryRunner(), this.retryDelayInSeconds, TimeUnit.SECONDS);
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        log.error("Downed Host retry shutdown hook called");
        if ( sf != null ) 
          sf.cancel(true);
        if ( executor != null ) 
          executor.shutdownNow();
         
        log.error("Downed Host retry shutdown complete");        
      }
    });
    log.info("Downed Host Retry service started with queue size {} and retry delay {}s", 
        cassandraHostConfigurator.getRetryDownedHostsQueueSize(), 
        retryDelayInSeconds);
    
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



  class RetryRunner implements Callable<CassandraHost>{
    
    @Override
    public CassandraHost call() throws Exception {
      CassandraHost cassandraHost = downedHostQueue.poll();
      boolean reconnected = verifyConnection(cassandraHost);
      log.info("Downed Host retry status {} with host: {}", reconnected, cassandraHost.getName());
      if ( reconnected ) {
        cassandraClientPool.getCluster().addHost(cassandraHost, true);
      }
      if ( cassandraHost != null ) {
        downedHostQueue.add(cassandraHost);
      }
      return cassandraHost;
    }
    
    private boolean verifyConnection(CassandraHost cassandraHost) {
      if ( cassandraHost == null ) return false;
      TTransport tr = cassandraHost.getUseThriftFramedTransport() ? 
          new TFramedTransport(new TSocket(cassandraHost.getHost(), cassandraHost.getPort(), 15)) :
            new TSocket(cassandraHost.getHost(), cassandraHost.getPort(), 15);
      
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
