package me.prettyprint.cassandra.service.template;

import java.util.Iterator;

/**
 * Allows iteration of results from a query with the niceties that Hector provides for direct access.
 * 
 * @author david
 * @since Mar 10, 2011
 * @param <K>
 * @param <T>
 */
public interface ColumnFamilyResultsIterator<K, T> extends Iterator<T> {
  public T getByKey(K key);

  public int getCount();
}
