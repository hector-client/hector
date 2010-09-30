package me.prettyprint.cassandra.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A factory for getting handles to {@link CassandraClientPool}.
 *
 * Usually you want to call {@link #get()} to get a handle of a reusable pool or create one if this
 * is the first time this method is called. Calling get() reuses a static pool so this is the most
 * efficient way of using connection/or client pools.
 * However, if you really feel you need to get a fresh pool, call {@link #createDefault()}.
 *
 * @author Ran Tavory (rantan@gmail.com)
 *
 */
public enum CassandraClientPoolFactory {

  INSTANCE;

  private volatile CassandraClientPool defaultPool;

  private static final Logger log = LoggerFactory.getLogger(CassandraClientPoolFactory.class);

  private CassandraClientPoolFactory() {
  }

  public static CassandraClientPoolFactory getInstance() {
    return INSTANCE;
  }

  /**
   * Get a reference to a reusable pool.
   * @return
   */
  public CassandraClientPool get() {
    CassandraClientPool tmp = defaultPool;
    if (tmp == null) {
      synchronized(this) {
        tmp = defaultPool;
        if (tmp == null) {
          defaultPool = tmp = createDefault();
        }
      }
    }
    return tmp;
  }

  /**
   * Create a new pool.
   * @return
   */
  public CassandraClientPool createDefault() {
    log.debug("Creating a new CassandraClientPool...");
    CassandraClientPool pool = new CassandraClientPoolImpl(
        JmxMonitor.INSTANCE.getCassandraMonitor());
    JmxMonitor.INSTANCE.addPool(pool);
    if ( log.isDebugEnabled() ) {
      log.debug("CassandraClientPool was created: {}", pool);
    }
    return pool;
  }

  /**
   * Creates a new {@link CassandraClientPool} instance.
   * Caller may need to verify that no more than one pool in the application is created, depending
   * on the caller logic; This method does not perform this kind of check.
   *
   * @param cassandraHostConfigurator
   * @return
   */
  public CassandraClientPool createNew(CassandraHostConfigurator cassandraHostConfigurator) {
    log.debug("Creating a new CassandraClientPool...");
    CassandraClientPool pool = new CassandraClientPoolImpl(
        JmxMonitor.INSTANCE.getCassandraMonitor(), cassandraHostConfigurator);
    JmxMonitor.INSTANCE.addPool(pool);
    if ( cassandraHostConfigurator.getRetryDownedHosts() ) {
      pool.initializeDownHostRetryService();      
    }
    if ( log.isDebugEnabled() ) {
      log.debug("CassandraClientPool was created: {}", pool);
    }
    return pool;
  }
}
