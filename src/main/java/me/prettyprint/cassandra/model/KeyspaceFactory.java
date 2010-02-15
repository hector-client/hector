package me.prettyprint.cassandra.model;

import java.util.Map;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraClientFactory;
import me.prettyprint.cassandra.service.CassandraClientMonitor;
import me.prettyprint.cassandra.service.JmxMonitor;
import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class KeyspaceFactory {

  private static final Logger log = LoggerFactory.getLogger(KeyspaceFactory.class);
  public Keyspace create(CassandraClient cassandraClient, String keyspaceName,
      Map<String, Map<String, String>> keyspaceDesc, int consistencyLevel,
      FailoverPolicy failoverPolicy, CassandraClientFactory clientFactory)
      throws TException {

    CassandraClientMonitor monitor = null;
    try {
      monitor = JmxMonitor.INSTANCE.getCassandraMonitor();
    } catch (MalformedObjectNameException e) {
      // TODO(ran): Think of a better way to recover from this error.
      log.error("Unable to register JMX monitor", e);
    } catch (InstanceAlreadyExistsException e) {
      log.error("Unable to register JMX monitor", e);
    } catch (MBeanRegistrationException e) {
      log.error("Unable to register JMX monitor", e);
    } catch (NotCompliantMBeanException e) {
      log.error("Unable to register JMX monitor", e);
    }
    return new KeyspaceImpl(cassandraClient, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientFactory, monitor);
  }
}
