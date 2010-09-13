package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.AbstractColumnQuery;
import me.prettyprint.cassandra.model.HColumn;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.Result;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.hector.api.exceptions.HNotFoundException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.ColumnQuery;

/**
 * Thrift implementation of the ColumnQuery type.
 *
 * @author Ran Tavory
 *
 * @param <N> column name type
 * @param <V> value type
 */
public final class ThriftColumnQuery<N, V> extends AbstractColumnQuery<N, V>
    implements ColumnQuery<N, V> {

  public ThriftColumnQuery(KeyspaceOperator keyspaceOperator, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(keyspaceOperator, nameSerializer, valueSerializer);
  }

  @Override
  public Result<HColumn<N, V>> execute() {
    return new Result<HColumn<N, V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<HColumn<N, V>>() {
          @Override
          public HColumn<N, V> doInKeyspace(Keyspace ks) throws HectorException {
            try {
              org.apache.cassandra.thrift.Column thriftColumn =
                ks.getColumn(key, ThriftFactory.createColumnPath(columnFamilyName, name, columnNameSerializer));
              return new HColumn<N, V>(thriftColumn, columnNameSerializer, valueSerializer);
            } catch (HNotFoundException e) {
              return null;
            }
          }
        }), this);
  }
}
