package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Result;
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
/*package*/ abstract class AbstractThriftCountQuery implements Query<Integer>{

  protected final KeyspaceOperator keyspaceOperator;
  protected String columnFamily;
  protected String key;

  public AbstractThriftCountQuery(KeyspaceOperator ko) {
    Assert.notNull(ko, "keyspaceOperator can't be null");
    this.keyspaceOperator = ko;
  }

  public AbstractThriftCountQuery setKey(String key) {
    this.key = key;
    return this;
  }

  public AbstractThriftCountQuery setColumnFamily(String cf) {
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
            Integer count = ks.getCount(key, columnParent);
            return count;
          }
        }), this);
  }

}