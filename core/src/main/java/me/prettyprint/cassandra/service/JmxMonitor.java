package me.prettyprint.cassandra.service;

import java.lang.management.ManagementFactory;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.management.*;
import me.prettyprint.cassandra.connection.HConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A JMX monitor singlton.
 *
 * @author Ran Tavory (ran@outbain.com)
 *
 */
public class JmxMonitor {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private MBeanServer mbs;
  private static JmxMonitor monitorInstance;
  private Map<String,CassandraClientMonitor> monitors;

  private JmxMonitor() {
    mbs = ManagementFactory.getPlatformMBeanServer();
    monitors = new HashMap<String, CassandraClientMonitor>();    
  }

  public static JmxMonitor getInstance() {
    if ( monitorInstance == null ) {
      monitorInstance = new JmxMonitor();
    }
    return monitorInstance;
  }


  public void registerMonitor(String name, String monitorType, Object monitoringInterface)
      throws MalformedObjectNameException, InstanceAlreadyExistsException,
      MBeanRegistrationException, NotCompliantMBeanException {

    String monitorName = generateMonitorName(name, monitorType);
    log.info("Registering JMX {}", monitorName);

    ObjectName oName = new ObjectName(monitorName);

    // Check if the monitor is already registered
    if (mbs.isRegistered(oName)) {
      log.info("Monitor already registered: {}", oName);
      return;
    }

    mbs.registerMBean(monitoringInterface, oName);
  }
  
  public void unregisterMonitor(String name, String monitorType)
      throws MalformedObjectNameException, InstanceAlreadyExistsException,
      MBeanRegistrationException, NotCompliantMBeanException {

    String monitorName = generateMonitorName(name, monitorType);
    log.info("Unregistering JMX {}", monitorName);

    ObjectName oName = new ObjectName(monitorName);

    // Check if the monitor is already registered
    if (!mbs.isRegistered(oName)) {
    	log.info("Monitor is not registered: {}", oName);
      return;
    }

    try
    {
	    mbs.unregisterMBean(oName);
    }
    catch(InstanceNotFoundException e)
    {
    	log.warn("Failed to unregister monitor: {}" + oName.toString(), e);
    }
  }

  private String generateMonitorName(String className, String monitorType) {
    StringBuilder sb = new StringBuilder();
    sb.append(className);
    sb.append(":ServiceType=");
    // append the classloader name so we have unique names in web apps.
    sb.append(getUniqueClassloaderIdentifier());
    if (null != monitorType && monitorType.length() > 0) {
      sb.append(",MonitorType=" + monitorType);
    }
    return sb.toString();
  }

  /**
   * Generates a unique, but still nice and predictable name representing this classloader so that
   * even apps operating under a web server such as tomcat with multiple classloaders would bee able
   * to register each with its own unique mbean.
   */
  private String getUniqueClassloaderIdentifier() {
    String contextPath = getContextPath();
    if (contextPath != null) {
      return contextPath;
    }
    return "hector";
  }

  /**
   * Tries to guess a context path for the running application.
   * If this is a web application running under a tomcat server this will work.
   * If unsuccessful, returns null.
   * @return A string representing the current context path or null if it cannot be determined.
   */
  private String getContextPath() {
    ClassLoader loader = getClass().getClassLoader();
    if(loader == null)
     return null;
    URL url = loader.getResource("/");
    if (url != null) {
      String[] elements = url.toString().split("/");
      for (int i = elements.length - 1; i > 0; --i) {
        // URLs look like this: file:/.../ImageServer/WEB-INF/classes/
        // And we want that part that's just before WEB-INF
        if ("WEB-INF".equals(elements[i])) {
          return elements[i - 1];
        }
      }
    }
    return null;
  }

  public CassandraClientMonitor getCassandraMonitor(HConnectionManager connectionManager) {
    CassandraClientMonitor cassandraClientMonitor = monitors.get(connectionManager.getClusterName());
    if ( cassandraClientMonitor == null ) {
      try {
        cassandraClientMonitor = new CassandraClientMonitor(connectionManager);
        registerMonitor("me.prettyprint.cassandra.service_"+connectionManager.getClusterName(), "hector",
            cassandraClientMonitor);
        monitors.put(connectionManager.getClusterName(), cassandraClientMonitor);
      } catch (MalformedObjectNameException e) {
        log.error("Unable to register JMX monitor", e);
      } catch (InstanceAlreadyExistsException e) {
        log.error("Unable to register JMX monitor", e);
      } catch (MBeanRegistrationException e) {
        log.error("Unable to register JMX monitor", e);
      } catch (NotCompliantMBeanException e) {
        log.error("Unable to register JMX monitor", e);
      }
    }
    return cassandraClientMonitor;
  }

  public void removeCassandraMonitor(HConnectionManager connectionManager) {
  	CassandraClientMonitor cassandraClientMonitor = monitors.remove(connectionManager.getClusterName());
  	if(cassandraClientMonitor != null) {
  		try
      {
	      unregisterMonitor("me.prettyprint.cassandra.service_"+connectionManager.getClusterName(), "hector");
      }
      catch(MalformedObjectNameException e)
      {
        log.error("Unable to unregister JMX monitor", e);
      }
      catch(InstanceAlreadyExistsException e)
      {
        log.error("Unable to unregister JMX monitor", e);
      }
      catch(MBeanRegistrationException e)
      {
        log.error("Unable to unregister JMX monitor", e);
      }
      catch(NotCompliantMBeanException e)
      {
        log.error("Unable to unregister JMX monitor", e);
      }
  	}
  }
}
