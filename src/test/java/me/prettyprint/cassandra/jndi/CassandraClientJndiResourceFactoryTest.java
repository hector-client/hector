package me.prettyprint.cassandra.jndi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Perry Hoekstra (dutchman_mn@charter.net)
 */
@Ignore
public class CassandraClientJndiResourceFactoryTest extends BaseEmbededServerSetupTest {
  // canned data
  private final static String cassandraUrl = "localhost";
  private final static int cassandraPort = 9170;

  private CassandraClientJndiResourceFactory factory;

  @Before
  public void setupCase() throws Exception {
    factory = new CassandraClientJndiResourceFactory();
  }

  @After
  public void teardownCase() throws IOException {
    factory = null;
  }

  @Test
  public void getObjectInstance() throws Exception {
    Reference resource = new Reference("CassandraClientFactory");

    resource.add(new StringRefAddr("url", cassandraUrl));
    resource.add(new StringRefAddr("port", Integer.toString(cassandraPort)));

    Name jndiName = mock(Name.class);
    Context context = new InitialContext();
    Hashtable<String, String> environment = new Hashtable<String, String>();

    CassandraClientJndiResourcePool cassandraClientJNDIResourcePool =
      (CassandraClientJndiResourcePool) factory.getObjectInstance(resource, jndiName, context,
          environment);

    //CassandraClient cassandraClient = (CassandraClient) cassandraClientJNDIResourcePool.borrowObject();
    // TODO fix this
    //assertNotNull(cassandraClient);
    //assertEquals(cassandraUrl, cassandraClient.getCassandraHost().getHost());
    //assertEquals(cassandraPort, cassandraClient.getCassandraHost().getPort());
  }
}
