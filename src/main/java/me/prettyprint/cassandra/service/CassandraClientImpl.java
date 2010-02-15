package me.prettyprint.cassandra.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.Keyspace;
import me.prettyprint.cassandra.model.KeyspaceFactory;

import org.apache.cassandra.service.Cassandra;
import org.apache.cassandra.service.NotFoundException;
import org.apache.cassandra.service.Cassandra.Client;
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
  private final static String PROP_TOKEN_MAP = "token map";
  private final static String PROP_KEYSPACE = "keyspaces";
  private final static String PROP_VERSION = "version";

  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(CassandraClientImpl.class);

  /** The thrift object */
  private final Cassandra.Client cassandra;

  /** List of known keyspaces */
  private List<String> keyspaces;

  private final HashMap<String, Keyspace> keyspaceMap = new HashMap<String, Keyspace>();

  private String clusterName;

  private Map<String, String> tokenMap;

  private String configFile;

  private String serverVersion;

  private final KeyspaceFactory keyspaceFactory;

  private final int port;

  private final String url;

  private final CassandraClientPoolStore clientPools;

  public CassandraClientImpl(Cassandra.Client cassandraThriftClient,
      KeyspaceFactory keyspaceFactory, String url, int port, CassandraClientPoolStore clientPools) {
    cassandra = cassandraThriftClient;
    this.keyspaceFactory = keyspaceFactory;
    this.port = port;
    this.url = url;
    this.clientPools = clientPools;
  }

  @Override
  public String getClusterName() throws TException {
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
  public Keyspace getKeySpace(String keySpaceName) throws IllegalArgumentException,
      NotFoundException, TException {
    return getKeySpace(keySpaceName, DEFAULT_CONSISTENCY_LEVEL, DEFAULT_FAILOVER_POLICY);
  }

  @Override
  public Keyspace getKeySpace(String keyspaceName, int consistencyLevel,
      FailoverPolicy failoverPolicy)
      throws IllegalArgumentException, NotFoundException, TException {
    String keyspaceMapKey = buildKeyspaceMapName(keyspaceName, consistencyLevel, failoverPolicy);
    Keyspace keyspace = keyspaceMap.get(keyspaceMapKey);
    if (keyspace == null) {
      if (getKeyspaces().contains(keyspaceName)) {
        Map<String, Map<String, String>> keyspaceDesc = cassandra.describe_keyspace(keyspaceName);
        keyspace = keyspaceFactory.create(this, keyspaceName, keyspaceDesc, consistencyLevel,
            failoverPolicy, clientPools);
        keyspaceMap.put(keyspaceMapKey , keyspace);
      }else{
        throw new IllegalArgumentException(
            "request key space not exist, keyspaceName=" + keyspaceName);
      }
    }
    return keyspace;
  }

  @Override
  public List<String> getKeyspaces() throws TException {
    if (keyspaces == null) {
      keyspaces = cassandra.get_string_list_property(PROP_KEYSPACE);
    }
    return keyspaces;
  }

  @Override
  public String getStringProperty(String propertyName) throws TException {
    return cassandra.get_string_property(propertyName);
  }

  @Override
  public Map<String, String> getTokenMap(boolean fresh) throws TException {
    if (tokenMap == null || fresh) {
      tokenMap = new HashMap<String, String>();
      String strTokens = getStringProperty(PROP_TOKEN_MAP);
      // Parse the result of the form {"token1":"host1","token2":"host2"}
      strTokens = trimBothSides(strTokens);
      String[] tokenPairs = strTokens.split(",");
      for (String tokenPair: tokenPairs) {
        String[] keyValue = tokenPair.split(":");
        String token = trimBothSides(keyValue[0]);
        String host = trimBothSides(keyValue[1]);
        tokenMap.put(token, host);
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
  private String buildKeyspaceMapName(String keyspaceName, int consistencyLevel,
      FailoverPolicy failoverPolicy) {
    StringBuilder b = new StringBuilder(keyspaceName);
    b.append('[');
    b.append(consistencyLevel);
    b.append(',');
    b.append(failoverPolicy);
    b.append(']');
    return b.toString();
  }

  @Override
  public Client getCassandra() {
    return cassandra;
  }

  /**
   * Trims the string, one char from each side.
   * For example, this: asdf becomes this: sd
   * Useful in those cases:  "asdf" => asdf
   * @param str
   * @return
   */
  private String trimBothSides(String str) {
    str = str.substring(1);
    str = str.substring(0, str.length() - 1);
    return str;
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
    // Iterate over all keyspaces and ask them to update known hosts
    for (Keyspace k: keyspaceMap.values()) {
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
    b.append(">");
    return b.toString();
  }
}
