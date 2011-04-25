package me.prettyprint.hector.migration.type;

import me.prettyprint.hector.migration.MigrationException;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.Cluster;
import org.apache.cassandra.cli.CliMain;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * @author trnl
 *         Date: 4/21/11
 *         Time: 5:35 PM
 */
public class CliMigration extends AbstractMigration {
    public CliMigration(String version, Resource script) {
        super(version, script.getFilename());
    }

    public void migrate(Cluster cluster) {
        CassandraHost host = cluster.getConnectionManager().getHosts().iterator().next();
        String[] args = new String[6];
        args[0] = ("-host");
        args[1] = (host.getHost());
        args[2] = ("-port");
        args[3] = (String.valueOf(host.getPort()));
        args[4] = ("-file");
        args[5] = (filename);
        try {
            CliMain.main(args);
        } catch (IOException e) {
            throw new MigrationException(e);
        }
    }
}
