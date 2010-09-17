package me.prettyprint.cassandra.model;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import me.prettyprint.hector.api.ConsistencyLevelPolicy.OperationType;

import org.apache.cassandra.thrift.ConsistencyLevel;
import org.junit.Before;
import org.junit.Test;


public class ConfigurableConsistencyLevelTest {
  
  private ConfigurableConsistencyLevel configurableConsistencyLevel;
  private Map<String, ConsistencyLevel> clmap;
  
  @Before
  public void setup() {
    configurableConsistencyLevel = new ConfigurableConsistencyLevel();
    clmap = new HashMap<String, ConsistencyLevel>();
    clmap.put("MyColumnFamily", ConsistencyLevel.ONE);
    configurableConsistencyLevel.setReadCfConsistencyLevels(clmap);
    configurableConsistencyLevel.setWriteCfConsistencyLevels(clmap);    
  }
  
  @Test
  public void testReadWriteSame() {
    assertEquals(ConsistencyLevel.ONE,configurableConsistencyLevel.get(OperationType.READ, "MyColumnFamily"));
  }
  
  @Test
  public void testDefaults() {
    configurableConsistencyLevel.setDefaultWriteConsistencyLevel(ConsistencyLevel.ALL);
    configurableConsistencyLevel.setWriteCfConsistencyLevels(new HashMap<String, ConsistencyLevel>());
    assertEquals(ConsistencyLevel.ALL,configurableConsistencyLevel.get(OperationType.WRITE, "MyColumnFamily"));
  }
  
  @Test
  public void testSetRuntimeCl() {
    configurableConsistencyLevel.setConsistencyLevelForCfOperation(ConsistencyLevel.ANY, "OtherCf", OperationType.READ);
    assertEquals(ConsistencyLevel.ANY, configurableConsistencyLevel.get(OperationType.READ, "OtherCf"));
  }
}
