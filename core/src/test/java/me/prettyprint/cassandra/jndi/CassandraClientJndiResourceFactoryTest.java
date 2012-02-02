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
import me.prettyprint.hector.api.Keyspace;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Perry Hoekstra (dutchman_mn@charter.net)
 * @author zznate
 */
public class CassandraClientJndiResourceFactoryTest extends BaseEmbededServerSetupTest {
  // canned data
  private final static String cassandraUrl = "localhost:9170";


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

    resource.add(new StringRefAddr("hosts", cassandraUrl));
    resource.add(new StringRefAddr("clusterName", clusterName));
    resource.add(new StringRefAddr("keyspace", "Keyspace1"));
    resource.add(new StringRefAddr("autoDiscoverHosts", "true"));
    

    Name jndiName = mock(Name.class);
    Context context = new InitialContext();
    Hashtable<String, String> environment = new Hashtable<String, String>();

    Keyspace keyspace = (Keyspace) factory.getObjectInstance(resource, jndiName, context, environment);

    assertNotNull(keyspace);
    assertEquals("Keyspace1",keyspace.getKeyspaceName());
  }
}
