package me.prettyprint.hector.api.beans;

import java.util.List;


/**
 * Represents a return of the get_slice query for supercolumns
 *
 * @param <SN> Super column name type
 * @param <N> Column name type
 * @param <V> Column value type
 */
public interface SuperSlice<SN, N, V> {

  /**
   * @return an unmodifiable list of supercolumns
   */
  List<HSuperColumn<SN, N, V>> getSuperColumns();

  HSuperColumn<SN, N, V> getColumnByName(SN columnName);

}