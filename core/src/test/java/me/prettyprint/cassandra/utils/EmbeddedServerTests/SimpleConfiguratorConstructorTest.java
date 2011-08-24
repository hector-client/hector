package me.prettyprint.cassandra.utils.EmbeddedServerTests;

import me.prettyprint.cassandra.testutils.EmbeddedServerConfigurator;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class SimpleConfiguratorConstructorTest {
    @Test
    public void testSimpleConfiguratorConstructor() {

        EmbeddedServerConfigurator esc = new EmbeddedServerConfigurator();

        EmbeddedServerHelper embedded = new EmbeddedServerHelper(esc);
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
