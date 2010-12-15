package me.prettyprint.cassandra.service;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

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
  private CassandraClientMonitor cassandraClientMonitor;
  private static JmxMonitor monitorInstance;

  private JmxMonitor(HConnectionManager connectionManager) {
    CassandraClientMonitor cassandraClientMonitor = new CassandraClientMonitor(connectionManager);
    mbs = ManagementFactory.getPlatformMBeanServer();
    this.cassandraClientMonitor = cassandraClientMonitor;
    try {
      registerMonitor("me.prettyprint.cassandra.service", "hector",
          cassandraClientMonitor);
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

  public static JmxMonitor getInstance(HConnectionManager connectionManager) {
    if ( monitorInstance == null ) {
      monitorInstance = new JmxMonitor(connectionManager);
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

    // Register perf4j monitors
    if ("true".equalsIgnoreCase(
        System.getProperty("com.prettyprint.cassandra.load_hector_log4j", "true"))) {
      registerPerf4J();
    }
  }

  private void registerPerf4J() {
    URL url = getClass().getClassLoader().getResource("hectorLog4j.xml");
    if (url == null) {
      log.warn("Unable to locate hectorLog4j.xml; performance counters will not be exported");
    } else {
      try {
        final Class<?> domConfiguratorClass = getClass().getClassLoader().loadClass("org.apache.log4j.xml.DOMConfigurator");
        final Method method = domConfiguratorClass.getMethod( "configure", URL.class );
        method.invoke( null, url );
      } catch( ClassNotFoundException e ) {
        log.warn("Unable to load log4j's DOMConfigurator. Performance counters will not be exported. To fix, include the log4j jar in your application's classpath.");
      } catch( SecurityException e ) {
        log.error( "Could not access method DOMConfigurator.configure(URL)", e );
      } catch( NoSuchMethodException e ) {
        log.error( "Could not find method DOMConfigurator.configure(URL)", e );
      } catch( IllegalArgumentException e ) {
        log.error( "Could not invoke method DOMConfigurator.configure(URL)", e );
      } catch( IllegalAccessException e ) {
        log.error( "Could not invoke method DOMConfigurator.configure(URL)", e );
      } catch( InvocationTargetException e ) {
        throw new RuntimeException(e.getCause());
      }
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
    URL url = getClass().getClassLoader().getResource("/");
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

  public CassandraClientMonitor getCassandraMonitor() {
    return cassandraClientMonitor;
  }

}
