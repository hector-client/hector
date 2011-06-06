package me.prettyprint.cassandra.connection;

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
public interface HCassandraHost {


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
   * The default max wait time when exhausted happens, default value is negative, which means
   * it'll block indefinitely.
   */
  public static final long DEFAULT_MAX_WAITTIME_WHEN_EXHAUSTED = -1;

  /**
   * The default max idle number determines how many idle connections may reside in the pool.
   * If -1 then it's infinity.
   */
  public static final int DEFAULT_MAX_IDLE = -1;

  public static final boolean DEFAULT_LIFO = true;
  public static final long DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 18000000;
  public static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = -1;


  public String getUrl();

  /**
   * Checks whether name resolution should occur.
   *
   * @return
   */
  public boolean isPerformNameResolution();

  public String getName();

  public String getHost();

  public String getIp();

  public int getPort();

  @Override
  public String toString();

  /**
   * Returns true if the ip and port are equal
   */
  @Override
  public boolean equals(Object obj);

  @Override
  public int hashCode();

  public int getMaxActive();

  public void setMaxActive(int maxActive);

  public int getMaxIdle();

  public void setMaxIdle(int maxIdle);

  public long getMaxWaitTimeWhenExhausted();

  public void setMaxWaitTimeWhenExhausted(long maxWaitTimeWhenExhausted);

  public ExhaustedPolicy getExhaustedPolicy();

  public void setExhaustedPolicy(ExhaustedPolicy exhaustedPolicy);

  public int getCassandraThriftSocketTimeout();

  public void setCassandraThriftSocketTimeout(int cassandraThriftSocketTimeout);

  public boolean getUseThriftFramedTransport();

  public void setUseThriftFramedTransport(boolean useThriftFramedTransport);

  public boolean getLifo();

  public void setLifo(boolean lifo);

  public long getMinEvictableIdleTimeMillis();

  public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis);

  public long getTimeBetweenEvictionRunsMillis();

  public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis);

  public boolean getUseSocketKeepalive();

  public void setUseSocketKeepalive(boolean useSocketKeepalive);

}
