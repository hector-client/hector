package me.prettyprint.cassandra.locking;

import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.*;

import java.util.List;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.locking.HLockManager;
import me.prettyprint.hector.api.locking.HLockManagerConfigurator;

import org.junit.Test;

public class HLockManagerImplTest extends BaseEmbededServerSetupTest {

  @Test
  public void testInitWithDefaults() {
    Cluster cluster = getOrCreateCluster("MyCluster", getCHCForTest());
    HLockManager lm = new HLockManagerImpl(cluster);
    lm.init();
    
    KeyspaceDefinition keyspaceDef = cluster.describeKeyspace(lm.getKeyspace().getKeyspaceName());
    assertNotNull(keyspaceDef);
    assertTrue(verifyCFCreation(keyspaceDef.getCfDefs()));
   
  }

  private boolean verifyCFCreation(List<ColumnFamilyDefinition> cfDefs) {
    for (ColumnFamilyDefinition cfDef : cfDefs) {
      if (cfDef.getName().equals(HLockManagerConfigurator.DEFAUT_LOCK_MANAGER_CF))
        return true;
    }
    return false;
  }

}
