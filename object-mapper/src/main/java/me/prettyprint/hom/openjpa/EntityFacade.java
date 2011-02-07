package me.prettyprint.hom.openjpa;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Table;

import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.hector.api.Serializer;

public class EntityFacade implements Serializable {
  private static final Logger log = LoggerFactory.getLogger(EntityFacade.class);
  
  private static final long serialVersionUID = 4777260639119126462L;

  private final String columnFamilyName;
  private final Class<?> clazz;
  private final Serializer<?> keySerializer;
  private final Map<String, ColumnMeta<?>> columnMetas;
  
  public EntityFacade(ClassMetaData classMetaData) {
    clazz = classMetaData.getDescribedType();
    this.columnFamilyName = clazz.getAnnotation(Table.class) != null ? clazz.getAnnotation(Table.class).name() : clazz.getSimpleName();
    if ( log.isDebugEnabled() ) {
      log.debug("PK field name: {} and typeCode: {}", 
          classMetaData.getPrimaryKeyFields()[0].getType(), 
          classMetaData.getPrimaryKeyFields()[0].getObjectIdFieldTypeCode());
    }
    this.keySerializer = MappingUtils.getSerializer(classMetaData.getPrimaryKeyFields()[0]);
    columnMetas = new HashMap<String, ColumnMeta<?>>();
    FieldMetaData[] fmds = classMetaData.getFields();
    for (int i = 0; i < fmds.length; i++) {
      if ( fmds[i].getManagement() == FieldMetaData.MANAGE_NONE || fmds[i].isPrimaryKey())
        continue;
      if ( log.isDebugEnabled()) {
        log.debug("field name {} typeCode {} associationType: {} declaredType: {} embeddedMetaData: {}", 
            new Object[]{fmds[i].getName(), 
            fmds[i].getTypeCode(), 
            fmds[i].getAssociationType(), 
            fmds[i].getDeclaredType().getName(), 
            fmds[i].getElement().getDeclaredTypeMetaData() 
            });
      }
      // TODO if fmds[i].getAssociationType() > 0 .. we found an attached entity and need to find it's entityFacade
      columnMetas.put(fmds[i].getName(), new ColumnMeta(fmds[i].getIndex(), MappingUtils.getSerializer(fmds[i].getTypeCode())));
    }
  }  
  
  public String[] getColumnNames() {
    return columnMetas.keySet().toArray(new String[]{});
  }
    
  public String getColumnFamilyName() {
    return columnFamilyName;
  }

  public Class<?> getClazz() {
    return clazz;
  }

  public Serializer<?> getKeySerializer() {
    return keySerializer;
  }
  
  public int getFieldId(String columnName) {
    return columnMetas.get(columnName).fieldId;
  }
  
  public Serializer<?> getSerializer(String columnName) {    
    return columnMetas.get(columnName).serializer;
  }

  public Map<String, ColumnMeta<?>> getColumnMeta() {
    return columnMetas;
  }
  
  class ColumnMeta<V> {
    int fieldId;
    Serializer<V> serializer;
    
    ColumnMeta(int fieldId, Serializer<V> serializer) {
      this.fieldId = fieldId;
      this.serializer = serializer;
    }        
  }

  @Override
  public String toString() {
    return String.format("EntityFacade[class: %s, columnFamily: %s, columnNames: %s]", 
        clazz.getName(), 
        columnFamilyName, Arrays.toString(getColumnNames()));
  }
  
  
}
