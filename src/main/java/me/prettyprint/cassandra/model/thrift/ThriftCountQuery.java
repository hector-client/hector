package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.Result;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.query.CountQuery;


/**
 * Counts column for a standard column family
 *
 * @author Ran Tavory
 */
public final class ThriftCountQuery extends AbstractThriftCountQuery implements CountQuery {

  public ThriftCountQuery(Keyspace ko) {
    super(ko);
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
  public ThriftCountQuery setKey(String key) {
    return (ThriftCountQuery) super.setKey(key);
  }

  @Override
  public ThriftCountQuery setColumnFamily(String cf) {
    return (ThriftCountQuery) super.setColumnFamily(cf);
  }
}
