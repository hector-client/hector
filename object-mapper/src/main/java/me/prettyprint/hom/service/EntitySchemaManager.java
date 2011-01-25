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

/**
 * A service to manage the schema of JPA Entities
 * 
 * @author zznate
 */
public class EntitySchemaManager {
  
  private static Logger log = LoggerFactory.getLogger(EntitySchemaManager.class);
  
  private Cluster cluster;
  private Keyspace keyspace;
  
  public EntitySchemaManager(Cluster cluster, Keyspace keyspace) {
    this.cluster = cluster;
    this.keyspace = keyspace;
  }

  /**
   * Creates the schema if it does not already exist. If it does exist,
   * we catch the HInvalidRequestException and return an {@link EntitySchemaStatus}
   * with {@link SchemaResult#NOT_MODIFIED}
   * @param entityFacade
   * @return {@link EntitySchemaStatus} with {@link SchemaResult#CREATED} if successful
   */
  public EntitySchemaStatus createSchema(EntityFacade entityFacade) {
    if ( log.isDebugEnabled() ) {
      log.debug("EntitySchemaManager creating schema for: {}", entityFacade);
    }
    BasicColumnFamilyDefinition columnFamilyDefinition = new BasicColumnFamilyDefinition();
    columnFamilyDefinition.setKeyspaceName(keyspace.getKeyspaceName());
    columnFamilyDefinition.setName(entityFacade.getColumnFamilyName());    
    // TODO determine how to pull in other CF creation settings... custom annotations?
    ColumnFamilyDefinition cfDef = new ThriftCfDef(columnFamilyDefinition);
    EntitySchemaStatus entitySchemaStatus;
    try {
      cluster.addColumnFamily(cfDef);    
      entitySchemaStatus = new EntitySchemaStatus(SchemaResult.CREATED);
    } catch (HInvalidRequestException ire) {
      if ( log.isDebugEnabled() ) {
        log.debug("Caught HInvalidRequestException on createSchema. Details {}", ire.getMessage());
      }
      entitySchemaStatus = new EntitySchemaStatus(SchemaResult.NOT_MODIFIED); 
    }
    return entitySchemaStatus;
    
  }
  
  
}
