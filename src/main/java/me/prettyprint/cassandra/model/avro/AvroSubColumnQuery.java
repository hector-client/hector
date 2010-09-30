package me.prettyprint.cassandra.model.avro;

import me.prettyprint.cassandra.model.AbstractSubColumnQuery;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.query.SubColumnQuery;

/**
 * Thrift implementation of SubColumnQuery
 * @author Ran Tavory
 *
 * @param <SN> supercolumn name type
 * @param <N> column name type
 * @param <V> column value type
 */
public final class AvroSubColumnQuery<K, SN,N,V> extends AbstractSubColumnQuery<K, SN, N, V>
    implements SubColumnQuery<K, SN, N, V> {

  public AvroSubColumnQuery(Keyspace keyspace,
      Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(keyspace, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

}
