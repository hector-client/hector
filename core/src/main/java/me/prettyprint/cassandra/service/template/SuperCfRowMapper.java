package me.prettyprint.cassandra.service.template;

/**
 * Converts the contents of a given super column into an object. It is passed a
 * results object with the data of the current super column.
 * 
 * @author david
 * @since Mar 10, 2011
 * @param <K>
 * @param <SN>
 *          the super column name data type
 * @param <N>
 *          the child column data type
 * @param <V>
 *          the object being mapped to datatype
 */
public interface SuperCfRowMapper<K, SN, N, V> {
  public V mapRow(SuperCfResult<K, SN, N> results);
}
