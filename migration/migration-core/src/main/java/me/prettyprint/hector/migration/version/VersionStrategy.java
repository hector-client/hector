package me.prettyprint.hector.migration.version;


import me.prettyprint.hector.api.Cluster;

import java.util.Date;
import java.util.Set;

public interface VersionStrategy {
    boolean isVersioningEnabled(Cluster cluster);

    void enableVersioning(Cluster cluster);

    void disableVersioning(Cluster cluster);

    Set<String> appliedMigrations(Cluster cluster);

    void recordMigration(Cluster cluster, String version, Date startTime, long duration);
}
