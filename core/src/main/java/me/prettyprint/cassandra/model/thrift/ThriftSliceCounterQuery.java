package me.prettyprint.cassandra.model.thrift;

import java.util.List;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.CounterSliceImpl;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.CounterSlice;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceCounterQuery;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.CounterColumn;

/**
 * A query for the thrift call get_slice
 *
 * @author patricioe (Patricio Echague)
 *
 * @param <N>
 */
public final class ThriftSliceCounterQuery<K, N> extends AbstractSliceQuery<K, N, Long, CounterSlice<N>>
    implements SliceCounterQuery<K, N> {

  private K key;

  public ThriftSliceCounterQuery(Keyspace k,
      Serializer<K> keySerializer,
      Serializer<N> nameSerializer) {
	// The reason of Longserializer is just to 
    super(k, keySerializer, nameSerializer, LongSerializer.get());
  }

  @Override
  public SliceCounterQuery<K, N> setKey(K key) {
    this.key = key;
    return this;
  }

  @Override
  public QueryResult<CounterSlice<N>> execute() {
    return new QueryResultImpl<CounterSlice<N>>(keyspace.doExecute(
        new KeyspaceOperationCallback<CounterSlice<N>>() {
          @Override
          public CounterSlice<N> doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            List<CounterColumn> thriftRet = ks.getCounterSlice(keySerializer.toByteBuffer(key), columnParent, getPredicate());
            return new CounterSliceImpl<N>(thriftRet, columnNameSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "CounterSliceQuery(" + key + "," + toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public SliceCounterQuery<K, N> setColumnNames(N... columnNames) {
    return (SliceCounterQuery<K, N>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SliceCounterQuery<K, N> setRange(N start, N finish, boolean reversed, int count) {
    return (SliceCounterQuery<K, N>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SliceCounterQuery<K, N> setColumnFamily(String cf) {
    return (SliceCounterQuery<K, N>) super.setColumnFamily(cf);
  }
}
