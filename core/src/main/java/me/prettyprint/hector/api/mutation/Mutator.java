package me.prettyprint.hector.api.mutation;

import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.beans.HCounterSuperColumn;
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
public interface Mutator<K> {

  // Simple and immediate insertion of a column
  <N, V> MutationResult insert(final K key, final String cf, final HColumn<N, V> c);

  // overloaded insert-super
  <SN, N, V> MutationResult insert(final K key, final String cf,
      final HSuperColumn<SN, N, V> superColumn);

  <N> MutationResult delete(final K key, final String cf, final N columnName,
      final Serializer<N> nameSerializer);
  
  <N> MutationResult delete(final K key, final String cf, final N columnName,
      final Serializer<N> nameSerializer, long clock);

  /**
   * Deletes a subcolumn of a supercolumn
   * @param <SN> super column type
   * @param <N> subcolumn type
   */
  <SN, N> MutationResult subDelete(final K key, final String cf, final SN supercolumnName,
      final N columnName, final Serializer<SN> sNameSerializer, final Serializer<N> nameSerializer);
  
  /**
   * Deletes a supercolumn immediately
   * @param <SN> super column type
   */
  <SN> MutationResult superDelete(K key, String cf, SN supercolumnName,
      Serializer<SN> sNameSerializer);
  
  /**
   * batches a super column for deletion
   * 
   */
  <SN> Mutator<K> addSuperDelete(K key, String cf, SN sColumnName, Serializer<SN> sNameSerializer);
  

  // schedule an insertion to be executed in batch by the execute method
  // CAVEAT: a large number of calls with a typo in one of them will leave things in an
  // indeterminant state if we dont validate against LIVE (but cached of course)
  // keyspaces and CFs on each add/delete call
  // also, should throw a typed StatementValidationException or similar perhaps?
  <N, V> Mutator<K> addInsertion(K key, String cf, HColumn<N, V> c);

  /**
   * Schedule an insertion of a supercolumn to be inserted in batch mode by {@link #execute()}
   */
  <SN, N, V> Mutator<K> addInsertion(K key, String cf, HSuperColumn<SN, N, V> sc);

  /**
   * Adds a Deletion to the underlying batch_mutate call. The columnName argument can be null
   * in which case Deletion is created with only the Clock, in this case user defined,
   * resulting in the whole row being deleted.
   *
   * @param <N> column name type
   * @param key row key
   * @param cf column family
   * @param columnName column name. Use null to delete the whole row.
   * @param nameSerializer a name serializer
   * @return a mutator
   */
  <N> Mutator<K> addDeletion(K key, String cf, N columnName, Serializer<N> nameSerializer);
  
  /**
   * Alternate form for easy deletion of the whole row.
   * 
   * @param <N>
   * @param key
   * @return
   */
  <N> Mutator<K> addDeletion(K key, String cf);
  
  /**
   * Convenience methods to delete a list of rows.
   * 
   * @param <N>
   * @param keys list of keys to delete
   * @param cf Column Family name
   * @return this object (method chain)
   */
  <N> Mutator<K> addDeletion(Iterable<K> keys, String cf);
  
  /**
   * Convenience methods to delete a list of rows.
   * 
   * @param <N>
   * @param keys list of keys to delete
   * @param cf Column Family name
   * @param clock user defined clock
   * @return this object (method chain)
   */
  <N> Mutator<K> addDeletion(Iterable<K> keys, String cf, long clock);
  
  /**
   * Same as above accept we add the clock
   * 
   * @param <N>
   * @param key
   * @return
   */
  <N> Mutator<K> addDeletion(K key, String cf, long clock);

  /**
   * Adds a Deletion to the underlying batch_mutate call. The columnName argument can be null
   * in which case Deletion is created with only the Clock, in this case user defined,
   * resulting in the whole row being deleted.
   *
   * @param <N> column name type
   * @param key row key
   * @param cf column family
   * @param columnName column name. Use null to delete the whole row.
   * @param nameSerializer a name serializer
   * @param clock custom clock to use in the deletion
   * @return a mutator
   */
  <N> Mutator<K> addDeletion(K key, String cf, N columnName, Serializer<N> nameSerializer, long clock);

  /**
   * Adds a Range-Deletion to the underlying batch_mutate call.
   *
   * @param <N> column name type
   * @param key row key
   * @param cf column family
   * @param columnNameStart starting column name. Cannot be null.
   * @param columnNameFinish end column name. Cannot be null.
   * @param nameSerializer a name serializer
   * @return a mutator
   */
  <N> Mutator<K> addDeletion(K key, String cf, N columnNameStart, N columnNameFinish, Serializer<N> nameSerializer);

  /**
   * Adds a Range-Deletion to the underlying batch_mutate call.
   *
   * @param <N> column name type
   * @param key row key
   * @param cf column family
   * @param columnNameStart starting column name. Cannot be null.
   * @param columnNameFinish end column name. Cannot be null.
   * @param nameSerializer a name serializer
   * @param clock custom clock to use in the deletion
   * @return a mutator
   */
  <N> Mutator<K> addDeletion(K key, String cf, N columnNameStart, N columnNameFinish, Serializer<N> nameSerializer, long clock);

  
  <SN,N,V> Mutator<K> addSubDelete(K key, String cf, HSuperColumn<SN,N,V> sc);

  <SN,N,V> Mutator<K> addSubDelete(K key, String cf, HSuperColumn<SN,N,V> sc, long clock);
  
  <SN,N> Mutator<K> addSubDelete(K key, String cf, SN sColumnName, N columnName, Serializer<SN> sNameSerializer, Serializer<N> nameSerialer);
  
  <SN,N> Mutator<K> addSubDelete(K key, String cf, SN sColumnName, N columnName, Serializer<SN> sNameSerializer, Serializer<N> nameSerialer, long clock);
  
  
  
  /**
   * Batch executes all mutations scheduled to this Mutator instance by addInsertion, addDeletion etc.
   * May throw a HectorException which is a RuntimeException.
   * @return A MutationResult holds the status.
   */
  MutationResult execute();

  /**
   * Discards all pending mutations.
   */
  Mutator<K> discardPendingMutations();
  
  // Support for counters
  
  /** Simple and immediate insertion (increment/decrement) of a counter */
  <N> MutationResult insertCounter(final K key, final String cf, final HCounterColumn<N> c);
  
  /** Simple and immediate insertion (increment/decrement) of a counter part of a super column */
  <SN, N> MutationResult insertCounter(final K key, final String cf, final HCounterSuperColumn<SN, N> superColumn);
  
  /** Convenient method to increment a simple counter */
  <N> MutationResult incrementCounter(final K key, final String cf, final N columnName, final long increment);
  
  /** Convenient method to decrement a simple counter */
  <N> MutationResult decrementCounter(final K key, final String cf, final N columnName, final long increment);

  <N> MutationResult deleteCounter(final K key, final String cf, final N columnName, final Serializer<N> nameSerializer);
  
  /**
   * Deletes a subcolumn of a supercolumn for a counter
   * @param <SN> super column type
   * @param <N> subcolumn type
   */
  <SN, N> MutationResult subDeleteCounter(final K key, final String cf, final SN supercolumnName,
      final N columnName, final Serializer<SN> sNameSerializer, final Serializer<N> nameSerializer);
  
  /**
   * Schedule an increment of a CounterColumn to be inserted in batch mode by {@link #execute()}
   */
  <N> Mutator<K> addCounter(K key, String cf, HCounterColumn<N> c);

  /**
   * Schedule an increment of a SuperColumn to be inserted in batch mode by {@link #execute()}
   */
  <SN, N> Mutator<K> addCounter(K key, String cf, HCounterSuperColumn<SN, N> sc);
  
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
  <N> Mutator<K> addCounterDeletion(K key, String cf, N counterColumnName, Serializer<N> nameSerializer);
  
  /**
   * Alternate form for easy deletion of the whole row.
   * 
   * @param <N>
   * @param key
   * @return
   */
  <N> Mutator<K> addCounterDeletion(K key, String cf);

  /**
   * Schedule a counter deletion.
   */
  <SN,N> Mutator<K> addCounterSubDeletion(K key, String cf, HCounterSuperColumn<SN,N> sc);

  /**
   * Get the size of the pending mutations map
   */
  int getPendingMutationCount();

}