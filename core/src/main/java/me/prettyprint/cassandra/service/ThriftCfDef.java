package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.ddl.ColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ColumnType;
import me.prettyprint.hector.api.ddl.ComparatorType;

import me.prettyprint.cassandra.constants.CFMetaDataDefaults;
import org.apache.cassandra.thrift.CfDef;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ThriftCfDef implements ColumnFamilyDefinition {

  private String keyspace;
  private String name;
  private ColumnType columnType;
  private ComparatorType comparatorType;
  private ComparatorType subComparatorType;
	private String comparatorTypeAlias = "";
	private String subComparatorTypeAlias = "";
  private String comment;
  private double rowCacheSize;
  private int rowCacheSavePeriodInSeconds;
  private double keyCacheSize;
  private double readRepairChance;
  private List<ColumnDefinition> columnMetadata;
  private int gcGraceSeconds;
  private String keyValidationClass;
  private String defaultValidationClass;
  private int id;
  private int maxCompactionThreshold;
  private int minCompactionThreshold;
  private double memtableOperationsInMillions;
  private int memtableThroughputInMb;
  private int memtableFlushAfterMins;
  private int keyCacheSavePeriodInSeconds;
  private boolean replicateOnWrite;

  public ThriftCfDef(CfDef d) {
    Assert.notNull(d, "CfDef is null");
    keyspace = d.keyspace;
    name = d.name;
    columnType = ColumnType.getFromValue(d.column_type);
    comparatorType = ComparatorType.getByClassName(d.comparator_type);
    subComparatorType = ComparatorType.getByClassName(d.subcomparator_type);
    comment = d.comment;
    rowCacheSize = d.row_cache_size;
    rowCacheSavePeriodInSeconds = d.row_cache_save_period_in_seconds;
    keyCacheSize = d.key_cache_size;
    keyCacheSavePeriodInSeconds = d.key_cache_save_period_in_seconds;
    keyValidationClass = d.key_validation_class;
    readRepairChance = d.read_repair_chance;
    columnMetadata = ThriftColumnDef.fromThriftList(d.column_metadata);
    gcGraceSeconds = d.gc_grace_seconds;
    defaultValidationClass = d.default_validation_class;
    id = d.id;
    minCompactionThreshold = d.min_compaction_threshold == 0 ?
        CFMetaDataDefaults.DEFAULT_MIN_COMPACTION_THRESHOLD : d.min_compaction_threshold;
    maxCompactionThreshold = d.max_compaction_threshold == 0 ?
        CFMetaDataDefaults.DEFAULT_MAX_COMPACTION_THRESHOLD : d.max_compaction_threshold;
    memtableOperationsInMillions = d.memtable_operations_in_millions == 0 ?
        CFMetaDataDefaults.DEFAULT_MEMTABLE_OPERATIONS_IN_MILLIONS : d.memtable_operations_in_millions;
    memtableFlushAfterMins = d.memtable_flush_after_mins == 0 ?
        CFMetaDataDefaults.DEFAULT_MEMTABLE_LIFETIME_IN_MINS : d.memtable_flush_after_mins;
    memtableThroughputInMb = d.memtable_throughput_in_mb == 0 ?
        CFMetaDataDefaults.DEFAULT_MEMTABLE_THROUGHPUT_IN_MB : d.memtable_throughput_in_mb;

    replicateOnWrite = d.replicate_on_write;
  }

  public ThriftCfDef(ColumnFamilyDefinition columnFamilyDefinition) {
    keyspace = columnFamilyDefinition.getKeyspaceName();
    name = columnFamilyDefinition.getName();
    columnType = columnFamilyDefinition.getColumnType();
    comparatorType = columnFamilyDefinition.getComparatorType();
    subComparatorType = columnFamilyDefinition.getSubComparatorType();
		comparatorTypeAlias = columnFamilyDefinition.getComparatorTypeAlias();
		subComparatorTypeAlias = columnFamilyDefinition.getSubComparatorTypeAlias();
    comment = columnFamilyDefinition.getComment();
    rowCacheSize = columnFamilyDefinition.getRowCacheSize();
    rowCacheSavePeriodInSeconds = columnFamilyDefinition.getRowCacheSavePeriodInSeconds();
    keyCacheSize = columnFamilyDefinition.getKeyCacheSize();
    keyCacheSavePeriodInSeconds = columnFamilyDefinition.getKeyCacheSavePeriodInSeconds();
    keyValidationClass = columnFamilyDefinition.getKeyValidationClass();
    readRepairChance = columnFamilyDefinition.getReadRepairChance();
    columnMetadata = columnFamilyDefinition.getColumnMetadata();
    gcGraceSeconds = columnFamilyDefinition.getGcGraceSeconds();
    defaultValidationClass = columnFamilyDefinition.getDefaultValidationClass();
    id = columnFamilyDefinition.getId();
    minCompactionThreshold = columnFamilyDefinition.getMinCompactionThreshold() == 0 ?
        CFMetaDataDefaults.DEFAULT_MIN_COMPACTION_THRESHOLD : columnFamilyDefinition.getMinCompactionThreshold();
    maxCompactionThreshold = columnFamilyDefinition.getMaxCompactionThreshold() == 0 ?
        CFMetaDataDefaults.DEFAULT_MAX_COMPACTION_THRESHOLD : columnFamilyDefinition.getMaxCompactionThreshold();
    memtableFlushAfterMins = columnFamilyDefinition.getMemtableFlushAfterMins() == 0 ?
        CFMetaDataDefaults.DEFAULT_MEMTABLE_LIFETIME_IN_MINS : columnFamilyDefinition.getMemtableFlushAfterMins();
    memtableThroughputInMb = columnFamilyDefinition.getMemtableThroughputInMb() == 0 ?
        CFMetaDataDefaults.DEFAULT_MEMTABLE_THROUGHPUT_IN_MB : columnFamilyDefinition.getMemtableThroughputInMb();
    memtableOperationsInMillions = columnFamilyDefinition.getMemtableOperationsInMillions() == 0 ?
        CFMetaDataDefaults.DEFAULT_MEMTABLE_OPERATIONS_IN_MILLIONS : columnFamilyDefinition.getMemtableOperationsInMillions();
    replicateOnWrite = columnFamilyDefinition.isReplicateOnWrite();
  }

  public ThriftCfDef(String keyspace, String columnFamilyName) {
    this.keyspace = keyspace;
    name = columnFamilyName;
    columnMetadata = Collections.emptyList();

    columnType = ColumnType.STANDARD;
    comparatorType = ComparatorType.BYTESTYPE;
    readRepairChance = CFMetaDataDefaults.DEFAULT_READ_REPAIR_CHANCE;
    keyCacheSize = CFMetaDataDefaults.DEFAULT_KEY_CACHE_SIZE;
    keyCacheSavePeriodInSeconds = CFMetaDataDefaults.DEFAULT_KEY_CACHE_SAVE_PERIOD_IN_SECONDS;
    gcGraceSeconds = CFMetaDataDefaults.DEFAULT_GC_GRACE_SECONDS;
    minCompactionThreshold = CFMetaDataDefaults.DEFAULT_MIN_COMPACTION_THRESHOLD;
    maxCompactionThreshold = CFMetaDataDefaults.DEFAULT_MAX_COMPACTION_THRESHOLD;
    memtableFlushAfterMins = CFMetaDataDefaults.DEFAULT_MEMTABLE_LIFETIME_IN_MINS;
    memtableThroughputInMb = CFMetaDataDefaults.DEFAULT_MEMTABLE_THROUGHPUT_IN_MB;
    memtableOperationsInMillions = CFMetaDataDefaults.DEFAULT_MEMTABLE_OPERATIONS_IN_MILLIONS;
    replicateOnWrite = CFMetaDataDefaults.DEFAULT_REPLICATE_ON_WRITE;
  }

  public ThriftCfDef(String keyspace, String columnFamilyName, ComparatorType comparatorType) {
    this(keyspace, columnFamilyName);
    if (comparatorType != null) {
      this.comparatorType = comparatorType;
    }
  }

  public ThriftCfDef(String keyspace, String columnFamilyName, ComparatorType comparatorType, List<ColumnDefinition> columnMetadata) {
    this(keyspace, columnFamilyName, comparatorType);
    if (columnMetadata != null) {
      this.columnMetadata = columnMetadata;
    }
  }

  public static List<ColumnFamilyDefinition> fromThriftList(List<CfDef> cfDefs) {
    if ((cfDefs == null) || cfDefs.isEmpty()) {
      return Collections.emptyList();
    }
    List<ColumnFamilyDefinition> l = new ArrayList<ColumnFamilyDefinition>(cfDefs.size());
    for (CfDef d: cfDefs) {
      l.add(new ThriftCfDef(d));
    }
    return l;
  }

  @Override
  public String getKeyspaceName() {
    return keyspace;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public ColumnType getColumnType() {
    return columnType;
  }

  @Override
  public ComparatorType getComparatorType() {
    return comparatorType;
  }

  @Override
  public ComparatorType getSubComparatorType() {
    return subComparatorType;
  }

	public String getComparatorTypeAlias() { return this.comparatorTypeAlias; }

	public String getSubComparatorTypeAlias() { return this.subComparatorTypeAlias; }

  @Override
  public String getComment() {
    return comment;
  }

  @Override
  public double getRowCacheSize() {
    return rowCacheSize;
  }

  @Override
  public int getRowCacheSavePeriodInSeconds() {
    return rowCacheSavePeriodInSeconds;
  }

  @Override
  public double getKeyCacheSize() {
    return keyCacheSize;
  }

  @Override
  public double getReadRepairChance() {
    return readRepairChance;
  }

  @Override
  public List<ColumnDefinition> getColumnMetadata() {
    return columnMetadata;
  }

  @Override
  public int getGcGraceSeconds() {
    return gcGraceSeconds;
  }

  public static List<CfDef> toThriftList(List<ColumnFamilyDefinition> cfDefs) {
    if ((cfDefs == null) || cfDefs.isEmpty()) {
      return Collections.emptyList();
    }
    List<CfDef> l = new ArrayList<CfDef>(cfDefs.size());
    for (ColumnFamilyDefinition d: cfDefs) {
      l.add(((ThriftCfDef) d).toThrift());
    }
    return l;
  }

  public CfDef toThrift() {
    CfDef d = new CfDef(keyspace, name);
    d.setColumn_metadata(ThriftColumnDef.toThriftList(columnMetadata));
    d.setColumn_type(columnType.getValue());
    d.setComment(comment);
    d.setComparator_type(comparatorType.getClassName() + comparatorTypeAlias);
    d.setDefault_validation_class(defaultValidationClass);
    d.setGc_grace_seconds(gcGraceSeconds);
    d.setId(id);
    d.setKey_cache_size(keyCacheSize);
    d.setKey_cache_save_period_in_seconds(keyCacheSavePeriodInSeconds);
    d.setKey_validation_class(keyValidationClass);
    d.setMax_compaction_threshold(maxCompactionThreshold);
    d.setMin_compaction_threshold(minCompactionThreshold);
    d.setRead_repair_chance(readRepairChance);
    d.setRow_cache_size(rowCacheSize);
    d.setMemtable_operations_in_millions(memtableOperationsInMillions);
    d.setMemtable_throughput_in_mb(memtableThroughputInMb);
    d.setMemtable_flush_after_mins(memtableFlushAfterMins);        
    d.setReplicate_on_write(replicateOnWrite);

    if (subComparatorType != null) {
      d.setSubcomparator_type(subComparatorType.getClassName() + subComparatorTypeAlias);
    }
    return d;
  }

  @Override
  public String getDefaultValidationClass() {
    return defaultValidationClass;
  }

  @Override
  public String getKeyValidationClass(){
      return keyValidationClass;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public int getMaxCompactionThreshold() {
    return maxCompactionThreshold;
  }

  @Override
  public int getMinCompactionThreshold() {
    return minCompactionThreshold;
  }

  public void setColumnType(ColumnType columnType) {
    this.columnType = columnType;
  }

  public void setComparatorType(ComparatorType comparatorType) {
    this.comparatorType = comparatorType;
  }

  public void setSubComparatorType(ComparatorType subComparatorType) {
    this.subComparatorType = subComparatorType;
  }

	public void setComparatorTypeAlias(String alias) { this.comparatorTypeAlias = alias; }

	public void setSubComparatorTypeAlias(String alias) { this.subComparatorTypeAlias = alias; }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public void setRowCacheSize(double rowCacheSize) {
    this.rowCacheSize = rowCacheSize;
  }

  public void setRowCacheSavePeriodInSeconds(int rowCacheSavePeriodInSeconds) {
    this.rowCacheSavePeriodInSeconds = rowCacheSavePeriodInSeconds;
  }

  public void setKeyCacheSize(double keyCacheSize) {
    this.keyCacheSize = keyCacheSize;
  }

  public void setReadRepairChance(double readRepairChance) {
    this.readRepairChance = readRepairChance;
  }

  public void setColumnMetadata(List<ColumnDefinition> columnMetadata) {
    this.columnMetadata = columnMetadata;
  }
  
  @Override
  public void addColumnDefinition(ColumnDefinition columnDefinition) {
    this.columnMetadata.add(columnDefinition);
  }

  public void setGcGraceSeconds(int gcGraceSeconds) {
    this.gcGraceSeconds = gcGraceSeconds;
  }

  public void setDefaultValidationClass(String defaultValidationClass) {
    this.defaultValidationClass = defaultValidationClass;
  }

  public void setKeyValidationClass(String keyValidationClass){
      this.keyValidationClass = keyValidationClass;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setMaxCompactionThreshold(int maxCompactionThreshold) {
    this.maxCompactionThreshold = maxCompactionThreshold;
  }

  public void setMinCompactionThreshold(int minCompactionThreshold) {
    this.minCompactionThreshold = minCompactionThreshold;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

  @Override
  public int getMemtableFlushAfterMins() {
    return memtableFlushAfterMins;
  }

  @Override
  public double getMemtableOperationsInMillions() {
    return memtableOperationsInMillions;
  }

  @Override
  public int getMemtableThroughputInMb() {
    return memtableThroughputInMb;
  }

  @Override
  public int getKeyCacheSavePeriodInSeconds() {
    return keyCacheSavePeriodInSeconds;
  }

  public void setMemtableOperationsInMillions(double memtableOperationsInMillions) {
    this.memtableOperationsInMillions = memtableOperationsInMillions;
  }

  public void setMemtableThroughputInMb(int memtableThroughputInMb) {
    this.memtableThroughputInMb = memtableThroughputInMb;
  }

  public void setMemtableFlushAfterMins(int memtableFlushAfterMins) {
    this.memtableFlushAfterMins = memtableFlushAfterMins;
  }

  public void setKeyCacheSavePeriodInSeconds(int keyCacheSavePeriodInSeconds) {
    this.keyCacheSavePeriodInSeconds = keyCacheSavePeriodInSeconds;
  }

  public boolean isReplicateOnWrite() {
    return replicateOnWrite;
  }

  public void setReplicateOnWrite(boolean replicateOnWrite) {
    this.replicateOnWrite = replicateOnWrite;
  }

  @Override
  public void setKeyspaceName(String keyspaceName) {
    this.keyspace = keyspaceName;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

}
