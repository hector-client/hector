package me.prettyprint.cassandra.model;

import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.service.CassandraClient;

import org.apache.cassandra.service.Column;
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

  private final CassandraClient client;
  private final String keyspaceName;
  private final Map<String, Map<String, String>> keyspaceDesc;
  private final int consistencyLevel;

  public KeyspaceImpl(CassandraClient cassandraClient, String keyspaceName,
      Map<String, Map<String, String>> keyspaceDesc, int consistencyLevel) {
    this.client = cassandraClient;
    this.consistencyLevel = consistencyLevel;
    this.keyspaceDesc = keyspaceDesc;
    this.keyspaceName = keyspaceName;
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
    // TODO Auto-generated method stub
    return null;
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
    // TODO Auto-generated method stub

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
    // TODO Auto-generated method stub

  }

  @Override
  public String getKeyspaceName() {
    return keyspaceName;
  }

}
