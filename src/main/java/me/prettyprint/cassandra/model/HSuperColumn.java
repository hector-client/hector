package me.prettyprint.cassandra.model;

import java.util.List;

/**
 * Models a SuperColumn in a protocol independant manner
 * 
 * @author zznate 
 */
public class HSuperColumn<SN,N,V> {
  
  private SN name;
  private List<HColumn<N,V>> columns;
  private long timestamp;
  private final Extractor<SN> nameExtractor;

  /**
  * @param <SN> SuperColumn name type
  * @param List<HColumn<N,V>> Column values
  * @param Extractor<SN> the extractor type
  * @param timestamp
  */
  public HSuperColumn(SN sName, List<HColumn<N, V>> columns, Extractor<SN> sNameExtractor, long timestamp) {
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
  //TODO add thrift convinience methods
}
