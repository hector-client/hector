package me.prettyprint.hom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hom.beans.AnonymousWithCustomType;
import me.prettyprint.hom.beans.MyBlueTestBean;
import me.prettyprint.hom.beans.MyComplexEntity;
import me.prettyprint.hom.beans.MyComposite2PK;
import me.prettyprint.hom.beans.MyCompositeEntity;
import me.prettyprint.hom.beans.MyCompositePK;
import me.prettyprint.hom.beans.MyConvertedCollectionBean;
import me.prettyprint.hom.beans.MyCustomIdBean;
import me.prettyprint.hom.beans.MyGreenTestBean;
import me.prettyprint.hom.beans.MyPurpleTestBean;
import me.prettyprint.hom.beans.MyRedTestBean;
import me.prettyprint.hom.beans.MyTestBean;
import me.prettyprint.hom.beans.MyTestBeanNoAnonymous;

import org.junit.Test;

import com.mycompany.furniture.Drawer;

public class EntityManagerTest extends CassandraTestBase {

  @Test
  public void testInitializeSaveLoad() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyTestBean o1 = new MyTestBean();
    o1.setBaseId(UUID.randomUUID());
    o1.setIntProp1(1);
    o1.setBoolProp1(Boolean.TRUE);
    o1.setLongProp1(123L);
    em.persist(o1);
    MyTestBean o2 = em.find(MyTestBean.class, o1.getBaseId());

    assertEquals(o1.getBaseId(), o2.getBaseId());
    assertEquals(o1.getIntProp1(), o2.getIntProp1());
    assertEquals(o1.isBoolProp1(), o2.isBoolProp1());
    assertEquals(o1.getLongProp1(), o2.getLongProp1());
  }

  @Test
  public void testInitializeSaveLoadCollection() {
    List<Object> objList = new ArrayList<Object>(3);
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");

    MyGreenTestBean green = new MyGreenTestBean();
    green.setBaseId(UUID.randomUUID());
    green.setIntProp1(1);
    objList.add(green);
    
    MyBlueTestBean blue = new MyBlueTestBean();
    blue.setBaseId(UUID.randomUUID());
    blue.setIntProp1(2);
    objList.add(blue);
    
    MyPurpleTestBean purple = new MyPurpleTestBean();
    purple.setId("purple");
    purple.setLongProp1(3);
    objList.add(purple);
    
    em.persist(objList);
    
    MyGreenTestBean green2 = em.find(MyGreenTestBean.class, green.getBaseId());
    MyBlueTestBean blue2 = em.find(MyBlueTestBean.class, blue.getBaseId());
    MyPurpleTestBean purple2 = em.find(MyPurpleTestBean.class, purple.getId());
    
    assertEquals( green, green2);
    assertEquals(blue, blue2);
    assertEquals( purple, purple2);
  }

  @Test
  public void testInitializeSaveLoadCustomId() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyCustomIdBean o1 = new MyCustomIdBean();
    o1.setId(Colors.GREEN);
    o1.setLongProp1(111L);
    em.persist(o1);
    MyCustomIdBean o2 = em.find(MyCustomIdBean.class, Colors.GREEN);

    assertEquals(o1.getId(), o2.getId());
    assertEquals(o1.getLongProp1(), o2.getLongProp1());
  }

  @Test
  public void testExtraColumnShouldNotBeRead() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");

    MyTestBeanNoAnonymous bean1 = new MyTestBeanNoAnonymous();
    bean1.setBaseId(UUID.randomUUID());
    bean1.setLongProp1(1L);

    em.persist(bean1);

    // now add column that is not a property of bean
    Mutator<UUID> m = HFactory.createMutator(keyspace, UUIDSerializer.get());
    HColumn<String, String> col = HFactory.createColumn("anonymousProperty", "blah",
        StringSerializer.get(), StringSerializer.get());
    m.insert(bean1.getBaseId(), "NoAnonymousColumnFamily", col);

    MyTestBeanNoAnonymous bean2 = em.find(MyTestBeanNoAnonymous.class, bean1.getBaseId());

    assertNotNull("Could not load bean from cassandra", bean2);
    assertEquals(bean1.getLongProp1(), bean2.getLongProp1());
  }

  @Test
  public void testPersistAndFindComplexType() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyCompositePK pkKey = new MyCompositePK(1, "str-prop");
    MyComplexEntity entity1 = new MyComplexEntity();
    entity1.setIntProp1(pkKey.getIntProp1());
    entity1.setStrProp1(pkKey.getStrProp1());
    entity1.setStrProp2("str-prop-two");
    entity1.setDrawer(new Drawer(true, false, "a very nice drawer"));

    em.persist(entity1);

    MyComplexEntity entity2 = em.find(MyComplexEntity.class, pkKey);

    assertEquals(entity1.getIntProp1(), entity2.getIntProp1());
    assertEquals(entity1.getStrProp1(), entity2.getStrProp1());
    assertEquals(entity1.getStrProp2(), entity2.getStrProp2());
    assertEquals(entity1.getDrawer(), entity2.getDrawer());
  }

  @Test
  public void testPersistAndFindCompositeType() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyComposite2PK pkKey = new MyComposite2PK("str-prop", 1);
    MyCompositeEntity entity1 = new MyCompositeEntity();
    entity1.setIntProp1(pkKey.getIntProp1());
    entity1.setStrProp1(pkKey.getStrProp1());
    entity1.setStrProp2("str-prop-two");
    entity1.setDrawer(new Drawer(true, false, "a very nice drawer"));

    em.persist(entity1);

    MyCompositeEntity entity2 = em.find(MyCompositeEntity.class, pkKey);

    assertEquals(entity1.getIntProp1(), entity2.getIntProp1());
    assertEquals(entity1.getStrProp1(), entity2.getStrProp1());
    assertEquals(entity1.getStrProp2(), entity2.getStrProp2());
    assertEquals(entity1.getDrawer(), entity2.getDrawer());
  }
  
  @Test
  public void testMissingColumnsForPojoProps() {
    // Mutator<Long> m = HFactory.createMutator(keyspace, LongSerializer.get());
    // m.insert(1, "SimpleTestBeanColumnFamily", HFactory.createColumn(name,
    // value, nameSerializer, valueSerializer))
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyCompositePK pkKey = new MyCompositePK(1, "str-prop");
    MyComplexEntity entity1 = new MyComplexEntity();
    entity1.setIntProp1(pkKey.getIntProp1());
    entity1.setStrProp1(pkKey.getStrProp1());
    entity1.setStrProp2("str-prop-two");

    em.persist(entity1);

    MyComplexEntity entity2 = em.find(MyComplexEntity.class, pkKey);

    assertEquals(entity1.getIntProp1(), entity2.getIntProp1());
    assertEquals(entity1.getStrProp1(), entity2.getStrProp1());
    assertEquals(entity1.getStrProp2(), entity2.getStrProp2());
    assertNull(entity2.getStrProp3());
  }

  @Test
  public void testPojoWithListCollection() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyBlueTestBean b1 = new MyBlueTestBean();
    b1.setBaseId(UUID.randomUUID());
    b1.addToList(100).addToList(200).addToList(300);
    em.persist(b1);

    MyBlueTestBean b2 = em.find(MyBlueTestBean.class, b1.getBaseId());

    assertEquals(b1.getMySet().size(), b2.getMySet().size());
    for (Integer myInt : b1.getMySet()) {
      assertTrue(b2.getMySet().remove(myInt));
    }
  }

  @Test
  public void testPojoWithListUpdateCollection() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyBlueTestBean b1 = new MyBlueTestBean();
    b1.setBaseId(UUID.randomUUID());
    b1.addToList(100).addToList(200).addToList(300);
    em.persist(b1);

    MyBlueTestBean b2 = new MyBlueTestBean();
    b2.setBaseId(b1.getBaseId());
    b2.addToList(400);
    em.persist(b2);

    MyBlueTestBean b3 = em.find(MyBlueTestBean.class, b1.getBaseId());
    assertEquals(b2.getMySet().size(), b3.getMySet().size());
    for (Integer myInt : b2.getMySet()) {
      assertTrue(b3.getMySet().remove(myInt));
    }
  }

  @Test
  public void testPojoWithSetCollection() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyBlueTestBean b1 = new MyBlueTestBean();
    b1.setBaseId(UUID.randomUUID());
    b1.addToList(100).addToList(200).addToList(300);
    em.persist(b1);

    MyBlueTestBean b2 = em.find(MyBlueTestBean.class, b1.getBaseId());

    assertEquals(b1.getMySet().size(), b2.getMySet().size());
    for (Integer myInt : b1.getMySet()) {
      assertTrue(b2.getMySet().remove(myInt));
    }
  }

  @Test
  public void testPojoWithCustomCollection() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyConvertedCollectionBean b1 = new MyConvertedCollectionBean();
    b1.setId("me");
    b1.addToList(100).addToList(200).addToList(300);
    em.persist(b1);

    MyConvertedCollectionBean b2 = em.find(MyConvertedCollectionBean.class, b1.getId());

    assertEquals(b1.getMyCollection().size(), b2.getMyCollection().size());
    for (Integer myInt : b1.getMyCollection()) {
      assertTrue(b2.getMyCollection().remove(myInt));
    }
  }

  @Test
  public void testAnonymousCustomValuesObjectSerializer() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");

    AnonymousWithCustomType b1 = new AnonymousWithCustomType();
    b1.setId(123);
    b1.addAnonymousProp("one", new Drawer(true, false, "one"));
    b1.addAnonymousProp("two", new Drawer(false, true, "two"));
    b1.addAnonymousProp("three", new Drawer(true, true, "three"));

    em.persist(b1);
    AnonymousWithCustomType b2 = em.find(AnonymousWithCustomType.class, b1.getId());

    assertEquals(b1.getId(), b2.getId());
    assertEquals(b1.getAnonymousProps().size(), b2.getAnonymousProps().size());
    for (Entry<String, Drawer> entry : b1.getAnonymousProps()) {
      assertTrue("anonymous prop is in b1, but not b2", b2.getAnonymousProps().contains(entry));
    }
  }

  @Test
  public void testAnonymousHandlerOnBaseClass() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");

    MyRedTestBean b1 = new MyRedTestBean();
    b1.setBaseId(UUID.randomUUID());
    b1.addAnonymousProp("one", "1");
    b1.addAnonymousProp("two", "2");
    b1.addAnonymousProp("three", "3");

    em.persist(b1);
    MyRedTestBean b2 = em.find(MyRedTestBean.class, b1.getBaseId());

    assertEquals(b1.getBaseId(), b2.getBaseId());
    assertEquals(b1.getAnonymousProps().size(), b2.getAnonymousProps().size());
    for (Entry<String, String> entry : b1.getAnonymousProps()) {
      assertTrue("anonymous prop is in b1, but not b2", b2.getAnonymousProps().contains(entry));
    }
  }

  // --------------------

}
