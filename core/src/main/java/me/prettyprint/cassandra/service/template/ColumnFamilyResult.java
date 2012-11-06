package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import me.prettyprint.hector.api.ResultStatus;
import me.prettyprint.hector.api.beans.HColumn;

/**
 * A common interface for access to the resuls of a query of either a standard or super column family.
 * There are different implementations of this which hide the differences requires of standar/super 
 * column families. As this interface inherits from {@link ResultStatus}, results will also provide
 * execution details.
 *  
 * @author david
 * @author zznate
 * @param <K>
 * @param <N>
 */
public interface ColumnFamilyResult<K, N> extends Iterator<ColumnFamilyResult<K,N>>,ResultStatus {
  K getKey();

  UUID getUUID(N columnName);

  String getString(N columnName);

  Long getLong(N columnName);

  Integer getInteger(N columnName);

  Float getFloat(N columnName);
  
  Double getDouble(N columnName);

  Boolean getBoolean(N columnName);

  byte[] getByteArray(N columnName);

  Date getDate(N columnName);  

  Collection<N> getColumnNames();
  
  HColumn<N,ByteBuffer> getColumn(N columnName);
  
  boolean hasResults();
    
}
