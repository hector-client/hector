package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import me.prettyprint.cassandra.service.Cluster;
import me.prettyprint.cassandra.service.ClusterFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KeyspaceOperatorTest {
  
  private static final Logger log = LoggerFactory.getLogger(KeyspaceOperatorTest.class);
  private final static String KEYSPACE = "Keyspace1";
  private Cluster cluster;
  private KeyspaceOperator ko;

  @Before
  public void setup() {
    cluster = ClusterFactory.getOrCreate("MyCluster", "127.0.0.1:9170");
    ko = KeyspaceOperatorFactory.create(KEYSPACE, cluster);    
  }
  
  @Test
  @Ignore("Not ready yet")
  public void testInsertGetRemove() {
    String cf = "Standard1";
    
    Mutator m = MutatorFactory.createMutator(ko);
    MutationResult mr = m.insert("testInsertGetRemove", cf, 
        m.createColumn("testInsertGetRemove", "testInsertGetRemove_value_"));
    
    // Check the mutation result metadata
    assertTrue(mr.isSuccess());
    assertEquals("127.0.0.1:9170", mr.getHostUsed());
    log.debug("insert execution time: {}", mr.getExecutionTimeMili());

    // get value
    ColumnQuery q = QueryFactory.createColumnQuery(ko);
    q.setName("testInsertGetRemove").setColumnFamily(cf);
    Result r = q.setKey("testInsertGetRemove").execute();
    assertNotNull(r);
    assertTrue(r.isSuccess());
    Column c = r.asColumn();
    assertNotNull(c);
    String value = c.getValue().asString();
    assertEquals("testInsertGetRemove_value_", value);
    String name = c.getName().asString();
    assertEquals("testInsertGetRemove", name);
    assertEquals("testInsertGetRemove_value_", r.asString());
    assertEquals(bytes("testInsertGetRemove_value_"), r.raw());

    // remove value
    m = MutatorFactory.createMutator(ko);
    m.delete("testInsertGetRemove_", cf, "testInsertGetRemove");

    // get already removed value
    ColumnQuery q2 = QueryFactory.createColumnQuery(ko);
    q2.setName("testInsertGetRemove").setColumnFamily(cf);
    Result r2 = q2.setKey("testInsertGetRemove").execute();
    assertNotNull(r2);
    assertTrue(r2.isSuccess());
    assertNull("Value should have been deleted", r2.asColumn());
    assertNull("Value should have been deleted, yes, even raw", r2.raw());
  }

  @Test
  @Ignore("Not ready yet")
  public void testBatchInsertGetRemove() {
    String cf = "Standard1";
    
    Mutator m = MutatorFactory.createMutator(ko);
    for (int i = 0; i < 5; i++) {
      m.addInsertion("testInsertGetRemove" + i, cf, 
          m.createColumn("testInsertGetRemove", "testInsertGetRemove_value_" + i));
    }
    m.execute();

    // get value
    ColumnQuery q = QueryFactory.createColumnQuery(ko);
    q.setName("testInsertGetRemove").setColumnFamily(cf);
    for (int i = 0; i < 5; i++) {
      Result r = q.setKey("testInsertGetRemove" + i).execute();
      assertNotNull(r);
      assertTrue(r.isSuccess());
      Column c = r.asColumn();
      assertNotNull(c);
      String value = c.getValue().asString();
      assertEquals("testInsertGetRemove_value_" + i, value);
    }

    // remove value
    m = MutatorFactory.createMutator(ko);
    for (int i = 0; i < 5; i++) {
      m.addDeletion("testInsertGetRemove_" + i, cf, "testInsertGetRemove");
    }
    m.execute();

    // get already removed value
    ColumnQuery q2 = QueryFactory.createColumnQuery(ko);
    q2.setName("testInsertGetRemove").setColumnFamily(cf);
    for (int i = 0; i < 5; i++) {
      Result r = q2.setKey("testInsertGetRemove" + i).execute();
      assertNotNull(r);
      assertTrue(r.isSuccess());
      assertNull("Value should have been deleted", r.asColumn());
    }
  }

  
  @Test
  @Ignore("Not ready yet")
  public void testSuperInsertGetRemove() {
    String cf = "Super1";
    
    Mutator m = MutatorFactory.createMutator(ko);
    m.insert("testSuperInsertGetRemove", cf, 
        m.createSuperColumn("testSuperInsertGetRemove", 
            m.createColumn("name1", "value1"),
            m.createColumn("name2", "value2")));
    

    // get value
    ColumnQuery q = QueryFactory.createColumnQuery(ko);
    q.setName("testSuperInsertGetRemove").setColumnFamily(cf);
    Result r = q.setKey("testSuperInsertGetRemove").execute();
    assertNotNull(r);
    assertTrue(r.isSuccess());
    SuperColumn sc = r.asSuperColumn();
    assertNotNull(sc);
    assertEquals(2, sc.size());
    Column c = sc.get(0);
    String value = c.getValue().asString();
    assertEquals("value1", value);
    String name = c.getName().asString();
    assertEquals("name1", name);

  
    Column c2 = sc.get(1);
    assertEquals("name1", c2.getName().asString());
    assertEquals("value1", c2.getValue().asString());

    // remove value
    m = MutatorFactory.createMutator(ko);
    m.delete("testSuperInsertGetRemove_", cf, "testSuperInsertGetRemove");
  }

}
