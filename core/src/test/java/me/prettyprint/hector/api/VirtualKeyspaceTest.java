package me.prettyprint.hector.api;

import static me.prettyprint.hector.api.factory.HFactory.createVirtualKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;

import java.util.UUID;

// Run the entire ApiV2SystemTest but with a prefixed keyspace

public class VirtualKeyspaceTest extends ApiV2SystemTest {

  @Override
  public void setupCase() {
    cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
    ko = createVirtualKeyspace(KEYSPACE, UUID.randomUUID().toString(), se,
        cluster);
  }

}
