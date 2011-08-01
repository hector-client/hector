package me.prettyprint.cassandra.service;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.thrift.AbstractThriftClientWrapper;
import me.prettyprint.cassandra.serializers.ByteBufferSerializer;
import me.prettyprint.cassandra.serializers.PrefixedSerializer;

import org.apache.cassandra.thrift.Cassandra.Client;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.CounterColumn;
import org.apache.cassandra.thrift.IndexClause;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.KeySlice;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;

public class VirtualKeyspaceCassandraClient extends AbstractThriftClientWrapper {

  ByteBuffer prefixBytes;
  PrefixedSerializer<ByteBuffer, ByteBuffer> ps;
  ByteBufferSerializer be = new ByteBufferSerializer();

  public VirtualKeyspaceCassandraClient(Client client, ByteBuffer prefixBytes) {
    super(client);
    this.prefixBytes = prefixBytes;
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

  public KeySlice prefixKeySlice(KeySlice unprefixed) {
    KeySlice prefixed = new KeySlice();
    prefixed.key = ps.toByteBuffer(unprefixed.key);
    prefixed.columns = unprefixed.columns;
    return prefixed;
  }

  public KeySlice unprefixKeySlice(KeySlice prefixed) {
    KeySlice unprefixed = new KeySlice();
    unprefixed.key = ps.fromByteBuffer(unprefixed.key);
    unprefixed.columns = prefixed.columns;
    return unprefixed;
  }

  public List<KeySlice> prefixKeySlice(List<KeySlice> unprefixed) {
    List<KeySlice> prefixed = new ArrayList<KeySlice>();
    for (KeySlice ks : unprefixed) {
      prefixed.add(prefixKeySlice(ks));
    }
    return prefixed;
  }

  public List<KeySlice> unprefixKeySlice(List<KeySlice> prefixed) {
    List<KeySlice> unprefixed = new ArrayList<KeySlice>();
    for (KeySlice ks : prefixed) {
      unprefixed.add(unprefixKeySlice(ks));
    }
    return unprefixed;
  }

  @Override
  public void add(ByteBuffer key, ColumnParent column_parent,
      CounterColumn column, ConsistencyLevel consistency_level)
      throws InvalidRequestException, UnavailableException, TimedOutException,
      TException {
    super.add(ps.toByteBuffer(key), column_parent, column, consistency_level);
  }

  @Override
  public void batch_mutate(
      Map<ByteBuffer, Map<String, List<Mutation>>> mutation_map,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    super.batch_mutate(ps.toBytesMap(mutation_map), consistency_level);
  }

  @Override
  public ColumnOrSuperColumn get(ByteBuffer key, ColumnPath column_path,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      NotFoundException, UnavailableException, TimedOutException, TException {
    return super.get(ps.toByteBuffer(key), column_path, consistency_level);
  }

  @Override
  public int get_count(ByteBuffer key, ColumnParent column_parent,
      SlicePredicate predicate, ConsistencyLevel consistency_level)
      throws InvalidRequestException, UnavailableException, TimedOutException,
      TException {
    return super.get_count(ps.toByteBuffer(key), column_parent, predicate,
        consistency_level);
  }

  @Override
  public List<KeySlice> get_indexed_slices(ColumnParent column_parent,
      IndexClause index_clause, SlicePredicate column_predicate,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    return unprefixKeySlice(super.get_indexed_slices(column_parent,
        index_clause, column_predicate, consistency_level));
  }

  @Override
  public List<KeySlice> get_range_slices(ColumnParent column_parent,
      SlicePredicate predicate, KeyRange range,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    return unprefixKeySlice(super.get_range_slices(column_parent, predicate,
        range, consistency_level));
  }

  @Override
  public List<ColumnOrSuperColumn> get_slice(ByteBuffer key,
      ColumnParent column_parent, SlicePredicate predicate,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    return super.get_slice(ps.toByteBuffer(key), column_parent, predicate,
        consistency_level);
  }

  @Override
  public void insert(ByteBuffer key, ColumnParent column_parent, Column column,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    super
        .insert(ps.toByteBuffer(key), column_parent, column, consistency_level);
  }

  @Override
  public Map<ByteBuffer, Integer> multiget_count(List<ByteBuffer> keys,
      ColumnParent column_parent, SlicePredicate predicate,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    return ps.fromBytesMap(super.multiget_count(ps.toBytesList(keys),
        column_parent, predicate, consistency_level));
  }

  @Override
  public Map<ByteBuffer, List<ColumnOrSuperColumn>> multiget_slice(
      List<ByteBuffer> keys, ColumnParent column_parent,
      SlicePredicate predicate, ConsistencyLevel consistency_level)
      throws InvalidRequestException, UnavailableException, TimedOutException,
      TException {
    return ps.fromBytesMap(super.multiget_slice(ps.toBytesList(keys),
        column_parent, predicate, consistency_level));
  }

  @Override
  public void remove(ByteBuffer key, ColumnPath column_path, long timestamp,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    super.remove(ps.toByteBuffer(key), column_path, timestamp,
        consistency_level);
  }

  @Override
  public void remove_counter(ByteBuffer key, ColumnPath path,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    super.remove_counter(ps.toByteBuffer(key), path, consistency_level);
  }

}
