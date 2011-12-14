package me.prettyprint.cassandra.connection.factory;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;

/**
 * Provides different factory implementations based on {@link CassandraHostConfigurator} settings.
 * 
 * @author patricioe (Patricio Echague - patricioe@gmail.com)
 *
 */
public class HClientFactoryProvider {

  /**
   * Create a {@link HClientFactory} based on the setting in <code>chc</code>.
   * 
   * @param chc a {@link CassandraHostConfigurator} instance
   * @return an implementation of {@link HClientFactory}
   */
  public static HClientFactory createFactory(CassandraHostConfigurator chc) {
    if (chc.isUseKerberosAuthentication())
      return new HKerberosSecuredThriftClientFactoryImpl();
    else 
      return new HThriftClientFactoryImpl();
  }

}
