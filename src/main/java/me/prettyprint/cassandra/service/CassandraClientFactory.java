package me.prettyprint.cassandra.service;


import java.net.UnknownHostException;

import org.apache.cassandra.service.Cassandra;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
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
  private final String url;
  private final int port;

  public CassandraClientFactory(CassandraClientPool pools, String url, int port,
      CassandraClientMonitor clientMonitor) {
    this.pool = pools;
    this.url = url;
    this.port = port;
    timeout = getTimeout();
    this.clientMonitor = clientMonitor;
  }

  public CassandraClient create() throws TTransportException, TException, UnknownHostException {
    return new CassandraClientImpl(createThriftClient(url, port),
        new KeyspaceFactory(clientMonitor), url, port, pool);
  }

  private Cassandra.Client createThriftClient(String  url, int port)
      throws TTransportException , TException {
    TTransport tr = new TSocket(url, port, timeout);
    TProtocol proto = new TBinaryProtocol(tr);
    Cassandra.Client client = new Cassandra.Client(proto);
    try {
      tr.open();
    } catch (TTransportException e) {
      // Thrift exceptions aren't very good in reporting, so we have to catch the exception here and
      // add details to it.
      log.error("Unable to open transport to " + url + ":" + port, e);
      throw new TTransportException("Unable to open transport to " + url + ":" + port + " , " +
          e.getLocalizedMessage(), e);
    }
    return client;
  }

  /**
   * Gets an environment variable CASSANDRA_THRIFT_SOCKET_TIMEOUT value.
   * If doesn't exist, returns 0.
   */
  private int getTimeout() {
    String timeoutStr = System.getProperty("CASSANDRA_THRIFT_SOCKET_TIMEOUT");
    if (timeoutStr == null || timeoutStr.length() == 0) {
      return  0;
    } else {
      try {
        return Integer.valueOf(timeoutStr);
      } catch (NumberFormatException e) {
        log.error("Invalid value for CASSANDRA_THRIFT_SOCKET_TIMEOUT", e);
        return 0;
      }
    }
  }

  @Override
  public void activateObject(Object obj) throws Exception {
    // nada
  }

  @Override
  public void destroyObject(Object obj) throws Exception {
    CassandraClient client = (CassandraClient) obj ;
    log.debug("Close client {}", client);
    closeClient(client);
  }

  @Override
  public Object makeObject() throws Exception {
    log.debug("Creating a new client...");
    CassandraClient c = create();
    log.debug("New client created: {}", c);
    return c;
  }

  @Override
  public boolean validateObject(Object obj) {
    return validateClient((CassandraClient) obj);
  }

  private boolean validateClient(CassandraClient client) {
    // TODO send fast and easy request to cassandra
    return !client.isClosed() && !client.hasErrors();
  }

  private void closeClient(CassandraClient cclient) {
    ((CassandraClientPoolImpl) pool).reportDestroyed(cclient);
    Cassandra.Client client = cclient.getCassandra();
    client.getInputProtocol().getTransport().close();
    client.getOutputProtocol().getTransport().close();
    cclient.markAsClosed();
  }

  @Override
  public void passivateObject(Object obj) throws Exception {
    // TODO Auto-generated method stub
  }

}
