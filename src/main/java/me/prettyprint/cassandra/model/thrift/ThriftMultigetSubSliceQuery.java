package me.prettyprint.cassandra.model.thrift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.model.RowsImpl;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.MultigetSubSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

/**
 * A query wrapper for the thrift call multiget_slice for subcolumns  of supercolumns
 */
public final class ThriftMultigetSubSliceQuery<SN, N, V> extends
    AbstractSliceQuery<N, V, Rows<N, V>> implements MultigetSubSliceQuery<SN, N, V> {

  private Collection<String> keys;
  private final Serializer<SN> sNameSerializer;
  private SN superColumn;

  public ThriftMultigetSubSliceQuery(Keyspace ko, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, nameSerializer, valueSerializer);
    Assert.notNull(nameSerializer, "sNameSerializer can't be null");
    this.sNameSerializer = sNameSerializer;
  }

  @Override
  public MultigetSubSliceQuery<SN, N, V> setKeys(String... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  /**
   * Set the supercolumn to run the slice query on
   */
  @Override
  public MultigetSubSliceQuery<SN, N, V> setSuperColumn(SN superColumn) {
    Assert.notNull(superColumn, "supercolumn may not be null");
    this.superColumn = superColumn;
    return this;
  }


  @Override
  public QueryResult<Rows<N, V>> execute() {
    Assert.noneNull(keys, "Keys cannot be null");
    Assert.noneNull(columnFamilyName, "columnFamilyName cannot be null");
    Assert.noneNull(superColumn, "superColumn cannot be null");

    return new QueryResultImpl<Rows<N, V>>(
        keyspace.doExecute(new KeyspaceOperationCallback<Rows<N, V>>() {
          @Override
          public Rows<N, V> doInKeyspace(KeyspaceService ks) throws HectorException {
            List<String> keysList = new ArrayList<String>();
            keysList.addAll(keys);
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            columnParent.setSuper_column(sNameSerializer.toBytes(superColumn));
            Map<String, List<Column>> thriftRet = ks.multigetSlice(keysList,
                columnParent, getPredicate());
            return new RowsImpl<N, V>(thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "MultigetSubSliceQuery(" + superColumn + "," + keys + "," + super.toStringInternal()
        + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSubSliceQuery<SN, N, V> setColumnFamily(String cf) {
    return (MultigetSubSliceQuery<SN, N, V>) super.setColumnFamily(cf);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSubSliceQuery<SN, N, V> setRange(N start, N finish, boolean reversed, int count) {
    return (MultigetSubSliceQuery<SN, N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSubSliceQuery<SN, N, V> setColumnNames(N... columnNames) {
    return (MultigetSubSliceQuery<SN, N, V>) super.setColumnNames(columnNames);
  }
}
