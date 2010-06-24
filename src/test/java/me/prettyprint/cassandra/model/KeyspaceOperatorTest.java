package me.prettyprint.cassandra.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

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
  private static final StringExtractor se = new StringExtractor();
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
    
    Mutator<String> m = MutatorFactory.createMutator(ko);
    MutationResult mr = m.insert("testInsertGetRemove", cf, 
        m.createColumn("testInsertGetRemove", "testInsertGetRemove_value_", se, se));
    
    // Check the mutation result metadata
    assertTrue(mr.isSuccess());
    assertEquals("127.0.0.1:9170", mr.getHostUsed());
    log.debug("insert execution time: {}", mr.getExecutionTimeMili());

    // get value
    ColumnQuery<String,String> q = QueryFactory.createColumnQuery(ko, se, se);
    q.setName("testInsertGetRemove").setColumnFamily(cf);
    Result<HColumn<String,String>> r = q.setKey("testInsertGetRemove").execute();
    assertNotNull(r);
    assertTrue(r.isSuccess());
    HColumn<String,String> c = r.get();
    assertNotNull(c);
    String value = c.getValue();
    assertEquals("testInsertGetRemove_value_", value);
    String name = c.getName();
    assertEquals("testInsertGetRemove", name);

    // remove value
    m = MutatorFactory.createMutator(ko);
    m.delete("testInsertGetRemove_", cf, "testInsertGetRemove", se);

    // get already removed value
    ColumnQuery<String,String> q2 = QueryFactory.createColumnQuery(ko, StringExtractor.get(), StringExtractor.get());
    q2.setName("testInsertGetRemove").setColumnFamily(cf);
    Result<HColumn<String,String>> r2 = q2.setKey("testInsertGetRemove").execute();
    assertNotNull(r2);
    assertTrue(r2.isSuccess());
    assertNull("Value should have been deleted", r2.get());
  }

  @Test
  @Ignore("Not ready yet")
  public void testBatchInsertGetRemove() {
    String cf = "Standard1";
    
    Mutator<String> m = MutatorFactory.createMutator(ko);
    for (int i = 0; i < 5; i++) {
      m.addInsertion("testInsertGetRemove" + i, cf, 
          m.createColumn("testInsertGetRemove", "testInsertGetRemove_value_" + i, se, se));
    }
    m.execute();

    // get value
    ColumnQuery<String,String> q = QueryFactory.createColumnQuery(ko, StringExtractor.get(), StringExtractor.get());
    q.setName("testInsertGetRemove").setColumnFamily(cf);
    for (int i = 0; i < 5; i++) {
      Result<HColumn<String,String>> r = q.setKey("testInsertGetRemove" + i).execute();
      assertNotNull(r);
      assertTrue(r.isSuccess());
      HColumn<String,String> c = r.get();
      assertNotNull(c);
      String value = c.getValue();
      assertEquals("testInsertGetRemove_value_" + i, value);
    }

    // remove value
    m = MutatorFactory.createMutator(ko);
    for (int i = 0; i < 5; i++) {
      m.addDeletion("testInsertGetRemove_" + i, cf, "testInsertGetRemove", se);
    }
    m.execute();

    // get already removed value
    ColumnQuery<String, String> q2 = QueryFactory.createColumnQuery(ko, se, se);
    q2.setName("testInsertGetRemove").setColumnFamily(cf);
    for (int i = 0; i < 5; i++) {
      Result<HColumn<String,String>> r = q2.setKey("testInsertGetRemove" + i).execute();
      assertNotNull(r);
      assertTrue(r.isSuccess());
      assertNull("Value should have been deleted", r.get());
    }
  }

  
  @Test
  @Ignore("Not ready yet")
  public void testSuperInsertGetRemove() {
    String cf = "Super1";
    
    Mutator<String> m = MutatorFactory.createMutator(ko);
    
    @SuppressWarnings("unchecked") // aye, varargs and generics aren't good friends...
    List<HColumn<String,String>> columns = Arrays.asList(m.createColumn("name1", "value1", se, se), 
        m.createColumn("name2", "value2", se, se));
    m.insert("testSuperInsertGetRemove", cf, 
        m.createSuperColumn("testSuperInsertGetRemove", columns, se, se, se));
    

    // get value
    SuperColumnQuery<String,String,String> q = QueryFactory.createSuperColumnQuery(ko);
    q.setName("testSuperInsertGetRemove").setColumnFamily(cf);
    Result<HSuperColumn<String,String,String>> r = q.setKey("testSuperInsertGetRemove").execute();
    assertNotNull(r);
    assertTrue(r.isSuccess());
    HSuperColumn<String,String,String> sc = r.get();
    assertNotNull(sc);
    assertEquals(2, sc.getSize());
    HColumn<String,String> c = sc.get(0);
    String value = c.getValue();
    assertEquals("value1", value);
    String name = c.getName();
    assertEquals("name1", name);

  
    HColumn<String,String> c2 = sc.get(1);
    assertEquals("name1", c2.getName());
    assertEquals("value1", c2.getValue());

    // remove value
    m = MutatorFactory.createMutator(ko);
    m.delete("testSuperInsertGetRemove_", cf, "testSuperInsertGetRemove", se);
  }
}
