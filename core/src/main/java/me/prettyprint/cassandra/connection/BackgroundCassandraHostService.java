package me.prettyprint.cassandra.connection;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.utils.DaemonThreadPoolFactory;

public abstract class BackgroundCassandraHostService {

  protected final ScheduledExecutorService executor;

  protected final HConnectionManager connectionManager;
  protected final CassandraHostConfigurator cassandraHostConfigurator;

  protected ScheduledFuture<?> sf;
  protected int retryDelayInSeconds;

  public BackgroundCassandraHostService(HConnectionManager connectionManager,
      CassandraHostConfigurator cassandraHostConfigurator) {
    executor = Executors.newScheduledThreadPool(1, new DaemonThreadPoolFactory(getClass()));
    this.connectionManager = connectionManager;
    this.cassandraHostConfigurator = cassandraHostConfigurator;
    
  }

  abstract void shutdown();

  abstract void applyRetryDelay();



  public int getRetryDelayInSeconds() {
    return retryDelayInSeconds;
  }

  public void setRetryDelayInSeconds(int retryDelayInSeconds) {
    this.retryDelayInSeconds = retryDelayInSeconds;
  }
  


}

