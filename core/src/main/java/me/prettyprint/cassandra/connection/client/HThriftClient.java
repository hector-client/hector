package me.prettyprint.cassandra.connection.client;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.ExceptionsTranslator;
import me.prettyprint.cassandra.service.ExceptionsTranslatorImpl;
import me.prettyprint.cassandra.service.SystemProperties;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * It expects few system properties to be set up if it uses SSL:
 * <ul>
 * <li><code>ssl.truststore</code> File path for trust store
 * <li><code>ssl.truststore.password</code> Password for trust store
 * <li><code>ssl.protocol</code> SSL protocol, default SSL
 * <li><code>ssl.store.type</code> Store type, default JKS
 * <li><code>ssl.cipher.suites</code> Cipher suites
 * </ul>
 * <p>
*/
public class HThriftClient implements HClient {
  private long createdTime = System.currentTimeMillis();

  private static Logger log = LoggerFactory.getLogger(HThriftClient.class);

  private static final String NAME_FORMAT = "CassandraClient<%s-%d>";

  private static final AtomicLong serial = new AtomicLong(0);

  final CassandraHost cassandraHost;
  final ExceptionsTranslator exceptionsTranslator;

  private final long mySerial;
  protected final int timeout;
  protected String keyspaceName;
  private long useageStartTime;

  protected TTransport transport;
  protected Cassandra.Client cassandraClient;
  private TSSLTransportParameters params;
  
  private volatile long lastSuccessTime;

  private final Map<String, String> credentials = new HashMap<String, String>();

  /**
   * Constructor
   * @param cassandraHost
   */
  public HThriftClient(CassandraHost cassandraHost) {
    this.cassandraHost = cassandraHost;
    this.timeout = getTimeout(cassandraHost);
    mySerial = serial.incrementAndGet();
    exceptionsTranslator = new ExceptionsTranslatorImpl();
  }

  /**
   * Constructor
   * @param cassandraHost
   * @param params
   */
  public HThriftClient(CassandraHost cassandraHost, TSSLTransportParameters params) {
    this.cassandraHost = cassandraHost;
    this.timeout = getTimeout(cassandraHost);
    this.params = params;
    mySerial = serial.incrementAndGet();
    exceptionsTranslator = new ExceptionsTranslatorImpl();
  }
  /**
   * {@inheritDoc}
   */
  public Cassandra.Client getCassandra() {
    if ( !isOpen() ) {
      throw new IllegalStateException("getCassandra called on client that was not open. You should not have gotten here.");
    }
    if ( cassandraClient == null ) {
      cassandraClient = new Cassandra.Client(new TBinaryProtocol(transport));
    }
    return cassandraClient;
  }

  /**
   * {@inheritDoc}
   */
  public Cassandra.Client getCassandra(String keyspaceNameArg) {
    getCassandra();
    if ( keyspaceNameArg != null && !StringUtils.equals(keyspaceName, keyspaceNameArg)) {
      if ( log.isDebugEnabled() )
        log.debug("keyspace reseting from {} to {}", keyspaceName, keyspaceNameArg);
      try {
        cassandraClient.set_keyspace(keyspaceNameArg);
      } catch (InvalidRequestException ire) {
        throw new HInvalidRequestException(ire);
      } catch (TException e) {
    	throw exceptionsTranslator.translate(e);
      }
      keyspaceName = keyspaceNameArg;
    }
    return cassandraClient;
  }

  /**
   * {@inheritDoc}
   */
  public HThriftClient close() {
    if ( log.isDebugEnabled() ) {
      log.debug("Closing client {}", this);
    }
    if ( isOpen() ) {
      try {
        transport.flush();
      } catch (Exception e) {
        log.error("Could not flush transport (to be expected if the pool is shutting down) in close for client: " + toString(), e);
      } finally {
        try {
          transport.close();
        } catch (Exception e) {
          log.error("Error on transport close for client: " +toString(), e);
        }
      }
    }
    return this;
  }

  /**
   * {@inheritDoc}
   */
  public HThriftClient open() {
    if ( isOpen() ) {
      throw new IllegalStateException("Open called on already open connection. You should not have gotten here.");
    }
    if ( log.isDebugEnabled() ) {
      log.debug("Creating a new thrift connection to {}", cassandraHost);
    }

    TSocket socket;    
    try {
        socket = params == null ? 
                                new TSocket(cassandraHost.getHost(), cassandraHost.getPort(), timeout)
                                : TSSLTransportFactory.getClientSocket(cassandraHost.getHost(), cassandraHost.getPort(), timeout, params);
    } catch (TTransportException e) {
        throw new HectorTransportException("Could not get client socket: ", e);
    }
    
    if ( cassandraHost.getUseSocketKeepalive() ) {
      try {
        socket.getSocket().setKeepAlive(true);
      } catch (SocketException se) {
        throw new HectorTransportException("Could not set SO_KEEPALIVE on socket: ", se);
      }
    }

    transport = maybeWrapWithTFramedTransport(socket);

    // If using SSL, the socket will already be connected, and TFramedTransport and
    // TSocket just wind up calling socket.isConnected(), so check this before calling
    // open() to avoid a "Socket already connected" error.
    if (!transport.isOpen()) {
      try {
        transport.open();
      } catch (TTransportException e) {
        // Thrift exceptions aren't very good in reporting, so we have to catch the exception here and
        // add details to it.
        log.debug("Unable to open transport to " + cassandraHost.getName());
        //clientMonitor.incCounter(Counter.CONNECT_ERROR);
        throw new HectorTransportException("Unable to open transport to " + cassandraHost.getName() +" , " +
            e.getLocalizedMessage(), e);
      }
    }
    return this;
  }

  protected TTransport maybeWrapWithTFramedTransport(TTransport transport) {
    if (cassandraHost.getUseThriftFramedTransport()) {
      return new TFramedTransport(transport, cassandraHost.getMaxFrameSize());
    } else {
      return transport;
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isOpen() {
    boolean open = false;
    if (transport != null) {
      open = transport.isOpen();
    }
    if ( log.isTraceEnabled() ) {
      log.trace("Transport open status {} for client {}", open, this);
    }
    return open;
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
          timeoutVar = Integer.parseInt(timeoutStr);
        } catch (NumberFormatException e) {
          log.error("Invalid value for CASSANDRA_THRIFT_SOCKET_TIMEOUT", e);
        }
      }
    }
    return timeoutVar;
  }

  /**
   * {@inheritDoc}
   */
  public void startToUse() {
      useageStartTime = System.currentTimeMillis();
  }

  /**
   * {@inheritDoc}
   */
  public long getSinceLastUsed() {
	  return System.currentTimeMillis() - useageStartTime;
  }

  @Override
  public String toString() {
    return String.format(NAME_FORMAT, cassandraHost.getUrl(), mySerial);
  }

  /**
   * Compares the toString of these clients
   */
  @Override
  public boolean equals(Object obj) {
    return this.toString().equals(obj.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CassandraHost getCassandraHost() {
    return cassandraHost;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAlreadyAuthenticated(Map<String, String> credentials) {
    return credentials != null && this.credentials.equals(credentials);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clearAuthentication() {
    credentials.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAuthenticated(Map<String, String> credentials) {
    clearAuthentication();
    this.credentials.putAll(credentials);
  }

  /**
   * {@inheritDoc}
   */
  public long getCreatedTime() {
    return createdTime;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getLastSuccessTime() {
    return lastSuccessTime;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateLastSuccessTime() {
    lastSuccessTime = System.currentTimeMillis();
  }
}
