package me.prettyprint.cassandra.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ConsistencyLevel;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HNotFoundException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.cassandra.thrift.Cassandra;
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

  private final ConcurrentHashMap<String, KeyspaceServiceImpl> keyspaceMap =
      new ConcurrentHashMap<String, KeyspaceServiceImpl>();

  private String clusterName;

  private String serverVersion;

  private final KeyspaceServiceFactory keyspaceFactory;

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
      KeyspaceServiceFactory keyspaceFactory,
      CassandraHost cassandraHost,
      CassandraClientPool clientPools,
      Cluster cassandraCluster,
      ClockResolution clockResolution) {
    mySerial = serial.incrementAndGet();
    cassandra = cassandraThriftClient;
    this.cassandraHost = cassandraHost;
    this.keyspaceFactory = keyspaceFactory;
    cassandraClientPool = clientPools;
    this.clockResolution = clockResolution;
    cluster = cassandraCluster;
  }


  @Override
  public String getClusterName() throws HectorException {
    if (clusterName == null) {
      clusterName = cluster.getName();
    }
    return clusterName;
  }

  public Cassandra.Client getCassandra() {
    return this.cassandra;
  }

  @Override
  public KeyspaceService getKeyspace(String keySpaceName) throws HectorException {
    return getKeyspace(keySpaceName, DEFAULT_CONSISTENCY_LEVEL, DEFAULT_FAILOVER_POLICY);
  }


  @Override
  public KeyspaceService getKeyspace(String keySpaceName, ConsistencyLevel consistency) throws IllegalArgumentException,
      HNotFoundException, HectorTransportException {
    return getKeyspace(keySpaceName, consistency, DEFAULT_FAILOVER_POLICY);
  }


  @Override
  public KeyspaceService getKeyspace(String keyspaceName, ConsistencyLevel consistencyLevel,
      FailoverPolicy failoverPolicy)
      throws IllegalArgumentException, HNotFoundException, HectorTransportException {
    String keyspaceMapKey = buildKeyspaceMapName(keyspaceName, consistencyLevel, failoverPolicy);
    KeyspaceServiceImpl keyspace = keyspaceMap.get(keyspaceMapKey);
    if (keyspace == null) {

      KeyspaceDefinition keyspaceDesc = cluster.describeKeyspace(keyspaceName);
      if ( keyspaceDesc == null ) {
        throw new HNotFoundException("That keyspace is not defined on the cluster: " + keyspaceName);
      }
      keyspace = (KeyspaceServiceImpl) keyspaceFactory.create(this, keyspaceName, keyspaceDesc,
          consistencyLevel, failoverPolicy, cassandraClientPool);

      KeyspaceServiceImpl tmp = keyspaceMap.putIfAbsent(keyspaceMapKey , keyspace);
      if (tmp != null) {
        // There was another put that got here before we did.
        keyspace = tmp;
      }
    }
    return keyspace;
  }


  @Override
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
    b.append(consistencyLevel.ordinal());
    b.append(',');
    b.append(failoverPolicy);
    b.append(']');
    return b.toString();
  }


  @Override
  public CassandraHost getCassandraHost() {
    return cassandraHost;
  }



  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    b.append("CassandraClient<");
    b.append(cassandraHost.getUrl());
    b.append("-");
    b.append(mySerial);
    b.append(">");
    return b.toString();
  }


  @Override
  public void close() {
    if ( this.cassandra != null ) {
      if ( this.cassandra.getInputProtocol() != null ) {
        if ( this.cassandra.getInputProtocol().getTransport() != null ) {
          try {
            this.cassandra.getInputProtocol().getTransport().flush();
            this.cassandra.getInputProtocol().getTransport().close();
          } catch (Exception e) {
            log.error("Could not close transport in closing client", e);
          }
        }
      }
    }
    closed = true;
  }


  @Override
  public boolean isClosed() {
    return closed;
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
  public void removeKeyspace(KeyspaceService k) {
    String key = buildKeyspaceMapName(k.getName(), k.getConsistencyLevel(), k.getFailoverPolicy());
    keyspaceMap.remove(key);
  }


  @Override
  public ClockResolution getClockResolution() {
    return clockResolution;
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
