package me.prettyprint.hector.migration.version;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.RangeSlicesQuery;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * A trivial VersionStrategy which tracks only the minimal information required to note the current state of the database: the current version.
 */
public class SimpleVersionStrategy implements VersionStrategy {

    public static final String DEFAULT_HECTOR_KS = "hector_support";
    public static final String DEFAULT_MIGRATIONS_CF = "hector_migrations_cf";

    private String hectorKS;
    private String migrationsCF;

    public SimpleVersionStrategy() {
        this.hectorKS = DEFAULT_HECTOR_KS;
        this.migrationsCF = DEFAULT_MIGRATIONS_CF;
    }

    public SimpleVersionStrategy(String hectorKS, String migrationsCF) {
        this.hectorKS = hectorKS;
        this.migrationsCF = migrationsCF;
    }

    public boolean isVersioningEnabled(Cluster cluster) {
        try {
            KeyspaceDefinition ksDef = cluster.describeKeyspace(hectorKS);
            for (ColumnFamilyDefinition cfDef : ksDef.getCfDefs())
                if (migrationsCF.equals(cfDef.getName())) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public void enableVersioning(Cluster cluster) {
        if (!isVersioningEnabled(cluster)) {
            if (cluster.describeKeyspace(hectorKS) == null) {
                KeyspaceDefinition ksDef = HFactory.createKeyspaceDefinition(hectorKS);
                cluster.addKeyspace(ksDef,true);
            }
            ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition(hectorKS, migrationsCF);
            cluster.addColumnFamily(cfDef);
        }
    }

    public void disableVersioning(Cluster cluster) {
        if (isVersioningEnabled(cluster)) {
            cluster.dropKeyspace(hectorKS);
        }
    }

    public Set<String> appliedMigrations(Cluster cluster) {
        if (!isVersioningEnabled(cluster)) return null;

        HashSet<String> set = new HashSet<String>();

        Keyspace ks = HFactory.createKeyspace(hectorKS, cluster);
        StringSerializer se = new StringSerializer();
        RangeSlicesQuery<String, String, String> q = HFactory.createRangeSlicesQuery(ks, se, se, se);
        q.setKeys(null, null)
                .setColumnFamily(migrationsCF)
                .setRange(null, null, false, Integer.MAX_VALUE)
                .setReturnKeysOnly();
        for (Row<String, String, String> row : q.execute().get()) {
            set.add(row.getKey());
        }
        return set;
    }

    public void recordMigration(Cluster cluster, String version, Date startTime, long duration) {
        Keyspace ks = HFactory.createKeyspace(hectorKS, cluster);
        Mutator<String> m = HFactory.createMutator(ks, StringSerializer.get());
        m.addInsertion(version, migrationsCF, HFactory.createStringColumn("duration", String.valueOf(duration)));
        m.addInsertion(version, migrationsCF, HFactory.createStringColumn("startTime", String.valueOf(startTime.getTime())));
        m.execute();
    }

    public void setHectorKS(String hectorKS) {
        this.hectorKS = hectorKS;
    }


    public void setMigrationsCF(String migrationsCF) {
        this.migrationsCF = migrationsCF;
    }
}
