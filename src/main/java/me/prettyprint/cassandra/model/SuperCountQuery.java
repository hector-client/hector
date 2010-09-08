package me.prettyprint.cassandra.model;

import me.prettyprint.hector.api.query.Query;


/**
 * Counts super column for a of a key in a super column family
 *
 * @author Ran Tavory
 */
public final class SuperCountQuery<K,SN> extends AbstractCountQuery<K,SN> implements Query<Integer> {

  public SuperCountQuery(KeyspaceOperator ko, Serializer<K> keySerializer,
      Serializer<SN> superNameSerializer) {
    super(ko, keySerializer, superNameSerializer);
  }

  @Override
  public Result<Integer> execute() {
    return countColumns();
  }

  @Override
  public String toString() {
    return "SuperCountQuery(" + columnFamily + "," + key + ")";
  }

  @Override
  public SuperCountQuery<K,SN> setKey(K key) {
    return (SuperCountQuery<K,SN>) super.setKey(key);
  }

  @Override
  public SuperCountQuery<K,SN> setColumnFamily(String cf) {
    return (SuperCountQuery<K,SN>) super.setColumnFamily(cf);
  }
}
