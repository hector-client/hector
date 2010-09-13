package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.hector.api.exceptions.HectorException;

/**
 * A callback template used by the package classes. Not for external use.
 *
 * @author Ran Tavory
 *
 * @param <T>
 */
public abstract class KeyspaceOperationCallback<T> {

  public abstract T doInKeyspace(final Keyspace ks) throws HectorException;

  public ExecutionResult<T> doInKeyspaceAndMeasure(final Keyspace ks) {
    long start = System.nanoTime();
    T value = null;
    value = doInKeyspace(ks);

    return new ExecutionResult<T>(value, System.nanoTime() - start, ks.getClient().getCassandraHost());
  }

}
