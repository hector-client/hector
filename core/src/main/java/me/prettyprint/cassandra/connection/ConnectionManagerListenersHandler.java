package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.service.CassandraHost;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * This class handles all the {@link ConnectionManagerListener} listeners
 * @author Elyran Kogan
 */
public class ConnectionManagerListenersHandler implements Serializable {

    private Map<String ,ConnectionManagerListener> listeners = new HashMap<String, ConnectionManagerListener>();

    public void put(String listenerName, ConnectionManagerListener listener) {
        listeners.put(listenerName, listener);
    }

    public void remove(String listenerName) {
        listeners.remove(listenerName);
    }

    public void clear() {
        listeners.clear();
    }

    public void fireOnHostDown(CassandraHost cassandraHost) {
        for (ConnectionManagerListener listener : listeners.values()) {
            listener.onHostDown(cassandraHost);
        }
    }

    public void fireOnHostRestored(CassandraHost cassandraHost) {
        for (ConnectionManagerListener listener : listeners.values()) {
            listener.onHostRestored(cassandraHost);
        }
    }

    public void fireOnAllHostsDown() {
        for (ConnectionManagerListener listener : listeners.values()) {
            listener.onAllHostsDown();
        }
    }

    public void fireOnSuspendHost(CassandraHost cassandraHost, boolean removed) {
        for (ConnectionManagerListener listener : listeners.values()) {
            listener.onSuspendHost(cassandraHost, removed);
        }
    }

    public void fireOnUnSuspendHost(CassandraHost cassandraHost, boolean readded) {
        for (ConnectionManagerListener listener : listeners.values()) {
            listener.onUnSuspendHost(cassandraHost, readded);
        }
    }

    public void fireOnAddHost(CassandraHost cassandraHost, boolean added, String errorMessage, Exception e) {
        for (ConnectionManagerListener listener : listeners.values()) {
            listener.onAddHost(cassandraHost, added, errorMessage, e);
        }
    }

    public void fireOnRemoveHost(CassandraHost cassandraHost, boolean removed, String message) {
        for (ConnectionManagerListener listener : listeners.values()) {
            listener.onRemoveHost(cassandraHost, removed, message);
        }
    }
}
