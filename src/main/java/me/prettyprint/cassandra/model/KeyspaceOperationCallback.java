package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;

/*package*/ abstract class KeyspaceOperationCallback<T> {
  
  public abstract T doInKeyspace(final Keyspace ks) throws HectorException;

  public ExecutionResult<T> doInKeyspaceAndMeasure(final Keyspace ks) {
    long start = System.nanoTime();
    T value = null;
    try {
      value = doInKeyspace(ks);
      return new ExecutionResult<T>(value, true, System.nanoTime() - start);
    } catch (HectorException e) {
      return new ExecutionResult<T>(value, false, System.nanoTime() - start);
    }    
  }

}
