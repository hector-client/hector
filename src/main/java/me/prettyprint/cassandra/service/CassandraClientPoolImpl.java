package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.HectorTransportException;
import me.prettyprint.cassandra.service.CassandraClientMonitor.Counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * We declare this pool as enum to make sure it stays a singlton in the system so clients may
 * efficiently be reused.
 *
 * @author Ran Tavory (ran@outbain.com)
 * @author zznate
 *
 */
/*package*/ class CassandraClientPoolImpl implements CassandraClientPool {

  private static final Logger log = LoggerFactory.getLogger(CassandraClientPoolImpl.class);
  /**
   * Mapping b/w the CassandraHost and the pool used to store connections to it.
   */
  private final Map<CassandraHost, CassandraClientPoolByHost> pools;

  private final CassandraClientMonitor clientMonitor;
  
  private CassandraHostConfigurator cassandraHostConfigurator;
  private Cluster cluster;

  public CassandraClientPoolImpl(CassandraClientMonitor clientMonitor) {
    log.info("Creating a CassandraClientPool");
    pools = new HashMap<CassandraHost, CassandraClientPoolByHost>();
    this.clientMonitor = clientMonitor;
    this.cluster = new Cluster("Default Cluster", this);
  }

  public CassandraClientPoolImpl(CassandraClientMonitor clientMonitor,
      CassandraHost[] cassandraHosts) {
    this(clientMonitor);
    log.info("Creating a CassandraClientPool with the following configuration: {}", cassandraHosts);
    for (CassandraHost cassandraHost: cassandraHosts) {
      log.debug("Maybe creating pool-by-host instance for {} at {}", cassandraHost, this);
      getPool(cassandraHost);
    }
    this.cluster = new Cluster("Default Cluster", this);
  }
  
  public CassandraClientPoolImpl(CassandraClientMonitor clientMonitor,
      CassandraHostConfigurator cassandraHostConfigurator) {
    this(clientMonitor, cassandraHostConfigurator.buildCassandraHosts());
    this.cassandraHostConfigurator = cassandraHostConfigurator;
    this.cluster = new Cluster("Default Cluster", this);
  }



  public CassandraClient borrowClient() throws HectorException {
    String[] clients = new String[pools.size()];
    int x = 0;
    for(CassandraHost cassandraHost : pools.keySet()) {
      clients[x] = cassandraHost.getUrl();
      x++;
    }
    return borrowClient(clients);
  }


  public CassandraClient borrowClient(String url, int port) throws HectorException {
    return getPool(new CassandraHost(url, port)).borrowClient();
  }
  
  public CassandraClient borrowClient(CassandraHost cassandraHost) throws HectorException {
    return getPool(cassandraHost).borrowClient();
  }


  public Set<String> getExhaustedPoolNames() {
    Set<String> hosts = new HashSet<String>();
    for (CassandraClientPoolByHost pool: pools.values()) {
      if (pool.isExhausted()) {
        hosts.add(pool.getName());
      }
    }
    return hosts;
  }


  public int getNumActive() {
    int count = 0;
    for (CassandraClientPoolByHost pool: pools.values()) {
      count += pool.getNumActive();
    }
    return count;
  }


  public int getNumBlockedThreads() {
    int count = 0;
    for (CassandraClientPoolByHost pool: pools.values()) {
      count += pool.getNumBlockedThreads();
    }
    return count;
  }


  public int getNumExhaustedPools() {
    int count = 0;
    for (CassandraClientPoolByHost pool: pools.values()) {
      if (pool.isExhausted()) {
        ++count;
      }
    }
    return count;
  }


  public int getNumIdle() {
    int count = 0;
    for (CassandraClientPoolByHost pool: pools.values()) {
      count += pool.getNumIdle();
    }
    return count;
  }


  public int getNumPools() {
    return pools.size();
  }

  public CassandraClientPoolByHost getPool(CassandraHost cassandraHost) {
    CassandraClientPoolByHost pool = pools.get(cassandraHost);
    if (pool == null) {
      if (cassandraHostConfigurator != null) {
        cassandraHostConfigurator.applyConfig(cassandraHost); 
      } 
      addCassandraHost(cassandraHost);
      pool = pools.get(cassandraHost);
    }
    return pool;
  }



  public Set<String> getPoolNames() {
    Set<String> names = new HashSet<String>();
    for (CassandraClientPoolByHost pool: pools.values()) {
      names.add(pool.getName());
    }
    return names;
  }


  public void releaseClient(CassandraClient client) throws HectorException {
    if (client == null) {
      log.error("client is null; cannot release, there's a bug dude");
      return;
    }
    getPool(client).releaseClient(client);
  }


  public void updateKnownHosts() throws HectorTransportException {
    synchronized(pools) {
      for (Iterator<Entry<CassandraHost, CassandraClientPoolByHost>> iterator = pools.entrySet().iterator(); iterator.hasNext();) {
        Entry<CassandraHost, CassandraClientPoolByHost> pool = iterator.next();
        if (pool.getValue().getLiveClients().isEmpty()) {
          if ( log.isInfoEnabled() ) {
            log.info("Found empty CassandraClientPoolByHost to remove: {}", pool.toString());
          }
          iterator.remove();
        }
      }
    }
  }


  public Set<CassandraHost> getKnownHosts() {
    return Collections.unmodifiableSet(pools.keySet());
  }



  public void invalidateClient(CassandraClient client) {
    getPool(client).invalidateClient(client);
  }

  void reportDestroyed(CassandraClient client) {
    ((CassandraClientPoolByHostImpl) getPool(client)).reportDestroyed(client);
  }

  private CassandraClientPoolByHost getPool(CassandraClient c) {
    return getPool(c.getCassandraHost());
  }


  public void releaseKeyspace(Keyspace k) throws HectorException {
    releaseClient(k.getClient());
  }


  public CassandraClient borrowClient(String urlPort) throws HectorException {
    String url = parseHostFromUrl(urlPort);
    int port = parsePortFromUrl(urlPort);
    return borrowClient(url, port);
  }


  public CassandraClient borrowClient(String[] clientUrls) throws HectorException {
    List<String> clients = new ArrayList<String>(Arrays.asList(clientUrls));
    while(!clients.isEmpty()) {
      int rand = (int) (Math.random() * clients.size());
      try {
        return borrowClient(clients.get(rand));
      } catch (HectorException e) {
        if (clients.size() > 1) {
          log.warn("Unable to obtain client " + clients.get(rand) + " will try the next client", e);
          clientMonitor.incCounter(Counter.RECOVERABLE_LB_CONNECT_ERRORS);
          clients.remove(rand);
        } else {
          throw e;
        }
      }
    }
    // Method should never get here; an exception must have been thrown before, I'm only writing
    // this to make the compiler happy.
    return null;
  }

  private String parseHostFromUrl(String urlPort) {
    return urlPort.substring(0, urlPort.lastIndexOf(':'));
  }

  private int parsePortFromUrl(String urlPort) {
    return Integer.valueOf(urlPort.substring(urlPort.lastIndexOf(':')+1, urlPort.length()));
  }


  public void invalidateAllConnectionsToHost(CassandraClient client) {
    getPool(client).invalidateAll();
  }


  public CassandraClientMonitorMBean getMbean() {
    return clientMonitor;
  }


  public String toString() {
    return "CassandraClientPoolImpl(" + pools + ")";
  }


  public void addCassandraHost(CassandraHost cassandraHost) {    
    synchronized (pools) {
      CassandraClientPoolByHost pool = pools.get(cassandraHost);
      if (pool == null) {         
        pool = new CassandraClientPoolByHostImpl(cassandraHost, this, clientMonitor);
        pools.put(cassandraHost, pool);
        if ( log.isDebugEnabled() ) {
          log.debug("GenerigObjectPool created: {} {}", pool, pool.hashCode());
        }
      }
    }    
  }


  public Cluster getCluster() {

    return cluster;
  }
  
  
  
  
}
