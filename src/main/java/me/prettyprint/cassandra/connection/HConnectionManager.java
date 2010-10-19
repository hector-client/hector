package me.prettyprint.cassandra.connection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import me.prettyprint.cassandra.service.CassandraClientMonitor;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ClockResolution;
import me.prettyprint.cassandra.service.ExceptionsTranslator;
import me.prettyprint.cassandra.service.ExceptionsTranslatorImpl;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.cassandra.service.JmxMonitor;
import me.prettyprint.cassandra.service.Operation;
import me.prettyprint.cassandra.service.CassandraClientMonitor.Counter;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.exceptions.HTimedOutException;
import me.prettyprint.hector.api.exceptions.HUnavailableException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;
import me.prettyprint.hector.api.exceptions.PoolExhaustedException;

import org.apache.cassandra.thrift.Cassandra;
import org.cliffc.high_scale_lib.NonBlockingIdentityHashMap;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.security.action.GetLongAction;

public class HConnectionManager {
  
  private static final Logger log = LoggerFactory.getLogger(HConnectionManager.class);
  private static final Logger perf4jLogger =
    LoggerFactory.getLogger("me.prettyprint.cassandra.hector.TimingLogger");
  
  private final NonBlockingIdentityHashMap<CassandraHost,ConcurrentHClientPool> hostPools;
  private final CassandraHostRetryService cassandraHostRetryService;
  private LoadBalancingPolicy loadBalancingPolicy = new LeastActiveBalancingPolicy();
  
  private final ClockResolution clock;
  
  private final ExceptionsTranslator exceptionsTranslator;
  private CassandraClientMonitor monitor;

  
  public HConnectionManager(CassandraHostConfigurator cassandraHostConfigurator) {    
    clock = cassandraHostConfigurator.getClockResolution();
    hostPools = new NonBlockingIdentityHashMap<CassandraHost, ConcurrentHClientPool>();
    for ( CassandraHost host : cassandraHostConfigurator.buildCassandraHosts() ) {
      hostPools.put(host,new ConcurrentHClientPool(host));      
    }    
    cassandraHostRetryService = new CassandraHostRetryService(this, cassandraHostConfigurator);
    monitor = JmxMonitor.getInstance(this).getCassandraMonitor();
    exceptionsTranslator = new ExceptionsTranslatorImpl();
  }
    
  public void addCassandraHost(CassandraHost cassandraHost) {
    if ( !hostPools.containsKey(cassandraHost) ) {
      hostPools.put(cassandraHost, new ConcurrentHClientPool(cassandraHost));
      log.info("Added host {} to pool", cassandraHost.getName());
    } else {
      log.info("Host already existed for pool {}", cassandraHost.getName());
    }    
  }
      
  public Set<CassandraHost> getHosts() {
    return Collections.unmodifiableSet(hostPools.keySet());
  }
  
  
  public void operateWithFailover(Operation<?> op) throws HectorException {
    final StopWatch stopWatch = new Slf4JStopWatch(perf4jLogger);
    int retries = Math.min(op.failoverPolicy.numRetries, hostPools.size());    
    HThriftClient client = null;
    boolean success = false;
    Set<CassandraHost> excludeHosts = new HashSet<CassandraHost>();
    // TODO start timer for limiting retry time spent
    while ( !success ) {
      try {
        // TODO how to 'timeout' on this op when underlying pool is exhausted
        client =  getClientFromLBPolicy(excludeHosts);
        Cassandra.Client c = client.getCassandra();
        // Keyspace can be null for some system_* api calls
        if ( op.keyspaceName != null ) {
          c.set_keyspace(op.keyspaceName);
        }

        op.executeAndSetResult(c, client.cassandraHost);
        success = true;
        stopWatch.stop(op.stopWatchTagName + ".success_");                        
        break;

      } catch (Exception ex) {        
        HectorException he = exceptionsTranslator.translate(ex);        
        if ( he instanceof HInvalidRequestException ) {
          throw he;
        } else if ( he instanceof HUnavailableException || he instanceof HectorTransportException) {
          --retries;
          client.close();
          markHostAsDown(client);
          excludeHosts.add(client.cassandraHost);         
          if ( retries > 0 ) {
            monitor.incCounter(Counter.RECOVERABLE_TRANSPORT_EXCEPTIONS);
          }
        } else if ( he instanceof HTimedOutException ) {
          // DO NOT drecrement retries, we will be keep retrying on timeouts until it comes back
          monitor.incCounter(Counter.RECOVERABLE_TIMED_OUT_EXCEPTIONS);
          client.close();
          // TODO timecheck on how long we've been waiting on timeouts here
          // suggestion per user moores on hector-users 
        } else if ( he instanceof PoolExhaustedException ) {
          --retries;
          if ( hostPools.size() == 1 ) {
            throw he;
          }
          monitor.incCounter(Counter.POOL_EXHAUSTED);
          excludeHosts.add(client.cassandraHost);
        }
        if ( retries == 0 ) throw he;
        log.error("Could not fullfill request on this host {}", client.cassandraHost);
        monitor.incCounter(Counter.SKIP_HOST_SUCCESS);
        sleepBetweenHostSkips(op.failoverPolicy);
      } finally {                
        if ( !success ) {
          monitor.incCounter(op.failCounter);
          stopWatch.stop(op.stopWatchTagName + ".fail_");
        }         
        releaseClient(client);        
      }
    }
  }
  
  /**
  * Sleeps for the specified time as determined by sleepBetweenHostsMilli.
  * In many cases failing over to other hosts is done b/c the cluster is too busy, so the sleep b/w
  * hosts may help reduce load on the cluster.
  */
    private void sleepBetweenHostSkips(FailoverPolicy failoverPolicy) {
      if (failoverPolicy.sleepBetweenHostsMilli > 0) {
        if ( log.isDebugEnabled() ) {
          log.debug("Will sleep for {} millisec", failoverPolicy.sleepBetweenHostsMilli);
        }
        try {
          Thread.sleep(failoverPolicy.sleepBetweenHostsMilli);
        } catch (InterruptedException e) {
          log.warn("Sleep between hosts interrupted", e);
        }
      }
    }
  
  private HThriftClient getClientFromLBPolicy(Set<CassandraHost> excludeHosts) {
    HThriftClient client;
    if ( hostPools.size() == 0 ) {
      throw new HectorException("All host pools marked down. Retry burden pushed out to client.");
    }
    try {
      client = loadBalancingPolicy.getPool(hostPools.values(), excludeHosts).borrowClient();
    } catch (Exception e) {
      throw new HectorException("General exception in getClientFromLBPolicy",e);
    }
    return client;
  }
  
  void releaseClient(HThriftClient client) {
    if ( client == null ) return;      
    ConcurrentHClientPool pool = hostPools.get(client.cassandraHost);
    if ( pool != null ) {
      pool.releaseClient(client);
    } else {
      client.close();
    }
  }
  
  HThriftClient borrowClient() {
    return getClientFromLBPolicy(null);
  }
        
  void markHostAsDown(HThriftClient client) {
    log.error("MARK HOST AS DOWN TRIGGERED for host {}", client.cassandraHost.getName());
    ConcurrentHClientPool pool = hostPools.remove(client.cassandraHost);
    if ( pool != null ) {
      log.error("Pool state on shutdown: {}", pool.getStatusAsString());
      pool.shutdown();
      cassandraHostRetryService.add(client.cassandraHost);
    }
    client.close();    
  }

  public Set<CassandraHost> getDownedHosts() {
    return cassandraHostRetryService.getDownedHosts();
  }
  
  public void setLoadBalancingPolicy(LoadBalancingPolicy loadBalancingPolicy) {
    this.loadBalancingPolicy = loadBalancingPolicy;
  }
    
  public long createClock() {
    return this.clock.createClock();
  }

  
}

/*
 
- Make failoverPolicy a class
  ~ add max time skipping parameter
  ~ configurable and passed in via setter with default
*/