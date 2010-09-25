package me.prettyprint.hector.api.ddl;

public interface HColumnDef {
  // TODO(ran): should be typed
  byte[] getName();
  String getValidationClass();
  HIndexType getIndexType();
  String getIndexName();

}
