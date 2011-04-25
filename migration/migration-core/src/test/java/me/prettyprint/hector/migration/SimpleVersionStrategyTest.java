package me.prettyprint.hector.migration;

import me.prettyprint.hector.migration.version.SimpleVersionStrategy;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.*;

public class SimpleVersionStrategyTest extends CassandraTestBase {
    private static final String KS_NAME = "test_support";
    private static final String CF_NAME = "test_versions";

    private SimpleVersionStrategy strategy;

    @Before
    public void setup() {
        strategy = new SimpleVersionStrategy();
        strategy.setVersionsKS(KS_NAME);
        strategy.setVersionsCF(CF_NAME);
    }

    @After
    public void tear() {
        strategy.disableVersioning(cluster);
    }

    @Test
    public void testEnableVersioning() throws SQLException {
        strategy.enableVersioning(cluster);
        KeyspaceDefinition ksDef = cluster.describeKeyspace(KS_NAME);
        assertNotNull(ksDef);
    }

    @Test
    public void testDetermineVersionInUnversionedDatabase() throws SQLException {
        Set<String> migrations = strategy.appliedMigrations(cluster);
        assertNull(migrations);
    }

    @Test
    public void testDetermineVersionInNewlyEnabledDatabase() throws SQLException {
        strategy.enableVersioning(cluster);
        Set<String> migrations = strategy.appliedMigrations(cluster);

        assertNotNull(migrations);
        assertTrue(migrations.isEmpty());
    }

    @Test
    public void testRecordMigration() throws SQLException {
        final String v1 = "20080718214030";
        final String v2 = "20080718214530";

        strategy.enableVersioning(cluster);
        strategy.recordMigration(cluster, v1, new Date(), 768);

        Set<String> set = strategy.appliedMigrations(cluster);
        assertEquals(1, set.size());
        assertTrue(set.contains(v1));

        strategy.recordMigration(cluster, v2, new Date(), 231);

        set = strategy.appliedMigrations(cluster);
        assertEquals(2, set.size());
        assertTrue(set.contains(v1));
        assertTrue(set.contains(v2));
    }
}
