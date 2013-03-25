package me.prettyprint.cassandra.connection;

import static org.junit.Assert.assertTrue;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.connection.factory.HClientFactory;
import me.prettyprint.cassandra.connection.factory.HThriftClientFactoryImpl;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.CassandraClientMonitor;

import org.junit.Before;
import org.junit.Test;

public class LatencyAwareHClientPoolTest extends BaseEmbededServerSetupTest {

  private static final double DYNAMIC_BADNESS_THRESHOLD = 0.24;
  private CassandraHost cassandraHost;
  private LatencyAwareHClientPool clientPool;

  @Before
  public void setupTest() {
    setupClient();
    cassandraHost = cassandraHostConfigurator.buildCassandraHosts()[0];
    HClientFactory factory = new HThriftClientFactoryImpl();
    clientPool = new LatencyAwareHClientPool(factory, cassandraHost, new CassandraClientMonitor(connectionManager));
  }

  @Test
  public void testScore() {
    clientPool.add(120);
    clientPool.add(121);
    clientPool.add(122);
    clientPool.add(123);
    clientPool.add(124);
    double score1 = clientPool.score();
    clientPool.clear();

    clientPool.add(150);
    clientPool.add(151);
    clientPool.add(152);
    clientPool.add(152);
    clientPool.add(1530);
    double score2 = clientPool.score();
    clientPool.clear();
    System.out.println((score2 - score1) / score2);
    assertTrue("Error... score 2 is bigger than score 1...", (score2 - score1) / score2 > 0);
  }

  @Test
  public void testReverse() {

    clientPool.add(100);
    clientPool.add(101);
    clientPool.add(102);
    clientPool.add(103);
    clientPool.add(104);
    double score3 = clientPool.score();
    clientPool.clear();

    clientPool.add(10000);
    clientPool.add(10000);
    clientPool.add(10020);
    clientPool.add(11030);
    clientPool.add(1604);
    double score4 = clientPool.score();
    clientPool.clear();

    assertTrue("Error the latencies are not taken into account!", (score3 - score4) / score3 < 0);
  }

  @Test
  public void testThreshhold() {
    for (int i = 50; i < 150; i++) {
      clientPool.add(i);
    }
    double score1 = clientPool.score();
    clientPool.clear();
    System.out.println(score1);

    for (int i = 450; i < 550; i++) {
      clientPool.add(i);
    }

    double score2 = clientPool.score();
    clientPool.clear();
    System.out.println(score2);
    System.out.println((score2 - score1) / score2);
    // score 2 has things around 500 and score 1 has things around 100 so it is approx 25% bad
    assertTrue("Error The Badness threshold value is not taken into account!",
        (score2 - score1) / score2 > DYNAMIC_BADNESS_THRESHOLD);
  }

}
