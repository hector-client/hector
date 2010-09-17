package me.prettyprint.cassandra.model;

import java.util.HashMap;
import java.util.Map;

import me.prettyprint.hector.api.ConsistencyLevelPolicy;

import org.apache.cassandra.thrift.ConsistencyLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configurable and Runtime adjustable ConsistencyLevelPolicy
 * @author zznate
 */
public class ConfigurableConsistencyLevel implements ConsistencyLevelPolicy {
  private Logger log = LoggerFactory.getLogger(ConfigurableConsistencyLevel.class);
  
  private Map<String, ConsistencyLevel> readCfConsistencyLevels = new HashMap<String, ConsistencyLevel>();
  private Map<String, ConsistencyLevel> writeCfConsistencyLevels = new HashMap<String, ConsistencyLevel>();
  private ConsistencyLevel defaultReadConsistencyLevel = ConsistencyLevel.QUORUM;
  private ConsistencyLevel defaultWriteConsistencyLevel = ConsistencyLevel.QUORUM;
  
  @Override
  public ConsistencyLevel get(OperationType op) {
    return op.equals(OperationType.READ) ? defaultReadConsistencyLevel : defaultWriteConsistencyLevel;
  }

  @Override
  public ConsistencyLevel get(OperationType op, String cfName) {
    if (op.equals(OperationType.READ)) {
      ConsistencyLevel rcf = readCfConsistencyLevels.get(cfName);
      return rcf != null ? rcf : defaultReadConsistencyLevel;
    } else {
      ConsistencyLevel wcf = writeCfConsistencyLevels.get(cfName);
      return wcf != null ? wcf : defaultWriteConsistencyLevel;
    }    
  }

  public void setReadCfConsistencyLevels(Map<String, ConsistencyLevel> columnFamilyConsistencyLevels) {
    this.readCfConsistencyLevels = columnFamilyConsistencyLevels;
  }

  public void setWriteCfConsistencyLevels(Map<String, ConsistencyLevel> columnFamilyConsistencyLevels) {
    this.writeCfConsistencyLevels = columnFamilyConsistencyLevels;
  }

  public void setConsistencyLevelForCfOperation(ConsistencyLevel consistencyLevel,
      String columnFamily,        
      OperationType operationType) {
    if ( operationType.equals(OperationType.READ)) {
      readCfConsistencyLevels.put(columnFamily, consistencyLevel);      
    } else {
      writeCfConsistencyLevels.put(columnFamily, consistencyLevel);
    }    
    log.info("{} ConsistencyLevel set to {} for ColumnFamily {}", 
        new Object[]{operationType.toString(),consistencyLevel.toString(),columnFamily});
  }

  public void setDefaultReadConsistencyLevel(ConsistencyLevel defaultReadConsistencyLevel) {
    this.defaultReadConsistencyLevel = defaultReadConsistencyLevel;
  }

  public void setDefaultWriteConsistencyLevel(ConsistencyLevel defaultWriteConsistencyLevel) {
    this.defaultWriteConsistencyLevel = defaultWriteConsistencyLevel;
  }
  
  
  
}
