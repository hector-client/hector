package me.prettyprint.cassandra.service;

import java.util.Iterator;

import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.cassandra.serializers.AbstractSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

/**
 * Iterate all the columns in all the rows in the specified column family. It fetches
 * columns in batches as specified by the max columns count. It fetches only the row keys
 * and uses the specified max rows count in range slice query.
 *
 * @author Bharatendra Boddu
 *
 * @param <K>   Row key type
 * @param <N>   Column name type
 * @param <V>   Column value type
 */
public class ColumnFamilyIterator<K, N, V> implements Iterator<HColumn<N, V>> {
  private static StringSerializer stringSerializer = new StringSerializer();
  private Keyspace keyspace;
  private String columnFamily;
  private int queryRowCount;
  private int queryColCount;
  private long totalRows;
  private long totalColumns;
  private K nextRangeStart;
  private N nextSliceStart;
  private int rows;
  private int columns;
  private Iterator<Row<K, String, String>> rowsIterator;
  private Iterator<HColumn<N, V>> colsIterator;
  private SliceQuery<K, N, V> sliceQuery;
  private RangeSlicesQuery<K, String, String> rangeQuery;

  /**
   * Constructor
   *
   * @param ks         Keyspace
   * @param cf         Column family
   * @param serializer Key type serializer
   * @param query      Slice query object
   * @param maxRows    Max number of rows to retrieve per range query
   * @param maxCols    Max number of columns to retrieve per slice query
   */
  public ColumnFamilyIterator(final Keyspace ks, final String cf,
                              final AbstractSerializer<K> serializer,
                              SliceQuery<K, N, V> query,
                              final int maxRows, final int maxCols) {
      keyspace = ks;
      columnFamily = cf;
      queryRowCount = maxRows;
      queryColCount = maxCols;
      totalRows = 0;
      totalColumns = 0;
      nextRangeStart = null;
      nextSliceStart = null;
      rows = 0;
      columns = 0;
      rowsIterator = null;
      colsIterator = null;
      sliceQuery = query;
      sliceQuery.setColumnFamily(columnFamily);
      rangeQuery = HFactory
          .createRangeSlicesQuery(keyspace, serializer, stringSerializer, stringSerializer)
          .setColumnFamily(columnFamily)
          .setReturnKeysOnly()
          .setRowCount(queryRowCount);
  }

  @Override
  public boolean hasNext() {
      if (colsIterator == null || !colsIterator.hasNext()) {
          if (columns == queryColCount) {
              queryNextSlice(nextSliceStart);
              return hasNext();
          }
          else if (rowsIterator != null && rowsIterator.hasNext()) {
              nextRangeStart = rowsIterator.next().getKey();
              rows++;
              totalRows++;
              nextSliceStart = null;
              columns = 0;
              sliceQuery.setKey(nextRangeStart);        
              queryNextSlice(nextSliceStart);
              return hasNext();
          } else if (rowsIterator == null || rows == queryRowCount) {
              queryNextRange(nextRangeStart);
              if (rowsIterator.hasNext()) {
                  nextRangeStart = rowsIterator.next().getKey();
                  rows++;
                  totalRows++;
                  nextSliceStart = null;
                  columns = 0;
                  sliceQuery.setKey(nextRangeStart);        
                  queryNextSlice(nextSliceStart);
                  return hasNext();
              }
          }
      }
      return (colsIterator != null) ? colsIterator.hasNext() : false;
  }

  @Override
  public HColumn<N, V> next() {
      HColumn<N, V> col = colsIterator.next();
      nextSliceStart = col.getName();
      columns++;
      totalColumns++;
      return col;
  }

  @Override
  public void remove() {
  }

  /**
   * Query the next batch of rows starting from the key rangeStart.
   * Skip the first row in every batch except for the first batch.
   *
   * @param rangeStart Start key for the next batch of keys
   */
  private void queryNextRange(final K rangeStart) {
      rows = 0;
      rangeQuery.setKeys(rangeStart, null);
      rowsIterator = rangeQuery.execute().get().getList().iterator();
      if (rangeStart != null) {
          // skip the first row in the range
          if (rowsIterator.hasNext()) {
              rowsIterator.next();
              rows++;
          }
      }
  }

  /**
   * Query the next batch of columns starting from the column name sliceStart.
   * Skip the first column in every batch except for the first batch.
   *
   * @param sliceStart Start column name for the next batch of columns
   */
  private void queryNextSlice(final N sliceStart)  {
      columns = 0;
      sliceQuery.setRange(sliceStart, null, false, queryColCount);
      colsIterator = sliceQuery.execute().get().getColumns().iterator();
      if (sliceStart != null) {
          // skip the first column in the slice
          if (colsIterator.hasNext()) {
              colsIterator.next();
              columns++;
          }
      }
  }

  public long getTotalRowsCount() {
      return totalRows;
  }

  public long getTotalColumnsCount() {
      return totalColumns;
  }
}
