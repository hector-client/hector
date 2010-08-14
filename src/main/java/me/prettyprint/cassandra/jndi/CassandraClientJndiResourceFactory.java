package me.prettyprint.cassandra.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

/**
 * A factory for JNDI Resource managed objects. Responsible for creating a 
 * {@link CassandraClientJndiResourcePool}. Relies on a supplied URL and 
 * port which Cassandra is listening on.  These parameters are defined in 
 * a web application's context.xml file.  For example:
 * 
 * <p>
 * <pre>
 *     <Resource name="cassandra/CassandraClientFactory"
 *               auth="Container"
 *               type="me.prettyprint.cassandra.jndi.CassandraClientJndiResourcePool"
 *               factory="me.prettyprint.cassandra.jndi.CassandraClientJndiResourceFactory"
 *               url="localhost"
 *               port="9160" />      
 * </pre>
 *     
 * @author Perry Hoekstra (dutchman_mn@charter.net)
 * 
 * @since 0.5.1-8
 */

public class CassandraClientJndiResourceFactory implements ObjectFactory 
{
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
    
    RefAddr urlRefAddr = resourceRef.get("url");
    
    RefAddr portRefAddr = resourceRef.get("port");
  
    if ((urlRefAddr != null) && (portRefAddr != null)) {
      return new CassandraClientJndiResourcePool((String)urlRefAddr.getContent(), 
                                               Integer.parseInt((String)portRefAddr.getContent())); 
    }  else {
      throw new Exception("A url and port on which Cassandra is installed and listening " + 
                      "on must be provided as a ResourceParams in the context.xml");
    }
  }
}
