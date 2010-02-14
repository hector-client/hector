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

  /** The thrift object */
  private final Cassandra.Client cassandra;

  private List<String> keyspaces;

  private final HashMap<String, Keyspace> keyspaceMap = new HashMap<String, Keyspace>();

  private String clusterName;

  private String tokenMap;

  private String configFile;

  private String serverVersion;

  private final KeyspaceFactory keyspaceFactory;

  public CassandraClientImpl(Cassandra.Client cassandraThriftClient,
      KeyspaceFactory keyspaceFactory) {
    cassandra = cassandraThriftClient;
    this.keyspaceFactory = keyspaceFactory;
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
            failoverPolicy);
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
  public String getTokenMap() throws TException {
    if (tokenMap == null) {
      tokenMap = getStringProperty(PROP_TOKEN_MAP);
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
}
