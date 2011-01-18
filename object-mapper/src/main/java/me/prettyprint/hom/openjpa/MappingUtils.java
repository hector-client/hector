package me.prettyprint.hom.openjpa;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.hector.api.query.SliceQuery;

import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.util.OpenJPAId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MappingUtils {
  private static final Logger log = LoggerFactory.getLogger(MappingUtils.class);
  
  public SliceQuery<Long, String, byte[]> buildSliceQuery(ClassMetaData classMetaData) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public <I> I getDeserializedId(I idObj) {
    Long id = (Long)((OpenJPAId)idObj).getIdObject();
    log.debug("id in getObject: {}", id);
    //q.setKey(LongSerializer.get().toBytes(id));
    // TODO maintain static mapping from OpenJPA id types to our serializer types
    return null;
  }
  
  /**
   * Return a list of field names for which there are a 1-1 mapping 
   * to Column names
   * 
   * @param metaData
   * @return
   */
  List<String> buildColumnMap(ClassMetaData metaData) {
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
