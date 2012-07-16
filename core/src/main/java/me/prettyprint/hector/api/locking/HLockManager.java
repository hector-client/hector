package me.prettyprint.hector.api.locking;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;

/**
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 *
 */
public interface HLockManager {

  void init();

  Cluster getCluster();

  Keyspace getKeyspace();

  HLockManagerConfigurator getLockManagerConfigurator();

  void acquire(HLock lock);

  void release(HLock lock);
}
