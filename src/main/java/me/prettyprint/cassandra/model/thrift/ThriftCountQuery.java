package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.query.CountQuery;
import me.prettyprint.hector.api.query.QueryResult;


/**
 * Counts column for a standard column family
 *
 * @author Ran Tavory
 */
public final class ThriftCountQuery<K, N> extends AbstractThriftCountQuery<K, N> implements
    CountQuery<K, N> {

  public ThriftCountQuery(Keyspace k, Serializer<K> keySerializer,
      Serializer<N> nameSerializer) {
    super(k, keySerializer, nameSerializer);
  }

  @Override
  public QueryResult<Integer> execute() {
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
