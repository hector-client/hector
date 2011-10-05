package me.prettyprint.cassandra.model;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.exceptions.HCassandraInternalException;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.cassandra.utils.ByteBufferUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CqlQueryTest extends BaseEmbededServerSetupTest {
  
  private final static String KEYSPACE = "Keyspace1";
  private static final StringSerializer se = new StringSerializer();
  private static final LongSerializer le = new LongSerializer();
  private Cluster cluster;
  private Keyspace keyspace;
  private String cf = "StandardLong1";
  
  @Before
  public void setupCase() {
    cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
    keyspace = createKeyspace(KEYSPACE, cluster);
    createMutator(keyspace, se)
    .addInsertion("cqlQueryTest_key1", cf, createColumn("birthyear", 1974L, se, le))
    .addInsertion("cqlQueryTest_key1", cf, createColumn("birthmonth", 4L, se, le))
    .addInsertion("cqlQueryTest_key2", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("cqlQueryTest_key2", cf, createColumn("birthmonth", 4L, se, le))
    .addInsertion("cqlQueryTest_key3", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("cqlQueryTest_key3", cf, createColumn("birthmonth", 5L, se, le))
    .addInsertion("cqlQueryTest_key4", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("cqlQueryTest_key4", cf, createColumn("birthmonth", 6L, se, le))
    .addInsertion("cqlQueryTest_key5", cf, createColumn("birthyear", 1975L, se, le))
    .addInsertion("cqlQueryTest_key5", cf, createColumn("birthmonth", 7L, se, le))
    .addInsertion("cqlQueryTest_key6", cf, createColumn("birthyear", 1976L, se, le))
    .addInsertion("cqlQueryTest_key6", cf, createColumn("birthmonth", 6L, se, le))
    .execute();
  }
  
  @Test
  public void testSimpleSelect() {
    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    cqlQuery.setQuery("select * from StandardLong1");
    QueryResult<CqlRows<String,String,Long>> result = cqlQuery.execute();
    assertEquals(6,result.get().getCount());
    
  }
  
  @Test
  @Ignore
  public void testCountQuery() {
    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    cqlQuery.setQuery("SELECT COUNT(*) FROM StandardLong1 WHERE KEY = 'cqlQueryTest_key1'");
    QueryResult<CqlRows<String,String,Long>> result = cqlQuery.execute();
    assertEquals(2, result.get().getAsCount());
  }

  @Test(expected=HCassandraInternalException.class)
  public void testSyntaxFailQuery() {
    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    cqlQuery.setQuery("SELECT COUNT(*) FROM Standard1 WHERE KEY = 'cqlQueryTest_key1'");
    QueryResult<CqlRows<String,String,Long>> result = cqlQuery.execute();

  }
  
  @Test
  public void testInsertSyntax() {
    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    cqlQuery.setQuery("update StandardLong1 set 'birthyear' = '1976' WHERE KEY = 'cqlQueryTest_key7'");
    QueryResult<CqlRows<String,String,Long>> result = cqlQuery.execute();
  }
  
  @Test
  public void testInsertSyntaxHex() {
    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    String query = String.format("update Standard1 set '%s' = '%s' WHERE KEY = '%s'",
        ByteBufferUtil.bytesToHex(se.toByteBuffer("birthyear")),
        ByteBufferUtil.bytesToHex(se.toByteBuffer("1976")),
        ByteBufferUtil.bytesToHex(se.toByteBuffer("mykey1")));
    cqlQuery.setQuery(query);
    QueryResult<CqlRows<String,String,Long>> result = cqlQuery.execute();
  }
}
