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
  public void testRegularLimit() {
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
    try {
      Thread.currentThread().sleep(450);
    } catch (InterruptedException e) {

    }
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    try {
        Thread.currentThread().sleep(550);
      } catch (InterruptedException e) {

      }
    assertFalse(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertFalse(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
  }

  @Test
  public void testNegtiveTimeoutCounter() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    cassandraHostConfigurator.setSocketTimeoutCounter(-1);
    cassandraHostConfigurator.setSocketTimeoutWindow(500);  
    HConnectionManager connectionManager = new HConnectionManager("TestCluster", cassandraHostConfigurator);
    SocketTimeoutTracker socketTimeoutTracker = new SocketTimeoutTracker(connectionManager, cassandraHostConfigurator);  
    CassandraHost cassandraHost = new CassandraHost("localhost:9170");

    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));

    try {
      Thread.currentThread().sleep(450);
    } catch (InterruptedException e) {

    }
    
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    
    try {
        Thread.currentThread().sleep(550);
      } catch (InterruptedException e) {

      }

    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
  }

  @Test
  public void testZeroTimeoutCounter() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    cassandraHostConfigurator.setSocketTimeoutCounter(0);
    cassandraHostConfigurator.setSocketTimeoutWindow(500);  
    HConnectionManager connectionManager = new HConnectionManager("TestCluster", cassandraHostConfigurator);
    SocketTimeoutTracker socketTimeoutTracker = new SocketTimeoutTracker(connectionManager, cassandraHostConfigurator);  
    CassandraHost cassandraHost = new CassandraHost("localhost:9170");

    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));

    try {
      Thread.currentThread().sleep(450);
    } catch (InterruptedException e) {

    }
    
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    
    try {
        Thread.currentThread().sleep(550);
      } catch (InterruptedException e) {

      }

    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
  }
  
  @Test
  public void testOneTimeoutCounter() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    cassandraHostConfigurator.setSocketTimeoutCounter(1);
    cassandraHostConfigurator.setSocketTimeoutWindow(500);  
    HConnectionManager connectionManager = new HConnectionManager("TestCluster", cassandraHostConfigurator);
    SocketTimeoutTracker socketTimeoutTracker = new SocketTimeoutTracker(connectionManager, cassandraHostConfigurator);  
    CassandraHost cassandraHost = new CassandraHost("localhost:9170");

    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));

    try {
      Thread.currentThread().sleep(450);
    } catch (InterruptedException e) {

    }
    
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    
    try {
        Thread.currentThread().sleep(550);
      } catch (InterruptedException e) {

      }

    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
    assertTrue(socketTimeoutTracker.penalizeTimeout(cassandraHost));
  }
  
}
