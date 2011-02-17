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

    //HColumnFamily<String,String> columnFamily = keyspace.getColumnFamily("Standard1", se, se);
    // injected w/ hconnectionManager reference
    // - can thus call operateWithFailover internally
    // - call describe_keyspace to get CFMetaData to build typing information??
    // 
    
    // automatic slicing based on default predicate "first 100 cols in comparator order"
    //columnFamily.addKey("zznate");    
    // HColumnFamily extends iterable, but automatically calls next() for per-row operations getColumns()/getColumn
    //List<HColumn<String, ?>> cols = columnFamily.getColumns();
    // OR - we are already at the first result
    //String str = columnFamily.getString("name"); // getInt, getLong, getDate, getDouble, getTime, getUUID
    // also/or V v columnFamily.get(N n, Serializer<V> s);
    // but what happens when i try to getColumns on a multiget_slice? IAE?
    

  }
}
