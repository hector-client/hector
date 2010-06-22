package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.service.Cluster;
import me.prettyprint.cassandra.service.ClusterFactory;

import org.junit.Before;
import org.junit.Test;

public class MutatorTest extends BaseEmbededServerSetupTest {

  private Cluster cluster;
  private KeyspaceOperator keyspaceOperator;
  
  @Before
  public void doSetup() {
    cluster = ClusterFactory.getOrCreate("Test Cluster", "127.0.0.1:9170");
    keyspaceOperator = KeyspaceOperatorFactory.create("Keyspace1", cluster);    
  }
  
  @Test
  public void testInsert() {
    /*
    Mutator m = MutatorFactory.createMutator(keyspaceOperator);
    MutationResult mr = m.insert("testInsertGetRemove", "Standard1", 
        m.createColumn("testInsertGetRemove", "testInsertGetRemove_value_"));    
    */
  }
}
