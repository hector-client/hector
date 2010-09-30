package me.prettyprint.cassandra.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.config.ConfigurationException;
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
  public static void setup() throws TTransportException, IOException, InterruptedException, ConfigurationException {
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
    assertNull(dao.get("key", StringSerializer.get()));
    dao.insert("key", "value", StringSerializer.get());
    assertEquals("value", dao.get("key", StringSerializer.get()));
    dao.delete("key", StringSerializer.get());
    assertNull(dao.get("key", StringSerializer.get()));
  }

}
