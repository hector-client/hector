package me.prettyprint.hom;

import java.util.BitSet;
import java.util.Collection;
import java.util.List;

import org.apache.openjpa.abstractstore.AbstractStoreManager;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.meta.ClassMetaData;

public class CassandraStoreManager extends AbstractStoreManager {

  @Override
  public ResultObjectProvider executeExtent(ClassMetaData arg0, boolean arg1,
      FetchConfiguration arg2) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Collection flush(Collection arg0, Collection arg1, Collection arg2,
      Collection arg3, Collection arg4) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean initialize(OpenJPAStateManager stateManager, PCState pcState,
      FetchConfiguration fetchConfiguration, Object obj) {
    stateManager.getObjectId();
    
    return false;
  }

  @Override
  public boolean load(OpenJPAStateManager arg0, BitSet arg1,
      FetchConfiguration arg2, int arg3, Object arg4) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean exists(OpenJPAStateManager arg0, Object arg1) {
    // TODO Auto-generated method stub
    return false;
  }


  @Override
  public boolean isCached(List<Object> arg0, BitSet arg1) {
    // TODO Auto-generated method stub
    return false;
  }

  protected Collection getUnsupportedOptions() {
    Collection c = super.getUnsupportedOptions();

    // remove options we do support but the abstract store doesn't
    //c.remove(OpenJPAConfiguration.OPTION_ID_DATASTORE);
    //c.remove(OpenJPAConfiguration.OPTION_OPTIMISTIC);

    // and add some that we don't support but the abstract store does
    c.add(OpenJPAConfiguration.OPTION_EMBEDDED_RELATION);
    c.add(OpenJPAConfiguration.OPTION_EMBEDDED_COLLECTION_RELATION);
    c.add(OpenJPAConfiguration.OPTION_EMBEDDED_MAP_RELATION);
    c.add(OpenJPAConfiguration.OPTION_OPTIMISTIC);
    return c;
  }

  @Override
  protected OpenJPAConfiguration newConfiguration() {
    
    OpenJPAConfiguration conf =  super.newConfiguration();
    return conf;
  } 
  
  

}
