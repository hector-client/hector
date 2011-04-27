import me.prettyprint.hector.api.factory.HFactory
import me.prettyprint.hector.api.ddl.KeyspaceDefinition

try {
  cluster.dropKeyspace("testKS");
} catch (Exception e) {
  println "Exception ${e.getMessage()}"
}

KeyspaceDefinition ksDef = HFactory.createKeyspaceDefinition("testKS");
cluster.addKeyspace(ksDef);