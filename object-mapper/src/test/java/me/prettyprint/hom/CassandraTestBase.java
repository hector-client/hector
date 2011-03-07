package me.prettyprint.hom;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.cassandra.config.ConfigurationException;
import org.apache.cassandra.contrib.utils.service.CassandraServiceDataCleaner;
import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.service.EmbeddedCassandraService;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.KsDef;
import org.apache.thrift.transport.TTransportException;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class CassandraTestBase {
  protected static boolean cassandraStarted = false;
  protected static Keyspace keyspace;
  protected static Cluster cluster;

  protected static EmbeddedServerHelper embedded;
  
  public static void createKeyspace(Cluster cluster, String name, String strategy, int replicationFactor,
      List<CfDef> cfDefList) {
    try {
      KsDef ksDef = new KsDef(name, strategy, replicationFactor, cfDefList);
      cluster.addKeyspace(new ThriftKsDef(ksDef));
      return;
    }
    catch (Throwable e) {
      System.out.println("exception while creating keyspace, " + name + " - probably already exists : "
          + e.getMessage());
    }

    for (CfDef cfDef : cfDefList) {
      try {
        cluster.addColumnFamily(new ThriftCfDef(cfDef));
      }
      catch (Throwable e) {
        System.out.println("exception while creating CF, " + cfDef.getName() + " - probably already exists : "
            + e.getMessage());
      }
    }
  }

  @BeforeClass
  public static void setupKeyspace() throws TTransportException,
  SecurityException, IllegalArgumentException, IOException,
  InterruptedException, NoSuchMethodException, IllegalAccessException,
  InvocationTargetException {

    embedded = new EmbeddedServerHelper();
    try {
      embedded.setup();
    } catch (ConfigurationException ce) {
      ce.printStackTrace();
    }
    ArrayList<CfDef> cfDefList = new ArrayList<CfDef>(2);
    cfDefList.add(new CfDef("TestKeyspace", "TestBeanColumnFamily").setComparator_type(BytesType.class.getSimpleName())
        .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
    cfDefList.add(new CfDef("TestKeyspace", "CustomIdColumnFamily").setComparator_type(BytesType.class.getSimpleName())
        .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
    cfDefList.add(new CfDef("TestKeyspace", "SimpleTestBeanColumnFamily").setComparator_type(BytesType.class.getSimpleName())
        .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));  
    cfDefList.add(new CfDef("TestKeyspace", "SimpleRelationshipBeanColumnFamily").setComparator_type(BytesType.class.getSimpleName())
        .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));          
    cfDefList.add(new CfDef("TestKeyspace", "NoAnonymousColumnFamily").setComparator_type(BytesType.class.getSimpleName())
        .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));

    cfDefList.add(new CfDef("TestKeyspace", "ComplexColumnFamily").setComparator_type(BytesType.class.getSimpleName())
        .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
    cluster = HFactory.getOrCreateCluster("TestPool", "localhost:9161");
    createKeyspace(cluster, "TestKeyspace", "org.apache.cassandra.locator.SimpleStrategy", 1, cfDefList);
    keyspace = HFactory.createKeyspace("TestKeyspace", cluster);
  }

  @AfterClass
  public static void teardown() throws IOException {
    embedded.teardown();
    embedded = null;
  }
}
