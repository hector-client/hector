package me.prettyprint.cassandra.connection;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;

/**
 * Keep track of how often a node replies with a HTimeoutException. If we go 
 * past the threshold of [timeoutCounter] timeouts within [timeWindow] milliseconds,
 * then we mark the node as suspended. (10 timeouts within 500ms by default)
 * 
 * Periodically check the suspended nodes list every retryDelayInSeconds. If 
 * the node has been suspended longer than nodeSuspensionDurationInSeconds,
 * then we unsuspend,  placing it back in the available pool. (10 second 
 * suspension retried every 10 seconds by default). 
 *
 * @author zznate
 */
public class HostTimeoutTracker extends BackgroundCassandraHostService {
  private static final Logger log = LoggerFactory.getLogger(HostTimeoutTracker.class);

  private ConcurrentHashMap<CassandraHost, LinkedBlockingQueue<Long>> hostTimeouts;
  private ConcurrentHashMap<CassandraHost, Long> suspended;
  private int hostTimeoutCounter;
  private int hostTimeoutWindow;
  private int nodeSuspensionDurationInSeconds;
  
  public static final int DEF_TIMEOUT_COUNTER = 10;
  public static final int DEF_TIMEOUT_WINDOW = 500;
  public static final int DEF_NODE_SUSPENSION_DURATION_IN_SECONDS = 10;
  public static final int DEF_NODE_UNSUSPEND_CHECK_DELAY_IN_SECONDS = 10;
  
  
  public HostTimeoutTracker(HConnectionManager connectionManager,
      CassandraHostConfigurator cassandraHostConfigurator) {
    super(connectionManager, cassandraHostConfigurator);
    retryDelayInSeconds = cassandraHostConfigurator.getHostTimeoutUnsuspendCheckDelay();
    hostTimeouts = new ConcurrentHashMap<CassandraHost, LinkedBlockingQueue<Long>>();
    suspended = new ConcurrentHashMap<CassandraHost, Long>();
    sf = executor.scheduleWithFixedDelay(new Unsuspender(), retryDelayInSeconds,retryDelayInSeconds, TimeUnit.SECONDS);
    hostTimeoutCounter = cassandraHostConfigurator.getHostTimeoutCounter();
    hostTimeoutWindow = cassandraHostConfigurator.getHostTimeoutWindow();
    nodeSuspensionDurationInSeconds = cassandraHostConfigurator.getHostTimeoutSuspensionDurationInSeconds();
  }

  public boolean penalizeTimeout(CassandraHost cassandraHost) {
    final long currentTimeMillis = System.currentTimeMillis();
    if (hostTimeoutCounter <= 1 )
      {
        connectionManager.suspendCassandraHost(cassandraHost);
        suspended.putIfAbsent(cassandraHost, currentTimeMillis); 
        return true;  
      }
    
    hostTimeouts.putIfAbsent(cassandraHost, new LinkedBlockingQueue<Long>(hostTimeoutCounter - 1 ));
    
    if(hostTimeouts.get(cassandraHost).offer(currentTimeMillis)) {
      return false;
    } else {
      final long oldestTimeoutMillis = hostTimeouts.get(cassandraHost).peek();
      hostTimeouts.get(cassandraHost).poll();
      hostTimeouts.get(cassandraHost).offer(currentTimeMillis);
      if (currentTimeMillis - oldestTimeoutMillis < hostTimeoutWindow) {
        connectionManager.suspendCassandraHost(cassandraHost);
        suspended.putIfAbsent(cassandraHost, currentTimeMillis);  
        return true;
      } else {
        return false;
      }      
    } 

  }
  
  class Unsuspender implements Runnable {

    @Override
    public void run() {
      for (Iterator<Entry<CassandraHost,Long>> iterator = suspended.entrySet().iterator(); iterator.hasNext();) {
        Entry<CassandraHost,Long> vals = iterator.next();
        if ( vals.getValue() < (System.currentTimeMillis() - (nodeSuspensionDurationInSeconds * 1000)) ) {
          connectionManager.unsuspendCassandraHost(vals.getKey());
          iterator.remove();          
        }
      }      
    }
    
  }
  
  @Override
  void applyRetryDelay() {


  }

  @Override
  void shutdown() {
    log.info("Shutting down HostTimeoutTracker");
    if ( sf != null )
      sf.cancel(true);
    if ( executor != null ) 
      executor.shutdownNow();
    log.info("HostTimeTracker shutdown complete.");
  }

}
