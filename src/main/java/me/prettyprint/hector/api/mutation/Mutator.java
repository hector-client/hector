package me.prettyprint.hector.api.mutation;

import me.prettyprint.cassandra.model.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;

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
 */
public interface Mutator {

  // Simple and immediate insertion of a column
  <N, V> MutationResult insert(final String key, final String cf, final HColumn<N, V> c);

  // overloaded insert-super
  <SN, N, V> MutationResult insert(final String key, final String cf,
      final HSuperColumn<SN, N, V> superColumn);

  <N> MutationResult delete(final String key, final String cf, final N columnName,
      final Serializer<N> nameSerializer);

  /**
   * Deletes a subcolumn of a supercolumn
   * @param <SN> super column type
   * @param <N> subcolumn type
   */
  <SN, N> MutationResult subDelete(final String key, final String cf, final SN supercolumnName,
      final N columnName, final Serializer<SN> sNameSerializer, final Serializer<N> nameSerializer);

  // schedule an insertion to be executed in batch by the execute method
  // CAVEAT: a large number of calls with a typo in one of them will leave things in an
  // indeterminant state if we dont validate against LIVE (but cached of course)
  // keyspaces and CFs on each add/delete call
  // also, should throw a typed StatementValidationException or similar perhaps?
  <N, V> Mutator addInsertion(String key, String cf, HColumn<N, V> c);

  /**
   * Schedule an insertion of a supercolumn to be inserted in batch mode by {@link #execute()}
   */
  <SN, N, V> Mutator addInsertion(String key, String cf, HSuperColumn<SN, N, V> sc);

  <N> Mutator addDeletion(String key, String cf, N columnName, Serializer<N> nameSerializer);

  /**
   * Batch executes all mutations scheduled to this Mutator instance by addInsertion, addDeletion etc.
   * May throw a HectorException which is a RuntimeException.
   * @return A MutationResult holds the status.
   */
  MutationResult execute();

  /**
   * Discards all pending mutations.
   */
  Mutator discardPendingMutations();

}