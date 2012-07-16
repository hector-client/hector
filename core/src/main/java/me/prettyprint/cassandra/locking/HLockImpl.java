package me.prettyprint.cassandra.locking;

import me.prettyprint.hector.api.locking.HLock;

/**
 * Default Lock implementation of {@link HLock}
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 * 
 */
public class HLockImpl implements HLock {

  private String lockPath;
  private String lockId;
  private boolean acquired = false;

  public HLockImpl(String lockPath, String lockId) {
    this.lockPath = lockPath;
    this.lockId = lockId;
  }

  @Override
  public String getPath() {
    return lockPath;
  }

  @Override
  public void setPath(String lockPath) {
    this.lockPath = lockPath;
  }

  @Override
  public String getLockId() {
    return lockId;
  }

  @Override
  public void setLockId(String lockId) {
    this.lockId = lockId;
  }

  @Override
  public boolean isAcquired() {

    return acquired;
  }

  public void setAcquired(boolean acquired) {
    this.acquired = acquired;
  }

}
