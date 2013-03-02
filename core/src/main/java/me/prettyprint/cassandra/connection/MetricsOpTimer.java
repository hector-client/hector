package me.prettyprint.cassandra.connection;

import java.util.concurrent.TimeUnit;

import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.MetricsRegistry;
import com.yammer.metrics.core.Timer;
import com.yammer.metrics.core.TimerContext;

public class MetricsOpTimer implements HOpTimer {

  private static final String TIMER_TYPE = "hector";
  private final MetricsRegistry metricsRegistry;
  private final TimeUnit durationUnit;
  private final TimeUnit rateUnit;
  private final String clusterName;

  public MetricsOpTimer(final MetricsRegistry metricsRegistry, final String clusterName,
      final TimeUnit durationUnit, final TimeUnit rateUnit) {
    this.metricsRegistry = metricsRegistry;
    this.clusterName = clusterName;
    this.durationUnit = durationUnit;
    this.rateUnit = rateUnit;
  }

  public MetricsOpTimer(final String clusterName) {
    this.metricsRegistry = new MetricsRegistry();
    this.clusterName = clusterName;
    this.durationUnit = TimeUnit.NANOSECONDS;
    this.rateUnit = TimeUnit.SECONDS;
  }

  @Override
  public Object start(final String tagName) {
    final Timer timer = metricsRegistry.newTimer(new MetricName(clusterName, TIMER_TYPE, tagName),
        durationUnit, rateUnit);
    return timer.time();
  }

  @Override
  public void stop(final Object token, final String tagName, final boolean success) {
    final TimerContext timerContext = (TimerContext) token;
    timerContext.stop();
  }

}
