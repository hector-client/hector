package me.prettyprint.cassandra.service;

import java.util.List;
import java.util.Set;

import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorPoolException;

import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.TokenRange;

public interface Cluster {

  public abstract Set<CassandraHost> getKnownPoolHosts(boolean refresh);

  /**
   * These are all the hosts known to the cluster
   * @param refresh
   * @return
   */
  public abstract Set<String> getClusterHosts(boolean refresh);

  /**
   * Adds the host to this Cluster. Unless skipApplyConfig is set to true, the settings in
   * the CassandraHostConfigurator will be applied to the provided CassandraHost
   * @param cassandraHost
   * @param skipApplyConfig
   */
  public abstract void addHost(CassandraHost cassandraHost,
      boolean skipApplyConfig);

  /**
   * Descriptive name of the cluster.
   * This name is used to identify the cluster.
   * @return
   */
  public abstract String getName();

  public abstract CassandraClient borrowClient() throws HectorPoolException;

  public abstract void releaseClient(CassandraClient client);

  public abstract ClockResolution getClockResolution();

  public abstract Cluster setClockResolution(ClockResolution clockResolution);

  public abstract long createClock();

  public abstract List<KsDef> describeKeyspaces() throws HectorException;

  public abstract String describeClusterName() throws HectorException;

  public abstract String describeThriftVersion() throws HectorException;

  public abstract KsDef describeKeyspace(final String keyspace)
      throws HectorException;

  public abstract String getClusterName() throws HectorException;

  public abstract List<TokenRange> describeRing(final String keyspace)
      throws HectorException;

  public abstract String describePartitioner() throws HectorException;

  /**
   * Renames the Keyspace from oldName to newName
   *
   * @return the new SchemaId
   * @throws HectorException
   */
  public abstract String renameKeyspace(final String oldName,
      final String newName) throws HectorException;

  /**
   * Renames the ColumnFamily from oldName to newName
   *
   * @return the new SchemaId
   * @throws HectorException
   */
  public abstract String renameColumnFamily(final String oldName,
      final String newName) throws HectorException;

  /**
   * Drops the Keyspace from the cluster. Equivalent of 'drop database' in SQL terms
   *
   */
  public abstract String dropKeyspace(final String keyspace)
      throws HectorException;

  /**
   * Drops the Keyspace from the cluster. Equivalent of 'drop database' in SQL terms
   *
   */
  public abstract String addKeyspace(final KsDef ksdef) throws HectorException;

  /**
   * Updates the Keyspace from the cluster.
   */
  public abstract String updateKeyspace(final KsDef ksdef)
      throws HectorException;

  /**
   * Drops the ColumnFamily from the Keyspace. Equivalent of 'drop table' in SQL terms
   *
   */
  public abstract String dropColumnFamily(final String keyspaceName,
      final String columnFamily) throws HectorException;

  public abstract String addColumnFamily(final CfDef cfdef)
      throws HectorException;

  public abstract String updateColumnFamily(final CfDef cfdef)
      throws HectorException;

}