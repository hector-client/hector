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
import me.prettyprint.cassandra.connection.factory.HClientFactory;
import me.prettyprint.cassandra.connection.factory.HThriftClientFactoryImpl;
import me.prettyprint.hector.api.ClockResolution;
import me.prettyprint.hector.api.factory.HFactory;
import org.apache.commons.lang.StringUtils;


public final class CassandraHostConfigurator implements Serializable {
  // update this if you make changes
  private static final long serialVersionUID = -5798876803582956262L;
  
  public static final ClockResolution DEF_CLOCK_RESOLUTION = HFactory.createClockResolution(ClockResolution.MICROSECONDS_SYNC);
  private static ClockResolution clockResolution = DEF_CLOCK_RESOLUTION;

  private String hosts;
  private int port = CassandraHost.DEFAULT_PORT;
  private int maxActive = CassandraHost.DEFAULT_MAX_ACTIVE;
  private boolean lifo = CassandraHost.DEFAULT_LIFO;
  private long maxWaitTimeWhenExhausted = CassandraHost.DEFAULT_MAX_WAITTIME_WHEN_EXHAUSTED;
  private int cassandraThriftSocketTimeout;
  private boolean useThriftFramedTransport = CassandraHost.DEFAULT_USE_FRAMED_THRIFT_TRANSPORT;
  private int maxFrameSize = CassandraHost.DEFAULT_MAX_FRAME_SIZE;

  private boolean retryDownedHosts = true;
  private int retryDownedHostsQueueSize = CassandraHostRetryService.DEF_QUEUE_SIZE;
  private int retryDownedHostsDelayInSeconds = CassandraHostRetryService.DEF_RETRY_DELAY;

  private boolean autoDiscoverHosts = false;
  private int autoDiscoveryDelayInSeconds = NodeAutoDiscoverService.DEF_AUTO_DISCOVERY_DELAY;
  private List<String> autoDiscoveryDataCenters;

  private LoadBalancingPolicy loadBalancingPolicy = new RoundRobinBalancingPolicy();
  private int hostTimeoutCounter = HostTimeoutTracker.DEF_TIMEOUT_COUNTER;
  private int hostTimeoutWindow = HostTimeoutTracker.DEF_TIMEOUT_WINDOW;
  private int hostTimeoutSuspensionDurationInSeconds = HostTimeoutTracker.DEF_NODE_SUSPENSION_DURATION_IN_SECONDS;
  private int hostTimeoutUnsuspendCheckDelay = HostTimeoutTracker.DEF_NODE_UNSUSPEND_CHECK_DELAY_IN_SECONDS;
  private boolean useHostTimeoutTracker = false;
  private boolean runAutoDiscoveryAtStartup = false;
  private boolean useSocketKeepalive = false;
  private HOpTimer opTimer = new NullOpTimer();
  private Class<? extends HClientFactory> clientFactoryClass = HThriftClientFactoryImpl.class;
  private long maxConnectTimeMillis = CassandraHost.DEFAULT_MAX_CONNECT_TIME;
  private long maxLastSuccessTimeMillis = CassandraHost.DEFAULT_MAX_LAST_SUCCESS_TIME;

  public CassandraHostConfigurator() {
    this.hosts = null;
  }

	/**
	 * Creates a new {@code CassandraHostConfigurator} from the specified hosts String, formatted as
   * {@code host[:port][,host[:port]...]}.
   * @param hosts The hosts to create {@link CassandraHost}s from.
	 */
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
    cassandraHost.setMaxFrameSize(maxFrameSize);
    cassandraHost.setUseSocketKeepalive(useSocketKeepalive);
    cassandraHost.setMaxConnectTimeMillis(maxConnectTimeMillis);
    cassandraHost.setMaxLastSuccessTimeMillis(maxLastSuccessTimeMillis);

    // this is special as it can be passed in as a system property
    if (cassandraThriftSocketTimeout > 0) {
      cassandraHost.setCassandraThriftSocketTimeout(cassandraThriftSocketTimeout);
    }
  }

  /**
   * Specifies the hosts String, formatted as
   * {@code host[:port][,host[:port]...]}.
   * @param hosts The hosts to create {@link CassandraHost}s from.
   */
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
   * Sets this Clock for all clusters administered by Hector.
   * Notice this is a class method and ideally should be called from the CHC class just once.
   * 
   * @param resolutionString one of "SECONDS", "MILLISECONDS", "MICROSECONDS" or "MICROSECONDS_SYNC"
   */
  public static void setClockResolution(String resolutionString) {
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
    s.append("&maxFrameSize=");
    s.append(maxFrameSize);
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

  public void setMaxFrameSize(int maxFrameSize) {
    this.maxFrameSize = maxFrameSize;
  }

  public static ClockResolution getClockResolution() {
    return CassandraHostConfigurator.clockResolution;
  }

  /**
   * Sets this Clock for all clusters administered by Hector.
   * Notice this is a class method and ideally should be called from the CHC class just once.
   * @param clockResolution a specific ClockResolution
   */
  public static void setClockResolution(ClockResolution clockResolution) {
    CassandraHostConfigurator.clockResolution = clockResolution;
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
   * datacenter will be discarded. For configuration simplicity, you can provide
   * an empty or null string with the effect being the same as if you had not set
   * this property.
   * @param dataCenter DataCenter name
   */
  public void setAutoDiscoveryDataCenter(String dataCenter) {
    if (StringUtils.isNotBlank(dataCenter)) {
      this.autoDiscoveryDataCenters = Arrays.asList(dataCenter);
    }
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
   * Enable SO_KEEPALIVE on the underlying socket. OFF by default (per java.net.Socket).<br/>
   * Enabling the socket keepalive is the usual way to work around a firewall problem
   * (that is, a firewall that cuts idle connections between the Hector's host and the Cassandra
   * nodes, Hector being unaware of these cuts). To do so, this method must be used
   * in accordance with the appropriate "tcp_keepalive" settings of the machine that runs Hector,
   * to deal with the firewall own settings (that is, how long a connection should be detected
   * idle before the firewall cuts it).
   */
  public void setUseSocketKeepalive(boolean useSocketKeepalive) {
    this.useSocketKeepalive = useSocketKeepalive;
  }

  public void setClientFactoryClass(String cls) {
    String className = cls.contains(".") ? cls : "me.prettyprint.cassandra.connection.factory." + cls;

    Class<? extends HClientFactory> temp;

    try {
      temp = Class.forName(className).asSubclass(HClientFactory.class);
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException(String.format("Unable to find '%s' class.", className), e);
    } catch (ClassCastException e) {
      throw new IllegalArgumentException("ClientFactoryClass should be extended from HClientFactory.", e);
    }

    clientFactoryClass = temp;
  }

  public Class<? extends HClientFactory> getClientFactoryClass() {
    return clientFactoryClass;
  }

  /**
   * The maximum time in milliseconds that we'll allow a connection to stay open to a host. A negative
   * value indicates indefinitely (and is the default).
   * 
   * @return the number of milliseconds
   */
  public long getMaxConnectTimeMillis() {
    return maxConnectTimeMillis;
  }

  /**
   * Set the maximum time in milliseconds that we'll allow a connection to stay open to a host. A negative
   * value indicates indefinitely. This setting is useful if you you need to work around a firewall that 
   * forcefully closes connections after a fixed amount of time regardless of activity.
   * 
   * @param maxConnectTimeMillis the maximum time to use a connection
   */
  public void setMaxConnectTimeMillis(long maxConnectTimeMillis) {
    this.maxConnectTimeMillis = maxConnectTimeMillis;
  }

  /**
   * The maximum time in milliseconds that we'll allow a connection to stay idle to a host. A negative
   * value indicates indefinitely (and is the default).
   * 
   * @return the number of milliseconds
   */
  public long getMaxLastSuccessTimeMillis() {
    return this.maxLastSuccessTimeMillis;
  }

  /**
   * Set the maximum time in milliseconds that we'll allow a connection to stay idle to a host. A negative
   * value indicates indefinitely. <br/>
   * This setting is useful if you you need to work around a firewall that forcefully closes connections
   * after a fixed amount of idle time. Example: if your firewall cuts connections after an idle time
   * of 30 mn, one could set this property with the duration 29 mn 30s to have a margin. <br/>
   * But before using <code>setMaxLastSuccessTimeMillis</code>, the first way, that is, the most used way,
   * to try to work around a firewall problem should be to use the socket "keepalive" mechanism, see
   * <code>setUseSocketKeepalive</code> method. The current method <code>setMaxLastSuccessTimeMillis</code>
   * is generally used for dealing with firewalls when the "keepalive" mechanism could not be used.  
   *
   * @param maxLastSuccessTimeMillis the maximum idle time for a connection
   */
  public void setMaxLastSuccessTimeMillis(long maxLastSuccessTimeMillis) {
    this.maxLastSuccessTimeMillis = maxLastSuccessTimeMillis;
  }
}
