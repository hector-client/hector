package me.prettyprint.cassandra.connection;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.connection.client.HClient;
import me.prettyprint.cassandra.connection.factory.HClientFactory;
import me.prettyprint.cassandra.service.*;
import me.prettyprint.hector.api.exceptions.HPoolExhaustedException;
import me.prettyprint.hector.api.exceptions.HTimedOutException;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.thrift.Cassandra.Client;
import org.apache.commons.lang.mutable.MutableBoolean;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HConnectionManagerTest extends BaseEmbededServerSetupTest {

  @Test
  public void testRemoveHost() {
    setupClient();
    CassandraHost cassandraHost = new CassandraHost("127.0.0.1", 9170);
    connectionManager.removeCassandraHost(cassandraHost);
    assertEquals(0,connectionManager.getActivePools().size());
    assertTrue(connectionManager.addCassandraHost(cassandraHost));
    assertEquals(1,connectionManager.getActivePools().size());
    connectionManager.markHostAsDown(cassandraHost);
    assertTrue(connectionManager.removeCassandraHost(cassandraHost));
  }
  
  @Test 
  public void testAddCassandraHostFail() {
    setupClient();
    CassandraHost cassandraHost = new CassandraHost("127.0.0.1", 9180);
    assertFalse(connectionManager.addCassandraHost(cassandraHost));
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void testNullHostList() {
    new HConnectionManager(clusterName, new CassandraHostConfigurator());
  }
  
  @Test
  public void testMarkHostDownWithNoRetry() {
    cassandraHostConfigurator = new CassandraHostConfigurator("127.0.0.1:9170");
    cassandraHostConfigurator.setRetryDownedHosts(false);
    connectionManager = new HConnectionManager(clusterName, cassandraHostConfigurator);
    new CassandraHost("127.0.0.1", 9170);    
    HClient client = connectionManager.borrowClient();
    connectionManager.markHostAsDown(client.getCassandraHost());
    assertEquals(0,connectionManager.getActivePools().size());
  }
  
  @Test
  public void testSuspendCassandraHost() {
    setupClient();
    CassandraHost cassandraHost = new CassandraHost("127.0.0.1", 9170);
    assertTrue(connectionManager.suspendCassandraHost(cassandraHost));
    assertEquals(1,connectionManager.getSuspendedCassandraHosts().size());
    assertTrue(connectionManager.unsuspendCassandraHost(cassandraHost));
  }
  
  @Test(expected=HTimedOutException.class)
  public void testTimedOutOperateWithFailover() {
    setupClient();
    FailoverPolicy fp = FailoverPolicy.ON_FAIL_TRY_ONE_NEXT_AVAILABLE;
    connectionManager.operateWithFailover(new TimeoutOp(fp));
  }

  @Test
  public void clientPoolShouldBeSuspendedWhenExhaustedForTooLong() throws InterruptedException {

    final int maxActive = 5;

    CassandraHostConfigurator configurator = new CassandraHostConfigurator("127.0.0.1:9170");
    configurator.setClientFactoryClass(TestClientFactory.class.getName());
    configurator.setMaxActive(maxActive);
    configurator.setMaxWaitTimeWhenExhausted(50);
    configurator.setMaxExhaustedTimeBeforeMarkingAsDown(0);
    configurator.setRetryDownedHosts(false);

    final HConnectionManager connectionManager = new HConnectionManager("TestCluster", configurator);
    CassandraHost host = connectionManager.getHosts().iterator().next();
    ConnectionManagerListener listener = mock(ConnectionManagerListener.class);
    final MutableBoolean wasRemoved = new MutableBoolean();
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        wasRemoved.setValue(true);
        return null;
      }
    }).when(listener).onHostDown(host);
    connectionManager.addListener("TestListener", listener);

    ExecutorService exec = Executors.newCachedThreadPool();
    final CountDownLatch latch = new CountDownLatch(1);
    for (int i = 0; i < maxActive + 1; i++) {
      exec.execute(new Runnable() {
        @Override
        public void run() {
          try {
            connectionManager.operateWithFailover(new InfiniteOp(FailoverPolicy.FAIL_FAST));
          } catch (HPoolExhaustedException e) {
            latch.countDown();
          }
        }
      });
      Thread.sleep(50);
    }

    latch.await();

    assertTrue(wasRemoved.booleanValue());

    exec.shutdownNow();
  }

  public static class TestClientFactory implements HClientFactory {

    @Override
    public HClient createClient(final CassandraHost ch) {
      HClient client = mock(HClient.class);
      when(client.close()).thenReturn(client);
      when(client.open()).thenReturn(client);
      when(client.isOpen()).thenReturn(true);
      when(client.getCassandraHost()).thenReturn(ch);
      return client;
    }
  }

  abstract class StubOp extends Operation<String> {

    StubOp(FailoverPolicy fp) {      
      this(OperationType.META_READ);
      failoverPolicy = fp;
    }
    
    public StubOp(OperationType operationType) {
      super(operationType);
    }    
  }
  
  class TimeoutOp extends StubOp {
    
    TimeoutOp(FailoverPolicy fp) {
      super(fp);
    }

    @Override
    public String execute(Client cassandra) throws HectorException {
      throw new HTimedOutException("fake timeout");
    }
  }

  class InfiniteOp extends StubOp {

    InfiniteOp(FailoverPolicy fp) {
      super(fp);
    }

    @Override
    public String execute(Client cassandra) throws HectorException {
      try {
        Thread.sleep(Long.MAX_VALUE);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      return "";
    }
  }
}
