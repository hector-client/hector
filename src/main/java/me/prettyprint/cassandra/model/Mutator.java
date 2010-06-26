package me.prettyprint.cassandra.model;


/**
 * A Mutator inserts or deltes values from the cluster. 
 * There are two main ways to use a mutator:
 * 1. Use the insert/delete methods to immediately insert of delete values. 
 * or 2. Use the addInsertion/addDeletion methods to schedule batch operations and then execute() 
 * all of them in batch.
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
}
