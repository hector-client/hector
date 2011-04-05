package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;

/**
 * Holds the result for the contents of a super column. This interface add
 * access to the current super column name since this may be a property of the
 * object being mapped into.
 * 
 * @author david
 * @since Mar 10, 2011
 * @param <K>
 * @param <SN>
 *          super column name data type
 * @param <N>
 *          child column name data type
 */
public interface SuperCfResult<K, SN, N> extends ColumnFamilyResult<K, N> {
  // TODO remove. this no loger makes sense with many-to-one on a row
  
  Collection<SN> getSuperColumns();
  
  K getKey();

  UUID getUUID(SN sColumnName, N columnName);

  String getString(SN sColumnName, N columnName);

  Long getLong(SN sColumnName, N columnName);

  Integer getInteger(SN sColumnName, N columnName);

  Boolean getBoolean(SN sColumnName, N columnName);

  byte[] getByteArray(SN sColumnName, N columnName);
  
  ByteBuffer getByteBuffer(SN sColumnName, N columnName);

  Date getDate(SN sColumnName, N columnName);  
  
  void applySuperColumn(SN sColumnName);
}
