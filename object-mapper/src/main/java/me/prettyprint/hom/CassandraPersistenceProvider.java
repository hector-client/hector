package me.prettyprint.hom;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CassandraPersistenceProvider implements PersistenceProvider {
  private static Logger log = LoggerFactory.getLogger(CassandraPersistenceProvider.class);
  
  private Map<String, Object> defProperties;
  
  public CassandraPersistenceProvider() {    
  }
  
  public CassandraPersistenceProvider(Map<String, Object> map) {
    this.defProperties = map;
  }
  
  @Override
  public EntityManagerFactory createContainerEntityManagerFactory(
      PersistenceUnitInfo info, Map map) {
    if ( log.isDebugEnabled() ) {
      log.debug("creating EntityManagerFactory {} with properties {} ", "null", map);
    }
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public EntityManagerFactory createEntityManagerFactory(String emName, Map map) {
    if ( log.isDebugEnabled() ) {
      log.debug("creating EntityManagerFactory {} with properties {} ", emName, map);
    }
    if ( map == null || map.isEmpty()) {
      return new EntityManagerFactoryImpl(defProperties);
    }
    return new EntityManagerFactoryImpl(map);
  }

}
