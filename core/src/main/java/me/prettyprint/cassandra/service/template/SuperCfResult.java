package me.prettyprint.cassandra.service.template;

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
  SN getSuperName();
  
}
