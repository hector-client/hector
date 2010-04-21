package me.prettyprint.cassandra.service;

import java.lang.String;

public class CassandraHostConfigurator {

  private String hosts;
  private int maxActive;
  private int maxIdle;
  private long maxWaitTimeWhenExhausted;
  private int cassandraThriftSocketTimeout;
  private ExhaustedPolicy exhaustedPolicy;
  

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
      CassandraHost cassandraHost = new CassandraHost(hostVals[x]);     
      if (maxActive > 0)
        cassandraHost.setMaxActive(maxActive);
      if (maxIdle > 0)
        cassandraHost.setMaxIdle(maxIdle);
      if (maxWaitTimeWhenExhausted > 0) 
        cassandraHost.setMaxWaitTimeWhenExhausted(maxWaitTimeWhenExhausted);
      if (cassandraThriftSocketTimeout > 0)
        cassandraHost.setCassandraThriftSocketTimeout(cassandraThriftSocketTimeout);
      if (exhaustedPolicy != null)
        cassandraHost.setExhaustedPolicy(exhaustedPolicy);
      
      cassandraHosts[x] = cassandraHost;
    }
    return cassandraHosts;
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
  
  
}
