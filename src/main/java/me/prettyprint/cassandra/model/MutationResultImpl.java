package me.prettyprint.cassandra.model;

/**
 * MutationResult impl. Not convinced MutationResult needs to be
 * an interface?
 * 
 * @author zznate
 *
 */
public class MutationResultImpl implements MutationResult {

  @Override
  public long getExecutionTimeMili() {

    return 0;
  }

  @Override
  public String getHostUsed() {

    return null;
  }

  @Override
  public boolean isSuccess() {

    return false;
  }

}
