package me.prettyprint.lcp;

import org.apache.cassandra.config.ConfigurationException;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.db.commitlog.CommitLog;
import org.apache.cassandra.thrift.CassandraDaemon;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zznate
 */
public class EmbeddedServerLauncher {
  private static Logger log = LoggerFactory.getLogger(EmbeddedServerLauncher.class);

    private static final String CASS_ROOT = "cassandra";

    private final String yamlFile;
    static LcpCassandraDaemon cassandraDaemon;

    public EmbeddedServerLauncher() {
      this("/cassandra.yaml");
    }

    public EmbeddedServerLauncher(String yamlFile) {
      this.yamlFile = yamlFile;
    }

    static ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * Set embedded cassandra up and spawn it in a new thread.
     *
     * @throws org.apache.thrift.transport.TTransportException
     * @throws java.io.IOException
     * @throws InterruptedException
     */
    public void setup() throws TTransportException, IOException,
        InterruptedException, ConfigurationException {
      if ( cassandraDaemon != null && cassandraDaemon.isRPCServerRunning() ) {
        return;
      }
      System.setProperty("cassandra-foreground","true");
      DatabaseDescriptor.createAllDirectories();

      log.info("Starting executor");

      executor.execute(new CassandraRunner());
      log.info("Started executor");
      try
      {
          TimeUnit.SECONDS.sleep(3);
          log.info("Done sleeping");
      }
      catch (InterruptedException e)
      {
          throw new AssertionError(e);
      }
    }

    public static void teardown() {
      try {
        CommitLog.instance.shutdownBlocking();
      } catch (Exception e) {
        e.printStackTrace();
      }
      executor.shutdown();
      executor.shutdownNow();
      log.info("Teardown complete");
    }


    class CassandraRunner implements Runnable {

      public void run() {

        cassandraDaemon = new LcpCassandraDaemon();

        cassandraDaemon.activate();

      }

    }
}
