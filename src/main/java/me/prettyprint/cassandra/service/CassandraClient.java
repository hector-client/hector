package me.prettyprint.cassandra.service;

import java.util.List;
import java.util.Map;
import java.util.Set;


import org.apache.cassandra.service.Cassandra;
import org.apache.cassandra.service.ConsistencyLevel;
import org.apache.cassandra.service.NotFoundException;
import org.apache.thrift.TException;


/**
 * Client object, a high level handle to the remove cassandra service.
 * <p>
 * From a client you can obtain a Keyspace. A keyspace lets you write/read the remote cassandra.
 * <p>
 * Thread safely: The client is not thread safe, do not share it between threads!
 *
 * @author rantav
 */
public interface CassandraClient {

  static final int DEFAULT_CONSISTENCY_LEVEL = ConsistencyLevel.DCQUORUM;

  /**
   * What should the client do if a call to cassandra node fails and we suspect that the node is
   * down. (e.g. it's a communication error, not an application error).
   *
   * {@value #FAIL_FAST} will return the error as is to the user and not try anything smart
   *
   * {@value #ON_FAIL_TRY_ONE_NEXT_AVAILABLE} will try one more random server before returning to the
   * user with an error
   *
   * {@value #ON_FAIL_TRY_ALL_AVAILABLE} will try all available servers in the cluster before giving
   * up and returning the communication error to the user.
   *
   */
  public enum FailoverPolicy {

    /** On communication failure, just return the error to the client and don't retry */
    FAIL_FAST(0),
    /** On communication error try one more server before giving up */
    ON_FAIL_TRY_ONE_NEXT_AVAILABLE(1),
    /** On communication error try all known servers before giving up */
    ON_FAIL_TRY_ALL_AVAILABLE(Integer.MAX_VALUE - 1);

    private final int numRetries;

    FailoverPolicy(int numRetries) {
      this.numRetries = numRetries;
    }

    public int getNumRetries() {
      return numRetries;
    }
  }

  static final FailoverPolicy DEFAULT_FAILOVER_POLICY =
      FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;

  /**
   * @return the underline cassandra thrift object, all remote calls will be sent to this client.
   */
  Cassandra.Client getCassandra();

  /**
   * Return given key space, if keySpaceName not exist, will throw an exception.
   * <p>
   * Thread safety: not safe ;-)
   * Really, if you require thread safety do it at the application level, this class does not
   * provide it.
   * <p>
   * Uses the default consistency level, {@link #DEFAULT_CONSISTENCY_LEVEL}
   * <p>
   * Uses the default failover policy {@link #DEFAULT_FAILOVER_POLICY}
   */
  Keyspace getKeyspace(String keyspaceName)
      throws IllegalArgumentException, NotFoundException, TException;


  /**
   * Gets s keyspace with the specified consistency level.
   */
  Keyspace getKeyspace(String keyspaceName, int consistencyLevel, FailoverPolicy failoverPolicy)
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
   * Gets the token map with an option to refresh the value from cassandra.
   * If fresh is false, a local cached value may be returned.
   *
   * @param fresh Whether to query cassandra remote host for an up to date value, or to serve
   *  a possibly cached value.
   * @return  a map from tokens to hosts.
   */
  Map<String, String> getTokenMap(boolean fresh) throws TException;


  /**
   * @return config file content.
   */
  String getConfigFile() throws TException;

  /**
   * @return Server version
   */
  String getServerVersion() throws TException;

  public int getPort();

  public String getUrl();

  /**
   * Tells all instanciated keyspaces to update their known hosts
   */
  void updateKnownHosts() throws TException;

  void markAsClosed();

  boolean isClosed();

  Set<String> getKnownHosts();

  String getIp();

  void markAsError();

  boolean hasErrors();

  void removeKeyspace(Keyspace k);

}
