package me.prettyprint.cassandra.service;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.prettyprint.hector.api.Serializer;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.CounterColumn;
import org.apache.cassandra.thrift.CounterSuperColumn;
import org.apache.cassandra.thrift.Deletion;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.SuperColumn;

/**
 * A BatchMutation object is used to construct the {@link KeyspaceService#batchMutate(BatchMutation)} call.
 *
 * A BatchMutation encapsulates a set of updates (or insertions) and deletions all submitted at the
 * same time to cassandra. The BatchMutation object is useful for user friendly construction of
 * the thrift call batch_mutate.
 *
 * @author Ran Tavory (rantan@outbrain.com)
 * @author Nathan McCall (nate@riptano.com)
 * @author Patricio Echague (patricioe@gmail.com)
 *
 */
public final class BatchMutation<K> {

  private final Map<ByteBuffer,Map<String,List<Mutation>>> mutationMap;
  private final Serializer<K> keySerializer;
  private BatchSizeHint sizeHint;

  public BatchMutation(Serializer<K> serializer, BatchSizeHint sizeHint) {
    this.keySerializer = serializer;
    this.sizeHint = sizeHint;
    if (null == sizeHint) {
      mutationMap = new HashMap<ByteBuffer,Map<String,List<Mutation>>>();
    }
    else {
      mutationMap = new HashMap<ByteBuffer,Map<String,List<Mutation>>>(sizeHint.getNumOfRows());
    }
  }

  public BatchMutation(Serializer<K> serializer) {
    this(serializer, null);
  }

  private BatchMutation(Serializer<K> serializer, Map<ByteBuffer,Map<String,List<Mutation>>> mutationMap, BatchSizeHint sizeHint) {
    this.keySerializer = serializer;
    this.mutationMap = mutationMap;
    this.sizeHint = sizeHint;
  }

  /**
   * Add an Column insertion (or update) to the batch mutation request.
   */
  public BatchMutation<K> addInsertion(K key, List<String> columnFamilies,
      Column column) {
    Mutation mutation = new Mutation();
    mutation.setColumn_or_supercolumn(new ColumnOrSuperColumn().setColumn(column));
    addMutation(key, columnFamilies, mutation);
    return this;
  }
  
  /**
   * Add a SuperColumn insertion (or update) to the batch mutation request.
   */
  public BatchMutation<K> addSuperInsertion(K key, List<String> columnFamilies,
      SuperColumn superColumn) {
    Mutation mutation = new Mutation();
    mutation.setColumn_or_supercolumn(new ColumnOrSuperColumn().setSuper_column(superColumn));
    addMutation(key, columnFamilies, mutation);
    return this;
  }
  
  /**
   * Add a ColumnCounter insertion (or update)
   */
  public BatchMutation<K> addCounterInsertion(K key, List<String> columnFamilies, CounterColumn counterColumn) {
    Mutation mutation = new Mutation();
    mutation.setColumn_or_supercolumn(new ColumnOrSuperColumn().setCounter_column(counterColumn));
    addMutation(key, columnFamilies, mutation);
    return this;
  }
  
  /**
   * Add a SuperColumnCounter insertion (or update)
   */
  public BatchMutation<K> addSuperCounterInsertion(K key, List<String> columnFamilies, 
      CounterSuperColumn counterSuperColumn) {
    Mutation mutation = new Mutation();
    mutation.setColumn_or_supercolumn(new ColumnOrSuperColumn().setCounter_super_column(counterSuperColumn));
    addMutation(key, columnFamilies, mutation);
    return this;
  }

  /**
   * Add a deletion request to the batch mutation.
   */
  public BatchMutation<K> addDeletion(K key, List<String> columnFamilies, Deletion deletion) {
    Mutation mutation = new Mutation();
    mutation.setDeletion(deletion);
    addMutation(key, columnFamilies, mutation);
    return this;
  }

  private void addMutation(K key, List<String> columnFamilies, Mutation mutation) {
    Map<String, List<Mutation>> innerMutationMap = getInnerMutationMap(key);
    for (String columnFamily : columnFamilies) {
      List<Mutation> mutList = innerMutationMap.get(columnFamily);
      if (mutList == null) {
    	if (sizeHint == null) {
    	  mutList = new LinkedList<Mutation>();
    	}
    	else {
    	  mutList = new ArrayList<Mutation>(sizeHint.getNumOfColumns());	
    	}
        innerMutationMap.put(columnFamily, mutList);
      }
      mutList.add(mutation);
    }
  }
  

  private Map<String, List<Mutation>> getInnerMutationMap(K key) {
    Map<String, List<Mutation>> innerMutationMap = mutationMap.get(keySerializer.toByteBuffer(key));
    if (innerMutationMap == null) {
      innerMutationMap = new HashMap<String, List<Mutation>>();
      mutationMap.put(keySerializer.toByteBuffer(key), innerMutationMap);
    }
    return innerMutationMap;
  }
  
  public Map<ByteBuffer,Map<String,List<Mutation>>> getMutationMap() {
    return mutationMap;
  }

  /**
   * Makes a shallow copy of the mutation object.
   * @return
   */
  public BatchMutation<K> makeCopy() {
    return new BatchMutation<K>(keySerializer, mutationMap, sizeHint);
  }

  /**
   * Checks whether the mutation object contains any mutations.
   * @return
   */
  public boolean isEmpty() {
    return mutationMap.isEmpty() ;
  }

  /**
   * Return the current size of the underlying map
   * @return
   */
  public int getSize() {
    return mutationMap.size();
  }
}
