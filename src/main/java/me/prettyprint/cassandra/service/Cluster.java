package me.prettyprint.cassandra.service;

import static me.prettyprint.cassandra.model.HFactory.createColumn;
import static me.prettyprint.cassandra.model.HFactory.createKeyspaceOperator;
import static me.prettyprint.cassandra.model.HFactory.createMutator;
import static me.prettyprint.cassandra.model.HFactory.getOrCreateCluster;

import java.util.Set;

import me.prettyprint.cassandra.model.HectorPoolException;
import me.prettyprint.cassandra.model.MutationResult;
import me.prettyprint.cassandra.model.Mutator;

/**
 * A cluster instance the client side representation of a cassandra server cluster.
 * 
 * The cluster is usually the main entry point for programs using hector. To start operating on 
 * cassandra cluster you first get or create a cluster, then a keyspace operator for the keyspace
 * you're interested in and then create mutations of queries
 * <code>
 * 1. get a cluster: 
 *    Cluster cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
 * 2. get a keyspace from this cluster: 
 *    KeyspaceOperator ko = createKeyspaceOperator("Keyspace1", cluster);    
 * 3. Create a mutator:    
 *    Mutator m = createMutator(ko);
 * 4. Make a mutation:
 *    MutationResult mr = m.insert("key", cf, 
 *      createColumn("name", "value", se, se));
 *
 * @author Ran Tavory
 *
 */
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
