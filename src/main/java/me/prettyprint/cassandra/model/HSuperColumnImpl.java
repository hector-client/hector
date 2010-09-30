package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.noneNull;
import static me.prettyprint.cassandra.utils.Assert.notNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;

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
public final class HSuperColumnImpl<SN,N,V> implements HSuperColumn<SN, N, V> {

  private SN superName;
  private List<? extends HColumn<N,V>> columns;
  private long clock;
  private final Serializer<SN> superNameSerializer;
  private final Serializer<N> nameSerializer;
  private final Serializer<V> valueSerializer;

  /**
   * @param <SN> SuperColumn name type
   * @param List<HColumn<N,V>> Column values
   * @param Serializer<SN> the serializer type
   * @param clock
   */
  public HSuperColumnImpl(SN sName, List<HColumn<N, V>> columns, long clock,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    this(sNameSerializer, nameSerializer, valueSerializer);
    notNull(sName, "Name is null");
    notNull(columns, "Columns are null");
    this.superName = sName;
    this.columns = columns;
    this.clock = clock;
  }

  public HSuperColumnImpl(SuperColumn thriftSuperColumn, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    this(sNameSerializer, nameSerializer, valueSerializer);
    noneNull(thriftSuperColumn, sNameSerializer, nameSerializer, valueSerializer);
    superName = sNameSerializer.fromBytes(thriftSuperColumn.getName());
    columns = fromThriftColumns(thriftSuperColumn.getColumns());
  }

  /*package*/ HSuperColumnImpl(Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer) {
    noneNull(sNameSerializer, nameSerializer, valueSerializer);
    this.superNameSerializer = sNameSerializer;
    this.nameSerializer = nameSerializer;
    this.valueSerializer = valueSerializer;
  }

  @Override
  public HSuperColumn<SN, N, V> setName(SN name) {
    notNull(name, "name is null");
    this.superName = name;
    return this;
  }

  @Override
  public HSuperColumn<SN, N, V> setSubcolumns(List<HColumn<N, V>> subcolumns) {
    notNull(subcolumns, "subcolumns are null");
    this.columns = subcolumns;
    return this;
  }

  @Override
  public HSuperColumn<SN, N, V> setClock(long clock) {
    this.clock = clock;
    return this;
  }

  @Override
  public long getClock() {
    return clock;
  }

  @Override
  public int getSize() {
    return columns == null ? 0 : columns.size();
  }

  @Override
  public SN getName() {
    return superName;
  }

  /**
   *
   * @return an unmodifiable list of columns
   */
  @Override
  public List<HColumn<N,V>> getColumns() {
    return Collections.unmodifiableList(columns);
  }

  @Override
  public HColumn<N, V> get(int i) {
    return columns.get(i);
  }

  @Override
  public Serializer<SN> getNameSerializer() {
    return superNameSerializer;
  }

  @Override
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
      ret.add(((HColumnImpl<N, V>) c).toThrift());
    }
    return ret;
  }

  private List<HColumn<N, V>> fromThriftColumns(List<Column> tcolumns) {
    List<HColumn<N, V>> cs = new ArrayList<HColumn<N,V>>(tcolumns.size());
    for (Column c: tcolumns) {
      cs.add(new HColumnImpl<N, V>(c, nameSerializer, valueSerializer));
    }
    return cs;
  }

  @Override
  public Serializer<SN> getSuperNameSerializer() {
    return superNameSerializer;
  }

  @Override
  public Serializer<V> getValueSerializer() {
    return valueSerializer;
  }

  @Override
  public String toString() {
    return String.format("HSuperColumn(%s,%s)", superName, columns);
  }
}
