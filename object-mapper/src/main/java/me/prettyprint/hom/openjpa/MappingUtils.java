package me.prettyprint.hom.openjpa;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.model.thrift.ThriftSliceQuery;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.SerializerTypeInferer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.query.SliceQuery;

import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.util.OpenJPAId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for bridging Hector/OpenJPA functionality 
 *
 * @author zznate
 *
 */
public class MappingUtils {
  private static final Logger log = LoggerFactory.getLogger(MappingUtils.class);
  
  public SliceQuery<byte[], String, byte[]> buildSliceQuery(Object idObj, ClassMetaData classMetaData, Keyspace keyspace) {
    List<String> columns = buildColumnList(classMetaData);
    SliceQuery<byte[], String, byte[]> query = new ThriftSliceQuery(keyspace, BytesArraySerializer.get(), StringSerializer.get(), BytesArraySerializer.get());
    query.setColumnNames(columns.toArray(new String[]{}));
    query.setKey(getKeyBytes(idObj));
    query.setColumnFamily("TestBeanColumnFamily");
    return query;
  }
  
  /**
   * Get the byte[] representing this Id object
   * @param idObj
   * @return
   */
  public byte[] getKeyBytes(Object idObj) {
    Serializer serializer = getSerializer(idObj);
    if ( idObj instanceof OpenJPAId )
      return serializer.toBytes(((OpenJPAId)idObj).getIdObject());
    return serializer.toBytes(idObj);
  }
  
  /**
   * Retrieve the {@link Serializer} implementation for the id Object. 
   * @see {@link SerializerTypeInferer} for specifics.
   * @param idObj
   */
  public Serializer getSerializer(Object idObj) {
    Serializer serializer;
    if ( idObj instanceof OpenJPAId ) {
      serializer = SerializerTypeInferer.getSerializer(((OpenJPAId)idObj).getIdObject());
    } else {
      serializer = SerializerTypeInferer.getSerializer(idObj);
    }    
    return serializer;
  }
  
  /**
   * Return a list of field names for which there are a 1-1 mapping 
   * to Column names
   * 
   * @param metaData
   * @return
   */
  List<String> buildColumnList(ClassMetaData metaData) {    
    FieldMetaData[] fmds = metaData.getFields();
    List<String> cols = new ArrayList<String>(fmds.length);
    for (int i = 0; i < fmds.length; i++) {
        if (fmds[i].getManagement() != fmds[i].MANAGE_PERSISTENT || fmds[i].isPrimaryKey())
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
    return cols;
  }
}
