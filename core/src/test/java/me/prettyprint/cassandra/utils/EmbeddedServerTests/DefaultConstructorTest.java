package me.prettyprint.cassandra.utils.EmbeddedServerTests;

import me.prettyprint.cassandra.testutils.EmbeddedSchemaLoader;
import me.prettyprint.cassandra.testutils.EmbeddedServerConfigurator;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;

import org.apache.cassandra.service.EmbeddedCassandraService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Felipe Seré
 */
public class DefaultConstructorTest {
    public static Logger log = LoggerFactory.getLogger(DefaultConstructorTest.class);

    @Test
    public void testDefaultConstructor() {

        EmbeddedServerHelper embedded = new EmbeddedServerHelper();
        try {
            embedded.setup();
            TimeUnit.SECONDS.sleep(10);
            embedded.teardown();

        }
        catch(Exception e) {
            // Should never have reached here
            assertTrue("Exception " + e.toString(),false);

        }

         embedded = null;

         assertTrue(true);
    }
}
