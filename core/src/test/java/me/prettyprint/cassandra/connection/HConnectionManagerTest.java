package me.prettyprint.cassandra.connection;

import static org.junit.Assert.*;

import org.apache.cassandra.thrift.Cassandra.Client;
import org.junit.Before;
import org.junit.Test;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.cassandra.service.Operation;
import me.prettyprint.cassandra.service.OperationType;
import me.prettyprint.hector.api.exceptions.HTimedOutException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

public class HConnectionManagerTest extends BaseEmbededServerSetupTest {

  
 
  
  @Test
  public void testRemoveHost() {
    setupClient();
    CassandraHost cassandraHost = new CassandraHost("127.0.0.1", 9170);
    connectionManager.removeCassandraHost(cassandraHost);
    assertEquals(0,connectionManager.getActivePools().size());
    assertTrue(connectionManager.addCassandraHost(cassandraHost));
    assertEquals(1,connectionManager.getActivePools().size());
  }
  
  @Test 
  public void testAddCassandraHostFail() {
    setupClient();
    CassandraHost cassandraHost = new CassandraHost("127.0.0.1", 9180);
    assertFalse(connectionManager.addCassandraHost(cassandraHost));
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void testNullHostList() {
    HConnectionManager hcm = new HConnectionManager(clusterName, new CassandraHostConfigurator());
  }
  
  @Test
  public void testMarkHostDownWithNoRetry() {
    cassandraHostConfigurator = new CassandraHostConfigurator("127.0.0.1:9170");
    cassandraHostConfigurator.setRetryDownedHosts(false);
    connectionManager = new HConnectionManager(clusterName, cassandraHostConfigurator);
    CassandraHost cassandraHost = new CassandraHost("127.0.0.1", 9170);    
    HThriftClient client = connectionManager.borrowClient();
    connectionManager.markHostAsDown(client.cassandraHost);
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
}
