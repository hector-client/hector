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
  private MappingUtils mappingUtils;
  
  public CassandraStore(CassandraStoreConfiguration conf) {
    this.conf = conf;
    this.cluster = HFactory.getCluster(conf.getValue(EntityManagerConfigurator.CLUSTER_NAME_PROP)
        .getOriginalValue());
    // TODO needs passthrough of other configuration
    mappingUtils = new MappingUtils();
  }
  
  public CassandraStore open() {
    this.keyspace = HFactory.createKeyspace(conf.getValue(EntityManagerConfigurator.KEYSPACE_PROP)
        .getOriginalValue(), cluster);
    return this;
  }
  
  public <I> boolean getObject(OpenJPAStateManager stateManager, I idObj) {
    // build CFMappingDef
    ClassMetaData metaData = stateManager.getMetaData();
    SliceQuery<byte[], String, byte[]> sliceQuery = mappingUtils.buildSliceQuery(idObj, metaData, keyspace);
    
    QueryResult<ColumnSlice<String, byte[]>> result = sliceQuery.execute();
    log.debug("sliceResult: {}",new String(result.get().getColumnByName("name").getValue()));
    stateManager.storeString(1, StringUtils.string(result.get().getColumnByName("name").getValue()));
    stateManager.storeObject(0, idObj);
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
