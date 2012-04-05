package me.prettyprint.cassandra.service.template;

import java.util.Arrays;
import java.util.Date;

import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import org.apache.cassandra.thrift.IndexOperator;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ColumnFamilyTemplateTest extends BaseColumnFamilyTemplateTest {
  
  @Test
  public void testCreateSelect() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se);
        
    ColumnFamilyUpdater<String,String> updater = template.createUpdater("key1"); 
    updater.setString("column1","value1");
    template.update(updater);
    
    template.addColumn("column1", se);
    ColumnFamilyResult<String,String> wrapper = template.queryColumns("key1");    
    assertEquals("value1",wrapper.getString("column1"));
        
  }
  
  @Test
  public void testCreateSelectMultiColumn() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se);
    
    ColumnFamilyUpdater<String,String> updater = template.createUpdater("cskey1"); 
    updater.setString("stringval","value1");
    Date date = new Date();
    updater.setDate("curdate", date);
    updater.setLong("longval", 5L);
    template.update(updater);
    
    template.addColumn("stringval", se);
    template.addColumn("curdate", DateSerializer.get());
    template.addColumn("longval", LongSerializer.get());
    ColumnFamilyResult<String,String> wrapper = template.queryColumns("cskey1");    
    assertEquals("value1",wrapper.getString("stringval"));
    assertEquals(date,wrapper.getDate("curdate"));
    assertEquals(new Long(5),wrapper.getLong("longval"));
    assertEquals(3,wrapper.getColumnNames().size());
  }

    @Test
  public void testCompareClocks() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se);
    long ts1 = 1001;
    long ts2 = 1002;
    long ts3 = 1003;


    ColumnFamilyUpdater<String,String> updater = template.createUpdater("compare_clock_key1");
    updater.setClock(ts1);
    updater.setString("stringval","value1");
    Date date = new Date();
    updater.setClock(ts2);
    updater.setDate("curdate", date);
    updater.setClock(ts3);
    updater.setLong("longval", 5L);
    template.update(updater);
    template.addColumn("stringval", se);
    template.addColumn("curdate", DateSerializer.get());
    template.addColumn("longval", LongSerializer.get());

    ColumnFamilyResult wrapper = template.queryColumns("compare_clock_key1");
    assertEquals(ts1,wrapper.getColumn("stringval").getClock());
    assertEquals(ts2,wrapper.getColumn("curdate").getClock());
    assertEquals(ts3,wrapper.getColumn("longval").getClock());
    assertEquals(3,wrapper.getColumnNames().size());
  }


  @Test
  public void testCreateSelectTemplate() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se);
    ColumnFamilyUpdater<String,String> updater = template.createUpdater("key1"); 
    updater.setString("column1","value1");
    template.update(updater);
    template.setCount(10);
    String value = template.queryColumns("key1", new ColumnFamilyRowMapper<String, String, String>() {
      @Override
      public String mapRow(ColumnFamilyResult<String, String> results) {
        // TODO Auto-generated method stub
        return results.getString("column1");
      }
    });
    assertEquals("value1",value);           
  }
  
  @Test
  public void testOverloadedMapRowCallback() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se);
    ColumnFamilyUpdater<String,String> updater = template.createUpdater("key1"); 
    updater.setString("column1","value1");
    updater.addKey("key2");
    updater.setString("column1", "value2");
    template.update(updater);
    template.setCount(10);
    MappedColumnFamilyResult<String,String,String> result = template.queryColumns(Arrays.asList("key1","key2"), new ColumnFamilyRowMapper<String, String, String>() {
      @Override
      public String mapRow(ColumnFamilyResult<String, String> results) {

        return results.getString("column1");
      }
    });
    assertEquals("key1",result.getKey());
    assertEquals("value1", result.getRow());
    result.next();
    assertEquals("key2",result.getKey());
    assertEquals("value2", result.getRow());
    
    
  }
  
  @Test
  public void testQueryMultiget() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se);    
    ColumnFamilyUpdater<String,String> updater = template.createUpdater("mg_key1"); 
    updater.setString("column1","value1");
    updater.addKey("mg_key2");
    updater.setString("column1","value2");
    updater.addKey("mg_key3");
    updater.setString("column1","value3");
    template.update(updater);
    
    template.addColumn("column1", se);
    ColumnFamilyResult<String,String> wrapper = template.queryColumns(Arrays.asList("mg_key1", "mg_key2", "mg_key3"));
    assertEquals("value1",wrapper.getString("column1"));    
    wrapper.next();
    assertEquals("value2",wrapper.getString("column1"));
    wrapper.next();
    assertEquals("value3",wrapper.getString("column1"));
  }
  
  @Test
  public void testHasNoResults() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se);    
    assertFalse(template.queryColumns("noresults").hasResults());
    assertFalse(template.queryColumns("noresults").hasNext());
    assertEquals("noresults",template.queryColumns("noresults").getKey());
  }

  @Test
  public void testGetKeyTwiceCall() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se);    
    ColumnFamilyResult<String, String> results = template.queryColumns("noresults");    
    assertEquals("noresults",results.getKey());
    assertEquals("noresults",results.getKey());
    assertEquals("noresults",results.getKey());        
  }
  @Test
  public void testQueryIndexedSlices() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Indexed1", se, se);    
    ColumnFamilyUpdater<String,String> updater = template.createUpdater("index_key1"); 
    updater.setLong("birthyear", 1974L);
    updater.setLong("birthmonth", 4L);
    updater.addKey("index_key2");
    updater.setLong("birthyear", 1975L);
    updater.setLong("birthmonth", 4L);
    updater.addKey("index_key3");
    updater.setLong("birthyear", 1975L);
    updater.setLong("birthmonth", 5L);
    updater.addKey("index_key4");
    updater.setLong("birthyear", 1975L);
    updater.setLong("birthmonth", 6L);
    updater.addKey("index_key5");
    updater.setLong("birthyear", 1975L);
    updater.setLong("birthmonth", 7L);
    updater.addKey("index_key6");
    updater.setLong("birthyear", 1976L);
    updater.setLong("birthmonth", 6L);
    template.update(updater);

    IndexedSlicesPredicate<String, String, Long> predicate = 
        new IndexedSlicesPredicate<String, String, Long>(se, se, LongSerializer.get());
    predicate.startKey("");
    predicate.addExpression("birthyear", IndexOperator.EQ, 1975L);

    ColumnFamilyResult<String, String> result = 
        template.queryColumns(predicate);
    int cnt = result.getColumnNames().size();
    while (result.hasNext()) {
      cnt += result.next().getColumnNames().size();
    }
    assertEquals(8, cnt);

    result = 
        template.queryColumns(predicate, Arrays.asList(new String[]{"birthmonth"}));
    cnt = result.getColumnNames().size();
    while (result.hasNext()) {
      cnt += result.next().getColumnNames().size();
    }
    assertEquals(4, cnt);
  }
}
