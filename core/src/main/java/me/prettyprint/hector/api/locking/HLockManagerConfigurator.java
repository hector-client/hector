package me.prettyprint.hector.api.locking;

/**
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("HLockManagerConfigurator [keyspaceName=");
    builder.append(keyspaceName);
    builder.append(", lockManagerCF=");
    builder.append(lockManagerCF);
    builder.append(", rowsCacheEnabled=");
    builder.append(rowsCacheEnabled);
    builder.append(", locksTTLInMillis=");
    builder.append(locksTTLInMillis);
    builder.append(", backOffRetryDelayInMillis=");
    builder.append(backOffRetryDelayInMillis);
    builder.append(", replicationFactor=");
    builder.append(replicationFactor);
    builder.append("]");
    return builder.toString();
  }

}
