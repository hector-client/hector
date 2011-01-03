package com.mycompany.furniture;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hom.CassandraTestBase;
import me.prettyprint.hom.EntityManagerImpl;

import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.thrift.CfDef;
import org.apache.thrift.transport.TTransportException;
import org.junit.BeforeClass;
import org.junit.Test;


public class FurnitureTest extends CassandraTestBase {
  static Keyspace keyspace;
  static EntityManagerImpl entityMgr;

  @Test
  public void testFurniture() {
    Chair chair = new Chair();
    chair.setId( 1 );
    chair.setMaterial("wood");
    chair.setColor("brown");
    chair.setArms(true);
    chair.setRecliner(false);
    entityMgr.persist(chair);

    Couch couch = new Couch();
    couch.setId( 2 );
    couch.setMaterial("wood");
    couch.setColor("brown");
    couch.setFoldOutBed(false);
    couch.setNumCushions(3);
    entityMgr.persist(couch);

    Table table = new Table();
    table.setId( 3 );
    table.setMaterial("formica");
    table.setColor("blue");
    table.setExtendable(true);
    table.setShape("circle");
    entityMgr.persist(table);

    Desk desk = new Desk();
    desk.setId( 4);
    desk.setMaterial("pressBoard");
    desk.setColor("black");
    desk.setExtendable(false);
    desk.setShape("rectangle");
    desk.setNumDrawers(2);
    entityMgr.persist(desk);

    Furniture f1 = entityMgr.find(Furniture.class, 1);
    Furniture f2 = entityMgr.find(Furniture.class, 2);
    Furniture f3 = entityMgr.find(Furniture.class, 3);
    Furniture f4 = entityMgr.find(Furniture.class, 4);

    assertEquals( Chair.class, f1.getClass() );
    assertEquals( Couch.class, f2.getClass() );
    assertEquals( Table.class, f3.getClass() );
    assertEquals( Desk.class, f4.getClass() );
  }

  // --------------

  @BeforeClass
  public static void setup() throws TTransportException, SecurityException, IllegalArgumentException,
  IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    startCassandraInstance("target/cassandra-data");
    ArrayList<CfDef> cfDefList = new ArrayList<CfDef>(2);
    cfDefList.add(new CfDef("TestKeyspace", "Furniture").setComparator_type(BytesType.class.getSimpleName())
        .setKey_cache_size(0).setRow_cache_size(0).setGc_grace_seconds(86400));

    Cluster cluster = HFactory.getOrCreateCluster("TestPool", "localhost:9170");
    createKeyspace(cluster, "TestKeyspace", "org.apache.cassandra.locator.SimpleStrategy", 1, cfDefList);
    keyspace = HFactory.createKeyspace("TestKeyspace", cluster);

    entityMgr = new EntityManagerImpl(keyspace, "com.mycompany.furniture");
  }

}
