package me.prettyprint.cassandra.connection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.TokenRange;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.connection.CassandraHostRetryService.RetryRunner;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;


public class NodeAutoDiscoverService extends BackgroundCassandraHostService {
  
  private static final Logger log = LoggerFactory.getLogger(NodeAutoDiscoverService.class);
  
  private CassandraHost cassandraHost;
  
  public NodeAutoDiscoverService(HConnectionManager connectionManager,
      CassandraHostConfigurator cassandraHostConfigurator) {
    super(connectionManager, cassandraHostConfigurator);        
    sf = executor.scheduleWithFixedDelay(new QueryRing(), this.retryDelayInSeconds,this.retryDelayInSeconds, TimeUnit.SECONDS);
  }
  
  void shutdown() {
    log.error("Auto Discovery retry shutdown hook called");
    if ( sf != null ) 
      sf.cancel(true);
    if ( executor != null ) 
      executor.shutdownNow();     
    log.error("AutoDiscovery retry shutdown complete");    
  }
  
  public void applyRetryDelay() {
    // no op for now
  }
  
  class QueryRing implements Runnable {

    public void run() {
      if ( log.isDebugEnabled() ) {
        log.debug("Auto discovery service running...");
      }
      Set<CassandraHost> foundHosts = discoverNodes();
      if ( foundHosts != null && foundHosts.size() > 0 ) {
        log.info("Found {} new host(s) in Ring", foundHosts.size());
        for (CassandraHost cassandraHost : foundHosts) {
          log.info("Addding found host {} to pool", cassandraHost);
          cassandraHostConfigurator.applyConfig(cassandraHost);
          connectionManager.addCassandraHost(cassandraHost);
        }
      }
      if ( log.isDebugEnabled() ) {
        log.debug("Auto discovery service run complete.");
      }      
    }
    
  }
  
  public Set<CassandraHost> discoverNodes() {
    Set<CassandraHost> existingHosts = connectionManager.getHosts();
    Set<CassandraHost> foundHosts = new HashSet<CassandraHost>();
    
    HThriftClient thriftClient = null;
        
    try {
      thriftClient = connectionManager.borrowClient();
      List<TokenRange> tokens = thriftClient.getCassandra().describe_ring("System");
      for (TokenRange tokenRange : tokens) {
        if ( log.isDebugEnabled() ) {
          log.debug("Looking over TokenRange {} for new hosts", tokenRange);
        }
        List<String> endpoints = tokenRange.getEndpoints();
        for (String endpoint : endpoints) {
          CassandraHost foundHost = new CassandraHost(endpoint,cassandraHostConfigurator.getPort());
          if ( !existingHosts.contains(foundHost) ) {
            log.info("Found a node we don't know about {} for TokenRange {}", foundHost, tokenRange);
            foundHosts.add(foundHost);
          }
        }
        
      }
    } catch (Exception e) {
      //log.error("Downed Host retry failed attempt to verify CassandraHost", e);
    } finally {
      connectionManager.releaseClient(thriftClient);
    }      
    return foundHosts;
  }
}
  
