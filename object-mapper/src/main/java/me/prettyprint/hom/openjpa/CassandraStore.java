package me.prettyprint.hom.openjpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.util.OpenJPAId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.utils.StringUtils;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.hom.EntityManagerConfigurator;


/**
 * Holds the {@link Cluster} and {@link Keyspace} references needed
 * for accessing Cassandra
 * 
 * @author zznate
 */
public class CassandraStore {

  private static final Logger log = LoggerFactory.getLogger(CassandraStore.class);
  
  private final Cluster cluster;
  private final CassandraStoreConfiguration conf;
  private Keyspace keyspace;
  
  public CassandraStore(CassandraStoreConfiguration conf) {
    this.conf = conf;
    this.cluster = HFactory.getCluster(conf.getValue(EntityManagerConfigurator.CLUSTER_NAME_PROP)
        .getOriginalValue());
    // TODO needs passthrough of other configuration
  }
  
  public CassandraStore open() {
    this.keyspace = HFactory.createKeyspace(conf.getValue(EntityManagerConfigurator.KEYSPACE_PROP)
        .getOriginalValue(), cluster);
    return this;
  }
  
  public <I> boolean getObject(OpenJPAStateManager stateManager, I idObj) {
    // build CFMappingDef
    ClassMetaData metaData = stateManager.getMetaData();
    SliceQuery<byte[], String, byte[]> q = HFactory.createSliceQuery(keyspace,
        BytesArraySerializer.get(), StringSerializer.get(), BytesArraySerializer.get());
    q.setColumnFamily("TestBeanColumnFamily");
    Long id = (Long)((OpenJPAId)idObj).getIdObject();
    log.debug("id in getObject: {}", id);
    q.setKey(LongSerializer.get().toBytes(id));
    
    // run through each field writing out the value
    // metaData.getPrimaryKeyFields
    
    FieldMetaData[] fmds = metaData.getFields();
    ArrayList<String> cols = new ArrayList<String>(fmds.length);
    for (int i = 0; i < fmds.length; i++) {
        if (fmds[i].getManagement() != fmds[i].MANAGE_PERSISTENT)
            continue;

        log.debug("fmd.name: {}",fmds[i].getName());
        cols.add(fmds[i].getName());
        
        // write out the field data depending upon type
        switch (fmds[i].getTypeCode()) {
            case JavaTypes.COLLECTION:
            case JavaTypes.ARRAY:
                
                // write out each of the elements
                int elemType = fmds[i].getElement().getTypeCode();
                log.debug("(Collection|Array)typeCode: {}", elemType);
                /*
                for (Iterator ci = c.iterator(); ci.hasNext();) {
                    ...elemType, ci.next());
                }
                */
                break;

            case JavaTypes.MAP:
                
              int keyType = fmds[i].getKey().getTypeCode();
                int valueType = fmds[i].getElement().getTypeCode();
                log.debug("(Map) value typeCode: {}, key typeCode: {}",valueType,keyType );
                /*
                for (Iterator ei = entries.iterator(); ei.hasNext();) {
                    Map.Entry e = (Map.Entry) ei.next();
                    ...keyType, e.getKey());
                    ...valueType, e.getValue());
                }
                */
                break;

            default:
                log.debug("default typeCode: {}",fmds[i].getTypeCode());
                    
        }

    }
    //q.setRange("", "", false, 10);
    q.setColumnNames(cols.toArray(new String[]{}));
    log.debug("queryObj: {}",q);
    QueryResult<ColumnSlice<String, byte[]>> result = q.execute();
    log.debug("sliceResult: {}",new String(result.get().getColumnByName("name").getValue()));
    stateManager.storeString(1, StringUtils.string(result.get().getColumnByName("name").getValue()));
    stateManager.storeLong(0, id);
    // similarly handoff to hectorObjectMapper.createObject(CFMappingDef<T, I> cfMapDef, I id, ColumnSlice<String, byte[]> slice)
    return true;
  }
  
  

  public Cluster getCluster() {
    return cluster;
  }

  public Keyspace getKeyspace() {
    return keyspace;
  }


  
  
}
