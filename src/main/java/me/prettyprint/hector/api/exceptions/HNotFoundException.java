package me.prettyprint.hector.api.exceptions;



/**
 * @author Ran Tavory (rantav@gmail.com)
 */
public final class HNotFoundException extends HectorException {

  private static final long serialVersionUID = -8772138078816510007L;

  public HNotFoundException(String s) {
    super(s);
  }

  public HNotFoundException(Throwable t) {
    super(t);
  }
}
