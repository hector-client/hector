package me.prettyprint.hector.migration.maven;

import me.prettyprint.hector.migration.CassandraMigrationManager;
import me.prettyprint.hector.migration.MigrationManager;
import me.prettyprint.hector.migration.ResourceMigrationResolver;
import me.prettyprint.hector.migration.version.SimpleVersionStrategy;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import static org.apache.commons.io.FilenameUtils.separatorsToUnix;
import static org.apache.commons.lang.StringUtils.*;

public abstract class AbstractMigrationMojo extends AbstractMojo {

    /**
     * @parameter expression="${project}"
     * @required
     */
    protected MavenProject project;

    /**
     * @parameter
     */
    private String hosts;
    /**
     * @parameter
     */
    private String migrationsPath = "src/main/db/migrations/";
    /**
     * @parameter
     */
    private String hectorKS = SimpleVersionStrategy.DEFAULT_HECTOR_KS;

    /**
     * @parameter
     */
    private String migrationsCF = SimpleVersionStrategy.DEFAULT_MIGRATIONS_CF;

    public abstract void executeMojo() throws MojoExecutionException;

    public final void execute() throws MojoExecutionException {
        validateConfiguration();
        executeMojo();
    }

    protected void validateConfiguration() throws MojoExecutionException {
        if (isBlank(hosts)) {
            throw new MojoExecutionException("No cassandra hosts. Specify one in the plugin configuration.");
        }

        if (isBlank(migrationsPath)) {
            throw new MojoExecutionException("No migrations path. Specify one in the plugin configuration.");
        }

        if (isBlank(hectorKS)) {
            throw new MojoExecutionException("Keyspace not specified in the plugin configuration.");
        }

        if (isBlank(migrationsCF)) {
            throw new MojoExecutionException("ColumnFamily not specified in the plugin configuration.");
        }
    }

    public MigrationManager createMigrationManager() {
        CassandraMigrationManager manager = new CassandraMigrationManager(hosts);

        manager.setMigrationResolver(new ResourceMigrationResolver(convertPath(getMigrationsPath())));

        SimpleVersionStrategy strategy = new SimpleVersionStrategy(hectorKS, migrationsCF);
        manager.setVersionStrategy(strategy);

        return manager;
    }

    public String getMigrationsPath() {
        return migrationsPath;
    }

    public String getHosts() {
        return hosts;
    }

    public String convertPath(String path) {
        if (path.startsWith("file:")) {
            path = substring(path, 5);
        }
        if (!path.startsWith("classpath:") && !path.startsWith("\"") && !path.startsWith("/")) {
            path = project.getBasedir().getAbsolutePath() + "/" + path;
        }
        path = separatorsToUnix(path);
        return path;
    }
}
