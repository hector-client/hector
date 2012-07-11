package me.prettyprint.hector.api.exceptions;


/**
 * @author Ran Tavory (rantav@gmail.com)
 */
public final class HUnavailableException extends HectorException {

  private static final String ERR_MSG = 
    ": May not be enough replicas present to handle consistency level.";
  private static final long serialVersionUID = 1971498442136497970L;

  public HUnavailableException(String s) {
    super(s + ERR_MSG);
  }

  public HUnavailableException(Throwable t) {
    super(ERR_MSG,t);
  }
}
