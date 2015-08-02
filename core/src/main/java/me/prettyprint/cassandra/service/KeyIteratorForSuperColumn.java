package me.prettyprint.cassandra.service;

import java.util.Iterator;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.OrderedSuperRows;
import me.prettyprint.hector.api.beans.SuperRow;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSuperSlicesQuery;


/**
 * This class returns each key in the specified Column Family as an Iterator.  You 
 * can use this class in a for loop without the overhead of first storing each
 * key in a large array.  See StringKeyIterator for a convenience class if the key
 * is a String.
 * @author Tim Koop
 * @author pescuma
 * @param <K>	the type of the row key
 * @see StringKeyIterator
 */
public class KeyIteratorForSuperColumn<K> implements Iterable<K> {
  private static StringSerializer stringSerializer = new StringSerializer();

  private static int MAX_ROW_COUNT_DEFAULT = 500;
  private int maxColumnCount = 2;	// we only need this to tell if there are any columns in the row (to test for tombstones)

  private Iterator<SuperRow<K, String, String, String>> rowsIterator = null;

  private RangeSuperSlicesQuery<K, String, String, String> query = null;

  private K nextValue = null;
  private K lastReadValue = null;
  private K endKey;
  private boolean firstRun = true;

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
      SuperRow<K, String, String, String> row = rowsIterator.next();
      lastReadValue = row.getKey();
      if (!row.getSuperSlice().getSuperColumns().isEmpty()) {
        nextValue = lastReadValue;
      }
    }
    if (!rowsIterator.hasNext() && nextValue == null) {
      runQuery(lastReadValue, endKey);
    }
  }

  public KeyIteratorForSuperColumn(Keyspace keyspace, String columnFamily, Serializer<K> serializer) {
    this(keyspace, columnFamily, serializer, null, null, MAX_ROW_COUNT_DEFAULT);
  }

  public KeyIteratorForSuperColumn(Keyspace keyspace, String columnFamily, Serializer<K> serializer, int maxRowCount) {
    this(keyspace, columnFamily, serializer, null, null, maxRowCount);
  }
  
  public KeyIteratorForSuperColumn(Keyspace keyspace, String columnFamily, Serializer<K> serializer, K start, K end) {
    this(keyspace, columnFamily, serializer, start, end, MAX_ROW_COUNT_DEFAULT);
  }

  public KeyIteratorForSuperColumn(Keyspace keyspace, String columnFamily, Serializer<K> serializer, K start, K end, int maxRowCount) {
    query = HFactory
      .createRangeSuperSlicesQuery(keyspace, serializer, stringSerializer, stringSerializer, stringSerializer)
      .setColumnFamily(columnFamily)
      .setRange(null, null, false, maxColumnCount)
      .setRowCount(maxRowCount);

    endKey = end;
    runQuery(start, end);
  }

  private void runQuery(K start, K end) {
    query.setKeys(start, end);

    rowsIterator = null;
    QueryResult<OrderedSuperRows<K, String, String, String>> result = query.execute();
    OrderedSuperRows<K, String, String, String> rows = (result != null) ? result.get() : null;
    rowsIterator = (rows != null) ? rows.iterator() : null;

    // we'll skip this first one, since it is the same as the last one from previous time we executed
    if (!firstRun  && rowsIterator != null) 
      rowsIterator.next();

    firstRun = false;

    if (!rowsIterator.hasNext()) {
      nextValue = null;    // all done.  our iterator's hasNext() will now return false;
    } else {
      findNext(true);
    }
  }

  @Override
  public Iterator<K> iterator() {
    return keyIterator;
  }
}

