package me.prettyprint.cassandra.model;


/**
 * A query for the thrift call get_slice
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
@SuppressWarnings("unchecked")
public class SliceQuery<N,V> extends AbstractSliceQuery<N,V,ColumnSlice<N,V>> {

  SliceQuery(KeyspaceOperator ko, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, nameExtractor, valueExtractor);
  }

  SliceQuery<N,V> setKey(String key) {
    // TODO
    return null;
  }

  @Override
  public Result<ColumnSlice<N, V>> execute() {
    // TODO Auto-generated method stub
    return null;
  }
}
