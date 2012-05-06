package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.service.CassandraHost;

import java.io.Serializable;

/**
 * Listener for the status of CassandraHosts
 * in order to use it - simply implement it and apply your own actions on the events
 * Use {@link HConnectionManager#addListener(String, ConnectionManagerListener)}
 * You can also extend the empty implementation {@link ConnectionManagerEmptyListener} if you only need some of the events
 * @author Elyran Kogan
 */
public interface ConnectionManagerListener extends Serializable {

    /**
     * fires when a {@link CassandraHost} was detected as down
     * @param cassandraHost - the host that was detected as down
     */
    void onHostDown(CassandraHost cassandraHost);

    /**
     * fires when a {@link CassandraHost} was restored by the {@link CassandraHostRetryService}
     * CassandraHostRetryService must be enabled for this event to fire
     * @param cassandraHost - the restored host
     */
    void onHostRestored(CassandraHost cassandraHost);

    /**
     * fires when the {@link CassandraHostRetryService} detects that all nodes are down
     * CassandraHostRetryService must be enabled for this event to fire
     */
    void onAllHostsDown();

    /**
     * fired when a suspend action was triggered on the host
     * @param cassandraHost - the host to be suspended
     * @param removed - true if was successfully suspended
     */
    void onSuspendHost(CassandraHost cassandraHost, boolean removed);
    /**
     * fired when an un-suspend action was triggered on the host
     * @param cassandraHost - the host to be un-suspended
     * @param readded - true if was successfully un-suspended
     */
    void onUnSuspendHost(CassandraHost cassandraHost, boolean readded);

    /**
     * fires when an action to add {@link CassandraHost} was triggered
     * @param cassandraHost - the host to add
     * @param added - true if added successfully
     * @param errorMessage - the message in case of an error (can be null otherwise)
     * @param e - exception when trying to add
     */
    void onAddHost(CassandraHost cassandraHost, boolean added, String errorMessage, Exception e);

    /**
     * fires when an action to remove {@link CassandraHost} was triggered
     * @param cassandraHost - the host to remove
     * @param removed - true if successfully removed
     * @param message - the message received with the action
     */
    void onRemoveHost(CassandraHost cassandraHost, boolean removed, String message);

    /**
     * @return  the name of the listener
     */
    String getName();
}
