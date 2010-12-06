package me.prettyprint.hector.api.ddl;

import java.util.List;
import java.util.Map;

/**
 * Hector's implementation independent KsDef version.
 *
 * @author Ran Tavory
 *
 */
public interface KeyspaceDefinition {

  String getName();
  String getStrategyClass();
  Map<String, String> getStrategyOptions();
  int getReplicationFactor();
  List<ColumnFamilyDefinition> getCfDefs();
}
