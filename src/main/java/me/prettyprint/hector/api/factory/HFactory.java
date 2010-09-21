package me.prettyprint.hector.api.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.ExecutingKeyspace;
import me.prettyprint.cassandra.model.HColumnImpl;
import me.prettyprint.cassandra.model.HSuperColumnImpl;
import me.prettyprint.cassandra.model.MutatorImpl;
import me.prettyprint.cassandra.model.QuorumAllConsistencyLevelPolicy;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.model.thrift.ThriftColumnQuery;
import me.prettyprint.cassandra.model.thrift.ThriftCountQuery;
import me.prettyprint.cassandra.model.thrift.ThriftMultigetSliceQuery;
import me.prettyprint.cassandra.model.thrift.ThriftMultigetSubSliceQuery;
import me.prettyprint.cassandra.model.thrift.ThriftMultigetSuperSliceQuery;
import me.prettyprint.cassandra.model.thrift.ThriftRangeSlicesQuery;
import me.prettyprint.cassandra.model.thrift.ThriftRangeSubSlicesQuery;
import me.prettyprint.cassandra.model.thrift.ThriftRangeSuperSlicesQuery;
import me.prettyprint.cassandra.model.thrift.ThriftSliceQuery;
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
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.MultigetSubSliceQuery;
import me.prettyprint.hector.api.query.MultigetSuperSliceQuery;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.RangeSubSlicesQuery;
import me.prettyprint.hector.api.query.RangeSuperSlicesQuery;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.hector.api.query.SubColumnQuery;
import me.prettyprint.hector.api.query.SubCountQuery;
import me.prettyprint.hector.api.query.SubSliceQuery;
import me.prettyprint.hector.api.query.SuperColumnQuery;
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
   * Creates a Keyspace with the default consistency level policy.
   * @param keyspace
   * @param cluster
   * @return
   */
  public static Keyspace createKeyspace(String keyspace, Cluster cluster) {
    return createKeyspace(keyspace, cluster, createDefaultConsistencyLevelPolicy());
  }

  public static Keyspace createKeyspace(String keyspace, Cluster cluster,
      ConsistencyLevelPolicy consistencyLevelPolicy) {
    return new ExecutingKeyspace(keyspace, cluster, consistencyLevelPolicy);
  }

  public static ConsistencyLevelPolicy createDefaultConsistencyLevelPolicy() {
    return DEFAULT_CONSISTENCY_LEVEL_POLICY;
  }

  public static <N,V> Mutator createMutator(Keyspace ko) {
    return new MutatorImpl(ko);
  }

  public static ThriftCountQuery createCountQuery(Keyspace ko) {
    return new ThriftCountQuery(ko);
  }

  public static ThriftSuperCountQuery createSuperCountQuery(Keyspace ko) {
    return new ThriftSuperCountQuery(ko);
  }

  public static <SN> SubCountQuery<SN> createSubCountQuery(Keyspace ko,
      Serializer<SN> superNameSerializer) {
    return new ThriftSubCountQuery<SN>(ko, superNameSerializer);
  }

  public static <N,V> ColumnQuery<N, V> createColumnQuery(Keyspace ko,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftColumnQuery<N,V>(ko, nameSerializer, valueSerializer);
  }

  public static ColumnQuery<String, String> createStringColumnQuery(Keyspace ko) {
    StringSerializer se = StringSerializer.get();
    return createColumnQuery(ko, se, se);
  }

  public static <SN,N,V> SuperColumnQuery<SN, N, V> createSuperColumnQuery(Keyspace ko,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftSuperColumnQuery<SN, N, V>(ko, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <SN,N,V> SubColumnQuery<SN, N, V> createSubColumnQuery(Keyspace ko,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftSubColumnQuery<SN, N, V>(ko, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <N,V> MultigetSliceQuery<N, V> createMultigetSliceQuery(
      Keyspace ko, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftMultigetSliceQuery<N,V>(ko, nameSerializer, valueSerializer);
  }

  public static <SN,N,V> MultigetSuperSliceQuery<SN, N, V> createMultigetSuperSliceQuery(
      Keyspace ko, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftMultigetSuperSliceQuery<SN,N,V>(ko, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <SN,N,V> MultigetSubSliceQuery<SN, N, V> createMultigetSubSliceQuery(
      Keyspace ko, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftMultigetSubSliceQuery<SN,N,V>(ko, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <N,V> RangeSlicesQuery<N, V> createRangeSlicesQuery(
      Keyspace ko, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftRangeSlicesQuery<N,V>(ko, nameSerializer, valueSerializer);
  }

  public static <SN,N,V> RangeSuperSlicesQuery<SN, N, V> createRangeSuperSlicesQuery(
      Keyspace ko, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftRangeSuperSlicesQuery<SN,N,V>(ko, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <SN,N,V> RangeSubSlicesQuery<SN, N, V> createRangeSubSlicesQuery(
      Keyspace ko, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftRangeSubSlicesQuery<SN,N,V>(ko, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <N,V> SliceQuery<N, V> createSliceQuery(
      Keyspace ko, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftSliceQuery<N,V>(ko, nameSerializer, valueSerializer);
  }

  public static <SN,N,V> SubSliceQuery<SN, N, V> createSubSliceQuery(
      Keyspace ko, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftSubSliceQuery<SN,N,V>(ko, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <SN,N,V> SuperSliceQuery<SN, N, V> createSuperSliceQuery(
      Keyspace ko, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftSuperSliceQuery<SN,N,V>(ko, sNameSerializer, nameSerializer, valueSerializer);
  }

  /**
   * createSuperColumn accepts a variable number of column arguments
   * @param name supercolumn name
   * @param createColumn a variable number of column arguments
   */
  public static <SN,N,V> HSuperColumn<SN, N, V> createSuperColumn(SN name, List<HColumn<N,V>> columns,
      Serializer<SN> superNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new HSuperColumnImpl<SN, N, V>(name, columns, createTimestamp(), superNameSerializer,
        nameSerializer, valueSerializer);
  }

  public static <SN,N,V> HSuperColumn<SN, N, V> createSuperColumn(SN name, List<HColumn<N,V>> columns,
      long timestamp, Serializer<SN> superNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new HSuperColumnImpl<SN, N, V>(name, columns, timestamp, superNameSerializer, nameSerializer,
        valueSerializer);
  }

  public static <N,V> HColumn<N, V> createColumn(N name, V value, long timestamp,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new HColumnImpl<N, V>(name, value, timestamp, nameSerializer, valueSerializer);
  }

  /**
   * Creates a column with the timestamp of now.
   */
  public static <N,V> HColumn<N, V> createColumn(N name, V value,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new HColumnImpl<N, V>(name, value, createTimestamp(), nameSerializer, valueSerializer);
  }

  /**
   * Convienience method for creating a column with a String name and String value
   */
  public static HColumn<String, String> createStringColumn(String name, String value) {
    StringSerializer se = StringSerializer.get();
    return createColumn(name, value, se, se);
  }

  /**
   * Creates a timestamp of now with the default timestamp resolution (micorosec) as defined in
   * {@link CassandraHost}
   */
  public static long createTimestamp() {
    return CassandraHost.DEFAULT_TIMESTAMP_RESOLUTION.createTimestamp();
  }

}
