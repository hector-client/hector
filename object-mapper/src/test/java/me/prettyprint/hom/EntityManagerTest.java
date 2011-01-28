package me.prettyprint.hom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hom.EntityManagerImpl;
import me.prettyprint.hom.beans.MyCustomIdBean;
import me.prettyprint.hom.beans.MyTestBean;
import me.prettyprint.hom.beans.MyTestBeanNoAnonymous;

import org.junit.Ignore;
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
  @Ignore("CustomIdBean class requires the \"old\" @Id annotation so the Colors converter can be used")
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

}
