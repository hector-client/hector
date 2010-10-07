package me.prettyprint.cassandra.service;


public final class CassandraHostConfigurator {

  private String hosts;
  private int port = CassandraHost.DEFAULT_PORT;
  private int maxActive = CassandraHost.DEFAULT_MAX_ACTIVE;
  private int maxIdle = CassandraHost.DEFAULT_MAX_IDLE;
  private boolean lifo = CassandraHost.DEFAULT_LIFO;
  private long minEvictableIdleTimeMillis = CassandraHost.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
  private long timeBetweenEvictionRunsMillis = CassandraHost.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
  private long maxWaitTimeWhenExhausted = CassandraHost.DEFAULT_MAX_WAITTIME_WHEN_EXHAUSTED;
  private int cassandraThriftSocketTimeout;
  private ExhaustedPolicy exhaustedPolicy;
  private ClockResolution clockResolution;
  private boolean useThriftFramedTransport = CassandraHost.DEFAULT_USE_FRAMED_THRIFT_TRANSPORT;
  private boolean retryDownedHosts = false;
  private int retryDownedHostsQueueSize = DownCassandraHostRetryService.DEF_QUEUE_SIZE;
  private int retryDownedHostsDelayInSeconds = DownCassandraHostRetryService.DEF_RETRY_DELAY;
  private PoolType poolType = PoolType.COMMONS;
  

  public CassandraHostConfigurator() {
    this.hosts = null;
  }

  public CassandraHostConfigurator(String hosts) {
    this.hosts = hosts;
  }

  public CassandraHost[] buildCassandraHosts() {
    if (this.hosts == null) {
      return null;
    }
    String[] hostVals = hosts.split(",");
    CassandraHost[] cassandraHosts = new CassandraHost[hostVals.length];
    for (int x=0; x<hostVals.length; x++) {
      CassandraHost cassandraHost = this.port == CassandraHost.DEFAULT_PORT ? new CassandraHost(hostVals[x]) : new CassandraHost(hostVals[x], this.port);
      applyConfig(cassandraHost);
      cassandraHosts[x] = cassandraHost;
    }
    return cassandraHosts;
  }

  public void applyConfig(CassandraHost cassandraHost) {

    cassandraHost.setMaxActive(maxActive);
    cassandraHost.setMaxIdle(maxIdle);
    cassandraHost.setLifo(lifo);
    cassandraHost.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    cassandraHost.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    cassandraHost.setMaxWaitTimeWhenExhausted(maxWaitTimeWhenExhausted);
    cassandraHost.setUseThriftFramedTransport(useThriftFramedTransport);

    // this is special as it can be passed in as a system property
    if (cassandraThriftSocketTimeout > 0) {
      cassandraHost.setCassandraThriftSocketTimeout(cassandraThriftSocketTimeout);
    }
    if (exhaustedPolicy != null) {
      cassandraHost.setExhaustedPolicy(exhaustedPolicy);
    }
    if (clockResolution != null) {
      cassandraHost.setClockResolution(clockResolution);
    }
  }

  public void setHosts(String hosts) {
    this.hosts = hosts;
  }

  public void setMaxActive(int maxActive) {
    this.maxActive = maxActive;
  }

  public void setMaxIdle(int maxIdle) {
    this.maxIdle = maxIdle;
  }

  public void setMaxWaitTimeWhenExhausted(long maxWaitTimeWhenExhausted) {
    this.maxWaitTimeWhenExhausted = maxWaitTimeWhenExhausted;
  }

  public void setCassandraThriftSocketTimeout(int cassandraThriftSocketTimeout) {
    this.cassandraThriftSocketTimeout = cassandraThriftSocketTimeout;
  }

  public void setExhaustedPolicy(ExhaustedPolicy exhaustedPolicy) {
    this.exhaustedPolicy = exhaustedPolicy;
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
   * @param resolutionString one of "SECONDS", "MILLISECONDS" or "MICROSECONDS"
   */
  public void setClockResolution(String resolutionString) {
    clockResolution = ClockResolution.valueOf(resolutionString);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("CassandraHostConfigurator<");
    s.append("clockResolution=");
    s.append(clockResolution);
    s.append("&exhaustedPolicy=");
    s.append(exhaustedPolicy);
    s.append("&cassandraThriftSocketTimeout=");
    s.append(cassandraThriftSocketTimeout);
    s.append("&maxWaitTimeWhenExhausted=");
    s.append(maxWaitTimeWhenExhausted);
    s.append("&maxIdle=");
    s.append(maxIdle);
    s.append("&maxActive=");
    s.append(maxActive);
    s.append("&hosts=");
    s.append(hosts);
    s.append("&useThriftFramedTransport=");
    s.append(useThriftFramedTransport);
    s.append("&retryDownedHosts=");
    s.append(retryDownedHosts);
    s.append(">");
    return s.toString();
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

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setUseThriftFramedTransport(boolean useThriftFramedTransport) {
    this.useThriftFramedTransport = useThriftFramedTransport;
  }

  public PoolType getPoolType() {
    return poolType;
  }

  public void setPoolType(PoolType poolType) {
    this.poolType = poolType;
  }
  
  
}
