package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.model.ConsistencyLevelPolicy.OperationType;
import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.Cluster;
import me.prettyprint.cassandra.service.Keyspace;



public class KeyspaceOperatorImpl implements KeyspaceOperator {

  private ConsistencyLevelPolicy consistencyLevelPolicy;
  
  private final Cluster cluster;
  private final String keyspace;
  
  KeyspaceOperatorImpl(String keyspace, Cluster cluster, ConsistencyLevelPolicy consistencyLevelPolicy) {
    this.keyspace = keyspace;
    this.cluster = cluster;
    this.consistencyLevelPolicy = consistencyLevelPolicy;
  }
  
  @Override
  public Cluster getCluster() {
    return cluster;
  }

  @Override
  public void setConsistencyLevelPolicy(ConsistencyLevelPolicy cp) {
    this.consistencyLevelPolicy = cp;
  }
  
  public <T> T doExecute(KeyspaceOperationCallback<T> koc) throws HectorException {
    CassandraClient c = null;
    Keyspace ks = null; 
    try {
        c = cluster.borrowClient();
        ks = c.getKeyspace(keyspace, consistencyLevelPolicy.get(OperationType.READ));
        return koc.doInKeyspace(ks);         
    } finally {
      // dealing with cluster negate the need for pool.realeaseClient(ks.getClient()) stuff
      cluster.releaseClient();                        
    } 
  }

  
}
