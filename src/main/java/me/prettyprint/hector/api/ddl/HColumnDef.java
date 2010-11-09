package me.prettyprint.hector.api.ddl;

import java.nio.ByteBuffer;

public interface HColumnDef {
  // TODO(ran): should be typed
  ByteBuffer getName();
  String getValidationClass();
  HIndexType getIndexType();
  String getIndexName();

}
