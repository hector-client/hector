package me.prettyprint.cassandra.model;


// base type for SliceQuery, RangeSliceQuery and MultigetSliceQuery
/*package*/ interface AbstractSliceQuery<N,T> extends Query<T> {

  /**
   * Sets the column names to be retrieved by this query
   * @param columns a list of column names
   */
  AbstractSliceQuery<N,T> setColumnNames(N... columnNames);

  /**
   * Set a predicate of start/finish to retrieve a list of columns in this range.
   * 
   * @param start Start key
   * @param finish End key
   * @param reversed
   * @param count
   * @return
   */
  AbstractSliceQuery<N,T> setPredicate(String start, String finish, boolean reversed, int count);

}
