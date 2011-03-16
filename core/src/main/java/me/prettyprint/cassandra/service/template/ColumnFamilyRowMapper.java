package me.prettyprint.cassandra.service.template;

/**
 * Converts the contents of a standard column family row into an object.
 * 
 * @author david
 * @since Mar 10, 2011
 * @param <K>
 * @param <N> standard column type data type
 * @param <V> the object type being mapped into
 */
public interface ColumnFamilyRowMapper<K, N, V> {
  public V mapRow(ColumnFamilyResult<K, N> results);
}
