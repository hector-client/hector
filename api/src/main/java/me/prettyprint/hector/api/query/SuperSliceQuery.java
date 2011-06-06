package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.SuperSlice;

/**
 * A query for the  call get_slice.
 * <p>
 * Get a slice of super columns from a super column family.
 *
 * @author Ran Tavory
 *
 * @param <SN> type of the super column name
 * @param <N> type of the column name
 * @param <V> type of the column value
 */
public interface SuperSliceQuery<K, SN, N, V> extends Query<SuperSlice<SN, N, V>> {

  SuperSliceQuery<K, SN, N, V> setKey(K key);

  SuperSliceQuery<K, SN, N, V> setColumnNames(SN... columnNames);

  SuperSliceQuery<K, SN, N, V> setRange(SN start, SN finish, boolean reversed, int count);

  SuperSliceQuery<K, SN, N, V> setColumnFamily(String cf);

  Collection<SN> getColumnNames();

}
