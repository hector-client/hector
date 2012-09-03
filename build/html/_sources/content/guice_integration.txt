.. highlight:: java

.. index:: guice

Guice Integration
*****************

The folliwing is just a simple example on how you can inject Hector keyspaces through Google `Guice <http://http://code.google.com/p/google-guice/>`_ 

HectorModule example
======================

This example assume there is a app.properties file in the path ::

    public class HectorModule extends AbstractModule {

        private static final Logger log = LoggerFactory.getLogger(DAOModule.class);

        private static final String PROP_CASSANDRA_HOST_PORT = "CASSANDRA_HOST_PORT";

        private static final String DEFAULT_CASSANDRA_HOST_PORT = "127.0.0.1:9160";
        private static final String KEYSPACE = "RIQ";
        private static final String CLUSTER_NAME = "Test Cluster";

        private String propertyFileSuffix;
        private Properties properties;

        // private static Properties props;

        public HectorModule() {}

        @Override
        protected void configure() {

            // load properties
            properties = ConfigLoader.loadProperties(propertyFileSuffix);

            // bind so we can retrieve them elsewhere
            Names.bindProperties(binder(), properties);
        }

        @Singleton
        @Provides
        Properties provideProperties() {
            return properties;
        }


        @Singleton
        @Provides
        Keyspace provideKeyspace() {
            CassandraHostConfigurator chc = null;
            // Create the cluster
            if (properties.getProperty(PROP_CASSANDRA_HOST_PORT) != null || !properties.getProperty(PROP_CASSANDRA_HOST_PORT).equals("")) {
                chc = new CassandraHostConfigurator(properties.getProperty(PROP_CASSANDRA_HOST_PORT));
            } else {
                log.error("Unable to load cassandra host/port from properties; defaulting to " + DEFAULT_CASSANDRA_HOST_PORT);
                chc = new CassandraHostConfigurator(DEFAULT_CASSANDRA_HOST_PORT);
            }

            chc.setAutoDiscoverHosts(true);
            chc.setRetryDownedHosts(true);

            Cluster cluster = HFactory.getOrCreateCluster(CLUSTER_NAME, chc);

            // Create the long-life Keyspace object
            Keyspace keyspace = HFactory.createKeyspace(KEYSPACE, cluster);

            ConfigurableConsistencyLevel cp = new ConfigurableConsistencyLevel();
            cp.setDefaultReadConsistencyLevel(HConsistencyLevel.QUORUM);
            cp.setDefaultWriteConsistencyLevel(HConsistencyLevel.QUORUM);
            keyspace.setConsistencyLevelPolicy(cp);

            return keyspace;
        }

    }

And you would typically have a DAO like this ::

    public class MyDAO extends MyIFaceDAO {

        protected final Keyspace keyspace;

        @Inject
        public MyDAO(Keyspace keyspace) {
            this.keyspace = keyspace;
        }
    }
