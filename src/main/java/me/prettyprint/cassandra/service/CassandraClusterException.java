package me.prettyprint.cassandra.service;

public class CassandraClusterException extends RuntimeException {
  private static final String DEF_MSG = 
    "There was a problem calling the meta data api: ";
  
  public CassandraClusterException() {
    super(DEF_MSG);
  }
  
  public CassandraClusterException(String msg) {
    super(DEF_MSG + msg);
  }
  
  public CassandraClusterException(String msg, Throwable t) {
    super(DEF_MSG + msg, t);
  }

}
