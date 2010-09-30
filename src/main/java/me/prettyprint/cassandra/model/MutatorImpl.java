package me.prettyprint.cassandra.model;

import java.util.Arrays;

import me.prettyprint.cassandra.model.thrift.ThriftFactory;
import me.prettyprint.cassandra.serializers.TypeInferringSerializer;
import me.prettyprint.cassandra.service.BatchMutation;
import me.prettyprint.cassandra.service.KeyspaceService;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;

import org.apache.cassandra.thrift.Deletion;
import org.apache.cassandra.thrift.SlicePredicate;



/**
 * A Mutator inserts or deletes values from the cluster.
 * There are two main ways to use a mutator:
 * 1. Use the insert/delete methods to immediately insert of delete values.
 * or 2. Use the addInsertion/addDeletion methods to schedule batch operations and then execute()
 * all of them in batch.
 *
 * The class is not thread-safe.
 *
 * @author Ran Tavory
 * @author zznate
 */
public final class MutatorImpl<K> implements Mutator<K> {

  private final ExecutingKeyspace keyspace;

  protected final Serializer<K> keySerializer;

  private BatchMutation<K> pendingMutations;

  public MutatorImpl(Keyspace keyspace, Serializer<K> keySerializer) {
    this.keyspace = (ExecutingKeyspace) keyspace;
    this.keySerializer = keySerializer;
  }

  public MutatorImpl(Keyspace keyspace) {
    this(keyspace, TypeInferringSerializer.<K> get());
  }

  // Simple and immediate insertion of a column
  @Override
  public <N,V> MutationResult insert(final K key, final String cf, final HColumn<N,V> c) {
    addInsertion(key, cf, c);
    return execute();
  }

  // overloaded insert-super
  @Override
  public <SN,N,V> MutationResult insert(final K key, final String cf,
      final HSuperColumn<SN,N,V> superColumn) {
    addInsertion(key, cf, superColumn);
    return execute();
  }

  @Override
  public <N> MutationResult delete(final K key, final String cf, final N columnName,
      final Serializer<N> nameSerializer) {
    addDeletion(key, cf, columnName, nameSerializer);
    return execute();
  }

/**
 * Deletes a subcolumn of a supercolumn
 * @param <SN> super column type
 * @param <N> subcolumn type
 */
  @Override
  public <SN,N> MutationResult subDelete(final K key, final String cf, final SN supercolumnName,
      final N columnName, final Serializer<SN> sNameSerializer, final Serializer<N> nameSerializer) {
    return new MutationResultImpl(keyspace.doExecute(new KeyspaceOperationCallback<Void>() {
      @Override
      public Void doInKeyspace(KeyspaceService ks) throws HectorException {
        ks.remove(keySerializer.toBytes(key), ThriftFactory.createSuperColumnPath(cf,
            supercolumnName, columnName, sNameSerializer, nameSerializer));
        return null;
      }
    }));
  }

  // schedule an insertion to be executed in batch by the execute method
  // CAVEAT: a large number of calls with a typo in one of them will leave things in an
  // indeterminant state if we dont validate against LIVE (but cached of course)
  // keyspaces and CFs on each add/delete call
  // also, should throw a typed StatementValidationException or similar perhaps?
  @Override
  public <N,V> Mutator<K> addInsertion(K key, String cf, HColumn<N,V> c) {
    getPendingMutations().addInsertion(key, Arrays.asList(cf),
        ((HColumnImpl<N, V>) c).toThrift());
    return this;
  }

  /**
   * Schedule an insertion of a supercolumn to be inserted in batch mode by {@link #execute()}
   */
  @Override
  public <SN,N,V> Mutator<K> addInsertion(K key, String cf, HSuperColumn<SN,N,V> sc) {
    getPendingMutations().addSuperInsertion(key, Arrays.asList(cf),
        ((HSuperColumnImpl<SN,N,V>) sc).toThrift());
    return this;
  }

  /**
   * Adds a Deletion to the underlying batch_mutate call. The columnName argument can be null
   * in which case Deletion is created with only the Clock, resulting in the whole row being deleted
   */
  @Override
  public <N> Mutator<K> addDeletion(K key, String cf, N columnName, Serializer<N> nameSerializer) {
    SlicePredicate sp = new SlicePredicate();
    sp.addToColumn_names(nameSerializer.toBytes(columnName));
    Deletion d = columnName != null ? new Deletion(keyspace.createClock()).setPredicate(sp)
                                   : new Deletion(keyspace.createClock());
    getPendingMutations().addDeletion(key, Arrays.asList(cf), d);
    return this;
  }

  /**
   * Batch executes all mutations scheduled to this Mutator instance by addInsertion, addDeletion etc.
   * May throw a HectorException which is a RuntimeException.
   * @return A MutationResult holds the status.
   */
  @Override
  public MutationResult execute() {
    if (pendingMutations == null || pendingMutations.isEmpty()) {
      return new MutationResultImpl(true, 0, null);
    }
    final BatchMutation<K> mutations = pendingMutations.makeCopy();
    pendingMutations = null;
    return new MutationResultImpl(keyspace.doExecute(new KeyspaceOperationCallback<Void>() {
      @Override
      public Void doInKeyspace(KeyspaceService ks) throws HectorException {
        ks.batchMutate(mutations);
        return null;
      }
    }));
  }

  /**
   * Discards all pending mutations.
   */
  @Override
  public Mutator<K> discardPendingMutations() {
    pendingMutations = null;
    return this;
  }

  @Override
  public String toString() {
    return "Mutator(" + keyspace.toString() + ")";
  }

  private BatchMutation<K> getPendingMutations() {
    if (pendingMutations == null) {
      pendingMutations = new BatchMutation<K>(keySerializer);
    }
    return pendingMutations;
  }

}
