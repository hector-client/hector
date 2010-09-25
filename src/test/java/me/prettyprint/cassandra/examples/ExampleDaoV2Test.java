package me.prettyprint.cassandra.examples;

import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
  public static void setup() throws TTransportException, IOException, InterruptedException {
    embedded = new EmbeddedServerHelper();
    embedded.setup();
  }

  @AfterClass
  public static void teardown() throws IOException {
    embedded.teardown();
  }


  @Test
  public void testInsertGetDelete() throws HectorException {
    Cluster c = getOrCreateCluster("MyCluster", "localhost:9170");
    ExampleDaoV2 dao = new ExampleDaoV2(createKeyspace("Keyspace1", c));
    assertNull(dao.get("key"));
    dao.insert("key", "value");
    assertEquals("value", dao.get("key"));
    dao.delete("key");
    assertNull(dao.get("key"));
  }

  @Test
  public void testMultiInsertGetDelete() throws HectorException {
    Cluster c = getOrCreateCluster("MyCluster", "localhost:9170");
    ExampleDaoV2 dao = new ExampleDaoV2(createKeyspace("Keyspace1", c));

    // Get non-existing values
    Map<String, String> ret = dao.getMulti("key1", "key2");
    assertNotNull(ret);
    assertNull("value1", ret.get("key1"));

    // Insert values
    Map<String, String> keyValues = new HashMap<String, String>();
    keyValues.put("key1", "value1");
    keyValues.put("key2", "value2");
    dao.insertMulti(keyValues);

    // Simple get test
    ret = dao.getMulti("key1", "key2");
    assertNotNull(ret);
    assertEquals("value1", ret.get("key1"));
    assertEquals("value2", ret.get("key2"));

    // Get some values that don't exist
    ret = dao.getMulti("key2", "key3");
    assertNotNull(ret);
    assertEquals("value2", ret.get("key2"));
    assertNull(ret.get("key3"));
    assertNull(ret.get("key1"));

    // delete
    dao.delete("key1", "key2");

    // validate deletion
    ret = dao.getMulti("key1", "key2");
    assertNotNull(ret);
    assertNull(ret.get("key1"));
    assertNull(ret.get("key2"));

  }
}
