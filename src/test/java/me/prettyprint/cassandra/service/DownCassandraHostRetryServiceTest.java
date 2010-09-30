package me.prettyprint.cassandra.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class DownCassandraHostRetryServiceTest {

  private DownCassandraHostRetryService downCassandraHostRetryService;
  private CassandraClientPool cassandraClientPool = Mockito.mock(CassandraClientPool.class);
  
  @Before
  public void setup() {
    downCassandraHostRetryService = new DownCassandraHostRetryService(cassandraClientPool, new CassandraHostConfigurator());
  }
  
  @Test
  public void testAddCassandraHost() {
    CassandraHost cassandraHost = new CassandraHost("localhost:9170");
    downCassandraHostRetryService.add(cassandraHost);
    assertTrue(downCassandraHostRetryService.contains(new CassandraHost("localhost:9170")));
  }

  @Test(expected=IllegalStateException.class)
  public void testAddCassandraHostFailedQueueFull() {
    downCassandraHostRetryService.add(new CassandraHost("localhost:9170"));
    downCassandraHostRetryService.add(new CassandraHost("localhost:9171"));
    downCassandraHostRetryService.add(new CassandraHost("localhost:9172"));
    downCassandraHostRetryService.add(new CassandraHost("localhost:9173"));
  }
  
}
