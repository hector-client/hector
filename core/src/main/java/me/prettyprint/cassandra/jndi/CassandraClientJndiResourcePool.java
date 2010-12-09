package me.prettyprint.cassandra.jndi;



import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * Creates a pool of {@link CassandraClient} suitable for use in application servers such
 * as Apache Tomcat.
 *
 * @see GenericObjectPool
 * 
 * @author Perry Hoekstra (dutchman_mn@charter.net)
 *
 */
public class CassandraClientJndiResourcePool extends GenericObjectPool {
  /**
   * CassandraClientJndiResourcePool constructor.
   * 
   * @param url   url of the host that contains Cassandra.
   * @param port  port number that Cassandra is listening on.
   */
  
  public CassandraClientJndiResourcePool(String url, int port) {
    // TODO fix this
    //super(new JndiCassandraClientFactory(url, port)); 
  }
}
