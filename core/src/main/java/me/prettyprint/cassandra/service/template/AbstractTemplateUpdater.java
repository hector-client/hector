package me.prettyprint.cassandra.service.template;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.hector.api.ColumnFactory;
import me.prettyprint.hector.api.mutation.Mutator;

public abstract class AbstractTemplateUpdater<K,N> {

  protected List<K> keys;
  protected int keyPos = 0;
  protected ColumnFactory columnFactory;
  protected AbstractColumnFamilyTemplate<K,N> template;
  protected Mutator<K> mutator;
  
  public AbstractTemplateUpdater(AbstractColumnFamilyTemplate<K, N> template, ColumnFactory columnFactory, Mutator<K> mutator) {
    this.template = template;
    this.columnFactory = columnFactory;
    this.mutator = mutator;
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
  
  public Mutator<K> getCurrentMutator() {
    return mutator;
  }
}
