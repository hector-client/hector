package me.prettyprint.cassandra.service.template;

import static org.junit.Assert.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import org.junit.Test;


public class SuperCfTemplateTest extends BaseColumnFamilyTemplateTest {

	public void afterTest() {
		cluster.truncate(keyspace.getKeyspaceName(), "Super1");
	}
	
  @Test
  public void testSuperCfInsertReadTemplate() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater<String,String,String> sUpdater = sTemplate.createUpdater("skey1","super1");
    sUpdater.setString("sub_col_1", "sub_val_1");
    sTemplate.update(sUpdater);

    SuperCfResult<String,String,String> result = sTemplate.querySuperColumn("skey1", "super1");
    
    assertEquals("sub_val_1",result.getString("super1","sub_col_1"));
    
    sUpdater.deleteSuperColumn();
    sTemplate.update(sUpdater);
    assertEquals("super1",sUpdater.getCurrentSuperColumn());
    result = sTemplate.querySuperColumn("skey1", "super1");
    assertFalse(result.hasResults());
  }
  
  
  @Test
  public void testSuperCfMultiSc() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater<String,String,String> sUpdater = sTemplate.createUpdater("skey2","super1");
    sUpdater.setString("sub1_col_1", "sub1_val_1");
    sUpdater.addSuperColumn("super2");
    sUpdater.setString("sub2_col_1", "sub2_val_1");
    sTemplate.update(sUpdater);

    SuperCfResult<String,String,String> result = sTemplate.querySuperColumns("skey2", Arrays.asList("super1","super2"));
    assertEquals(2,result.getSuperColumns().size());
    /*for (String sName : result.getSuperColumns() ) {
      result.getString(sName,"sub1_col_1");
    }*/
    
    //assertEquals("sub1_val_1",result.getString("sub1_col_1"));
    //assertEquals("sub2_val_1",result.next().getString("sub2_col_1"));
    
  }
  
  @Test
  public void testQuerySingleSubColumn() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater<String,String,String> sUpdater = sTemplate.createUpdater("skey3","super1");
    sUpdater.setString("sub1_col_1", "sub1_val_1");
    sTemplate.update(sUpdater);
    
    HColumn<String,String> myCol = sTemplate.querySingleSubColumn("skey3", "super1", "sub1_col_1", se);
    assertEquals("sub1_val_1", myCol.getValue());
  }
  
  @Test
  public void testQuerySingleSubColumnExtractSuper() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater<String,String,String> sUpdater = sTemplate.createUpdater("skey3","super1");
    sUpdater.setString("sub1_col_1", "sub1_val_1");
    sUpdater.setString("sub1_col_2", "sub1_val_2");
    sTemplate.update(sUpdater);
    
    SuperCfResult<String, String, String> result = sTemplate.querySuperColumns("skey3");
    HSuperColumn<String, String, ByteBuffer> superColumn = result.getSuperColumn("super1");
    assertNotNull(superColumn);
    assertEquals("super1",superColumn.getName());
    assertEquals(2,superColumn.getColumns().size());
  }
  
  @Test
  public void testQuerySingleSubColumnEmpty() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater<String,String,String> sUpdater = sTemplate.createUpdater("skey3","super1");
    sUpdater.setString("sub1_col_1", "sub1_val_1");
    sTemplate.update(sUpdater);
    
    HColumn<String,String> myCol = sTemplate.querySingleSubColumn("skey3", "super2", "sub1_col_1", se);
    assertNull(myCol);
  }
  
  @Test
  public void testSuperCfInsertReadMultiKey() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater<String,String,String> sUpdater = sTemplate.createUpdater("s_multi_key1","super1");
    sUpdater.setString("sub_col_1", "sub_val_1");
    sUpdater.addKey("s_multi_key2");
    sUpdater.addSuperColumn("super1");
    sUpdater.setString("sub_col_1", "sub_val_2");
    sTemplate.update(sUpdater);

    SuperCfResult<String,String,String> result = sTemplate.querySuperColumns(Arrays.asList("s_multi_key1","s_multi_key2"), Arrays.asList("super1"));
    assertTrue(result.hasResults());
    assertEquals("sub_val_2",result.getString("super1","sub_col_1"));
    assertEquals("sub_val_1",result.next().getString("super1","sub_col_1"));
    
  }
  
  @Test
  public void testSuperCfInsertReadMultiKeyNoSc() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater<String,String,String> sUpdater = sTemplate.createUpdater("s_multi_key1","super1");
    sUpdater.setString("sub_col_1", "sub_val_1");
    sUpdater.addKey("s_multi_key2");
    sUpdater.addSuperColumn("super1");
    sUpdater.setString("sub_col_1", "sub_val_2");
    sTemplate.update(sUpdater);

    SuperCfResult<String,String,String> result = sTemplate.querySuperColumns(Arrays.asList("s_multi_key1","s_multi_key2"));
    assertTrue(result.hasResults());
    assertEquals("sub_val_2",result.getString("super1","sub_col_1"));
    assertEquals("sub_val_1",result.next().getString("super1","sub_col_1"));
    
  }

  @Test
  public void testSuperCfKeyOnly() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater<String,String,String> sUpdater = sTemplate.createUpdater("skey1","super1");
    sUpdater.setString("sub_col_1", "sub_val_1");
    sUpdater.addSuperColumn("super2");
    sUpdater.setString("sub_col_1", "sub_val_2");
    sTemplate.update(sUpdater);
    
    SuperCfResult<String,String,String> result = sTemplate.querySuperColumns("skey1");        
    assertEquals(2, result.getSuperColumns().size());
    assertTrue(result.hasResults());
    result = sTemplate.querySuperColumns("skey1-non-existing-key");
    assertNull(result.getActiveSuperColumn());
  }
  
  @Test
  public void testSuperCfNoResults() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    
    assertFalse(sTemplate.querySuperColumns("no_results").hasResults());
  }
  
  @Test
  public void testDeleteSubColumns() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater<String,String,String> sUpdater = sTemplate.createUpdater("skey3","super1");
    sUpdater.setString("sub1_col_1", "sub1_val_1");
    sUpdater.setString("sub1_col_2", "sub1_val_2");
    sUpdater.setString("sub1_col_3", "sub1_val_3");
    sTemplate.update(sUpdater);
    
    SuperCfResult<String,String,String> result = sTemplate.querySuperColumn("skey3","super1");
    assertEquals(3, result.getColumnNames().size());
    
    sUpdater.deleteSubColumn("sub1_col_1");
    sTemplate.update(sUpdater);
    
    result = sTemplate.querySuperColumn("skey3","super1");
    assertEquals(2, result.getColumnNames().size());
  }
  
  @Test
  public void testTemplateLevelDeleteSuper() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater<String,String,String> sUpdater = sTemplate.createUpdater("skey_del_super","super1");
    sUpdater.setString("sub1_col_1", "sub1_val_1");
    sTemplate.update(sUpdater);
    
    SuperCfResult<String,String,String> result = sTemplate.querySuperColumn("skey_del_super","super1");
    assertEquals(1, result.getColumnNames().size());
    
    sTemplate.deleteColumn("skey_del_super", "super1");        
    
    result = sTemplate.querySuperColumn("skey_del_super","super1");
    assertFalse(result.hasResults());
    assertEquals(0, result.getColumnNames().size());
  }
  
  @Test
  public void testTemplateLevelDeleteRow() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater<String,String,String> sUpdater = sTemplate.createUpdater("skey_row_del","super1");
    sUpdater.setString("sub1_col_1", "sub1_val_1");
    sTemplate.update(sUpdater);
    
    SuperCfResult<String,String,String> result = sTemplate.querySuperColumn("skey_row_del","super1");
    assertEquals(1, result.getColumnNames().size());
    
    sTemplate.deleteRow("skey_row_del");        
    
    result = sTemplate.querySuperColumns("skey_row_del");
    assertFalse(result.hasResults());
    assertEquals(0, result.getSuperColumns().size());
  }
  
  @Test
  public void testTemplateLevelDeleteMiss() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater<String,String,String> sUpdater = sTemplate.createUpdater("skey_row_del_miss","super1");
    sUpdater.setString("sub1_col_1", "sub1_val_1");
    sTemplate.update(sUpdater);
    
    SuperCfResult<String,String,String> result = sTemplate.querySuperColumn("skey_row_del_miss","super1");
    assertEquals(1, result.getColumnNames().size());
    
    sTemplate.deleteRow("skey_row_miss_foo");  
    sTemplate.deleteColumn("skey_row_del", "foo");
    
    result = sTemplate.querySuperColumns("skey_row_del_miss");
    assertTrue(result.hasResults());
    assertEquals(1, result.getSuperColumns().size());
  }
  
  @Test
  public void testSuperCfColumnValueTypes() {
    final float EPSILON = 0.0000001f;

    SuperCfTemplate<String, String, String> sTemplate =
      new ThriftSuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    SuperCfUpdater<String,String,String> sUpdater = sTemplate.createUpdater("skey1","super1");
    sUpdater.setString("subcolumn1","string value");
    sUpdater.setUUID("subcolumn2", UUID.fromString("cf316d50-f7c0-11e1-a21f-0800200c9a66"));
    sUpdater.setLong("subcolumn3", 829398278497234L);
    sUpdater.setInteger("subcolumn4", 27344);
    sUpdater.setFloat("subcolumn5",3.14159265f);
    sUpdater.setDouble("subcolumn6",3.14159265358979323846264338327950288);
    sUpdater.setBoolean("subcolumn7", true);
    sUpdater.setByteArray("subcolumn8", new byte[] {97, 91, 99});
    final Date date = new Date();
    sUpdater.setDate("subcolumn9", date);

    sTemplate.update(sUpdater);

    SuperCfResult<String,String,String> result = sTemplate.querySuperColumn("skey1", "super1");
    assertEquals("string value",result.getString("subcolumn1"));
    assertEquals(UUID.fromString("cf316d50-f7c0-11e1-a21f-0800200c9a66"), result.getUUID("subcolumn2"));
    assertEquals(Long.valueOf(829398278497234L), result.getLong("subcolumn3"));
    assertEquals(Integer.valueOf(27344), result.getInteger("subcolumn4"));
    assertEquals(3.14159265f,result.getFloat("subcolumn5"), EPSILON);
    assertEquals(3.14159265358979323846264338327950288, result.getDouble("subcolumn6"), EPSILON);
    assertEquals(true, result.getBoolean("subcolumn7"));
    assertArrayEquals(new byte[] {97, 91, 99}, result.getByteArray("subcolumn8"));
    assertEquals(date, result.getDate("subcolumn9"));
  }
}
