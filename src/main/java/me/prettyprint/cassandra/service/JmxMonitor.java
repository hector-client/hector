package me.prettyprint.cassandra.service;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A JMX monitor singlton.
 *
 * @author Ran Tavory (ran@outbain.com)
 *
 */
public enum JmxMonitor {
  INSTANCE;

  private static final Logger log = LoggerFactory.getLogger(JmxMonitor.class);

  private final MBeanServer mbs;
  private CassandraClientMonitor cassandraClientMonitor;

  private JmxMonitor() {
    mbs = ManagementFactory.getPlatformMBeanServer();
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

  public CassandraClientMonitor getCassandraMonitor(CassandraClient client) {
    if (cassandraClientMonitor == null) {
      cassandraClientMonitor = new CassandraClientMonitor();
      cassandraClientMonitor.addClient(client);
      try {
        registerMonitor(CassandraClientMonitor.class.getName(), "hector", cassandraClientMonitor);
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

  private String generateMonitorName(String className, String monitorType) {
    StringBuilder sb = new StringBuilder();
    sb.append(className);
    sb.append(":ServiceType=Hector-");
    // append the classloader name so we have unique names in web apps.
    sb.append(getClass().getClassLoader().toString());
    if (null != monitorType && monitorType.length() > 0) {
      sb.append(",MonitorType=" + monitorType);
    }
    return sb.toString();
  }
}
