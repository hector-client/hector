package me.prettyprint.cassandra.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.service.Operation;
import me.prettyprint.cassandra.service.OperationType;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.thrift.Cassandra.Client;
import org.junit.Test;

import com.yammer.metrics.core.Metric;
import com.yammer.metrics.core.MetricName;
import com.yammer.metrics.core.MetricsRegistry;
import com.yammer.metrics.core.Timer;

public class HConnectionManagerMetricsOpTimerTest extends BaseEmbededServerSetupTest {

  @Test
  public void testWithOptimer() {
    setupClient();
    final MetricsRegistry registry = new MetricsRegistry();
    final MetricsOpTimer opTimer = new MetricsOpTimer(registry, "TEST_CLUSTER",
        TimeUnit.NANOSECONDS, TimeUnit.SECONDS);
    connectionManager.setTimer(opTimer);
    connectionManager.operateWithFailover(new NullOp());

    final SortedMap<String, SortedMap<MetricName, Metric>> metrics = registry.groupedMetrics();
    assertNotNull("Hector metric should exist in metrics registry", metrics);
    assertFalse("Hector metrics should exist in metrics register", metrics.isEmpty());
    final Entry<String, SortedMap<MetricName, Metric>> entry = metrics.entrySet().iterator().next();
    assertEquals("Incorrect metrics key should be [cluster_name].hector", "TEST_CLUSTER.hector",
        entry.getKey());
    final Entry<MetricName, Metric> metric = entry.getValue().entrySet().iterator().next();
    assertEquals("Incorrect metrics name should be META_READ", "META_READ", metric.getKey()
                                                                                  .getName());
    assertEquals("Incorrect metrics type should be timer", Timer.class, metric.getValue()
                                                                              .getClass());
  }

  class NullOp extends Operation<String> {

    NullOp() {
      super(OperationType.META_READ);
    }

    @Override
    public String execute(final Client cassandra) throws HectorException {
      return null;
    }
  }
}
