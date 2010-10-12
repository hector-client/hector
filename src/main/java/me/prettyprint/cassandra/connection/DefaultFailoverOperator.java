package me.prettyprint.cassandra.connection;

import java.util.List;

import me.prettyprint.cassandra.service.CassandraClientMonitor;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.cassandra.service.JmxMonitor;
import me.prettyprint.cassandra.service.Operation;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.exceptions.HTimedOutException;
import me.prettyprint.hector.api.exceptions.HUnavailableException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.cassandra.thrift.Cassandra;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A fail-over operation executor.
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class DefaultFailoverOperator {

  private static final Logger log = LoggerFactory.getLogger(DefaultFailoverOperator.class);

  private static final Logger perf4jLogger =
    LoggerFactory.getLogger("me.prettyprint.cassandra.hector.TimingLogger");

  private final CassandraClientMonitor monitor;

  /**
   * A reference to the "current host".
   * The current host under normal conditions is just <code>client.getCassandraHost()</code> however
   * when skipNextHost is invoked the client.getCassandraHost may differ from currentHost. This can
   * happen when there was an exception chen borrowing the client, so currentHost is what the client
   * SHOULD have been if the operation was successful, while the client stays at the previous client.
   *
   * currentHost is used as a pointer to the "last host we tried connecting to or sending an
   * operation to" and by which skipNextHost is operating.
   */
  private CassandraHost currentHost;
  private HConnectionManager connectionManager;
  private int retryCount;
  
  /**
   *
   */
  public DefaultFailoverOperator(HConnectionManager connectionManager) {
    this.monitor = JmxMonitor.INSTANCE.getCassandraMonitor();
    this.connectionManager = connectionManager;    
  }

  /**
   * Performs the operation and retries in in case the class is configured for
   * retries, and there are enough hosts to try and the error was
   * {@link HTimedOutException}.
   */
  public void operate(Operation<?> op) throws HectorException {
    final StopWatch stopWatch = new Slf4JStopWatch(perf4jLogger);
    int retries = Math.min(op.failoverPolicy.numRetries + 1, connectionManager.getRetryCount());
    boolean isFirst = true;
    HThriftClient client = null;
    boolean success = false;

    while (retries > 0) {
      try {
        if (!isFirst) {
          --retries;
        }

        client = connectionManager.borrowClient(); 
        Cassandra.Client c = client.getCassandra();
        // Keyspace can be null for some system_* api calls
        if ( op.keyspaceName != null ) {
          c.set_keyspace(op.keyspaceName);
        }

        op.executeAndSetResult(c);
        success = true;
        stopWatch.stop(op.stopWatchTagName + ".success_");                        
        break;

      } catch (HInvalidRequestException e) {      
        throw e;
      } catch (HUnavailableException e) {
        connectionManager.markHostAsDown(client);
        if (retries == 0) throw e; 
      } catch (HectorTransportException e) {
        connectionManager.markHostAsDown(client);
        if (retries == 0) throw e;
      } catch (HTimedOutException e) {      
        log.info("Received timeout from host {}", client.cassandraHost);
        if (retries == 0) throw e;      
      } catch (Exception e) {
        log.error("Cannot retry failover, got an Exception", e);
        throw new HectorException(e);
      } finally {
        isFirst = false;
        if ( !success ) {
          monitor.incCounter(op.failCounter);
          stopWatch.stop(op.stopWatchTagName + ".fail_");
          sleepBetweenHostSkips(op.failoverPolicy);
        }
      }
    }
  }

  
  /**
   * Sleeps for the specified time as determined by sleepBetweenHostsMilli.
   * In many cases failing over to other hosts is done b/c the cluster is too busy, so the sleep b/w
   * hosts may help reduce load on the cluster.
   */
  private void sleepBetweenHostSkips(FailoverPolicy failoverPolicy) {
    if (failoverPolicy.sleepBetweenHostsMilli > 0) {
      if ( log.isDebugEnabled() ) {
        log.debug("Will sleep for {} millisec", failoverPolicy.sleepBetweenHostsMilli);
      }
      try {
        Thread.sleep(failoverPolicy.sleepBetweenHostsMilli);
      } catch (InterruptedException e) {
        log.warn("Sleep between hosts interrupted", e);
      }
    }
  }




}



