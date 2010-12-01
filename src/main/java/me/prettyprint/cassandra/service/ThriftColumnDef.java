package me.prettyprint.cassandra.service;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.ddl.ColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnIndexType;

import org.apache.cassandra.thrift.ColumnDef;
import org.apache.cassandra.thrift.IndexType;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ThriftColumnDef implements ColumnDefinition {

  private final ByteBuffer name; //TODO(ran): use a serializer and type safety?
  private final String validationClass;
  private final ColumnIndexType indexType;
  private final String indexName;

  public ThriftColumnDef(ColumnDef cd) {
    Assert.notNull(cd, "ColumnDef is null");
    name = cd.name;
    validationClass = cd.validation_class;
    indexType = indexTypeFromThrift(cd.index_type);
    indexName = cd.index_name;
  }
  
  public ThriftColumnDef(ColumnDefinition columnDefinition) {
    name = columnDefinition.getName();
    validationClass = columnDefinition.getValidationClass();
    indexType = columnDefinition.getIndexType();
    indexName = columnDefinition.getIndexName();
  }

  private ColumnIndexType indexTypeFromThrift(IndexType tIndexType) {
    switch (tIndexType) {
    case KEYS:
      return ColumnIndexType.KEYS;
    default:
      throw new RuntimeException("Unknown thrift IndexType: " + tIndexType);
    }
  }

  public static List<ColumnDefinition> fromThriftList(List<ColumnDef> columnDefs) {
    if (columnDefs == null || columnDefs.isEmpty()) {
      return Collections.emptyList();
    }
    List<ColumnDefinition> l = new ArrayList<ColumnDefinition>(columnDefs.size());
    for (ColumnDef cd: columnDefs) {
      l.add(new ThriftColumnDef(cd));
    }
    return l;
  }

  @Override
  public ByteBuffer getName() {
    return name;
  }

  @Override
  public String getValidationClass() {
    return validationClass;
  }

  @Override
  public ColumnIndexType getIndexType() {
    return indexType;
  }

  @Override
  public String getIndexName() {
    return indexName;
  }

  public static List<ColumnDef> toThriftList(List<ColumnDefinition> columnDefs) {
    if (columnDefs == null || columnDefs.isEmpty()) {
      return Collections.emptyList();
    }
    List<ColumnDef> l = new ArrayList<ColumnDef>(columnDefs.size());
    for (ColumnDefinition d: columnDefs) {
      l.add((new ThriftColumnDef(d)).toThrift());
    }
    return l;
  }

  private ColumnDef toThrift() {
    ColumnDef d = new ColumnDef();
    d.setIndex_name(indexName);
    d.setIndex_type(indexTypeToThrift(indexType));
    d.setName(name);
    d.setValidation_class(validationClass);
    return d;
  }

  private IndexType indexTypeToThrift(ColumnIndexType indexType2) {
    switch (indexType2) {
    case KEYS:
      return IndexType.KEYS;
    default:
      throw new RuntimeException("Unknown ColumnIndexType value: " + indexType2);
    }
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
