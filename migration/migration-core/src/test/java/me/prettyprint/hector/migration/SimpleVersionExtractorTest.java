package me.prettyprint.hector.migration;

import me.prettyprint.hector.migration.version.SimpleVersionExtractor;
import me.prettyprint.hector.migration.version.VersionExtractor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleVersionExtractorTest
{
    @Test(expected = MigrationException.class)
    public void testInvalidFilename()
    {
        new SimpleVersionExtractor().extractVersion("foo.groovy");
    }

    @Test
    public void testNumberParsing()
    {
        VersionExtractor extractor = new SimpleVersionExtractor();
        assertEquals(extractor.extractVersion("000.groovy"), "000");
        assertEquals(extractor.extractVersion("000_create_foo.groovy"), "000");
        assertEquals(extractor.extractVersion("20080718214030_tinman.groovy"), "20080718214030");
    }
}
