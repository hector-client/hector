package me.prettyprint.hector.api.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.ExecutingKeyspace;
import me.prettyprint.cassandra.model.ExecutingVirtualKeyspace;
import me.prettyprint.cassandra.model.HColumnImpl;
import me.prettyprint.cassandra.model.HSuperColumnImpl;
import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.cassandra.model.MutatorImpl;
import me.prettyprint.cassandra.model.QuorumAllConsistencyLevelPolicy;
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
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.cassandra.service.clock.MicrosecondsClockResolution;
import me.prettyprint.cassandra.service.clock.MicrosecondsSyncClockResolution;
import me.prettyprint.cassandra.service.clock.MillisecondsClockResolution;
import me.prettyprint.cassandra.service.clock.SecondsClockResolution;
import me.prettyprint.hector.api.ClockResolution;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.ddl.ColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.CountQuery;
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
import me.prettyprint.hector.api.query.SuperCountQuery;
import me.prettyprint.hector.api.query.SuperSliceQuery;

/**
 * A convenience class with bunch of factory static methods to help create a
 * mutator, queries etc.
 * 
 * @author Ran
 * @author zznate
 */
public final class HFactory {

  private static final Map<String, Cluster> clusters = new HashMap<String, Cluster>();

  private static final ConsistencyLevelPolicy DEFAULT_CONSISTENCY_LEVEL_POLICY = new QuorumAllConsistencyLevelPolicy();

  public static Cluster getCluster(String clusterName) {
    return clusters.get(clusterName);
  }

  /**
   * Method tries to create a Cluster instance for an existing Cassandra
   * cluster. If another class already called getOrCreateCluster, the factory
   * returns the cached instance. If the instance doesn't exist in memory, a new
   * ThriftCluster is created and cached.
   * 
   * Example usage for a default installation of Cassandra.
   * 
   * String clusterName = "Test Cluster"; String host = "localhost:9160";
   * Cluster cluster = HFactory.getOrCreateCluster(clusterName, host);
   * 
   * Note the host should be the hostname and port number. It is preferable to
   * use the hostname instead of the IP address.
   * 
   * @param clusterName
   *          The cluster name. This is an identifying string for the cluster,
   *          e.g. "production" or "test" etc. Clusters will be created on
   *          demand per each unique clusterName key.
   * @param hostIp
   *          host:ip format string
   * @return
   */
  public static Cluster getOrCreateCluster(String clusterName, String hostIp) {
    return getOrCreateCluster(clusterName,
        new CassandraHostConfigurator(hostIp));
  }

  /**
   * Method tries to create a Cluster instance for an existing Cassandra
   * cluster. If another class already called getOrCreateCluster, the factory
   * returns the cached instance. If the instance doesn't exist in memory, a new
   * ThriftCluster is created and cached.
   * 
   * Example usage for a default installation of Cassandra.
   * 
   * String clusterName = "Test Cluster"; String host = "localhost:9160";
   * Cluster cluster = HFactory.getOrCreateCluster(clusterName, new
   * CassandraHostConfigurator(host));
   * 
   * @param clusterName
   *          The cluster name. This is an identifying string for the cluster,
   *          e.g. "production" or "test" etc. Clusters will be created on
   *          demand per each unique clusterName key.
   * @param cassandraHostConfigurator
   */
  public static Cluster getOrCreateCluster(String clusterName,
      CassandraHostConfigurator cassandraHostConfigurator) {
    synchronized (clusters) {
      Cluster c = clusters.get(clusterName);
      if (c == null) {
        c = createCluster(clusterName, cassandraHostConfigurator);
        clusters.put(clusterName, c);
      }
      return c;
    }
  }

  /**
   * Method looks in the cache for the cluster by name. If none exists, a new
   * ThriftCluster instance is created.
   * 
   * @param clusterName
   *          The cluster name. This is an identifying string for the cluster,
   *          e.g. "production" or "test" etc. Clusters will be created on
   *          demand per each unique clusterName key.
   * @param cassandraHostConfigurator
   * 
   */
  public static Cluster createCluster(String clusterName,
      CassandraHostConfigurator cassandraHostConfigurator) {
    return clusters.get(clusterName) == null ? new ThriftCluster(clusterName,
        cassandraHostConfigurator) : clusters.get(clusterName);
  }

  /**
   * Method looks in the cache for the cluster by name. If none exists, a new
   * ThriftCluster instance is created.
   * 
   * @param clusterName
   *          The cluster name. This is an identifying string for the cluster,
   *          e.g. "production" or "test" etc. Clusters will be created on
   *          demand per each unique clusterName key.
   * @param cassandraHostConfigurator
   * @param credentials
   */
  public static Cluster createCluster(String clusterName,
      CassandraHostConfigurator cassandraHostConfigurator,
      Map<String, String> credentials) {
    return clusters.get(clusterName) == null ? new ThriftCluster(clusterName,
        cassandraHostConfigurator, credentials) : clusters.get(clusterName);
  }

  /**
   * Creates a Keyspace with the default consistency level policy.
   * 
   * @param keyspace
   * @param cluster
   * @return
   */
  public static Keyspace createKeyspace(String keyspace, Cluster cluster) {
    return createKeyspace(keyspace, cluster,
        createDefaultConsistencyLevelPolicy(),
        FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE);
  }

  public static Keyspace createKeyspace(String keyspace, Cluster cluster,
      ConsistencyLevelPolicy consistencyLevelPolicy) {
    return createKeyspace(keyspace, cluster, consistencyLevelPolicy,
        FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE);
  }

  public static Keyspace createKeyspace(String keyspace, Cluster cluster,
      ConsistencyLevelPolicy consistencyLevelPolicy,
      FailoverPolicy failoverPolicy) {
    return new ExecutingKeyspace(keyspace, cluster.getConnectionManager(),
        consistencyLevelPolicy, failoverPolicy, cluster.getCredentials());
  }

  public static Keyspace createKeyspace(String keyspace, Cluster cluster,
      ConsistencyLevelPolicy consistencyLevelPolicy,
      FailoverPolicy failoverPolicy, Map<String, String> credentials) {
    return new ExecutingKeyspace(keyspace, cluster.getConnectionManager(),
        consistencyLevelPolicy, failoverPolicy, credentials);
  }

  public static <E> Keyspace createVirtualKeyspace(String keyspace,
      E keyPrefix, Serializer<E> keyPrefixSerializer, Cluster cluster) {
    return createVirtualKeyspace(keyspace, keyPrefix, keyPrefixSerializer,
        cluster, createDefaultConsistencyLevelPolicy(),
        FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE);
  }

  public static <E> Keyspace createVirtualKeyspace(String keyspace,
      E keyPrefix, Serializer<E> keyPrefixSerializer, Cluster cluster,
      ConsistencyLevelPolicy consistencyLevelPolicy) {
    return createVirtualKeyspace(keyspace, keyPrefix, keyPrefixSerializer,
        cluster, consistencyLevelPolicy,
        FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE);
  }

  public static <E> Keyspace createVirtualKeyspace(String keyspace,
      E keyPrefix, Serializer<E> keyPrefixSerializer, Cluster cluster,
      ConsistencyLevelPolicy consistencyLevelPolicy,
      FailoverPolicy failoverPolicy) {
    return new ExecutingVirtualKeyspace<E>(keyspace, keyPrefix,
        keyPrefixSerializer, cluster.getConnectionManager(),
        consistencyLevelPolicy, failoverPolicy, cluster.getCredentials());
  }

  public static <E> Keyspace createVirtualKeyspace(String keyspace,
      E keyPrefix, Serializer<E> keyPrefixSerializer, Cluster cluster,
      ConsistencyLevelPolicy consistencyLevelPolicy,
      FailoverPolicy failoverPolicy, Map<String, String> credentials) {
    return new ExecutingVirtualKeyspace<E>(keyspace, keyPrefix,
        keyPrefixSerializer, cluster.getConnectionManager(),
        consistencyLevelPolicy, failoverPolicy, credentials);
  }

  public static ConsistencyLevelPolicy createDefaultConsistencyLevelPolicy() {
    return DEFAULT_CONSISTENCY_LEVEL_POLICY;
  }

  public static <K, N, V> Mutator<K> createMutator(Keyspace keyspace,
      Serializer<K> keySerializer) {
    return new MutatorImpl<K>(keyspace, keySerializer);
  }

  public static <K, N, V> ColumnQuery<K, N, V> createColumnQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftColumnQuery<K, N, V>(keyspace, keySerializer,
        nameSerializer, valueSerializer);
  }

  public static <K, N> CountQuery<K, N> createCountQuery(Keyspace keyspace,
      Serializer<K> keySerializer, Serializer<N> nameSerializer) {
    return new ThriftCountQuery<K, N>(keyspace, keySerializer, nameSerializer);
  }

  public static <K, SN> SuperCountQuery<K, SN> createSuperCountQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<SN> superNameSerializer) {
    return new ThriftSuperCountQuery<K, SN>(keyspace, keySerializer,
        superNameSerializer);
  }

  public static <K, SN, N> SubCountQuery<K, SN, N> createSubCountQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<SN> superNameSerializer, Serializer<N> nameSerializer) {
    return new ThriftSubCountQuery<K, SN, N>(keyspace, keySerializer,
        superNameSerializer, nameSerializer);
  }

  public static ColumnQuery<String, String, String> createStringColumnQuery(
      Keyspace keyspace) {
    StringSerializer se = StringSerializer.get();
    return createColumnQuery(keyspace, se, se, se);
  }

  public static <K, SN, N, V> SuperColumnQuery<K, SN, N, V> createSuperColumnQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftSuperColumnQuery<K, SN, N, V>(keyspace, keySerializer,
        sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K, N, V> MultigetSliceQuery<K, N, V> createMultigetSliceQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftMultigetSliceQuery<K, N, V>(keyspace, keySerializer,
        nameSerializer, valueSerializer);
  }

  public static <K, SN, N, V> SubColumnQuery<K, SN, N, V> createSubColumnQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftSubColumnQuery<K, SN, N, V>(keyspace, keySerializer,
        sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K, SN, N, V> MultigetSuperSliceQuery<K, SN, N, V> createMultigetSuperSliceQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftMultigetSuperSliceQuery<K, SN, N, V>(keyspace,
        keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K, SN, N, V> MultigetSubSliceQuery<K, SN, N, V> createMultigetSubSliceQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftMultigetSubSliceQuery<K, SN, N, V>(keyspace,
        keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K, N, V> RangeSlicesQuery<K, N, V> createRangeSlicesQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftRangeSlicesQuery<K, N, V>(keyspace, keySerializer,
        nameSerializer, valueSerializer);
  }

  public static <K, SN, N, V> RangeSuperSlicesQuery<K, SN, N, V> createRangeSuperSlicesQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftRangeSuperSlicesQuery<K, SN, N, V>(keyspace,
        keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K, N, V> IndexedSlicesQuery<K, N, V> createIndexedSlicesQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new IndexedSlicesQuery<K, N, V>(keyspace, keySerializer,
        nameSerializer, valueSerializer);
  }

  public static <K, SN, N, V> RangeSubSlicesQuery<K, SN, N, V> createRangeSubSlicesQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftRangeSubSlicesQuery<K, SN, N, V>(keyspace, keySerializer,
        sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K, N, V> SliceQuery<K, N, V> createSliceQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new ThriftSliceQuery<K, N, V>(keyspace, keySerializer,
        nameSerializer, valueSerializer);
  }

  public static <K, SN, N, V> SubSliceQuery<K, SN, N, V> createSubSliceQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftSubSliceQuery<K, SN, N, V>(keyspace, keySerializer,
        sNameSerializer, nameSerializer, valueSerializer);
  }

  public static <K, SN, N, V> SuperSliceQuery<K, SN, N, V> createSuperSliceQuery(
      Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new ThriftSuperSliceQuery<K, SN, N, V>(keyspace, keySerializer,
        sNameSerializer, nameSerializer, valueSerializer);
  }

  /**
   * createSuperColumn accepts a variable number of column arguments
   * 
   * @param name
   *          supercolumn name
   * @param columns
   * @param superNameSerializer
   * @param nameSerializer
   * @param valueSerializer
   * @return
   */
  public static <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name,
      List<HColumn<N, V>> columns, Serializer<SN> superNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new HSuperColumnImpl<SN, N, V>(name, columns, createClock(),
        superNameSerializer, nameSerializer, valueSerializer);
  }

  public static <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name,
      List<HColumn<N, V>> columns, long clock,
      Serializer<SN> superNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return new HSuperColumnImpl<SN, N, V>(name, columns, clock,
        superNameSerializer, nameSerializer, valueSerializer);
  }

  public static <N, V> HColumn<N, V> createColumn(N name, V value, long clock,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new HColumnImpl<N, V>(name, value, clock, nameSerializer,
        valueSerializer);
  }

  /**
   * Creates a column with the clock of now.
   */
  public static <N, V> HColumn<N, V> createColumn(N name, V value,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new HColumnImpl<N, V>(name, value, createClock(), nameSerializer,
        valueSerializer);
  }

  /**
   * Convienience method for creating a column with a String name and String
   * value
   */
  public static HColumn<String, String> createStringColumn(String name,
      String value) {
    StringSerializer se = StringSerializer.get();
    return createColumn(name, value, se, se);
  }

  /**
   * Creates a clock of now with the default clock resolution (micorosec) as
   * defined in {@link CassandraHostConfigurator}. Notice that this is a
   * convenient method. Be aware that there might be multiple
   * {@link CassandraHostConfigurator} each of them with different clock
   * resolutions, in which case the result of {@link HFactory.createClock} will
   * not be consistent. {@link Keyspace.createClock()} should be used instead.
   */
  public static long createClock() {
    return CassandraHostConfigurator.DEF_CLOCK_RESOLUTION.createClock();
  }

  /**
   * Use createKeyspaceDefinition to add a new Keyspace to cluster. Example:
   * 
   * String testKeyspace = "testKeyspace"; KeyspaceDefinition newKeyspace =
   * HFactory.createKeyspaceDefinition(testKeyspace);
   * cluster.addKeyspace(newKeyspace);
   * 
   * @param keyspace
   */
  public static KeyspaceDefinition createKeyspaceDefinition(String keyspace) {
    return new ThriftKsDef(keyspace);
  }

  /**
   * Use createKeyspaceDefinition to add a new Keyspace to cluster. Example:
   * 
   * String testKeyspace = "testKeyspace"; KeyspaceDefinition newKeyspace =
   * HFactory.createKeyspaceDefinition(testKeyspace);
   * cluster.addKeyspace(newKeyspace);
   * 
   * @param keyspace
   * @param strategyClass
   *          - example:
   *          org.apache.cassandra.locator.SimpleStrategy.class.getName()
   * @param replicationFactor
   *          - http://wiki.apache.org/cassandra/Operations
   */
  public static KeyspaceDefinition createKeyspaceDefinition(
      String keyspaceName, String strategyClass, int replicationFactor,
      List<ColumnFamilyDefinition> cfDefs) {
    return new ThriftKsDef(keyspaceName, strategyClass, replicationFactor,
        cfDefs);
  }

  /**
   * Create a column family for a given keyspace without comparator type.
   * Example: String keyspace = "testKeyspace"; String column1 = "testcolumn";
   * ColumnFamilyDefinition columnFamily1 =
   * HFactory.createColumnFamilyDefinition(keyspace, column1);
   * List<ColumnFamilyDefinition> columns = new
   * ArrayList<ColumnFamilyDefinition>(); columns.add(columnFamily1);
   * KeyspaceDefinition testKeyspace =
   * HFactory.createKeyspaceDefinition(keyspace,
   * org.apache.cassandra.locator.SimpleStrategy.class.getName(), 1, columns);
   * cluster.addKeyspace(testKeyspace);
   * 
   * @param keyspace
   * @param columnFamilyName
   */
  public static ColumnFamilyDefinition createColumnFamilyDefinition(
      String keyspace, String cfName) {
    return new ThriftCfDef(keyspace, cfName);
  }

  /**
   * Create a column family for a given keyspace without comparator type.
   * Example: String keyspace = "testKeyspace"; String column1 = "testcolumn";
   * ColumnFamilyDefinition columnFamily1 =
   * HFactory.createColumnFamilyDefinition(keyspace, column1,
   * ComparatorType.UTF8TYPE); List<ColumnFamilyDefinition> columns = new
   * ArrayList<ColumnFamilyDefinition>(); columns.add(columnFamily1);
   * KeyspaceDefinition testKeyspace =
   * HFactory.createKeyspaceDefinition(keyspace,
   * org.apache.cassandra.locator.SimpleStrategy.class.getName(), 1, columns);
   * cluster.addKeyspace(testKeyspace);
   * 
   * @param keyspace
   * @param columnFamilyName
   * @param comparatorType
   */
  public static ColumnFamilyDefinition createColumnFamilyDefinition(
      String keyspace, String cfName, ComparatorType comparatorType) {
    return new ThriftCfDef(keyspace, cfName, comparatorType);
  }

  public static ColumnFamilyDefinition createColumnFamilyDefinition(
      String keyspace, String cfName, ComparatorType comparatorType,
      List<ColumnDefinition> columnMetadata) {
    return new ThriftCfDef(keyspace, cfName, comparatorType, columnMetadata);
  }

  /**
   * Create a clock resolution based on <code>clockResolutionName</code> which
   * has to match any of the constants defined at {@link ClockResolution}
   * 
   * @param clockResolutionName
   *          type of clock resolution to create
   * @return a ClockResolution
   */
  public static ClockResolution createClockResolution(String clockResolutionName) {
    if (clockResolutionName.equals(ClockResolution.SECONDS)) {
      return new SecondsClockResolution();
    } else if (clockResolutionName.equals(ClockResolution.MILLISECONDS)) {
      return new MillisecondsClockResolution();
    } else if (clockResolutionName.equals(ClockResolution.MICROSECONDS)) {
      return new MicrosecondsClockResolution();
    } else if (clockResolutionName.equals(ClockResolution.MICROSECONDS_SYNC)) {
      return new MicrosecondsSyncClockResolution();
    }
    throw new RuntimeException(String.format(
        "Unsupported clock resolution: %s", clockResolutionName));
  }

}
