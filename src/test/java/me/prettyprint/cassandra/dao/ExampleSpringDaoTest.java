package me.prettyprint.cassandra.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import javax.annotation.Resource;

import me.prettyprint.cassandra.service.CassandraClientPool;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;

import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/cassandra-context-test.xml")
public class ExampleSpringDaoTest {

  @Resource
  private ExampleSpringDao exampleSpringDao;
    
    
    
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
  public void testInsertGetDelete() throws Exception {        
    assertNull(exampleSpringDao.get("key"));
    exampleSpringDao.insert("key", "value");
    assertEquals("value", exampleSpringDao.get("key"));
    exampleSpringDao.delete("key");
    assertNull(exampleSpringDao.get("key"));   
  }
    
}
