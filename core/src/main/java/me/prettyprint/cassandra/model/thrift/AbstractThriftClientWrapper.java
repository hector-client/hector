package me.prettyprint.cassandra.model.thrift;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.AuthenticationException;
import org.apache.cassandra.thrift.AuthenticationRequest;
import org.apache.cassandra.thrift.AuthorizationException;
import org.apache.cassandra.thrift.Cassandra.Client;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.Compression;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.CounterColumn;
import org.apache.cassandra.thrift.CqlResult;
import org.apache.cassandra.thrift.IndexClause;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.KeySlice;
import org.apache.cassandra.thrift.KsDef;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.SchemaDisagreementException;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.TokenRange;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TProtocol;

/**
 * For creating wrappers around the Cassandra client in order to perform pre and
 * post processing
 * 
 * @author Ed Anuff
 */

public abstract class AbstractThriftClientWrapper extends Client {

  Client client;

  public AbstractThriftClientWrapper(Client client) {
    super(client.getInputProtocol(), client.getOutputProtocol());
    this.client = client;
  }

  @Override
  public void add(ByteBuffer key, ColumnParent column_parent,
      CounterColumn column, ConsistencyLevel consistency_level)
      throws InvalidRequestException, UnavailableException, TimedOutException,
      TException {
    client.add(key, column_parent, column, consistency_level);
  }

  @Override
  public void batch_mutate(
      Map<ByteBuffer, Map<String, List<Mutation>>> mutation_map,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    client.batch_mutate(mutation_map, consistency_level);
  }

  @Override
  public String describe_cluster_name() throws TException {
    return client.describe_cluster_name();
  }

  @Override
  public KsDef describe_keyspace(String keyspace) throws NotFoundException,
      InvalidRequestException, TException {
    return client.describe_keyspace(keyspace);
  }

  @Override
  public List<KsDef> describe_keyspaces() throws InvalidRequestException,
      TException {
    return client.describe_keyspaces();
  }

  @Override
  public String describe_partitioner() throws TException {
    return client.describe_partitioner();
  }

  @Override
  public List<TokenRange> describe_ring(String keyspace)
      throws InvalidRequestException, TException {
    return client.describe_ring(keyspace);
  }

  @Override
  public Map<String, List<String>> describe_schema_versions()
      throws InvalidRequestException, TException {
    return client.describe_schema_versions();
  }

  @Override
  public String describe_snitch() throws TException {
    return client.describe_snitch();
  }

  @Override
  public List<String> describe_splits(String cfName, String start_token,
      String end_token, int keys_per_split) throws TException,
      InvalidRequestException {
    return client.describe_splits(cfName, start_token, end_token,
        keys_per_split);
  }

  @Override
  public String describe_version() throws TException {
    return client.describe_version();
  }

  @Override
  public CqlResult execute_cql_query(ByteBuffer query, Compression compression)
      throws InvalidRequestException, UnavailableException, TimedOutException,
      SchemaDisagreementException, TException {
    return client.execute_cql_query(query, compression);
  }

  @Override
  public ColumnOrSuperColumn get(ByteBuffer key, ColumnPath column_path,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      NotFoundException, UnavailableException, TimedOutException, TException {
    return client.get(key, column_path, consistency_level);
  }

  @Override
  public int get_count(ByteBuffer key, ColumnParent column_parent,
      SlicePredicate predicate, ConsistencyLevel consistency_level)
      throws InvalidRequestException, UnavailableException, TimedOutException,
      TException {
    return client.get_count(key, column_parent, predicate, consistency_level);
  }

  @Override
  public List<KeySlice> get_indexed_slices(ColumnParent column_parent,
      IndexClause index_clause, SlicePredicate column_predicate,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    return client.get_indexed_slices(column_parent, index_clause,
        column_predicate, consistency_level);
  }

  @Override
  public List<KeySlice> get_range_slices(ColumnParent column_parent,
      SlicePredicate predicate, KeyRange range,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    return client.get_range_slices(column_parent, predicate, range,
        consistency_level);
  }

  @Override
  public List<ColumnOrSuperColumn> get_slice(ByteBuffer key,
      ColumnParent column_parent, SlicePredicate predicate,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    return client.get_slice(key, column_parent, predicate, consistency_level);
  }

  @Override
  public TProtocol getInputProtocol() {
    return client.getInputProtocol();
  }

  @Override
  public TProtocol getOutputProtocol() {
    return client.getOutputProtocol();
  }

  @Override
  public void insert(ByteBuffer key, ColumnParent column_parent, Column column,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    client.insert(key, column_parent, column, consistency_level);
  }

  @Override
  public void login(AuthenticationRequest auth_request)
      throws AuthenticationException, AuthorizationException, TException {
    client.login(auth_request);
  }

  @Override
  public Map<ByteBuffer, Integer> multiget_count(List<ByteBuffer> keys,
      ColumnParent column_parent, SlicePredicate predicate,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    return client.multiget_count(keys, column_parent, predicate,
        consistency_level);
  }

  @Override
  public Map<ByteBuffer, List<ColumnOrSuperColumn>> multiget_slice(
      List<ByteBuffer> keys, ColumnParent column_parent,
      SlicePredicate predicate, ConsistencyLevel consistency_level)
      throws InvalidRequestException, UnavailableException, TimedOutException,
      TException {
    return client.multiget_slice(keys, column_parent, predicate,
        consistency_level);
  }

  @Override
  public void recv_add() throws InvalidRequestException, UnavailableException,
      TimedOutException, TException {
    client.recv_add();
  }

  @Override
  public void recv_batch_mutate() throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    client.recv_batch_mutate();
  }

  @Override
  public String recv_describe_cluster_name() throws TException {
    return client.recv_describe_cluster_name();
  }

  @Override
  public KsDef recv_describe_keyspace() throws NotFoundException,
      InvalidRequestException, TException {
    return client.recv_describe_keyspace();
  }

  @Override
  public List<KsDef> recv_describe_keyspaces() throws InvalidRequestException,
      TException {
    return client.recv_describe_keyspaces();
  }

  @Override
  public String recv_describe_partitioner() throws TException {
    return client.recv_describe_partitioner();
  }

  @Override
  public List<TokenRange> recv_describe_ring() throws InvalidRequestException,
      TException {
    return client.recv_describe_ring();
  }

  @Override
  public Map<String, List<String>> recv_describe_schema_versions()
      throws InvalidRequestException, TException {
    return client.recv_describe_schema_versions();
  }

  @Override
  public String recv_describe_snitch() throws TException {
    return client.recv_describe_snitch();
  }

  @Override
  public List<String> recv_describe_splits() throws TException,
      InvalidRequestException {
    return client.recv_describe_splits();
  }

  @Override
  public String recv_describe_version() throws TException {
    return client.recv_describe_version();
  }

  @Override
  public CqlResult recv_execute_cql_query() throws InvalidRequestException,
      UnavailableException, TimedOutException, SchemaDisagreementException,
      TException {
    return client.recv_execute_cql_query();
  }

  @Override
  public ColumnOrSuperColumn recv_get() throws InvalidRequestException,
      NotFoundException, UnavailableException, TimedOutException, TException {
    return client.recv_get();
  }

  @Override
  public int recv_get_count() throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    return client.recv_get_count();
  }

  @Override
  public List<KeySlice> recv_get_indexed_slices()
      throws InvalidRequestException, UnavailableException, TimedOutException,
      TException {
    return client.recv_get_indexed_slices();
  }

  @Override
  public List<KeySlice> recv_get_range_slices() throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    return client.recv_get_range_slices();
  }

  @Override
  public List<ColumnOrSuperColumn> recv_get_slice()
      throws InvalidRequestException, UnavailableException, TimedOutException,
      TException {
    return client.recv_get_slice();
  }

  @Override
  public void recv_insert() throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    client.recv_insert();
  }

  @Override
  public void recv_login() throws AuthenticationException,
      AuthorizationException, TException {
    client.recv_login();
  }

  @Override
  public Map<ByteBuffer, Integer> recv_multiget_count()
      throws InvalidRequestException, UnavailableException, TimedOutException,
      TException {
    return client.recv_multiget_count();
  }

  @Override
  public Map<ByteBuffer, List<ColumnOrSuperColumn>> recv_multiget_slice()
      throws InvalidRequestException, UnavailableException, TimedOutException,
      TException {
    return client.recv_multiget_slice();
  }

  @Override
  public void recv_remove() throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    client.recv_remove();
  }

  @Override
  public void recv_remove_counter() throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    client.recv_remove_counter();
  }

  @Override
  public void recv_set_keyspace() throws InvalidRequestException, TException {
    client.recv_set_keyspace();
  }

  @Override
  public String recv_system_add_column_family() throws InvalidRequestException,
      TException, SchemaDisagreementException {
    return client.recv_system_add_column_family();
  }

  @Override
  public String recv_system_add_keyspace() throws InvalidRequestException,
      TException, SchemaDisagreementException {
    return client.recv_system_add_keyspace();
  }

  @Override
  public String recv_system_drop_column_family()
      throws InvalidRequestException, TException, SchemaDisagreementException {
    return client.recv_system_drop_column_family();
  }

  @Override
  public String recv_system_drop_keyspace() throws InvalidRequestException,
      TException, SchemaDisagreementException {
    return client.recv_system_drop_keyspace();
  }

  @Override
  public String recv_system_update_column_family()
      throws InvalidRequestException, TException, SchemaDisagreementException {
    return client.recv_system_update_column_family();
  }

  @Override
  public String recv_system_update_keyspace() throws InvalidRequestException,
      TException, SchemaDisagreementException {
    return client.recv_system_update_keyspace();
  }

  @Override
  public void recv_truncate() throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    client.recv_truncate();
  }

  @Override
  public void remove(ByteBuffer key, ColumnPath column_path, long timestamp,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    client.remove(key, column_path, timestamp, consistency_level);
  }

  @Override
  public void remove_counter(ByteBuffer key, ColumnPath path,
      ConsistencyLevel consistency_level) throws InvalidRequestException,
      UnavailableException, TimedOutException, TException {
    client.remove_counter(key, path, consistency_level);
  }

  @Override
  public void send_add(ByteBuffer key, ColumnParent column_parent,
      CounterColumn column, ConsistencyLevel consistency_level)
      throws TException {
    client.send_add(key, column_parent, column, consistency_level);
  }

  @Override
  public void send_batch_mutate(
      Map<ByteBuffer, Map<String, List<Mutation>>> mutation_map,
      ConsistencyLevel consistency_level) throws TException {
    client.send_batch_mutate(mutation_map, consistency_level);
  }

  @Override
  public void send_describe_cluster_name() throws TException {
    client.send_describe_cluster_name();
  }

  @Override
  public void send_describe_keyspace(String keyspace) throws TException {
    client.send_describe_keyspace(keyspace);
  }

  @Override
  public void send_describe_keyspaces() throws TException {
    client.send_describe_keyspaces();
  }

  @Override
  public void send_describe_partitioner() throws TException {
    client.send_describe_partitioner();
  }

  @Override
  public void send_describe_ring(String keyspace) throws TException {
    client.send_describe_ring(keyspace);
  }

  @Override
  public void send_describe_schema_versions() throws TException {
    client.send_describe_schema_versions();
  }

  @Override
  public void send_describe_snitch() throws TException {
    client.send_describe_snitch();
  }

  @Override
  public void send_describe_splits(String cfName, String start_token,
      String end_token, int keys_per_split) throws TException {
    client.send_describe_splits(cfName, start_token, end_token, keys_per_split);
  }

  @Override
  public void send_describe_version() throws TException {
    client.send_describe_version();
  }

  @Override
  public void send_execute_cql_query(ByteBuffer query, Compression compression)
      throws TException {
    client.send_execute_cql_query(query, compression);
  }

  @Override
  public void send_get(ByteBuffer key, ColumnPath column_path,
      ConsistencyLevel consistency_level) throws TException {
    client.send_get(key, column_path, consistency_level);
  }

  @Override
  public void send_get_count(ByteBuffer key, ColumnParent column_parent,
      SlicePredicate predicate, ConsistencyLevel consistency_level)
      throws TException {
    client.send_get_count(key, column_parent, predicate, consistency_level);
  }

  @Override
  public void send_get_indexed_slices(ColumnParent column_parent,
      IndexClause index_clause, SlicePredicate column_predicate,
      ConsistencyLevel consistency_level) throws TException {
    client.send_get_indexed_slices(column_parent, index_clause,
        column_predicate, consistency_level);
  }

  @Override
  public void send_get_range_slices(ColumnParent column_parent,
      SlicePredicate predicate, KeyRange range,
      ConsistencyLevel consistency_level) throws TException {
    client.send_get_range_slices(column_parent, predicate, range,
        consistency_level);
  }

  @Override
  public void send_get_slice(ByteBuffer key, ColumnParent column_parent,
      SlicePredicate predicate, ConsistencyLevel consistency_level)
      throws TException {
    client.send_get_slice(key, column_parent, predicate, consistency_level);
  }

  @Override
  public void send_insert(ByteBuffer key, ColumnParent column_parent,
      Column column, ConsistencyLevel consistency_level) throws TException {
    client.send_insert(key, column_parent, column, consistency_level);
  }

  @Override
  public void send_login(AuthenticationRequest auth_request) throws TException {
    client.send_login(auth_request);
  }

  @Override
  public void send_multiget_count(List<ByteBuffer> keys,
      ColumnParent column_parent, SlicePredicate predicate,
      ConsistencyLevel consistency_level) throws TException {
    client.send_multiget_count(keys, column_parent, predicate,
        consistency_level);
  }

  @Override
  public void send_multiget_slice(List<ByteBuffer> keys,
      ColumnParent column_parent, SlicePredicate predicate,
      ConsistencyLevel consistency_level) throws TException {
    client.send_multiget_slice(keys, column_parent, predicate,
        consistency_level);
  }

  @Override
  public void send_remove(ByteBuffer key, ColumnPath column_path,
      long timestamp, ConsistencyLevel consistency_level) throws TException {
    client.send_remove(key, column_path, timestamp, consistency_level);
  }

  @Override
  public void send_remove_counter(ByteBuffer key, ColumnPath path,
      ConsistencyLevel consistency_level) throws TException {
    client.send_remove_counter(key, path, consistency_level);
  }

  @Override
  public void send_set_keyspace(String keyspace) throws TException {
    client.send_set_keyspace(keyspace);
  }

  @Override
  public void send_system_add_column_family(CfDef cf_def) throws TException {
    client.send_system_add_column_family(cf_def);
  }

  @Override
  public void send_system_add_keyspace(KsDef ks_def) throws TException {
    client.send_system_add_keyspace(ks_def);
  }

  @Override
  public void send_system_drop_column_family(String column_family)
      throws TException {
    client.send_system_drop_column_family(column_family);
  }

  @Override
  public void send_system_drop_keyspace(String keyspace) throws TException {
    client.send_system_drop_keyspace(keyspace);
  }

  @Override
  public void send_system_update_column_family(CfDef cf_def) throws TException {
    client.send_system_update_column_family(cf_def);
  }

  @Override
  public void send_system_update_keyspace(KsDef ks_def) throws TException {
    client.send_system_update_keyspace(ks_def);
  }

  @Override
  public void send_truncate(String cfname) throws TException {
    client.send_truncate(cfname);
  }

  @Override
  public void set_keyspace(String keyspace) throws InvalidRequestException,
      TException {
    client.set_keyspace(keyspace);
  }

  @Override
  public String system_add_column_family(CfDef cf_def)
      throws InvalidRequestException, TException, SchemaDisagreementException {
    return client.system_add_column_family(cf_def);
  }

  @Override
  public String system_add_keyspace(KsDef ks_def)
      throws InvalidRequestException, TException, SchemaDisagreementException {
    return client.system_add_keyspace(ks_def);
  }

  @Override
  public String system_drop_column_family(String column_family)
      throws InvalidRequestException, TException, SchemaDisagreementException {
    return client.system_drop_column_family(column_family);
  }

  @Override
  public String system_drop_keyspace(String keyspace)
      throws InvalidRequestException, TException, SchemaDisagreementException {
    return client.system_drop_keyspace(keyspace);
  }

  @Override
  public String system_update_column_family(CfDef cf_def)
      throws InvalidRequestException, TException, SchemaDisagreementException {
    return client.system_update_column_family(cf_def);
  }

  @Override
  public String system_update_keyspace(KsDef ks_def)
      throws InvalidRequestException, TException, SchemaDisagreementException {
    return client.system_update_keyspace(ks_def);
  }

  @Override
  public void truncate(String cfname) throws InvalidRequestException,
      UnavailableException, TException, TimedOutException {
    client.truncate(cfname);
  }
}
