package me.prettyprint.cassandra.model;

import java.util.Iterator;
import java.util.Map;

/**
 * Returned by a MultigetSliceQuery (multiget_slice thrift call)
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public class Rows<N,V> implements Iterable<Row<N,V>>{

  private Map<String, Row<N,V>> rows;

  public Row<N,V> getByKey(String key) {
    return rows.get(key);
  }

  public int getCount() {
    return rows.size();
  }

  @Override
  public Iterator<Row<N, V>> iterator() {
    // TODO Auto-generated method stub
    return null;
  }
}
