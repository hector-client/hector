package me.prettyprint.cassandra.service.template;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.hector.api.ColumnFactory;

public abstract class AbstractTemplateUpdater<K,N> {

  private List<K> keys;
  private int keyPos = 0;
  protected ColumnFactory columnFactory;
  protected AbstractColumnFamilyTemplate<K,N> template;
  
  public AbstractTemplateUpdater(AbstractColumnFamilyTemplate<K, N> template, ColumnFactory columnFactory) {
    this.template = template;
    this.columnFactory = columnFactory;
  }
  
  public AbstractTemplateUpdater<K,N> addKey(K key) {
    if ( keys == null ) {
      keys = new ArrayList<K>();      
    } else {
      keyPos++;
    }
    keys.add(key);
    
    return this;
  }
  
  /**
   * @return Give the updater access to the current key if it needs it
   */
  public K getCurrentKey() {
    return keys.get(keyPos);
  }
  
  /**
   * To be overridden by folks choosing to add their own functionality. Default is a no-op.
   */
  public void update() {
    
  }
}
