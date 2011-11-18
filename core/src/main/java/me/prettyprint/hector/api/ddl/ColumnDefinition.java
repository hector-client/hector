package me.prettyprint.hector.api.ddl;

import java.nio.ByteBuffer;

public interface ColumnDefinition {
  // TODO(ran): should be typed
  ByteBuffer getName();
  String getValidationClass();
  ColumnIndexType getIndexType();
  String getIndexName();

}
