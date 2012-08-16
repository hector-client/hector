package me.prettyprint.hom;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.persistence.spi.ProviderUtil;
import javax.sql.DataSource;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

import static me.prettyprint.hom.EntityManagerFactoryImpl.PACKAGES_TO_SCAN;

public class CassandraPersistenceProvider implements PersistenceProvider {
  private static final String KEYSPACE_PROP = "me.prettyprint.keyspace";
  private static final String CLUSTER_PROP = "me.prettyprint.cluster";
  private static final String HOST_PROP = "me.prettyprint.host";
  private static final String CONSISTENCY_PROP = "me.prettyprint.consistency";

  @Override
  public EntityManagerFactory createEntityManagerFactory(final String emName, final Map map) {
    final Keyspace keyspace = keyspace((String) map.get(HOST_PROP), (String) map.get(CLUSTER_PROP), (String) map.get(KEYSPACE_PROP), (String) map.get(CONSISTENCY_PROP));
    return new EntityManagerFactoryImpl(new LightPersistenceUnitInfo((String) map.get(PACKAGES_TO_SCAN)), keyspace);
  }

  @Override
  public EntityManagerFactory createContainerEntityManagerFactory(final PersistenceUnitInfo info, final Map map) {
    final String keyspaceName = value(KEYSPACE_PROP, map, info.getProperties());
    final String host = value(HOST_PROP, map, info.getProperties());
    final String clusterName = value(CLUSTER_PROP, map, info.getProperties());
    final String consistency = value(CONSISTENCY_PROP, map, info.getProperties());
    final Keyspace keyspace = keyspace(host, clusterName, keyspaceName, consistency);
    return new EntityManagerFactoryImpl(info, keyspace);
  }

  private String value(final String key, final Map map, final Properties properties) {
    if (map.containsKey(key)) {
      return (String) map.get(key);
    }
    return properties.getProperty(key);
  }

  private Keyspace keyspace(final String host, final String clusterName, final String keyspaceName, final String consistency) {
    if (clusterName == null) {
      throw new IllegalArgumentException("cluster name can't be null");
    }
    if (keyspaceName == null) {
      throw new IllegalArgumentException("keyspace name can't be null");
    }
    if (host == null) {
      throw new IllegalArgumentException("host can't be null");
    }

    final ConfigurableConsistencyLevel consistencyLevelPolicy = new ConfigurableConsistencyLevel();
    if (consistency == null) {
      consistencyLevelPolicy.setDefaultReadConsistencyLevel(HConsistencyLevel.QUORUM);
    } else {
      consistencyLevelPolicy.setDefaultReadConsistencyLevel(HConsistencyLevel.valueOf(consistency.toUpperCase()));
    }

    final ThriftCluster cluster = new ThriftCluster(clusterName, new CassandraHostConfigurator(host));
    return HFactory.createKeyspace(keyspaceName, cluster, consistencyLevelPolicy);
  }

  @Override
  public ProviderUtil getProviderUtil() {
    return null; // null == JPA 1.0, that's fine for a lot of cases
  }

  // just for the JSE mode, a mock impl implementing what we use
  private class LightPersistenceUnitInfo implements PersistenceUnitInfo {
    private String toScan;

    private LightPersistenceUnitInfo(String toScan) {
      this.toScan = toScan;
    }

    @Override
    public String getPersistenceUnitName() {
      return null;
    }

    @Override
    public String getPersistenceProviderClassName() {
      return CassandraPersistenceProvider.class.getName();
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
      return null;
    }

    @Override
    public DataSource getJtaDataSource() {
      return null;
    }

    @Override
    public DataSource getNonJtaDataSource() {
      return null;
    }

    @Override
    public List<String> getMappingFileNames() {
      return null;
    }

    @Override
    public List<URL> getJarFileUrls() {
      return null;
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
      return null;
    }

    @Override
    public List<String> getManagedClassNames() {
      return null;
    }

    @Override
    public boolean excludeUnlistedClasses() {
      return true;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
      return null;
    }

    @Override
    public ValidationMode getValidationMode() {
      return null;
    }

    @Override
    public Properties getProperties() {
      final Properties properties = new Properties();
      properties.setProperty(PACKAGES_TO_SCAN, toScan);
      return properties;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
      return null;
    }

    @Override
    public ClassLoader getClassLoader() {
      return null;
    }

    @Override
    public void addTransformer(ClassTransformer transformer) {

    }

    @Override
    public ClassLoader getNewTempClassLoader() {
      return null;
    }
  }
}