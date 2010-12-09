package me.prettyprint.cassandra.connection;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;

public abstract class BackgroundCassandraHostService {

  protected ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

  protected final HConnectionManager connectionManager;
  protected final CassandraHostConfigurator cassandraHostConfigurator;

  protected ScheduledFuture sf;
  protected int retryDelayInSeconds;

  public BackgroundCassandraHostService(HConnectionManager connectionManager,
      CassandraHostConfigurator cassandraHostConfigurator) {
    this.connectionManager = connectionManager;
    this.cassandraHostConfigurator = cassandraHostConfigurator;
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        shutdown();
      }
    });
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
