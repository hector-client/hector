package me.prettyprint.cassandra.model;

import java.util.HashMap;
import java.util.Map;
import me.prettyprint.cassandra.service.OperationType;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configurable and Runtime adjustable ConsistencyLevelPolicy
 * @author zznate
 */
public class ConfigurableConsistencyLevel implements ConsistencyLevelPolicy {
  private final Logger log = LoggerFactory.getLogger(ConfigurableConsistencyLevel.class);

  private Map<String, HConsistencyLevel> readCfConsistencyLevels = new HashMap<String, HConsistencyLevel>();
  private Map<String, HConsistencyLevel> writeCfConsistencyLevels = new HashMap<String, HConsistencyLevel>();
  private HConsistencyLevel defaultReadConsistencyLevel = HConsistencyLevel.QUORUM;
  private HConsistencyLevel defaultWriteConsistencyLevel = HConsistencyLevel.QUORUM;

  @Override
  public HConsistencyLevel get(OperationType op) {
    return (op == OperationType.READ) ? defaultReadConsistencyLevel : defaultWriteConsistencyLevel;
  }

  @Override
  public HConsistencyLevel get(OperationType op, String cfName) {
    if (op == OperationType.READ) {
      HConsistencyLevel rcf = readCfConsistencyLevels.get(cfName);
      return rcf != null ? rcf : defaultReadConsistencyLevel;
    } else {
      HConsistencyLevel wcf = writeCfConsistencyLevels.get(cfName);
      return wcf != null ? wcf : defaultWriteConsistencyLevel;
    }
  }

  public void setReadCfConsistencyLevels(Map<String, HConsistencyLevel> columnFamilyConsistencyLevels) {
    this.readCfConsistencyLevels = columnFamilyConsistencyLevels;
    log.info("Read ColumnFamily ConsistencyLevels set to: {}", columnFamilyConsistencyLevels);
  }

  public void setWriteCfConsistencyLevels(Map<String, HConsistencyLevel> columnFamilyConsistencyLevels) {
    this.writeCfConsistencyLevels = columnFamilyConsistencyLevels;
    log.info("Write ColumnFamily ConsistencyLevels set to: {}", columnFamilyConsistencyLevels);
  }

  public void setConsistencyLevelForCfOperation(HConsistencyLevel consistencyLevel,
      String columnFamily,
      OperationType operationType) {
    if (operationType == OperationType.READ) {
      readCfConsistencyLevels.put(columnFamily, consistencyLevel);
    } else {
      writeCfConsistencyLevels.put(columnFamily, consistencyLevel);
    }
    log.info("{} ConsistencyLevel set to {} for ColumnFamily {}",
        new Object[]{operationType.toString(),consistencyLevel.toString(),columnFamily});
  }

  public void setDefaultReadConsistencyLevel(HConsistencyLevel defaultReadConsistencyLevel) {
    this.defaultReadConsistencyLevel = defaultReadConsistencyLevel;
    log.info("Default read ConsistencyLevel set to: {}", defaultReadConsistencyLevel.toString() + ".");
  }

  public void setDefaultWriteConsistencyLevel(HConsistencyLevel defaultWriteConsistencyLevel) {
    this.defaultWriteConsistencyLevel = defaultWriteConsistencyLevel;
    log.info("Default write ConsistencyLevel set to: {}", defaultWriteConsistencyLevel.toString() + ".");
  }



}
