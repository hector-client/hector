package me.prettyprint.cassandra.service;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.cassandra.contrib.utils.service.CassandraServiceDataCleaner;
import org.apache.cassandra.service.EmbeddedCassandraService;
import org.apache.cassandra.utils.FileUtils;
import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
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

  @Test
  public void testGetKeySpaceString() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetKeySpaceStringConsistencyLevel() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetStringProperty() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetKeyspaces() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetClusterName() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetTokenMap() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetConfigFile() {
    fail("Not yet implemented");
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
