package me.prettyprint.lcp;

import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;
import org.apache.cassandra.thrift.*;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 * @author zznate
 */
public class LcpCassandraServer implements Cassandra.Iface {

  /*
    general contract for each method:
    - acquire Cassandra.Client from pool, pass through
    - if method uses a key, check the token cache & route

   */


  public LcpCassandraServer() {

  }
  /**
   * Use the key to deduce locality for "client-mediated selects"
   *
   * @return
   */
  protected Cassandra.Client acquire() {
    //  DecoratedKey<?> dk = StorageService.getPartitioner().decorateKey(key);
    //  ~ in the case of RP, is just: return new BigIntegerToken(FBUtilities.hashToBigInteger(key));
    // --- leads to ---
    // from RandomPartitioner#getToken(bb key): return new BigIntegerToken(FBUtilities.hashToBigInteger(key));
    //
    // really just need to query describe_ring and store the output
    // - model after locator.TokenMetadata? Use TokenMetadata directly
    // - TokenRange from describe_ring has everything, but may not want to convert BB to strings
    //   * make a local version which uses Token/TokenMetadata directly
    //   * see TokenMetadata ctor for this
    Cassandra.Client client;
    try {
      TSocket socket = new TSocket("localhost",9160,10000);
      socket.getSocket().setSoLinger(false, 0);
      socket.getSocket().setKeepAlive(true);
      socket.getSocket().setTcpNoDelay(true);
      TTransport transport = new TFramedTransport(socket);
      transport.open();
      client = new Cassandra.Client(new TBinaryProtocol(transport));
    } catch (SocketException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    } catch (TTransportException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    // map
    // (StorageProxy#read takes the commands and noodles hosts via #fetchRows)
    // - then via StorageService#getLiveNaturalEndpoints
    // - then via Table#getReplicationStrategy#getNaturalEndpoints
    // RowPosition implements RingPosition

    return null;
  }

  @Override
  public void login(AuthenticationRequest auth_request) throws AuthenticationException, AuthorizationException, TException {
    acquire().login(auth_request);
  }

  @Override
  public void set_keyspace(String keyspace) throws InvalidRequestException, TException {
    acquire().set_keyspace(keyspace);
  }

  @Override
  public ColumnOrSuperColumn get(ByteBuffer key, ColumnPath column_path, ConsistencyLevel consistency_level) throws InvalidRequestException, NotFoundException, UnavailableException, TimedOutException, TException {
    return acquire().get(key,column_path,consistency_level);
  }

  @Override
  public List<ColumnOrSuperColumn> get_slice(ByteBuffer key, ColumnParent column_parent, SlicePredicate predicate, ConsistencyLevel consistency_level) throws InvalidRequestException, UnavailableException, TimedOutException, TException {
    return acquire().get_slice(key,column_parent,predicate,consistency_level);
  }

  @Override
  public int get_count(ByteBuffer key, ColumnParent column_parent, SlicePredicate predicate, ConsistencyLevel consistency_level) throws InvalidRequestException, UnavailableException, TimedOutException, TException {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Map<ByteBuffer, List<ColumnOrSuperColumn>> multiget_slice(List<ByteBuffer> keys, ColumnParent column_parent, SlicePredicate predicate, ConsistencyLevel consistency_level) throws InvalidRequestException, UnavailableException, TimedOutException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Map<ByteBuffer, Integer> multiget_count(List<ByteBuffer> keys, ColumnParent column_parent, SlicePredicate predicate, ConsistencyLevel consistency_level) throws InvalidRequestException, UnavailableException, TimedOutException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public List<KeySlice> get_range_slices(ColumnParent column_parent, SlicePredicate predicate, KeyRange range, ConsistencyLevel consistency_level) throws InvalidRequestException, UnavailableException, TimedOutException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public List<KeySlice> get_indexed_slices(ColumnParent column_parent, IndexClause index_clause, SlicePredicate column_predicate, ConsistencyLevel consistency_level) throws InvalidRequestException, UnavailableException, TimedOutException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void insert(ByteBuffer key, ColumnParent column_parent, Column column, ConsistencyLevel consistency_level) throws InvalidRequestException, UnavailableException, TimedOutException, TException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void add(ByteBuffer key, ColumnParent column_parent, CounterColumn column, ConsistencyLevel consistency_level) throws InvalidRequestException, UnavailableException, TimedOutException, TException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void remove(ByteBuffer key, ColumnPath column_path, long timestamp, ConsistencyLevel consistency_level) throws InvalidRequestException, UnavailableException, TimedOutException, TException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void remove_counter(ByteBuffer key, ColumnPath path, ConsistencyLevel consistency_level) throws InvalidRequestException, UnavailableException, TimedOutException, TException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void batch_mutate(Map<ByteBuffer, Map<String, List<Mutation>>> mutation_map, ConsistencyLevel consistency_level) throws InvalidRequestException, UnavailableException, TimedOutException, TException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void truncate(String cfname) throws InvalidRequestException, UnavailableException, TException {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public Map<String, List<String>> describe_schema_versions() throws InvalidRequestException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public List<KsDef> describe_keyspaces() throws InvalidRequestException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String describe_cluster_name() throws TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String describe_version() throws TException {
    return "Hector-LCP-"+Constants.VERSION;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public List<TokenRange> describe_ring(String keyspace) throws InvalidRequestException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String describe_partitioner() throws TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String describe_snitch() throws TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public KsDef describe_keyspace(String keyspace) throws NotFoundException, InvalidRequestException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public List<String> describe_splits(String cfName, String start_token, String end_token, int keys_per_split) throws InvalidRequestException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String system_add_column_family(CfDef cf_def) throws InvalidRequestException, SchemaDisagreementException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String system_drop_column_family(String column_family) throws InvalidRequestException, SchemaDisagreementException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String system_add_keyspace(KsDef ks_def) throws InvalidRequestException, SchemaDisagreementException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String system_drop_keyspace(String keyspace) throws InvalidRequestException, SchemaDisagreementException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String system_update_keyspace(KsDef ks_def) throws InvalidRequestException, SchemaDisagreementException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String system_update_column_family(CfDef cf_def) throws InvalidRequestException, SchemaDisagreementException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public CqlResult execute_cql_query(ByteBuffer query, Compression compression) throws InvalidRequestException, UnavailableException, TimedOutException, SchemaDisagreementException, TException {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
