package me.prettyprint.cassandra.service;

import java.util.Iterator;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;


/**
 * This class returns each key in the specified Column Family as an Iterator.  You 
 * can use this class in a for loop without the overhead of first storing each
 * key in a large array.  See StringKeyIterator for a convenience class if the key
 * is a String.
 * @author Tim Koop
 * @param <K>	the type of the row key
 * @see StringKeyIterator
 */
public class KeyIterator<K> implements Iterable<K> {
  private static StringSerializer stringSerializer = new StringSerializer();

  private static int MAX_ROW_COUNT_DEFAULT = 500;
  private int maxColumnCount = 2;	// we only need this to tell if there are any columns in the row (to test for tombstones)

  private Iterator<Row<K, String, String>> rowsIterator = null;

  private RangeSlicesQuery<K, String, String> query = null;

  private K nextValue = null;
  private K lastReadValue = null;
  private K endKey;
  private boolean firstRun = true;
  private int rowCount = MAX_ROW_COUNT_DEFAULT;

  private Iterator<K> keyIterator = new Iterator<K>() {
    @Override
    public boolean hasNext() {
      return nextValue != null;
    }

    @Override
    public K next() {
      K next = nextValue;
      findNext(false);
      return next;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  };

  private void findNext(boolean fromRunQuery) {
    nextValue = null;
    if (rowsIterator == null) {
      return;
    }
    while (rowsIterator.hasNext() && nextValue == null) {
      Row<K, String, String> row = rowsIterator.next();
      lastReadValue = row.getKey();
      if (!row.getColumnSlice().getColumns().isEmpty()) {
        nextValue = lastReadValue;
      }
    }
    if (!rowsIterator.hasNext() && nextValue == null) {
      runQuery(lastReadValue, endKey);
    }
  }

  @Deprecated
  public KeyIterator(Keyspace keyspace, String columnFamily, Serializer<K> serializer) {
    this(keyspace, columnFamily, serializer, null, null, MAX_ROW_COUNT_DEFAULT);
  }

  @Deprecated
  public KeyIterator(Keyspace keyspace, String columnFamily, Serializer<K> serializer, int maxRowCount) {
    this(keyspace, columnFamily, serializer, null, null, maxRowCount);
  }

  @Deprecated
  public KeyIterator(Keyspace keyspace, String columnFamily, Serializer<K> serializer, K start, K end) {
    this(keyspace, columnFamily, serializer, start, end, MAX_ROW_COUNT_DEFAULT);
  }

  @Deprecated
  /*
  * When pulling deprecated methods out, do not remove this but change it to private constructor
  */
  public KeyIterator(Keyspace keyspace, String columnFamily, Serializer<K> serializer, K start, K end, int maxRowCount) {
    query = HFactory
      .createRangeSlicesQuery(keyspace, serializer, stringSerializer, stringSerializer)
      .setColumnFamily(columnFamily)
      .setRange(null, null, false, maxColumnCount)
      .setRowCount(maxRowCount);

    endKey = end;
    if(maxRowCount < Integer.MAX_VALUE) {
      rowCount = maxRowCount+1; //to compensate the first entry skip (except in first run)
    }
    runQuery(start, end);
  }

  private void runQuery(K start, K end) {
    query.setKeys(start, end);
    if(!firstRun) {
        query.setRowCount(rowCount);
    }
    rowsIterator = null;
    QueryResult<OrderedRows<K, String, String>> result = query.execute();
    OrderedRows<K, String, String> rows = (result != null) ? result.get() : null;
    rowsIterator = (rows != null) ? rows.iterator() : null;

    // we'll skip this first one, since it is the same as the last one from previous time we executed
    if (!firstRun  && rowsIterator != null) 
      rowsIterator.next();

    firstRun = false;

    if (rowsIterator != null && !rowsIterator.hasNext()) {
      nextValue = null;    // all done.  our iterator's hasNext() will now return false;
    } else {
      findNext(true);
    }
  }

  @Override
  public Iterator<K> iterator() {
    return keyIterator;
  }

  public static class Builder<K> {

    //required
    private Keyspace keyspace;
    private String columnFamily;
    private Serializer<K> serializer;

    //optional
    private K start;
    private K end;
    private Integer maxRowCount;

    public Builder(Keyspace keyspace, String columnFamily, Serializer<K> serializer) {
      this.keyspace = keyspace;
      this.columnFamily = columnFamily;
      this.serializer = serializer;
    }

    public Builder<K> start(K start) {
      this.start = start;
      return this;
    }

    public Builder<K> end(K end) {
      this.end = end;
      return this;
    }

    public Builder<K> maxRowCount(int maxRowCount) {
      this.maxRowCount = maxRowCount;
      return this;
    }

    public KeyIterator<K> build() {
      return new KeyIterator<K>(this);
    }

  }

  protected KeyIterator(Builder<K> builder) {
    this(builder.keyspace, builder.columnFamily, builder.serializer, builder.start, builder.end,
            builder.maxRowCount == null? MAX_ROW_COUNT_DEFAULT : builder.maxRowCount);
  }
}

