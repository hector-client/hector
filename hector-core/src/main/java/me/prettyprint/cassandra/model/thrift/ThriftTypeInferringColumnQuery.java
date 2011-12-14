package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.TypeInferringSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;

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

  public ThriftTypeInferringColumnQuery(Keyspace keyspace, Serializer<V> valueSerializer) {
    super(keyspace, TypeInferringSerializer.<K>get(), TypeInferringSerializer.<N>get(),
        valueSerializer);
  }

  @SuppressWarnings("unchecked")
  public ThriftTypeInferringColumnQuery(Keyspace keyspace) {
    super(keyspace, TypeInferringSerializer.<K> get(), TypeInferringSerializer.<N> get(),
        (Serializer<V>) StringSerializer.get());
  }
}
