package me.prettyprint.cassandra;

import java.io.IOException;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.testutils.EmbeddedServerHelper;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for test cases that need access to EmbeddedServerHelper
 *
 * @author Nate McCall (nate@vervewireless.com)
 *
 */
public abstract class BaseEmbededServerSetupTest {
  private static Logger log = LoggerFactory.getLogger(BaseEmbededServerSetupTest.class);
  private static EmbeddedServerHelper embedded;

  protected HConnectionManager connectionManager;
  protected CassandraHostConfigurator cassandraHostConfigurator;
  protected String clusterName = "TestCluster";

  /**
   * Set embedded cassandra up and spawn it in a new thread.
   *
   * @throws TTransportException
   * @throws IOException
   * @throws InterruptedException
   */
  @BeforeClass
  public static void setup() throws TTransportException, IOException, InterruptedException, ConfigurationException {
    log.info("in setup of BaseEmbedded.Test");
    embedded = new EmbeddedServerHelper();
    embedded.setup();
  }

  @AfterClass
  public static void teardown() throws IOException {
    EmbeddedServerHelper.teardown();
    embedded = null;
  }


  protected void setupClient() {
    cassandraHostConfigurator = new CassandraHostConfigurator("127.0.0.1:9170");
    configure(cassandraHostConfigurator);
    connectionManager = new HConnectionManager(clusterName,cassandraHostConfigurator);
  }
  
  protected void configure(CassandraHostConfigurator configurator) {
  }

  protected CassandraHostConfigurator getCHCForTest() {
    CassandraHostConfigurator chc = new CassandraHostConfigurator("127.0.0.1:9170");
    chc.setMaxActive(2);
    return chc;
  }
}
