package me.prettyprint.cassandra.model;

import java.util.List;

// get
/**
 * @param <SN> SuperColumn name type
 * @param <N> Column name type
 * @param <V> Column value type
 */
public class HSuperColumn<SN,N,V> {
  
  private SN name;
  private List<HColumn<N,V>> columns;
  private long timestamp;
  private final Extractor<SN> nameExtractor;
  
  public HSuperColumn(SN sName, Extractor<SN> sNameExtractor, List<HColumn<N, V>> columns, long timestamp) {
    this.name = sName;
    this.nameExtractor = sNameExtractor;
    this.columns = columns;
    this.timestamp = timestamp;
  }
  
  public HSuperColumn<SN, N, V> setName(SN name) {
    this.name = name;
    return this;
  }
  
  public HSuperColumn<SN, N, V> setValues(List<HColumn<N, V>> values) {
    this.columns = values;
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
    return columns.size();
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
    return nameExtractor;
  }

  public byte[] getNameBytes() {
    return nameExtractor.toBytes(getName());
  }
  
}
