package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.notNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.SuperColumn;

/**
 * Models a SuperColumn in a protocol independant manner
 * 
 * @param <SN>
 *          SuperColumn name type
 * @param <N>
 *          Column name type
 * @param <V>
 *          Column value type
 * 
 * @author zznate
 */
public class HSuperColumn<SN,N,V> {

  private SN name;
  private List<HColumn<N,V>> columns;
  private long timestamp;
  private final Extractor<SN> superNameExtractor;

  /**
   * @param <SN> SuperColumn name type
   * @param List<HColumn<N,V>> Column values
   * @param Extractor<SN> the extractor type
   * @param timestamp
   */
  /*package*/HSuperColumn(SN sName, List<HColumn<N, V>> columns, Extractor<SN> sNameExtractor,
      long timestamp) {
    notNull(sName, "Name is null");
    notNull(columns, "Columns are null");
    notNull(sNameExtractor, "name extractor is null");
    this.name = sName;
    this.superNameExtractor = sNameExtractor;
    this.columns = columns;
    this.timestamp = timestamp;
  }

  public HSuperColumn<SN, N, V> setName(SN name) {
    notNull(name, "name is null");
    this.name = name;
    return this;
  }

  public HSuperColumn<SN, N, V> setSubcolumns(List<HColumn<N, V>> subcolumns) {
    notNull(subcolumns, "subcolumns are null");
    this.columns = subcolumns;
    return this;
  }

  public HSuperColumn<SN, N, V> setTimestamp(long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public int getSize() {
    return columns == null ? 0 : columns.size();
  }

  public SN getName() {
    return name;
  }

  public List<HColumn<N,V>> getColumns() {
    return columns;
  }

  public HColumn<N,V> get(int i) {
    return columns.get(i);
  }

  public Extractor<SN> getNameExtractor() {
    return superNameExtractor;
  }

  public byte[] getNameBytes() {
    return superNameExtractor.toBytes(getName());
  }

  public SuperColumn toThrift() {
    if (name == null || columns == null) {
      return null;
    }
    return new SuperColumn(superNameExtractor.toBytes(name), toThriftColumn());
  }

  private List<Column> toThriftColumn() {
    List<Column> ret = new ArrayList<Column>(columns.size());
    for (HColumn<N, V> c: columns) {
      ret.add(c.toThrift());
    }
    return ret;
  }
}
