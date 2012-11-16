package me.prettyprint.hector.api.locking;

/**
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 * @author tnine (Todd Nine)
 * 
 */
public class HLockManagerConfigurator {

  public static final String DEFAUT_LOCK_MANAGER_CF = "HLocks";
  private static final String DEFAULT_LOCK_MANAGER_KEYSPACE_NAME = "HLockingManager";

  private String keyspaceName = DEFAULT_LOCK_MANAGER_KEYSPACE_NAME;
  private String lockManagerCF = DEFAUT_LOCK_MANAGER_CF;
  private boolean rowsCacheEnabled = true;
  private long locksTTLInMillis = 5000L;
  private long backOffRetryDelayInMillis = 100L;
  private int replicationFactor = 3;
  private int numberOfLockObserverThreads = 1;
  private int maxSelectSize = 10;

  public String getLockManagerCF() {
    return lockManagerCF;
  }

  public void setLockManagerCF(String lockManagerCF) {
    this.lockManagerCF = lockManagerCF;
  }

  public boolean isRowsCacheEnabled() {
    return rowsCacheEnabled;
  }

  public void setRowsCacheEnabled(boolean rowsCacheEnabled) {
    this.rowsCacheEnabled = rowsCacheEnabled;
  }

  public long getLocksTTLInMillis() {
    return locksTTLInMillis;
  }

  public void setLocksTTLInMillis(long locksTTLInMillis) {
    this.locksTTLInMillis = locksTTLInMillis;
  }

  public long getBackOffRetryDelayInMillis() {
    return backOffRetryDelayInMillis;
  }

  public void setBackOffRetryDelayInMillis(long backOffRetryDelayInMillis) {
    this.backOffRetryDelayInMillis = backOffRetryDelayInMillis;
  }

  public String getKeyspaceName() {
    return this.keyspaceName;
  }

  public void setKeyspaceName(String keyspaceName) {
    this.keyspaceName = keyspaceName;
  }

  public int getReplicationFactor() {
    return replicationFactor;
  }

  public void setReplicationFactor(int replicationFactor) {
    this.replicationFactor = replicationFactor;
  }

  /**
   * @return the numberOfLockObserverThreads
   */
  public int getNumberOfLockObserverThreads() {
    return numberOfLockObserverThreads;
  }

  /**
   * @param numberOfLockObserverThreads the numberOfLockObserverThreads to set
   */
  public void setNumberOfLockObserverThreads(int numberOfLockObserverThreads) {
    this.numberOfLockObserverThreads = numberOfLockObserverThreads;
  }

  /**
   * @return the maxSelectSize
   */
  public int getMaxSelectSize() {
    return maxSelectSize;
  }

  /**
   * @param maxSelectSize the maxSelectSize to set
   */
  public void setMaxSelectSize(int maxSelectSize) {
    this.maxSelectSize = maxSelectSize;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "HLockManagerConfigurator [keyspaceName=" + keyspaceName + ", lockManagerCF=" + lockManagerCF
        + ", rowsCacheEnabled=" + rowsCacheEnabled + ", locksTTLInMillis=" + locksTTLInMillis
        + ", backOffRetryDelayInMillis=" + backOffRetryDelayInMillis + ", replicationFactor=" + replicationFactor
        + ", numberOfLockObserverThreads=" + numberOfLockObserverThreads + ", maxSelectSize=" + maxSelectSize + "]";
  }

 

}
