package me.prettyprint.cassandra.model;

import java.util.List;

import me.prettyprint.cassandra.service.Keyspace;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;


/**
 * A query for the thrift call get_slice
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
@SuppressWarnings("unchecked")
public class SliceQuery<N,V> extends AbstractSliceQuery<N,V,ColumnSlice<N,V>> {

  private String key;

  /*package*/ SliceQuery(KeyspaceOperator ko, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, nameExtractor, valueExtractor);
  }

  public SliceQuery<N,V> setKey(String key) {
    this.key = key;
    return this;
  }

  @Override
  public Result<ColumnSlice<N, V>> execute() {
    return new Result<ColumnSlice<N, V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<ColumnSlice<N, V>>() {
          @Override
          public ColumnSlice<N, V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            List<Column> thriftRet = ks.getSlice(key, columnParent, getPredicate());
            return new ColumnSlice<N, V>(thriftRet, columnNameExtractor, valueExtractor);
          }
        }), this);
  }

  @Override
  public String toString() {
    return "SliceQuery(" + key + "," + toStringInternal() + ")";
  }
}
