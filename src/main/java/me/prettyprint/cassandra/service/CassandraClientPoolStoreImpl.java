package me.prettyprint.cassandra.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*package*/ class CassandraClientPoolStoreImpl implements CassandraClientPoolStore {

  /**
   * Mapping b/w the host identifier (url:port) and the pool used to store connections to it.
   */
  private final Map<String, CassandraClientPool> pools;

  public CassandraClientPoolStoreImpl() {
    pools = new HashMap<String, CassandraClientPool>();
  }

  @Override
  public CassandraClient borrowClient(String url, int port)
      throws IllegalStateException, PoolExhaustedException, Exception {
    return getPool(url, port).borrowClient();
  }

  @Override
  public Set<String> getExhaustedPoolNames() {
    Set<String> hosts = new HashSet<String>();
    for (CassandraClientPool pool: pools.values()) {
      if (pool.isExhausted()) {
        hosts.add(pool.getName());
      }
    }
    return hosts;
  }

  @Override
  public int getNumActive() {
    int count = 0;
    for (CassandraClientPool pool: pools.values()) {
      count += pool.getNumActive();
    }
    return count;
  }

  @Override
  public int getNumBlockedThreads() {
    int count = 0;
    for (CassandraClientPool pool: pools.values()) {
      count += pool.getNumBlockedThreads();
    }
    return count;
  }

  @Override
  public int getNumExhaustedPools() {
    int count = 0;
    for (CassandraClientPool pool: pools.values()) {
      if (pool.isExhausted()) {
        ++count;
      }
    }
    return count;
  }

  @Override
  public int getNumIdle() {
    int count = 0;
    for (CassandraClientPool pool: pools.values()) {
      count += pool.getNumIdle();
    }
    return count;
  }

  @Override
  public int getNumPools() {
    return pools.size();
  }

  @Override
  public CassandraClientPool getPool(String url, int port) {
    String key = url + ":" + port;
    CassandraClientPool pool = pools.get(key);
    if (pool == null) {
      synchronized (pools) {
        pool = new CassandraClientPoolImpl(url, port, this);
        pools.put(key, pool);
      }
    }
    return pool;
  }

  @Override
  public Set<String> getPoolNames() {
    Set<String> names = new HashSet<String>();
    for (CassandraClientPool pool: pools.values()) {
      names.add(pool.getName());
    }
    return names;
  }

  @Override
  public void releaseClient(CassandraClient client) throws Exception {
    getPool(client.getUrl(), client.getPort()).releaseClient(client);
  }

  @Override
  public void updateKnownHosts() {
    for (CassandraClientPool pool: pools.values()) {
      pool.updateKnownHosts();
    }
  }

  @Override
  public Set<String> getKnownHosts() {
    Set<String> hosts = new HashSet<String>();
    for (CassandraClientPool pool: pools.values()) {
      hosts.addAll(pool.getKnownHosts());
    }
    return hosts;
  }
}
