package me.prettyprint.cassandra.locking;

import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.*;

import java.util.List;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.locking.HLock;
import me.prettyprint.hector.api.locking.HLockManager;
import me.prettyprint.hector.api.locking.HLockManagerConfigurator;
import me.prettyprint.hector.api.locking.HLockTimeoutException;

import org.junit.Before;
import org.junit.Test;

public class HLockManagerImplTest extends BaseEmbededServerSetupTest {

  Cluster cluster;
  HLockManager lm;
  HLockManagerConfigurator hlc;

  @Before
  public void setupTest() {
    cluster = getOrCreateCluster("MyCluster", getCHCForTest());
    hlc = new HLockManagerConfigurator();
    hlc.setReplicationFactor(1);
    lm = new HLockManagerImpl(cluster, hlc);
    lm.init();
  }

  @Test
  public void testInitWithDefaults() {

    KeyspaceDefinition keyspaceDef = cluster.describeKeyspace(lm.getKeyspace().getKeyspaceName());
    assertNotNull(keyspaceDef);
    assertTrue(verifyCFCreation(keyspaceDef.getCfDefs()));
  }

  @Test
  public void testHeartbeatNoExpiration() throws InterruptedException {
    HLock lock = lm.createLock("/testHeartbeatNoExpiration");
    lm.acquire(lock);

    assertTrue(lock.isAcquired());

    // Force timeout if heartbeat isn't working
    Thread.sleep(hlc.getLocksTTLInMillis() + 2000);

    HLock newLock = lm.createLock("/testHeartbeatNoExpiration");

    boolean lockTimedOut = false;

    try {
      lm.acquire(newLock, 0);
    } catch (HLockTimeoutException te) {
      lockTimedOut = true;
    }

    assertTrue(lock.isAcquired());
    assertFalse(newLock.isAcquired());
    assertTrue(lockTimedOut);

  }

  @Test
  public void testHeartbeatFailure() throws InterruptedException {

    HLockManagerImpl failedLockManager = new HLockManagerImpl(cluster, hlc);
    failedLockManager.init();

    HLock lock = failedLockManager.createLock("/testHeartbeatFailure");
    failedLockManager.acquire(lock);

    assertTrue(lock.isAcquired());

    failedLockManager.shutdownScheduler();

    // Force timeout since heartbeat isn't working
    Thread.sleep(hlc.getLocksTTLInMillis() + 2000);
    
    //use a different lock manager to try and get the lock, should succeed

    HLock newLock = lm.createLock("/testHeartbeatFailure");

    boolean lockTimedOut = false;

    try {
      lm.acquire(newLock, 0);
    } catch (HLockTimeoutException te) {
      lockTimedOut = true;
    }

    assertFalse(lockTimedOut);
    assertTrue(newLock.isAcquired());

  }

  @Test
  public void testNonConcurrentLockUnlock() {
    HLock lock = lm.createLock("/Users/patricioe");
    lm.acquire(lock);

    assertTrue(lock.isAcquired());

    try {
      HLock lock2 = lm.createLock("/Users/patricioe");
      lm.acquire(lock2, 1000);
      fail();
    } catch (HLockTimeoutException e) {
      // ok, this should happen
    }

    lm.release(lock);

    assertFalse(lock.isAcquired());

    // test we can re-acquire it
    HLock nextLock = lm.createLock("/Users/patricioe");
    lm.acquire(nextLock, 0);
    assertTrue(nextLock.isAcquired());
  }

  private boolean verifyCFCreation(List<ColumnFamilyDefinition> cfDefs) {
    for (ColumnFamilyDefinition cfDef : cfDefs) {
      if (cfDef.getName().equals(HLockManagerConfigurator.DEFAUT_LOCK_MANAGER_CF))
        return true;
    }
    return false;
  }

}