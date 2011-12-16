package me.prettyprint.cassandra.utils.EmbeddedServerTests;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.hector.testutils.EmbeddedServerConfigurator;
import me.prettyprint.hector.testutils.EmbeddedServerHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.misc.IOUtils;

import java.awt.geom.Path2D;
import java.io.*;
import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExternalYamlConfiguratorConstructorTest {
    static String clusterName = "Alfonso";
    static int thriftPort = 2204;
    ThriftCluster cassandraCluster;
    static EmbeddedServerHelper embedded;
    static File yamlFile;
    private static final String sep = File.separator;
    private static final String pathName = sep + "me" + sep + "prettyprint" + sep + "cassandra" + sep + "utils" + sep + "EmbeddedServerTests" + sep + "test-cassandra.yaml";

    @BeforeClass
    public static void getYamlFile() throws IOException {

        // Get the resource
        URL resource = ExternalYamlConfiguratorConstructorTest.class.getResource(pathName);
        yamlFile = File.createTempFile("test-cassandra","yaml");


        // Transfer it to a tempfile for later use
        BufferedReader br = new BufferedReader(new InputStreamReader(ExternalYamlConfiguratorConstructorTest.class.getResourceAsStream(pathName)));
        PrintWriter pw = new PrintWriter(yamlFile);
        String line = null;
        while((line = br.readLine()) != null) {
            pw.println(line);
        }

        br.close();
        pw.close();

    }

    @Test
    public void constructorTest() {

        EmbeddedServerConfigurator esc = EmbeddedServerConfigurator.createFromYaml(yamlFile.getPath());

        assertTrue("ClusterName mismatch: esc " + esc.getClusterName() + " and " + clusterName, esc.getClusterName().equals(clusterName));
        assertTrue("Thirft port mismatch: esc " + esc.getThriftPort() + " and " + thriftPort  , esc.getThriftPort() == thriftPort);
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
