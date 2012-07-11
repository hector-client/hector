package me.prettyprint.hector.api.beans;

import java.util.List;


/**
 * A ColumnSlice represents a set of columns as returned by calls such as get_slice
 *
 * @author patricioe (Patricio Echague)
 */
public interface CounterSlice<N> {

  /**
   *
   * @return an unmodifiable list of the columns
   */
  List<HCounterColumn<N>> getColumns();

  HCounterColumn<N> getColumnByName(N columnName);

}