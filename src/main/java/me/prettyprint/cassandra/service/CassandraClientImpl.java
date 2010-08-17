package me.prettyprint.cassandra.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.HectorTransportException;
import me.prettyprint.cassandra.model.NotFoundException;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.KsDef;
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

  private final ClockResolution clockResolution;

  /** List of known keyspaces */
  private ArrayList<KsDef> keyspaces;

  private final ConcurrentHashMap<String, KeyspaceImpl> keyspaceMap =
      new ConcurrentHashMap<String, KeyspaceImpl>();

  private String clusterName;

  private String serverVersion;

  private final KeyspaceFactory keyspaceFactory;

  private final CassandraClientPool cassandraClientPool;
  
  /** An instance of the cluster object used to manage meta-operations */
  private final Cluster cluster;

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
      Cluster cassandraCluster,      
      ClockResolution clockResolution)
      throws UnknownHostException {
    this.mySerial = serial.incrementAndGet();
    cassandra = cassandraThriftClient;
    this.cassandraHost = cassandraHost;
    this.keyspaceFactory = keyspaceFactory;
    this.cassandraClientPool = clientPools;
    this.clockResolution = clockResolution;
    this.cluster = cassandraCluster;
  }


  public String getClusterName() throws HectorException {
    if (clusterName == null) {
      clusterName = cluster.getName();
    }
    return clusterName;
  }


  public Keyspace getKeyspace(String keySpaceName) throws HectorException {
    return getKeyspace(keySpaceName, DEFAULT_CONSISTENCY_LEVEL, DEFAULT_FAILOVER_POLICY);
  }


  public Keyspace getKeyspace(String keySpaceName, ConsistencyLevel consistency) throws IllegalArgumentException,
      NotFoundException, HectorTransportException {
    return getKeyspace(keySpaceName, consistency, DEFAULT_FAILOVER_POLICY);
  }


  public Keyspace getKeyspace(String keyspaceName, ConsistencyLevel consistencyLevel,
      FailoverPolicy failoverPolicy)
      throws IllegalArgumentException, NotFoundException, HectorTransportException {
    String keyspaceMapKey = buildKeyspaceMapName(keyspaceName, consistencyLevel, failoverPolicy);
    KeyspaceImpl keyspace = keyspaceMap.get(keyspaceMapKey);
    if (keyspace == null) {
      if (keyspaceExists(keyspaceName)) {
        try {
          KsDef keyspaceDesc = cassandra.describe_keyspace(keyspaceName);
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


  public List<KsDef> getKeyspaces() throws HectorTransportException {
    if (keyspaces == null) {
      try {
        keyspaces = new ArrayList<KsDef>(cassandra.describe_keyspaces());
      } catch (TException e) {
        throw new HectorTransportException(e);
      }
    }
    return keyspaces;
  }

  public boolean keyspaceExists(String keyspace) {
    List<KsDef> ksDefs = getKeyspaces();
    for (KsDef ksDef : ksDefs) {
      if (ksDef.getName().equals(keyspace)) return true;
    }
    return false;
  }


  public String getServerVersion() throws HectorException {
    if (serverVersion == null) {
      serverVersion = cluster.describeThriftVersion();
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


  public Cassandra.Client getCassandra() {
    return cassandra;
  }


  public CassandraHost getCassandraHost() {
    return this.cassandraHost;
  }



  public String toString() {
    StringBuilder b = new StringBuilder();
    b.append("CassandraClient<");
    b.append(cassandraHost.getUrl());
    b.append("-");
    b.append(mySerial);
    b.append(">");
    return b.toString();
  }


  public void markAsClosed() {
    closed = true;
  }


  public boolean isClosed() {
    return closed;
  }


  public boolean hasErrors() {
    return hasErrors ;
  }


  public void markAsError() {
    hasErrors = true;
  }


  public void removeKeyspace(Keyspace k) {
    String key = buildKeyspaceMapName(k.getName(), k.getConsistencyLevel(), k.getFailoverPolicy());
    keyspaceMap.remove(key);
  }


  public ClockResolution getClockResolution() {
    return clockResolution;
  }


  public boolean isReleased() {
    return released;
  }


  public void markAsReleased() {
    released = true;
  }


  public void markAsBorrowed() {
    released = false;
  }
}
