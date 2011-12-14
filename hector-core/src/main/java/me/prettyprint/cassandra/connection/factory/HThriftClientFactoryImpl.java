/**
 * 
 */
package me.prettyprint.cassandra.connection.factory;

import me.prettyprint.cassandra.connection.client.HClient;
import me.prettyprint.cassandra.connection.client.HThriftClient;
import me.prettyprint.cassandra.service.CassandraHost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author patricioe (Patricio Echague - patricio@datastax.com)
 *
 */
public class HThriftClientFactoryImpl implements HClientFactory {
  
  private static final Logger log = LoggerFactory.getLogger(HThriftClientFactoryImpl.class);

  /**
   * {@inheritDoc}
   */
  public HClient createClient(CassandraHost ch) {
    if ( log.isDebugEnabled() ) {
      log.debug("Creation of new client for host: " + ch.getIp());
    }
    return new HThriftClient(ch);
  }

}
