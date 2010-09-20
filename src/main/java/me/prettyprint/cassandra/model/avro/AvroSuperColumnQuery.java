package me.prettyprint.cassandra.model.avro;

import static me.prettyprint.cassandra.utils.Assert.notNull;
import me.prettyprint.cassandra.model.AbstractSuperColumnQuery;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.query.SuperColumnQuery;

import org.apache.commons.lang.NotImplementedException;

/**
 * Avro implementation of the SuperColumnQuery
 *
 * @author Ran Tavory
 *
 * @param <SN>
 * @param <N>
 * @param <V>
 */
public final class AvroSuperColumnQuery<K, SN,N,V> extends AbstractSuperColumnQuery<K, SN, N, V>
    implements SuperColumnQuery<K, SN, N, V> {

  public AvroSuperColumnQuery(KeyspaceOperator keyspaceOperator,
      Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(keyspaceOperator, keySerializer, sNameSerializer, nameSerializer, valueSerializer);
  }

  @Override
  public Result<HSuperColumn<SN, N, V>> execute() {
    notNull(columnFamilyName, "columnFamilyName is null");
    notNull(superName, "superName is null");
    // TODO: implement
    throw new NotImplementedException();
  }
}
