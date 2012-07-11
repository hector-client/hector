package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.utils.Assert.noneNull;
import static me.prettyprint.cassandra.utils.Assert.notNull;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.beans.HCounterSuperColumn;

import org.apache.cassandra.thrift.CounterColumn;
import org.apache.cassandra.thrift.CounterSuperColumn;

/**
 * Models a CounterSuperColumn in a protocol independent manner.
 *
 * @param <SN>
 *          CounterSuperColumn name type
 * @param <N>
 *          CounterColumn name type

 *
 * @author patricioe
 */
public final class HCounterSuperColumnImpl<SN,N> implements HCounterSuperColumn<SN, N> {

  private SN superName;
  private List<HCounterColumn<N>> counterColumns;
  private final Serializer<SN> superNameSerializer;
  private final Serializer<N> nameSerializer;

  /**
   * @param sName<SN> CounterSuperColumn name type
   * @param List<HCounterColumn<N,V>> CounterColumn values
   * @param Serializer<SN> the serializer type
   */
  public HCounterSuperColumnImpl(SN sName, List<HCounterColumn<N>> counterColumns,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer) {
    this(sNameSerializer, nameSerializer);
    notNull(sName, "Name is null");
    notNull(counterColumns, "Columns are null");
    this.superName = sName;
    this.counterColumns = counterColumns;
  }

  public HCounterSuperColumnImpl(CounterSuperColumn thriftCounterSuperColumn, Serializer<SN> sNameSerializer,
      Serializer<N> nameSerializer) {
    this(sNameSerializer, nameSerializer);
    noneNull(thriftCounterSuperColumn, sNameSerializer, nameSerializer);
    superName = sNameSerializer.fromByteBuffer(ByteBuffer.wrap(thriftCounterSuperColumn.getName()));
    counterColumns = fromThriftColumns(thriftCounterSuperColumn.getColumns());
  }

  public HCounterSuperColumnImpl(Serializer<SN> sNameSerializer, Serializer<N> nameSerializer) {
    noneNull(sNameSerializer, nameSerializer);
    this.superNameSerializer = sNameSerializer;
    this.nameSerializer = nameSerializer;
  }

  public HCounterSuperColumn<SN, N> addSubCounterColumn(HCounterColumn<N> counterColumn) {
    if ( counterColumns == null )
      counterColumns = new ArrayList<HCounterColumn<N>>();
    counterColumns.add(counterColumn);
    return this;
  }

  @Override
  public HCounterSuperColumn<SN, N> setName(SN name) {
    notNull(name, "name is null");
    this.superName = name;
    return this;
  }

  @Override
  public HCounterSuperColumn<SN, N> setSubcolumns(List<HCounterColumn<N>> counterSubcolumns) {
    notNull(counterSubcolumns, "subcolumns are null");
    this.counterColumns = counterSubcolumns;
    return this;
  }

  @Override
  public int getSize() {
    return counterColumns == null ? 0 : counterColumns.size();
  }

  @Override
  public SN getName() {
    return superName;
  }

  /**
   *
   * @return an unmodifiable list of counterColumns
   */
  @Override
  public List<HCounterColumn<N>> getColumns() {
    return counterColumns;
  }

  @Override
  public HCounterColumn<N> get(int i) {
    return counterColumns.get(i);
  }

  @Override
  public Serializer<SN> getNameSerializer() {
    return superNameSerializer;
  }

  @Override
  public byte[] getNameBytes() {
    return superNameSerializer.toByteBuffer(getName()).array();
  }
  
  public ByteBuffer getNameByteBuffer() {
    return superNameSerializer.toByteBuffer(getName());
  }

  public CounterSuperColumn toThrift() {
    if (superName == null || counterColumns == null) {
      return null;
    }
    return new CounterSuperColumn(superNameSerializer.toByteBuffer(superName), toThriftColumn());
  }

  private List<CounterColumn> toThriftColumn() {
    List<CounterColumn> ret = new ArrayList<CounterColumn>(counterColumns.size());
    for (HCounterColumn<N> c: counterColumns) {
      ret.add(((HCounterColumnImpl<N>) c).toThrift());
    }
    return ret;
  }

  private List<HCounterColumn<N>> fromThriftColumns(List<CounterColumn> tcolumns) {
    List<HCounterColumn<N>> cs = new ArrayList<HCounterColumn<N>>(tcolumns.size());
    for (CounterColumn c: tcolumns) {
      cs.add(new HCounterColumnImpl<N>(c, nameSerializer));
    }
    return cs;
  }

  @Override
  public Serializer<SN> getSuperNameSerializer() {
    return superNameSerializer;
  }

  @Override
  public String toString() {
    return String.format("HCounterSuperColumn(%s,%s)", superName, counterColumns);
  }
}
