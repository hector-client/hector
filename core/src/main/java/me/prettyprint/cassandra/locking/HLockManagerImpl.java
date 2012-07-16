package me.prettyprint.cassandra.locking;

import java.util.List;

import com.google.common.collect.Lists;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
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

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;

/**
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

  @Override
  public void acquire(HLock lock) {
    // TODO Auto-generated method stub

  }

  @Override
  public void release(HLock lock) {
    // TODO Auto-generated method stub

  }

  private void writeLock(HLock lock) {
    Mutator<String> mutator = createMutator(keyspace, StringSerializer.get());
    mutator.addInsertion(lock.getPath(), lockManagerConfigurator.getLockManagerCF(), createColumnForLock(lock));
    mutator.execute();
  }
  
  private void deleteLock(HLock lock) {
    Mutator<String> mutator = createMutator(keyspace, StringSerializer.get());
    mutator.addDeletion(lock.getPath(), lockManagerConfigurator.getLockManagerCF(), lock.getLockId(), StringSerializer.get());
    mutator.execute();
  }
  
  /**
   * Reads all existing locks for this lock path
   * @param lockPath a lock path
   * @return a list of locks waiting on this lockpath
   */
  private List<HLock> readExistingLocks(String lockPath) {
    SliceQuery<String, String, String> sliceQuery = HFactory.createSliceQuery(keyspace, StringSerializer.get(), StringSerializer.get(), StringSerializer.get())
      .setColumnFamily(lockManagerConfigurator.getLockManagerCF())
      .setKey(lockPath)
      .setRange(null, null, false, Integer.MAX_VALUE);

    QueryResult<ColumnSlice<String, String>> queryResult = sliceQuery.execute();
    
    List<HLock> result = Lists.newArrayList();
    
    for (HColumn<String, String> col : queryResult.get().getColumns()) {
      result.add(new HLockImpl(lockPath, col.getName()));
    }
    
    return result;
  }

  private HColumn<String, String> createColumnForLock(HLock lock) {
    return createColumn(lock.getPath(), DUMMY_VALUE, lockManagerConfigurator.getLocksTTLInMillis(),
        StringSerializer.get(), StringSerializer.get());
  }
}
