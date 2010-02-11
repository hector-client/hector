package me.prettyprint.cassandra.service;

import java.util.List;

import me.prettyprint.cassandra.model.Keyspace;

import org.apache.cassandra.service.ConsistencyLevel;
import org.apache.cassandra.service.NotFoundException;
import org.apache.thrift.TException;


/**
 * Client object, a high level handle to the remove cassandra service.
 * <p>
 * From a client you can obtain a Keyspace. A keyspace lets you write/read the remote cassandra.
 *
 * @author rantav
 */
public interface CassandraClient {

  static final int DEFAULT_CONSISTENCY_LEVEL = ConsistencyLevel.DCQUORUM;

//  /**
//   * @return the underline cassandra thrift object, all remote calls will be sent to this client.
//   */
//  Cassandra.Client getCassandra();

  /**
   * Return given key space, if keySpaceName not exist, will throw an exception.
   * <p>
   * Thread safety: not safe ;-)
   * Really, if you require thread safety do it at the application level, this class does not
   * provide it.
   * <p>
   * Uses the default consistency level, {@link #DEFAULT_CONSISTENCY_LEVEL}
   */
  Keyspace getKeySpace(String keyspaceName)
      throws IllegalArgumentException, NotFoundException, TException;


  /**
   * Gets s keyspace with the specified consistency level.
   */
  Keyspace getKeySpace(String keyspaceName, int consistencyLevel)
      throws IllegalArgumentException, NotFoundException, TException;



  /**
   * Gets a string property from the server, such as:
   * "cluster name": cluster name;
   * "config file" : all config file content, if need you can try to explain it.
   * "token map" :  get the token map from local gossip protocal.
   */
  String getStringProperty(String propertyName) throws TException;


  /**
   * @return all keyspaces name of this client.
   */
  List<String> getKeyspaces() throws TException;


  /**
   * @return target server cluster name
   */
  String getClusterName() throws TException;


  /**
   * @return the tokens map.
   */
  String getTokenMap() throws TException;


  /**
   * @return config file content.
   */
  String getConfigFile() throws TException;

  /**
   * @return Server version
   */
  String getServerVersion() throws TException;
}
