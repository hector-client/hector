package me.prettyprint.cassandra.model;

public class MutationResult extends ExecutionResult<Void> {

  /*package*/ MutationResult(boolean success, long execTime) {
    super(null, success, execTime);
  }

  /*package*/ MutationResult(ExecutionResult<Void> res) {
    super(null, res.isSuccess(), res.getExecutionTimeMicro());
  }

  
  @Override
  public String toString() {
    return "MutationResult(" + toStringInternal() + ")";
  }
}
