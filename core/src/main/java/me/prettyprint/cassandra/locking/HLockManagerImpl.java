package me.prettyprint.cassandra.locking;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.locking.HLock;
import me.prettyprint.hector.api.locking.HLockManagerConfigurator;
import me.prettyprint.hector.api.locking.HLockTimeoutException;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Wait Chain implementation created by Dominic Williams, reviewed by Aaron
 * Morton and Patricio Echague.
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 * @author tnine (Todd Nine)
 * 
 */
public class HLockManagerImpl extends AbstractLockManager {

  private static final Logger logger = LoggerFactory.getLogger(HLockManagerImpl.class);

  private ScheduledExecutorService scheduler;
  private long lockTtl = 5000;
  private int colTtl = 5;
  private int maxSelectSize = 10;
  
  public HLockManagerImpl(Cluster cluster, HLockManagerConfigurator hlc) {
    super(cluster, hlc);
    scheduler = Executors.newScheduledThreadPool(lockManagerConfigurator.getNumberOfLockObserverThreads());
    lockTtl = lockManagerConfigurator.getLocksTTLInMillis();
    colTtl = (int) (lockTtl / 1000);
    maxSelectSize = hlc.getMaxSelectSize();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * me.prettyprint.hector.api.locking.HLockManager#acquire(me.prettyprint.hector
   * .api.locking.HLock)
   */
  @Override
  public void acquire(HLock lock) {
    acquire(lock, Long.MAX_VALUE - System.currentTimeMillis() - 10000);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void acquire(HLock lock, long timeout) {
    verifyPrecondition(lock);

    // Generate the internal lock id (CLID)
    maybeSetInternalLockId(lock);

    writeLock(lock);

    // Pairs of type <LockId, CommandSeparatedSeenLockIds>
    Map<String, String> canBeEarlier = readExistingLocks(lock);

    String nextWaitingClientId = null;
    long waitStart = System.currentTimeMillis();

    while (true) {

      // If it is just me...
      if (canBeEarlier.size() <= 1) {
        setAcquired(lock, canBeEarlier);
        return;
      }

      // We can't get the lock, and we've timed out, give up
      if (waitStart + timeout < System.currentTimeMillis()) {
        deleteLock(lock);
        throw new HLockTimeoutException(String.format("Unable to get lock before %d ", waitStart + timeout));

      }

      boolean recv_all_acks = true;

      // Let's see of other nodes know me
      for (Entry<String, String> otherLock : canBeEarlier.entrySet()) {

        if (!lock.getLockId().equals(otherLock.getKey()) && !hasThisLockSeenMe(otherLock.getValue(), lock.getLockId())) {
          recv_all_acks = false;
          break;
        }
      }

      List<String> canBeEarlierSortedList = null;

      // If everyone acknowledged to have seen me then ...
      if (recv_all_acks) {
        canBeEarlierSortedList = Lists.newArrayList(canBeEarlier.keySet());
        // sort them
        Collections.sort(canBeEarlierSortedList);

        nextWaitingClientId = canBeEarlierSortedList.get(0);

        // check if we are the first ones
        if (nextWaitingClientId.equals(lock.getLockId())) {
          break;
        }
      }

      // Let everyone know what I have already seen
      writeLock(lock, canBeEarlier.keySet());

      smartWait(lockManagerConfigurator.getBackOffRetryDelayInMillis());

      // Refresh the list, but only read locks we read at our initial acquire
      // for optimization
      canBeEarlier = readExistingLocks(lock);
    }

    if (logger.isDebugEnabled()) {
      logLock(lock, canBeEarlier.keySet());
    }

    setAcquired(lock, canBeEarlier);
  }

  /**
   * Start the heartbeat thread before we return
   * 
   * @param lock
   */
  private void setAcquired(HLock lock, Map<String, String> canBeEarlier) {
    // start the heartbeat
    Future<Void> heartbeat = scheduler.schedule(new Heartbeat(lock), lockTtl / 2, TimeUnit.MILLISECONDS);

    ((HLockImpl) lock).setHeartbeat(heartbeat);

    ((HLockImpl) lock).setAcquired(true);

    if (logger.isDebugEnabled()) {
      logLock(lock, canBeEarlier.keySet());
    }

  }

  private static void logLock(HLock lock, Set<String> earlier) {
    List<String> canBeEarlierSortedList = Lists.newArrayList(earlier);
    // sort them
    Collections.sort(canBeEarlierSortedList);

    String peers = Joiner.on(", ").join(canBeEarlierSortedList);
    logger.debug("{} acquired lock.  Peers are {}", lock, peers);
  }

  /**
   * Here for testing purposes only, this should never really be invoked
   */
  public void shutdownScheduler() {
    scheduler.shutdownNow();
  }

  private void smartWait(long sleepTime) {
    try {
      Thread.sleep((sleepTime + (long) (Math.random() * sleepTime)));
    } catch (InterruptedException e) {
      // throw new RuntimeException();
      // swallow, we woke up early, not worth re-throwing an exception
      logger.warn("Interrupted while waiting", e);
    }
  }

  private boolean hasThisLockSeenMe(String commaSeparatedLockIds, String myLockId) {

    String[] seenLocksIds = commaSeparatedLockIds.split(",");

    for (int i = 0; i < seenLocksIds.length; i++) {
      if (seenLocksIds[i].equals(myLockId))
        return true;
    }
    return false;
  }

  /**
   * Fill up the lock id info if it does not exist.
   * 
   * @param lock
   *          the lock object to fill up with a new generated lock id for this
   *          client/thread
   */
  private void maybeSetInternalLockId(HLock lock) {
    if (lock.getLockId() == null) {
      lock.setLockId(generateLockId());
    }
  }

  @Override
  public void release(HLock lock) {
    verifyPrecondition(lock);
    deleteLock(lock);
    ((HLockImpl) lock).setAcquired(false);
  }

  /**
   * Generates a CLID (Client Lock ID)
   * 
   */
  private String generateLockId() {
    return UUID.randomUUID().toString();
  }

  private void verifyPrecondition(HLock lock) {
    assert lock != null;

    if (lock.getPath() == null)
      throw new RuntimeException("Lock path cannot be null");

  }

  private void writeLock(HLock lock) {
    writeLock(lock, lock.getLockId().toString());
  }

  private void writeLock(HLock lock, Set<String> keySet) {
    String seenLockIds = Joiner.on(",").join(keySet);
    writeLock(lock, seenLockIds);
  }

  private void writeLock(HLock lock, String seenLockIds) {
    Mutator<String> mutator = createMutator(keyspace, StringSerializer.get());
    mutator.addInsertion(lock.getPath(), lockManagerConfigurator.getLockManagerCF(),
        createColumnForLock(lock.getLockId(), seenLockIds));
    mutator.execute();
  }

  private void deleteLock(HLock lock) {
    // cancel the heartbeat task if it exists
    Future<Void> heartbeat = ((HLockImpl) lock).getHeartbeat();

    if (heartbeat != null) {
      heartbeat.cancel(false);
    }

    Mutator<String> mutator = createMutator(keyspace, StringSerializer.get());
    mutator.addDeletion(lock.getPath(), lockManagerConfigurator.getLockManagerCF(), lock.getLockId(),
        StringSerializer.get(), keyspace.createClock());
    mutator.execute();

  }

  /**
   * Reads all existing locks for this lock path
   * 
   * @param lockPath
   *          a lock path
   * @return a list of locks waiting on this lockpath
   */
  private Map<String, String> readExistingLocks(HLock lock) {
    // logger.debug("Started reading all columns");
    SliceQuery<String, String, String> sliceQuery = HFactory
        .createSliceQuery(keyspace, StringSerializer.get(), StringSerializer.get(), StringSerializer.get())
        .setColumnFamily(lockManagerConfigurator.getLockManagerCF()).setKey(lock.getPath());

    //we only care about the first 2 locks, anything else is simply queued.  Select 10 just to be safe if the clients aren't ordered properly
    sliceQuery.setRange(null, null, false, maxSelectSize);

    QueryResult<ColumnSlice<String, String>> queryResult = sliceQuery.execute();

    // logger.debug("Finished reading all columns");
    return getResults(queryResult);
  }

  /**
   * Reads all existing locks for this lock path
   * 
   * @param lockPath
   *          a lock path
   * @return a list of locks waiting on this lockpath
   */
  private Map<String, String> readExistingLocks(HLock lock, String lockName) {
    // logger.debug("Started reading existing columns");
    SliceQuery<String, String, String> sliceQuery = HFactory
        .createSliceQuery(keyspace, StringSerializer.get(), StringSerializer.get(), StringSerializer.get())
        .setColumnFamily(lockManagerConfigurator.getLockManagerCF()).setKey(lock.getPath());

    sliceQuery.setColumnNames(lockName);

    QueryResult<ColumnSlice<String, String>> queryResult = sliceQuery.execute();

    // logger.debug("Finished reading existing columns");
    return getResults(queryResult);

  }

  private Map<String, String> getResults(QueryResult<ColumnSlice<String, String>> queryResult) {
    Map<String, String> result = Maps.newHashMap();

    for (HColumn<String, String> col : queryResult.get().getColumns()) {
      result.put(col.getName(), col.getValue());
    }

    return result;
  }

  private HColumn<String, String> createColumnForLock(String name, String value) {
    return createColumn(name, value, keyspace.createClock(), colTtl, StringSerializer.get(), StringSerializer.get());
  }

  @Override
  public HLock createLock(String lockPath) {
    return new HLockImpl(lockPath, generateLockId());
  }

  /**
   * Simple scheduled class to write heart beats to the column families. This
   * heart beat should be used to signal we're still waiting for a lock
   * 
   * @author tnine
   * 
   */
  private class Heartbeat implements Callable<Void> {

    private HLock lock;

    private Heartbeat(HLock lock) {
      this.lock = lock;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public Void call() throws Exception {
      logger.debug("{} heartbeat", lock);

      /**
       * We check that we still exist in Cassandra, then re write our state for
       * 2 reasons.
       * 
       * Cassandra is the authoritative system for locking
       * 
       * If there is lag and another client appears before us in the list after
       * we acquire the lock, we never want to acknowledge it. We simply keep
       * writing the state we had when we acquired the lock originally. This
       * ensures that we never get a race condition on initial lock due to clock
       * drift or column ordering in Cassandra
       */

      Map<String, String> existing = readExistingLocks(lock, lock.getLockId());

      String values = existing.get(lock.getLockId());

      if (values == null) {
        logger.debug("{} lock has been removed from cassandra.  Short circuiting", lock);
        return null;
      }

      writeLock(lock, values);

      scheduler.schedule(this, lockTtl / 2, TimeUnit.MILLISECONDS);
      return null;
    }
  }
}
