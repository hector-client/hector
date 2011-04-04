package me.prettyprint.cassandra.service.template;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;


public class SuperCfTemplateTest extends BaseColumnFamilyTemplateTest {

  @Test
  public void testSuperCfInsertReadTemplate() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater sUpdater = sTemplate.createUpdater("skey1","super1");
    sUpdater.setString("sub_col_1", "sub_val_1");
    sTemplate.update(sUpdater);

    SuperCfResult<String,String,String> result = sTemplate.querySuperColumn("skey1", "super1");
    assertEquals("sub_val_1",result.getString("sub_col_1"));
  }
  
  @Test
  public void testSuperCfMultiSc() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater sUpdater = sTemplate.createUpdater("skey2","super1");
    sUpdater.setString("sub1_col_1", "sub1_val_1");
    //sUpdater.addSuperColumn("super2");
    //sUpdater.setString("sub2_col_1", "sub2_val_1");
    sTemplate.update(sUpdater);

    SuperCfResult<String,String,String> result = sTemplate.querySuperColumns("skey2", Arrays.asList("super1"));
    //assertEquals("sub_val_1",result.getString("sub_col_1"));
  }
  
}
