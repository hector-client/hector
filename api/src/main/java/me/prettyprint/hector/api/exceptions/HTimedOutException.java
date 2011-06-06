package me.prettyprint.hector.api.exceptions;


/**
 * @author Ran Tavory (rantav@gmail.com)
 */
public final class HTimedOutException extends HectorException {

  private static final long serialVersionUID = 6830964658496659923L;

  public HTimedOutException(String s) {
    super(s);
  }
  public HTimedOutException(String s, Throwable t) {
    super(s, t);
  }
  public HTimedOutException(Throwable t) {
    super(t);
  }
}
