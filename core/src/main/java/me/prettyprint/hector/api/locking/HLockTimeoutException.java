package me.prettyprint.hector.api.locking;

import me.prettyprint.hector.api.exceptions.HectorException;


/**
 * The waiting time for a lock has expired.
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 *
 */
public class HLockTimeoutException extends HectorException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public HLockTimeoutException(String msg) {
    super(msg);
  }

}
