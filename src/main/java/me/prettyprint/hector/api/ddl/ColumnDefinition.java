package me.prettyprint.hector.api.ddl;

public interface ColumnDefinition {
  // TODO(ran): should be typed
  byte[] getName();
  String getValidationClass();
  ColumnIndexType getIndexType();
  String getIndexName();

}
