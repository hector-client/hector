package me.prettyprint.cassandra.connection;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraClientPool;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.ConcurrentCassandraClientPoolByHost;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentHClientPool {
  
  private static final Logger log = LoggerFactory.getLogger(ConcurrentCassandraClientPoolByHost.class);

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
    HThriftClient cassandraClient;
    try {
      numBlocked.incrementAndGet();
      cassandraClient = clientQueue.poll();
      if ( cassandraClient == null ) {
        if ( getNumBeforeExhausted() > 0 ) {
          cassandraClient = new HThriftClient(cassandraHost).open();
        } else {
          // blocked take on the queue if we are configured to wait forever
          cassandraClient = maxWaitTimeWhenExhausted == 0 ? clientQueue.take() : clientQueue.poll(maxWaitTimeWhenExhausted, TimeUnit.MILLISECONDS);    
        }
      }                  
      numBlocked.decrementAndGet();
      numActive.incrementAndGet();
    } catch (InterruptedException ie) {
      //monitor.incCounter(Counter.POOL_EXHAUSTED);
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



  public CassandraHost getCassandraHost() {   
    return cassandraHost;
  }

  public String getName() {
    return String.format("<ConcurrentCassandraClientPoolByHost>:{}", cassandraHost.getName());
  }


  public int getNumActive() {
    return numActive.get();
  }


  public int getNumBeforeExhausted() {
    return cassandraHost.getMaxActive() - numActive.get();
  }


  public int getNumBlockedThreads() {    
    return numBlocked.get();
  }


  public int getNumIdle() {
    return clientQueue.size();
  }
  

  public boolean isExhausted() {
    return getNumBeforeExhausted() == 0;
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
