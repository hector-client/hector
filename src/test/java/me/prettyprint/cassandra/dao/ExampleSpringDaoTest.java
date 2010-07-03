package me.prettyprint.cassandra.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import javax.annotation.Resource;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;

import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/cassandra-context-test.xml")
public class ExampleSpringDaoTest extends BaseEmbededServerSetupTest {

  @Resource
  private ExampleSpringDao exampleSpringDao;

  @Test
  public void testInsertGetDelete() throws HectorException {
    assertNull(exampleSpringDao.get("key"));
    exampleSpringDao.insert("key", "value");
    assertEquals("value", exampleSpringDao.get("key"));
    exampleSpringDao.delete("key");
    assertNull(exampleSpringDao.get("key"));
  }

}
