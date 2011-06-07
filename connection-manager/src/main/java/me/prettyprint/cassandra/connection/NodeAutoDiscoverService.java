package me.prettyprint.cassandra.connection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import me.prettyprint.hector.api.Keyspace;

import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.TokenRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NodeAutoDiscoverService extends BackgroundCassandraHostService {

  private static final Logger log = LoggerFactory.getLogger(NodeAutoDiscoverService.class);

  public static final int DEF_AUTO_DISCOVERY_DELAY = 30;


  public NodeAutoDiscoverService(HConnectionManager connectionManager,
      CassandraHostConfigurator cassandraHostConfigurator) {
    super(connectionManager, cassandraHostConfigurator);
    this.retryDelayInSeconds = cassandraHostConfigurator.getAutoDiscoveryDelayInSeconds();
    sf = executor.scheduleWithFixedDelay(new QueryRing(), retryDelayInSeconds,retryDelayInSeconds, TimeUnit.SECONDS);
  }

  @Override
  void shutdown() {
    log.error("Auto Discovery retry shutdown hook called");
    if ( sf != null ) {
      sf.cancel(true);
    }
    if ( executor != null ) {
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
    if ( log.isDebugEnabled() ) {
      log.debug("Auto discovery service running...");
    }
    Set<HCassandraHost> foundHosts = discoverNodes();
    if ( foundHosts != null && foundHosts.size() > 0 ) {
      log.info("Found {} new host(s) in Ring", foundHosts.size());
      for (HCassandraHost cassandraHost : foundHosts) {
        log.info("Addding found host {} to pool", cassandraHost);
        cassandraHostConfigurator.applyConfig(cassandraHost);
        connectionManager.addCassandraHost(cassandraHost);
      }
    }
    if ( log.isDebugEnabled() ) {
      log.debug("Auto discovery service run complete.");
    }
  }

  public Set<HCassandraHost> discoverNodes() {
    Set<HCassandraHost> existingHosts = connectionManager.getHosts();
    Set<HCassandraHost> foundHosts = new HashSet<HCassandraHost>();

    HThriftClient thriftClient = null;
    log.info("using existing hosts {}", existingHosts);
    try {
      thriftClient = connectionManager.borrowClient();

      for (KsDef keyspace : thriftClient.getCassandra().describe_keyspaces()) {
        if (!keyspace.getName().equals(Keyspace.KEYSPACE_SYSTEM)) {
          List<TokenRange> tokenRanges = thriftClient.getCassandra().describe_ring(keyspace.getName());
          for (TokenRange tokenRange : tokenRanges) {
            for (String host : tokenRange.getEndpoints()) {
              HCassandraHost foundHost = new HCassandraHostImpl(host,cassandraHostConfigurator.getPort());
              if ( !existingHosts.contains(foundHost) ) {
                log.info("Found a node we don't know about {} for TokenRange {}", foundHost, tokenRange);
                foundHosts.add(foundHost);
              }
            }
          }
          break;
        }
      }
    } catch (Exception e) {
      log.error("Discovery Service failed attempt to connect CassandraHost", e);
    } finally {
      connectionManager.releaseClient(thriftClient);
    }
    return foundHosts;
  }

}

