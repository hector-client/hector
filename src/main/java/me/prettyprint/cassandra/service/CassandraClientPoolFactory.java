package me.prettyprint.cassandra.service;


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

  private CassandraClientPool defaultPool;

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
    if (defaultPool == null) {
      synchronized (INSTANCE) {
        if (defaultPool == null) {
          defaultPool = createDefault();
        }
      }
    }
    return defaultPool;
  }

  /**
   * Create a new pool.
   * @return
   */
  public CassandraClientPool createDefault() {
    CassandraClientPool pool = new CassandraClientPoolImpl(
        JmxMonitor.INSTANCE.getCassandraMonitor());
    JmxMonitor.INSTANCE.addPool(pool);
    return pool;
  }

  public CassandraClientPool createNew(CassandraHostConfigurator cassandraHostConfigurator) {
    CassandraClientPool pool = new CassandraClientPoolImpl(
        JmxMonitor.INSTANCE.getCassandraMonitor(), cassandraHostConfigurator.buildCassandraHosts());
    JmxMonitor.INSTANCE.addPool(pool);
    return pool;
  }
}
