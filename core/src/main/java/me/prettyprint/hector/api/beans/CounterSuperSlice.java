package me.prettyprint.hector.api.beans;

import java.util.List;


/**
 * Represents a return of the get_slice query for supercolumns
 *
 * @param <SN> Super column name type
 * @param <N> Column name type
 */
public interface CounterSuperSlice<SN, N> {

  /**
   * @return an unmodifiable list of supercolumns
   */
  List<HCounterSuperColumn<SN, N>> getSuperColumns();

  HCounterSuperColumn<SN, N> getColumnByName(SN columnName);

}