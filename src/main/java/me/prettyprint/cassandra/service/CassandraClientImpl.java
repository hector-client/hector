package me.prettyprint.cassandra.service;

import java.util.HashMap;
import java.util.List;

import me.prettyprint.cassandra.model.Keyspace;

import org.apache.cassandra.service.Cassandra;
import org.apache.cassandra.service.NotFoundException;
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

  private HashMap<String, Keyspace> keyspaceMap;

  private String clusterName;

  private String tokenMap;

  private String configFile;

  private String serverVersion;

  public CassandraClientImpl(Cassandra.Client cassandraThriftClient) {
    cassandra = cassandraThriftClient;
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Keyspace getKeySpace(String keySpaceName, int consitencyLevel)
      throws IllegalArgumentException, NotFoundException, TException {
    // TODO Auto-generated method stub
    return null;
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
}
