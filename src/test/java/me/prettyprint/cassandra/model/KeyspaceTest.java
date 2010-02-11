package me.prettyprint.cassandra.model;

import static org.junit.Assert.*;

import java.io.IOException;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraClientFactory;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;

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
  public void setupCase() throws TTransportException, TException {
    client = new CassandraClientFactory().create("localhost", 9170);
  }


  @Test
  public void testGetClient() {
    fail("Not yet implemented");
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
  public void testDescribeKeyspace() {
    fail("Not yet implemented");
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
    fail("Not yet implemented");
  }

  @Test
  public void testGetKeyspaceName() {
    fail("Not yet implemented");
  }

}
