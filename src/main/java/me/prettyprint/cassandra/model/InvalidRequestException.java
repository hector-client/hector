package me.prettyprint.cassandra.model;

/**
 * @author Ran Tavory (rantav@gmail.com)
 */
public class InvalidRequestException extends HectorException {

  private static final long serialVersionUID = 7186392651338685069L;

  private String why;
  
  public InvalidRequestException(String msg) {
    super(msg);
    setWhy(msg);
  }
  public InvalidRequestException(Throwable t) {
    super(t);
  }
  
  public void setWhy(String w) {
    why = w;
  }
  
  public String getWhy() {
    return why;
  }
}
