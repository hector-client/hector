package me.prettyprint.cassandra.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.Keyspace;
import me.prettyprint.cassandra.utils.Assert;

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
  
  IndexedSlicesQuery(KeyspaceOperator ko, Serializer<K> keySerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, keySerializer, nameSerializer, valueSerializer);
    indexClause = new IndexClause();
  }
  
  public IndexedSlicesQuery<K,N,V> addEqualsExpression(N columnName, V columnValue) {
    indexClause.addToExpressions(new IndexExpression(columnNameSerializer.toBytes(columnName), 
        IndexOperator.EQ, 
        valueSerializer.toBytes(columnValue)));
    return this;
  }
  
  public IndexedSlicesQuery<K,N,V> setStartKey(K startKey) {
    indexClause.setStart_key(keySerializer.toBytes(startKey));
    return this;
  }
  
  public Result<OrderedRows<K,N, V>> execute() {    

    return new Result<OrderedRows<K,N,V>>(keyspaceOperator.doExecute(
        new KeyspaceOperationCallback<OrderedRows<K,N,V>>() {
          @Override
          public OrderedRows<K,N,V> doInKeyspace(Keyspace ks) throws HectorException {
            ColumnParent columnParent = new ColumnParent(columnFamilyName);
            Map<K, List<Column>> thriftRet = keySerializer.fromBytesMap(
                ks.getIndexedSlices(columnParent, indexClause, getPredicate()));
            return new OrderedRows<K,N,V>((LinkedHashMap<K, List<Column>>) thriftRet, columnNameSerializer, valueSerializer);
          }
        }), this);
  }  

}
