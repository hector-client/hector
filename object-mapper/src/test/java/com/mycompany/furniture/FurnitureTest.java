package com.mycompany.furniture;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import me.prettyprint.hom.CassandraTestBase;
import me.prettyprint.hom.EntityManagerImpl;

import org.apache.thrift.transport.TTransportException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class FurnitureTest extends CassandraTestBase {
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

    BasicTable table = new BasicTable();
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
    desk.setDeskType("roll-top");
    desk.addDrawer(new Drawer(true, true, "pencil")).addDrawer(new Drawer(false, true, "filing")).addDrawer(new Drawer(false, false, "upperLeft")).addDrawer(new Drawer(false, true, "lowerLeft"));
    desk.getOneColumnDrawerList().addAll(desk.getDrawerList());
    entityMgr.persist(desk);

    Furniture f1 = entityMgr.find(Furniture.class, 1);
    Furniture f2 = entityMgr.find(Furniture.class, 2);
    Furniture f3 = entityMgr.find(Furniture.class, 3);
    Furniture f4 = entityMgr.find(Furniture.class, 4);

    assertEquals( Chair.class, f1.getClass() );
    assertEquals( Couch.class, f2.getClass() );
    assertEquals( BasicTable.class, f3.getClass() );
    assertEquals( Desk.class, f4.getClass() );
    
    assertEquals( chair.isArms(), ((Chair)f1).isArms() );
    
    assertEquals( desk.getDrawerList().size(), ((Desk)f4).getDrawerList().size() );
    for ( int i=0;i < desk.getDrawerList().size();i++ ) {
      assertEquals( desk.getDrawerList().get(i), ((Desk)f4).getDrawerList().get(i));
    }
    assertEquals( desk.getOneColumnDrawerList().size(), ((Desk)f4).getOneColumnDrawerList().size() );
    for ( int i=0;i < desk.getOneColumnDrawerList().size();i++ ) {
      assertEquals( desk.getOneColumnDrawerList().get(i), ((Desk)f4).getOneColumnDrawerList().get(i));
    }
  }

  // --------------

  @Before
  public void setup() throws TTransportException, SecurityException, IllegalArgumentException,
  IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    entityMgr = new EntityManagerImpl(keyspace, "com.mycompany.furniture");
  }

}
