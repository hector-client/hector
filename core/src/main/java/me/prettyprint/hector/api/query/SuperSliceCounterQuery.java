package me.prettyprint.hector.api.query;

import java.util.Collection;

import me.prettyprint.hector.api.beans.CounterSuperSlice;

/**
 * A query for the  call get_slice.
 * <p>
 * Get a slice of super counter columns from a super column family.
 *
 *
 * @param <SN> type of the super column name
 * @param <N> type of the column name
 */
public interface SuperSliceCounterQuery<K, SN, N> extends Query<CounterSuperSlice<SN, N>> {

  SuperSliceCounterQuery<K, SN, N> setKey(K key);

  SuperSliceCounterQuery<K, SN, N> setColumnNames(SN... columnNames);

  SuperSliceCounterQuery<K, SN, N> setRange(SN start, SN finish, boolean reversed, int count);

  SuperSliceCounterQuery<K, SN, N> setColumnFamily(String cf);

  Collection<SN> getColumnNames();

}
