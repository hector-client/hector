package me.prettyprint.hector.migration;

import me.prettyprint.hector.migration.type.Migration;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResourceMigrationResolverTest {
    static final String SINGLE = "classpath:/test_migrations/valid_1/";
    static final String MULTIPLE = "classpath:/test_migrations/valid_2/";
    static final String DUPLICATE = "classpath:/test_migrations/duplicate_1/";

    @Test
    public void testResolveMigrationsSet1() {
        ResourceMigrationResolver resolver = new ResourceMigrationResolver(SINGLE);
        Set<Migration> migrations = resolver.resolve();
        Assert.assertNotNull(migrations);
        Assert.assertEquals(migrations.size(), 1);
    }

    @Test
    public void testResolveMigrationsSet2() {
        ResourceMigrationResolver resolver = new ResourceMigrationResolver(MULTIPLE);
        Set<Migration> migrations = resolver.resolve();
        Assert.assertNotNull(migrations);
        Assert.assertEquals(migrations.size(), 3);
    }

    @Test(expected = MigrationException.class)
    public void testShouldThrowMigrationExceptionOnNonUniqueVersion() {
        ResourceMigrationResolver resolver = new ResourceMigrationResolver(DUPLICATE);
        resolver.resolve();
    }

    @Test
    public void testSpringifyingMigrationsLocation() {
        // Append Trailing slash and wildcard.
        Assert.assertEquals(convert("src/migrations"), "file:src/migrations/*");
        Assert.assertEquals(convert("src/migrations/"), "file:src/migrations/*");

        // Explicit inclusion of spring prefixes.
        Assert.assertEquals(convert("file:src/migrations"), "file:src/migrations/*");
        Assert.assertEquals(convert("classpath:com/acme"), "classpath:com/acme/*");
        Assert.assertEquals(convert("classpath:com/acme/"), "classpath:com/acme/*");
        Assert.assertEquals(convert("classpath:/com/acme"), "classpath:/com/acme/*");
        //assertThat(convert("classpath*:/com/acme"), is("classpath*:/com/acme/*"));

        // Wildcards and filename filters.
        Assert.assertEquals(convert("src/migrations/*.groovy"), "file:src/migrations/*.groovy");
        Assert.assertEquals(convert("src/migrations/patch-*.groovy"), "file:src/migrations/patch-*.groovy");
        Assert.assertEquals(convert("src/migrations/**/patch-*.groovy"), "file:src/migrations/**/patch-*.groovy");
    }

    private String convert(String location) {
        return new ResourceMigrationResolver("").convertMigrationsLocation(location);
    }
}
