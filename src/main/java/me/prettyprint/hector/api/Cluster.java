package me.prettyprint.hector.api;

import java.util.List;
import java.util.Set;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.ddl.HCfDef;
import me.prettyprint.hector.api.ddl.HKsDef;
import me.prettyprint.hector.api.exceptions.HectorException;

public interface Cluster {

  Set<CassandraHost> getKnownPoolHosts(boolean refresh);

  HConnectionManager getConnectionManager();
  
  /**
   * These are all the hosts known to the cluster
   * @param refresh
   * @return
   */
  Set<String> getClusterHosts(boolean refresh);

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


  String describeClusterName() throws HectorException;

  String describeThriftVersion() throws HectorException;

  HKsDef describeKeyspace(final String keyspace) throws HectorException;

  String getClusterName() throws HectorException;

  List<HKsDef> describeKeyspaces() throws HectorException;

  /**
   * Drops the Keyspace from the cluster. Equivalent of 'drop database' in SQL terms
   *
   */
  String dropKeyspace(final String keyspace) throws HectorException;

  /**
   * Updates the Keyspace from the cluster.
   */
  String updateKeyspace(final HKsDef ksdef) throws HectorException;

  String describePartitioner() throws HectorException;

  String addColumnFamily(final HCfDef cfdef) throws HectorException;

  String dropColumnFamily(final String keyspaceName, final String columnFamily)
      throws HectorException;

  String addKeyspace(final HKsDef ksdef) throws HectorException;


}