package me.prettyprint.cassandra.connection;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;

public class HostTimeoutTrackerTest {

    @Test
    public void testRegularLimit() {
      CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
      cassandraHostConfigurator.setHostTimeoutCounter(3);
      cassandraHostConfigurator.setHostTimeoutWindow(500);  
      HConnectionManager connectionManager = new HConnectionManager("TestCluster", cassandraHostConfigurator);
      HostTimeoutTracker hostTimeoutTracker = new HostTimeoutTracker(connectionManager, cassandraHostConfigurator);  
      CassandraHost cassandraHost = new CassandraHost("localhost:9170");
      
      assertFalse(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertFalse(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      try {
        Thread.currentThread().sleep(450);
      } catch (InterruptedException e) {

      }
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      try {
          Thread.currentThread().sleep(550);
        } catch (InterruptedException e) {

        }
      assertFalse(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertFalse(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
    }

    @Test
    public void testNegtiveTimeoutCounter() {
      CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
      cassandraHostConfigurator.setHostTimeoutCounter(-1);
      cassandraHostConfigurator.setHostTimeoutWindow(500);  
      HConnectionManager connectionManager = new HConnectionManager("TestCluster", cassandraHostConfigurator);
      HostTimeoutTracker hostTimeoutTracker = new HostTimeoutTracker(connectionManager, cassandraHostConfigurator);  
      CassandraHost cassandraHost = new CassandraHost("localhost:9170");

      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));

      try {
        Thread.currentThread().sleep(450);
      } catch (InterruptedException e) {

      }
      
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      
      try {
          Thread.currentThread().sleep(550);
        } catch (InterruptedException e) {

        }

      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
    }

    @Test
    public void testZeroTimeoutCounter() {
      CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
      cassandraHostConfigurator.setHostTimeoutCounter(0);
      cassandraHostConfigurator.setHostTimeoutWindow(500);  
      HConnectionManager connectionManager = new HConnectionManager("TestCluster", cassandraHostConfigurator);
      HostTimeoutTracker hostTimeoutTracker = new HostTimeoutTracker(connectionManager, cassandraHostConfigurator);  
      CassandraHost cassandraHost = new CassandraHost("localhost:9170");

      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));

      try {
        Thread.currentThread().sleep(450);
      } catch (InterruptedException e) {

      }
      
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      
      try {
          Thread.currentThread().sleep(550);
        } catch (InterruptedException e) {

        }

      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
    }
    
    @Test
    public void testOneTimeoutCounter() {
      CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
      cassandraHostConfigurator.setHostTimeoutCounter(1);
      cassandraHostConfigurator.setHostTimeoutWindow(500);  
      HConnectionManager connectionManager = new HConnectionManager("TestCluster", cassandraHostConfigurator);
      HostTimeoutTracker hostTimeoutTracker = new HostTimeoutTracker(connectionManager, cassandraHostConfigurator);  
      CassandraHost cassandraHost = new CassandraHost("localhost:9170");

      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));

      try {
        Thread.currentThread().sleep(450);
      } catch (InterruptedException e) {

      }
      
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      
      try {
          Thread.currentThread().sleep(550);
        } catch (InterruptedException e) {

        }

      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
      assertTrue(hostTimeoutTracker.penalizeTimeout(cassandraHost));
    }
    
}
