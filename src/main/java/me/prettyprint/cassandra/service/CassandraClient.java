package me.prettyprint.cassandra.service;

import java.util.List;
import java.util.Set;

import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.HectorTransportException;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ConsistencyLevel;


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

  static final ConsistencyLevel DEFAULT_CONSISTENCY_LEVEL = ConsistencyLevel.QUORUM;

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
    FAIL_FAST(0, 0),
    /** On communication error try one more server before giving up */
    ON_FAIL_TRY_ONE_NEXT_AVAILABLE(1, 0),
    /** On communication error try all known servers before giving up */
    ON_FAIL_TRY_ALL_AVAILABLE(Integer.MAX_VALUE - 1, 0);

    public final int numRetries;

    public final int sleepBetweenHostsMilli;

    FailoverPolicy(int numRetries, int sleepBwHostsMilli) {
      this.numRetries = numRetries;
      sleepBetweenHostsMilli = sleepBwHostsMilli;
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
  Keyspace getKeyspace(String keyspaceName) throws HectorException;

  /**
   * Gets s keyspace with the specified consistency level
   */
  Keyspace getKeyspace(String keyspaceName, ConsistencyLevel consistencyLevel) throws HectorException;

  /**
   * Gets s keyspace with the specified consistency level and failover policy
   */
  Keyspace getKeyspace(String keyspaceName, ConsistencyLevel consistencyLevel, 
      FailoverPolicy failoverPolicy)
      throws HectorException;


  /**
   * @return all keyspaces name of this client.
   */
  List<String> getKeyspaces() throws HectorTransportException;


  /**
   * @return target server cluster name
   * @throws HectorTransportException 
   */
  String getClusterName() throws HectorException;

  /**
   * @return Server version
   */
  String getServerVersion() throws HectorException;

  CassandraHost getCassandraHost();  

  void markAsClosed();

  boolean isClosed();

  void markAsError();

  boolean hasErrors();

  void removeKeyspace(Keyspace k);

  TimestampResolution getTimestampResolution();

  /**
   * @return Whether this client has been released (returned) to the pool
   */
  boolean isReleased();

  /** Marks this client has already been released back to the pool */
  void markAsReleased();

  /** Mark that this client has been borrowed from the pool. Reverts the markAsReleased */
  void markAsBorrowed();
}
