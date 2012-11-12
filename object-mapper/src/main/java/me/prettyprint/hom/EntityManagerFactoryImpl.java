package me.prettyprint.hom;

import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hom.annotations.AnnotationScanner;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.spi.PersistenceUnitInfo;
import java.util.Map;

public class EntityManagerFactoryImpl implements EntityManagerFactory {
  static final String PACKAGES_TO_SCAN = "me.prettyprint.hom.classpathPrefix";

  private EntityManager em;

  public EntityManagerFactoryImpl(final PersistenceUnitInfo info, final Keyspace space, final AnnotationScanner scanner) {
    if (!info.excludeUnlistedClasses()) {
      final Class<?>[] classes = new Class<?>[info.getManagedClassNames().size()];
      final ClassLoader cl = Thread.currentThread().getContextClassLoader();
      int i = 0;
      for (String name : info.getManagedClassNames()) {
        try {
          classes[i++] = cl.loadClass(name);
        } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
        }
      }
      em = new EntityManagerImpl(space, this, classes);
    } else {
      em = new EntityManagerImpl(space, this, info.getProperties().getProperty(PACKAGES_TO_SCAN).split(","), scanner);
    }
  }

  @Override
  public EntityManager createEntityManager() {
    return em;
  }

  @Override
  public EntityManager createEntityManager(Map map) {
    return em;
  }

  @Override
  public CriteriaBuilder getCriteriaBuilder() {
    return null;
  }

  @Override
  public Metamodel getMetamodel() {
    return null;
  }

  @Override
  public boolean isOpen() {
    return true;
  }

  @Override
  public void close() {
    // no-op
  }

  @Override
  public Map<String, Object> getProperties() {
    return null;
  }

  @Override
  public Cache getCache() {
    return null;
  }

  @Override
  public PersistenceUnitUtil getPersistenceUnitUtil() {
    return null;
  }
}
