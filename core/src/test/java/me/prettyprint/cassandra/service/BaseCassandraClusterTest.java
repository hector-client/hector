package me.prettyprint.cassandra.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.net.UnknownHostException;
import java.util.List;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.Test;


public class BaseCassandraClusterTest extends BaseEmbededServerSetupTest {

  private ThriftCluster cassandraCluster;
  private CassandraHostConfigurator cassandraHostConfigurator;

  @Before
  public void setupCase() throws TTransportException, TException, IllegalArgumentException,
          NotFoundException, UnknownHostException, Exception {
    cassandraHostConfigurator = getCHCForTest();
    cassandraCluster = new ThriftCluster(clusterName, cassandraHostConfigurator);
  }

  @Test
  public void testDescribeKeyspaces() throws Exception {
    List<KeyspaceDefinition> keyspaces = cassandraCluster.describeKeyspaces();
	// System
	// Keyspace1
	// system_traces
    assertEquals(3,keyspaces.size());
  }

  @Test
  public void testDescribeKeyspace() throws Exception {
    KeyspaceDefinition keyspaceDetail = cassandraCluster.describeKeyspace("Keyspace1");
    assertNotNull(keyspaceDetail);
    assertEquals(22, keyspaceDetail.getCfDefs().size());
  }
}
