package me.prettyprint.hector.api.ddl;

import java.util.List;

/**
 * Hector's implementation independent CfDef version.
 *
 * @author Ran Tavory
 *
 */
public interface ColumnFamilyDefinition {

  KeyspaceDefinition getKeyspace();
  String getName();
  ColumnType getColumnType();
  String getComparatorType();
  String getSubcomparatorType();
  String getComment();
  double getRowCacheSize();
  boolean isPreloadRowCache();
  double getKeyCacheSize();
  double getReadRepairChance();
  List<ColumnDefinition> getColumnMetadata();
  int getGcGraceSeconds();
  String getDefaultValidationClass();
  int getId();
  int getMaxCompactionThreshold();
  int getMinCompactionThreshold();


}
