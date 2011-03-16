package me.prettyprint.cassandra.service.template;

import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ResultStatus;
import me.prettyprint.hector.api.factory.HFactory;

public class ColumnFamilyTemplateTest extends BaseEmbededServerSetupTest {
  
  private Keyspace keyspace;
  private static final StringSerializer se = StringSerializer.get();
  
  @Before
  public void setupLocal() {
    Cluster cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
    keyspace = createKeyspace("Keyspace1", cluster);
  }
  
  @Test
  public void testCreateSelect() {
    ColumnFamilyTemplate<String, String> template = new ColumnFamilyTemplate<String, String>(keyspace, "Standard1", se, se, HFactory.createMutator(keyspace, se));
    
    
    ColumnFamilyUpdater updater = template.createUpdater("key1"); // createStaticUpater()
    updater.setString("column1","value1");
    template.update(updater);
    /*
    template.update("key1", new ColumnFamilyUpdater<String, String>() {      
      @Override
      public void update() {
        setString("column1", "value1");        
      }
    });
    */
    template.addColumn("column1", se);
    ColumnFamilyResultWrapper wrapper = template.queryColumns("key1");
    
    assertEquals("value1",wrapper.getString("column1"));
    
/*    String value = template.queryColumns("key1", "", "", new ColumnFamilyRowMapper<String, String, String>() {
      @Override
      public String mapRow(ColumnFamilyResult<String, String> results) {
        // TODO Auto-generated method stub
        return results.getString("column1");
      }
    });
    assertEquals("value1",value);
    */
    
    
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

}
