package me.prettyprint.cassandra.service.template;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class SuperCfTemplateTest extends BaseColumnFamilyTemplateTest {

  @Test
  public void testSuperCfInsertReadTemplate() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new SuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    sTemplate.update("super_key_1", Arrays.asList("super_name_1"), new SuperCfUpdater<String, String, String, String>() {
      @Override
      public String update(String obj) {
        setString("sub_col_1", "sub_val_1");
        return obj;
      }      
    });
    
    assertEquals("sub_val_1",sTemplate.querySingleSubColumn("super_key_1", "super_name_1", "sub_col_1", se).getValue());
  }
  
  @Test
  public void testSuperCfInsertRead() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new SuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    
  }
}
