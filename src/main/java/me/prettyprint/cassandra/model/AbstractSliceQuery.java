package me.prettyprint.cassandra.model;

import java.util.List;

// base type for SliceQuery, RangeSliceQuery and MultigetSliceQuery
/*package*/ interface AbstractSliceQuery<T> extends Query<T> {
  SliceQuery setPredicate(List<Object> columnNames);
  SliceQuery setPredicate(Object start, Object finish, boolean reversed, int count);

}
