package me.prettyprint.cassandra.model;

public interface KeyspaceOperator {

//  void mutate(Mutator mutation);
  
//  Result query(Query q);
  
  void setConsistencyLevelPolicy(ConsistencyLevelPolicy cp);
  
  Cluster getCluster();
}
