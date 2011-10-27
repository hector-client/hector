package me.prettyprint.cassandra.connection.client;

import java.net.Socket;
import java.net.SocketException;

import javax.security.auth.Subject;

import me.prettyprint.cassandra.connection.security.KerberosHelper;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.ietf.jgss.GSSContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hector client that authenticate against kerberos.
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 */
public class HKerberosThriftClient extends HThriftClient implements HClient {

  private static Logger log = LoggerFactory.getLogger(HKerberosThriftClient.class);
  
  private Subject kerberosTicket;
  private String servicePrincipalName;

  /**
   * Constructor
   * @param kerberosTicket 
   * @param cassandraHost
   */
  public HKerberosThriftClient(Subject kerberosTicket, CassandraHost cassandraHost, String servicePrincipalName) {
    super(cassandraHost);
    this.kerberosTicket = kerberosTicket;
    this.servicePrincipalName = servicePrincipalName;
  }

  /**
   * {@inheritDoc}
   */
  public HKerberosThriftClient open() {
    if ( isOpen() ) {
      throw new IllegalStateException("Open called on already open connection. You should not have gotten here.");
    }
    if ( log.isDebugEnabled() ) {
      log.debug("Creating a new thrift connection to {}", cassandraHost);
    }

    TSocket socket = new TSocket(cassandraHost.getHost(), cassandraHost.getPort(), timeout);
    if ( cassandraHost.getUseSocketKeepalive() ) {
      try {
        socket.getSocket().setKeepAlive(true);
      } catch (SocketException se) {
        throw new HectorTransportException("Could not set SO_KEEPALIVE on socket: ", se);
      }
    }

    // TODO (patricioe) What should I do with it ?
    // KerberosHelper.getSourcePrinciple(clientContext));

    if (cassandraHost.getUseThriftFramedTransport()) {
      transport = new TFramedTransport(socket);
    } else {
      transport = socket;
    }

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
    
    // Kerberos authentication
    Socket internalSocket = socket.getSocket();

    final GSSContext clientContext = KerberosHelper.authenticateClient(internalSocket, kerberosTicket, servicePrincipalName);

    if (clientContext == null) {
      close();
      throw new HectorTransportException("Kerberos context couldn't be established with client.");
    }

    return this;
  }

}
