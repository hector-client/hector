package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.hector.api.query.CountQuery;


/**
 * Counts column for a standard column family
 *
 * @author Ran Tavory
 */
public final class ThriftCountQuery<K, N> extends AbstractThriftCountQuery<K, N> implements
    CountQuery<K, N> {

  public ThriftCountQuery(KeyspaceOperator ko, Serializer<K> keySerializer,
      Serializer<N> nameSerializer) {
    super(ko, keySerializer, nameSerializer);
  }

  @Override
  public Result<Integer> execute() {
    return countColumns();
  }

  @Override
  public String toString() {
    return "CountQuery(" + columnFamily + "," + key + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public ThriftCountQuery<K, N> setKey(K key) {
    return (ThriftCountQuery<K, N>) super.setKey(key);
  }

  @SuppressWarnings("unchecked")
  @Override
  public ThriftCountQuery<K, N> setColumnFamily(String cf) {
    return (ThriftCountQuery<K, N>) super.setColumnFamily(cf);
  }

  @SuppressWarnings("unchecked")
  @Override
  public CountQuery<K, N> setRange(N start, N finish, int count) {
    return (CountQuery<K, N>) super.setRange(start, finish, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public CountQuery<K, N> setColumnNames(N... columnNames) {
    return (CountQuery<K, N>) super.setColumnNames(columnNames);
  }
}
