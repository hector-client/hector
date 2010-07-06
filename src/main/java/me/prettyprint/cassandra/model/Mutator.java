package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.model.HFactory.*;

import java.util.Arrays;

import me.prettyprint.cassandra.service.BatchMutation;
import me.prettyprint.cassandra.service.Keyspace;

import org.apache.cassandra.thrift.Deletion;
import org.apache.cassandra.thrift.SlicePredicate;


/**
 * A Mutator inserts or deltes values from the cluster.
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
public class Mutator {

  private final KeyspaceOperator ko;

  private BatchMutation pendingMutations;

  /*package*/ Mutator(KeyspaceOperator ko) {
    this.ko = ko;
  }

  // Simple and immediate insertion of a column
  public <N,V> MutationResult insert(final String key, final String cf, final HColumn<N,V> c) {
    return new MutationResult(ko.doExecute(new KeyspaceOperationCallback<Void>() {
      @Override
      public Void doInKeyspace(Keyspace ks) throws HectorException {
        ks.insert(key, createColumnPath(cf, c.getNameBytes()), c.getValueBytes());
        return null;
      }
    }));
  }

  // overloaded insert-super
  public <SN,N,V> MutationResult insert(final String key, final String cf, final HSuperColumn<SN,N,V> superColumn) {
    return new MutationResult(ko.doExecute(new KeyspaceOperationCallback<Void>() {
      @Override
      public Void doInKeyspace(Keyspace ks) throws HectorException {
        HColumn<N, V> hColumn = superColumn.getColumns().get(0);
        ks.insert(key, createSuperColumnPath(cf, superColumn.getNameBytes(), hColumn.getNameBytes()), hColumn.getValueBytes());
        return null;
      }
    }));
  }

  public <N> MutationResult delete(final String key, final String cf, final N columnName,
      final Extractor<N> nameExtractor) {
    return new MutationResult(ko.doExecute(new KeyspaceOperationCallback<Void>() {
      @Override
      public Void doInKeyspace(Keyspace ks) throws HectorException {
        ks.remove(key, createColumnPath(cf, nameExtractor.toBytes(columnName)));
        return null;
      }
    }));
  }

  // schedule an insertion to be executed in batch by the execute method
  // CAVEAT: a large number of calls with a typo in one of them will leave things in an
  // indeterminant state if we dont validate against LIVE (but cached of course)
  // keyspaces and CFs on each add/delete call
  // also, should throw a typed StatementValidationException or similar perhaps?
  public <N,V> Mutator addInsertion(String key, String cf, HColumn<N,V> c) {
    getPendingMutations().addInsertion(key, Arrays.asList(cf), c.toThrift());
    return this;
  }

  public <N> Mutator addDeletion(String key, String cf, N columnName, Extractor<N> nameExtractor) {
    SlicePredicate sp = new SlicePredicate();
    sp.addToColumn_names(nameExtractor.toBytes(columnName));
    Deletion d = new Deletion(ko.createTimestamp()).setPredicate(sp);
    getPendingMutations().addDeletion(key, Arrays.asList(cf), d);
    return this;
  }


  /**
   * Batch executes all mutations scheduled to this Mutator instance by addInsertion, addDeletion etc.
   * May throw a HectorException which is a RuntimeException.
   * @return A MutationResult holds the status.
   */
  public MutationResult execute() {
    if (pendingMutations == null || pendingMutations.isEmpty()) {
      return new MutationResult(true, 0);
    }
    final BatchMutation mutations = pendingMutations.makeCopy();
    pendingMutations = null;
    return new MutationResult(ko.doExecute(new KeyspaceOperationCallback<Void>() {
      @Override
      public Void doInKeyspace(Keyspace ks) throws HectorException {
        ks.batchMutate(mutations);
        return null;
      }
    }));
  }

  /**
   * Discards all pending mutations.
   */
  public Mutator discardPendingMutations() {
    pendingMutations = null;
    return this;
  }

  @Override
  public String toString() {
    return "Mutator(" + ko.toString() + ")";
  }

  private BatchMutation getPendingMutations() {
    if (pendingMutations == null) {
      pendingMutations = new BatchMutation();
    }
    return pendingMutations;
  }
}
