package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.model.HFactory.createColumn;
import static me.prettyprint.cassandra.model.HFactory.createColumnQuery;
import static me.prettyprint.cassandra.model.HFactory.createKeyspaceOperator;
import static me.prettyprint.cassandra.model.HFactory.createMultigetSliceQuery;
import static me.prettyprint.cassandra.model.HFactory.createMutator;
import static me.prettyprint.cassandra.model.HFactory.createSuperColumn;
import static me.prettyprint.cassandra.model.HFactory.createSuperColumnQuery;
import static me.prettyprint.cassandra.model.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.extractors.StringExtractor;
import me.prettyprint.cassandra.service.Cluster;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiV2SystemTest extends BaseEmbededServerSetupTest {

  private static final Logger log = LoggerFactory.getLogger(ApiV2SystemTest.class);
  private final static String KEYSPACE = "Keyspace1";
  private static final StringExtractor se = new StringExtractor();
  private Cluster cluster;
  private KeyspaceOperator ko;



  @Before
  public void setupCase() {
    cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
    ko = createKeyspaceOperator(KEYSPACE, cluster);
  }

  @After
  public void teardownCase() {
    ko = null;
    cluster = null;
  }

  @Test
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
      m.addDeletion("testInsertGetRemove" + i, cf, "testInsertGetRemove", se);
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
  public void testSuperInsertGetRemove() {
    String cf = "Super1";

    Mutator m = createMutator(ko);

    @SuppressWarnings("unchecked") // aye, varargs and generics aren't good friends...
    List<HColumn<String,String>> columns = Arrays.asList(createColumn("name1", "value1", se, se),
        createColumn("name2", "value2", se, se));
    m.insert("testSuperInsertGetRemove", cf,
        createSuperColumn("testSuperInsertGetRemove", columns, se, se, se));


    // get value
    SuperColumnQuery<String,String,String> q = createSuperColumnQuery(ko, se, se, se);
    q.setSuperName("testSuperInsertGetRemove").setColumnFamily(cf);
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
    assertEquals("name2", c2.getName());
    assertEquals("value2", c2.getValue());

    // remove value
    m = createMutator(ko);
    m.delete("testSuperInsertGetRemove_", cf, "testSuperInsertGetRemove", se);
  }

  @Test
  public void testMultigetSliceQuery() {
    String cf = "Standard1";

    Mutator m = createMutator(ko);
    for (int i = 1; i < 4; ++i) {
      for (int j = 1; j < 3; ++j) {
        m.addInsertion("testMultigetSliceQuery" + i, cf,
            createColumn("testMultigetSliceQueryColumn" + j, "value" + i + j, se, se));
      }
    }
    MutationResult mr = m.execute();
    assertTrue(mr.isSuccess());
    assertTrue("Time should be > 0", mr.getExecutionTimeMicro() > 0);
    log.debug("insert execution time: {}", mr.getExecutionTimeMicro());

    // get value
    MultigetSliceQuery<String, String> q = createMultigetSliceQuery(ko, se, se);
    q.setColumnFamily(cf);
    q.setKeys("testMultigetSliceQuery1", "testMultigetSliceQuery2");
    // try with column name first
    q.setColumnNames("testMultigetSliceQueryColumn1", "testMultigetSliceQueryColumn2");
    Result<Rows<String,String>> r = q.execute();
    assertNotNull(r);
    assertTrue(r.isSuccess());
    Rows<String,String> rows = r.get();
    assertNotNull(rows);
    assertEquals(2, rows.getCount());
    Row<String,String> row = rows.getByKey("testMultigetSliceQuery1");
    assertNotNull(row);
    assertEquals("testMultigetSliceQuery1", row.getKey());
    ColumnSlice<String,String> slice = row.getColumnSlice();
    assertNotNull(slice);
    // Test slice.getColumnByName
    assertEquals("value11",
        slice.getColumnByName("testMultigetSliceQueryColumn1").getValue());
    assertEquals("value12",
        slice.getColumnByName("testMultigetSliceQueryColumn2").getValue());
    assertNull(slice.getColumnByName("testMultigetSliceQueryColumn3"));
    // Test slice.getColumns
    List<HColumn<String,String>> columns = slice.getColumns();
    assertNotNull(columns);
    assertEquals(2, columns.size());

    // now try with start/finish
    q = createMultigetSliceQuery(ko, se, se);
    q.setColumnFamily(cf);
    q.setKeys("testMultigetSliceQuery3");
    q.setRange("testMultigetSliceQueryColumn1", "testMultigetSliceQueryColumn3", false, 100);
    r = q.execute();
    assertNotNull(r);
    assertTrue(r.isSuccess());
    rows = r.get();
    assertEquals(1, rows.getCount());
    for (Row<String,String> row2: rows) {
      assertNotNull(row2);
      slice = row2.getColumnSlice();
      assertNotNull(slice);
      for (HColumn<String,String> column: slice.getColumns()) {
        if (!column.getName().equals("testMultigetSliceQueryColumn1") &&
            !column.getName().equals("testMultigetSliceQueryColumn2") &&
            !column.getName().equals("testMultigetSliceQueryColumn3")) {
          fail("A columns with unexpected column name returned: " + column.getName());
        }
      }
    }

    // Delete values
    for (int i = 1; i < 4; ++i) {
      for (int j = 1; j < 3; ++j) {
        m.addDeletion("testMultigetSliceQuery" + i, cf, "testMultigetSliceQueryColumn" + j, se);
      }
    }
    mr = m.execute();
    assertTrue(mr.isSuccess());
  }

  @Test
  @Ignore("Not ready yet")
  public void testSuperMultigetSliceQuery() {
    //TODO
  }

  @Test
  @Ignore("Not ready yet")
  public void testRangeSlicesQuery() {
    //TODO
  }

  @Test
  @Ignore("Not ready yet")
  public void testSuperRangeSlicesQuery() {
    //TODO
  }

  @Test
  @Ignore("Not ready yet")
  public void testSlicesQuery() {
    //TODO
  }

  @Test
  @Ignore("Not ready yet")
  public void testSuperSlicesQuery() {
    //TODO
  }
}
