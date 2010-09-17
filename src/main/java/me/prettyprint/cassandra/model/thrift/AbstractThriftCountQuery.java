package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.HSlicePredicate;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.Query;

import org.apache.cassandra.thrift.ColumnParent;

/**
 * A base and abstract class to the count querues
 * @author Ran Tavory
 *
 */
/*package*/ abstract class AbstractThriftCountQuery<K, N> implements Query<Integer>{

  protected final KeyspaceOperator keyspaceOperator;
  protected String columnFamily;
  protected K key;
  protected final Serializer<K> keySerializer;
  /** The slice predicate for which the count it performed*/
  protected final HSlicePredicate<N> slicePredicate;

  public AbstractThriftCountQuery(KeyspaceOperator ko, Serializer<K> keySerializer,
      Serializer<N> nameSerializer) {
    Assert.notNull(ko, "keyspaceOperator can't be null");
    Assert.notNull(nameSerializer, "nameSerializer is null");
    Assert.notNull(keySerializer, "keySerializer is null");
    this.keyspaceOperator = ko;
    this.keySerializer = keySerializer;
    this.slicePredicate = new HSlicePredicate<N>(nameSerializer);
  }

  public Query<Integer> setKey(K key) {
    this.key = key;
    return this;
  }

  public Query<Integer> setColumnFamily(String cf) {
    this.columnFamily = cf;
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
            return ks.getCount(keySerializer.toBytes(key), columnParent,
                slicePredicate.toThrift());
          }
        }), this);
  }

  public Query<Integer> setColumnNames(N... columnNames) {
    slicePredicate.setColumnNames(columnNames);
    return this;
  }

  public Query<Integer> setRange(N start, N finish, int count) {
    slicePredicate.setRange(start, finish, false, count);
    return this;
  }
}