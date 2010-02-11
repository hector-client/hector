package me.prettyprint.cassandra.service;

import java.util.List;

import me.prettyprint.cassandra.model.Keyspace;

import org.apache.cassandra.service.NotFoundException;
import org.apache.thrift.TException;

/**
 * Implementation of the client interface.
 *
 * @author Ran Tavory (rantav@gmail.com)
 *
 */
/*package*/ class CassandraClientImpl implements CassandraClient {

  @Override
  public String getClusterName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getConfigFile() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Keyspace getKeySpace(String keySpaceName) throws IllegalArgumentException,
      NotFoundException, TException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Keyspace getKeySpace(String keySpaceName, int consitencyLevel)
      throws IllegalArgumentException, NotFoundException, TException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<String> getKeyspaces() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getStringProperty(String propertyName) throws TException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getTokenMap() {
    // TODO Auto-generated method stub
    return null;
  }

}
