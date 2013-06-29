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

/**
 * First cut at a CQL implementation. Not too much time has been spent here
 * as this API is currently a moving target. We have a lot of experience with
 * these hijinks by the Apache Cassandra team, so it was deemed prudent to do
 * something simple initially until the dust settles. 
 * 
 * You are expected to know what you are getting into if you plan on using
 * CQL queries in your application. Spend some time looking through the 
 * unit tests here in Hector and the Cassandra source tree. For a number of 
 * detailed examples, see test_cql.py in the test/system folder of the
 * Apache Cassandra source distribution.
 * 
 * Note: if you immediately get an exception such as:
 * "InvalidRequestException(why:cannot parse 'foo' as hex bytes)"
 * It means one of two things:
 * <ol>
 * <li>you have not formatted your query correct</li>
 * <li>You have not configured the correct validators on your column family</li>
 * </ol>
 * 
 * In both cases, even though the query is most likely a string, it is up to you to format
 * this query according to the comparator (used for the column name), key validator 
 * and value validator. This can be a little confusing as only the comparator is required.
 * The other two default to BytesType.
 * 
 * See the docs on {@link CqlRows} for additional details.
 * 
 * @author zznate
 *
 */
public class CqlQuery<K, N, V> extends AbstractBasicQuery<K, N, CqlRows<K,N,V>> {
  private static Logger log = LoggerFactory.getLogger(CqlQuery.class);
  
  private Serializer<V> valueSerializer;
  private ByteBuffer query;
  private boolean useCompression;
  private boolean suppressKeyInColumns;
  
  public CqlQuery(Keyspace k, Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(k, keySerializer, nameSerializer);
    this.valueSerializer = valueSerializer;
  }
  
  /**
   * Set the query as a String. Here for convienience. See above for some
   * caveats. Calls {@link StringSerializer#toByteBuffer(String)} directly.
   * @param query
   * @return
   */
  public CqlQuery<K, N, V> setQuery(String query) {
    this.query = StringSerializer.get().toByteBuffer(query);
    return this;
  }
  
  public CqlQuery<K, N, V> setQuery(ByteBuffer qeury) {
    this.query = qeury;
    return this;
  }

  @Override
  public CqlQuery<K, N, V> setCqlVersion(String version){
    this.cqlVersion=version;
    return this;
  }
  
  public CqlQuery<K, N, V> setSuppressKeyInColumns(boolean suppressKeyInColumns) {
    this.suppressKeyInColumns = suppressKeyInColumns;
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
              if (cqlVersion != null) {
                  cassandra.set_cql_version(cqlVersion);
              }
              CqlResult result = cassandra.execute_cql_query(query, 
                  useCompression ? Compression.GZIP : Compression.NONE);
              if ( log.isDebugEnabled() ) {
                log.debug("Found CqlResult: {}", result);
              }
              switch (result.getType()) {
              case VOID:
                rows = new CqlRows<K, N, V>();
                break;

              default:
                if ( result.getRowsSize() > 0 ) {
                  LinkedHashMap<ByteBuffer, List<Column>> ret = new LinkedHashMap<ByteBuffer, List<Column>>(result.getRowsSize());
                  
                  for (Iterator<CqlRow> rowsIter = result.getRowsIterator(); rowsIter.hasNext(); ) {
                    CqlRow row = rowsIter.next();
                    ret.put(ByteBuffer.wrap(row.getKey()), filterKeyColumn(row));
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
  
  /*
   * Trims the first column from the row if it's name is equal to "KEY"
   */
  private List<Column> filterKeyColumn(CqlRow row) {
    if ( suppressKeyInColumns && row.isSetColumns() && row.columns.size() > 0) {
      Iterator<Column> columnsIterator = row.getColumnsIterator();
      Column column = columnsIterator.next();
      if ( column.name.duplicate().equals(KEY_BB) ) {
        columnsIterator.remove();  
      }      
    }
    return row.getColumns();
  }

  private static ByteBuffer KEY_BB = StringSerializer.get().toByteBuffer("KEY");
}
