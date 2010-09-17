package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.SubCountQuery;

import org.apache.cassandra.thrift.ColumnParent;


/**
 * Counts sub columns for a of a key and a super column in a super column family
 *
 * @author Ran Tavory
 *
 * @param <SN> super column name tyoe
 */
public final class ThriftSubCountQuery<K,SN,N> extends AbstractThriftCountQuery<K,N>
    implements SubCountQuery<K, SN, N> {

  private final Serializer<SN> superNameSerializer;

  private SN superColumnName;

  public ThriftSubCountQuery(KeyspaceOperator ko, Serializer<K> keySerializer,
      Serializer<SN> superNameExtractor, Serializer<N> nameSerializer) {
    super(ko, keySerializer, nameSerializer);
    Assert.notNull(superNameExtractor, "superNameExtractor is null");
    this.superNameSerializer = superNameExtractor;
  }

  @Override
  public SubCountQuery<K,SN,N> setSuperColumn(SN sc) {
    superColumnName = sc;
    return this;
  }

  @Override
  public Result<Integer> execute() {
    Assert.notNull(key, "key is null");
    Assert.notNull(columnFamily, "columnFamily is null");
    Assert.notNull(superColumnName, "superColumnName is null");
    return new Result<Integer>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<Integer>() {
          @Override
          public Integer doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamily);
            columnParent.setSuper_column(superNameSerializer.toBytes(superColumnName));
            Integer count = ks.getCount(keySerializer.toBytes(key), columnParent,
                slicePredicate.toThrift());
            return count;
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SubCountQuery(" + columnFamily + "," + key + "," + superColumnName + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public SubCountQuery<K,SN,N> setKey(K key) {
    return (SubCountQuery<K,SN,N>) super.setKey(key);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SubCountQuery<K,SN,N> setColumnFamily(String cf) {
    return (SubCountQuery<K,SN,N>) super.setColumnFamily(cf);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SubCountQuery<K,SN,N> setColumnNames(N... columnNames) {
    return (SubCountQuery<K, SN, N>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SubCountQuery<K,SN,N> setRange(N start, N finish, int count) {
    return (SubCountQuery<K, SN, N>) super.setRange(start, finish, count);
  }
}
