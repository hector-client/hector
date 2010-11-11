package me.prettyprint.cassandra.connection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.TokenRange;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;


public class NodeAutoDiscoverService extends BackgroundCassandraHostService {
  
  private static final Logger log = LoggerFactory.getLogger(NodeAutoDiscoverService.class);
  
  private CassandraHost cassandraHost;
  
  public NodeAutoDiscoverService(HConnectionManager connectionManager,
      CassandraHostConfigurator cassandraHostConfigurator) {
    super(connectionManager, cassandraHostConfigurator);

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
    
    private Set<CassandraHost> discoverNodes() {
      Set<CassandraHost> existingHosts = connectionManager.getHosts();
      Set<CassandraHost> foundHosts = new HashSet<CassandraHost>();
      TTransport tr = cassandraHost.getUseThriftFramedTransport() ? 
          new TFramedTransport(new TSocket(cassandraHost.getHost(), cassandraHost.getPort(), 10)) :
            new TSocket(cassandraHost.getHost(), cassandraHost.getPort(), 10);
      
      TProtocol proto = new TBinaryProtocol(tr);
      Cassandra.Client client = new Cassandra.Client(proto);
      try {
        tr.open();
        List<TokenRange> tokens = client.describe_ring("System");
        for (TokenRange tokenRange : tokens) {
          List<String> endpoints = tokenRange.getEndpoints();
          for (String endpoint : endpoints) {
            CassandraHost foundHost = new CassandraHost(endpoint,cassandraHostConfigurator.getPort());
            if ( !existingHosts.contains(foundHost) ) {
              foundHosts.add(foundHost);
            }
          }
          
        }
      } catch (Exception e) {
        //log.error("Downed Host retry failed attempt to verify CassandraHost", e);
      } finally {
        tr.close();
      }      
      return foundHosts;
    }
  }
}
  
