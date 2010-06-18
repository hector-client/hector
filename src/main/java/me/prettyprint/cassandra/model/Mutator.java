package me.prettyprint.cassandra.model;

public interface Mutator {

  // Simple and immediate insertion of a column
  void insert(String row, String cf, Column c);

  void delete (String row, String cf, Object columnName);

  // schedule an insertion to be executed in batch by the execute method
  Mutator addInsertion(String row, String cf, Column c);
  
  Mutator addDeletion(String row, String cf, Object columnName);

  Column createColumn(Object name, Object value);

  void execute();


}
