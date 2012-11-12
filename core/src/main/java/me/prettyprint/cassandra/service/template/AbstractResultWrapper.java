package me.prettyprint.cassandra.service.template;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.DoubleSerializer;
import me.prettyprint.cassandra.serializers.FloatSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.ResultStatus;
import me.prettyprint.hector.api.Serializer;

/**
 * Provides access to the current row of data during queries. There is a lot of
 * overlap in needs for both standard and super queries. This class consolidates
 * what they have in common. All data is read into ByteBuffers and translated to
 * a primitive type when requested.
 * 
 * This class is a non-static inner class which inherits the Java generic
 * parameters of it's containing ColumnFamilyTemplate instance. This allows it to
 * inherit the <K> parameter from ColumnFamilyTemplate.
 * 
 * The <N> parameters allows this to be used by standard and super column
 * queries
 * 
 * @author david
 * @author zznate
 * @param <K> 
 *          the type of the key
 * @param <N>
 *          the standard column name type or the super column's child column
 *          type
 */
public abstract class AbstractResultWrapper<K, N> implements ColumnFamilyResult<K, N> {

  protected Serializer<K> keySerializer;
  protected Serializer<N> columnNameSerializer;
  protected ResultStatus resultStatus;
  
  public AbstractResultWrapper(Serializer<K> keySerializer, Serializer<N> columnNameSerializer, ResultStatus resultStatus) {
    this.keySerializer = keySerializer;
    this.columnNameSerializer = columnNameSerializer;
    this.resultStatus = resultStatus;
  }

  public abstract ByteBuffer getColumnValue(N columnName);

  public UUID getUUID(N columnName) {
    return UUIDSerializer.get().fromByteBuffer(getColumnValue(columnName));
  }

  public String getString(N columnName) {
    return StringSerializer.get().fromByteBuffer(getColumnValue(columnName));
  }

  public Long getLong(N columnName) {
    return LongSerializer.get().fromByteBuffer(getColumnValue(columnName));
  }

  public Integer getInteger(N columnName) {
    return IntegerSerializer.get().fromByteBuffer(getColumnValue(columnName));
  }

  public Float getFloat(N columnName) {
    return FloatSerializer.get().fromByteBuffer(getColumnValue(columnName));
  }
  
  public Double getDouble(N columnName) {
    return DoubleSerializer.get().fromByteBuffer(getColumnValue(columnName));
  }

  public Boolean getBoolean(N columnName) {
    return BooleanSerializer.get().fromByteBuffer(getColumnValue(columnName));
  }

  public byte[] getByteArray(N columnName) {
    return BytesArraySerializer.get()
        .fromByteBuffer(getColumnValue(columnName));
  }

  public Date getDate(N columnName) {
    return DateSerializer.get().fromByteBuffer(getColumnValue(columnName));
  }
  
  @Override
  public long getExecutionTimeMicro() {
    return  resultStatus.getExecutionTimeMicro();
  }  
  
  @Override
  public long getExecutionTimeNano() {
    return resultStatus.getExecutionTimeNano();
  }

  @Override
  public CassandraHost getHostUsed() {
    return resultStatus.getHostUsed();
  }
  
}
