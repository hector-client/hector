package me.prettyprint.hector.api.exceptions;


/**
 * @author Ran Tavory (rantav@gmail.com)
 */
public final class HUnavailableException extends HectorException {

  private static final long serialVersionUID = 1971498442136497970L;

  public HUnavailableException(String s) {
    super(s);
  }

  public HUnavailableException(Throwable t) {
    super(t);
  }
}
