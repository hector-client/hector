package me.prettyprint.cassandra.model.thrift;

import java.util.List;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.CounterSliceImpl;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.CounterSlice;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SubSliceCounterQuery;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.CounterColumn;


/**
 * A query for the thrift call get_slice on subcolumns of a supercolumns.
 */
public final class ThriftSubSliceCounterQuery<K,SN,N> extends AbstractSliceQuery<K,N,Long,CounterSlice<N>>
  implements SubSliceCounterQuery<K, SN, N>{

  private K key;
  private SN superColumn;
  private final Serializer<SN> sNameSerializer;

  public ThriftSubSliceCounterQuery(Keyspace k, Serializer<K> keySerializer, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer) {
    super(k, keySerializer, nameSerializer, LongSerializer.get());
    Assert.notNull(sNameSerializer, "Supername serializer cannot be null");
    this.sNameSerializer = sNameSerializer;
  }

  @Override
  public SubSliceCounterQuery<K,SN,N> setKey(K key) {
    this.key = key;
    return this;
  }

  /**
   * Set the supercolumn to run the slice query on
   */
  @Override
  public SubSliceCounterQuery<K,SN,N> setSuperColumn(SN superColumn) {
    this.superColumn = superColumn;
    return this;
  }

  @Override
  public QueryResult<CounterSlice<N>> execute() {
    Assert.notNull(key, "Key cannot be null");
    Assert.notNull(superColumn, "Supercolumn cannot be null");
    return new QueryResultImpl<CounterSlice<N>>(keyspace.doExecute(
        new KeyspaceOperationCallback<CounterSlice<N>>() {
          @Override
          public CounterSlice<N> doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameSerializer.toByteBuffer(superColumn));
            List<CounterColumn> thriftRet = ks.getCounterSlice(keySerializer.toByteBuffer(key), columnParent, getPredicate());
            return new CounterSliceImpl<N>(thriftRet, columnNameSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SubSliceCounterQuery(" + key + "," + superColumn + "," + toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public SubSliceCounterQuery<K, SN, N> setColumnNames(N... columnNames) {
    return (SubSliceCounterQuery<K, SN, N>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SubSliceCounterQuery<K, SN, N> setRange(N start, N finish, boolean reversed, int count) {
    return (SubSliceCounterQuery<K, SN, N>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public SubSliceCounterQuery<K, SN, N> setColumnFamily(String cf) {
    return (SubSliceCounterQuery<K, SN, N>) super.setColumnFamily(cf);
  }
}
