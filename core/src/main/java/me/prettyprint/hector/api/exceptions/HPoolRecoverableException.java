package me.prettyprint.hector.api.exceptions;


/**
 * Base exception for recoverable pool exceptions.
 *
 * @author Patricio Echague (patricioe@gmail.com)
 *
 */
public class HPoolRecoverableException extends HectorException {

  private static final long serialVersionUID = -4734247664375419943L;

  public HPoolRecoverableException(String msg) {
    super(msg);
  }

  public HPoolRecoverableException(Throwable t) {
    super(t);
  }
}
