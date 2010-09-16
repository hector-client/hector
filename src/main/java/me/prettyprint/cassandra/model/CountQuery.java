package me.prettyprint.cassandra.model;

import me.prettyprint.hector.api.query.Query;


/**
 * Counts column for a standard column family
 *
 * @author Ran Tavory
 */
public final class CountQuery extends AbstractCountQuery implements Query<Integer> {

  public CountQuery(KeyspaceOperator ko) {
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
  public CountQuery setKey(String key) {
    return (CountQuery) super.setKey(key);
  }

  @Override
  public CountQuery setColumnFamily(String cf) {
    return (CountQuery) super.setColumnFamily(cf);
  }
}
