package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;
import me.prettyprint.hector.api.ddl.HCfDef;
import me.prettyprint.hector.api.ddl.HKsDef;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.HectorTransportException;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.IndexClause;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.KeySlice;
import org.apache.cassandra.thrift.KsDef;
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
/* package */class KeyspaceServiceImpl implements KeyspaceService {

  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(KeyspaceServiceImpl.class);

  private CassandraClient client;

  private final String keyspaceName;

  private final HKsDef keyspaceDesc;

  private final ConsistencyLevel consistency;

  private final FailoverPolicy failoverPolicy;

  private final CassandraClientPool clientPools;

  private final CassandraClientMonitor monitor;

  private final ExceptionsTranslator xtrans;

  public KeyspaceServiceImpl(CassandraClient client, String keyspaceName,
      HKsDef keyspaceDesc, ConsistencyLevel consistencyLevel,
      FailoverPolicy failoverPolicy, CassandraClientPool clientPools, CassandraClientMonitor monitor)
      throws HectorTransportException {
    this.client = client;
    this.consistency = consistencyLevel;
    this.keyspaceDesc = keyspaceDesc;
    this.keyspaceName = keyspaceName;
    this.failoverPolicy = failoverPolicy;
    this.clientPools = clientPools;
    this.monitor = monitor;
    xtrans = new ExceptionsTranslatorImpl();
  }


  @Override
  public void batchMutate(final Map<byte[],Map<String,List<Mutation>>> mutationMap)
      throws HectorException {
    Operation<Void> op = new Operation<Void>(OperationType.WRITE) {

      @Override
      public Void execute(Cassandra.Client cassandra) throws HectorException {
        try {
          cassandra.batch_mutate(mutationMap, consistency);
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
  public int getCount(final byte[] key, final ColumnParent columnParent, final SlicePredicate predicate) throws HectorException {
    Operation<Integer> op = new Operation<Integer>(OperationType.READ) {

      @Override
      public Integer execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.get_count(key, columnParent, predicate, consistency);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  private void operateWithFailover(Operation<?> op) throws HectorException {
    FailoverOperator operator = new FailoverOperator(failoverPolicy, monitor, client,
        clientPools, this);
    client = operator.operate(op);
  }



  @Override
  public Map<byte[], List<Column>> getRangeSlices(final ColumnParent columnParent,
      final SlicePredicate predicate, final KeyRange keyRange) throws HectorException {
    Operation<Map<byte[], List<Column>>> op = new Operation<Map<byte[], List<Column>>>(
        OperationType.READ) {

      @Override
      public Map<byte[], List<Column>> execute(Cassandra.Client cassandra)
          throws HectorException {
        try {
          List<KeySlice> keySlices = cassandra.get_range_slices(columnParent,
              predicate, keyRange, consistency);
          if (keySlices == null || keySlices.isEmpty()) {
            return new LinkedHashMap<byte[], List<Column>>(0);
          }
          LinkedHashMap<byte[], List<Column>> ret = new LinkedHashMap<byte[], List<Column>>(
              keySlices.size());
          for (KeySlice keySlice : keySlices) {
            ret.put(keySlice.getKey(), getColumnList(keySlice.getColumns()));
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
  public Map<byte[], List<SuperColumn>> getSuperRangeSlices(
      final ColumnParent columnParent, final SlicePredicate predicate, final KeyRange keyRange)
      throws HectorException {
    Operation<Map<byte[], List<SuperColumn>>> op = new Operation<Map<byte[], List<SuperColumn>>>(
        OperationType.READ) {

      @Override
      public Map<byte[], List<SuperColumn>> execute(Cassandra.Client cassandra)
          throws HectorException {
        try {
          List<KeySlice> keySlices = cassandra.get_range_slices(columnParent,
              predicate, keyRange, consistency);
          if (keySlices == null || keySlices.isEmpty()) {
            return new LinkedHashMap<byte[], List<SuperColumn>>();
          }
          LinkedHashMap<byte[], List<SuperColumn>> ret = new LinkedHashMap<byte[], List<SuperColumn>>(
              keySlices.size());
          for (KeySlice keySlice : keySlices) {
            ret.put(keySlice.getKey(), getSuperColumnList(keySlice.getColumns()));
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
  public List<Column> getSlice(final byte[] key, final ColumnParent columnParent,
      final SlicePredicate predicate) throws HectorException {
    Operation<List<Column>> op = new Operation<List<Column>>(OperationType.READ) {

      @Override
      public List<Column> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          List<ColumnOrSuperColumn> cosclist = cassandra.get_slice(key, columnParent,
              predicate, consistency);

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
      return getSlice(key.getBytes(), columnParent, predicate);
  }

  @Override
  public SuperColumn getSuperColumn(final byte[] key, final ColumnPath columnPath) throws HectorException {
//    valideColumnPath(columnPath);

    Operation<SuperColumn> op = new Operation<SuperColumn>(OperationType.READ) {

      @Override
      public SuperColumn execute(Cassandra.Client cassandra) throws HectorException {
        ColumnOrSuperColumn cosc;
        try {
          cosc = cassandra.get(key, columnPath, consistency);
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
      return getSuperSlice(key.getBytes(), columnParent, predicate);
  }


  @Override
  public SuperColumn getSuperColumn(final byte[] key, final ColumnPath columnPath,
      final boolean reversed, final int size) throws HectorException {
    valideSuperColumnPath(columnPath);
    final SliceRange sliceRange = new SliceRange(new byte[0], new byte[0], reversed, size);
    Operation<SuperColumn> op = new Operation<SuperColumn>(OperationType.READ) {

      @Override
      public SuperColumn execute(Cassandra.Client cassandra) throws HectorException {
        ColumnParent clp = new ColumnParent(columnPath.getColumn_family());
        clp.setSuper_column(columnPath.getSuper_column());

        SlicePredicate sp = new SlicePredicate();
        sp.setSlice_range(sliceRange);

        try {
          List<ColumnOrSuperColumn> cosc = cassandra.get_slice(key, clp, sp,
              consistency);
          if (cosc == null || cosc.isEmpty()) {
            return null;
          }
          return new SuperColumn(columnPath.getSuper_column(), getColumnList(cosc));
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
      return getSuperColumn(key.getBytes(), columnPath);
  }


  @Override
  public List<SuperColumn> getSuperSlice(final byte[] key, final ColumnParent columnParent,
      final SlicePredicate predicate) throws HectorException {
    Operation<List<SuperColumn>> op = new Operation<List<SuperColumn>>(OperationType.READ) {

      @Override
      public List<SuperColumn> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          List<ColumnOrSuperColumn> cosclist = cassandra.get_slice(key, columnParent,
              predicate, consistency);
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
  public void insert(final byte[] key, final ColumnParent columnParent, final Column column) throws HectorException {
    Operation<Void> op = new Operation<Void>(OperationType.WRITE) {

      @Override
      public Void execute(Cassandra.Client cassandra) throws HectorException {
        try {
          cassandra.insert(key, columnParent, column, consistency);
          return null;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
  }

  @Override
  public void insert(String key, ColumnPath columnPath, byte[] value) throws HectorException {
//    valideColumnPath(columnPath);
      ColumnParent columnParent = new ColumnParent(columnPath.getColumn_family());
      if (columnPath.isSetSuper_column()) {
        columnParent.setSuper_column(columnPath.getSuper_column());
      }
      Column column = new Column(columnPath.getColumn(), value, createClock());
      insert(key.getBytes(), columnParent, column);
  }

  @Override
  public void insert(String key, ColumnPath columnPath, byte[] value, long timestamp) throws HectorException {
//    valideColumnPath(columnPath);
      ColumnParent columnParent = new ColumnParent(columnPath.getColumn_family());
      if (columnPath.isSetSuper_column()) {
      columnParent.setSuper_column(columnPath.getSuper_column());
    }
      Column column = new Column(columnPath.getColumn(), value, timestamp);
      insert(key.getBytes(), columnParent, column);
  }


  @Override
  public Map<byte[], List<Column>> multigetSlice(final List<byte[]> keys,
      final ColumnParent columnParent, final SlicePredicate predicate) throws HectorException {
    Operation<Map<byte[], List<Column>>> getCount = new Operation<Map<byte[], List<Column>>>(
        OperationType.READ) {

      @Override
      public Map<byte[], List<Column>> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          Map<byte[], List<ColumnOrSuperColumn>> cfmap = cassandra.multiget_slice(
              keys, columnParent, predicate, consistency);

          Map<byte[], List<Column>> result = new HashMap<byte[], List<Column>>();
          for (Map.Entry<byte[], List<ColumnOrSuperColumn>> entry : cfmap.entrySet()) {
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
  public Map<byte[], SuperColumn> multigetSuperColumn(List<byte[]> keys, ColumnPath columnPath)
      throws HectorException {
    return multigetSuperColumn(keys, columnPath, false, Integer.MAX_VALUE);
  }


  @Override
  public Map<byte[], SuperColumn> multigetSuperColumn(List<byte[]> keys, ColumnPath columnPath,
      boolean reversed, int size) throws HectorException {
    valideSuperColumnPath(columnPath);

    // only can get supercolumn by multigetSuperSlice
    ColumnParent clp = new ColumnParent(columnPath.getColumn_family());
    clp.setSuper_column(columnPath.getSuper_column());

    SliceRange sr = new SliceRange(new byte[0], new byte[0], reversed, size);
    SlicePredicate sp = new SlicePredicate();
    sp.setSlice_range(sr);

    Map<byte[], List<SuperColumn>> sclist = multigetSuperSlice(keys, clp, sp);

    if (sclist == null || sclist.isEmpty()) {
      return Collections.emptyMap();
    }

    Map<byte[], SuperColumn> result = new HashMap<byte[], SuperColumn>(keys.size() * 2);
    for (Map.Entry<byte[], List<SuperColumn>> entry : sclist.entrySet()) {
      List<SuperColumn> sclistByKey = entry.getValue();
      if (sclistByKey.size() > 0) {
        result.put(entry.getKey(), sclistByKey.get(0));
      }
    }
    return result;
  }


  @Override
  public Map<byte[], List<SuperColumn>> multigetSuperSlice(final List<byte[]> keys,
      final ColumnParent columnParent, final SlicePredicate predicate) throws HectorException {
    Operation<Map<byte[], List<SuperColumn>>> getCount = new Operation<Map<byte[], List<SuperColumn>>>(
        OperationType.READ) {

      @Override
      public Map<byte[], List<SuperColumn>> execute(Cassandra.Client cassandra)
          throws HectorException {
        try {
          Map<byte[], List<ColumnOrSuperColumn>> cfmap = cassandra.multiget_slice(
              keys, columnParent, predicate, consistency);
          // if user not given super column name, the multiget_slice will return
          // List
          // filled with
          // super column, if user given a column name, the return List will
          // filled
          // with column,
          // this is a bad interface design.
          if (columnParent.getSuper_column() == null) {
            Map<byte[], List<SuperColumn>> result = new HashMap<byte[], List<SuperColumn>>();
            for (Map.Entry<byte[], List<ColumnOrSuperColumn>> entry : cfmap.entrySet()) {
              result.put(entry.getKey(), getSuperColumnList(entry.getValue()));
            }
            return result;
          } else {
            Map<byte[], List<SuperColumn>> result = new HashMap<byte[], List<SuperColumn>>();
            for (Map.Entry<byte[], List<ColumnOrSuperColumn>> entry : cfmap.entrySet()) {
              SuperColumn spc = new SuperColumn(columnParent.getSuper_column(),
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
  public Map<byte[], List<Column>> getIndexedSlices(final ColumnParent columnParent,
      final IndexClause indexClause,
      final SlicePredicate predicate) throws HectorException {
    Operation<Map<byte[], List<Column>>> op = new Operation<Map<byte[], List<Column>>>(
        OperationType.READ) {

      @Override
      public Map<byte[], List<Column>> execute(Cassandra.Client cassandra)
          throws HectorException {
        try {
          List<KeySlice> keySlices = cassandra.get_indexed_slices(columnParent, indexClause,
              predicate, consistency);
          if (keySlices == null || keySlices.isEmpty()) {
            return new LinkedHashMap<byte[], List<Column>>(0);
          }
          LinkedHashMap<byte[], List<Column>> ret = new LinkedHashMap<byte[], List<Column>>(
              keySlices.size());
          for (KeySlice keySlice : keySlices) {
            ret.put(keySlice.getKey(), getColumnList(keySlice.getColumns()));
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
  public void remove(byte[] key, ColumnPath columnPath) {
    this.remove(key, columnPath, createClock());
  }

  @Override
  public Map<byte[], Integer> multigetCount(final List<byte[]> keys, final ColumnParent columnParent,
      final SlicePredicate slicePredicate) throws HectorException {
    Operation<Map<byte[],Integer>> op = new Operation<Map<byte[],Integer>>(OperationType.READ) {

      @Override
      public Map<byte[], Integer> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.multiget_count(keys, columnParent, slicePredicate, consistency);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public void remove(final byte[] key, final ColumnPath columnPath, final long timestamp)
  throws HectorException {
    Operation<Void> op = new Operation<Void>(OperationType.WRITE) {

      @Override
      public Void execute(Cassandra.Client cassandra) throws HectorException {
        try {
          cassandra.remove(key, columnPath, timestamp, consistency);
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
    remove(key.getBytes(), columnPath);
  }

  /**
   * Same as two argument version, but the caller must specify their own timestamp
   */
  @Override
  public void remove(String key, ColumnPath columnPath, long timestamp) throws HectorException {
    remove(key.getBytes(), columnPath, timestamp);
  }


  @Override
  public String getName() {
    return keyspaceName;
  }


  @Override
  public CassandraClient getClient() {
    return client;
  }


  @Override
  public Column getColumn(final byte[] key, final ColumnPath columnPath) throws HectorException {
//    valideColumnPath(columnPath);

    Operation<Column> op = new Operation<Column>(OperationType.READ) {

      @Override
      public Column execute(Cassandra.Client cassandra) throws HectorException {
        ColumnOrSuperColumn cosc;
        try {
          cosc = cassandra.get(key, columnPath, consistency);
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
      return getColumn(key.getBytes(), columnPath);
  }

  @Override
  public ConsistencyLevel getConsistencyLevel() {
    return consistency;
  }


  @Override
  public long createClock() {
    return client.getClockResolution().createClock();
  }

  private HCfDef getCfDef(String cf) {
      List<HCfDef> cfDefs = keyspaceDesc.getCfDefs();
      if (cfDefs != null) {
          for (HCfDef cfDef: cfDefs) {
              if (cf.equals(cfDef.getName())) {
                  return cfDef;
              }
          }
      }
      return null;
  }

//  /**
//   * Make sure that if the given column path was a Column. Throws an
//   * InvalidRequestException if not.
//   *
//   * @param columnPath
//   * @throws HInvalidRequestException
//   *           if either the column family does not exist or that it's type does
//   *           not match (super)..
//   */
//  private void valideColumnPath(ColumnPath columnPath) throws HInvalidRequestException {
//    String cf = columnPath.getColumn_family();
//    CfDef cfdefine;
//    String errorMsg;
//    if ((cfdefine = getCfDef(cf)) != null) {
//      if (cfdefine.getColumn_type().equals(CF_TYPE_STANDARD) && columnPath.getColumn() != null) {
//        // if the column family is a standard column
//        return;
//      } else if (cfdefine.getColumn_type().equals(CF_TYPE_SUPER)
//          && columnPath.getSuper_column() != null) {
//        // if the column family is a super column and also give the super_column
//        // name
//        return;
//      } else {
//        errorMsg = "Invalid Request for column family " + cf
//            + " Make sure you have the right type";
//      }
//    } else {
//      errorMsg = "The specified column family does not exist: " + cf;
//    }
//    throw new HInvalidRequestException(errorMsg);
//  }

  /**
   * Make sure that the given column path is a SuperColumn in the DB, Throws an
   * exception if it's not.
   *
   * @throws HInvalidRequestException
   */
  private void valideSuperColumnPath(ColumnPath columnPath) throws HInvalidRequestException {
    String cf = columnPath.getColumn_family();
    HCfDef cfdefine;
    if ((cfdefine = getCfDef(cf)) != null && cfdefine.getColumnType().equals(CF_TYPE_SUPER)
        && columnPath.getSuper_column() != null) {
      return;
    }
    throw new HInvalidRequestException(
        "Invalid super column name or super column family does not exist: " + cf);
  }

  private static List<ColumnOrSuperColumn> getSoscList(List<Column> columns) {
    ArrayList<ColumnOrSuperColumn> list = new ArrayList<ColumnOrSuperColumn>(columns.size());
    for (Column col : columns) {
      ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
      columnOrSuperColumn.setColumn(col);
      list.add(columnOrSuperColumn);
    }
    return list;
  }

  private static List<ColumnOrSuperColumn> getSoscSuperList(List<SuperColumn> columns) {
    ArrayList<ColumnOrSuperColumn> list = new ArrayList<ColumnOrSuperColumn>(columns.size());
    for (SuperColumn col : columns) {
      ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
      columnOrSuperColumn.setSuper_column(col);
      list.add(columnOrSuperColumn);
    }
    return list;
  }

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
  public FailoverPolicy getFailoverPolicy() {
    return failoverPolicy;
  }



  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    b.append("KeyspaceImpl<");
    b.append(getClient());
    b.append(">");
    return b.toString();
  }
}
