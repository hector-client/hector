package me.prettyprint.cassandra.service.spring;

import java.nio.ByteBuffer;
import java.util.List;

import me.prettyprint.cassandra.model.HCounterColumnImpl;
import me.prettyprint.cassandra.model.IndexedSlicesQuery;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.beans.HCounterSuperColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.CountQuery;
import me.prettyprint.hector.api.query.CounterQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.MultigetSubSliceQuery;
import me.prettyprint.hector.api.query.MultigetSuperSliceQuery;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.RangeSubSlicesQuery;
import me.prettyprint.hector.api.query.RangeSuperSlicesQuery;
import me.prettyprint.hector.api.query.SliceCounterQuery;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.hector.api.query.SubCountQuery;
import me.prettyprint.hector.api.query.SubSliceQuery;
import me.prettyprint.hector.api.query.SuperColumnQuery;
import me.prettyprint.hector.api.query.SuperCountQuery;
import me.prettyprint.hector.api.query.SuperSliceQuery;

/**
 * The main interface used to operate with the underlying database.
 *
 * The interface is mirroring HFactory.
 *
 * @author Bozhidar Bozhanov
 *
 */
public interface HectorTemplate {

  <K, N, V> Mutator<K> createMutator(Serializer<K> keySerializer);

  <K, N, V> Mutator<K> createMutator();

  <K, N, V> ColumnQuery<K, N, V> createColumnQuery();

  <K, N, V> ColumnQuery<K, N, V> createColumnQuery(Serializer<V> valueSErializer);

  <K, N> CountQuery<K, N> createCountQuery(Serializer<K> keySerializer, Serializer<N> nameSerializer);

  <K, SN> SuperCountQuery<K, SN> createSuperCountQuery(Serializer<K> keySerializer,
      Serializer<SN> superNameSerializer);

  <K, SN, N> SubCountQuery<K, SN, N> createSubCountQuery(Serializer<K> keySerializer,
      Serializer<SN> superNameSerializer, Serializer<N> nameSerializer);

  <K, SN, N, V> SuperColumnQuery<K, SN, N, V> createSuperColumnQuery(Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer);

  <K, N, V> MultigetSliceQuery<K, N, V> createMultigetSliceQuery(Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer);

  <K, SN, N, V> MultigetSuperSliceQuery<K, SN, N, V> createMultigetSuperSliceQuery(
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer);

  <K, SN, N, V> MultigetSubSliceQuery<K, SN, N, V> createMultigetSubSliceQuery(
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer);

  <K, N, V> RangeSlicesQuery<K, N, V> createRangeSlicesQuery(Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer);

  <K, SN, N, V> RangeSuperSlicesQuery<K, SN, N, V> createRangeSuperSlicesQuery(
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer);

  <K, SN, N, V> RangeSubSlicesQuery<K, SN, N, V> createRangeSubSlicesQuery(
      Serializer<K> keySerializer, Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer);

  <K, N, V> SliceQuery<K, N, V> createSliceQuery(Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer);

  <K> SliceQuery<K, ByteBuffer, ByteBuffer> createSliceQuery();

  <K, SN, N, V> SubSliceQuery<K, SN, N, V> createSubSliceQuery(Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer);

  <K, SN, N, V> SuperSliceQuery<K, SN, N, V> createSuperSliceQuery(Serializer<K> keySerializer,
      Serializer<SN> sNameSerializer, Serializer<N> nameSerializer, Serializer<V> valueSerializer);

  <K> SuperSliceQuery<K, ByteBuffer, ByteBuffer, ByteBuffer> createSuperSliceQuery();

  <K, N, V> IndexedSlicesQuery<K, N, V> createIndexSlicesQuery(Serializer<K> keySerializer,
      Serializer<N> nameSerializer, Serializer<V> valueSerializer);

  /**
   * createSuperColumn accepts a variable number of column arguments
   *
   * @param name
   *          supercolumn name
   * @param createColumn
   *          a variable number of column arguments
   */
  <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name, List<HColumn<N, V>> columns,
      Serializer<SN> superNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer);

  <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name, List<HColumn<N, V>> columns);

  <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name, List<HColumn<N, V>> columns,
      long clock, Serializer<SN> superNameSerializer, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer);

  <N, V> HColumn<N, V> createColumn(N name, V value, long clock, Serializer<N> nameSerializer,
      Serializer<V> valueSerializer);

  /**
   * Creates a column with the clock of now.
   */
  <N, V> HColumn<N, V> createColumn(N name, V value);
  
  /**
   * Creates a column with the specified <code>name/value</code> and <code>clock</code>.
   */
  <N, V> HColumn<N, V> createColumn(N name, V value, long clock);

  /**
   * Create a counter column with a name and long value
   */
  <N> HCounterColumn<N> createCounterColumn(N name, long value, Serializer<N> nameSerializer);
  
  /**
   * Convenient method for creating a counter column with a String name and long value
   */
  HCounterColumn<String> createCounterColumn(String name, long value);
  
  <K, N> CounterQuery<K, N> createCounterColumnQuery(Serializer<K> keySerializer, Serializer<N> nameSerializer);
  
  <K, N> SliceCounterQuery<K, N> createCounterSliceQuery(Serializer<K> keySerializer, Serializer<N> nameSerializer);
  
  <SN, N> HCounterSuperColumn<SN, N> createCounterSuperColumn(SN name, List<HCounterColumn<N>> columns, 
          Serializer<SN> superNameSerializer, Serializer<N> nameSerializer);
  
  /**
   * Creates a clock of now with the default clock resolution (micorosec) as defined in
   * {@link CassandraHost}
   */
  long createClock();

  Cluster getCluster();

  String getReplicationStrategyClass();

  int getReplicationFactor();

  String getKeyspaceName();
}