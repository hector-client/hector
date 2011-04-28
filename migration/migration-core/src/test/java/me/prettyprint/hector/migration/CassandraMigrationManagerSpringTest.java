package me.prettyprint.hector.migration;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Note: This is really more of an example of how to wire up using spring configuration than an actual test.  See the corresponding spring xml to see one way
 * migrations can be configured.
 */

public class CassandraMigrationManagerSpringTest extends CassandraTestBase {
    @Autowired
    CassandraMigrationManager migrationManager;

    @After
    public void tear() {
        migrationManager.getVersionStrategy().disableVersioning(cluster);
    }

    @Test
    public void testMigration() {
        migrationManager.migrate();
        Assert.assertTrue(migrationManager.validate());
    }
}
