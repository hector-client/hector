package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;


public class KeyspaceOperatorTest {
  private final static String KEYSPACE = "Keyspace1";

  @Test
  @Ignore("Not ready yet")
  public void testInsertGetRemove() {
    String cf = "Standard1";
    Cluster cluster = ClusterFactory.create("127.0.0.1:9170");
    KeyspaceOperator ko = KeyspaceOperatorFactory.create(KEYSPACE, cluster);
    
    Mutator m = MutatorFactory.createMutator(ko);
    for (int i = 0; i < 5; i++) {
      m.insert("testInsertGetRemove" + i, cf, 
          m.createColumn("testInsertGetRemove", "testInsertGetRemove_value_" + i));
    
    }

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
      String name = c.getName().asString();
      assertEquals("testInsertGetRemove", name);
      assertEquals("testInsertGetRemove_value_" + i, r.asString());
      assertEquals(bytes("testInsertGetRemove_value_" + i), r.raw());
    }

    // remove value
    m = MutatorFactory.createMutator(ko);
    for (int i = 0; i < 5; i++) {
      m.delete("testInsertGetRemove_" + i, cf, "testInsertGetRemove");
    }

    // get already removed value
    ColumnQuery q2 = QueryFactory.createColumnQuery(ko);
    q2.setName("testInsertGetRemove").setColumnFamily(cf);
    for (int i = 0; i < 5; i++) {
      Result r = q2.setKey("testInsertGetRemove" + i).execute();
      assertNotNull(r);
      assertTrue(r.isSuccess());
      assertNull("Value should have been deleted", r.asColumn());
      assertNull("Value should have been deleted, yes, even raw", r.raw());
    }
  }

  @Test
  @Ignore("Not ready yet")
  public void testBatchInsertGetRemove() {
    String cf = "Standard1";
    KeyspaceOperator ko = null;
    
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

}
