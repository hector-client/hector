package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.noneNull;
import static me.prettyprint.cassandra.utils.Assert.notNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.extractors.StringExtractor;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.Cluster;

import org.apache.cassandra.thrift.Clock;
import org.apache.cassandra.thrift.ColumnPath;
/**
 * A convenience class with bunch of factory static methods to help create a mutator,
 * queries etc.
 *
 * @author Ran
 * @author zznate
 */
public final class HFactory {

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

  public static <K,N,V> Mutator<K> createMutator(KeyspaceOperator ko, Extractor<K> keyExtractor) {
    return new Mutator<K>(ko, keyExtractor);
  }

  public static <K,N,V> ColumnQuery<K,N,V> createColumnQuery(KeyspaceOperator ko, Extractor<K> keyExtractor,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new ColumnQuery<K,N,V>(ko, keyExtractor, nameExtractor, valueExtractor);
  }

  public static ColumnQuery<String, String, String> createStringColumnQuery(KeyspaceOperator ko) {
    StringExtractor se = StringExtractor.get();
    return createColumnQuery(ko, se, se, se);
  }

  public static <K,SN,N,V> SuperColumnQuery<K,SN,N,V> createSuperColumnQuery(KeyspaceOperator ko,
      Extractor<K> keyExtractor, Extractor<SN> sNameExtractor, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new SuperColumnQuery<K,SN, N, V>(ko, keyExtractor, sNameExtractor, nameExtractor, valueExtractor);
  }

  public static <K,N,V> MultigetSliceQuery<K,N,V> createMultigetSliceQuery(
      KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new MultigetSliceQuery<K,N,V>(ko, keyExtractor, nameExtractor, valueExtractor);
  }

  public static <K,SN,N,V> MultigetSuperSliceQuery<K,SN,N,V> createMultigetSuperSliceQuery(
      KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<SN> sNameExtractor, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new MultigetSuperSliceQuery<K,SN,N,V>(ko, keyExtractor, sNameExtractor, nameExtractor, valueExtractor);
  }

  public static <K,SN,N,V> MultigetSubSliceQuery<K,SN,N,V> createMultigetSubSliceQuery(
      KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<SN> sNameExtractor, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new MultigetSubSliceQuery<K,SN,N,V>(ko, keyExtractor, sNameExtractor, nameExtractor, valueExtractor);
  }

  public static <K,N,V> RangeSlicesQuery<K,N,V> createRangeSlicesQuery(
      KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new RangeSlicesQuery<K,N,V>(ko, keyExtractor, nameExtractor, valueExtractor);
  }

  public static <K,SN,N,V> RangeSuperSlicesQuery<K,SN,N,V> createRangeSuperSlicesQuery(
      KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<SN> sNameExtractor, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    return new RangeSuperSlicesQuery<K,SN,N,V>(ko, keyExtractor, sNameExtractor, nameExtractor, valueExtractor);
  }

  public static <K,SN,N,V> RangeSubSlicesQuery<K,SN,N,V> createRangeSubSlicesQuery(
      KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<SN> sNameExtractor, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    return new RangeSubSlicesQuery<K,SN,N,V>(ko, keyExtractor, sNameExtractor, nameExtractor, valueExtractor);
  }

  public static <K,N,V> SliceQuery<K,N,V> createSliceQuery(
      KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new SliceQuery<K,N,V>(ko, keyExtractor, nameExtractor, valueExtractor);
  }

  public static <K,SN,N,V> SubSliceQuery<K,SN,N,V> createSubSliceQuery(
      KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<SN> sNameExtractor, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    return new SubSliceQuery<K,SN,N,V>(ko, keyExtractor, sNameExtractor, nameExtractor, valueExtractor);
  }

  public static <K,SN,N,V> SuperSliceQuery<K,SN,N,V> createSuperSliceQuery(
      KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<SN> sNameExtractor, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    return new SuperSliceQuery<K,SN,N,V>(ko, keyExtractor, sNameExtractor, nameExtractor, valueExtractor);
  }

  /**
   * createSuperColumn accepts a variable number of column arguments
   * @param name supercolumn name
   * @param createColumn a variable number of column arguments
   */
  public static <SN,N,V> HSuperColumn<SN,N,V> createSuperColumn(SN name, List<HColumn<N,V>> columns,
      Extractor<SN> superNameExtractor, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new HSuperColumn<SN, N, V>(name, columns, createClock(), superNameExtractor,
        nameExtractor, valueExtractor);
  }

  public static <SN,N,V> HSuperColumn<SN,N,V> createSuperColumn(SN name, List<HColumn<N,V>> columns,
      Clock clock, Extractor<SN> superNameExtractor, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    return new HSuperColumn<SN, N, V>(name, columns, clock, superNameExtractor, nameExtractor,
        valueExtractor);
  }

  public static <N,V> HColumn<N,V> createColumn(N name, V value, Clock clock,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new HColumn<N, V>(name, value, clock, nameExtractor, valueExtractor);
  }

  /**
   * Creates a column with the clock of now.
   */
  public static <N,V> HColumn<N,V> createColumn(N name, V value,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    return new HColumn<N, V>(name, value, createClock(), nameExtractor, valueExtractor);
  }

  /**
   * Convienience method for creating a column with a String name and String value
   */
  public static HColumn<String,String> createStringColumn(String name, String value) {
    StringExtractor se = StringExtractor.get();
    return createColumn(name, value, se, se);
  }

  /**
   * Creates a clock of now with the default clock resolution (micorosec) as defined in
   * {@link CassandraHost}
   */
  public static Clock createClock() {
    return CassandraHost.DEFAULT_TIMESTAMP_RESOLUTION.createClock();
  }

  // probably should be typed for thrift vs. avro
  /*package*/ static <N> ColumnPath createColumnPath(String columnFamilyName, N columnName,
      Extractor<N> nameExtractor) {
    return createColumnPath(columnFamilyName, nameExtractor.toBytes(columnName));
  }

  private static <N> ColumnPath createColumnPath(String columnFamilyName, byte[] columnName) {
    notNull(columnFamilyName, "columnFamilyName cannot be null");
    ColumnPath columnPath = new ColumnPath(columnFamilyName);
    if (columnName != null) {
      columnPath.setColumn(columnName);
    }
    return columnPath;
  }

  /*package*/ static <N> ColumnPath createColumnPath(String columnFamilyName) {
    return createColumnPath(columnFamilyName, null);
  }

  /*package*/ static <SN,N> ColumnPath createSuperColumnPath(String columnFamilyName,
      SN superColumnName, N columnName, Extractor<SN> superNameExtractor,
      Extractor<N> nameExtractor) {
    noneNull(columnFamilyName, superColumnName, superNameExtractor, nameExtractor);
    ColumnPath columnPath = createColumnPath(columnFamilyName, nameExtractor.toBytes(columnName));
    columnPath.setSuper_column(superNameExtractor.toBytes(superColumnName));
    return columnPath;
  }

  /*package*/ static <SN> ColumnPath createSuperColumnPath(String columnFamilyName,
      SN superColumnName, Extractor<SN> superNameExtractor) {
    noneNull(columnFamilyName, superNameExtractor);
    ColumnPath columnPath = createColumnPath(columnFamilyName, null);
    if (superColumnName != null) {
      columnPath.setSuper_column(superNameExtractor.toBytes(superColumnName));
    }
    return columnPath;
  }
}
