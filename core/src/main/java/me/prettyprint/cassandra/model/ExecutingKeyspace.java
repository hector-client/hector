package me.prettyprint.cassandra.model;

import java.util.Collections;
import java.util.Map;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.cassandra.service.ExceptionsTranslator;
import me.prettyprint.cassandra.service.ExceptionsTranslatorImpl;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.service.KeyspaceServiceImpl;
import me.prettyprint.cassandra.service.Operation;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.exceptions.HectorException;
import org.apache.cassandra.thrift.Cassandra;

/**
 * Thread Safe
 * 
 * @author Ran Tavory
 * @author zznate
 */
public class ExecutingKeyspace implements Keyspace {
  private static final Map<String, String> EMPTY_CREDENTIALS = Collections
      .emptyMap();

  protected ConsistencyLevelPolicy consistencyLevelPolicy;
  protected FailoverPolicy failoverPolicy;
  protected String cqlVersion;

  protected final HConnectionManager connectionManager;
  protected final String keyspace;
  protected final Map<String, String> credentials;
  private final ExceptionsTranslator exceptionTranslator;


  public ExecutingKeyspace(String keyspace,
      HConnectionManager connectionManager,
      ConsistencyLevelPolicy consistencyLevelPolicy,
      FailoverPolicy failoverPolicy) {
    this(keyspace, connectionManager, consistencyLevelPolicy, failoverPolicy,
        EMPTY_CREDENTIALS);

  }

  public ExecutingKeyspace(String keyspace,
      HConnectionManager connectionManager,
      ConsistencyLevelPolicy consistencyLevelPolicy,
      FailoverPolicy failoverPolicy, Map<String, String> credentials) {
    Assert.noneNull(consistencyLevelPolicy, connectionManager);
    this.keyspace = keyspace;
    this.connectionManager = connectionManager;
    this.consistencyLevelPolicy = consistencyLevelPolicy;
    this.failoverPolicy = failoverPolicy;
    this.credentials = credentials;
    //this.cqlVersion = cqlVersion;
    // TODO make this plug-able
    exceptionTranslator = new ExceptionsTranslatorImpl();
  }

  @Override
  public void setConsistencyLevelPolicy(ConsistencyLevelPolicy cp) {
    // TODO remove this method
    consistencyLevelPolicy = cp;
  }  
  
  @Override
  public String getKeyspaceName() {
    return keyspace;
  }

  @Override
  public String toString() {
    return "ExecutingKeyspace(" + keyspace + "," + connectionManager + ")";
  }

  @Override
  public long createClock() {
    return connectionManager.createClock();
  }

  public <T> ExecutionResult<T> doExecute(KeyspaceOperationCallback<T> koc)
      throws HectorException {
    KeyspaceService ks = null;
    try {
      ks = new KeyspaceServiceImpl(keyspace, consistencyLevelPolicy,
          connectionManager, failoverPolicy, credentials);
      return koc.doInKeyspaceAndMeasure(ks);
    } finally {
      if (ks != null) {
        // connectionManager.releaseClient(ks.getClient());
      }
    }
  }

  public <T> ExecutionResult<T> doExecuteOperation(Operation<T> operation)
      throws HectorException {
    operation.applyConnectionParams(keyspace, consistencyLevelPolicy,
        failoverPolicy, credentials);
    connectionManager.operateWithFailover(operation);
    return operation.getExecutionResult();
  }

  public ExceptionsTranslator getExceptionsTranslator() {
    return exceptionTranslator;
  }
}
