package me.prettyprint.cassandra.connection;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;

/**
 * Keep track of how often a node replies with a HTimeoutException. If we go 
 * past the threshold of [timeoutCounter] timeouts within [timeWindow] seconds,
 * then we mark the node as suspended. 
 * 
 * Periodically check the suspended nodes list every retryDelayInSeconds. If 
 * the node has been suspended longer than nodeSuspensionDurationInSeconds,
 * then we unsuspend,  placing it back in the available pool 
 *
 * @author zznate
 */
public class HostTimeoutTracker extends BackgroundCassandraHostService {

  private ConcurrentHashMap<CassandraHost, LinkedBlockingQueue<Long>> timeouts;
  private ConcurrentHashMap<CassandraHost, Long> suspended;
  private int timeoutCounter;
  private int timeoutWindow;
  private int nodeSuspensionDurationInSeconds;
  
  public static final int DEF_TIMEOUT_COUNTER = 10;
  public static final int DEF_TIMEOUT_WINDOW = 500;
  public static final int DEF_NODE_SUSPENSION_DURATION_IN_SECONDS = 10;
  public static final int DEF_NODE_UNSUSPEND_CHECK_DELAY_IN_SECONDS = 10;
  
  
  public HostTimeoutTracker(HConnectionManager connectionManager,
      CassandraHostConfigurator cassandraHostConfigurator) {
    super(connectionManager, cassandraHostConfigurator);
    retryDelayInSeconds = cassandraHostConfigurator.getHostTimeoutUnsuspendCheckDelay();
    timeouts = new ConcurrentHashMap<CassandraHost, LinkedBlockingQueue<Long>>();
    suspended = new ConcurrentHashMap<CassandraHost, Long>();
    sf = executor.scheduleWithFixedDelay(new Unsuspender(), retryDelayInSeconds,retryDelayInSeconds, TimeUnit.SECONDS);
    timeoutCounter = cassandraHostConfigurator.getHostTimeoutCounter();
    timeoutWindow = cassandraHostConfigurator.getHostTimeoutWindow();
    nodeSuspensionDurationInSeconds = cassandraHostConfigurator.getHostTimeoutSuspensionDurationInSeconds();
  }

  public boolean checkTimeout(CassandraHost cassandraHost) {
    timeouts.putIfAbsent(cassandraHost, new LinkedBlockingQueue<Long>());
    long currentTimeMillis = System.currentTimeMillis();
    timeouts.get(cassandraHost).add(currentTimeMillis);
    boolean timeout = false;
    // if there are 3 timeouts within 500ms, return false
    if ( timeouts.get(cassandraHost).size() > timeoutCounter) {
      Long last = timeouts.get(cassandraHost).remove();      
      if (last.longValue() < (currentTimeMillis - timeoutWindow)) {        
        timeout = true;
        connectionManager.suspendCassandraHost(cassandraHost);
        suspended.putIfAbsent(cassandraHost, currentTimeMillis);                                    
      }      
    }
    return timeout;
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
    // TODO probably a noop, as HConnectionManager will handle teardown of suspended on shutdown. 
    // - anything here actually might cause a race condition(?)

  }

}
