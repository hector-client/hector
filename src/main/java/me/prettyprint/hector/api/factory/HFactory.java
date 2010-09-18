package me.prettyprint.hector.api.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.HColumn;
import me.prettyprint.cassandra.model.HSuperColumn;
import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Mutator;
import me.prettyprint.cassandra.model.QuorumAllConsistencyLevelPolicy;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.model.SliceQuery;
import me.prettyprint.cassandra.model.thrift.ThriftColumnQuery;
import me.prettyprint.cassandra.model.thrift.ThriftCountQuery;
import me.prettyprint.cassandra.model.thrift.ThriftMultigetSliceQuery;
import me.prettyprint.cassandra.model.thrift.ThriftMultigetSubSliceQuery;
import me.prettyprint.cassandra.model.thrift.ThriftMultigetSuperSliceQuery;
import me.prettyprint.cassandra.model.thrift.ThriftRangeSlicesQuery;
import me.prettyprint.cassandra.model.thrift.ThriftRangeSubSlicesQuery;
import me.prettyprint.cassandra.model.thrift.ThriftRangeSuperSlicesQuery;
import me.prettyprint.cassandra.model.thrift.ThriftSubColumnQuery;
import me.prettyprint.cassandra.model.thrift.ThriftSubCountQuery;
import me.prettyprint.cassandra.model.thrift.ThriftSubSliceQuery;
import me.prettyprint.cassandra.model.thrift.ThriftSuperColumnQuery;
import me.prettyprint.cassandra.model.thrift.ThriftSuperCountQuery;
import me.prettyprint.cassandra.model.thrift.ThriftSuperSliceQuery;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.Cluster;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.CountQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.MultigetSubSliceQuery;
import me.prettyprint.hector.api.query.MultigetSuperSliceQuery;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.RangeSubSlicesQuery;
import me.prettyprint.hector.api.query.RangeSuperSlicesQuery;
import me.prettyprint.hector.api.query.SubColumnQuery;
import me.prettyprint.hector.api.query.SubCountQuery;
import me.prettyprint.hector.api.query.SubSliceQuery;
import me.prettyprint.hector.api.query.SuperColumnQuery;
import me.prettyprint.hector.api.query.SuperCountQuery;
import me.prettyprint.hector.api.query.SuperSliceQuery;
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

  public static <K,N,V> Mutator<K> createMutator(KeyspaceOperator ko, Serializer<K> keySerializer) {
    return new Mutator<K>(ko, keySerializer);
  }

  public static <K,N,V> ColumnQuery<K,N,V> createColumnQuery(KeyspaceOperator ko, Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftColumnQuery<K,N,V>(ko, keySerializer, nameSerializer, valueSerializer);
  }

  public static <K, N> CountQuery<K, N> createCountQuery(KeyspaceOperator ko,
      Serializer<K> keySerializer, Serializer<N> nameSerializer) {
    return new ThriftCountQuery<K, N>(ko, keySerializer, nameSerializer);
  }

  public static <K,SN> SuperCountQuery<K,SN> createSuperCountQuery(KeyspaceOperator ko,
      Serializer<K> keySerializer, Serializer<SN> superNameSerializer) {
    return new ThriftSuperCountQuery<K,SN>(ko, keySerializer, superNameSerializer);
  }

  public static <K,SN,N> SubCountQuery<K,SN,N> createSubCountQuery(KeyspaceOperator ko,
      Serializer<K> keySerializer, Serializer<SN> superNameSerializer, Serializer<N> nameSerializer) {
    return new ThriftSubCountQuery<K,SN,N>(ko, keySerializer, superNameSerializer, nameSerializer);
  }

  public static ColumnQuery<String, String, String> createStringColumnQuery(KeyspaceOperator ko) {
    StringSerializer se = StringSerializer.get();
    return createColumnQuery(ko, se, se, se);
  }

  public static <K,SN,N,V> SuperColumnQuery<K,SN,N,V> createSuperColumnQuery(KeyspaceOperator ko,
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftSuperColumnQuery<K,SN, N, V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }


  public static <K,N,V> MultigetSliceQuery<K,N,V> createMultigetSliceQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftMultigetSliceQuery<K,N,V>(ko, keySerializer, nameSerializer, valueSerializer);
  }

  public static <K, SN, N, V> SubColumnQuery<K, SN, N, V> createSubColumnQuery(KeyspaceOperator ko,
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftSubColumnQuery<K, SN, N, V>(ko, keySerializer, sNameSerializer, nameSerializer,
        valueSerializer);
  }

  public static <K,SN,N,V> MultigetSuperSliceQuery<K,SN,N,V> createMultigetSuperSliceQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftMultigetSuperSliceQuery<K,SN,N,V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K,SN,N,V> MultigetSubSliceQuery<K,SN,N,V> createMultigetSubSliceQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftMultigetSubSliceQuery<K,SN,N,V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K,N,V> RangeSlicesQuery<K,N,V> createRangeSlicesQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftRangeSlicesQuery<K,N,V>(ko, keySerializer, nameSerializer, valueSerializer);
  }

  public static <K,SN,N,V> RangeSuperSlicesQuery<K,SN,N,V> createRangeSuperSlicesQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftRangeSuperSlicesQuery<K,SN,N,V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K,N,V> IndexedSlicesQuery<K, N, V> createIndexedSlicesQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new IndexedSlicesQuery<K, N, V>(ko, keySerializer, nameSerializer, valueSerializer);
  }

  public static <K,SN,N,V> RangeSubSlicesQuery<K,SN,N,V> createRangeSubSlicesQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftRangeSubSlicesQuery<K,SN,N,V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K,N,V> SliceQuery<K,N,V> createSliceQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new SliceQuery<K,N,V>(ko, keySerializer, nameSerializer, valueSerializer);
  }

  public static <K,SN,N,V> SubSliceQuery<K,SN,N,V> createSubSliceQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftSubSliceQuery<K,SN,N,V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K,SN,N,V> SuperSliceQuery<K,SN,N,V> createSuperSliceQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftSuperSliceQuery<K,SN,N,V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  /**
   * createSuperColumn accepts a variable number of column arguments
   * @param name supercolumn name
   * @param createColumn a variable number of column arguments
   */
  public static <SN,N,V> HSuperColumn<SN,N,V> createSuperColumn(SN name, List<HColumn<N,V>> columns,
      Serializer<SN> superNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new HSuperColumn<SN, N, V>(name, columns, createClock(), superNameSerializer,
        nameSerializer, valueSerializer);
  }

  public static <SN,N,V> HSuperColumn<SN,N,V> createSuperColumn(SN name, List<HColumn<N,V>> columns,
      long clock, Serializer<SN> superNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new HSuperColumn<SN, N, V>(name, columns, clock, superNameSerializer, nameSerializer,
        valueSerializer);
  }

  public static <N,V> HColumn<N,V> createColumn(N name, V value, long clock,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new HColumn<N, V>(name, value, clock, nameSerializer, valueSerializer);
  }

  /**
   * Creates a column with the clock of now.
   */
  public static <N,V> HColumn<N,V> createColumn(N name, V value,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new HColumn<N, V>(name, value, createClock(), nameSerializer, valueSerializer);
  }

  /**
   * Convienience method for creating a column with a String name and String value
   */
  public static HColumn<String,String> createStringColumn(String name, String value) {
    StringSerializer se = StringSerializer.get();
    return createColumn(name, value, se, se);
  }

  /**
   * Creates a clock of now with the default clock resolution (micorosec) as defined in
   * {@link CassandraHost}
   */
  public static long createClock() {
    return CassandraHost.DEFAULT_TIMESTAMP_RESOLUTION.createClock();
  }

}
