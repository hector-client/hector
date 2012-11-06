package me.prettyprint.cassandra.locking;

import java.util.concurrent.Future;

import me.prettyprint.hector.api.locking.HLock;
import me.prettyprint.hector.api.locking.HLockObserver;

/**
 * Default Lock implementation of {@link HLock}
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 * @author tnine (Todd Nine)
 * 
 */
public class HLockImpl implements HLock {

  private String lockPath;
  private String lockId;
  private boolean acquired = false;
  private HLockObserver observer;
  private Future<Void> heartbeat;

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

  @Override
  public void setObserver(HLockObserver observer) {
      this.observer = observer;
  }

  /**
   * @return the observer
   */
  public HLockObserver getObserver() {
      return observer;
  }
  
  /**
   * @return the heartbeat
   */
  public Future<Void> getHeartbeat() {
    return heartbeat;
  }

  /**
   * @param heartbeat the heartbeat to set
   */
  public void setHeartbeat(Future<Void> heartbeat) {
    this.heartbeat = heartbeat;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((lockId == null) ? 0 : lockId.hashCode());
    result = prime * result + ((lockPath == null) ? 0 : lockPath.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    HLockImpl other = (HLockImpl) obj;
    if (lockId == null) {
      if (other.lockId != null)
        return false;
    } else if (!lockId.equals(other.lockId))
      return false;
    if (lockPath == null) {
      if (other.lockPath != null)
        return false;
    } else if (!lockPath.equals(other.lockPath))
      return false;
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "HLockImpl [lockPath=" + lockPath + ", lockId=" + lockId + ", acquired=" + acquired + "]";
  }
  
  





  
  

}
