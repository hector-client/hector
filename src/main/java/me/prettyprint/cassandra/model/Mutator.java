package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.model.HFactory.createColumnPath;
import me.prettyprint.cassandra.service.Keyspace;


/**
 * A Mutator inserts or deltes values from the cluster. 
 * There are two main ways to use a mutator:
 * 1. Use the insert/delete methods to immediately insert of delete values. 
 * or 2. Use the addInsertion/addDeletion methods to schedule batch operations and then execute() 
 * all of them in batch.
 * 
 * @author Ran Tavory 
 */
public class Mutator {

  private final KeyspaceOperator ko;
  
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
  public <SN,N,V> MutationResult insert(String key, String cf, HSuperColumn<SN,N,V> superColumn) {
    //TODO
    return null;
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
    //TODO
    return null;

  }
  
  public <N> Mutator addDeletion(String key, String cf, N columnName, Extractor<N> nameExtractor) {
    //TODO
    return null;

  }


  /**
   * Batch executes all mutations scheduled to this Mutator instance by addInsertion, addDeletion etc.
   * May throw a HectorException which is a RuntimeException. 
   * @return A MutationResult holds the status.
   */
  public MutationResult execute() {
    //TODO
    return null;
  }

  @Override
  public String toString() {
    return "Mutator(" + ko.toString() + ")";
  }
  
  
}
