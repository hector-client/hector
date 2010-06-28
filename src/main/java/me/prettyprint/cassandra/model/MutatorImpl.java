package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;
import static me.prettyprint.cassandra.model.HFactory.*;

/**
 * Initial implementaiton of Mutator
 * @author zznate
 *
 */
public class MutatorImpl implements Mutator {
  private final KeyspaceOperator keyspaceOperator;
  
  MutatorImpl(KeyspaceOperator keyspaceOperator) {
    this.keyspaceOperator = keyspaceOperator;
  }
  
  @Override
  public <N> Mutator addDeletion(String key, String cf, N columnName, Extractor<N> nameExtractor) {

    return null;
  }

  @Override
  public <N, V> Mutator addInsertion(String key, String cf, HColumn<N, V> c) {
    return null;
  }

  @Override
  public <N> MutationResult delete(final String key, final String cf, 
      final N columnName, final Extractor<N> nameExtractor) {
    MutationResult mutationResult = keyspaceOperator.doExecute(new KeyspaceOperationCallback<MutationResult>() {
      @Override
      public MutationResult doInKeyspace(Keyspace ks) throws HectorException {
        MutationResult mutationResult = new MutationResultImpl();
        ks.remove(key, createColumnPath(cf, columnName, nameExtractor));
        return mutationResult;
      }            
    });    
    return mutationResult;
  }

  @Override
  public <N, V> MutationResult insert(String key, String cf, HColumn<N, V> c) {
    return null;
  }

  @Override
  public <SN, N, V> MutationResult insert(String key, String cf, HSuperColumn<SN, N, V> superColumn) {
    return null;
  }

  @Override
  public MutationResult execute() {
    return null;
  }

}
