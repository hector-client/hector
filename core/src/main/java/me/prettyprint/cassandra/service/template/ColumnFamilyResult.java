package me.prettyprint.cassandra.service.template;

import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import me.prettyprint.hector.api.ResultStatus;

/**
 * A commen interface for access to the resuls of a query of either a standard or super column family.
 * There are different implementations of this which hide the differences requires of standar/super 
 * column families.
 *  
 * @author david
 * @since Mar 10, 2011
 * @param <K>
 * @param <N>
 */
public interface ColumnFamilyResult<K, N> extends Iterator<ColumnFamilyResult<K,N>>,ResultStatus {
  public K getKey();

  public UUID getUUID(N columnName);

  public String getString(N columnName);

  public Long getLong(N columnName);

  public Integer getInteger(N columnName);

  public Boolean getBoolean(N columnName);

  public byte[] getByteArray(N columnName);

  public Date getDate(N columnName);

}
