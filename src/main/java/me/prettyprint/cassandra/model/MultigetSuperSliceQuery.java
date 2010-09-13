package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.exceptions.HectorException;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.SuperColumn;

/**
 * A query wrapper for the thrift call multiget_slice for a slice of supercolumns
 */
public final class MultigetSuperSliceQuery<SN, N, V> extends
    AbstractSliceQuery<SN, V, SuperRows<SN, N, V>> {

  private Collection<String> keys;
  private final Serializer<N> nameSerializer;

  public MultigetSuperSliceQuery(KeyspaceOperator ko, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, sNameSerializer, valueSerializer);
    Assert.notNull(nameSerializer, "nameSerializer can't be null");
    this.nameSerializer = nameSerializer;
  }

  public MultigetSuperSliceQuery<SN, N, V> setKeys(String... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  @Override
  public Result<SuperRows<SN, N, V>> execute() {
    return new Result<SuperRows<SN, N, V>>(
        keyspaceOperator.doExecute(new KeyspaceOperationCallback<SuperRows<SN, N, V>>() {
          @Override
          public SuperRows<SN, N, V> doInKeyspace(Keyspace ks) throws HectorException {
            List<String> keysList = new ArrayList<String>();
            keysList.addAll(keys);
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<String, List<SuperColumn>> thriftRet = ks.multigetSuperSlice(keysList,
                columnParent, getPredicate());
            return new SuperRows<SN, N, V>(thriftRet, columnNameSerializer, nameSerializer,
                valueSerializer);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "MultigetSuperSliceQuery(" + keys + "," + super.toStringInternal() + ")";
  }
}
