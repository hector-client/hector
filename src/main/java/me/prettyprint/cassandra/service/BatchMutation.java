package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.Deletion;
import org.apache.cassandra.thrift.Mutation;

public class BatchMutation {
  
  Map<String, Map<String, List<Mutation>>> mutationMap;
  
  public BatchMutation() {
    mutationMap = new HashMap<String, Map<String,List<Mutation>>>();
  }
  
  public BatchMutation addInsertion(String key, List<String> columnFamilies, ColumnOrSuperColumn columnOrSuperColumn) {    
    Mutation mutation = new Mutation();
    mutation.setColumn_or_supercolumn(columnOrSuperColumn);
    addMutation(key, columnFamilies, mutation);
    return this;
  }
  
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

}
