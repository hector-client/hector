package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.CassandraClient;

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
import org.apache.thrift.TException;

/**
 * Implamentation of a keyspace
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
/*package*/ class KeyspaceImpl implements Keyspace {

  private static String CF_TYPE = "Type" ;
  private static String CF_TYPE_STANDARD = "Standard" ;
  private static String CF_TYPE_SUPER = "Super" ;

  private final CassandraClient client;

  /** The cassandra thrift proxy */
  private final Cassandra.Client cassandra;

  private final String keyspaceName;

  private final Map<String, Map<String, String>> keyspaceDesc;

  private final int consistencyLevel;

  public KeyspaceImpl(CassandraClient client, String keyspaceName,
      Map<String, Map<String, String>> keyspaceDesc, int consistencyLevel) {
    this.client = client;
    this.consistencyLevel = consistencyLevel;
    this.keyspaceDesc = keyspaceDesc;
    this.keyspaceName = keyspaceName;
    this.cassandra = client.getCassandra();
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

    // do really insert
    cassandra.batch_insert(keyspaceName , key, cfmap , consistencyLevel);
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

  @Override
  public int getCount(String key, ColumnParent columnParent) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    return cassandra.get_count(keyspaceName, key, columnParent, consistencyLevel);
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
    cassandra.insert(keyspaceName, key, columnPath, value, createTimeStamp(), consistencyLevel);
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

}
