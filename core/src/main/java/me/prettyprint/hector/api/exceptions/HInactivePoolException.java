package me.prettyprint.hector.api.exceptions;


/**
 * Indicates that a client pool has been suspended.
 *
 * @author Patricio Echague (patricioe@gmail.com)
 *
 */
public final class HInactivePoolException extends HPoolRecoverableException {

  private static final long serialVersionUID = 7300705379434905077L;

  public HInactivePoolException(String msg) {
    super(msg);
  }

  public HInactivePoolException(Throwable t) {
    super(t);
  }
}
