package me.prettyprint.cassandra.model.avro;

import me.prettyprint.cassandra.model.AbstractColumnQuery;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
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
public final class AvroColumnQuery<N, V> extends AbstractColumnQuery<N, V>
    implements ColumnQuery<N, V> {

  public AvroColumnQuery(Keyspace keyspace, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(keyspace, nameSerializer, valueSerializer);
  }

  @Override
  public Result<HColumn<N, V>> execute() {
    return new Result<HColumn<N, V>>(keyspace.doExecute(
        new KeyspaceOperationCallback<HColumn<N, V>>() {
          @Override
          public HColumn<N, V> doInKeyspace(KeyspaceService ks) throws HectorException {
            //TODO: not implemented yet.
            return null;
          }
        }), this);
  }
}
