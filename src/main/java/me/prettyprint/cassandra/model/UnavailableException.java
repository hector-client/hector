package me.prettyprint.cassandra.model;

/**
 * @author Ran Tavory (rantav@gmail.com)
 */
public class UnavailableException extends HectorException {

  private static final long serialVersionUID = 1971498442136497970L;

  public UnavailableException(String s) {
    super(s);
  }

  public UnavailableException(Throwable t) {
    super(t);
  }
}
