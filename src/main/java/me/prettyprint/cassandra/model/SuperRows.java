package me.prettyprint.cassandra.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.SuperColumn;

/**
 * Returned by a MultigetSuperSliceQuery (multiget_slice for supercolumns)
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public class SuperRows<SN, N, V> implements Iterable<SuperRow<SN, N, V>> {

  private final Map<byte[], SuperRow<SN, N, V>> rows;

  public SuperRows(Map<byte[], List<SuperColumn>> thriftRet, Extractor<SN> sNameExtractor,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    Assert.noneNull(thriftRet, sNameExtractor, nameExtractor, valueExtractor);
    rows = new HashMap<byte[], SuperRow<SN, N, V>>(thriftRet.size());
    for (Map.Entry<byte[], List<SuperColumn>> entry : thriftRet.entrySet()) {
      rows.put(entry.getKey(), new SuperRow<SN, N, V>(entry.getKey(), entry.getValue(),
          sNameExtractor, nameExtractor, valueExtractor));
    }
  }

  public SuperRow<SN, N, V> getByKey(String key) {
    return rows.get(key);
  }

  public int getCount() {
    return rows.size();
  }

  public Iterator<SuperRow<SN, N, V>> iterator() {
    return rows.values().iterator();
  }

  @Override
  public String toString() {
    return "SuperRows(" + rows + ")";
  }
}
