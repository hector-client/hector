package me.prettyprint.cassandra.locking;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;

import java.util.Collections;
import java.util.HashMap;
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
  
  

  public HLockManagerImpl(Cluster cluster, HLockManagerConfigurator hlc) {
    super(cluster, hlc);
    scheduler = Executors.newScheduledThreadPool(lockManagerConfigurator.getNumberOfLockObserverThreads());
    lockTtl = lockManagerConfigurator.getLocksTTLInMillis();
    colTtl = (int) (lockTtl/1000);
  }

  /* (non-Javadoc)
   * @see me.prettyprint.hector.api.locking.HLockManager#acquire(me.prettyprint.hector.api.locking.HLock)
   */
  @Override
  public void acquire(HLock lock) {
    acquire(lock, Long.MAX_VALUE-System.currentTimeMillis()-10000);
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
    Map<String, String> canBeEarlier = readExistingLocks(lock.getPath());
    
    
    startHeartBeat(lock, canBeEarlier.keySet());
   

    // If it is just me...
    if (canBeEarlier.size() <= 1) {
      ((HLockImpl) lock).setAcquired(true);
      return;
    }

    String nextWaitingClientId = null;
    final long SAY_CONTINUE = 15 * 1000L;
    long last_write_acks = 0L;
    long waitStart = System.currentTimeMillis();

    while (true) {
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
      if (System.currentTimeMillis() - last_write_acks > SAY_CONTINUE) {
        // Write this lock again with the list of Lock Id I have seen
        writeLock(lock, canBeEarlier.keySet());
        // Set the flag so we don't do it again
        last_write_acks = System.currentTimeMillis();
      }

      //We can't get the lock, and we've timed out, give out
      if(waitStart+timeout < System.currentTimeMillis()){
        cleanupStates(lock);
        deleteLock(lock);
        throw new HLockTimeoutException(String.format("Unable to get lock before %d ", waitStart+timeout));
        
      }
      
      smartWait(lockManagerConfigurator.getBackOffRetryDelayInMillis());
      
      // Refresh the list
      canBeEarlier = readExistingLocks(lock.getPath(), canBeEarlierSortedList);
    }

    ((HLockImpl) lock).setAcquired(true);
  }
  
 
  /**
   * Here for testing purposes only, this should never really be invoked
   */
  public void shutdownScheduler(){
    scheduler.shutdownNow();
  }
  
  private void cleanupStates(HLock lock){
    LockState state = states.get(lock);
    
    if(state == null){
      return;
    }
    
    state.heartbeat.cancel(false);
    
    states.remove(lock);
  }
  
  private void startHeartBeat(HLock lock, Set<String> seenLocks){
    LockState state = new LockState();
    state.heartbeat = scheduler.schedule(new Heartbeat(lock), lockTtl/2, TimeUnit.MILLISECONDS);
//    state.timeout = scheduler.schedule(new Timeout(lock), timeout, TimeUnit.MILLISECONDS);
    
    states.put(lock, state);
  }




  private void smartWait(long sleepTime) {
    try {
      Thread.sleep((sleepTime + (long) (Math.random() * sleepTime)));
    } catch (InterruptedException e) {
//      throw new RuntimeException();
      //swallow, we woke up early, not worth re-throwing an exception
      logger.warn("Interrupted while waiting", e);
    }
  }

  private boolean hasThisLockSeenMe(String commaSeparatedLockIds, String myLockId) {

    String[] seenLocksIds = commaSeparatedLockIds.split(",");

    for (int i = 0; i < seenLocksIds.length; i++) {
      logger.debug("{} comparing to {}", myLockId, seenLocksIds[i]);
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
    cleanupStates(lock);
    deleteLock(lock);
    ((HLockImpl)lock).setAcquired(false);
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
        StringSerializer.get(), keyspace.createClock());
    mutator.execute();
    
    /**
     * Remove the lock from states
     */
    states.remove(lock);
  }

  /**
   * Reads all existing locks for this lock path
   * 
   * @param lockPath
   *          a lock path
   * @return a list of locks waiting on this lockpath
   */
  private Map<String, String> readExistingLocks(String lockPath, List<String> columnNames) {
    SliceQuery<String, String, String> sliceQuery = HFactory
        .createSliceQuery(keyspace, StringSerializer.get(), StringSerializer.get(), StringSerializer.get())
        .setColumnFamily(lockManagerConfigurator.getLockManagerCF()).setKey(lockPath);
    
    if (columnNames == null) {
      sliceQuery.setRange(null, null, false, Integer.MAX_VALUE);
    } else {
      sliceQuery.setColumnNames(columnNames.toArray(new String[columnNames.size()]));
    }

    QueryResult<ColumnSlice<String, String>> queryResult = sliceQuery.execute();

    Map<String, String> result = Maps.newHashMap();

    for (HColumn<String, String> col : queryResult.get().getColumns()) {
      result.put(col.getName(), col.getValue());
    }

    return result;
  }

  private Map<String, String> readExistingLocks(String path) {
    return readExistingLocks(path, null);
  }

  private HColumn<String, String> createColumnForLock(String name, String value) {
    return createColumn(name, value, keyspace.createClock(), colTtl, StringSerializer.get(),
        StringSerializer.get());
  }

  @Override
  public HLock createLock(String lockPath) {
    return new HLockImpl(lockPath, generateLockId());
  }
  
  
  private Map<HLock, LockState> states = new HashMap<HLock, LockState>();
  
  /**
   * Internal state for a lock.  Contains the last seen keys, as well as futures for cancellation
   * @author tnine
   *
   */
  private class LockState{
      private Set<String> seenLocks;
      Future<Void> heartbeat;
  }
  
  /**
   * Simple scheduled class to write heart beats to the column families.  This heart beat should be used to signal we're still waiting for a lock
   * @author tnine
   *
   */
  private class Heartbeat implements Callable<Void>{

    private HLock lock;
    
    private Heartbeat(HLock lock){
      this.lock = lock;
    }
    
    /* (non-Javadoc)
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public Void call() throws Exception {
      logger.debug("{} heartbeat", lock);
      
      //update the lock
      LockState state = states.get(lock);
      
      //Lock has been removed
      if(state == null){
        logger.debug("{} lock state has been removed.  Short circuiting", lock);
        deleteLock(lock);
        return null;
      }
      
      //We're not locked, re-read to get our latest state update for the heartbeat.  If we have the lock, we'll never have to do this, that last state we wrote/read is sufficient
      if(!lock.isAcquired()){
        writeLock(lock, readExistingLocks(lock.getPath()).keySet());
      } else{
        //we haven't saved the state since we acquired the lock, do it now to prevent furthur reads
        if(state.seenLocks == null){
          state.seenLocks = readExistingLocks(lock.getPath()).keySet();
        }
        
        writeLock(lock, state.seenLocks);
      }
      
      
      
      
      
      
     
      state.heartbeat = scheduler.schedule(this, lockTtl/2, TimeUnit.MILLISECONDS);
      return null;
    }
    
  }
//  
//  /**
//   * Fired when a lock times out waiting
//   * @author tnine
//   *
//   */
//  private class Timeout implements Callable<Void>{
//    private HLock lock;
//    
//    private Timeout(HLock lock){
//      this.lock = lock;
//    }
//
//    /* (non-Javadoc)
//     * @see java.util.concurrent.Callable#call()
//     */
//    @Override
//    public Void call() throws Exception {
//      LockState state = states.get(lock);
//      
//      //do nothing, it's already been removed
//      if(state == null){
//        return null;
//      }
//      
//      //cancel the heartbeat
//      state.heartbeat.cancel(false);
//      
//      //delete the pending lock
//      deleteLock(lock);
//      
//      //signal we've timed out to the observer
//      
//      HLockObserver observer = lock.getObserver();
//      
//      if(observer != null){
//        observer.timeout(lock);
//      }
//      
//      return null;
//    }
//  }
}
