package me.prettyprint.cassandra.service;

import java.util.Set;

import me.prettyprint.cassandra.model.HectorPoolException;

public class Cluster {
  
  private final CassandraClientPool pool;
  private final String name;
  private TimestampResolution timestampResolution = CassandraHost.DEFAULT_TIMESTAMP_RESOLUTION;
  
  public Cluster(String clusterName, CassandraHostConfigurator cassandraHostConfigurator) {
    pool = CassandraClientPoolFactory.INSTANCE.createNew(cassandraHostConfigurator);
    name = clusterName;
  }

  public Set<String> getKnownHosts() {
    //TODO
    return null;
  }
  
  public void addHost(CassandraHost cassandraHost) {
    //TODO
  }
  
  
  /**
   * Descriptive name of the cluster. 
   * This name is used to identify the cluster.
   * @return
   */
  public String getName() {
    return name;
  }
  
  // rest of the methods from the current CassandraCluster
  
  public CassandraClient borrowClient() throws HectorPoolException {
    return pool.borrowClient();
  }
  
  public void releaseClient(CassandraClient client) {
    pool.releaseClient(client);
  }

  @Override
  public String toString() {
    return "Cluster(" + name + "," + pool + ")";
  }

  public TimestampResolution getTimestampResolution() {
    return timestampResolution;
  }

  public Cluster setTimestampResolution(TimestampResolution timestampResolution) {
    this.timestampResolution = timestampResolution;
    return this;
  }
  
  public long createTimestamp() {
    return timestampResolution.createTimestamp();
  }
}
