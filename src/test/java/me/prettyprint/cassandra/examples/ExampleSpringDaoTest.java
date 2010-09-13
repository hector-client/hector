package me.prettyprint.cassandra.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.hector.api.exceptions.HectorException;

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
