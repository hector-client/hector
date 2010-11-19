package me.prettyprint.cassandra.service.spring;

import java.util.List;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.model.MutatorImpl;
import me.prettyprint.cassandra.model.thrift.ThriftColumnQuery;
import me.prettyprint.cassandra.serializers.TypeInferringSerializer;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.factory.HFactory;
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

  public HectorTemplateImpl(Cluster cluster, String keyspace,
	  int replicationFactor, String replicationStrategyClass,
	  ConfigurableConsistencyLevel configurableConsistencyLevelPolicy) {
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
  public <N, V> ColumnQuery<N, V> createColumnQuery(
	  Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
	return new ThriftColumnQuery<N, V>(keyspace, nameSerializer,
		valueSerializer);
  }

  @Override
  public CountQuery createCountQuery() {
	return HFactory.createCountQuery(keyspace);
  }

  @Override
  public SuperCountQuery createSuperCountQuery() {
	return HFactory.createSuperCountQuery(keyspace);
  }

  @Override
  public <SN> SubCountQuery<SN> createSubCountQuery(
	  Serializer<SN> superNameSerializer) {
	return HFactory.createSubCountQuery(keyspace, superNameSerializer);
  }

  @Override
  public <SN, N, V> SuperColumnQuery<SN, N, V> createSuperColumnQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer) {
	return HFactory.createSuperColumnQuery(keyspace, sNameSerializer,
		nameSerializer, valueSerializer);
  }

  @Override
  public <N, V> MultigetSliceQuery<N, V> createMultigetSliceQuery(
	  Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
	return HFactory.createMultigetSliceQuery(keyspace, nameSerializer,
		valueSerializer);
  }

  @Override
  public <SN, N, V> MultigetSuperSliceQuery<SN, N, V> createMultigetSuperSliceQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer) {
	return HFactory.createMultigetSuperSliceQuery(keyspace, sNameSerializer,
		nameSerializer, valueSerializer);
  }

  @Override
  public <SN, N, V> MultigetSubSliceQuery<SN, N, V> createMultigetSubSliceQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer) {
	return HFactory.createMultigetSubSliceQuery(keyspace, sNameSerializer,
		nameSerializer, valueSerializer);
  }

  @Override
  public <N, V> RangeSlicesQuery<N, V> createRangeSlicesQuery(
	  Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
	return HFactory.createRangeSlicesQuery(keyspace, nameSerializer,
		valueSerializer);
  }

  @Override
  public <SN, N, V> RangeSuperSlicesQuery<SN, N, V> createRangeSuperSlicesQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer) {
	return HFactory.createRangeSuperSlicesQuery(keyspace, sNameSerializer,
		nameSerializer, valueSerializer);
  }

  @Override
  public <SN, N, V> RangeSubSlicesQuery<SN, N, V> createRangeSubSlicesQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer) {
	return HFactory.createRangeSubSlicesQuery(keyspace, sNameSerializer,
		nameSerializer, valueSerializer);
  }

  @Override
  public <N, V> SliceQuery<N, V> createSliceQuery(Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer) {
	return HFactory.createSliceQuery(keyspace, nameSerializer, valueSerializer);
  }

  @Override
  public <SN, N, V> SubSliceQuery<SN, N, V> createSubSliceQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer) {
	return HFactory.createSubSliceQuery(keyspace, sNameSerializer,
		nameSerializer, valueSerializer);
  }

  @Override
  public SliceQuery<byte[], byte[]> createSliceQuery() {
	// TODO Auto-generated method stub
	return null;
  }

  @Override
  public SuperSliceQuery<byte[], byte[], byte[]> createSuperSliceQuery() {
	// TODO Auto-generated method stub
	return null;
  }

  @Override
  public <SN, N, V> SuperSliceQuery<SN, N, V> createSuperSliceQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer) {
	return HFactory.createSuperSliceQuery(keyspace, sNameSerializer,
		nameSerializer, valueSerializer);
  }

  @Override
  public <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name,
	  List<HColumn<N, V>> columns, Serializer<SN> superNameSerializer,
	  Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
	return HFactory.createSuperColumn(name, columns, createTimestamp(),
		superNameSerializer, nameSerializer, valueSerializer);
  }

  @Override
  public <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name,
	  List<HColumn<N, V>> columns, long clock,
	  Serializer<SN> superNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer) {
	return HFactory.createSuperColumn(name, columns, clock,
		superNameSerializer, nameSerializer, valueSerializer);
  }

  @Override
  public <N, V> HColumn<N, V> createColumn(N name, V value, long clock,
	  Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
	return HFactory.createColumn(name, value, clock, nameSerializer,
		valueSerializer);
  }

  @Override
  public <N, V> HColumn<N, V> createColumn(N name, V value,
	  Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
	return HFactory.createColumn(name, value, createTimestamp(),
		nameSerializer, valueSerializer);
  }

  @Override
  public long createTimestamp() {
	return CassandraHost.DEFAULT_TIMESTAMP_RESOLUTION.createTimestamp();
  }

  private ColumnPath createColumnPath(String columnFamilyName) {
	Validate.notNull(columnFamilyName, "columnFamilyName cannot be null");
	ColumnPath columnPath = new ColumnPath(columnFamilyName);
	return columnPath;
  }

  <SN> ColumnPath createSuperColumnPath(String columnFamilyName,
	  SN superColumnName, Serializer<SN> superNameSerializer) {
	noNullElements(columnFamilyName, superNameSerializer);
	ColumnPath columnPath = createColumnPath(columnFamilyName);
	if (superColumnName != null) {
	  columnPath.setSuper_column(superNameSerializer.toBytes(superColumnName));
	}
	return columnPath;
  }

  private void noNullElements(Object... elements) {
	Validate.noNullElements(elements);
  }

  @Override
  public <N, V> Mutator createMutator() {
	return new MutatorImpl(keyspace);
  }

  @Override
  public <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name,
	  List<HColumn<N, V>> columns) {
	return createSuperColumn(name, columns, TypeInferringSerializer.<SN> get(),
		TypeInferringSerializer.<N> get(), TypeInferringSerializer.<V> get());
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