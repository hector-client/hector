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

//  private List<String> keyspaces;

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
      clusterName = cassandra.get_string_property(PROP_CLUSTER_NAME);
    }
    return clusterName;
  }

  @Override
  public String getConfigFile() {
    // TODO Auto-generated method stub
    return null;
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
  public List<String> getKeyspaces() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getStringProperty(String propertyName) throws TException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getTokenMap() {
    // TODO Auto-generated method stub
    return null;
  }

}
