package me.prettyprint.cassandra.model;

import static me.prettyprint.cassandra.model.HFactory.createKeyspaceOperator;
import static me.prettyprint.cassandra.model.HFactory.getOrCreateCluster;
import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.service.Cluster;

import org.junit.Before;
import org.junit.Test;
public class MutatorTest extends BaseEmbededServerSetupTest {

  private Cluster cluster;
  private KeyspaceOperator keyspaceOperator;
  
  @Before
  public void doSetup() {
    cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9170");
    keyspaceOperator = createKeyspaceOperator("Keyspace1", cluster);    
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
