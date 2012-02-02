package me.prettyprint.cassandra.service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.PrefixedSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.CounterColumn;
import org.apache.cassandra.thrift.CounterSuperColumn;
import org.apache.cassandra.thrift.IndexClause;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SuperColumn;

public class VirtualKeyspaceServiceImpl extends KeyspaceServiceImpl {

  ByteBuffer prefixBytes;
  PrefixedSerializer<ByteBuffer, ByteBuffer> ps;
  ByteBufferSerializer be = new ByteBufferSerializer();
  StringSerializer se = new StringSerializer();

  public <E> VirtualKeyspaceServiceImpl(String keyspaceName, E keyPrefix,
      Serializer<E> keyPrefixSerializer,
      ConsistencyLevelPolicy consistencyLevel,
      HConnectionManager connectionManager, FailoverPolicy failoverPolicy)
      throws HectorTransportException {
    super(keyspaceName, consistencyLevel, connectionManager, failoverPolicy);

    prefixBytes = keyPrefixSerializer.toByteBuffer(keyPrefix);
    ps = new PrefixedSerializer<ByteBuffer, ByteBuffer>(prefixBytes, be, be);
  }

  public <E> VirtualKeyspaceServiceImpl(String keyspaceName, E keyPrefix,
      Serializer<E> keyPrefixSerializer,
      ConsistencyLevelPolicy consistencyLevel,
      HConnectionManager connectionManager, FailoverPolicy failoverPolicy,
      Map<String, String> credentials) throws HectorTransportException {
    super(keyspaceName, consistencyLevel, connectionManager, failoverPolicy,
        credentials);

    prefixBytes = keyPrefixSerializer.toByteBuffer(keyPrefix);
    ps = new PrefixedSerializer<ByteBuffer, ByteBuffer>(prefixBytes, be, be);
  }

  public KeyRange prefixKeyRange(KeyRange unprefixed) {
    KeyRange prefixed = new KeyRange();
    prefixed.count = unprefixed.count;
    prefixed.end_token = unprefixed.end_token;
    prefixed.end_key = ps.toByteBuffer(unprefixed.end_key);
    prefixed.start_token = unprefixed.start_token;
    prefixed.start_key = ps.toByteBuffer(unprefixed.start_key);
    return prefixed;
  }

  public KeyRange unprefixKeyRange(KeyRange prefixed) {
    KeyRange unprefixed = new KeyRange();
    unprefixed.count = prefixed.count;
    unprefixed.end_token = prefixed.end_token;
    unprefixed.end_key = ps.fromByteBuffer(prefixed.end_key);
    unprefixed.start_token = prefixed.start_token;
    unprefixed.start_key = ps.fromByteBuffer(prefixed.start_key);
    return unprefixed;
  }

  @Override
  public void batchMutate(Map<ByteBuffer, Map<String, List<Mutation>>> mutationMap) throws HectorException {
    super.batchMutate(ps.toBytesMap(mutationMap));
  }

  @Override
  public void batchMutate(BatchMutation batchMutate) throws HectorException {
    batchMutate(batchMutate.getMutationMap());
  }


  @Override
  public int getCount(ByteBuffer key, ColumnParent columnParent, SlicePredicate predicate) throws HectorException {
    return super.getCount(ps.toByteBuffer(key), columnParent, predicate);
  }

  @Override
  public Map<ByteBuffer, List<Column>> getRangeSlices(
      ColumnParent columnParent, SlicePredicate predicate, KeyRange keyRange)
      throws HectorException {

    return ps.fromBytesMap(super.getRangeSlices(columnParent, predicate,
        prefixKeyRange(keyRange)));
  }

  @Override
  public Map<ByteBuffer, List<CounterColumn>> getRangeCounterSlices(
      ColumnParent columnParent, SlicePredicate predicate, KeyRange keyRange)
      throws HectorException {

    return ps.fromBytesMap(super.getRangeCounterSlices(columnParent, predicate,
        prefixKeyRange(keyRange)));
  }

  @Override
  public Map<ByteBuffer, List<SuperColumn>> getSuperRangeSlices(
      ColumnParent columnParent, SlicePredicate predicate, KeyRange keyRange)
      throws HectorException {

    return ps.fromBytesMap(super.getSuperRangeSlices(columnParent, predicate,
        prefixKeyRange(keyRange)));
  }

  @Override
  public Map<ByteBuffer, List<CounterSuperColumn>> getSuperRangeCounterSlices(
      ColumnParent columnParent, SlicePredicate predicate, KeyRange keyRange)
      throws HectorException {

    return ps.fromBytesMap(super.getSuperRangeCounterSlices(columnParent,
        predicate, prefixKeyRange(keyRange)));
  }

  @Override
  public List<Column> getSlice(ByteBuffer key, ColumnParent columnParent,
      SlicePredicate predicate) throws HectorException {

    return super.getSlice(ps.toByteBuffer(key), columnParent, predicate);
  }

  @Override
  public List<CounterColumn> getCounterSlice(ByteBuffer key,
      ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException {

    return super.getCounterSlice(ps.toByteBuffer(key), columnParent, predicate);
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

    return ps.fromBytesMap(super.multigetSlice(ps.toBytesList(keys),
        columnParent, predicate));
  }

  @Override
  public Map<ByteBuffer, List<CounterColumn>> multigetCounterSlice(
      List<ByteBuffer> keys, ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException {

    return ps.fromBytesMap(super.multigetCounterSlice(ps.toBytesList(keys),
        columnParent, predicate));
  }

  @Override
  public Map<ByteBuffer, List<SuperColumn>> multigetSuperSlice(
      List<ByteBuffer> keys, ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException {

    return ps.fromBytesMap(super.multigetSuperSlice(ps.toBytesList(keys),
        columnParent, predicate));
  }

  @Override
  public Map<ByteBuffer, List<CounterSuperColumn>> multigetCounterSuperSlice(
      List<ByteBuffer> keys, ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException {

    return ps.fromBytesMap(super.multigetCounterSuperSlice(
        ps.toBytesList(keys), columnParent, predicate));
  }

  @Override
  public Map<ByteBuffer, List<Column>> getIndexedSlices(
      ColumnParent columnParent, IndexClause indexClause,
      SlicePredicate predicate) throws HectorException {

    return ps.fromBytesMap(super.getIndexedSlices(columnParent, indexClause,
        predicate));
  }

  @Override
  public Map<ByteBuffer, Integer> multigetCount(List<ByteBuffer> keys,
      ColumnParent columnParent, SlicePredicate slicePredicate)
      throws HectorException {

    return ps.fromBytesMap(super.multigetCount(ps.toBytesList(keys),
        columnParent, slicePredicate));
  }

  @Override
  public void remove(ByteBuffer key, ColumnPath columnPath, long timestamp)
      throws HectorException {

    super.remove(ps.toByteBuffer(key), columnPath, timestamp);
  }

  @Override
  public Column getColumn(ByteBuffer key, ColumnPath columnPath)
      throws HectorException {

    return super.getColumn(ps.toByteBuffer(key), columnPath);
  }

  @Override
  public CounterColumn getCounter(ByteBuffer key, ColumnPath columnPath)
      throws HectorException {
    return super.getCounter(ps.toByteBuffer(key), columnPath);
  }

  @Override
  public void addCounter(final ByteBuffer key, final ColumnParent columnParent,
      final CounterColumn counterColumn) throws HectorException {
    super.addCounter(ps.toByteBuffer(key), columnParent, counterColumn);
  }

  @Override
  public List<CounterSuperColumn> getCounterSuperSlice(ByteBuffer key,
      ColumnParent columnParent, SlicePredicate predicate)
      throws HectorException {
    return super.getCounterSuperSlice(ps.toByteBuffer(key), columnParent,
        predicate);
  }

  @Override
  public void removeCounter(ByteBuffer key, ColumnPath columnPath)
      throws HectorException {
    super.removeCounter(ps.toByteBuffer(key), columnPath);
  }

}
