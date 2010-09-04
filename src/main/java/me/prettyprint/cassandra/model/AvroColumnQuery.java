package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;
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

  public AvroColumnQuery(KeyspaceOperator keyspaceOperator, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(keyspaceOperator, nameSerializer, valueSerializer);
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
