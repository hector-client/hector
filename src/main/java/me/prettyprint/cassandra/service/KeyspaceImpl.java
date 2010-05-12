package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.KeySlice;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.cassandra.thrift.SuperColumn;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
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
  private List<String> knownHosts = new ArrayList<String>();

  private final CassandraClientPool clientPools;

  private final CassandraClientMonitor monitor;

  public KeyspaceImpl(CassandraClient client, String keyspaceName,
      Map<String, Map<String, String>> keyspaceDesc, ConsistencyLevel consistencyLevel,
      FailoverPolicy failoverPolicy, CassandraClientPool clientPools, CassandraClientMonitor monitor)
      throws TException {
    this.client = client;
    this.consistency = consistencyLevel;
    this.keyspaceDesc = keyspaceDesc;
    this.keyspaceName = keyspaceName;
    this.failoverPolicy = failoverPolicy;
    this.clientPools = clientPools;
    this.monitor = monitor;
    initFailover();
  }

  @Override
  public void batchInsert(final String key, Map<String, List<Column>> columnMap,
      Map<String, List<SuperColumn>> superColumnMap) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
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
      public Void execute(Cassandra.Client cassandra) throws InvalidRequestException, UnavailableException,
          TException, TimedOutException {
        cassandra.batch_insert(keyspaceName, key, cfmap, consistency);
        return null;
      }
    };
    operateWithFailover(op);
  }

  @Override
  public void batchMutate(final Map<String, Map<String, List<Mutation>>> mutationMap)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    Operation<Void> op = new Operation<Void>(OperationType.WRITE) {
      @Override
      public Void execute(Cassandra.Client cassandra) throws InvalidRequestException, UnavailableException,
      TException, TimedOutException {
        cassandra.batch_mutate(keyspaceName, mutationMap, consistency);
        return null;
      }
    };
    operateWithFailover(op);
  }



  @Override
  public void batchMutate(BatchMutation batchMutate)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    batchMutate(batchMutate.getMutationMap());
  }

  @Override
  public int getCount(final String key, final ColumnParent columnParent)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    Operation<Integer> op = new Operation<Integer>(OperationType.READ) {
      @Override
      public Integer execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
        return cassandra.get_count(keyspaceName, key, columnParent, consistency);
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  private void operateWithFailover(Operation<?> op) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    FailoverOperator operator = new FailoverOperator(failoverPolicy, knownHosts, monitor, client,
        clientPools, this);
    client = operator.operate(op);
  }

  @Override
  public Map<String, List<Column>> getRangeSlice(final ColumnParent columnParent,
      final SlicePredicate predicate, final String start, final String finish, final int count)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    Operation<Map<String, List<Column>>> op = new Operation<Map<String, List<Column>>>(
        OperationType.READ) {
      @Override
      public Map<String, List<Column>> execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
        List<KeySlice> keySlices = cassandra.get_range_slice(keyspaceName, columnParent, predicate,
            start, finish, count, consistency);
        if (keySlices == null || keySlices.isEmpty()) {
          return Collections.emptyMap();
        }
        Map<String, List<Column>> ret = new LinkedHashMap<String, List<Column>>(keySlices.size());
        for (KeySlice keySlice : keySlices) {
          ret.put(keySlice.getKey(), getColumnList(keySlice.getColumns()));
        }
        return ret;
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public Map<String, List<Column>> getRangeSlices(final ColumnParent columnParent,
      final SlicePredicate predicate, final KeyRange keyRange)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    Operation<Map<String, List<Column>>> op = new Operation<Map<String, List<Column>>>(
        OperationType.READ) {
      @Override
      public Map<String, List<Column>> execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
        List<KeySlice> keySlices = cassandra.get_range_slices(keyspaceName, columnParent, predicate,
            keyRange, consistency);
        if (keySlices == null || keySlices.isEmpty()) {
          return Collections.emptyMap();
        }
        Map<String, List<Column>> ret = new LinkedHashMap<String, List<Column>>(keySlices.size());
        for (KeySlice keySlice : keySlices) {
          ret.put(keySlice.getKey(), getColumnList(keySlice.getColumns()));
        }
        return ret;
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public Map<String, List<SuperColumn>> getSuperRangeSlice(final ColumnParent columnParent,
      final SlicePredicate predicate, final String start, final String finish, final int count)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    Operation<Map<String, List<SuperColumn>>> op = new Operation<Map<String, List<SuperColumn>>>(
        OperationType.READ) {
      @Override
      public Map<String, List<SuperColumn>> execute(Cassandra.Client cassandra)
          throws InvalidRequestException, UnavailableException, TException, TimedOutException {
        List<KeySlice> keySlices = cassandra.get_range_slice(keyspaceName, columnParent, predicate,
            start, finish, count, consistency);
        if (keySlices == null || keySlices.isEmpty()) {
          return Collections.emptyMap();
        }
        Map<String, List<SuperColumn>> ret = new LinkedHashMap<String, List<SuperColumn>>(
            keySlices.size());
        for (KeySlice keySlice : keySlices) {
          ret.put(keySlice.getKey(), getSuperColumnList(keySlice.getColumns()));
        }
        return ret;
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public List<Column> getSlice(final String key, final ColumnParent columnParent,
      final SlicePredicate predicate) throws InvalidRequestException, NotFoundException,
      UnavailableException, TException, TimedOutException {
    Operation<List<Column>> op = new Operation<List<Column>>(OperationType.READ) {
      @Override
      public List<Column> execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
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
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public SuperColumn getSuperColumn(String key, ColumnPath columnPath)
      throws InvalidRequestException, NotFoundException, UnavailableException, TException,
      TimedOutException {
    return getSuperColumn(key, columnPath, false, Integer.MAX_VALUE);
  }

  @Override
  public SuperColumn getSuperColumn(final String key, final ColumnPath columnPath,
      final boolean reversed, final int size) throws InvalidRequestException, NotFoundException,
      UnavailableException, TException, TimedOutException {
    valideSuperColumnPath(columnPath);
    final SliceRange sliceRange = new SliceRange(new byte[0], new byte[0], reversed, size);
    Operation<SuperColumn> op = new Operation<SuperColumn>(OperationType.READ) {
      @Override
      public SuperColumn execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
        ColumnParent clp = new ColumnParent(columnPath.getColumn_family());
        clp.setSuper_column(columnPath.getSuper_column());

        SlicePredicate sp = new SlicePredicate();
        sp.setSlice_range(sliceRange);

        List<ColumnOrSuperColumn> cosc = cassandra.get_slice(keyspaceName, key, clp, sp,
            consistency);
        return new SuperColumn(columnPath.getSuper_column(), getColumnList(cosc));
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public List<SuperColumn> getSuperSlice(final String key, final ColumnParent columnParent,
      final SlicePredicate predicate) throws InvalidRequestException, NotFoundException,
      UnavailableException, TException, TimedOutException {
    Operation<List<SuperColumn>> op = new Operation<List<SuperColumn>>(OperationType.READ) {
      @Override
      public List<SuperColumn> execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
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
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public void insert(final String key, final ColumnPath columnPath, final byte[] value)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    valideColumnPath(columnPath);
    Operation<Void> op = new Operation<Void>(OperationType.WRITE) {
      @Override
      public Void execute(Cassandra.Client cassandra) throws InvalidRequestException, UnavailableException,
          TException, TimedOutException {
        cassandra.insert(keyspaceName, key, columnPath, value, createTimestamp(), consistency);
        return null;
      }
    };
    operateWithFailover(op);
  }

  @Override
  public Map<String, Column> multigetColumn(final List<String> keys, final ColumnPath columnPath)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    valideColumnPath(columnPath);

    Operation<Map<String, Column>> op = new Operation<Map<String, Column>>(OperationType.READ) {
      @Override
      public Map<String, Column> execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
        Map<String, ColumnOrSuperColumn> cfmap = cassandra.multiget(keyspaceName, keys, columnPath,
            consistency);
        if (cfmap == null || cfmap.isEmpty()) {
          return Collections.emptyMap();
        }
        Map<String, Column> result = new HashMap<String, Column>();
        for (Map.Entry<String, ColumnOrSuperColumn> entry : cfmap.entrySet()) {
          result.put(entry.getKey(), entry.getValue().getColumn());
        }
        return result;
      }
    };
    operateWithFailover(op);
    return op.getResult();
  }

  @Override
  public Map<String, List<Column>> multigetSlice(final List<String> keys,
      final ColumnParent columnParent, final SlicePredicate predicate)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    Operation<Map<String, List<Column>>> getCount = new Operation<Map<String, List<Column>>>(
        OperationType.READ) {
      @Override
      public Map<String, List<Column>> execute(Cassandra.Client cassandra) throws InvalidRequestException,
          UnavailableException, TException, TimedOutException {
        Map<String, List<ColumnOrSuperColumn>> cfmap = cassandra.multiget_slice(keyspaceName, keys,
            columnParent, predicate, consistency);

        Map<String, List<Column>> result = new HashMap<String, List<Column>>();
        for (Map.Entry<String, List<ColumnOrSuperColumn>> entry : cfmap.entrySet()) {
          result.put(entry.getKey(), getColumnList(entry.getValue()));
        }
        return result;
      }
    };
    operateWithFailover(getCount);
    return getCount.getResult();

  }

  @Override
  public Map<String, SuperColumn> multigetSuperColumn(List<String> keys, ColumnPath columnPath)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    return multigetSuperColumn(keys, columnPath, false, Integer.MAX_VALUE);
  }

  @Override
  public Map<String, SuperColumn> multigetSuperColumn(List<String> keys, ColumnPath columnPath,
      boolean reversed, int size) throws InvalidRequestException, UnavailableException, TException,
      TimedOutException {
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
      final ColumnParent columnParent, final SlicePredicate predicate)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    Operation<Map<String, List<SuperColumn>>> getCount = new Operation<Map<String, List<SuperColumn>>>(
        OperationType.READ) {
      @Override
      public Map<String, List<SuperColumn>> execute(Cassandra.Client cassandra)
          throws InvalidRequestException, UnavailableException, TException, TimedOutException {
        Map<String, List<ColumnOrSuperColumn>> cfmap = cassandra.multiget_slice(keyspaceName, keys,
            columnParent, predicate, consistency);

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
      }
    };
    operateWithFailover(getCount);
    return getCount.getResult();

  }

  @Override
  public void remove(final String key, final ColumnPath columnPath) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    remove(key, columnPath, createTimestamp());
  }



  @Override
  public void remove(final String key, final ColumnPath columnPath, final long timestamp)
      throws InvalidRequestException, UnavailableException, TException,
      TimedOutException {
    Operation<Void> op = new Operation<Void>(OperationType.WRITE) {
      @Override
      public Void execute(Cassandra.Client cassandra) throws InvalidRequestException, UnavailableException,
          TException, TimedOutException {
        cassandra.remove(keyspaceName, key, columnPath, timestamp, consistency);
        return null;
      }
    };
    operateWithFailover(op);
  }

  @Override
  public String getName() {
    return keyspaceName;
  }

  @Override
  public Map<String, Map<String, String>> describeKeyspace() throws NotFoundException, TException {
    return keyspaceDesc;
  }

  @Override
  public CassandraClient getClient() {
    return client;
  }

  @Override
  public Column getColumn(final String key, final ColumnPath columnPath)
      throws InvalidRequestException, NotFoundException, UnavailableException, TException,
      TimedOutException {
    valideColumnPath(columnPath);

    Operation<Column> op = new Operation<Column>(OperationType.READ) {
      @Override
      public Column execute(Cassandra.Client cassandra) throws InvalidRequestException, UnavailableException,
          TException, TimedOutException {
        ColumnOrSuperColumn cosc;
        try {
          cosc = cassandra.get(keyspaceName, key, columnPath, consistency);
        } catch (NotFoundException e) {
          setException(e);
          return null;
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
    long current = System.currentTimeMillis();
    switch(client.getTimestampResolution()) {
    case MICROSECONDS:
      return current * 1000;
    case MILLISECONDS:
      return current;
    case SECONDS:
      return current / 1000;
    default:
      throw new RuntimeException("Unknown TimestampResolution: " +
          client.getTimestampResolution());
    }
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
    if ((cfdefine = keyspaceDesc.get(cf)) != null) {
      if (cfdefine.get(CF_TYPE).equals(CF_TYPE_STANDARD) && columnPath.getColumn() != null) {
        // if the column family is a standard column
        return;
      } else if (cfdefine.get(CF_TYPE).equals(CF_TYPE_SUPER)
          && columnPath.getSuper_column() != null && columnPath.getColumn() != null) {
        // if the column family is a super column and also give the super_column
        // name
        return;
      }
    }
    throw new InvalidRequestException("The specified column family does not exist: " + cf);
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
        "Invalid super column or super column family does not exist: " + cf);
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
   * @throws TException
   */
  private void initFailover() throws TException {
    if (failoverPolicy == FailoverPolicy.FAIL_FAST) {
      knownHosts.clear();
      knownHosts.add(client.getUrl());
      return;
    }
    // learn about other cassandra hosts in the ring
    updateKnownHosts();
  }

  /**
   * Uses the current known host to query about all other hosts in the ring.
   *
   * @throws TException
   */
  public void updateKnownHosts() throws TException {
    // When update starts we only know of this client, nothing else
    knownHosts.clear();
    knownHosts.add(getClient().getUrl());

    // Now query for more hosts. If the query fails, then even this client is
    // now "known"
    try {
      Map<String, String> map = getClient().getTokenMap(true);
      Set<String> hosts = new HashSet<String>();
      knownHosts.clear();
      for (Map.Entry<String, String> entry : map.entrySet()) {
        hosts.add(entry.getValue());
      }
      if (!hosts.contains(getClient().getUrl()) && !hosts.contains(getClient().getIp())) {
        hosts.add(getClient().getIp());
      }
      knownHosts = new ArrayList<String>(hosts);
    } catch (TException e) {
      knownHosts.clear();
      log.error("Cannot query tokenMap; Keyspace {} is now disconnected", toString());
    }
  }

  public Set<String> getKnownHosts() {
    Set<String> hosts = new HashSet<String>();
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
