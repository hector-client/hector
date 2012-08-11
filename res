[INFO] Scanning for projects...
[INFO] Reactor build order: 
[INFO]   hector
[INFO]   test
[INFO]   hector-core
[INFO]   hector-object-mapper
[INFO] ------------------------------------------------------------------------
[INFO] Building hector
[INFO]    task-segment: [test]
[INFO] ------------------------------------------------------------------------
[INFO] [enforcer:enforce {execution: enforce-maven}]
[INFO] ------------------------------------------------------------------------
[INFO] Building test
[INFO]    task-segment: [test]
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
[INFO] ------------------------------------------------------------------------
[INFO] Building hector-core
[INFO]    task-segment: [test]
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
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.127 sec
Running me.prettyprint.hector.api.beans.DynamicCompositeTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.282 sec
Running me.prettyprint.hector.api.VirtualKeyspaceTest
17:50:16,852  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
17:50:16,874  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
17:50:17,041  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
17:50:17,224  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
17:50:17,712  INFO EmbeddedServerHelper:66 - Starting executor
17:50:17,713  INFO EmbeddedServerHelper:69 - Started executor
17:50:17,713  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
17:50:17,714  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
17:50:17,714  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter2011370259698845891.jar
17:50:17,717  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
17:50:17,732  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
17:50:17,745  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
17:50:17,746  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
17:50:17,750  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
17:50:17,875  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
17:50:17,876  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
17:50:17,921  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:17,929  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
17:50:17,932  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:17,934  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
17:50:17,960  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1508028338(78/97 serialized/live bytes, 2 ops)
17:50:17,960  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-42419992054157.log
17:50:17,961  INFO Memtable:266 - Writing Memtable-IndexInfo@1508028338(78/97 serialized/live bytes, 2 ops)
17:50:17,962  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
17:50:17,964  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-42419992054157.log
17:50:17,968  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-42419992054157.log
17:50:17,969  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
17:50:17,987  INFO StorageService:412 - Cassandra version: 1.1.0
17:50:17,988  INFO StorageService:413 - Thrift API version: 19.30.0
17:50:17,994  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
17:50:18,043  INFO StorageService:444 - Loading persisted ring state
17:50:18,046  INFO StorageService:525 - Starting up server gossip
17:50:18,055  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
17:50:18,253  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
17:50:18,255  INFO Memtable:266 - Writing Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
17:50:18,256  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
17:50:18,679  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
17:50:18,696  INFO MessagingService:284 - Starting Messaging Service on port 7070
17:50:18,704  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
17:50:18,713  WARN StorageService:633 - Generated random token PxG9rpBvqre6DZ1E. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
17:50:18,715  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1503155693(53/66 serialized/live bytes, 2 ops)
17:50:18,715  INFO Memtable:266 - Writing Memtable-LocationInfo@1503155693(53/66 serialized/live bytes, 2 ops)
17:50:18,925  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
17:50:18,929  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
17:50:18,939  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
17:50:18,940  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
17:50:18,972  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
17:50:18,976  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
17:50:18,980  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
17:50:18,980  INFO CassandraDaemon:212 - Listening for thrift clients...
17:50:20,713  INFO EmbeddedServerHelper:73 - Done sleeping
17:50:20,757  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
17:50:20,779  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
17:50:20,896  INFO NodeId:200 - No saved local node id, using newly generated: 4176be00-e3cc-11e1-0000-fe8ebeead9fb
17:50:21,547  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.935 sec
17:50:21,572  INFO CassandraDaemon:218 - Stop listening to thrift clients
17:50:21,575  INFO MessagingService:539 - Waiting for messaging service to quiesce
17:50:21,576  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.hector.api.KeyspaceCreationTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.061 sec
Running me.prettyprint.hector.api.ClockResolutionTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.244 sec
Running me.prettyprint.hector.api.HFactoryTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.12 sec
Running me.prettyprint.hector.api.CompositeTest
Tests run: 5, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 0.588 sec <<< FAILURE!
Running me.prettyprint.hector.api.ApiV2SystemTest
17:50:24,852  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
17:50:24,887  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
17:50:25,161  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
17:50:25,367  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
17:50:25,837  INFO EmbeddedServerHelper:66 - Starting executor
17:50:25,838  INFO EmbeddedServerHelper:69 - Started executor
17:50:25,839  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
17:50:25,840  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
17:50:25,840  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3659025135087835753.jar
17:50:25,843  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
17:50:25,871  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
17:50:25,893  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
17:50:25,895  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
17:50:25,902  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
17:50:26,089  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
17:50:26,090  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
17:50:26,159  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:26,171  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
17:50:26,174  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:26,177  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
17:50:26,211  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-42428116136237.log
17:50:26,214  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-42428116136237.log
17:50:26,216  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@458982688(78/97 serialized/live bytes, 2 ops)
17:50:26,217  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-42428116136237.log
17:50:26,217  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
17:50:26,218  INFO Memtable:266 - Writing Memtable-IndexInfo@458982688(78/97 serialized/live bytes, 2 ops)
17:50:26,220  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
17:50:26,236  INFO StorageService:412 - Cassandra version: 1.1.0
17:50:26,236  INFO StorageService:413 - Thrift API version: 19.30.0
17:50:26,240  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
17:50:26,301  INFO StorageService:444 - Loading persisted ring state
17:50:26,306  INFO StorageService:525 - Starting up server gossip
17:50:26,322  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
17:50:26,509  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
17:50:26,511  INFO Memtable:266 - Writing Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
17:50:26,513  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
17:50:26,770  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
17:50:26,788  INFO MessagingService:284 - Starting Messaging Service on port 7070
17:50:26,799  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
17:50:26,817  WARN StorageService:633 - Generated random token 8W0A85vJWdcUWdYi. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
17:50:26,821  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1934329031(53/66 serialized/live bytes, 2 ops)
17:50:26,822  INFO Memtable:266 - Writing Memtable-LocationInfo@1934329031(53/66 serialized/live bytes, 2 ops)
17:50:27,094  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
17:50:27,097  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
17:50:27,110  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
17:50:27,112  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
17:50:27,151  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
17:50:27,155  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
17:50:27,159  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
17:50:27,160  INFO CassandraDaemon:212 - Listening for thrift clients...
17:50:28,839  INFO EmbeddedServerHelper:73 - Done sleeping
17:50:28,883  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
17:50:28,908  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
17:50:29,039  INFO NodeId:200 - No saved local node id, using newly generated: 465143f0-e3cc-11e1-0000-fe8ebeead9db
17:50:29,603  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.024 sec
17:50:29,633  INFO CassandraDaemon:218 - Stop listening to thrift clients
17:50:29,635  INFO MessagingService:539 - Waiting for messaging service to quiesce
17:50:29,636  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.serializers.ObjectSerializerTest
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.154 sec
Running me.prettyprint.cassandra.serializers.BigIntegerSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.088 sec
Running me.prettyprint.cassandra.serializers.JaxbSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.186 sec
Running me.prettyprint.cassandra.serializers.UUIDSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.094 sec
Running me.prettyprint.cassandra.serializers.BooleanSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.089 sec
Running me.prettyprint.cassandra.serializers.DateSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.089 sec
Running me.prettyprint.cassandra.serializers.FastInfosetSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.22 sec
Running me.prettyprint.cassandra.serializers.IntegerSerializerTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.092 sec
Running me.prettyprint.cassandra.serializers.BytesArraySerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.252 sec
Running me.prettyprint.cassandra.serializers.TypeInferringSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.146 sec
Running me.prettyprint.cassandra.serializers.ShortSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.088 sec
Running me.prettyprint.cassandra.serializers.PrefixedSerializerTest
17:50:35,007 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
17:50:35,020 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
17:50:35,024 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
17:50:35,026 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
17:50:35,028 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
Tests run: 12, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.289 sec
Running me.prettyprint.cassandra.serializers.StringSerializerTest
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.31 sec
Running me.prettyprint.cassandra.serializers.LongSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.106 sec
Running me.prettyprint.cassandra.jndi.CassandraClientJndiResourceFactoryTest
17:50:36,436  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
17:50:36,463  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
17:50:36,657  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
17:50:36,853  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
17:50:37,330  INFO EmbeddedServerHelper:66 - Starting executor
17:50:37,331  INFO EmbeddedServerHelper:69 - Started executor
17:50:37,331  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
17:50:37,332  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
17:50:37,332  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3140777081366356775.jar
17:50:37,334  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
17:50:37,349  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
17:50:37,362  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
17:50:37,363  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
17:50:37,367  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
17:50:37,491  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
17:50:37,492  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
17:50:37,533  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:37,540  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
17:50:37,543  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:37,545  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
17:50:37,564  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@378423301(39/48 serialized/live bytes, 1 ops)
17:50:37,567  INFO Memtable:266 - Writing Memtable-IndexInfo@378423301(39/48 serialized/live bytes, 1 ops)
17:50:37,570  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-42439610661141.log
17:50:37,572  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@239228739(39/48 serialized/live bytes, 1 ops)
17:50:37,574  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-42439610661141.log
17:50:37,581  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-42439610661141.log
17:50:37,581  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
17:50:37,627  INFO StorageService:412 - Cassandra version: 1.1.0
17:50:37,627  INFO StorageService:413 - Thrift API version: 19.30.0
17:50:37,632  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
17:50:37,679  INFO StorageService:444 - Loading persisted ring state
17:50:37,683  INFO StorageService:525 - Starting up server gossip
17:50:37,697  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
17:50:37,903  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
17:50:37,906  INFO Memtable:266 - Writing Memtable-IndexInfo@239228739(39/48 serialized/live bytes, 1 ops)
17:50:37,908  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
17:50:38,130  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
17:50:38,131  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
17:50:38,132  INFO Memtable:266 - Writing Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
17:50:38,667  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
17:50:38,687  INFO MessagingService:284 - Starting Messaging Service on port 7070
17:50:38,693  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
17:50:38,702  WARN StorageService:633 - Generated random token y8ziny3uT454ObUe. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
17:50:38,704  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2061551555(53/66 serialized/live bytes, 2 ops)
17:50:38,705  INFO Memtable:266 - Writing Memtable-LocationInfo@2061551555(53/66 serialized/live bytes, 2 ops)
17:50:39,059  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
17:50:39,065  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
17:50:39,076  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
17:50:39,077  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
17:50:39,112  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
17:50:39,116  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
17:50:39,120  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
17:50:39,120  INFO CassandraDaemon:212 - Listening for thrift clients...
17:50:40,331  INFO EmbeddedServerHelper:73 - Done sleeping
17:50:40,610  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
17:50:40,680  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
17:50:40,712  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.49 sec
17:50:40,785  INFO CassandraDaemon:218 - Stop listening to thrift clients
17:50:40,787  INFO MessagingService:539 - Waiting for messaging service to quiesce
17:50:40,791  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.dao.SimpleCassandraDaoTest
17:50:42,054  INFO TestContextManager:185 - @TestExecutionListeners is not present for class [class me.prettyprint.cassandra.dao.SimpleCassandraDaoTest]: using defaults.
17:50:42,120  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
17:50:42,183  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
17:50:42,393  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
17:50:42,634  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
17:50:43,153  INFO EmbeddedServerHelper:66 - Starting executor
17:50:43,155  INFO EmbeddedServerHelper:69 - Started executor
17:50:43,156  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
17:50:43,157  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
17:50:43,157  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter5590815160709876671.jar
17:50:43,161  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
17:50:43,178  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
17:50:43,192  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
17:50:43,193  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
17:50:43,198  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
17:50:43,337  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
17:50:43,340  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
17:50:43,384  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:43,393  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
17:50:43,396  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:43,398  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
17:50:43,421  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
17:50:43,422  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-42445430175871.log
17:50:43,424  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1522338280(78/97 serialized/live bytes, 2 ops)
17:50:43,426  INFO Memtable:266 - Writing Memtable-IndexInfo@1522338280(78/97 serialized/live bytes, 2 ops)
17:50:43,432  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-42445430175871.log
17:50:43,436  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-42445430175871.log
17:50:43,437  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
17:50:43,456  INFO StorageService:412 - Cassandra version: 1.1.0
17:50:43,456  INFO StorageService:413 - Thrift API version: 19.30.0
17:50:43,462  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
17:50:43,516  INFO StorageService:444 - Loading persisted ring state
17:50:43,519  INFO StorageService:525 - Starting up server gossip
17:50:43,531  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@280564915(126/157 serialized/live bytes, 3 ops)
17:50:43,705  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
17:50:43,708  INFO Memtable:266 - Writing Memtable-LocationInfo@280564915(126/157 serialized/live bytes, 3 ops)
17:50:43,709  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
17:50:43,939  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
17:50:43,958  INFO MessagingService:284 - Starting Messaging Service on port 7070
17:50:43,966  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
17:50:43,975  WARN StorageService:633 - Generated random token s0TBNFNWlnNYuylK. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
17:50:43,977  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1278499651(53/66 serialized/live bytes, 2 ops)
17:50:43,977  INFO Memtable:266 - Writing Memtable-LocationInfo@1278499651(53/66 serialized/live bytes, 2 ops)
17:50:44,208  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
17:50:44,212  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
17:50:44,223  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
17:50:44,224  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
17:50:44,259  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
17:50:44,263  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
17:50:44,267  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
17:50:44,267  INFO CassandraDaemon:212 - Listening for thrift clients...
17:50:46,156  INFO EmbeddedServerHelper:73 - Done sleeping
17:50:46,248  INFO XmlBeanDefinitionReader:315 - Loading XML bean definitions from class path resource [cassandra-context-test-v2.xml]
17:50:46,419  INFO GenericApplicationContext:456 - Refreshing org.springframework.context.support.GenericApplicationContext@788390b0: startup date [Sat Aug 11 17:50:46 CEST 2012]; root of context hierarchy
17:50:46,583  INFO DefaultListableBeanFactory:557 - Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@1a5e68a: defining beans [cassandraHostConfigurator,cluster,consistencyLevelPolicy,keyspaceOperator,simpleCassandraDao,org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor]; root of factory hierarchy
17:50:46,648  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
17:50:46,681  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
17:50:46,875  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.232 sec
17:50:46,893  INFO GenericApplicationContext:1002 - Closing org.springframework.context.support.GenericApplicationContext@788390b0: startup date [Sat Aug 11 17:50:46 CEST 2012]; root of context hierarchy
17:50:46,897  INFO CassandraDaemon:218 - Stop listening to thrift clients
17:50:46,897  INFO DefaultListableBeanFactory:422 - Destroying singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@1a5e68a: defining beans [cassandraHostConfigurator,cluster,consistencyLevelPolicy,keyspaceOperator,simpleCassandraDao,org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor]; root of factory hierarchy
17:50:46,910  INFO MessagingService:539 - Waiting for messaging service to quiesce
17:50:46,911  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.ConfigurableConsistencyLevelTest
17:50:47,881  INFO ConfigurableConsistencyLevel:57 - READ ConsistencyLevel set to ANY for ColumnFamily OtherCf
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.277 sec
Running me.prettyprint.cassandra.model.RangeSlicesQueryTest
17:50:48,396  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
17:50:48,421  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
17:50:48,617  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
17:50:48,788  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
17:50:49,224  INFO EmbeddedServerHelper:66 - Starting executor
17:50:49,225  INFO EmbeddedServerHelper:69 - Started executor
17:50:49,226  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
17:50:49,227  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
17:50:49,227  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter1690791731132095516.jar
17:50:49,230  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
17:50:49,247  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
17:50:49,260  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
17:50:49,261  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
17:50:49,266  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
17:50:49,392  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
17:50:49,393  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
17:50:49,435  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:49,444  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
17:50:49,447  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:49,450  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
17:50:49,471  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-42451500476796.log
17:50:49,473  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-42451500476796.log
17:50:49,477  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-42451500476796.log
17:50:49,478  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
17:50:49,487  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@458982688(78/97 serialized/live bytes, 2 ops)
17:50:49,489  INFO Memtable:266 - Writing Memtable-IndexInfo@458982688(78/97 serialized/live bytes, 2 ops)
17:50:49,490  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
17:50:49,505  INFO StorageService:412 - Cassandra version: 1.1.0
17:50:49,505  INFO StorageService:413 - Thrift API version: 19.30.0
17:50:49,509  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
17:50:49,584  INFO StorageService:444 - Loading persisted ring state
17:50:49,589  INFO StorageService:525 - Starting up server gossip
17:50:49,605  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@664219194(126/157 serialized/live bytes, 3 ops)
17:50:49,791  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
17:50:49,794  INFO Memtable:266 - Writing Memtable-LocationInfo@664219194(126/157 serialized/live bytes, 3 ops)
17:50:49,796  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
17:50:50,018  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
17:50:50,038  INFO MessagingService:284 - Starting Messaging Service on port 7070
17:50:50,044  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
17:50:50,053  WARN StorageService:633 - Generated random token c0zWtPMRHbR548er. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
17:50:50,055  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1609377764(53/66 serialized/live bytes, 2 ops)
17:50:50,055  INFO Memtable:266 - Writing Memtable-LocationInfo@1609377764(53/66 serialized/live bytes, 2 ops)
17:50:50,255  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
17:50:50,259  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
17:50:50,270  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
17:50:50,272  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
17:50:50,304  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
17:50:50,308  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
17:50:50,311  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
17:50:50,312  INFO CassandraDaemon:212 - Listening for thrift clients...
17:50:52,226  INFO EmbeddedServerHelper:73 - Done sleeping
17:50:52,265  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
17:50:52,285  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
17:50:52,587  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.412 sec
17:50:52,604  INFO CassandraDaemon:218 - Stop listening to thrift clients
17:50:52,607  INFO MessagingService:539 - Waiting for messaging service to quiesce
17:50:52,609  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.GetSliceQueryTest
17:50:53,557  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
17:50:53,582  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
17:50:53,773  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
17:50:53,959  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
17:50:54,392  INFO EmbeddedServerHelper:66 - Starting executor
17:50:54,394  INFO EmbeddedServerHelper:69 - Started executor
17:50:54,394  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
17:50:54,395  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
17:50:54,395  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter9217236078051749646.jar
17:50:54,398  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
17:50:54,417  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
17:50:54,429  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
17:50:54,430  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
17:50:54,434  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
17:50:54,581  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
17:50:54,581  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
17:50:54,622  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:54,630  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
17:50:54,634  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:54,636  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
17:50:54,658  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-42456666164697.log
17:50:54,660  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@404454518(78/97 serialized/live bytes, 2 ops)
17:50:54,661  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-42456666164697.log
17:50:54,661  INFO Memtable:266 - Writing Memtable-IndexInfo@404454518(78/97 serialized/live bytes, 2 ops)
17:50:54,662  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
17:50:54,665  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-42456666164697.log
17:50:54,665  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
17:50:54,680  INFO StorageService:412 - Cassandra version: 1.1.0
17:50:54,681  INFO StorageService:413 - Thrift API version: 19.30.0
17:50:54,685  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
17:50:54,734  INFO StorageService:444 - Loading persisted ring state
17:50:54,738  INFO StorageService:525 - Starting up server gossip
17:50:54,749  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
17:50:54,997  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
17:50:54,999  INFO Memtable:266 - Writing Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
17:50:55,000  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
17:50:55,200  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
17:50:55,215  INFO MessagingService:284 - Starting Messaging Service on port 7070
17:50:55,222  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
17:50:55,230  WARN StorageService:633 - Generated random token rGhUHoyIi9OKRcXI. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
17:50:55,232  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1255545583(53/66 serialized/live bytes, 2 ops)
17:50:55,233  INFO Memtable:266 - Writing Memtable-LocationInfo@1255545583(53/66 serialized/live bytes, 2 ops)
17:50:55,457  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
17:50:55,464  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
17:50:55,477  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
17:50:55,479  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
17:50:55,526  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
17:50:55,531  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
17:50:55,540  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
17:50:55,540  INFO CassandraDaemon:212 - Listening for thrift clients...
17:50:57,394  INFO EmbeddedServerHelper:73 - Done sleeping
17:50:57,436  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
17:50:57,443  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
17:50:57,609  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.259 sec
17:50:57,631  INFO CassandraDaemon:218 - Stop listening to thrift clients
17:50:57,633  INFO MessagingService:539 - Waiting for messaging service to quiesce
17:50:57,633  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.MultigetCountQueryTest
17:50:58,647  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
17:50:58,677  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
17:50:58,857  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
17:50:59,130  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
17:50:59,573  INFO EmbeddedServerHelper:66 - Starting executor
17:50:59,575  INFO EmbeddedServerHelper:69 - Started executor
17:50:59,575  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
17:50:59,576  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
17:50:59,576  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter4228172657440775965.jar
17:50:59,578  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
17:50:59,594  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
17:50:59,614  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
17:50:59,615  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
17:50:59,620  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
17:50:59,763  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
17:50:59,763  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
17:50:59,805  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:59,813  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
17:50:59,816  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:50:59,818  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
17:50:59,840  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@192714409(78/97 serialized/live bytes, 2 ops)
17:50:59,841  INFO Memtable:266 - Writing Memtable-IndexInfo@192714409(78/97 serialized/live bytes, 2 ops)
17:50:59,842  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
17:50:59,842  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-42461856123093.log
17:50:59,850  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-42461856123093.log
17:50:59,854  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-42461856123093.log
17:50:59,855  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
17:50:59,873  INFO StorageService:412 - Cassandra version: 1.1.0
17:50:59,873  INFO StorageService:413 - Thrift API version: 19.30.0
17:50:59,877  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
17:50:59,923  INFO StorageService:444 - Loading persisted ring state
17:50:59,926  INFO StorageService:525 - Starting up server gossip
17:50:59,936  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
17:51:00,110  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
17:51:00,112  INFO Memtable:266 - Writing Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
17:51:00,113  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
17:51:00,324  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
17:51:00,346  INFO MessagingService:284 - Starting Messaging Service on port 7070
17:51:00,353  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
17:51:00,365  WARN StorageService:633 - Generated random token bbc910wQ3ylVG3ea. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
17:51:00,367  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
17:51:00,368  INFO Memtable:266 - Writing Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
17:51:00,560  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
17:51:00,564  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
17:51:00,576  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
17:51:00,579  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
17:51:00,617  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
17:51:00,621  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
17:51:00,625  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
17:51:00,626  INFO CassandraDaemon:212 - Listening for thrift clients...
17:51:02,576  INFO EmbeddedServerHelper:73 - Done sleeping
17:51:02,634  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
17:51:02,672  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
17:51:02,867  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.433 sec
17:51:02,884  INFO CassandraDaemon:218 - Stop listening to thrift clients
17:51:02,886  INFO MessagingService:539 - Waiting for messaging service to quiesce
17:51:02,887  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.CqlQueryTest
17:51:03,998  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
17:51:04,029  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
17:51:04,223  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
17:51:04,399  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
17:51:04,836  INFO EmbeddedServerHelper:66 - Starting executor
17:51:04,838  INFO EmbeddedServerHelper:69 - Started executor
17:51:04,838  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
17:51:04,839  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
17:51:04,839  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter6823322534391509766.jar
17:51:04,841  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
17:51:04,862  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
17:51:04,878  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
17:51:04,880  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
17:51:04,886  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
17:51:05,044  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
17:51:05,045  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
17:51:05,095  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:51:05,112  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
17:51:05,115  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
17:51:05,118  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
17:51:05,143  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@2137552888(39/48 serialized/live bytes, 1 ops)
17:51:05,146  INFO Memtable:266 - Writing Memtable-IndexInfo@2137552888(39/48 serialized/live bytes, 1 ops)
17:51:05,149  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-42467114303472.log
17:51:05,150  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@223489506(39/48 serialized/live bytes, 1 ops)
17:51:05,161  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-42467114303472.log
17:51:05,164  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-42467114303472.log
17:51:05,165  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
17:51:05,187  INFO StorageService:412 - Cassandra version: 1.1.0
17:51:05,187  INFO StorageService:413 - Thrift API version: 19.30.0
17:51:05,190  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
17:51:05,251  INFO StorageService:444 - Loading persisted ring state
17:51:05,256  INFO StorageService:525 - Starting up server gossip
17:51:05,275  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@194337136(126/157 serialized/live bytes, 3 ops)
17:51:05,485  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
17:51:05,489  INFO Memtable:266 - Writing Memtable-IndexInfo@223489506(39/48 serialized/live bytes, 1 ops)
17:51:05,490  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
17:51:05,773  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
17:51:05,774  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
17:51:05,775  INFO Memtable:266 - Writing Memtable-LocationInfo@194337136(126/157 serialized/live bytes, 3 ops)
17:51:06,053  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
17:51:06,070  INFO MessagingService:284 - Starting Messaging Service on port 7070
17:51:06,080  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
17:51:06,091  WARN StorageService:633 - Generated random token zIgVNYvQxwDm6hFJ. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
17:51:06,093  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@540108943(53/66 serialized/live bytes, 2 ops)
17:51:06,094  INFO Memtable:266 - Writing Memtable-LocationInfo@540108943(53/66 serialized/live bytes, 2 ops)
17:51:06,337  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
17:51:06,343  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
17:51:06,352  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
17:51:06,353  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
17:51:06,385  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
17:51:06,389  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
17:51:06,393  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
17:51:06,393  INFO CassandraDaemon:212 - Listening for thrift clients...
17:51:07,839  INFO EmbeddedServerHelper:73 - Done sleeping
17:51:07,886  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
17:51:07,910  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
17:51:08,212  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.523 sec
17:51:08,231  INFO CassandraDaemon:218 - Stop listening to thrift clients
17:51:08,233  INFO MessagingService:539 - Waiting for messaging service to quiesce
17:51:08,236  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.RangeSlicesCounterQueryTest
17:51:09,241  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
17:51:09,265  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
17:51:09,534  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
17:51:09,716  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
17:51:10,154  INFO EmbeddedServerHelper:66 - Starting executor
17:51:10,155  INFO EmbeddedServerHelper:69 - Started executor
17:51:10,155  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
17:51:10,155  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
17:51:10,155  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter4365858453041506787.jar
17:51:10,157  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
17:51:10,175  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
17:51:10,188  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
17:51:10,189  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
17:51:10,193  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
