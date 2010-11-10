package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.ddl.HKsDef;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.KsDef;

/**
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
/*package*/ class KeyspaceServiceFactory {

  private final CassandraClientMonitor clientMonitor;

  public KeyspaceServiceFactory(CassandraClientMonitor clientMonitor) {
    this.clientMonitor = clientMonitor;
  }

  public KeyspaceService create(String keyspaceName,
      ConsistencyLevelPolicy consistencyLevel,
      HConnectionManager connectionManager)
      throws HectorTransportException {
    return new KeyspaceServiceImpl(keyspaceName, consistencyLevel,
        connectionManager);
  }
}
