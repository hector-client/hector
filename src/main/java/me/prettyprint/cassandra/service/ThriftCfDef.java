package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.ddl.HCfDef;
import me.prettyprint.hector.api.ddl.HColumnDef;

import org.apache.cassandra.thrift.CfDef;

public class ThriftCfDef implements HCfDef {

  private final String keyspace;
  private final String name;
  private String columnType;
  private String comparatorType;
  private String subcomparatorType;
  private String comment;
  private double rowCacheSize;
  private boolean preloadRowCache;
  private double keyCacheSize;
  private double readRepairChance;
  private List<HColumnDef> columnMetadata;
  private int gcGraceSeconds;
  private String defaultValidationClass;
  private int id;
  private int maxCompactionThreshold;
  private int minCompactionThreshold;

  public ThriftCfDef(CfDef d) {
    Assert.notNull(d, "CfDef is null");
    keyspace = d.keyspace;
    name = d.name;
    columnType = d.column_type;
    comparatorType = d.comparator_type;
    subcomparatorType = d.subcomparator_type;
    comment = d.comment;
    rowCacheSize = d.row_cache_size;
    preloadRowCache = d.preload_row_cache;
    keyCacheSize = d.key_cache_size;
    readRepairChance = d.read_repair_chance;
    columnMetadata = ThriftColumnDef.fromThriftList(d.column_metadata);
    gcGraceSeconds = d.gc_grace_seconds;
    defaultValidationClass = d.default_validation_class;
    id = d.id;
    minCompactionThreshold = d.min_compaction_threshold;
    maxCompactionThreshold = d.max_compaction_threshold;

  }

  public ThriftCfDef(String keyspace, String columnFamilyName) {
    this.keyspace = keyspace;
    this.name = columnFamilyName;
    columnMetadata = Collections.emptyList();
  }

  public static List<HCfDef> fromThriftList(List<CfDef> cfDefs) {
    if (cfDefs == null || cfDefs.isEmpty()) {
      return Collections.emptyList();
    }
    List<HCfDef> l = new ArrayList<HCfDef>(cfDefs.size());
    for (CfDef d: cfDefs) {
      l.add(new ThriftCfDef(d));
    }
    return l;
  }

  @Override
  public String getKeyspace() {
    return keyspace;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getColumnType() {
    return columnType;
  }

  @Override
  public String getComparatorType() {
    return comparatorType;
  }

  @Override
  public String getSubcomparatorType() {
    return subcomparatorType;
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
  public boolean isPreloadRowCache() {
    return preloadRowCache;
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
  public List<HColumnDef> getColumnMetadata() {
    return columnMetadata;
  }

  @Override
  public int getGcGraceSeconds() {
    return gcGraceSeconds;
  }

  public static List<CfDef> toThriftList(List<HCfDef> cfDefs) {
    if (cfDefs == null || cfDefs.isEmpty()) {
      return Collections.emptyList();
    }
    List<CfDef> l = new ArrayList<CfDef>(cfDefs.size());
    for (HCfDef d: cfDefs) {
      l.add(((ThriftCfDef) d).toThrift());
    }
    return l;
  }

  public CfDef toThrift() {
    CfDef d = new CfDef(keyspace, name);
    d.column_metadata = ThriftColumnDef.toThriftList(columnMetadata);
    d.column_type = columnType;
    d.comment = comment;
    d.comparator_type = comparatorType;
    d.default_validation_class = defaultValidationClass;
    d.gc_grace_seconds = gcGraceSeconds;
    d.id = id;
    d.key_cache_size = keyCacheSize;
    d.max_compaction_threshold = maxCompactionThreshold;
    d.min_compaction_threshold = minCompactionThreshold;
    d.preload_row_cache = preloadRowCache;
    d.read_repair_chance = readRepairChance;
    d.row_cache_size = rowCacheSize;
    d.subcomparator_type = subcomparatorType;
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

  public void setColumnType(String columnType) {
    this.columnType = columnType;
  }

  public void setComparatorType(String comparatorType) {
    this.comparatorType = comparatorType;
  }

  public void setSubcomparatorType(String subcomparatorType) {
    this.subcomparatorType = subcomparatorType;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public void setRowCacheSize(double rowCacheSize) {
    this.rowCacheSize = rowCacheSize;
  }

  public void setPreloadRowCache(boolean preloadRowCache) {
    this.preloadRowCache = preloadRowCache;
  }

  public void setKeyCacheSize(double keyCacheSize) {
    this.keyCacheSize = keyCacheSize;
  }

  public void setReadRepairChance(double readRepairChance) {
    this.readRepairChance = readRepairChance;
  }

  public void setColumnMetadata(List<HColumnDef> columnMetadata) {
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
}
