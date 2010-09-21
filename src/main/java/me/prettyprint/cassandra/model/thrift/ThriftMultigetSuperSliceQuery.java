package me.prettyprint.cassandra.model.thrift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.AbstractSliceQuery;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.model.SuperRowsImpl;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.SuperRows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.MultigetSuperSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.SuperColumn;

/**
 * A query wrapper for the thrift call multiget_slice for a slice of supercolumns
 *
 * @author ran
 */
public final class ThriftMultigetSuperSliceQuery<SN, N, V> extends
    AbstractSliceQuery<SN, V, SuperRows<SN, N, V>> implements MultigetSuperSliceQuery<SN, N, V> {

  private Collection<String> keys;
  private final Serializer<N> nameSerializer;

  public ThriftMultigetSuperSliceQuery(Keyspace ko, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, sNameSerializer, valueSerializer);
    Assert.notNull(nameSerializer, "nameSerializer can't be null");
    this.nameSerializer = nameSerializer;
  }

  @Override
  public MultigetSuperSliceQuery<SN, N, V> setKeys(String... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  @Override
  public QueryResult<SuperRows<SN, N, V>> execute() {
    return new QueryResultImpl<SuperRows<SN, N, V>>(
        keyspace.doExecute(new KeyspaceOperationCallback<SuperRows<SN, N, V>>() {
          @Override
          public SuperRows<SN, N, V> doInKeyspace(KeyspaceService ks) throws HectorException {
            List<String> keysList = new ArrayList<String>();
            keysList.addAll(keys);
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<String, List<SuperColumn>> thriftRet = ks.multigetSuperSlice(keysList,
                columnParent, getPredicate());
            return new SuperRowsImpl<SN, N, V>(thriftRet, columnNameSerializer, nameSerializer,
                valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "MultigetSuperSliceQuery(" + keys + "," + super.toStringInternal() + ")";
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSuperSliceQuery<SN, N, V> setRange(SN start, SN finish, boolean reversed, int count) {
    return (MultigetSuperSliceQuery<SN, N, V>) super.setRange(start, finish, reversed, count);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSuperSliceQuery<SN, N, V> setColumnNames(SN... columnNames) {
    return (MultigetSuperSliceQuery<SN, N, V>) super.setColumnNames(columnNames);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MultigetSuperSliceQuery<SN, N, V> setColumnFamily(String cf) {
    return (MultigetSuperSliceQuery<SN, N, V>) super.setColumnFamily(cf);
  }

}
