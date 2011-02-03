package me.prettyprint.cassandra.connection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.prettyprint.cassandra.service.CassandraClientMonitor;
import me.prettyprint.cassandra.service.CassandraClientMonitor.Counter;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ExceptionsTranslator;
import me.prettyprint.cassandra.service.ExceptionsTranslatorImpl;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.cassandra.service.JmxMonitor;
import me.prettyprint.cassandra.service.Operation;
import me.prettyprint.hector.api.ClockResolution;
import me.prettyprint.hector.api.exceptions.HCassandraInternalException;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.exceptions.HTimedOutException;
import me.prettyprint.hector.api.exceptions.HUnavailableException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;
import me.prettyprint.hector.api.exceptions.PoolExhaustedException;

import org.apache.cassandra.thrift.AuthenticationRequest;
import org.apache.cassandra.thrift.Cassandra;
import org.cliffc.high_scale_lib.NonBlockingHashMap;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HConnectionManager {

  private static final Logger log = LoggerFactory.getLogger(HConnectionManager.class);
  private static final Logger perf4jLogger =
    LoggerFactory.getLogger("me.prettyprint.cassandra.hector.TimingLogger");

  private final NonBlockingHashMap<CassandraHost,ConcurrentHClientPool> hostPools;
  private CassandraHostRetryService cassandraHostRetryService;
  private NodeAutoDiscoverService nodeAutoDiscoverService;
  private LoadBalancingPolicy loadBalancingPolicy;
  private CassandraHostConfigurator cassandraHostConfigurator;

  private final ClockResolution clock;

  final ExceptionsTranslator exceptionsTranslator;
  private CassandraClientMonitor monitor;


  public HConnectionManager(CassandraHostConfigurator cassandraHostConfigurator) {
    loadBalancingPolicy = cassandraHostConfigurator.getLoadBalancingPolicy();
    clock = cassandraHostConfigurator.getClockResolution();
    hostPools = new NonBlockingHashMap<CassandraHost, ConcurrentHClientPool>();
    if ( cassandraHostConfigurator.getRetryDownedHosts() ) {
      cassandraHostRetryService = new CassandraHostRetryService(this, cassandraHostConfigurator);
    }    
    for ( CassandraHost host : cassandraHostConfigurator.buildCassandraHosts()) {
      try {
        ConcurrentHClientPool chcp = new ConcurrentHClientPool(host);
        hostPools.put(host,chcp);
      } catch (HectorTransportException hte) {
        log.error("Could not start connection pool for host {}", host);
        if ( cassandraHostRetryService != null ) {
          cassandraHostRetryService.add(host);
        }
      }
    }

    if ( cassandraHostConfigurator.getAutoDiscoverHosts() ) {
      nodeAutoDiscoverService = new NodeAutoDiscoverService(this, cassandraHostConfigurator);
    }    
    monitor = JmxMonitor.getInstance(this).getCassandraMonitor();
    exceptionsTranslator = new ExceptionsTranslatorImpl();
    this.cassandraHostConfigurator = cassandraHostConfigurator;
  }

  /**
   * Returns true if the host was successfully added. In any sort of failure exceptions are 
   * caught and logged, returning false.
   * @param cassandraHost
   * @return
   */
  public boolean addCassandraHost(CassandraHost cassandraHost) {
    if ( !getHosts().contains(cassandraHost) ) {
      ConcurrentHClientPool pool = null;
      try {
        cassandraHostConfigurator.applyConfig(cassandraHost);
        pool = new ConcurrentHClientPool(cassandraHost);
        hostPools.putIfAbsent(cassandraHost, pool);
        log.info("Added host {} to pool", cassandraHost.getName());
        return true;
      } catch (HectorTransportException hte) {
        log.error("Transport exception host to HConnectionManager: " + cassandraHost, hte);
      } catch (Exception ex) {
        log.error("General exception host to HConnectionManager: " + cassandraHost, ex);
      }
    } else {
      log.info("Host already existed for pool {}", cassandraHost.getName());
    }
    return false;
  }

  /**
   * Remove the {@link CassandraHost} from the pool, bypassing retry service. This
   * would be called on a host that is known to be going away. Gracefully shuts down
   * the underlying connections via {@link ConcurrentHClientPool#shutdown()}
   * @param cassandraHost
   */
  public boolean removeCassandraHost(CassandraHost cassandraHost) {
    boolean removed = getHosts().contains(cassandraHost);
    if ( removed ) {
      ConcurrentHClientPool pool = hostPools.remove(cassandraHost);
      if ( pool != null ) {
        pool.shutdown();
      } else {
        removed = false;
        log.info("removeCassandraHost attempt miss for CassandraHost {} May have been beaten by another thread?", cassandraHost);
      }
    }
    log.info("Remove status for CassandraHost pool {} was {}", cassandraHost, removed);
    return removed;
  }

  public Set<CassandraHost> getHosts() {
    return Collections.unmodifiableSet(hostPools.keySet());
  }

  public List<String> getStatusPerPool() {
    List<String> stats = new ArrayList<String>();
    for (ConcurrentHClientPool clientPool : hostPools.values()) {
        stats.add(clientPool.getStatusAsString());
    }
    return stats;
  }


  public void operateWithFailover(Operation<?> op) throws HectorException {
    final StopWatch stopWatch = new Slf4JStopWatch(perf4jLogger);
    int retries = Math.min(op.failoverPolicy.numRetries, hostPools.size());
    HThriftClient client = null;
    boolean success = false;
    boolean retryable = false;
    Set<CassandraHost> excludeHosts = new HashSet<CassandraHost>();
    // TODO start timer for limiting retry time spent
    while ( !success ) {
      try {
        // TODO how to 'timeout' on this op when underlying pool is exhausted
        client =  getClientFromLBPolicy(excludeHosts);
        Cassandra.Client c = client.getCassandra(op.keyspaceName);
        // Keyspace can be null for some system_* api calls
        if ( op.credentials != null && !op.credentials.isEmpty() ) {
          c.login(new AuthenticationRequest(op.credentials));
        }

        op.executeAndSetResult(c, client.cassandraHost);
        success = true;
        stopWatch.stop(op.stopWatchTagName + ".success_");
        break;

      } catch (Exception ex) {
        HectorException he = exceptionsTranslator.translate(ex);
        if ( he instanceof HInvalidRequestException || he instanceof HCassandraInternalException ) {
          throw he;
        } else if ( he instanceof HectorTransportException) {
          --retries;
          client.close();
          markHostAsDown(client);
          excludeHosts.add(client.cassandraHost);
          retryable = true;
          if ( retries > 0 ) {
            monitor.incCounter(Counter.RECOVERABLE_TRANSPORT_EXCEPTIONS);
          }
        } else if (he instanceof HTimedOutException || he instanceof HUnavailableException ) {
          // DO NOT drecrement retries, we will be keep retrying on timeouts until it comes back
          retryable = true;
          monitor.incCounter(Counter.RECOVERABLE_TIMED_OUT_EXCEPTIONS);
          client.close();
          // TODO timecheck on how long we've been waiting on timeouts here
          // suggestion per user moores on hector-users
        } else if ( he instanceof PoolExhaustedException ) {
          retryable = true;
          --retries;
          if ( hostPools.size() == 1 ) {
            throw he;
          }
          monitor.incCounter(Counter.POOL_EXHAUSTED);
          excludeHosts.add(client.cassandraHost);
        }
        if ( retries <= 0 || retryable == false) throw he;
        log.error("Could not fullfill request on this host {}", client);
        log.error("Exception: ", he);
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
    if ( hostPools.isEmpty() ) {
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
      log.info("Client {} released to inactive or dead pool. Closing.", client);
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
      if ( cassandraHostRetryService != null ) 
        cassandraHostRetryService.add(client.cassandraHost);
    }
    client.close();
  }

  public Set<CassandraHost> getDownedHosts() {
    return cassandraHostRetryService.getDownedHosts();
  }

  public Collection<ConcurrentHClientPool> getActivePools() {
    return Collections.unmodifiableCollection(hostPools.values());
  }

  public long createClock() {
    return this.clock.createClock();
  }

  public void shutdown() {
    log.info("Shutdown called on HConnectionManager");
    if ( cassandraHostRetryService != null )
      cassandraHostRetryService.shutdown();
    if ( nodeAutoDiscoverService != null )
      nodeAutoDiscoverService.shutdown();

    for (ConcurrentHClientPool pool : hostPools.values()) {
      try {
        pool.shutdown();
      } catch (IllegalArgumentException iae) {
        log.error("Out of order in HConnectionManager shutdown()?: {}", iae.getMessage());
      }
    }
  }


}
