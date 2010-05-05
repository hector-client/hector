package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.prettyprint.cassandra.service.CassandraClientMonitor.Counter;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * We declare this pool as enum to make sure it stays a singlton in the system so clients may
 * efficiently be reused.
 *
 * @author Ran Tavory (ran@outbain.com)
 * @author Nate McCall (nate@vervewireless.com)
 *
 */
/*package*/ class CassandraClientPoolImpl implements CassandraClientPool {

  private static final Logger log = LoggerFactory.getLogger(CassandraClientPoolImpl.class);
  /**
   * Mapping b/w the host identifier (url:port) and the pool used to store connections to it.
   */
  private final Map<CassandraHost, CassandraClientPoolByHost> pools;

  private final CassandraClientMonitor clientMonitor;

  public CassandraClientPoolImpl(CassandraClientMonitor clientMonitor) {
    pools = new HashMap<CassandraHost, CassandraClientPoolByHost>();
    this.clientMonitor = clientMonitor;
  }

  public CassandraClientPoolImpl(CassandraClientMonitor clientMonitor, CassandraHost[] cassandraHosts) {
    this(clientMonitor);
    for (CassandraHost cassandraHost : cassandraHosts) {
      log.debug("Creating pool-by-host instance: {}", cassandraHost);
      getPool(cassandraHost);
    }
  }


  @Override
  public CassandraClient borrowClient() throws IllegalStateException,
        PoolExhaustedException, Exception {
    String[] clients = new String[pools.size()];
    int x = 0;
    for(CassandraHost cassandraHost : pools.keySet()) {
      clients[x] = cassandraHost.getUrlPort();
      x++;
    }
    return borrowClient(clients);
  }

  @Override
  public CassandraClient borrowClient(String url, int port)
      throws IllegalStateException, PoolExhaustedException, Exception {
    return getPool(new CassandraHost(url, port)).borrowClient();
  }

  @Override
  public Set<String> getExhaustedPoolNames() {
    Set<String> hosts = new HashSet<String>();
    for (CassandraClientPoolByHost pool: pools.values()) {
      if (pool.isExhausted()) {
        hosts.add(pool.getName());
      }
    }
    return hosts;
  }

  @Override
  public int getNumActive() {
    int count = 0;
    for (CassandraClientPoolByHost pool: pools.values()) {
      count += pool.getNumActive();
    }
    return count;
  }

  @Override
  public int getNumBlockedThreads() {
    int count = 0;
    for (CassandraClientPoolByHost pool: pools.values()) {
      count += pool.getNumBlockedThreads();
    }
    return count;
  }

  @Override
  public int getNumExhaustedPools() {
    int count = 0;
    for (CassandraClientPoolByHost pool: pools.values()) {
      if (pool.isExhausted()) {
        ++count;
      }
    }
    return count;
  }

  @Override
  public int getNumIdle() {
    int count = 0;
    for (CassandraClientPoolByHost pool: pools.values()) {
      count += pool.getNumIdle();
    }
    return count;
  }

  @Override
  public int getNumPools() {
    return pools.size();
  }

  public CassandraClientPoolByHost getPool(CassandraHost cassandraHost) {
    CassandraClientPoolByHost pool = pools.get(cassandraHost);
    if (pool == null) {
      synchronized (pools) {
        pool = pools.get(cassandraHost);
        if (pool == null) {
          pool = new CassandraClientPoolByHostImpl(cassandraHost, this, clientMonitor);
          pools.put(cassandraHost, pool);
        }
      }
    }
    return pool;
  }


  @Override
  public Set<String> getPoolNames() {
    Set<String> names = new HashSet<String>();
    for (CassandraClientPoolByHost pool: pools.values()) {
      names.add(pool.getName());
    }
    return names;
  }

  @Override
  public void releaseClient(CassandraClient client) throws Exception {
    getPool(client).releaseClient(client);
  }

  @Override
  public void updateKnownHosts() throws TException {
    for (CassandraClientPoolByHost pool: pools.values()) {
      pool.updateKnownHosts();
    }
  }

  @Override
  public Set<String> getKnownHosts() {
    Set<String> hosts = new HashSet<String>();
    for (CassandraClientPoolByHost pool: pools.values()) {
      hosts.addAll(pool.getKnownHosts());
    }
    return hosts;
  }


  @Override
  public void invalidateClient(CassandraClient client) {
    getPool(client).invalidateClient(client);
  }

  void reportDestroyed(CassandraClient client) {
    ((CassandraClientPoolByHostImpl) getPool(client)).reportDestroyed(client);
  }

  private CassandraClientPoolByHost getPool(CassandraClient c) {
    return getPool(new CassandraHost(c.getUrl(), c.getPort()));
  }

  @Override
  public void releaseKeyspace(Keyspace k) throws Exception {
    releaseClient(k.getClient());
  }

  @Override
  public CassandraClient borrowClient(String urlPort) throws IllegalStateException,
      PoolExhaustedException, Exception {
    String url = parseHostFromUrl(urlPort);
    int port = parsePortFromUrl(urlPort);
    return borrowClient(url, port);
  }

  @Override
  public CassandraClient borrowClient(String[] clientUrls) throws Exception {
    List<String> clients = new ArrayList<String>(Arrays.asList(clientUrls));
    while(!clients.isEmpty()) {
      int rand = (int) (Math.random() * clients.size());
      try {
        return borrowClient(clients.get(rand));
      } catch (Exception e) {
        if (clients.size() > 1) {
          log.warn("Unable to obtain client " + clients.get(rand) + " will try the next client", e);
          clientMonitor.incCounter(Counter.RECOVERABLE_LB_CONNECT_ERRORS);
          clients.remove(rand);
        } else {
          throw e;
        }
      }
    }
    // Method should never get here; an exception must have been thrown before, I'm only writing
    // this to make the compiler happy.
    return null;
  }

  private String parseHostFromUrl(String urlPort) {
    return urlPort.substring(0, urlPort.lastIndexOf(':'));
  }

  private int parsePortFromUrl(String urlPort) {
    return Integer.valueOf(urlPort.substring(urlPort.lastIndexOf(':')+1, urlPort.length()));
  }

  @Override
  public void invalidateAllConnectionsToHost(CassandraClient client) {
    getPool(client).invalidateAll();
  }

  @Override
  public CassandraClientMonitorMBean getMbean() {
    return clientMonitor;
  }
}
