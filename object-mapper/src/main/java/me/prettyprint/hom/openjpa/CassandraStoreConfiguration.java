package me.prettyprint.hom.openjpa;

import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.lib.conf.ProductDerivations;

public class CassandraStoreConfiguration extends OpenJPAConfigurationImpl {

  public CassandraStoreConfiguration() {
    super(false, false);

    // override the default and the current value of lock manager plugin
    // from our superclass to use the single-jvm lock manager
    lockManagerPlugin.setDefault("version");
    lockManagerPlugin.setString("version");
    addString("me.prettyprint.hom.classpathPrefix");
    addString("me.prettyprint.hom.keyspace");
    addString("me.prettyprint.hom.clusterName");
    addString("me.prettyprint.hom.hostList");

    ProductDerivations.beforeConfigurationLoad(this);
    loadGlobals();
    
  }
}
