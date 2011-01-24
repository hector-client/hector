package me.prettyprint.hom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.model.BasicColumnFamilyDefinition;
import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hom.openjpa.EntityFacade;
import me.prettyprint.hom.service.EntitySchemaStatus.SchemaResult;

public class EntitySchemaManager {
  
  private static Logger log = LoggerFactory.getLogger(EntitySchemaManager.class);
  
  private Cluster cluster;
  private Keyspace keyspace;
  
  public EntitySchemaManager(Cluster cluster, Keyspace keyspace) {
    this.cluster = cluster;
    this.keyspace = keyspace;
  }

  public EntitySchemaStatus createSchema(EntityFacade entityFacade) {
    BasicColumnFamilyDefinition columnFamilyDefinition = new BasicColumnFamilyDefinition();
    columnFamilyDefinition.setKeyspaceName(keyspace.getKeyspaceName());
    columnFamilyDefinition.setName(entityFacade.getColumnFamilyName());    
    
    ColumnFamilyDefinition cfDef = new ThriftCfDef(columnFamilyDefinition);
    EntitySchemaStatus entitySchemaStatus;
    try {
      cluster.addColumnFamily(cfDef);    
      entitySchemaStatus = new EntitySchemaStatus(SchemaResult.CREATED);
    } catch (HInvalidRequestException ire) {
      entitySchemaStatus = new EntitySchemaStatus(SchemaResult.NOT_MODIFIED); 
    }
    return entitySchemaStatus;
    
  }
  
  
}
