package me.prettyprint.cassandra.service;


import me.prettyprint.cassandra.service.CassandraClientMonitor.Counter;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory for {@link CassandraClient} objects.
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
/*package*/ class CassandraClientFactory implements PoolableObjectFactory {

  /** Socket timeout */
  private final int timeout;

  private final CassandraClientMonitor clientMonitor;

  private static final Logger log = LoggerFactory.getLogger(CassandraClientFactory.class);
  /**
   * The pool associated with this client factory.
   */
  private final CassandraClientPool pool;
  private final boolean useThriftFramedTransport;
  private final ClockResolution clockResolution;
  private final CassandraHost cassandraHost;

  public CassandraClientFactory(CassandraClientPool pools, CassandraHost cassandraHost,
      CassandraClientMonitor clientMonitor) {
    this.pool = pools;
    this.cassandraHost = cassandraHost;
    timeout = getTimeout(cassandraHost);
    this.clientMonitor = clientMonitor;
    this.useThriftFramedTransport = cassandraHost.getUseThriftFramedTransport();
    clockResolution = cassandraHost.getClockResolution();
  }

  /**
   * CassandraClientFactory constructor.  A default {@link CassandraClientPool}
   * and {@link CassandraClientMonitor} will be initialized.
   *
   * @param url
   * @param port
   */
  public CassandraClientFactory(String url, int port) {
    this.clientMonitor = new CassandraClientMonitor();
    this.pool = new CassandraClientPoolImpl(this.clientMonitor);
    this.cassandraHost = new CassandraHost(url,port);
    timeout = getTimeout(null);
    this.useThriftFramedTransport = CassandraHost.DEFAULT_USE_FRAMED_THRIFT_TRANSPORT;
    clockResolution = CassandraHost.DEFAULT_TIMESTAMP_RESOLUTION;
  }

  public CassandraClient create() throws HectorException {
    CassandraClient c;
    try {
      c = new CassandraClientImpl(createThriftClient(cassandraHost),
          new KeyspaceServiceFactory(clientMonitor), cassandraHost, pool,
          pool.getCluster(), clockResolution);
    } catch (Exception e) {
      throw new HectorException(e);
    }
    if ( log.isDebugEnabled() ) {
      log.debug("Creating client {}", c);
    }
    return c;
  }
  
  public CassandraClient tearDown(CassandraClient cassandraClient) throws HectorException {
    Cassandra.Client client = cassandraClient.getCassandra();
    if ( client != null ) {
      if ( client.getInputProtocol() != null ) {
        if ( client.getInputProtocol().getTransport() != null ) {
          try {
            client.getInputProtocol().getTransport().flush();
            client.getInputProtocol().getTransport().close();
          } catch (Exception e) {
            log.error("Could not close transport in tearDown", e);
          }
        }
      }
    }
    return cassandraClient;
  }

  private Cassandra.Client createThriftClient(CassandraHost cassandraHost)
      throws HectorTransportException {
    if ( log.isDebugEnabled() ) {
      log.debug("Creating a new thrift connection to {}", cassandraHost);
    }
    TTransport tr;
    if (useThriftFramedTransport) {
      tr = new TFramedTransport(new TSocket(cassandraHost.getHost(), cassandraHost.getPort(), timeout));
    } else {
      tr = new TSocket(cassandraHost.getHost(), cassandraHost.getPort(), timeout);
    }
    TProtocol proto = new TBinaryProtocol(tr);
    Cassandra.Client client = new Cassandra.Client(proto);
    try {
      tr.open();
    } catch (TTransportException e) {
      // Thrift exceptions aren't very good in reporting, so we have to catch the exception here and
      // add details to it.
      log.error("Unable to open transport to " + cassandraHost.getName(), e);
      clientMonitor.incCounter(Counter.CONNECT_ERROR);
      throw new HectorTransportException("Unable to open transport to " + cassandraHost.getName() +" , " +
          e.getLocalizedMessage(), e);
    }
    return client;
  }

  /**
   * If CassandraHost was not null we use {@link CassandraHost#getCassandraThriftSocketTimeout()}
   * if it was greater than zero. Otherwise look for an environment
   * variable name CASSANDRA_THRIFT_SOCKET_TIMEOUT value.
   * If doesn't exist, returns 0.
   * @param cassandraHost
   */
  private int getTimeout(CassandraHost cassandraHost) {
    int timeoutVar = 0;
    if ( cassandraHost != null && cassandraHost.getCassandraThriftSocketTimeout() > 0 ) {
      timeoutVar = cassandraHost.getCassandraThriftSocketTimeout();
    } else {
    String timeoutStr = System.getProperty(
        SystemProperties.CASSANDRA_THRIFT_SOCKET_TIMEOUT.toString());
    if (timeoutStr != null && timeoutStr.length() > 0) {
      try {
        timeoutVar = Integer.valueOf(timeoutStr);
      } catch (NumberFormatException e) {
        log.error("Invalid value for CASSANDRA_THRIFT_SOCKET_TIMEOUT", e);
      }
    }
    }
    return timeoutVar;
  }

  @Override
  public void activateObject(Object obj) throws Exception {
    // nada
  }

  @Override
  public void destroyObject(Object obj) throws Exception {
    CassandraClient client = (CassandraClient) obj ;
    if ( log.isDebugEnabled() ) {
      log.debug("Closing client {} (thread={})", client, Thread.currentThread().getName());
    }
    closeClient(client);
  }

  @Override
  public Object makeObject() throws Exception {
    if ( log.isDebugEnabled() ) {
      log.debug("Creating a new client... (thread={})", Thread.currentThread().getName());
    }
    CassandraClient c = create();
    if ( log.isDebugEnabled() ) {
      log.debug("New client created: {} (thread={})", c, Thread.currentThread().getName());
    }
    return c;
  }

  @Override
  public boolean validateObject(Object obj) {
    return validateClient((CassandraClient) obj);
  }

  private boolean validateClient(CassandraClient client) {
    return !client.isClosed() && !client.hasErrors();
  }

  private void closeClient(CassandraClient cclient) {
    if ( log.isDebugEnabled() ) {
      log.debug("Closing client {}", cclient);
    }
    ((CassandraClientPoolImpl) pool).reportDestroyed(cclient);
    Cassandra.Client client = cclient.getCassandra();
    client.getInputProtocol().getTransport().close();
    client.getOutputProtocol().getTransport().close();
    cclient.markAsClosed();
  }

  @Override
  public void passivateObject(Object obj) throws Exception {
    // none
  }

}
