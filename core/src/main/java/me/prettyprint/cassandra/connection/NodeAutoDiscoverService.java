package me.prettyprint.cassandra.connection;

import java.util.concurrent.TimeUnit;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NodeAutoDiscoverService extends BackgroundCassandraHostService {

  private static final Logger log = LoggerFactory.getLogger(NodeAutoDiscoverService.class);

  public static final int DEF_AUTO_DISCOVERY_DELAY = 30;
 
  private NodeDiscovery newNodeSearch; 

  public NodeAutoDiscoverService(HConnectionManager connectionManager,
      CassandraHostConfigurator cassandraHostConfigurator) {
    super(connectionManager, cassandraHostConfigurator);
    this.retryDelayInSeconds = cassandraHostConfigurator.getAutoDiscoveryDelayInSeconds(); 
    newNodeSearch = new NodeDiscovery(cassandraHostConfigurator, connectionManager);
    sf = executor.scheduleWithFixedDelay(new QueryRing(), retryDelayInSeconds, retryDelayInSeconds, TimeUnit.SECONDS);
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

  public void doAddNodes() {
    newNodeSearch.doAddNodes();
  }
  
  class QueryRing implements Runnable { 
    @Override
    public void run() {
      doAddNodes();
    }
  }
  
}

