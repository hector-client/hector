package me.prettyprint.cassandra.model.avro;

import static me.prettyprint.cassandra.utils.Assert.notNull;
import me.prettyprint.cassandra.model.AbstractSuperColumnQuery;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.query.QueryResult;
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
public final class AvroSuperColumnQuery<SN,N,V> extends AbstractSuperColumnQuery<SN, N, V>
    implements SuperColumnQuery<SN, N, V> {

  public AvroSuperColumnQuery(Keyspace keyspace,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(keyspace, sNameSerializer, nameSerializer, valueSerializer);
  }

  @Override
  public QueryResult<HSuperColumn<SN, N, V>> execute() {
    notNull(columnFamilyName, "columnFamilyName is null");
    notNull(superName, "superName is null");
    // TODO: implement
    throw new NotImplementedException();
  }
}
