package me.prettyprint.cassandra.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.TokenRange;
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

  private final static String PROP_CLUSTER_NAME = "cluster name";
  private final static String PROP_CONFIG_FILE = "config file";
  @SuppressWarnings("unused")
  private final static String PROP_TOKEN_MAP = "token map";
  @SuppressWarnings("unused")
  private final static String PROP_KEYSPACE = "keyspaces";
  private final static String PROP_VERSION = "version";

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

  private Map<String, String> tokenMap;

  private String configFile;

  private String serverVersion;

  private final KeyspaceFactory keyspaceFactory;

  private final int port;

  private final String url;
  private final String ip;

  private final CassandraClientPool clientPools;

  /** Has the client network connection been closed? */
  private boolean closed = false;

  /** Does this client have errors */
  private boolean hasErrors = false;

  /** Whether the client has been released back to the pool already */
  private boolean released = false;

  public CassandraClientImpl(Cassandra.Client cassandraThriftClient,
      KeyspaceFactory keyspaceFactory, String url, int port, CassandraClientPool clientPools,
      TimestampResolution timestampResolution)
      throws UnknownHostException {
    this.mySerial = serial.incrementAndGet();
    cassandra = cassandraThriftClient;
    this.keyspaceFactory = keyspaceFactory;
    this.port = port;
    this.url = url;
    ip = getIpString(url);
    this.clientPools = clientPools;
    this.timestampResolution = timestampResolution;
  }

  private static String getIpString(String url) throws UnknownHostException {
    return InetAddress.getByName(url).getHostAddress();
  }

  @Override
  public String getClusterName() throws TException {
    // TODO replace with meta data API
    if (clusterName == null) {
      clusterName = getStringProperty(PROP_CLUSTER_NAME);
    }
    return clusterName;
  }

  @Override
  public String getConfigFile() throws TException {
    if (configFile == null) {
      configFile = getStringProperty(PROP_CONFIG_FILE);
    }
    return configFile;
  }

  @Override
  public Keyspace getKeyspace(String keySpaceName) throws IllegalArgumentException,
      NotFoundException, TException {
    return getKeyspace(keySpaceName, DEFAULT_CONSISTENCY_LEVEL, DEFAULT_FAILOVER_POLICY);
  }

  @Override
  public Keyspace getKeyspace(String keySpaceName, ConsistencyLevel consistency) throws IllegalArgumentException,
      NotFoundException, TException {
    return getKeyspace(keySpaceName, consistency, DEFAULT_FAILOVER_POLICY);
  }

  @Override
  public Keyspace getKeyspace(String keyspaceName, ConsistencyLevel consistencyLevel,
      FailoverPolicy failoverPolicy)
      throws IllegalArgumentException, NotFoundException, TException {
    String keyspaceMapKey = buildKeyspaceMapName(keyspaceName, consistencyLevel, failoverPolicy);
    KeyspaceImpl keyspace = keyspaceMap.get(keyspaceMapKey);
    if (keyspace == null) {
      if (getKeyspaces().contains(keyspaceName)) {
        Map<String, Map<String, String>> keyspaceDesc = cassandra.describe_keyspace(keyspaceName);
        keyspace = (KeyspaceImpl) keyspaceFactory.create(this, keyspaceName, keyspaceDesc,
            consistencyLevel, failoverPolicy, clientPools);
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
  public List<String> getKeyspaces() throws TException {
    if (keyspaces == null) {
      keyspaces = new ArrayList<String>(cassandra.describe_keyspaces());
    }
    return keyspaces;
  }

  @Override
  public String getStringProperty(String propertyName) throws TException {
    // TODO remove
    return cassandra.get_string_property(propertyName);
  }

  @Override
  public Map<String, String> getTokenMap(boolean fresh) throws TException {
    if (tokenMap == null || fresh) {
      tokenMap = new HashMap<String, String>();
      List<String> keyspaces = getKeyspaces();
      for (String keyspace : keyspaces) {
        if ( keyspace.equals("system") )
          continue;
        List<TokenRange> tokenRanges = cassandra.describe_ring(keyspace);
        for (TokenRange tokenRange : tokenRanges) {
          for(String host : tokenRange.getEndpoints()) {
            log.debug("token start: {} end: {} host: {}",
                new Object[]{tokenRange.getStart_token(), tokenRange.getEnd_token(), host});
            tokenMap.put(tokenRange.getStart_token() + "-" + tokenRange.getEnd_token(), host);
          }
        }
      }

    }
    return tokenMap;
  }

  @Override
  public String getServerVersion() throws TException {
    if (serverVersion == null) {
      serverVersion = getStringProperty(PROP_VERSION);
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
    return port;
  }

  @Override
  public String getUrl() {
    return url;
  }

  @Override
  public void updateKnownHosts() throws TException {
    // TODO rebuild to use meta API
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
    // TODO update to use META API
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
    return ip;
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
