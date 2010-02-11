package me.prettyprint.cassandra.model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraClientFactory;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;

import org.apache.cassandra.service.NotFoundException;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
*
* @author Ran Tavory (rantav@gmail.com)
*
*/
public class KeyspaceTest {

  private static EmbeddedServerHelper embedded;

  private CassandraClient client;
  private Keyspace keyspace;

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

  @Before
  public void setupCase()
      throws TTransportException, TException, IllegalArgumentException, NotFoundException {
    client = new CassandraClientFactory().create("localhost", 9170);
    keyspace = client.getKeySpace("Keyspace1");
  }


  @Test
  public void testGetClient() {
    assertEquals(client, keyspace.getClient());
  }

  @Test
  public void testGetColumn() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetSuperColumnStringColumnPath() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetSuperColumnStringColumnPathBooleanInt() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetSlice() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetSuperSlice() {
    fail("Not yet implemented");
  }

  @Test
  public void testMultigetColumn() {
    fail("Not yet implemented");
  }

  @Test
  public void testMultigetSuperColumnListOfStringColumnPath() {
    fail("Not yet implemented");
  }

  @Test
  public void testMultigetSuperColumnListOfStringColumnPathBooleanInt() {
    fail("Not yet implemented");
  }

  @Test
  public void testMultigetSlice() {
    fail("Not yet implemented");
  }

  @Test
  public void testMultigetSuperSlice() {
    fail("Not yet implemented");
  }

  @Test
  public void testInsert() {
    fail("Not yet implemented");
  }

  @Test
  public void testBatchInsert() {
    fail("Not yet implemented");
  }

  @Test
  public void testRemove() {
    fail("Not yet implemented");
  }

  @Test
  public void testDescribeKeyspace() throws NotFoundException, TException {
    Map<String, Map<String, String>> description = keyspace.describeKeyspace();
    assertNotNull(description);
    assertEquals(4, description.size());
  }

  @Test
  public void testGetCount() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetRangeSlice() {
    fail("Not yet implemented");
  }

  @Test
  public void testGetConsistencyLevel() {
    assertEquals(CassandraClient.DEFAULT_CONSISTENCY_LEVEL, keyspace.getConsistencyLevel());
  }

  @Test
  public void testGetKeyspaceName() {
    assertEquals("Keyspace1", keyspace.getKeyspaceName());
  }

}
