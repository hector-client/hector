package me.prettyprint.cassandra.service;

import static org.junit.Assert.*;

import me.prettyprint.hector.api.ClockResolution;

import org.junit.Before;
import org.junit.Test;

public class CassandraHostConfiguratorTest {

  @Before
  public void setup() {

  }

  @Test
  public void testSimpleCassandraHostSetup() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    CassandraHost[] cassandraHosts = cassandraHostConfigurator.buildCassandraHosts();
    assertEquals(1,cassandraHosts.length);
  }

  @Test
  public void testCassandraHostSetupSplit() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170,localhost:9171,localhost:9172");
    CassandraHost[] cassandraHosts = cassandraHostConfigurator.buildCassandraHosts();
    assertEquals(3,cassandraHosts.length);
    assertEquals(9172,cassandraHosts[2].getPort());
  }

  @Test
  public void testConfigValuesPropogated() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170,localhost:9171,localhost:9172");
    cassandraHostConfigurator.setMaxActive(20);
    cassandraHostConfigurator.setMaxIdle(5);
    cassandraHostConfigurator.setCassandraThriftSocketTimeout(3000);
    cassandraHostConfigurator.setMaxWaitTimeWhenExhausted(4000);
    cassandraHostConfigurator.setExhaustedPolicy(ExhaustedPolicy.WHEN_EXHAUSTED_GROW);
    CassandraHost[] cassandraHosts = cassandraHostConfigurator.buildCassandraHosts();
    // no need to test all, just a smattering
    assertEquals(20, cassandraHosts[1].getMaxActive());
    assertEquals(20, cassandraHosts[0].getMaxActive());
    assertEquals(5, cassandraHosts[1].getMaxIdle());
    assertEquals(ExhaustedPolicy.WHEN_EXHAUSTED_GROW, cassandraHosts[1].getExhaustedPolicy());
    assertEquals(4000, cassandraHosts[1].getMaxWaitTimeWhenExhausted());
    assertEquals(3000, cassandraHosts[2].getCassandraThriftSocketTimeout());
    assertEquals(3000, cassandraHosts[0].getCassandraThriftSocketTimeout());
  }

  @Test
  public void testApplyConfig() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    cassandraHostConfigurator.setMaxActive(15);
    CassandraHost extraHost = new CassandraHost("localhost:9171");
    cassandraHostConfigurator.applyConfig(extraHost);
    assertEquals(15, extraHost.getMaxActive());
  }

  @Test
  public void testHostnameOnlyDefaultPort() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost");
    CassandraHost[] cassandraHosts = cassandraHostConfigurator.buildCassandraHosts();
    assertEquals(CassandraHost.DEFAULT_PORT,cassandraHosts[0].getPort());
  }

  @Test
  public void testHostnameOnlyDefaultPortMultipleHosts() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("h1,h2,h3:1111");
    CassandraHost[] cassandraHosts = cassandraHostConfigurator.buildCassandraHosts();
    assertEquals(CassandraHost.DEFAULT_PORT, cassandraHosts[0].getPort());
    assertEquals(CassandraHost.DEFAULT_PORT, cassandraHosts[1].getPort());
    assertEquals(1111, cassandraHosts[2].getPort());
  }

  @Test
  public void testConfiguratorPort() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost");
    cassandraHostConfigurator.setPort(9177);
    CassandraHost[] cassandraHosts = cassandraHostConfigurator.buildCassandraHosts();
    assertEquals(9177,cassandraHosts[0].getPort());
  }

  @Test
  public void testConfiguratorClockResolution() {
    // Define my own clock resolution.
    class SequentialClockResolution implements ClockResolution {
        @Override
        public long createClock() {
            return System.currentTimeMillis() * -1;
        }
    }

    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost");
    cassandraHostConfigurator.setClockResolution(new SequentialClockResolution());
    
    assertNotSame(CassandraHostConfigurator.DEF_CLOCK_RESOLUTION, cassandraHostConfigurator.getClockResolution());
  }
}
