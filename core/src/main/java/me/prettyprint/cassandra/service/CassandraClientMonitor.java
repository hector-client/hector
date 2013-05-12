package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import me.prettyprint.cassandra.connection.HClientPool;
import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CassandraClientMonitor implements CassandraClientMonitorMBean {

  private static final Logger log = LoggerFactory.getLogger(CassandraClientMonitor.class);
  private final Map<Counter, AtomicLong> counters;

  private final HConnectionManager connectionManager;

  /**
   * List of available JMX counts
   */
  public enum Counter {
    RECOVERABLE_TIMED_OUT_EXCEPTIONS,
    RECOVERABLE_UNAVAILABLE_EXCEPTIONS,
    RECOVERABLE_TRANSPORT_EXCEPTIONS,
    SKIP_HOST_SUCCESS,
    WRITE_SUCCESS,
    WRITE_FAIL,
    READ_SUCCESS,
    READ_FAIL,
    POOL_EXHAUSTED,
    /** Load balance connection errors */
    RECOVERABLE_LB_CONNECT_ERRORS,
    /** Connection time errors - unable to connect to host or something... */
    CONNECT_ERROR,
    RENEWED_IDLE_CONNECTIONS,
    RENEWED_TOO_LONG_CONNECTIONS
  }

  public CassandraClientMonitor(HConnectionManager connectionManager) {
    this.connectionManager = connectionManager;
    counters = new EnumMap<Counter, AtomicLong>(Counter.class);
    for (Counter counter: Counter.values()) {
      counters.put(counter, new AtomicLong(0));
    }
  }

  public void incCounter(Counter counterType) {
    counters.get(counterType).incrementAndGet();
  }

  public long getWriteSuccess() {
    return counters.get(Counter.WRITE_SUCCESS).longValue();
  }


  @Override
  public long getReadFail() {
    return counters.get(Counter.READ_FAIL).longValue();
  }

  public long getReadSuccess() {
    return counters.get(Counter.READ_SUCCESS).longValue();
  }


  @Override
  public long getSkipHostSuccess() {
    return counters.get(Counter.SKIP_HOST_SUCCESS).longValue();
  }


  @Override
  public long getRecoverableTimedOutCount() {
    return counters.get(Counter.RECOVERABLE_TIMED_OUT_EXCEPTIONS).longValue();
  }


  @Override
  public long getRecoverableUnavailableCount() {
    return counters.get(Counter.RECOVERABLE_UNAVAILABLE_EXCEPTIONS).longValue();
  }


  @Override
  public long getWriteFail() {
    return counters.get(Counter.WRITE_FAIL).longValue();
  }


  @Override
  public void updateKnownHosts() throws HectorTransportException {
   log.info("Updating all known cassandra hosts on all clients");
   connectionManager.doAddNodes();
  }


  @Override
  public long getNumPoolExhaustedEventCount() {
    return counters.get(Counter.POOL_EXHAUSTED).longValue();
  }


  @Override
  public Set<String> getExhaustedPoolNames() {
    Set<String> ret = new HashSet<String>();
    for ( CassandraHost host : connectionManager.getDownedHosts() ) {
      ret.add(host.toString());
    }
    return ret;
  }


  @Override
  public int getNumActive() {
    int ret = 0;
    Collection<HClientPool> pools = connectionManager.getActivePools();
    for (HClientPool concurrentHClientPool : pools) {
      ret += concurrentHClientPool.getNumActive();
    }
    return ret;
  }


  @Override
  public int getNumBlockedThreads() {
    int ret = 0;
    Collection<HClientPool> pools = connectionManager.getActivePools();
    for (HClientPool concurrentHClientPool : pools) {
      ret += concurrentHClientPool.getNumBlockedThreads();
    }
    return ret;
  }


  @Override
  public int getNumExhaustedPools() {
    return connectionManager.getDownedHosts().size();
  }


  @Override
  public int getNumIdleConnections() {
    int ret = 0;
    Collection<HClientPool> pools = connectionManager.getActivePools();
    for (HClientPool concurrentHClientPool : pools) {
      ret += concurrentHClientPool.getNumIdle();
    }
    return ret;
  }


  @Override
  public int getNumPools() {
    return connectionManager.getHosts().size();
  }


  @Override
  public List<String> getKnownHosts() {
    List<String> hosts = new ArrayList<String>();
    for (CassandraHost cassandraHost : connectionManager.getHosts()) {
        hosts.add(cassandraHost.toString());
    }
    return hosts;
  }


  @Override
  public List<String> getStatisticsPerPool() {
    return connectionManager.getStatusPerPool();
  }


  @Override
  public long getRecoverableTransportExceptionCount() {
    return counters.get(Counter.RECOVERABLE_TRANSPORT_EXCEPTIONS).longValue();
  }


  @Override
  public long getRecoverableErrorCount() {
    return getRecoverableTimedOutCount() + getRecoverableTransportExceptionCount() +
        getRecoverableUnavailableCount() + getRecoverableLoadBalancedConnectErrors();
  }


  @Override
  public long getRecoverableLoadBalancedConnectErrors() {
    return counters.get(Counter.RECOVERABLE_LB_CONNECT_ERRORS).longValue();
  }


  @Override
  public long getNumConnectionErrors() {
    return counters.get(Counter.CONNECT_ERROR).longValue();
  }

  @Override
  public boolean addCassandraHost(String hostStr) {    
    return connectionManager.addCassandraHost(new CassandraHost(hostStr));
  }

  @Override
  public boolean removeCassandraHost(String hostStr) {  
    return connectionManager.removeCassandraHost(new CassandraHost(hostStr));
  }

  @Override
  public Set<String> getSuspendedCassandraHosts() {
    Set<CassandraHost> hosts = connectionManager.getSuspendedCassandraHosts();
    Set<String> hostsStr = new HashSet<String>();
    for (CassandraHost host : hosts) {
        hostsStr.add(host.getName());    
    }
    return hostsStr;
  }

  @Override
  public boolean suspendCassandraHost(String hostStr) {    
    return connectionManager.suspendCassandraHost(new CassandraHost(hostStr));
  }

  @Override
  public boolean unsuspendCassandraHost(String hostStr) {
    return connectionManager.unsuspendCassandraHost(new CassandraHost(hostStr));
  }
  
  @Override
  public boolean setCassandraHostRetryDelay(String retryDelay) {
    int delay;
    try {
      delay = Integer.parseInt(retryDelay);
      if (delay > 0) {
        connectionManager.setCassandraHostRetryDelay(delay);
        return true;
      } else {
        return false;
      }
    } catch (NumberFormatException e) {
      log.error("Invalid number entered: " + retryDelay);
      return false;
    }
  }

  @Override
  public int getNumRenewedIdleConnections() {
    return counters.get(Counter.RENEWED_IDLE_CONNECTIONS).intValue();
  }

  @Override
  public int getNumRenewedTooLongConnections() {
    return counters.get(Counter.RENEWED_TOO_LONG_CONNECTIONS).intValue();
  }
}
