package me.prettyprint.cassandra.connection;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import me.prettyprint.cassandra.connection.client.HClient;
import me.prettyprint.cassandra.connection.factory.HClientFactory;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraClientMonitor;
import me.prettyprint.hector.api.exceptions.HectorException;

/**
 * This class provides a queue function of latencies over CHCP, collecting all the latency information and calculates
 * the score (expensive operation).
 * 
 * @author Vijay Parthasarathy
 */
public class LatencyAwareHClientPool extends ConcurrentHClientPool {
  private static final AtomicInteger intervalupdates = new AtomicInteger(0);
  // Mostly static configuration this doesnt need to be configurable to the clients.
  private static final int UPDATES_PER_INTERVAL = 1000;
  private static final int WINDOW_QUEUE_SIZE = 100;
  private static final double SENTINEL_COMPARE = 0.768;
  private final LinkedBlockingDeque<Double> latencies;

  public LatencyAwareHClientPool(HClientFactory clientFactory, CassandraHost host, CassandraClientMonitor monitor) {
    super(clientFactory, host, monitor);
    latencies = new LinkedBlockingDeque<Double>(WINDOW_QUEUE_SIZE);
  }

  @Override
  public HClient borrowClient() throws HectorException {
    HClient client = super.borrowClient();
    client.startToUse();
    return client;
  }

  @Override
  public void releaseClient(HClient client) throws HectorException {
    add(client.getSinceLastUsed());
    super.releaseClient(client);
  }

  void add(double i) {
    if (intervalupdates.intValue() >= UPDATES_PER_INTERVAL)
      return;
    if (!latencies.offer(i)) {
      latencies.poll();
      latencies.offer(i);
    }
    intervalupdates.getAndIncrement();
  }

  double score() {
    double log = 0d;
    if (latencies.size() > 0) {
      double probability = p(SENTINEL_COMPARE);
      log = (-1) * Math.log10(probability);
    }
    return log;
  }

  double p(double t) {
    double mean = mean();
    double exponent = (-1) * (t) / mean;
    return 1 - Math.pow(Math.E, exponent);
  }

  private double mean() {
    double total = 0;
    for (double d : latencies) {
      total += d;
    }
    return total / latencies.size();
  }

  public void resetIntervel() {
    intervalupdates.set(0);
  }

  public void clear() {
    latencies.clear();
    intervalupdates.set(0);
  }

  @Override
  public boolean equals(Object obj) {
    return ((LatencyAwareHClientPool) obj).getCassandraHost().equals(getCassandraHost());
  }
}
