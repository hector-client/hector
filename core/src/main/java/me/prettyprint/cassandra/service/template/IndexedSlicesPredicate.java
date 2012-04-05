package me.prettyprint.cassandra.service.template;

import me.prettyprint.hector.api.Serializer;

import org.apache.cassandra.thrift.IndexClause;
import org.apache.cassandra.thrift.IndexExpression;
import org.apache.cassandra.thrift.IndexOperator;

/**
 * Predicate wrapping the Thrift IndexClause for the get_indexed_slices call.
 * 
 * @author Jim Ancona (jim@anconafamily.com)
 *
 * @param <K> Key class
 * @param <N> Column name class
 * @param <V> Column value class
 */
public class IndexedSlicesPredicate<K,N,V> {
  private Serializer<K> keySerializer;
  private Serializer<N> nameSerializer;
  private Serializer<V> valueSerializer;
  private IndexClause indexClause = new IndexClause();

  public IndexedSlicesPredicate(Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super();
    this.keySerializer = keySerializer;
    this.nameSerializer = nameSerializer;
    this.valueSerializer = valueSerializer;
  }
  public IndexedSlicesPredicate<K,N,V> count(int count) {
    indexClause.setCount(count);
    return this;
  }
  public IndexedSlicesPredicate<K,N,V> startKey(K key) {
    indexClause.setStart_key(keySerializer.toByteBuffer(key));
    return this;
  }
  public IndexedSlicesPredicate<K,N,V> addExpression(N columnName, IndexOperator op, V value) {
    indexClause.addToExpressions(new IndexExpression(nameSerializer.toByteBuffer(columnName), op, valueSerializer.toByteBuffer(value)));
    return this;
  }
  public IndexClause toThrift() {
    if (!indexClause.isSetStart_key()) {
        indexClause.setStart_key(new byte[0]);
    }
    return indexClause;
  }
}
