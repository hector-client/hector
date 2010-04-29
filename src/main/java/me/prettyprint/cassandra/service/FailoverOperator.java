package me.prettyprint.cassandra.service;

import java.io.IOException;
import java.util.List;

import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;
import me.prettyprint.cassandra.service.CassandraClientMonitor.Counter;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
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

  private final FailoverPolicy failoverPolicy;

  /** List of all known remote cassandra nodes */
  private final List<String> knownHosts;

  private final CassandraClientMonitor monitor;

  private CassandraClient client;

  private final CassandraClientPool clientPools;

  /**
   * A reference to the keyspace operating in this context, if it's a keyspace.
   * This can be null if no keyspace in context.
   */
  private final Keyspace keyspace;

  /**
   *
   * @param policy The failover policy for this operator.
   * @param hosts The list of known hosts it can failover to.
   * @param keyspace The keyspace performing this operation (if it's a keyspace performing it). May
   * be null
   */
  public FailoverOperator(FailoverPolicy policy, List<String> hosts, CassandraClientMonitor monitor,
      CassandraClient client, CassandraClientPool clientPools, Keyspace keyspace) {
    this.failoverPolicy = policy;
    this.knownHosts = hosts;
    this.monitor = monitor;
    this.client = client;
    this.clientPools = clientPools;
    this.keyspace = keyspace;
  }

  /**
   * Performs the operation and retries in in case the class is configured for
   * retries, and there are enough hosts to try and the error was
   * {@link TimedOutException}.
   */
  public void operate(Operation<?> op) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    final StopWatch stopWatch = new Slf4JStopWatch();
    int retries = Math.min(failoverPolicy.getNumRetries() + 1, knownHosts.size());
    boolean isFirst = true;
    try {
      while (retries > 0) {
        if (!isFirst) {
          --retries;
        }
        try {
          boolean success = operateSingleIteration(op, stopWatch, retries, isFirst);
          if (success) {
            return;
          }
        } catch (SkipHostException e) {
          log.warn("Skip-host failed ", e);
          // continue the loop to the next host.
        }
        isFirst = false;
      }
    } catch (InvalidRequestException e) {
      monitor.incCounter(op.failCounter);
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      throw e;
    } catch (UnavailableException e) {
      invalidate();
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      monitor.incCounter(op.failCounter);
      throw e;
    } catch (TException e) {
      invalidate();
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      monitor.incCounter(op.failCounter);
      throw e;
    } catch (TimedOutException e) {
      invalidate();
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      monitor.incCounter(op.failCounter);
      throw e;
    } catch (PoolExhaustedException e) {
      log.warn("Pool is exhausted", e);
      monitor.incCounter(op.failCounter);
      monitor.incCounter(Counter.POOL_EXHAUSTED);
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      throw new UnavailableException();
    } catch (IllegalStateException e) {
      log.error("Client Pool is already closed, cannot obtain new clients.", e);
      monitor.incCounter(op.failCounter);
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      throw new UnavailableException();
    } catch (IOException e) {
      invalidate();
      monitor.incCounter(op.failCounter);
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      throw new UnavailableException();
    } catch (Exception e) {
      log.error("Cannot retry failover, got an Exception", e);
      monitor.incCounter(op.failCounter);
      stopWatch.stop(op.stopWatchTagName + ".fail_");
      throw new UnavailableException();
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
      int retries, boolean isFirst) throws InvalidRequestException, TException, TimedOutException,
      PoolExhaustedException, Exception, UnavailableException, TTransportException {
    log.debug("Performing operation on {}; retries: {}", client.getUrl(), retries);
    try {
      // Perform operation and save its result value
      op.executeAndSetResult(client.getCassandra());
      // hmmm don't count success, there are too many...
      // monitor.incCounter(op.successCounter);
      log.debug("Operation succeeded on {}", client.getUrl());
      stopWatch.stop(op.stopWatchTagName + ".success_");
      return true;
    } catch (TimedOutException e) {
      log.warn("Got a TimedOutException from {}. Num of retries: {}", client.getUrl(), retries);
      if (retries == 0) {
        throw e;
      } else {
        skipToNextHost(isFirst, false);
        monitor.incCounter(Counter.RECOVERABLE_TIMED_OUT_EXCEPTIONS);
      }
    } catch (UnavailableException e) {
      log.warn("Got a UnavailableException from {}. Num of retries: {}", client.getUrl(),
          retries);
      if (retries == 0) {
        throw e;
      } else {
        skipToNextHost(isFirst, true);
        monitor.incCounter(Counter.RECOVERABLE_UNAVAILABLE_EXCEPTIONS);
      }
    } catch (TTransportException e) {
      log.warn("Got a TTransportException from {}. Num of retries: {}", client.getUrl(),
          retries);
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
      boolean invalidateAllConnectionsToCurrentHost)
      throws SkipHostException {
    log.info("Skipping to next host. Current host is: {}", client.getUrl());
    invalidate();
    if (invalidateAllConnectionsToCurrentHost) {
      clientPools.invalidateAllConnectionsToHost(client);
    }

    String nextHost = isRetrySameHostAgain ? client.getUrl() :
        getNextHost(client.getUrl(), client.getIp());
    if (nextHost == null) {
      log.error("Unable to find next host to skip to at {}", toString());
      throw new SkipHostException("Unable to failover to next host");
    }


    // assume all hosts in the ring use the same port (cassandra's API only provides IPs, not ports)
    try {
      client = clientPools.borrowClient(nextHost, client.getPort());
    } catch (IllegalStateException e) {
      throw new SkipHostException(e);
    } catch (PoolExhaustedException e) {
      throw new SkipHostException(e);
    } catch (Exception e) {
      throw new SkipHostException(e);
    }
//    cassandra = client.getCassandra();
    monitor.incCounter(Counter.SKIP_HOST_SUCCESS);
    log.info("Skipped host. New host is: {}", client.getUrl());
  }

  /**
   * Invalidates this keyspace and client associated with it.
   * This method should be used when the keyspace had errors.
   * It returns the client to the pool and marks it as invalid (essentially taking taking the client
   * out of the pool indefinitely) and removed the keyspace from the client.
   */
  private void invalidate() {
    try {
      clientPools.invalidateClient(client);
      client.removeKeyspace(keyspace);
    } catch (Exception e) {
      log.error("Unable to invalidate client {}. Will continue anyhow.", client);
    }
  }
  /**
   * Finds the next host in the knownHosts. Next is the one after the given url
   * (modulo the number of elemens in the list)
   *
   * @return URL of the next presumably available host. null if none can be
   *         found.
   */
  private String getNextHost(String url, String ip) {
    int size = knownHosts.size();
    if (size < 1) {
      return null;
    }
    for (int i = 0; i < knownHosts.size(); ++i) {
      if (url.equals(knownHosts.get(i)) || ip.equals(knownHosts.get(i))) {
        // found this host. Return the next one in the array
        return knownHosts.get((i + 1) % size);
      }
    }
    log.error("The URL {} wasn't found in the knownHosts", url);
    return null;
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
  private NotFoundException exception;

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
  public abstract T execute(Cassandra.Client cassandra) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException;

  public void executeAndSetResult(Cassandra.Client cassandra) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    setResult(execute(cassandra));
  }

  public void setException(NotFoundException e) {
    exception = e;
  }

  public boolean hasException() {
    return exception != null;
  }

  public NotFoundException getException() {
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
  META_READ;
}


/**
 * An internal implementation excption used to signal that the skip-host action has failed.
 * @author Ran Tavory (ran@outbain.com)
 *
 */
/*package*/ class SkipHostException extends Exception {

  private static final long serialVersionUID = -6099636388926769255L;

  public SkipHostException(String msg) {
    super(msg);
  }

  public SkipHostException(Throwable t) {
    super(t);
  }

}
