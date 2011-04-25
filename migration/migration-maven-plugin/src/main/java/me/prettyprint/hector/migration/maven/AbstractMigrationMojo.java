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
    private String clusterName;

    /**
     * @parameter
     */
    private String migrationsPath = "src/main/db/migrations/";

    private String createScript = "src/main/db/migrations/create.groovy";

    private String dropScript = "src/main/db/migrations/drop.groovy";
    /**
     * @parameter
     */
    private String versionsKS = "support";

    /**
     * @parameter
     */
    private String versionsCF = "versions";

    public abstract void executeMojo() throws MojoExecutionException;

    public final void execute() throws MojoExecutionException {
        if (isBlank(hosts) || isBlank(clusterName)) {
            return;
        }

        validateConfiguration();

        executeMojo();
    }

    protected void validateConfiguration() throws MojoExecutionException {
        if (isBlank(hosts)) {
            throw new MojoExecutionException("No cassandra hosts. Specify one in the plugin configuration.");
        }
        if (isBlank(clusterName)) {
            throw new MojoExecutionException("No cluster name. Specify one in the plugin configuration.");
        }
    }

    public MigrationManager createMigrationManager() {
        CassandraMigrationManager manager = new CassandraMigrationManager(hosts,clusterName);

        manager.setMigrationResolver(new ResourceMigrationResolver(convertPath(getMigrationsPath())));

        SimpleVersionStrategy strategy = new SimpleVersionStrategy();
        strategy.setVersionsKS(defaultIfEmpty(versionsKS, SimpleVersionStrategy.DEFAULT_VERSIONS_KS));
        strategy.setVersionsCF(defaultIfEmpty(versionsCF, SimpleVersionStrategy.DEFAULT_VERSIONS_CF));
        manager.setVersionStrategy(strategy);

        return manager;
    }

    public String getMigrationsPath() {
        return migrationsPath;
    }

    public String getHosts() {
        return hosts;
    }

    public String getClusterName() {
        return clusterName;
    }

    public String getCreateScript() {
        return createScript;
    }

    public String getDropScript() {
        return dropScript;
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
