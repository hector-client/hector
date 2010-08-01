package me.prettyprint.cassandra.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.Column;

/**
 * Returned by a MultigetSliceQuery (multiget_slice thrift call)
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public class Rows<N, V> implements Iterable<Row<N, V>> {

  protected final Map<String, Row<N, V>> rows;

  public Rows(Map<String, List<Column>> thriftRet, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    Assert.noneNull(thriftRet, nameExtractor, valueExtractor);
    rows = new HashMap<String, Row<N, V>>(thriftRet.size());
    for (Map.Entry<String, List<Column>> entry : thriftRet.entrySet()) {
      rows.put(entry.getKey(), new Row<N, V>(entry.getKey(), entry.getValue(), nameExtractor,
          valueExtractor));
    }
  }

  public Row<N, V> getByKey(String key) {
    return rows.get(key);
  }

  public int getCount() {
    return rows.size();
  }

  @Override
  public Iterator<Row<N, V>> iterator() {
    return rows.values().iterator();
  }

  @Override
  public String toString() {
    return "Rows(" + rows + ")";
  }
}
