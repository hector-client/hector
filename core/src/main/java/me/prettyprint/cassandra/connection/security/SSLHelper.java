package me.prettyprint.cassandra.connection.security;

import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

public class SSLHelper {
  public static final String SSL_PROTOCOL = "SSL";
  public static final String SSL_STORE_TYPE = "JKS";
  public static final String TRUST_MANAGER_TYPE = "X509";

  /**
   * build TSSLTranportParameters by getting trust store path, trust store password,
   * ssl protocol (default SSL) , store type (default JKS), cipher suites
   *
   */
  public static TSSLTransportParameters getTSSLTransportParameters() {
    String SSLTrustStore = System.getProperty("ssl.truststore");
    if (SSLTrustStore == null)
      return null;

    String SSLTrustStorePassword = System.getProperty("ssl.truststore.password");
    String SSLProtocol = System.getProperty("ssl.protocol");
    String SSLStoreType = System.getProperty("ssl.store.type");
    String SSLCipherSuites = System.getProperty("ssl.cipher.suites");

    if (SSLProtocol == null)
      SSLProtocol = SSL_PROTOCOL;

    if (SSLStoreType == null)
      SSLStoreType = SSL_STORE_TYPE;

    String [] cipherSuites = null;
    if (SSLCipherSuites != null)
      cipherSuites = SSLCipherSuites.split(",");

    TSSLTransportParameters params = new TSSLTransportFactory.TSSLTransportParameters(SSLProtocol, cipherSuites);
    params.setTrustStore(SSLTrustStore, SSLTrustStorePassword, TRUST_MANAGER_TYPE, SSLStoreType);
    return params;
  }

}
