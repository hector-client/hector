package me.prettyprint.cassandra.locking;

import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HLockManagerImplTest extends BaseEmbededServerSetupTest {

  private static final Logger logger = LoggerFactory.getLogger(HLockManagerImplTest.class);

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

    // use a different lock manager to try and get the lock, should succeed

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
  
  @Test
  public void testNoConflict() throws InterruptedException {
    LockWorkerPool pool = new LockWorkerPool(1000, lm);
    pool.runEm("/testNoConflict");
    
    
  }
  
 

  private boolean verifyCFCreation(List<ColumnFamilyDefinition> cfDefs) {
    for (ColumnFamilyDefinition cfDef : cfDefs) {
      if (cfDef.getName().equals(HLockManagerConfigurator.DEFAUT_LOCK_MANAGER_CF))
        return true;
    }
    return false;
  }

  private static class LockWorkerPool {
    private int numberLocks;
    private Executor executor;
    private CountDownLatch startLatch;
    private CountDownLatch finishLatch;
    private HLockManager lm;

    
    private LockWorkerPool(int numberLocks, HLockManager lm) {
      this.numberLocks = numberLocks;
      this.lm = lm;
      this.executor = Executors.newFixedThreadPool(8);
      startLatch = new CountDownLatch(1);
      finishLatch = new CountDownLatch(numberLocks);
    }

    private void runEm(String lockPath) throws InterruptedException {

      // fire up the executors so they're running and blocking
      for (int i = 0; i < numberLocks; i++) {
        executor.execute(new LockWorker(lockPath, lm, startLatch, finishLatch));
      }

      // now release the latch
      startLatch.countDown();

      // wait for workers to finish
      finishLatch.await();

    }
    
    

  }

  private static class LockWorker implements Runnable {
    private String path;
    private HLockManager lm;
    private CountDownLatch startLatch;
    private CountDownLatch finishLatch;

    /**
     * @param path
     * @param lm
     */
    public LockWorker(String path, HLockManager lm, CountDownLatch startLatch, CountDownLatch finishLatch) {
      super();
      this.path = path;
      this.lm = lm;
      this.startLatch = startLatch;
      this.finishLatch = finishLatch;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
      HLock lock = lm.createLock(path);

      // sync up all threads to wait to acquire lock at the same time
      try {
        startLatch.await();
      } catch (InterruptedException e) {
      }

      // get our lock
      lm.acquire(lock);

      logger.info("Acquired lock {}", lock);
      

      // release the lock
      lm.release(lock);

      finishLatch.countDown();

    }

  }
}
