package me.prettyprint.cassandra.connection;

import me.prettyprint.cassandra.connection.client.HClient;
import me.prettyprint.cassandra.connection.factory.HClientFactory;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorTransportException;
import me.prettyprint.hector.api.factory.HFactory;
import org.apache.cassandra.thrift.TokenRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CassandraHostRetryService extends BackgroundCassandraHostService {

  private static Logger log = LoggerFactory.getLogger(CassandraHostRetryService.class);

  public static final int DEF_QUEUE_SIZE = -1;
  public static final int DEF_RETRY_DELAY = 10;

  private final HClientFactory clientFactory;
  private final LinkedBlockingQueue<CassandraHost> downedHostQueue;
  private ConnectionManagerListenersHandler listenerHandler;
  private boolean autoDiscoverHosts;

  public CassandraHostRetryService(HConnectionManager connectionManager, HClientFactory clientFactory,
      CassandraHostConfigurator cassandraHostConfigurator, ConnectionManagerListenersHandler listenerHandler) {

    super(connectionManager, cassandraHostConfigurator);
    this.clientFactory = clientFactory;

    this.listenerHandler = listenerHandler;
    this.retryDelayInSeconds = cassandraHostConfigurator.getRetryDownedHostsDelayInSeconds();
    this.autoDiscoverHosts = cassandraHostConfigurator.getAutoDiscoverHosts();
    downedHostQueue = new LinkedBlockingQueue<CassandraHost>(cassandraHostConfigurator.getRetryDownedHostsQueueSize() < 1
        ? Integer.MAX_VALUE : cassandraHostConfigurator.getRetryDownedHostsQueueSize());

    sf = executor.scheduleWithFixedDelay(new RetryRunner(), this.retryDelayInSeconds,this.retryDelayInSeconds, TimeUnit.SECONDS);

    log.info("Downed Host Retry service started with queue size {} and retry delay {}s",
        cassandraHostConfigurator.getRetryDownedHostsQueueSize(),
        retryDelayInSeconds);
  }

  @Override
  void shutdown() {
    log.info("Downed Host retry shutdown hook called");
    if ( sf != null ) {
      sf.cancel(true);
    }
    if ( executor != null ) {
      executor.shutdownNow();
    }
    log.info("Downed Host retry shutdown complete");
  }

  public void add(final CassandraHost cassandraHost) {
    downedHostQueue.add(cassandraHost);
    if ( log.isInfoEnabled() ) {
      log.info("Host detected as down was added to retry queue: {}", cassandraHost.getName());
    }

    //schedule a check of this host immediately,
    executor.submit(new Runnable() {
      @Override
      public void run() {
        if(downedHostQueue.contains(cassandraHost) && verifyConnection(cassandraHost)) {
          if (connectionManager.addCassandraHost(cassandraHost)) {
            listenerHandler.fireOnHostRestored(cassandraHost);
            downedHostQueue.remove(cassandraHost);
          }
          return;
        }
      }
    });
  }

  public boolean remove(CassandraHost cassandraHost) {
      return downedHostQueue.remove(cassandraHost);
  }

  public boolean contains(CassandraHost cassandraHost) {
    return downedHostQueue.contains(cassandraHost);
  }

  public Set<CassandraHost> getDownedHosts() {
    return Collections.unmodifiableSet(new HashSet<CassandraHost>(downedHostQueue));
  }

  @Override
  public void applyRetryDelay() {
    sf.cancel(false);
    executor.schedule(new RetryRunner(), retryDelayInSeconds, TimeUnit.SECONDS);
  }

  public void flushQueue() {
    downedHostQueue.clear();
    log.info("Downed Host retry queue flushed.");
  }



  class RetryRunner implements Runnable {

    @Override
    public void run() {
      if(!downedHostQueue.isEmpty()) {
        log.debug("Retry service fired, checking {} downed hosts.", downedHostQueue.size());
        try {
          retryDownedHosts();
        } catch (Throwable t) {
          log.error("An error occurred while retrying one or more downed hosts", t);
        }
      } else {
        log.debug("Retry service fired... nothing to do.");
      }
    }
    
    private void retryDownedHosts() {
      // we only check the ring if we have nodes in the cluster to query
      // and auto discovery is on. Otherwise we risk removing hosts from the ring with no way to re-add them
      boolean checkRing = connectionManager.getHosts().size() > 0 && autoDiscoverHosts ? true : false;
      Set<CassandraHost> ringInfo = null;
      if( checkRing) {
        // Let's check the ring just once per cycle.
        ringInfo = buildRingInfo();
        if (ringInfo!=null && ringInfo.isEmpty()) {
          ringInfo = null;
          log.warn("Got an empty ring info, maybe not enough permission");
        }
      }

      Iterator<CassandraHost> iter = downedHostQueue.iterator();
      while( iter.hasNext() ) {
        CassandraHost cassandraHost = iter.next();

        if( cassandraHost == null ) {
          continue;
        }

        if (connectionManager.getHosts().size() == 0) {
          listenerHandler.fireOnAllHostsDown();
          log.info("Not checking that {} is a member of the ring since there are no live hosts", cassandraHost);
        }

        // The host may have been removed from the ring. It makes no sense to keep trying
        // to connect to it. If the ThriftCluster is unknown to HFactory, ringInfo may not be available,
        // in which case we have no choice but to continue checking.
        if ( checkRing && ringInfo != null && !ringInfo.contains(cassandraHost)) {
          log.info("Removing host " + cassandraHost.getName() + " - It does no longer exist in the ring.");
          iter.remove();
          continue;
        }

        boolean reconnected = verifyConnection(cassandraHost);
        log.info("Downed Host retry status {} with host: {}", reconnected, cassandraHost.getName());
        if ( reconnected ) {
          connectionManager.addCassandraHost(cassandraHost);
          //we can't call iter.remove() based on return value of connectionManager.addCassandraHost, since
          //that returns false if an error occurs, or if the host already exists
          if(connectionManager.getHosts().contains(cassandraHost)) {
            listenerHandler.fireOnHostRestored(cassandraHost);
            iter.remove();
          }
        }
      }      
    }


    private Set<CassandraHost> buildRingInfo() {

      ThriftCluster cluster = (ThriftCluster) HFactory.getCluster(connectionManager.getClusterName());

      // ThriftCluster is not exclusively created & cached by HFactory. E.g. Some users instantiate directly via Spring.
      if(cluster != null) {
        Set<CassandraHost> ringInfo = new HashSet<CassandraHost>();
        for(KeyspaceDefinition keyspaceDefinition: cluster.describeKeyspaces()) {
          if (!keyspaceDefinition.getName().equals(Keyspace.KEYSPACE_SYSTEM)) {
            List<TokenRange> tokenRanges = cluster.describeRing(keyspaceDefinition.getName());
            for (TokenRange tokenRange : tokenRanges) {
              for (String host : tokenRange.getRpc_endpoints()) {
                CassandraHost aHost = new CassandraHost(host, cassandraHostConfigurator.getPort());
                if (!ringInfo.contains(aHost) ) {
                  ringInfo.add(aHost);
                }
              }
            }
            break;
          }
        }
        return ringInfo;
      } else {
        return null;
      }
    }
  }


  private boolean verifyConnection(CassandraHost cassandraHost) {
    if ( cassandraHost == null ) {
      return false;
    }
    boolean found = false;
    HClient client = clientFactory.createClient(cassandraHost);
    try {
      client.open();
      found = client.getCassandra().describe_cluster_name() != null;
      client.close();
    } catch (HectorTransportException he) {
      log.warn("Downed {} host still appears to be down: {}", cassandraHost, he.getMessage());
    } catch (Throwable t) {
      log.error("Downed Host retry failed attempt to verify CassandraHost", t);
    }
    return found;
  }
}
