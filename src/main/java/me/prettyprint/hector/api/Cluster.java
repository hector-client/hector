package me.prettyprint.hector.api;

import java.util.Map;
import java.util.Set;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.TimestampResolution;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorPoolException;

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

  public abstract TimestampResolution getTimestampResolution();

  public abstract Cluster setTimestampResolution(
      TimestampResolution timestampResolution);

  public abstract long createTimestamp();

  public abstract Set<String> describeKeyspaces() throws HectorException;

  public abstract String describeClusterName() throws HectorException;

  public abstract String describeThriftVersion() throws HectorException;

  public abstract Map<String, Map<String, String>> describeKeyspace(
      final String keyspace) throws HectorException;

  public abstract String getClusterName() throws HectorException;
}