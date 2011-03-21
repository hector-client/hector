package me.prettyprint.cassandra.service.template;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import me.prettyprint.hector.api.factory.HFactory;

import org.junit.Test;

public class ColumnFamilyTemplateTest extends BaseColumnFamilyTemplateTest {
  
  @Test
  public void testCreateSelect() {
    ColumnFamilyTemplate<String, String> template = new ColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se, HFactory.createMutator(keyspace, se));
        
    ColumnFamilyUpdater updater = template.createUpdater("key1"); 
    updater.setString("column1","value1");
    template.update(updater);
    
    template.addColumn("column1", se);
    ColumnFamilyResultWrapper wrapper = template.queryColumns("key1");    
    assertEquals("value1",wrapper.getString("column1"));
        
  }

  @Test
  public void testCreateSelectTemplate() {
    ColumnFamilyTemplate<String, String> template = new ColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se, HFactory.createMutator(keyspace, se));
    template.update("key1", new ColumnFamilyUpdater<String, String>() {      
      @Override
      public void update() {
        setString("column1", "value1");        
      }
    });
    
    String value = template.queryColumns("key1", "", "", new ColumnFamilyRowMapper<String, String, String>() {
      @Override
      public String mapRow(ColumnFamilyResult<String, String> results) {
        // TODO Auto-generated method stub
        return results.getString("column1");
      }
    });
    assertEquals("value1",value);           
  }
  
  @Test
  public void testQueryMultiget() {
    ColumnFamilyTemplate<String, String> template = new ColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se, HFactory.createMutator(keyspace, se));    
    ColumnFamilyUpdater updater = template.createUpdater("mg_key1"); 
    updater.setString("column1","value1");
    updater.addKey("mg_key2");
    updater.setString("column1","value2");
    updater.addKey("mg_key3");
    updater.setString("column1","value3");
    template.update(updater);
    
    template.addColumn("column1", se);
    ColumnFamilyResultWrapper wrapper = template.queryColumns(Arrays.asList("mg_key1", "mg_key2", "mg_key3"));
    assertEquals("value1",wrapper.getString("column1"));    
    wrapper.next();
    assertEquals("value2",wrapper.getString("column1"));
    wrapper.next();
    assertEquals("value3",wrapper.getString("column1"));
  }

}
