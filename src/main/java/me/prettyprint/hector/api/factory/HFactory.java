package me.prettyprint.hector.api.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.ConsistencyLevelPolicy;
import me.prettyprint.cassandra.model.CountQuery;
import me.prettyprint.cassandra.model.HColumn;
import me.prettyprint.cassandra.model.HSuperColumn;
import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.cassandra.model.IndexedSlicesQueryTest;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.MultigetSliceQuery;
import me.prettyprint.cassandra.model.MultigetSubSliceQuery;
import me.prettyprint.cassandra.model.MultigetSuperSliceQuery;
import me.prettyprint.cassandra.model.Mutator;
import me.prettyprint.cassandra.model.QuorumAllConsistencyLevelPolicy;
import me.prettyprint.cassandra.model.RangeSlicesQuery;
import me.prettyprint.cassandra.model.RangeSubSlicesQuery;
import me.prettyprint.cassandra.model.RangeSuperSlicesQuery;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.model.SliceQuery;
import me.prettyprint.cassandra.model.SubColumnQuery;
import me.prettyprint.cassandra.model.SubCountQuery;
import me.prettyprint.cassandra.model.SubSliceQuery;
import me.prettyprint.cassandra.model.SuperColumnQuery;
import me.prettyprint.cassandra.model.SuperCountQuery;
import me.prettyprint.cassandra.model.SuperSliceQuery;
import me.prettyprint.cassandra.model.ThriftColumnQuery;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.Cluster;
import me.prettyprint.hector.api.query.ColumnQuery;

import org.apache.cassandra.thrift.Clock;
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
    return new CountQuery<K, N>(ko, keySerializer, nameSerializer);
  }

  public static <K,SN> SuperCountQuery<K,SN> createSuperCountQuery(KeyspaceOperator ko,
      Serializer<K> keySerializer, Serializer<SN> superNameSerializer) {
    return new SuperCountQuery<K,SN>(ko, keySerializer, superNameSerializer);
  }

  public static <K,SN,N> SubCountQuery<K,SN,N> createSubCountQuery(KeyspaceOperator ko,
      Serializer<K> keySerializer, Serializer<SN> superNameSerializer, Serializer<N> nameSerializer) {
    return new SubCountQuery<K,SN,N>(ko, keySerializer, superNameSerializer, nameSerializer);
  }

  public static ColumnQuery<String, String, String> createStringColumnQuery(KeyspaceOperator ko) {
    StringSerializer se = StringSerializer.get();
    return createColumnQuery(ko, se, se, se);
  }

  public static <K,SN,N,V> SuperColumnQuery<K,SN,N,V> createSuperColumnQuery(KeyspaceOperator ko,
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new SuperColumnQuery<K,SN, N, V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K,N,V> MultigetSliceQuery<K,N,V> createMultigetSliceQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new MultigetSliceQuery<K,N,V>(ko, keySerializer, nameSerializer, valueSerializer);
  }

  public static <K, SN, N, V> SubColumnQuery<K, SN, N, V> createSubColumnQuery(KeyspaceOperator ko,
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new SubColumnQuery<K, SN, N, V>(ko, keySerializer, sNameSerializer, nameSerializer,
        valueSerializer);
  }

  public static <K,SN,N,V> MultigetSuperSliceQuery<K,SN,N,V> createMultigetSuperSliceQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new MultigetSuperSliceQuery<K,SN,N,V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K,SN,N,V> MultigetSubSliceQuery<K,SN,N,V> createMultigetSubSliceQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new MultigetSubSliceQuery<K,SN,N,V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K,N,V> RangeSlicesQuery<K,N,V> createRangeSlicesQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new RangeSlicesQuery<K,N,V>(ko, keySerializer, nameSerializer, valueSerializer);
  }

  public static <K,SN,N,V> RangeSuperSlicesQuery<K,SN,N,V> createRangeSuperSlicesQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new RangeSuperSlicesQuery<K,SN,N,V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }
  
  public static <K,N,V> IndexedSlicesQuery<K, N, V> createIndexedSlicesQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer, 
      Serializer<V> valueSerializer) {
    return new IndexedSlicesQuery<K, N, V>(ko, keySerializer, nameSerializer, valueSerializer);
  }

  public static <K,SN,N,V> RangeSubSlicesQuery<K,SN,N,V> createRangeSubSlicesQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new RangeSubSlicesQuery<K,SN,N,V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K,N,V> SliceQuery<K,N,V> createSliceQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new SliceQuery<K,N,V>(ko, keySerializer, nameSerializer, valueSerializer);
  }

  public static <K,SN,N,V> SubSliceQuery<K,SN,N,V> createSubSliceQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new SubSliceQuery<K,SN,N,V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K,SN,N,V> SuperSliceQuery<K,SN,N,V> createSuperSliceQuery(
      KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new SuperSliceQuery<K,SN,N,V>(ko, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
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
      Clock clock, Serializer<SN> superNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new HSuperColumn<SN, N, V>(name, columns, clock, superNameSerializer, nameSerializer,
        valueSerializer);
  }

  public static <N,V> HColumn<N,V> createColumn(N name, V value, Clock clock,
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
  public static Clock createClock() {
    return CassandraHost.DEFAULT_TIMESTAMP_RESOLUTION.createClock();
  }

}
