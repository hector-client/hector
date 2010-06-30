package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.model.ConsistencyLevelPolicy.OperationType;
import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.Cluster;
import me.prettyprint.cassandra.service.Keyspace;

public class KeyspaceOperator {

  private ConsistencyLevelPolicy consistencyLevelPolicy;
  
  private final Cluster cluster;
  private final String keyspace;
  
  KeyspaceOperator(String keyspace, Cluster cluster, ConsistencyLevelPolicy consistencyLevelPolicy) {
    this.keyspace = keyspace;
    this.cluster = cluster;
    this.consistencyLevelPolicy = consistencyLevelPolicy;
  }
  
  public void setConsistencyLevelPolicy(ConsistencyLevelPolicy cp) {
    this.consistencyLevelPolicy = cp;
  }
  
  public Cluster getCluster() {
    return cluster;
  }
  
  /*package*/ <T> ExecutionResult<T> doExecute(KeyspaceOperationCallback<T> koc) throws HectorException {
    CassandraClient c = null;
    Keyspace ks = null; 
    try {
        c = cluster.borrowClient();
        ks = c.getKeyspace(keyspace, consistencyLevelPolicy.get(OperationType.READ));
        return koc.doInKeyspaceAndMeasure(ks);         
    } finally {
      cluster.releaseClient(ks.getClient());                        
    }
  }

  @Override
  public String toString() {
    return "KeyspaceOperator(" + keyspace +"," + cluster + ")";
  }

  public long createTimestamp() {
    return cluster.createTimestamp();
  }
}
