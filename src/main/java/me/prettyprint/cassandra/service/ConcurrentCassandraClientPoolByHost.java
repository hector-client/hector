package me.prettyprint.cassandra.service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.service.CassandraClientMonitor.Counter;
import me.prettyprint.hector.api.exceptions.HectorException;

/**
 * Implements CassandraClientPoolByHost with high-concurrency data structures.
 * This pool implements a true FIFO block on exhaustion, but DOES NOT support
 * the WHEN_EXHAUSTED_GROW policy.
 * 
 * @author zznate
 */
public class ConcurrentCassandraClientPoolByHost implements CassandraClientPoolByHost {
  
  private static final Logger log = LoggerFactory.getLogger(ConcurrentCassandraClientPoolByHost.class);

  private final ArrayBlockingQueue<CassandraClient> clientQueue;
  private final CassandraClientPool cassandraClientPool;
  private final CassandraHost cassandraHost;
  private final CassandraClientMonitor monitor;
  private final AtomicInteger numActive, numBlocked;
  private final AtomicBoolean active;
  private final CassandraClientFactory cassandraClientFactory;
  private final long maxWaitTimeWhenExhausted;
  
  public ConcurrentCassandraClientPoolByHost(CassandraHost host,
      CassandraClientPool cassandraClientPool,
      CassandraClientMonitor monitor) {
    this.cassandraHost = host;
    this.cassandraClientPool = cassandraClientPool;
    this.monitor = monitor;
    clientQueue = new ArrayBlockingQueue<CassandraClient>(cassandraHost.getMaxActive(), true);
    numActive = new AtomicInteger();
    numBlocked = new AtomicInteger();
    active = new AtomicBoolean(true);
    cassandraClientFactory = new CassandraClientFactory(cassandraClientPool, cassandraHost, monitor);
    maxWaitTimeWhenExhausted = cassandraHost.getMaxWaitTimeWhenExhausted() < 0 ? 0 : cassandraHost.getMaxWaitTimeWhenExhausted();
    
    for (int i = 0; i < cassandraHost.getMaxActive()/3; i++) {
      clientQueue.add(cassandraClientFactory.create());
    }
    if ( log.isDebugEnabled() ) {
      log.debug("Concurrent Host pool started with {} active clients; max: {} exhausted wait: {}", 
          new Object[]{getNumIdle(), 
          cassandraHost.getMaxActive(), 
          maxWaitTimeWhenExhausted});
    }    
  }
  
  @Override
  public CassandraClient borrowClient() throws HectorException {
    CassandraClient cassandraClient;
    try {
      numBlocked.incrementAndGet();
      // blocked take on the queue if we are configured to wait forever
      cassandraClient = maxWaitTimeWhenExhausted == 0 ? clientQueue.take() : clientQueue.poll(maxWaitTimeWhenExhausted, TimeUnit.MILLISECONDS);      
      if ( cassandraClient == null ) {
        cassandraClient = cassandraClientFactory.create();
      }
      numBlocked.decrementAndGet();
      cassandraClient.markAsBorrowed();
      numActive.incrementAndGet();
    } catch (InterruptedException ie) {
      monitor.incCounter(Counter.POOL_EXHAUSTED);
      throw new HectorException(String.format("maxWaitTimeWhenExhausted exceeded for thread {} on host {}",
          new Object[]{
          Thread.currentThread().getName(), 
          cassandraHost.getName()}
      ));      
    }
    if ( log.isDebugEnabled() ) {
      log.debug("borrowed client {} from pool", cassandraClient);
    }
    return cassandraClient;
  }

  @Override
  public void close() {    
    // TODO
  }

  @Override
  public CassandraHost getCassandraHost() {   
    return cassandraHost;
  }

  @Override
  public Set<CassandraClient> getLiveClients() {
    // TODO this is not germaine. NO ONE should have direct visibility into 
    // *any* of the clients - this has caused us all sorts of issues
    return null;
  }

  @Override
  public String getName() {
    return String.format("<ConcurrentCassandraClientPoolByHost>:{}", cassandraHost.getName());
  }

  @Override
  public int getNumActive() {
    return numActive.get();
  }

  @Override
  public int getNumBeforeExhausted() {
    return cassandraHost.getMaxActive() - numActive.get();
  }

  @Override
  public int getNumBlockedThreads() {    
    return numBlocked.get();
  }

  @Override
  public int getNumIdle() {
    return clientQueue.size();
  }

  /**
   * This invalidateAll invocation is potentially time consuming as we will
   * block for 500 mseconds for additional CassandraClients to be returned 
   * for cleanup. Ideally, the API can be modified to return a Future so 
   * we can shut things down correctly regardless of how long they take
   */
  @Override
  public void invalidateAll() {
    if ( active.compareAndSet(true, false) ) {
      Set<CassandraClient> clients = new HashSet<CassandraClient>();
      clientQueue.drainTo(clients);
      while ( numActive.get() > 0 ) {
        try {
          clients.add(clientQueue.poll(500, TimeUnit.MILLISECONDS));
        } catch (InterruptedException ie) {
          log.error("Wait time exceeded while invalidating all clients");
          break;
        }
      }
      for (CassandraClient cassandraClient : clients) {      
        cassandraClientFactory.tearDown(cassandraClient);
      }
    }
  }

  @Override
  public void invalidateClient(CassandraClient client) {
    boolean removed = clientQueue.remove(client);    
    log.info("Client {} was invalidated? {}", client.toString(), removed);    
  }

  @Override
  public boolean isExhausted() {
    return getNumBeforeExhausted() == 0;
  }

  @Override
  public void releaseClient(CassandraClient client) throws HectorException {
    numActive.decrementAndGet();
    if ( !client.hasErrors() && !client.isClosed() ) {
      if ( log.isDebugEnabled() ) {
        log.debug("adding released client {} to queue", client.toString());
      }
      client.markAsReleased();
      clientQueue.add(client);  
    }
  }

}
