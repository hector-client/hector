package me.prettyprint.hector.migration.type;

import me.prettyprint.hector.migration.MigrationException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;

public class MigrationFactory {
    public static Migration create(String version, Resource resource) {
        final String extension = FilenameUtils.getExtension(resource.getFilename()).toLowerCase();

        if ("groovy".equals(extension)) {
            return new GroovyMigration(version, resource);
        }
        if ("cli".equals(extension)) {
            return new CliMigration(version, resource);
        }

        throw new MigrationException("Can't determine migration type for " + resource);
    }

}
