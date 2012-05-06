package me.prettyprint.cassandra.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import me.prettyprint.cassandra.connection.CassandraHostRetryService;
import me.prettyprint.cassandra.connection.HOpTimer;
import me.prettyprint.cassandra.connection.HostTimeoutTracker;
import me.prettyprint.cassandra.connection.LoadBalancingPolicy;
import me.prettyprint.cassandra.connection.NodeAutoDiscoverService;
import me.prettyprint.cassandra.connection.NullOpTimer;
import me.prettyprint.cassandra.connection.RoundRobinBalancingPolicy;
import me.prettyprint.hector.api.ClockResolution;
import me.prettyprint.hector.api.factory.HFactory;


public final class CassandraHostConfigurator implements Serializable {
  // update this if you make changes
  private static final long serialVersionUID = -5798876803582956262L;

  private String hosts;
  private int port = CassandraHost.DEFAULT_PORT;
  private int maxActive = CassandraHost.DEFAULT_MAX_ACTIVE;
  private boolean lifo = CassandraHost.DEFAULT_LIFO;
  private long maxWaitTimeWhenExhausted = CassandraHost.DEFAULT_MAX_WAITTIME_WHEN_EXHAUSTED;
  private int cassandraThriftSocketTimeout;
  private ClockResolution clockResolution = DEF_CLOCK_RESOLUTION;
  private boolean useThriftFramedTransport = CassandraHost.DEFAULT_USE_FRAMED_THRIFT_TRANSPORT;
  
  private boolean retryDownedHosts = true;
  private int retryDownedHostsQueueSize = CassandraHostRetryService.DEF_QUEUE_SIZE;
  private int retryDownedHostsDelayInSeconds = CassandraHostRetryService.DEF_RETRY_DELAY;

  private boolean autoDiscoverHosts = false;
  private int autoDiscoveryDelayInSeconds = NodeAutoDiscoverService.DEF_AUTO_DISCOVERY_DELAY;
  private List<String> autoDiscoveryDataCenters;

  private LoadBalancingPolicy loadBalancingPolicy = new RoundRobinBalancingPolicy();
  public static final ClockResolution DEF_CLOCK_RESOLUTION = HFactory.createClockResolution(ClockResolution.MICROSECONDS_SYNC);
  private int hostTimeoutCounter = HostTimeoutTracker.DEF_TIMEOUT_COUNTER;
  private int hostTimeoutWindow = HostTimeoutTracker.DEF_TIMEOUT_WINDOW;
  private int hostTimeoutSuspensionDurationInSeconds = HostTimeoutTracker.DEF_NODE_SUSPENSION_DURATION_IN_SECONDS;
  private int hostTimeoutUnsuspendCheckDelay = HostTimeoutTracker.DEF_NODE_UNSUSPEND_CHECK_DELAY_IN_SECONDS;
  private boolean useHostTimeoutTracker = false;
  private boolean runAutoDiscoveryAtStartup = false;
  private boolean useSocketKeepalive = false;
  private HOpTimer opTimer = new NullOpTimer();
  private boolean useKerberosAuthentication = false;


  public CassandraHostConfigurator() {
    this.hosts = null;
  }

  public CassandraHostConfigurator(String hosts) {
    this.hosts = hosts;
  }

  public CassandraHost[] buildCassandraHosts() {
    if (this.hosts == null) {
      throw new IllegalArgumentException("Need to define at least one host in order to apply configuration.");
    }
    String[] hostVals = hosts.split(",");
    CassandraHost[] cassandraHosts = new CassandraHost[hostVals.length];
    for (int x=0; x<hostVals.length; x++) {
      CassandraHost cassandraHost = this.port == CassandraHost.DEFAULT_PORT ? new CassandraHost(hostVals[x].trim()) : new CassandraHost(hostVals[x], this.port);
      applyConfig(cassandraHost);
      cassandraHosts[x] = cassandraHost;
    }
    return cassandraHosts;
  }

  public void applyConfig(CassandraHost cassandraHost) {

    cassandraHost.setMaxActive(maxActive);
    cassandraHost.setLifo(lifo);
    cassandraHost.setMaxWaitTimeWhenExhausted(maxWaitTimeWhenExhausted);
    cassandraHost.setUseThriftFramedTransport(useThriftFramedTransport);
    cassandraHost.setUseSocketKeepalive(useSocketKeepalive);

    // this is special as it can be passed in as a system property
    if (cassandraThriftSocketTimeout > 0) {
      cassandraHost.setCassandraThriftSocketTimeout(cassandraThriftSocketTimeout);
    }
  }

  public void setHosts(String hosts) {
    this.hosts = hosts;
  }

  public void setMaxActive(int maxActive) {
    this.maxActive = maxActive;
  }


  public void setMaxWaitTimeWhenExhausted(long maxWaitTimeWhenExhausted) {
    this.maxWaitTimeWhenExhausted = maxWaitTimeWhenExhausted;
  }

  /**
   * The value (in milliseconds) which gets passed down to {@link java.net.Socket#setSoTimeout(int)}
   * used by the underlying Thrift transport.
   */
  public void setCassandraThriftSocketTimeout(int cassandraThriftSocketTimeout) {
    this.cassandraThriftSocketTimeout = cassandraThriftSocketTimeout;
  }

  public boolean getRetryDownedHosts() {
    return this.retryDownedHosts;
  }

  public void setRetryDownedHosts(boolean retryDownedHosts) {
    this.retryDownedHosts = retryDownedHosts;
  }

  public void setRetryDownedHostsQueueSize(int retryDownedHostsQueueSize) {
    this.retryDownedHostsQueueSize = retryDownedHostsQueueSize;
  }

  public int getRetryDownedHostsQueueSize() {
    return retryDownedHostsQueueSize;
  }

  public void setRetryDownedHostsDelayInSeconds(int retryDownedHostsDelayInSeconds) {
    this.retryDownedHostsDelayInSeconds = retryDownedHostsDelayInSeconds;
  }

  public int getRetryDownedHostsDelayInSeconds() {
    return retryDownedHostsDelayInSeconds;
  }

  /**
   * @param resolutionString one of "SECONDS", "MILLISECONDS", "MICROSECONDS" or "MICROSECONDS_SYNC"
   */
  public void setClockResolution(String resolutionString) {
    clockResolution = HFactory.createClockResolution(resolutionString);
  }

  public HOpTimer getOpTimer() {
	  return opTimer;
  }

  public void setOpTimer(HOpTimer opTimer) {
	  this.opTimer = opTimer;
  }
  
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("CassandraHostConfigurator<");
    s.append("clockResolution=");
    s.append(clockResolution);
    s.append("&cassandraThriftSocketTimeout=");
    s.append(cassandraThriftSocketTimeout);
    s.append("&maxWaitTimeWhenExhausted=");
    s.append(maxWaitTimeWhenExhausted);
    s.append("&maxActive=");
    s.append(maxActive);
    s.append("&hosts=");
    s.append(hosts);
    s.append("&useThriftFramedTransport=");
    s.append(useThriftFramedTransport);
    s.append("&retryDownedHosts=");
    s.append(retryDownedHosts);
    s.append("&opTimer=");
    s.append(opTimer);    
    s.append(">");
    return s.toString();
  }

  public boolean getLifo() {
    return lifo;
  }

  public void setLifo(boolean lifo) {
    this.lifo = lifo;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setUseThriftFramedTransport(boolean useThriftFramedTransport) {
    this.useThriftFramedTransport = useThriftFramedTransport;
  }

  public ClockResolution getClockResolution() {
    return clockResolution;
  }

  public void setClockResolution(ClockResolution clockResolution) {
    this.clockResolution = clockResolution;
  }

  public boolean getAutoDiscoverHosts() {
    return autoDiscoverHosts;
  }

  public void setAutoDiscoverHosts(boolean autoDiscoverHosts) {
    this.autoDiscoverHosts = autoDiscoverHosts;
  }

  public int getAutoDiscoveryDelayInSeconds() {
    return autoDiscoveryDelayInSeconds;
  }

  public void setAutoDiscoveryDelayInSeconds(int autoDiscoveryDelayInSeconds) {
    this.autoDiscoveryDelayInSeconds = autoDiscoveryDelayInSeconds;
  }

  /**
   * Sets the local datacenter for the DiscoveryService. Nodes out of this 
   * datacenter will be discarded.
   * @param dataCenter DataCenter name
   */
  public void setAutoDiscoveryDataCenter(String dataCenter) {
    this.autoDiscoveryDataCenters = Arrays.asList(dataCenter);
  }

  /**
   * Sets the datacenters for the DiscoveryService. Nodes out of these 
   * datacenters will be discarded.
   */
  public void setAutoDiscoveryDataCenter(List<String> dataCenters) {
    this.autoDiscoveryDataCenters = dataCenters;
  }

  /**
   * Retrieves the 'local' datacenter names that the DiscoveryService recognizes as valid 
   * in order to discover new hosts.
   * @return a list of 'local' datacenter names
   */
  public List<String> getAutoDiscoveryDataCenters() {
    return autoDiscoveryDataCenters;
  }

  public LoadBalancingPolicy getLoadBalancingPolicy() {
    return loadBalancingPolicy;
  }

  public void setLoadBalancingPolicy(LoadBalancingPolicy loadBalancingPolicy) {
    this.loadBalancingPolicy = loadBalancingPolicy;
  }

  public int getHostTimeoutCounter() {
    return hostTimeoutCounter;
  }

  public void setHostTimeoutCounter(int hostTimeoutCounter) {
    this.hostTimeoutCounter = hostTimeoutCounter;
  }

  public int getHostTimeoutWindow() {
    return hostTimeoutWindow;
  }

  public void setHostTimeoutWindow(int hostTimeoutWindow) {
    this.hostTimeoutWindow = hostTimeoutWindow;
  }

  public int getHostTimeoutSuspensionDurationInSeconds() {
    return hostTimeoutSuspensionDurationInSeconds;
  }

  public void setHostTimeoutSuspensionDurationInSeconds(int hostTimeoutSuspensionDurationInSeconds) {
    this.hostTimeoutSuspensionDurationInSeconds = hostTimeoutSuspensionDurationInSeconds;
  }

  public int getHostTimeoutUnsuspendCheckDelay() {
    return hostTimeoutUnsuspendCheckDelay;
  }

  public void setHostTimeoutUnsuspendCheckDelay(int hostTimeoutUnsuspendCheckDelay) {
    this.hostTimeoutUnsuspendCheckDelay = hostTimeoutUnsuspendCheckDelay;
  }

  public boolean getUseHostTimeoutTracker() {
    return useHostTimeoutTracker;
  }

  public void setUseHostTimeoutTracker(boolean useHostTimeoutTracker) {
    this.useHostTimeoutTracker = useHostTimeoutTracker;
  }

  public boolean getRunAutoDiscoveryAtStartup() {
    return runAutoDiscoveryAtStartup;
  }

  /**
   * Set to true to run {@link NodeAutoDiscoverService} at startup.
   * You must also call {@link CassandraHostConfigurator#setAutoDiscoverHosts(boolean)}
   * to true for this to have an effect.
   * @param runAutoDiscoveryAtStartup
   */
  public void setRunAutoDiscoveryAtStartup(boolean runAutoDiscoveryAtStartup) {
    this.runAutoDiscoveryAtStartup = runAutoDiscoveryAtStartup;
  }

  public boolean getUseSocketKeepalive() {
    return useSocketKeepalive;
  }

  /**
   * Enable SO_KEEPALIVE on the underlying socket. OFF by default (per java.net.Socket) 
   * 
   */
  public void setUseSocketKeepalive(boolean useSocketKeepalive) {
    this.useSocketKeepalive = useSocketKeepalive;
  }

  /**
   * Retrieves whether Kerberos authentication is enabled or not.
   * @return <code>TRUE</code> if Kerberos in enabled. <code>FALSE</code> otherwise.
   */
  public boolean isUseKerberosAuthentication() {
    return useKerberosAuthentication;
  }

  /**
   * Set Kerberos Authentication.
   * @param useKerberosAuthentication 
   */
  public void setUseKerberosAuthentication(boolean useKerberosAuthentication) {
    this.useKerberosAuthentication = useKerberosAuthentication;
  }

}
