package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.ColumnParent;

/**
 * A base and abstract class to the count queries
 * @author Ran Tavory
 *
 */
/*package*/ abstract class AbstractCountQuery<K, N> implements Query<Integer>{

  protected final KeyspaceOperator keyspaceOperator;
  protected final Serializer<K> keySerializer;
  protected String columnFamily;
  protected K key;
  /** The slice predicate for which the count it performed*/
  protected final HSlicePredicate<N> slicePredicate;

  public AbstractCountQuery(KeyspaceOperator ko, Serializer<K> keySerializer,
      Serializer<N> columnNameSerializer) {
    Assert.notNull(ko, "keyspaceOperator can't be null");
    Assert.notNull(keySerializer, "keySerializer can't be null");
    Assert.notNull(columnNameSerializer, "columnNameSerializer is null");
    this.keyspaceOperator = ko;
    this.keySerializer = keySerializer;
    this.slicePredicate = new HSlicePredicate<N>(columnNameSerializer);
  }

  public AbstractCountQuery<K,N> setKey(K key) {
    this.key = key;
    return this;
  }

  public AbstractCountQuery<K,N> setColumnFamily(String cf) {
    this.columnFamily = cf;
    return this;
  }

  public AbstractCountQuery<K,N> setColumnNames(N... columnNames) {
    slicePredicate.setColumnNames(columnNames);
    return this;
  }

  public AbstractCountQuery<K,N> setRange(N start, N finish, int count) {
    slicePredicate.setRange(start, finish, false, count);
    return this;
  }


  protected  Result<Integer> countColumns() {
    Assert.notNull(key, "key is null");
    Assert.notNull(columnFamily, "columnFamily is null");
    return new Result<Integer>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<Integer>() {
          @Override
          public Integer doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamily);
            Integer count = ks.getCount(keySerializer.toBytes(key), columnParent,
                slicePredicate.toThrift());
            return count;
          }
        }), this);
  }
}