/**
 * 
 */
package me.prettyprint.cassandra.connection.factory;

import me.prettyprint.cassandra.connection.client.HClient;
import me.prettyprint.cassandra.connection.client.HThriftClient;
import me.prettyprint.cassandra.connection.security.SSLHelper;
import me.prettyprint.cassandra.service.CassandraHost;

import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author patricioe (Patricio Echague - patricio@datastax.com)
 *
 */
public class HThriftClientFactoryImpl implements HClientFactory {
  
  private static final Logger log = LoggerFactory.getLogger(HThriftClientFactoryImpl.class);
  private TSSLTransportParameters params;

  /**
   * {@inheritDoc}
   */
  public HClient createClient(CassandraHost ch) {
    params = SSLHelper.getTSSLTransportParameters();
      
    log.info("SSL enabled for client<->server communications.");
    log.info("Properties:");
    log.info("  ssl.truststore = {}", System.getProperty("ssl.truststore"));
    log.info("  ssl.truststore.password = {}", System.getProperty("ssl.truststore.password"));
    log.info("  ssl.protocol = {}", System.getProperty("ssl.protocol"));
    log.info("  ssl.store.type = {}", System.getProperty("ssl.store.type"));
    log.info("  ssl.cipher.suites = {}", System.getProperty("ssl.cipher.suites"));
    
    if ( log.isDebugEnabled() ) {
      log.debug("Creation of new client for host: " + ch.getIp());
    }
    
    return params == null ? new HThriftClient(ch) : new HThriftClient(ch, params);
  }

}
