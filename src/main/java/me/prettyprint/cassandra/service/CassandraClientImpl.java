package me.prettyprint.cassandra.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.HectorTransportException;
import me.prettyprint.cassandra.model.NotFoundException;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the client interface.
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
/*package*/ class CassandraClientImpl implements CassandraClient {

  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(CassandraClientImpl.class);

  /** Serial number of the client used to track client creation for debug purposes */
  private static final AtomicLong serial = new AtomicLong(0);

  private final long mySerial;

  /** The thrift object */
  private final Cassandra.Client cassandra;

  private final TimestampResolution timestampResolution;

  /** List of known keyspaces */
  private List<String> keyspaces;

  private final ConcurrentHashMap<String, KeyspaceImpl> keyspaceMap =
      new ConcurrentHashMap<String, KeyspaceImpl>();

  private String clusterName;

  private String serverVersion;

  private final KeyspaceFactory keyspaceFactory;

  private final CassandraClientPool cassandraClientPool;
  
  /** An instance of the cluster object used to manage meta-operations */
  private final CassandraCluster cassandraCluster;

  /** Has the client network connection been closed? */
  private boolean closed = false;

  /** Does this client have errors */
  private boolean hasErrors = false;

  /** Whether the client has been released back to the pool already */
  private boolean released = false;
  
  private final CassandraHost cassandraHost;

  public CassandraClientImpl(Cassandra.Client cassandraThriftClient,
      KeyspaceFactory keyspaceFactory, 
      CassandraHost cassandraHost, 
      CassandraClientPool clientPools,
      CassandraCluster cassandraCluster,      
      TimestampResolution timestampResolution)
      throws UnknownHostException {
    this.mySerial = serial.incrementAndGet();
    cassandra = cassandraThriftClient;
    this.cassandraHost = cassandraHost;
    this.keyspaceFactory = keyspaceFactory;
    this.cassandraClientPool = clientPools;
    this.timestampResolution = timestampResolution;
    this.cassandraCluster = cassandraCluster;
  }

  @Override
  public String getClusterName() throws HectorException {
    if (clusterName == null) {
      clusterName = cassandraCluster.getClusterName();
    }
    return clusterName;
  }

  @Override
  public Keyspace getKeyspace(String keySpaceName) throws HectorException {
    return getKeyspace(keySpaceName, DEFAULT_CONSISTENCY_LEVEL, DEFAULT_FAILOVER_POLICY);
  }

  @Override
  public Keyspace getKeyspace(String keySpaceName, ConsistencyLevel consistency) throws IllegalArgumentException,
      NotFoundException, HectorTransportException {
    return getKeyspace(keySpaceName, consistency, DEFAULT_FAILOVER_POLICY);
  }

  @Override
  public Keyspace getKeyspace(String keyspaceName, ConsistencyLevel consistencyLevel,
      FailoverPolicy failoverPolicy)
      throws IllegalArgumentException, NotFoundException, HectorTransportException {
    String keyspaceMapKey = buildKeyspaceMapName(keyspaceName, consistencyLevel, failoverPolicy);
    KeyspaceImpl keyspace = keyspaceMap.get(keyspaceMapKey);
    if (keyspace == null) {
      if (getKeyspaces().contains(keyspaceName)) {
        try {
          Map<String, Map<String, String>> keyspaceDesc = cassandra.describe_keyspace(keyspaceName);
          keyspace = (KeyspaceImpl) keyspaceFactory.create(this, keyspaceName, keyspaceDesc,
              consistencyLevel, failoverPolicy, cassandraClientPool);
        } catch (TException e) {
          throw new HectorTransportException(e);
        } catch (org.apache.cassandra.thrift.NotFoundException e) {
          throw new NotFoundException(e);
        }
        KeyspaceImpl tmp = keyspaceMap.putIfAbsent(keyspaceMapKey , keyspace);
        if (tmp != null) {
          // There was another put that got here before we did.
          keyspace = tmp;
        }
      }else{
        throw new IllegalArgumentException(
            "Requested key space not exist, keyspaceName=" + keyspaceName);
      }
    }
    return keyspace;
  }

  @Override
  public List<String> getKeyspaces() throws HectorTransportException {
    if (keyspaces == null) {
      try {
        keyspaces = new ArrayList<String>(cassandra.describe_keyspaces());
      } catch (TException e) {
        throw new HectorTransportException(e);
      }
    }
    return keyspaces;
  }

  @Override
  public List<String> getKnownHosts(boolean fresh) throws HectorException {
    return cassandraCluster.getKnownHosts(fresh);
  }

  @Override
  public String getServerVersion() throws HectorException {
    if (serverVersion == null) {
      serverVersion = cassandraCluster.describeThriftVersion();
    }
    return serverVersion;
   }

  /**
   * Creates a unique map name for the keyspace and its consistency level
   * @param keyspaceName
   * @param consistencyLevel
   * @return
   */
  private String buildKeyspaceMapName(String keyspaceName, ConsistencyLevel consistencyLevel,
      FailoverPolicy failoverPolicy) {
    StringBuilder b = new StringBuilder(keyspaceName);
    b.append('[');
    b.append(consistencyLevel.getValue());
    b.append(',');
    b.append(failoverPolicy);
    b.append(']');
    return b.toString();
  }

  @Override
  public Cassandra.Client getCassandra() {
    return cassandra;
  }

  @Override
  public int getPort() {
    return cassandraHost.getPort();
  }

  @Override
  public String getUrl() {
    return cassandraHost.getUrl();
  }

  @Override
  public void updateKnownHosts() throws HectorTransportException {
    if (closed) {
      return;
    }
    // Iterate over all keyspaces and ask them to update known hosts
    for (KeyspaceImpl k: keyspaceMap.values()) {
      k.updateKnownHosts();
    }
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    b.append("CassandraClient<");
    b.append(getUrl());
    b.append(":");
    b.append(getPort());
    b.append("-");
    b.append(mySerial);
    b.append(">");
    return b.toString();
  }

  @Override
  public void markAsClosed() {
    closed = true;
  }

  @Override
  public boolean isClosed() {
    return closed;
  }

  @Override
  public Set<String> getKnownHosts() {
    Set<String> hosts = new HashSet<String>();
    if (closed) {
      return hosts;
    }
    // Iterate over all keyspaces and ask them to update known hosts
    for (KeyspaceImpl k: keyspaceMap.values()) {
      hosts.addAll(k.getKnownHosts());
    }
    return hosts;
  }

  @Override
  public String getIp() {
    return cassandraHost.getIp();
  }

  @Override
  public boolean hasErrors() {
    return hasErrors ;
  }

  @Override
  public void markAsError() {
    hasErrors = true;
  }

  @Override
  public void removeKeyspace(Keyspace k) {
    String key = buildKeyspaceMapName(k.getName(), k.getConsistencyLevel(), k.getFailoverPolicy());
    keyspaceMap.remove(key);
  }

  @Override
  public TimestampResolution getTimestampResolution() {
    return timestampResolution;
  }

  @Override
  public boolean isReleased() {
    return released;
  }

  @Override
  public void markAsReleased() {
    released = true;
  }

  @Override
  public void markAsBorrowed() {
    released = false;
  }
}
