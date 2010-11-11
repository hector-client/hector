package me.prettyprint.cassandra.model;

import me.prettyprint.hector.api.ddl.ColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnIndexType;

import java.nio.ByteBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: peter
 * Date: 3/11/2010
 * Time: 11:18:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class BasicColumnDefinition implements ColumnDefinition {

  private ByteBuffer name;
  private String validationClass;
  private ColumnIndexType indexType;
  private String indexName;

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

  public void setName(ByteBuffer name) {
    this.name = name;
  }

  public void setValidationClass(String validationClass) {
    this.validationClass = validationClass;
  }

  public void setIndexType(ColumnIndexType indexType) {
    this.indexType = indexType;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }


}
