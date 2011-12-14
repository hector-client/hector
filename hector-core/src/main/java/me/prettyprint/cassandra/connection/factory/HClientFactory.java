/**
 * 
 */
package me.prettyprint.cassandra.connection.factory;

import me.prettyprint.cassandra.connection.client.HClient;
import me.prettyprint.cassandra.service.CassandraHost;

/**
 * Basic interface for all implementations of this factory.
 * 
 * @author patricioe (Patricio Echague - patricio@datastax.com)
 *
 */
public interface HClientFactory {
  
  /**
   * Creates a Hector Client against the host represented by <code>ch</code>
   * 
   * @param ch a {@link CassandraHost} 
   * @return a new HClient
   */
  HClient createClient(CassandraHost ch);

}
