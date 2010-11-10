package me.prettyprint.cassandra.service;

import me.prettyprint.cassandra.model.BasicColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnIndexType;
import org.apache.cassandra.thrift.ColumnDef;
import org.apache.cassandra.thrift.IndexType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: peter
 * Date: 3/11/2010
 * Time: 11:53:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class ThriftDefinitionFactory {

  // Column Definition

  public ColumnDefinition getColumnDefinition( ColumnDef def){
    BasicColumnDefinition definition = new BasicColumnDefinition();
    definition.setIndexName(def.getIndex_name());
    definition.setIndexType(indexTypeFromThrift(def.getIndex_type()));
    definition.setName(def.getName());
    definition.setValidationClass(def.getValidation_class());
    return definition;

  }

  private ColumnIndexType indexTypeFromThrift(IndexType tIndexType) {
    switch (tIndexType) {
    case KEYS:
      return ColumnIndexType.KEYS;
    default:
      throw new RuntimeException("Unknown thrift IndexType: " + tIndexType);
    }
  }

  public List<ColumnDefinition> fromThriftList(List<ColumnDef> columnDefs) {
    if (columnDefs == null || columnDefs.isEmpty()) {
      return Collections.emptyList();
    }
    List<ColumnDefinition> definitionList = new ArrayList<ColumnDefinition>(columnDefs.size());
    for (ColumnDef def: columnDefs) {
      definitionList.add( getColumnDefinition( def ));
    }
    return definitionList;
  }


  // Column Family Definition



  // Keyspace Definition


}
