package me.prettyprint.cassandra.service;

import java.util.List;
import java.util.Map;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.TokenRange;

public class ThriftCluster extends AbstractCluster implements Cluster {

  public ThriftCluster(String clusterName, CassandraHostConfigurator cassandraHostConfigurator) {
    super(clusterName, cassandraHostConfigurator);
  }

  public ThriftCluster(String clusterName, CassandraHostConfigurator cassandraHostConfigurator, Map<String, String> credentials) {
    super(clusterName, cassandraHostConfigurator, credentials);
  }

  public List<TokenRange> describeRing(final String keyspace) throws HectorException {
    Operation<List<TokenRange>> op = new Operation<List<TokenRange>>(OperationType.META_READ, getCredentials()) {
      @Override
      public List<TokenRange> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.describe_ring(keyspace);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    connectionManager.operateWithFailover(op);
    return op.getResult();
  }



  @Override
  public String updateKeyspace(final KeyspaceDefinition ksdef) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE, getCredentials()) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.system_update_keyspace(((ThriftKsDef) ksdef).toThrift());
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    connectionManager.operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public String addColumnFamily(final ColumnFamilyDefinition cfdef) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE,
        FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE,
        cfdef.getKeyspaceName(), 
        getCredentials()) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.system_add_column_family(((ThriftCfDef) cfdef).toThrift());
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    connectionManager.operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public String updateColumnFamily(final ColumnFamilyDefinition cfdef) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE,
        FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE,
        cfdef.getKeyspaceName(), 
        getCredentials()) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.system_update_column_family(((ThriftCfDef) cfdef).toThrift());
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    connectionManager.operateWithFailover(op);
    return op.getResult();
  }
  
  @Override
  public String addKeyspace(final KeyspaceDefinition ksdef) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE, getCredentials()) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.system_add_keyspace(((ThriftKsDef) ksdef).toThrift());
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    connectionManager.operateWithFailover(op);
    return op.getResult();
  }

}
