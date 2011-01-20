package me.prettyprint.cassandra.service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.PrefixedSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.IndexClause;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SuperColumn;

public class PrefixedKeyspaceServiceImpl extends KeyspaceServiceImpl {

  ByteBuffer prefixBytes;
  PrefixedSerializer<ByteBuffer, ByteBuffer> ps;
  ByteBufferSerializer be = new ByteBufferSerializer();
  StringSerializer se = new StringSerializer();

  public <E> PrefixedKeyspaceServiceImpl(String keyspaceName, E keyPrefix,
      Serializer<E> keyPrefixSerializer,
      ConsistencyLevelPolicy consistencyLevel,
      HConnectionManager connectionManager, FailoverPolicy failoverPolicy)
      throws HectorTransportException {
    super(keyspaceName, consistencyLevel, connectionManager, failoverPolicy);

    prefixBytes = keyPrefixSerializer.toByteBuffer(keyPrefix);
    ps = new PrefixedSerializer<ByteBuffer, ByteBuffer>(prefixBytes, be, be);
  }

  public <E> PrefixedKeyspaceServiceImpl(String keyspaceName, E keyPrefix,
      Serializer<E> keyPrefixSerializer,
      ConsistencyLevelPolicy consistencyLevel,
      HConnectionManager connectionManager, FailoverPolicy failoverPolicy,
      Map<String, String> credentials) throws HectorTransportException {
    super(keyspaceName, consistencyLevel, connectionManager, failoverPolicy,
        credentials);

    prefixBytes = keyPrefixSerializer.toByteBuffer(keyPrefix);
    ps = new PrefixedSerializer<ByteBuffer, ByteBuffer>(prefixBytes, be, be);
  }

  public KeyRange toCassandra(KeyRange from) {
    KeyRange to = new KeyRange();
    to.count = from.count;
    to.end_token = from.end_token;
    to.end_key = ps.toByteBuffer(from.end_key);
    to.start_token = from.start_token;
    to.start_key = ps.toByteBuffer(from.start_key);
    return to;
  }

  public KeyRange fromCassandra(KeyRange from) {
    KeyRange to = new KeyRange();
    to.count = from.count;
    to.end_token = from.end_token;
    to.end_key = ps.fromByteBuffer(from.end_key);
    to.start_token = from.start_token;
    to.start_key = ps.fromByteBuffer(from.start_key);
    return to;
  }

  @Override
  public void batchMutate(
      Map<ByteBuffer, Map<String, List<Mutation>>> mutationMap)
      throws HectorException {

    super.batchMutate(ps.toBytesMap(mutationMap));
  }

  @Override
  public void batchMutate(BatchMutation batchMutate) throws HectorException {

    super.batchMutate(batchMutate);
  }

  @Override
  public int getCount(ByteBuffer key, ColumnParent columnParent,
      SlicePredicate predicate) throws HectorException {

    return super.getCount(ps.toByteBuffer(key), columnParent, predicate);
  }

  @Override
  public CassandraHost getCassandraHost() {

    return super.getCassandraHost();
  }

  @Override
  public Map<ByteBuffer, List<Column>> getRangeSlices(
      ColumnParent columnParent, SlicePredicate predicate, KeyRange keyRange)
      throws HectorException {

    return ps.fromBytesMap(super.getRangeSlices(columnParent, predicate,
        toCassandra(keyRange)));
  }

  @Override
  public Map<ByteBuffer, List<SuperColumn>> getSuperRangeSlices(
      ColumnParent columnParent, SlicePredicate predicate, KeyRange keyRange)
      throws HectorException {

    return ps.fromBytesMap(super.getSuperRangeSlices(columnParent, predicate,
        toCassandra(keyRange)));
  }

  @Override
  public List<Column> getSlice(ByteBuffer key, ColumnParent columnParent,
      SlicePredicate predicate) throws HectorException {

    return super.getSlice(ps.toByteBuffer(key), columnParent, predicate);
  }

  @Override
  public SuperColumn getSuperColumn(ByteBuffer key, ColumnPath columnPath)
      throws HectorException {

    return super.getSuperColumn(ps.toByteBuffer(key), columnPath);
  }

  @Override
  public SuperColumn getSuperColumn(ByteBuffer key, ColumnPath columnPath,
      boolean reversed, int size) throws HectorException {

    return super.getSuperColumn(ps.toByteBuffer(key), columnPath, reversed,
        size);
  }

  @Override
  public List<SuperColumn> getSuperSlice(ByteBuffer key,
      ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException {

    return super.getSuperSlice(ps.toByteBuffer(key), columnParent, predicate);
  }

  @Override
  public void insert(ByteBuffer key, ColumnParent columnParent, Column column)
      throws HectorException {

    super.insert(ps.toByteBuffer(key), columnParent, column);
  }

  @Override
  public Map<ByteBuffer, List<Column>> multigetSlice(List<ByteBuffer> keys,
      ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException {

    return super.multigetSlice(ps.toBytesList(keys), columnParent, predicate);
  }

  @Override
  public Map<ByteBuffer, SuperColumn> multigetSuperColumn(
      List<ByteBuffer> keys, ColumnPath columnPath) throws HectorException {

    return super.multigetSuperColumn(ps.toBytesList(keys), columnPath);
  }

  @Override
  public Map<ByteBuffer, SuperColumn> multigetSuperColumn(
      List<ByteBuffer> keys, ColumnPath columnPath, boolean reversed, int size)
      throws HectorException {

    return super.multigetSuperColumn(ps.toBytesList(keys), columnPath,
        reversed, size);
  }

  @Override
  public Map<ByteBuffer, List<SuperColumn>> multigetSuperSlice(
      List<ByteBuffer> keys, ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException {

    return super.multigetSuperSlice(ps.toBytesList(keys), columnParent,
        predicate);
  }

  @Override
  public Map<ByteBuffer, List<Column>> getIndexedSlices(
      ColumnParent columnParent, IndexClause indexClause,
      SlicePredicate predicate) throws HectorException {

    return super.getIndexedSlices(columnParent, indexClause, predicate);
  }

  @Override
  public void remove(ByteBuffer key, ColumnPath columnPath) {

    super.remove(ps.toByteBuffer(key), columnPath);
  }

  @Override
  public Map<ByteBuffer, Integer> multigetCount(List<ByteBuffer> keys,
      ColumnParent columnParent, SlicePredicate slicePredicate)
      throws HectorException {

    return super.multigetCount(ps.toBytesList(keys), columnParent,
        slicePredicate);
  }

  @Override
  public void remove(ByteBuffer key, ColumnPath columnPath, long timestamp)
      throws HectorException {

    super.remove(ps.toByteBuffer(key), columnPath, timestamp);
  }

  @Override
  public String getName() {

    return super.getName();
  }

  @Override
  public Column getColumn(ByteBuffer key, ColumnPath columnPath)
      throws HectorException {

    return super.getColumn(ps.toByteBuffer(key), columnPath);
  }

  @Override
  public HConsistencyLevel getConsistencyLevel(OperationType operationType) {

    return super.getConsistencyLevel(operationType);
  }

  @Override
  public String toString() {

    return super.toString();
  }

}
