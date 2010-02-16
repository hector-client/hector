package me.prettyprint.cassandra.service;

/**
 *
 * @author Ran Tavory (rantan@gmail.com)
 *
 */
public class CassandraClientPoolFactory {

  public CassandraClientPool create() {
    CassandraClientPool store = new CassandraClientPoolImpl();
    CassandraClientMonitor monitor = JmxMonitor.INSTANCE.getCassandraMonitor();
    monitor.setPoolStore(store);
    return store;
  }
}
