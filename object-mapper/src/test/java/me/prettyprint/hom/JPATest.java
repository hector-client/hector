package me.prettyprint.hom;

import java.util.HashMap;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import me.prettyprint.hom.beans.MyTestBean;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JPATest extends CassandraTestBase {
  private static EntityManagerFactory emf;

  @BeforeClass
  public static void createEmf() {
    emf = Persistence.createEntityManagerFactory("hom", new HashMap<String, String>() {{
      put("me.prettyprint.hom.classpathPrefix", "me.prettyprint.hom.beans");
      put("me.prettyprint.keyspace", "TestKeyspace");
      put("me.prettyprint.cluster", "TestPool");
      put("me.prettyprint.host", "localhost:9170");
      put("me.prettyprint.consistency", "ONE");
    }});
  }

  @Test
  public void testInitializeSaveLoad() {
    final EntityManager em = emf.createEntityManager();

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
}

