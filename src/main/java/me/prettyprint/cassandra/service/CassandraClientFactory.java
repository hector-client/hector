package me.prettyprint.cassandra.service;

import org.apache.cassandra.service.Cassandra;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Factory for {@link CassandraClient} objects.
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class CassandraClientFactory {

  public CassandraClient create(String url, int port) throws TTransportException, TException {
    return new CassandraClientImpl(createThriftClient(url, port));
  }

  public Cassandra.Client createThriftClient(String  url, int port)
      throws TTransportException , TException {
    TTransport tr = new TSocket(url, port);
    TProtocol proto = new TBinaryProtocol(tr);
    Cassandra.Client client = new Cassandra.Client(proto);
    tr.open();
    return client;
  }

}
