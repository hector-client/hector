package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.Deletion;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.SuperColumn;

/**
 * A BatchMutation object is used to construct the {@link Keyspace#batchMutate(BatchMutation)} call.
 *
 * A BatchMutation encapsulates a set of updates (or insertions) and deletions all submitted at the
 * same time to cassandra. The BatchMutation object is useful for user friendly construction of
 * the thrift call batch_mutate.
 *
 * @author Ran Tavory (rantan@outbrain.com)
 * @author Nathan McCall (nate@vervewireless.com)
 *
 */
public class BatchMutation {

  private final Map<String, Map<String, List<Mutation>>> mutationMap;

  public BatchMutation() {
    mutationMap = new HashMap<String, Map<String,List<Mutation>>>();
  }

  private BatchMutation(Map<String, Map<String, List<Mutation>>> mutationMap) {
    this.mutationMap = mutationMap;
  }

  /**
   * Add an Column insertion (or update) to the batch mutation request.
   */
  public BatchMutation addInsertion(String key, List<String> columnFamilies,
      Column column) {
    Mutation mutation = new Mutation();
    mutation.setColumn_or_supercolumn(new ColumnOrSuperColumn().setColumn(column));
    addMutation(key, columnFamilies, mutation);
    return this;
  }

  /**
   * Add an SuperColumn insertion (or update) to the batch mutation request.
   */
  public BatchMutation addSuperInsertion(String key, List<String> columnFamilies,
      SuperColumn superColumn) {
    Mutation mutation = new Mutation();
    mutation.setColumn_or_supercolumn(new ColumnOrSuperColumn().setSuper_column(superColumn));
    addMutation(key, columnFamilies, mutation);
    return this;
  }

  /**
   * Add a deletion request to the batch mutation.
   */
  public BatchMutation addDeletion(String key, List<String> columnFamilies, Deletion deletion) {
    Mutation mutation = new Mutation();
    mutation.setDeletion(deletion);
    addMutation(key, columnFamilies, mutation);
    return this;
  }

  private void addMutation(String key, List<String> columnFamilies, Mutation mutation) {
    Map<String, List<Mutation>> innerMutationMap = getInnerMutationMap(key);
    for (String columnFamily : columnFamilies) {
      if (innerMutationMap.get(columnFamily) == null) {
        innerMutationMap.put(columnFamily, Arrays.asList(mutation));
      } else {
        List<Mutation> mutations = new ArrayList<Mutation>(innerMutationMap.get(columnFamily));
        mutations.add(mutation);
        innerMutationMap.put(columnFamily, mutations);
      }
    }
    mutationMap.put(key, innerMutationMap);
  }

  private Map<String, List<Mutation>> getInnerMutationMap(String key) {
    Map<String, List<Mutation>> innerMutationMap = mutationMap.get(key);
    if (innerMutationMap == null) {
      innerMutationMap = new HashMap<String, List<Mutation>>();
    }
    return innerMutationMap;
  }

  Map<String, Map<String, List<Mutation>>> getMutationMap() {
    return mutationMap;
  }

  /**
   * Makes a shallow copy of the mutation object.
   * @return
   */
  public BatchMutation makeCopy() {
    return new BatchMutation(mutationMap);
  }

  /**
   * Checks whether the mutation object contains any mutations.
   * @return
   */
  public boolean isEmpty() {
    return mutationMap.isEmpty();
  }
}
