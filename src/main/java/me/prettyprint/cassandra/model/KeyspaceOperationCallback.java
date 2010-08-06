package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;

/*package*/ abstract class KeyspaceOperationCallback<T> {

  public abstract T doInKeyspace(final Keyspace ks) throws HectorException;

  public ExecutionResult<T> doInKeyspaceAndMeasure(final Keyspace ks) {
    long start = System.nanoTime();
    T value = null;
    value = doInKeyspace(ks);
    return new ExecutionResult<T>(value, System.nanoTime() - start);
  }

}
