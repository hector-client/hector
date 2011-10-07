package me.prettyprint.hector.api.exceptions;


/**
 * Indicates that a client pool has been exhausted.
 *
 * @author Ran Tavory (ran@outbain.com)
 * @author Patricio Echague (patricioe@gmail.com)
 *
 */
public final class HPoolExhaustedException extends HPoolRecoverableException {

  private static final long serialVersionUID = -6200999597951673383L;

  public HPoolExhaustedException(String msg) {
    super(msg);
  }

  public HPoolExhaustedException(Throwable t) {
    super(t);
  }
}
