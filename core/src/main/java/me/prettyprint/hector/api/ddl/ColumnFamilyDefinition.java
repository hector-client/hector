package me.prettyprint.hector.api.ddl;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

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

  /**
   * Sets the type alias for the comparator to be used for the row keys of the column family.
   * For composite types, supply the alias in the following format:
   * {@code (TypeName1, TypeName2, ...)}.
   * @param alias An alias String defining the comparator to be used for the row keys.
   * @see <a href="http://www.datastax.com/docs/1.1/ddl/column_family#about-data-types-comparators-and-validators">DataStax column family reference</a>
   */
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

	String getKeyValidationAlias();
	void setKeyValidationAlias(String keyValidationAlias);
	
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

  String getCompactionStrategy();
  void setCompactionStrategy(String strategy);

  Map<String,String> getCompactionStrategyOptions();
  void setCompactionStrategyOptions(Map<String,String> compactionStrategyOptions);

  Map<String,String> getCompressionOptions();
  void setCompressionOptions(Map<String,String> compressionOptions);

  double getMergeShardsChance();

  void setMergeShardsChance(double mergeShardsChance);

  String getRowCacheProvider();

  void setRowCacheProvider(String rowCacheProvider);

  ByteBuffer getKeyAlias();

  void setKeyAlias(ByteBuffer keyAlias);

  int getRowCacheKeysToSave();

  void setRowCacheKeysToSave(int rowCacheKeysToSave);
}
