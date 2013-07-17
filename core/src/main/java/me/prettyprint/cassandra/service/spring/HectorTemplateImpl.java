package me.prettyprint.cassandra.service.spring;

import java.nio.ByteBuffer;
import java.util.List;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.model.HColumnImpl;
import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.cassandra.model.MutatorImpl;
import me.prettyprint.cassandra.model.thrift.ThriftColumnQuery;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.TypeInferringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.beans.HCounterSuperColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.CountQuery;
import me.prettyprint.hector.api.query.CounterQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.MultigetSubSliceQuery;
import me.prettyprint.hector.api.query.MultigetSuperSliceQuery;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.RangeSubSlicesQuery;
import me.prettyprint.hector.api.query.RangeSuperSlicesQuery;
import me.prettyprint.hector.api.query.SliceCounterQuery;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.hector.api.query.SubCountQuery;
import me.prettyprint.hector.api.query.SubSliceQuery;
import me.prettyprint.hector.api.query.SuperColumnQuery;
import me.prettyprint.hector.api.query.SuperCountQuery;
import me.prettyprint.hector.api.query.SuperSliceQuery;

import org.apache.cassandra.thrift.ColumnPath;
import org.apache.commons.lang.Validate;

/**
 * Implementation of the HectorTemplate
 *
 * @author Bozhidar Bozhanov
 *
 */
public class HectorTemplateImpl implements HectorTemplate {

  private String keyspaceName;
  private Cluster cluster;
  private Keyspace keyspace;

  private ConfigurableConsistencyLevel configurableConsistencyLevelPolicy;
  private String replicationStrategyClass;
  private int replicationFactor;


  public HectorTemplateImpl() {
  }

  public HectorTemplateImpl(Cluster cluster, String keyspace, int replicationFactor, String replicationStrategyClass, ConfigurableConsistencyLevel configurableConsistencyLevelPolicy) {
    this.cluster = cluster;
    this.keyspaceName = keyspace;
    this.replicationFactor = replicationFactor;
    this.replicationStrategyClass = replicationStrategyClass;
    this.configurableConsistencyLevelPolicy = configurableConsistencyLevelPolicy;
    initKeyspaceOperator();
  }

  public void init() {
    initKeyspaceOperator();
  }

  private void initKeyspaceOperator() {
    ConsistencyLevelPolicy clPolicy;
    if (configurableConsistencyLevelPolicy == null) {
      clPolicy = HFactory.createDefaultConsistencyLevelPolicy();
    } else {
      clPolicy = configurableConsistencyLevelPolicy;
    }
    keyspace = HFactory.createKeyspace(keyspaceName, cluster, clPolicy);
  }

  @Override
  public <K, N, V> Mutator<K> createMutator(Serializer<K> keySerializer) {
    return HFactory.createMutator(keyspace, keySerializer);
  }

  @Override
  public <K, N, V> ColumnQuery<K, N, V> createColumnQuery() {
    return new ThriftColumnQuery<K, N, V>(keyspace);
  }

  @Override
  public <K, N, V> ColumnQuery<K, N, V> createColumnQuery(Serializer<V> valueSerializer) {
    return new ThriftColumnQuery<K, N, V>(keyspace, valueSerializer);
  }

  @Override
  public <K, N> CountQuery<K, N> createCountQuery(Serializer<K> keySerializer,
      Serializer<N> nameSerializer) {
    return HFactory.createCountQuery(keyspace, keySerializer, nameSerializer);
  }

  @Override
  public <K, SN> SuperCountQuery<K, SN> createSuperCountQuery(Serializer<K> keySerializer,
      Serializer<SN> superNameSerializer) {
    return HFactory.createSuperCountQuery(keyspace, keySerializer, superNameSerializer);
  }

  @Override
  public <K, SN, N> SubCountQuery<K, SN, N> createSubCountQuery(Serializer<K> keySerializer,
      Serializer<SN> superNameSerializer, Serializer<N> nameSerializer) {
    return HFactory.createSubCountQuery(keyspace, keySerializer, superNameSerializer,
        nameSerializer);
  }

  @Override
  public <K, SN, N, V> SuperColumnQuery<K, SN, N, V> createSuperColumnQuery(
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return HFactory.createSuperColumnQuery(keyspace, keySerializer, sNameSerializer,
        nameSerializer, valueSerializer);
  }

  @Override
  public <K, N, V> MultigetSliceQuery<K, N, V> createMultigetSliceQuery(
      Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return HFactory.createMultigetSliceQuery(keyspace, keySerializer, nameSerializer,
        valueSerializer);
  }

  @Override
  public <K, SN, N, V> MultigetSuperSliceQuery<K, SN, N, V> createMultigetSuperSliceQuery(
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return HFactory.createMultigetSuperSliceQuery(keyspace, keySerializer, sNameSerializer,
        nameSerializer, valueSerializer);
  }

  @Override
  public <K, SN, N, V> MultigetSubSliceQuery<K, SN, N, V> createMultigetSubSliceQuery(
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return HFactory.createMultigetSubSliceQuery(keyspace, keySerializer, sNameSerializer,
        nameSerializer, valueSerializer);
  }

  @Override
  public <K, N, V> RangeSlicesQuery<K, N, V> createRangeSlicesQuery(Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return HFactory.createRangeSlicesQuery(keyspace, keySerializer, nameSerializer,
        valueSerializer);
  }

  @Override
  public <K, SN, N, V> RangeSuperSlicesQuery<K, SN, N, V> createRangeSuperSlicesQuery(
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return HFactory.createRangeSuperSlicesQuery(keyspace, keySerializer, sNameSerializer,
        nameSerializer, valueSerializer);
  }

  @Override
  public <K, SN, N, V> RangeSubSlicesQuery<K, SN, N, V> createRangeSubSlicesQuery(
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return HFactory.createRangeSubSlicesQuery(keyspace, keySerializer, sNameSerializer,
        nameSerializer, valueSerializer);
  }

  @Override
  public <K, N, V> SliceQuery<K, N, V> createSliceQuery(Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return HFactory.createSliceQuery(keyspace, keySerializer, nameSerializer,
        valueSerializer);
  }

  @Override
  public <K, SN, N, V> SubSliceQuery<K, SN, N, V> createSubSliceQuery(Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return HFactory.createSubSliceQuery(keyspace, keySerializer, sNameSerializer,
        nameSerializer, valueSerializer);
  }

  @Override
  public <K, SN, N, V> SuperSliceQuery<K, SN, N, V> createSuperSliceQuery(
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return HFactory.createSuperSliceQuery(keyspace, keySerializer, sNameSerializer,
        nameSerializer, valueSerializer);
  }

  @Override
  public <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name, List<HColumn<N, V>> columns,
      Serializer<SN> superNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return HFactory.createSuperColumn(name, columns, createClock(), superNameSerializer,
        nameSerializer, valueSerializer);
  }

  @Override
  public <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name, List<HColumn<N, V>> columns,
      long clock, Serializer<SN> superNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    return HFactory.createSuperColumn(name, columns, clock, superNameSerializer, nameSerializer,
        valueSerializer);
  }

  @Override
  public <N, V> HColumn<N, V> createColumn(N name, V value, long clock,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return HFactory.createColumn(name, value, clock, nameSerializer, valueSerializer);
  }

  @Override
  public <N, V> HColumn<N, V> createColumn(N name, V value) {
    return new HColumnImpl<N, V>(name, value, createClock());
  }
  
  @Override
  public <N, V> HColumn<N, V> createColumn(N name, V value, long clock) {
    return new HColumnImpl<N, V>(name, value, clock);
  }

  @Override
  public long createClock() {
    return HFactory.createClock();
  }

  // probably should be typed for thrift vs. avro
  <N> ColumnPath createColumnPath(String columnFamilyName, N columnName,
      Serializer<N> nameSerializer) {
    return createColumnPath(columnFamilyName, nameSerializer.toByteBuffer(columnName));
  }

  private <N> ColumnPath createColumnPath(String columnFamilyName, ByteBuffer columnName) {
    Validate.notNull(columnFamilyName, "columnFamilyName cannot be null");
    ColumnPath columnPath = new ColumnPath(columnFamilyName);
    if (columnName != null) {
      columnPath.setColumn(columnName);
    }
    return columnPath;
  }

  <N> ColumnPath createColumnPath(String columnFamilyName) {
    return createColumnPath(columnFamilyName, null);
  }

  <SN, N> ColumnPath createSuperColumnPath(String columnFamilyName, SN superColumnName,
      N columnName, Serializer<SN> superNameSerializer, Serializer<N> nameSerializer) {
    noNullElements(columnFamilyName, superColumnName, superNameSerializer, nameSerializer);
    ColumnPath columnPath = createColumnPath(columnFamilyName, nameSerializer.toByteBuffer(columnName));
    columnPath.setSuper_column(superNameSerializer.toByteBuffer(superColumnName));
    return columnPath;
  }

  <SN> ColumnPath createSuperColumnPath(String columnFamilyName, SN superColumnName,
      Serializer<SN> superNameSerializer) {
    noNullElements(columnFamilyName, superNameSerializer);
    ColumnPath columnPath = createColumnPath(columnFamilyName, null);
    if (superColumnName != null) {
      columnPath.setSuper_column(superNameSerializer.toByteBuffer(superColumnName));
    }
    return columnPath;
  }

  private void noNullElements(Object... elements) {
    Validate.noNullElements(elements);
  }

  @Override
  public <K, N, V> Mutator<K> createMutator() {
    return new MutatorImpl<K>(keyspace);
  }

  @Override
  public <K, N, V> IndexedSlicesQuery<K, N, V> createIndexSlicesQuery(Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    return new IndexedSlicesQuery<K, N, V>(keyspace, keySerializer, nameSerializer,
        valueSerializer);
  }

  @Override
  public <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name,
      List<HColumn<N, V>> columns) {
    return createSuperColumn(name, columns, TypeInferringSerializer.<SN>get(), TypeInferringSerializer.<N>get(), TypeInferringSerializer.<V>get());
  }

  @Override
  public <K> SliceQuery<K, ByteBuffer, ByteBuffer> createSliceQuery() {
    return createSliceQuery(TypeInferringSerializer.<K>get(), ByteBufferSerializer.get(), ByteBufferSerializer.get());
  }


  @Override
  public <K> SuperSliceQuery<K, ByteBuffer, ByteBuffer, ByteBuffer> createSuperSliceQuery() {
    return createSuperSliceQuery(TypeInferringSerializer.<K>get(), ByteBufferSerializer.get(), ByteBufferSerializer.get(), ByteBufferSerializer.get());
  }

  @Override
  public <N> HCounterColumn<N> createCounterColumn(N name, long value, Serializer<N> nameSerializer) {
    return HFactory.createCounterColumn(name, value, nameSerializer);
  }

  @Override
  public HCounterColumn<String> createCounterColumn(String name, long value) {
    return HFactory.createCounterColumn(name, value);
  }

  @Override
  public <K, N> CounterQuery<K, N> createCounterColumnQuery(Serializer<K> keySerializer,
        Serializer<N> nameSerializer) {
    return HFactory.createCounterColumnQuery(keyspace, keySerializer, nameSerializer);
  }

  @Override
  public <K, N> SliceCounterQuery<K, N> createCounterSliceQuery(Serializer<K> keySerializer,
        Serializer<N> nameSerializer) {
    return HFactory.createCounterSliceQuery(keyspace, keySerializer, nameSerializer);
  }

  @Override
  public <SN, N> HCounterSuperColumn<SN, N> createCounterSuperColumn(SN name, List<HCounterColumn<N>> columns,
        Serializer<SN> superNameSerializer, Serializer<N> nameSerializer) {
    return HFactory.createCounterSuperColumn(name, columns, superNameSerializer, nameSerializer);
  }
  
  @Override
  public String getKeyspaceName() {
    return keyspaceName;
  }

  public void setKeyspaceName(String keyspace) {
    this.keyspaceName = keyspace;
  }

  public Keyspace getKeyspace() {
    return keyspace;
  }

  public void setKeyspace(Keyspace keyspace) {
    this.keyspace = keyspace;
  }

  public ConfigurableConsistencyLevel getConfigurableConsistencyLevelPolicy() {
    return configurableConsistencyLevelPolicy;
  }

  public void setConfigurableConsistencyLevelPolicy(
      ConfigurableConsistencyLevel configurableConsistencyLevelPolicy) {
    this.configurableConsistencyLevelPolicy = configurableConsistencyLevelPolicy;
  }

  @Override
  public String getReplicationStrategyClass() {
    return replicationStrategyClass;
  }

  public void setReplicationStrategyClass(String replicationStrategyClass) {
    this.replicationStrategyClass = replicationStrategyClass;
  }

  @Override
  public int getReplicationFactor() {
    return replicationFactor;
  }

  public void setReplicationFactor(int replicationFactor) {
    this.replicationFactor = replicationFactor;
  }

  @Override
  public Cluster getCluster() {
    return cluster;
  }

  public void setCluster(Cluster cluster) {
    this.cluster = cluster;
  }

}
