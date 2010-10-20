package me.prettyprint.cassandra.model;

import me.prettyprint.hector.api.ddl.ColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ColumnType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: peter
 * Date: 20/10/2010
 * Time: 4:53:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasicColumnFamilyDefinition implements ColumnFamilyDefinition {




  /**
   * SHOULD THIS BE HERE? A COLUMN DEFINITION IS PART OF A KEYSPACE BY VIRTUE
   * OF BEING IN A KEYSPACE LIST
   */
  @Override
  public KeyspaceDefinition getKeyspace() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String getName() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public ColumnType getColumnType() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String getComparatorType() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String getSubcomparatorType() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String getComment() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public double getRowCacheSize() {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public boolean isPreloadRowCache() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public double getKeyCacheSize() {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public double getReadRepairChance() {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public List<ColumnDefinition> getColumnMetadata() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int getGcGraceSeconds() {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public String getDefaultValidationClass() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int getId() {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int getMaxCompactionThreshold() {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public int getMinCompactionThreshold() {
    return 0;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
