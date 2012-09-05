package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import me.prettyprint.hector.api.beans.HSuperColumn;

/**
 * Holds the result for the contents of a super column. This interface add
 * access to the current super column similar to {@link ColumnFamilyResult}
 * 
 * @author david
 * @author zznate
 * @param <K>
 * @param <SN>
 *          super column name data type
 * @param <N>
 *          child column name data type
 */
public interface SuperCfResult<K, SN, N> extends ColumnFamilyResult<K, N> {
  
  Collection<SN> getSuperColumns();
  
  K getKey();

  UUID getUUID(SN sColumnName, N columnName);

  String getString(SN sColumnName, N columnName);

  Long getLong(SN sColumnName, N columnName);

  Integer getInteger(SN sColumnName, N columnName);

  Boolean getBoolean(SN sColumnName, N columnName);
  
  Float getFloat(SN sColumnName, N columnName);

  Double getDouble(SN sColumnName, N columnName);

  byte[] getByteArray(SN sColumnName, N columnName);
  
  ByteBuffer getByteBuffer(SN sColumnName, N columnName);

  Date getDate(SN sColumnName, N columnName);  
  
  void applySuperColumn(SN sColumnName);
  
  SN getActiveSuperColumn();
  
  /**
   * Retrieved named superColumn as an HSuperColumn with sub columns. 
   * Underlying column value is a ByteBuffer. 
   * Note: Correct derialization and treatment is up to the caller.
   * @param superColumn
   * @return
   */
  HSuperColumn<SN, N, ByteBuffer> getSuperColumn(SN superColumn);
  
  @Override
  SuperCfResult<K, SN, N> next();
}
