package me.prettyprint.cassandra.model;

import org.apache.cassandra.thrift.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.model.ConsistencyLevelPolicy.OperationType;
import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.Cluster;
import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

public /*final*/ class KeyspaceOperator {

   private static final Logger log = LoggerFactory.getLogger(KeyspaceOperator.class);

  private ConsistencyLevelPolicy consistencyLevelPolicy;

  private final Cluster cluster;
  private final String keyspace;

  /*package*/ KeyspaceOperator(String keyspace, Cluster cluster,
      ConsistencyLevelPolicy consistencyLevelPolicy) {
    Assert.noneNull(keyspace, cluster, consistencyLevelPolicy);
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

  @Override
  public String toString() {
    return "KeyspaceOperator(" + keyspace +"," + cluster + ")";
  }

  public Clock createClock() {
    return cluster.createClock();
  }

  /*package*/ <T> ExecutionResult<T> doExecute(KeyspaceOperationCallback<T> koc) throws HectorException {
    CassandraClient c = null;
    Keyspace ks = null;
    try {
        c = cluster.borrowClient();
        ks = c.getKeyspace(keyspace, consistencyLevelPolicy.get(OperationType.READ));
        return koc.doInKeyspaceAndMeasure(ks);
    } catch (NotFoundException ex) {
        log.error("Keyspace not found", ex);
        return null;
    } catch (Exception ex) {
        log.error("A problem occurred while getting keyspace", ex);
        return null;
    } finally {
      cluster.releaseClient(ks.getClient());
    }
  }
}
