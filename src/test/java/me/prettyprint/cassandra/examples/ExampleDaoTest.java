package me.prettyprint.cassandra.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;

import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExampleDaoTest {

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
    ExampleDao dao = new ExampleDao();
    assertNull(dao.get("key"));
    dao.insert("key", "value");
    assertEquals("value", dao.get("key"));
    dao.delete("key");
    assertNull(dao.get("key"));
  }

}
