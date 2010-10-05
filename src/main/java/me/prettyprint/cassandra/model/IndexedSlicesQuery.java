package me.prettyprint.cassandra.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.IndexClause;
import org.apache.cassandra.thrift.IndexExpression;
import org.apache.cassandra.thrift.IndexOperator;

/**
 * Uses new secondary indexes. Your CF must be configured for such to use this.
 * The following creates an Indexed CF with the "birthday" column indexed
 * (where birthdate represents a timestamp as it is validated by the LongType):
 * <pre>
 *         - name: Indexed1
 *           column_metadata:
 *             - name: birthdate
 *               validator_class: LongType
 *               index_type: KEYS
 *</pre>
 *
 * @author zznate (nate@riptano.com)
 */
public class IndexedSlicesQuery<K,N,V> extends AbstractSliceQuery<K,N,V,OrderedRows<K,N,V>> {

  private final IndexClause indexClause;

  public IndexedSlicesQuery(Keyspace k, Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(k, keySerializer, nameSerializer, valueSerializer);
    indexClause = new IndexClause();
  }

  public IndexedSlicesQuery<K,N,V> addEqualsExpression(N columnName, V columnValue) {
    indexClause.addToExpressions(new IndexExpression(columnNameSerializer.toBytes(columnName),
        IndexOperator.EQ,
        valueSerializer.toBytes(columnValue)));
    return this;
  }
  
  public IndexedSlicesQuery<K,N,V> addLteExpression(N columnName, V columnValue) {
    indexClause.addToExpressions(new IndexExpression(columnNameSerializer.toBytes(columnName),
        IndexOperator.LTE,
        valueSerializer.toBytes(columnValue)));
    return this;
  }
  
  public IndexedSlicesQuery<K,N,V> addGteExpression(N columnName, V columnValue) {
    indexClause.addToExpressions(new IndexExpression(columnNameSerializer.toBytes(columnName),
        IndexOperator.GTE,
        valueSerializer.toBytes(columnValue)));
    return this;
  }
  
  public IndexedSlicesQuery<K,N,V> addLtExpression(N columnName, V columnValue) {
    indexClause.addToExpressions(new IndexExpression(columnNameSerializer.toBytes(columnName),
        IndexOperator.LT,
        valueSerializer.toBytes(columnValue)));
    return this;
  }
  
  public IndexedSlicesQuery<K,N,V> addGtExpression(N columnName, V columnValue) {
    indexClause.addToExpressions(new IndexExpression(columnNameSerializer.toBytes(columnName),
        IndexOperator.GT,
        valueSerializer.toBytes(columnValue)));
    return this;
  }
  
  

  public IndexedSlicesQuery<K,N,V> setStartKey(K startKey) {
    indexClause.setStart_key(keySerializer.toBytes(startKey));
    return this;
  }

  @Override
  public QueryResult<OrderedRows<K,N, V>> execute() {

    return new QueryResultImpl<OrderedRows<K,N,V>>(keyspace.doExecute(
        new KeyspaceOperationCallback<OrderedRows<K,N,V>>() {
          @Override
          public OrderedRows<K,N,V> doInKeyspace(KeyspaceService ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<Column>> thriftRet = keySerializer.fromBytesMap(
                ks.getIndexedSlices(columnParent, indexClause, getPredicate()));
            return new OrderedRowsImpl<K,N,V>((LinkedHashMap<K, List<Column>>) thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }

}
