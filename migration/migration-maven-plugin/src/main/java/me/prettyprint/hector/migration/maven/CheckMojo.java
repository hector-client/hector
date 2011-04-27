package me.prettyprint.hector.migration.maven;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import me.prettyprint.hector.migration.MigrationManager;
import me.prettyprint.hector.migration.type.Migration;
import org.apache.maven.plugin.MojoExecutionException;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Set;

import static java.lang.String.format;

/**
 * Check current schema against available migrations to see if database is up to date,
 * causing the build to fail if the database is not up to date.
 * <p/>
 *
 * @goal check
 */
public class CheckMojo extends AbstractMigrationMojo {
    public void executeMojo() throws MojoExecutionException {     
        
        getLog().info("Checking " + getHosts() + " using migrations at " + getMigrationsPath() + "");

        Set<Migration> pendingMigrations;
        try {
            MigrationManager manager = createMigrationManager();
            pendingMigrations = manager.pendingMigrations();
        }
        catch (Exception e) {
            throw new MojoExecutionException("Failed to check " + getHosts(), e);
        }

        if (pendingMigrations.isEmpty()) {
            return;
        }

        Collection<String> pendingMigrationsNames = Collections2.transform(pendingMigrations, new Function<Migration, String>() {
            public String apply(Migration migration) {
                return migration.getFilename();
            }
        });

        String msg = format("There %s %d pending migrations: \n\n    %s\n\n    Run cassandra-migration:migrate to apply pending migrations.",
                pendingMigrations.size() == 1 ? "is" : "are",
                pendingMigrations.size(),
                Joiner.on("\n    ").join(pendingMigrationsNames));
        getLog().warn(msg);

        throw new MojoExecutionException(format("There %s %d pending migrations, migrate your cassandra instance and try again.", pendingMigrations.size() == 1 ? "is" : "are", pendingMigrations.size()));
    }
}