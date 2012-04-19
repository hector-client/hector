package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.service.CassandraHost;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * This class handles all the {@link ConnectionManagerListener} listeners
 * @author Elyran Kogan
 */
public class ConnectionManagerListenersHandler implements Serializable {

    private List<ConnectionManagerListener> listeners = new ArrayList<ConnectionManagerListener>();

    public void add(ConnectionManagerListener listener) {
        listeners.add(listener);
    }

    public void remove(ConnectionManagerListener listener) {
        listeners.add(listener);
    }

    public void clear() {
        listeners.clear();
    }

    public void fireOnHostDown(CassandraHost cassandraHost) {
        for (ConnectionManagerListener listener : listeners) {
            listener.onHostDown(cassandraHost);
        }
    }

    public void fireOnHostRestored(CassandraHost cassandraHost) {
        for (ConnectionManagerListener listener : listeners) {
            listener.onHostRestored(cassandraHost);
        }
    }

    public void fireOnAllHostsDown() {
        for (ConnectionManagerListener listener : listeners) {
            listener.onAllHostsDown();
        }
    }

    public void fireOnSuspendHost(CassandraHost cassandraHost, boolean removed) {
        for (ConnectionManagerListener listener : listeners) {
            listener.onSuspendHost(cassandraHost, removed);
        }
    }

    public void fireOnUnSuspendHost(CassandraHost cassandraHost, boolean readded) {
        for (ConnectionManagerListener listener : listeners) {
            listener.onUnSuspendHost(cassandraHost, readded);
        }
    }

    public void fireOnAddHost(CassandraHost cassandraHost, boolean added, String errorMessage, Exception e) {
        for (ConnectionManagerListener listener : listeners) {
            listener.onAddHost(cassandraHost, added, errorMessage, e);
        }
    }

    public void fireOnRemoveHost(CassandraHost cassandraHost, boolean removed, String message) {
        for (ConnectionManagerListener listener : listeners) {
            listener.onRemoveHost(cassandraHost, removed, message);
        }
    }
}
