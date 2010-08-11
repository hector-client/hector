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
public final class MultigetSuperSliceQuery<SN, N, V> extends
    AbstractSliceQuery<SN, V, SuperRows<SN, N, V>> {

  private Collection<byte[]> keys;
  private final Extractor<N> nameExtractor;

  /*package*/MultigetSuperSliceQuery(KeyspaceOperator ko, Extractor<SN> sNameExtractor,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, sNameExtractor, valueExtractor);
    Assert.notNull(nameExtractor, "nameExtractor can't be null");
    this.nameExtractor = nameExtractor;
  }

  public MultigetSuperSliceQuery<SN, N, V> setKeys(byte[]... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  public Result<SuperRows<SN, N, V>> execute() {
    return new Result<SuperRows<SN, N, V>>(
        keyspaceOperator.doExecute(new KeyspaceOperationCallback<SuperRows<SN, N, V>>() {
          @Override
          public SuperRows<SN, N, V> doInKeyspace(Keyspace ks) throws HectorException {
            List<byte[]> keysList = new ArrayList<byte[]>();
            keysList.addAll(keys);
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<byte[], List<SuperColumn>> thriftRet = ks.multigetSuperSlice(keysList,
                columnParent, getPredicate());
            return new SuperRows<SN, N, V>(thriftRet, columnNameExtractor, nameExtractor,
                valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "MultigetSuperSliceQuery(" + keys + "," + super.toStringInternal() + ")";
  }
}
