package me.prettyprint.cassandra.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;
import me.prettyprint.cassandra.service.CassandraClientMonitor.Counter;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.exceptions.HTimedOutException;
import me.prettyprint.hector.api.exceptions.HUnavailableException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;
import me.prettyprint.hector.api.exceptions.PoolExhaustedException;

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
/*package*/ class FailoverOperator {

  private static final Logger log = LoggerFactory.getLogger(FailoverOperator.class);

  private static final Logger perf4jLogger =
 LoggerFactory.getLogger("me.prettyprint.cassandra.hector.TimingLogger");

  private final FailoverPolicy failoverPolicy;

  /** List of all known remote cassandra nodes */
  private final List<CassandraHost> knownHosts;

  private final CassandraClientMonitor monitor;

  private CassandraClient client;

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

  private final CassandraClientPool clientPools;

  /**
   * A reference to the keyspace operating in this context, if it's a keyspace.
   * This can be null if no keyspace in context.
   */
  private final KeyspaceService keyspace;

  /**
   *
   * @param policy The failover policy for this operator.
   * @param hosts The list of known hosts it can failover to.
   * @param keyspace The keyspace performing this operation (if it's a keyspace performing it). May
   * be null
   */
  public FailoverOperator(FailoverPolicy policy, CassandraClientMonitor monitor,
      CassandraClient client, CassandraClientPool clientPools, KeyspaceService keyspace) {
    Assert.noneNull(policy, monitor, client, clientPools /* keyspace may be null*/);
    this.failoverPolicy = policy;
    this.knownHosts = new ArrayList<CassandraHost>(clientPools.getKnownHosts());
    this.monitor = monitor;
    this.client = client;
    currentHost = client.getCassandraHost();
    this.clientPools = clientPools;
    this.keyspace = keyspace;
  }

  /**
   * Performs the operation and retries in in case the class is configured for
   * retries, and there are enough hosts to try and the error was
   * {@link HTimedOutException}.
   */
  public CassandraClient operate(Operation<?> op) throws HectorException {
    final StopWatch stopWatch = new Slf4JStopWatch(perf4jLogger);
    int retries = Math.min(failoverPolicy.numRetries + 1, knownHosts.size());
    boolean isFirst = true;
    try {
      while (retries > 0) {
        if (!isFirst) {
          --retries;
        }
        try {
          boolean success = operateSingleIteration(op, stopWatch, retries, isFirst);
          if (success) {
            return client;
          }
        } catch (SkipHostException e) {
          log.warn("Skip-host failed ", e);
          // continue the loop to the next host.
        }
        sleepBetweenHostSkips();
        isFirst = false;
      }
    } catch (HInvalidRequestException e) {
      monitor.incCounter(op.failCounter);
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      throw e;
    } catch (HUnavailableException e) {
      invalidate();
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      monitor.incCounter(op.failCounter);
      throw e;
    } catch (HectorTransportException e) {
      invalidate();
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      monitor.incCounter(op.failCounter);
      throw e;
    } catch (HTimedOutException e) {
      invalidate();
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      monitor.incCounter(op.failCounter);
      throw e;
    } catch (PoolExhaustedException e) {
      log.warn("Pool is exhausted", e);
      monitor.incCounter(op.failCounter);
      monitor.incCounter(Counter.POOL_EXHAUSTED);
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      throw e;
    } catch (IllegalStateException e) {
      log.error("Client Pool is already closed, cannot obtain new clients.", e);
      monitor.incCounter(op.failCounter);
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      throw new HectorException(e);
    } catch (IOException e) {
      invalidate();
      monitor.incCounter(op.failCounter);
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      throw new HectorTransportException(e);
    } catch (Exception e) {
      log.error("Cannot retry failover, got an Exception", e);
      monitor.incCounter(op.failCounter);
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      throw new HectorException(e);
    }
    return client;
  }

  /**
   * Sleeps for the specified time as determined by sleepBetweenHostsMilli.
   * In many cases failing over to other hosts is done b/c the cluster is too busy, so the sleep b/w
   * hosts may help reduce load on the cluster.
   */
  private void sleepBetweenHostSkips() {
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

  /**
   * Runs a single iteration of the operation.
   * If successful, then returns true.
   * If unsuccessful, then if a skip operation was successful, return false. If a skip operation was
   * unsuccessful or retries == 0, then throws an exception.
   * @param op the operation to perform
   * @param stopWatch the stop watch measuring performance of this operation.
   * @param retries the number of retries left.
   * @param isFirst is this the first iteraion?
   */
  private boolean operateSingleIteration(Operation<?> op, final StopWatch stopWatch,
      int retries, boolean isFirst) throws HectorException,
      PoolExhaustedException, Exception, HUnavailableException, HectorTransportException {
    if ( log.isDebugEnabled() ) {
      log.debug("Performing operation on {}; retries: {}", client.getCassandraHost().getUrl(), retries);
    }
    try {
      // Perform operation and save its result value
      Cassandra.Client c = client.getCassandra();
      if (keyspace != null) {
        c.set_keyspace(keyspace.getName());
      }
      op.executeAndSetResult(c);
      // hmmm don't count success, there are too many...
      // monitor.incCounter(op.successCounter);
      if ( log.isDebugEnabled() ) {
        log.debug("Operation succeeded on {}", client.getCassandraHost().getUrl());
      }
      stopWatch.stop(op.stopWatchTagName + ".success_");
      return true;
    } catch (HTimedOutException e) {
      log.warn("Got a TimedOutException from {}. Num of retries: {} (thread={})",
          new Object[]{client.getCassandraHost().getUrl(), retries, Thread.currentThread().getName()});
      if (retries == 0) {
        throw e;
      } else {
        skipToNextHost(isFirst, false);
        monitor.incCounter(Counter.RECOVERABLE_TIMED_OUT_EXCEPTIONS);
      }
    } catch (HUnavailableException e) {
      log.warn("Got a UnavailableException from {}. Num of retries: {} (thread={})",
          new Object[]{client.getCassandraHost().getUrl(), retries, Thread.currentThread().getName()});
      if (retries == 0) {
        throw e;
      } else {
        skipToNextHost(isFirst, true);
        monitor.incCounter(Counter.RECOVERABLE_UNAVAILABLE_EXCEPTIONS);
      }
    } catch (HectorTransportException e) {
      log.warn("Got a HectorTException from {}. Num of retries: {}; message: {}; cause: {} " +
      		"(thread={})",
          new Object[]{client.getCassandraHost().getUrl(), retries, e.getMessage(), e.getCause(),
                       Thread.currentThread().getName()});
      if (retries == 0) {
        throw e;
      } else {
        skipToNextHost(isFirst, true);
        monitor.incCounter(Counter.RECOVERABLE_TRANSPORT_EXCEPTIONS);
      }
    }
    return false;
  }

  /**
   * Updates the client member and cassandra member to the next host in the
   * ring.
   *
   * Returns the current client to the pool and retreives a new client from the
   * next pool.
   * @param isRetrySameHostAgain should the skip operation try the same current host, or should it
   * really skip to the next host in the ring?
   * @param invalidateAllConnectionsToCurrentHost If true, all connections to the current host
   * should be invalidated.
   */
  private void skipToNextHost(boolean isRetrySameHostAgain,
      boolean invalidateAllConnectionsToCurrentHost) throws SkipHostException {
    Assert.notNull(currentHost, "currentHost is null");

    if ( log.isInfoEnabled() ) {
      log.info("Skipping to next host (thread={}). Current host is: {}",
          Thread.currentThread().getName(), client.getCassandraHost().getUrl());
    }
    invalidate();
    if (invalidateAllConnectionsToCurrentHost) {
      clientPools.invalidateAllConnectionsToHost(client);
    }

    CassandraHost nextHost = isRetrySameHostAgain ? currentHost : getNextHost(currentHost);
    if (nextHost == null) {
      log.error("Unable to find next host to skip to at {}", toString());
      throw new SkipHostException("Unable to failover to next host");
    }

    // assume all hosts in the ring use the same port (cassandra's API only provides IPs, not ports)
    try {
      currentHost = nextHost;
      client = clientPools.borrowClient(nextHost);
    } catch (IllegalStateException e) {
      throw new SkipHostException(e);
    } catch (PoolExhaustedException e) {
      throw new SkipHostException(e);
    } catch (Exception e) {
      throw new SkipHostException(e);
    }
    monitor.incCounter(Counter.SKIP_HOST_SUCCESS);
    if ( log.isInfoEnabled() ) {
      log.info("Skipped host (thread={}). New client is {}", Thread.currentThread().getName(),
          client);
    }
  }

  /**
   * Invalidates this keyspace and client associated with it.
   * This method should be used when the keyspace had errors.
   * It returns the client to the pool and marks it as invalid (essentially taking taking the client
   * out of the pool indefinitely) and removed the keyspace from the client.
   */
  private void invalidate() {
    if ( log.isInfoEnabled() ) {
      log.info("Invalidating client {} (thread={})", client, Thread.currentThread().getName());
    }
    try {
      clientPools.invalidateClient(client);
      if (keyspace != null) {
        client.removeKeyspace(keyspace);
      }
    } catch (Exception e) {
      log.error("Unable to invalidate client {}. Will continue anyhow. (thread={})", client,
          Thread.currentThread().getName());
    }
  }
  /**
   * Finds the next host in the knownHosts. Next is the one after the given url
   * (modulo the number of elements in the list)
   *
   * @return URL of the next presumably available host. null if none can be
   *         found.
   */
  private CassandraHost getNextHost(CassandraHost cassandraHost) {
    int size = knownHosts.size();
    if (size < 1) {
      return null;
    }
    for (int i = 0; i < knownHosts.size(); ++i) {
      if (cassandraHost.equals(knownHosts.get(i))) {
        // found this host. Return the next one in the array
        return knownHosts.get((i + 1) % size);
      }
    }
    log.error("The host {} wasn't found in the knownHosts ({}). Will try to choose a random " +
        "host from the known host list. (thread={})",
        new Object[]{cassandraHost, knownHosts, Thread.currentThread().getName()});
    return chooseRandomHost(knownHosts);
  }

  /**
   * Chooses a random host from the list.
   * @param knownHosts
   * @return
   */
  private CassandraHost chooseRandomHost(List<CassandraHost> knownHosts) {
    int rnd = (int) (Math.random() * knownHosts.size());
    CassandraHost host = knownHosts.get(rnd);
    if ( log.isInfoEnabled() ) {
      log.info("Choosing random host to skip to: {}", host);
    }
    return host;
  }

}

/**
 * Defines the interface of an operation performed on cassandra
 *
 * @param <T>
 *          The result type of the operation (if it has a result), such as the
 *          result of get_count or get_column
 *
 *          Oh closures, how I wish you were here...
 */
/*package*/ abstract class Operation<T> {

  /** Counts failed attempts */
  protected final Counter failCounter;

  /** The stopwatch used to measure operation performance */
  protected final String stopWatchTagName;

  protected T result;
  private HectorException exception;

  public Operation(OperationType operationType) {
    this.failCounter = operationType.equals(OperationType.READ) ? Counter.READ_FAIL :
      Counter.WRITE_FAIL;
    this.stopWatchTagName = operationType.name();
  }

  public void setResult(T executionResult) {
    result = executionResult;
  }

  /**
   *
   * @return The result of the operation, if this is an operation that has a
   *         result (such as getColumn etc.
   */
  public T getResult() {
    return result;
  }

  /**
   * Performs the operation on the given cassandra instance.
   */
  public abstract T execute(Cassandra.Client cassandra) throws HectorException;

  public void executeAndSetResult(Cassandra.Client cassandra) throws HectorException {
    setResult(execute(cassandra));
  }

  public void setException(HectorException e) {
    exception = e;
  }

  public boolean hasException() {
    return exception != null;
  }

  public HectorException getException() {
    return exception;
  }
}

/**
 * Specifies the "type" of operation - read or write.
 * It's used for perf4j, so should be in sync with hectorLog4j.xml
 * @author Ran Tavory (ran@outbain.com)
 * 
 */
/*package*/ enum OperationType {
  /** Read operations*/
  READ,
  /** Write operations */
  WRITE,
  /** Meta read operations, such as describe*() */
  META_READ,
  /** Operation on one of the system_ methods */
  META_WRITE;
}


/**
 * An internal implementation excption used to signal that the skip-host action has failed.
 * @author Ran Tavory (ran@outbain.com)
 *
 */
/*package*/ class SkipHostException extends HectorException {

  private static final long serialVersionUID = -6099636388926769255L;

  public SkipHostException(String msg) {
    super(msg);
  }

  public SkipHostException(Throwable t) {
    super(t);
  }

}
