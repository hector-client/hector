package me.prettyprint.cassandra.connection;

import java.net.InetAddress;
import java.net.UnknownHostException;

import me.prettyprint.hector.SystemProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encapsulates the information required for connecting to a Cassandra host.
 * Also exposes pool configuration parameters for that host.
 *
 * @author zznate(nate@riptano.com)
 *
 */
public class HCassandraHostImpl implements HCassandraHost {
	
  private static Logger log = LoggerFactory.getLogger(HCassandraHostImpl.class);

  private final String host, ip, url;
  private final int port;
  private final String name;

  private int maxActive = DEFAULT_MAX_ACTIVE;
  private int maxIdle = DEFAULT_MAX_IDLE;

  private boolean lifo = DEFAULT_LIFO;
  private long minEvictableIdleTimeMillis = DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
  private long timeBetweenEvictionRunsMillis = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

  private long maxWaitTimeWhenExhausted = DEFAULT_MAX_WAITTIME_WHEN_EXHAUSTED;
  private int cassandraThriftSocketTimeout;
  private ExhaustedPolicy exhaustedPolicy = ExhaustedPolicy.WHEN_EXHAUSTED_BLOCK;
  private boolean useThriftFramedTransport = DEFAULT_USE_FRAMED_THRIFT_TRANSPORT;
  private boolean useSocketKeepalive;
  //TODO(ran): private FailoverPolicy failoverPolicy = DEFAULT_FAILOVER_POLICY;

  public HCassandraHostImpl(String url) {
    this(url, HostUtils.parsePortFromUrl(url));
  }

  public HCassandraHostImpl(String url2, int port) {
    url2 = HostUtils.parseHostFromUrl(url2);
    this.port = port;
    StringBuilder b = new StringBuilder();
    InetAddress address;
    String turl, tip;
    try {
      address = InetAddress.getByName(url2);
      turl = isPerformNameResolution() ? address.getHostName() : url2;
      tip = address.getHostAddress();
    } catch (UnknownHostException e) {
      log.error("Unable to resolve host {}", url2);
      turl = url2;
      tip = url2;
    }
    this.host = turl;
    ip = tip;
    b.append(url2);
    b.append("(");
    b.append(ip);
    b.append("):");
    b.append(port);
    name = b.toString();
    url = String.format("%s:%d",host,port);
  }

  public String getUrl() {
    return url;
  }

  /**
   * Checks whether name resolution should occur.
   *
   * @return
   */
  public boolean isPerformNameResolution() {
    String sysprop = System.getProperty(
        SystemProperties.HECTOR_PERFORM_NAME_RESOLUTION.toString());
    return sysprop != null && Boolean.valueOf(sysprop);

  }

  public String getName() {
    return name;
  }

  public String getHost() {
    return host;
  }

  public String getIp() {
    return ip;
  }

  public int getPort() {
    return port;
  }

  @Override
  public String toString() {
    return name;
  }

  /**
   * Returns true if the ip and port are equal
   */
  @Override
  public boolean equals(Object obj) {
    if (! (obj instanceof HCassandraHost)) {
      return false;
    }
    HCassandraHost other = (HCassandraHost) obj;
    return other.getIp().equals(ip) && other.getPort() == port;
  }

  @Override
  public int hashCode() {
    return ip.hashCode();
  }

  public int getMaxActive() {
    return maxActive;
  }

  public void setMaxActive(int maxActive) {
    this.maxActive = maxActive;
  }

  public int getMaxIdle() {
    return maxIdle;
  }

  public void setMaxIdle(int maxIdle) {
    this.maxIdle = maxIdle;
  }

  public long getMaxWaitTimeWhenExhausted() {
    return maxWaitTimeWhenExhausted;
  }

  public void setMaxWaitTimeWhenExhausted(long maxWaitTimeWhenExhausted) {
    this.maxWaitTimeWhenExhausted = maxWaitTimeWhenExhausted;
  }

  public ExhaustedPolicy getExhaustedPolicy() {
    return exhaustedPolicy;
  }

  public void setExhaustedPolicy(ExhaustedPolicy exhaustedPolicy) {
    this.exhaustedPolicy = exhaustedPolicy;
  }

  public int getCassandraThriftSocketTimeout() {
    return cassandraThriftSocketTimeout;
  }

  public void setCassandraThriftSocketTimeout(int cassandraThriftSocketTimeout) {
    this.cassandraThriftSocketTimeout = cassandraThriftSocketTimeout;
  }

  public boolean getUseThriftFramedTransport() {
    return useThriftFramedTransport;
  }

  public void setUseThriftFramedTransport(boolean useThriftFramedTransport) {
    this.useThriftFramedTransport = useThriftFramedTransport;
  }

  public boolean getLifo() {
    return lifo;
  }

  public void setLifo(boolean lifo) {
    this.lifo = lifo;
  }

  public long getMinEvictableIdleTimeMillis() {
    return minEvictableIdleTimeMillis;
  }

  public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
    this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
  }

  public long getTimeBetweenEvictionRunsMillis() {
    return timeBetweenEvictionRunsMillis;
  }

  public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
    this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
  }

  public boolean getUseSocketKeepalive() {
    return useSocketKeepalive;
  }

  public void setUseSocketKeepalive(boolean useSocketKeepalive) {
    this.useSocketKeepalive = useSocketKeepalive;
  }

  
}
