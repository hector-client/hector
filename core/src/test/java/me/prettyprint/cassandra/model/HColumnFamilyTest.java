package me.prettyprint.cassandra.model;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.service.HColumnFamilyImpl;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.HColumnFamily;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;


public class HColumnFamilyTest extends BaseEmbededServerSetupTest {
  
  private Keyspace keyspace;
  private UUID timeUUID;
  @Before
  public void setupLocal() {
    setupClient();
    Cluster cluster = new ThriftCluster("Test Cluster", cassandraHostConfigurator);
    keyspace = HFactory.createKeyspace("Keyspace1", cluster);
    Mutator<String> mutator = HFactory.createMutator(keyspace, StringSerializer.get());
    mutator.addInsertion("zznate", "Standard1", HFactory.createStringColumn("email", "nate@datastax.com"));
    mutator.addInsertion("zznate", "Standard1", HFactory.createColumn("int", 1, StringSerializer.get(), IntegerSerializer.get()));
    mutator.addInsertion("zznate", "Standard1", HFactory.createColumn("long", 1L, StringSerializer.get(), LongSerializer.get()));
    timeUUID = TimeUUIDUtils.getTimeUUID(System.currentTimeMillis());
    mutator.addInsertion("zznate", "Standard1", HFactory.createColumn("uuid", timeUUID, StringSerializer.get(), UUIDSerializer.get()));
    mutator.execute();
  }
  
  @Test
  public void testColumnFamilySetup() {
    HColumnFamily<String, String> columnFamily = new HColumnFamilyImpl<String,String>(keyspace, "Standard1",StringSerializer.get(), StringSerializer.get());
    columnFamily.addKey("zznate");
    // columnFamily.loadSlice(15); columnFamily.addKey().loadSlice() ~ def. 100
    assertEquals(1,columnFamily.getInt("int"));
    assertEquals("nate@datastax.com",columnFamily.getString("email"));
    assertEquals(1L, columnFamily.getLong("long"));
    assertEquals(timeUUID, columnFamily.getUUID("uuid"));

  }
  
  @Test
  public void testColumnFamilyReadahead() {
    HColumnFamily<String, String> columnFamily = new HColumnFamilyImpl<String,String>(keyspace, "Standard1",StringSerializer.get(), StringSerializer.get());
    columnFamily.addKey("zznate").setCount(10);
    assertEquals(4,columnFamily.getColumns().size());
    // columnFamily.loadSlice(15); columnFamily.addKey().loadSlice() ~ def. 100
    assertEquals(1,columnFamily.getInt("int"));
    assertEquals("nate@datastax.com",columnFamily.getString("email"));
    assertEquals(1L, columnFamily.getLong("long"));
    assertEquals(timeUUID, columnFamily.getUUID("uuid"));

  }
}
