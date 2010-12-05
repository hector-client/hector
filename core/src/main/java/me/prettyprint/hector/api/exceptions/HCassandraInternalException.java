package me.prettyprint.hector.api.exceptions;

/**
 * Designed to loosely wrap TApplicationException which can be generated
 * by Apache Cassandra under a variety of ambiguous conditions - some of
 * them transient, some of them not. 
 *
 * @author zznate
 */
public class HCassandraInternalException extends HectorException {

  private static final long serialVersionUID = -266109391311421129L;
  
  private int type;
  
  private static final String ERR_MSG = 
    "Cassandra encountered an internal error processing this request: ";
  
  public HCassandraInternalException(String msg) {
    super(ERR_MSG + msg); 
  }

  public HCassandraInternalException(int type, String msg) {
    super(ERR_MSG + "TApplicationError type: " + type + " message:" + msg);
    this.type = type;
  }

  public HCassandraInternalException(String s, Throwable t) {
    super(ERR_MSG + s, t); 
  }

  public HCassandraInternalException(Throwable t) {
    super(t); 
  }

  /**
   * The underlying 'type' directly from TApplicationException#getType
   * @return
   */
  public int getType() {
    return type;
  }
 
 

}
