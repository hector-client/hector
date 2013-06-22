package me.prettyprint.cassandra.connection;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;

import me.prettyprint.hector.api.factory.HFactory;
import org.apache.cassandra.thrift.EndpointDetails;
import org.apache.cassandra.thrift.TokenRange;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.collect.Sets;

public class NodeDiscoveryTest {

	public static final String TEST_CLUSTER_NAME = "TestCluster";
	CassandraHostConfigurator cassandraHostConfigurator;
	HConnectionManager connectionManager;
	ThriftCluster cluster;
	
	@Before
	public void setup() {
		cassandraHostConfigurator = new CassandraHostConfigurator("localhost");
		connectionManager = Mockito.mock(HConnectionManager.class);
		cluster = Mockito.mock(ThriftCluster.class);
		Mockito.when(cluster.getConfigurator()).thenReturn(cassandraHostConfigurator);
		
		final KeyspaceDefinition kdef = Mockito.mock(KeyspaceDefinition.class);
		Mockito.when(kdef.getName()).thenReturn("TestKeyspace");
		Mockito.when(cluster.describeKeyspaces()).thenReturn(new LinkedList<KeyspaceDefinition>() {{
			add(kdef);
		}});
	}
	
	@Test
	public void testDoAdd() { 
		List<TokenRange> tokens = createRange("localhost", "google.com");
		Mockito.when(cluster.describeRing("TestKeyspace")).thenReturn(tokens);
		Mockito.when(cluster.getName()).thenReturn(TEST_CLUSTER_NAME);
		Mockito.when(connectionManager.getClusterName()).thenReturn(TEST_CLUSTER_NAME);
		Mockito.when(connectionManager.getHosts()).thenReturn(Sets.newHashSet(new CassandraHost("localhost",9160)));
		HFactory.setClusterForTest(cluster);

		NodeDiscovery q = new NodeDiscovery(cassandraHostConfigurator, connectionManager);
		q.doAddNodes();

		Mockito.verify(connectionManager).addCassandraHost(new CassandraHost("google.com",9160));
	}
	
	@Test
	public void testMultipleAdded() { 
		List<TokenRange> tokens = createRange("localhost", "google.com", "yahoo.com", "datastax.com");
		Mockito.when(cluster.describeRing("TestKeyspace")).thenReturn(tokens);
		Mockito.when(cluster.getName()).thenReturn(TEST_CLUSTER_NAME);
		Mockito.when(connectionManager.getClusterName()).thenReturn(TEST_CLUSTER_NAME);
		Mockito.when(connectionManager.getHosts()).thenReturn(Sets.newHashSet(new CassandraHost("localhost",9160)));
		HFactory.setClusterForTest(cluster);

		NodeDiscovery q = new NodeDiscovery(cassandraHostConfigurator, connectionManager);
		q.doAddNodes();
		
		Mockito.verify(connectionManager).addCassandraHost(new CassandraHost("google.com",9160));
		Mockito.verify(connectionManager).addCassandraHost(new CassandraHost("yahoo.com",9160));
		Mockito.verify(connectionManager).addCassandraHost(new CassandraHost("datastax.com",9160)); 
	}
	
	@Test
	public void testNoneAdded() { 
		List<TokenRange> tokens = createRange("localhost");
		Mockito.when(cluster.describeRing("TestKeyspace")).thenReturn(tokens);
		Mockito.when(cluster.getName()).thenReturn(TEST_CLUSTER_NAME);
		Mockito.when(connectionManager.getClusterName()).thenReturn(TEST_CLUSTER_NAME);
		Mockito.when(connectionManager.getHosts()).thenReturn(Sets.newHashSet(new CassandraHost("localhost",9160)));
		HFactory.setClusterForTest(cluster);
		
		NodeDiscovery q = new NodeDiscovery(cassandraHostConfigurator, connectionManager);
		Assert.assertEquals(0, q.discoverNodes().size());
	}
	
	private static List<TokenRange> createRange(String ... hosts) {
		List<TokenRange> ret = new LinkedList<TokenRange>();
		for(final String h: hosts) {
			TokenRange tr1 = Mockito.mock(TokenRange.class);
			Mockito.when(tr1.getEndpoint_details()).thenReturn(new LinkedList<EndpointDetails>(){{
				add(new EndpointDetails(h, "DC1"));
			}});
			ret.add(tr1);
		}
		return ret;
	}
}
