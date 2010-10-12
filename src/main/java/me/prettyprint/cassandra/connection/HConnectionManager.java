package me.prettyprint.cassandra.connection;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import me.prettyprint.cassandra.service.CassandraClientMonitor;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.JmxMonitor;
import me.prettyprint.cassandra.service.Operation;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.exceptions.HUnavailableException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.cassandra.thrift.Cassandra;
import org.cliffc.high_scale_lib.Counter;
import org.cliffc.high_scale_lib.NonBlockingHashMapLong;
import org.cliffc.high_scale_lib.NonBlockingHashSet;
import org.cliffc.high_scale_lib.NonBlockingIdentityHashMap;
import org.cliffc.high_scale_lib.NonBlockingSetInt;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HConnectionManager {
  
  private static final Logger log = LoggerFactory.getLogger(HConnectionManager.class);
  private static final Logger perf4jLogger =
    LoggerFactory.getLogger("me.prettyprint.cassandra.hector.TimingLogger");
  
  private final NonBlockingIdentityHashMap<CassandraHost,ConcurrentHClientPool> hostPools;
  private final CassandraHostRetryService cassandraHostRetryService;
  private LoadBalancingPolicy loadBalancingPolicy = new LeastActiveBalancingPolicy();
  

  private CassandraClientMonitor monitor;
  
  public HConnectionManager(CassandraHostConfigurator cassandraHostConfigurator) {    
    
    hostPools = new NonBlockingIdentityHashMap<CassandraHost, ConcurrentHClientPool>();
    for ( CassandraHost host : cassandraHostConfigurator.buildCassandraHosts() ) {
      hostPools.put(host,new ConcurrentHClientPool(host));      
    }
    
    cassandraHostRetryService = new CassandraHostRetryService(this, cassandraHostConfigurator);
    monitor = JmxMonitor.INSTANCE.getCassandraMonitor();  
  }
    
  public void addCassandraHost(CassandraHost cassandraHost) {

    if ( !hostPools.contains(cassandraHost) ) {
      hostPools.put(cassandraHost, new ConcurrentHClientPool(cassandraHost));
      log.info("Added host {} to pool", cassandraHost.getName());
    } else {
      log.info("Host already existed for pool {}", cassandraHost.getName());
    }    
  }
  
  int getRetryCount() {
    return hostPools.size();
  }
      
  
  public void operateWithFailover(Operation<?> op) throws HectorException {
    final StopWatch stopWatch = new Slf4JStopWatch(perf4jLogger);
    int retries = Math.min(op.failoverPolicy.numRetries, hostPools.size());    
    HThriftClient client = null;
    boolean success = false;
    Set<CassandraHost> excludeHosts = new HashSet<CassandraHost>();
    while (--retries >= 0) {
      try {
        
        client = loadBalancingPolicy.getPool(hostPools.values(), excludeHosts).borrowClient(); 
        Cassandra.Client c = client.getCassandra();
        // Keyspace can be null for some system_* api calls
        if ( op.keyspaceName != null ) {
          c.set_keyspace(op.keyspaceName);
        }

        op.executeAndSetResult(c);
        success = true;
        stopWatch.stop(op.stopWatchTagName + ".success_");                        
        break;

      } catch (HInvalidRequestException ire) {                
        throw ire;
      } catch (HectorException he) {
        if ( he instanceof HUnavailableException ) {          
          markHostAsDown(client);
          excludeHosts.add(client.cassandraHost);
        } else if ( he instanceof HectorTransportException ) {
          client.close();
        }
        log.error("Could not fullfill request on this host {}", client.cassandraHost);        
        if ( retries == 0 ) throw he;
      } catch (Exception e) {
        log.error("Cannot retry failover, got an Exception", e);
        throw new HectorException(e);
      } finally {        
        if ( !success ) {
          monitor.incCounter(op.failCounter);
          stopWatch.stop(op.stopWatchTagName + ".fail_");
        } 
        releaseClient(client);        
      }
    }
  }
  
  void releaseClient(HThriftClient client) {
    ConcurrentHClientPool pool = hostPools.get(client.cassandraHost);
    if ( pool != null ) {
      pool.releaseClient(client);
    } else {
      client.close();
    }
  }
  
  HThriftClient borrowClient() {
    return loadBalancingPolicy.getPool(hostPools.values(), null).borrowClient();
  }
        
  void markHostAsDown(HThriftClient client) {
    log.error("MARK HOST AS DOWN TRIGGERED for host {}", client.cassandraHost.getName());
    ConcurrentHClientPool pool = hostPools.remove(client.cassandraHost);
    log.error("Pool state on shutdown: {}", pool.getStatusAsString());
    pool.shutdown();    
    client.close();
    cassandraHostRetryService.add(client.cassandraHost);
  }
  

  
  // void setBalancingStrategy()
  // 
  
  
}

/*
 - should this be abstract and Failover 'connection managers' just extend and implement operateWithFailover as needed?
 
 - interplay between operate methods in DefaultFailoverOperator? is full exception catch stack needed?

return (++i == items.length)? 0 : i;
*/