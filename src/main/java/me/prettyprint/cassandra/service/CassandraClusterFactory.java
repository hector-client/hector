package me.prettyprint.cassandra.service;


/**
 * Controls access to CassandraCluster, as we need a live CassandraClient 
 * in order to do anything useful
 * 
 * @author Nate McCall (nate@vervewireless.com)
 *
 */
public enum CassandraClusterFactory {
  
  INSTANCE;
  
  public static CassandraClusterFactory getInstance() {
    return INSTANCE;
  }
    
  
  private CassandraClusterFactory() {
  }
  
  public CassandraCluster create(CassandraClientPool cassandraClientPool) throws PoolExhaustedException, Exception {
    return new CassandraClusterImpl(cassandraClientPool);
  }
  
}
