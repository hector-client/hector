package me.prettyprint.cassandra.service.template;

import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;

import org.junit.Before;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;

public abstract class BaseColumnFamilyTemplateTest extends BaseEmbededServerSetupTest {
	protected Cluster cluster;
  protected Keyspace keyspace;
  static final StringSerializer se = StringSerializer.get();
  
  @Before
  public void setupLocal() {
    cluster = getOrCreateCluster("MyCluster", "127.0.0.1:9170");
    keyspace = createKeyspace("Keyspace1", cluster);
  }
}
