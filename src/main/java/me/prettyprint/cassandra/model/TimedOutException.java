package me.prettyprint.cassandra.model;

/**
 * @author Ran Tavory (rantav@gmail.com)
 */
public class TimedOutException extends HectorException {

  private static final long serialVersionUID = 6830964658496659923L;

  public TimedOutException(String s) {
    super(s);
  }
  public TimedOutException(String s, Throwable t) {
    super(s, t);
  }
  public TimedOutException(Throwable t) {
    super(t);
  }
}
