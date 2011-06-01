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
  String getName();
  ColumnType getColumnType();
  ComparatorType getComparatorType();
  ComparatorType getSubComparatorType();
  String getComment();
  double getRowCacheSize();
  int getRowCacheSavePeriodInSeconds();
  int getKeyCacheSavePeriodInSeconds();
  double getKeyCacheSize();
  String getKeyValidationClass();
  double getReadRepairChance();
  List<ColumnDefinition> getColumnMetadata();
  int getGcGraceSeconds();
  String getDefaultValidationClass();
  int getId();
  int getMaxCompactionThreshold();
  int getMinCompactionThreshold();
  double getMemtableOperationsInMillions();
  int getMemtableThroughputInMb();
  int getMemtableFlushAfterMins();


}
