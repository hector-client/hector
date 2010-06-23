package me.prettyprint.cassandra.model;


// base type for SliceQuery, RangeSliceQuery and MultigetSliceQuery
/*package*/ interface AbstractSliceQuery<T> extends Query<T> {
  
  /**
   * Sets the column names to be retrieved by this query
   * @param columns a list of column names
   */
  AbstractSliceQuery<T> setColumnNames(Object... columns);

  /**
   * Set a predicate of start/finish to retrieve a list of columns in this range.
   * 
   * @param start
   * @param finish
   * @param reversed
   * @param count
   * @return
   */
  AbstractSliceQuery<T> setPredicate(Object start, Object finish, boolean reversed, int count);

}
