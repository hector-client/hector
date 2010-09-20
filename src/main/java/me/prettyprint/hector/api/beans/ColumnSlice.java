package me.prettyprint.hector.api.beans;

import java.util.List;


/**
 * A ColumnSlice represents a set of columns as returned by calls such as get_slice
 *
 * @author ran
 */
public interface ColumnSlice<N, V> {

  /**
   *
   * @return an unmodifiable list of the columns
   */
  List<HColumn<N, V>> getColumns();

  HColumn<N, V> getColumnByName(N columnName);

}