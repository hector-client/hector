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

  public Map<String,List<String>> describeSchemaVersions() throws HectorException {
    Operation<Map<String,List<String>>> op = new Operation<Map<String,List<String>>>(OperationType.META_READ, getCredentials()) {
      @Override
      public Map<String,List<String>> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.describe_schema_versions();
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
    return updateKeyspace(ksdef, false);
  }
  
  public String updateKeyspace(final KeyspaceDefinition ksdef, final boolean waitForSchemaAgreement) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE, FailoverPolicy.FAIL_FAST, getCredentials()) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          if (waitForSchemaAgreement) {
            waitForSchemaAgreement(cassandra);
          }
          String schemaId = cassandra.system_update_keyspace(toThriftImplementation(ksdef).toThrift());
          if (waitForSchemaAgreement) {
            waitForSchemaAgreement(cassandra);
          }
          return schemaId;
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
    return addColumnFamily(cfdef, false);
  }
  
  @Override
  public String addColumnFamily(final ColumnFamilyDefinition cfdef, final boolean waitForSchemaAgreement) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE,
        FailoverPolicy.FAIL_FAST,
        cfdef.getKeyspaceName(), 
        getCredentials()) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          if (waitForSchemaAgreement) {
            waitForSchemaAgreement(cassandra);
          }
          String schemaId = cassandra.system_add_column_family(toThriftImplementation(cfdef).toThrift());
          if (waitForSchemaAgreement) {
            waitForSchemaAgreement(cassandra);
          }
          return schemaId;
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
    return updateColumnFamily(cfdef, false);
  }
  
  @Override
  public String updateColumnFamily(final ColumnFamilyDefinition cfdef, final boolean waitForSchemaAgreement) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE,
        FailoverPolicy.FAIL_FAST,
        cfdef.getKeyspaceName(), 
        getCredentials()) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          if (waitForSchemaAgreement) {
            waitForSchemaAgreement(cassandra);
          }
          String schemaId = cassandra.system_update_column_family(toThriftImplementation(cfdef).toThrift());
          if (waitForSchemaAgreement) {
            waitForSchemaAgreement(cassandra);
          }
          return schemaId;
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
    return addKeyspace(ksdef, false);
  }
  
  @Override
  public String addKeyspace(final KeyspaceDefinition ksdef, final boolean waitForSchemaAgreement) throws HectorException {
    Operation<String> op = new Operation<String>(OperationType.META_WRITE, FailoverPolicy.FAIL_FAST, getCredentials()) {
      @Override
      public String execute(Cassandra.Client cassandra) throws HectorException {
        try {
          if (waitForSchemaAgreement) {
            waitForSchemaAgreement(cassandra);
          }
          String schemaId = cassandra.system_add_keyspace(toThriftImplementation(ksdef).toThrift());
          if (waitForSchemaAgreement) {
            waitForSchemaAgreement(cassandra);
          }
          return schemaId;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    connectionManager.operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public void onStartup() {
    if (getConfigurator().getRunAutoDiscoveryAtStartup()) {
      connectionManager.doAddNodes();
    }
  }

  private ThriftCfDef toThriftImplementation(final ColumnFamilyDefinition cfdef) {
  	if(cfdef instanceof ThriftCfDef) {
  	  return (ThriftCfDef) cfdef;
  	} else {
  	  return new ThriftCfDef(cfdef);
  	}
  }
  
  private ThriftKsDef toThriftImplementation(final KeyspaceDefinition cfdef) {
  	if(cfdef instanceof ThriftKsDef) {
  	  return (ThriftKsDef) cfdef;
  	} else {
  	  return new ThriftKsDef(cfdef);
  	}
  }
}
