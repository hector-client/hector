package me.prettyprint.cassandra.service;

import java.util.List;

import me.prettyprint.hector.api.ddl.HKsDef;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.KsDef;


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
  KeyspaceService getKeyspace(String keyspaceName) throws HectorException;

  /**
   * Gets s keyspace with the specified consistency level
   */
  KeyspaceService getKeyspace(String keyspaceName, ConsistencyLevel consistencyLevel) throws HectorException;

  /**
   * Gets s keyspace with the specified consistency level and failover policy
   */
  KeyspaceService getKeyspace(String keyspaceName, ConsistencyLevel consistencyLevel, 
      FailoverPolicy failoverPolicy)
      throws HectorException;



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

  void removeKeyspace(KeyspaceService k);

  ClockResolution getClockResolution();

  /**
   * @return Whether this client has been released (returned) to the pool
   */
  boolean isReleased();

  /** Marks this client has already been released back to the pool */
  void markAsReleased();

  /** Mark that this client has been borrowed from the pool. Reverts the markAsReleased */
  void markAsBorrowed();
}
