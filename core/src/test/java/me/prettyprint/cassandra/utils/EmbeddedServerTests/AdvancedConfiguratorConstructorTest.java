package me.prettyprint.cassandra.utils.EmbeddedServerTests;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.cassandra.testutils.EmbeddedServerConfigurator;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.utils.EstimatedHistogram;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdvancedConfiguratorConstructorTest {
    static String clusterName = "Alfonso";
    static int thriftPort = 2204;
    ThriftCluster cassandraCluster;
    static EmbeddedServerHelper embedded;


    @BeforeClass
    public static void testSimpleConfiguratorConstructor() {

        EmbeddedServerConfigurator esc = new EmbeddedServerConfigurator();
        esc.setFolder("apple/pie");
        esc.setClusterName(clusterName);
        esc.setThriftPort(thriftPort);

        embedded = new EmbeddedServerHelper(esc);
        try {
            embedded.setup();
        }catch (Exception e) {}
    }

    @AfterClass
    public static void teardown() {
        try {
            embedded.teardown();
        }
        catch ( Exception e){}
    }

    @Test
    public void testConnection() {
        CassandraHostConfigurator chc = new CassandraHostConfigurator();
        chc = new CassandraHostConfigurator("localhost:" + thriftPort);

        try {
            cassandraCluster = new ThriftCluster("Test Cluster", chc);
            assertTrue(cassandraCluster.describeClusterName().equals(clusterName));
        }
        catch(Exception e) {
            // Should never have reached here
            assertTrue("Exception " + e.toString(),false);
        }

    }
}
