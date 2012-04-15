package me.prettyprint.hom;

import java.util.UUID;
import javax.ejb.Singleton;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import me.prettyprint.hom.beans.MyTestBean;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JPAEETest extends CassandraTestBase {
    private static EJBContainer container;

    @PersistenceContext(unitName = "homEE")
    private EntityManager em; // jta

    @Inject
    private AnEJB ejb;

    @BeforeClass
    public static void start() {
        container = EJBContainer.createEJBContainer();
    }

    @Before
    public void inject() throws NamingException {
        container.getContext().bind("inject", this);
    }

    @AfterClass
    public static void stop() {
        container.close();
    }

    @Test
    public void testInitializeSaveLoad() {
        MyTestBean o1 = new MyTestBean();
        o1.setBaseId(UUID.randomUUID());
        o1.setIntProp1(1);
        o1.setBoolProp1(Boolean.TRUE);
        o1.setLongProp1(123L);
        ejb.persist(o1);
        MyTestBean o2 = em.find(MyTestBean.class, o1.getBaseId());

        assertEquals(o1.getBaseId(), o2.getBaseId());
        assertEquals(o1.getIntProp1(), o2.getIntProp1());
        assertEquals(o1.isBoolProp1(), o2.isBoolProp1());
        assertEquals(o1.getLongProp1(), o2.getLongProp1());
    }

    @Singleton
    public static class AnEJB {
        @PersistenceContext(unitName = "homEE")
        private EntityManager em;

        // just for the tx context
        public void persist(Object o) {
            em.persist(o);
        }
    }
}
