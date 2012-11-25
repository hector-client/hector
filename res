[INFO] Scanning for projects...
[INFO] Reactor build order: 
[INFO]   hector
[INFO]   test
[INFO]   hector-core
[INFO]   hector-object-mapper
[INFO] ------------------------------------------------------------------------
[INFO] Building hector
[INFO]    task-segment: [install]
[INFO] ------------------------------------------------------------------------
[INFO] [enforcer:enforce {execution: enforce-maven}]
[INFO] [site:attach-descriptor {execution: default-attach-descriptor}]
[INFO] [jar:test-jar {execution: default}]
[WARNING] JAR will be empty - no content was marked for inclusion!
[INFO] Preparing source:jar
[WARNING] Removing: jar from forked lifecycle, to prevent recursive invocation.
[INFO] [enforcer:enforce {execution: enforce-maven}]
[INFO] [source:jar {execution: attach-sources}]
[INFO] [install:install {execution: default-install}]
[INFO] Installing /home/cesare/workspace/tesi/CassandraBM/hector/pom.xml to /root/.m2/repository/org/hectorclient/hector/1.1-2-SNAPSHOT/hector-1.1-2-SNAPSHOT.pom
[INFO] Installing /home/cesare/workspace/tesi/CassandraBM/hector/target/hector-1.1-2-SNAPSHOT-tests.jar to /root/.m2/repository/org/hectorclient/hector/1.1-2-SNAPSHOT/hector-1.1-2-SNAPSHOT-tests.jar
[INFO] ------------------------------------------------------------------------
[INFO] Building test
[INFO]    task-segment: [install]
[INFO] ------------------------------------------------------------------------
[INFO] [enforcer:enforce {execution: enforce-maven}]
[INFO] [resources:resources {execution: default-resources}]
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /home/cesare/workspace/tesi/CassandraBM/hector/test/src/main/resources
[INFO] [compiler:compile {execution: default-compile}]
[INFO] Nothing to compile - all classes are up to date
[INFO] [resources:testResources {execution: default-testResources}]
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /home/cesare/workspace/tesi/CassandraBM/hector/test/src/test/resources
[INFO] [compiler:testCompile {execution: default-testCompile}]
[INFO] No sources to compile
[WARNING] DEPRECATED [systemProperties]: Use systemPropertyVariables instead.
[INFO] [surefire:test {execution: default-test}]
[INFO] No tests to run.
[INFO] Surefire report directory: /home/cesare/workspace/tesi/CassandraBM/hector/test/target/surefire-reports
[INFO] [jar:jar {execution: default-jar}]
[INFO] [jar:test-jar {execution: default}]
[WARNING] JAR will be empty - no content was marked for inclusion!
[INFO] Preparing source:jar
[WARNING] Removing: jar from forked lifecycle, to prevent recursive invocation.
[INFO] [enforcer:enforce {execution: enforce-maven}]
[INFO] [source:jar {execution: attach-sources}]
[INFO] Building jar: /home/cesare/workspace/tesi/CassandraBM/hector/test/target/hector-test-1.1-2-SNAPSHOT-sources.jar
[INFO] [install:install {execution: default-install}]
[INFO] Installing /home/cesare/workspace/tesi/CassandraBM/hector/test/target/hector-test-1.1-2-SNAPSHOT.jar to /root/.m2/repository/org/hectorclient/hector-test/1.1-2-SNAPSHOT/hector-test-1.1-2-SNAPSHOT.jar
[INFO] Installing /home/cesare/workspace/tesi/CassandraBM/hector/test/target/hector-test-1.1-2-SNAPSHOT-tests.jar to /root/.m2/repository/org/hectorclient/hector-test/1.1-2-SNAPSHOT/hector-test-1.1-2-SNAPSHOT-tests.jar
[INFO] Installing /home/cesare/workspace/tesi/CassandraBM/hector/test/target/hector-test-1.1-2-SNAPSHOT-sources.jar to /root/.m2/repository/org/hectorclient/hector-test/1.1-2-SNAPSHOT/hector-test-1.1-2-SNAPSHOT-sources.jar
[INFO] ------------------------------------------------------------------------
[INFO] Building hector-core
[INFO]    task-segment: [install]
[INFO] ------------------------------------------------------------------------
[INFO] [enforcer:enforce {execution: enforce-maven}]
[INFO] [resources:resources {execution: default-resources}]
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] [compiler:compile {execution: default-compile}]
[INFO] Nothing to compile - all classes are up to date
[INFO] [resources:testResources {execution: default-testResources}]
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 12 resources
[INFO] [compiler:testCompile {execution: default-testCompile}]
[INFO] Nothing to compile - all classes are up to date
[WARNING] DEPRECATED [systemProperties]: Use systemPropertyVariables instead.
[INFO] [surefire:test {execution: default-test}]
[INFO] Surefire report directory: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running me.prettyprint.hector.api.ddl.ComparatorTypeTest
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.113 sec
Running me.prettyprint.hector.api.beans.DynamicCompositeTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.291 sec
Running me.prettyprint.hector.api.VirtualKeyspaceTest
19:58:52,978  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
19:58:53,000  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
19:58:53,183  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
19:58:53,360  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
19:58:53,793  INFO EmbeddedServerHelper:66 - Starting executor
19:58:53,794  INFO EmbeddedServerHelper:69 - Started executor
19:58:53,794  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
19:58:53,795  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
19:58:53,795  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter7028201423697062732.jar
19:58:53,798  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
19:58:53,821  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
19:58:53,835  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
19:58:53,836  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
19:58:53,840  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
19:58:53,962  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
19:58:53,963  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
19:58:54,007  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:58:54,016  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
19:58:54,019  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:58:54,022  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
19:58:54,054  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50136071840785.log
19:58:54,061  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50136071840785.log
19:58:54,065  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50136071840785.log
19:58:54,068  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
19:58:54,068  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1821457857(78/97 serialized/live bytes, 2 ops)
19:58:54,070  INFO Memtable:266 - Writing Memtable-IndexInfo@1821457857(78/97 serialized/live bytes, 2 ops)
19:58:54,071  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
19:58:54,087  INFO StorageService:412 - Cassandra version: 1.1.0
19:58:54,087  INFO StorageService:413 - Thrift API version: 19.30.0
19:58:54,102  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
19:58:54,172  INFO StorageService:444 - Loading persisted ring state
19:58:54,176  INFO StorageService:525 - Starting up server gossip
19:58:54,190  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
19:58:54,563  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
19:58:54,566  INFO Memtable:266 - Writing Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
19:58:54,567  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
19:58:54,777  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
19:58:54,794  INFO MessagingService:284 - Starting Messaging Service on port 7070
19:58:54,800  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
19:58:54,808  WARN StorageService:633 - Generated random token xqufTN4QMzGbeMvY. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
19:58:54,810  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@598834505(53/66 serialized/live bytes, 2 ops)
19:58:54,811  INFO Memtable:266 - Writing Memtable-LocationInfo@598834505(53/66 serialized/live bytes, 2 ops)
19:58:55,036  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
19:58:55,041  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
19:58:55,056  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
19:58:55,058  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
19:58:55,094  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
19:58:55,097  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
19:58:55,101  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
19:58:55,102  INFO CassandraDaemon:212 - Listening for thrift clients...
19:58:56,794  INFO EmbeddedServerHelper:73 - Done sleeping
19:58:56,833  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
19:58:56,858  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
19:58:56,986  INFO NodeId:200 - No saved local node id, using newly generated: 389ca3a0-e3de-11e1-0000-fe8ebeead9db
19:58:57,584  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.816 sec
19:58:57,609  INFO CassandraDaemon:218 - Stop listening to thrift clients
19:58:57,611  INFO MessagingService:539 - Waiting for messaging service to quiesce
19:58:57,612  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.hector.api.KeyspaceCreationTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.05 sec
Running me.prettyprint.hector.api.ClockResolutionTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.198 sec
Running me.prettyprint.hector.api.HFactoryTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.119 sec
Running me.prettyprint.hector.api.CompositeTest
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.47 sec
Running me.prettyprint.hector.api.ApiV2SystemTest
19:59:00,359  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
19:59:00,381  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
19:59:00,550  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
19:59:00,708  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
19:59:01,129  INFO EmbeddedServerHelper:66 - Starting executor
19:59:01,130  INFO EmbeddedServerHelper:69 - Started executor
19:59:01,131  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
19:59:01,131  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
19:59:01,131  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter6511870865108343531.jar
19:59:01,135  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
19:59:01,149  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
19:59:01,162  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
19:59:01,163  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
19:59:01,167  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
19:59:01,299  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
19:59:01,300  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
19:59:01,342  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:01,350  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
19:59:01,352  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:01,354  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
19:59:01,374  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50143412422102.log
19:59:01,376  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50143412422102.log
19:59:01,379  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1821457857(78/97 serialized/live bytes, 2 ops)
19:59:01,380  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50143412422102.log
19:59:01,380  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
19:59:01,381  INFO Memtable:266 - Writing Memtable-IndexInfo@1821457857(78/97 serialized/live bytes, 2 ops)
19:59:01,384  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
19:59:01,400  INFO StorageService:412 - Cassandra version: 1.1.0
19:59:01,401  INFO StorageService:413 - Thrift API version: 19.30.0
19:59:01,406  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
19:59:01,475  INFO StorageService:444 - Loading persisted ring state
19:59:01,479  INFO StorageService:525 - Starting up server gossip
19:59:01,495  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
19:59:01,737  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
19:59:01,739  INFO Memtable:266 - Writing Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
19:59:01,740  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
19:59:01,948  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
19:59:01,967  INFO MessagingService:284 - Starting Messaging Service on port 7070
19:59:01,975  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
19:59:01,989  WARN StorageService:633 - Generated random token o3nXOuPh6fO0idXz. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
19:59:01,991  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@598834505(53/66 serialized/live bytes, 2 ops)
19:59:01,992  INFO Memtable:266 - Writing Memtable-LocationInfo@598834505(53/66 serialized/live bytes, 2 ops)
19:59:02,217  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
19:59:02,222  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
19:59:02,234  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
19:59:02,235  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
19:59:02,267  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
19:59:02,271  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
19:59:02,275  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
19:59:02,275  INFO CassandraDaemon:212 - Listening for thrift clients...
19:59:04,131  INFO EmbeddedServerHelper:73 - Done sleeping
19:59:04,175  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
19:59:04,204  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
19:59:04,337  INFO NodeId:200 - No saved local node id, using newly generated: 3cfe5010-e3de-11e1-0000-fe8ebeead9ff
19:59:04,914  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.761 sec
19:59:04,939  INFO CassandraDaemon:218 - Stop listening to thrift clients
19:59:04,941  INFO MessagingService:539 - Waiting for messaging service to quiesce
19:59:04,942  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.serializers.ObjectSerializerTest
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.169 sec
Running me.prettyprint.cassandra.serializers.BigIntegerSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.09 sec
Running me.prettyprint.cassandra.serializers.JaxbSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.176 sec
Running me.prettyprint.cassandra.serializers.UUIDSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.081 sec
Running me.prettyprint.cassandra.serializers.BooleanSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.089 sec
Running me.prettyprint.cassandra.serializers.DateSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.071 sec
Running me.prettyprint.cassandra.serializers.FastInfosetSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.193 sec
Running me.prettyprint.cassandra.serializers.IntegerSerializerTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.079 sec
Running me.prettyprint.cassandra.serializers.BytesArraySerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.193 sec
Running me.prettyprint.cassandra.serializers.TypeInferringSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.124 sec
Running me.prettyprint.cassandra.serializers.ShortSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.072 sec
Running me.prettyprint.cassandra.serializers.PrefixedSerializerTest
19:59:09,872 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
19:59:09,882 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
19:59:09,886 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
19:59:09,888 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
19:59:09,891 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
Tests run: 12, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.268 sec
Running me.prettyprint.cassandra.serializers.StringSerializerTest
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.236 sec
Running me.prettyprint.cassandra.serializers.LongSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.079 sec
Running me.prettyprint.cassandra.jndi.CassandraClientJndiResourceFactoryTest
19:59:11,121  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
19:59:11,145  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
19:59:11,315  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
19:59:11,486  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
19:59:11,915  INFO EmbeddedServerHelper:66 - Starting executor
19:59:11,916  INFO EmbeddedServerHelper:69 - Started executor
19:59:11,916  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
19:59:11,917  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
19:59:11,917  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter1914314734269196545.jar
19:59:11,919  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
19:59:11,937  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
19:59:11,952  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
19:59:11,954  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
19:59:11,958  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
19:59:12,089  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
19:59:12,090  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
19:59:12,131  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:12,139  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
19:59:12,142  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:12,145  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
19:59:12,167  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50154199921145.log
19:59:12,171  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@223489506(78/97 serialized/live bytes, 2 ops)
19:59:12,172  INFO Memtable:266 - Writing Memtable-IndexInfo@223489506(78/97 serialized/live bytes, 2 ops)
19:59:12,172  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50154199921145.log
19:59:12,173  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
19:59:12,177  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50154199921145.log
19:59:12,177  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
19:59:12,191  INFO StorageService:412 - Cassandra version: 1.1.0
19:59:12,191  INFO StorageService:413 - Thrift API version: 19.30.0
19:59:12,196  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
19:59:12,246  INFO StorageService:444 - Loading persisted ring state
19:59:12,249  INFO StorageService:525 - Starting up server gossip
19:59:12,259  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
19:59:12,470  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
19:59:12,472  INFO Memtable:266 - Writing Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
19:59:12,473  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
19:59:12,695  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
19:59:12,711  INFO MessagingService:284 - Starting Messaging Service on port 7070
19:59:12,718  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
19:59:12,726  WARN StorageService:633 - Generated random token 0TzjxV7OTkX7CGlp. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
19:59:12,728  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
19:59:12,729  INFO Memtable:266 - Writing Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
19:59:12,943  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
19:59:12,948  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
19:59:12,962  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
19:59:12,964  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
19:59:12,996  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
19:59:13,000  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
19:59:13,004  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
19:59:13,004  INFO CassandraDaemon:212 - Listening for thrift clients...
19:59:14,917  INFO EmbeddedServerHelper:73 - Done sleeping
19:59:15,099  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
19:59:15,138  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
19:59:15,150  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.221 sec
19:59:15,183  INFO CassandraDaemon:218 - Stop listening to thrift clients
19:59:15,184  INFO MessagingService:539 - Waiting for messaging service to quiesce
19:59:15,185  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.dao.SimpleCassandraDaoTest
19:59:16,182  INFO TestContextManager:185 - @TestExecutionListeners is not present for class [class me.prettyprint.cassandra.dao.SimpleCassandraDaoTest]: using defaults.
19:59:16,211  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
19:59:16,234  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
19:59:16,371  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
19:59:16,613  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
19:59:17,042  INFO EmbeddedServerHelper:66 - Starting executor
19:59:17,043  INFO EmbeddedServerHelper:69 - Started executor
19:59:17,043  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
19:59:17,044  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
19:59:17,044  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter7513500288060390994.jar
19:59:17,047  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
19:59:17,062  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
19:59:17,075  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
19:59:17,076  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
19:59:17,080  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
19:59:17,204  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
19:59:17,207  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
19:59:17,245  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:17,253  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
19:59:17,256  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:17,257  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
19:59:17,278  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50159326470048.log
19:59:17,281  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1179468258(78/97 serialized/live bytes, 2 ops)
19:59:17,282  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50159326470048.log
19:59:17,282  INFO Memtable:266 - Writing Memtable-IndexInfo@1179468258(78/97 serialized/live bytes, 2 ops)
19:59:17,282  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
19:59:17,285  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50159326470048.log
19:59:17,285  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
19:59:17,303  INFO StorageService:412 - Cassandra version: 1.1.0
19:59:17,303  INFO StorageService:413 - Thrift API version: 19.30.0
19:59:17,308  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
19:59:17,360  INFO StorageService:444 - Loading persisted ring state
19:59:17,363  INFO StorageService:525 - Starting up server gossip
19:59:17,373  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@750922299(126/157 serialized/live bytes, 3 ops)
19:59:17,578  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
19:59:17,581  INFO Memtable:266 - Writing Memtable-LocationInfo@750922299(126/157 serialized/live bytes, 3 ops)
19:59:17,584  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
19:59:17,800  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
19:59:17,822  INFO MessagingService:284 - Starting Messaging Service on port 7070
19:59:17,830  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
19:59:17,839  WARN StorageService:633 - Generated random token lkp4IJnFFXre8xtn. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
19:59:17,841  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@503262747(53/66 serialized/live bytes, 2 ops)
19:59:17,842  INFO Memtable:266 - Writing Memtable-LocationInfo@503262747(53/66 serialized/live bytes, 2 ops)
19:59:18,069  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
19:59:18,074  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
19:59:18,085  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
19:59:18,087  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
19:59:18,120  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
19:59:18,124  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
19:59:18,128  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
19:59:18,129  INFO CassandraDaemon:212 - Listening for thrift clients...
19:59:20,043  INFO EmbeddedServerHelper:73 - Done sleeping
19:59:20,128  INFO XmlBeanDefinitionReader:315 - Loading XML bean definitions from class path resource [cassandra-context-test-v2.xml]
19:59:20,293  INFO GenericApplicationContext:456 - Refreshing org.springframework.context.support.GenericApplicationContext@459d3b3a: startup date [Sat Aug 11 19:59:20 CEST 2012]; root of context hierarchy
19:59:20,428  INFO DefaultListableBeanFactory:557 - Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@32c26ede: defining beans [cassandraHostConfigurator,cluster,consistencyLevelPolicy,keyspaceOperator,simpleCassandraDao,org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor]; root of factory hierarchy
19:59:20,487  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
19:59:20,521  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
19:59:20,730  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.793 sec
19:59:20,746  INFO CassandraDaemon:218 - Stop listening to thrift clients
19:59:20,748  INFO GenericApplicationContext:1002 - Closing org.springframework.context.support.GenericApplicationContext@459d3b3a: startup date [Sat Aug 11 19:59:20 CEST 2012]; root of context hierarchy
19:59:20,752  INFO MessagingService:539 - Waiting for messaging service to quiesce
19:59:20,756  INFO DefaultListableBeanFactory:422 - Destroying singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@32c26ede: defining beans [cassandraHostConfigurator,cluster,consistencyLevelPolicy,keyspaceOperator,simpleCassandraDao,org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor]; root of factory hierarchy
19:59:20,757  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.ConfigurableConsistencyLevelTest
19:59:21,776  INFO ConfigurableConsistencyLevel:57 - READ ConsistencyLevel set to ANY for ColumnFamily OtherCf
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.233 sec
Running me.prettyprint.cassandra.model.RangeSlicesQueryTest
19:59:22,233  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
19:59:22,257  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
19:59:22,432  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
19:59:22,589  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
19:59:23,013  INFO EmbeddedServerHelper:66 - Starting executor
19:59:23,014  INFO EmbeddedServerHelper:69 - Started executor
19:59:23,014  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
19:59:23,015  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
19:59:23,015  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3519384782948448936.jar
19:59:23,018  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
19:59:23,034  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
19:59:23,050  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
19:59:23,051  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
19:59:23,057  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
19:59:23,194  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
19:59:23,195  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
19:59:23,253  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:23,263  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
19:59:23,268  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:23,270  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
19:59:23,292  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50165295215489.log
19:59:23,293  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@221571972(78/97 serialized/live bytes, 2 ops)
19:59:23,294  INFO Memtable:266 - Writing Memtable-IndexInfo@221571972(78/97 serialized/live bytes, 2 ops)
19:59:23,295  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
19:59:23,295  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50165295215489.log
19:59:23,299  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50165295215489.log
19:59:23,300  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
19:59:23,317  INFO StorageService:412 - Cassandra version: 1.1.0
19:59:23,317  INFO StorageService:413 - Thrift API version: 19.30.0
19:59:23,323  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
19:59:23,370  INFO StorageService:444 - Loading persisted ring state
19:59:23,373  INFO StorageService:525 - Starting up server gossip
19:59:23,383  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@664219194(126/157 serialized/live bytes, 3 ops)
19:59:23,579  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
19:59:23,581  INFO Memtable:266 - Writing Memtable-LocationInfo@664219194(126/157 serialized/live bytes, 3 ops)
19:59:23,582  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
19:59:23,803  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
19:59:23,819  INFO MessagingService:284 - Starting Messaging Service on port 7070
19:59:23,826  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
19:59:23,835  WARN StorageService:633 - Generated random token aK23LUbnRTvfoAfD. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
19:59:23,836  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1609377764(53/66 serialized/live bytes, 2 ops)
19:59:23,837  INFO Memtable:266 - Writing Memtable-LocationInfo@1609377764(53/66 serialized/live bytes, 2 ops)
19:59:24,050  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
19:59:24,055  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
19:59:24,064  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
19:59:24,065  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
19:59:24,098  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
19:59:24,101  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
19:59:24,105  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
19:59:24,105  INFO CassandraDaemon:212 - Listening for thrift clients...
19:59:26,014  INFO EmbeddedServerHelper:73 - Done sleeping
19:59:26,050  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
19:59:26,070  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
19:59:26,336  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.301 sec
19:59:26,357  INFO CassandraDaemon:218 - Stop listening to thrift clients
19:59:26,359  INFO MessagingService:539 - Waiting for messaging service to quiesce
19:59:26,360  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.GetSliceQueryTest
19:59:27,341  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
19:59:27,365  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
19:59:27,515  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
19:59:27,702  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
19:59:28,128  INFO EmbeddedServerHelper:66 - Starting executor
19:59:28,130  INFO EmbeddedServerHelper:69 - Started executor
19:59:28,130  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
19:59:28,130  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
19:59:28,130  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter437827643445756441.jar
19:59:28,134  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
19:59:28,149  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
19:59:28,164  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
19:59:28,165  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
19:59:28,169  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
19:59:28,296  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
19:59:28,297  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
19:59:28,339  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:28,347  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
19:59:28,349  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:28,351  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
19:59:28,370  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50170411269197.log
19:59:28,373  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50170411269197.log
19:59:28,374  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@881341271(78/97 serialized/live bytes, 2 ops)
19:59:28,375  INFO Memtable:266 - Writing Memtable-IndexInfo@881341271(78/97 serialized/live bytes, 2 ops)
19:59:28,377  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50170411269197.log
19:59:28,377  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
19:59:28,377  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
19:59:28,394  INFO StorageService:412 - Cassandra version: 1.1.0
19:59:28,394  INFO StorageService:413 - Thrift API version: 19.30.0
19:59:28,398  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
19:59:28,453  INFO StorageService:444 - Loading persisted ring state
19:59:28,457  INFO StorageService:525 - Starting up server gossip
19:59:28,466  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
19:59:28,675  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
19:59:28,677  INFO Memtable:266 - Writing Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
19:59:28,679  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
19:59:28,872  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
19:59:28,889  INFO MessagingService:284 - Starting Messaging Service on port 7070
19:59:28,901  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
19:59:28,910  WARN StorageService:633 - Generated random token aIAuKsZaKjD2BlH7. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
19:59:28,912  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1255545583(53/66 serialized/live bytes, 2 ops)
19:59:28,913  INFO Memtable:266 - Writing Memtable-LocationInfo@1255545583(53/66 serialized/live bytes, 2 ops)
19:59:29,144  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
19:59:29,148  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
19:59:29,161  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
19:59:29,162  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
19:59:29,195  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
19:59:29,198  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
19:59:29,202  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
19:59:29,202  INFO CassandraDaemon:212 - Listening for thrift clients...
19:59:31,130  INFO EmbeddedServerHelper:73 - Done sleeping
19:59:31,169  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
19:59:31,176  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
19:59:31,356  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.21 sec
19:59:31,376  INFO CassandraDaemon:218 - Stop listening to thrift clients
19:59:31,378  INFO MessagingService:539 - Waiting for messaging service to quiesce
19:59:31,378  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.MultigetCountQueryTest
19:59:32,334  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
19:59:32,367  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
19:59:32,574  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
19:59:32,733  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
19:59:33,188  INFO EmbeddedServerHelper:66 - Starting executor
19:59:33,189  INFO EmbeddedServerHelper:69 - Started executor
19:59:33,190  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
19:59:33,190  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
19:59:33,190  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter5243014047342300927.jar
19:59:33,194  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
19:59:33,210  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
19:59:33,224  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
19:59:33,225  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
19:59:33,229  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
19:59:33,353  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
19:59:33,354  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
19:59:33,396  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:33,404  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
19:59:33,406  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:33,408  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
19:59:33,425  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@378423301(39/48 serialized/live bytes, 1 ops)
19:59:33,426  INFO Memtable:266 - Writing Memtable-IndexInfo@378423301(39/48 serialized/live bytes, 1 ops)
19:59:33,434  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@628029189(39/48 serialized/live bytes, 1 ops)
19:59:33,434  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50175469346782.log
19:59:33,438  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50175469346782.log
19:59:33,442  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50175469346782.log
19:59:33,442  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
19:59:33,457  INFO StorageService:412 - Cassandra version: 1.1.0
19:59:33,457  INFO StorageService:413 - Thrift API version: 19.30.0
19:59:33,461  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
19:59:33,505  INFO StorageService:444 - Loading persisted ring state
19:59:33,508  INFO StorageService:525 - Starting up server gossip
19:59:33,521  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
19:59:34,186  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
19:59:34,189  INFO Memtable:266 - Writing Memtable-IndexInfo@628029189(39/48 serialized/live bytes, 1 ops)
19:59:34,190  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
19:59:34,410  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
19:59:34,412  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
19:59:34,412  INFO Memtable:266 - Writing Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
19:59:34,636  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
19:59:34,649  INFO MessagingService:284 - Starting Messaging Service on port 7070
19:59:34,655  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
19:59:34,664  WARN StorageService:633 - Generated random token zsd8yc52fQq73KNM. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
19:59:34,666  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2061551555(53/66 serialized/live bytes, 2 ops)
19:59:34,666  INFO Memtable:266 - Writing Memtable-LocationInfo@2061551555(53/66 serialized/live bytes, 2 ops)
19:59:34,893  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
19:59:34,898  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
19:59:34,909  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
19:59:34,910  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
19:59:34,943  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
19:59:34,946  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
19:59:34,950  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
19:59:34,951  INFO CassandraDaemon:212 - Listening for thrift clients...
19:59:36,190  INFO EmbeddedServerHelper:73 - Done sleeping
19:59:36,231  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
19:59:36,256  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
19:59:36,416  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.277 sec
19:59:36,444  INFO CassandraDaemon:218 - Stop listening to thrift clients
19:59:36,445  INFO MessagingService:539 - Waiting for messaging service to quiesce
19:59:36,446  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.CqlQueryTest
19:59:37,550  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
19:59:37,575  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
19:59:37,776  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
19:59:37,977  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
19:59:38,400  INFO EmbeddedServerHelper:66 - Starting executor
19:59:38,401  INFO EmbeddedServerHelper:69 - Started executor
19:59:38,401  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
19:59:38,401  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
19:59:38,402  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter9107648953760660151.jar
19:59:38,405  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
19:59:38,421  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
19:59:38,435  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
19:59:38,436  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
19:59:38,440  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
19:59:38,567  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
19:59:38,568  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
19:59:38,609  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:38,616  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
19:59:38,619  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:38,621  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
19:59:38,642  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50180682405700.log
19:59:38,644  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@628029189(78/97 serialized/live bytes, 2 ops)
19:59:38,645  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50180682405700.log
19:59:38,645  INFO Memtable:266 - Writing Memtable-IndexInfo@628029189(78/97 serialized/live bytes, 2 ops)
19:59:38,646  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
19:59:38,648  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50180682405700.log
19:59:38,648  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
19:59:38,662  INFO StorageService:412 - Cassandra version: 1.1.0
19:59:38,662  INFO StorageService:413 - Thrift API version: 19.30.0
19:59:38,666  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
19:59:38,718  INFO StorageService:444 - Loading persisted ring state
19:59:38,721  INFO StorageService:525 - Starting up server gossip
19:59:38,730  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
19:59:38,985  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
19:59:38,987  INFO Memtable:266 - Writing Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
19:59:38,989  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
19:59:39,217  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
19:59:39,232  INFO MessagingService:284 - Starting Messaging Service on port 7070
19:59:39,238  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
19:59:39,246  WARN StorageService:633 - Generated random token iLoakxXJXPHhPgvp. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
19:59:39,248  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1255545583(53/66 serialized/live bytes, 2 ops)
19:59:39,249  INFO Memtable:266 - Writing Memtable-LocationInfo@1255545583(53/66 serialized/live bytes, 2 ops)
19:59:39,475  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
19:59:39,480  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
19:59:39,493  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
19:59:39,494  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
19:59:39,526  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
19:59:39,530  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
19:59:39,534  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
19:59:39,534  INFO CassandraDaemon:212 - Listening for thrift clients...
19:59:41,401  INFO EmbeddedServerHelper:73 - Done sleeping
19:59:41,449  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
19:59:41,480  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
19:59:41,804  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.495 sec
19:59:41,825  INFO CassandraDaemon:218 - Stop listening to thrift clients
19:59:41,828  INFO MessagingService:539 - Waiting for messaging service to quiesce
19:59:41,830  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.RangeSlicesCounterQueryTest
19:59:42,839  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
19:59:42,863  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
19:59:43,057  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
19:59:43,234  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
19:59:43,658  INFO EmbeddedServerHelper:66 - Starting executor
19:59:43,659  INFO EmbeddedServerHelper:69 - Started executor
19:59:43,659  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
19:59:43,659  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
19:59:43,659  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter6681279619138952795.jar
19:59:43,663  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
19:59:43,678  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
19:59:43,692  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
19:59:43,693  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
19:59:43,697  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
19:59:43,818  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
19:59:43,819  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
19:59:43,862  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:43,870  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
19:59:43,872  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:43,874  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
19:59:43,897  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@192714409(78/97 serialized/live bytes, 2 ops)
19:59:43,897  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50185943391575.log
19:59:43,898  INFO Memtable:266 - Writing Memtable-IndexInfo@192714409(78/97 serialized/live bytes, 2 ops)
19:59:43,899  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
19:59:43,901  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50185943391575.log
19:59:43,905  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50185943391575.log
19:59:43,905  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
19:59:43,921  INFO StorageService:412 - Cassandra version: 1.1.0
19:59:43,921  INFO StorageService:413 - Thrift API version: 19.30.0
19:59:43,927  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
19:59:43,979  INFO StorageService:444 - Loading persisted ring state
19:59:43,982  INFO StorageService:525 - Starting up server gossip
19:59:43,992  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
19:59:44,173  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
19:59:44,175  INFO Memtable:266 - Writing Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
19:59:44,176  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
19:59:44,399  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
19:59:44,414  INFO MessagingService:284 - Starting Messaging Service on port 7070
19:59:44,420  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
19:59:44,430  WARN StorageService:633 - Generated random token myQlw5EE7bZVx0IX. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
19:59:44,432  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
19:59:44,432  INFO Memtable:266 - Writing Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
19:59:44,658  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
19:59:44,661  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
19:59:44,671  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
19:59:44,672  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
19:59:44,710  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
19:59:44,716  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
19:59:44,720  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
19:59:44,721  INFO CassandraDaemon:212 - Listening for thrift clients...
19:59:46,659  INFO EmbeddedServerHelper:73 - Done sleeping
19:59:46,707  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
19:59:46,740  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
19:59:46,879  INFO NodeId:200 - No saved local node id, using newly generated: 56598de0-e3de-11e1-0000-fe8ebeead9ff
19:59:46,964  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.329 sec
19:59:46,987  INFO CassandraDaemon:218 - Stop listening to thrift clients
19:59:46,989  INFO MessagingService:539 - Waiting for messaging service to quiesce
19:59:46,990  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.SuperColumnSliceTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.136 sec
Running me.prettyprint.cassandra.model.MultigetSliceQueryTest
19:59:48,389  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
19:59:48,414  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
19:59:48,601  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
19:59:48,748  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
19:59:49,167  INFO EmbeddedServerHelper:66 - Starting executor
19:59:49,169  INFO EmbeddedServerHelper:69 - Started executor
19:59:49,169  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
19:59:49,169  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
19:59:49,170  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter2310824578986454958.jar
19:59:49,173  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
19:59:49,192  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
19:59:49,207  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
19:59:49,208  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
19:59:49,212  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
19:59:49,338  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
19:59:49,339  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
19:59:49,381  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:49,388  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
19:59:49,391  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:49,393  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
19:59:49,414  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50191454206731.log
19:59:49,417  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@223489506(78/97 serialized/live bytes, 2 ops)
19:59:49,418  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50191454206731.log
19:59:49,418  INFO Memtable:266 - Writing Memtable-IndexInfo@223489506(78/97 serialized/live bytes, 2 ops)
19:59:49,419  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
19:59:49,421  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50191454206731.log
19:59:49,421  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
19:59:49,438  INFO StorageService:412 - Cassandra version: 1.1.0
19:59:49,438  INFO StorageService:413 - Thrift API version: 19.30.0
19:59:49,444  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
19:59:49,501  INFO StorageService:444 - Loading persisted ring state
19:59:49,504  INFO StorageService:525 - Starting up server gossip
19:59:49,514  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
19:59:49,713  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
19:59:49,716  INFO Memtable:266 - Writing Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
19:59:49,720  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
19:59:49,947  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
19:59:49,963  INFO MessagingService:284 - Starting Messaging Service on port 7070
19:59:49,970  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
19:59:49,979  WARN StorageService:633 - Generated random token 6GWa2uXVv0HmMb8G. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
19:59:49,981  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
19:59:49,981  INFO Memtable:266 - Writing Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
19:59:50,206  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
19:59:50,210  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
19:59:50,228  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
19:59:50,230  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
19:59:50,263  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
19:59:50,267  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
19:59:50,270  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
19:59:50,271  INFO CassandraDaemon:212 - Listening for thrift clients...
19:59:52,169  INFO EmbeddedServerHelper:73 - Done sleeping
19:59:52,206  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
19:59:52,235  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
19:59:52,420  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.273 sec
19:59:52,437  INFO CassandraDaemon:218 - Stop listening to thrift clients
19:59:52,440  INFO MessagingService:539 - Waiting for messaging service to quiesce
19:59:52,441  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.AbstractSliceQueryTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.397 sec
Running me.prettyprint.cassandra.model.IndexedSlicesQueryTest
19:59:54,120  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
19:59:54,144  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
19:59:54,329  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
19:59:54,489  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
19:59:54,921  INFO EmbeddedServerHelper:66 - Starting executor
19:59:54,922  INFO EmbeddedServerHelper:69 - Started executor
19:59:54,922  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
19:59:54,923  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
19:59:54,923  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter4155072486240450162.jar
19:59:54,926  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
19:59:54,944  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
19:59:54,959  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
19:59:54,960  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
19:59:54,964  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
19:59:55,103  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
19:59:55,103  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
19:59:55,144  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:55,153  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
19:59:55,161  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
19:59:55,164  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
19:59:55,185  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@192714409(78/97 serialized/live bytes, 2 ops)
19:59:55,188  INFO Memtable:266 - Writing Memtable-IndexInfo@192714409(78/97 serialized/live bytes, 2 ops)
19:59:55,188  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
19:59:55,193  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50197200480830.log
19:59:55,197  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50197200480830.log
19:59:55,201  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50197200480830.log
19:59:55,201  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
19:59:55,222  INFO StorageService:412 - Cassandra version: 1.1.0
19:59:55,222  INFO StorageService:413 - Thrift API version: 19.30.0
19:59:55,225  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
19:59:55,270  INFO StorageService:444 - Loading persisted ring state
19:59:55,273  INFO StorageService:525 - Starting up server gossip
19:59:55,283  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
19:59:55,478  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
19:59:55,480  INFO Memtable:266 - Writing Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
19:59:55,482  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
19:59:55,707  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
19:59:55,728  INFO MessagingService:284 - Starting Messaging Service on port 7070
19:59:55,734  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
19:59:55,743  WARN StorageService:633 - Generated random token zpalktkWfoQytn2l. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
19:59:55,745  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@780298059(53/66 serialized/live bytes, 2 ops)
19:59:55,745  INFO Memtable:266 - Writing Memtable-LocationInfo@780298059(53/66 serialized/live bytes, 2 ops)
19:59:55,965  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
19:59:55,969  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
19:59:55,981  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
19:59:55,982  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
19:59:56,015  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
19:59:56,018  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
19:59:56,022  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
19:59:56,023  INFO CassandraDaemon:212 - Listening for thrift clients...
19:59:57,922  INFO EmbeddedServerHelper:73 - Done sleeping
19:59:57,964  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
19:59:57,995  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
19:59:58,222  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.307 sec
19:59:58,241  INFO CassandraDaemon:218 - Stop listening to thrift clients
19:59:58,243  INFO MessagingService:539 - Waiting for messaging service to quiesce
19:59:58,244  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.HColumnFamilyTest
19:59:59,174  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
19:59:59,198  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
19:59:59,384  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
19:59:59,549  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
19:59:59,971  INFO EmbeddedServerHelper:66 - Starting executor
19:59:59,972  INFO EmbeddedServerHelper:69 - Started executor
19:59:59,972  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
19:59:59,973  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
19:59:59,973  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter8003811707582457792.jar
19:59:59,977  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
19:59:59,994  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:00:00,009  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:00:00,010  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:00:00,014  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:00:00,135  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:00:00,136  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:00:00,178  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:00,186  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:00:00,188  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:00,190  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:00:00,210  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50202258466932.log
20:00:00,212  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@221571972(78/97 serialized/live bytes, 2 ops)
20:00:00,213  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50202258466932.log
20:00:00,213  INFO Memtable:266 - Writing Memtable-IndexInfo@221571972(78/97 serialized/live bytes, 2 ops)
20:00:00,214  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:00:00,216  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50202258466932.log
20:00:00,217  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:00:00,232  INFO StorageService:412 - Cassandra version: 1.1.0
20:00:00,232  INFO StorageService:413 - Thrift API version: 19.30.0
20:00:00,235  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:00:00,293  INFO StorageService:444 - Loading persisted ring state
20:00:00,296  INFO StorageService:525 - Starting up server gossip
20:00:00,306  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
20:00:00,506  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
20:00:00,508  INFO Memtable:266 - Writing Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
20:00:00,509  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:00:00,721  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:00:00,736  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:00:00,742  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:00:00,751  WARN StorageService:633 - Generated random token fQ7ewfpHMd6YofFp. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:00:00,753  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1075051425(53/66 serialized/live bytes, 2 ops)
20:00:00,753  INFO Memtable:266 - Writing Memtable-LocationInfo@1075051425(53/66 serialized/live bytes, 2 ops)
20:00:00,979  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:00:00,984  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:00:00,996  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:00:00,997  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:00:01,030  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:00:01,033  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:00:01,037  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:00:01,037  INFO CassandraDaemon:212 - Listening for thrift clients...
20:00:02,973  INFO EmbeddedServerHelper:73 - Done sleeping
20:00:03,015  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:03,038  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
20:00:03,258  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.287 sec
20:00:03,281  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:00:03,284  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:00:03,284  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.MutatorTest
20:00:04,175  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
20:00:04,203  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:00:04,411  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:00:04,586  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:00:05,061  INFO EmbeddedServerHelper:66 - Starting executor
20:00:05,062  INFO EmbeddedServerHelper:69 - Started executor
20:00:05,062  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:00:05,063  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:00:05,063  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter7148192197184968419.jar
20:00:05,067  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:00:05,084  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:00:05,099  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:00:05,100  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:00:05,104  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:00:05,228  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:00:05,229  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:00:05,270  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:05,278  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:00:05,280  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:05,283  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:00:05,304  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50207344228604.log
20:00:05,308  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50207344228604.log
20:00:05,311  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1235930463(78/97 serialized/live bytes, 2 ops)
20:00:05,312  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50207344228604.log
20:00:05,313  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:00:05,313  INFO Memtable:266 - Writing Memtable-IndexInfo@1235930463(78/97 serialized/live bytes, 2 ops)
20:00:05,313  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:00:05,330  INFO StorageService:412 - Cassandra version: 1.1.0
20:00:05,330  INFO StorageService:413 - Thrift API version: 19.30.0
20:00:05,333  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:00:05,384  INFO StorageService:444 - Loading persisted ring state
20:00:05,388  INFO StorageService:525 - Starting up server gossip
20:00:05,399  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
20:00:05,570  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
20:00:05,572  INFO Memtable:266 - Writing Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
20:00:05,574  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:00:05,803  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:00:05,822  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:00:05,829  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:00:05,839  WARN StorageService:633 - Generated random token q4Wv7OTi2hvIJXR6. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:00:05,841  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@598834505(53/66 serialized/live bytes, 2 ops)
20:00:05,842  INFO Memtable:266 - Writing Memtable-LocationInfo@598834505(53/66 serialized/live bytes, 2 ops)
20:00:06,072  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:00:06,079  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:00:06,089  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:00:06,091  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:00:06,132  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:00:06,138  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:00:06,145  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:00:06,145  INFO CassandraDaemon:212 - Listening for thrift clients...
20:00:08,063  INFO EmbeddedServerHelper:73 - Done sleeping
20:00:08,098  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:08,118  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
20:00:08,394  INFO NodeId:200 - No saved local node id, using newly generated: 632c7b90-e3de-11e1-0000-fe8ebeead9d9
20:00:08,399  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.422 sec
20:00:08,417  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:00:08,418  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:00:08,419  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.ColumnSliceTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.105 sec
Running me.prettyprint.cassandra.connection.HConnectionManagerTest
20:00:10,121  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
20:00:10,145  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:00:10,292  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:00:10,452  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:00:10,880  INFO EmbeddedServerHelper:66 - Starting executor
20:00:10,882  INFO EmbeddedServerHelper:69 - Started executor
20:00:10,882  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:00:10,882  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:00:10,883  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter4331512993187865042.jar
20:00:10,886  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:00:10,910  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:00:10,923  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:00:10,924  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:00:10,927  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:00:11,050  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:00:11,050  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:00:11,094  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:11,102  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:00:11,106  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:11,108  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:00:11,133  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50213161695250.log
20:00:11,136  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50213161695250.log
20:00:11,137  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@2137552888(39/48 serialized/live bytes, 1 ops)
20:00:11,139  INFO Memtable:266 - Writing Memtable-IndexInfo@2137552888(39/48 serialized/live bytes, 1 ops)
20:00:11,143  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@628029189(39/48 serialized/live bytes, 1 ops)
20:00:11,144  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50213161695250.log
20:00:11,144  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:00:11,160  INFO StorageService:412 - Cassandra version: 1.1.0
20:00:11,160  INFO StorageService:413 - Thrift API version: 19.30.0
20:00:11,165  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:00:11,218  INFO StorageService:444 - Loading persisted ring state
20:00:11,223  INFO StorageService:525 - Starting up server gossip
20:00:11,245  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
20:00:11,517  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
20:00:11,520  INFO Memtable:266 - Writing Memtable-IndexInfo@628029189(39/48 serialized/live bytes, 1 ops)
20:00:11,521  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:00:11,739  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
20:00:11,741  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:00:11,741  INFO Memtable:266 - Writing Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
20:00:11,965  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:00:11,986  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:00:11,994  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:00:12,003  WARN StorageService:633 - Generated random token YZV6qSj5TrqwDQf3. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:00:12,005  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@780298059(53/66 serialized/live bytes, 2 ops)
20:00:12,005  INFO Memtable:266 - Writing Memtable-LocationInfo@780298059(53/66 serialized/live bytes, 2 ops)
20:00:12,234  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:00:12,239  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:00:12,252  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:00:12,253  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:00:12,285  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:00:12,289  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:00:12,293  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:00:12,293  INFO CassandraDaemon:212 - Listening for thrift clients...
20:00:13,882  INFO EmbeddedServerHelper:73 - Done sleeping
20:00:13,918  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:13,940  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
20:00:13,944  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:13,948  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:13,949  INFO HConnectionManager:172 - Remove status for CassandraHost pool 127.0.0.1(127.0.0.1):9170 was true
20:00:13,957  INFO HConnectionManager:114 - Added host 127.0.0.1(127.0.0.1):9170 to pool
20:00:13,957 ERROR HConnectionManager:421 - MARK HOST AS DOWN TRIGGERED for host 127.0.0.1(127.0.0.1):9170
20:00:13,958 ERROR HConnectionManager:425 - Pool state on shutdown: <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}; IsActive?: true; Active: 0; Blocked: 0; Idle: 16; NumBeforeExhausted: 50
20:00:13,958  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:13,960  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:13,961  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
20:00:13,962  INFO HConnectionManager:165 - Host 127.0.0.1(127.0.0.1):9170 not in active pools, but found in retry service.
20:00:13,973  INFO HConnectionManager:172 - Remove status for CassandraHost pool 127.0.0.1(127.0.0.1):9170 was true
20:00:13,981  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:13,992 ERROR HConnectionManager:119 - Transport exception host to HConnectionManager: 127.0.0.1(127.0.0.1):9180
me.prettyprint.hector.api.exceptions.HectorTransportException: Unable to open transport to 127.0.0.1(127.0.0.1):9180 , java.net.ConnectException: Connection refused
	at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:144)
	at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:26)
	at me.prettyprint.cassandra.connection.ConcurrentHClientPool.createClient(ConcurrentHClientPool.java:147)
	at me.prettyprint.cassandra.connection.ConcurrentHClientPool.<init>(ConcurrentHClientPool.java:53)
	at me.prettyprint.cassandra.connection.RoundRobinBalancingPolicy.createConnection(RoundRobinBalancingPolicy.java:67)
	at me.prettyprint.cassandra.connection.HConnectionManager.addCassandraHost(HConnectionManager.java:112)
	at me.prettyprint.cassandra.connection.HConnectionManagerTest.testAddCassandraHostFail(HConnectionManagerTest.java:37)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
	at java.lang.reflect.Method.invoke(Method.java:597)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:76)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:28)
	at org.junit.internal.runners.statements.RunAfters.evaluate(RunAfters.java:31)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
	at org.apache.maven.surefire.junit4.JUnit4TestSet.execute(JUnit4TestSet.java:35)
	at org.apache.maven.surefire.junit4.JUnit4Provider.executeTestSet(JUnit4Provider.java:146)
	at org.apache.maven.surefire.junit4.JUnit4Provider.invoke(JUnit4Provider.java:97)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
	at java.lang.reflect.Method.invoke(Method.java:597)
	at org.apache.maven.surefire.booter.ProviderFactory$ClassLoaderProxy.invoke(ProviderFactory.java:103)
	at $Proxy0.invoke(Unknown Source)
	at org.apache.maven.surefire.booter.SurefireStarter.invokeProvider(SurefireStarter.java:145)
	at org.apache.maven.surefire.booter.SurefireStarter.runSuitesInProcess(SurefireStarter.java:70)
	at org.apache.maven.surefire.booter.ForkedBooter.main(ForkedBooter.java:69)
Caused by: org.apache.thrift.transport.TTransportException: java.net.ConnectException: Connection refused
	at org.apache.thrift.transport.TSocket.open(TSocket.java:183)
	at org.apache.thrift.transport.TFramedTransport.open(TFramedTransport.java:81)
	at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:138)
	... 36 more
Caused by: java.net.ConnectException: Connection refused
	at java.net.PlainSocketImpl.socketConnect(Native Method)
	at java.net.PlainSocketImpl.doConnect(PlainSocketImpl.java:351)
	at java.net.PlainSocketImpl.connectToAddress(PlainSocketImpl.java:213)
	at java.net.PlainSocketImpl.connect(PlainSocketImpl.java:200)
	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:366)
	at java.net.Socket.connect(Socket.java:529)
	at org.apache.thrift.transport.TSocket.open(TSocket.java:178)
	... 38 more
20:00:14,003  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:14,008  INFO HConnectionManager:114 - Added host 127.0.0.1(127.0.0.1):9170 to pool
20:00:14,011 ERROR HConnectionManager:421 - MARK HOST AS DOWN TRIGGERED for host 127.0.0.1(127.0.0.1):9170
20:00:14,011 ERROR HConnectionManager:425 - Pool state on shutdown: <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}; IsActive?: true; Active: 1; Blocked: 0; Idle: 15; NumBeforeExhausted: 49
20:00:14,012  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:14,012  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:14,014  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:14,019  INFO HConnectionManager:191 - Suspend operation status was true for CassandraHost 127.0.0.1(127.0.0.1):9170
20:00:14,019  INFO HConnectionManager:212 - UN-Suspend operation status was true for CassandraHost 127.0.0.1(127.0.0.1):9170
20:00:14,021  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:14,033  WARN HConnectionManager:306 - Could not fullfill request on this host CassandraClient<127.0.0.1:9170-99>
20:00:14,033  WARN HConnectionManager:307 - Exception: 
me.prettyprint.hector.api.exceptions.HTimedOutException: fake timeout
	at me.prettyprint.cassandra.connection.HConnectionManagerTest$TimeoutOp.execute(HConnectionManagerTest.java:92)
	at me.prettyprint.cassandra.connection.HConnectionManagerTest$TimeoutOp.execute(HConnectionManagerTest.java:84)
	at me.prettyprint.cassandra.service.Operation.executeAndSetResult(Operation.java:103)
	at me.prettyprint.cassandra.connection.HConnectionManager.operateWithFailover(HConnectionManager.java:258)
	at me.prettyprint.cassandra.connection.HConnectionManagerTest.testTimedOutOperateWithFailover(HConnectionManagerTest.java:69)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
	at java.lang.reflect.Method.invoke(Method.java:597)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
	at org.junit.internal.runners.statements.ExpectException.evaluate(ExpectException.java:21)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:76)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:28)
	at org.junit.internal.runners.statements.RunAfters.evaluate(RunAfters.java:31)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
	at org.apache.maven.surefire.junit4.JUnit4TestSet.execute(JUnit4TestSet.java:35)
	at org.apache.maven.surefire.junit4.JUnit4Provider.executeTestSet(JUnit4Provider.java:146)
	at org.apache.maven.surefire.junit4.JUnit4Provider.invoke(JUnit4Provider.java:97)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
	at java.lang.reflect.Method.invoke(Method.java:597)
	at org.apache.maven.surefire.booter.ProviderFactory$ClassLoaderProxy.invoke(ProviderFactory.java:103)
	at $Proxy0.invoke(Unknown Source)
	at org.apache.maven.surefire.booter.SurefireStarter.invokeProvider(SurefireStarter.java:145)
	at org.apache.maven.surefire.booter.SurefireStarter.runSuitesInProcess(SurefireStarter.java:70)
	at org.apache.maven.surefire.booter.ForkedBooter.main(ForkedBooter.java:69)
20:00:14,036  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.113 sec
20:00:14,077  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:00:14,078  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:00:14,079  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.connection.ConcurrentHClientPoolTest
20:00:15,028  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
20:00:15,053  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:00:15,227  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:00:15,392  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:00:15,832  INFO EmbeddedServerHelper:66 - Starting executor
20:00:15,833  INFO EmbeddedServerHelper:69 - Started executor
20:00:15,834  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:00:15,834  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:00:15,834  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter6213983939558856665.jar
20:00:15,838  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:00:15,858  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:00:15,871  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:00:15,872  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:00:15,875  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:00:15,998  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:00:15,999  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:00:16,045  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:16,053  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:00:16,057  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:16,060  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:00:16,083  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50218120242331.log
20:00:16,086  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50218120242331.log
20:00:16,097  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1508028338(78/97 serialized/live bytes, 2 ops)
20:00:16,098  INFO Memtable:266 - Writing Memtable-IndexInfo@1508028338(78/97 serialized/live bytes, 2 ops)
20:00:16,099  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50218120242331.log
20:00:16,099  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:00:16,099  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:00:16,113  INFO StorageService:412 - Cassandra version: 1.1.0
20:00:16,114  INFO StorageService:413 - Thrift API version: 19.30.0
20:00:16,118  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:00:16,171  INFO StorageService:444 - Loading persisted ring state
20:00:16,175  INFO StorageService:525 - Starting up server gossip
20:00:16,184  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
20:00:16,401  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
20:00:16,403  INFO Memtable:266 - Writing Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
20:00:16,405  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:00:16,635  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:00:16,655  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:00:16,661  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:00:16,669  WARN StorageService:633 - Generated random token A7buN8dKBZdLQKLs. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:00:16,671  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
20:00:16,672  INFO Memtable:266 - Writing Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
20:00:16,881  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:00:16,885  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:00:16,897  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:00:16,899  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:00:16,942  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:00:16,948  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:00:16,955  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:00:16,955  INFO CassandraDaemon:212 - Listening for thrift clients...
20:00:18,834  INFO EmbeddedServerHelper:73 - Done sleeping
20:00:18,865  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:18,884  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
20:00:18,920  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:18,935  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:18,936  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:18,938  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:18,953  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.14 sec
20:00:18,992  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:00:18,993  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:00:18,994  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.connection.HConnectionManagerListenerTest
20:00:19,951  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
20:00:19,975  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:00:20,183  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:00:20,366  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:00:20,809  INFO EmbeddedServerHelper:66 - Starting executor
20:00:20,811  INFO EmbeddedServerHelper:69 - Started executor
20:00:20,811  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:00:20,811  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:00:20,811  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter227784821238499349.jar
20:00:20,815  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:00:20,829  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:00:20,842  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:00:20,843  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:00:20,847  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:00:20,975  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:00:20,975  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:00:21,017  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:21,025  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:00:21,027  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:21,029  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:00:21,051  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50223093123279.log
20:00:21,053  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1989766346(78/97 serialized/live bytes, 2 ops)
20:00:21,054  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50223093123279.log
20:00:21,055  INFO Memtable:266 - Writing Memtable-IndexInfo@1989766346(78/97 serialized/live bytes, 2 ops)
20:00:21,055  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:00:21,058  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50223093123279.log
20:00:21,058  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:00:21,074  INFO StorageService:412 - Cassandra version: 1.1.0
20:00:21,074  INFO StorageService:413 - Thrift API version: 19.30.0
20:00:21,078  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:00:21,129  INFO StorageService:444 - Loading persisted ring state
20:00:21,132  INFO StorageService:525 - Starting up server gossip
20:00:21,142  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@277818534(126/157 serialized/live bytes, 3 ops)
20:00:21,347  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
20:00:21,349  INFO Memtable:266 - Writing Memtable-LocationInfo@277818534(126/157 serialized/live bytes, 3 ops)
20:00:21,350  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:00:21,570  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:00:21,588  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:00:21,594  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:00:21,603  WARN StorageService:633 - Generated random token a7F3nGhbH2euEqtc. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:00:21,605  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1623247029(53/66 serialized/live bytes, 2 ops)
20:00:21,605  INFO Memtable:266 - Writing Memtable-LocationInfo@1623247029(53/66 serialized/live bytes, 2 ops)
20:00:21,829  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:00:21,834  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:00:21,851  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:00:21,852  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:00:21,886  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:00:21,890  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:00:21,895  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:00:21,895  INFO CassandraDaemon:212 - Listening for thrift clients...
20:00:23,811  INFO EmbeddedServerHelper:73 - Done sleeping
20:00:23,846  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:23,874  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
20:00:23,879  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:23,883  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:23,883  INFO HConnectionManager:172 - Remove status for CassandraHost pool 127.0.0.1(127.0.0.1):9170 was true
20:00:23,888  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:23,906 ERROR HConnectionManager:119 - Transport exception host to HConnectionManager: 127.0.0.1(127.0.0.1):9180
me.prettyprint.hector.api.exceptions.HectorTransportException: Unable to open transport to 127.0.0.1(127.0.0.1):9180 , java.net.ConnectException: Connection refused
	at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:144)
	at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:26)
	at me.prettyprint.cassandra.connection.ConcurrentHClientPool.createClient(ConcurrentHClientPool.java:147)
	at me.prettyprint.cassandra.connection.ConcurrentHClientPool.<init>(ConcurrentHClientPool.java:53)
	at me.prettyprint.cassandra.connection.RoundRobinBalancingPolicy.createConnection(RoundRobinBalancingPolicy.java:67)
	at me.prettyprint.cassandra.connection.HConnectionManager.addCassandraHost(HConnectionManager.java:112)
	at me.prettyprint.cassandra.connection.HConnectionManagerListenerTest.testOnAddCassandraHostFail(HConnectionManagerListenerTest.java:47)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
	at java.lang.reflect.Method.invoke(Method.java:597)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:76)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:28)
	at org.junit.internal.runners.statements.RunAfters.evaluate(RunAfters.java:31)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
	at org.apache.maven.surefire.junit4.JUnit4TestSet.execute(JUnit4TestSet.java:35)
	at org.apache.maven.surefire.junit4.JUnit4Provider.executeTestSet(JUnit4Provider.java:146)
	at org.apache.maven.surefire.junit4.JUnit4Provider.invoke(JUnit4Provider.java:97)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
	at java.lang.reflect.Method.invoke(Method.java:597)
	at org.apache.maven.surefire.booter.ProviderFactory$ClassLoaderProxy.invoke(ProviderFactory.java:103)
	at $Proxy0.invoke(Unknown Source)
	at org.apache.maven.surefire.booter.SurefireStarter.invokeProvider(SurefireStarter.java:145)
	at org.apache.maven.surefire.booter.SurefireStarter.runSuitesInProcess(SurefireStarter.java:70)
	at org.apache.maven.surefire.booter.ForkedBooter.main(ForkedBooter.java:69)
Caused by: org.apache.thrift.transport.TTransportException: java.net.ConnectException: Connection refused
	at org.apache.thrift.transport.TSocket.open(TSocket.java:183)
	at org.apache.thrift.transport.TFramedTransport.open(TFramedTransport.java:81)
	at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:138)
	... 36 more
Caused by: java.net.ConnectException: Connection refused
	at java.net.PlainSocketImpl.socketConnect(Native Method)
	at java.net.PlainSocketImpl.doConnect(PlainSocketImpl.java:351)
	at java.net.PlainSocketImpl.connectToAddress(PlainSocketImpl.java:213)
	at java.net.PlainSocketImpl.connect(PlainSocketImpl.java:200)
	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:366)
	at java.net.Socket.connect(Socket.java:529)
	at org.apache.thrift.transport.TSocket.open(TSocket.java:178)
	... 38 more
20:00:23,913  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:23,924  INFO HConnectionManager:128 - Host already existed for pool 127.0.0.1(127.0.0.1):9170
20:00:23,925  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:23,936  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:23,939  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:23,939  INFO HConnectionManager:172 - Remove status for CassandraHost pool 127.0.0.1(127.0.0.1):9170 was true
20:00:23,944  INFO HConnectionManager:114 - Added host 127.0.0.1(127.0.0.1):9170 to pool
20:00:23,945  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:23,951 ERROR HConnectionManager:421 - MARK HOST AS DOWN TRIGGERED for host 127.0.0.1(127.0.0.1):9170
20:00:23,952 ERROR HConnectionManager:425 - Pool state on shutdown: <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}; IsActive?: true; Active: 0; Blocked: 0; Idle: 16; NumBeforeExhausted: 50
20:00:23,952  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:23,960  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:23,960  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
20:00:23,963  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 1s
20:00:23,970 ERROR HConnectionManager:421 - MARK HOST AS DOWN TRIGGERED for host 127.0.0.1(127.0.0.1):9170
20:00:23,970 ERROR HConnectionManager:425 - Pool state on shutdown: <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}; IsActive?: true; Active: 0; Blocked: 0; Idle: 16; NumBeforeExhausted: 50
20:00:23,971  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:23,975  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
20:00:23,976  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
20:00:23,988  INFO HConnectionManager:114 - Added host 127.0.0.1(127.0.0.1):9170 to pool
20:00:23,997  INFO HConnectionManager:114 - Added host 127.0.0.1(127.0.0.1):9170 to pool
20:00:25,079  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:25,088  INFO HConnectionManager:191 - Suspend operation status was true for CassandraHost 127.0.0.1(127.0.0.1):9170
20:00:25,097  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:25,105  INFO HConnectionManager:191 - Suspend operation status was true for CassandraHost 127.0.0.1(127.0.0.1):9170
20:00:25,106  INFO HConnectionManager:212 - UN-Suspend operation status was true for CassandraHost 127.0.0.1(127.0.0.1):9170
20:00:25,107  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.353 sec
20:00:25,150  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:00:25,151  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:00:25,152  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.connection.LeastActiveBalancingPolicyTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.648 sec
Running me.prettyprint.cassandra.connection.RoundRobinBalancingPolicyTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.524 sec
Running me.prettyprint.cassandra.connection.DynamicBalancingPolicyTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.493 sec
Running me.prettyprint.cassandra.connection.HostTimeoutTrackerTest
20:00:33,658  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:33,688 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
20:00:33,688  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
20:00:33,690  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
20:00:33,737  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
20:00:34,244  INFO HConnectionManager:191 - Suspend operation status was false for CassandraHost localhost(127.0.0.1):9170
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.811 sec
Running me.prettyprint.cassandra.connection.LatencyAwareHClientPoolTest
20:00:34,692  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
20:00:34,716  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:00:34,902  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:00:35,062  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:00:35,497  INFO EmbeddedServerHelper:66 - Starting executor
20:00:35,498  INFO EmbeddedServerHelper:69 - Started executor
20:00:35,498  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:00:35,499  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:00:35,499  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter4648606090813458809.jar
20:00:35,502  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:00:35,517  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:00:35,532  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:00:35,533  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:00:35,537  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:00:35,669  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:00:35,670  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:00:35,711  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:35,719  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:00:35,721  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:35,723  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:00:35,742  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1461341140(39/48 serialized/live bytes, 1 ops)
20:00:35,743  INFO Memtable:266 - Writing Memtable-IndexInfo@1461341140(39/48 serialized/live bytes, 1 ops)
20:00:35,744  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50237780662847.log
20:00:35,750  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@404454518(39/48 serialized/live bytes, 1 ops)
20:00:35,750  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50237780662847.log
20:00:35,754  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50237780662847.log
20:00:35,755  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:00:35,768  INFO StorageService:412 - Cassandra version: 1.1.0
20:00:35,768  INFO StorageService:413 - Thrift API version: 19.30.0
20:00:35,772  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:00:35,827  INFO StorageService:444 - Loading persisted ring state
20:00:35,831  INFO StorageService:525 - Starting up server gossip
20:00:35,852  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
20:00:36,026  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
20:00:36,029  INFO Memtable:266 - Writing Memtable-IndexInfo@404454518(39/48 serialized/live bytes, 1 ops)
20:00:36,030  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:00:36,252  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
20:00:36,254  INFO Memtable:266 - Writing Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
20:00:36,255  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:00:36,477  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:00:36,490  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:00:36,496  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:00:36,505  WARN StorageService:633 - Generated random token dOj4ioAtvwb0IRid. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:00:36,507  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1934329031(53/66 serialized/live bytes, 2 ops)
20:00:36,508  INFO Memtable:266 - Writing Memtable-LocationInfo@1934329031(53/66 serialized/live bytes, 2 ops)
20:00:36,737  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:00:36,742  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:00:36,757  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:00:36,758  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:00:36,791  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:00:36,795  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:00:36,799  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:00:36,799  INFO CassandraDaemon:212 - Listening for thrift clients...
20:00:38,499  INFO EmbeddedServerHelper:73 - Done sleeping
20:00:38,531  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:38,553  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
0.1978148891795742
20:00:38,580  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:38,593  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
2.11413685380516
2.813508101786223
0.24857623389712308
20:00:38,607  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.116 sec
20:00:38,643  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:00:38,644  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:00:38,644  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.connection.HThriftClientTest
20:00:39,618  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
20:00:39,641  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:00:39,788  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:00:39,957  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:00:40,391  INFO EmbeddedServerHelper:66 - Starting executor
20:00:40,393  INFO EmbeddedServerHelper:69 - Started executor
20:00:40,393  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:00:40,394  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:00:40,394  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter752904423273698196.jar
20:00:40,397  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:00:40,415  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:00:40,428  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:00:40,429  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:00:40,433  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:00:40,555  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:00:40,556  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:00:40,597  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:40,604  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:00:40,607  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:40,609  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:00:40,631  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50242678034861.log
20:00:40,634  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1387626138(39/48 serialized/live bytes, 1 ops)
20:00:40,637  INFO Memtable:266 - Writing Memtable-IndexInfo@1387626138(39/48 serialized/live bytes, 1 ops)
20:00:40,641  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50242678034861.log
20:00:40,643  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@81035498(39/48 serialized/live bytes, 1 ops)
20:00:40,648  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50242678034861.log
20:00:40,648  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:00:40,664  INFO StorageService:412 - Cassandra version: 1.1.0
20:00:40,664  INFO StorageService:413 - Thrift API version: 19.30.0
20:00:40,670  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:00:40,715  INFO StorageService:444 - Loading persisted ring state
20:00:40,718  INFO StorageService:525 - Starting up server gossip
20:00:40,731  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
20:00:40,910  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
20:00:40,912  INFO Memtable:266 - Writing Memtable-IndexInfo@81035498(39/48 serialized/live bytes, 1 ops)
20:00:40,913  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:00:41,137  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
20:00:41,139  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:00:41,139  INFO Memtable:266 - Writing Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
20:00:41,363  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:00:41,379  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:00:41,385  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:00:41,393  WARN StorageService:633 - Generated random token NNhrS6FblN7hgzQ9. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:00:41,395  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@598834505(53/66 serialized/live bytes, 2 ops)
20:00:41,396  INFO Memtable:266 - Writing Memtable-LocationInfo@598834505(53/66 serialized/live bytes, 2 ops)
20:00:41,621  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:00:41,625  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:00:41,640  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:00:41,641  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:00:41,674  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:00:41,677  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:00:41,681  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:00:41,682  INFO CassandraDaemon:212 - Listening for thrift clients...
20:00:43,393  INFO EmbeddedServerHelper:73 - Done sleeping
20:00:43,490  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:43,514  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
20:00:43,582  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1197082559(191/238 serialized/live bytes, 4 ops)
20:00:43,583  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1197082559(191/238 serialized/live bytes, 4 ops)
20:00:44,112  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-1-Data.db (254 bytes)
20:00:44,197  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@948129019(193/241 serialized/live bytes, 4 ops)
20:00:44,198  INFO Memtable:266 - Writing Memtable-schema_keyspaces@948129019(193/241 serialized/live bytes, 4 ops)
20:00:44,426  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-2-Data.db (258 bytes)
20:00:44,438  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.022 sec
20:00:44,466  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:00:44,468  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:00:44,469  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.io.StreamTest
20:00:45,388  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
20:00:45,411  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:00:45,560  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:00:45,715  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:00:46,141  INFO EmbeddedServerHelper:66 - Starting executor
20:00:46,142  INFO EmbeddedServerHelper:69 - Started executor
20:00:46,142  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:00:46,143  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:00:46,143  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3276519032536152252.jar
20:00:46,146  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:00:46,162  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:00:46,175  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:00:46,176  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:00:46,180  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:00:46,306  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:00:46,307  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:00:46,348  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:46,356  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:00:46,358  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:46,360  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:00:46,382  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50248423964050.log
20:00:46,383  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@221571972(78/97 serialized/live bytes, 2 ops)
20:00:46,384  INFO Memtable:266 - Writing Memtable-IndexInfo@221571972(78/97 serialized/live bytes, 2 ops)
20:00:46,385  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50248423964050.log
20:00:46,385  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:00:46,389  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50248423964050.log
20:00:46,389  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:00:46,411  INFO StorageService:412 - Cassandra version: 1.1.0
20:00:46,411  INFO StorageService:413 - Thrift API version: 19.30.0
20:00:46,417  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:00:46,462  INFO StorageService:444 - Loading persisted ring state
20:00:46,466  INFO StorageService:525 - Starting up server gossip
20:00:46,475  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@664219194(126/157 serialized/live bytes, 3 ops)
20:00:46,680  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
20:00:46,683  INFO Memtable:266 - Writing Memtable-LocationInfo@664219194(126/157 serialized/live bytes, 3 ops)
20:00:46,684  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:00:46,904  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:00:46,918  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:00:46,925  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:00:46,934  WARN StorageService:633 - Generated random token W1S3FYBSKYeqD7DI. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:00:46,936  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1075051425(53/66 serialized/live bytes, 2 ops)
20:00:46,936  INFO Memtable:266 - Writing Memtable-LocationInfo@1075051425(53/66 serialized/live bytes, 2 ops)
20:00:47,153  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:00:47,158  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:00:47,172  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:00:47,173  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:00:47,207  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:00:47,210  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:00:47,214  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:00:47,214  INFO CassandraDaemon:212 - Listening for thrift clients...
20:00:49,142  INFO EmbeddedServerHelper:73 - Done sleeping
20:00:49,187  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:49,210  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
20:00:49,235  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:49,261  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
20:00:49,611  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@2121904503(190/237 serialized/live bytes, 4 ops)
20:00:49,612  INFO Memtable:266 - Writing Memtable-schema_keyspaces@2121904503(190/237 serialized/live bytes, 4 ops)
20:00:49,833  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-1-Data.db (252 bytes)
20:00:49,835  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@2095180519(1140/1425 serialized/live bytes, 20 ops)
20:00:49,835  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@2095180519(1140/1425 serialized/live bytes, 20 ops)
20:00:50,058  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-1-Data.db (1210 bytes)
20:00:50,374  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:50,387  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:50,400  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@584004921(0/0 serialized/live bytes, 1 ops)
20:00:50,400  INFO Memtable:266 - Writing Memtable-schema_keyspaces@584004921(0/0 serialized/live bytes, 1 ops)
20:00:50,650  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-2-Data.db (62 bytes)
20:00:50,651  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1064486011(0/0 serialized/live bytes, 1 ops)
20:00:50,652  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1064486011(0/0 serialized/live bytes, 1 ops)
20:00:50,876  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-2-Data.db (62 bytes)
20:00:50,878  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@739528893(0/0 serialized/live bytes, 1 ops)
20:00:50,879  INFO Memtable:266 - Writing Memtable-schema_columns@739528893(0/0 serialized/live bytes, 1 ops)
20:00:51,102  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-1-Data.db (62 bytes)
20:00:51,120  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-Blob@1207715470(795/993 serialized/live bytes, 26 ops)
20:00:51,121  INFO Memtable:266 - Writing Memtable-Blob@1207715470(795/993 serialized/live bytes, 26 ops)
20:00:51,306  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/TestKeyspace/Blob/TestKeyspace-Blob-hc-1-Data.db (262 bytes)
20:00:51,355  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1288040975(190/237 serialized/live bytes, 4 ops)
20:00:51,356  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1288040975(190/237 serialized/live bytes, 4 ops)
20:00:51,584  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-3-Data.db (252 bytes)
20:00:51,585  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1909686955(1140/1425 serialized/live bytes, 20 ops)
20:00:51,586  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1909686955(1140/1425 serialized/live bytes, 20 ops)
20:00:51,777  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-3-Data.db (1210 bytes)
20:00:51,825  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 6.632 sec
20:00:51,844  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:00:51,846  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:00:51,846  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.examples.ExampleDaoV2Test
20:00:52,743  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:00:52,896  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:00:53,053  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:00:53,481  INFO EmbeddedServerHelper:66 - Starting executor
20:00:53,482  INFO EmbeddedServerHelper:69 - Started executor
20:00:53,483  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:00:53,483  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:00:53,483  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3090549027593224167.jar
20:00:53,487  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:00:53,503  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:00:53,517  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:00:53,518  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:00:53,522  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:00:53,649  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:00:53,650  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:00:53,689  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:53,699  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:00:53,701  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:53,703  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:00:53,730  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1524063303(78/97 serialized/live bytes, 2 ops)
20:00:53,732  INFO Memtable:266 - Writing Memtable-IndexInfo@1524063303(78/97 serialized/live bytes, 2 ops)
20:00:53,732  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:00:53,737  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50255763639708.log
20:00:53,740  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50255763639708.log
20:00:53,743  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50255763639708.log
20:00:53,743  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:00:53,757  INFO StorageService:412 - Cassandra version: 1.1.0
20:00:53,757  INFO StorageService:413 - Thrift API version: 19.30.0
20:00:53,763  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:00:53,808  INFO StorageService:444 - Loading persisted ring state
20:00:53,811  INFO StorageService:525 - Starting up server gossip
20:00:53,821  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1685580695(126/157 serialized/live bytes, 3 ops)
20:00:54,022  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
20:00:54,023  INFO Memtable:266 - Writing Memtable-LocationInfo@1685580695(126/157 serialized/live bytes, 3 ops)
20:00:54,025  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:00:54,245  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:00:54,264  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:00:54,270  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:00:54,279  WARN StorageService:633 - Generated random token lL0iVJUk9yHWkCzh. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:00:54,281  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1987744292(53/66 serialized/live bytes, 2 ops)
20:00:54,281  INFO Memtable:266 - Writing Memtable-LocationInfo@1987744292(53/66 serialized/live bytes, 2 ops)
20:00:54,492  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:00:54,498  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:00:54,514  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:00:54,515  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:00:54,547  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:00:54,551  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:00:54,554  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:00:54,555  INFO CassandraDaemon:212 - Listening for thrift clients...
20:00:56,483  INFO EmbeddedServerHelper:73 - Done sleeping
20:00:56,525  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:00:56,547  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
20:00:56,764  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.227 sec
20:00:56,788  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:00:56,790  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:00:56,792  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.CassandraAuthTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.037 sec
Running me.prettyprint.cassandra.service.template.SuperCfTemplateTest
20:00:57,982  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
20:00:58,008  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:00:58,205  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:00:58,388  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:00:58,822  INFO EmbeddedServerHelper:66 - Starting executor
20:00:58,824  INFO EmbeddedServerHelper:69 - Started executor
20:00:58,824  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:00:58,824  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:00:58,824  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter1811373537426631946.jar
20:00:58,828  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:00:58,844  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:00:58,857  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:00:58,858  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:00:58,862  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:00:58,984  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:00:58,985  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:00:59,026  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:59,035  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:00:59,041  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:00:59,044  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:00:59,069  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@545732387(78/97 serialized/live bytes, 2 ops)
20:00:59,070  INFO Memtable:266 - Writing Memtable-IndexInfo@545732387(78/97 serialized/live bytes, 2 ops)
20:00:59,071  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:00:59,076  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50261105813373.log
20:00:59,078  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50261105813373.log
20:00:59,081  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50261105813373.log
20:00:59,082  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:00:59,099  INFO StorageService:412 - Cassandra version: 1.1.0
20:00:59,099  INFO StorageService:413 - Thrift API version: 19.30.0
20:00:59,104  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:00:59,160  INFO StorageService:444 - Loading persisted ring state
20:00:59,164  INFO StorageService:525 - Starting up server gossip
20:00:59,176  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
20:00:59,339  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
20:00:59,341  INFO Memtable:266 - Writing Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
20:00:59,342  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:00:59,571  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:00:59,588  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:00:59,597  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:00:59,610  WARN StorageService:633 - Generated random token ECb15a7z6JL4Cn1M. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:00:59,613  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1934329031(53/66 serialized/live bytes, 2 ops)
20:00:59,614  INFO Memtable:266 - Writing Memtable-LocationInfo@1934329031(53/66 serialized/live bytes, 2 ops)
20:00:59,840  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:00:59,843  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:00:59,856  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:00:59,857  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:00:59,890  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:00:59,894  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:00:59,898  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:00:59,898  INFO CassandraDaemon:212 - Listening for thrift clients...
20:01:01,824  INFO EmbeddedServerHelper:73 - Done sleeping
20:01:01,861  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:01,889  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
20:01:02,183  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 13, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.411 sec
20:01:02,211  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:01:02,213  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:01:02,214  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.template.ColumnFamilyTemplateTest
20:01:03,129  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
20:01:03,152  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:01:03,323  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:01:03,499  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:01:03,924  INFO EmbeddedServerHelper:66 - Starting executor
20:01:03,925  INFO EmbeddedServerHelper:69 - Started executor
20:01:03,925  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:01:03,925  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:01:03,926  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter4219661219493530999.jar
20:01:03,929  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:01:03,944  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:01:03,957  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:01:03,958  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:01:03,962  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:01:04,085  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:01:04,086  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:01:04,127  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:01:04,134  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:01:04,137  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:01:04,139  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:01:04,156  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1246777025(39/48 serialized/live bytes, 1 ops)
20:01:04,158  INFO Memtable:266 - Writing Memtable-IndexInfo@1246777025(39/48 serialized/live bytes, 1 ops)
20:01:04,164  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@881341271(39/48 serialized/live bytes, 1 ops)
20:01:04,165  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50266207486243.log
20:01:04,168  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50266207486243.log
20:01:04,171  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50266207486243.log
20:01:04,172  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:01:04,188  INFO StorageService:412 - Cassandra version: 1.1.0
20:01:04,188  INFO StorageService:413 - Thrift API version: 19.30.0
20:01:04,191  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:01:04,234  INFO StorageService:444 - Loading persisted ring state
20:01:04,237  INFO StorageService:525 - Starting up server gossip
20:01:04,250  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
20:01:04,451  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
20:01:04,453  INFO Memtable:266 - Writing Memtable-IndexInfo@881341271(39/48 serialized/live bytes, 1 ops)
20:01:04,454  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:01:04,674  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
20:01:04,675  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:01:04,675  INFO Memtable:266 - Writing Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
20:01:04,899  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:01:04,912  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:01:04,919  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:01:04,927  WARN StorageService:633 - Generated random token xSeZexp7RwehYSW4. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:01:04,930  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1503155693(53/66 serialized/live bytes, 2 ops)
20:01:04,930  INFO Memtable:266 - Writing Memtable-LocationInfo@1503155693(53/66 serialized/live bytes, 2 ops)
20:01:05,157  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:01:05,161  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:01:05,173  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:01:05,174  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:01:05,207  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:01:05,211  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:01:05,214  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:01:05,215  INFO CassandraDaemon:212 - Listening for thrift clients...
20:01:06,925  INFO EmbeddedServerHelper:73 - Done sleeping
20:01:06,960  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:06,988  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
20:01:07,265  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.341 sec
20:01:07,289  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:01:07,291  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:01:07,292  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.BatchMutationTest
Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.118 sec
Running me.prettyprint.cassandra.service.CassandraHostConfiguratorTest
20:01:09,273 ERROR CassandraHost:68 - Unable to resolve host h1
20:01:09,372 ERROR CassandraHost:68 - Unable to resolve host h2
20:01:09,462 ERROR CassandraHost:68 - Unable to resolve host h3
Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.077 sec
Running me.prettyprint.cassandra.service.KeyspaceTest
20:01:09,937  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
20:01:09,959  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:01:10,136  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:01:10,317  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:01:10,773  INFO EmbeddedServerHelper:66 - Starting executor
20:01:10,774  INFO EmbeddedServerHelper:69 - Started executor
20:01:10,775  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:01:10,775  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:01:10,775  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter2525525994810483827.jar
20:01:10,778  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:01:10,797  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:01:10,813  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:01:10,814  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:01:10,819  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:01:10,966  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:01:10,966  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:01:11,008  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:01:11,016  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:01:11,017  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:01:11,019  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:01:11,037  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@545732387(39/48 serialized/live bytes, 1 ops)
20:01:11,038  INFO Memtable:266 - Writing Memtable-IndexInfo@545732387(39/48 serialized/live bytes, 1 ops)
20:01:11,039  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50273050187382.log
20:01:11,042  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1821457857(39/48 serialized/live bytes, 1 ops)
20:01:11,043  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50273050187382.log
20:01:11,047  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50273050187382.log
20:01:11,047  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:01:11,062  INFO StorageService:412 - Cassandra version: 1.1.0
20:01:11,062  INFO StorageService:413 - Thrift API version: 19.30.0
20:01:11,068  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:01:11,120  INFO StorageService:444 - Loading persisted ring state
20:01:11,123  INFO StorageService:525 - Starting up server gossip
20:01:11,136  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1734393680(126/157 serialized/live bytes, 3 ops)
20:01:11,325  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
20:01:11,328  INFO Memtable:266 - Writing Memtable-IndexInfo@1821457857(39/48 serialized/live bytes, 1 ops)
20:01:11,329  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:01:11,558  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
20:01:11,559  INFO Memtable:266 - Writing Memtable-LocationInfo@1734393680(126/157 serialized/live bytes, 3 ops)
20:01:11,559  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:01:11,784  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:01:11,801  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:01:11,808  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:01:11,817  WARN StorageService:633 - Generated random token tLmna7fHBNdlYXHl. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:01:11,819  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2061551555(53/66 serialized/live bytes, 2 ops)
20:01:11,820  INFO Memtable:266 - Writing Memtable-LocationInfo@2061551555(53/66 serialized/live bytes, 2 ops)
20:01:12,043  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:01:12,048  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:01:12,063  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:01:12,064  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:01:12,097  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:01:12,100  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:01:12,104  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:01:12,104  INFO CassandraDaemon:212 - Listening for thrift clients...
20:01:13,775  INFO EmbeddedServerHelper:73 - Done sleeping
20:01:13,815  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:13,835  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
20:01:14,031  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
20:01:14,344  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
20:01:14,598  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:14,631  INFO NodeId:200 - No saved local node id, using newly generated: 8aa79970-e3de-11e1-0000-fe8ebeead9ef
20:01:14,663  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:14,694  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:14,706  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:15,071  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:15,382  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:15,587  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:15,594  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:15,606  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
20:01:15,748  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:15,833  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
20:01:15,891  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:15,984  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
20:01:16,076  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
20:01:16,184  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:16,358  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
20:01:16,402  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:16,414  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:16,673  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:16,686  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:16,692  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:16,799  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:16,836  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:16,939  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:16,965  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:17,003  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:17,099  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
20:01:17,243  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:17,254  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:17,261  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 24, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 7.536 sec
20:01:17,290  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:01:17,292  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:01:17,293  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.CassandraClusterTest
20:01:18,251  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
20:01:18,273  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:01:18,470  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:01:18,671  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:01:19,107  INFO EmbeddedServerHelper:66 - Starting executor
20:01:19,109  INFO EmbeddedServerHelper:69 - Started executor
20:01:19,109  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:01:19,110  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:01:19,110  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter4960077882823465063.jar
20:01:19,114  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:01:19,133  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:01:19,146  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:01:19,147  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:01:19,151  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:01:19,277  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:01:19,279  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:01:19,323  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:01:19,332  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:01:19,334  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:01:19,336  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:01:19,357  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50281383617241.log
20:01:19,360  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50281383617241.log
20:01:19,360  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@81035498(78/97 serialized/live bytes, 2 ops)
20:01:19,362  INFO Memtable:266 - Writing Memtable-IndexInfo@81035498(78/97 serialized/live bytes, 2 ops)
20:01:19,363  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:01:19,364  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50281383617241.log
20:01:19,364  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:01:19,389  INFO StorageService:412 - Cassandra version: 1.1.0
20:01:19,389  INFO StorageService:413 - Thrift API version: 19.30.0
20:01:19,394  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:01:19,443  INFO StorageService:444 - Loading persisted ring state
20:01:19,447  INFO StorageService:525 - Starting up server gossip
20:01:19,456  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
20:01:19,656  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
20:01:19,658  INFO Memtable:266 - Writing Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
20:01:19,659  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:01:19,889  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:01:19,908  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:01:19,914  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:01:19,923  WARN StorageService:633 - Generated random token azzmQcncmnhlhkSJ. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:01:19,925  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1660575731(53/66 serialized/live bytes, 2 ops)
20:01:19,925  INFO Memtable:266 - Writing Memtable-LocationInfo@1660575731(53/66 serialized/live bytes, 2 ops)
20:01:20,125  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:01:20,129  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:01:20,145  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:01:20,146  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:01:20,178  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:01:20,182  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:01:20,186  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:01:20,186  INFO CassandraDaemon:212 - Listening for thrift clients...
20:01:22,110  INFO EmbeddedServerHelper:73 - Done sleeping
20:01:22,148  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:22,168  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
20:01:22,238  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:22,272  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:22,281  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:22,302  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:22,329  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:22,334  INFO AbstractCluster:246 - in execute with client org.apache.cassandra.thrift.Cassandra$Client@47e9d9b1
20:01:22,339  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:22,678  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1617610261(1158/1447 serialized/live bytes, 20 ops)
20:01:22,679  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1617610261(1158/1447 serialized/live bytes, 20 ops)
20:01:22,916  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-1-Data.db (1225 bytes)
20:01:23,029  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1045088358(957/1196 serialized/live bytes, 21 ops)
20:01:23,030  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1045088358(957/1196 serialized/live bytes, 21 ops)
20:01:23,254  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-2-Data.db (1024 bytes)
20:01:23,313  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@543998628(1158/1447 serialized/live bytes, 20 ops)
20:01:23,313  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@543998628(1158/1447 serialized/live bytes, 20 ops)
20:01:23,698  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-3-Data.db (1225 bytes)
20:01:23,733  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1070253050(957/1196 serialized/live bytes, 21 ops)
20:01:23,733  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1070253050(957/1196 serialized/live bytes, 21 ops)
20:01:23,981  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-4-Data.db (1024 bytes)
20:01:23,988  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-1-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-3-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-4-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-2-Data.db')]
20:01:24,003  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:24,023  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@430554787(1338/1672 serialized/live bytes, 20 ops)
20:01:24,024  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@430554787(1338/1672 serialized/live bytes, 20 ops)
20:01:24,441  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-5-Data.db,].  4.498 to 1.073 (~23% of original) bytes for 1 keys at 0,002305MB/s.  Time: 444ms.
20:01:24,520  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-6-Data.db (1405 bytes)
20:01:24,646  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-TruncateableCf@1343563331(29/36 serialized/live bytes, 1 ops)
20:01:24,647  INFO Memtable:266 - Writing Memtable-TruncateableCf@1343563331(29/36 serialized/live bytes, 1 ops)
20:01:24,847  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/Keyspace1/TruncateableCf/Keyspace1-TruncateableCf-hc-1-Data.db (77 bytes)
20:01:24,849  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-Versions@1572590979(83/103 serialized/live bytes, 3 ops)
20:01:24,849  INFO Memtable:266 - Writing Memtable-Versions@1572590979(83/103 serialized/live bytes, 3 ops)
20:01:25,058  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/Versions/system-Versions-hc-1-Data.db (247 bytes)
20:01:25,197  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:25,227  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1374806939(189/236 serialized/live bytes, 4 ops)
20:01:25,228  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1374806939(189/236 serialized/live bytes, 4 ops)
20:01:25,452  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-1-Data.db (250 bytes)
20:01:25,454  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@2022121061(1158/1447 serialized/live bytes, 20 ops)
20:01:25,455  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@2022121061(1158/1447 serialized/live bytes, 20 ops)
20:01:25,678  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-7-Data.db (1227 bytes)
20:01:25,718  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1781335171(0/0 serialized/live bytes, 1 ops)
20:01:25,719  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1781335171(0/0 serialized/live bytes, 1 ops)
20:01:25,948  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-2-Data.db (61 bytes)
20:01:25,950  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@719340043(0/0 serialized/live bytes, 1 ops)
20:01:25,951  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@719340043(0/0 serialized/live bytes, 1 ops)
20:01:26,173  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-8-Data.db (61 bytes)
20:01:26,175  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@384464201(0/0 serialized/live bytes, 1 ops)
20:01:26,175  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-7-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-8-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-6-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-5-Data.db')]
20:01:26,176  INFO Memtable:266 - Writing Memtable-schema_columns@384464201(0/0 serialized/live bytes, 1 ops)
20:01:26,608  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-1-Data.db (61 bytes)
20:01:26,638  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-9-Data.db,].  3.766 to 2.480 (~65% of original) bytes for 2 keys at 0,005142MB/s.  Time: 460ms.
20:01:26,648  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1721309039(189/236 serialized/live bytes, 4 ops)
20:01:26,649  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1721309039(189/236 serialized/live bytes, 4 ops)
20:01:26,871  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-3-Data.db (250 bytes)
20:01:26,874  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1505169310(1158/1447 serialized/live bytes, 20 ops)
20:01:26,875  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1505169310(1158/1447 serialized/live bytes, 20 ops)
20:01:27,129  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-10-Data.db (1227 bytes)
20:01:27,162  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1347941759(0/0 serialized/live bytes, 1 ops)
20:01:27,162  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1347941759(0/0 serialized/live bytes, 1 ops)
20:01:27,443  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-4-Data.db (61 bytes)
20:01:27,445  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1268038167(0/0 serialized/live bytes, 1 ops)
20:01:27,445  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-1-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-4-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-2-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-3-Data.db')]
20:01:27,446  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1268038167(0/0 serialized/live bytes, 1 ops)
20:01:28,694  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-11-Data.db (61 bytes)
20:01:28,696  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@1571172802(0/0 serialized/live bytes, 1 ops)
20:01:28,697  INFO Memtable:266 - Writing Memtable-schema_columns@1571172802(0/0 serialized/live bytes, 1 ops)
20:01:28,766  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-5-Data.db,].  622 to 61 (~9% of original) bytes for 1 keys at 0,000044MB/s.  Time: 1.318ms.
20:01:28,975  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-2-Data.db (61 bytes)
20:01:28,997  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:29,021  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1677170384(177/221 serialized/live bytes, 4 ops)
20:01:29,022  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1677170384(177/221 serialized/live bytes, 4 ops)
20:01:29,244  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-6-Data.db (241 bytes)
20:01:29,246  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1397120162(1158/1447 serialized/live bytes, 20 ops)
20:01:29,247  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1397120162(1158/1447 serialized/live bytes, 20 ops)
20:01:29,639  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-12-Data.db (1230 bytes)
20:01:29,641  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-9-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-11-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-10-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-12-Data.db')]
20:01:29,661  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:29,680  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@464673410(190/237 serialized/live bytes, 4 ops)
20:01:29,681  INFO Memtable:266 - Writing Memtable-schema_keyspaces@464673410(190/237 serialized/live bytes, 4 ops)
20:01:30,124  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-13-Data.db,].  4.998 to 3.710 (~74% of original) bytes for 3 keys at 0,007371MB/s.  Time: 480ms.
20:01:30,154  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-7-Data.db (252 bytes)
20:01:30,157  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1713303340(1238/1547 serialized/live bytes, 20 ops)
20:01:30,157  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1713303340(1238/1547 serialized/live bytes, 20 ops)
20:01:30,357  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-14-Data.db (1308 bytes)
20:01:30,392  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1051606440(190/237 serialized/live bytes, 4 ops)
20:01:30,392  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1051606440(190/237 serialized/live bytes, 4 ops)
20:01:30,617  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-8-Data.db (252 bytes)
20:01:30,619  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-5-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-6-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-8-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-7-Data.db')]
20:01:30,650  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@812783973(0/0 serialized/live bytes, 1 ops)
20:01:30,651  INFO Memtable:266 - Writing Memtable-schema_keyspaces@812783973(0/0 serialized/live bytes, 1 ops)
20:01:31,047  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-9-Data.db,].  806 to 554 (~68% of original) bytes for 3 keys at 0,001240MB/s.  Time: 426ms.
20:01:31,066  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-10-Data.db (62 bytes)
20:01:31,068  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1704294964(0/0 serialized/live bytes, 1 ops)
20:01:31,069  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1704294964(0/0 serialized/live bytes, 1 ops)
20:01:31,292  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-15-Data.db (62 bytes)
20:01:31,294  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@1950123753(0/0 serialized/live bytes, 1 ops)
20:01:31,295  INFO Memtable:266 - Writing Memtable-schema_columns@1950123753(0/0 serialized/live bytes, 1 ops)
20:01:31,518  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-3-Data.db (62 bytes)
20:01:31,533  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:31,558  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1424019819(190/237 serialized/live bytes, 4 ops)
20:01:31,558  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1424019819(190/237 serialized/live bytes, 4 ops)
20:01:31,787  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-11-Data.db (252 bytes)
20:01:31,789  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@565415132(1238/1547 serialized/live bytes, 20 ops)
20:01:31,790  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@565415132(1238/1547 serialized/live bytes, 20 ops)
20:01:32,001  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-16-Data.db (1308 bytes)
20:01:32,003  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-14-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-15-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-13-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-16-Data.db')]
20:01:32,057  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@935521285(1238/1547 serialized/live bytes, 20 ops)
20:01:32,058  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@935521285(1238/1547 serialized/live bytes, 20 ops)
20:01:32,383  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-17-Data.db,].  6.388 to 5.080 (~79% of original) bytes for 5 keys at 0,012817MB/s.  Time: 378ms.
20:01:32,453  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-18-Data.db (1308 bytes)
20:01:32,455  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@1680287410(815/1018 serialized/live bytes, 10 ops)
20:01:32,456  INFO Memtable:266 - Writing Memtable-schema_columns@1680287410(815/1018 serialized/live bytes, 10 ops)
20:01:32,677  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-4-Data.db (877 bytes)
20:01:32,679  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-4-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-1-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-3-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-2-Data.db')]
20:01:32,702  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746864617465, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthdate_idx'}
20:01:32,704  INFO SecondaryIndex:159 - Submitting index build of DynamicCF.birthdate_idx for data in 
20:01:32,706  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1819028088(38/47 serialized/live bytes, 1 ops)
20:01:32,707  INFO Memtable:266 - Writing Memtable-IndexInfo@1819028088(38/47 serialized/live bytes, 1 ops)
20:01:32,708  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:32,753  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@526362387(194/242 serialized/live bytes, 4 ops)
20:01:33,092  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-5-Data.db,].  1.061 to 1.000 (~94% of original) bytes for 3 keys at 0,002315MB/s.  Time: 412ms.
20:01:33,116  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (100 bytes)
20:01:33,117  INFO SecondaryIndex:200 - Index build of DynamicCF.birthdate_idx complete
20:01:33,117  INFO Memtable:266 - Writing Memtable-schema_keyspaces@526362387(194/242 serialized/live bytes, 4 ops)
20:01:33,343  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-12-Data.db (260 bytes)
20:01:33,345  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-12-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-11-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-9-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-10-Data.db')]
20:01:33,365  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1762842542(0/0 serialized/live bytes, 1 ops)
20:01:33,366  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1762842542(0/0 serialized/live bytes, 1 ops)
20:01:33,849  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-13-Data.db,].  1.128 to 876 (~77% of original) bytes for 5 keys at 0,001664MB/s.  Time: 502ms.
20:01:33,871  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-14-Data.db (66 bytes)
20:01:33,872  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@201833163(0/0 serialized/live bytes, 1 ops)
20:01:33,873  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@201833163(0/0 serialized/live bytes, 1 ops)
20:01:34,274  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-19-Data.db (66 bytes)
20:01:34,276  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@339855735(0/0 serialized/live bytes, 1 ops)
20:01:34,277  INFO Memtable:266 - Writing Memtable-schema_columns@339855735(0/0 serialized/live bytes, 1 ops)
20:01:34,489  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-6-Data.db (66 bytes)
20:01:34,505  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:34,521  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@372889617(194/242 serialized/live bytes, 4 ops)
20:01:34,521  INFO Memtable:266 - Writing Memtable-schema_keyspaces@372889617(194/242 serialized/live bytes, 4 ops)
20:01:35,414  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-15-Data.db (260 bytes)
20:01:35,443  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@275980095(0/0 serialized/live bytes, 1 ops)
20:01:35,444  INFO Memtable:266 - Writing Memtable-schema_keyspaces@275980095(0/0 serialized/live bytes, 1 ops)
20:01:35,672  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-16-Data.db (66 bytes)
20:01:35,673  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1292550819(0/0 serialized/live bytes, 1 ops)
20:01:35,673  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-15-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-16-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-14-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-13-Data.db')]
20:01:35,674  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1292550819(0/0 serialized/live bytes, 1 ops)
20:01:36,067  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-20-Data.db (66 bytes)
20:01:36,068  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@94379921(0/0 serialized/live bytes, 1 ops)
20:01:36,068  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-20-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-19-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-18-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-17-Data.db')]
20:01:36,069  INFO Memtable:266 - Writing Memtable-schema_columns@94379921(0/0 serialized/live bytes, 1 ops)
20:01:36,104  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-17-Data.db,].  1.268 to 682 (~53% of original) bytes for 5 keys at 0,001516MB/s.  Time: 429ms.
20:01:36,424  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-7-Data.db (66 bytes)
20:01:36,438  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:36,457  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1321288348(190/237 serialized/live bytes, 4 ops)
20:01:36,458  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1321288348(190/237 serialized/live bytes, 4 ops)
20:01:36,739  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-21-Data.db,].  6.520 to 5.146 (~78% of original) bytes for 6 keys at 0,007336MB/s.  Time: 669ms.
20:01:37,243  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-18-Data.db (252 bytes)
20:01:37,269  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1251696669(190/237 serialized/live bytes, 4 ops)
20:01:37,270  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1251696669(190/237 serialized/live bytes, 4 ops)
20:01:37,480  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-19-Data.db (252 bytes)
20:01:37,501  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@831285673(0/0 serialized/live bytes, 1 ops)
20:01:37,502  INFO Memtable:266 - Writing Memtable-schema_keyspaces@831285673(0/0 serialized/live bytes, 1 ops)
20:01:37,727  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-20-Data.db (62 bytes)
20:01:37,729  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-17-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-20-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-18-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-19-Data.db')]
20:01:37,729  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1592409311(0/0 serialized/live bytes, 1 ops)
20:01:37,730  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1592409311(0/0 serialized/live bytes, 1 ops)
20:01:38,135  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-22-Data.db (62 bytes)
20:01:38,136  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@1083461053(0/0 serialized/live bytes, 1 ops)
20:01:38,137  INFO Memtable:266 - Writing Memtable-schema_columns@1083461053(0/0 serialized/live bytes, 1 ops)
20:01:38,183  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-21-Data.db,].  1.248 to 744 (~59% of original) bytes for 6 keys at 0,001566MB/s.  Time: 453ms.
20:01:38,392  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-8-Data.db (62 bytes)
20:01:38,393  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-6-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-8-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-5-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-7-Data.db')]
20:01:38,405  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:38,418  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@596545504(1158/1447 serialized/live bytes, 20 ops)
20:01:38,418  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@596545504(1158/1447 serialized/live bytes, 20 ops)
20:01:38,883  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-9-Data.db,].  1.194 to 1.128 (~94% of original) bytes for 5 keys at 0,002200MB/s.  Time: 489ms.
20:01:38,919  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-23-Data.db (1225 bytes)
20:01:38,950  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@336575420(957/1196 serialized/live bytes, 21 ops)
20:01:38,950  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@336575420(957/1196 serialized/live bytes, 21 ops)
20:01:39,512  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-24-Data.db (1024 bytes)
20:01:39,514  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-23-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-24-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-22-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-21-Data.db')]
20:01:39,535  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:39,551  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1454474388(187/233 serialized/live bytes, 4 ops)
20:01:39,552  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1454474388(187/233 serialized/live bytes, 4 ops)
20:01:40,461  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-25-Data.db,].  7.457 to 5.208 (~69% of original) bytes for 7 keys at 0,005245MB/s.  Time: 947ms.
20:01:40,509  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-22-Data.db (246 bytes)
20:01:40,536  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@810158789(1178/1472 serialized/live bytes, 20 ops)
20:01:40,537  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@810158789(1178/1472 serialized/live bytes, 20 ops)
20:01:40,777  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-26-Data.db (1245 bytes)
20:01:40,805  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@974510717(1177/1471 serialized/live bytes, 20 ops)
20:01:40,805  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@974510717(1177/1471 serialized/live bytes, 20 ops)
20:01:41,035  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-27-Data.db (1244 bytes)
20:01:41,073  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1126660656(978/1222 serialized/live bytes, 21 ops)
20:01:41,074  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1126660656(978/1222 serialized/live bytes, 21 ops)
20:01:41,306  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-28-Data.db (1045 bytes)
20:01:41,308  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-26-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-25-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-27-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-28-Data.db')]
20:01:41,321  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 17, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 23.277 sec
20:01:41,339  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:01:41,342  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:01:41,342  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.KeyIteratorTest
20:01:42,328  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
20:01:42,352  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:01:42,501  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:01:42,657  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:01:43,089  INFO EmbeddedServerHelper:66 - Starting executor
20:01:43,090  INFO EmbeddedServerHelper:69 - Started executor
20:01:43,090  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:01:43,090  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:01:43,090  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter2467732637031408085.jar
20:01:43,092  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:01:43,111  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:01:43,125  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:01:43,126  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:01:43,130  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:01:43,251  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:01:43,252  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:01:43,293  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:01:43,301  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:01:43,306  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:01:43,310  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:01:43,334  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@160094946(78/97 serialized/live bytes, 2 ops)
20:01:43,336  INFO Memtable:266 - Writing Memtable-IndexInfo@160094946(78/97 serialized/live bytes, 2 ops)
20:01:43,336  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:01:43,339  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50305371541947.log
20:01:43,342  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50305371541947.log
20:01:43,346  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50305371541947.log
20:01:43,347  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:01:43,364  INFO StorageService:412 - Cassandra version: 1.1.0
20:01:43,365  INFO StorageService:413 - Thrift API version: 19.30.0
20:01:43,369  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:01:43,411  INFO StorageService:444 - Loading persisted ring state
20:01:43,415  INFO StorageService:525 - Starting up server gossip
20:01:43,425  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
20:01:43,643  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
20:01:43,646  INFO Memtable:266 - Writing Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
20:01:43,647  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:01:43,876  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:01:43,895  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:01:43,901  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:01:43,910  WARN StorageService:633 - Generated random token BpqqhuWP85ojSHOM. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:01:43,912  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1609377764(53/66 serialized/live bytes, 2 ops)
20:01:43,912  INFO Memtable:266 - Writing Memtable-LocationInfo@1609377764(53/66 serialized/live bytes, 2 ops)
20:01:44,134  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:01:44,139  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:01:44,150  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:01:44,151  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:01:44,183  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:01:44,186  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:01:44,190  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:01:44,190  INFO CassandraDaemon:212 - Listening for thrift clients...
20:01:46,090  INFO EmbeddedServerHelper:73 - Done sleeping
20:01:46,125  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:46,147  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
20:01:46,372  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.248 sec
20:01:46,388  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:01:46,389  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:01:46,390  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.ColumnSliceIteratorTest
20:01:47,282  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
20:01:47,306  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
20:01:47,481  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
20:01:47,664  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
20:01:48,121  INFO EmbeddedServerHelper:66 - Starting executor
20:01:48,122  INFO EmbeddedServerHelper:69 - Started executor
20:01:48,122  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
20:01:48,123  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
20:01:48,123  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter2017255016306438407.jar
20:01:48,126  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
20:01:48,141  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
20:01:48,156  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
20:01:48,157  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
20:01:48,161  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
20:01:48,292  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
20:01:48,293  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
20:01:48,340  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:01:48,348  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
20:01:48,350  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
20:01:48,352  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
20:01:48,373  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-50310403465541.log
20:01:48,376  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@404454518(78/97 serialized/live bytes, 2 ops)
20:01:48,377  INFO Memtable:266 - Writing Memtable-IndexInfo@404454518(78/97 serialized/live bytes, 2 ops)
20:01:48,378  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
20:01:48,378  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-50310403465541.log
20:01:48,384  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-50310403465541.log
20:01:48,384  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
20:01:48,399  INFO StorageService:412 - Cassandra version: 1.1.0
20:01:48,399  INFO StorageService:413 - Thrift API version: 19.30.0
20:01:48,403  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
20:01:48,452  INFO StorageService:444 - Loading persisted ring state
20:01:48,455  INFO StorageService:525 - Starting up server gossip
20:01:48,464  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
20:01:48,709  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
20:01:48,711  INFO Memtable:266 - Writing Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
20:01:48,712  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
20:01:48,936  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
20:01:48,951  INFO MessagingService:284 - Starting Messaging Service on port 7070
20:01:48,957  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
20:01:48,965  WARN StorageService:633 - Generated random token R1AJRETGniqz3jiF. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
20:01:48,967  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1255545583(53/66 serialized/live bytes, 2 ops)
20:01:48,968  INFO Memtable:266 - Writing Memtable-LocationInfo@1255545583(53/66 serialized/live bytes, 2 ops)
20:01:49,193  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
20:01:49,196  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
20:01:49,205  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
20:01:49,207  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
20:01:49,242  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
20:01:49,246  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
20:01:49,250  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
20:01:49,251  INFO CassandraDaemon:212 - Listening for thrift clients...
20:01:51,122  INFO EmbeddedServerHelper:73 - Done sleeping
20:01:51,163  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
20:01:51,188  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
20:01:51,864  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
20:01:51,925  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
20:01:52,079  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
20:01:52,378  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
20:01:52,782  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.694 sec
20:01:52,808  INFO CassandraDaemon:218 - Stop listening to thrift clients
20:01:52,810  INFO MessagingService:539 - Waiting for messaging service to quiesce
20:01:52,812  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.utils.TimeUUIDUtilsTest
20:01:53,733  INFO TimeUUIDUtilsTest:85 - Original Time: 1344708113733
20:01:53,738  INFO TimeUUIDUtilsTest:86 - ----
20:01:53,738  INFO TimeUUIDUtilsTest:90 - Java UUID: a1f61750-e3de-11e1-86fc-5cac4c624515
20:01:53,738  INFO TimeUUIDUtilsTest:91 - Java UUID timestamp: 135640009137330000
20:01:53,744  INFO TimeUUIDUtilsTest:92 - Date: Wed Oct 13 05:55:30 CEST 4300230
20:01:53,744  INFO TimeUUIDUtilsTest:94 - ----
20:01:53,744  INFO TimeUUIDUtilsTest:97 - eaio UUID: 00000139-16d9-7545-0000-000000000000
20:01:53,744  INFO TimeUUIDUtilsTest:98 - eaio UUID timestamp: 1344708113733
20:01:53,745  INFO TimeUUIDUtilsTest:99 - Date: Sat Aug 11 20:01:53 CEST 2012
20:01:53,745  INFO TimeUUIDUtilsTest:101 - ----
20:01:53,745  INFO TimeUUIDUtilsTest:104 - Java UUID to bytes to time: 1344708113733
20:01:53,745  INFO TimeUUIDUtilsTest:105 - Java UUID to bytes time to Date: Sat Aug 11 20:01:53 CEST 2012
20:01:53,745  INFO TimeUUIDUtilsTest:107 - ----
20:01:53,746  INFO TimeUUIDUtilsTest:110 - Java UUID to time: 1344708113733
20:01:53,746  INFO TimeUUIDUtilsTest:111 - Java UUID to time to Date: Sat Aug 11 20:01:53 CEST 2012
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.211 sec
Running me.prettyprint.cassandra.utils.ByteBufferOutputStreamTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.489 sec

Results :

Tests run: 273, Failures: 0, Errors: 0, Skipped: 2

[INFO] [bundle:bundle {execution: default-bundle}]
[WARNING] Warning building bundle org.hectorclient:hector-core:bundle:1.1-2-SNAPSHOT : Did not find matching referal for !javax.jms.*
[WARNING] Warning building bundle org.hectorclient:hector-core:bundle:1.1-2-SNAPSHOT : Did not find matching referal for !javax.mail.*
[WARNING] Warning building bundle org.hectorclient:hector-core:bundle:1.1-2-SNAPSHOT : Did not find matching referal for !javax.servlet.*
[WARNING] Warning building bundle org.hectorclient:hector-core:bundle:1.1-2-SNAPSHOT : Did not find matching referal for !jline.*
[WARNING] Warning building bundle org.hectorclient:hector-core:bundle:1.1-2-SNAPSHOT : Did not find matching referal for !org.antlr.*
[WARNING] Warning building bundle org.hectorclient:hector-core:bundle:1.1-2-SNAPSHOT : Did not find matching referal for !org.apache.avro.*
[WARNING] Warning building bundle org.hectorclient:hector-core:bundle:1.1-2-SNAPSHOT : Did not find matching referal for !sun.misc.*
[INFO] [jar:test-jar {execution: default}]
[INFO] Building jar: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/hector-core-1.1-2-SNAPSHOT-tests.jar
[INFO] Preparing source:jar
[WARNING] Removing: jar from forked lifecycle, to prevent recursive invocation.
[INFO] [enforcer:enforce {execution: enforce-maven}]
[INFO] [source:jar {execution: attach-sources}]
[INFO] Building jar: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/hector-core-1.1-2-SNAPSHOT-sources.jar
[INFO] [install:install {execution: default-install}]
[INFO] Installing /home/cesare/workspace/tesi/CassandraBM/hector/core/target/hector-core-1.1-2-SNAPSHOT.jar to /root/.m2/repository/org/hectorclient/hector-core/1.1-2-SNAPSHOT/hector-core-1.1-2-SNAPSHOT.jar
[INFO] Installing /home/cesare/workspace/tesi/CassandraBM/hector/core/target/hector-core-1.1-2-SNAPSHOT-tests.jar to /root/.m2/repository/org/hectorclient/hector-core/1.1-2-SNAPSHOT/hector-core-1.1-2-SNAPSHOT-tests.jar
[INFO] Installing /home/cesare/workspace/tesi/CassandraBM/hector/core/target/hector-core-1.1-2-SNAPSHOT-sources.jar to /root/.m2/repository/org/hectorclient/hector-core/1.1-2-SNAPSHOT/hector-core-1.1-2-SNAPSHOT-sources.jar
[INFO] [bundle:install {execution: default-install}]
[INFO] Parsing file:/root/.m2/repository/repository.xml
[INFO] Installing org/hectorclient/hector-core/1.1-2-SNAPSHOT/hector-core-1.1-2-SNAPSHOT.jar
[INFO] Writing OBR metadata
[INFO] ------------------------------------------------------------------------
[INFO] Building hector-object-mapper
[INFO]    task-segment: [install]
[INFO] ------------------------------------------------------------------------
[INFO] [enforcer:enforce {execution: enforce-maven}]
[INFO] [resources:resources {execution: default-resources}]
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 1 resource
[INFO] [compiler:compile {execution: default-compile}]
[INFO] Nothing to compile - all classes are up to date
[INFO] [resources:testResources {execution: default-testResources}]
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 2 resources
[INFO] [compiler:testCompile {execution: default-testCompile}]
[INFO] Nothing to compile - all classes are up to date
[WARNING] DEPRECATED [systemProperties]: Use systemPropertyVariables instead.
[INFO] [surefire:test {execution: default-test}]
[INFO] Surefire report directory: /home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.mycompany.furniture.FurnitureTest
INFO - Loading settings from file:tmp/cassandra.yaml
INFO - Loading settings from file:tmp/cassandra.yaml
INFO - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
INFO - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
INFO - Global memtable threshold is enabled at 163MB
INFO - Global memtable threshold is enabled at 163MB
INFO - Starting executor
INFO - Started executor
INFO - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
INFO - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
INFO - Heap size: 514523136/514523136
INFO - Heap size: 514523136/514523136
INFO - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/surefire/surefirebooter8211740020271229437.jar
INFO - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/surefire/surefirebooter8211740020271229437.jar
INFO - JNA not found. Native methods will be disabled.
INFO - JNA not found. Native methods will be disabled.
INFO - Initializing key cache with capacity of 24 MBs.
INFO - Initializing key cache with capacity of 24 MBs.
INFO - Scheduling key cache save to each 14400 seconds (going to save all keys).
INFO - Scheduling key cache save to each 14400 seconds (going to save all keys).
INFO - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.ConcurrentLinkedHashCacheProvider
INFO - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.ConcurrentLinkedHashCacheProvider
INFO - Scheduling row cache save to each 0 seconds (going to save all keys).
INFO - Scheduling row cache save to each 0 seconds (going to save all keys).
INFO - Couldn't detect any schema definitions in local storage.
INFO - Couldn't detect any schema definitions in local storage.
INFO - Found table data in data directories. Consider using the CLI to define your schema.
INFO - Found table data in data directories. Consider using the CLI to define your schema.
INFO - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
INFO - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
INFO - Submitting index build of Indexed2.birthyear_index for data in 
INFO - Submitting index build of Indexed2.birthyear_index for data in 
INFO - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
INFO - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
INFO - Submitting index build of Indexed1.birthyear_index for data in 
INFO - Submitting index build of Indexed1.birthyear_index for data in 
INFO - Enqueuing flush of Memtable-IndexInfo@270142182(39/48 serialized/live bytes, 1 ops)
INFO - Enqueuing flush of Memtable-IndexInfo@270142182(39/48 serialized/live bytes, 1 ops)
INFO - Writing Memtable-IndexInfo@270142182(39/48 serialized/live bytes, 1 ops)
INFO - Writing Memtable-IndexInfo@270142182(39/48 serialized/live bytes, 1 ops)
INFO - Replaying target/cassandra-data/commitlog/CommitLog-50320676561833.log
INFO - Replaying target/cassandra-data/commitlog/CommitLog-50320676561833.log
INFO - Enqueuing flush of Memtable-IndexInfo@251172046(39/48 serialized/live bytes, 1 ops)
INFO - Enqueuing flush of Memtable-IndexInfo@251172046(39/48 serialized/live bytes, 1 ops)
INFO - Replaying target/cassandra-data/commitlog/CommitLog-50320676561833.log
INFO - Replaying target/cassandra-data/commitlog/CommitLog-50320676561833.log
INFO - Finished reading target/cassandra-data/commitlog/CommitLog-50320676561833.log
INFO - Finished reading target/cassandra-data/commitlog/CommitLog-50320676561833.log
INFO - Log replay complete, 0 replayed mutations
INFO - Log replay complete, 0 replayed mutations
INFO - Cassandra version: 1.1.0
INFO - Cassandra version: 1.1.0
INFO - Thrift API version: 19.30.0
INFO - Thrift API version: 19.30.0
INFO - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
INFO - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
INFO - Loading persisted ring state
INFO - Loading persisted ring state
INFO - Starting up server gossip
INFO - Starting up server gossip
INFO - Enqueuing flush of Memtable-LocationInfo@1847652919(126/157 serialized/live bytes, 3 ops)
INFO - Enqueuing flush of Memtable-LocationInfo@1847652919(126/157 serialized/live bytes, 3 ops)
INFO - Completed flushing target/cassandra-data/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
INFO - Completed flushing target/cassandra-data/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
INFO - Writing Memtable-IndexInfo@251172046(39/48 serialized/live bytes, 1 ops)
INFO - Writing Memtable-IndexInfo@251172046(39/48 serialized/live bytes, 1 ops)
INFO - Index build of Indexed2.birthyear_index complete
INFO - Index build of Indexed2.birthyear_index complete
INFO - Completed flushing target/cassandra-data/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
INFO - Completed flushing target/cassandra-data/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
INFO - Index build of Indexed1.birthyear_index complete
INFO - Index build of Indexed1.birthyear_index complete
INFO - Writing Memtable-LocationInfo@1847652919(126/157 serialized/live bytes, 3 ops)
INFO - Writing Memtable-LocationInfo@1847652919(126/157 serialized/live bytes, 3 ops)
INFO - Completed flushing target/cassandra-data/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
INFO - Completed flushing target/cassandra-data/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
INFO - Starting Messaging Service on port 7070
INFO - Starting Messaging Service on port 7070
INFO - This node will not auto bootstrap because it is configured to be a seed node.
INFO - This node will not auto bootstrap because it is configured to be a seed node.
WARN - Generated random token 82828827060293933776289161250580588421. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
WARN - Generated random token 82828827060293933776289161250580588421. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
INFO - Enqueuing flush of Memtable-LocationInfo@85031456(53/66 serialized/live bytes, 2 ops)
INFO - Enqueuing flush of Memtable-LocationInfo@85031456(53/66 serialized/live bytes, 2 ops)
INFO - Writing Memtable-LocationInfo@85031456(53/66 serialized/live bytes, 2 ops)
INFO - Writing Memtable-LocationInfo@85031456(53/66 serialized/live bytes, 2 ops)
INFO - Completed flushing target/cassandra-data/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
INFO - Completed flushing target/cassandra-data/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
INFO - Node localhost/127.0.0.1 state jump to normal
INFO - Node localhost/127.0.0.1 state jump to normal
INFO - Bootstrap/Replace/Move completed! Now serving reads.
INFO - Bootstrap/Replace/Move completed! Now serving reads.
INFO - Will not load MX4J, mx4j-tools.jar is not in the classpath
INFO - Will not load MX4J, mx4j-tools.jar is not in the classpath
INFO - Binding thrift service to localhost/127.0.0.1:9170
INFO - Binding thrift service to localhost/127.0.0.1:9170
INFO - Using TFastFramedTransport with a max frame size of 15728640 bytes.
INFO - Using TFastFramedTransport with a max frame size of 15728640 bytes.
INFO - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
INFO - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
INFO - Listening for thrift clients...
INFO - Listening for thrift clients...
INFO - Done sleeping
INFO - Downed Host Retry service started with queue size -1 and retry delay 10s
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Concurrent Host pool started with 16 active clients; max: 50 exhausted wait: 0
INFO - Registering JMX me.prettyprint.cassandra.service_TestPool:ServiceType=hector,MonitorType=hector
INFO - Enqueuing flush of Memtable-schema_keyspaces@795578445(190/237 serialized/live bytes, 4 ops)
INFO - Enqueuing flush of Memtable-schema_keyspaces@795578445(190/237 serialized/live bytes, 4 ops)
INFO - Writing Memtable-schema_keyspaces@795578445(190/237 serialized/live bytes, 4 ops)
INFO - Writing Memtable-schema_keyspaces@795578445(190/237 serialized/live bytes, 4 ops)
INFO - Completed flushing target/cassandra-data/data/system/schema_keyspaces/system-schema_keyspaces-hc-1-Data.db (252 bytes)
INFO - Completed flushing target/cassandra-data/data/system/schema_keyspaces/system-schema_keyspaces-hc-1-Data.db (252 bytes)
INFO - Enqueuing flush of Memtable-schema_columnfamilies@2039421489(16398/20497 serialized/live bytes, 220 ops)
INFO - Enqueuing flush of Memtable-schema_columnfamilies@2039421489(16398/20497 serialized/live bytes, 220 ops)
INFO - Writing Memtable-schema_columnfamilies@2039421489(16398/20497 serialized/live bytes, 220 ops)
INFO - Writing Memtable-schema_columnfamilies@2039421489(16398/20497 serialized/live bytes, 220 ops)
INFO - Completed flushing target/cassandra-data/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-1-Data.db (16572 bytes)
INFO - Completed flushing target/cassandra-data/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-1-Data.db (16572 bytes)
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture/**/*.class]
DEBUG - Resolved location pattern [classpath*:com/mycompany/furniture/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture/Chair.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture/Desk.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture/Drawer.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture/BasicTable.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture/Furniture.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture/FurnitureTest.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture/Couch.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture/Chair.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture/Desk.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture/BasicTable.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture/Furniture.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/com/mycompany/furniture/Couch.class]
DEBUG - found candidate bean = com.mycompany.furniture.Chair
DEBUG - found annotated class, com.mycompany.furniture.Chair
DEBUG - found candidate bean = com.mycompany.furniture.Desk
DEBUG - found annotated class, com.mycompany.furniture.Desk
DEBUG - found candidate bean = com.mycompany.furniture.BasicTable
DEBUG - found annotated class, com.mycompany.furniture.BasicTable
DEBUG - found candidate bean = com.mycompany.furniture.Couch
DEBUG - found annotated class, com.mycompany.furniture.Couch
DEBUG - classpath array has 1 items : [com.mycompany.furniture]
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.647 sec
INFO - Stop listening to thrift clients
INFO - Stop listening to thrift clients
INFO - Waiting for messaging service to quiesce
INFO - Waiting for messaging service to quiesce
INFO - MessagingService shutting down server thread.
INFO - MessagingService shutting down server thread.
Running me.prettyprint.hom.KeyConcatenationDelimiterStrategyTest
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.085 sec
Running me.prettyprint.hom.converters.VariableIntegerConverterTest
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.094 sec
Running me.prettyprint.hom.cache.InheritanceParserValidatorTest
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.167 sec
Running me.prettyprint.hom.cache.IdClassParserValidatorTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.108 sec
Running me.prettyprint.hom.annotations.AnnotationScannerTest
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.31 sec
Running me.prettyprint.hom.ClassCacheMgrTest
Tests run: 19, Failures: 0, Errors: 0, Skipped: 2, Time elapsed: 0.27 sec
Running me.prettyprint.hom.HectorObjectMapperTest
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.482 sec
Running me.prettyprint.hom.EntityManagerTest
INFO - Loading settings from file:tmp/cassandra.yaml
INFO - Loading settings from file:tmp/cassandra.yaml
INFO - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
INFO - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
INFO - Global memtable threshold is enabled at 163MB
INFO - Global memtable threshold is enabled at 163MB
INFO - Starting executor
INFO - Started executor
INFO - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
INFO - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
INFO - Heap size: 514523136/514523136
INFO - Heap size: 514523136/514523136
INFO - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/surefire/surefirebooter4687200428143101421.jar
INFO - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/surefire/surefirebooter4687200428143101421.jar
INFO - JNA not found. Native methods will be disabled.
INFO - JNA not found. Native methods will be disabled.
INFO - Initializing key cache with capacity of 24 MBs.
INFO - Initializing key cache with capacity of 24 MBs.
INFO - Scheduling key cache save to each 14400 seconds (going to save all keys).
INFO - Scheduling key cache save to each 14400 seconds (going to save all keys).
INFO - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.ConcurrentLinkedHashCacheProvider
INFO - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.ConcurrentLinkedHashCacheProvider
INFO - Scheduling row cache save to each 0 seconds (going to save all keys).
INFO - Scheduling row cache save to each 0 seconds (going to save all keys).
INFO - Couldn't detect any schema definitions in local storage.
INFO - Couldn't detect any schema definitions in local storage.
INFO - Found table data in data directories. Consider using the CLI to define your schema.
INFO - Found table data in data directories. Consider using the CLI to define your schema.
INFO - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
INFO - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
INFO - Submitting index build of Indexed2.birthyear_index for data in 
INFO - Submitting index build of Indexed2.birthyear_index for data in 
INFO - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
INFO - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
INFO - Submitting index build of Indexed1.birthyear_index for data in 
INFO - Submitting index build of Indexed1.birthyear_index for data in 
INFO - Replaying target/cassandra-data/commitlog/CommitLog-50330179568629.log
INFO - Replaying target/cassandra-data/commitlog/CommitLog-50330179568629.log
INFO - Replaying target/cassandra-data/commitlog/CommitLog-50330179568629.log
INFO - Replaying target/cassandra-data/commitlog/CommitLog-50330179568629.log
INFO - Finished reading target/cassandra-data/commitlog/CommitLog-50330179568629.log
INFO - Finished reading target/cassandra-data/commitlog/CommitLog-50330179568629.log
INFO - Log replay complete, 0 replayed mutations
INFO - Log replay complete, 0 replayed mutations
INFO - Enqueuing flush of Memtable-IndexInfo@1643210767(39/48 serialized/live bytes, 1 ops)
INFO - Enqueuing flush of Memtable-IndexInfo@1643210767(39/48 serialized/live bytes, 1 ops)
INFO - Writing Memtable-IndexInfo@1643210767(39/48 serialized/live bytes, 1 ops)
INFO - Writing Memtable-IndexInfo@1643210767(39/48 serialized/live bytes, 1 ops)
INFO - Enqueuing flush of Memtable-IndexInfo@1579948023(39/48 serialized/live bytes, 1 ops)
INFO - Enqueuing flush of Memtable-IndexInfo@1579948023(39/48 serialized/live bytes, 1 ops)
INFO - Cassandra version: 1.1.0
INFO - Cassandra version: 1.1.0
INFO - Thrift API version: 19.30.0
INFO - Thrift API version: 19.30.0
INFO - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
INFO - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
INFO - Loading persisted ring state
INFO - Loading persisted ring state
INFO - Starting up server gossip
INFO - Starting up server gossip
INFO - Enqueuing flush of Memtable-LocationInfo@1847652919(126/157 serialized/live bytes, 3 ops)
INFO - Enqueuing flush of Memtable-LocationInfo@1847652919(126/157 serialized/live bytes, 3 ops)
INFO - Completed flushing target/cassandra-data/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
INFO - Completed flushing target/cassandra-data/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
INFO - Writing Memtable-IndexInfo@1579948023(39/48 serialized/live bytes, 1 ops)
INFO - Writing Memtable-IndexInfo@1579948023(39/48 serialized/live bytes, 1 ops)
INFO - Index build of Indexed2.birthyear_index complete
INFO - Index build of Indexed2.birthyear_index complete
INFO - Completed flushing target/cassandra-data/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
INFO - Completed flushing target/cassandra-data/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
INFO - Index build of Indexed1.birthyear_index complete
INFO - Index build of Indexed1.birthyear_index complete
INFO - Writing Memtable-LocationInfo@1847652919(126/157 serialized/live bytes, 3 ops)
INFO - Writing Memtable-LocationInfo@1847652919(126/157 serialized/live bytes, 3 ops)
INFO - Completed flushing target/cassandra-data/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
INFO - Completed flushing target/cassandra-data/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
INFO - Starting Messaging Service on port 7070
INFO - Starting Messaging Service on port 7070
INFO - This node will not auto bootstrap because it is configured to be a seed node.
INFO - This node will not auto bootstrap because it is configured to be a seed node.
WARN - Generated random token 44068389141110688553320769147679782217. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
WARN - Generated random token 44068389141110688553320769147679782217. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
INFO - Enqueuing flush of Memtable-LocationInfo@85031456(53/66 serialized/live bytes, 2 ops)
INFO - Enqueuing flush of Memtable-LocationInfo@85031456(53/66 serialized/live bytes, 2 ops)
INFO - Writing Memtable-LocationInfo@85031456(53/66 serialized/live bytes, 2 ops)
INFO - Writing Memtable-LocationInfo@85031456(53/66 serialized/live bytes, 2 ops)
INFO - Completed flushing target/cassandra-data/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
INFO - Completed flushing target/cassandra-data/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
INFO - Node localhost/127.0.0.1 state jump to normal
INFO - Node localhost/127.0.0.1 state jump to normal
INFO - Bootstrap/Replace/Move completed! Now serving reads.
INFO - Bootstrap/Replace/Move completed! Now serving reads.
INFO - Will not load MX4J, mx4j-tools.jar is not in the classpath
INFO - Will not load MX4J, mx4j-tools.jar is not in the classpath
INFO - Binding thrift service to localhost/127.0.0.1:9170
INFO - Binding thrift service to localhost/127.0.0.1:9170
INFO - Using TFastFramedTransport with a max frame size of 15728640 bytes.
INFO - Using TFastFramedTransport with a max frame size of 15728640 bytes.
INFO - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
INFO - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
INFO - Listening for thrift clients...
INFO - Listening for thrift clients...
INFO - Done sleeping
INFO - Downed Host Retry service started with queue size -1 and retry delay 10s
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Creation of new client for host: 127.0.0.1
DEBUG - Creating a new thrift connection to localhost(127.0.0.1):9170
DEBUG - Concurrent Host pool started with 16 active clients; max: 50 exhausted wait: 0
INFO - Registering JMX me.prettyprint.cassandra.service_TestPool:ServiceType=hector,MonitorType=hector
INFO - Enqueuing flush of Memtable-schema_keyspaces@1563763559(190/237 serialized/live bytes, 4 ops)
INFO - Enqueuing flush of Memtable-schema_keyspaces@1563763559(190/237 serialized/live bytes, 4 ops)
INFO - Writing Memtable-schema_keyspaces@1563763559(190/237 serialized/live bytes, 4 ops)
INFO - Writing Memtable-schema_keyspaces@1563763559(190/237 serialized/live bytes, 4 ops)
INFO - Completed flushing target/cassandra-data/data/system/schema_keyspaces/system-schema_keyspaces-hc-1-Data.db (252 bytes)
INFO - Completed flushing target/cassandra-data/data/system/schema_keyspaces/system-schema_keyspaces-hc-1-Data.db (252 bytes)
INFO - Enqueuing flush of Memtable-schema_columnfamilies@1567116838(16398/20497 serialized/live bytes, 220 ops)
INFO - Enqueuing flush of Memtable-schema_columnfamilies@1567116838(16398/20497 serialized/live bytes, 220 ops)
INFO - Writing Memtable-schema_columnfamilies@1567116838(16398/20497 serialized/live bytes, 220 ops)
INFO - Writing Memtable-schema_columnfamilies@1567116838(16398/20497 serialized/live bytes, 220 ops)
INFO - Completed flushing target/cassandra-data/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-1-Data.db (16572 bytes)
INFO - Completed flushing target/cassandra-data/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-1-Data.db (16572 bytes)
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - classpath array has 1 items : [me.prettyprint.hom.beans]
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - classpath array has 1 items : [me.prettyprint.hom.beans]
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - classpath array has 1 items : [me.prettyprint.hom.beans]
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - classpath array has 1 items : [me.prettyprint.hom.beans]
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - classpath array has 1 items : [me.prettyprint.hom.beans]
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - classpath array has 1 items : [me.prettyprint.hom.beans]
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - classpath array has 1 items : [me.prettyprint.hom.beans]
DEBUG - keyspace reseting from null to TestKeyspace
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - classpath array has 1 items : [me.prettyprint.hom.beans]
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - classpath array has 1 items : [me.prettyprint.hom.beans]
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - classpath array has 1 items : [me.prettyprint.hom.beans]
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - classpath array has 1 items : [me.prettyprint.hom.beans]
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - classpath array has 1 items : [me.prettyprint.hom.beans]
DEBUG - Looking for matching resources in directory tree [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans]
DEBUG - Searching directory [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans] for files matching pattern [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/**/*.class]
DEBUG - Resolved location pattern [classpath*:me/prettyprint/hom/beans/**/*.class] to resources [file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositePK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/ColorEmbedded.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyNonEntityTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComposite2PK.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class], file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyPurpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleRelationshipBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyComplexEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyRedTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCustomIdBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyConvertedCollectionBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/AnonymousWithCustomType.class]
DEBUG - Ignored because not a concrete top-level class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyAbstractGreenTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyTestBeanNoAnonymous.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyBlueTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/SimpleTestBean.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyCompositeEntity.class]
DEBUG - Identified candidate component class: file [/home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/test-classes/me/prettyprint/hom/beans/MyGreenTestBean.class]
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyPurpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleRelationshipBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyComplexEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyRedTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCustomIdBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyConvertedCollectionBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found annotated class, me.prettyprint.hom.beans.AnonymousWithCustomType
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found annotated class, me.prettyprint.hom.beans.MyTestBeanNoAnonymous
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyBlueTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.SimpleTestBean
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found annotated class, me.prettyprint.hom.beans.MyCompositeEntity
DEBUG - found candidate bean = me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - found annotated class, me.prettyprint.hom.beans.MyGreenTestBean
DEBUG - classpath array has 1 items : [me.prettyprint.hom.beans]
Tests run: 13, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 6.397 sec

Results :

Tests run: 58, Failures: 0, Errors: 0, Skipped: 2

INFO - Stop listening to thrift clients
INFO - Stop listening to thrift clients
INFO - Waiting for messaging service to quiesce
INFO - Waiting for messaging service to quiesce
INFO - MessagingService shutting down server thread.
INFO - MessagingService shutting down server thread.
[INFO] [jar:jar {execution: default-jar}]
[INFO] [jar:test-jar {execution: default}]
[INFO] Preparing source:jar
[WARNING] Removing: jar from forked lifecycle, to prevent recursive invocation.
[INFO] [enforcer:enforce {execution: enforce-maven}]
[INFO] [source:jar {execution: attach-sources}]
[INFO] Building jar: /home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/hector-object-mapper-3.1-07-SNAPSHOT-sources.jar
[INFO] [install:install {execution: default-install}]
[INFO] Installing /home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/hector-object-mapper-3.1-07-SNAPSHOT.jar to /root/.m2/repository/org/hectorclient/hector-object-mapper/3.1-07-SNAPSHOT/hector-object-mapper-3.1-07-SNAPSHOT.jar
[INFO] Installing /home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/hector-object-mapper-3.1-07-SNAPSHOT-tests.jar to /root/.m2/repository/org/hectorclient/hector-object-mapper/3.1-07-SNAPSHOT/hector-object-mapper-3.1-07-SNAPSHOT-tests.jar
[INFO] Installing /home/cesare/workspace/tesi/CassandraBM/hector/object-mapper/target/hector-object-mapper-3.1-07-SNAPSHOT-sources.jar to /root/.m2/repository/org/hectorclient/hector-object-mapper/3.1-07-SNAPSHOT/hector-object-mapper-3.1-07-SNAPSHOT-sources.jar
[INFO] 
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO] ------------------------------------------------------------------------
[INFO] hector ................................................ SUCCESS [2.460s]
[INFO] test .................................................. SUCCESS [1.210s]
[INFO] hector-core ........................................... SUCCESS [3:05.884s]
[INFO] hector-object-mapper .................................. SUCCESS [17.448s]
[INFO] ------------------------------------------------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 3 minutes 29 seconds
[INFO] Finished at: Sat Aug 11 20:02:14 CEST 2012
[INFO] Final Memory: 42M/351M
[INFO] ------------------------------------------------------------------------
