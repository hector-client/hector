package me.prettyprint.cassandra.model;

public interface Mutator {

  // Simple and immediate insertion of a column
  // +1 nate
  ExecutionResult insert(String row, String cf, Column c);

  ExecutionResult delete(String row, String cf, Object columnName);

  // schedule an insertion to be executed in batch by the execute method
  // CAVEAT: a large number of calls with a typo in one of them will leave things in an 
  // indeterminant state if we dont validate against LIVE (but cached of course) 
  // keyspaces and CFs on each add/delete call
  // also, should throw a typed StatementValidationException or similar perhaps?
  Mutator addInsertion(String row, String cf, Column c);
  
  Mutator addDeletion(String row, String cf, Object columnName);

  Column createColumn(Object name, Object value);

  ExecutionResult execute();


}
