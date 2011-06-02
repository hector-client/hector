package me.prettyprint.cassandra.model;

import java.util.LinkedHashMap;
import java.util.List;

import me.prettyprint.hector.api.Serializer;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.CqlResultType;

/**
 * Row wrapper specific to the multi-type results capable from a CqlQuery.
 * This is a bit more convoluted than I would like, put most of this API 
 * is still moving around, so we will stick with the overloading for now.
 * 
 * @author zznate
 */
public final class CqlRows<K, N, V> extends OrderedRowsImpl<K, N, V> {

  private final CqlResultType resultType;
  private int count;
  
  /**
   * Constructed for {@link CqlResultType#ROWS}
   * @param thriftRet
   * @param nameSerializer
   * @param valueSerializer
   * @param resultType
   */
  public CqlRows(LinkedHashMap<K, List<Column>> thriftRet,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(thriftRet, nameSerializer, valueSerializer);
    this.resultType = CqlResultType.ROWS;
  }
  
  /**
   * Constructed with only a count for {@link CqlResultType#INT}
   * @param count
   */
  public CqlRows(int count) {
    super();
    this.resultType = CqlResultType.INT;
    this.count = count;
  }
  
  /**
   * Constructed as empty for {@link CqlResultType#VOID}
   */
  public CqlRows() {
    super();
    this.resultType = CqlResultType.VOID;    
  }

  /**
   * Will throw an IllegalArgumentException if called on a query that was not
   * {@link CqlResultType#INT}
   * @return
   */
  public int getAsCount() {
    if ( !resultType.equals(CqlResultType.INT)) 
      throw new IllegalArgumentException("Attempted to extract count from the wrong type of CQL query: " + resultType.toString());
    return count;
  }
  
}
