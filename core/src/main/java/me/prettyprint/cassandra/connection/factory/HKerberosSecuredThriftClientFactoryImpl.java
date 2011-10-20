package me.prettyprint.cassandra.connection.factory;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import me.prettyprint.cassandra.connection.client.HClient;
import me.prettyprint.cassandra.connection.client.HThriftClient;
import me.prettyprint.cassandra.connection.security.KerberosHelper;
import me.prettyprint.cassandra.service.CassandraHost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Client Factory that provides Secure sockets using Kerberos as authentication mechanism.
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 *
 */
public class HKerberosSecuredThriftClientFactoryImpl implements HClientFactory {
  
  private static final Logger log = LoggerFactory.getLogger(HKerberosSecuredThriftClientFactoryImpl.class);
  
  public static final String JAAS_CONFIG = "./jaas.conf";
  public static final String KRB5_CONFIG = "./krb5.conf";
  
  private final Subject kerberosTicket;
  
  public HKerberosSecuredThriftClientFactoryImpl() {
    String jaasConf = System.getProperty("java.security.auth.login.config");
    String krb5Conf = System.getProperty("java.security.krb5.conf");
    String krbDebug = System.getProperty("sun.security.krb5.debug");
    String krbServiceName = System.getProperty("kerberos.service.name");

    if (krbDebug == null)
        System.setProperty("sun.security.krb5.debug", "false");

    if (jaasConf == null)
        System.setProperty("java.security.auth.login.config", JAAS_CONFIG);

    if (krb5Conf == null)
        System.setProperty("java.security.krb5.conf", KRB5_CONFIG);
    
    if (krbServiceName == null)
      krbServiceName = "Client";

    System.setProperty("javax.security.auth.useSubjectCredsOnly", "true");

    log.info("Kerberos V5 was enabled for client<->server communications.");
    log.info("Properties:");
    log.info("  sun.security.krb5.debug = {}", System.getProperty("sun.security.krb5.debug"));
    log.info("  java.security.auth.login.config = {}", System.getProperty("java.security.auth.login.config"));
    log.info("  java.security.krb5.conf = {}", System.getProperty("java.security.krb5.conf"));
    log.info("  javax.security.auth.useSubjectCredsOnly = true");

    log.info("Trying to login to the KDC...");

    try
    {
        kerberosTicket = KerberosHelper.loginService(krbServiceName);
    }
    catch (LoginException e)
    {
        throw new RuntimeException(e);
    }
    log.info("Kerberos authenticated successfully against KDC");
  }

  /**
   * {@inheritDoc}
   */
  public HClient createClient(CassandraHost ch) {
    if ( log.isDebugEnabled() ) {
      log.debug("Creation of new client");
    }
    return new HThriftClient(ch);
  }

}
