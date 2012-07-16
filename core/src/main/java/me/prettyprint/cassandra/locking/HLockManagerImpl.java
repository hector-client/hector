package me.prettyprint.cassandra.locking;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.locking.HLock;
import me.prettyprint.hector.api.locking.HLockManagerConfigurator;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Wait Chain implementation created by Dominic Williams, reviewed by Aaron
 * Morton and Patricio Echague.
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 * 
 */
public class HLockManagerImpl extends AbstractLockManager {

  public HLockManagerImpl(Cluster cluster, Keyspace keyspace) {
    super(cluster, keyspace);
  }

  public HLockManagerImpl(Cluster cluster, Keyspace keyspace, HLockManagerConfigurator lockManagerConfigurator) {
    super(cluster, keyspace, lockManagerConfigurator);
  }

  public HLockManagerImpl(Cluster cluster) {
    super(cluster);
  }

  public HLockManagerImpl(Cluster cluster, HLockManagerConfigurator hlc) {
    super(cluster, hlc);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void acquire(HLock lock) {
    verifyPrecondition(lock);

    // Generate the internal lock id (CLID)
    maybeSetInternalLockId(lock);

    writeLock(lock);

    // Pairs of type <LockId, CommandSeparatedSeenLockIds>
    Map<String, String> canBeEarlier = readExistingLocks(lock.getPath());

    // If it is just me...
    if (canBeEarlier.size() <= 1) {
      ((HLockImpl) lock).setAcquired(true);
      return;
    }

    String nextWaitingClientId = null;
    final long SAY_CONTINUE = 15 * 1000L;
    long last_write_acks = 0L;

    while (true) {
      boolean recv_all_acks = true;

      // Let's see of other nodes know me
      for (Entry<String, String> otherLock : canBeEarlier.entrySet()) {

        if (!lock.getLockId().equals(otherLock.getKey()) && !hasThisLockSeenMe(otherLock.getValue(), lock.getLockId())) {
          recv_all_acks = false;
          break;
        }

        // If everyone acknowledged to have seen me then ...
        if (recv_all_acks) {
          List<String> canBeEarlierSortedList = Lists.newArrayList(canBeEarlier.keySet());
          // sort them
          Collections.sort(canBeEarlierSortedList);
          nextWaitingClientId = canBeEarlierSortedList.get(0);
          // check if we are the first ones
          if (nextWaitingClientId.equals(lock.getLockId())) {
            break;
          }
        }

        // Let everyone know what I have already seen
        if (System.currentTimeMillis() - last_write_acks > SAY_CONTINUE) {
          // Write this lock again with the list of Lock Id I have seen
          writeLock(lock, canBeEarlier.keySet());
          // Set the flag so we don't do it again
          last_write_acks = System.currentTimeMillis();
        }

        smartWait(lockManagerConfigurator.getBackOffRetryDelayInMillis());

        // Refresh the list
        canBeEarlier = readExistingLocks(lock.getPath());

      }
    }

    // Continue the development here.

    // throw new HLockTimeoutException("dummy ex for now");

  }

  private void smartWait(long sleepTime) {
    try {
      Thread.sleep((sleepTime + (long) (Math.random() * sleepTime)));
    } catch (InterruptedException e) {
      throw new RuntimeException();
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
    Mutator<String> mutator = createMutator(keyspace, StringSerializer.get());
    mutator.addInsertion(lock.getPath(), lockManagerConfigurator.getLockManagerCF(),
        createColumnForLock(lock.getLockId(), lock.getLockId()));
    mutator.execute();
  }

  private void writeLock(HLock lock, Set<String> keySet) {
    Mutator<String> mutator = createMutator(keyspace, StringSerializer.get());
    // Of course I have seen myself
    keySet.remove(lock.getLockId());
    String seenLockIds = Joiner.on(",").join(keySet);
    mutator.addInsertion(lock.getPath(), lockManagerConfigurator.getLockManagerCF(),
        createColumnForLock(lock.getLockId(), seenLockIds));
    mutator.execute();
  }

  private void deleteLock(HLock lock) {
    Mutator<String> mutator = createMutator(keyspace, StringSerializer.get());
    mutator.addDeletion(lock.getPath(), lockManagerConfigurator.getLockManagerCF(), lock.getLockId(),
        StringSerializer.get());
    mutator.execute();
  }

  /**
   * Reads all existing locks for this lock path
   * 
   * @param lockPath
   *          a lock path
   * @return a list of locks waiting on this lockpath
   */
  private Map<String, String> readExistingLocks(String lockPath) {
    SliceQuery<String, String, String> sliceQuery = HFactory
        .createSliceQuery(keyspace, StringSerializer.get(), StringSerializer.get(), StringSerializer.get())
        .setColumnFamily(lockManagerConfigurator.getLockManagerCF()).setKey(lockPath)
        .setRange(null, null, false, Integer.MAX_VALUE);

    QueryResult<ColumnSlice<String, String>> queryResult = sliceQuery.execute();

    Map<String, String> result = Maps.newHashMap();

    for (HColumn<String, String> col : queryResult.get().getColumns()) {
      result.put(col.getName(), col.getValue());
    }

    return result;
  }

  private HColumn<String, String> createColumnForLock(String name, String value) {
    return createColumn(name, value, lockManagerConfigurator.getLocksTTLInMillis(), StringSerializer.get(),
        StringSerializer.get());
  }

  @Override
  public HLock createLock(String lockPath) {
    return new HLockImpl(lockPath, generateLockId());
  }
}
