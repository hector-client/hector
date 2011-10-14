package me.prettyprint.hector.api.ddl;

import java.util.List;

/**
 * Hector's implementation independent CfDef version.
 *
 * @author Ran Tavory
 *
 */
public interface ColumnFamilyDefinition {

  String getKeyspaceName();
  void setKeyspaceName(String keyspaceName);
  
  String getName();
  void setName(String name);
  
  ColumnType getColumnType();
  void setColumnType(ColumnType columnType);
  
  ComparatorType getComparatorType();
  void setComparatorType(ComparatorType comparitorType);
  
  ComparatorType getSubComparatorType();
  void setSubComparatorType(ComparatorType subComparitorType);

	String getComparatorTypeAlias();
	void setComparatorTypeAlias(String alias);

	String getSubComparatorTypeAlias();
	void setSubComparatorTypeAlias(String alias);

  String getComment();
  void setComment(String comment);
  
  double getRowCacheSize();
  void setRowCacheSize(double rowCacheSize);
  
  int getRowCacheSavePeriodInSeconds();
  void setRowCacheSavePeriodInSeconds(int rowCacheSavePeriodInSeconds);
  
  int getKeyCacheSavePeriodInSeconds();
  void setKeyCacheSavePeriodInSeconds(int keyCacheSavePeriodInSeconds);
  
  double getKeyCacheSize();
  void setKeyCacheSize(double keyCacheSize);
  
  String getKeyValidationClass();
  void setKeyValidationClass(String keyValidationClass);
  
  double getReadRepairChance();
  void setReadRepairChance(double readRepairChance);
  
  List<ColumnDefinition> getColumnMetadata();
  void addColumnDefinition( ColumnDefinition columnDefinition);
  
  int getGcGraceSeconds();
  void setGcGraceSeconds(int gcGraceSeconds);
  
  String getDefaultValidationClass();
  void setDefaultValidationClass(String defaultValidationClass);
  
  int getId();
  void setId(int id);
  
  int getMaxCompactionThreshold();
  void setMaxCompactionThreshold(int maxCompactionThreshold);
  
  int getMinCompactionThreshold();
  void setMinCompactionThreshold(int minCompactionThreshold);
  
  double getMemtableOperationsInMillions();
  void setMemtableOperationsInMillions(double memtableOperationsInMillions);
  
  int getMemtableThroughputInMb();
  void setMemtableThroughputInMb(int memtableThroughputInMb);
  
  int getMemtableFlushAfterMins();
  void setMemtableFlushAfterMins(int memtableFlushAfterMins);
  
  boolean isReplicateOnWrite();
  void setReplicateOnWrite(boolean replicateOnWrite);

}
