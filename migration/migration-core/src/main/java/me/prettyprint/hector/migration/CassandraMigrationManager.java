package me.prettyprint.hector.migration;

import me.prettyprint.hector.migration.type.Migration;
import me.prettyprint.hector.migration.version.SimpleVersionStrategy;
import me.prettyprint.hector.migration.version.VersionStrategy;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author trnl
 *         Date: 4/21/11
 *         Time: 2:09 PM
 */
public class CassandraMigrationManager implements MigrationManager {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private MigrationResolver migrationResolver = new ResourceMigrationResolver();

    private VersionStrategy versionStrategy = new SimpleVersionStrategy();

    private Cluster cluster;


    public CassandraMigrationManager(Cluster cluster) {
        this.cluster = cluster;
    }

    public CassandraMigrationManager(String hosts) {
        this(HFactory.getOrCreateCluster("CassandraCluster", hosts));
    }

    public boolean validate() {
        return pendingMigrations().isEmpty();
    }

    public SortedSet<Migration> pendingMigrations() {
        Set<String> appliedMigrations = versionStrategy.appliedMigrations(cluster);
        Set<Migration> availableMigrations = migrationResolver.resolve();

        SortedSet<Migration> pendingMigrations = new TreeSet<Migration>();
        CollectionUtils.select(availableMigrations, new PendingMigrationPredicate(appliedMigrations), pendingMigrations);

        return pendingMigrations;
    }

    public Set<String> appliedMigrations() {
        return versionStrategy.appliedMigrations(cluster);
    }

    public void migrate() {

        versionStrategy.enableVersioning(cluster);
        final Set<Migration> pendingMigrations = pendingMigrations();

        if (pendingMigrations.isEmpty()) {
            logger.info("Database is up to date; no migration necessary.");
            return;
        }

        StopWatch watch = new StopWatch();
        watch.start();

        logger.info("Migrating database... applying " + pendingMigrations.size() + " migration" + (pendingMigrations.size() > 1 ? "s" : "") + ".");

        Migration currentMigration = null;
        try {
            for (Migration migration : pendingMigrations) {
                currentMigration = migration;
                logger.info("Running migration " + currentMigration.getFilename() + ".");

                final Date startTime = new Date();
                StopWatch migrationWatch = new StopWatch();
                migrationWatch.start();
                currentMigration.migrate(cluster);
                versionStrategy.recordMigration(cluster, currentMigration.getVersion(), startTime, migrationWatch.getTime());
            }
        } catch (Throwable e) {
            String message = "Migration for version " + currentMigration.getVersion() + " failed.";
            logger.error(message, e);
            throw new MigrationException(message, e);
        }


        watch.stop();

        logger.info("Migrated database in " + DurationFormatUtils.formatDurationHMS(watch.getTime()) + ".");
    }

    public void setMigrationResolver(MigrationResolver migrationResolver) {
        this.migrationResolver = migrationResolver;
    }

    public void setVersionStrategy(VersionStrategy versionStrategy) {
        this.versionStrategy = versionStrategy;
    }

    public MigrationResolver getMigrationResolver() {
        return migrationResolver;
    }

    public VersionStrategy getVersionStrategy() {
        return versionStrategy;
    }

    public Cluster getCluster() {
        return cluster;
    }

    private static class PendingMigrationPredicate implements Predicate {
        private final Set<String> appliedMigrations;

        public PendingMigrationPredicate(Set<String> appliedMigrations) {
            this.appliedMigrations = appliedMigrations == null ? new HashSet<String>() : appliedMigrations;
        }

        public boolean evaluate(Object input) {
            if (input instanceof Migration) {
                return !appliedMigrations.contains(((Migration) input).getVersion());
            } else {
                return !appliedMigrations.contains(input.toString());
            }
        }
    }
}
