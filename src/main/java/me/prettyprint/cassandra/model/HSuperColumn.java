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
  private final Serializer<SN> superNameExtractor;
  private final Serializer<N> nameExtractor;
  private final Serializer<V> valueExtractor;

  /**
   * @param <SN> SuperColumn name type
   * @param List<HColumn<N,V>> Column values
   * @param Extractor<SN> the extractor type
   * @param timestamp
   */
  /*package*/ HSuperColumn(SN sName, List<HColumn<N, V>> columns, long timestamp,
      Serializer<SN> sNameExtractor, Serializer<N> nameExtractor, Serializer<V> valueExtractor) {
    this(sNameExtractor, nameExtractor, valueExtractor);
    notNull(sName, "Name is null");
    notNull(columns, "Columns are null");
    this.superName = sName;
    this.columns = columns;
    this.timestamp = timestamp;
  }

  /*package*/ HSuperColumn(SuperColumn thriftSuperColumn, Serializer<SN> sNameExtractor,
      Serializer<N> nameExtractor, Serializer<V> valueExtractor) {
    this(sNameExtractor, nameExtractor, valueExtractor);
    noneNull(thriftSuperColumn, sNameExtractor, nameExtractor, valueExtractor);
    superName = sNameExtractor.fromBytes(thriftSuperColumn.getName());
    columns = fromThriftColumns(thriftSuperColumn.getColumns());
  }

  /*package*/ HSuperColumn(Serializer<SN> sNameExtractor, Serializer<N> nameExtractor,
      Serializer<V> valueExtractor) {
    noneNull(sNameExtractor, nameExtractor, valueExtractor);
    this.superNameExtractor = sNameExtractor;
    this.nameExtractor = nameExtractor;
    this.valueExtractor = valueExtractor;
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

  public Serializer<SN> getNameExtractor() {
    return superNameExtractor;
  }

  public byte[] getNameBytes() {
    return superNameExtractor.toBytes(getName());
  }

  public SuperColumn toThrift() {
    if (superName == null || columns == null) {
      return null;
    }
    return new SuperColumn(superNameExtractor.toBytes(superName), toThriftColumn());
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
      cs.add(new HColumn<N, V>(c, nameExtractor, valueExtractor));
    }
    return cs;
  }

  public Serializer<SN> getSuperNameExtractor() {
    return superNameExtractor;
  }

  public Serializer<V> getValueExtractor() {
    return valueExtractor;
  }

  @Override
  public String toString() {
    return String.format("HSuperColumn(%s,%s)", superName, columns);
  }
}
