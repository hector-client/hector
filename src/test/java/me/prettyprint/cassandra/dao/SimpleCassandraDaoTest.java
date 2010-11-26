package me.prettyprint.cassandra.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/cassandra-context-test-v2.xml")
public class SimpleCassandraDaoTest extends BaseEmbededServerSetupTest {

  @Resource
  private SimpleCassandraDao simpleCassandraDao;
  
  @Test
  public void testInsertGetDelete() {
    simpleCassandraDao.insert("fk1", "colName1", "value1");
    assertEquals("value1",simpleCassandraDao.get("fk1", "colName1"));
    simpleCassandraDao.delete("colName1", "fk1");
    assertNull(simpleCassandraDao.get("fk1", "colName1"));
  }
  
}
