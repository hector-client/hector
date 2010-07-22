package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.prettyprint.cassandra.model.HectorException;
import me.prettyprint.cassandra.model.HectorTransportException;
import me.prettyprint.cassandra.model.InvalidRequestException;
import me.prettyprint.cassandra.model.PoolExhaustedException;
import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
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
/* package */class KeyspaceImpl implements Keyspace {

  private static final Logger log = LoggerFactory.getLogger(KeyspaceImpl.class);

  private CassandraClient client;

  private final String keyspaceName;

  private final Map<String, Map<String, String>> keyspaceDesc;

  private final ConsistencyLevel consistency;

  private final FailoverPolicy failoverPolicy;

  /** List of all known remote cassandra nodes */
  private List<CassandraHost> knownHosts = new ArrayList<CassandraHost>();

  private final CassandraClientPool clientPools;

  private final CassandraClientMonitor monitor;

  private final ExceptionsTranslator xtrans;

  public KeyspaceImpl(CassandraClient client, String keyspaceName,
      Map<String, Map<String, String>> keyspaceDesc, ConsistencyLevel consistencyLevel,
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
    initFailover();
  }

  @Override
  public void batchInsert(final String key, Map<String, List<Column>> columnMap,
      Map<String, List<SuperColumn>> superColumnMap) throws HectorException {
    if (columnMap == null && superColumnMap == null) {
      throw new InvalidRequestException("columnMap and SuperColumnMap can not be null at same time");
    }

    int size = (columnMap == null ? 0 : columnMap.size())
        + (superColumnMap == null ? 0 : superColumnMap.size());
    final Map<String, List<ColumnOrSuperColumn>> cfmap = new HashMap<String, List<ColumnOrSuperColumn>>(
        size * 2);

    if (columnMap != null) {
      for (Map.Entry<String, List<Column>> entry : columnMap.entrySet()) {
        cfmap.put(entry.getKey(), getSoscList(entry.getValue()));
      }
    }

    if (superColumnMap != null) {
      for (Map.Entry<String, List<SuperColumn>> entry : superColumnMap.entrySet()) {
        cfmap.put(entry.getKey(), getSoscSuperList(entry.getValue()));
      }
    }

    Operation<Void> op = new Operation<Void>(OperationType.WRITE) {
      @Override
      public Void execute(Cassandra.Client cassandra) throws HectorException {
        try {
          cassandra.batch_insert(keyspaceName, key, cfmap, consistency);
          return null;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
  }

  @Override
  public void batchMutate(final Map<String, Map<String, List<Mutation>>> mutationMap)
      throws HectorException {
    Operation<Void> op = new Operation<Void>(OperationType.WRITE) {
      @Override
      public Void execute(Cassandra.Client cassandra) throws HectorException {
        try {
          cassandra.batch_mutate(keyspaceName, mutationMap, consistency);
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
  public int getCount(final String key, final ColumnParent columnParent) throws HectorException {
    Operation<Integer> op = new Operation<Integer>(OperationType.READ) {
      @Override
      public Integer execute(Cassandra.Client cassandra) throws HectorException {
        try {
          return cassandra.get_count(keyspaceName, key, columnParent, consistency);
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  private void operateWithFailover(Operation<?> op) throws HectorException {
    FailoverOperator operator = new FailoverOperator(failoverPolicy, knownHosts, monitor, client,
        clientPools, this);
    client = operator.operate(op);
  }

  @Override
  public Map<String, List<Column>> getRangeSlice(final ColumnParent columnParent,
      final SlicePredicate predicate, final String start, final String finish, final int count)
      throws HectorException {
    Operation<Map<String, List<Column>>> op = new Operation<Map<String, List<Column>>>(
        OperationType.READ) {
      @Override
      public Map<String, List<Column>> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          List<KeySlice> keySlices = cassandra.get_range_slice(keyspaceName, columnParent,
              predicate, start, finish, count, consistency);
          if (keySlices == null || keySlices.isEmpty()) {
            return Collections.emptyMap();
          }
          Map<String, List<Column>> ret = new LinkedHashMap<String, List<Column>>(keySlices.size());
          for (KeySlice keySlice : keySlices) {
            ret.put(keySlice.getKey(), getColumnList(keySlice.getColumns()));
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
  public Map<String, List<Column>> getRangeSlices(final ColumnParent columnParent,
      final SlicePredicate predicate, final KeyRange keyRange) throws HectorException {
    Operation<Map<String, List<Column>>> op = new Operation<Map<String, List<Column>>>(
        OperationType.READ) {
      @Override
      public Map<String, List<Column>> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          List<KeySlice> keySlices = cassandra.get_range_slices(keyspaceName, columnParent,
              predicate, keyRange, consistency);
          if (keySlices == null || keySlices.isEmpty()) {
            return Collections.emptyMap();
          }
          Map<String, List<Column>> ret = new LinkedHashMap<String, List<Column>>(keySlices.size());
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
  public Map<String, List<SuperColumn>> getSuperRangeSlice(final ColumnParent columnParent,
      final SlicePredicate predicate, final String start, final String finish, final int count)
      throws HectorException {
    Operation<Map<String, List<SuperColumn>>> op = new Operation<Map<String, List<SuperColumn>>>(
        OperationType.READ) {
      @Override
      public Map<String, List<SuperColumn>> execute(Cassandra.Client cassandra)
          throws HectorException {
        try {
          List<KeySlice> keySlices = cassandra.get_range_slice(keyspaceName, columnParent,
              predicate, start, finish, count, consistency);
          if (keySlices == null || keySlices.isEmpty()) {
            return Collections.emptyMap();
          }
          Map<String, List<SuperColumn>> ret = new LinkedHashMap<String, List<SuperColumn>>(
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
  public Map<String, List<SuperColumn>> getSuperRangeSlices(final ColumnParent columnParent,
      final SlicePredicate predicate, final KeyRange keyRange) throws HectorException {
    Operation<Map<String, List<SuperColumn>>> op = new Operation<Map<String, List<SuperColumn>>>(
        OperationType.READ) {
      @Override
      public Map<String, List<SuperColumn>> execute(Cassandra.Client cassandra)
          throws HectorException {
        try {
          List<KeySlice> keySlices = cassandra.get_range_slices(keyspaceName, columnParent,
              predicate, keyRange, consistency);
          if (keySlices == null || keySlices.isEmpty()) {
            return Collections.emptyMap();
          }
          Map<String, List<SuperColumn>> ret = new LinkedHashMap<String, List<SuperColumn>>(
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
  public List<Column> getSlice(final String key, final ColumnParent columnParent,
      final SlicePredicate predicate) throws HectorException {
    Operation<List<Column>> op = new Operation<List<Column>>(OperationType.READ) {
      @Override
      public List<Column> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          List<ColumnOrSuperColumn> cosclist = cassandra.get_slice(keyspaceName, key, columnParent,
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
  public SuperColumn getSuperColumn(final String key, final ColumnPath columnPath) throws HectorException {
    valideColumnPath(columnPath);

    Operation<SuperColumn> op = new Operation<SuperColumn>(OperationType.READ) {
      @Override
      public SuperColumn execute(Cassandra.Client cassandra) throws HectorException {
        ColumnOrSuperColumn cosc;
        try {
          cosc = cassandra.get(keyspaceName, key, columnPath, consistency);
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
  public SuperColumn getSuperColumn(final String key, final ColumnPath columnPath,
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
          List<ColumnOrSuperColumn> cosc = cassandra.get_slice(keyspaceName, key, clp, sp,
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
  public List<SuperColumn> getSuperSlice(final String key, final ColumnParent columnParent,
      final SlicePredicate predicate) throws HectorException {
    Operation<List<SuperColumn>> op = new Operation<List<SuperColumn>>(OperationType.READ) {
      @Override
      public List<SuperColumn> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          List<ColumnOrSuperColumn> cosclist = cassandra.get_slice(keyspaceName, key, columnParent,
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
  public void insert(final String key, final ColumnPath columnPath, final byte[] value)
      throws HectorException {
    insert(key, columnPath, value, createTimestamp());
  }

  @Override
  public void insert(final String key, final ColumnPath columnPath, final byte[] value, 
      final long timestamp) throws HectorException {
    valideColumnPath(columnPath);
    Operation<Void> op = new Operation<Void>(OperationType.WRITE) {
      @Override
      public Void execute(Cassandra.Client cassandra) throws HectorException {
        try {
          cassandra.insert(keyspaceName, key, columnPath, value, timestamp, consistency);
          return null;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
  }


  @Override
  public Map<String, Column> multigetColumn(final List<String> keys, final ColumnPath columnPath)
      throws HectorException {
    valideColumnPath(columnPath);

    Operation<Map<String, Column>> op = new Operation<Map<String, Column>>(OperationType.READ) {
      @Override
      public Map<String, Column> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          Map<String, ColumnOrSuperColumn> cfmap = cassandra.multiget(keyspaceName, keys,
              columnPath, consistency);
          if (cfmap == null || cfmap.isEmpty()) {
            return Collections.emptyMap();
          }
          Map<String, Column> result = new HashMap<String, Column>();
          for (Map.Entry<String, ColumnOrSuperColumn> entry : cfmap.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getColumn());
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
  public Map<String, List<Column>> multigetSlice(final List<String> keys,
      final ColumnParent columnParent, final SlicePredicate predicate) throws HectorException {
    Operation<Map<String, List<Column>>> getCount = new Operation<Map<String, List<Column>>>(
        OperationType.READ) {
      @Override
      public Map<String, List<Column>> execute(Cassandra.Client cassandra) throws HectorException {
        try {
          Map<String, List<ColumnOrSuperColumn>> cfmap = cassandra.multiget_slice(keyspaceName,
              keys, columnParent, predicate, consistency);

          Map<String, List<Column>> result = new HashMap<String, List<Column>>();
          for (Map.Entry<String, List<ColumnOrSuperColumn>> entry : cfmap.entrySet()) {
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
  public Map<String, SuperColumn> multigetSuperColumn(List<String> keys, ColumnPath columnPath)
      throws HectorException {
    return multigetSuperColumn(keys, columnPath, false, Integer.MAX_VALUE);
  }

  @Override
  public Map<String, SuperColumn> multigetSuperColumn(List<String> keys, ColumnPath columnPath,
      boolean reversed, int size) throws HectorException {
    valideSuperColumnPath(columnPath);

    // only can get supercolumn by multigetSuperSlice
    ColumnParent clp = new ColumnParent(columnPath.getColumn_family());
    clp.setSuper_column(columnPath.getSuper_column());

    SliceRange sr = new SliceRange(new byte[0], new byte[0], reversed, size);
    SlicePredicate sp = new SlicePredicate();
    sp.setSlice_range(sr);

    Map<String, List<SuperColumn>> sclist = multigetSuperSlice(keys, clp, sp);

    if (sclist == null || sclist.isEmpty()) {
      return Collections.emptyMap();
    }

    Map<String, SuperColumn> result = new HashMap<String, SuperColumn>(keys.size() * 2);
    for (Map.Entry<String, List<SuperColumn>> entry : sclist.entrySet()) {
      List<SuperColumn> sclistByKey = entry.getValue();
      if (sclistByKey.size() > 0) {
        result.put(entry.getKey(), sclistByKey.get(0));
      }
    }
    return result;
  }

  @Override
  public Map<String, List<SuperColumn>> multigetSuperSlice(final List<String> keys,
      final ColumnParent columnParent, final SlicePredicate predicate) throws HectorException {
    Operation<Map<String, List<SuperColumn>>> getCount = new Operation<Map<String, List<SuperColumn>>>(
        OperationType.READ) {
      @Override
      public Map<String, List<SuperColumn>> execute(Cassandra.Client cassandra)
          throws HectorException {
        try {
          Map<String, List<ColumnOrSuperColumn>> cfmap = cassandra.multiget_slice(keyspaceName,
              keys, columnParent, predicate, consistency);
          // if user not given super column name, the multiget_slice will return
          // List
          // filled with
          // super column, if user given a column name, the return List will
          // filled
          // with column,
          // this is a bad interface design.
          if (columnParent.getSuper_column() == null) {
            Map<String, List<SuperColumn>> result = new HashMap<String, List<SuperColumn>>();
            for (Map.Entry<String, List<ColumnOrSuperColumn>> entry : cfmap.entrySet()) {
              result.put(entry.getKey(), getSuperColumnList(entry.getValue()));
            }
            return result;
          } else {
            Map<String, List<SuperColumn>> result = new HashMap<String, List<SuperColumn>>();
            for (Map.Entry<String, List<ColumnOrSuperColumn>> entry : cfmap.entrySet()) {
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
  public void remove(final String key, final ColumnPath columnPath) throws HectorException {
    remove(key, columnPath, createTimestamp());
  }

  @Override
  public void remove(final String key, final ColumnPath columnPath, final long timestamp)
      throws HectorException {
    Operation<Void> op = new Operation<Void>(OperationType.WRITE) {
      @Override
      public Void execute(Cassandra.Client cassandra) throws HectorException {
        try {
          cassandra.remove(keyspaceName, key, columnPath, timestamp, consistency);
          return null;
        } catch (Exception e) {
          throw xtrans.translate(e);
        }
      }
    };
    operateWithFailover(op);
  }

  @Override
  public String getName() {
    return keyspaceName;
  }

  @Override
  public Map<String, Map<String, String>> describeKeyspace() throws HectorException {
    return keyspaceDesc;
  }

  @Override
  public CassandraClient getClient() {
    return client;
  }

  @Override
  public Column getColumn(final String key, final ColumnPath columnPath) throws HectorException {
    valideColumnPath(columnPath);

    Operation<Column> op = new Operation<Column>(OperationType.READ) {
      @Override
      public Column execute(Cassandra.Client cassandra) throws HectorException {
        ColumnOrSuperColumn cosc;
        try {
          cosc = cassandra.get(keyspaceName, key, columnPath, consistency);
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
  public ConsistencyLevel getConsistencyLevel() {
    return consistency;
  }

  public long createTimestamp() {
    return client.getTimestampResolution().createTimestamp();
  }

  /**
   * Make sure that if the given column path was a Column. Throws an
   * InvalidRequestException if not.
   * 
   * @param columnPath
   * @throws InvalidRequestException
   *           if either the column family does not exist or that it's type does
   *           not match (super)..
   */
  private void valideColumnPath(ColumnPath columnPath) throws InvalidRequestException {
    String cf = columnPath.getColumn_family();
    Map<String, String> cfdefine;
    String errorMsg;
    if ((cfdefine = keyspaceDesc.get(cf)) != null) {
      if (cfdefine.get(CF_TYPE).equals(CF_TYPE_STANDARD) && columnPath.getColumn() != null) {
        // if the column family is a standard column
        return;
      } else if (cfdefine.get(CF_TYPE).equals(CF_TYPE_SUPER)
          && columnPath.getSuper_column() != null) {
        // if the column family is a super column and also give the super_column
        // name
        return;
      } else {
        errorMsg = new String("Invalid Request for column family " + cf
            + " Make sure you have the right type");
      }
    } else {
      errorMsg = new String("The specified column family does not exist: " + cf);
    }
    throw new InvalidRequestException(errorMsg);
  }

  /**
   * Make sure that the given column path is a SuperColumn in the DB, Throws an
   * exception if it's not.
   * 
   * @throws InvalidRequestException
   */
  private void valideSuperColumnPath(ColumnPath columnPath) throws InvalidRequestException {
    String cf = columnPath.getColumn_family();
    Map<String, String> cfdefine;
    if ((cfdefine = keyspaceDesc.get(cf)) != null && cfdefine.get(CF_TYPE).equals(CF_TYPE_SUPER)
        && columnPath.getSuper_column() != null) {
      return;
    }
    throw new InvalidRequestException(
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

  /**
   * Initializes the ring info so we can handle failover if this happens later.
   * 
   * @throws HectorTransportException
   */
  private void initFailover() throws HectorTransportException {
    if (failoverPolicy == FailoverPolicy.FAIL_FAST) {
      knownHosts.clear();
      knownHosts.add(client.getCassandraHost());
      return;
    }
    // learn about other cassandra hosts in the ring
    updateKnownHosts();
  }

  /**
   * Uses the current known host to query about all other hosts in the ring.
   * 
   * @throws HectorTransportException
   */
  public void updateKnownHosts() throws HectorTransportException {
    // When update starts we only know of this client, nothing else
    knownHosts.clear();
    knownHosts.add(getClient().getCassandraHost());

    // Now query for more hosts. If the query fails, then even this client is
    // now "known"
    try {
      List<CassandraHost> hosts;
      hosts = getClient().getKnownHosts(true);
      if (hosts != null && hosts.size() > 0) {
        if (!hosts.contains(getClient().getCassandraHost())) {
          hosts.add(getClient().getCassandraHost());
        }
        knownHosts = new ArrayList<CassandraHost>(hosts);
      }
    } catch (HectorTransportException e) {
      knownHosts.clear();
      log.error("Cannot query host names; Keyspace {} is now disconnected", toString());
    } catch (IllegalStateException e) {
      knownHosts.clear();
      log.error("Cannot query host names; Keyspace {} is now disconnected", toString());
    } catch (PoolExhaustedException e) {
      knownHosts.clear();
      log.error("Cannot query host names; Keyspace {} is now disconnected", toString());
    } catch (Exception e) {
      knownHosts.clear();
      log.error("Cannot query host names; Keyspace {} is now disconnected", toString());
    }
  }

  public Set<CassandraHost> getKnownHosts() {
    Set<CassandraHost> hosts = new HashSet<CassandraHost>();
    hosts.addAll(knownHosts);
    return hosts;
  }

  @Override
  public String toString() {
    StringBuilder b = new StringBuilder();
    b.append("KeyspaceImpl<");
    b.append(getClient());
    b.append(">");
    return super.toString();
  }
}
