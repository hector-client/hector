package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.ddl.ColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ColumnType;
import me.prettyprint.hector.api.ddl.ComparatorType;

import org.apache.cassandra.thrift.CfDef;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ThriftCfDef implements ColumnFamilyDefinition {

  private final String keyspace;
  private final String name;
  private ColumnType columnType;
  private ComparatorType comparatorType;
  private ComparatorType subComparatorType;
  private String comment;
  private double rowCacheSize;
  private int rowCacheSavePeriodInSeconds;
  private double keyCacheSize;
  private double readRepairChance;
  private List<ColumnDefinition> columnMetadata;
  private int gcGraceSeconds;
  private String defaultValidationClass;
  private int id;
  private int maxCompactionThreshold;
  private int minCompactionThreshold;

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
    readRepairChance = d.read_repair_chance;
    columnMetadata = ThriftColumnDef.fromThriftList(d.column_metadata);
    gcGraceSeconds = d.gc_grace_seconds;
    defaultValidationClass = d.default_validation_class;
    id = d.id;
    minCompactionThreshold = d.min_compaction_threshold;
    maxCompactionThreshold = d.max_compaction_threshold;

  }
  
  public ThriftCfDef(ColumnFamilyDefinition columnFamilyDefinition) {
    keyspace = columnFamilyDefinition.getKeyspaceName();
    name = columnFamilyDefinition.getName();
    columnType = columnFamilyDefinition.getColumnType();
    comparatorType = columnFamilyDefinition.getComparatorType();
    subComparatorType = columnFamilyDefinition.getSubComparatorType();
    comment = columnFamilyDefinition.getComment();
    rowCacheSize = columnFamilyDefinition.getRowCacheSize();
    rowCacheSavePeriodInSeconds = columnFamilyDefinition.getRowCacheSavePeriodInSeconds();
    keyCacheSize = columnFamilyDefinition.getKeyCacheSize();
    readRepairChance = columnFamilyDefinition.getReadRepairChance();
    columnMetadata = columnFamilyDefinition.getColumnMetadata();
    gcGraceSeconds = columnFamilyDefinition.getGcGraceSeconds();
    defaultValidationClass = columnFamilyDefinition.getDefaultValidationClass();
    id = columnFamilyDefinition.getId();
    minCompactionThreshold = columnFamilyDefinition.getMinCompactionThreshold();
    maxCompactionThreshold = columnFamilyDefinition.getMaxCompactionThreshold();
  }

  public ThriftCfDef(String keyspace, String columnFamilyName) {
    this.keyspace = keyspace;
    name = columnFamilyName;
    columnMetadata = Collections.emptyList();

    columnType = ColumnType.STANDARD;
    comparatorType = ComparatorType.BYTESTYPE;
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
    d.setComparator_type(comparatorType.getClassName());
    d.setDefault_validation_class(defaultValidationClass);
    d.setGc_grace_seconds(gcGraceSeconds);
    d.setId(id);
    d.setKey_cache_size(keyCacheSize);
    d.setMax_compaction_threshold(maxCompactionThreshold);
    d.setMin_compaction_threshold(minCompactionThreshold);
    d.setRead_repair_chance(readRepairChance);
    d.setRow_cache_size(rowCacheSize);
    if (subComparatorType != null) {
      d.setSubcomparator_type(subComparatorType.getClassName());
    }
    return d;
  }

  @Override
  public String getDefaultValidationClass() {
    return defaultValidationClass;
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

  public void setGcGraceSeconds(int gcGraceSeconds) {
    this.gcGraceSeconds = gcGraceSeconds;
  }

  public void setDefaultValidationClass(String defaultValidationClass) {
    this.defaultValidationClass = defaultValidationClass;
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
}
