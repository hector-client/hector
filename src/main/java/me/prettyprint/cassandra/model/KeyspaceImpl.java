package me.prettyprint.cassandra.model;

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

  private static String CF_COMPAREWITH = "CompareWith" ;
  private static String CF_DESC = "Desc" ;
  private static String CF_TYPE = "Type" ;
  private static String CF_TYPE_STANDARD = "Standard" ;
  private static String CF_TYPE_SUPER = "Super" ;
  private static String CF_FLUSHPERIOD = "FlushPeriodInMinutes" ;

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
  public void batchInsert(String key, Map<String, List<Column>> cfmap,
      Map<String, List<SuperColumn>> superColumnMap) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub
    return 0;
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SuperColumn getSuperColumn(String key, ColumnPath columnPath)
      throws InvalidRequestException, NotFoundException, UnavailableException, TException,
      TimedOutException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SuperColumn getSuperColumn(String key, ColumnPath columnPath, boolean reversed, int size)
      throws InvalidRequestException, NotFoundException, UnavailableException, TException,
      TimedOutException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<SuperColumn> getSuperSlice(String key, ColumnParent columnParent,
      SlicePredicate predicate) throws InvalidRequestException, NotFoundException,
      UnavailableException, TException, TimedOutException {
    // TODO Auto-generated method stub
    return null;
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, List<Column>> multigetSlice(List<String> keys, ColumnParent columnParent,
      SlicePredicate predicate) throws InvalidRequestException, UnavailableException, TException,
      TimedOutException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, SuperColumn> multigetSuperColumn(List<String> keys, ColumnPath columnPath)
      throws InvalidRequestException, UnavailableException, TException, TimedOutException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, SuperColumn> multigetSuperColumn(List<String> keys, ColumnPath columnPath,
      boolean reversed, int size) throws InvalidRequestException, UnavailableException, TException,
      TimedOutException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, List<SuperColumn>> multigetSuperSlice(List<String> keys,
      ColumnParent columnParent, SlicePredicate predicate) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    // TODO Auto-generated method stub
    return null;
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

}
