package me.prettyprint.hector.migration.type;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMigration implements Migration
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final String version;
    protected final String filename;

    protected AbstractMigration(String version, String filename)
    {
        Validate.notEmpty(version);
        Validate.notEmpty(filename);
        this.version = version;
        this.filename = filename;
    }

    public String getVersion()
    {
        return version;
    }

    public String getFilename()
    {
        return filename;
    }

    public int compareTo(Migration o)
    {
        return getVersion().compareTo(o.getVersion());
    }
}
