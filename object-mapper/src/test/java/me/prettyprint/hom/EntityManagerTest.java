package me.prettyprint.hom;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.UUID;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hom.EntityManagerImpl;
import me.prettyprint.hom.beans.MyCustomIdBean;
import me.prettyprint.hom.beans.MyTestBean;

import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.thrift.CfDef;
import org.apache.thrift.transport.TTransportException;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;


public class EntityManagerTest extends CassandraTestBase {
  private static Keyspace keyspace;

  @Test
  public void testInitializeSaveLoad() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyTestBean o1 = new MyTestBean();
    o1.setBaseId(UUID.randomUUID());
    o1.setIntProp1(1);
    o1.setBoolProp1(Boolean.TRUE);
    o1.setLongProp1(123L);
    em.save(o1);
    MyTestBean o2 = em.load(MyTestBean.class, o1.getBaseId());

    assertEquals(o1.getBaseId(), o2.getBaseId());
    assertEquals(o1.getIntProp1(), o2.getIntProp1());
    assertEquals(o1.isBoolProp1(), o2.isBoolProp1());
    assertEquals(o1.getLongProp1(), o2.getLongProp1());
  }

  @Test
  @Ignore
  public void testInitializeSaveLoadCustomId() {
    // TODO fixme - custom ID support
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyCustomIdBean o1 = new MyCustomIdBean();
    o1.setId(Colors.GREEN);
    o1.setLongProp1(111L);
    em.save(o1);
    MyCustomIdBean o2 = em.load(MyCustomIdBean.class, Colors.GREEN);

    assertEquals(o1.getId(), o2.getId());
    assertEquals(o1.getLongProp1(), o2.getLongProp1());
  }

  // ----------------

  @BeforeClass
  public static void setupKeyspace() throws TTransportException, SecurityException, IllegalArgumentException,
  IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    startCassandraInstance("target/cassandra-data");

    ArrayList<CfDef> cfDefList = new ArrayList<CfDef>(2);
    cfDefList.add(new CfDef("TestKeyspace", "TestBeanColumnFamily").setComparator_type(BytesType.class.getSimpleName())
        .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));
    cfDefList.add(new CfDef("TestKeyspace", "CustomIdColumnFamily").setComparator_type(BytesType.class.getSimpleName())
        .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));

    Cluster cluster = HFactory.getOrCreateCluster("TestPool", "localhost:9161");
    createKeyspace(cluster, "TestKeyspace", "org.apache.cassandra.locator.SimpleStrategy", 1, cfDefList);
    keyspace = HFactory.createKeyspace("TestKeyspace", cluster);
  }

}
