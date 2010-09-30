package me.prettyprint.cassandra.model.avro;

import me.prettyprint.cassandra.model.AbstractColumnQuery;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;

/**
 * Avro implementation of the ColumnQuery type.
 *
 * @author Ran Tavory
 *
 * @param <N> column name type
 * @param <V> value type
 */
public final class AvroColumnQuery<K, N, V> extends AbstractColumnQuery<K, N, V>
    implements ColumnQuery<K, N, V> {

  public AvroColumnQuery(Keyspace keyspace, Serializer<K> keySerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(keyspace, keySerializer, nameSerializer, valueSerializer);
  }

  @Override
  public QueryResult<HColumn<N, V>> execute() {
    return new QueryResultImpl<HColumn<N, V>>(keyspace.doExecute(
        new KeyspaceOperationCallback<HColumn<N, V>>() {
          @Override
          public HColumn<N, V> doInKeyspace(KeyspaceService ks) throws HectorException {
            //TODO: not implemented yet.
            return null;
          }
        }), this);
  }
}
