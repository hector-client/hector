package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * @author Nate McCall (nate@vervewireless.com)
 *
 */
/*package*/ class CassandraClientPoolImpl implements CassandraClientPool {

  private static final Logger log = LoggerFactory.getLogger(CassandraClientPoolImpl.class);
  /**
   * Mapping b/w the host identifier (url:port) and the pool used to store connections to it.
   */
  private final Map<CassandraHost, CassandraClientPoolByHost> pools;

  private final CassandraClientMonitor clientMonitor;
  
  private CassandraHostConfigurator cassandraHostConfigurator;

  public CassandraClientPoolImpl(CassandraClientMonitor clientMonitor) {
    log.info("Creating a CassandraClientPool");
    pools = new HashMap<CassandraHost, CassandraClientPoolByHost>();
    this.clientMonitor = clientMonitor;
  }

  public CassandraClientPoolImpl(CassandraClientMonitor clientMonitor,
      CassandraHost[] cassandraHosts) {
    this(clientMonitor);
    log.info("Creating a CassandraClientPool with the following configuration: {}", cassandraHosts);
    for (CassandraHost cassandraHost: cassandraHosts) {
      log.debug("Maybe creating pool-by-host instance for {} at {}", cassandraHost, this);
      getPool(cassandraHost);
    }
  }
  
  public CassandraClientPoolImpl(CassandraClientMonitor clientMonitor,
      CassandraHostConfigurator cassandraHostConfigurator) {
    this(clientMonitor, cassandraHostConfigurator.buildCassandraHosts());
    this.cassandraHostConfigurator = cassandraHostConfigurator;    
  }


  @Override
  public CassandraClient borrowClient() throws HectorException {
    String[] clients = new String[pools.size()];
    int x = 0;
    for(CassandraHost cassandraHost : pools.keySet()) {
      clients[x] = cassandraHost.getUrl();
      x++;
    }
    return borrowClient(clients);
  }

  @Override
  public CassandraClient borrowClient(String url, int port) throws HectorException {
    return getPool(new CassandraHost(url, port)).borrowClient();
  }
  
  public CassandraClient borrowClient(CassandraHost cassandraHost) throws HectorException {
    return getPool(cassandraHost).borrowClient();
  }

  @Override
  public Set<String> getExhaustedPoolNames() {
    Set<String> hosts = new HashSet<String>();
    for (CassandraClientPoolByHost pool: pools.values()) {
      if (pool.isExhausted()) {
        hosts.add(pool.getName());
      }
    }
    return hosts;
  }

  @Override
  public int getNumActive() {
    int count = 0;
    for (CassandraClientPoolByHost pool: pools.values()) {
      count += pool.getNumActive();
    }
    return count;
  }

  @Override
  public int getNumBlockedThreads() {
    int count = 0;
    for (CassandraClientPoolByHost pool: pools.values()) {
      count += pool.getNumBlockedThreads();
    }
    return count;
  }

  @Override
  public int getNumExhaustedPools() {
    int count = 0;
    for (CassandraClientPoolByHost pool: pools.values()) {
      if (pool.isExhausted()) {
        ++count;
      }
    }
    return count;
  }

  @Override
  public int getNumIdle() {
    int count = 0;
    for (CassandraClientPoolByHost pool: pools.values()) {
      count += pool.getNumIdle();
    }
    return count;
  }

  @Override
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


  @Override
  public Set<String> getPoolNames() {
    Set<String> names = new HashSet<String>();
    for (CassandraClientPoolByHost pool: pools.values()) {
      names.add(pool.getName());
    }
    return names;
  }

  @Override
  public void releaseClient(CassandraClient client) throws HectorException {
    if (client == null) {
      log.error("client is null; cannot release, there's a bug dude");
      return;
    }
    getPool(client).releaseClient(client);
  }

  @Override
  public void updateKnownHosts() throws HectorTransportException {
    for (CassandraClientPoolByHost pool: pools.values()) {
      if (pool.getLiveClients().isEmpty()) {
        log.info("Found empty CassandraClientPoolByHost to remove: {}", pool.toString());
        pools.remove(pool.getCassandraHost());
        // TODO add the removed host to another map to be retried later
      }      
    }
  }

  @Override
  public Set<String> getKnownHosts() {
    Set<String> hosts = new HashSet<String>();    
    for (CassandraClientPoolByHost pool: pools.values()) {
      hosts.add(pool.getCassandraHost().getIp());
    }
    return hosts;
  }


  @Override
  public void invalidateClient(CassandraClient client) {
    getPool(client).invalidateClient(client);
  }

  void reportDestroyed(CassandraClient client) {
    ((CassandraClientPoolByHostImpl) getPool(client)).reportDestroyed(client);
  }

  private CassandraClientPoolByHost getPool(CassandraClient c) {
    return getPool(new CassandraHost(c.getUrl(), c.getPort()));
  }

  @Override
  public void releaseKeyspace(Keyspace k) throws HectorException {
    releaseClient(k.getClient());
  }

  @Override
  public CassandraClient borrowClient(String urlPort) throws HectorException {
    String url = parseHostFromUrl(urlPort);
    int port = parsePortFromUrl(urlPort);
    return borrowClient(url, port);
  }

  @Override
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

  @Override
  public void invalidateAllConnectionsToHost(CassandraClient client) {
    getPool(client).invalidateAll();
  }

  @Override
  public CassandraClientMonitorMBean getMbean() {
    return clientMonitor;
  }

  @Override
  public String toString() {
    return "CassandraClientPoolImpl(" + pools + ")";
  }

  @Override
  public void addCassandraHost(CassandraHost cassandraHost) {    
    synchronized (pools) {
      CassandraClientPoolByHost pool = pools.get(cassandraHost);
      if (pool == null) {         
        pool = new CassandraClientPoolByHostImpl(cassandraHost, this, clientMonitor);
        pools.put(cassandraHost, pool);
        log.debug("GenerigObjectPool created: {} {}", pool, pool.hashCode());
      }
    }    
  }
  
  
}
