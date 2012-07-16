package me.prettyprint.hector.api.locking;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.exceptions.HectorException;

/**
 * Entity responsible for managing locks acquisition.
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 * 
 */
public interface HLockManager {

  /**
   * Initializes the Lock Manager.
   */
  void init();

  /**
   * the Cluster this Lock Manager is running on
   * 
   * @return
   */
  Cluster getCluster();

  /**
   * 
   * @return The keyspace associated to this lock manager
   */
  Keyspace getKeyspace();

  /**
   * 
   * @return the configuration of this Lock Manager.
   */
  HLockManagerConfigurator getLockManagerConfigurator();

  /**
   * Acquires a lock represented by lock.getPath and filling up the lockId with
   * a unique client/caller lock id.
   * 
   * @param lock
   *          a lock object with path identifying the lock path to lock on
   * @throws HLockTimeoutException
   *           if the waiting time for acquiring a lot has elapsed
   * @throws HectorException
   *           if any other error has occurred
   */
  void acquire(HLock lock);

  /**
   * Releases the lock
   * 
   * @param lock
   *          a lock to release
   */
  void release(HLock lock);

  /**
   * Creates a lock instance to use later on acquire(HLock)
   * 
   * @param string
   * @return
   */
  HLock createLock(String string);
}
