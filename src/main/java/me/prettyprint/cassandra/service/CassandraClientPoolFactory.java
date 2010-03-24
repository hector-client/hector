package me.prettyprint.cassandra.service;

import java.util.List;

/**
 * A factory for getting handles to {@link CassandraClientPool}.
 *
 * Usually you want to call {@link #get()} to get a handle of a reusable pool or create one if this
 * is the first time this method is called. Calling get() reuses a static pool so this is the most
 * efficient way of using connection/or client pools.
 * However, if you really feel you need to get a fresh pool, call {@link #createNew()}.
 *
 * @author Ran Tavory (rantan@gmail.com)
 *
 */
public enum CassandraClientPoolFactory {

  INSTANCE;

  private final CassandraClientPool pool;

  private final JmxMonitor jmx;

  private CassandraClientPoolFactory() {
    jmx = new JmxMonitor();
    pool = createNew();
  }
  
  public static CassandraClientPoolFactory getInstance() {
    return INSTANCE;
  }

  /**
   * Get a reference to a reusable pool.
   * @return
   */
  public CassandraClientPool get() {
    return pool;
  }

  /**
   * Create a new pool.
   * @return
   */
  public CassandraClientPool createNew() {
    CassandraClientPool pool = new CassandraClientPoolImpl(jmx.getCassandraMonitor());
    jmx.addPool(pool);
    return pool;
  }
  
  public CassandraClientPool createNew(String[] urlPorts) {
    CassandraClientPool pool = new CassandraClientPoolImpl(jmx.getCassandraMonitor(), urlPorts);
    jmx.addPool(pool);
    return pool;
  }
}
