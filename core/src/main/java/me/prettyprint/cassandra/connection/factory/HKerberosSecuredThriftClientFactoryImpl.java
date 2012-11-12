package me.prettyprint.cassandra.connection.factory;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import me.prettyprint.cassandra.connection.client.HClient;
import me.prettyprint.cassandra.connection.client.HKerberosThriftClient;
import me.prettyprint.cassandra.connection.security.KerberosHelper;
import me.prettyprint.cassandra.connection.security.SSLHelper;
import me.prettyprint.cassandra.service.CassandraHost;

import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client Factory that provides Secure sockets using Kerberos as authentication
 * mechanism.
 * 
 * It expects few system properties to be set up:
 * <ul>
 * <li><code>java.security.auth.login.config</code>: location of the "jaas.conf"
 * file. Default is <code>jaas.conf</code> at the root of the classpath.
 * <li><code>java.security.krb5.conf</code>: location of the "krb5.conf"
 * file. Default is <code>krb5.conf</code> at the root of the classpath.
 * <li><code>sun.security.krb5.debug</code>. Set to <code>TRUE</code> for debug. Default is <code>FALSE</code>.
 * <li><code>kerberos.client.reference.name</code> Kerberos client reference name specified in <code>jaas.conf</code>. 
 * Default: "Client".
 * <li><code>kerberos.service.principal.name</code> Kerberos Service principal name without the domain. Default: "cassandra".
 * <li><code>kerberos.client.principal.name</code> Username for when .keytab file is not specified.
 * <li><code>kerberos.client.password</code> Password for then .keytab file is not specified.
 * 
 * <li><code>ssl.truststore</code> File path for trust store
 * <li><code>ssl.truststore.password</code> Password for trust store
 * <li><code>ssl.protocol</code> SSL protocol, default SSL
 * <li><code>ssl.store.type</code> Store type, default JKS
 * <li><code>ssl.cipher.suites</code> Cipher suites
 * </ul>
 * <p>
 * 
 * If a <code>.keytab</code> is going to be used, please avoid setting <code>kerberos.client.username</code> and
 * <code>kerberos.client.password</code>.
 * 
 * {@link HKerberosThriftClient} completes the authentication that this factory started against Kerberos.
 * 
 * Sample <code>jaas.conf</code> file:
 * <p>
 * 
 * <pre>
 * Client {
 *   com.sun.security.auth.module.Krb5LoginModule required
 *     useKeyTab=true
 *     keyTab="./hector-kerberos.keytab"
 *     useTicketCache=true
 *     renewTGT=true
 *     storeKey=true
 *     principal="<user_name>@your_realm";
 * };
 *
 * Server {
 *   com.sun.security.auth.module.Krb5LoginModule required
 *     useKeyTab=false
 *     storeKey=true
 *     useTicketCache=false
 *     principal="service_principal@your_realm";
 * };
 * </pre>
 * 
 * <code>useKeyTab</code> and <code>keytab</code> can be omitted if <code>kerberos.client.principal.name</code>
 * and <code>kerberos.client.password</code> are specified.
 * 
 * @see HKerberosThriftClient
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 * 
 */
public class HKerberosSecuredThriftClientFactoryImpl implements HClientFactory {

  private static final Logger log = LoggerFactory.getLogger(HKerberosSecuredThriftClientFactoryImpl.class);

  public static final String JAAS_CONFIG = "jaas.conf";
  public static final String KRB5_CONFIG = "krb5.conf";

  private final Subject kerberosTicket;
  private String krbServicePrincipalName;
  private TSSLTransportParameters params;

  public HKerberosSecuredThriftClientFactoryImpl() {

    params = SSLHelper.getTSSLTransportParameters();
    
    log.info("SSL enabled for client<->server communications.");
    log.info("Properties:");
    log.info("  ssl.truststore = {}", System.getProperty("ssl.truststore"));
    log.info("  ssl.protocol = {}", System.getProperty("ssl.protocol"));
    log.info("  ssl.store.type = {}", System.getProperty("ssl.store.type"));
    log.info("  ssl.cipher.suites = {}", System.getProperty("ssl.cipher.suites")); 
    
    String jaasConf = System.getProperty("java.security.auth.login.config");
    String krb5Conf = System.getProperty("java.security.krb5.conf");
    String krbDebug = System.getProperty("sun.security.krb5.debug");
    String krbClientReferenceName = System.getProperty("kerberos.client.reference.name");
    String krbClientUsername = System.getProperty("kerberos.client.principal.name");
    String krbClientPassword = System.getProperty("kerberos.client.password");
    krbServicePrincipalName = System.getProperty("kerberos.service.principal.name");

    if (krbDebug == null)
      System.setProperty("sun.security.krb5.debug", "false");

    if (jaasConf == null)
      System.setProperty("java.security.auth.login.config", JAAS_CONFIG);

    if (krb5Conf == null)
      System.setProperty("java.security.krb5.conf", KRB5_CONFIG);

    if (krbClientReferenceName == null)
      krbClientReferenceName = "Client";
    
    if (krbServicePrincipalName == null)
      krbServicePrincipalName = "cassandra";

    System.setProperty("javax.security.auth.useSubjectCredsOnly", "true");

    log.info("Kerberos V5 was enabled for client<->server communications.");
    log.info("Properties:");
    log.info("  sun.security.krb5.debug = {}", System.getProperty("sun.security.krb5.debug"));
    log.info("  java.security.auth.login.config = {}", System.getProperty("java.security.auth.login.config"));
    log.info("  java.security.krb5.conf = {}", System.getProperty("java.security.krb5.conf"));
    log.info("  kerberos.client.reference.name = {}", System.getProperty("kerberos.client.reference.name", krbClientReferenceName));
    log.info("  kerberos.service.principal.name = {}", System.getProperty("kerberos.service.principal.name", krbServicePrincipalName));
    log.info("  kerberos.client.principal.name = {}", System.getProperty("kerberos.client.principal.name"));
    log.info("  kerberos.client.password = {}", System.getProperty("kerberos.client.password"));
    log.info("  javax.security.auth.useSubjectCredsOnly = true");

    log.info("Trying to login to the KDC...");

    try {
      // Ticket Granting Ticket (TGT) from the Authentication Server (AS)
      if (krbClientUsername != null && krbClientPassword != null)
        kerberosTicket = KerberosHelper.loginService(krbClientReferenceName, krbClientUsername, krbClientPassword);
      else
        kerberosTicket = KerberosHelper.loginService(krbClientReferenceName);
    } catch (LoginException e) {
      throw new RuntimeException(e);
    }
    log.info("Kerberos authenticated successfully against KDC");
  }

  /**
   * {@inheritDoc}
   */
  public HClient createClient(CassandraHost ch) {
    if (log.isDebugEnabled()) {
      log.debug("Creation of new client");
    }
    
    return params == null ?
                new HKerberosThriftClient(kerberosTicket, ch, krbServicePrincipalName)
                : new HKerberosThriftClient(kerberosTicket, ch, krbServicePrincipalName, params);
  }

}
