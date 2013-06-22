package me.prettyprint.cassandra.connection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.cassandra.thrift.EndpointDetails;
import org.apache.cassandra.thrift.TokenRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;

public class NodeDiscovery {
  private static final Logger log = LoggerFactory.getLogger(NodeDiscovery.class);

  private CassandraHostConfigurator cassandraHostConfigurator;
  private HConnectionManager connectionManager;
  private DataCenterValidator dataCenterValidator;

  public NodeDiscovery(CassandraHostConfigurator cassandraHostConfigurator, HConnectionManager connectionManager) {
    this.cassandraHostConfigurator = cassandraHostConfigurator;
    this.connectionManager = connectionManager;
    this.dataCenterValidator = new DataCenterValidator(cassandraHostConfigurator.getAutoDiscoveryDataCenters());
  }
  
  /**
   * Find any nodes that are not already in the connection manager but are in
   * the cassandra ring and add them
   */
  public void doAddNodes() {
    if (log.isDebugEnabled()) {
      log.debug("Node discovery running...");
    }
    Set<CassandraHost> foundHosts = discoverNodes();
    if (foundHosts != null && foundHosts.size() > 0) {
      log.info("Found {} new host(s) in Ring", foundHosts.size());
      for (CassandraHost cassandraHost : foundHosts) {
        log.info("Addding found host {} to pool", cassandraHost);
        cassandraHostConfigurator.applyConfig(cassandraHost);
        connectionManager.addCassandraHost(cassandraHost);
      }
    }
    if (log.isDebugEnabled()) {
      log.debug("Node discovery run complete.");
    }
  }

  /**
   * Find any unknown nodes in the cassandra ring
   */
  public Set<CassandraHost> discoverNodes() {
    Cluster cluster =  HFactory.getCluster(connectionManager.getClusterName());
    if (cluster == null) {
      return null;
    }

    Set<CassandraHost> existingHosts = connectionManager.getHosts();
    Set<CassandraHost> foundHosts = new HashSet<CassandraHost>();

    if (log.isDebugEnabled()) {
      log.debug("using existing hosts {}", existingHosts);
    }

    try {

      for (KeyspaceDefinition keyspaceDefinition : cluster.describeKeyspaces()) {
        if (!keyspaceDefinition.getName().equals(Keyspace.KEYSPACE_SYSTEM)) {
          List<TokenRange> tokenRanges = cluster.describeRing(keyspaceDefinition.getName());
          for (TokenRange tokenRange : tokenRanges) {

            for (EndpointDetails endPointDetail : tokenRange.getEndpoint_details()) {
              // Check if we are allowed to include this Data
              // Center.
              if (dataCenterValidator == null
                  || dataCenterValidator.validate(endPointDetail.getDatacenter())) {
                // Maybe add this host if it's a new host.
                CassandraHost foundHost = new CassandraHost(endPointDetail.getHost(),
                    cassandraHostConfigurator.getPort());
                if (!existingHosts.contains(foundHost)) {
                  log.info("Found a node we don't know about {} for TokenRange {}", foundHost,
                      tokenRange);
                  foundHosts.add(foundHost);
                }
              }
            }

          }
          break;
        }
      }
    } catch (Exception e) {
      log.error("Discovery Service failed attempt to connect CassandraHost", e);
    }

    return foundHosts;
  }

  /**
   * Abstraction to validate that the discovered nodes belong to a specific
   * datacenters.
   * 
   * @author patricioe (Patricio Echague - patricio@datastax.com)
   * 
   */
  class DataCenterValidator {

    Set<String> dataCenters;

    public DataCenterValidator(List<String> dataCenters) {
      if (dataCenters != null)
        this.dataCenters = new HashSet<String>(dataCenters);
    }

    public boolean validate(String dcName) {
      // If the DC is not defined (i.e: single cluster) always returns
      // TRUE.
      if (dataCenters == null || dcName == null)
        return true;

      return dataCenters.contains(dcName);
    }
  }

}
