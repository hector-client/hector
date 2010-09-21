package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.Result;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.query.SuperCountQuery;


/**
 * Counts super column for a of a key in a super column family
 *
 * @author Ran Tavory
 */
public final class ThriftSuperCountQuery extends AbstractThriftCountQuery implements SuperCountQuery {

  public ThriftSuperCountQuery(Keyspace ko) {
    super(ko);
  }

  @Override
  public Result<Integer> execute() {
    return countColumns();
  }

  @Override
  public String toString() {
    return "SuperCountQuery(" + columnFamily + "," + key + ")";
  }

  @Override
  public ThriftSuperCountQuery setKey(String key) {
    return (ThriftSuperCountQuery) super.setKey(key);
  }

  @Override
  public ThriftSuperCountQuery setColumnFamily(String cf) {
    return (ThriftSuperCountQuery) super.setColumnFamily(cf);
  }
}
