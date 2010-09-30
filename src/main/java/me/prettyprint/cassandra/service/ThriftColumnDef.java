package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.ddl.HColumnDef;
import me.prettyprint.hector.api.ddl.HIndexType;

import org.apache.cassandra.thrift.ColumnDef;
import org.apache.cassandra.thrift.IndexType;

public class ThriftColumnDef implements HColumnDef {

  private final byte[] name; //TODO(ran): use a serializer and type safety?
  private final String validationClass;
  private final HIndexType indexType;
  private final String indexName;

  public ThriftColumnDef(ColumnDef cd) {
    Assert.notNull(cd, "ColumnDef is null");
    name = cd.name;
    validationClass = cd.validation_class;
    indexType = indexTypeFromThrift(cd.index_type);
    indexName = cd.index_name;
  }

  private HIndexType indexTypeFromThrift(IndexType tIndexType) {
    switch (tIndexType) {
    case KEYS:
      return HIndexType.KEYS;
    default:
      throw new RuntimeException("Unknown thrift IndexType: " + tIndexType);
    }
  }

  public static List<HColumnDef> fromThriftList(List<ColumnDef> columnDefs) {
    if (columnDefs == null || columnDefs.isEmpty()) {
      return Collections.emptyList();
    }
    List<HColumnDef> l = new ArrayList<HColumnDef>(columnDefs.size());
    for (ColumnDef cd: columnDefs) {
      l.add(new ThriftColumnDef(cd));
    }
    return l;
  }

  @Override
  public byte[] getName() {
    return name;
  }

  @Override
  public String getValidationClass() {
    return validationClass;
  }

  @Override
  public HIndexType getIndexType() {
    return indexType;
  }

  @Override
  public String getIndexName() {
    return indexName;
  }

  public static List<ColumnDef> toThriftList(List<HColumnDef> columnDefs) {
    if (columnDefs == null || columnDefs.isEmpty()) {
      return Collections.emptyList();
    }
    List<ColumnDef> l = new ArrayList<ColumnDef>(columnDefs.size());
    for (HColumnDef d: columnDefs) {
      l.add(((ThriftColumnDef) d).toThrift());
    }
    return l;
  }

  private ColumnDef toThrift() {
    ColumnDef d = new ColumnDef();
    d.index_name = indexName;
    d.index_type = indexTypeToThrift(indexType);
    d.name = name;
    d.validation_class = validationClass;
    return d;
  }

  private IndexType indexTypeToThrift(HIndexType indexType2) {
    switch (indexType2) {
    case KEYS:
      return IndexType.KEYS;
    default:
      throw new RuntimeException("Unknown HIndexType value: " + indexType2);
    }
  }
}
