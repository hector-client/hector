package me.prettyprint.cassandra.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encapsulates the information required for connecting to a Cassandra host.
 * Also exposes pool configuration parameters for that host.
 *
 * @author Nate McCall (nate@vervewireless.com)
 *
 */
public class CassandraHost {
  private static Logger log = LoggerFactory.getLogger(CassandraHost.class);

  public static final int DEFAULT_MAX_ACTIVE = 50;

  /**
   * By default, we will use TSocket transport on thrift (matches default Cassandra configs)
   */
  public static final boolean DEFAULT_USE_FRAMED_THRIFT_TRANSPORT = false;

  /**
   * The default max wait time when exhausted happens, default value is negative, which means
   * it'll block indefinitely.
   */
  public static final long DEFAULT_MAX_WAITTIME_WHEN_EXHAUSTED = -1;

  /**
   * The default max idle number is 5, so if clients keep idle, the total connection
   * number will decrease to 5
   */
  public static final int DEFAULT_MAX_IDLE = -1;

  public static final boolean DEFAULT_LIFO = GenericObjectPool.DEFAULT_LIFO;
  public static final long DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = GenericObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
  public static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = GenericObjectPool.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

  public static final TimestampResolution DEFAULT_TIMESTAMP_RESOLUTION =
      TimestampResolution.MICROSECONDS;

  private final String url, ip;
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
  private TimestampResolution timestampResolution = DEFAULT_TIMESTAMP_RESOLUTION;
  //TODO(ran): private FailoverPolicy failoverPolicy = DEFAULT_FAILOVER_POLICY;

  public CassandraHost(String urlPort) {
    this(parseHostFromUrl(urlPort), parsePortFromUrl(urlPort));
  }

  public CassandraHost(String url2, int port) {
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
    this.url = turl;
    ip = tip;
    b.append(url2);
    b.append("(");
    b.append(ip);
    b.append("):");
    b.append(port);
    name = b.toString();
  }

  public String getUrlPort() {
    return new StringBuilder(32).append(url).append(':').append(port).toString();
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

  public String getUrl() {
    return url;
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

  @Override
  public boolean equals(Object obj) {
    if (! (obj instanceof CassandraHost)) {
      return false;
    }
    CassandraHost other = (CassandraHost) obj;
    return other.ip.equals(ip) && other.port == port;
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

  public static String parseHostFromUrl(String urlPort) {
    return urlPort.substring(0, urlPort.lastIndexOf(':'));
  }

  public static int parsePortFromUrl(String urlPort) {
    return Integer.valueOf(urlPort.substring(urlPort.lastIndexOf(':')+1, urlPort.length()));
  }

  public void setTimestampResolution(TimestampResolution timestampResolution) {
    this.timestampResolution = timestampResolution;
  }

  public TimestampResolution getTimestampResolution() {
    return timestampResolution;
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

}
