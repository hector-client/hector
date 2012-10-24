package me.prettyprint.hector.api.locking;

/**
 * A Lock whose path refers to a common path/id clients compete to acquire. Fro
 * example:
 * 
 * Lock("/Users/patricioe")
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 * @author tnine (Todd Nine)
 * 
 */
public interface HLock {

  /**
   * @return the lock path shared among all client that refer to this lock. I.e:
   *         /Users/patricioe
   */
  String getPath();

  /**
   * Sets the lock path for this lock.
   * 
   * @param lockPath
   *          the lock path i.e.: /Users/patricioe or
   *          "aLockPathAllClientsWillAgreeOn"
   */
  void setPath(String lockPath);

  /**
   * @return the lock id assigned to this lock after this lock is placed
   */
  String getLockId();

  /**
   * Sets the lock id (UID) that identifies uniquely this lock object
   * 
   * @param lockId
   *          unique lock id that identifies uniquely a client. This is for
   *          internal usage only. Users don't and should not call this setter.
   */
  void setLockId(String lockId);

  /**
   * 
   * @return whether this lock as been successfully acquired or not
   */
  boolean isAcquired();
  
  /**
   * Set the observer to receive events 
   * @param observer
   */
  void setObserver(HLockObserver observer);
  
  /**
   * Get the observer for this lock
   * @return
   */
  HLockObserver getObserver();

}
