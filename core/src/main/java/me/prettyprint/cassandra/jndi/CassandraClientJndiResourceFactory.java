package me.prettyprint.cassandra.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A factory for JNDI Resource managed objects. Responsible for creating a 
 * {@link Keyspace} references for passing to {@link HFactory}.  
 * A limited set of configuration parameters are supported. 
 * These parameters are defined in a web application's context.xml file.  
 * Parameter descriptions can be found in {@link CassandraHostConfigurator}
 * 
 * <p>
 * <pre>
 *     <Resource name="cassandra/CassandraClientFactory"
 *               auth="Container"
 *               type="me.prettyprint.cassandra.api.Keyspace"
 *               factory="me.prettyprint.cassandra.jndi.CassandraClientJndiResourceFactory"
 *               hosts="cass1:9160,cass2:9160,cass3:9160"
 *               keyspace="Keyspace1"
 *               clusterName="Test Cluster" 
 *               maxActive="20"
 *               maxWaitTimeWhenExhausted="10"
 *               autoDiscoverHosts="true"
 *               runAutoDiscoveryAtStartup="true"/>      
 * </pre>
 *     
 * @author Perry Hoekstra (dutchman_mn@charter.net)
 * @author zznate
 * 
 * @since 0.5.1-8
 */

public class CassandraClientJndiResourceFactory implements ObjectFactory {
  private Logger log = LoggerFactory.getLogger(CassandraClientJndiResourceFactory.class);
  
  private CassandraHostConfigurator cassandraHostConfigurator;
  private Cluster cluster;
  private Keyspace keyspace;
  
  /**
   * Creates an object using the location or reference information specified. 
   * 
   * @param object       The possibly null object containing location or reference information that 
   *                     can be used in creating an object.
   * @param jndiName     The name of this object relative to nameCtx, or null if no name is 
   *                     specified.
   * @param context      The context relative to which the name parameter is specified, or null 
   *                     if name is relative to the default initial context.
   * @param environment  The possibly null environment that is used in creating the object. 
   * 
   * @return Object - The object created; null if an object cannot be created. 
   * 
   * @exception Exception - if this object factory encountered an exception while attempting 
   *                        to create an object, and no other object factories are to be tried.
   */
  public Object getObjectInstance(Object object, Name jndiName, Context context,
      Hashtable<?, ?> environment) throws Exception {  
    Reference resourceRef = null;
    
    if (object instanceof Reference) {
        resourceRef = (Reference) object;
    } else {
      throw new Exception("Object provided is not a javax.naming.Reference type");
    }
    
    // config CassandraHostConfigurator  
    if ( cluster == null ) {
      configure(resourceRef);
    }

    return keyspace;
  }
  
  private void configure(Reference resourceRef) throws Exception {
 // required
    RefAddr hostsRefAddr = resourceRef.get("hosts");
    RefAddr clusterNameRef = resourceRef.get("clusterName");
    RefAddr keyspaceNameRef = resourceRef.get("keyspace");
    // optional
    RefAddr maxActiveRefAddr = resourceRef.get("maxActive");
    RefAddr maxWaitTimeWhenExhausted = resourceRef.get("maxWaitTimeWhenExhausted");
    RefAddr maxExhaustedTimeBeforeMarkingAsDown = resourceRef.get("maxExhaustedTimeBeforeMarkingAsDown");
    RefAddr autoDiscoverHosts = resourceRef.get("autoDiscoverHosts");
    RefAddr runAutoDiscoverAtStartup = resourceRef.get("runAutoDiscoveryAtStartup");
    RefAddr retryDownedHostDelayInSeconds = resourceRef.get("retryDownedHostDelayInSeconds");
    
    if ( hostsRefAddr == null || hostsRefAddr.getContent() == null) {
      throw new Exception("A url and port on which Cassandra is installed and listening " + 
      "on must be provided as a ResourceParams in the context.xml");
    }        

    cassandraHostConfigurator = new CassandraHostConfigurator((String)hostsRefAddr.getContent());
    if ( autoDiscoverHosts != null ) {
      cassandraHostConfigurator.setAutoDiscoverHosts(Boolean.parseBoolean((String)autoDiscoverHosts.getContent()));
      if ( runAutoDiscoverAtStartup  != null )
        cassandraHostConfigurator.setRunAutoDiscoveryAtStartup(Boolean.parseBoolean((String)autoDiscoverHosts.getContent()));
    }    
    if ( retryDownedHostDelayInSeconds != null ) {
      int retryDelay = Integer.parseInt((String)retryDownedHostDelayInSeconds.getContent());
      // disable retry if less than 1
      if ( retryDelay < 1 )
        cassandraHostConfigurator.setRetryDownedHosts(false);      
      cassandraHostConfigurator.setRetryDownedHostsDelayInSeconds(retryDelay);
    }
    if ( maxActiveRefAddr != null ) 
      cassandraHostConfigurator.setMaxActive(Integer.parseInt((String)maxActiveRefAddr.getContent()));
    if ( maxWaitTimeWhenExhausted != null ) 
      cassandraHostConfigurator.setMaxWaitTimeWhenExhausted(Integer.parseInt((String)maxWaitTimeWhenExhausted.getContent()));
      if (maxExhaustedTimeBeforeMarkingAsDown != null) {
        cassandraHostConfigurator.setMaxExhaustedTimeBeforeMarkingAsDown(Integer.parseInt((String) maxExhaustedTimeBeforeMarkingAsDown.getContent()));
      }
    
    if ( log.isDebugEnabled() )
      log.debug("JNDI resource created with CassandraHostConfiguration: {}", cassandraHostConfigurator.getAutoDiscoverHosts());
    
    cluster = HFactory.getOrCreateCluster((String)clusterNameRef.getContent(), cassandraHostConfigurator);
    keyspace = HFactory.createKeyspace((String)keyspaceNameRef.getContent(), cluster);
  }
}
