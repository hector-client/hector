package me.prettyprint.cassandra.model;

import java.util.List;

// get
/**
 * @param <SN> SuperColumn name type
 * @param <N> Column name type
 * @param <V> Column value type
 */
public class HSuperColumn<SN,N,V> {

  private List<HColumn<N,V>> columns;
  
  public int getSize() {
    return columns.size();
  }
  
  public SN getName() {
    return null;
  }
  
  public List<HColumn<N,V>> getColumns() {
    return columns;
  }
  
  public HColumn<N,V> get(int i) {
    return columns.get(i);
  }
  
}
