package me.prettyprint.cassandra.service.template;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory to remove Cassandra configuration concerns from DAO objects. 
 * This is invoked via a spring factory method that allows injection of the
 * Hector Cluster object into the DAO. 
 * <p>
 * 
 * </p>
 * @author david
 * @since Jan 14, 2011
 *
 */
public class CassandraClusterFactory
{
  static final Logger LOGGER = LoggerFactory.getLogger( CassandraClusterFactory.class );
  
  public static Cluster getInstance( String name, String host, int port )
  {
    LOGGER.debug( "getInstance: creating cluster name=" + name + ", host=" + host + ", port=" + port );
    return HFactory.getOrCreateCluster( name, host + ":" + port );
    
  }
}
