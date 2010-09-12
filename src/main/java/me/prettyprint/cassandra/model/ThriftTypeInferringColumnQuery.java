package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.model.thrift.ThriftColumnQuery;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.TypeInferringSerializer;

/**
 * The same as ColumnQuery, but dynamically inferring the serializers needed to transform the key
 * and column name to their binary form.
 *
 * @author Bozhidar Bozhanov
 *
 * @param <K> key type
 * @param <N> column name type
 * @param <V> value type
 */
public class ThriftTypeInferringColumnQuery<K, N, V> extends ThriftColumnQuery<K, N, V> {

  public ThriftTypeInferringColumnQuery(KeyspaceOperator keyspaceOperator, Serializer<V> valueSerializer) {
    super(keyspaceOperator, TypeInferringSerializer.<K>get(), TypeInferringSerializer.<N>get(),
        valueSerializer);
  }

  @SuppressWarnings("unchecked")
  public ThriftTypeInferringColumnQuery(KeyspaceOperator keyspaceOperator) {
    super(keyspaceOperator, TypeInferringSerializer.<K> get(), TypeInferringSerializer.<N> get(),
        (Serializer<V>) StringSerializer.get());
  }
}
