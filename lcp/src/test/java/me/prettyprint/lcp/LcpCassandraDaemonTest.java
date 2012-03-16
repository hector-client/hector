package me.prettyprint.lcp;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Constants;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LcpCassandraDaemonTest {

  private EmbeddedServerLauncher launcher = new EmbeddedServerLauncher();
  private Cluster cluster;
  private Keyspace keyspace;

  @Before
  public void localSetup() throws Exception {
    launcher.setup();
    //cluster = HFactory.getOrCreateCluster("TestCluster","localhost:9170");

  }

  @Test
  public void testFireUp() throws Exception {
    //assertEquals("19.20.0",cluster.describeThriftVersion());

    Cassandra.Iface client = CassandraProxyClient.newProxyConnection("localhost",9170);
    client.describe_version();
  }

}