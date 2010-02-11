package me.prettyprint.cassandra.model;

import static org.junit.Assert.*;
import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static me.prettyprint.cassandra.utils.StringUtils.string;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraClientFactory;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;

import org.apache.cassandra.service.Column;
import org.apache.cassandra.service.ColumnPath;
import org.apache.cassandra.service.InvalidRequestException;
import org.apache.cassandra.service.NotFoundException;
import org.apache.cassandra.service.TimedOutException;
import org.apache.cassandra.service.UnavailableException;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * For the tests we assume the following structure:
 *
 * &lt;Keyspaces&gt; &lt;Keyspace Name="Keyspace1"&gt; &lt;ColumnFamily
 * CompareWith="BytesType" Name="Standard1" FlushPeriodInMinutes="60"/&gt;
 * &lt;ColumnFamily CompareWith="UTF8Type" Name="Standard2"/&gt;
 * &lt;ColumnFamily CompareWith="TimeUUIDType" Name="StandardByUUID1"/&gt;
 * &lt;ColumnFamily ColumnType="Super" CompareWith="UTF8Type"
 * CompareSubcolumnsWith="UTF8Type" Name="Super1"/&gt;
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
  public void setupCase() throws TTransportException, TException, IllegalArgumentException,
      NotFoundException {
    client = new CassandraClientFactory().create("localhost", 9170);
    keyspace = client.getKeySpace("Keyspace1", 1);
  }

  @Test
  public void testInsertAndGetAndRemove() throws IllegalArgumentException, NoSuchElementException,
      IllegalStateException, NotFoundException, Exception {

    // insert value
    ColumnPath cp = new ColumnPath("Standard1", null, bytes("testInsertAndGetAndRemove"));
    for (int i = 0; i < 100; i++) {
      keyspace.insert("testInsertAndGetAndRemove_" + i, cp,
          bytes("testInsertAndGetAndRemove_value_" + i));
    }

    // get value
    for (int i = 0; i < 100; i++) {
      Column col = keyspace.getColumn("testInsertAndGetAndRemove_" + i, cp);
      assertNotNull(col);
      String value = string(col.getValue());
      assertEquals("testInsertAndGetAndRemove_value_" + i, value);
    }

    // remove value
    for (int i = 0; i < 100; i++) {
      keyspace.remove("testInsertAndGetAndRemove_" + i, cp);
    }

    // get already removed value
    for (int i = 0; i < 100; i++) {
      try {
        keyspace.getColumn("testInsertAndGetAndRemove_" + i, cp);
        fail("the value should already being deleted");
      } catch (NotFoundException e) {
        // good
      }
    }
  }

  @Test
  public void testValideColumnPath() throws  UnavailableException, TException, TimedOutException {
    // Try to insert invalid columns
    // insert value
    ColumnPath cp = new ColumnPath("Standard1", null, bytes("testValideColumnPath"));
    try {
      keyspace.insert("testValideColumnPath", cp, bytes("testValideColumnPath_value"));
      keyspace.remove("testValideColumnPath", cp);
    } catch (InvalidRequestException e) {
      fail("Should not have thrown an error for Standard1");
    }

    cp = new ColumnPath("CFdoesNotExist", null, bytes("testInsertAndGetAndRemove"));
    try {
      keyspace.insert("testValideColumnPath", cp, bytes("testValideColumnPath_value"));
      fail("Should have failed with CFdoesNotExist");
    } catch (InvalidRequestException e) {
      // ok
    }

    cp = new ColumnPath("Standard1", bytes("testInsertAndGetAndRemove"), null);
    try {
      keyspace.insert("testValideColumnPath", cp, bytes("testValideColumnPath_value"));
      fail("Should have failed with supercolumn");
    } catch (InvalidRequestException e) {
      // ok
    }

  }

  @Test
  public void testGetClient() {
    assertEquals(client, keyspace.getClient());
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
  public void testBatchInsert() {
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
