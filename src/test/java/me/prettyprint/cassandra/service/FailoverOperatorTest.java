package me.prettyprint.cassandra.service;

import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.PoolExhaustedException;
import me.prettyprint.cassandra.model.TimedOutException;
import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

public class FailoverOperatorTest {

  private CassandraClient h1client = mock(CassandraClient.class);
  private CassandraClient h2client = mock(CassandraClient.class);
  private CassandraClient h3client = mock(CassandraClient.class);
  private Cassandra.Client h1cassandra = mock(Cassandra.Client.class);
  private Cassandra.Client h2cassandra = mock(Cassandra.Client.class);
  private Cassandra.Client h3cassandra = mock(Cassandra.Client.class);
  
  private List<CassandraHost> hosts = Arrays.asList(new CassandraHost[]{
      new CassandraHost("h1:111"), 
      new CassandraHost("h2:111"),
      new CassandraHost("h3:111")});
  private Map<Cassandra.Client,CassandraHost> clientHosts = new HashMap<Cassandra.Client,CassandraHost>();
  private Map<String, Map<String, String>> keyspaceDesc = new HashMap<String, Map<String, String>>();
  private Map<String, String> keyspace1Desc = new HashMap<String, String>();
  
  
  private String keyspaceName = "Keyspace1";
  private ConsistencyLevel consistencyLevel = ConsistencyLevel.ONE;
  private ColumnPath cp = new ColumnPath("Standard1");
  private CassandraClientPool clientPools = mock(CassandraClientPool.class);
  private CassandraClientMonitor monitor = mock(CassandraClientMonitor.class);

  
  @Before
  public void setup() {
    clientHosts.put(h1cassandra, hosts.get(0));
    clientHosts.put(h2cassandra, hosts.get(1));
    clientHosts.put(h3cassandra, hosts.get(2));

    
    keyspace1Desc.put(Keyspace.CF_TYPE, Keyspace.CF_TYPE_STANDARD);
    keyspaceDesc.put("Standard1", keyspace1Desc);
    
    
    cp.setColumn(bytes("testFailover"));
    
  }
  
  @Test
  public void testFailover() throws HectorException,
  org.apache.cassandra.thrift.InvalidRequestException, UnavailableException,
  org.apache.cassandra.thrift.TimedOutException, TException {
    
    when(h1client.getCassandra()).thenReturn(h1cassandra);
    when(h1client.getCassandraHost()).thenReturn(hosts.get(0));
    when(h2client.getCassandra()).thenReturn(h2cassandra);
    when(h2client.getCassandraHost()).thenReturn(hosts.get(1));
    when(h3client.getCassandra()).thenReturn(h3cassandra);
    when(h3client.getCassandraHost()).thenReturn(hosts.get(2));

    when(h1client.getClockResolution()).thenReturn(ClockResolution.MICROSECONDS);
    when(h2client.getClockResolution()).thenReturn(ClockResolution.MICROSECONDS);
    when(h3client.getClockResolution()).thenReturn(ClockResolution.MICROSECONDS);
    when(clientPools.borrowClient(hosts.get(0))).thenReturn(h1client);
    when(clientPools.borrowClient(hosts.get(1))).thenReturn(h2client);
    when(clientPools.borrowClient(hosts.get(2))).thenReturn(h3client);
    
    when(clientPools.getKnownHosts()).thenReturn(new HashSet<CassandraHost>(clientHosts.values()));

    // Create one positive pass without failures
    FailoverPolicy failoverPolicy = FailoverPolicy.FAIL_FAST;
    Keyspace ks = new KeyspaceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientPools, monitor);

    ks.insert("key", cp, bytes("value"));

    // now fail the call and make sure it fails fast
    //doThrow(new org.apache.cassandra.thrift.TimedOutException()).when(h1cassandra).insert(anyString(), anyString(),
    //    (ColumnPath) anyObject(), (byte[]) anyObject(), anyLong(), Matchers.<ConsistencyLevel>any());
    try {
      ks.insert("key", cp, bytes("value"));
      fail("Should not have gotten here. The method should have failed with TimedOutException; "
          + "FAIL_FAST");
    } catch (TimedOutException e) {
      // ok
    }

    // Now try the ON_FAIL_TRY_ONE_NEXT_AVAILABLE policy
    // h1 fails, h3 succeeds
    failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ONE_NEXT_AVAILABLE;
    ks = new KeyspaceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel, failoverPolicy,
        clientPools, monitor);

    ks.insert("key", cp, bytes("value"));
    Cassandra.Client cc = ks.getClient().getCassandra();
    //verify(cc).insert(anyString(), anyString(), (ColumnPath) anyObject(),
    //    (byte[]) anyObject(), anyLong(), Matchers.<ConsistencyLevel>any());
    
    verify(clientPools).borrowClient(clientHosts.get(cc));

    // make both h1 and h2 fail
    ks = new KeyspaceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel, failoverPolicy,
        clientPools, monitor);
    //doThrow(new org.apache.cassandra.thrift.TimedOutException()).when(cc).insert(anyString(), anyString(),
    //    (ColumnPath) anyObject(), (byte[]) anyObject(), anyLong(), Matchers.<ConsistencyLevel>any());
    try {
      ks.insert("key", cp, bytes("value"));
      fail("Should not have gotten here. The method should have failed with TimedOutException; "
          + "ON_FAIL_TRY_ONE_NEXT_AVAILABLE");
    } catch (TimedOutException e) {
      // ok
    }

    // Now try the full cycle
    // h1 fails, h2 fails, h3 succeeds
    failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    ks = new KeyspaceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel, failoverPolicy,
        clientPools, monitor);
    
    ks.insert("key", cp, bytes("value"));
    cc = ks.getClient().getCassandra();
    //verify(cc).insert(anyString(), anyString(), (ColumnPath) anyObject(),
    //    (byte[]) anyObject(), anyLong(), Matchers.<ConsistencyLevel>any());

    // now fail them all. h1 fails, h2 fails, h3 fails
    ks = new KeyspaceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel, failoverPolicy,
        clientPools, monitor);
    //doThrow(new org.apache.cassandra.thrift.TimedOutException()).when(h2cassandra).insert(anyString(), anyString(),
    //    (ColumnPath) anyObject(), (byte[]) anyObject(), anyLong(), Matchers.<ConsistencyLevel>any());
    //doThrow(new org.apache.cassandra.thrift.TimedOutException()).when(h3cassandra).insert(anyString(), anyString(),
    //    (ColumnPath) anyObject(), (byte[]) anyObject(), anyLong(), Matchers.<ConsistencyLevel>any());
    try {
      ks.insert("key", cp, bytes("value"));
      fail("Should not have gotten here. The method should have failed with TimedOutException; "
          + "ON_FAIL_TRY_ALL_AVAILABLE");
    } catch (TimedOutException e) {
      // ok
    }
  }
  
  /**
   * Test case for bug http://github.com/rantav/hector/issues/closed#issue/8
   * @throws IllegalStateException
   * @throws PoolExhaustedException
   * @throws Exception
   */
  @Test
  public void testFailoverBug8() throws IllegalStateException, PoolExhaustedException, Exception {
    when(h1client.getCassandra()).thenReturn(h1cassandra);
    when(h1client.getCassandraHost()).thenReturn(clientHosts.get(h1cassandra));
    when(h2client.getCassandra()).thenReturn(h2cassandra);    
    when(h2client.getCassandraHost()).thenReturn(clientHosts.get(h2cassandra));
    when(h3client.getCassandra()).thenReturn(h3cassandra);    
    when(h3client.getCassandraHost()).thenReturn(clientHosts.get(h3cassandra));
    
    when(clientPools.getKnownHosts()).thenReturn(new HashSet<CassandraHost>(clientHosts.values()));
     
    when(h1client.getClockResolution()).thenReturn(ClockResolution.MICROSECONDS);
    when(h2client.getClockResolution()).thenReturn(ClockResolution.MICROSECONDS);
    when(h3client.getClockResolution()).thenReturn(ClockResolution.MICROSECONDS);
    
    when(clientPools.borrowClient(hosts.get(0))).thenReturn(h1client);
    when(clientPools.borrowClient(hosts.get(1))).thenReturn(h2client);
    when(clientPools.borrowClient(hosts.get(2))).thenReturn(h3client);
    FailoverPolicy failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    Keyspace ks = new KeyspaceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientPools, monitor);

    // fail the call, use a transport exception.
    // This simulates a host we can connect to, but cannot perform operations on. The host is semi-
    // down
    //doThrow(new TTransportException()).when(h1cassandra).insert(anyString(), anyString(),
    //    (ColumnPath) anyObject(), (byte[]) anyObject(), anyLong(), Matchers.<ConsistencyLevel>any());

    ks.insert("key", cp, bytes("value"));

    // Make sure the client is invalidated
    verify(clientPools, times(2)).invalidateClient(h1client);

    // make sure the next call is to h2
    verify(h3client).getCassandra();

    // Now run another insert on the same keyspace to make sure it can handle next writes.
    ks.insert("key2", cp, bytes("value2"));
  }
  
  /**
   * A test case for bug 14 http://github.com/rantav/hector/issues#issue/14
   * A host goes down and can't even reconnect to it, so failover fails to skip to the next host.
   * @throws Exception
   * @throws PoolExhaustedException
   * @throws IllegalStateException
   */
  @Test
  public void testFailoverBug14() throws IllegalStateException, PoolExhaustedException, Exception {

    when(h1client.getCassandra()).thenReturn(h1cassandra);
    when(h1client.getCassandraHost()).thenReturn(clientHosts.get(h1cassandra));
    when(h2client.getCassandra()).thenReturn(h2cassandra);    
    when(h2client.getCassandraHost()).thenReturn(clientHosts.get(h2cassandra));
    when(h3client.getCassandra()).thenReturn(h3cassandra);    
    when(h3client.getCassandraHost()).thenReturn(clientHosts.get(h3cassandra));
    
    when(h1client.getClockResolution()).thenReturn(ClockResolution.MICROSECONDS);
    when(h2client.getClockResolution()).thenReturn(ClockResolution.MICROSECONDS);
    when(h3client.getClockResolution()).thenReturn(ClockResolution.MICROSECONDS);
    when(clientPools.borrowClient(hosts.get(0))).thenReturn(h1client);
    when(clientPools.borrowClient(hosts.get(1))).thenReturn(h2client);
    when(clientPools.borrowClient(hosts.get(2))).thenReturn(h3client);

    when(clientPools.getKnownHosts()).thenReturn(new HashSet<CassandraHost>(clientHosts.values()));
    
    FailoverPolicy failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    Keyspace ks = new KeyspaceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientPools, monitor);

    // fail the call, use a transport exception
    //doThrow(new TTransportException()).when(h1cassandra).insert(anyString(), anyString(),
    //    (ColumnPath) anyObject(), (byte[]) anyObject(), anyLong(), Matchers.<ConsistencyLevel>any());

    // And also fail the call to borrowClient when trying to borrow from this host again.
    // This is actually simulation the host down permanently (well, until the test ends at least...)
    doThrow(new HectorException("test")).when(clientPools).borrowClient("h1", 2);

    // And also fail the call to borrowClient when trying to borrow from this host again.
    // This is actually simulation the host down permanently (well, until the test ends at least...)
    doThrow(new HectorException("test")).when(clientPools).borrowClient("h1", 2);

    ks.insert("key", cp, bytes("value"));

    // Make sure the client is invalidated
    verify(clientPools, times(2)).invalidateClient(h1client);

    // make sure the next call is to h2
    verify(h3client).getCassandra();

    // Now run another insert on the same keyspace to make sure it can handle next writes.
    ks.insert("key2", cp, bytes("value2"));
  }

}
