package me.prettyprint.cassandra.model;

import java.util.List;

/**
 * 
 * @author Ran Tavory 
 */
public interface Mutator {

  // Simple and immediate insertion of a column
  <N,V> MutationResult insert(String key, String cf, HColumn<N,V> c);

  // overloaded insert-super
  <SN,N,V> MutationResult insert(String key, String cf, HSuperColumn<SN,N,V> superColumn);

  <N> MutationResult delete(String key, String cf, N columnName, Extractor<N> nameExtractor);

  // schedule an insertion to be executed in batch by the execute method
  // CAVEAT: a large number of calls with a typo in one of them will leave things in an 
  // indeterminant state if we dont validate against LIVE (but cached of course) 
  // keyspaces and CFs on each add/delete call
  // also, should throw a typed StatementValidationException or similar perhaps?
  <N,V> Mutator addInsertion(String key, String cf, HColumn<N,V> c);
  
  <N> Mutator addDeletion(String key, String cf, N columnName, Extractor<N> nameExtractor);


  /**
   * Batch executes all mutations scheduled to this Mutator instance by addInsertion, addDeletion etc.
   * May throw a HectorException which is a RuntimeException. 
   * @return A MutationResult holds the status.
   */
  MutationResult execute();

//  K getKeyExtractor();
//  
//  Mutator<K> setKeyExtractor(K extractor);
//  
  
  ////////////////////
  // Factory methods
  ///////////////////
  /**
   * createSuperColumn accepts a variable number of column arguments
   * @param name supercolumn name
   * @param createColumn a variable number of column arguments
   * @return
   */
  <SN,N,V> HSuperColumn<SN,N,V> createSuperColumn(N name, List<HColumn<N,V>> column, 
      Extractor<V> superNameExtractor, Extractor<N> nameExtractor, Extractor<V> valueExtractor);
  
  <N,V> HColumn<N,V> createColumn(N name, V value, Extractor<N> nameExtractor, 
      Extractor<V> valueExtractor);

}
