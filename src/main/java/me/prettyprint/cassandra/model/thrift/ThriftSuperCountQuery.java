package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.hector.api.query.SuperCountQuery;


/**
 * Counts super column for a of a key in a super column family
 *
 * @author Ran Tavory
 */
public final class ThriftSuperCountQuery<K, SN> extends AbstractThriftCountQuery<K, SN>
    implements SuperCountQuery<K, SN> {

  public ThriftSuperCountQuery(KeyspaceOperator ko, Serializer<K> keySerializer,
      Serializer<SN> sNnameSerializer) {
    super(ko, keySerializer, sNnameSerializer);
  }

  @Override
  public Result<Integer> execute() {
    return countColumns();
  }

  @Override
  public String toString() {
    return "SuperCountQuery(" + columnFamily + "," + key + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public SuperCountQuery<K, SN> setKey(K key) {
    return (SuperCountQuery<K, SN>) super.setKey(key);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SuperCountQuery<K, SN> setColumnFamily(String cf) {
    return (SuperCountQuery<K, SN>) super.setColumnFamily(cf);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SuperCountQuery<K, SN> setColumnNames(SN... columnNames) {
    return (SuperCountQuery<K, SN>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SuperCountQuery<K, SN> setRange(SN start, SN finish, int count) {
    return (SuperCountQuery<K, SN>) super.setRange(start, finish, count);
  }
}
