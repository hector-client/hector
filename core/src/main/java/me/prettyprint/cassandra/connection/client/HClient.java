/**
 * 
 */
package me.prettyprint.cassandra.connection.client;


import java.util.Map;

import me.prettyprint.cassandra.service.CassandraHost;

import org.apache.cassandra.thrift.Cassandra;

/**
 * Hector Client Interface.
 * 
 * @author patricioe (Patricio Echague - patricio@datastax.com)
 * 
 */
public interface HClient {
  /**
   * Returns the time that this HClient was created.
   * 
   * @return the time this client was created
   */
  long getCreatedTime();

  /**
   * Returns a new Cassandra.Client on each invocation using the underlying
   * transport.
   * 
   * @return Cassandra.Client from the underlying transport
   * @throws IllegalStateException
   *           if it is called on a closed client
   */
  Cassandra.Client getCassandra();

  /**
   * Returns a new Cassandra.Client on each invocation using the underlying
   * transport.
   * 
   * @param keyspaceNameArg
   *          a keyspace
   * 
   * @return Cassandra.Client from the underlying transport
   * @throws IllegalStateException
   *           if it is called on a closed client
   * @throws HInvalidRequestException
   *           if the keyspace does not exist or if it is malformed
   * @throws HectorTransportException
   *           if any other error occurs
   */
  Cassandra.Client getCassandra(String keyspaceNameArg);

  /**
   * Close this client and its underlying connection.
   * 
   * @return this object
   */
  HClient close();

  /**
   * Open a connection for this client.
   * 
   * @return this object
   * @throws IllegalStateException
   *           if this method is called from a client with an open connection
   * @throws HectorTransportException
   *           if the connection cannot be established
   */
  HClient open();

  /**
   * Retrieves whether the underlying connection for this client is open or not.
   * 
   * @return <code>TRUE</code> if the underlying connection for this client is
   *         open. <code>FALSE<code> otherwise
   */
  boolean isOpen();

  /**
   * Start tracking the beginning of use for this client. This is expected to be
   * called per operation basis. Followed by a {@link HClient#getSinceLastUsed}
   */
  void startToUse();

  /**
   * Retrieves the time in milliseconds since this client was used last time.
   * 
   * @return time in milliseconds since last used.
   */
  long getSinceLastUsed();
  
  /**
   * Retrieves the CassandraHost associate to this client.
   * 
   * @return the @link {@link CassandraHost} object for this client
   */
  CassandraHost getCassandraHost();
  
  /**
   * Sets authentication credentials to the client.
   * @param credentials credentials to be set.
   */
  void setAuthenticated(Map<String, String> credentials);

 /**
   * Retrieves whether client has been authenticated with the given credentials.
   * 
   * @param credentials authentication credentials 
   * @return <code>TRUE</code> if the client has previously been authenticated using the
   * credentials, <code>FALSE<code> otherwise (wrong credentials or not authenticated)
   */
  boolean isAlreadyAuthenticated(Map<String, String> credentials);

  /**
   * Clears current authentication
   */
  void clearAuthentication();

  /**
   * Retrieves the time of the last success in milliseconds.
   *
   * @return -1 if no successful operation has already happened, or the time 
   * of the last success in milliseconds.
   */
  long getLastSuccessTime();

  /**
   * Update the time of the last success with the current time. 
   */
  void updateLastSuccessTime();
}
