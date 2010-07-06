package me.prettyprint.cassandra.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.Cluster;

import org.apache.cassandra.thrift.ColumnPath;

/**
 * A convenience class with bunch of factory static methods to help create a mutator,
 * queries etc.
 * 
 * @author Ran
 *
 */
public class HFactory {

  private static final Map<String, Cluster> clusters = new HashMap<String, Cluster>();

  private static final ConsistencyLevelPolicy DEFAULT_CONSISTENCY_LEVEL_POLICY =
    new QuorumAllConsistencyLevelPolicy();

  public static Cluster getCluster(String clusterName) {
    return clusters.get(clusterName);
  }
  /**
   * 
   * @param clusterName The cluster name. This is an identifying string for the cluster, e.g.
   * "production" or "test" etc. Clusters will be created on demand per each unique clusterName key.
   * @param hostIp host:ip format string
   * @return
   */
  public static Cluster getOrCreateCluster(String clusterName, String hostIp) {
    /*
     I would like to move off of string literals for hosts, perhaps
     providing them for convinience, and used specific types

     */
    return getOrCreateCluster(clusterName, new CassandraHostConfigurator(hostIp));
  }

  public static Cluster getOrCreateCluster(String clusterName,
      CassandraHostConfigurator cassandraHostConfigurator) {
    Cluster c = clusters.get(clusterName);
    if (c == null) {
      synchronized (clusters) {
        c = clusters.get(clusterName);
        if (c == null) {
          c = createCluster(clusterName, cassandraHostConfigurator);
          clusters.put(clusterName, c);
        }
      }
    }
    return c;
  }

  public static Cluster createCluster(String clusterName, CassandraHostConfigurator cassandraHostConfigurator) {
    return new Cluster(clusterName, cassandraHostConfigurator);
  }

  /**
   * Creates a KeyspaceOperator with the default consistency level policy.
   * @param keyspace
   * @param cluster
   * @return
   */
  public static KeyspaceOperator createKeyspaceOperator(String keyspace, Cluster cluster) {
    return createKeyspaceOperator(keyspace, cluster, createDefaultConsistencyLevelPolicy());
  }

  public static KeyspaceOperator createKeyspaceOperator(String keyspace, Cluster cluster,
      ConsistencyLevelPolicy consistencyLevelPolicy) {
    return new KeyspaceOperator(keyspace, cluster, consistencyLevelPolicy);
  }

  public static ConsistencyLevelPolicy createDefaultConsistencyLevelPolicy() {
    return DEFAULT_CONSISTENCY_LEVEL_POLICY;
  }

  public static <N,V> Mutator createMutator(KeyspaceOperator ko) {
    return new Mutator(ko);
  }

  public static <N,V> ColumnQuery<N,V> createColumnQuery(KeyspaceOperator ko,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new ColumnQuery<N,V>(ko, nameExtractor, valueExtractor);
  }

  public static <SN,N,V> SuperColumnQuery<SN,N,V> createSuperColumnQuery(KeyspaceOperator ko) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * 
   * @param <K> Row key type
   * @param keyspaceOperator
   * @return
   */
  public static <N,V> MultigetSliceQuery<N,V>
  createMultigetSliceQuery(KeyspaceOperator keyspaceOperator) {
    // TODO Auto-generated method stub
    return null;
  }


  /**
   * createSuperColumn accepts a variable number of column arguments
   * @param name supercolumn name
   * @param createColumn a variable number of column arguments
   * @return
   */
  public static <SN,N,V> HSuperColumn<SN,N,V> createSuperColumn(SN name, List<HColumn<N,V>> columns, 
      Extractor<SN> superNameExtractor) {
    return new HSuperColumn<SN, N, V>(name, columns, superNameExtractor, createTimestamp());
  }
  
  public static <SN,N,V> HSuperColumn<SN,N,V> createSuperColumn(SN name, List<HColumn<N,V>> columns, 
      long timestamp, Extractor<SN> superNameExtractor) {
    return new HSuperColumn<SN, N, V>(name, columns, superNameExtractor, timestamp);
  }

  public static <N,V> HColumn<N,V> createColumn(N name, V value, long timestamp,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new HColumn<N, V>(name, value, timestamp, nameExtractor, valueExtractor);
  }

  /**
   * Creates a column with the timestamp of now.
   */
  public static <N,V> HColumn<N,V> createColumn(N name, V value,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new HColumn<N, V>(name, value, createTimestamp(), nameExtractor, valueExtractor);
  }

  /**
   * Creates a timestamp of now with the default timestamp resolution (micorosec) as defined in
   * {@link CassandraHost}
   */
  public static long createTimestamp() {
    return CassandraHost.DEFAULT_TIMESTAMP_RESOLUTION.createTimestamp();
  }

  // probably should be typed for thrift vs. avro
  /*package*/ static <N> ColumnPath createColumnPath(String columnFamilyName, N columnName,
      Extractor<N> nameExtractor) {
    return createColumnPath(columnFamilyName, nameExtractor.toBytes(columnName));
  }

  /*package*/ static <N> ColumnPath createColumnPath(String columnFamilyName, byte[] columnName) {
    ColumnPath columnPath = new ColumnPath(columnFamilyName);
    if ( columnName != null ) {
      columnPath.setColumn(columnName);
    }
    return columnPath;
  }
  
  /*package*/ static <N> ColumnPath createSuperColumnPath(String columnFamilyName, byte[] superColumnName, byte[] columnName) {
    ColumnPath columnPath = createColumnPath(columnFamilyName, columnName);
    if ( superColumnName != null ) {
      columnPath.setSuper_column(superColumnName);
    } 
    return columnPath;
  }
}
