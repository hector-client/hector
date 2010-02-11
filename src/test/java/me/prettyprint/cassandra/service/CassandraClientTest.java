package me.prettyprint.cassandra.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import me.prettyprint.cassandra.model.Keyspace;

import org.apache.cassandra.contrib.utils.service.CassandraServiceDataCleaner;
import org.apache.cassandra.service.ConsistencyLevel;
import org.apache.cassandra.service.EmbeddedCassandraService;
import org.apache.cassandra.service.NotFoundException;
import org.apache.cassandra.utils.FileUtils;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class CassandraClientTest {

  private static EmbeddedCassandraService cassandra;

  private static final String TMP = "tmp";

  private CassandraClient client;

  /**
   * Set embedded cassandra up and spawn it in a new thread.
   *
   * @throws TTransportException
   * @throws IOException
   * @throws InterruptedException
   */
  @BeforeClass
  public static void setup() throws TTransportException, IOException, InterruptedException {
    // delete tmp dir first
    rmdir(TMP);
    // make a tmp dir and copy storag-conf.xml and log4j.properties to it
    copy("/storage-conf.xml", TMP);
    copy("/log4j.properties", TMP);
    System.setProperty("storage-config", TMP);

    CassandraServiceDataCleaner cleaner = new CassandraServiceDataCleaner();
    cleaner.prepare();
    cassandra = new EmbeddedCassandraService();
    cassandra.init();
    Thread t = new Thread(cassandra);
    t.setDaemon(true);
    t.start();
  }

  @AfterClass
  public static void teardown() throws IOException {
    CassandraServiceDataCleaner cleaner = new CassandraServiceDataCleaner();
    cleaner.cleanupDataDirectories();
    rmdir(TMP);
  }

  @Before
  public void setupCase() throws TTransportException, TException {
    client = new CassandraClientFactory().create("localhost", 9170);
  }

  @Test
  public void testGetKeySpaceString()
      throws IllegalArgumentException, NotFoundException, TException {
    Keyspace k = client.getKeySpace("Keyspace1");
    assertNotNull(k);
    assertEquals(CassandraClient.DEFAULT_CONSISTENCY_LEVEL, k.getConsistencyLevel());

    // negative path
    try {
      k = client.getKeySpace("KeyspaceDoesntExist");
      fail("Should have thrown an exception IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      // good
    }
  }

  @Test
  public void testGetKeySpaceStringConsistencyLevel()
      throws IllegalArgumentException, NotFoundException, TException {
    Keyspace k = client.getKeySpace("Keyspace1", ConsistencyLevel.ALL);
    assertNotNull(k);
    assertEquals(ConsistencyLevel.ALL, k.getConsistencyLevel());

    k = client.getKeySpace("Keyspace1", ConsistencyLevel.ZERO);
    assertNotNull(k);
    assertEquals(ConsistencyLevel.ZERO, k.getConsistencyLevel());
  }

  @Test
  public void testGetStringProperty() throws TException {
    String prop = client.getStringProperty("cluster name");
    assertEquals("Test Cluster", prop);
  }

  @Test
  public void testGetKeyspaces() throws TException {
    List<String> spaces = client.getKeyspaces();
    assertNotNull(spaces);
    // There should be two spaces: Keyspace1 and system
    assertEquals(2, spaces.size());
    assertTrue("Keyspace1".equals(spaces.get(0)) || "Keyspace1".equals(spaces.get(1)));
  }

  @Test
  public void testGetClusterName() throws TException {
    String name = client.getClusterName();
    assertEquals("Test Cluster", name);
  }

  @Test
  public void testGetTokenMap() throws TException {
    String map = client.getTokenMap();
    assertNotNull(map);
    assertTrue(map.indexOf("127.0.0.1") > 0);
  }

  @Test
  public void testGetConfigFile() throws TException {
    String config = client.getConfigFile();
    assertNotNull(config);
    assertTrue(config.length() > 0);
  }

  private static void rmdir(String dir) throws IOException {
    File dirFile = new File(dir);
    if (dirFile.exists()) {
      FileUtils.deleteDir(new File(dir));
    }
  }
  /**
   * Copies a resource from within the jar to a directory.
   *
   * @param resourceName
   * @param directory
   * @throws IOException
   */
  private static void copy(String resource, String directory) throws IOException {
    mkdir(directory);
    InputStream is = CassandraClientTest.class.getResourceAsStream(resource);
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

  /**
   * Creates a directory
   * @param dir
   * @throws IOException
   */
  private static void mkdir(String dir) throws IOException {
    FileUtils.createDirectory(dir);
  }

}
