package me.prettyprint.hector.api;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createColumnQuery;
import static me.prettyprint.hector.api.factory.HFactory.createCountQuery;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMultigetSliceQuery;
import static me.prettyprint.hector.api.factory.HFactory.createMultigetSubSliceQuery;
import static me.prettyprint.hector.api.factory.HFactory.createMultigetSuperSliceQuery;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.createRangeSlicesQuery;
import static me.prettyprint.hector.api.factory.HFactory.createRangeSubSlicesQuery;
import static me.prettyprint.hector.api.factory.HFactory.createRangeSuperSlicesQuery;
import static me.prettyprint.hector.api.factory.HFactory.createSliceQuery;
import static me.prettyprint.hector.api.factory.HFactory.createSubColumnQuery;
import static me.prettyprint.hector.api.factory.HFactory.createSubCountQuery;
import static me.prettyprint.hector.api.factory.HFactory.createSubSliceQuery;
import static me.prettyprint.hector.api.factory.HFactory.createSuperColumn;
import static me.prettyprint.hector.api.factory.HFactory.createSuperColumnQuery;
import static me.prettyprint.hector.api.factory.HFactory.createSuperCountQuery;
import static me.prettyprint.hector.api.factory.HFactory.createSuperSliceQuery;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.OrderedSuperRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.beans.SuperRow;
import me.prettyprint.hector.api.beans.SuperRows;
import me.prettyprint.hector.api.beans.SuperSlice;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.CountQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.MultigetSubSliceQuery;
import me.prettyprint.hector.api.query.MultigetSuperSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.RangeSubSlicesQuery;
import me.prettyprint.hector.api.query.RangeSuperSlicesQuery;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.hector.api.query.SubColumnQuery;
import me.prettyprint.hector.api.query.SubCountQuery;
import me.prettyprint.hector.api.query.SubSliceQuery;
import me.prettyprint.hector.api.query.SuperColumnQuery;
import me.prettyprint.hector.api.query.SuperCountQuery;
import me.prettyprint.hector.api.query.SuperSliceQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiV2SystemTest extends BaseEmbededServerSetupTest {

  private static final Logger log = LoggerFactory.getLogger(ApiV2SystemTest.class);
  private final static String KEYSPACE = "Keyspace1";
  private static final StringSerializer se = new StringSerializer();
  private Cluster cluster;
  private Keyspace ko;

  @Before
  public void setupCase() {
    cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
    ko = createKeyspace(KEYSPACE, cluster);
  }

  @After
  public void teardownCase() {
    ko = null;
    cluster = null;
  }

  @Test
  public void testInsertGetRemove() {
    String cf = "Standard1";

    Mutator<String> m = createMutator(ko, se);
    MutationResult mr = m.insert("testInsertGetRemove", cf,
        createColumn("testInsertGetRemove", "testInsertGetRemove_value_", se, se));

    // Check the mutation result metadata
    // assertEquals("127.0.0.1:9170", mr.getHostUsed());
    assertTrue("Time should be > 0", mr.getExecutionTimeMicro() > 0);

    log.debug("insert execution time: {}", mr.getExecutionTimeMicro());

    // get value
    ColumnQuery<String, String, String> q = createColumnQuery(ko, se, se, se);
    q.setName("testInsertGetRemove").setColumnFamily(cf);
    QueryResult<HColumn<String, String>> r = q.setKey("testInsertGetRemove").execute();
    assertNotNull(r);

    HColumn<String, String> c = r.get();
    assertNotNull(c);
    String value = c.getValue();
    assertEquals("testInsertGetRemove_value_", value);
    String name = c.getName();
    assertEquals("testInsertGetRemove", name);
    assertEquals(q, r.getQuery());
    assertTrue("Time should be > 0", r.getExecutionTimeMicro() > 0);

    // remove value
    m = createMutator(ko, se);
    MutationResult mr2 = m.delete("testInsertGetRemove", cf, "testInsertGetRemove", se);
    assertTrue("Time should be > 0", mr2.getExecutionTimeMicro() > 0);

    // get already removed value
    ColumnQuery<String, String, String> q2 = createColumnQuery(ko, se, se, se);
    q2.setName("testInsertGetRemove").setColumnFamily(cf);
    QueryResult<HColumn<String, String>> r2 = q2.setKey("testInsertGetRemove").execute();
    assertNotNull(r2);
    assertNull("Value should have been deleted", r2.get());
  }

  @Test
  public void testBatchInsertGetRemove() {
    String cf = "Standard1";

    Mutator<String> m = createMutator(ko, se);
    for (int i = 0; i < 5; i++) {
      m.addInsertion("testInsertGetRemove" + i, cf,
          createColumn("testInsertGetRemove", "testInsertGetRemove_value_" + i, se, se));
    }
    m.execute();

    // get value
    ColumnQuery<String, String, String> q = createColumnQuery(ko, se, se, se);
    q.setName("testInsertGetRemove").setColumnFamily(cf);
    for (int i = 0; i < 5; i++) {
      QueryResult<HColumn<String, String>> r = q.setKey("testInsertGetRemove" + i).execute();
      assertNotNull(r);
      HColumn<String, String> c = r.get();
      assertNotNull(c);
      String value = c.getValue();
      assertEquals("testInsertGetRemove_value_" + i, value);
    }

    // remove value
    m = createMutator(ko, se);
    for (int i = 0; i < 5; i++) {
      m.addDeletion("testInsertGetRemove" + i, cf, "testInsertGetRemove", se);
    }
    m.execute();

    // get already removed value
    ColumnQuery<String, String, String> q2 = createColumnQuery(ko, se, se, se);
    q2.setName("testInsertGetRemove").setColumnFamily(cf);
    for (int i = 0; i < 5; i++) {
      QueryResult<HColumn<String, String>> r = q2.setKey("testInsertGetRemove" + i).execute();
      assertNotNull(r);
      assertNull("Value should have been deleted", r.get());
    }
  }

  @Test
  public void testSuperInsertGetRemove() {
    String cf = "Super1";

    Mutator<String> m = createMutator(ko, se);

    @SuppressWarnings("unchecked")
    // aye, varargs and generics aren't good friends...
    List<HColumn<String, String>> columns = Arrays.asList(createColumn("name1", "value1", se, se),
        createColumn("name2", "value2", se, se));
    m.insert("testSuperInsertGetRemove", cf,
        createSuperColumn("testSuperInsertGetRemove", columns, se, se, se));

    // get value
    SuperColumnQuery<String, String, String, String> q = createSuperColumnQuery(ko, se, se, se, se);
    q.setSuperName("testSuperInsertGetRemove").setColumnFamily(cf);
    QueryResult<HSuperColumn<String, String, String>> r = q.setKey("testSuperInsertGetRemove").execute();
    assertNotNull(r);
    HSuperColumn<String, String, String> sc = r.get();
    assertNotNull(sc);
    assertEquals(2, sc.getSize());
    HColumn<String, String> c = sc.get(0);
    String value = c.getValue();
    assertEquals("value1", value);
    String name = c.getName();
    assertEquals("name1", name);

    HColumn<String, String> c2 = sc.get(1);
    assertEquals("name2", c2.getName());
    assertEquals("value2", c2.getValue());

    // remove value
    m = createMutator(ko, se);
    m.subDelete("testSuperInsertGetRemove", cf, "testSuperInsertGetRemove", null, se, se);

    // test after removal
    r = q.execute();
    sc = r.get();
    assertNull(sc);
  }

  @Test
  public void testSubColumnQuery() {
    String cf = "Super1";

    TestCleanupDescriptor cleanup = insertSuperColumns(cf, 1, "testSubColumnQuery", 1,
        "testSubColumnQuerySuperColumn");

    // get value
    SubColumnQuery<String,String, String, String> q = createSubColumnQuery(ko, se, se, se, se);
    q.setSuperColumn("testSubColumnQuerySuperColumn0").setColumn("c000").setColumnFamily(cf);
    QueryResult<HColumn<String, String>> r = q.setKey("testSubColumnQuery0").execute();
    assertNotNull(r);
    HColumn<String, String> c = r.get();
    assertNotNull(c);
    String value = c.getValue();
    assertEquals("v000", value);
    String name = c.getName();
    assertEquals("c000", name);

    // get nonexisting value
    q.setColumn("column doesn't exist");
    r = q.execute();
    assertNotNull(r);
    c = r.get();
    assertNull(c);

    // remove value
    deleteColumns(cleanup);
  }

  @Test
  public void testMultigetSliceQuery() {
    String cf = "Standard1";

    TestCleanupDescriptor cleanup = insertColumns(cf, 4, "testMultigetSliceQuery", 3,
        "testMultigetSliceQueryColumn");

    // get value
    MultigetSliceQuery<String, String, String> q = createMultigetSliceQuery(ko, se, se, se);
    q.setColumnFamily(cf);
    q.setKeys("testMultigetSliceQuery1", "testMultigetSliceQuery2");
    // try with column name first
    q.setColumnNames("testMultigetSliceQueryColumn1", "testMultigetSliceQueryColumn2");
    QueryResult<Rows<String, String, String>> r = q.execute();
    assertNotNull(r);
    Rows<String, String, String> rows = r.get();
    assertNotNull(rows);
    assertEquals(2, rows.getCount());
    Row<String, String, String> row = rows.getByKey("testMultigetSliceQuery1");
    assertNotNull(row);
    assertEquals("testMultigetSliceQuery1", row.getKey());
    ColumnSlice<String, String> slice = row.getColumnSlice();
    assertNotNull(slice);
    // Test slice.getColumnByName
    assertEquals("value11", slice.getColumnByName("testMultigetSliceQueryColumn1").getValue());
    assertEquals("value12", slice.getColumnByName("testMultigetSliceQueryColumn2").getValue());
    assertNull(slice.getColumnByName("testMultigetSliceQueryColumn3"));
    // Test slice.getColumns
    List<HColumn<String, String>> columns = slice.getColumns();
    assertNotNull(columns);
    assertEquals(2, columns.size());

    // now try with start/finish
    q = createMultigetSliceQuery(ko, se, se, se);
    q.setColumnFamily(cf);
    q.setKeys("testMultigetSliceQuery3");
    q.setRange("testMultigetSliceQueryColumn1", "testMultigetSliceQueryColumn3", false, 100);
    r = q.execute();
    assertNotNull(r);
    rows = r.get();
    assertEquals(1, rows.getCount());
    for (Row<String, String, String> row2 : rows) {
      assertNotNull(row2);
      slice = row2.getColumnSlice();
      assertNotNull(slice);
      for (HColumn<String, String> column : slice.getColumns()) {
        if (!column.getName().equals("testMultigetSliceQueryColumn1")
            && !column.getName().equals("testMultigetSliceQueryColumn2")
            && !column.getName().equals("testMultigetSliceQueryColumn3")) {
          fail("A columns with unexpected column name returned: " + column.getName());
        }
      }
    }

    deleteColumns(cleanup);
  }

  @Test
  public void testSliceQuery() {
    String cf = "Standard1";

    TestCleanupDescriptor cleanup = insertColumns(cf, 1, "testSliceQuery", 4, "testSliceQuery");

    // get value
    SliceQuery<String, String, String> q = createSliceQuery(ko, se, se, se);
    q.setColumnFamily(cf);
    q.setKey("testSliceQuery0");
    // try with column name first
    q.setColumnNames("testSliceQuery1", "testSliceQuery2", "testSliceQuery3");
    QueryResult<ColumnSlice<String, String>> r = q.execute();
    assertNotNull(r);
    ColumnSlice<String, String> slice = r.get();
    assertNotNull(slice);
    assertEquals(3, slice.getColumns().size());
    // Test slice.getColumnByName
    assertEquals("value01", slice.getColumnByName("testSliceQuery1").getValue());
    assertEquals("value02", slice.getColumnByName("testSliceQuery2").getValue());
    assertEquals("value03", slice.getColumnByName("testSliceQuery3").getValue());
    // Test slice.getColumns
    List<HColumn<String, String>> columns = slice.getColumns();
    assertNotNull(columns);
    assertEquals(3, columns.size());

    // now try with start/finish
    q = createSliceQuery(ko, se, se, se);
    q.setColumnFamily(cf);
    q.setKey("testSliceQuery0");
    // try reversed this time
    q.setRange("testSliceQuery2", "testSliceQuery1", true, 100);
    r = q.execute();
    assertNotNull(r);
    slice = r.get();
    assertNotNull(slice);
    assertEquals(2, slice.getColumns().size());
    for (HColumn<String, String> column : slice.getColumns()) {
      if (!column.getName().equals("testSliceQuery1")
          && !column.getName().equals("testSliceQuery2")) {
        fail("A columns with unexpected column name returned: " + column.getName());
      }
    }

    deleteColumns(cleanup);
  }

  @Test
  public void testSuperSliceQuery() {
    String cf = "Super1";

    Mutator<String> m = createMutator(ko, se);
    for (int j = 1; j <= 3; ++j) {
      @SuppressWarnings("unchecked")
      HSuperColumn<String, String, String> sc = createSuperColumn("testSuperSliceQuery" + j,
          Arrays.asList(createColumn("name", "value", se, se)), se, se, se);
      m.addInsertion("testSuperSliceQuery", cf, sc);
    }

    MutationResult mr = m.execute();
    assertTrue("Time should be > 0", mr.getExecutionTimeMicro() > 0);
    log.debug("insert execution time: {}", mr.getExecutionTimeMicro());

    // get value
    SuperSliceQuery<String, String, String, String> q = createSuperSliceQuery(ko, se, se, se, se);
    q.setColumnFamily(cf);
    q.setKey("testSuperSliceQuery");
    // try with column name first
    q.setColumnNames("testSuperSliceQuery1", "testSuperSliceQuery2", "testSuperSliceQuery3");
    QueryResult<SuperSlice<String, String, String>> r = q.execute();
    assertNotNull(r);
    SuperSlice<String, String, String> slice = r.get();
    assertNotNull(slice);
    assertEquals(3, slice.getSuperColumns().size());
    // Test slice.getColumnByName
    assertEquals("value", slice.getColumnByName("testSuperSliceQuery1").getColumns().get(0)
                               .getValue());

    // now try with start/finish
    q = createSuperSliceQuery(ko, se, se, se, se);
    q.setColumnFamily(cf);
    q.setKey("testSuperSliceQuery");
    // try reversed this time
    q.setRange("testSuperSliceQuery1", "testSuperSliceQuery2", false, 2);
    r = q.execute();
    assertNotNull(r);
    slice = r.get();
    assertNotNull(slice);
    for (HSuperColumn<String, String, String> scolumn : slice.getSuperColumns()) {
      if (!scolumn.getName().equals("testSuperSliceQuery1")
          && !scolumn.getName().equals("testSuperSliceQuery2")) {
        fail("A columns with unexpected column name returned: " + scolumn.getName());
      }
    }

    // Delete values
    for (int j = 1; j <= 3; ++j) {
      m.addDeletion("testSuperSliceQuery", cf, "testSuperSliceQuery" + j, se);
    }
    mr = m.execute();

    // Test after deletion
    r = q.execute();
    assertNotNull(r);
    slice = r.get();
    assertNotNull(slice);
    assertTrue(slice.getSuperColumns().isEmpty());
  }

  /**
   * Tests the SubSliceQuery, a query on columns within a supercolumn
   */
  @Test
  public void testSubSliceQuery() {
    String cf = "Super1";

    // insert
    TestCleanupDescriptor cleanup = insertSuperColumns(cf, 1, "testSliceQueryOnSubcolumns", 1,
        "testSliceQueryOnSubcolumns_column");

    // get value
    SubSliceQuery<String, String, String, String> q = createSubSliceQuery(ko, se, se, se, se);
    q.setColumnFamily(cf);
    q.setSuperColumn("testSliceQueryOnSubcolumns_column0");
    q.setKey("testSliceQueryOnSubcolumns0");
    // try with column name first
    q.setColumnNames("c000", "c110", "c_doesn't_exist");
    QueryResult<ColumnSlice<String, String>> r = q.execute();
    assertNotNull(r);
    ColumnSlice<String, String> slice = r.get();
    assertNotNull(slice);
    assertEquals(2, slice.getColumns().size());
    // Test slice.getColumnByName
    assertEquals("v000", slice.getColumnByName("c000").getValue());

    // now try with start/finish
    q = createSubSliceQuery(ko, se, se, se, se);
    q.setColumnFamily(cf);
    q.setKey("testSliceQueryOnSubcolumns0");
    q.setSuperColumn("testSliceQueryOnSubcolumns_column0");
    // try reversed this time
    q.setRange("c000", "c110", false, 2);
    r = q.execute();
    assertNotNull(r);
    slice = r.get();
    assertNotNull(slice);
    for (HColumn<String, String> column : slice.getColumns()) {
      if (!column.getName().equals("c000") && !column.getName().equals("c110")) {
        fail("A columns with unexpected column name returned: " + column.getName());
      }
    }

    // Delete values
    deleteColumns(cleanup);

    // Test after deletion
    r = q.execute();
    assertNotNull(r);
    slice = r.get();
    assertNotNull(slice);
    assertTrue(slice.getColumns().isEmpty());
  }

  @Test
  public void testMultigetSuperSliceQuery() {
    String cf = "Super1";

    TestCleanupDescriptor cleanup = insertSuperColumns(cf, 4, "testSuperMultigetSliceQueryKey", 3,
        "testSuperMultigetSliceQuery");

    // get value
    MultigetSuperSliceQuery<String, String, String, String> q = createMultigetSuperSliceQuery(ko, se, se, se,
        se);
    q.setColumnFamily(cf);
    q.setKeys("testSuperMultigetSliceQueryKey0", "testSuperMultigetSliceQueryKey3");
    // try with column name first
    q.setColumnNames("testSuperMultigetSliceQuery1", "testSuperMultigetSliceQuery2");
    QueryResult<SuperRows<String, String, String, String>> r = q.execute();
    assertNotNull(r);
    SuperRows<String, String, String, String> rows = r.get();
    assertNotNull(rows);
    assertEquals(2, rows.getCount());
    SuperRow<String, String, String, String> row = rows.getByKey("testSuperMultigetSliceQueryKey0");
    assertNotNull(row);
    assertEquals("testSuperMultigetSliceQueryKey0", row.getKey());
    SuperSlice<String, String, String> slice = row.getSuperSlice();
    assertNotNull(slice);
    // Test slice.getColumnByName
    assertEquals("v001", slice.getColumnByName("testSuperMultigetSliceQuery1").getColumns().get(0)
                              .getValue());
    assertNull(slice.getColumnByName("testSuperMultigetSliceQuery3"));

    deleteColumns(cleanup);
  }

  @Test
  public void testMultigetSubSliceQuery() {
    String cf = "Super1";

    // insert
    TestCleanupDescriptor cleanup = insertSuperColumns(cf, 3, "testMultigetSubSliceQuery", 1,
        "testMultigetSubSliceQuery");

    // get value
    MultigetSubSliceQuery<String, String, String, String> q = createMultigetSubSliceQuery(ko, se, se, se, se);
    q.setColumnFamily(cf);
    q.setSuperColumn("testMultigetSubSliceQuery0");
    q.setKeys("testMultigetSubSliceQuery0", "testMultigetSubSliceQuery2");
    // try with column name first
    q.setColumnNames("c000", "c110");
    QueryResult<Rows<String, String, String>> r = q.execute();
    assertNotNull(r);
    Rows<String, String, String> rows = r.get();
    assertNotNull(rows);
    assertEquals(2, rows.getCount());
    Row<String, String, String> row = rows.getByKey("testMultigetSubSliceQuery0");
    assertNotNull(row);
    assertEquals("testMultigetSubSliceQuery0", row.getKey());
    ColumnSlice<String, String> slice = row.getColumnSlice();
    assertNotNull(slice);
    // Test slice.getColumnByName
    assertEquals("v000", slice.getColumnByName("c000").getValue());
    assertEquals("v100", slice.getColumnByName("c110").getValue());
    // Test slice.getColumns
    List<HColumn<String, String>> columns = slice.getColumns();
    assertNotNull(columns);
    assertEquals(2, columns.size());

    // now try with start/finish
    q = createMultigetSubSliceQuery(ko, se, se, se, se);
    q.setColumnFamily(cf);
    q.setKeys("testMultigetSubSliceQuery0");
    q.setSuperColumn("testMultigetSubSliceQuery0");
    // try reversed this time
    q.setRange("c000", "c110", false, 2);
    r = q.execute();
    assertNotNull(r);
    rows = r.get();
    assertEquals(1, rows.getCount());
    for (Row<String, String, String> row2 : rows) {
      assertNotNull(row2);
      slice = row2.getColumnSlice();
      assertNotNull(slice);
      assertEquals(2, slice.getColumns().size());
      for (HColumn<String, String> column : slice.getColumns()) {
        if (!column.getName().equals("c000") && !column.getName().equals("c110")) {
          fail("A columns with unexpected column name returned: " + column.getName());
        }
      }
    }

    // Delete values
    deleteColumns(cleanup);
  }

  @Test
  public void testRangeSlicesQuery() {
    String cf = "Standard1";

    TestCleanupDescriptor cleanup = insertColumns(cf, 4, "testRangeSlicesQuery", 3,
        "testRangeSlicesQueryColumn");

    // get value
    RangeSlicesQuery<String, String, String> q = createRangeSlicesQuery(ko, se, se, se);
    q.setColumnFamily(cf);
    q.setKeys("testRangeSlicesQuery1", "testRangeSlicesQuery3");
    // try with column name first
    q.setColumnNames("testRangeSlicesQueryColumn1", "testRangeSlicesQueryColumn2");
    QueryResult<OrderedRows<String, String, String>> r = q.execute();

    assertNotNull(r);
    OrderedRows<String, String, String> rows = r.get();
    assertNotNull(rows);

    assertEquals(3, rows.getCount());
    Row<String, String, String> row = rows.getList().get(0);
    assertNotNull(row);
    assertEquals("testRangeSlicesQuery1", row.getKey());
    ColumnSlice<String, String> slice = row.getColumnSlice();
    assertNotNull(slice);
    // Test slice.getColumnByName
    assertEquals("value11", slice.getColumnByName("testRangeSlicesQueryColumn1").getValue());
    assertEquals("value12", slice.getColumnByName("testRangeSlicesQueryColumn2").getValue());
    assertNull(slice.getColumnByName("testRangeSlicesQueryColumn3"));
    // Test slice.getColumns
    List<HColumn<String, String>> columns = slice.getColumns();
    assertNotNull(columns);
    assertEquals(2, columns.size());

    // now try with setKeys in combination with setRange
    q.setKeys("testRangeSlicesQuery1", "testRangeSlicesQuery5");
    q.setRange("testRangeSlicesQueryColumn1", "testRangeSlicesQueryColumn3", false, 100);
    r = q.execute();
    assertNotNull(r);
    rows = r.get();
    assertEquals(3, rows.getCount());
    for (Row<String, String, String> row2 : rows) {
      assertNotNull(row2);
      slice = row2.getColumnSlice();
      assertNotNull(slice);
      assertEquals(2, slice.getColumns().size());
      for (HColumn<String, String> column : slice.getColumns()) {
        if (!column.getName().equals("testRangeSlicesQueryColumn1")
            && !column.getName().equals("testRangeSlicesQueryColumn2")) {
          fail("A columns with unexpected column name returned: " + column.getName());
        }
      }

    }

    // Delete values
    deleteColumns(cleanup);
  }

  @Test
  public void testRangeSuperSlicesQuery() {
    String cf = "Super1";

    TestCleanupDescriptor cleanup = insertSuperColumns(cf, 4, "testRangeSuperSlicesQuery", 3,
        "testRangeSuperSlicesQuery");

    // get value
    RangeSuperSlicesQuery<String, String,String, String> q = createRangeSuperSlicesQuery(ko, se, se, se, se);
    q.setColumnFamily(cf);
    q.setKeys("testRangeSuperSlicesQuery2", "testRangeSuperSlicesQuery3");
    // try with column name first
    q.setColumnNames("testRangeSuperSlicesQuery1", "testRangeSuperSlicesQuery2");
    QueryResult<OrderedSuperRows<String, String, String, String>> r = q.execute();
    assertNotNull(r);
    OrderedSuperRows<String, String, String, String> rows = r.get();
    assertNotNull(rows);
    assertEquals(2, rows.getCount());
    SuperRow<String, String, String, String> row = rows.getList().get(0);
    assertNotNull(row);
    assertEquals("testRangeSuperSlicesQuery2", row.getKey());
    SuperSlice<String, String, String> slice = row.getSuperSlice();
    assertNotNull(slice);
    // Test slice.getColumnByName
    assertEquals("v021", slice.getColumnByName("testRangeSuperSlicesQuery1").get(0).getValue());
    assertEquals("v022", slice.getColumnByName("testRangeSuperSlicesQuery2").get(0).getValue());
    assertNull(slice.getColumnByName("testRangeSuperSlicesQuery3"));

    // now try with setKeys in combination with setRange
    q.setKeys("testRangeSuperSlicesQuery0", "testRangeSuperSlicesQuery5");
    q.setRange("testRangeSuperSlicesQuery1", "testRangeSuperSlicesQuery3", false, 100);
    r = q.execute();
    assertNotNull(r);
    rows = r.get();
    assertEquals(4, rows.getCount());
    for (SuperRow<String, String, String, String> row2 : rows) {
      assertNotNull(row2);
      slice = row2.getSuperSlice();
      assertNotNull(slice);
      assertEquals(2, slice.getSuperColumns().size());
      for (HSuperColumn<String, String, String> column : slice.getSuperColumns()) {
        if (!column.getName().equals("testRangeSuperSlicesQuery1")
            && !column.getName().equals("testRangeSuperSlicesQuery2")) {
          fail("A columns with unexpected column name returned: " + column.getName());
        }
      }
    }

    // Delete values
    deleteColumns(cleanup);
  }

  @Test
   public void testRangeSubSlicesQuery() {
    String cf = "Super1";

    TestCleanupDescriptor cleanup = insertSuperColumns(cf, 4, "testRangeSubSlicesQuery", 3,
        "testRangeSubSlicesQuery");

    // get value
    RangeSubSlicesQuery<String, String,String, String> q = createRangeSubSlicesQuery(ko, se, se, se, se);
    q.setColumnFamily(cf);
    q.setKeys("testRangeSubSlicesQuery2", "testRangeSubSlicesQuery3");
    // try with column name first
    q.setSuperColumn("testRangeSubSlicesQuery1");
    q.setColumnNames("c021", "c111");
    QueryResult<OrderedRows<String, String, String>> r = q.execute();
    assertNotNull(r);
    OrderedRows<String, String, String> rows = r.get();
    assertNotNull(rows);
    assertEquals(2, rows.getCount());
    Row<String, String, String> row = rows.getList().get(0);
    assertNotNull(row);
    assertEquals("testRangeSubSlicesQuery2", row.getKey());
    ColumnSlice<String, String> slice = row.getColumnSlice();
    assertNotNull(slice);
    // Test slice.getColumnByName
    assertEquals("v021", slice.getColumnByName("c021").getValue());
    assertEquals("v121", slice.getColumnByName("c111").getValue());
    assertNull(slice.getColumnByName("c033"));

    // Delete values
    deleteColumns(cleanup);
  }

  @Test
  public void testCountQuery() {
    String cf = "Standard1";

    TestCleanupDescriptor cleanup = insertColumns(cf, 1, "testCountQuery", 10,
        "testCountQueryColumn");
    CountQuery<String, String> cq = createCountQuery(ko, se, se);
    cq.setColumnFamily(cf).setKey("testCountQuery0");
    cq.setRange("testCountQueryColumn", "testCountQueryColumn999", 100);
    QueryResult<Integer> r = cq.execute();
    assertNotNull(r);
    assertEquals(Integer.valueOf(10), r.get());

    // Delete values
    deleteColumns(cleanup);

    // Try a non existing row, make sure it gets 0 (not exceptions)
    cq = createCountQuery(ko, se, se);
    cq.setColumnFamily(cf).setKey("testCountQuery_nonexisting");
    cq.setRange("testCountQueryColumn", "testCountQueryColumn999", 100);
    r = cq.execute();
    assertNotNull(r);
    assertEquals(Integer.valueOf(0), r.get());
}


  @Test
  public void testSuperCountQuery() {
    String cf = "Super1";

    TestCleanupDescriptor cleanup = insertSuperColumns(cf, 1, "testSuperCountQuery", 11,
        "testSuperCountQueryColumn");
    SuperCountQuery<String, String> cq = createSuperCountQuery(ko, se, se);
    cq.setColumnFamily(cf).setKey("testSuperCountQuery0");
    cq.setRange("testSuperCountQueryColumn", "testSuperCountQueryColumn999", 100);
    QueryResult<Integer> r = cq.execute();
    assertNotNull(r);
    assertEquals(Integer.valueOf(11), r.get());

    // Delete values
    deleteColumns(cleanup);
  }

  @Test
  public void testSubCountQuery() {
    String cf = "Super1";

    TestCleanupDescriptor cleanup = insertSuperColumns(cf, 1, "testSubCountQuery", 1,
        "testSubCountQueryColumn");
    SubCountQuery<String, String, String> cq = createSubCountQuery(ko, se, se, se);
    cq.setRange("c0", "c3", 100);
    QueryResult<Integer> r = cq.setColumnFamily(cf).setKey("testSubCountQuery0").
        setSuperColumn("testSubCountQueryColumn0").execute();
    assertNotNull(r);
    assertEquals(Integer.valueOf(2), r.get());

    // Delete values
    deleteColumns(cleanup);
  }

  private void deleteColumns(TestCleanupDescriptor cleanup) {
    Mutator<String> m = createMutator(ko, se);
    for (int i = 0; i < cleanup.rowCount; ++i) {
      for (int j = 0; j < cleanup.columnCount; ++j) {
        m.addDeletion(cleanup.rowPrefix + i, cleanup.cf, cleanup.columnsPrefix + j, se);
      }
    }
    m.execute();
  }

  private TestCleanupDescriptor insertSuperColumns(String cf, int rowCount, String rowPrefix,
      int scCount, String scPrefix) {
    Mutator<String> m = createMutator(ko, se);
    for (int i = 0; i < rowCount; ++i) {
      for (int j = 0; j < scCount; ++j) {
        @SuppressWarnings("unchecked")
        HSuperColumn<String, String, String> sc = createSuperColumn(
            scPrefix + j,
            Arrays.asList(createColumn("c0" + i + j, "v0" + i + j, se, se),
                createColumn("c1" + 1 + j, "v1" + i + j, se, se)), se, se, se);
        m.addInsertion(rowPrefix + i, cf, sc);
      }
    }
    m.execute();
    return new TestCleanupDescriptor(cf, rowCount, rowPrefix, scCount, scPrefix);
  }

  private TestCleanupDescriptor insertColumns(String cf, int rowCount, String rowPrefix,
      int columnCount, String columnPrefix) {
    Mutator<String> m = createMutator(ko, se);
    for (int i = 0; i < rowCount; ++i) {
      for (int j = 0; j < columnCount; ++j) {
        m.addInsertion(rowPrefix + i, cf, createColumn(columnPrefix + j, "value" + i + j, se, se));
      }
    }
    MutationResult mr = m.execute();
    assertTrue("Time should be > 0", mr.getExecutionTimeMicro() > 0);
    log.debug("insert execution time: {}", mr.getExecutionTimeMicro());
    log.debug(mr.toString());
    return new TestCleanupDescriptor(cf, rowCount, rowPrefix, columnCount, columnPrefix);
  }

  /**
   * A class describing what kind of cleanup is required at the end of the test. Just some
   * bookeeping, that's all.
   *
   * @author Ran Tavory
   *
   */
  private static class TestCleanupDescriptor {
    public final String cf;
    public final int rowCount;
    public final String rowPrefix;
    public final int columnCount;
    public final String columnsPrefix;

    public TestCleanupDescriptor(String cf, int rowCount, String rowPrefix, int scCount,
        String scPrefix) {
      this.cf = cf;
      this.rowCount = rowCount;
      this.rowPrefix = rowPrefix;
      this.columnCount = scCount;
      this.columnsPrefix = scPrefix;
    }
  }
}
