package me.prettyprint.cassandra.service;

import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
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

import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;
import me.prettyprint.hector.api.ddl.HCfDef;
import me.prettyprint.hector.api.ddl.HKsDef;
import me.prettyprint.hector.api.exceptions.HTimedOutException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.PoolExhaustedException;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

public class FailoverOperatorTest {

  private final HKsDef keyspaceDesc = mock(HKsDef.class);
  private final HCfDef keyspace1Desc = mock(HCfDef.class);

  private final CassandraClient h1client = mock(CassandraClient.class);
  private final CassandraClient h2client = mock(CassandraClient.class);
  private final CassandraClient h3client = mock(CassandraClient.class);
  private final CassandraHost h1host = new CassandraHost("h1:111");
  private final CassandraHost h2host = new CassandraHost("h2:111");
  private final CassandraHost h3host = new CassandraHost("h3:111");
  private final Cassandra.Client h1cassandra = mock(Cassandra.Client.class);
  private final Cassandra.Client h2cassandra = mock(Cassandra.Client.class);
  private final Cassandra.Client h3cassandra = mock(Cassandra.Client.class);

  private final List<CassandraHost> hosts = Arrays.asList(new CassandraHost[]{
      h1host, h2host, h3host});
  private final Map<Cassandra.Client,CassandraHost> clientHosts = new HashMap<Cassandra.Client,CassandraHost>();

  private final String keyspaceName = "Keyspace1";
  private final ConsistencyLevel consistencyLevel = ConsistencyLevel.ONE;
  private final ColumnPath cp = new ColumnPath("Standard1");
  private final CassandraClientPool clientPools = mock(CassandraClientPool.class);
  private final CassandraClientMonitor monitor = mock(CassandraClientMonitor.class);

  @Before
  public void setup() {
    clientHosts.put(h1cassandra, hosts.get(0));
    clientHosts.put(h2cassandra, hosts.get(1));
    clientHosts.put(h3cassandra, hosts.get(2));

    when(keyspace1Desc.getKeyspace()).thenReturn(keyspaceName);
    when(keyspace1Desc.getName()).thenReturn("Standard1");
    when(keyspace1Desc.getColumnType()).thenReturn(KeyspaceService.CF_TYPE_STANDARD);
    when(keyspaceDesc.getCfDefs()).thenReturn(Arrays.asList(keyspace1Desc));
    

    cp.setColumn(bytes("testFailover"));

    cp.setColumn(bytes("testFailover"));

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

  }

  @Test
  public void testFailover() throws HectorException,
      org.apache.cassandra.thrift.InvalidRequestException, UnavailableException,
      org.apache.cassandra.thrift.TimedOutException, TException {

    // Create one positive pass without failures
    FailoverPolicy failoverPolicy = FailoverPolicy.FAIL_FAST;
    KeyspaceService ks = new KeyspaceServiceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientPools, monitor);

    ks.insert("key", cp, bytes("value"));

    // now fail the call and make sure it fails fast
    doThrow(new org.apache.cassandra.thrift.TimedOutException()).when(h1cassandra).insert((byte[]) anyObject(),
        (ColumnParent) anyObject(), (Column) anyObject(), Matchers.<ConsistencyLevel>any());
    try {
      ks.insert("key", cp, bytes("value"));
      fail("Should not have gotten here. The method should have failed with TimedOutException; "
          + "FAIL_FAST");
    } catch (HTimedOutException e) {
      // ok
    }

    // Now try the ON_FAIL_TRY_ONE_NEXT_AVAILABLE policy
    // h1 fails, h3 succeeds
    failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ONE_NEXT_AVAILABLE;
    ks = new KeyspaceServiceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel, failoverPolicy,
        clientPools, monitor);

    ks.insert("key", cp, bytes("value"));
    Cassandra.Client cc = ks.getClient().getCassandra();
    verify(cc).insert((byte[]) anyObject(),
        (ColumnParent) anyObject(), (Column) anyObject(), Matchers.<ConsistencyLevel>any());

    verify(clientPools).borrowClient(clientHosts.get(cc));

    // make both h1 and h2 fail
    ks = new KeyspaceServiceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel, failoverPolicy,
        clientPools, monitor);
    doThrow(new org.apache.cassandra.thrift.TimedOutException()).when(cc).insert((byte[]) anyObject(),
        (ColumnParent) anyObject(), (Column) anyObject(), Matchers.<ConsistencyLevel>any());
    try {
      ks.insert("key", cp, bytes("value"));
      fail("Should not have gotten here. The method should have failed with TimedOutException; "
          + "ON_FAIL_TRY_ONE_NEXT_AVAILABLE");
    } catch (HTimedOutException e) {
      // ok
    }

    // Now try the full cycle
    // h1 fails, h2 fails, h3 succeeds
    failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    ks = new KeyspaceServiceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel, failoverPolicy,
        clientPools, monitor);

    ks.insert("key", cp, bytes("value"));
    cc = ks.getClient().getCassandra();
    verify(cc).insert((byte[]) anyObject(),
        (ColumnParent) anyObject(), (Column) anyObject(), Matchers.<ConsistencyLevel>any());

    // now fail them all. h1 fails, h2 fails, h3 fails
    ks = new KeyspaceServiceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel, failoverPolicy,
        clientPools, monitor);
    doThrow(new org.apache.cassandra.thrift.TimedOutException()).when(h2cassandra).insert((byte[]) anyObject(),
        (ColumnParent) anyObject(), (Column) anyObject(), Matchers.<ConsistencyLevel>any());
    doThrow(new org.apache.cassandra.thrift.TimedOutException()).when(h3cassandra).insert((byte[]) anyObject(),
        (ColumnParent) anyObject(), (Column) anyObject(), Matchers.<ConsistencyLevel>any());
    try {
      ks.insert("key", cp, bytes("value"));
      fail("Should not have gotten here. The method should have failed with TimedOutException; "
          + "ON_FAIL_TRY_ALL_AVAILABLE");
    } catch (HTimedOutException e) {
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
    FailoverPolicy failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    KeyspaceService ks = new KeyspaceServiceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientPools, monitor);

    // fail the call, use a transport exception.
    // This simulates a host we can connect to, but cannot perform operations on. The host is semi-
    // down
    doThrow(new TTransportException()).when(h1cassandra).insert((byte[]) anyObject(),
        (ColumnParent) anyObject(), (Column) anyObject(), Matchers.<ConsistencyLevel>any());

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
   */
  @Test
  public void testFailoverBug14() throws IllegalStateException, PoolExhaustedException, Exception {
    FailoverPolicy failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    KeyspaceService ks = new KeyspaceServiceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientPools, monitor);

    // fail the call, use a transport exception
    doThrow(new TTransportException()).when(h1cassandra).insert((byte[]) anyObject(),
        (ColumnParent) anyObject(), (Column) anyObject(), Matchers.<ConsistencyLevel>any());

    // And also fail the call to borrowClient when trying to borrow from this host again.
    // This is actually simulation the host down permanently (well, until the test ends at least...)
    doThrow(new HectorException("test")).when(clientPools).borrowClient(h1host);

    ks.insert("key", cp, bytes("value"));

    // Make sure the client is invalidated
    verify(clientPools, times(2)).invalidateClient(h1client);

    // make sure the next call is to h3
    verify(h3client).getCassandra();

    // Now run another insert on the same keyspace to make sure it can handle next writes.
    ks.insert("key2", cp, bytes("value2"));
  }

  /**
   * A test case for bug http://github.com/rantav/hector/issues/issue/53
   * h1 has an error so we skip to the next (h3) but h3 is down, so we SHOULD
   * skip to next (h2) and not retry h3 again
   */
  @Test
  public void testFailoverBug53() throws IllegalStateException, PoolExhaustedException, Exception {
    FailoverPolicy failoverPolicy = FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE;
    KeyspaceService ks = new KeyspaceServiceImpl(h1client, keyspaceName, keyspaceDesc, consistencyLevel,
        failoverPolicy, clientPools, monitor);

    // fail the call, use a transport exception
    doThrow(new TTransportException()).when(h1cassandra).insert((byte[]) anyObject(),
        (ColumnParent) anyObject(), (Column) anyObject(), Matchers.<ConsistencyLevel>any());

    // And also fail the call to borrowClient when trying to borrow from h3
    doThrow(new HectorException("test")).when(clientPools).borrowClient(h3host);

    ks.insert("key", cp, bytes("value"));

    // make sure there was a call to h2
    verify(h2client).getCassandra();

    // Now run another insert on the same keyspace to make sure it can handle next writes.
    ks.insert("key2", cp, bytes("value2"));
  }
}
