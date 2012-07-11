package me.prettyprint.hector.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cassandra.thrift.TokenRange;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorException;

public interface Cluster {

  Set<CassandraHost> getKnownPoolHosts(boolean refresh);

  HConnectionManager getConnectionManager();


  /**
   * Adds the host to this Cluster. Unless skipApplyConfig is set to true, the settings in
   * the CassandraHostConfigurator will be applied to the provided CassandraHost
   * @param cassandraHost
   * @param skipApplyConfig
   */
  void addHost(CassandraHost cassandraHost, boolean skipApplyConfig);

  /**
   * Descriptive name of the cluster.
   * This name is used to identify the cluster.
   * @return
   */
  String getName();

   /**
    *
    * @deprecated use {@link #describeClusterName()}
    */
  String getClusterName() throws HectorException;

  String describeClusterName() throws HectorException;

  String describeThriftVersion() throws HectorException;
  
  Map<String, List<String>> describeSchemaVersions() throws HectorException;

  KeyspaceDefinition describeKeyspace(final String keyspace) throws HectorException;

  List<KeyspaceDefinition> describeKeyspaces() throws HectorException;
  
  List<TokenRange> describeRing(final String keyspace) throws HectorException;

  /**
   * Drops the Keyspace from the cluster. Equivalent of 'drop database' in SQL terms.
   * Does not wait for schema agreement.
   *
   */
  String dropKeyspace(final String keyspace) throws HectorException;
  
  /**
   * Drops a Keyspace and waits for schema cluster agreement if <code>waitForSchemaAgreement</code>
   * is set to true. Otherwise it behaves exactly like: {@link #dropKeyspace(String)}
   */
  String dropKeyspace(final String keyspace, boolean waitForSchemaAgreement) throws HectorException;

  /**
   * Updates the Keyspace from the cluster without waiting for schema agreement.
   */
  String updateKeyspace(final KeyspaceDefinition ksdef) throws HectorException;
  
  /**
   * Updates the Keyspace and waitz for schema cluster agreement if <code>waitForSchemaAgreement</code>
   * is set to true. Otherwise it behaves exactly like: {@link #updateKeyspace(KeyspaceDefinition)}
   */
  String updateKeyspace(final KeyspaceDefinition ksdef, boolean waitForSchemaAgreement) throws HectorException;

  String describePartitioner() throws HectorException;

  /**
   * Add a column family and returns without waiting for schema cluster agreement.
   */
  String addColumnFamily(final ColumnFamilyDefinition cfdef) throws HectorException;

  /**
   * Add a column family and wait for schema cluster agreement if <code>waitForSchemaAgreement</code>
   * is set to true. Otherwise it behaves exactly like: {@link #addColumnFamily(ColumnFamilyDefinition)}
   */
  String addColumnFamily(final ColumnFamilyDefinition cfdef, boolean waitForSchemaAgreement) throws HectorException;

  /**
   * Drops a column family and does not wait for schema agreement.
   */
  String dropColumnFamily(final String keyspaceName, final String columnFamily) throws HectorException;
  
  /**
   * Drops a column family and waits for schema cluster agreement if <code>waitForSchemaAgreement</code>
   * is set to true. Otherwise it behaves exactly like: {@link #dropColumnFamily(String, String)}
   */
  String dropColumnFamily(final String keyspaceName, final String columnFamily, boolean waitForSchemaAgreement) throws HectorException;
  
  /**
   * Updates a column family and does not wait for schema agreement.
   */
  String updateColumnFamily(final ColumnFamilyDefinition cfdef) throws HectorException;
  
  /**
   * Update a column family and wait for schema cluster agreement if <code>waitForSchemaAgreement</code>
   * is set to true. Otherwise it behaves exactly like: {@link #updateColumnFamily(me.prettyprint.hector.api.ddl.ColumnFamilyDefinition)}
   */
  String updateColumnFamily(final ColumnFamilyDefinition cfdef, boolean waitForSchemaAgreement) throws HectorException;

  /**
   * Add a keyspace and does not wait for schema agreement.
   */
  String addKeyspace(final KeyspaceDefinition ksdef) throws HectorException;
  
  /**
   * Add a keyspace and wait for schema cluster agreement if <code>waitForSchemaAgreement</code>
   * is set to true. Otherwise it behaves exactly like: {@link #addKeyspace(me.prettyprint.hector.api.ddl.KeyspaceDefinition)}
   */
  String addKeyspace(final KeyspaceDefinition ksdef, boolean waitForSchemaAgreement) throws HectorException;
  
  void truncate(final String keyspaceName, final String columnFamily) throws HectorException;

  Map<String, String> getCredentials();

  /**
   * called after the cluster has been initialized.  Default implementation
   * is to do nothing
   */
  void onStartup();
}