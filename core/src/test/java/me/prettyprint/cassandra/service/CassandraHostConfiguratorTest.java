package me.prettyprint.cassandra.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.prettyprint.cassandra.connection.factory.HKerberosSecuredThriftClientFactoryImpl;
import me.prettyprint.cassandra.connection.factory.HThriftClientFactoryImpl;
import me.prettyprint.hector.api.ClockResolution;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class CassandraHostConfiguratorTest {

  @Before
  public void setup() {

  }

  @Test
  public void testSimpleCassandraHostSetup() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    CassandraHost[] cassandraHosts = cassandraHostConfigurator.buildCassandraHosts();
    assertEquals(1, cassandraHosts.length);
  }

  @Test
  public void testCassandraHostSetupSplit() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170,localhost:9171,localhost:9172");
    CassandraHost[] cassandraHosts = cassandraHostConfigurator.buildCassandraHosts();
    assertEquals(3, cassandraHosts.length);
    assertEquals(9172, cassandraHosts[2].getPort());
  }

  @Test
  public void testConfigValuesPropogated() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170,localhost:9171,localhost:9172");
    cassandraHostConfigurator.setMaxActive(20);
    cassandraHostConfigurator.setCassandraThriftSocketTimeout(3000);
    cassandraHostConfigurator.setMaxWaitTimeWhenExhausted(4000);
    cassandraHostConfigurator.setMaxExhaustedTimeBeforeMarkingAsDown(5000);
    CassandraHost[] cassandraHosts = cassandraHostConfigurator.buildCassandraHosts();
    // no need to test all, just a smattering
    assertEquals(20, cassandraHosts[1].getMaxActive());
    assertEquals(20, cassandraHosts[0].getMaxActive());
    assertEquals(4000, cassandraHosts[1].getMaxWaitTimeWhenExhausted());
    assertEquals(5000, cassandraHosts[0].getMaxExhaustedTimeBeforeMarkingAsDown());
    assertEquals(3000, cassandraHosts[2].getCassandraThriftSocketTimeout());
    assertEquals(3000, cassandraHosts[0].getCassandraThriftSocketTimeout());
  }

  @Test
  public void testApplyConfig() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9170");
    cassandraHostConfigurator.setMaxActive(15);
    cassandraHostConfigurator.setMaxConnectTimeMillis(30000);
    cassandraHostConfigurator.setMaxLastSuccessTimeMillis(40000);
    CassandraHost extraHost = new CassandraHost("localhost:9171");
    cassandraHostConfigurator.applyConfig(extraHost);
    assertEquals(15, extraHost.getMaxActive());
    assertEquals(30000, extraHost.getMaxConnectTimeMillis());
    assertEquals(40000, extraHost.getMaxLastSuccessTimeMillis());
  }

  @Test
  public void testHostnameOnlyDefaultPort() {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost");
    CassandraHost[] cassandraHosts = cassandraHostConfigurator.buildCassandraHosts();
    assertEquals(CassandraHost.DEFAULT_PORT, cassandraHosts[0].getPort());
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
    assertEquals(9177, cassandraHosts[0].getPort());
  }

  @Test
  public void testConfiguratorClockResolution() {
    // Define my own clock resolution.
    @SuppressWarnings("serial")
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

  @Test
  public void testSerialization() throws Exception {
    CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator("localhost:9876");

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(bos);
    out.writeObject(cassandraHostConfigurator);
    out.close();

    byte[] serializedByteArray = bos.toByteArray();

    ByteArrayInputStream bin = new ByteArrayInputStream(serializedByteArray);
    ObjectInputStream in = new ObjectInputStream(bin);
    CassandraHostConfigurator cassandraHostConfiguratorDeserialized = (CassandraHostConfigurator) in.readObject();

    //TODO: define equality for CassandraHostConfigurator
    //assertSame(cassandraHostConfigurator, cassandraHostConfiguratorDeserialized);
  }

  @Test
  public void testSettingCorrectClientFactory() {
    CassandraHostConfigurator chc = new CassandraHostConfigurator("localhost");

    chc.setClientFactoryClass(HKerberosSecuredThriftClientFactoryImpl.class.getSimpleName());
    assertEquals(chc.getClientFactoryClass(), HKerberosSecuredThriftClientFactoryImpl.class);

    chc.setClientFactoryClass(HThriftClientFactoryImpl.class.getCanonicalName());
    assertEquals(chc.getClientFactoryClass(), HThriftClientFactoryImpl.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSettingNotFoundClientFactory() {
    CassandraHostConfigurator chc = new CassandraHostConfigurator("localhost");
    chc.setClientFactoryClass("NotARealClass");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSettingInvalidExistingClassClientFactory() {
    CassandraHostConfigurator chc = new CassandraHostConfigurator("localhost");
    chc.setClientFactoryClass(CassandraHostConfigurator.class.getCanonicalName());
  }
}
