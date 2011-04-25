package me.prettyprint.hector.migration;

import me.prettyprint.hector.migration.type.Migration;

import java.util.SortedSet;

public interface MigrationManager
{
    /**
     * Validates whether the database is currently up-to-date.
     *
     * @return true if the database is up-to-date, false if it is not or is unversioned
     */
    boolean validate();

    /**
     * Returns a sorted set of pending migrations, in the order that they would be run if a migration was performed.
     *
     * @return a sorted set of pending migrations, or an empty set if there are none pending
     */
    SortedSet<Migration> pendingMigrations();

    /**
     * Migrates the database to the latest version, enabling migrations if necessary.
     */
    void migrate();
}
