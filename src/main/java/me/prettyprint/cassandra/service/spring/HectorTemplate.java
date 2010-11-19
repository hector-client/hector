/**
 * 
 */
package me.prettyprint.cassandra.service.spring;

import java.util.List;

import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.CountQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.MultigetSubSliceQuery;
import me.prettyprint.hector.api.query.MultigetSuperSliceQuery;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.RangeSubSlicesQuery;
import me.prettyprint.hector.api.query.RangeSuperSlicesQuery;
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
 * @author hadolphs
 * 
 */
public interface HectorTemplate {

  <N, V> Mutator createMutator();

  <N, V> ColumnQuery<N, V> createColumnQuery(Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer);

  CountQuery createCountQuery();

  SuperCountQuery createSuperCountQuery();

  <SN> SubCountQuery<SN> createSubCountQuery(Serializer<SN> superNameSerializer);

  <SN, N, V> SuperColumnQuery<SN, N, V> createSuperColumnQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer);

  <N, V> MultigetSliceQuery<N, V> createMultigetSliceQuery(
	  Serializer<N> nameSerializer, Serializer<V> valueSerializer);

  <SN, N, V> MultigetSuperSliceQuery<SN, N, V> createMultigetSuperSliceQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer);

  <SN, N, V> MultigetSubSliceQuery<SN, N, V> createMultigetSubSliceQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer);

  <N, V> RangeSlicesQuery<N, V> createRangeSlicesQuery(
	  Serializer<N> nameSerializer, Serializer<V> valueSerializer);

  <SN, N, V> RangeSuperSlicesQuery<SN, N, V> createRangeSuperSlicesQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer);

  <SN, N, V> RangeSubSlicesQuery<SN, N, V> createRangeSubSlicesQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer);

  <N, V> SliceQuery<N, V> createSliceQuery(Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer);
  
  SliceQuery<byte[], byte[]> createSliceQuery();

  <SN, N, V> SubSliceQuery<SN, N, V> createSubSliceQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer);

  <SN, N, V> SuperSliceQuery<SN, N, V> createSuperSliceQuery(
	  Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer);
  
  SuperSliceQuery<byte[], byte[], byte[]> createSuperSliceQuery();
  
  /**
   * createSuperColumn accepts a variable number of column arguments
   * 
   * @param name
   *          supercolumn name
   * @param createColumn
   *          a variable number of column arguments
   */
  <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name,
	  List<HColumn<N, V>> columns, Serializer<SN> superNameSerializer,
	  Serializer<N> nameSerializer, Serializer<V> valueSerializer);

  <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name,
	  List<HColumn<N, V>> columns);

  <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(SN name,
	  List<HColumn<N, V>> columns, long clock,
	  Serializer<SN> superNameSerializer, Serializer<N> nameSerializer,
	  Serializer<V> valueSerializer);

  <N, V> HColumn<N, V> createColumn(N name, V value, long clock,
	  Serializer<N> nameSerializer, Serializer<V> valueSerializer);

  /**
   * Creates a column with the clock of now.
   */
  <N, V> HColumn<N, V> createColumn(N name, V value,
	  Serializer<N> nameSerializer, Serializer<V> valueSerializer);

  /**
   * Creates a timestamp of now with the default timestamp resolution
   * (micorosec) as defined in {@link CassandraHost}
   */
  long createTimestamp();

  Cluster getCluster();

  String getReplicationStrategyClass();

  int getReplicationFactor();

  String getKeyspaceName();
}