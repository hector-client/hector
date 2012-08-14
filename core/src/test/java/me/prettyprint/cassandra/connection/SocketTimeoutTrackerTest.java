package me.prettyprint.cassandra.connection;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;

/**
 * @author <a href="mailto:scheng@adconion.com">Sheng Cheng</a>
 */
public class SocketTimeoutTrackerTest {
  
  @Test
  public void testRegularLimit() throws Exception {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    cassandraHostConfigurator.setSocketTimeoutCounter(3);
    cassandraHostConfigurator.setSocketTimeoutWindow(500);  
    HConnectionManager connectionManager = new HConnectionManager("TestCluster", cassandraHostConfigurator);
    SocketTimeoutTracker socketTimeoutTracker = new SocketTimeoutTracker(connectionManager, cassandraHostConfigurator);  
    CassandraHost cassandraHost = new CassandraHost("localhost:9170");
    
    assertFalse(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertFalse(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    
    Thread.sleep(450);
    
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    
    Thread.sleep(550);
    
    assertFalse(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertFalse(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
  }

  @Test
  public void testNegtiveTimeoutCounter() throws Exception {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    cassandraHostConfigurator.setSocketTimeoutCounter(-1);
    cassandraHostConfigurator.setSocketTimeoutWindow(500);  
    HConnectionManager connectionManager = new HConnectionManager("TestCluster", cassandraHostConfigurator);
    SocketTimeoutTracker socketTimeoutTracker = new SocketTimeoutTracker(connectionManager, cassandraHostConfigurator);  
    CassandraHost cassandraHost = new CassandraHost("localhost:9170");

    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));

    Thread.sleep(450);
        
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    
    
    Thread.sleep(550);
    
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
  }

  @Test
  public void testZeroTimeoutCounter() throws Exception {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    cassandraHostConfigurator.setSocketTimeoutCounter(0);
    cassandraHostConfigurator.setSocketTimeoutWindow(500);  
    HConnectionManager connectionManager = new HConnectionManager("TestCluster", cassandraHostConfigurator);
    SocketTimeoutTracker socketTimeoutTracker = new SocketTimeoutTracker(connectionManager, cassandraHostConfigurator);  
    CassandraHost cassandraHost = new CassandraHost("localhost:9170");

    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));

    Thread.sleep(450);
        
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));

    Thread.sleep(550);

    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
  }
  
  @Test
  public void testOneTimeoutCounter() throws Exception {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    cassandraHostConfigurator.setSocketTimeoutCounter(1);
    cassandraHostConfigurator.setSocketTimeoutWindow(500);  
    HConnectionManager connectionManager = new HConnectionManager("TestCluster", cassandraHostConfigurator);
    SocketTimeoutTracker socketTimeoutTracker = new SocketTimeoutTracker(connectionManager, cassandraHostConfigurator);  
    CassandraHost cassandraHost = new CassandraHost("localhost:9170");

    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));

    Thread.sleep(450);
        
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
     
    Thread.sleep(550);
    
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
  }
  
}
