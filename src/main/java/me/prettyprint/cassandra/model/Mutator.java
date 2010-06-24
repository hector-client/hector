package me.prettyprint.cassandra.model;

import java.util.List;

public interface Mutator<N,V> {

  // Simple and immediate insertion of a column
  MutationResult insert(String row, String cf, HColumn<N,V> c);

  // overloaded insert-super
  <SN> MutationResult insert(String row, String cf, HSuperColumn<SN,N,V> superColumn);

  MutationResult delete(String row, String cf, N columnName);

  // schedule an insertion to be executed in batch by the execute method
  // CAVEAT: a large number of calls with a typo in one of them will leave things in an 
  // indeterminant state if we dont validate against LIVE (but cached of course) 
  // keyspaces and CFs on each add/delete call
  // also, should throw a typed StatementValidationException or similar perhaps?
  Mutator<N,V> addInsertion(String row, String cf, HColumn<N,V> c);
  
  Mutator<N,V> addDeletion(String row, String cf, N columnName);


  /**
   * Batch executes all mutations scheduled to this Mutator instance by addInsertion, addDeletion etc.
   * May throw a HectorException which is a RuntimeException. 
   * @return A MutationResult holds the status.
   */
  MutationResult execute();

  
  
  ////////////////////
  // Factory methods
  ///////////////////
  /**
   * createSuperColumn accepts a variable number of column arguments
   * @param name supercolumn name
   * @param createColumn a variable number of column arguments
   * @return
   */
  <SN> HSuperColumn<SN,N,V> createSuperColumn(N name, List<HColumn<N,V>> column);
  
  HColumn<N,V> createColumn(N name, V value);

}
