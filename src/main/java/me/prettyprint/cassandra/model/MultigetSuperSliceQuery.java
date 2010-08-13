package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.SuperColumn;

/**
 * A query wrapper for the thrift call multiget_slice for a slice of supercolumns
 */
public final class MultigetSuperSliceQuery<K, SN, N, V> extends
    AbstractSliceQuery<K, SN, V, SuperRows<K, SN, N, V>> {

  private Collection<K> keys;
  private final Extractor<N> nameExtractor;

  /*package*/MultigetSuperSliceQuery(KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<SN> sNameExtractor,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, keyExtractor, sNameExtractor, valueExtractor);
    Assert.notNull(nameExtractor, "nameExtractor can't be null");
    this.nameExtractor = nameExtractor;
  }

  public MultigetSuperSliceQuery<K, SN, N, V> setKeys(K... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  public Result<SuperRows<K, SN, N, V>> execute() {
    return new Result<SuperRows<K, SN, N, V>>(
        keyspaceOperator.doExecute(new KeyspaceOperationCallback<SuperRows<K, SN, N, V>>() {
          @Override
          public SuperRows<K, SN, N, V> doInKeyspace(Keyspace ks) throws HectorException {
            List<K> keysList = new ArrayList<K>();
            keysList.addAll(keys);
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<SuperColumn>> thriftRet = keyExtractor.fromBytesMap(ks.multigetSuperSlice(keyExtractor.toBytesList(keysList),
                columnParent, getPredicate()));
            return new SuperRows<K, SN, N, V>(thriftRet, keyExtractor, columnNameExtractor, nameExtractor,
                valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "MultigetSuperSliceQuery(" + keys + "," + super.toStringInternal() + ")";
  }
}
