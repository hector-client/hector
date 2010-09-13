package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.Query;

import org.apache.cassandra.thrift.ColumnParent;


/**
 * Counts column for a standard column family
 *
 * @author Ran Tavory
 */
public class CountQuery<K,N> implements Query<Integer> {

  protected final KeyspaceOperator keyspaceOperator;
  protected final Serializer<K> keySerializer;
  protected String columnFamily;
  protected K key;
  /** The slice predicate for which the count it performed*/
  protected final HSlicePredicate<N> slicePredicate;

  public CountQuery(KeyspaceOperator ko, Serializer<K> keySerializer,
      Serializer<N> nameSerializer) {
    Assert.notNull(ko, "keyspaceOperator can't be null");
    Assert.notNull(keySerializer, "keySerializer can't be null");
    Assert.notNull(nameSerializer, "columnNameSerializer is null");
    this.keyspaceOperator = ko;
    this.keySerializer = keySerializer;
    this.slicePredicate = new HSlicePredicate<N>(nameSerializer);
  }

  @Override
  public Result<Integer> execute() {
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

  @Override
  public String toString() {
    return "CountQuery(" + columnFamily + "," + key + ")";
  }

  public CountQuery<K,N> setKey(K key) {
    this.key = key;
    return this;
  }

  public CountQuery<K,N> setColumnFamily(String cf) {
    this.columnFamily = cf;
    return this;
  }

  public CountQuery<K,N> setColumnNames(N... columnNames) {
    slicePredicate.setColumnNames(columnNames);
    return this;
  }

  public CountQuery<K,N> setRange(N start, N finish, int count) {
    slicePredicate.setRange(start, finish, false, count);
    return this;
  }
}
