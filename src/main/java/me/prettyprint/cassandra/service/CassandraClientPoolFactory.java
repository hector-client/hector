package me.prettyprint.cassandra.service;

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

  private CassandraClientPool pool;

  /**
   * Get a reference to a reusable pool.
   * @return
   */
  public CassandraClientPool get() {
    if (pool == null) {
      synchronized (this) {
        if (pool == null) {
          pool = createNew();
        }
      }
    }
    return pool;
  }

  /**
   * Create a new pool.
   * @return
   */
  public CassandraClientPool createNew() {
    CassandraClientPool store = new CassandraClientPoolImpl();
    CassandraClientMonitor monitor = JmxMonitor.INSTANCE.getCassandraMonitor();
    monitor.setPoolStore(store);
    return store;
  }
}
