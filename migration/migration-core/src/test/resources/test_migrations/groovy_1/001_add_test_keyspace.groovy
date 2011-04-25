import me.prettyprint.hector.api.factory.HFactory
import me.prettyprint.hector.api.ddl.KeyspaceDefinition

cluster.dropKeyspace("testKS");
KeyspaceDefinition ksDef = HFactory.createKeyspaceDefinition("testKS");
cluster.addKeyspace(ksDef);