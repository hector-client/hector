package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.model.HFactory.createColumn;
import static me.prettyprint.cassandra.model.HFactory.createColumnQuery;
import static me.prettyprint.cassandra.model.HFactory.createKeyspaceOperator;
import static me.prettyprint.cassandra.model.HFactory.createMutator;
import static me.prettyprint.cassandra.model.HFactory.createSuperColumn;
import static me.prettyprint.cassandra.model.HFactory.createSuperColumnQuery;
import static me.prettyprint.cassandra.model.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.service.Cluster;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyspaceOperatorTest extends BaseEmbededServerSetupTest {
  
  private static final Logger log = LoggerFactory.getLogger(KeyspaceOperatorTest.class);
  private final static String KEYSPACE = "Keyspace1";
  private static final StringExtractor se = new StringExtractor();
  private Cluster cluster;
  private KeyspaceOperator ko;
  


  @Before
  public void setupCase() {
    cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
    ko = createKeyspaceOperator(KEYSPACE, cluster);    
  }
  
  @Test
  //@Ignore("Not ready yet")
  public void testInsertGetRemove() {
    String cf = "Standard1";
    
    Mutator m = createMutator(ko);
    MutationResult mr = m.insert("testInsertGetRemove", cf, 
        createColumn("testInsertGetRemove", "testInsertGetRemove_value_", se, se));
    
    // Check the mutation result metadata
    assertTrue(mr.isSuccess());
    //assertEquals("127.0.0.1:9170", mr.getHostUsed());
    assertTrue("Time should be > 0", mr.getExecutionTimeMicro() > 0);
    log.debug("insert execution time: {}", mr.getExecutionTimeMicro());

    // get value
    ColumnQuery<String,String> q = createColumnQuery(ko, se, se);
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
    assertEquals(q, r.getQuery());
    assertTrue("Time should be > 0", r.getExecutionTimeMicro() > 0);


    // remove value
    m = createMutator(ko);
    MutationResult mr2 = m.delete("testInsertGetRemove", cf, "testInsertGetRemove", se);
    assertTrue("Delete failed", mr2.isSuccess());
    assertTrue("Time should be > 0", mr2.getExecutionTimeMicro() > 0);

    // get already removed value
    ColumnQuery<String,String> q2 = createColumnQuery(ko, se, se);
    q2.setName("testInsertGetRemove").setColumnFamily(cf);
    Result<HColumn<String,String>> r2 = q2.setKey("testInsertGetRemove").execute();
    assertNotNull(r2);
    assertTrue("no success..", r2.isSuccess());
    assertNull("Value should have been deleted", r2.get());
  }

  @Test
  @Ignore("Not ready yet")
  public void testBatchInsertGetRemove() {
    String cf = "Standard1";
    
    Mutator m = createMutator(ko);
    for (int i = 0; i < 5; i++) {
      m.addInsertion("testInsertGetRemove" + i, cf, 
          createColumn("testInsertGetRemove", "testInsertGetRemove_value_" + i, se, se));
    }
    m.execute();

    // get value
    ColumnQuery<String,String> q = createColumnQuery(ko, se, se);
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
    m = createMutator(ko);
    for (int i = 0; i < 5; i++) {
      m.addDeletion("testInsertGetRemove_" + i, cf, "testInsertGetRemove", se);
    }
    m.execute();

    // get already removed value
    ColumnQuery<String, String> q2 = createColumnQuery(ko, se, se);
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
    
    Mutator m = createMutator(ko);
    
    @SuppressWarnings("unchecked") // aye, varargs and generics aren't good friends...
    List<HColumn<String,String>> columns = Arrays.asList(createColumn("name1", "value1", se, se), 
        createColumn("name2", "value2", se, se));
    m.insert("testSuperInsertGetRemove", cf, 
        createSuperColumn("testSuperInsertGetRemove", columns, se, se, se));
    

    // get value
    SuperColumnQuery<String,String,String> q = createSuperColumnQuery(ko);
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
    m = createMutator(ko);
    m.delete("testSuperInsertGetRemove_", cf, "testSuperInsertGetRemove", se);
  }
}
