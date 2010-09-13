package me.prettyprint.cassandra.model.avro;

import me.prettyprint.cassandra.model.AbstractColumnQuery;
import me.prettyprint.cassandra.model.HColumn;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.ColumnQuery;

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

  public AvroColumnQuery(KeyspaceOperator keyspaceOperator, Serializer<K> keySerializer,
      Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(keyspaceOperator, keySerializer, nameSerializer, valueSerializer);
  }

  @Override
  public Result<HColumn<N, V>> execute() {
    return new Result<HColumn<N, V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<HColumn<N, V>>() {
          @Override
          public HColumn<N, V> doInKeyspace(Keyspace ks) throws HectorException {
            //TODO: not implemented yet.
            return null;
          }
        }), this);
  }
}
