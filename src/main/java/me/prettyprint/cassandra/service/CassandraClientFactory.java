package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.model.KeyspaceFactory;

import org.apache.cassandra.service.Cassandra;
import org.apache.commons.pool.BasePoolableObjectFactory;
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
public class CassandraClientFactory extends BasePoolableObjectFactory
    implements PoolableObjectFactory {

  private static final Logger log = LoggerFactory.getLogger(CassandraClientFactory.class);
  /**
   * The pool associated with this client factory.
   */
  private final CassandraClientPoolStore pools;
  private final String url;
  private final int port;

  public CassandraClientFactory(CassandraClientPoolStore pools, String url, int port) {
    this.pools = pools;
    this.url = url;
    this.port = port;
  }

  public CassandraClient create() throws TTransportException, TException {
    return new CassandraClientImpl(createThriftClient(url, port), new KeyspaceFactory(), url, port,
        pools);
  }

  private Cassandra.Client createThriftClient(String  url, int port)
      throws TTransportException , TException {
    TTransport tr = new TSocket(url, port);
    TProtocol proto = new TBinaryProtocol(tr);
    Cassandra.Client client = new Cassandra.Client(proto);
    tr.open();
    return client;
  }

  @Override
  public void activateObject(Object obj) throws Exception {
    // nada
  }

  @Override
  public void destroyObject(Object obj) throws Exception {
    CassandraClient client = (CassandraClient)obj ;
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
    return validateClient((CassandraClient)obj);
  }

  private boolean validateClient(me.prettyprint.cassandra.service.CassandraClient obj) {
    // TODO send fast and easy request to cassandra
    return true;
  }

  private static void closeClient(CassandraClient cclient) {
    Cassandra.Client client = cclient.getCassandra();
    client.getInputProtocol().getTransport().close();
    client.getOutputProtocol().getTransport().close();
    cclient.markAsClosed();
  }

}
