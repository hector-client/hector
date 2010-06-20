package me.prettyprint.cassandra.model;

public interface Mutator {

  // Simple and immediate insertion of a column
  // +1 nate
  MutationResult insert(String row, String cf, Column c);

  // overloaded insert-super
  MutationResult insert(String row, String cf, SuperColumn createSuperColumn);

  MutationResult delete(String row, String cf, Object columnName);

  // schedule an insertion to be executed in batch by the execute method
  // CAVEAT: a large number of calls with a typo in one of them will leave things in an 
  // indeterminant state if we dont validate against LIVE (but cached of course) 
  // keyspaces and CFs on each add/delete call
  // also, should throw a typed StatementValidationException or similar perhaps?
  Mutator addInsertion(String row, String cf, Column c);
  
  Mutator addDeletion(String row, String cf, Object columnName);


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
  SuperColumn createSuperColumn(Object name, Column... column);
  Column createColumn(Object name, Object value);



}
