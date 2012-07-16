package me.prettyprint.hector.api.locking;

import me.prettyprint.hector.api.exceptions.HectorException;


/**
 * The waiting time for a lock has expired.
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 *
 */
public class HLockTimeoutException extends HectorException {

  public HLockTimeoutException(String msg) {
    super(msg);
    // TODO Auto-generated constructor stub
  }

}
