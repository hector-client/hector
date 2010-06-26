package me.prettyprint.cassandra.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import static me.prettyprint.cassandra.model.HFactory.*;

import java.io.IOException;
import java.util.Arrays;
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
  @Ignore("Not functinal yet")
  public void testInsertGetDelete() throws HectorException {
    Cluster c = getOrCreateCluster("MyCluster", "localhost:9170");
    ExampleDaoV2 dao = new ExampleDaoV2(createKeyspaceOperator("Keyspace1", c));
    assertNull(dao.get("key"));
    dao.insert("key", "value");
    assertEquals("value", dao.get("key"));
    dao.delete("key");
    assertNull(dao.get("key"));    
  }

  @Test
  @Ignore("Not functinal yet")
  public void testMultiInsertGetDelete() throws HectorException {
    Cluster c = getOrCreateCluster("MyCluster", "localhost:9170");
    ExampleDaoV2 dao = new ExampleDaoV2(createKeyspaceOperator("Keyspace1", c));

    // Get non-existing values
    Map<String, String> ret = dao.getMulti(Arrays.asList("key1", "key2"));
    assertNotNull(ret);
    assertNull("value1", ret.get("key1"));

    // Insert values
    Map<String, String> keyValues = new HashMap<String, String>();
    keyValues.put("key1", "value1");
    keyValues.put("key2", "value2");
    dao.insertMulti(keyValues);
    
    // Simple get test
    ret = dao.getMulti(Arrays.asList("key1", "key2"));
    assertNotNull(ret);
    assertEquals("value1", ret.get("key1"));
    assertEquals("value2", ret.get("key2"));
    
    // Get some values that don't exist
    ret = dao.getMulti(Arrays.asList("key2", "key3"));
    assertNotNull(ret);
    assertEquals("value2", ret.get("key2"));
    assertNull(ret.get("key3"));
    assertNull(ret.get("key1"));

    // delete
    dao.deleteMulti(Arrays.asList("key1", "key2"));
    
    // validate deletion
    ret = dao.getMulti(Arrays.asList("key1", "key2"));
    assertNotNull(ret);
    assertNull(ret.get("key1"));
    assertNull(ret.get("key2"));
    
  }  
}
