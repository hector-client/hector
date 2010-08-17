package me.prettyprint.cassandra.model;


/**
 * Counts column for a standard column family
 *
 * @author Ran Tavory
 */
public final class CountQuery<K,N> extends AbstractCountQuery<K,N> implements Query<Integer> {

  /*package*/ CountQuery(KeyspaceOperator ko, Serializer<K> keySerializer,
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

  @Override
  public CountQuery<K,N> setKey(K key) {
    return (CountQuery<K,N>) super.setKey(key);
  }

  @Override
  public CountQuery<K,N> setColumnFamily(String cf) {
    return (CountQuery<K,N>) super.setColumnFamily(cf);
  }
}
