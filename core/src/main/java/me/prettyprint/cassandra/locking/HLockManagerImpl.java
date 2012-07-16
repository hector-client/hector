package me.prettyprint.cassandra.locking;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;

import java.util.List;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.locking.HLock;
import me.prettyprint.hector.api.locking.HLockManagerConfigurator;
import me.prettyprint.hector.api.locking.HLockTimeoutException;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

import com.google.common.collect.Lists;

/**
 * Wait Chain implementation created by Dominic Williams, reviewed by Aaron Morton and Patricio Echague.
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
    List<HLock> canBeEarlier = readExistingLocks(lock.getPath());

    // If it is just me...
    if (canBeEarlier.size() <= 1) {
      ((HLockImpl) lock).setAcquired(true);
      return;
    }
    
    // Continue the development here.
    
    throw new HLockTimeoutException("dummy ex for now");

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
    mutator.addInsertion(lock.getPath(), lockManagerConfigurator.getLockManagerCF(), createColumnForLock(lock));
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
  private List<HLock> readExistingLocks(String lockPath) {
    SliceQuery<String, String, String> sliceQuery = HFactory
        .createSliceQuery(keyspace, StringSerializer.get(), StringSerializer.get(), StringSerializer.get())
        .setColumnFamily(lockManagerConfigurator.getLockManagerCF()).setKey(lockPath)
        .setRange(null, null, false, Integer.MAX_VALUE);

    QueryResult<ColumnSlice<String, String>> queryResult = sliceQuery.execute();

    List<HLock> result = Lists.newArrayList();

    for (HColumn<String, String> col : queryResult.get().getColumns()) {
      result.add(new HLockImpl(lockPath, col.getName()));
    }

    return result;
  }

  private HColumn<String, String> createColumnForLock(HLock lock) {
    return createColumn(lock.getLockId(), DUMMY_VALUE, lockManagerConfigurator.getLocksTTLInMillis(),
        StringSerializer.get(), StringSerializer.get());
  }

  @Override
  public HLock createLock(String lockPath) {
    return new HLockImpl(lockPath, generateLockId());
  }
}
