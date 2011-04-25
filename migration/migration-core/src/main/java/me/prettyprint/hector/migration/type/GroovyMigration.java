package me.prettyprint.hector.migration.type;

import me.prettyprint.hector.migration.MigrationException;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import me.prettyprint.hector.api.Cluster;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class GroovyMigration extends AbstractMigration
{
    private final Resource script;

    public GroovyMigration(String version, Resource script)
    {
        super(version, script.getFilename());
        this.script = script;
    }

    public void migrate(Cluster cluster)
    {
        Binding binding = new Binding();
        binding.setVariable("cluster", cluster);
        GroovyShell shell = new GroovyShell(binding);

        InputStream inputStream = null;
        try
        {
            inputStream = script.getInputStream();
            shell.evaluate(IOUtils.toString(inputStream));
        }
        catch (IOException e)
        {
            throw new MigrationException(e);
        }
        finally
        {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
