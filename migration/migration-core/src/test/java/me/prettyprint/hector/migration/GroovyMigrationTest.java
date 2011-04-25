package me.prettyprint.hector.migration;

import me.prettyprint.hector.migration.type.GroovyMigration;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class GroovyMigrationTest extends CassandraTestBase {

    @Test
    public void testMigrate() throws Exception {
        Resource script = new ClassPathResource("/test_migrations/groovy_1/001_add_test_keyspace.groovy");
        new GroovyMigration("1", script).migrate(cluster);
    }
}
