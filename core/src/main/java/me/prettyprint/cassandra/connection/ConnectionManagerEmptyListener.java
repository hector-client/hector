package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.service.CassandraHost;

/**
 * an Empty implementation of {@link ConnectionManagerListener}
 * can be extended if you need only some of the events
 * @author Elyran Kogan
 */
public abstract class ConnectionManagerEmptyListener implements ConnectionManagerListener {

    @Override
    public void onHostDown(CassandraHost cassandraHost) {
    }

    @Override
    public void onHostRestored(CassandraHost cassandraHost) {
    }

    @Override
    public void onAllHostsDown() {
    }

    @Override
    public void onSuspendHost(CassandraHost cassandraHost, boolean removed) {
    }

    @Override
    public void onUnSuspendHost(CassandraHost cassandraHost, boolean readded) {
    }

    @Override
    public void onAddHost(CassandraHost cassandraHost, boolean added, String errorMessage, Exception e) {
    }

    @Override
    public void onRemoveHost(CassandraHost cassandraHost, boolean removed, String message) {
    }

    @Override
    public String getName() {
        return null;
    }
}
