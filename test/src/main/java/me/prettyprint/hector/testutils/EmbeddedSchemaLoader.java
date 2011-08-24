package me.prettyprint.hector.testutils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.config.ColumnDefinition;
import org.apache.cassandra.config.ConfigurationException;
import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.config.KSMetaData;
import org.apache.cassandra.db.ColumnFamilyType;
import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.AsciiType;
import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.db.marshal.CompositeType;
import org.apache.cassandra.db.marshal.CounterColumnType;
import org.apache.cassandra.db.marshal.IntegerType;
import org.apache.cassandra.db.marshal.LongType;
import org.apache.cassandra.db.marshal.TimeUUIDType;
import org.apache.cassandra.db.marshal.UTF8Type;
import org.apache.cassandra.locator.AbstractReplicationStrategy;
import org.apache.cassandra.locator.SimpleStrategy;
import org.apache.cassandra.thrift.IndexType;

import com.google.common.base.Charsets;

public class EmbeddedSchemaLoader {
  public static void loadSchema() {
    try {
      for (KSMetaData ksm : schemaDefinition()) {
        for (CFMetaData cfm : ksm.cfMetaData().values())
          CFMetaData.map(cfm);
        DatabaseDescriptor.setTableDefinition(ksm, DatabaseDescriptor
            .getDefsVersion());
      }
    } catch (ConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  public static Collection<KSMetaData> schemaDefinition() {
    List<KSMetaData> schema = new ArrayList<KSMetaData>();

    // A whole bucket of shorthand
    String ks1 = "Keyspace1";
    String ks2 = "Keyspace2";

    Class<? extends AbstractReplicationStrategy> simple = SimpleStrategy.class;
    Map<String, String> opts = new HashMap<String, String>();
    opts.put("replication_factor", Integer.toString(1));
    
    ColumnFamilyType st = ColumnFamilyType.Standard;
    ColumnFamilyType su = ColumnFamilyType.Super;
    AbstractType bytes = BytesType.instance;
    
    List<AbstractType> subComparators = new ArrayList<AbstractType>();
    subComparators.add(BytesType.instance);
    subComparators.add(TimeUUIDType.instance);
    subComparators.add(IntegerType.instance);
    
    // Keyspace 1
    schema.add(new KSMetaData(
        ks1,
        simple,
        opts,

        // Column Families
        standardCFMD(ks1, "Standard1"), standardCFMD(ks1, "Standard2"),
        standardCFMD(ks1, "Standard3"), standardCFMD(ks1, "Standard4"),
        cqlTestCf(ks1, "StandardLong1",UTF8Type.instance), 
        standardCFMD(ks1, "StandardLong2").keyValidator(UTF8Type.instance),
        superCFMD(ks1, "Super1", BytesType.instance),
        superCFMD(ks1, "Super2", LongType.instance),
        superCFMD(ks1, "Super3", LongType.instance),
        superCFMD(ks1, "Super4", UTF8Type.instance),
        superCFMD(ks1, "Super5", bytes),
        indexCFMD(ks1, "Indexed1", true),
        indexCFMD(ks1, "Indexed2", false),
        new CFMetaData(ks1, "StandardInteger1", st, IntegerType.instance, null).keyCacheSize(0),        
        new CFMetaData(ks1, "Counter1", st, bytes, null).replicateOnWrite(true).defaultValidator(CounterColumnType.instance),
        new CFMetaData(ks1, "Counter2", st, bytes, null).replicateOnWrite(true).defaultValidator(CounterColumnType.instance),
        new CFMetaData(ks1, "SuperCounter1", su, bytes, bytes).replicateOnWrite(true).defaultValidator(CounterColumnType.instance),
        jdbcCFMD(ks1, "JdbcInteger", IntegerType.instance),
        jdbcCFMD(ks1, "JdbcUtf8", UTF8Type.instance),
        jdbcCFMD(ks1, "JdbcLong", LongType.instance),
        jdbcCFMD(ks1, "JdbcBytes", bytes),
        jdbcCFMD(ks1, "JdbcAscii", AsciiType.instance)));

    // Keyspace 2
   
    return schema;
  }

  private static CFMetaData compositeCFMD(String ksName, String cfName, AbstractType... types) {
    try {
      return new CFMetaData(ksName, cfName, ColumnFamilyType.Standard, CompositeType.getInstance(Arrays.asList(types)), null);      
    } catch (ConfigurationException e) {
      
    }
    return null;
  }
  
  private static CFMetaData standardCFMD(String ksName, String cfName) {
    return new CFMetaData(ksName, cfName, ColumnFamilyType.Standard,
        BytesType.instance, null).keyCacheSize(0);
  }

  private static CFMetaData superCFMD(String ksName, String cfName,
      AbstractType subcc) {
    return new CFMetaData(ksName, cfName, ColumnFamilyType.Super,
        BytesType.instance, subcc).keyCacheSize(0);
  }

  private static CFMetaData indexCFMD(String ksName, String cfName, final Boolean withIdxType)
  {
      return standardCFMD(ksName, cfName)
              .columnMetadata(new HashMap<ByteBuffer, ColumnDefinition>()
                  {{
                      ByteBuffer cName = ByteBuffer.wrap("birthyear".getBytes(Charsets.UTF_8));
                      IndexType keys = withIdxType ? IndexType.KEYS : null;
                      put(cName, new ColumnDefinition(cName, LongType.instance, keys, null));
                  }});
  }

  private static CFMetaData jdbcCFMD(String ksName, String cfName,
      AbstractType comp) {
    return new CFMetaData(ksName, cfName, ColumnFamilyType.Standard, comp, null)
        .defaultValidator(comp);
  }
  
  private static CFMetaData cqlTestCf(String ksName, String cfName,
      AbstractType comp) {
    return new CFMetaData(ksName, cfName, ColumnFamilyType.Standard, comp, null)
        .keyValidator(UTF8Type.instance).columnMetadata(new HashMap<ByteBuffer, ColumnDefinition>()
            {{
              ByteBuffer cName = ByteBuffer.wrap("birthyear".getBytes(Charsets.UTF_8));
              put(cName, new ColumnDefinition(cName, LongType.instance, null, null));
          }});
  }
}

