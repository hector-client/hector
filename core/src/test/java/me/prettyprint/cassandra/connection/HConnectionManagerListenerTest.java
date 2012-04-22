package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HConnectionManagerListenerTest extends BaseEmbededServerSetupTest {
    private String listenerName = "test-listener";

    @Test
    public void testOnRemoveHost() {
        setupClient();
        final boolean[] eventFired = {false};
        final CassandraHost host = new CassandraHost("127.0.0.1", 9170);
        connectionManager.addListener(listenerName, new ConnectionManagerEmptyListener() {
            @Override
            public void onRemoveHost(CassandraHost cassandraHost, boolean removed, String message) {
                assertEquals(cassandraHost, host);
                assertTrue(removed);
                eventFired[0] = true;
            }
        });
        connectionManager.removeCassandraHost(host);
        assertTrue(eventFired[0]);

    }

    @Test
    public void testOnAddCassandraHostFail() {
        setupClient();
        final CassandraHost host = new CassandraHost("127.0.0.1", 9180);
        final boolean[] eventFired = {false};
        connectionManager.addListener(listenerName, new ConnectionManagerEmptyListener() {

            @Override
            public void onAddHost(CassandraHost cassandraHost, boolean added, String errorMessage, Exception e) {
                assertEquals(cassandraHost, host);
                assertFalse(added);
                eventFired[0] = true;
            }
        });
        assertFalse(connectionManager.addCassandraHost(host));
        assertTrue(eventFired[0]);
    }

    @Test
    public void testOnAddCassandraHostFailExists() {
        setupClient();
        final CassandraHost host = new CassandraHost("127.0.0.1", 9170);
        final boolean[] eventFired = {false};
        connectionManager.addListener(listenerName, new ConnectionManagerEmptyListener() {

            @Override
            public void onAddHost(CassandraHost cassandraHost, boolean added, String errorMessage, Exception e) {
                assertEquals(cassandraHost, host);
                assertFalse(added);
                eventFired[0] = true;
            }
        });
        assertFalse(connectionManager.addCassandraHost(host));
        assertTrue(eventFired[0]);
    }

    @Test
    public void testOnAddCassandraHostSuccess() {
        setupClient();
        final CassandraHost host = new CassandraHost("127.0.0.1", 9170);
        final boolean[] eventFired = {false};
        connectionManager.addListener(listenerName, new ConnectionManagerEmptyListener() {

            @Override
            public void onAddHost(CassandraHost cassandraHost, boolean added, String errorMessage, Exception e) {
                assertEquals(cassandraHost, host);
                assertTrue(added);
                eventFired[0] = true;
            }
        });
        connectionManager.removeCassandraHost(host);
        assertTrue(connectionManager.addCassandraHost(host));
        assertTrue(eventFired[0]);
    }

    @Test
    public void testOnHostDown() {
        setupClient();
        final CassandraHost host = new CassandraHost("127.0.0.1", 9170);
        final boolean[] eventFired = {false};
        connectionManager.addListener(listenerName, new ConnectionManagerEmptyListener() {
            @Override
            public void onHostDown(CassandraHost cassandraHost) {
                assertEquals(cassandraHost, host);
                eventFired[0] = true;
            }
        });
        connectionManager.markHostAsDown(host);
        assertTrue(eventFired[0]);
    }

    @Test
    public void testOnHostRestored() throws InterruptedException {
        cassandraHostConfigurator = new CassandraHostConfigurator("127.0.0.1:9170");
        cassandraHostConfigurator.setRetryDownedHostsDelayInSeconds(1);
        cassandraHostConfigurator.setRetryDownedHosts(true);
        connectionManager = new HConnectionManager(clusterName,cassandraHostConfigurator);
        final CassandraHost host = new CassandraHost("127.0.0.1", 9170);
        final boolean[] eventFired = {false};

        connectionManager.addListener(listenerName, new ConnectionManagerEmptyListener() {
            @Override
            public void onHostRestored(CassandraHost cassandraHost) {
                assertEquals(cassandraHost, host);
                eventFired[0] = true;
            }
        });
        connectionManager.markHostAsDown(host);
        Thread.sleep(1100);
        assertTrue(eventFired[0]);
    }

    @Test
    public void testOnSuspendCassandraHost() {
        setupClient();
        final CassandraHost host = new CassandraHost("127.0.0.1", 9170);
        assertTrue(connectionManager.suspendCassandraHost(host));
        final boolean[] eventFired = {false};
        connectionManager.addListener(listenerName, new ConnectionManagerEmptyListener() {

            @Override
            public void onSuspendHost(CassandraHost cassandraHost, boolean removed) {
                assertEquals(cassandraHost, host);
                assertTrue(removed);
                eventFired[0] = true;
            }
        });
    }

    @Test
    public void testOnUnSuspendCassandraHost() {
        setupClient();
        final CassandraHost host = new CassandraHost("127.0.0.1", 9170);
        assertTrue(connectionManager.suspendCassandraHost(host));
        final boolean[] eventFired = {false};
        connectionManager.addListener(listenerName, new ConnectionManagerEmptyListener() {

            @Override
            public void onUnSuspendHost(CassandraHost cassandraHost, boolean readded) {
                assertEquals(cassandraHost, host);
                assertTrue(readded);
                eventFired[0] = true;
            }
        });
        assertTrue(connectionManager.unsuspendCassandraHost(host));
    }

}
