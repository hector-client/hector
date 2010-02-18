package me.prettyprint.cassandra.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * We declare this pool as enum to make sure it stays a singlton in the system so clients may
 * efficiently be reused.
 *
 * @author Ran Tavory (ran@outbain.com)
 *
 */
/*package*/ class CassandraClientPoolImpl implements CassandraClientPool {

  private static final Logger log = LoggerFactory.getLogger(CassandraClientPoolImpl.class);
  /**
   * Mapping b/w the host identifier (url:port) and the pool used to store connections to it.
   */
  private final Map<String, CassandraClientPoolByHost> pools;

  private final CassandraClientMonitor clientMonitor;

  public CassandraClientPoolImpl(CassandraClientMonitor clientMonitor) {
    pools = new HashMap<String, CassandraClientPoolByHost>();
    this.clientMonitor = clientMonitor;
  }

  @Override
  public CassandraClient borrowClient(String url, int port)
      throws IllegalStateException, PoolExhaustedException, Exception {
    return getPool(url, port).borrowClient();
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

  @Override
  public CassandraClientPoolByHost getPool(String url, int port) {
    PoolKey key = new PoolKey(url, port);
    CassandraClientPoolByHost pool = pools.get(key.ip);
    if (pool == null) {
      synchronized (pools) {
        pool = pools.get(key.ip);
        if (pool == null) {
          pool = new CassandraClientPoolByHostImpl(url, port, key.name, this, clientMonitor);
          pools.put(key.ip, pool);
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

  private class PoolKey {
    @SuppressWarnings("unused")
    private final String url, ip;
    @SuppressWarnings("unused")
    private final int port;
    private final String name;

    public PoolKey(String url, int port) {
      this.port = port;
      StringBuilder b = new StringBuilder();
      InetAddress address;
      String turl, tip;
      try {
        address = InetAddress.getByName(url);
        turl = address.getHostName();
        tip = address.getHostAddress();
      } catch (UnknownHostException e) {
        log.error("Unable to resolve host {}", url);
        turl = url;
        tip = url;
      }
      this.url = turl;
      ip = tip;
      b.append(url);
      b.append("(");
      b.append(ip);
      b.append("):");
      b.append(port);
      name = b.toString();
    }
  }

  @Override
  public void invalidateClient(CassandraClient client) {
    getPool(client).invalidateClient(client);

  }

  void reportDestroyed(CassandraClient client) {
    ((CassandraClientPoolByHostImpl) getPool(client)).reportDestroyed(client);
  }

  private CassandraClientPoolByHost getPool(CassandraClient c) {
    return getPool(c.getUrl(), c.getPort());
  }

}
