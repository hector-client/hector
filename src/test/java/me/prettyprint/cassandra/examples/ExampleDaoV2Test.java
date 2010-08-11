package me.prettyprint.cassandra.examples;

import static me.prettyprint.cassandra.model.HFactory.createKeyspaceOperator;
import static me.prettyprint.cassandra.model.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.service.Cluster;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;

import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.apache.cassandra.config.ConfigurationException;

public class ExampleDaoV2Test {

  private static EmbeddedServerHelper embedded;

  /**
   * Set embedded cassandra up and spawn it in a new thread.
   *
   * @throws TTransportException
   * @throws IOException
   * @throws InterruptedException
   */
  @BeforeClass
  public static void setup() throws TTransportException, IOException, InterruptedException, ConfigurationException {
    embedded = new EmbeddedServerHelper();
    embedded.setup();
  }

  @AfterClass
  public static void teardown() throws IOException {
    embedded.teardown();
  }


  @Test
  @Ignore("Not functinal yet")
  public void testInsertGetDelete() throws HectorException {
    Cluster c = getOrCreateCluster("MyCluster", "localhost:9170");
    ExampleDaoV2 dao = new ExampleDaoV2(createKeyspaceOperator("Keyspace1", c));
    assertNull(dao.get("key".getBytes()));
    dao.insert("key".getBytes(), "value");
    assertEquals("value", dao.get("key".getBytes()));
    dao.delete("key".getBytes());
    assertNull(dao.get("key".getBytes()));
  }

  @Test
  @Ignore("Not functinal yet")
  public void testMultiInsertGetDelete() throws HectorException {
    Cluster c = getOrCreateCluster("MyCluster", "localhost:9170");
    ExampleDaoV2 dao = new ExampleDaoV2(createKeyspaceOperator("Keyspace1", c));

    // Get non-existing values
    Map<byte[], String> ret = dao.getMulti("key1".getBytes(), "key2".getBytes());
    assertNotNull(ret);
    assertNull("value1", ret.get("key1"));

    // Insert values
    Map<byte[], String> keyValues = new HashMap<byte[], String>();
    keyValues.put("key1".getBytes(), "value1");
    keyValues.put("key2".getBytes(), "value2");
    dao.insertMulti(keyValues);

    // Simple get test
    ret = dao.getMulti("key1".getBytes(), "key2".getBytes());
    assertNotNull(ret);
    assertEquals("value1", ret.get("key1".getBytes()));
    assertEquals("value2", ret.get("key2".getBytes()));

    // Get some values that don't exist
    ret = dao.getMulti("key2".getBytes(), "key3".getBytes());
    assertNotNull(ret);
    assertEquals("value2", ret.get("key2".getBytes()));
    assertNull(ret.get("key3".getBytes()));
    assertNull(ret.get("key1".getBytes()));

    // delete
    dao.delete("key1".getBytes(), "key2".getBytes());

    // validate deletion
    ret = dao.getMulti("key1".getBytes(), "key2".getBytes());
    assertNotNull(ret);
    assertNull(ret.get("key1".getBytes()));
    assertNull(ret.get("key2".getBytes()));

  }
}
