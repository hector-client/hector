package me.prettyprint.cassandra.model;

import me.prettyprint.hector.api.ddl.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: peter
 * Date: 20/10/2010
 * Time: 4:53:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicColumnFamilyDefinition implements ColumnFamilyDefinition {


  private String keyspaceName;
  private String name;
  private ColumnType columnType;
  private ComparatorType comparitorType;
  private ComparatorType subComparitorType;
  private String comment;
  private double rowCacheSize;
  private boolean preloadRowCache;
  private double keyCacheSize;
  private double readRepairChance;
  private int gcGraceSeconds;
  private String defaultValidationClass;
  private int id;
  private int maxCompactionThreshold;
  private int minCompactionThreshold;

  private List<ColumnDefinition> columnDefinitions;


  public BasicColumnFamilyDefinition() {
    this.columnDefinitions = new ArrayList<ColumnDefinition>();
  }

  public void setKeyspaceName(String keyspaceName) {
    this.keyspaceName = keyspaceName;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setColumnType(ColumnType columnType) {
    this.columnType = columnType;
  }

  public void setComparitorType(ComparatorType comparitorType) {
    this.comparitorType = comparitorType;
  }

  public void setSubComparitorType(ComparatorType subComparitorType) {
    this.subComparitorType = subComparitorType;
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

  public void addColumnDefinition( ColumnDefinition columnDefinition){
    this.columnDefinitions.add( columnDefinition );
  }

  /**
   * SHOULD THIS BE HERE? A COLUMN DEFINITION IS PART OF A KEYSPACE BY VIRTUE
   * OF BEING IN A KEYSPACE LIST
   */
  @Override
  public String getKeyspaceName() {
    return this.keyspaceName;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public ColumnType getColumnType() {
    return this.columnType;
  }

  @Override
  public ComparatorType getComparatorType() {
    return this.comparitorType;
  }

  @Override
  public ComparatorType getSubcomparatorType() {
    return this.subComparitorType;
  }

  @Override
  public String getComment() {
    return this.comment;
  }

  @Override
  public double getRowCacheSize() {
    return this.rowCacheSize;
  }

  @Override
  public boolean isPreloadRowCache() {
    return this.preloadRowCache;
  }

  @Override
  public double getKeyCacheSize() {
    return this.keyCacheSize;
  }

  @Override
  public double getReadRepairChance() {
    return this.readRepairChance;
  }

  @Override
  public List<ColumnDefinition> getColumnMetadata() {
    return this.columnDefinitions;
  }

  @Override
  public int getGcGraceSeconds() {
    return this.gcGraceSeconds;
  }

  @Override
  public String getDefaultValidationClass() {
    return this.defaultValidationClass;
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public int getMaxCompactionThreshold() {
    return this.maxCompactionThreshold;
  }

  @Override
  public int getMinCompactionThreshold() {
    return this.minCompactionThreshold;
  }


}
