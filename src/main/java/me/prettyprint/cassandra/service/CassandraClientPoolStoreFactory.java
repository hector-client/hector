package me.prettyprint.cassandra.service;

/**
 *
 * @author Ran Tavory (rantan@gmail.com)
 *
 */
public class CassandraClientPoolStoreFactory {

  public CassandraClientPoolStore create() {
    CassandraClientPoolStore store = new CassandraClientPoolStoreImpl();
    CassandraClientMonitor monitor = JmxMonitor.INSTANCE.getCassandraMonitor();
    monitor.setPoolStore(store);
    return store;
  }
}
