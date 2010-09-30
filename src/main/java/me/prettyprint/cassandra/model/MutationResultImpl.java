package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.mutation.MutationResult;

public final class MutationResultImpl extends ExecutionResult<Void> implements MutationResult {

  /*package*/ MutationResultImpl(boolean success, long execTime, CassandraHost cassandraHost) {
    super(null, execTime, cassandraHost);
  }

  /*package*/ MutationResultImpl(ExecutionResult<Void> res) {
    super(null, res.getExecutionTimeMicro(), res.getHostUsed());
  }


  @Override
  public String toString() {
    return formatMessage("MutationResult", "n/a");
  }
}
