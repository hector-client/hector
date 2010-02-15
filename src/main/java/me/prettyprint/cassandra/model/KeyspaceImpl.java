package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.service.CassandraClientFactory;
import me.prettyprint.cassandra.service.CassandraClientMonitor;
import me.prettyprint.cassandra.service.CassandraClient.FailoverPolicy;
import me.prettyprint.cassandra.service.CassandraClientMonitor.Counter;

import org.apache.cassandra.service.Cassandra;
import org.apache.cassandra.service.Column;
import org.apache.cassandra.service.ColumnOrSuperColumn;
import org.apache.cassandra.service.ColumnParent;
import org.apache.cassandra.service.ColumnPath;
import org.apache.cassandra.service.InvalidRequestException;
import org.apache.cassandra.service.NotFoundException;
import org.apache.cassandra.service.SlicePredicate;
import org.apache.cassandra.service.SliceRange;
import org.apache.cassandra.service.SuperColumn;
import org.apache.cassandra.service.TimedOutException;
import org.apache.cassandra.service.UnavailableException;
import org.apache.cassandra.service.Cassandra.Client;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implamentation of a keyspace
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
/*package*/ class KeyspaceImpl implements Keyspace {

  private static final Logger log = LoggerFactory.getLogger(KeyspaceImpl.class);

  private CassandraClient client;

  /** The cassandra thrift proxy */
  private Cassandra.Client cassandra;

  private final String keyspaceName;

  private final Map<String, Map<String, String>> keyspaceDesc;

  private final int consistencyLevel;

  private final FailoverPolicy failoverPolicy;

  /** List of all known remote cassandra nodes */
  List<String> knownHosts = new ArrayList<String>();

  private final CassandraClientFactory clientFactory;

  private final CassandraClientMonitor monitor;

  public KeyspaceImpl(CassandraClient client, String keyspaceName,
      Map<String, Map<String, String>> keyspaceDesc, int consistencyLevel,
      FailoverPolicy failoverPolicy, CassandraClientFactory clientFactory,
      CassandraClientMonitor monitor) throws TException {
    this.client = client;
    this.consistencyLevel = consistencyLevel;
    this.keyspaceDesc = keyspaceDesc;
    this.keyspaceName = keyspaceName;
    this.cassandra = client.getCassandra();
    this.failoverPolicy = failoverPolicy;
    this.clientFactory = clientFactory;
    this.monitor = monitor;
    initFailover();
  }

  @Override
  public void batchInsert(String key, Map<String, List<Column>> columnMap,
      Map<String, List<SuperColumn>> superColumnMap) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    if (columnMap == null && superColumnMap == null) {
      throw new InvalidRequestException("columnMap and SuperColumnMap can not be null at same time");
    }

    int size = (columnMap == null ? 0 : columnMap.size()) +
        (columnMap == null ? 0 : columnMap.size());
    Map<String, List<ColumnOrSuperColumn>> cfmap =
        new HashMap<String, List<ColumnOrSuperColumn>>(size * 2);

    if (columnMap != null){
      for (Map.Entry<String, List<Column>> entry : columnMap.entrySet()){
        cfmap.put(entry.getKey(), getSoscList(entry.getValue()));
      }
    }

    if (superColumnMap != null){
      for( Map.Entry<String, List<SuperColumn>> entry : superColumnMap.entrySet()){
        cfmap.put(entry.getKey(), getSoscSuperList(entry.getValue()));
      }
    }

    Operation<Void> batchInsert = new Operation<Void>(keyspaceName, key, null /*columnPath*/,
        null /*value*/, cfmap, null /*columnParent*/, createTimeStamp(), consistencyLevel,
        Counter.WRITE_SUCCESS, Counter.WRITE_FAIL) {
      @Override
      public Void execute(Client cassandra) throws InvalidRequestException, UnavailableException,
          TException, TimedOutException {
        cassandra.batch_insert(keyspaceName , key, cfmap , consistencyLevel);
        return null;
      }
    };
    operateWithFailover(batchInsert);
  }

  @Override
  public int getCount(String key, ColumnParent columnParent) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    Operation<Integer> getCount = new Operation<Integer>(keyspaceName, key, null /*columnPath*/,
        null /*value*/, null /*cfmap*/, columnParent, createTimeStamp(), consistencyLevel,
        Counter.READ_SUCCESS, Counter.READ_FAIL) {
      @Override
      public Integer execute(Client cassandra) throws InvalidRequestException, UnavailableException,
          TException, TimedOutException {
        return cassandra.get_count(keyspaceName, key, columnParent, consistencyLevel);
      }
    };
    operateWithFailover(getCount);
    return getCount.getResult();
  }

  @Override
  public List<String> getRangeSlice(ColumnParent columnParent, SlicePredicate predicate,
      String start, String finish, int count) throws InvalidRequestException, UnavailableException,
      TException, TimedOutException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Column> getSlice(String key, ColumnParent columnParent, SlicePredicate predicate)
      throws InvalidRequestException, NotFoundException, UnavailableException, TException,
      TimedOutException {
    List<ColumnOrSuperColumn> cosclist = cassandra.get_slice(keyspaceName, key, columnParent,
        predicate, consistencyLevel);

    if (cosclist == null) {
      return null;
    }
    ArrayList<Column> result = new ArrayList<Column>(cosclist.size());
    for(ColumnOrSuperColumn cosc : cosclist){
      result.add(cosc.getColumn());
    }
    return result;
  }

  @Override
  public SuperColumn getSuperColumn(String key, ColumnPath columnPath)
      throws InvalidRequestException, NotFoundException, UnavailableException, TException,
      TimedOutException {
    return getSuperColumn(key, columnPath, false, Integer.MAX_VALUE);
  }

  @Override
  public SuperColumn getSuperColumn(String key, ColumnPath columnPath, boolean reversed, int size)
      throws InvalidRequestException, NotFoundException, UnavailableException, TException,
      TimedOutException {
    valideSuperColumnPath(columnPath);

    ColumnParent clp = new ColumnParent(columnPath.getColumn_family(), columnPath.getSuper_column());
    SliceRange sr = new SliceRange(new byte[0], new byte[0], reversed, size);
    SlicePredicate sp = new SlicePredicate(null, sr);
    List<ColumnOrSuperColumn> cosc = cassandra.get_slice(keyspaceName, key, clp, sp,
        consistencyLevel);

    return new SuperColumn(columnPath.getSuper_column(), getColumnList(cosc));
  }

  @Override
  public List<SuperColumn> getSuperSlice(String key, ColumnParent columnParent,
      SlicePredicate predicate) throws InvalidRequestException, NotFoundException,
      UnavailableException, TException, TimedOutException {
    List<ColumnOrSuperColumn> cosclist = cassandra.get_slice(keyspaceName, key, columnParent,
        predicate, consistencyLevel);
    if (cosclist == null) {
      return null;
    }
    ArrayList<SuperColumn> result = new ArrayList<SuperColumn>(cosclist.size());
    for(ColumnOrSuperColumn cosc: cosclist){
      result.add(cosc.getSuper_column());
    }
    return result;
  }

  @Override
  public void insert(String key, ColumnPath columnPath, byte[] value)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    valideColumnPath(columnPath);
    Operation<Void> insert = new Operation<Void>(keyspaceName, key, columnPath, value,
        null /*cfmap*/, null /*columnParent*/, createTimeStamp(), consistencyLevel,
        Counter.WRITE_SUCCESS, Counter.WRITE_FAIL) {
      @Override
      public Void execute(Client cassandra) throws InvalidRequestException, UnavailableException,
          TException, TimedOutException {
        cassandra.insert(keyspace, key, columnPath, value, timestamp, consistency);
        return null;
      }
    };
    operateWithFailover(insert);
  }

  @Override
  public Map<String, Column> multigetColumn(List<String> keys, ColumnPath columnPath)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    valideColumnPath(columnPath);

    Map<String, ColumnOrSuperColumn> cfmap = cassandra.multiget(keyspaceName, keys, columnPath,
        consistencyLevel);

    if (cfmap == null || cfmap.isEmpty()) {
      return Collections.emptyMap();
    }

    Map<String, Column> result = new HashMap<String , Column>();
    for(Map.Entry<String, ColumnOrSuperColumn> entry : cfmap.entrySet()){
      result.put(entry.getKey(), entry.getValue().getColumn());
    }
    return result;
  }

  @Override
  public Map<String, List<Column>> multigetSlice(List<String> keys, ColumnParent columnParent,
      SlicePredicate predicate) throws InvalidRequestException, UnavailableException, TException,
      TimedOutException {
    Map<String,List<ColumnOrSuperColumn>> cfmap = cassandra.multiget_slice(
        keyspaceName, keys, columnParent, predicate, consistencyLevel);

    Map<String, List<Column>> result = new HashMap<String , List<Column>>();
    for(Map.Entry<String, List<ColumnOrSuperColumn>> entry : cfmap.entrySet()){
      result.put(entry.getKey(), getColumnList(entry.getValue()));
    }
    return result;
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
    ColumnParent clp = new ColumnParent(columnPath.getColumn_family(), columnPath.getSuper_column());
    SliceRange sr = new SliceRange(new byte[0], new byte[0], reversed, size);
    SlicePredicate sp = new SlicePredicate(null, sr);
    Map<String, List<SuperColumn>> sclist = multigetSuperSlice(keys, clp, sp);

    if (sclist == null || sclist.isEmpty()) {
      return Collections.emptyMap();
    }

    HashMap<String, SuperColumn> result = new HashMap<String, SuperColumn>(keys.size() * 2);
    for (Map.Entry<String, List<SuperColumn>> entry : sclist.entrySet()) {
      List<SuperColumn> sclistByKey = entry.getValue();
      if (sclistByKey.size() > 0) {
        result.put(entry.getKey(), sclistByKey.get(0));
      }
    }
    return result;
  }

  @Override
  public Map<String, List<SuperColumn>> multigetSuperSlice(List<String> keys,
      ColumnParent columnParent, SlicePredicate predicate) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    Map<String,List<ColumnOrSuperColumn>> cfmap = cassandra.multiget_slice(
        keyspaceName, keys, columnParent, predicate, consistencyLevel);

    // if user not given super column name, the multiget_slice will return List filled with
    // super column, if user given a column name, the return List will filled with column,
    // this is a bad interface design.
    if(columnParent.getSuper_column() == null){
      Map<String, List<SuperColumn>> result = new HashMap<String , List<SuperColumn>>();
      for(Map.Entry<String, List<ColumnOrSuperColumn>> entry : cfmap.entrySet()){
        result.put(entry.getKey(), getSuperColumnList(entry.getValue()));
      }
      return result;
    }else{
      Map<String, List<SuperColumn>> result = new HashMap<String , List<SuperColumn>>();
      for(Map.Entry<String, List<ColumnOrSuperColumn>> entry : cfmap.entrySet()){
        SuperColumn spc = new SuperColumn( columnParent.getSuper_column() ,  getColumnList(entry.getValue()) );
        ArrayList<SuperColumn> spclist = new ArrayList<SuperColumn>(1);
        spclist.add(spc) ;
        result.put(entry.getKey(), spclist );
      }
      return result;
    }
  }

  @Override
  public void remove(String key, ColumnPath columnPath) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    cassandra.remove(keyspaceName, key, columnPath, createTimeStamp(), consistencyLevel);
  }

  @Override
  public String getKeyspaceName() {
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
  public Column getColumn(String key, ColumnPath columnPath) throws InvalidRequestException,
      NotFoundException, UnavailableException, TException, TimedOutException {
    valideColumnPath(columnPath);
    ColumnOrSuperColumn cosc = cassandra.get(keyspaceName, key, columnPath, consistencyLevel);
    return cosc == null ? null : cosc.getColumn();
  }

  @Override
  public int getConsistencyLevel() {
    return consistencyLevel;
  }

  private long createTimeStamp() {
    return System.currentTimeMillis();
  }

  /**
   * Make sure that if the given column path was a Column.
   * Throws an InvalidRequestException if not.
   * @param columnPath
   * @throws InvalidRequestException if either the column family does not exist or that it's type
   * does not match (super)..
   */
  private void valideColumnPath(ColumnPath columnPath) throws InvalidRequestException {
    String cf = columnPath.getColumn_family();
    Map<String, String> cfdefine;
    if ((cfdefine = keyspaceDesc.get(cf)) != null){
      if (cfdefine.get(CF_TYPE).equals(CF_TYPE_STANDARD) && columnPath.getColumn() != null) {
        // if the column family is a standard column
        return;
      } else if (cfdefine.get(CF_TYPE).equals(CF_TYPE_SUPER)
          && columnPath.getSuper_column() != null
          && columnPath.getColumn() != null) {
        // if the column family is a super column and also give the super_column name
        return;
      }
    }
    throw new InvalidRequestException("The specified column family does not exist: " + cf);
  }

  /**
   * Make sure that the given column path is a SuperColumn in the DB,
   * Throws an exception if it's not.
   *
   * @throws InvalidRequestException
   */
  private void valideSuperColumnPath(ColumnPath columnPath) throws InvalidRequestException{
    String cf = columnPath.getColumn_family() ;
    Map<String, String> cfdefine;
    if((cfdefine = keyspaceDesc.get(cf)) != null &&
        cfdefine.get(CF_TYPE).equals(CF_TYPE_SUPER) &&
        columnPath.getSuper_column()!= null) {
      return;
    }
    throw new InvalidRequestException("Invalid super column or super column family does not exist: "
        + cf);
  }

  private List<ColumnOrSuperColumn> getSoscList(List<Column> columns){
    ArrayList<ColumnOrSuperColumn> list = new ArrayList<ColumnOrSuperColumn>(columns.size());
    for(Column col : columns){
      list.add(new ColumnOrSuperColumn(col , null ));
    }
    return list;
  }

  private List<ColumnOrSuperColumn> getSoscSuperList(List<SuperColumn> columns){
    ArrayList<ColumnOrSuperColumn> list = new ArrayList<ColumnOrSuperColumn>(columns.size());
    for(SuperColumn col : columns){
      list.add(new ColumnOrSuperColumn(null , col ));
    }
    return list;
  }

  private List<Column>  getColumnList(List<ColumnOrSuperColumn> columns){
    ArrayList<Column> list = new ArrayList<Column>(columns.size());
    for(ColumnOrSuperColumn col : columns){
      list.add(col.getColumn());
    }
    return list;
  }

  private List<SuperColumn>  getSuperColumnList(List<ColumnOrSuperColumn> columns){
    ArrayList<SuperColumn> list = new ArrayList<SuperColumn>(columns.size());
    for(ColumnOrSuperColumn col : columns){
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
    Map<String, String> map = getClient().getTokenMap(true);
    knownHosts.clear();
    for (Map.Entry<String, String> entry: map.entrySet()) {
      knownHosts.add(entry.getValue());
    }
  }

  /**
   * Updates the
   * @throws TTransportException
   * @throws TException
   */
  private void skipToNextHost() throws TTransportException, TException {
    log.info("Skipping to next host. Current host is: {}", client.getUrl());
    // Release client first
    // TODO(ran): release client from connection pool

    // Acquire a new client
    // TODO(ran) acquire from connection pool

    // assume they use the same port
    client = clientFactory.create(getNextHost(client.getUrl()), client.getPort());
    cassandra = client.getCassandra();
    monitor.incCounter(Counter.SKIP_HOST_SUCCESS);
    log.info("Skipped host. New host is: {}", client.getUrl());
  }

  /**
   * Finds the next host in the knownHosts.
   * Next is the one after the given url (modulo the number of elemens in the list)
   */
  private String getNextHost(String url) {
    int size = knownHosts.size();
    assert size > 1;
    for (int i = 0; i < knownHosts.size(); ++i) {
      if (url.equals(knownHosts.get(i))) {
        // found this host. Return the next one in the array
        return knownHosts.get((i + 1) % size);
      }
    }
    log.error("The URL {} wasn't found in the knownHosts", url);
    return null;
  }

  /**
   * Performs the operation and retries in in case the class is configured for retries, and there
   * are enough hosts to try and the error was {@link TimedOutException}.
   */
  @SuppressWarnings("unchecked")
  private void operateWithFailover(Operation op) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    int retries = Math.min(failoverPolicy.getNumRetries() + 1, knownHosts.size());
    try {
      while (retries > 0) {
        --retries;
        log.debug("Performing operation on {}; retries: {}", client.getUrl(), retries);
        try {
          // Perform operation and save its result value
          op.executeAndSetResult(cassandra);
          monitor.incCounter(op.successCounter);
          log.debug("Operation succeeded on {}", client.getUrl());
          return;
        } catch (TimedOutException e) {
          log.warn("Got a TimedOutException. Num of retries: {}", retries);
          monitor.incCounter(Counter.TIMED_OUT_EXCEPTIONS);
          if (retries == 0) {
            throw e;
          } else {
            skipToNextHost();
          }
        } catch (UnavailableException e) {
          log.warn("Got a UnavailableException. Num of retries: {}", retries);
          monitor.incCounter(Counter.UNAVAILABLE_EXCEPTIONS);
          if (retries == 0) {
            throw e;
          } else {
            skipToNextHost();
          }
        }
      }
    } catch (InvalidRequestException e) {
      monitor.incCounter(op.failCounter);
      throw e;
    } catch (UnavailableException e) {
      monitor.incCounter(op.failCounter);
      throw e;
    } catch (TException e) {
      monitor.incCounter(op.failCounter);
      throw e;
    } catch (TimedOutException e) {
      monitor.incCounter(op.failCounter);
      throw e;
    }
  }

  /**
   * Defines the interface of an operation performed on cassandra
   *
   * @param <T> The result type of the operation (if it has a result), such as the result of
   *    get_count or get_column
   */
  private abstract static class Operation<T> {

    protected final String keyspace;
    protected final String key;
    protected final ColumnPath columnPath;
    protected final byte[] value;
    protected final Map<String, List<ColumnOrSuperColumn>> cfmap;
    protected final ColumnParent columnParent;
    protected final long timestamp;
    protected final int consistency;
    protected final Counter successCounter;
    protected final Counter failCounter;
    protected T result;

    public Operation(String keyspace,
        String key,
        ColumnPath columnPath,
        byte[] value,
        Map<String, List<ColumnOrSuperColumn>> cfmap,
        ColumnParent columnParent,
        long timestamp,
        int consistency,
        Counter successCounter,
        Counter failCounter) {
      this.keyspace = keyspace;
      this.key = key;
      this.columnPath = columnPath;
      this.value = value;
      this.cfmap = cfmap;
      this.columnParent = columnParent;
      this.timestamp = timestamp;
      this.consistency = consistency;
      this.successCounter = successCounter;
      this.failCounter = failCounter;
    }

    public void setResult(T executionResult) {
      result = executionResult;
    }

    /**
     *
     * @return The result of the operation, if this is an operation that has a result (such as
     *    getColumn etc.
     */
    public T getResult() {
      return result;
    }

    /**
     * Performs the operation on the given cassandra instance.
     */
    public abstract T execute(Cassandra.Client cassandra)
        throws InvalidRequestException, UnavailableException, TException, TimedOutException;

    public void executeAndSetResult(Cassandra.Client cassandra)
        throws InvalidRequestException, UnavailableException, TException, TimedOutException {
      setResult(execute(cassandra));
    }
  }
}
