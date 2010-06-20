package me.prettyprint.cassandra.service;

import java.util.Set;

import me.prettyprint.cassandra.model.HectorPoolException;

public class ClusterImpl implements Cluster {

  private final CassandraClientPool pool;
  private final String name;
  
  public ClusterImpl(String clusterName, CassandraHostConfigurator cassandraHostConfigurator) {
    pool = CassandraClientPoolFactory.INSTANCE.createNew(cassandraHostConfigurator);
    name = clusterName;
  }

  @Override
  public void addHost(CassandraHost cassandraHost) {
    // TODO Auto-generated method stub

  }

  @Override
  public CassandraClient borrowClient() throws HectorPoolException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<String> getKnownHosts() {
    return pool.getKnownHosts();
  }

  @Override
  public void releaseClient() {
    // TODO Auto-generated method stub

  }

  @Override
  public String getName() {
    return name;
  }

}
