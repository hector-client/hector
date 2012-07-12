package me.prettyprint.cassandra.connection;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.cassandra.thrift.EndpointDetails;
import org.apache.cassandra.thrift.TokenRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NodeAutoDiscoverService extends BackgroundCassandraHostService {

  private static final Logger log = LoggerFactory.getLogger(NodeAutoDiscoverService.class);

  public static final int DEF_AUTO_DISCOVERY_DELAY = 30;

  private DataCenterValidator dataCenterValidator;


  public NodeAutoDiscoverService(HConnectionManager connectionManager,
                                 CassandraHostConfigurator cassandraHostConfigurator) {
    super(connectionManager, cassandraHostConfigurator);
    this.retryDelayInSeconds = cassandraHostConfigurator.getAutoDiscoveryDelayInSeconds();
    this.dataCenterValidator = new DataCenterValidator(cassandraHostConfigurator.getAutoDiscoveryDataCenters());
    sf = executor.scheduleWithFixedDelay(new QueryRing(), retryDelayInSeconds, retryDelayInSeconds, TimeUnit.SECONDS);
  }

  @Override
  void shutdown() {
    log.error("Auto Discovery retry shutdown hook called");
    if (sf != null) {
      sf.cancel(true);
    }
    if (executor != null) {
      executor.shutdownNow();
    }
    log.error("AutoDiscovery retry shutdown complete");
  }

  @Override
  public void applyRetryDelay() {
    // no op for now
  }

  class QueryRing implements Runnable {

    @Override
    public void run() {
      doAddNodes();
    }

  }

  public void doAddNodes() {
    if (log.isDebugEnabled()) {
      log.debug("Auto discovery service running...");
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
      log.debug("Auto discovery service run complete.");
    }
  }

  public Set<CassandraHost> discoverNodes() {
    Set<CassandraHost> existingHosts = connectionManager.getHosts();
    if (log.isDebugEnabled())
      log.debug("using existing hosts {}", existingHosts);
    return discoverNodesFromCluster(existingHosts);
  }

  private Set<CassandraHost> discoverNodesFromCluster(Set<CassandraHost> existingHosts) {
    try {
      //this could be suspect, but we need this
      ThriftCluster cluster = (ThriftCluster) HFactory.getCluster(connectionManager.getClusterName());
      for (KeyspaceDefinition keyspaceDefinition : cluster.describeKeyspaces()){
        if (!keyspaceDefinition.getName().equals(Keyspace.KEYSPACE_SYSTEM)){
          return processSystemTokenRangesForNewNodes(existingHosts, cluster.describeRing(keyspaceDefinition.getName()));
        }
      }
    } catch (Exception e) {
      log.error("Discovery Service failed attempt to connect CassandraHost", e);
    }
    return Collections.emptySet();
  }

  private Set<CassandraHost> processSystemTokenRangesForNewNodes(Set<CassandraHost> existingHosts, List<TokenRange> tokenRanges) {
    Set<CassandraHost> foundHosts = new HashSet<CassandraHost>();
    for (TokenRange tokenRange : tokenRanges)
      processTokenRangeForNewNodes(existingHosts, foundHosts, tokenRange);
    return foundHosts;
  }

  private void processTokenRangeForNewNodes(Set<CassandraHost> existingHosts, Set<CassandraHost> foundHosts, TokenRange tokenRange) {
    List<EndpointDetails> endpointDetails = tokenRange.getEndpoint_details();
    List<String> rpcEndpoints = tokenRange.getRpc_endpoints();

    //Sanity check. Assumes that endpointDetails and rpcEndpoints are in the same order and therefor the same length.
    if (endpointDetails.size() == rpcEndpoints.size()){
      processTokenRangeForNewNodesWithinValidDCs(existingHosts, foundHosts, tokenRange, endpointDetails, rpcEndpoints);
    }
  }

  private void processTokenRangeForNewNodesWithinValidDCs(Set<CassandraHost> existingHosts, Set<CassandraHost> foundHosts, TokenRange tokenRange, List<EndpointDetails> endpointDetails, List<String> rpcEndpoints) {
    for (int i = 0; i < endpointDetails.size(); i++) {
      // Check if we are allowed to include this Data Center.
      if (dataCenterValidator.validate(endpointDetails.get(i).getDatacenter())){
        CassandraHost foundHost = new CassandraHost(rpcEndpoints.get(i), cassandraHostConfigurator.getPort());
        // Maybe add this host if it's a new host.
        if (!existingHosts.contains(foundHost)) {
          log.info("Found a node we don't know about {} for TokenRange {}", foundHost, tokenRange);
          foundHosts.add(foundHost);
        }
      }
    }
  }

  /**
   * Abstraction to validate that the discovered nodes belong to a specific datacenters.
   *
   * @author patricioe (Patricio Echague - patricio@datastax.com)
   */
  class DataCenterValidator {

    Set<String> dataCenters;

    public DataCenterValidator(List<String> dataCenters) {
      if (dataCenters != null)
        this.dataCenters = new HashSet<String>(dataCenters);
    }

    public boolean validate(String dcName) {
      // If the DC is not defined (i.e: single cluster) always returns TRUE.
      if (dataCenters == null || dcName == null)
        return true;

      return dataCenters.contains(dcName);
    }
  }

}

