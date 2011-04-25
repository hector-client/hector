package me.prettyprint.hector.migration.version;

import me.prettyprint.hector.migration.MigrationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Assumes the filename, minus the extension and non-numeric text, is the migration version.
 * <p/>
 * Examples: <ul><li>20080518134512_create_foo.groovy -> 20080518134512</li><li>20080718214051_add_foo_name.groovy -> 20080718214051</li><li>012_create_bar.groovy ->
 * 012</li></ul>
 */
public class SimpleVersionExtractor implements VersionExtractor
{
    private static final Pattern FILENAME_PATTERN = Pattern.compile("^(\\d+).*");

    public String extractVersion(String name)
    {
        try
        {
            Matcher matcher = FILENAME_PATTERN.matcher(name);
            matcher.find();
            return matcher.group(1);
        }
        catch (Exception e)
        {
            throw new MigrationException("Error parsing migration version from " + name, e);
        }
    }
}

