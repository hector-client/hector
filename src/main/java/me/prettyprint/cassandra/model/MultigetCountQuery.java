package me.prettyprint.cassandra.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.Query;

import org.apache.cassandra.thrift.ColumnParent;

public class MultigetCountQuery<K,N> implements Query<Map<K, Integer>> {

  protected final KeyspaceOperator keyspaceOperator;
  protected final Serializer<K> keySerializer;
  protected String columnFamily;
  protected List<K> keys;

  /** The slice predicate for which the count it performed*/
  protected final HSlicePredicate<N> slicePredicate;

  public MultigetCountQuery(KeyspaceOperator ko, Serializer<K> keySerializer,
      Serializer<N> nameSerializer) {
    Assert.notNull(ko, "keyspaceOperator can't be null");
    Assert.notNull(keySerializer, "keySerializer can't be null");
    Assert.notNull(nameSerializer, "columnNameSerializer is null");
    this.keyspaceOperator = ko;
    this.keySerializer = keySerializer;
    this.slicePredicate = new HSlicePredicate<N>(nameSerializer);
  }

  public MultigetCountQuery<K, N> setKeys(K... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  public MultigetCountQuery<K,N> setColumnFamily(String cf) {
    this.columnFamily = cf;
    return this;
  }

  public MultigetCountQuery<K,N> setColumnNames(N... columnNames) {
    slicePredicate.setColumnNames(columnNames);
    return this;
  }

  public MultigetCountQuery<K,N> setRange(N start, N finish, int count) {
    slicePredicate.setRange(start, finish, false, count);
    return this;
  }

  @Override
  public Result<Map<K, Integer>> execute() {
    Assert.notNull(keys, "keys list is null");
    Assert.notNull(columnFamily, "columnFamily is null");
    return new Result<Map<K,Integer>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<Map<K,Integer>>() {
          @Override
          public Map<K,Integer> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamily);
            Map<K,Integer> counts = keySerializer.fromBytesMap(
                ks.multigetCount(keySerializer.toBytesSet(keys), columnParent, slicePredicate.toThrift()));
            return counts;
          }
        }), this);
  }

  @Override
  public String toString() {
   return String.format("MultigetCountQuery(%s) on cf: %s with keys: %s",slicePredicate.toString(),columnFamily,keys.toString());
  }

}
