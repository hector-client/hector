package me.prettyprint.cassandra.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPoolFactory;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

/*package*/ class CassandraClientPoolByHostImpl implements CassandraClientPoolByHost {

  private static final Logger log = LoggerFactory.getLogger(CassandraClientPoolByHostImpl.class);

  private final CassandraClientFactory clientFactory;
  private final String url;
  private final String name;
  private final int port;
  private final int maxActive;
  private final int maxIdle;
  private final ExhaustedPolicy exhaustedPolicy;
  private final long maxWaitTimeWhenExhausted;
  private final GenericObjectPool pool;

  /**
   * Number of currently blocked threads.
   * This includes the number of threads waiting for an idle connection, as well as threads
   * wanting for connection initialization after they won a free slot.
   */
  private final AtomicInteger blockedThreadsCount;

  /**
   * The set of live clients created by the pool.
   * This set includes both the active clients currently used by active threads as well as idle
   * clients waiting in the pool.
   */
  private final Set<CassandraClient> liveClientsFromPool;

  public CassandraClientPoolByHostImpl(String cassandraUrl, int cassandraPort, String name,
      CassandraClientPool pools, CassandraClientMonitor clientMonitor) {
    this(cassandraUrl, cassandraPort, name, pools, clientMonitor, DEFAULT_MAX_ACTIVE,
        DEFAULT_MAX_WAITTIME_WHEN_EXHAUSTED,
        DEFAULT_MAX_IDLE, DEFAULT_EXHAUSTED_POLICY);
  }

  public CassandraClientPoolByHostImpl(String cassandraUrl, int cassandraPort, String name,
      CassandraClientPool pools, CassandraClientMonitor clientMonitor, int maxActive,
      long maxWait, int maxIdle, ExhaustedPolicy exhaustedPolicy) {
    this(cassandraUrl, cassandraPort, name, pools, maxActive, maxWait, maxIdle,
        exhaustedPolicy, new CassandraClientFactory(pools, cassandraUrl, cassandraPort, clientMonitor));

  }

  public CassandraClientPoolByHostImpl(String cassandraUrl, int cassandraPort, String name,
      CassandraClientPool pools, int maxActive,
      long maxWait, int maxIdle, ExhaustedPolicy exhaustedPolicy,
      CassandraClientFactory clientFactory) {
    log.debug("Creating new connection pool for {}:{}", cassandraUrl, cassandraPort);
    url = cassandraUrl;
    port = cassandraPort;
    this.name = name;
    this.maxActive = maxActive;
    this.maxIdle = maxIdle;
    this.maxWaitTimeWhenExhausted = maxWait;
    this.exhaustedPolicy = exhaustedPolicy;
    this.clientFactory = clientFactory;
    blockedThreadsCount = new AtomicInteger(0);
    // Create a set implemented as a ConcurrentHashMap for performance and concurrency.
    liveClientsFromPool =
        Collections.newSetFromMap(new ConcurrentHashMap<CassandraClient,Boolean>());
    pool = createPool();
  }

  @Override
  public CassandraClient borrowClient() throws Exception, PoolExhaustedException,
      IllegalStateException {
    try {
      blockedThreadsCount.incrementAndGet();
      CassandraClient client = (CassandraClient) pool.borrowObject();
      liveClientsFromPool.add(client);
      return client;
    } catch (NoSuchElementException e) {
      throw new PoolExhaustedException(e.getMessage());
    } finally {
      blockedThreadsCount.decrementAndGet();
    }
  }

  @Override
  public void close() {
    try {
      pool.close();
    } catch (Exception e) {
      log.error("Unable to close pool", e);
    }
  }

  @Override
  public int getNumIdle() {
    return pool.getNumIdle();
  }

  @Override
  public int getNumActive() {
    return pool.getNumActive();
  }

  @Override
  public int getNumBeforeExhausted() {
    return maxActive - pool.getNumActive();
  }

  @Override
  public void releaseClient(CassandraClient client) throws Exception {
    pool.returnObject(client);
  }

  private GenericObjectPool createPool() {
    GenericObjectPoolFactory poolFactory = new GenericObjectPoolFactory(clientFactory, maxActive,
        getObjectPoolExhaustedAction(exhaustedPolicy),
        maxWaitTimeWhenExhausted, maxIdle);
    return (GenericObjectPool) poolFactory.createPool();
  }

  public static byte getObjectPoolExhaustedAction(ExhaustedPolicy exhaustedAction){
    switch(exhaustedAction){
      case WHEN_EXHAUSTED_FAIL:
        return GenericObjectPool.WHEN_EXHAUSTED_FAIL;
      case WHEN_EXHAUSTED_BLOCK:
        return GenericObjectPool.WHEN_EXHAUSTED_BLOCK;
      case WHEN_EXHAUSTED_GROW:
        return GenericObjectPool.WHEN_EXHAUSTED_GROW;
      default:
        return GenericObjectPool.WHEN_EXHAUSTED_BLOCK;
    }
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    b.append("CassandraClientPoolImpl<");
    b.append(url);
    b.append(":");
    b.append(port);
    b.append(">");
    return b.toString();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isExhausted() {
    return getNumBeforeExhausted() <= 0 &&
        (exhaustedPolicy.equals(ExhaustedPolicy.WHEN_EXHAUSTED_BLOCK) ||
         exhaustedPolicy.equals(ExhaustedPolicy.WHEN_EXHAUSTED_FAIL));
  }

  @Override
  public int getNumBlockedThreads() {
    return blockedThreadsCount.intValue();
  }

  @Override
  public void updateKnownHosts() throws TException {
    Set<CassandraClient> removed = new HashSet<CassandraClient>();
    for (CassandraClient c: liveClientsFromPool) {
      if (c.isClosed()) {
        removed.add(c);
      } else {
        try {
          c.updateKnownHosts();
        } catch (TException e) {
          log.error("Unable to update hosts list at {}", c, e);
          throw e;
        }
      }
    }
    // perform cleanup
    liveClientsFromPool.removeAll(removed);
  }

  @Override
  public Set<String> getKnownHosts() {
    Set<String> hosts = new HashSet<String>();
    for (CassandraClient c: liveClientsFromPool) {
      if (!c.isClosed()) {
        hosts.addAll(c.getKnownHosts());
      }
    }
    return hosts;
  }

  @Override
  public void invalidateClient(CassandraClient client) {
    try {
      liveClientsFromPool.remove(client);
      client.markAsError();
      pool.invalidateObject(client);
    } catch (Exception e) {
      log.error("Unable to invalidate client " + client, e);
    }
  }

  @Override
  public Set<CassandraClient> getLiveClients() {
    return ImmutableSet.copyOf(liveClientsFromPool);
  }

  void reportDestroyed(CassandraClient client) {
    log.debug("Client has been destroyed: {}", client);
    liveClientsFromPool.remove(client);
  }

}
