package me.prettyprint.cassandra.service.template;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SuperCfTemplateTest extends BaseColumnFamilyTemplateTest {

  @Test
  public void testSuperCfInsertReadTemplate() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new SuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
  }
  
  @Test
  public void testSuperCfInsertRead() {
    SuperCfTemplate<String, String, String> sTemplate = 
      new SuperCfTemplate<String, String, String>(keyspace, "Super1", se, se, se);
    
  }
}
