package me.prettyprint.cassandra.model;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.query.QueryResult;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.junit.Before;
import org.junit.Test;

public class CqlQueryTest extends BaseEmbededServerSetupTest {
    
  private final static String KEYSPACE = "Keyspace1";
  private static final StringSerializer se = new StringSerializer();
  private static final LongSerializer le = new LongSerializer();
  private static int customColumns = 0;
  private Cluster cluster;
  private Keyspace keyspace;
  private String cf = "StandardLong1";
  
  @Before
  public void setupCase() {
    cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
    keyspace = createKeyspace(KEYSPACE, cluster);
    createMutator(keyspace, se)
    .addInsertion("cqlQueryTest_key1", cf, createColumn("birthyear", 1971L, se, le))
    .addInsertion("cqlQueryTest_key1", cf, createColumn("birthmonth", 1L, se, le))
    .addInsertion("cqlQueryTest_key2", cf, createColumn("birthyear", 1972L, se, le))
    .addInsertion("cqlQueryTest_key2", cf, createColumn("birthmonth", 2L, se, le))
    .addInsertion("cqlQueryTest_key3", cf, createColumn("birthyear", 1973L, se, le))
    .addInsertion("cqlQueryTest_key3", cf, createColumn("birthmonth", 3L, se, le))
    .addInsertion("cqlQueryTest_key4", cf, createColumn("birthyear", 1974L, se, le))
    .addInsertion("cqlQueryTest_key4", cf, createColumn("birthmonth", 4L, se, le))
    .addInsertion("cqlQueryTest_key5", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("cqlQueryTest_key5", cf, createColumn("birthmonth", 5L, se, le))
    .addInsertion("cqlQueryTest_key6", cf, createColumn("birthyear", 1976L, se, le))
    .addInsertion("cqlQueryTest_key6", cf, createColumn("birthmonth", 6L, se, le))
    .execute();
  }
  
  @Test
  public void testSimpleSelect() {
    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    cqlQuery.setQuery("select * from StandardLong1");
    QueryResult<CqlRows<String,String,Long>> result = cqlQuery.execute();
    CqlRows<String, String, Long> rows = result.get();
    
    // check that we contain a 'key' column
    assertNotNull(rows.getList().get(0).getColumnSlice().getColumnByName("KEY"));
    assertEquals(6 + customColumns,rows.getCount());    
  }

   @Test
  public void testSimpleSelect20() {
    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    cqlQuery.setQuery("select birth .. birthzz from StandardLong1");
    cqlQuery.setCqlVersion("2.0.0");
    QueryResult<CqlRows<String,String,Long>> result = cqlQuery.execute();
    CqlRows<String, String, Long> rows = result.get();
    assertEquals(6 + customColumns,rows.getCount());    
  }

  @Test
  public void testSelectAllSuppressesKeyColumn() {
    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    cqlQuery.setQuery("select * from StandardLong1");
    cqlQuery.setSuppressKeyInColumns(true);

    QueryResult<CqlRows<String,String,Long>> result = cqlQuery.execute();
    CqlRows<String, String, Long> rows = result.get();
    // check that we contain a 'key' column
    assertNull(rows.getList().get(0).getColumnSlice().getColumnByName("KEY"));
    // arbitrary row check
    assertNull(rows.getList().get(3).getColumnSlice().getColumnByName("KEY"));
    assertEquals(6,rows.getCount());    
  }
  
  @Test
  public void testCountQuery() {
    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    cqlQuery.setQuery("SELECT COUNT(*) FROM StandardLong1 WHERE KEY in ('cqlQueryTest_key1', 'cqlQueryTest_key2')");
    QueryResult<CqlRows<String,String,Long>> result = cqlQuery.execute();
    assertEquals(2, result.get().getAsCount());
  }

  @Test(expected=HInvalidRequestException.class)
  public void testSyntaxFailQuery() {
    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    cqlQuery.setQuery("SELECT COUNT(*) FROM Standard1 WHERE KEY = 'cqlQueryTest_key1'");
    cqlQuery.execute();

  }
  
  @Test
  public void testInsertSyntax() {
    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    cqlQuery.setQuery("update StandardLong1 set 'birthyear' = '1977' WHERE KEY = 'cqlQueryTest_key7'");
    cqlQuery.execute();
    customColumns++;
  }
  
  @Test
  public void testInsertSyntaxHex() {
    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    String query = String.format("update Standard1 set '%s' = '%s' WHERE KEY = '%s'",
        ByteBufferUtil.bytesToHex(se.toByteBuffer("birthyear")),
        ByteBufferUtil.bytesToHex(se.toByteBuffer("1976")),
        ByteBufferUtil.bytesToHex(se.toByteBuffer("mykey1")));
    cqlQuery.setQuery(query);
    cqlQuery.execute();
  }
}
