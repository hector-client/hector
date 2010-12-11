package me.prettyprint.cassandra.connection;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.PoolExhaustedException;

import org.cliffc.high_scale_lib.NonBlockingHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentHClientPool implements PoolMetric {

  private static final Logger log = LoggerFactory.getLogger(ConcurrentHClientPool.class);

  private final ArrayBlockingQueue<HThriftClient> availableClientQueue;
  private final NonBlockingHashSet<HThriftClient> activeClients;

  private final CassandraHost cassandraHost;
  //private final CassandraClientMonitor monitor;
  private final AtomicInteger numActive, numBlocked;
  private final AtomicBoolean active;

  private final long maxWaitTimeWhenExhausted;

  public ConcurrentHClientPool(CassandraHost host) {
    this.cassandraHost = host;

    availableClientQueue = new ArrayBlockingQueue<HThriftClient>(cassandraHost.getMaxActive(), true);
    activeClients = new NonBlockingHashSet<HThriftClient>();
    numActive = new AtomicInteger();
    numBlocked = new AtomicInteger();
    active = new AtomicBoolean(true);

    maxWaitTimeWhenExhausted = cassandraHost.getMaxWaitTimeWhenExhausted() < 0 ? 0 : cassandraHost.getMaxWaitTimeWhenExhausted();

    for (int i = 0; i < cassandraHost.getMaxActive()/3; i++) {
      availableClientQueue.add(new HThriftClient(cassandraHost).open());
    }
    if ( log.isDebugEnabled() ) {
      log.debug("Concurrent Host pool started with {} active clients; max: {} exhausted wait: {}",
          new Object[]{getNumIdle(),
          cassandraHost.getMaxActive(),
          maxWaitTimeWhenExhausted});
    }
  }


  public HThriftClient borrowClient() throws HectorException {
    if ( !active.get() ) {
      throw new HectorException("Attempt to borrow on in-active pool: " + getName());
    }
    HThriftClient cassandraClient;
    int currentActive = numActive.incrementAndGet();
    int tillExhausted = cassandraHost.getMaxActive() - currentActive;

    numBlocked.incrementAndGet();
    cassandraClient = availableClientQueue.poll();
    if ( cassandraClient == null ) {
      if ( tillExhausted > 0 ) {
        // if we start with #of threads == getMaxActive, we could trigger this condition
        addClientToPoolGently(new HThriftClient(cassandraHost).open());
        log.debug("created new client. NumActive:{} untilExhausted: {}", currentActive, tillExhausted);
      }
      // blocked take on the queue if we are configured to wait forever
      if ( log.isDebugEnabled() ) {
        log.debug("blocking on queue - current block count {}", numBlocked.get());
      }
      // wait and catch, creating a new one if the counts have changed. Infinite wait should just recurse.
      if ( maxWaitTimeWhenExhausted == 0 ) {
        while (cassandraClient == null && active.get() ) {
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
          if ( cassandraClient == null ) {
            numBlocked.decrementAndGet();
            throw new PoolExhaustedException(String.format("maxWaitTimeWhenExhausted exceeded for thread %s on host %s",
                new Object[]{
                Thread.currentThread().getName(),
                cassandraHost.getName()}
            ));
          }
        } catch (InterruptedException ie) {
          //monitor.incCounter(Counter.POOL_EXHAUSTED);
          numActive.decrementAndGet();
        }
      }

    }
    if ( cassandraClient == null ) {
      throw new HectorException("HConnectionManager returned a null client after aquisition - are we shutting down?");
    }
    activeClients.add(cassandraClient);
    numBlocked.decrementAndGet();


    return cassandraClient;
  }

  /**
   * Controlled shutdown of pool. Go through the list of available clients
   * in the queue and call {@link HThriftClient#close()} on each. Toggles
   * a flag to indicate we are going into shutdown mode. Any subsequent calls
   * will throw an IllegalArgumentException.
   *
   *
   */
  void shutdown() {
    if (!active.compareAndSet(true, false) ) {
      throw new IllegalArgumentException("shutdown() called for inactive pool: " + getName());
    }
    log.error("Shutdown triggered on {}", getName());
    Set<HThriftClient> clients = new HashSet<HThriftClient>();
    availableClientQueue.drainTo(clients);
    if ( clients.size() > 0 ) {
      for (HThriftClient hThriftClient : clients) {
        hThriftClient.close();
      }
    }
    log.error("Shutdown complete on {}", getName());
  }

  public CassandraHost getCassandraHost() {
    return cassandraHost;
  }

  @Override
  public String getName() {
    return String.format("<ConcurrentCassandraClientPoolByHost>:{%s}", cassandraHost.getName());
  }


  @Override
  public int getNumActive() {
    return numActive.intValue();
  }


  public int getNumBeforeExhausted() {
    return cassandraHost.getMaxActive() - numActive.intValue();
  }


  @Override
  public int getNumBlockedThreads() {
    return numBlocked.intValue();
  }


  @Override
  public int getNumIdle() {
    return availableClientQueue.size();
  }


  public boolean isExhausted() {
    return getNumBeforeExhausted() == 0;
  }

  public int getMaxActive() {
    return cassandraHost.getMaxActive();
  }

  public boolean getIsActive() {
    return active.get();
  }

  public String getStatusAsString() {
    return String.format("%s; IsActive?: %s; Active: %d; Blocked: %d; Idle: %d; NumBeforeExhausted: %d",
        getName(), getIsActive(), getNumActive(), getNumBlockedThreads(), getNumIdle(), getNumBeforeExhausted());
  }

  public void releaseClient(HThriftClient client) throws HectorException {
    activeClients.remove(client);
    numActive.decrementAndGet();
    boolean open = client.isOpen();
    if ( open ) {
      if ( active.get() ) {
        addClientToPoolGently(client);
      } else {
        log.info("Open client {} released to in-active pool for host {}. Closing.", client, cassandraHost);
        client.close();
      }
    } else {
      if ( activeClients.size() < getMaxActive() && numBlocked.get() > 0) {
        addClientToPoolGently(new HThriftClient(cassandraHost).open());
      }
    }

    if ( log.isDebugEnabled() ) {
      log.debug("Status of releaseClient {} to queue: {}", client.toString(), open);
    }
  }

  /**
   * Avoids a race condition on adding clients back to the pool if pool is almost full.
   * Almost always a result of batch operation startup and shutdown (when multiple threads
   * are releasing at the same time).
   * @param client
   */
  private void addClientToPoolGently(HThriftClient client) {
    try {
      availableClientQueue.add(client);
    } catch (IllegalStateException ise) {
      log.error("Capacity hit adding client back to queue. Closing extra.");
      client.close();
    }
  }



}
