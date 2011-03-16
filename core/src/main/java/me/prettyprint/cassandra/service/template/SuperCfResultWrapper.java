package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;

/**
 * Provides access to the current row of data during super column queries. 
 * 
 * This class is a non-static inner class which inherits the Java generic parameters
 * of it's containing CassandraTemplate instance. This allows it to inherit the 
 * <KEY> and <COL> parameter and adds the <SUBCOL> type passed in from whichever
 * query***() method was called.
 * 
 * @author david
 * @since Mar 10, 2011
 * @param <N> the super column's sub column name type
 */
public class SuperCfResultWrapper<K,SN,N> extends AbstractResultWrapper<K,N> implements SuperCfResult<K,SN,N> {


  public SuperCfResultWrapper(Serializer<K> keySerializer,
      Serializer<N> columnNameSerializer) {
    super(keySerializer, columnNameSerializer);
    // TODO Auto-generated constructor stub
  }

  private HSuperColumn<SN,N,ByteBuffer> superColumn;
  private List<HColumn<N,ByteBuffer>> subColumns;
  private Map<N,HColumn<N,ByteBuffer>> subColumnsMap = new HashMap<N,HColumn<N,ByteBuffer>>();

  

  /**
   * Provides access to the current super column name from the mapper objects. This may be
   * needed when the super columm name may be part of the object being mapped into.
   */
  public SN getSuperName()
  {
    return superColumn.getName();
  }

  public ByteBuffer getColumnValue( N columnName)
  {
    HColumn<N,ByteBuffer> col = getColumn( columnName );
    return col != null ? col.getValue() : null;
  }

  private HColumn<N,ByteBuffer> getColumn( N columnName )
  {
    return subColumnsMap.get( columnName );
  }

  @Override
  public K getKey() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean hasNext() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public ColumnFamilyResult<K, N> next() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void remove() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public long getExecutionTimeMicro() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public CassandraHost getHostUsed() {
    // TODO Auto-generated method stub
    return null;
  }
}
