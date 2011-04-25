package me.prettyprint.hector.migration.maven;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * Migrate to latest schema version.
 * <p/>
 *
 * @goal migrate
 */
public class MigrateMojo extends AbstractMigrationMojo {
    public void executeMojo() throws MojoExecutionException {
        getLog().info("Migrating " + getHosts() + " using migrations at " + getMigrationsPath() + ".");

        try {
            createMigrationManager().migrate();
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to migrate " + getHosts(), e);
        }
    }
}
