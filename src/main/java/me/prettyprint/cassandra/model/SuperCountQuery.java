package me.prettyprint.cassandra.model;


/**
 * Counts super column for a of a key in a super column family
 *
 * @author Ran Tavory
 */
public final class SuperCountQuery extends AbstractCountQuery implements Query<Integer> {

  /*package*/ SuperCountQuery(KeyspaceOperator ko) {
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
  public SuperCountQuery setKey(String key) {
    return (SuperCountQuery) super.setKey(key);
  }

  @Override
  public SuperCountQuery setColumnFamily(String cf) {
    return (SuperCountQuery) super.setColumnFamily(cf);
  }
}
