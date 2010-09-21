package me.prettyprint.cassandra.model.thrift;

import me.prettyprint.cassandra.model.AbstractColumnQuery;
import me.prettyprint.cassandra.model.HColumnImpl;
import me.prettyprint.cassandra.model.KeyspaceOperationCallback;
import me.prettyprint.cassandra.model.QueryResultImpl;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.exceptions.HNotFoundException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;

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

  public ThriftColumnQuery(Keyspace keyspace, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    super(keyspace, nameSerializer, valueSerializer);
  }

  @Override
  public QueryResult<HColumn<N, V>> execute() {
    return new QueryResultImpl<HColumn<N, V>>(keyspace.doExecute(
        new KeyspaceOperationCallback<HColumn<N, V>>() {
          @Override
          public HColumn<N, V> doInKeyspace(KeyspaceService ks) throws HectorException {
            try {
              org.apache.cassandra.thrift.Column thriftColumn =
                ks.getColumn(key, ThriftFactory.createColumnPath(columnFamilyName, name, columnNameSerializer));
              return new HColumnImpl<N, V>(thriftColumn, columnNameSerializer, valueSerializer);
            } catch (HNotFoundException e) {
              return null;
            }
          }
        }), this);
  }
}
