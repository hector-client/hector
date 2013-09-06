package me.prettyprint.hector.api.exceptions;

import me.prettyprint.cassandra.service.CassandraHost;

/**
 * Base exception class for all Hector related exceptions.
 * 
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class HectorException extends RuntimeException {

  private static final long serialVersionUID = -8498691501268563571L;

  private CassandraHost host;

  public HectorException(String msg) {
    super(msg);
  }
  
  public HectorException(Throwable t) {
    super(t);
  }

  public HectorException(String s, Throwable t) {
    super(s, t);
  }

  public CassandraHost getHost() {
    return host;
  }

  public void setHost(CassandraHost host) {
    this.host = host;
  }

  @Override
  public String getMessage() {
    if (host != null) {
      return "[" + host.toString() + "] " + super.getMessage();
    } else {
      return super.getMessage();
    }
  }
}
