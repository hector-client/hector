package me.prettyprint.cassandra.connection;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;

public class HostTimeoutTrackerTest {

  private HostTimeoutTracker hostTimeoutTracker;
  @Before
  public void setup() {
    // map cassandraHost --> arrayBlockingQueue of timestamp Longs
    // - if abq.size > size, && peekLast.get > time, add host to excludedMap<cassandraHost,timestampOfExclusion> for <suspendTime> ms
    // - background thread to sweep for exclusion time expiration every <sweepInterval> seconds
    // - getExcludedHosts calls excludedMap.keySet
    //
    // HTL with a three timeout trigger durring 500ms intervals
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    cassandraHostConfigurator.setHostTimeoutCounter(3);
    HConnectionManager connectionManager = new HConnectionManager("TestCluster", cassandraHostConfigurator);
    hostTimeoutTracker = new HostTimeoutTracker(connectionManager, cassandraHostConfigurator);
  }
    
  @Test
  public void testTrackHostLatency() {
    CassandraHost cassandraHost = new CassandraHost("localhost:9170");
    assertFalse(hostTimeoutTracker.checkTimeout(cassandraHost));
    assertFalse(hostTimeoutTracker.checkTimeout(cassandraHost));
    assertFalse(hostTimeoutTracker.checkTimeout(cassandraHost));
    try {
      Thread.currentThread().sleep(501);
    } catch (InterruptedException e) {

    }
    assertTrue(hostTimeoutTracker.checkTimeout(cassandraHost));
    // ... 
    // in HConnectionManager: 
    // - if ( hostLatencyTracker.checkTimeout(cassandraHost) )
    //        markHostAsDown(cassandraHost);
    //        excludeHosts.add(cassandraHost);
    
  }
}
