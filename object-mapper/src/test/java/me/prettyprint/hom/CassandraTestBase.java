package me.prettyprint.hom;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.testutils.EmbeddedServerHelper;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.KsDef;
import org.apache.thrift.transport.TTransportException;
import org.junit.BeforeClass;

public class CassandraTestBase {
  protected static boolean cassandraStarted = false;
  protected static Keyspace keyspace;
  protected static Cluster cluster;
  protected static EmbeddedServerHelper embedded;

  public static void startCassandraInstance(String pathToDataDir) throws TTransportException, IOException,
  InterruptedException, SecurityException, IllegalArgumentException, NoSuchMethodException,
  IllegalAccessException, InvocationTargetException {
    if (cassandraStarted) {
      return;
    }

    try {
      FileUtils.deleteRecursive(new File(pathToDataDir));
    }
    catch (AssertionError e) {
      // eat
    }

    embedded = new EmbeddedServerHelper();
    try {
      embedded.setup();
    } catch (ConfigurationException ce) {
      throw new RuntimeException(ce);
    }
    
    cassandraStarted = true;
  }

  public static void createKeyspace(Cluster cluster, String name, String strategy, int replicationFactor,
      List<CfDef> cfDefList) {
    try {
      KsDef ksDef = new KsDef(name, strategy, cfDefList);
      ksDef.setReplication_factor(replicationFactor);
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
        startCassandraInstance("tmp/var/lib/cassandra");

        ArrayList<CfDef> cfDefList = new ArrayList<CfDef>(2);
        cfDefList.add(new CfDef("TestKeyspace", "AnonumousColumnFamily").setComparator_type(BytesType.class.getSimpleName())
            .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
        cfDefList.add(new CfDef("TestKeyspace", "TestBeanColumnFamily").setComparator_type(BytesType.class.getSimpleName())
            .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
        cfDefList.add(new CfDef("TestKeyspace", "TestBeanColumnFamily").setComparator_type(BytesType.class.getSimpleName())
            .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
        cfDefList.add(new CfDef("TestKeyspace", "PurpleColumnFamily").setComparator_type(BytesType.class.getSimpleName())
            .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
        cfDefList.add(new CfDef("TestKeyspace", "SimpleTestBeanColumnFamily").setComparator_type(BytesType.class.getSimpleName())
            .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
        cfDefList.add(new CfDef("TestKeyspace", "SimpleRelationshipBeanColumnFamily").setComparator_type(BytesType.class.getSimpleName())
            .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
        cfDefList.add(new CfDef("TestKeyspace", "NoAnonymousColumnFamily").setComparator_type(BytesType.class.getSimpleName())
            .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
        cfDefList.add(new CfDef("TestKeyspace", "ComplexColumnFamily").setComparator_type(BytesType.class.getSimpleName())
            .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
        cfDefList.add(new CfDef("TestKeyspace", "CompositeColumnFamily").setComparator_type(BytesType.class.getSimpleName())
                .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
        cfDefList.add(new CfDef("TestKeyspace", "Furniture").setComparator_type(BytesType.class.getSimpleName())
            .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
        cfDefList.add(new CfDef("TestKeyspace", "MyConvertedCollectionFamily").setComparator_type(BytesType.class.getSimpleName())
            .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
        cfDefList.add(new CfDef("TestKeyspace", "CustomIdColumnFamily").setComparator_type(BytesType.class.getSimpleName())
            .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
        cluster = HFactory.getOrCreateCluster("TestPool", "localhost:9170");
        createKeyspace(cluster, "TestKeyspace", "org.apache.cassandra.locator.SimpleStrategy", 1, cfDefList);
        keyspace = HFactory.createKeyspace("TestKeyspace", cluster);
      }
}
