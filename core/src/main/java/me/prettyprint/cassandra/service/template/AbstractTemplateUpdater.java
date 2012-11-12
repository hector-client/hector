package me.prettyprint.cassandra.service.template;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.hector.api.ColumnFactory;
import me.prettyprint.hector.api.mutation.Mutator;

public abstract class AbstractTemplateUpdater<K,N> {

  protected static final int DEF_TTL = 0; 
  
  protected List<K> keys;
  protected int keyPos = 0;
  protected ColumnFactory columnFactory;
  protected AbstractColumnFamilyTemplate<K,N> template;
  protected Mutator<K> mutator;
  protected long clock;
  protected int globalTtl;   
  
  public AbstractTemplateUpdater(AbstractColumnFamilyTemplate<K, N> template, ColumnFactory columnFactory, Mutator<K> mutator) {
    this.template = template;
    this.columnFactory = columnFactory;
    this.mutator = mutator;
    this.clock = template.getClock();
    this.globalTtl = DEF_TTL; 
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

  /**
   * Reset the clock used for column creation. By default, we hold a reference to a single
   * clock value created at construction from {@link AbstractColumnFamilyTemplate#getClock()}
   *
   * Since updater implementations are instance-based (and thus should not be shared among threads),
   * call this as often as you need to apply the clock to the underlying columns. 
   *
   * @param clock
   */
  public void setClock(long clock) {
    this.clock = clock;
  }
  
  
  /**
   * Globally sets the ttl for this updater instance. Any columns created via the updater will use this ttl value unless a ttl is explicitly provided 
   * to one of the set* methods of subclasses. 
   * @param ttl
   */
  public void setTtl(int ttl) { 
    this.globalTtl = ttl; 
  }
}
