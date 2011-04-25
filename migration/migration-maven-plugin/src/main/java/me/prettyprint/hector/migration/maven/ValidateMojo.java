package me.prettyprint.hector.migration.maven;

import me.prettyprint.hector.migration.MigrationManager;
import me.prettyprint.hector.migration.type.Migration;
import org.apache.maven.plugin.MojoExecutionException;

import java.util.SortedSet;

/**
 * Validate current schema against available migrations.
 * <p/>
 *
 * @goal validate
 */
public class ValidateMojo extends AbstractMigrationMojo
{
    public void executeMojo() throws MojoExecutionException
    {
        getLog().info("Validating " + getHosts() + " using migrations at " + getMigrationsPath() + ".");

        try
        {
            MigrationManager manager = createMigrationManager();
            SortedSet<Migration> pendingMigrations = manager.pendingMigrations();
            StringBuilder sb = new StringBuilder();
            sb.append("\n            Database: ").append(getHosts());
            sb.append("\n          Up-to-date: ").append(pendingMigrations.isEmpty());
            sb.append("\n  Pending Migrations: ");

            if (!pendingMigrations.isEmpty())
            {
                boolean first = true;
                for (Migration migration : pendingMigrations)
                {
                    if (!first)
                    {
                        sb.append("\n                      ");
                    }
                    first = false;
                    sb.append(migration.getFilename());
                }
            }

            getLog().info(sb.toString());
        }
        catch (Exception e)
        {
            throw new MojoExecutionException("Failed to validate " + getHosts(), e);
        }
    }
}
