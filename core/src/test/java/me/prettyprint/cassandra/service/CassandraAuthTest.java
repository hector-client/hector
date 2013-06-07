package me.prettyprint.cassandra.service;

import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static me.prettyprint.cassandra.utils.StringUtils.string;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.cassandra.model.QuorumAllConsistencyLevelPolicy;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HNotFoundException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.testutils.EmbeddedServerHelper;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.thrift.AuthenticationException;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class CassandraAuthTest {
  private static final String TMP = "tmp";

  private static EmbeddedServerHelper embedded;
  private static Map<String, String> user1Credentials = new HashMap<String, String>();
  private static Map<String, String> user2Credentials = new HashMap<String, String>();
  private static Map<String, String> user1CredentialsBad = new HashMap<String, String>();

  private CassandraHostConfigurator cassandraHostConfigurator;
  private HConnectionManager connectionManager;
  private String clusterName = "TestCluster";
  
  /**
   * Set embedded cassandra up and spawn it in a new thread.
   */
  @BeforeClass
  public static void setup() throws TTransportException, IOException, InterruptedException, ConfigurationException {
    System.setProperty("passwd.properties", TMP + "/passwd.properties");
    System.setProperty("access.properties", TMP + "/access.properties");
    embedded = new EmbeddedServerHelper("/cassandra-auth.yaml");
    embedded.setup();
    // Setup deletes the TMP directory, so do this afterward
    copy("/passwd.properties", TMP);
    copy("/access.properties", TMP);
    // Set up credentials to match those in passwd.properties (or not)
    user1Credentials.put("username", "user1");
    user1Credentials.put("password", "password1");
    user2Credentials.put("username", "user2");
    user2Credentials.put("password", "password2");
    user1CredentialsBad.put("username", "user1");
    user1CredentialsBad.put("password", "badpassword");
  }

  @AfterClass
  public static void teardown() throws IOException {
    EmbeddedServerHelper.teardown();
    embedded = null;
  }

  @Before
  public void setupCase() throws TTransportException, TException, IllegalArgumentException,
          NotFoundException, UnknownHostException, Exception {
    cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    connectionManager = new HConnectionManager(clusterName,cassandraHostConfigurator);
  }

  @Test
  public void testDescribeKeyspaces() throws Exception {
    ThriftCluster cassandraCluster = new ThriftCluster("Test Cluster", cassandraHostConfigurator, user1Credentials);
    List<KeyspaceDefinition> keyspaces = cassandraCluster.describeKeyspaces();
    assertEquals(2,keyspaces.size());
  }

  @Test
  public void testDescribeKeyspacesBadAuth() throws Exception {
    ThriftCluster cassandraCluster = new ThriftCluster("Test Cluster", cassandraHostConfigurator, user1CredentialsBad);
    try {
      cassandraCluster.describeKeyspaces();
      fail("Should have gotten a wrapped AuthenticationException");
    } catch (HectorException he) {
      assertTrue(he.getCause() instanceof AuthenticationException);
    }
  }


  @Test
  public void testDescribeKeyspace() throws Exception {
    ThriftCluster cassandraCluster = new ThriftCluster("Test Cluster", cassandraHostConfigurator, user1Credentials);
    KeyspaceDefinition keyspaceDetail = cassandraCluster.describeKeyspace("Keyspace1");
    assertNotNull(keyspaceDetail);
    assertEquals(22, keyspaceDetail.getCfDefs().size());
  }

  @Test
  public void testAddDropColumnFamily() throws Exception {
    ThriftCluster cassandraCluster = new ThriftCluster("Test Cluster", cassandraHostConfigurator, user1Credentials);
    ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition("Keyspace1", "DynCf");
    cassandraCluster.addColumnFamily(cfDef);
    String cfid2 = cassandraCluster.dropColumnFamily("Keyspace1", "DynCf");
    assertNotNull(cfid2);
  }

  @Test
  public void testAddDropKeyspace() throws Exception {
    ThriftCluster cassandraCluster = new ThriftCluster("Test Cluster", cassandraHostConfigurator, user1Credentials);
    ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition("DynKeyspace", "DynCf");
    cassandraCluster.addKeyspace(
        new ThriftKsDef("DynKeyspace", "org.apache.cassandra.locator.SimpleStrategy", 1, Arrays.asList(cfDef)));

    String ksid2 = cassandraCluster.dropKeyspace("DynKeyspace");
    assertNotNull(ksid2);
  }
  
  @Test
  public void testInsertAndGetAndRemove() throws IllegalArgumentException, NoSuchElementException,
  IllegalStateException, HNotFoundException, Exception {
    KeyspaceService keyspace = new KeyspaceServiceImpl("Keyspace1", new QuorumAllConsistencyLevelPolicy(), 
        connectionManager, FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE, user1Credentials);

    // insert value
    ColumnPath cp = new ColumnPath("Standard1");
    cp.setColumn(bytes("testInsertAndGetAndRemove"));
    for (int i = 0; i < 100; i++) {
      keyspace.insert("testInsertAndGetAndRemove_" + i, cp,
          StringSerializer.get().toByteBuffer("testInsertAndGetAndRemove_value_" + i));
    }

    // get value
    for (int i = 0; i < 100; i++) {
      Column col = keyspace.getColumn("testInsertAndGetAndRemove_" + i, cp);
      assertNotNull(col);
      String value = string(col.getValue());
      assertEquals("testInsertAndGetAndRemove_value_" + i, value);
    }

    // remove value
    for (int i = 0; i < 100; i++) {
      keyspace.remove("testInsertAndGetAndRemove_" + i, cp);
    }

    // get already removed value
    for (int i = 0; i < 100; i++) {
      try {
        keyspace.getColumn("testInsertAndGetAndRemove_" + i, cp);
        fail("the value should already being deleted");
      } catch (HNotFoundException e) {
        // good
      }
    }
  }

  @Test
  public void testInsertAndGetAndRemoveBadAuth() throws IllegalArgumentException, NoSuchElementException,
  IllegalStateException, HNotFoundException, Exception {
    KeyspaceService keyspace = new KeyspaceServiceImpl("Keyspace1", new QuorumAllConsistencyLevelPolicy(), 
        connectionManager, FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE, user1CredentialsBad);
    try {
      // insert value
      ColumnPath cp = new ColumnPath("Standard1");
      cp.setColumn(bytes("testInsertAndGetAndRemove"));
      for (int i = 0; i < 100; i++) {
        keyspace.insert("testInsertAndGetAndRemove_" + i, cp,
            StringSerializer.get().toByteBuffer("testInsertAndGetAndRemove_value_" + i));
      }
      fail("Should have gotten a wrapped AuthenticationException");
    } catch (HectorException he) {
      assertTrue(he.getCause() instanceof AuthenticationException);
    }
  }
  /**
   * Copies a resource from within the jar to a directory.
   *
   * @param resource
   * @param directory
   * @throws IOException
   */
  private static void copy(String resource, String directory) throws IOException {
    FileUtils.createDirectory(directory);
    InputStream is = CassandraAuthTest.class.getResourceAsStream(resource);
    String fileName = resource.substring(resource.lastIndexOf("/") + 1);
    File file = new File(directory + System.getProperty("file.separator") + fileName);
    OutputStream out = new FileOutputStream(file);
    byte buf[] = new byte[1024];
    int len;
    while ((len = is.read(buf)) > 0) {
      out.write(buf, 0, len);
    }
    out.close();
    is.close();
  }
}
