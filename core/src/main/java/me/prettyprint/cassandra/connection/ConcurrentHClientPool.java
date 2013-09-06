package me.prettyprint.cassandra.connection;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import me.prettyprint.cassandra.connection.client.HClient;
import me.prettyprint.cassandra.connection.factory.HClientFactory;
import me.prettyprint.cassandra.service.CassandraClientMonitor;
import me.prettyprint.cassandra.service.CassandraClientMonitor.Counter;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.exceptions.HInactivePoolException;
import me.prettyprint.hector.api.exceptions.HPoolExhaustedException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentHClientPool implements HClientPool {

  private static final Logger log = LoggerFactory.getLogger(ConcurrentHClientPool.class);

  private final ArrayBlockingQueue<HClient> availableClientQueue;
  private final AtomicInteger activeClientsCount;
  private final AtomicInteger realActiveClientsCount;
  private final AtomicLong exhaustedStartTime;

  private final CassandraHost cassandraHost;

  /** Total threads waiting for connections */
  private final AtomicInteger numBlocked;
  private final AtomicBoolean active;

  private final long maxWaitTimeWhenExhausted;

  private final HClientFactory clientFactory;

  private final CassandraClientMonitor monitor;

  public ConcurrentHClientPool(HClientFactory clientFactory, CassandraHost host, CassandraClientMonitor monitor) {
    this.clientFactory = clientFactory;
    this.cassandraHost = host;
    this.monitor = monitor;

    availableClientQueue = new ArrayBlockingQueue<HClient>(cassandraHost.getMaxActive(), true);
    // This counter can be offset by as much as the number of threads.
    activeClientsCount = new AtomicInteger(0);
    realActiveClientsCount = new AtomicInteger(0);
    exhaustedStartTime = new AtomicLong(-1);
    numBlocked = new AtomicInteger();
    active = new AtomicBoolean(true);

    maxWaitTimeWhenExhausted = cassandraHost.getMaxWaitTimeWhenExhausted() < 0 ? 0 : cassandraHost.getMaxWaitTimeWhenExhausted();

    for (int i = 0; i < cassandraHost.getMaxActive() / 3; i++) {
      availableClientQueue.add(createClient());
    }

    if ( log.isDebugEnabled() ) {
      log.debug("Concurrent Host pool started with {} active clients; max: {} exhausted wait: {}",
          new Object[]{getNumIdle(),
          cassandraHost.getMaxActive(),
          maxWaitTimeWhenExhausted});
    }
  }

  @Override
  public HClient borrowClient() throws HectorException {
    if ( !active.get() ) {
      throw new HInactivePoolException("Attempt to borrow on in-active pool: " + getName());
    }

    HClient cassandraClient = availableClientQueue.poll();
    int currentActiveClients = activeClientsCount.incrementAndGet();

    try {
      if (cassandraClient != null) {
	    if (cassandraClient.getCassandraHost().getMaxLastSuccessTimeMillis() > 0
            && cassandraClient.getLastSuccessTime() > 0
            && System.currentTimeMillis() - cassandraClient.getLastSuccessTime() > cassandraClient.getCassandraHost().getMaxLastSuccessTimeMillis()) {
          log.info("Closing connection to {} due to too long idle time of {} ms", cassandraClient.getCassandraHost().getHost(),
              System.currentTimeMillis() - cassandraClient.getLastSuccessTime());
          cassandraClient.close();
          cassandraClient = null;

          monitor.incCounter(Counter.RENEWED_IDLE_CONNECTIONS);
		}
      }
      if (cassandraClient != null) {
	    if (cassandraClient.getCassandraHost().getMaxConnectTimeMillis() > 0
            && System.currentTimeMillis() - cassandraClient.getCreatedTime() > cassandraClient.getCassandraHost().getMaxConnectTimeMillis()) {
          log.info("Closing connection to {} due to too long existence time of {} ms", cassandraClient.getCassandraHost().getHost(),
              System.currentTimeMillis() - cassandraClient.getCreatedTime());
          cassandraClient.close();
          cassandraClient = null;

          monitor.incCounter(Counter.RENEWED_TOO_LONG_CONNECTIONS);
		}
      }
      if ( cassandraClient == null ) {

        if (currentActiveClients <= cassandraHost.getMaxActive()) {
          cassandraClient = createClient();
        } else {
          // We can't grow so let's wait for a connection to become available.
          cassandraClient = waitForConnection();
        }

      }

      if ( cassandraClient == null ) {
        throw new HectorException("HConnectionManager returned a null client after aquisition - are we shutting down?");
      }
    } catch (RuntimeException e) {
      activeClientsCount.decrementAndGet();
      throw e;
    }

    realActiveClientsCount.incrementAndGet();
    if (isExhausted()) {
      exhaustedStartTime.set(System.currentTimeMillis());
    }
    return cassandraClient;
  }


  private HClient waitForConnection() {
    HClient cassandraClient = null;
    numBlocked.incrementAndGet();

    // blocked take on the queue if we are configured to wait forever
    if ( log.isDebugEnabled() ) {
      log.debug("blocking on queue - current block count {}", numBlocked.get());
    }

    try {
      // wait and catch, creating a new one if the counts have changed. Infinite wait should just recurse.
      if (maxWaitTimeWhenExhausted == 0) {
        while (cassandraClient == null && active.get()) {
          try {
            cassandraClient = availableClientQueue.poll(100, TimeUnit.MILLISECONDS);
          } catch (InterruptedException ie) {
            log.error("InterruptedException poll operation on retry forever", ie);
            break;
          }
        }
      } else {
        try {
          cassandraClient = availableClientQueue.poll(maxWaitTimeWhenExhausted, TimeUnit.MILLISECONDS);
          if (cassandraClient == null) {
            throw new HPoolExhaustedException(String.format(
                "maxWaitTimeWhenExhausted exceeded for thread %s on host %s",
                new Object[] { Thread.currentThread().getName(), cassandraHost.getName() }));
          }
        } catch (InterruptedException ie) {
          // monitor.incCounter(Counter.POOL_EXHAUSTED);
          log.error("Cassandra client acquisition interrupted", ie);
        }
      }
    } finally {
      numBlocked.decrementAndGet();
    }

    return cassandraClient;
  }


/**
   * Used when we still have room to grow. Return an HThriftClient without
   * having to wait on polling logic. (But still increment all the counters)
   * @return
   */
  private HClient createClient() {
    return clientFactory.createClient(cassandraHost).open();
  }

  /**
   * Controlled shutdown of pool. Go through the list of available clients
   * in the queue and call {@link HClient#close()} on each. Toggles
   * a flag to indicate we are going into shutdown mode. Any subsequent calls
   * will throw an IllegalArgumentException.
   *
   *
   */
  @Override
  public void shutdown() {
    if (!active.compareAndSet(true, false) ) {
      throw new IllegalArgumentException("shutdown() called for inactive pool: " + getName());
    }
    log.info("Shutdown triggered on {}", getName());
    Set<HClient> clients = new HashSet<HClient>();
    availableClientQueue.drainTo(clients);
    if ( clients.size() > 0 ) {
      for (HClient hClient : clients) {
        hClient.close();
      }
    }
    log.info("Shutdown complete on {}", getName());
  }

@Override
public CassandraHost getCassandraHost() {
    return cassandraHost;
  }

@Override
  public String getName() {
    return String.format("<ConcurrentCassandraClientPoolByHost>:{%s}", cassandraHost.getName());
  }

@Override
  public int getNumActive() {
    return realActiveClientsCount.get();
  }


@Override
public int getNumBeforeExhausted() {
    return cassandraHost.getMaxActive() - realActiveClientsCount.get();
  }


@Override
  public int getNumBlockedThreads() {
    return numBlocked.intValue();
  }


@Override
  public int getNumIdle() {
    return availableClientQueue.size();
  }


@Override
public boolean isExhausted() {
    return getNumBeforeExhausted() == 0;
  }

@Override
public int getMaxActive() {
    return cassandraHost.getMaxActive();
  }

@Override
public boolean getIsActive() {
    return active.get();
  }

  @Override
  public long getExhaustedTime() {
    long startTime = exhaustedStartTime.get();
    return (startTime == -1) ? -1 : System.currentTimeMillis() - startTime;
  }

@Override
public String getStatusAsString() {
    return String.format("%s; IsActive?: %s; Active: %d; Blocked: %d; Idle: %d; NumBeforeExhausted: %d",
        getName(), getIsActive(), getNumActive(), getNumBlockedThreads(), getNumIdle(), getNumBeforeExhausted());
  }

@Override
public void releaseClient(HClient client) throws HectorException {
    if ( cassandraHost.getMaxActive() == 0 ) {
      client.close();
    }
    boolean open = client.isOpen();
    if ( open ) {
      if ( active.get() ) {
        addClientToPoolGently(client);
      } else {
        log.info("Open client {} released to in-active pool for host {}. Closing.", client, cassandraHost);
        client.close();
      }
    } else {
      try {
        addClientToPoolGently(createClient());
      } catch (HectorTransportException e) {
        // if unable to open client then don't add one back to the pool
        log.error("Transport exception in re-opening client in release on {}", getName());
      }
    }

    realActiveClientsCount.decrementAndGet();
    exhaustedStartTime.set(-1);
    activeClientsCount.decrementAndGet();

    if ( log.isTraceEnabled() ) {
      log.trace("Status of releaseClient {} to queue: {}", client.toString(), open);
    }
  }

  /**
   * Avoids a race condition on adding clients back to the pool if pool is almost full.
   * Almost always a result of batch operation startup and shutdown (when multiple threads
   * are releasing at the same time).
   * @param client
   */
  private void addClientToPoolGently(HClient client) {
    try {
      availableClientQueue.add(client);
    } catch (IllegalStateException ise) {
      log.warn("Capacity hit adding client back to queue. Closing extra");
      client.close();
    }
  }



}
