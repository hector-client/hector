package me.prettyprint.cassandra.connection;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;

public abstract class BackgroundCassandraHostService {

  protected final ScheduledExecutorService executor;

  protected final HConnectionManager connectionManager;
  protected final CassandraHostConfigurator cassandraHostConfigurator;

  protected ScheduledFuture<?> sf;
  protected int retryDelayInSeconds;

  public BackgroundCassandraHostService(HConnectionManager connectionManager,
      CassandraHostConfigurator cassandraHostConfigurator) {
    executor = Executors.newScheduledThreadPool(1, new DaemonThreadPoolFactory("Hector." + getClass().getSimpleName() + "Thread"));
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
  
  private static class DaemonThreadPoolFactory implements ThreadFactory {

    private static final AtomicInteger count = new AtomicInteger(); 
    private final String name;

    public DaemonThreadPoolFactory(String name) {
      this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.setName(name + "-" + count.incrementAndGet());
        return t;
    }
      
  }

}

