package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.CassandraHost;

public final class MutationResult extends ExecutionResult<Void> {

  /*package*/ MutationResult(boolean success, long execTime, CassandraHost cassandraHost) {
    super(null, execTime, cassandraHost);
  }

  /*package*/ MutationResult(ExecutionResult<Void> res) {
    super(null, res.getExecutionTimeMicro(), res.getHostUsed());
  }


  @Override
  public String toString() {
    return "MutationResult(" + toStringInternal() + ")";
  }
}
