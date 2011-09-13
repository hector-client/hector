package me.prettyprint.hom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hom.EntityManagerImpl;
import me.prettyprint.hom.beans.MyComplexEntity;
import me.prettyprint.hom.beans.MyCompositePK;
import me.prettyprint.hom.beans.MyCustomIdBean;
import me.prettyprint.hom.beans.MyTestBean;
import me.prettyprint.hom.beans.MyTestBeanNoAnonymous;

import org.junit.Test;

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
  public void testInitializeSaveLoadMultiple() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyTestBean o1 = new MyTestBean();
    UUID id1 = UUID.randomUUID();
    o1.setBaseId(id1);
    o1.setIntProp1(1);
    o1.setBoolProp1(Boolean.TRUE);
    o1.setLongProp1(123L);
    em.persist(o1);
    
    UUID id2 = UUID.randomUUID();
    MyTestBean o2 = new MyTestBean();
    o2.setBaseId(id2);
    o2.setIntProp1(2);
    o2.setBoolProp1(Boolean.FALSE);
    o2.setLongProp1(1231L);
    em.persist(o2);
    
    Set<UUID> ids = new HashSet<UUID>();
    ids.add(id1);
    ids.add(id2);
    em.load(MyTestBean.class, ids);
    
    Map<UUID, MyTestBean> results = em.load(MyTestBean.class, ids);
    MyTestBean o3 = results.get(id1);
    MyTestBean o4 = results.get(id2);
    assertNotNull(o3);
    assertNotNull(o4);

    assertEquals(o1.getBaseId(), o3.getBaseId());
    assertEquals(o1.getIntProp1(), o3.getIntProp1());
    assertEquals(o1.isBoolProp1(), o3.isBoolProp1());
    assertEquals(o1.getLongProp1(), o3.getLongProp1());
    
    assertEquals(o2.getBaseId(), o4.getBaseId());
    assertEquals(o2.getIntProp1(), o4.getIntProp1());
    assertEquals(o2.isBoolProp1(), o4.isBoolProp1());
    assertEquals(o2.getLongProp1(), o4.getLongProp1());
  }




  @Test
  public void testInitializeSaveLoadCustomId() {
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyCustomIdBean o1 = new MyCustomIdBean();
    o1.setId(Colors.GREEN);
    o1.setLongProp1(111L);
    em.save(o1);
    MyCustomIdBean o2 = em.load(MyCustomIdBean.class, Colors.GREEN);

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

    em.persist(entity1);
    
    MyComplexEntity entity2 = em.find(MyComplexEntity.class, pkKey);
    
    assertEquals( entity1.getIntProp1(), entity2.getIntProp1() );
    assertEquals( entity1.getStrProp1(), entity2.getStrProp1() );
    assertEquals( entity1.getStrProp2(), entity2.getStrProp2() );
  }
  
  @Test
  public void testPersistAndFindComplexTypeMultiple() {
	  EntityManagerImpl em = new EntityManagerImpl(keyspace,
	  "me.prettyprint.hom.beans");
	  MyCompositePK pkKey1 = new MyCompositePK(3, "str-prop-3");
	  MyComplexEntity entity1 = new MyComplexEntity();
	  entity1.setIntProp1(pkKey1.getIntProp1());
	  entity1.setStrProp1(pkKey1.getStrProp1());
	  entity1.setStrProp2("str-prop-three");

	  em.persist(entity1);

	  MyCompositePK pkKey2 = new MyCompositePK(4, "str-prop-4");
	  MyComplexEntity entity2 = new MyComplexEntity();
	  entity2.setIntProp1(pkKey2.getIntProp1());
	  entity2.setStrProp1(pkKey2.getStrProp1());
	  entity2.setStrProp2("str-prop-four");

	  em.persist(entity2);

	  Set<MyCompositePK> ids = new HashSet<MyCompositePK>();
	  ids.add(pkKey1);
	  ids.add(pkKey2);
	  Map<MyCompositePK, MyComplexEntity> results = em.load(MyComplexEntity.class, ids);

	  MyComplexEntity entity3 = results.get(pkKey1);
	  MyComplexEntity entity4 = results.get(pkKey2);

	  assertNotNull(entity3);
	  assertNotNull(entity4);

	  assertEquals(entity1.getIntProp1(), entity3.getIntProp1());
	  assertEquals(entity1.getStrProp1(), entity3.getStrProp1());
	  assertEquals(entity1.getStrProp2(), entity3.getStrProp2());

	  assertEquals(entity2.getIntProp1(), entity4.getIntProp1());
	  assertEquals(entity2.getStrProp1(), entity4.getStrProp1());
	  assertEquals(entity2.getStrProp2(), entity4.getStrProp2());
  }


  @Test
  public void testMissingColumnsForPojoProps() {
//  Mutator<Long> m = HFactory.createMutator(keyspace, LongSerializer.get());
//  m.insert(1, "SimpleTestBeanColumnFamily", HFactory.createColumn(name, value, nameSerializer, valueSerializer))
    EntityManagerImpl em = new EntityManagerImpl(keyspace, "me.prettyprint.hom.beans");
    MyCompositePK pkKey = new MyCompositePK(1, "str-prop");
    MyComplexEntity entity1 = new MyComplexEntity();
    entity1.setIntProp1(pkKey.getIntProp1());
    entity1.setStrProp1(pkKey.getStrProp1());
    entity1.setStrProp2("str-prop-two");

    em.persist(entity1);
    
    MyComplexEntity entity2 = em.find(MyComplexEntity.class, pkKey);
    
    assertEquals( entity1.getIntProp1(), entity2.getIntProp1() );
    assertEquals( entity1.getStrProp1(), entity2.getStrProp1() );
    assertEquals( entity1.getStrProp2(), entity2.getStrProp2() );
    assertNull( entity2.getStrProp3() );
  }
  
}
