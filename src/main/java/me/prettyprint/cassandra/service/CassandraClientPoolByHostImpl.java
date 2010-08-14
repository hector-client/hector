package me.prettyprint.cassandra.service;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import me.prettyprint.cassandra.model.HectorException;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

/*package*/ class CassandraClientPoolByHostImpl implements CassandraClientPoolByHost {

  private static final Logger log = LoggerFactory.getLogger(CassandraClientPoolByHostImpl.class);

  private final CassandraClientFactory clientFactory;
  private final String name;
  private final int maxActive;
  private final int maxIdle;
  private final boolean lifo;
  private final long minEvictableIdleTimeMillis;
  private final long timeBetweenEvictionRunsMillis;

  private final ExhaustedPolicy exhaustedPolicy;
  private final long maxWaitTimeWhenExhausted;
  private final GenericObjectPool pool;

  private final ExceptionsTranslator xTrans;
  private final CassandraHost cassandraHost;

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

  public CassandraClientPoolByHostImpl(CassandraHost cassandraHost,
      CassandraClientPool pools,
      CassandraClientMonitor cassandraClientMonitor) {
    this(cassandraHost, pools, cassandraClientMonitor, new CassandraClientFactory(pools, cassandraHost, cassandraClientMonitor));
  }

  public CassandraClientPoolByHostImpl(CassandraHost cassandraHost,
      CassandraClientPool pools,
      CassandraClientMonitor cassandraClientMonitor,
      CassandraClientFactory cassandraClientFactory) {
    log.debug("Creating new connection pool for {}", cassandraHost.getUrl());
    this.cassandraHost = cassandraHost;
    this.name = cassandraHost.getName();
    this.maxActive = cassandraHost.getMaxActive();
    this.maxIdle = cassandraHost.getMaxIdle();
    this.lifo = cassandraHost.getLifo();
    this.minEvictableIdleTimeMillis = cassandraHost.getMinEvictableIdleTimeMillis();
    this.timeBetweenEvictionRunsMillis = cassandraHost.getTimeBetweenEvictionRunsMillis();
    this.maxWaitTimeWhenExhausted = cassandraHost.getMaxWaitTimeWhenExhausted();
    this.exhaustedPolicy = cassandraHost.getExhaustedPolicy();
    this.clientFactory = cassandraClientFactory;
    xTrans = new ExceptionsTranslatorImpl();

    blockedThreadsCount = new AtomicInteger(0);
    // Create a set implemented as a ConcurrentHashMap for performance and concurrency.
    liveClientsFromPool =
        Collections.newSetFromMap(new ConcurrentHashMap<CassandraClient,Boolean>());
    pool = createPool();
  }


  public CassandraClient borrowClient() throws HectorException {
    log.debug("Borrowing client from {}", this);
    try {
      blockedThreadsCount.incrementAndGet();
      log.debug("Just before borrow: {}", toDebugString());
      CassandraClient client = (CassandraClient) pool.borrowObject();
      client.markAsBorrowed();
      liveClientsFromPool.add(client);
      log.debug("Client {} successfully borrowed from {} (thread={})",
          new Object[] {client, this, Thread.currentThread().getName()});
      return client;
    } catch (NoSuchElementException e) {
      log.info("Pool is exhausted {} (thread={})", toDebugString(), Thread.currentThread().getName());
      throw xTrans.translate(e);
    } catch (Exception e) {
      throw xTrans.translate(e);
    } finally {
      blockedThreadsCount.decrementAndGet();
    }
  }

  private String toDebugString() {
    StringBuilder s = new StringBuilder();
    s.append(toString());
    s.append("&maxActive=");
    s.append(pool.getMaxActive());
    s.append("&maxIdle=");
    s.append(pool.getMaxIdle());
    s.append("&lifo=");
    s.append(pool.getLifo());
    s.append("&minEvictableIdleTimeMillis=");
    s.append(pool.getMinEvictableIdleTimeMillis());
    s.append("&timeBetweenEvictionRunsMillis=");
    s.append(pool.getTimeBetweenEvictionRunsMillis());
    s.append("&blockedThreadCount=");
    s.append(blockedThreadsCount);
    s.append("&liveClientsFromPool.size=");
    s.append(liveClientsFromPool.size());
    s.append("&numActive=");
    s.append(pool.getNumActive());
    s.append("&numIdle=");
    s.append(pool.getNumIdle());
    return s.toString();
  }


  public void close() {
    try {
      pool.close();
    } catch (Exception e) {
      log.error("Unable to close pool", e);
    }
  }


  public int getNumIdle() {
    return pool.getNumIdle();
  }


  public int getNumActive() {
    return pool.getNumActive();
  }


  public int getNumBeforeExhausted() {
    return maxActive - pool.getNumActive();
  }


  public void releaseClient(CassandraClient client) throws HectorException {
    log.debug("Maybe releasing client {}. is aready Released? {}", client, client.isReleased());
    if (client.isReleased()) {
      // The common case with clients that had errors is that they've already been release.
      // If we release them again the pool's counters will go crazy so we don't want that...
      return;
    }
    client.markAsReleased();
    try {
      pool.returnObject(client);
    } catch (Exception e) {
      throw xTrans.translate(e);
    }
  }

  private GenericObjectPool createPool() {
    GenericObjectPoolFactory poolFactory = new GenericObjectPoolFactory(clientFactory, maxActive,
        getObjectPoolExhaustedAction(exhaustedPolicy),
        maxWaitTimeWhenExhausted, maxIdle);
    GenericObjectPool p = (GenericObjectPool) poolFactory.createPool();
    p.setTestOnBorrow(true);
    // maxIdle controls the maximum number of objects that can sit idle in the pool at any time.
    // When negative, there is no limit to the number of objects that may be idle at one time.
    p.setMaxIdle(maxIdle);

    p.setLifo(lifo);
    p.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    p.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

    return p;
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


  public String toString() {
    return String.format("CassandraClientPoolImpl<%s>", name);
  }


  public String getName() {
    return name;
  }


  public boolean isExhausted() {
    return getNumBeforeExhausted() <= 0 &&
        (exhaustedPolicy.equals(ExhaustedPolicy.WHEN_EXHAUSTED_BLOCK) ||
         exhaustedPolicy.equals(ExhaustedPolicy.WHEN_EXHAUSTED_FAIL));
  }


  public int getNumBlockedThreads() {
    return blockedThreadsCount.intValue();
  }



  public CassandraHost getCassandraHost() {
    return cassandraHost;
  }


  public void invalidateClient(CassandraClient client) {
    log.debug("Invalidating client {}", client);
    try {
      liveClientsFromPool.remove(client);
      client.markAsError();
      if (!client.isReleased()) {
        client.markAsReleased();
        pool.invalidateObject(client);
      }
    } catch (Exception e) {
      log.error("Unable to invalidate client " + client, e);
    }
  }


  public Set<CassandraClient> getLiveClients() {
    return ImmutableSet.copyOf(liveClientsFromPool);
  }

  void reportDestroyed(CassandraClient client) {
    log.debug("Client has been destroyed: {} (thread={})", client, Thread.currentThread().getName());
    liveClientsFromPool.remove(client);
  }


  public void invalidateAll() {
    log.debug("Invalidating all connections at {} (thread={})", this,
        Thread.currentThread().getName());
    while (!liveClientsFromPool.isEmpty()) {
      //TODO(ran): There's a multithreading sync issue here.
      invalidateClient(liveClientsFromPool.iterator().next());
    }
  }

  public Long getMinEvictableIdleTimeMillis() {
    return minEvictableIdleTimeMillis;
  }

  public Long getTimeBetweenEvictionRunsMillis() {
    return timeBetweenEvictionRunsMillis;
  }

}
