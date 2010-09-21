package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.ExecutingKeyspace;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.Query;

import org.apache.cassandra.thrift.ColumnParent;

/**
 * A base and abstract class to the count querues
 * @author Ran Tavory
 *
 */
/*package*/ abstract class AbstractThriftCountQuery implements Query<Integer>{

  protected final ExecutingKeyspace keyspace;
  protected String columnFamily;
  protected String key;

  public AbstractThriftCountQuery(Keyspace keyspace) {
    Assert.notNull(keyspace, "k can't be null");
    this.keyspace = (ExecutingKeyspace) keyspace;
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
    return new Result<Integer>(keyspace.doExecute(
        new KeyspaceOperationCallback<Integer>() {
          @Override
          public Integer doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamily);
            Integer count = ks.getCount(key, columnParent);
            return count;
          }
        }), this);
  }

}