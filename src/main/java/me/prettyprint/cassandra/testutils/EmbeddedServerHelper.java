package me.prettyprint.cassandra.testutils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.cassandra.contrib.utils.service.CassandraServiceDataCleaner;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.service.EmbeddedCassandraService;
import org.apache.thrift.transport.TTransportException;

import org.apache.cassandra.config.KSMetaData;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.config.ConfigurationException;

/**
*
* @author Ran Tavory (rantav@gmail.com)
*
*/
public class EmbeddedServerHelper {

  private static final String TMP = "tmp";

  private EmbeddedCassandraService cassandra;

  /**
   * Set embedded cassandra up and spawn it in a new thread.
   *
   * @throws TTransportException
   * @throws IOException
   * @throws InterruptedException
   */
  public void setup() throws TTransportException, IOException, InterruptedException, ConfigurationException {
    // delete tmp dir first
    rmdir(TMP);
    // make a tmp dir and copy storag-conf.xml and log4j.properties to it
    copy("/storage-conf.xml", TMP);
    copy("/log4j.properties", TMP);
    copy("/cassandra.yaml", TMP);
    System.setProperty("storage-config", TMP);

    CassandraServiceDataCleaner cleaner = new CassandraServiceDataCleaner();
    cleaner.prepare();

    // Manually load tables from the test configuration file.
    for (KSMetaData table : DatabaseDescriptor.readTablesFromYaml())
    {
        for (CFMetaData cfm : table.cfMetaData().values())
            CFMetaData.map(cfm);
        DatabaseDescriptor.setTableDefinition(table, DatabaseDescriptor.getDefsVersion());
    }

    cassandra = new EmbeddedCassandraService();
    cassandra.init();
    Thread t = new Thread(cassandra);
    t.setDaemon(true);
    t.start();
  }

  public void teardown() {
    CassandraServiceDataCleaner cleaner = new CassandraServiceDataCleaner();

    try {
      cleaner.cleanupDataDirectories();
      rmdir(TMP);
    } catch (IOException e) {
      // IGNORE
    }
  }

  private static void rmdir(String dir) throws IOException {
    File dirFile = new File(dir);
    if (dirFile.exists()) {
      FileUtils.deleteRecursive(new File(dir));
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
    mkdir(directory);
    InputStream is = EmbeddedServerHelper.class.getResourceAsStream(resource);
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
