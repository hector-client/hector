package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.ConsistencyLevelPolicy.OperationType;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.exceptions.HectorException;

/**
 *
 * @author Ran Tavory
 *
 */
public class ExecutingKeyspace implements Keyspace {

  private ConsistencyLevelPolicy consistencyLevelPolicy;

  private final Cluster cluster;
  private final String keyspace;

  public ExecutingKeyspace(String keyspace, Cluster cluster,
      ConsistencyLevelPolicy consistencyLevelPolicy) {
    Assert.noneNull(keyspace, cluster, consistencyLevelPolicy);
    this.keyspace = keyspace;
    this.cluster = cluster;
    this.consistencyLevelPolicy = consistencyLevelPolicy;
  }

  @Override
  public void setConsistencyLevelPolicy(ConsistencyLevelPolicy cp) {
    this.consistencyLevelPolicy = cp;
  }

  @Override
  public Cluster getCluster() {
    return cluster;
  }

  @Override
  public String toString() {
    return "ExecutingKeyspace(" + keyspace +"," + cluster + ")";
  }

  @Override
  public long createClock() {
    return cluster.createClock();
  }

  public <T> ExecutionResult<T> doExecute(KeyspaceOperationCallback<T> koc) throws HectorException {
    CassandraClient c = null;
    KeyspaceService ks = null;
    try {
        c = cluster.borrowClient();
        ks = c.getKeyspace(keyspace, consistencyLevelPolicy.get(OperationType.READ));
        return koc.doInKeyspaceAndMeasure(ks);
    } finally {
      if (ks != null) {
        cluster.releaseClient(ks.getClient());
      }
    }
  }
}
