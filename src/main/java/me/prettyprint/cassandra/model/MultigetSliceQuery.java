package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;

/**
 * A query wrapper for the thrift call multiget_slice
 */
public final class MultigetSliceQuery<N,V> extends AbstractSliceQuery<N,V,Rows<N,V>> {

  private Collection<byte[]> keys;

  /*package*/ MultigetSliceQuery(KeyspaceOperator ko, Extractor<N> nameExtractor,
      Extractor<V> valueExtractor) {
    super(ko, nameExtractor, valueExtractor);
  }

  public MultigetSliceQuery<N,V> setKeys(byte[]... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }


  public Result<Rows<N,V>> execute() {
    Assert.notNull(columnFamilyName, "columnFamilyName can't be null");
    Assert.notNull(keys, "keys can't be null");

    return new Result<Rows<N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<Rows<N,V>>() {
        
          public Rows<N,V> doInKeyspace(Keyspace ks) throws HectorException {
            List<byte[]> keysList = new ArrayList<byte[]>();
            keysList.addAll(keys);
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<byte[], List<Column>> thriftRet =
              ks.multigetSlice(keysList, columnParent, getPredicate());
            return new Rows<N,V>(thriftRet, columnNameExtractor, valueExtractor);
          }
        }), this);
  }


  public String toString() {
    return "MultigetSliceQuery(" + keys + "," + super.toStringInternal() + ")";
  }
}
