package me.prettyprint.cassandra.service.template;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;

import me.prettyprint.cassandra.model.HSlicePredicate;
import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.hector.api.factory.HFactory;

import org.junit.Test;

public class ColumnFamilyTemplateTest extends BaseColumnFamilyTemplateTest {
  
  @Test
  public void testCreateSelect() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se, HFactory.createMutator(keyspace, se));
        
    ColumnFamilyUpdater updater = template.createUpdater("key1"); 
    updater.setString("column1","value1");
    template.update(updater);
    
    template.addColumn("column1", se);
    ColumnFamilyResult wrapper = template.queryColumns("key1");    
    assertEquals("value1",wrapper.getString("column1"));
        
  }
  
  @Test
  public void testCreateSelectMultiColumn() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se, HFactory.createMutator(keyspace, se));
    
    ColumnFamilyUpdater<String,String> updater = template.createUpdater("cskey1"); 
    updater.setString("stringval","value1");
    Date date = new Date();
    updater.setDate("curdate", date);
    updater.setLong("longval", 5L);
    template.update(updater);
    
    template.addColumn("stringval", se);
    template.addColumn("curdate", DateSerializer.get());
    template.addColumn("longval", LongSerializer.get());
    ColumnFamilyResult wrapper = template.queryColumns("cskey1");    
    assertEquals("value1",wrapper.getString("stringval"));
    assertEquals(date,wrapper.getDate("curdate"));
    assertEquals(new Long(5),wrapper.getLong("longval"));
  }

  @Test
  public void testCreateSelectTemplate() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se, HFactory.createMutator(keyspace, se));
    ColumnFamilyUpdater updater = template.createUpdater("key1"); 
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
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se, HFactory.createMutator(keyspace, se));
    ColumnFamilyUpdater updater = template.createUpdater("key1"); 
    updater.setString("column1","value1");
    updater.addKey("key2");
    updater.setString("column1", "value2");
    template.update(updater);
    template.setCount(10);
    MappedColumnFamilyResult result = template.queryColumns(Arrays.asList("key1","key2"), new ColumnFamilyRowMapper<String, String, String>() {
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
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se, HFactory.createMutator(keyspace, se));    
    ColumnFamilyUpdater updater = template.createUpdater("mg_key1"); 
    updater.setString("column1","value1");
    updater.addKey("mg_key2");
    updater.setString("column1","value2");
    updater.addKey("mg_key3");
    updater.setString("column1","value3");
    template.update(updater);
    
    template.addColumn("column1", se);
    ColumnFamilyResult wrapper = template.queryColumns(Arrays.asList("mg_key1", "mg_key2", "mg_key3"));
    assertEquals("value1",wrapper.getString("column1"));    
    wrapper.next();
    assertEquals("value2",wrapper.getString("column1"));
    wrapper.next();
    assertEquals("value3",wrapper.getString("column1"));
  }
  
  @Test
  public void testHasNoResults() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se, HFactory.createMutator(keyspace, se));    
    assertFalse(template.queryColumns("noresults").hasResults());
    assertFalse(template.queryColumns("noresults").hasNext());
    assertEquals("noresults",template.queryColumns("noresults").getKey());
  }

  @Test
  public void testGetKeyTwiceCall() {
    ColumnFamilyTemplate<String, String> template = new ThriftColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se, HFactory.createMutator(keyspace, se));    
    ColumnFamilyResult<String, String> results = template.queryColumns("noresults");    
    assertEquals("noresults",results.getKey());
    assertEquals("noresults",results.getKey());
    assertEquals("noresults",results.getKey());        
  }
}
