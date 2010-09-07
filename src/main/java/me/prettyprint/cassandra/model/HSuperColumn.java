package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.noneNull;
import static me.prettyprint.cassandra.utils.Assert.notNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.SuperColumn;

/**
 * Models a SuperColumn in a protocol independant manner
 *
 * @param <SN>
 *          SuperColumn name type
 * @param <N>
 *          Column name type
 * @param <V>
 *          Column value type
 *
 * @author zznate
 */
public final class HSuperColumn<SN,N,V> {

  private SN superName;
  private List<HColumn<N,V>> columns;
  private long timestamp;
  private final Serializer<SN> superNameSerializer;
  private final Serializer<N> nameSerializer;
  private final Serializer<V> valueSerializer;

  /**
   * @param <SN> SuperColumn name type
   * @param List<HColumn<N,V>> Column values
   * @param Serializer<SN> the serializer type
   * @param timestamp
   */
  public HSuperColumn(SN sName, List<HColumn<N, V>> columns, long timestamp,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    this(sNameSerializer, nameSerializer, valueSerializer);
    notNull(sName, "Name is null");
    notNull(columns, "Columns are null");
    this.superName = sName;
    this.columns = columns;
    this.timestamp = timestamp;
  }

  /*package*/ HSuperColumn(SuperColumn thriftSuperColumn, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    this(sNameSerializer, nameSerializer, valueSerializer);
    noneNull(thriftSuperColumn, sNameSerializer, nameSerializer, valueSerializer);
    superName = sNameSerializer.fromBytes(thriftSuperColumn.getName());
    columns = fromThriftColumns(thriftSuperColumn.getColumns());
  }

  /*package*/ HSuperColumn(Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    noneNull(sNameSerializer, nameSerializer, valueSerializer);
    this.superNameSerializer = sNameSerializer;
    this.nameSerializer = nameSerializer;
    this.valueSerializer = valueSerializer;
  }

  public HSuperColumn<SN, N, V> setName(SN name) {
    notNull(name, "name is null");
    this.superName = name;
    return this;
  }

  public HSuperColumn<SN, N, V> setSubcolumns(List<HColumn<N, V>> subcolumns) {
    notNull(subcolumns, "subcolumns are null");
    this.columns = subcolumns;
    return this;
  }

  public HSuperColumn<SN, N, V> setTimestamp(long timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public int getSize() {
    return columns == null ? 0 : columns.size();
  }

  public SN getName() {
    return superName;
  }

  /**
   *
   * @return an unmodifiable list of columns
   */
  public List<HColumn<N,V>> getColumns() {
    return Collections.unmodifiableList(columns);
  }

  public HColumn<N,V> get(int i) {
    return columns.get(i);
  }

  public Serializer<SN> getNameSerializer() {
    return superNameSerializer;
  }

  public byte[] getNameBytes() {
    return superNameSerializer.toBytes(getName());
  }

  public SuperColumn toThrift() {
    if (superName == null || columns == null) {
      return null;
    }
    return new SuperColumn(superNameSerializer.toBytes(superName), toThriftColumn());
  }

  private List<Column> toThriftColumn() {
    List<Column> ret = new ArrayList<Column>(columns.size());
    for (HColumn<N, V> c: columns) {
      ret.add(c.toThrift());
    }
    return ret;
  }

  private List<HColumn<N, V>> fromThriftColumns(List<Column> tcolumns) {
    List<HColumn<N, V>> cs = new ArrayList<HColumn<N,V>>(tcolumns.size());
    for (Column c: tcolumns) {
      cs.add(new HColumn<N, V>(c, nameSerializer, valueSerializer));
    }
    return cs;
  }

  public Serializer<SN> getSuperNameSerializer() {
    return superNameSerializer;
  }

  public Serializer<V> getValueSerializer() {
    return valueSerializer;
  }

  @Override
  public String toString() {
    return String.format("HSuperColumn(%s,%s)", superName, columns);
  }
}
