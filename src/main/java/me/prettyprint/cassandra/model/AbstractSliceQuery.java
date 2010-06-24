package me.prettyprint.cassandra.model;


// base type for SliceQuery, RangeSliceQuery and MultigetSliceQuery
/*package*/ interface AbstractSliceQuery<K,N,T> extends Query<T> {
  
  /**
   * Sets the column names to be retrieved by this query
   * @param columns a list of column names
   */
  AbstractSliceQuery<K,N,T> setColumnNames(N... columnNames);

  /**
   * Set a predicate of start/finish to retrieve a list of columns in this range.
   * 
   * @param start Start key 
   * @param finish End key
   * @param reversed
   * @param count
   * @return
   */
  AbstractSliceQuery<K,N,T> setPredicate(K start, K finish, boolean reversed, int count);

}
