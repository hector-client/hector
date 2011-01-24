package me.prettyprint.hom.service;

public class EntitySchemaStatus {

  private final SchemaResult schemaResult;
  
  public EntitySchemaStatus(SchemaResult result) {
    this.schemaResult = result;
  }
  
  
  public SchemaResult getSchemaResult() {
    return schemaResult;
  }
  
  public enum SchemaResult {
    CREATED,
    UPDATED,
    NOT_MODIFIED
  }
}
