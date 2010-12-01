package me.prettyprint.cassandra.service;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.connection.HConnectionManager;
import me.prettyprint.cassandra.model.thrift.ThriftConverter;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.IndexClause;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.KeySlice;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.cassandra.thrift.SuperColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of a Keyspace
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
public class KeyspaceServiceImpl implements KeyspaceService {
  private static final Map<String, String> EMPTY_CREDENTIALS = Collections.emptyMap();

  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(KeyspaceServiceImpl.class);

  private final String keyspaceName;

  private final ConsistencyLevelPolicy consistency;

  private final ExceptionsTranslator xtrans;

  private final HConnectionManager connectionManager;

  private CassandraHost cassandraHost;

  private final FailoverPolicy failoverPolicy;
  
  private final Map<String, String> credentials;

  public KeyspaceServiceImpl(String keyspaceName,
      ConsistencyLevelPolicy consistencyLevel,
      HConnectionManager connectionManager,
      FailoverPolicy failoverPolicy)
      throws HectorTransportException {
    this(keyspaceName, consistencyLevel, connectionManager, failoverPolicy, EMPTY_CREDENTIALS);
  }

  public KeyspaceServiceImpl(String keyspaceName,
      ConsistencyLevelPolicy consistencyLevel,
      HConnectionManager connectionManager,
      FailoverPolicy failoverPolicy,
      Map<String, String> credentials)
      throws HectorTransportException {
    this.consistency = consistencyLevel;
    this.keyspaceName = keyspaceName;
    this.connectionManager = connectionManager;
    this.failoverPolicy = failoverPolicy;
    this.credentials = Collections.unmodifiableMap(credentials);
    xtrans = new ExceptionsTranslatorImpl();
  }


  @Override
  public void batchMutate(final Map<ByteBuffer,Map<String,List<Mutation>>> mutationMap)
      throws HectorException {
    Operation<Void> op = new Operation<Void>(OperationType.WRITE, failoverPolicy, keyspaceName, credentials) {

      @Override
      public Void execute(Cassandra.Client cassandra) throws HectorException {
        try {
          cassandra.batch_mutate(mutationMap, getThriftCl(OperationType.WRITE));
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
        return null;
      }
    };
    operateWithFailover(op);
  }


  @Override
  public void batchMutate(BatchMutation batchMutate) throws HectorException {
    batchMutate(batchMutate.getMutationMap());
  }


  @Override
  public int getCount(final ByteBuffer key, final ColumnParent columnParent, final SlicePredicate predicate) throws HectorException {
    Operation<Integer> op = new Operation<Integer>(OperationType.READ, failoverPolicy, keyspaceName, credentials) {

      @Override
      public Integer execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.get_count(key, columnParent, predicate, getThriftCl(OperationType.READ));
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }


  private void operateWithFailover(Operation<?> op) throws HectorException {
    connectionManager.operateWithFailover(op);
    this.cassandraHost = op.getCassandraHost();
  }

  @Override
  public CassandraHost getCassandraHost() {
    return this.cassandraHost;
  }


  @Override
  public Map<ByteBuffer, List<Column>> getRangeSlices(final ColumnParent columnParent,
      final SlicePredicate predicate, final KeyRange keyRange) throws HectorException {
    Operation<Map<ByteBuffer, List<Column>>> op = new Operation<Map<ByteBuffer, List<Column>>>(
        OperationType.READ, failoverPolicy, keyspaceName, credentials) {

      @Override
      public Map<ByteBuffer, List<Column>> execute(Cassandra.Client cassandra)
          throws HectorException {
        try {
          List<KeySlice> keySlices = cassandra.get_range_slices(columnParent,
              predicate, keyRange, getThriftCl(OperationType.READ));
          if (keySlices == null || keySlices.isEmpty()) {
            return new LinkedHashMap<ByteBuffer, List<Column>>(0);
          }
          LinkedHashMap<ByteBuffer, List<Column>> ret = new LinkedHashMap<ByteBuffer, List<Column>>(
              keySlices.size());
          for (KeySlice keySlice : keySlices) {
            ret.put(ByteBuffer.wrap(keySlice.getKey()), getColumnList(keySlice.getColumns()));
          }
          return ret;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      };
    };
    operateWithFailover(op);
    return op.getResult();
  }



  @Override
  public Map<ByteBuffer, List<SuperColumn>> getSuperRangeSlices(
      final ColumnParent columnParent, final SlicePredicate predicate, final KeyRange keyRange)
      throws HectorException {
    Operation<Map<ByteBuffer, List<SuperColumn>>> op = new Operation<Map<ByteBuffer, List<SuperColumn>>>(
        OperationType.READ, failoverPolicy, keyspaceName, credentials) {

      @Override
      public Map<ByteBuffer, List<SuperColumn>> execute(Cassandra.Client cassandra)
          throws HectorException {
        try {
          List<KeySlice> keySlices = cassandra.get_range_slices(columnParent,
              predicate, keyRange, getThriftCl(OperationType.READ));
          if (keySlices == null || keySlices.isEmpty()) {
            return new LinkedHashMap<ByteBuffer, List<SuperColumn>>();
          }
          LinkedHashMap<ByteBuffer, List<SuperColumn>> ret = new LinkedHashMap<ByteBuffer, List<SuperColumn>>(
              keySlices.size());
          for (KeySlice keySlice : keySlices) {
            ret.put(ByteBuffer.wrap(keySlice.getKey()), getSuperColumnList(keySlice.getColumns()));
          }
          return ret;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }


  @Override
  public List<Column> getSlice(final ByteBuffer key, final ColumnParent columnParent,
      final SlicePredicate predicate) throws HectorException {
    Operation<List<Column>> op = new Operation<List<Column>>(OperationType.READ, failoverPolicy, keyspaceName, credentials) {

      @Override
      public List<Column> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          List<ColumnOrSuperColumn> cosclist = cassandra.get_slice(key, columnParent,
              predicate, getThriftCl(OperationType.READ));

          if (cosclist == null) {
            return null;
          }
          ArrayList<Column> result = new ArrayList<Column>(cosclist.size());
          for (ColumnOrSuperColumn cosc : cosclist) {
            result.add(cosc.getColumn());
          }
          return result;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public List<Column> getSlice(String key, ColumnParent columnParent, SlicePredicate predicate)
  throws HectorException {
      return getSlice(StringSerializer.get().toByteBuffer(key), columnParent, predicate);
  }

  @Override
  public SuperColumn getSuperColumn(final ByteBuffer key, final ColumnPath columnPath) throws HectorException {

    Operation<SuperColumn> op = new Operation<SuperColumn>(OperationType.READ, failoverPolicy, keyspaceName, credentials) {

      @Override
      public SuperColumn execute(Cassandra.Client cassandra) throws HectorException {
        ColumnOrSuperColumn cosc;
        try {
          cosc = cassandra.get(key, columnPath, getThriftCl(OperationType.READ));
        } catch (NotFoundException e) {
          setException(xtrans.translate(e));
          return null;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
        return cosc == null ? null : cosc.getSuper_column();
      }

    };
    operateWithFailover(op);
    if (op.hasException()) {
      throw op.getException();
    }
    return op.getResult();
  }

  @Override
  public List<SuperColumn> getSuperSlice(String key, ColumnParent columnParent,
          SlicePredicate predicate) throws HectorException {
      return getSuperSlice(StringSerializer.get().toByteBuffer(key), columnParent, predicate);
  }


  @Override
  public SuperColumn getSuperColumn(final ByteBuffer key, final ColumnPath columnPath,
      final boolean reversed, final int size) throws HectorException {
    //valideSuperColumnPath(columnPath);
    final SliceRange sliceRange = new SliceRange(ByteBuffer.wrap(new byte[0]),
        ByteBuffer.wrap(new byte[0]), reversed, size);
    Operation<SuperColumn> op = new Operation<SuperColumn>(OperationType.READ, failoverPolicy,
        keyspaceName, credentials) {

      @Override
      public SuperColumn execute(Cassandra.Client cassandra) throws HectorException {
        ColumnParent clp = new ColumnParent(columnPath.getColumn_family());
        clp.setSuper_column(columnPath.getSuper_column());

        SlicePredicate sp = new SlicePredicate();
        sp.setSlice_range(sliceRange);

        try {
          List<ColumnOrSuperColumn> cosc = cassandra.get_slice(key, clp, sp,
              getThriftCl(OperationType.READ));
          if (cosc == null || cosc.isEmpty()) {
            return null;
          }
          return new SuperColumn(ByteBuffer.wrap(columnPath.getSuper_column()), getColumnList(cosc));
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public SuperColumn getSuperColumn(String key, ColumnPath columnPath) throws HectorException {
      return getSuperColumn(StringSerializer.get().toByteBuffer(key), columnPath);
  }


  @Override
  public List<SuperColumn> getSuperSlice(final ByteBuffer key, final ColumnParent columnParent,
      final SlicePredicate predicate) throws HectorException {
    Operation<List<SuperColumn>> op = new Operation<List<SuperColumn>>(OperationType.READ, failoverPolicy, keyspaceName, credentials) {

      @Override
      public List<SuperColumn> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          List<ColumnOrSuperColumn> cosclist = cassandra.get_slice(key, columnParent,
              predicate, getThriftCl(OperationType.READ));
          if (cosclist == null) {
            return null;
          }
          ArrayList<SuperColumn> result = new ArrayList<SuperColumn>(cosclist.size());
          for (ColumnOrSuperColumn cosc : cosclist) {
            result.add(cosc.getSuper_column());
          }
          return result;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }



  @Override
  public void insert(final ByteBuffer key, final ColumnParent columnParent, final Column column) throws HectorException {
    Operation<Void> op = new Operation<Void>(OperationType.WRITE, failoverPolicy, keyspaceName, credentials) {

      @Override
      public Void execute(Cassandra.Client cassandra) throws HectorException {
        try {
          cassandra.insert(key, columnParent, column, getThriftCl(OperationType.WRITE));
          return null;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
  }

  @Override
  public void insert(String key, ColumnPath columnPath, ByteBuffer value) throws HectorException {
//    valideColumnPath(columnPath);
      ColumnParent columnParent = new ColumnParent(columnPath.getColumn_family());
      if (columnPath.isSetSuper_column()) {
        columnParent.setSuper_column(columnPath.getSuper_column());
      }
      Column column = new Column(ByteBuffer.wrap(columnPath.getColumn()), value, connectionManager.createClock());
      insert(StringSerializer.get().toByteBuffer(key), columnParent, column);
  }

  @Override
  public void insert(String key, ColumnPath columnPath, ByteBuffer value, long timestamp) throws HectorException {
//    valideColumnPath(columnPath);
      ColumnParent columnParent = new ColumnParent(columnPath.getColumn_family());
      if (columnPath.isSetSuper_column()) {
      columnParent.setSuper_column(columnPath.getSuper_column());
    }
      Column column = new Column(ByteBuffer.wrap(columnPath.getColumn()), value, timestamp);
      insert(StringSerializer.get().toByteBuffer(key), columnParent, column);
  }


  @Override
  public Map<ByteBuffer, List<Column>> multigetSlice(final List<ByteBuffer> keys,
      final ColumnParent columnParent, final SlicePredicate predicate) throws HectorException {
    Operation<Map<ByteBuffer, List<Column>>> getCount = new Operation<Map<ByteBuffer, List<Column>>>(
        OperationType.READ, failoverPolicy, keyspaceName, credentials) {

      @Override
      public Map<ByteBuffer, List<Column>> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          Map<ByteBuffer, List<ColumnOrSuperColumn>> cfmap = cassandra.multiget_slice(
              keys, columnParent, predicate, getThriftCl(OperationType.READ));

          Map<ByteBuffer, List<Column>> result = new HashMap<ByteBuffer, List<Column>>();
          for (Map.Entry<ByteBuffer, List<ColumnOrSuperColumn>> entry : cfmap.entrySet()) {
            result.put(entry.getKey(), getColumnList(entry.getValue()));
          }
          return result;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(getCount);
    return getCount.getResult();

  }


  @Override
  public Map<ByteBuffer, SuperColumn> multigetSuperColumn(List<ByteBuffer> keys, ColumnPath columnPath)
      throws HectorException {
    return multigetSuperColumn(keys, columnPath, false, Integer.MAX_VALUE);
  }


  @Override
  public Map<ByteBuffer, SuperColumn> multigetSuperColumn(List<ByteBuffer> keys, ColumnPath columnPath,
      boolean reversed, int size) throws HectorException {
    //valideSuperColumnPath(columnPath);

    // only can get supercolumn by multigetSuperSlice
    ColumnParent clp = new ColumnParent(columnPath.getColumn_family());
    clp.setSuper_column(columnPath.getSuper_column());

    SliceRange sr = new SliceRange(ByteBuffer.wrap(new byte[0]), ByteBuffer.wrap(new byte[0]), reversed, size);
    SlicePredicate sp = new SlicePredicate();
    sp.setSlice_range(sr);

    Map<ByteBuffer, List<SuperColumn>> sclist = multigetSuperSlice(keys, clp, sp);

    if (sclist == null || sclist.isEmpty()) {
      return Collections.emptyMap();
    }

    Map<ByteBuffer, SuperColumn> result = new HashMap<ByteBuffer, SuperColumn>(keys.size() * 2);
    for (Map.Entry<ByteBuffer, List<SuperColumn>> entry : sclist.entrySet()) {
      List<SuperColumn> sclistByKey = entry.getValue();
      if (sclistByKey.size() > 0) {
        result.put(entry.getKey(), sclistByKey.get(0));
      }
    }
    return result;
  }


  @Override
  public Map<ByteBuffer, List<SuperColumn>> multigetSuperSlice(final List<ByteBuffer> keys,
      final ColumnParent columnParent, final SlicePredicate predicate) throws HectorException {
    Operation<Map<ByteBuffer, List<SuperColumn>>> getCount = new Operation<Map<ByteBuffer, List<SuperColumn>>>(
        OperationType.READ, failoverPolicy, keyspaceName, credentials) {

      @Override
      public Map<ByteBuffer, List<SuperColumn>> execute(Cassandra.Client cassandra)
          throws HectorException {
        try {
          Map<ByteBuffer, List<ColumnOrSuperColumn>> cfmap = cassandra.multiget_slice(
              keys, columnParent, predicate, getThriftCl(OperationType.READ));
          // if user not given super column name, the multiget_slice will return
          // List
          // filled with
          // super column, if user given a column name, the return List will
          // filled
          // with column,
          // this is a bad interface design.
          if (!columnParent.isSetSuper_column()) {
            Map<ByteBuffer, List<SuperColumn>> result = new HashMap<ByteBuffer, List<SuperColumn>>();
            for (Map.Entry<ByteBuffer, List<ColumnOrSuperColumn>> entry : cfmap.entrySet()) {
              result.put(entry.getKey(), getSuperColumnList(entry.getValue()));
            }
            return result;
          } else {
            Map<ByteBuffer, List<SuperColumn>> result = new HashMap<ByteBuffer, List<SuperColumn>>();
            for (Map.Entry<ByteBuffer, List<ColumnOrSuperColumn>> entry : cfmap.entrySet()) {
              SuperColumn spc = new SuperColumn(ByteBuffer.wrap(columnParent.getSuper_column()),
                  getColumnList(entry.getValue()));
              ArrayList<SuperColumn> spclist = new ArrayList<SuperColumn>(1);
              spclist.add(spc);
              result.put(entry.getKey(), spclist);
            }
            return result;
          }
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(getCount);
    return getCount.getResult();

  }

  @Override
  public Map<ByteBuffer, List<Column>> getIndexedSlices(final ColumnParent columnParent,
      final IndexClause indexClause,
      final SlicePredicate predicate) throws HectorException {
    Operation<Map<ByteBuffer, List<Column>>> op = new Operation<Map<ByteBuffer, List<Column>>>(
        OperationType.READ, failoverPolicy, keyspaceName, credentials) {

      @Override
      public Map<ByteBuffer, List<Column>> execute(Cassandra.Client cassandra)
          throws HectorException {
        try {
          List<KeySlice> keySlices = cassandra.get_indexed_slices(columnParent, indexClause,
              predicate, getThriftCl(OperationType.READ));
          if (keySlices == null || keySlices.isEmpty()) {
            return new LinkedHashMap<ByteBuffer, List<Column>>(0);
          }
          LinkedHashMap<ByteBuffer, List<Column>> ret = new LinkedHashMap<ByteBuffer, List<Column>>(
              keySlices.size());
          for (KeySlice keySlice : keySlices) {
            ret.put(ByteBuffer.wrap(keySlice.getKey()), getColumnList(keySlice.getColumns()));
          }
          return ret;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      };
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public void remove(ByteBuffer key, ColumnPath columnPath) {
    this.remove(key, columnPath, connectionManager.createClock());
  }

  @Override
  public Map<ByteBuffer, Integer> multigetCount(final List<ByteBuffer> keys, final ColumnParent columnParent,
      final SlicePredicate slicePredicate) throws HectorException {
    Operation<Map<ByteBuffer,Integer>> op = new Operation<Map<ByteBuffer,Integer>>(OperationType.READ, failoverPolicy, keyspaceName, credentials) {

      @Override
      public Map<ByteBuffer, Integer> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.multiget_count(keys, columnParent, slicePredicate,
              getThriftCl(OperationType.READ));
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public void remove(final ByteBuffer key, final ColumnPath columnPath, final long timestamp)
  throws HectorException {
    Operation<Void> op = new Operation<Void>(OperationType.WRITE, failoverPolicy, keyspaceName, credentials) {

      @Override
      public Void execute(Cassandra.Client cassandra) throws HectorException {
        try {
          cassandra.remove(key, columnPath, timestamp, getThriftCl(OperationType.WRITE));
          return null;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
  }

  @Override
  public void remove(String key, ColumnPath columnPath) throws HectorException {
    remove(StringSerializer.get().toByteBuffer(key), columnPath);
  }

  /**
   * Same as two argument version, but the caller must specify their own timestamp
   */
  @Override
  public void remove(String key, ColumnPath columnPath, long timestamp) throws HectorException {
    remove(StringSerializer.get().toByteBuffer(key), columnPath, timestamp);
  }


  @Override
  public String getName() {
    return keyspaceName;
  }


  @Override
  public Column getColumn(final ByteBuffer key, final ColumnPath columnPath) throws HectorException {
//    valideColumnPath(columnPath);

    Operation<Column> op = new Operation<Column>(OperationType.READ, failoverPolicy, keyspaceName, credentials) {

      @Override
      public Column execute(Cassandra.Client cassandra) throws HectorException {
        ColumnOrSuperColumn cosc;
        try {
          cosc = cassandra.get(key, columnPath, getThriftCl(OperationType.READ));
        } catch (NotFoundException e) {
          setException(xtrans.translate(e));
          return null;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
        return cosc == null ? null : cosc.getColumn();
      }

    };
    operateWithFailover(op);
    if (op.hasException()) {
      throw op.getException();
    }
    return op.getResult();

  }

  @Override
  public Column getColumn(String key, ColumnPath columnPath) throws HectorException {
      return getColumn(StringSerializer.get().toByteBuffer(key), columnPath);
  }

  @Override
  public HConsistencyLevel getConsistencyLevel(OperationType operationType) {
    return consistency.get(operationType);
  }

  private ConsistencyLevel getThriftCl(OperationType operationType) {
    return ThriftConverter.consistencyLevel(consistency.get(operationType));
  }


//  private static List<ColumnOrSuperColumn> getSoscList(List<Column> columns) {
//    ArrayList<ColumnOrSuperColumn> list = new ArrayList<ColumnOrSuperColumn>(columns.size());
//    for (Column col : columns) {
//      ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
//      columnOrSuperColumn.setColumn(col);
//      list.add(columnOrSuperColumn);
//    }
//    return list;
//  }
//
//  private static List<ColumnOrSuperColumn> getSoscSuperList(List<SuperColumn> columns) {
//    ArrayList<ColumnOrSuperColumn> list = new ArrayList<ColumnOrSuperColumn>(columns.size());
//    for (SuperColumn col : columns) {
//      ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
//      columnOrSuperColumn.setSuper_column(col);
//      list.add(columnOrSuperColumn);
//    }
//    return list;
//  }

  private static List<Column> getColumnList(List<ColumnOrSuperColumn> columns) {
    ArrayList<Column> list = new ArrayList<Column>(columns.size());
    for (ColumnOrSuperColumn col : columns) {
      list.add(col.getColumn());
    }
    return list;
  }

  private static List<SuperColumn> getSuperColumnList(List<ColumnOrSuperColumn> columns) {
    ArrayList<SuperColumn> list = new ArrayList<SuperColumn>(columns.size());
    for (ColumnOrSuperColumn col : columns) {
      list.add(col.getSuper_column());
    }
    return list;
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    b.append("KeyspaceImpl<");
    b.append(keyspaceName);
    b.append(">");
    return b.toString();
  }
}
