package me.prettyprint.cassandra.model;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.Operation;
import me.prettyprint.cassandra.service.OperationType;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.query.QueryResult;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.Compression;
import org.apache.cassandra.thrift.CqlResult;
import org.apache.cassandra.thrift.CqlRow;
import org.apache.cassandra.thrift.Cassandra.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CqlQuery<K, N, V> extends AbstractBasicQuery<K, N, CqlRows<K,N,V>> {
  private static Logger log = LoggerFactory.getLogger(CqlQuery.class);
  
  private Serializer<V> valueSerializer;
  private String query;
  private boolean useCompression;
  
  public CqlQuery(Keyspace k, Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(k, keySerializer, nameSerializer);
    this.valueSerializer = valueSerializer;
  }
  
  public CqlQuery<K, N, V> setQuery(String query) {
    this.query = query;
    return this;
  }
  
  public CqlQuery<K, N, V> useCompression() {
    useCompression = true;
    return this;
  }
  
  
  @Override
  public QueryResult<CqlRows<K, N, V>> execute() {
    
    return new QueryResultImpl<CqlRows<K, N, V>>(
        keyspace.doExecuteOperation(new Operation<CqlRows<K, N, V>>(OperationType.READ) {

          @Override
          public CqlRows<K, N, V> execute(Client cassandra) throws HectorException {
            CqlRows<K, N, V> rows = null;
            try {
              CqlResult result = cassandra.execute_cql_query(StringSerializer.get().toByteBuffer(query), 
                  useCompression ? Compression.GZIP : Compression.NONE);
              if ( log.isDebugEnabled() ) {
                log.debug("Found CqlResult: {}", result);
              }
              switch (result.getType()) {
              case INT:
                rows = new CqlRows<K, N, V>(result.getNum());
                break;
              case VOID:
                rows = new CqlRows<K, N, V>();
                break;

              default:
                if ( result.getRowsSize() > 0 ) {
                  LinkedHashMap<ByteBuffer, List<Column>> ret = new LinkedHashMap<ByteBuffer, List<Column>>(result.getRowsSize());
                  
                  for (Iterator<CqlRow> rowsIter = result.getRowsIterator(); rowsIter.hasNext(); ) {
                    CqlRow row = rowsIter.next();
                    ret.put(ByteBuffer.wrap(row.getKey()), row.getColumns());
                  }
                  Map<K, List<Column>> thriftRet = keySerializer.fromBytesMap(ret);
                  rows = new CqlRows<K, N, V>((LinkedHashMap<K, List<Column>>)thriftRet, columnNameSerializer, valueSerializer);
                }
                break;
              }
            } catch (Exception ex) {
              throw keyspace.getExceptionsTranslator().translate(ex);
            }
            return rows;
          }
        
        }), this);
  }

}
