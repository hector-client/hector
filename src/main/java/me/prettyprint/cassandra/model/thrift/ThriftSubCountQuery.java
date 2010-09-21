package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
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
public final class ThriftSubCountQuery<SN> extends AbstractThriftCountQuery implements SubCountQuery<SN> {

  private final Serializer<SN> superNameSerializer;

  private SN superColumnName;

  public ThriftSubCountQuery(Keyspace ko, Serializer<SN> superNameSerializer) {
    super(ko);
    Assert.notNull(superNameSerializer, "superNameSerializer is null");
    this.superNameSerializer = superNameSerializer;
  }

  @Override
  public SubCountQuery<SN> setSuperColumn(SN sc) {
    superColumnName = sc;
    return this;
  }

  @Override
  public Result<Integer> execute() {
    Assert.notNull(key, "key is null");
    Assert.notNull(columnFamily, "columnFamily is null");
    Assert.notNull(superColumnName, "superColumnName is null");
    return new Result<Integer>(keyspace.doExecute(
        new KeyspaceOperationCallback<Integer>() {
          @Override
          public Integer doInKeyspace(KeyspaceService ks) throws HectorException {
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
  public ThriftSubCountQuery<SN> setKey(String key) {
    return (ThriftSubCountQuery<SN>) super.setKey(key);
  }

  @SuppressWarnings("unchecked")
  @Override
  public ThriftSubCountQuery<SN> setColumnFamily(String cf) {
    return (ThriftSubCountQuery<SN>) super.setColumnFamily(cf);
  }
}
