package me.prettyprint.cassandra.model;

public class ResultImpl<T> implements Result<T> {

  public ResultImpl(Column column) {
    // TODO Auto-generated constructor stub
  }


  @Override
  public Query<T> getQuery() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isSuccess() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public long getExecutionTimeMili() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getHostUsed() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public T get() {
    // TODO Auto-generated method stub
    return null;
  }

}
