package me.prettyprint.cassandra.model;

/**
 * Describes the state of the executed cassandra command.
 * This is a handy call metadata inspector which reports the call's execution time, status, 
 * which actual host was connected etc.
 * 
 * @author Ran 
 *
 */
public interface ExecutionResult {

  boolean isSuccess();
  
  /** The cassandra host that was actually used to execute the operation */
  String getHostUsed();
  
  /** Overall execution time in milisec */
  long getExecutionTimeMili();
  
  // Rest of meta methods here
}
