package me.prettyprint.hector.api.exceptions;

/**
 * Base exception class for all Hector related exceptions.
 * 
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class HectorException extends RuntimeException {

  private static final long serialVersionUID = -8498691501268563571L;

  public HectorException(String msg) {
    super(msg);
  }
  
  public HectorException(Throwable t) {
    super(t);
  }

  public HectorException(String s, Throwable t) {
    super(s, t);
  }
}
