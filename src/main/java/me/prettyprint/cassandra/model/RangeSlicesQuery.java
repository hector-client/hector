package me.prettyprint.cassandra.model;

/**
 * A query for the thrift call get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N>
 * @param <V>
 */
@SuppressWarnings("unchecked")
public class RangeSlicesQuery<N,V> extends AbstractSliceQuery<N,V,OrderedRows<N,V>> {


  RangeSlicesQuery(KeyspaceOperator ko, Extractor<N> nameExtractor, Extractor<V> valueExtractor) {
    super(ko, nameExtractor, valueExtractor);
  }

  @Override
  public Result<OrderedRows<N, V>> execute() {
    // TODO Auto-generated method stub
    return null;
  }

}
