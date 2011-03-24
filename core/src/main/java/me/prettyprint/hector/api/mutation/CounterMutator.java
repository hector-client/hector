package me.prettyprint.hector.api.mutation;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.beans.HCounterSuperColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;

/**
 * A Mutator inserts or deletes counter values from the cluster.
 * There are two main ways to use a mutator:
 * 1. Use the increment/decrement/delete methods to immediately increment, decrement or delete values.
 * or 2. Use the addIncrement/addDecrement/addDeletion methods to schedule batch operations and then execute()
 * all of them in batch.
 *
 * The class is not thread-safe.
 *
 * @author patricioe (patricioe@gmail.com)
 */
public interface CounterMutator<K> {

  // Simple and immediate insertion of a column
  <N> MutationResult increment(final K key, final String cf, final HCounterColumn<N> c);
  
  <N> MutationResult decrement(final K key, final String cf, final HCounterColumn<N> c);
  
  // overloaded insert-super
  <SN, N> MutationResult increment(final K key, final String cf, final HCounterSuperColumn<SN, N> superColumn);
  
  <SN, N> MutationResult decrement(final K key, final String cf, final HCounterSuperColumn<SN, N> superColumn);

  <N> MutationResult delete(final K key, final String cf, final N columnName,
      final Serializer<N> nameSerializer);

  /**
   * Deletes a subcolumn of a supercolumn for a counter
   * @param <SN> super column type
   * @param <N> subcolumn type
   */
  <SN, N> MutationResult subDelete(final K key, final String cf, final SN supercolumnName,
      final N columnName, final Serializer<SN> sNameSerializer, final Serializer<N> nameSerializer);

  /**
   * Schedule an increment of a CounterColumn to be inserted in batch mode by {@link #execute()}
   */
  <N> CounterMutator<K> addIncrement(K key, String cf, HCounterColumn<N> c);

  /**
   * Schedule an increment of a SuperColumn to be inserted in batch mode by {@link #execute()}
   */
  <SN, N> CounterMutator<K> addIncrement(K key, String cf, HCounterSuperColumn<SN, N> sc);
  
  /**
   * Schedule a decrement of a CounterColumn to be inserted in batch mode by {@link #execute()}
   */
  <N> CounterMutator<K> addDecrement(K key, String cf, HCounterColumn<N> c);

  /**
   * Schedule a decrement of a SuperColumn to be inserted in batch mode by {@link #execute()}
   */
  <SN, N> CounterMutator<K> addDecrement(K key, String cf, HCounterSuperColumn<SN, N> sc);

  /**
   * Adds a Deletion to the underlying batch_mutate call. The columnName argument can be null
   * in which case the whole row being deleted.
   *
   * @param <N> column name type
   * @param key row key
   * @param cf column family
   * @param counterColumnName column name. Use null to delete the whole row.
   * @param nameSerializer a name serializer
   * @return a mutator
   */
  <N> CounterMutator<K> addDeletion(K key, String cf, N counterColumnName, Serializer<N> nameSerializer);
  
  /**
   * Alternate form for easy deletion of the whole row.
   * 
   * @param <N>
   * @param key
   * @return
   */
  <N> CounterMutator<K> addDeletion(K key, String cf);

  
  <SN,N> CounterMutator<K> addSubDeletion(K key, String cf, HCounterSuperColumn<SN,N> sc);

  
  /**
   * Batch executes all mutations scheduled to this Mutator instance by addIncrement, addDecrement and addDeletion etc.
   * May throw a HectorException which is a RuntimeException.
   * @return A MutationResult holds the status.
   */
  MutationResult execute();

  /**
   * Discards all pending mutations.
   */
  CounterMutator<K> discardPendingMutations();

}