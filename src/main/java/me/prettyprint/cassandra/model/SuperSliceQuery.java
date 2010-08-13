package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.SuperColumn;


/**
 * A query for the thrift call get_slice
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
public final class SuperSliceQuery<K,SN,N,V> extends AbstractSliceQuery<K,N,V,SuperSlice<SN,N,V>> {

  private K key;
  private final Extractor<SN> sNameExtractor;

  /*package*/ SuperSliceQuery(KeyspaceOperator ko, Extractor<K> keyExtractor, Extractor<SN> sNameExtractor,
      Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, keyExtractor, nameExtractor, valueExtractor);
    Assert.notNull(sNameExtractor, "sNameExtractor cannot be null");
    this.sNameExtractor = sNameExtractor;
  }

  public SuperSliceQuery<K,SN,N,V> setKey(K key) {
    this.key = key;
    return this;
  }

  public Result<SuperSlice<SN,N,V>> execute() {
    return new Result<SuperSlice<SN,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<SuperSlice<SN,N,V>>() {
          @Override
          public SuperSlice<SN,N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            List<SuperColumn> thriftRet = ks.getSuperSlice(keyExtractor.toBytes(key), columnParent, getPredicate());
            return new SuperSlice<SN,N,V>(thriftRet, sNameExtractor, columnNameExtractor, valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SuperSliceQuery(" + key + "," + toStringInternal() + ")";
  }
}
