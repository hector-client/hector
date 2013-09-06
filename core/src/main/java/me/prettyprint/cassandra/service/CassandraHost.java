package me.prettyprint.cassandra.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encapsulates the information required for connecting to a Cassandra host.
 * Also exposes pool configuration parameters for that host.
 *
 * @author zznate(nate@riptano.com)
 *
 */
public final class CassandraHost {
  private static Logger log = LoggerFactory.getLogger(CassandraHost.class);

  /**
   * The default port number to which we will connect
   */
  public static final int DEFAULT_PORT = 9160;

  public static final int DEFAULT_MAX_ACTIVE = 50;

  /**
   * By default, we will use TSocket transport on thrift (matches default Cassandra configs)
   */
  public static final boolean DEFAULT_USE_FRAMED_THRIFT_TRANSPORT = true;

  /**
   * By default, match TFramedTransport's default max frame size (Integer.MAX_VALUE).
   */
  public static final int DEFAULT_MAX_FRAME_SIZE = 0x7FFFFFFF;

  /**
   * The default max wait time when exhausted happens, default value is negative, which means
   * it'll block indefinitely.
   */
  public static final long DEFAULT_MAX_WAITTIME_WHEN_EXHAUSTED = -1;

  /**
   * The default max exhausted time before suspending.  Default value is set to
   * maximum so that it won't suspend.
   */
  public static final long DEFAULT_MAX_EXHAUSTED_TIME_BEFORE_MARKING_AS_DOWN = Long.MAX_VALUE;

  public static final boolean DEFAULT_LIFO = true;
  /**
   * The default number of milliseconds (since creation time) we'll allow a connection
   * to stay open. Default value is negative which means indefinitely.
   */
  public static final long DEFAULT_MAX_CONNECT_TIME = -1;
  /**
   * The default number of milliseconds (since last success) we'll allow a connection
   * to stay open. Default value is negative which means indefinitely.
   */
  public static final long DEFAULT_MAX_LAST_SUCCESS_TIME = -1;

  private final String host, ip, url;
  private final int port;
  private final String name;

  private int maxActive = DEFAULT_MAX_ACTIVE;

  private boolean lifo = DEFAULT_LIFO;

  private long maxWaitTimeWhenExhausted = DEFAULT_MAX_WAITTIME_WHEN_EXHAUSTED;
  private long maxExhaustedTimeBeforeMarkingAsDown = DEFAULT_MAX_EXHAUSTED_TIME_BEFORE_MARKING_AS_DOWN;
  private int cassandraThriftSocketTimeout;
  private boolean useThriftFramedTransport = DEFAULT_USE_FRAMED_THRIFT_TRANSPORT;
  private int maxFrameSize = DEFAULT_MAX_FRAME_SIZE;
  private boolean useSocketKeepalive;
  private long maxConnectTimeMillis = DEFAULT_MAX_CONNECT_TIME;
  private long maxLastSuccessTimeMillis = DEFAULT_MAX_LAST_SUCCESS_TIME;
  //TODO(ran): private FailoverPolicy failoverPolicy = DEFAULT_FAILOVER_POLICY;

  public CassandraHost(String url) {
    this(url, parsePortFromUrl(url));
  }

  public CassandraHost(String url2, int port) {
    url2 = parseHostFromUrl(url2);
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
    return sysprop != null && Boolean.parseBoolean(sysprop);

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

  public long getMaxWaitTimeWhenExhausted() {
    return maxWaitTimeWhenExhausted;
  }

  public void setMaxWaitTimeWhenExhausted(long maxWaitTimeWhenExhausted) {
    this.maxWaitTimeWhenExhausted = maxWaitTimeWhenExhausted;
  }

  public long getMaxExhaustedTimeBeforeMarkingAsDown() {
    return maxExhaustedTimeBeforeMarkingAsDown;
  }

  public void setMaxExhaustedTimeBeforeMarkingAsDown(long maxExhaustedTimeBeforeMarkingAsDown) {
    this.maxExhaustedTimeBeforeMarkingAsDown = maxExhaustedTimeBeforeMarkingAsDown;
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

  public int getMaxFrameSize() {
      return maxFrameSize;
  }

  public void setMaxFrameSize(int maxFrameSize) {
      this.maxFrameSize = maxFrameSize;
  }

  public static String parseHostFromUrl(String urlPort) {
    return urlPort.lastIndexOf(':') > 0 ? urlPort.substring(0, urlPort.lastIndexOf(':')) : urlPort;
  }

  public static int parsePortFromUrl(String urlPort) {
    return urlPort.lastIndexOf(':') > 0 ? Integer.parseInt(urlPort.substring(urlPort.lastIndexOf(':')+1, urlPort.length())) : DEFAULT_PORT;
  }

  public boolean getLifo() {
    return lifo;
  }

  public void setLifo(boolean lifo) {
    this.lifo = lifo;
  }

  public boolean getUseSocketKeepalive() {
    return useSocketKeepalive;
  }

  public void setUseSocketKeepalive(boolean useSocketKeepalive) {
    this.useSocketKeepalive = useSocketKeepalive;
  }

  public long getMaxConnectTimeMillis() {
    return this.maxConnectTimeMillis ;
  }

  public void setMaxConnectTimeMillis(long maxConnectTimeMillis) {
    this.maxConnectTimeMillis = maxConnectTimeMillis;
  }

  public long getMaxLastSuccessTimeMillis() {
    return this.maxLastSuccessTimeMillis;
  }

  public void setMaxLastSuccessTimeMillis(long maxLastSuccessTimeMillis) {
    this.maxLastSuccessTimeMillis = maxLastSuccessTimeMillis;
  }


}
