package me.prettyprint.cassandra.service.template;

import java.util.List;

import me.prettyprint.cassandra.model.ColumnQuery;
import me.prettyprint.cassandra.model.ConsistencyLevelPolicy;
import me.prettyprint.cassandra.model.CountQuery;
import me.prettyprint.cassandra.model.HColumn;
import me.prettyprint.cassandra.model.HSuperColumn;
import me.prettyprint.cassandra.model.KeyspaceOperator;
import me.prettyprint.cassandra.model.MultigetSliceQuery;
import me.prettyprint.cassandra.model.MultigetSubSliceQuery;
import me.prettyprint.cassandra.model.MultigetSuperSliceQuery;
import me.prettyprint.cassandra.model.Mutator;
import me.prettyprint.cassandra.model.RangeSlicesQuery;
import me.prettyprint.cassandra.model.RangeSubSlicesQuery;
import me.prettyprint.cassandra.model.RangeSuperSlicesQuery;
import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.cassandra.model.SliceQuery;
import me.prettyprint.cassandra.model.SubCountQuery;
import me.prettyprint.cassandra.model.SubSliceQuery;
import me.prettyprint.cassandra.model.SuperColumnQuery;
import me.prettyprint.cassandra.model.SuperCountQuery;
import me.prettyprint.cassandra.model.SuperSliceQuery;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.cassandra.service.Cluster;

import org.apache.cassandra.thrift.Clock;

/**
 * The main interface used to operate with the underlying database.
 *
 * The interface is mirroring HFactory.
 *
 * @author Bozhidar Bozhanov
 *
 */
public interface HectorTemplate {

    /**
     * Creates a KeyspaceOperator with the default consistency level policy.
     *
     * @param keyspace
     * @param cluster
     * @return
     */
    KeyspaceOperator createKeyspaceOperator(Cluster cluster);

    KeyspaceOperator createKeyspaceOperator(Cluster cluster, ConsistencyLevelPolicy consistencyLevelPolicy);

    <K, N, V> Mutator<K> createMutator(Serializer<K> keySerializer);

    <K, N, V> Mutator<K> createMutator();

    <K, N, V> ColumnQuery<K, N, V> createColumnQuery();

    <K, N, V> ColumnQuery<K, N, V> createColumnQuery(Serializer<V> valueSErializer);

    <K, N> CountQuery<K, N> createCountQuery(
            Serializer<K> keySerializer,
            Serializer<N> nameSerializer);

    <K, SN> SuperCountQuery<K, SN> createSuperCountQuery(
            Serializer<K> keySerializer,
            Serializer<SN> superNameSerializer);

    <K, SN, N> SubCountQuery<K, SN, N> createSubCountQuery(
            Serializer<K> keySerializer,
            Serializer<SN> superNameSerializer, Serializer<N> nameSerializer);

    <K, SN, N, V> SuperColumnQuery<K, SN, N, V> createSuperColumnQuery(
            Serializer<K> keySerializer,
            Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
            Serializer<V> valueSerializer);

    <K, N, V> MultigetSliceQuery<K, N, V> createMultigetSliceQuery(
            Serializer<K> keySerializer,
            Serializer<N> nameSerializer, Serializer<V> valueSerializer);

    <K, SN, N, V> MultigetSuperSliceQuery<K, SN, N, V> createMultigetSuperSliceQuery(
            Serializer<K> keySerializer,
            Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
            Serializer<V> valueSerializer);

    <K, SN, N, V> MultigetSubSliceQuery<K, SN, N, V> createMultigetSubSliceQuery(
            Serializer<K> keySerializer,
            Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
            Serializer<V> valueSerializer);

    <K, N, V> RangeSlicesQuery<K, N, V> createRangeSlicesQuery(
            Serializer<K> keySerializer,
            Serializer<N> nameSerializer, Serializer<V> valueSerializer);

    <K, SN, N, V> RangeSuperSlicesQuery<K, SN, N, V> createRangeSuperSlicesQuery(
            Serializer<K> keySerializer,
            Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
            Serializer<V> valueSerializer);

    <K, SN, N, V> RangeSubSlicesQuery<K, SN, N, V> createRangeSubSlicesQuery(
            Serializer<K> keySerializer,
            Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
            Serializer<V> valueSerializer);

    <K, N, V> SliceQuery<K, N, V> createSliceQuery(
            Serializer<K> keySerializer,
            Serializer<N> nameSerializer, Serializer<V> valueSerializer);

    <K, SN, N, V> SubSliceQuery<K, SN, N, V> createSubSliceQuery(
            Serializer<K> keySerializer,
            Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
            Serializer<V> valueSerializer);

    <K, SN, N, V> SuperSliceQuery<K, SN, N, V> createSuperSliceQuery(
            Serializer<K> keySerializer,
            Serializer<SN> sNameSerializer, Serializer<N> nameSerializer,
            Serializer<V> valueSerializer);

    /**
     * createSuperColumn accepts a variable number of column arguments
     *
     * @param name
     *            supercolumn name
     * @param createColumn
     *            a variable number of column arguments
     */
    <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(
            SN name, List<HColumn<N, V>> columns,
            Serializer<SN> superNameSerializer, Serializer<N> nameSerializer,
            Serializer<V> valueSerializer);

    <SN, N, V> HSuperColumn<SN, N, V> createSuperColumn(
            SN name, List<HColumn<N, V>> columns, Clock clock,
            Serializer<SN> superNameSerializer, Serializer<N> nameSerializer,
            Serializer<V> valueSerializer);

    <N, V> HColumn<N, V> createColumn(N name, V value,
            Clock clock, Serializer<N> nameSerializer,
            Serializer<V> valueSerializer);

    /**
     * Creates a column with the clock of now.
     */
    <N, V> HColumn<N, V> createColumn(N name, V value);

    /**
     * Creates a clock of now with the default clock resolution (micorosec) as
     * defined in {@link CassandraHost}
     */
    Clock createClock();

    Cluster getCluster();

    HectorTemplateFactory getFactory();
}