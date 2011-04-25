package me.prettyprint.hector.migration.type;

import me.prettyprint.hector.api.Cluster;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

public interface Migration extends Comparable<Migration>
{
    String getVersion();

    String getFilename();

    void migrate(Cluster cluster);

    class MigrationVersionPredicate implements Predicate
    {
        private final String version;

        public MigrationVersionPredicate(String version)
        {
            this.version = version;
        }

        public boolean evaluate(Object object)
        {
            return StringUtils.equalsIgnoreCase(((Migration) object).getVersion(), version);
        }
    }
}
