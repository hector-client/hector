package me.prettyprint.cassandra.model;

import java.util.Arrays;
import java.util.Collection;

// multiget_slice. returns Rows
/**
 * A query wrapper for the thrift call multiget_slice
 */
@SuppressWarnings("unchecked")
public class MultigetSliceQuery<N,V> extends AbstractSliceQuery<N,V,Rows<N,V>> {

  private Collection<String> keys;

  /*package*/ MultigetSliceQuery(KeyspaceOperator ko, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, nameExtractor, valueExtractor);
  }

  public MultigetSliceQuery<N,V> setKeys(String... keys) {
    this.keys = Arrays.asList(keys);
    return this;
  }

  @Override
  public Result<Rows<N, V>> execute() {
    //TODO
    return null;
  }

}
