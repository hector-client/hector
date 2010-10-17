package me.prettyprint.cassandra.connection;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.cliffc.high_scale_lib.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentHClientPool {
  
  private static final Logger log = LoggerFactory.getLogger(ConcurrentHClientPool.class);

  private final ArrayBlockingQueue<HThriftClient> clientQueue;

  private final CassandraHost cassandraHost;
  //private final CassandraClientMonitor monitor;
  private final AtomicInteger numActive, numBlocked;
  private final AtomicBoolean active;

  private final long maxWaitTimeWhenExhausted;
  
  public ConcurrentHClientPool(CassandraHost host) {
    this.cassandraHost = host;

    clientQueue = new ArrayBlockingQueue<HThriftClient>(cassandraHost.getMaxActive(), true);
    numActive = new AtomicInteger();    
    numBlocked = new AtomicInteger();
    active = new AtomicBoolean(true);

    maxWaitTimeWhenExhausted = cassandraHost.getMaxWaitTimeWhenExhausted() < 0 ? 0 : cassandraHost.getMaxWaitTimeWhenExhausted();
    
    for (int i = 0; i < cassandraHost.getMaxActive()/3; i++) {
      clientQueue.add(new HThriftClient(cassandraHost).open());
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
    try {      
      numBlocked.incrementAndGet();
      cassandraClient = clientQueue.poll();
      if ( cassandraClient == null ) {
        if ( tillExhausted > 0 ) {
          clientQueue.add(new HThriftClient(cassandraHost).open());          
          log.debug("created new client. NumActive:{} untilExhausted: {}", currentActive, tillExhausted);
        }
        // blocked take on the queue if we are configured to wait forever  
        if ( log.isDebugEnabled() ) {
          log.debug("blocking on queue - current block count {}", numBlocked.get());
        }
        cassandraClient = maxWaitTimeWhenExhausted == 0 ? clientQueue.take() : clientQueue.poll(maxWaitTimeWhenExhausted, TimeUnit.MILLISECONDS);
        log.debug("blocking complete");
      }      
      numBlocked.decrementAndGet();
    } catch (InterruptedException ie) {
      //monitor.incCounter(Counter.POOL_EXHAUSTED);
      numActive.decrementAndGet();
      throw new HectorException(String.format("maxWaitTimeWhenExhausted exceeded for thread {} on host {}",
          new Object[]{
          Thread.currentThread().getName(), 
          cassandraHost.getName()}
      ));      
    }
        
    return cassandraClient;
  }

  void shutdown() {
    if (!active.compareAndSet(true, false) ) {
      throw new IllegalArgumentException("shutdown() called for inactive pool: " + getName());
    }
    log.error("Shutdown triggered on {}", getName());
    Set<HThriftClient> clients = new HashSet<HThriftClient>();
    clientQueue.drainTo(clients);
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

  public String getName() {
    return String.format("<ConcurrentCassandraClientPoolByHost>:{}", cassandraHost.getName());
  }


  public int getNumActive() {
    return numActive.intValue();
  }


  public int getNumBeforeExhausted() {
    return cassandraHost.getMaxActive() - numActive.intValue();
  }


  public int getNumBlockedThreads() {    
    return numBlocked.intValue();
  }


  public int getNumIdle() {
    return clientQueue.size();
  }
  

  public boolean isExhausted() {
    return getNumBeforeExhausted() == 0;
  }

  public String getStatusAsString() {
    return String.format("%s; Active: %d; Blocked: %d; Idle: %d; NumBeforeExhausted: %d", 
        getName(), getNumActive(), getNumBlockedThreads(), getNumIdle(), getNumBeforeExhausted());
  }

  public void releaseClient(HThriftClient client) throws HectorException {
    numActive.decrementAndGet();
    boolean open = client.isOpen();
    if ( open ) {      
      clientQueue.add(client);  
    } 
    
    if ( log.isDebugEnabled() ) {
      log.debug("Status of releaseClient {} to queue: {}", client.toString(), open);
    }
  }





}
