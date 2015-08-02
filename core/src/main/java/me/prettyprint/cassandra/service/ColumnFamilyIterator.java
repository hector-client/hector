package me.prettyprint.cassandra.service;

import java.util.Iterator;

import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.hector.api.Serializer;

/**
 * Iterate all the columns in all the rows in the specified column family. It uses
 * KeyIterator to iterate the rows and ColumnSliceIterator to iterate columns in a row.
 *
 * @author Bharatendra Boddu
 *
 * @param <K>   Row key type
 * @param <N>   Column name type
 * @param <V>   Column value type
 */
public class ColumnFamilyIterator<K, N, V> implements Iterator<HColumn<N, V>> {
  private int maxColumnsCount;
  private N startColumn;
  private N finishColumn;
  private long totalRows;
  private long totalColumns;
  private Iterator<K> rowsIterator;
  private Iterator<HColumn<N, V>> colsIterator;
  private SliceQuery<K, N, V> sliceQuery;

  /**
   * Constructor - Iterate over all the rows
   *
   * @param ks         Keyspace
   * @param cf         Column family
   * @param serializer Key type serializer
   * @param query      Slice query object
   * @param maxRows    Max number of rows to retrieve per range query
   * @param maxCols    Max number of columns to retrieve per slice query
   */
  public ColumnFamilyIterator(final Keyspace ks, final String cf,
                              final Serializer<K> serializer,
                              SliceQuery<K, N, V> query,
                              final int maxRows, final int maxCols) {
    this(ks, cf, serializer, null, null, query, maxRows, maxCols);
  }

  /**
   * Constructor - Iterate over the rows specified between start and finish
   *
   * @param ks         Keyspace
   * @param cf         Column family
   * @param serializer Key type serializer
   * @param start      Start row key
   * @param finish     End row key
   * @param query      Slice query object
   * @param maxRows    Max number of rows to retrieve per range query
   * @param maxCols    Max number of columns to retrieve per slice query
   */
  public ColumnFamilyIterator(final Keyspace ks, final String cf,
                              final Serializer<K> serializer,
                              final K start, final K finish,
                              SliceQuery<K, N, V> query,
                              final int maxRows, final int maxCols) {
    this(new KeyIterator<K>(ks, cf, serializer, start, finish, maxRows), cf, query, maxCols);
  }
  /**
   * Constructor - Iterate the rows using keyIterator
   *
   * @param keyIterator Key iterator
   * @param cf          Column family
   * @param query       Slice query object
   * @param maxCols     Max number of columns to retrieve per slice query
   */
  public ColumnFamilyIterator(final KeyIterator<K> keyIterator, final String cf, SliceQuery<K, N, V> query, final int maxCols) {
      this(keyIterator, cf, query, null, null, maxCols);
  }

  /**
   * Constructor - Iterate the rows using keyIterator and in each row iterate over the column in the range
   *               [startColumn:finishColumn]
   *
   * @param keyIterator Key iterator
   * @param cf            Column family
   * @param query         Slice query object
   * @param startColumn   Start column name
   * @param finishColumn  End column name
   * @param maxCols     Max number of columns to retrieve per slice query
   */
  public ColumnFamilyIterator(final KeyIterator<K> keyIterator, final String cf, SliceQuery<K, N, V> query,
                              final N startCol, final N finishCol, final int maxCols) {
      maxColumnsCount = maxCols;
      startColumn = startCol;
      finishColumn = finishCol;
      totalRows = 0;
      totalColumns = 0;
      rowsIterator = keyIterator.iterator();
      colsIterator = null;
      sliceQuery = query;
      sliceQuery.setColumnFamily(cf);
  }
  
  @Override
  public boolean hasNext() {
    if (colsIterator == null || !colsIterator.hasNext()) {
      if (rowsIterator.hasNext()) {
        K nextRowKey = rowsIterator.next();
        totalRows++;
        sliceQuery.setKey(nextRowKey);
        colsIterator = new ColumnSliceIterator(sliceQuery, startColumn, finishColumn, false, maxColumnsCount);
        return hasNext();
      }
    }
    return (colsIterator != null) ? colsIterator.hasNext() : false;
  }

  @Override
  public HColumn<N, V> next() {
    HColumn<N, V> column = colsIterator.next();
    totalColumns++;
    return column;
  }

  @Override
  public void remove() {
  }

  public long getTotalRowsCount() {
    return totalRows;
  }

  public long getTotalColumnsCount() {
    return totalColumns;
  }
}
