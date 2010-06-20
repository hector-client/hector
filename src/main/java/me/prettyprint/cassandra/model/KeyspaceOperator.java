package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Cluster;

public interface KeyspaceOperator {

//  void mutate(Mutator mutation);
  
  //Result query(Query q);
  
  void setConsistencyLevelPolicy(ConsistencyLevelPolicy cp);
  
  Cluster getCluster();
  
  <T> T doExecute(KeyspaceOperationCallback<T> koc) throws HectorException; 
}
