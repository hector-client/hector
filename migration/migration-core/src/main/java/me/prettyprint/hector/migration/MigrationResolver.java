package me.prettyprint.hector.migration;


import me.prettyprint.hector.migration.type.Migration;

import java.util.Set;

/**
 * Defines a strategy for finding all of the migrations which could potentially be run against a database.
 */
public interface MigrationResolver {
    /**
     * Find all of the available migrations.
     *
     * @return a set of all available migrations, empty if no migrations are available
     */
    Set<Migration> resolve();
}
