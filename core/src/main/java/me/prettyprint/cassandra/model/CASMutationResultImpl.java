package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.cassandra.thrift.CASResult;
import org.apache.cassandra.thrift.Column;

import me.prettyprint.cassandra.serializers.TypeInferringSerializer;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.mutation.MutationResult;

public final class CASMutationResultImpl extends ExecutionResult<org.apache.cassandra.thrift.CASResult> implements MutationResult {

  private CASResult casResult = null;
	
  /*package*/ CASMutationResultImpl(boolean success, long execTime, CassandraHost cassandraHost) {
    super(null, execTime, cassandraHost);
  }

  CASMutationResultImpl(ExecutionResult<org.apache.cassandra.thrift.CASResult> res) {
	  this(res.get().isSuccess(), res.getExecutionTimeNano(), res.getHostUsed());
	  casResult = res.get();
  }

  public CASResult getCASResult() {
	  return casResult;
  }

  public void setCASResult(CASResult res) {
	  casResult = res;
  }
  
  /**
   * Returns the raw Thrift Columns as a generic List.
   * @return
   */
  public List<Column> getCurrentValues() {
	  return casResult.getCurrent_values();
  }
  
  /**
   * Convert the raw Thrift columns into HColumn<N,V> to make it easier
   * to use.
   * @return
   */
  public <N,V> List<HColumn<N,V>> getCurrentColumnValues() {
	  List<HColumn<N,V>> columnValues = new ArrayList<HColumn<N,V>>();
	  for (Column c: casResult.getCurrent_values()) {
		  columnValues.add(new HColumnImpl<N,V>(c, TypeInferringSerializer.<N>get(), TypeInferringSerializer.<V>get()));
	  }
	  return columnValues;
  }
  
  @Override
  public String toString() {
    return formatMessage("CASMutationResult", "n/a");
  }
}
