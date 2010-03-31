package me.prettyprint.cassandra.jndi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Hashtable;

import me.prettyprint.cassandra.service.CassandraClient;
import me.prettyprint.cassandra.testutils.EmbeddedServerHelper;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.StringRefAddr;

/**
 * @author Perry Hoekstra (dutchman_mn@charter.net)
 */

public class CassandraClientJndiResourceFactoryTest {
  // canned data
  private final static String cassandraUrl = "localhost";
  private final static int cassandraPort = 9170;
	
  private CassandraClientJndiResourceFactory factory;
  private static EmbeddedServerHelper embeddedServerHelper;
  
  @Before
  public void setupTest() throws Exception {	   
    embeddedServerHelper = new EmbeddedServerHelper();
	embeddedServerHelper.setup();
	  
	factory = new CassandraClientJndiResourceFactory();
  }
	  
  @AfterClass
  public static void teardown() throws IOException {
	  embeddedServerHelper.teardown();
  }
  
  @Test
  @SuppressWarnings("unchecked")
  public void getObjectInstance() throws Exception {
    Reference resource = new Reference("CassandraClientFactory");
		
    resource.add(new StringRefAddr("url", cassandraUrl));
    resource.add(new StringRefAddr("port", Integer.toString(cassandraPort)));
		
	Name jndiName = mock(Name.class);
	Context context = new InitialContext();
	Hashtable environment = new Hashtable();
		
	CassandraClientJndiResourcePool cassandraClientJNDIResourcePool =
	  (CassandraClientJndiResourcePool) factory.getObjectInstance(resource, jndiName, context, environment);
	
	CassandraClient cassandraClient = (CassandraClient) cassandraClientJNDIResourcePool.borrowObject();
		
	assertNotNull(cassandraClient);
	assertEquals(cassandraUrl, cassandraClient.getUrl());
	assertEquals(cassandraPort, cassandraClient.getPort());
  }
}
