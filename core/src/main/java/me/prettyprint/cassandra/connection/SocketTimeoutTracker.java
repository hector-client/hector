package me.prettyprint.cassandra.connection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Keep track of how often a node replies with a HectorTransportException. If we go
 * past the threshold of [socketTimeoutCounter] timeouts within [socketTimeoutWindow]
 * milliseconds, then penalizeTimeout method will return true. (10 timeouts within 500ms
 * by default)
 * 
 * @author <a href="mailto:scheng@adconion.com">Sheng Cheng</a>
 */
public class SocketTimeoutTracker {
  private static final Logger log = LoggerFactory.getLogger(SocketTimeoutTracker.class);
  private final ConcurrentHashMap<CassandraHost, LinkedBlockingQueue<Long>> socketTimeouts;

  private final int socketTimeoutCounter;
  private final int socketTimeoutWindow;

  public static final int DEF_SOCKET_TIMEOUT_COUNTER = 10;
  public static final int DEF_SOCKET_TIMEOUT_WINDOW = 500;

  public SocketTimeoutTracker(HConnectionManager connectionManager,
      CassandraHostConfigurator cassandraHostConfigurator) {
      socketTimeouts = new ConcurrentHashMap<CassandraHost, LinkedBlockingQueue<Long>>();
      socketTimeoutCounter = cassandraHostConfigurator.getSocketTimeoutCounter();
      socketTimeoutWindow = cassandraHostConfigurator.getSocketTimeoutWindow();
  }

  public boolean penalizeTimeout(CassandraHost cassandraHost) {
    if (socketTimeoutCounter <= 1 )
    {
      return true;  
    }
      
    socketTimeouts.putIfAbsent(cassandraHost, new LinkedBlockingQueue<Long>(socketTimeoutCounter - 1 ));
    final long currentTimeMillis = System.currentTimeMillis();
    
    if(socketTimeouts.get(cassandraHost).offer(currentTimeMillis)) {
      return false;
    }
    else {
      final long oldestTimeoutMillis = socketTimeouts.get(cassandraHost).peek();
      socketTimeouts.get(cassandraHost).poll();
      socketTimeouts.get(cassandraHost).offer(currentTimeMillis);
      if (currentTimeMillis - oldestTimeoutMillis < socketTimeoutWindow) {
        return true;
      } else {
        return false;
      }      
    } 
  }  
    
}
