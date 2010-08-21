package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.ColumnParent;


/**
 * Counts sub columns for a of a key and a super column in a super column family
 *
 * @author Ran Tavory
 *
 * @param <SN> super column name tyoe
 */
public final class SubCountQuery<SN> extends AbstractCountQuery implements Query<Integer> {

  private final Serializer<SN> superNameSerializer;

  private SN superColumnName;

  /*package*/ SubCountQuery(KeyspaceOperator ko, Serializer<SN> superNameSerializer) {
    super(ko);
    Assert.notNull(superNameSerializer, "superNameSerializer is null");
    this.superNameSerializer = superNameSerializer;
  }

  public SubCountQuery<SN> setSuperColumn(SN sc) {
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
            Integer count = ks.getCount(key, columnParent);
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
  public SubCountQuery<SN> setKey(String key) {
    return (SubCountQuery<SN>) super.setKey(key);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SubCountQuery<SN> setColumnFamily(String cf) {
    return (SubCountQuery<SN>) super.setColumnFamily(cf);
  }
}
