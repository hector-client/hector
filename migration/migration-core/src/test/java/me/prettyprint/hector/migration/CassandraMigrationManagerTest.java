package me.prettyprint.hector.migration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CassandraMigrationManagerTest  extends CassandraTestBase {
    static final String SINGLE = "classpath:/test_migrations/valid_1/";
    static final String MULTIPLE = "classpath:/test_migrations/valid_2/";
    static final String DUPLICATE = "classpath:/test_migrations/duplicate_1/";

    private CassandraMigrationManager migrationManager;

    @Before
    public void setup() {
        migrationManager = new CassandraMigrationManager(cluster);
    }

    @After
    public void tear() {
        migrationManager.getVersionStrategy().disableVersioning(cluster);
    }

    @Test
    public void pendingMigrationsShouldReturnOneMigration() {
        migrationManager.setMigrationResolver(new ResourceMigrationResolver(SINGLE));
        assertEquals(1, migrationManager.pendingMigrations().size());
    }

    @Test
    public void migrateShouldApplyOneMigration() {
        migrationManager.setMigrationResolver(new ResourceMigrationResolver(SINGLE));

        migrationManager.migrate();
        assertTrue(migrationManager.validate());

        assertEquals(1, migrationManager.appliedMigrations().size());
    }

    @Test
    public void migrateShouldApplyMultipleMigrations() {
        migrationManager.setMigrationResolver(new ResourceMigrationResolver(MULTIPLE));

        migrationManager.migrate();
        assertTrue(migrationManager.validate());
        assertEquals(3, migrationManager.appliedMigrations().size());
    }

    @Test
    public void pendingMigrationsShouldReturnMultipleMigration() {
        migrationManager.setMigrationResolver(new ResourceMigrationResolver(MULTIPLE));
        assertEquals(3, migrationManager.pendingMigrations().size());
    }

    @Test(expected = MigrationException.class)
    public void migrateShouldCatchDuplicateMigrationVersions() {
        migrationManager.setMigrationResolver(new ResourceMigrationResolver(DUPLICATE));
        migrationManager.migrate();
    }
}
