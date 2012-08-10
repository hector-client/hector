sudo mvn test
[sudo] password for cesare: 
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
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.214 sec
Running me.prettyprint.hector.api.beans.DynamicCompositeTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.687 sec
Running me.prettyprint.hector.api.VirtualKeyspaceTest
18:00:37,288  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:00:37,580  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:00:38,000  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:00:38,392  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:00:40,450  INFO EmbeddedServerHelper:66 - Starting executor
18:00:40,468  INFO EmbeddedServerHelper:69 - Started executor
18:00:40,469  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:00:40,469  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:00:40,469  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter531277650335534209.jar
18:00:40,471  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:00:40,490  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:00:40,554  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:00:40,555  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:00:40,559  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:00:40,774  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:00:40,805  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:00:40,878  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:00:40,879  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:00:40,948  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:00:40,958  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:00:40,961  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:00:40,970  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:00:40,993  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:00:40,996  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18108199166159.log
18:00:40,997  INFO Memtable:266 - Writing Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:00:41,002  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1380741586(39/48 serialized/live bytes, 1 ops)
18:00:41,003  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18108199166159.log
18:00:41,055  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18108199166159.log
18:00:41,056  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:00:41,082  INFO StorageService:412 - Cassandra version: 1.1.0
18:00:41,082  INFO StorageService:413 - Thrift API version: 19.30.0
18:00:41,086  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:00:41,174  INFO StorageService:444 - Loading persisted ring state
18:00:41,177  INFO StorageService:525 - Starting up server gossip
18:00:41,188  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@298922610(126/157 serialized/live bytes, 3 ops)
18:00:41,570  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:00:41,573  INFO Memtable:266 - Writing Memtable-IndexInfo@1380741586(39/48 serialized/live bytes, 1 ops)
18:00:41,574  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:00:41,864  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:00:41,865  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:00:41,866  INFO Memtable:266 - Writing Memtable-LocationInfo@298922610(126/157 serialized/live bytes, 3 ops)
18:00:42,134  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:00:42,173  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:00:42,185  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:00:42,195  WARN StorageService:633 - Generated random token EypnhZPURmxJNpF2. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:00:42,197  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@921400113(53/66 serialized/live bytes, 2 ops)
18:00:42,197  INFO Memtable:266 - Writing Memtable-LocationInfo@921400113(53/66 serialized/live bytes, 2 ops)
18:00:42,470  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:00:42,473  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:00:42,482  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:00:42,484  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:00:42,541  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:00:42,544  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:00:42,548  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:00:42,549  INFO CassandraDaemon:212 - Listening for thrift clients...
18:00:43,469  INFO EmbeddedServerHelper:73 - Done sleeping
18:00:43,641  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:00:43,732  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:00:44,014  INFO NodeId:200 - No saved local node id, using newly generated: 0cbd80e0-dbf2-11e1-0000-fe8ebeead9df
18:00:44,714  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 7.721 sec
18:00:44,738  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:00:44,741  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:00:44,743  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.hector.api.KeyspaceCreationTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.051 sec
Running me.prettyprint.hector.api.ClockResolutionTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.237 sec
Running me.prettyprint.hector.api.HFactoryTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.133 sec
Running me.prettyprint.hector.api.CompositeTest
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.526 sec
Running me.prettyprint.hector.api.ApiV2SystemTest
18:00:47,822  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:00:47,844  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:00:48,047  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:00:48,229  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:00:48,655  INFO EmbeddedServerHelper:66 - Starting executor
18:00:48,656  INFO EmbeddedServerHelper:69 - Started executor
18:00:48,657  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:00:48,658  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:00:48,658  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter6540172673545548189.jar
18:00:48,661  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:00:48,681  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:00:48,694  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:00:48,695  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:00:48,699  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:00:48,783  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:00:48,797  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:00:48,845  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:00:48,846  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:00:48,890  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:00:48,898  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:00:48,900  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:00:48,902  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:00:48,929  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18116492380596.log
18:00:48,929  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@404454518(39/48 serialized/live bytes, 1 ops)
18:00:48,931  INFO Memtable:266 - Writing Memtable-IndexInfo@404454518(39/48 serialized/live bytes, 1 ops)
18:00:48,932  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18116492380596.log
18:00:48,936  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1152296720(39/48 serialized/live bytes, 1 ops)
18:00:48,936  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18116492380596.log
18:00:48,937  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:00:48,960  INFO StorageService:412 - Cassandra version: 1.1.0
18:00:48,961  INFO StorageService:413 - Thrift API version: 19.30.0
18:00:48,966  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:00:49,017  INFO StorageService:444 - Loading persisted ring state
18:00:49,020  INFO StorageService:525 - Starting up server gossip
18:00:49,030  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:00:49,373  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:00:49,375  INFO Memtable:266 - Writing Memtable-IndexInfo@1152296720(39/48 serialized/live bytes, 1 ops)
18:00:49,377  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:00:49,652  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:00:49,653  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:00:49,653  INFO Memtable:266 - Writing Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:00:49,965  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:00:49,978  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:00:49,985  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:00:49,994  WARN StorageService:633 - Generated random token Bh7REq6AQ8o7Mqqo. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:00:49,996  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2013151593(53/66 serialized/live bytes, 2 ops)
18:00:49,996  INFO Memtable:266 - Writing Memtable-LocationInfo@2013151593(53/66 serialized/live bytes, 2 ops)
18:00:50,280  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:00:50,285  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:00:50,295  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:00:50,297  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:00:50,331  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:00:50,335  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:00:50,339  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:00:50,339  INFO CassandraDaemon:212 - Listening for thrift clients...
18:00:51,657  INFO EmbeddedServerHelper:73 - Done sleeping
18:00:51,701  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:00:51,733  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:00:51,848  INFO NodeId:200 - No saved local node id, using newly generated: 1168e080-dbf2-11e1-0000-fe8ebeead9fb
18:00:52,329  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.743 sec
18:00:52,347  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:00:52,351  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:00:52,353  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.serializers.ObjectSerializerTest
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.294 sec
Running me.prettyprint.cassandra.serializers.BigIntegerSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.085 sec
Running me.prettyprint.cassandra.serializers.JaxbSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.382 sec
Running me.prettyprint.cassandra.serializers.UUIDSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.101 sec
Running me.prettyprint.cassandra.serializers.BooleanSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.088 sec
Running me.prettyprint.cassandra.serializers.DateSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.095 sec
Running me.prettyprint.cassandra.serializers.FastInfosetSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.29 sec
Running me.prettyprint.cassandra.serializers.IntegerSerializerTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.097 sec
Running me.prettyprint.cassandra.serializers.BytesArraySerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.227 sec
Running me.prettyprint.cassandra.serializers.TypeInferringSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.379 sec
Running me.prettyprint.cassandra.serializers.ShortSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.101 sec
Running me.prettyprint.cassandra.serializers.PrefixedSerializerTest
18:00:58,540 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
18:00:58,553 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
18:00:58,558 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
18:00:58,561 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
18:00:58,565 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
Tests run: 12, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.301 sec
Running me.prettyprint.cassandra.serializers.StringSerializerTest
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.326 sec
Running me.prettyprint.cassandra.serializers.LongSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.111 sec
Running me.prettyprint.cassandra.jndi.CassandraClientJndiResourceFactoryTest
18:01:00,354  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:00,389  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:00,577  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:00,759  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:01,210  INFO EmbeddedServerHelper:66 - Starting executor
18:01:01,211  INFO EmbeddedServerHelper:69 - Started executor
18:01:01,211  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:01,212  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:01,212  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3957556590079579457.jar
18:01:01,215  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:01,234  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:01,247  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:01,248  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:01,252  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:01,336  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:01,355  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:01,409  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:01,411  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:01,463  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:01,471  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:01,474  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:01,477  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:01,501  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18129040537373.log
18:01:01,505  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18129040537373.log
18:01:01,508  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18129040537373.log
18:01:01,509  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:01,511  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@458982688(78/97 serialized/live bytes, 2 ops)
18:01:01,512  INFO Memtable:266 - Writing Memtable-IndexInfo@458982688(78/97 serialized/live bytes, 2 ops)
18:01:01,512  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:01,526  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:01,527  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:01,534  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:01,589  INFO StorageService:444 - Loading persisted ring state
18:01:01,593  INFO StorageService:525 - Starting up server gossip
18:01:01,604  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:01:02,211  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:01:02,215  INFO Memtable:266 - Writing Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:01:02,216  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:02,471  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:02,489  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:02,495  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:02,504  WARN StorageService:633 - Generated random token 2BTUPeIyrIP0xm0C. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:02,506  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@540108943(53/66 serialized/live bytes, 2 ops)
18:01:02,506  INFO Memtable:266 - Writing Memtable-LocationInfo@540108943(53/66 serialized/live bytes, 2 ops)
18:01:02,762  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:02,766  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:02,775  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:02,777  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:02,818  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:02,821  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:02,825  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:02,826  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:04,212  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:04,531  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:04,596  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:01:04,634  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.65 sec
18:01:04,675  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:04,677  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:04,677  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.dao.SimpleCassandraDaoTest
18:01:05,871  INFO TestContextManager:185 - @TestExecutionListeners is not present for class [class me.prettyprint.cassandra.dao.SimpleCassandraDaoTest]: using defaults.
18:01:05,939  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:05,969  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:06,132  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:06,294  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:06,756  INFO EmbeddedServerHelper:66 - Starting executor
18:01:06,757  INFO EmbeddedServerHelper:69 - Started executor
18:01:06,757  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:06,758  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:06,758  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter6310978130335924458.jar
18:01:06,761  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:06,776  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:06,790  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:06,791  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:06,796  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:06,879  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:06,894  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:06,936  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:06,937  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:06,981  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:06,989  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:06,992  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:06,996  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:07,018  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18134566420095.log
18:01:07,021  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18134566420095.log
18:01:07,024  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18134566420095.log
18:01:07,024  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@153523906(78/97 serialized/live bytes, 2 ops)
18:01:07,024  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:07,026  INFO Memtable:266 - Writing Memtable-IndexInfo@153523906(78/97 serialized/live bytes, 2 ops)
18:01:07,028  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:07,047  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:07,048  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:07,053  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:07,100  INFO StorageService:444 - Loading persisted ring state
18:01:07,103  INFO StorageService:525 - Starting up server gossip
18:01:07,114  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2137169521(126/157 serialized/live bytes, 3 ops)
18:01:07,426  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:01:07,428  INFO Memtable:266 - Writing Memtable-LocationInfo@2137169521(126/157 serialized/live bytes, 3 ops)
18:01:07,429  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:07,707  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:07,726  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:07,735  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:07,745  WARN StorageService:633 - Generated random token Ve4D80MU2YvdJZoF. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:07,748  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1546386943(53/66 serialized/live bytes, 2 ops)
18:01:07,748  INFO Memtable:266 - Writing Memtable-LocationInfo@1546386943(53/66 serialized/live bytes, 2 ops)
18:01:08,112  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:08,117  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:08,131  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:08,133  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:08,168  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:08,171  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:08,175  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:08,175  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:09,758  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:10,004  INFO XmlBeanDefinitionReader:315 - Loading XML bean definitions from class path resource [cassandra-context-test-v2.xml]
18:01:10,379  INFO GenericApplicationContext:456 - Refreshing org.springframework.context.support.GenericApplicationContext@459d3b3a: startup date [Wed Aug 01 18:01:10 CEST 2012]; root of context hierarchy
18:01:10,641  INFO DefaultListableBeanFactory:557 - Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@6a69ed4a: defining beans [cassandraHostConfigurator,cluster,consistencyLevelPolicy,keyspaceOperator,simpleCassandraDao,org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor]; root of factory hierarchy
18:01:10,777  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:10,799  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:01:11,008  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.523 sec
18:01:11,033  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:11,037  INFO GenericApplicationContext:1002 - Closing org.springframework.context.support.GenericApplicationContext@459d3b3a: startup date [Wed Aug 01 18:01:10 CEST 2012]; root of context hierarchy
18:01:11,039  INFO DefaultListableBeanFactory:422 - Destroying singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@6a69ed4a: defining beans [cassandraHostConfigurator,cluster,consistencyLevelPolicy,keyspaceOperator,simpleCassandraDao,org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor]; root of factory hierarchy
18:01:11,039  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:11,042  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.ConfigurableConsistencyLevelTest
18:01:12,147  INFO ConfigurableConsistencyLevel:57 - READ ConsistencyLevel set to ANY for ColumnFamily OtherCf
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.216 sec
Running me.prettyprint.cassandra.model.RangeSlicesQueryTest
18:01:12,733  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:12,762  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:12,956  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:13,127  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:13,556  INFO EmbeddedServerHelper:66 - Starting executor
18:01:13,558  INFO EmbeddedServerHelper:69 - Started executor
18:01:13,558  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:13,558  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:13,558  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter5375038015584976331.jar
18:01:13,560  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:13,581  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:13,595  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:13,596  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:13,600  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:13,694  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:13,707  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:13,752  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:13,753  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:13,795  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:13,803  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:13,805  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:13,807  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:13,830  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18141386769547.log
18:01:13,832  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18141386769547.log
18:01:13,836  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@881341271(78/97 serialized/live bytes, 2 ops)
18:01:13,836  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18141386769547.log
18:01:13,837  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:13,837  INFO Memtable:266 - Writing Memtable-IndexInfo@881341271(78/97 serialized/live bytes, 2 ops)
18:01:13,838  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:13,853  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:13,853  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:13,857  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:13,917  INFO StorageService:444 - Loading persisted ring state
18:01:13,920  INFO StorageService:525 - Starting up server gossip
18:01:13,931  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
18:01:14,192  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:01:14,194  INFO Memtable:266 - Writing Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
18:01:14,195  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:14,481  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:14,498  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:14,504  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:14,512  WARN StorageService:633 - Generated random token dDoTD2ojEBBtmJti. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:14,514  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
18:01:14,515  INFO Memtable:266 - Writing Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
18:01:14,762  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:14,765  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:14,775  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:14,776  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:14,809  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:14,813  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:14,817  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:14,817  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:16,558  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:16,596  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:16,619  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:16,928  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.5 sec
18:01:16,946  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:16,948  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:16,950  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.GetSliceQueryTest
18:01:17,950  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:17,975  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:18,171  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:18,352  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:18,798  INFO EmbeddedServerHelper:66 - Starting executor
18:01:18,799  INFO EmbeddedServerHelper:69 - Started executor
18:01:18,799  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:18,799  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:18,800  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter6078657981519629536.jar
18:01:18,802  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:18,819  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:18,832  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:18,833  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:18,837  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:18,938  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:18,951  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:18,993  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:18,994  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:19,036  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:19,044  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:19,047  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:19,052  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:19,069  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18146637357910.log
18:01:19,071  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@881341271(39/48 serialized/live bytes, 1 ops)
18:01:19,071  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18146637357910.log
18:01:19,072  INFO Memtable:266 - Writing Memtable-IndexInfo@881341271(39/48 serialized/live bytes, 1 ops)
18:01:19,075  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@871992664(39/48 serialized/live bytes, 1 ops)
18:01:19,075  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18146637357910.log
18:01:19,075  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:19,091  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:19,091  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:19,095  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:19,143  INFO StorageService:444 - Loading persisted ring state
18:01:19,146  INFO StorageService:525 - Starting up server gossip
18:01:19,156  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
18:01:19,547  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:19,549  INFO Memtable:266 - Writing Memtable-IndexInfo@871992664(39/48 serialized/live bytes, 1 ops)
18:01:19,550  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:19,797  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:19,798  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:19,799  INFO Memtable:266 - Writing Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
18:01:20,056  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:20,079  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:20,086  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:20,095  WARN StorageService:633 - Generated random token FUsza4mgbwQ6aYS8. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:20,097  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2013151593(53/66 serialized/live bytes, 2 ops)
18:01:20,098  INFO Memtable:266 - Writing Memtable-LocationInfo@2013151593(53/66 serialized/live bytes, 2 ops)
18:01:20,360  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:20,363  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:20,373  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:20,375  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:20,412  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:20,416  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:20,419  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:20,420  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:21,799  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:21,832  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:21,839  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:22,003  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.269 sec
18:01:22,022  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:22,024  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:22,025  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.MultigetCountQueryTest
18:01:23,059  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:23,082  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:23,278  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:23,454  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:23,882  INFO EmbeddedServerHelper:66 - Starting executor
18:01:23,883  INFO EmbeddedServerHelper:69 - Started executor
18:01:23,883  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:23,883  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:23,884  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3915649142378393915.jar
18:01:23,886  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:23,903  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:23,916  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:23,917  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:23,921  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:24,017  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:24,031  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:24,079  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:24,080  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:24,122  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:24,130  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:24,133  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:24,136  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:24,155  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@160094946(39/48 serialized/live bytes, 1 ops)
18:01:24,157  INFO Memtable:266 - Writing Memtable-IndexInfo@160094946(39/48 serialized/live bytes, 1 ops)
18:01:24,166  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1235930463(39/48 serialized/live bytes, 1 ops)
18:01:24,168  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18151718625359.log
18:01:24,174  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18151718625359.log
18:01:24,180  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18151718625359.log
18:01:24,181  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:24,198  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:24,198  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:24,202  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:24,247  INFO StorageService:444 - Loading persisted ring state
18:01:24,251  INFO StorageService:525 - Starting up server gossip
18:01:24,261  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
18:01:24,525  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:24,527  INFO Memtable:266 - Writing Memtable-IndexInfo@1235930463(39/48 serialized/live bytes, 1 ops)
18:01:24,529  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:24,861  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:24,862  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:24,863  INFO Memtable:266 - Writing Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
18:01:25,120  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:25,133  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:25,140  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:25,148  WARN StorageService:633 - Generated random token xDmK0wSYqaKTRAQ7. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:25,150  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@197629132(53/66 serialized/live bytes, 2 ops)
18:01:25,151  INFO Memtable:266 - Writing Memtable-LocationInfo@197629132(53/66 serialized/live bytes, 2 ops)
18:01:25,422  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:25,425  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:25,435  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:25,436  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:25,468  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:25,472  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:25,476  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:25,476  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:26,883  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:26,932  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:26,955  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:01:27,145  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.304 sec
18:01:27,168  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:27,169  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:27,170  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.CqlQueryTest
18:01:28,265  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:28,290  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:28,487  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:28,670  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:29,107  INFO EmbeddedServerHelper:66 - Starting executor
18:01:29,108  INFO EmbeddedServerHelper:69 - Started executor
18:01:29,109  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:29,109  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:29,109  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter1250493279285947270.jar
18:01:29,112  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:29,127  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:29,140  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:29,141  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:29,145  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:29,238  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:29,252  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:29,300  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:29,301  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:29,343  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:29,351  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:29,354  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:29,356  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:29,379  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18156942661387.log
18:01:29,381  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18156942661387.log
18:01:29,385  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18156942661387.log
18:01:29,385  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@360777192(39/48 serialized/live bytes, 1 ops)
18:01:29,386  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:29,387  INFO Memtable:266 - Writing Memtable-IndexInfo@360777192(39/48 serialized/live bytes, 1 ops)
18:01:29,395  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@471860896(39/48 serialized/live bytes, 1 ops)
18:01:29,404  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:29,404  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:29,409  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:29,464  INFO StorageService:444 - Loading persisted ring state
18:01:29,468  INFO StorageService:525 - Starting up server gossip
18:01:29,478  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
18:01:29,727  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:29,732  INFO Memtable:266 - Writing Memtable-IndexInfo@471860896(39/48 serialized/live bytes, 1 ops)
18:01:29,733  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:30,036  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:30,037  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:30,037  INFO Memtable:266 - Writing Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
18:01:30,307  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:30,319  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:30,325  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:30,334  WARN StorageService:633 - Generated random token NeOkeV1Z3BvUcV01. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:30,336  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1543103262(53/66 serialized/live bytes, 2 ops)
18:01:30,340  INFO Memtable:266 - Writing Memtable-LocationInfo@1543103262(53/66 serialized/live bytes, 2 ops)
18:01:30,588  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:30,591  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:30,601  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:30,603  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:30,636  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:30,640  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:30,644  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:30,644  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:32,109  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:32,145  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:32,175  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:32,484  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.442 sec
18:01:32,502  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:32,503  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:32,504  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.RangeSlicesCounterQueryTest
18:01:33,469  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:33,493  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:33,687  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:33,865  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:34,301  INFO EmbeddedServerHelper:66 - Starting executor
18:01:34,303  INFO EmbeddedServerHelper:69 - Started executor
18:01:34,303  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:34,304  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:34,304  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3302378188665525607.jar
18:01:34,306  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:34,323  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:34,336  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:34,337  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:34,341  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:34,434  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:34,446  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:34,491  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:34,492  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:34,535  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:34,543  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:34,546  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:34,548  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:34,567  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@2137552888(39/48 serialized/live bytes, 1 ops)
18:01:34,569  INFO Memtable:266 - Writing Memtable-IndexInfo@2137552888(39/48 serialized/live bytes, 1 ops)
18:01:34,572  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18162137137804.log
18:01:34,574  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:01:34,577  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18162137137804.log
18:01:34,581  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18162137137804.log
18:01:34,581  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:34,597  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:34,597  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:34,603  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:34,649  INFO StorageService:444 - Loading persisted ring state
18:01:34,652  INFO StorageService:525 - Starting up server gossip
18:01:34,662  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:01:34,920  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:34,923  INFO Memtable:266 - Writing Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:01:34,924  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:35,179  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:35,180  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:35,180  INFO Memtable:266 - Writing Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:01:35,493  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:35,509  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:35,516  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:35,524  WARN StorageService:633 - Generated random token LrQBe5yYJQYEbRbN. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:35,526  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1660575731(53/66 serialized/live bytes, 2 ops)
18:01:35,527  INFO Memtable:266 - Writing Memtable-LocationInfo@1660575731(53/66 serialized/live bytes, 2 ops)
18:01:35,795  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:35,799  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:35,810  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:35,811  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:35,844  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:35,847  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:35,851  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:35,851  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:37,303  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:37,344  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:37,369  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:37,479  INFO NodeId:200 - No saved local node id, using newly generated: 2c9b9d70-dbf2-11e1-0000-fe8ebeead9fe
18:01:37,577  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.326 sec
18:01:37,595  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:37,597  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:37,597  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.SuperColumnSliceTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.108 sec
Running me.prettyprint.cassandra.model.MultigetSliceQueryTest
18:01:38,927  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:38,951  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:39,150  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:39,331  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:39,777  INFO EmbeddedServerHelper:66 - Starting executor
18:01:39,778  INFO EmbeddedServerHelper:69 - Started executor
18:01:39,779  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:39,779  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:39,779  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3580611962473289647.jar
18:01:39,781  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:39,799  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:39,812  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:39,813  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:39,817  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:39,909  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:39,923  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:39,967  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:39,968  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:40,012  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:40,021  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:40,025  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:40,028  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:40,050  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18167609892693.log
18:01:40,051  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@192714409(39/48 serialized/live bytes, 1 ops)
18:01:40,052  INFO Memtable:266 - Writing Memtable-IndexInfo@192714409(39/48 serialized/live bytes, 1 ops)
18:01:40,055  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18167609892693.log
18:01:40,056  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@458982688(39/48 serialized/live bytes, 1 ops)
18:01:40,058  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18167609892693.log
18:01:40,058  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:40,071  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:40,071  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:40,075  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:40,129  INFO StorageService:444 - Loading persisted ring state
18:01:40,132  INFO StorageService:525 - Starting up server gossip
18:01:40,142  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:01:40,417  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:40,420  INFO Memtable:266 - Writing Memtable-IndexInfo@458982688(39/48 serialized/live bytes, 1 ops)
18:01:40,421  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:40,710  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:40,711  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:40,712  INFO Memtable:266 - Writing Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:01:40,969  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:40,983  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:40,990  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:40,998  WARN StorageService:633 - Generated random token Q8gmXMLHTKysHu8Z. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:41,000  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1660575731(53/66 serialized/live bytes, 2 ops)
18:01:41,001  INFO Memtable:266 - Writing Memtable-LocationInfo@1660575731(53/66 serialized/live bytes, 2 ops)
18:01:41,260  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:41,264  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:41,273  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:41,275  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:41,316  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:41,320  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:41,325  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:41,325  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:42,779  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:42,817  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:42,842  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:43,011  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.303 sec
18:01:43,029  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:43,031  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:43,032  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.AbstractSliceQueryTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.479 sec
Running me.prettyprint.cassandra.model.IndexedSlicesQueryTest
18:01:44,778  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:44,804  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:45,009  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:45,185  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:45,627  INFO EmbeddedServerHelper:66 - Starting executor
18:01:45,628  INFO EmbeddedServerHelper:69 - Started executor
18:01:45,628  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:45,629  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:45,629  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter1402890079148212427.jar
18:01:45,632  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:45,653  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:45,668  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:45,669  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:45,673  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:45,755  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:45,769  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:45,814  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:45,815  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:45,856  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:45,864  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:45,866  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:45,867  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:45,890  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@223489506(39/48 serialized/live bytes, 1 ops)
18:01:45,891  INFO Memtable:266 - Writing Memtable-IndexInfo@223489506(39/48 serialized/live bytes, 1 ops)
18:01:45,906  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18173458674110.log
18:01:45,910  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1937516689(39/48 serialized/live bytes, 1 ops)
18:01:45,910  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18173458674110.log
18:01:45,913  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18173458674110.log
18:01:45,914  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:45,933  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:45,933  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:45,936  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:45,981  INFO StorageService:444 - Loading persisted ring state
18:01:45,984  INFO StorageService:525 - Starting up server gossip
18:01:45,994  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
18:01:46,282  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:46,284  INFO Memtable:266 - Writing Memtable-IndexInfo@1937516689(39/48 serialized/live bytes, 1 ops)
18:01:46,285  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:46,539  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:46,540  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:46,540  INFO Memtable:266 - Writing Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
18:01:47,088  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:47,101  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:47,108  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:47,116  WARN StorageService:633 - Generated random token Pc83EawLDI06018p. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:47,118  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@995824187(53/66 serialized/live bytes, 2 ops)
18:01:47,119  INFO Memtable:266 - Writing Memtable-LocationInfo@995824187(53/66 serialized/live bytes, 2 ops)
18:01:47,458  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:47,461  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:47,470  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:47,472  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:47,505  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:47,509  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:47,513  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:47,513  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:48,629  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:48,665  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:48,693  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:48,911  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.342 sec
18:01:48,927  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:48,929  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:48,930  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.HColumnFamilyTest
18:01:49,888  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:49,912  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:50,103  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:50,275  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:50,706  INFO EmbeddedServerHelper:66 - Starting executor
18:01:50,708  INFO EmbeddedServerHelper:69 - Started executor
18:01:50,709  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:50,709  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:50,709  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter820813063874024108.jar
18:01:50,711  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:50,727  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:50,740  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:50,741  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:50,745  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:50,827  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:50,846  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:50,890  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:50,891  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:50,932  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:50,940  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:50,943  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:50,945  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:50,970  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@2066231378(39/48 serialized/live bytes, 1 ops)
18:01:50,971  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18178539011365.log
18:01:50,972  INFO Memtable:266 - Writing Memtable-IndexInfo@2066231378(39/48 serialized/live bytes, 1 ops)
18:01:50,974  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18178539011365.log
18:01:50,977  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1235930463(39/48 serialized/live bytes, 1 ops)
18:01:50,979  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18178539011365.log
18:01:50,979  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:50,993  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:50,993  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:50,998  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:51,046  INFO StorageService:444 - Loading persisted ring state
18:01:51,050  INFO StorageService:525 - Starting up server gossip
18:01:51,061  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
18:01:51,297  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:51,299  INFO Memtable:266 - Writing Memtable-IndexInfo@1235930463(39/48 serialized/live bytes, 1 ops)
18:01:51,301  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:51,582  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:51,583  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:51,583  INFO Memtable:266 - Writing Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
18:01:51,907  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:51,924  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:51,930  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:51,938  WARN StorageService:633 - Generated random token Yjc4V7VqkGbIrHc1. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:51,940  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@598834505(53/66 serialized/live bytes, 2 ops)
18:01:51,941  INFO Memtable:266 - Writing Memtable-LocationInfo@598834505(53/66 serialized/live bytes, 2 ops)
18:01:52,199  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:52,205  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:52,218  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:52,219  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:52,255  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:52,259  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:52,263  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:52,263  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:53,709  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:53,742  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:53,765  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:53,980  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.304 sec
18:01:53,999  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:54,001  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:54,002  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.MutatorTest
18:01:55,066  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:55,091  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:55,287  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:55,466  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:55,908  INFO EmbeddedServerHelper:66 - Starting executor
18:01:55,909  INFO EmbeddedServerHelper:69 - Started executor
18:01:55,909  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:55,910  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:55,910  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3883322571861534340.jar
18:01:55,914  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:55,930  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:55,944  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:55,945  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:55,949  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:56,041  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:56,053  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:56,097  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:56,098  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:56,140  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:56,148  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:56,151  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:56,153  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:56,182  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18183743003012.log
18:01:56,185  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18183743003012.log
18:01:56,187  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@830274872(78/97 serialized/live bytes, 2 ops)
18:01:56,189  INFO Memtable:266 - Writing Memtable-IndexInfo@830274872(78/97 serialized/live bytes, 2 ops)
18:01:56,190  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18183743003012.log
18:01:56,191  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:56,193  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:56,215  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:56,215  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:56,223  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:56,271  INFO StorageService:444 - Loading persisted ring state
18:01:56,274  INFO StorageService:525 - Starting up server gossip
18:01:56,285  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@194337136(126/157 serialized/live bytes, 3 ops)
18:01:56,538  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:01:56,540  INFO Memtable:266 - Writing Memtable-LocationInfo@194337136(126/157 serialized/live bytes, 3 ops)
18:01:56,542  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:56,788  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:56,801  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:56,807  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:56,819  WARN StorageService:633 - Generated random token h46ncHKPc7gEFqyD. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:56,822  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@780298059(53/66 serialized/live bytes, 2 ops)
18:01:56,823  INFO Memtable:266 - Writing Memtable-LocationInfo@780298059(53/66 serialized/live bytes, 2 ops)
18:01:57,202  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:57,205  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:57,217  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:57,218  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:57,262  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:57,265  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:57,269  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:57,270  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:58,910  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:58,947  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:58,979  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:01:59,235  INFO NodeId:200 - No saved local node id, using newly generated: 39935130-dbf2-11e1-0000-fe8ebeead9fb
18:01:59,241  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.397 sec
18:01:59,280  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:59,284  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:59,288  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.ColumnSliceTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.111 sec
Running me.prettyprint.cassandra.connection.HConnectionManagerTest
18:02:00,760  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:00,786  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:00,987  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:01,169  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:01,616  INFO EmbeddedServerHelper:66 - Starting executor
18:02:01,618  INFO EmbeddedServerHelper:69 - Started executor
18:02:01,618  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:01,618  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:01,619  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3324329624753825257.jar
18:02:01,622  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:01,638  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:01,652  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:01,653  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:01,657  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:01,751  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:01,768  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:01,818  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:01,819  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:01,874  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:01,882  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:01,886  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:01,888  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:01,907  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18189450469297.log
18:02:01,909  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18189450469297.log
18:02:01,913  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18189450469297.log
18:02:01,913  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:02:01,917  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:02:01,918  INFO Memtable:266 - Writing Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:02:01,924  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1939520811(39/48 serialized/live bytes, 1 ops)
18:02:01,940  INFO StorageService:412 - Cassandra version: 1.1.0
18:02:01,940  INFO StorageService:413 - Thrift API version: 19.30.0
18:02:01,944  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:02:01,996  INFO StorageService:444 - Loading persisted ring state
18:02:01,999  INFO StorageService:525 - Starting up server gossip
18:02:02,011  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:02:02,294  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:02:02,298  INFO Memtable:266 - Writing Memtable-IndexInfo@1939520811(39/48 serialized/live bytes, 1 ops)
18:02:02,299  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:02:02,640  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:02:02,641  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:02:02,641  INFO Memtable:266 - Writing Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:02:02,920  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:02:02,936  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:02:02,943  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:02:02,951  WARN StorageService:633 - Generated random token foU87DoKE3438CES. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:02:02,953  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@197629132(53/66 serialized/live bytes, 2 ops)
18:02:02,954  INFO Memtable:266 - Writing Memtable-LocationInfo@197629132(53/66 serialized/live bytes, 2 ops)
18:02:03,268  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:02:03,271  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:02:03,285  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:02:03,286  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:02:03,330  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:02:03,334  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:02:03,338  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:02:03,338  INFO CassandraDaemon:212 - Listening for thrift clients...
18:02:04,618  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:04,650  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:04,675  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:02:04,680  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
18:02:04,685  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
18:02:04,686  INFO HConnectionManager:172 - Remove status for CassandraHost pool 127.0.0.1(127.0.0.1):9170 was true
18:02:04,698  INFO HConnectionManager:114 - Added host 127.0.0.1(127.0.0.1):9170 to pool
18:02:04,698 ERROR HConnectionManager:421 - MARK HOST AS DOWN TRIGGERED for host 127.0.0.1(127.0.0.1):9170
18:02:04,699 ERROR HConnectionManager:425 - Pool state on shutdown: <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}; IsActive?: true; Active: 0; Blocked: 0; Idle: 16; NumBeforeExhausted: 50
18:02:04,699  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
18:02:04,703  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
18:02:04,704  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:04,705  INFO HConnectionManager:165 - Host 127.0.0.1(127.0.0.1):9170 not in active pools, but found in retry service.
18:02:04,717  INFO HConnectionManager:172 - Remove status for CassandraHost pool 127.0.0.1(127.0.0.1):9170 was true
18:02:04,726  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:04,738 ERROR HConnectionManager:119 - Transport exception host to HConnectionManager: 127.0.0.1(127.0.0.1):9180
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
18:02:04,739  INFO HConnectionManager:114 - Added host 127.0.0.1(127.0.0.1):9170 to pool
18:02:04,748  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:04,755 ERROR HConnectionManager:421 - MARK HOST AS DOWN TRIGGERED for host 127.0.0.1(127.0.0.1):9170
18:02:04,756 ERROR HConnectionManager:425 - Pool state on shutdown: <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}; IsActive?: true; Active: 1; Blocked: 0; Idle: 15; NumBeforeExhausted: 49
18:02:04,756  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
18:02:04,757  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
18:02:04,759  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:04,773  INFO HConnectionManager:191 - Suspend operation status was true for CassandraHost 127.0.0.1(127.0.0.1):9170
18:02:04,774  INFO HConnectionManager:212 - UN-Suspend operation status was true for CassandraHost 127.0.0.1(127.0.0.1):9170
18:02:04,775  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:04,785  WARN HConnectionManager:306 - Could not fullfill request on this host CassandraClient<127.0.0.1:9170-99>
18:02:04,785  WARN HConnectionManager:307 - Exception: 
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
18:02:04,789  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.257 sec
18:02:04,828  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:02:04,829  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:02:04,830  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.connection.ConcurrentHClientPoolTest
18:02:05,796  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:05,823  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:06,063  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:06,274  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:06,762  INFO EmbeddedServerHelper:66 - Starting executor
18:02:06,763  INFO EmbeddedServerHelper:69 - Started executor
18:02:06,763  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:06,763  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:06,763  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter8945871341766861825.jar
18:02:06,766  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:06,787  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:06,806  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:06,808  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:06,814  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:06,921  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:06,937  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:06,995  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:06,996  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:07,049  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:07,059  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:07,062  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:07,065  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:07,093  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@160094946(78/97 serialized/live bytes, 2 ops)
18:02:07,095  INFO Memtable:266 - Writing Memtable-IndexInfo@160094946(78/97 serialized/live bytes, 2 ops)
18:02:07,095  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:02:07,098  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18194600236756.log
18:02:07,101  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18194600236756.log
18:02:07,106  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18194600236756.log
18:02:07,106  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:02:07,124  INFO StorageService:412 - Cassandra version: 1.1.0
18:02:07,124  INFO StorageService:413 - Thrift API version: 19.30.0
18:02:07,131  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:02:07,193  INFO StorageService:444 - Loading persisted ring state
18:02:07,198  INFO StorageService:525 - Starting up server gossip
18:02:07,212  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:02:07,789  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:02:07,792  INFO Memtable:266 - Writing Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:02:07,793  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:02:09,202  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:02:09,214  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:02:09,224  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:02:09,236  WARN StorageService:633 - Generated random token DDxf9lKiyP1jJYvm. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:02:09,238  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@540108943(53/66 serialized/live bytes, 2 ops)
18:02:09,239  INFO Memtable:266 - Writing Memtable-LocationInfo@540108943(53/66 serialized/live bytes, 2 ops)
18:02:09,763  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:09,788  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:09,801 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:09,801  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:09,803  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:09,805  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:02:09,818  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:09,818 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:09,819  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:09,820  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:09,822  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:09,823 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:09,823  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:09,824  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:09,826  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 3, Failures: 0, Errors: 3, Skipped: 0, Time elapsed: 4.248 sec <<< FAILURE!
18:02:09,827 ERROR AbstractCassandraDaemon:370 - Exception encountered during startup
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
Exception encountered during startup: java.lang.InterruptedException
18:02:09,882  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:02:09,883  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.connection.HConnectionManagerListenerTest
18:02:11,758  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:11,784  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:12,029  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:12,270  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:12,997  INFO EmbeddedServerHelper:66 - Starting executor
18:02:12,998  INFO EmbeddedServerHelper:69 - Started executor
18:02:12,998  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:12,999  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:12,999  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter4933163825956237450.jar
18:02:13,002  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:13,021  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:13,036  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:13,037  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:13,041  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:13,202  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:13,214  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:13,254  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:13,254  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:13,297  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:13,305  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:13,307  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:13,309  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:13,329  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18200744221601.log
18:02:13,334  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18200744221601.log
18:02:13,337  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18200744221601.log
18:02:13,337  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:02:13,446  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@239228739(39/48 serialized/live bytes, 1 ops)
18:02:13,447  INFO Memtable:266 - Writing Memtable-IndexInfo@239228739(39/48 serialized/live bytes, 1 ops)
18:02:13,455  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1515632954(39/48 serialized/live bytes, 1 ops)
18:02:13,463  INFO StorageService:412 - Cassandra version: 1.1.0
18:02:13,464  INFO StorageService:413 - Thrift API version: 19.30.0
18:02:13,468  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:02:13,536  INFO StorageService:444 - Loading persisted ring state
18:02:13,540  INFO StorageService:525 - Starting up server gossip
18:02:13,551  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1921615168(126/157 serialized/live bytes, 3 ops)
18:02:14,483  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:02:14,486  INFO Memtable:266 - Writing Memtable-IndexInfo@1515632954(39/48 serialized/live bytes, 1 ops)
18:02:14,487  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:02:15,691  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:02:15,692  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:02:15,692  INFO Memtable:266 - Writing Memtable-LocationInfo@1921615168(126/157 serialized/live bytes, 3 ops)
18:02:15,999  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:16,037  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:16,053 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:16,054  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:16,055  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:16,058  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:02:16,232  INFO HConnectionManager:165 - Host 127.0.0.1(127.0.0.1):9170 not in active pools, but found in retry service.
18:02:16,233  INFO HConnectionManager:172 - Remove status for CassandraHost pool 127.0.0.1(127.0.0.1):9170 was true
18:02:16,236  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:16,237 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:16,237  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:16,238  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:16,299 ERROR HConnectionManager:119 - Transport exception host to HConnectionManager: 127.0.0.1(127.0.0.1):9180
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
18:02:16,305  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:16,306 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:16,306  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:16,307  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:16,321 ERROR HConnectionManager:119 - Transport exception host to HConnectionManager: 127.0.0.1(127.0.0.1):9170
me.prettyprint.hector.api.exceptions.HectorTransportException: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
        at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:144)
        at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:26)
        at me.prettyprint.cassandra.connection.ConcurrentHClientPool.createClient(ConcurrentHClientPool.java:147)
        at me.prettyprint.cassandra.connection.ConcurrentHClientPool.<init>(ConcurrentHClientPool.java:53)
        at me.prettyprint.cassandra.connection.RoundRobinBalancingPolicy.createConnection(RoundRobinBalancingPolicy.java:67)
        at me.prettyprint.cassandra.connection.HConnectionManager.addCassandraHost(HConnectionManager.java:112)
        at me.prettyprint.cassandra.connection.HConnectionManagerListenerTest.testOnAddCassandraHostFailExists(HConnectionManagerListenerTest.java:65)
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
18:02:16,327  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:16,329 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:16,329  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:16,330  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:16,376  INFO HConnectionManager:165 - Host 127.0.0.1(127.0.0.1):9170 not in active pools, but found in retry service.
18:02:16,376  INFO HConnectionManager:172 - Remove status for CassandraHost pool 127.0.0.1(127.0.0.1):9170 was true
18:02:16,378 ERROR HConnectionManager:119 - Transport exception host to HConnectionManager: 127.0.0.1(127.0.0.1):9170
me.prettyprint.hector.api.exceptions.HectorTransportException: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
        at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:144)
        at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:26)
        at me.prettyprint.cassandra.connection.ConcurrentHClientPool.createClient(ConcurrentHClientPool.java:147)
        at me.prettyprint.cassandra.connection.ConcurrentHClientPool.<init>(ConcurrentHClientPool.java:53)
        at me.prettyprint.cassandra.connection.RoundRobinBalancingPolicy.createConnection(RoundRobinBalancingPolicy.java:67)
        at me.prettyprint.cassandra.connection.HConnectionManager.addCassandraHost(HConnectionManager.java:112)
        at me.prettyprint.cassandra.connection.HConnectionManagerListenerTest.testOnAddCassandraHostSuccess(HConnectionManagerListenerTest.java:84)
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
18:02:16,390  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:16,391 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:16,392  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:16,393  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:16,421 ERROR HConnectionManager:421 - MARK HOST AS DOWN TRIGGERED for host 127.0.0.1(127.0.0.1):9170
18:02:16,423  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 1s
18:02:16,424 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:16,425  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:16,426  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:16,476 ERROR HConnectionManager:421 - MARK HOST AS DOWN TRIGGERED for host 127.0.0.1(127.0.0.1):9170
18:02:16,501  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:02:16,513  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:02:16,522  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:02:16,534  WARN StorageService:633 - Generated random token LD6ygoOPcrFLuNgz. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:02:16,536  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1755221044(53/66 serialized/live bytes, 2 ops)
18:02:16,536  INFO Memtable:266 - Writing Memtable-LocationInfo@1755221044(53/66 serialized/live bytes, 2 ops)
18:02:17,423  INFO CassandraHostRetryService:146 - Not checking that 127.0.0.1(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:02:17,425  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:17,425  INFO CassandraHostRetryService:159 - Downed Host retry status false with host: 127.0.0.1(127.0.0.1):9170
18:02:17,579  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:17,580 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:17,581  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:17,581  INFO HConnectionManager:191 - Suspend operation status was false for CassandraHost 127.0.0.1(127.0.0.1):9170
18:02:17,582  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:17,584  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:17,586 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:17,587  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:17,588  INFO HConnectionManager:191 - Suspend operation status was false for CassandraHost 127.0.0.1(127.0.0.1):9170
18:02:17,589  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:17,591 ERROR AbstractCassandraDaemon:370 - Exception encountered during startup
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
Exception encountered during startup: java.lang.InterruptedException
18:02:17,597  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 8, Failures: 4, Errors: 0, Skipped: 0, Time elapsed: 6.125 sec <<< FAILURE!
18:02:17,653  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:02:17,654  INFO MessagingService:695 - MessagingService shutting down server thread.
18:02:17,704  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
Running me.prettyprint.cassandra.connection.LeastActiveBalancingPolicyTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.109 sec
Running me.prettyprint.cassandra.connection.RoundRobinBalancingPolicyTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 6.211 sec
Running me.prettyprint.cassandra.connection.DynamicBalancingPolicyTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.896 sec
Running me.prettyprint.cassandra.connection.HostTimeoutTrackerTest
18:02:29,264  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:29,305 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:02:29,306  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:02:29,308  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:29,425  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:02:29,934  INFO HConnectionManager:191 - Suspend operation status was false for CassandraHost localhost(127.0.0.1):9170
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.178 sec
Running me.prettyprint.cassandra.connection.LatencyAwareHClientPoolTest
18:02:30,614  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:30,642  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:30,881  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:31,132  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:31,711  INFO EmbeddedServerHelper:66 - Starting executor
18:02:31,713  INFO EmbeddedServerHelper:69 - Started executor
18:02:31,713  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:31,715  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:31,716  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter4347243897966209257.jar
18:02:31,833  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:32,223  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:32,399  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:32,505  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:32,523  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:33,692  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:33,837  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:33,861  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:34,139  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:34,141  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:34,144  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:34,714  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:34,751  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:34,752  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:34,879  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:34,887  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1519652738(78/97 serialized/live bytes, 2 ops)
18:02:34,889  INFO Memtable:266 - Writing Memtable-IndexInfo@1519652738(78/97 serialized/live bytes, 2 ops)
18:02:34,889  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:02:35,119 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:35,120  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:35,122  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:35,249  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:02:35,413  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:35,414 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:35,415  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:35,419  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:35,421  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:35,422 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:35,422  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:35,428  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 3, Failures: 0, Errors: 3, Skipped: 0, Time elapsed: 5.111 sec <<< FAILURE!
18:02:35,430  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:35,463  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18219511160408.log
18:02:35,463  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18219511160408.log
18:02:35,465 ERROR AbstractCassandraDaemon:370 - Exception encountered during startup
java.nio.channels.ClosedByInterruptException
        at java.nio.channels.spi.AbstractInterruptibleChannel.end(AbstractInterruptibleChannel.java:184)
        at sun.nio.ch.FileChannelImpl.size(FileChannelImpl.java:304)
        at org.apache.cassandra.io.util.RandomAccessReader.<init>(RandomAccessReader.java:81)
        at org.apache.cassandra.io.util.RandomAccessReader.open(RandomAccessReader.java:102)
        at org.apache.cassandra.io.util.RandomAccessReader.open(RandomAccessReader.java:87)
        at org.apache.cassandra.db.commitlog.CommitLog.recover(CommitLog.java:189)
        at org.apache.cassandra.db.commitlog.CommitLog.recover(CommitLog.java:143)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:223)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
java.nio.channels.ClosedByInterruptException
        at java.nio.channels.spi.AbstractInterruptibleChannel.end(AbstractInterruptibleChannel.java:184)
        at sun.nio.ch.FileChannelImpl.size(FileChannelImpl.java:304)
        at org.apache.cassandra.io.util.RandomAccessReader.<init>(RandomAccessReader.java:81)
        at org.apache.cassandra.io.util.RandomAccessReader.open(RandomAccessReader.java:102)
        at org.apache.cassandra.io.util.RandomAccessReader.open(RandomAccessReader.java:87)
        at org.apache.cassandra.db.commitlog.CommitLog.recover(CommitLog.java:189)
        at org.apache.cassandra.db.commitlog.CommitLog.recover(CommitLog.java:143)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:223)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Exception encountered during startup: null
Running me.prettyprint.cassandra.connection.HThriftClientTest
18:02:37,755  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:37,784  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:37,991  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:38,186  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:38,662  INFO EmbeddedServerHelper:66 - Starting executor
18:02:38,663  INFO EmbeddedServerHelper:69 - Started executor
18:02:38,664  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:38,664  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:38,664  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter164474774063028626.jar
18:02:38,666  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:38,683  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:38,696  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:38,697  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:38,701  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:38,800  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:38,815  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:38,854  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:38,855  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:38,897  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:38,905  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:38,907  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:38,909  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:38,935  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18226498674724.log
18:02:38,939  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18226498674724.log
18:02:38,946  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18226498674724.log
18:02:38,946  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:02:38,948  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@81035498(78/97 serialized/live bytes, 2 ops)
18:02:38,949  INFO Memtable:266 - Writing Memtable-IndexInfo@81035498(78/97 serialized/live bytes, 2 ops)
18:02:38,950  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:02:39,721  INFO StorageService:412 - Cassandra version: 1.1.0
18:02:39,722  INFO StorageService:413 - Thrift API version: 19.30.0
18:02:39,725  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:02:40,055  INFO StorageService:444 - Loading persisted ring state
18:02:40,060  INFO StorageService:525 - Starting up server gossip
18:02:40,074  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:02:40,760  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:02:40,763  INFO Memtable:266 - Writing Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:02:40,764  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:02:41,338  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:02:41,480  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:02:41,488  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:02:41,497  WARN StorageService:633 - Generated random token tstALhskiHXZC9Fu. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:02:41,499  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1924003262(53/66 serialized/live bytes, 2 ops)
18:02:41,500  INFO Memtable:266 - Writing Memtable-LocationInfo@1924003262(53/66 serialized/live bytes, 2 ops)
18:02:41,664  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:41,704  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 7, Failures: 0, Errors: 6, Skipped: 0, Time elapsed: 4.213 sec <<< FAILURE!
18:02:41,705 ERROR AbstractCassandraDaemon:370 - Exception encountered during startup
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        ... 11 more
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
Exception encountered during startup: java.lang.InterruptedException
18:02:41,730  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:02:41,731  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.io.StreamTest
18:02:44,236  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:44,265  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:44,473  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:44,652  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:45,207  INFO EmbeddedServerHelper:66 - Starting executor
18:02:45,208  INFO EmbeddedServerHelper:69 - Started executor
18:02:45,208  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:45,209  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:45,209  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter2591591863186191853.jar
18:02:45,211  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:45,229  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:45,245  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:45,246  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:45,250  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:45,331  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:45,345  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:45,385  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:45,386  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:45,429  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:45,438  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:45,440  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:45,441  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:45,463  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18232917848237.log
18:02:45,467  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18232917848237.log
18:02:45,469  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18232917848237.log
18:02:45,469  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:02:46,485  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@643444394(39/48 serialized/live bytes, 1 ops)
18:02:46,486  INFO Memtable:266 - Writing Memtable-IndexInfo@643444394(39/48 serialized/live bytes, 1 ops)
18:02:46,492  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@10202458(39/48 serialized/live bytes, 1 ops)
18:02:46,503  INFO StorageService:412 - Cassandra version: 1.1.0
18:02:46,503  INFO StorageService:413 - Thrift API version: 19.30.0
18:02:46,508  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:02:46,560  INFO StorageService:444 - Loading persisted ring state
18:02:46,563  INFO StorageService:525 - Starting up server gossip
18:02:46,574  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@11555382(126/157 serialized/live bytes, 3 ops)
18:02:48,209  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:48,365  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:48,380 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:48,380  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:48,382  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:48,385  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:02:49,448  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:49,449 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:02:49,449  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:02:49,449  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:02:49,450  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:49,589  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:49,590 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:49,590  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:49,591  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:49,591  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:49,592 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:02:49,592  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:02:49,593  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:49,594  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 2, Failures: 0, Errors: 2, Skipped: 0, Time elapsed: 6.777 sec <<< FAILURE!
18:02:49,595 ERROR AbstractCassandraDaemon:370 - Exception encountered during startup
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.incrementAndGetGeneration(SystemTable.java:350)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:534)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.incrementAndGetGeneration(SystemTable.java:350)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:534)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 10 more
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 10 more
Exception encountered during startup: java.lang.InterruptedException
18:02:49,616 ERROR AbstractCassandraDaemon:134 - Exception in thread Thread[StorageServiceShutdownHook,5,main]
java.lang.NullPointerException
        at org.apache.cassandra.gms.Gossiper.stop(Gossiper.java:1113)
        at org.apache.cassandra.service.StorageService$2.runMayThrow(StorageService.java:478)
        at org.apache.cassandra.utils.WrappedRunnable.run(WrappedRunnable.java:30)
        at java.lang.Thread.run(Thread.java:662)
Running me.prettyprint.cassandra.examples.ExampleDaoV2Test
18:02:52,377  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:52,580  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:52,760  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:53,203  INFO EmbeddedServerHelper:66 - Starting executor
18:02:53,206  INFO EmbeddedServerHelper:69 - Started executor
18:02:53,207  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:53,207  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:53,207  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3406107125145063572.jar
18:02:53,210  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:53,235  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:53,252  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:53,253  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:53,257  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:53,357  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:53,369  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:53,420  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:53,423  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:53,470  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:53,478  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:53,480  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:53,482  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:53,510  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1861413442(39/48 serialized/live bytes, 1 ops)
18:02:53,511  INFO Memtable:266 - Writing Memtable-IndexInfo@1861413442(39/48 serialized/live bytes, 1 ops)
18:02:53,515  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@2066231378(39/48 serialized/live bytes, 1 ops)
18:02:53,518  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18241037640838.log
18:02:53,521  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18241037640838.log
18:02:53,523  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18241037640838.log
18:02:53,524  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:02:53,545  INFO StorageService:412 - Cassandra version: 1.1.0
18:02:53,545  INFO StorageService:413 - Thrift API version: 19.30.0
18:02:53,549  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:02:53,595  INFO StorageService:444 - Loading persisted ring state
18:02:53,599  INFO StorageService:525 - Starting up server gossip
18:02:53,612  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@277818534(126/157 serialized/live bytes, 3 ops)
18:02:54,411  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:02:54,415  INFO Memtable:266 - Writing Memtable-IndexInfo@2066231378(39/48 serialized/live bytes, 1 ops)
18:02:54,416  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:02:55,017  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:02:55,018  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:02:55,018  INFO Memtable:266 - Writing Memtable-LocationInfo@277818534(126/157 serialized/live bytes, 3 ops)
18:02:55,508  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:02:55,522  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:02:55,529  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:02:55,538  WARN StorageService:633 - Generated random token SzypFInRYbd2J4Ia. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:02:55,541  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1255545583(53/66 serialized/live bytes, 2 ops)
18:02:55,541  INFO Memtable:266 - Writing Memtable-LocationInfo@1255545583(53/66 serialized/live bytes, 2 ops)
18:02:56,155  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:02:56,158  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:02:56,167  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:02:56,168  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:02:56,207  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:56,243  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:02:56,249  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:02:56,258  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:02:56,258  INFO CassandraDaemon:212 - Listening for thrift clients...
18:02:56,273  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:56,298  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:02:57,253  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.23 sec
18:02:57,270  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:02:57,272  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:02:57,272  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.CassandraAuthTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.061 sec
Running me.prettyprint.cassandra.service.template.SuperCfTemplateTest
18:02:59,497  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:59,526  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:59,757  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:59,959  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:03:00,505  INFO EmbeddedServerHelper:66 - Starting executor
18:03:00,507  INFO EmbeddedServerHelper:69 - Started executor
18:03:00,507  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:03:00,508  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:03:00,508  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter6255856131138956688.jar
18:03:00,511  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:03:00,528  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:03:00,541  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:03:00,542  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:03:00,546  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:03:00,638  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:03:00,651  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:03:00,697  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:03:00,698  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:03:00,747  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:00,757  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:03:00,760  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:00,766  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:03:00,791  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18248265824183.log
18:03:00,792  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@628029189(39/48 serialized/live bytes, 1 ops)
18:03:00,793  INFO Memtable:266 - Writing Memtable-IndexInfo@628029189(39/48 serialized/live bytes, 1 ops)
18:03:00,796  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18248265824183.log
18:03:00,801  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1152296720(39/48 serialized/live bytes, 1 ops)
18:03:00,806  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18248265824183.log
18:03:00,806  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:03:00,828  INFO StorageService:412 - Cassandra version: 1.1.0
18:03:00,829  INFO StorageService:413 - Thrift API version: 19.30.0
18:03:00,834  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:03:00,894  INFO StorageService:444 - Loading persisted ring state
18:03:00,898  INFO StorageService:525 - Starting up server gossip
18:03:00,908  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:03:02,110  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:03:02,114  INFO Memtable:266 - Writing Memtable-IndexInfo@1152296720(39/48 serialized/live bytes, 1 ops)
18:03:02,114  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:03:03,136  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:03:03,137  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:03:03,137  INFO Memtable:266 - Writing Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:03:03,507  INFO EmbeddedServerHelper:73 - Done sleeping
18:03:03,549  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:03,566 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:03:03,566  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:03:03,567  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:03,570  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:03:04,052  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:03:04,199  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:03:04,265  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:03:04,398  WARN StorageService:633 - Generated random token hRv7GexehRb1iZhA. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:03:04,401  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@86789207(53/66 serialized/live bytes, 2 ops)
18:03:04,402  INFO Memtable:266 - Writing Memtable-LocationInfo@86789207(53/66 serialized/live bytes, 2 ops)
18:03:04,812  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 13, Failures: 0, Errors: 13, Skipped: 0, Time elapsed: 5.592 sec <<< FAILURE!
18:03:04,813 ERROR AbstractCassandraDaemon:370 - Exception encountered during startup
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
Exception encountered during startup: java.lang.InterruptedException
18:03:04,860  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:03:04,869  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.template.ColumnFamilyTemplateTest
18:03:07,699  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:03:07,797  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:03:08,072  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:03:08,283  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:03:09,006  INFO EmbeddedServerHelper:66 - Starting executor
18:03:09,007  INFO EmbeddedServerHelper:69 - Started executor
18:03:09,008  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:03:09,008  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:03:09,008  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3355090328383458115.jar
18:03:09,011  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:03:09,032  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:03:09,045  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:03:09,046  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:03:09,050  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:03:09,237  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:03:09,252  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:03:09,291  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:03:09,292  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:03:09,337  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:09,345  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:03:09,347  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:09,349  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:03:09,370  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@545732387(39/48 serialized/live bytes, 1 ops)
18:03:09,372  INFO Memtable:266 - Writing Memtable-IndexInfo@545732387(39/48 serialized/live bytes, 1 ops)
18:03:09,372  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18256799834124.log
18:03:09,380  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@415546420(39/48 serialized/live bytes, 1 ops)
18:03:09,381  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18256799834124.log
18:03:09,386  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18256799834124.log
18:03:09,387  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:03:09,407  INFO StorageService:412 - Cassandra version: 1.1.0
18:03:09,407  INFO StorageService:413 - Thrift API version: 19.30.0
18:03:09,421  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:03:09,479  INFO StorageService:444 - Loading persisted ring state
18:03:09,483  INFO StorageService:525 - Starting up server gossip
18:03:09,494  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
18:03:10,387  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:03:10,390  INFO Memtable:266 - Writing Memtable-IndexInfo@415546420(39/48 serialized/live bytes, 1 ops)
18:03:10,391  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:03:10,945  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:03:10,946  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:03:10,946  INFO Memtable:266 - Writing Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
18:03:11,825  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:03:11,838  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:03:11,846  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:03:11,859  WARN StorageService:633 - Generated random token WCl50r8iEo611Uvw. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:03:11,861  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1589377628(53/66 serialized/live bytes, 2 ops)
18:03:11,862  INFO Memtable:266 - Writing Memtable-LocationInfo@1589377628(53/66 serialized/live bytes, 2 ops)
18:03:12,008  INFO EmbeddedServerHelper:73 - Done sleeping
18:03:12,044  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:12,059 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:03:12,060  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:03:12,062  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:12,065  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:03:14,017  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:03:14,226  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:03:14,372  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:03:14,387  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:03:14,641  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:03:14,644  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:03:14,670  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:03:14,671  INFO CassandraDaemon:212 - Listening for thrift clients...
18:03:14,676  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 9, Failures: 0, Errors: 9, Skipped: 0, Time elapsed: 7.399 sec <<< FAILURE!
18:03:14,759  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:03:14,761  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:03:14,763  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.BatchMutationTest
Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.163 sec
Running me.prettyprint.cassandra.service.CassandraHostConfiguratorTest
18:03:16,947 ERROR CassandraHost:68 - Unable to resolve host h1
18:03:17,563 ERROR CassandraHost:68 - Unable to resolve host h2
18:03:18,220 ERROR CassandraHost:68 - Unable to resolve host h3
Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.218 sec
Running me.prettyprint.cassandra.service.KeyspaceTest
18:03:19,031  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:03:19,055  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:03:19,329  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:03:19,533  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:03:19,967  INFO EmbeddedServerHelper:66 - Starting executor
18:03:19,968  INFO EmbeddedServerHelper:69 - Started executor
18:03:19,968  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:03:19,969  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:03:19,969  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter5786190392443908020.jar
18:03:19,974  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:03:19,989  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:03:20,002  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:03:20,004  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:03:20,008  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:03:20,103  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:03:20,116  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:03:20,156  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:03:20,157  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:03:20,200  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:20,207  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:03:20,210  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:20,213  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:03:20,237  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18267801643939.log
18:03:20,240  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18267801643939.log
18:03:20,243  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18267801643939.log
18:03:20,243  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:03:20,504  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1939520811(78/97 serialized/live bytes, 2 ops)
18:03:20,505  INFO Memtable:266 - Writing Memtable-IndexInfo@1939520811(78/97 serialized/live bytes, 2 ops)
18:03:20,506  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:03:20,519  INFO StorageService:412 - Cassandra version: 1.1.0
18:03:20,519  INFO StorageService:413 - Thrift API version: 19.30.0
18:03:20,524  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:03:20,609  INFO StorageService:444 - Loading persisted ring state
18:03:20,615  INFO StorageService:525 - Starting up server gossip
18:03:20,629  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
18:03:21,097  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:03:21,099  INFO Memtable:266 - Writing Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
18:03:21,100  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:03:21,621  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:03:21,634  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:03:21,643  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:03:21,652  WARN StorageService:633 - Generated random token mQo2gmBTwaZJvRLf. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:03:21,656  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2061551555(53/66 serialized/live bytes, 2 ops)
18:03:21,656  INFO Memtable:266 - Writing Memtable-LocationInfo@2061551555(53/66 serialized/live bytes, 2 ops)
18:03:22,346  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:03:22,348  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:03:22,358  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:03:22,359  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:03:22,397  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:03:22,401  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:03:22,407  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:03:22,407  INFO CassandraDaemon:212 - Listening for thrift clients...
18:03:22,969  INFO EmbeddedServerHelper:73 - Done sleeping
18:03:23,010  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:23,041  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:03:23,502  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:23,681  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:23,859  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:23,907  INFO NodeId:200 - No saved local node id, using newly generated: 6c0b3d30-dbf2-11e1-0000-fe8ebeead9de
18:03:24,033  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,059  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,069  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,458  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,751  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,883  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,890  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,900  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:25,004  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,073  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:25,303  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,485  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:25,542  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:25,625  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,758  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:25,789  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,801  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,967  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,984  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,992  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,070  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,116  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,218  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,246  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,285  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,392  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:26,507  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,543  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,565  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 24, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 7.785 sec
18:03:26,687  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:03:26,689  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:03:26,691  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.CassandraClusterTest
18:03:29,450  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:03:29,475  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:03:29,675  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:03:29,864  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:03:30,596  INFO EmbeddedServerHelper:66 - Starting executor
18:03:30,597  INFO EmbeddedServerHelper:69 - Started executor
18:03:30,597  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:03:30,597  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:03:30,597  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter7613313003852012668.jar
18:03:30,599  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:03:30,615  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:03:30,628  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:03:30,629  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:03:30,633  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:03:30,723  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:03:30,735  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:03:30,778  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:03:30,779  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:03:30,822  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:30,829  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:03:30,831  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:30,833  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:03:30,855  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18278286149053.log
18:03:30,857  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:03:30,858  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18278286149053.log
18:03:30,858  INFO Memtable:266 - Writing Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:03:30,861  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18278286149053.log
18:03:30,861  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@643444394(39/48 serialized/live bytes, 1 ops)
18:03:30,862  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:03:30,879  INFO StorageService:412 - Cassandra version: 1.1.0
18:03:30,880  INFO StorageService:413 - Thrift API version: 19.30.0
18:03:30,885  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:03:30,930  INFO StorageService:444 - Loading persisted ring state
18:03:30,933  INFO StorageService:525 - Starting up server gossip
18:03:30,943  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:03:32,087  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:03:32,089  INFO Memtable:266 - Writing Memtable-IndexInfo@643444394(39/48 serialized/live bytes, 1 ops)
18:03:32,091  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:03:32,537  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:03:32,538  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:03:32,538  INFO Memtable:266 - Writing Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:03:32,973  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:03:32,986  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:03:32,994  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:03:33,003  WARN StorageService:633 - Generated random token SZTZgpayDJSDsHhd. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:03:33,005  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@120708763(53/66 serialized/live bytes, 2 ops)
18:03:33,006  INFO Memtable:266 - Writing Memtable-LocationInfo@120708763(53/66 serialized/live bytes, 2 ops)
18:03:33,597  INFO EmbeddedServerHelper:73 - Done sleeping
18:03:33,631  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,645 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,645  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,647  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,650  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:03:33,661  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,662 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,662  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,663  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,664  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:03:33,665  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,666 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,666  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,667  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,668  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:03:33,669  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,670 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,670  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,673  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,676  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,678 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,678  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,679  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,680  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:03:33,682  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:03:33,682  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,684 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,688  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,692  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,698  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,700 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,701  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,704  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,884  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,885 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,885  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,886  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,888  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,889 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,889  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,890  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,922  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,923 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,923  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,924  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,926  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,928 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,928  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,929  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:03:33,929  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,970  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:03:33,972  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,974  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:03:33,975  INFO CassandraDaemon:212 - Listening for thrift clients...
18:03:35,847  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1373806697(190/237 serialized/live bytes, 4 ops)
18:03:35,848  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1373806697(190/237 serialized/live bytes, 4 ops)
18:03:36,735  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-1-Data.db (252 bytes)
18:03:36,737  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@557591935(1238/1547 serialized/live bytes, 20 ops)
18:03:36,737  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@557591935(1238/1547 serialized/live bytes, 20 ops)
18:03:37,632  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-1-Data.db (1308 bytes)
18:03:39,106  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1700203556(1238/1547 serialized/live bytes, 20 ops)
18:03:39,107  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1700203556(1238/1547 serialized/live bytes, 20 ops)
18:03:39,631  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-2-Data.db (1308 bytes)
18:03:39,632  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@320803739(815/1018 serialized/live bytes, 10 ops)
18:03:39,633  INFO Memtable:266 - Writing Memtable-schema_columns@320803739(815/1018 serialized/live bytes, 10 ops)
18:03:39,968  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-1-Data.db (877 bytes)
18:03:40,201  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746864617465, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthdate_idx'}
18:03:40,202  INFO SecondaryIndex:159 - Submitting index build of DynamicCF.birthdate_idx for data in 
18:03:40,205  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@891929669(38/47 serialized/live bytes, 1 ops)
18:03:40,206  INFO Memtable:266 - Writing Memtable-IndexInfo@891929669(38/47 serialized/live bytes, 1 ops)
18:03:40,206  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:40,229  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@445099367(194/242 serialized/live bytes, 4 ops)
18:03:40,660  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-3-Data.db (100 bytes)
18:03:40,660  INFO SecondaryIndex:200 - Index build of DynamicCF.birthdate_idx complete
18:03:40,660  INFO Memtable:266 - Writing Memtable-schema_keyspaces@445099367(194/242 serialized/live bytes, 4 ops)
18:03:41,062  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-2-Data.db (260 bytes)
18:03:41,097  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@873551122(0/0 serialized/live bytes, 1 ops)
18:03:41,098  INFO Memtable:266 - Writing Memtable-schema_keyspaces@873551122(0/0 serialized/live bytes, 1 ops)
18:03:41,554  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-3-Data.db (66 bytes)
18:03:41,555  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@897350024(0/0 serialized/live bytes, 1 ops)
18:03:41,556  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@897350024(0/0 serialized/live bytes, 1 ops)
18:03:41,928  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-3-Data.db (66 bytes)
18:03:41,930  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@745557237(0/0 serialized/live bytes, 1 ops)
18:03:41,930  INFO Memtable:266 - Writing Memtable-schema_columns@745557237(0/0 serialized/live bytes, 1 ops)
18:03:42,306  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-2-Data.db (66 bytes)
18:03:42,319  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:42,371  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1980984405(194/242 serialized/live bytes, 4 ops)
18:03:42,372  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1980984405(194/242 serialized/live bytes, 4 ops)
18:03:42,786  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-4-Data.db (260 bytes)
18:03:42,812  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@719148673(0/0 serialized/live bytes, 1 ops)
18:03:42,813  INFO Memtable:266 - Writing Memtable-schema_keyspaces@719148673(0/0 serialized/live bytes, 1 ops)
18:03:42,833  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-1-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-2-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-3-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-4-Data.db')]
18:03:43,277  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-5-Data.db (66 bytes)
18:03:43,279  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@957297868(0/0 serialized/live bytes, 1 ops)
18:03:43,279  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@957297868(0/0 serialized/live bytes, 1 ops)
18:03:43,631  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,661  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,664  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,669  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,672  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,681  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,691  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,884  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,888  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,922  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,926  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:44,500  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-4-Data.db (66 bytes)
18:03:44,501  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@796902479(0/0 serialized/live bytes, 1 ops)
18:03:44,501  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-3-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-1-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-2-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-4-Data.db')]
18:03:44,502  INFO Memtable:266 - Writing Memtable-schema_columns@796902479(0/0 serialized/live bytes, 1 ops)
18:03:44,710  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,711  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,711  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,711  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,711  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,711  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,710  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,712  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,712  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,712  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,712  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:45,527  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-6-Data.db,].  838 to 512 (~61% of original) bytes for 2 keys at 0,000255MB/s.  Time: 1.918ms.
18:03:45,717  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:45,721  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:45,723  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:45,725  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:45,855  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-3-Data.db (66 bytes)
18:03:45,864  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-5-Data.db,].  2.748 to 1.374 (~50% of original) bytes for 2 keys at 0,000965MB/s.  Time: 1.358ms.
18:03:45,869  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:45,878  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1726531547(190/237 serialized/live bytes, 4 ops)
18:03:45,879  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1726531547(190/237 serialized/live bytes, 4 ops)
18:03:46,358  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-7-Data.db (252 bytes)
18:03:46,404  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@866752711(190/237 serialized/live bytes, 4 ops)
18:03:46,404  INFO Memtable:266 - Writing Memtable-schema_keyspaces@866752711(190/237 serialized/live bytes, 4 ops)
18:03:46,728  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,728  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,729  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,729  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,730  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,731  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,731  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,973  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-8-Data.db (252 bytes)
18:03:46,975  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-5-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-8-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-7-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-6-Data.db')]
18:03:46,996  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1794306256(0/0 serialized/live bytes, 1 ops)
18:03:46,996  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1794306256(0/0 serialized/live bytes, 1 ops)
18:03:47,476  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-10-Data.db (62 bytes)
18:03:47,478  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@976017192(0/0 serialized/live bytes, 1 ops)
18:03:47,478  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@976017192(0/0 serialized/live bytes, 1 ops)
18:03:47,481  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-9-Data.db,].  1.082 to 570 (~52% of original) bytes for 3 keys at 0,001076MB/s.  Time: 505ms.
18:03:47,880  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-6-Data.db (62 bytes)
18:03:47,881  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@1343001934(0/0 serialized/live bytes, 1 ops)
18:03:47,882  INFO Memtable:266 - Writing Memtable-schema_columns@1343001934(0/0 serialized/live bytes, 1 ops)
18:03:48,283  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-4-Data.db (62 bytes)
18:03:48,284  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-3-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-2-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-4-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-1-Data.db')]
18:03:48,298  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:48,356  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@905375489(1158/1447 serialized/live bytes, 20 ops)
18:03:48,358  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@905375489(1158/1447 serialized/live bytes, 20 ops)
18:03:48,746  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-5-Data.db,].  1.071 to 1.005 (~93% of original) bytes for 3 keys at 0,002079MB/s.  Time: 461ms.
18:03:48,811  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-7-Data.db (1225 bytes)
18:03:48,855  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1574355766(957/1196 serialized/live bytes, 21 ops)
18:03:48,856  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1574355766(957/1196 serialized/live bytes, 21 ops)
18:03:49,448  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-8-Data.db (1024 bytes)
18:03:49,449  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-7-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-6-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-5-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-8-Data.db')]
18:03:49,465  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:49,475  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1410209382(187/233 serialized/live bytes, 4 ops)
18:03:49,476  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1410209382(187/233 serialized/live bytes, 4 ops)
18:03:51,431  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-11-Data.db (246 bytes)
18:03:51,433  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-9-Data.db,].  3.685 to 2.509 (~68% of original) bytes for 4 keys at 0,001207MB/s.  Time: 1.982ms.
18:03:51,457  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1793061098(1178/1472 serialized/live bytes, 20 ops)
18:03:51,457  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1793061098(1178/1472 serialized/live bytes, 20 ops)
18:03:52,133  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-10-Data.db (1245 bytes)
18:03:52,173  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@439235775(1177/1471 serialized/live bytes, 20 ops)
18:03:52,173  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@439235775(1177/1471 serialized/live bytes, 20 ops)
18:03:52,704  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-11-Data.db (1244 bytes)
18:03:52,740  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@320508688(978/1222 serialized/live bytes, 21 ops)
18:03:52,740  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@320508688(978/1222 serialized/live bytes, 21 ops)
18:03:53,328  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-12-Data.db (1045 bytes)
18:03:53,330  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-11-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-10-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-9-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-12-Data.db')]
18:03:53,343  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 17, Failures: 0, Errors: 11, Skipped: 0, Time elapsed: 24.155 sec <<< FAILURE!
18:03:53,376  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:03:53,379  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:03:53,380  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.KeyIteratorTest
18:03:56,295  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:03:56,321  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:03:56,522  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:03:56,700  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:03:57,574  INFO EmbeddedServerHelper:66 - Starting executor
18:03:57,576  INFO EmbeddedServerHelper:69 - Started executor
18:03:57,576  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:03:57,576  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:03:57,576  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter8980463302497951572.jar
18:03:57,578  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:03:57,593  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:03:57,606  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:03:57,607  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:03:57,611  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:03:57,702  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:03:57,715  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:03:57,754  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:03:57,754  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:03:57,797  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:57,805  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:03:57,807  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:57,809  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:03:57,831  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18305025177761.log
18:03:57,838  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18305025177761.log
18:03:57,841  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18305025177761.log
18:03:57,841  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:03:58,271  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1821457857(78/97 serialized/live bytes, 2 ops)
18:03:58,272  INFO Memtable:266 - Writing Memtable-IndexInfo@1821457857(78/97 serialized/live bytes, 2 ops)
18:03:58,273  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:03:58,288  INFO StorageService:412 - Cassandra version: 1.1.0
18:03:58,288  INFO StorageService:413 - Thrift API version: 19.30.0
18:03:58,292  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:03:58,338  INFO StorageService:444 - Loading persisted ring state
18:03:58,341  INFO StorageService:525 - Starting up server gossip
18:03:58,351  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@194337136(126/157 serialized/live bytes, 3 ops)
18:03:58,788  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:03:58,790  INFO Memtable:266 - Writing Memtable-LocationInfo@194337136(126/157 serialized/live bytes, 3 ops)
18:03:58,791  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:03:59,800  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:03:59,813  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:03:59,821  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:03:59,830  WARN StorageService:633 - Generated random token Pqac41qD6pIMg4tq. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:03:59,832  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@197629132(53/66 serialized/live bytes, 2 ops)
18:03:59,833  INFO Memtable:266 - Writing Memtable-LocationInfo@197629132(53/66 serialized/live bytes, 2 ops)
18:04:00,470  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:04:00,474  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:04:00,484  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:04:00,485  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:04:00,518  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:04:00,522  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:04:00,526  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:04:00,526  INFO CassandraDaemon:212 - Listening for thrift clients...
18:04:00,576  INFO EmbeddedServerHelper:73 - Done sleeping
18:04:00,614  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:04:00,638  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:04:02,456  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 6.399 sec
18:04:02,479  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:04:02,481  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:04:02,484  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.ColumnSliceIteratorTest
18:04:04,054  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:04:04,082  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:04:04,301  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:04:04,509  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:04:05,000  INFO EmbeddedServerHelper:66 - Starting executor
18:04:05,002  INFO EmbeddedServerHelper:69 - Started executor
18:04:05,002  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:04:05,003  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:04:05,003  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter2194912455261444484.jar
18:04:05,005  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:04:05,025  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:04:05,044  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:04:05,045  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:04:05,051  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:04:05,162  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:04:05,175  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:04:05,231  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:04:05,232  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:04:05,287  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:04:05,295  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:04:05,298  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:04:05,300  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:04:05,330  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18312832760891.log
18:04:05,333  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18312832760891.log
18:04:05,330  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@223489506(39/48 serialized/live bytes, 1 ops)
18:04:05,341  INFO Memtable:266 - Writing Memtable-IndexInfo@223489506(39/48 serialized/live bytes, 1 ops)
18:04:05,356  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1226412018(39/48 serialized/live bytes, 1 ops)
18:04:05,359  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18312832760891.log
18:04:05,360  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:04:05,392  INFO StorageService:412 - Cassandra version: 1.1.0
18:04:05,392  INFO StorageService:413 - Thrift API version: 19.30.0
18:04:05,395  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:04:05,444  INFO StorageService:444 - Loading persisted ring state
18:04:05,448  INFO StorageService:525 - Starting up server gossip
18:04:05,460  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
18:04:06,400  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:04:06,403  INFO Memtable:266 - Writing Memtable-IndexInfo@1226412018(39/48 serialized/live bytes, 1 ops)
18:04:06,404  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:04:07,259  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:04:07,261  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:04:07,261  INFO Memtable:266 - Writing Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
18:04:08,003  INFO EmbeddedServerHelper:73 - Done sleeping
18:04:08,062  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:04:08,148  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:04:08,164  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:04:08,182  WARN StorageService:633 - Generated random token rcYkDOBXdQPZ7Itw. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:04:08,185  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@63323788(53/66 serialized/live bytes, 2 ops)
18:04:08,186  INFO Memtable:266 - Writing Memtable-LocationInfo@63323788(53/66 serialized/live bytes, 2 ops)
18:04:08,188  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:04:08,206 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:04:08,207  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:04:08,209  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:04:08,211  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:04:08,943  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:04:08,964  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:04:09,086  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:04:09,088  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:04:09,392  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:04:09,395  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:04:09,401  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:04:09,401  INFO CassandraDaemon:212 - Listening for thrift clients...
18:04:09,582  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 4, Failures: 0, Errors: 4, Skipped: 0, Time elapsed: 5.85 sec <<< FAILURE!
18:04:09,648  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:04:09,653  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:04:09,654  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.utils.TimeUUIDUtilsTest
18:04:12,161  INFO TimeUUIDUtilsTest:85 - Original Time: 1343837052160
18:04:12,166  INFO TimeUUIDUtilsTest:86 - ----
18:04:12,166  INFO TimeUUIDUtilsTest:90 - Java UUID: 88ce1000-dbf2-11e1-a61f-5cac4c624515
18:04:12,166  INFO TimeUUIDUtilsTest:91 - Java UUID timestamp: 135631298521600000
18:04:12,355  INFO TimeUUIDUtilsTest:92 - Date: Sat Oct 02 22:26:40 CEST 4299954
18:04:12,356  INFO TimeUUIDUtilsTest:94 - ----
18:04:12,356  INFO TimeUUIDUtilsTest:97 - eaio UUID: 00000138-e2ee-1d00-0000-000000000000
18:04:12,356  INFO TimeUUIDUtilsTest:98 - eaio UUID timestamp: 1343837052160
18:04:12,356  INFO TimeUUIDUtilsTest:99 - Date: Wed Aug 01 18:04:12 CEST 2012
18:04:12,357  INFO TimeUUIDUtilsTest:101 - ----
18:04:12,357  INFO TimeUUIDUtilsTest:104 - Java UUID to bytes to time: 1343837052160
18:04:12,357  INFO TimeUUIDUtilsTest:105 - Java UUID to bytes time to Date: Wed Aug 01 18:04:12 CEST 2012
18:04:12,357  INFO TimeUUIDUtilsTest:107 - ----
18:04:12,358  INFO TimeUUIDUtilsTest:110 - Java UUID to time: 1343837052160
18:04:12,358  INFO TimeUUIDUtilsTest:111 - Java UUID to time to Date: Wed Aug 01 18:04:12 CEST 2012
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.754 sec
Running me.prettyprint.cassandra.utils.ByteBufferOutputStreamTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.574 sec

Results :

Failed tests: 

Tests in error: 

Tests run: 270, Failures: 4, Errors: 46, Skipped: 2

[INFO] ------------------------------------------------------------------------
[ERROR] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] There are test failures.

Please refer to /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire-reports for the individual test results.
[INFO] ------------------------------------------------------------------------
[INFO] For more information, run Maven with the -e switch
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 3 minutes 50 seconds
[INFO] Finished at: Wed Aug 01 18:04:13 CEST 2012
[INFO] Final Memory: 33M/328M
[INFO] ------------------------------------------------------------------------
sudo mvn test
[sudo] password for cesare: 
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
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.214 sec
Running me.prettyprint.hector.api.beans.DynamicCompositeTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.687 sec
Running me.prettyprint.hector.api.VirtualKeyspaceTest
18:00:37,288  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:00:37,580  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:00:38,000  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:00:38,392  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:00:40,450  INFO EmbeddedServerHelper:66 - Starting executor
18:00:40,468  INFO EmbeddedServerHelper:69 - Started executor
18:00:40,469  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:00:40,469  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:00:40,469  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter531277650335534209.jar
18:00:40,471  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:00:40,490  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:00:40,554  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:00:40,555  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:00:40,559  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:00:40,774  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:00:40,805  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:00:40,878  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:00:40,879  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:00:40,948  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:00:40,958  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:00:40,961  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:00:40,970  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:00:40,993  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:00:40,996  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18108199166159.log
18:00:40,997  INFO Memtable:266 - Writing Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:00:41,002  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1380741586(39/48 serialized/live bytes, 1 ops)
18:00:41,003  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18108199166159.log
18:00:41,055  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18108199166159.log
18:00:41,056  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:00:41,082  INFO StorageService:412 - Cassandra version: 1.1.0
18:00:41,082  INFO StorageService:413 - Thrift API version: 19.30.0
18:00:41,086  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:00:41,174  INFO StorageService:444 - Loading persisted ring state
18:00:41,177  INFO StorageService:525 - Starting up server gossip
18:00:41,188  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@298922610(126/157 serialized/live bytes, 3 ops)
18:00:41,570  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:00:41,573  INFO Memtable:266 - Writing Memtable-IndexInfo@1380741586(39/48 serialized/live bytes, 1 ops)
18:00:41,574  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:00:41,864  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:00:41,865  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:00:41,866  INFO Memtable:266 - Writing Memtable-LocationInfo@298922610(126/157 serialized/live bytes, 3 ops)
18:00:42,134  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:00:42,173  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:00:42,185  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:00:42,195  WARN StorageService:633 - Generated random token EypnhZPURmxJNpF2. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:00:42,197  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@921400113(53/66 serialized/live bytes, 2 ops)
18:00:42,197  INFO Memtable:266 - Writing Memtable-LocationInfo@921400113(53/66 serialized/live bytes, 2 ops)
18:00:42,470  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:00:42,473  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:00:42,482  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:00:42,484  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:00:42,541  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:00:42,544  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:00:42,548  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:00:42,549  INFO CassandraDaemon:212 - Listening for thrift clients...
18:00:43,469  INFO EmbeddedServerHelper:73 - Done sleeping
18:00:43,641  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:00:43,732  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:00:44,014  INFO NodeId:200 - No saved local node id, using newly generated: 0cbd80e0-dbf2-11e1-0000-fe8ebeead9df
18:00:44,714  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 7.721 sec
18:00:44,738  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:00:44,741  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:00:44,743  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.hector.api.KeyspaceCreationTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.051 sec
Running me.prettyprint.hector.api.ClockResolutionTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.237 sec
Running me.prettyprint.hector.api.HFactoryTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.133 sec
Running me.prettyprint.hector.api.CompositeTest
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.526 sec
Running me.prettyprint.hector.api.ApiV2SystemTest
18:00:47,822  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:00:47,844  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:00:48,047  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:00:48,229  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:00:48,655  INFO EmbeddedServerHelper:66 - Starting executor
18:00:48,656  INFO EmbeddedServerHelper:69 - Started executor
18:00:48,657  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:00:48,658  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:00:48,658  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter6540172673545548189.jar
18:00:48,661  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:00:48,681  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:00:48,694  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:00:48,695  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:00:48,699  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:00:48,783  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:00:48,797  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:00:48,845  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:00:48,846  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:00:48,890  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:00:48,898  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:00:48,900  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:00:48,902  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:00:48,929  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18116492380596.log
18:00:48,929  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@404454518(39/48 serialized/live bytes, 1 ops)
18:00:48,931  INFO Memtable:266 - Writing Memtable-IndexInfo@404454518(39/48 serialized/live bytes, 1 ops)
18:00:48,932  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18116492380596.log
18:00:48,936  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1152296720(39/48 serialized/live bytes, 1 ops)
18:00:48,936  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18116492380596.log
18:00:48,937  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:00:48,960  INFO StorageService:412 - Cassandra version: 1.1.0
18:00:48,961  INFO StorageService:413 - Thrift API version: 19.30.0
18:00:48,966  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:00:49,017  INFO StorageService:444 - Loading persisted ring state
18:00:49,020  INFO StorageService:525 - Starting up server gossip
18:00:49,030  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:00:49,373  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:00:49,375  INFO Memtable:266 - Writing Memtable-IndexInfo@1152296720(39/48 serialized/live bytes, 1 ops)
18:00:49,377  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:00:49,652  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:00:49,653  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:00:49,653  INFO Memtable:266 - Writing Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:00:49,965  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:00:49,978  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:00:49,985  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:00:49,994  WARN StorageService:633 - Generated random token Bh7REq6AQ8o7Mqqo. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:00:49,996  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2013151593(53/66 serialized/live bytes, 2 ops)
18:00:49,996  INFO Memtable:266 - Writing Memtable-LocationInfo@2013151593(53/66 serialized/live bytes, 2 ops)
18:00:50,280  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:00:50,285  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:00:50,295  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:00:50,297  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:00:50,331  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:00:50,335  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:00:50,339  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:00:50,339  INFO CassandraDaemon:212 - Listening for thrift clients...
18:00:51,657  INFO EmbeddedServerHelper:73 - Done sleeping
18:00:51,701  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:00:51,733  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:00:51,848  INFO NodeId:200 - No saved local node id, using newly generated: 1168e080-dbf2-11e1-0000-fe8ebeead9fb
18:00:52,329  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.743 sec
18:00:52,347  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:00:52,351  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:00:52,353  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.serializers.ObjectSerializerTest
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.294 sec
Running me.prettyprint.cassandra.serializers.BigIntegerSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.085 sec
Running me.prettyprint.cassandra.serializers.JaxbSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.382 sec
Running me.prettyprint.cassandra.serializers.UUIDSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.101 sec
Running me.prettyprint.cassandra.serializers.BooleanSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.088 sec
Running me.prettyprint.cassandra.serializers.DateSerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.095 sec
Running me.prettyprint.cassandra.serializers.FastInfosetSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.29 sec
Running me.prettyprint.cassandra.serializers.IntegerSerializerTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.097 sec
Running me.prettyprint.cassandra.serializers.BytesArraySerializerTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.227 sec
Running me.prettyprint.cassandra.serializers.TypeInferringSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.379 sec
Running me.prettyprint.cassandra.serializers.ShortSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.101 sec
Running me.prettyprint.cassandra.serializers.PrefixedSerializerTest
18:00:58,540 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
18:00:58,553 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
18:00:58,558 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
18:00:58,561 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
18:00:58,565 ERROR PrefixedSerializer:66 - Unprefixed value received, throwing exception...
Tests run: 12, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.301 sec
Running me.prettyprint.cassandra.serializers.StringSerializerTest
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.326 sec
Running me.prettyprint.cassandra.serializers.LongSerializerTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.111 sec
Running me.prettyprint.cassandra.jndi.CassandraClientJndiResourceFactoryTest
18:01:00,354  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:00,389  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:00,577  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:00,759  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:01,210  INFO EmbeddedServerHelper:66 - Starting executor
18:01:01,211  INFO EmbeddedServerHelper:69 - Started executor
18:01:01,211  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:01,212  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:01,212  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3957556590079579457.jar
18:01:01,215  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:01,234  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:01,247  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:01,248  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:01,252  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:01,336  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:01,355  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:01,409  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:01,411  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:01,463  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:01,471  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:01,474  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:01,477  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:01,501  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18129040537373.log
18:01:01,505  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18129040537373.log
18:01:01,508  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18129040537373.log
18:01:01,509  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:01,511  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@458982688(78/97 serialized/live bytes, 2 ops)
18:01:01,512  INFO Memtable:266 - Writing Memtable-IndexInfo@458982688(78/97 serialized/live bytes, 2 ops)
18:01:01,512  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:01,526  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:01,527  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:01,534  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:01,589  INFO StorageService:444 - Loading persisted ring state
18:01:01,593  INFO StorageService:525 - Starting up server gossip
18:01:01,604  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:01:02,211  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:01:02,215  INFO Memtable:266 - Writing Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:01:02,216  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:02,471  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:02,489  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:02,495  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:02,504  WARN StorageService:633 - Generated random token 2BTUPeIyrIP0xm0C. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:02,506  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@540108943(53/66 serialized/live bytes, 2 ops)
18:01:02,506  INFO Memtable:266 - Writing Memtable-LocationInfo@540108943(53/66 serialized/live bytes, 2 ops)
18:01:02,762  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:02,766  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:02,775  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:02,777  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:02,818  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:02,821  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:02,825  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:02,826  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:04,212  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:04,531  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:04,596  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:01:04,634  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.65 sec
18:01:04,675  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:04,677  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:04,677  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.dao.SimpleCassandraDaoTest
18:01:05,871  INFO TestContextManager:185 - @TestExecutionListeners is not present for class [class me.prettyprint.cassandra.dao.SimpleCassandraDaoTest]: using defaults.
18:01:05,939  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:05,969  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:06,132  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:06,294  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:06,756  INFO EmbeddedServerHelper:66 - Starting executor
18:01:06,757  INFO EmbeddedServerHelper:69 - Started executor
18:01:06,757  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:06,758  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:06,758  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter6310978130335924458.jar
18:01:06,761  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:06,776  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:06,790  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:06,791  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:06,796  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:06,879  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:06,894  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:06,936  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:06,937  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:06,981  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:06,989  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:06,992  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:06,996  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:07,018  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18134566420095.log
18:01:07,021  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18134566420095.log
18:01:07,024  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18134566420095.log
18:01:07,024  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@153523906(78/97 serialized/live bytes, 2 ops)
18:01:07,024  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:07,026  INFO Memtable:266 - Writing Memtable-IndexInfo@153523906(78/97 serialized/live bytes, 2 ops)
18:01:07,028  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:07,047  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:07,048  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:07,053  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:07,100  INFO StorageService:444 - Loading persisted ring state
18:01:07,103  INFO StorageService:525 - Starting up server gossip
18:01:07,114  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2137169521(126/157 serialized/live bytes, 3 ops)
18:01:07,426  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:01:07,428  INFO Memtable:266 - Writing Memtable-LocationInfo@2137169521(126/157 serialized/live bytes, 3 ops)
18:01:07,429  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:07,707  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:07,726  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:07,735  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:07,745  WARN StorageService:633 - Generated random token Ve4D80MU2YvdJZoF. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:07,748  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1546386943(53/66 serialized/live bytes, 2 ops)
18:01:07,748  INFO Memtable:266 - Writing Memtable-LocationInfo@1546386943(53/66 serialized/live bytes, 2 ops)
18:01:08,112  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:08,117  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:08,131  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:08,133  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:08,168  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:08,171  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:08,175  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:08,175  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:09,758  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:10,004  INFO XmlBeanDefinitionReader:315 - Loading XML bean definitions from class path resource [cassandra-context-test-v2.xml]
18:01:10,379  INFO GenericApplicationContext:456 - Refreshing org.springframework.context.support.GenericApplicationContext@459d3b3a: startup date [Wed Aug 01 18:01:10 CEST 2012]; root of context hierarchy
18:01:10,641  INFO DefaultListableBeanFactory:557 - Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@6a69ed4a: defining beans [cassandraHostConfigurator,cluster,consistencyLevelPolicy,keyspaceOperator,simpleCassandraDao,org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor]; root of factory hierarchy
18:01:10,777  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:10,799  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:01:11,008  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.523 sec
18:01:11,033  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:11,037  INFO GenericApplicationContext:1002 - Closing org.springframework.context.support.GenericApplicationContext@459d3b3a: startup date [Wed Aug 01 18:01:10 CEST 2012]; root of context hierarchy
18:01:11,039  INFO DefaultListableBeanFactory:422 - Destroying singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@6a69ed4a: defining beans [cassandraHostConfigurator,cluster,consistencyLevelPolicy,keyspaceOperator,simpleCassandraDao,org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor]; root of factory hierarchy
18:01:11,039  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:11,042  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.ConfigurableConsistencyLevelTest
18:01:12,147  INFO ConfigurableConsistencyLevel:57 - READ ConsistencyLevel set to ANY for ColumnFamily OtherCf
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.216 sec
Running me.prettyprint.cassandra.model.RangeSlicesQueryTest
18:01:12,733  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:12,762  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:12,956  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:13,127  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:13,556  INFO EmbeddedServerHelper:66 - Starting executor
18:01:13,558  INFO EmbeddedServerHelper:69 - Started executor
18:01:13,558  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:13,558  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:13,558  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter5375038015584976331.jar
18:01:13,560  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:13,581  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:13,595  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:13,596  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:13,600  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:13,694  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:13,707  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:13,752  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:13,753  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:13,795  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:13,803  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:13,805  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:13,807  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:13,830  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18141386769547.log
18:01:13,832  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18141386769547.log
18:01:13,836  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@881341271(78/97 serialized/live bytes, 2 ops)
18:01:13,836  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18141386769547.log
18:01:13,837  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:13,837  INFO Memtable:266 - Writing Memtable-IndexInfo@881341271(78/97 serialized/live bytes, 2 ops)
18:01:13,838  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:13,853  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:13,853  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:13,857  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:13,917  INFO StorageService:444 - Loading persisted ring state
18:01:13,920  INFO StorageService:525 - Starting up server gossip
18:01:13,931  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
18:01:14,192  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:01:14,194  INFO Memtable:266 - Writing Memtable-LocationInfo@325350008(126/157 serialized/live bytes, 3 ops)
18:01:14,195  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:14,481  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:14,498  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:14,504  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:14,512  WARN StorageService:633 - Generated random token dDoTD2ojEBBtmJti. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:14,514  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
18:01:14,515  INFO Memtable:266 - Writing Memtable-LocationInfo@1458850232(53/66 serialized/live bytes, 2 ops)
18:01:14,762  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:14,765  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:14,775  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:14,776  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:14,809  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:14,813  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:14,817  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:14,817  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:16,558  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:16,596  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:16,619  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:16,928  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.5 sec
18:01:16,946  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:16,948  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:16,950  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.GetSliceQueryTest
18:01:17,950  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:17,975  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:18,171  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:18,352  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:18,798  INFO EmbeddedServerHelper:66 - Starting executor
18:01:18,799  INFO EmbeddedServerHelper:69 - Started executor
18:01:18,799  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:18,799  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:18,800  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter6078657981519629536.jar
18:01:18,802  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:18,819  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:18,832  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:18,833  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:18,837  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:18,938  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:18,951  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:18,993  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:18,994  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:19,036  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:19,044  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:19,047  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:19,052  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:19,069  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18146637357910.log
18:01:19,071  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@881341271(39/48 serialized/live bytes, 1 ops)
18:01:19,071  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18146637357910.log
18:01:19,072  INFO Memtable:266 - Writing Memtable-IndexInfo@881341271(39/48 serialized/live bytes, 1 ops)
18:01:19,075  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@871992664(39/48 serialized/live bytes, 1 ops)
18:01:19,075  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18146637357910.log
18:01:19,075  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:19,091  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:19,091  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:19,095  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:19,143  INFO StorageService:444 - Loading persisted ring state
18:01:19,146  INFO StorageService:525 - Starting up server gossip
18:01:19,156  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
18:01:19,547  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:19,549  INFO Memtable:266 - Writing Memtable-IndexInfo@871992664(39/48 serialized/live bytes, 1 ops)
18:01:19,550  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:19,797  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:19,798  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:19,799  INFO Memtable:266 - Writing Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
18:01:20,056  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:20,079  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:20,086  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:20,095  WARN StorageService:633 - Generated random token FUsza4mgbwQ6aYS8. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:20,097  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2013151593(53/66 serialized/live bytes, 2 ops)
18:01:20,098  INFO Memtable:266 - Writing Memtable-LocationInfo@2013151593(53/66 serialized/live bytes, 2 ops)
18:01:20,360  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:20,363  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:20,373  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:20,375  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:20,412  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:20,416  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:20,419  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:20,420  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:21,799  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:21,832  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:21,839  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:22,003  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.269 sec
18:01:22,022  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:22,024  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:22,025  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.MultigetCountQueryTest
18:01:23,059  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:23,082  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:23,278  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:23,454  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:23,882  INFO EmbeddedServerHelper:66 - Starting executor
18:01:23,883  INFO EmbeddedServerHelper:69 - Started executor
18:01:23,883  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:23,883  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:23,884  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3915649142378393915.jar
18:01:23,886  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:23,903  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:23,916  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:23,917  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:23,921  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:24,017  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:24,031  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:24,079  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:24,080  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:24,122  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:24,130  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:24,133  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:24,136  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:24,155  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@160094946(39/48 serialized/live bytes, 1 ops)
18:01:24,157  INFO Memtable:266 - Writing Memtable-IndexInfo@160094946(39/48 serialized/live bytes, 1 ops)
18:01:24,166  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1235930463(39/48 serialized/live bytes, 1 ops)
18:01:24,168  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18151718625359.log
18:01:24,174  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18151718625359.log
18:01:24,180  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18151718625359.log
18:01:24,181  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:24,198  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:24,198  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:24,202  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:24,247  INFO StorageService:444 - Loading persisted ring state
18:01:24,251  INFO StorageService:525 - Starting up server gossip
18:01:24,261  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
18:01:24,525  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:24,527  INFO Memtable:266 - Writing Memtable-IndexInfo@1235930463(39/48 serialized/live bytes, 1 ops)
18:01:24,529  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:24,861  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:24,862  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:24,863  INFO Memtable:266 - Writing Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
18:01:25,120  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:25,133  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:25,140  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:25,148  WARN StorageService:633 - Generated random token xDmK0wSYqaKTRAQ7. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:25,150  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@197629132(53/66 serialized/live bytes, 2 ops)
18:01:25,151  INFO Memtable:266 - Writing Memtable-LocationInfo@197629132(53/66 serialized/live bytes, 2 ops)
18:01:25,422  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:25,425  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:25,435  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:25,436  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:25,468  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:25,472  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:25,476  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:25,476  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:26,883  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:26,932  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:26,955  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:01:27,145  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.304 sec
18:01:27,168  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:27,169  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:27,170  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.CqlQueryTest
18:01:28,265  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:28,290  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:28,487  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:28,670  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:29,107  INFO EmbeddedServerHelper:66 - Starting executor
18:01:29,108  INFO EmbeddedServerHelper:69 - Started executor
18:01:29,109  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:29,109  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:29,109  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter1250493279285947270.jar
18:01:29,112  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:29,127  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:29,140  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:29,141  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:29,145  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:29,238  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:29,252  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:29,300  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:29,301  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:29,343  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:29,351  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:29,354  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:29,356  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:29,379  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18156942661387.log
18:01:29,381  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18156942661387.log
18:01:29,385  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18156942661387.log
18:01:29,385  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@360777192(39/48 serialized/live bytes, 1 ops)
18:01:29,386  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:29,387  INFO Memtable:266 - Writing Memtable-IndexInfo@360777192(39/48 serialized/live bytes, 1 ops)
18:01:29,395  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@471860896(39/48 serialized/live bytes, 1 ops)
18:01:29,404  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:29,404  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:29,409  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:29,464  INFO StorageService:444 - Loading persisted ring state
18:01:29,468  INFO StorageService:525 - Starting up server gossip
18:01:29,478  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
18:01:29,727  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:29,732  INFO Memtable:266 - Writing Memtable-IndexInfo@471860896(39/48 serialized/live bytes, 1 ops)
18:01:29,733  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:30,036  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:30,037  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:30,037  INFO Memtable:266 - Writing Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
18:01:30,307  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:30,319  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:30,325  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:30,334  WARN StorageService:633 - Generated random token NeOkeV1Z3BvUcV01. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:30,336  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1543103262(53/66 serialized/live bytes, 2 ops)
18:01:30,340  INFO Memtable:266 - Writing Memtable-LocationInfo@1543103262(53/66 serialized/live bytes, 2 ops)
18:01:30,588  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:30,591  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:30,601  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:30,603  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:30,636  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:30,640  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:30,644  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:30,644  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:32,109  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:32,145  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:32,175  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:32,484  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.442 sec
18:01:32,502  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:32,503  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:32,504  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.RangeSlicesCounterQueryTest
18:01:33,469  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:33,493  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:33,687  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:33,865  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:34,301  INFO EmbeddedServerHelper:66 - Starting executor
18:01:34,303  INFO EmbeddedServerHelper:69 - Started executor
18:01:34,303  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:34,304  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:34,304  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3302378188665525607.jar
18:01:34,306  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:34,323  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:34,336  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:34,337  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:34,341  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:34,434  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:34,446  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:34,491  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:34,492  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:34,535  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:34,543  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:34,546  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:34,548  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:34,567  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@2137552888(39/48 serialized/live bytes, 1 ops)
18:01:34,569  INFO Memtable:266 - Writing Memtable-IndexInfo@2137552888(39/48 serialized/live bytes, 1 ops)
18:01:34,572  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18162137137804.log
18:01:34,574  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:01:34,577  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18162137137804.log
18:01:34,581  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18162137137804.log
18:01:34,581  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:34,597  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:34,597  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:34,603  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:34,649  INFO StorageService:444 - Loading persisted ring state
18:01:34,652  INFO StorageService:525 - Starting up server gossip
18:01:34,662  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:01:34,920  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:34,923  INFO Memtable:266 - Writing Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:01:34,924  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:35,179  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:35,180  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:35,180  INFO Memtable:266 - Writing Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:01:35,493  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:35,509  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:35,516  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:35,524  WARN StorageService:633 - Generated random token LrQBe5yYJQYEbRbN. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:35,526  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1660575731(53/66 serialized/live bytes, 2 ops)
18:01:35,527  INFO Memtable:266 - Writing Memtable-LocationInfo@1660575731(53/66 serialized/live bytes, 2 ops)
18:01:35,795  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:35,799  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:35,810  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:35,811  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:35,844  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:35,847  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:35,851  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:35,851  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:37,303  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:37,344  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:37,369  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:37,479  INFO NodeId:200 - No saved local node id, using newly generated: 2c9b9d70-dbf2-11e1-0000-fe8ebeead9fe
18:01:37,577  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.326 sec
18:01:37,595  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:37,597  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:37,597  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.SuperColumnSliceTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.108 sec
Running me.prettyprint.cassandra.model.MultigetSliceQueryTest
18:01:38,927  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:38,951  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:39,150  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:39,331  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:39,777  INFO EmbeddedServerHelper:66 - Starting executor
18:01:39,778  INFO EmbeddedServerHelper:69 - Started executor
18:01:39,779  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:39,779  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:39,779  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3580611962473289647.jar
18:01:39,781  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:39,799  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:39,812  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:39,813  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:39,817  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:39,909  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:39,923  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:39,967  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:39,968  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:40,012  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:40,021  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:40,025  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:40,028  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:40,050  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18167609892693.log
18:01:40,051  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@192714409(39/48 serialized/live bytes, 1 ops)
18:01:40,052  INFO Memtable:266 - Writing Memtable-IndexInfo@192714409(39/48 serialized/live bytes, 1 ops)
18:01:40,055  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18167609892693.log
18:01:40,056  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@458982688(39/48 serialized/live bytes, 1 ops)
18:01:40,058  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18167609892693.log
18:01:40,058  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:40,071  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:40,071  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:40,075  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:40,129  INFO StorageService:444 - Loading persisted ring state
18:01:40,132  INFO StorageService:525 - Starting up server gossip
18:01:40,142  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:01:40,417  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:40,420  INFO Memtable:266 - Writing Memtable-IndexInfo@458982688(39/48 serialized/live bytes, 1 ops)
18:01:40,421  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:40,710  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:40,711  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:40,712  INFO Memtable:266 - Writing Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:01:40,969  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:40,983  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:40,990  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:40,998  WARN StorageService:633 - Generated random token Q8gmXMLHTKysHu8Z. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:41,000  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1660575731(53/66 serialized/live bytes, 2 ops)
18:01:41,001  INFO Memtable:266 - Writing Memtable-LocationInfo@1660575731(53/66 serialized/live bytes, 2 ops)
18:01:41,260  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:41,264  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:41,273  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:41,275  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:41,316  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:41,320  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:41,325  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:41,325  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:42,779  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:42,817  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:42,842  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:43,011  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.303 sec
18:01:43,029  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:43,031  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:43,032  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.AbstractSliceQueryTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.479 sec
Running me.prettyprint.cassandra.model.IndexedSlicesQueryTest
18:01:44,778  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:44,804  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:45,009  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:45,185  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:45,627  INFO EmbeddedServerHelper:66 - Starting executor
18:01:45,628  INFO EmbeddedServerHelper:69 - Started executor
18:01:45,628  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:45,629  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:45,629  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter1402890079148212427.jar
18:01:45,632  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:45,653  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:45,668  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:45,669  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:45,673  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:45,755  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:45,769  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:45,814  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:45,815  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:45,856  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:45,864  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:45,866  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:45,867  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:45,890  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@223489506(39/48 serialized/live bytes, 1 ops)
18:01:45,891  INFO Memtable:266 - Writing Memtable-IndexInfo@223489506(39/48 serialized/live bytes, 1 ops)
18:01:45,906  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18173458674110.log
18:01:45,910  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1937516689(39/48 serialized/live bytes, 1 ops)
18:01:45,910  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18173458674110.log
18:01:45,913  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18173458674110.log
18:01:45,914  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:45,933  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:45,933  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:45,936  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:45,981  INFO StorageService:444 - Loading persisted ring state
18:01:45,984  INFO StorageService:525 - Starting up server gossip
18:01:45,994  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
18:01:46,282  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:46,284  INFO Memtable:266 - Writing Memtable-IndexInfo@1937516689(39/48 serialized/live bytes, 1 ops)
18:01:46,285  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:46,539  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:46,540  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:46,540  INFO Memtable:266 - Writing Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
18:01:47,088  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:47,101  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:47,108  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:47,116  WARN StorageService:633 - Generated random token Pc83EawLDI06018p. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:47,118  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@995824187(53/66 serialized/live bytes, 2 ops)
18:01:47,119  INFO Memtable:266 - Writing Memtable-LocationInfo@995824187(53/66 serialized/live bytes, 2 ops)
18:01:47,458  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:47,461  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:47,470  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:47,472  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:47,505  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:47,509  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:47,513  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:47,513  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:48,629  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:48,665  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:48,693  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:48,911  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.342 sec
18:01:48,927  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:48,929  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:48,930  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.HColumnFamilyTest
18:01:49,888  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:49,912  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:50,103  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:50,275  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:50,706  INFO EmbeddedServerHelper:66 - Starting executor
18:01:50,708  INFO EmbeddedServerHelper:69 - Started executor
18:01:50,709  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:50,709  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:50,709  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter820813063874024108.jar
18:01:50,711  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:50,727  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:50,740  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:50,741  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:50,745  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:50,827  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:50,846  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:50,890  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:50,891  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:50,932  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:50,940  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:50,943  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:50,945  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:50,970  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@2066231378(39/48 serialized/live bytes, 1 ops)
18:01:50,971  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18178539011365.log
18:01:50,972  INFO Memtable:266 - Writing Memtable-IndexInfo@2066231378(39/48 serialized/live bytes, 1 ops)
18:01:50,974  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18178539011365.log
18:01:50,977  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1235930463(39/48 serialized/live bytes, 1 ops)
18:01:50,979  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18178539011365.log
18:01:50,979  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:50,993  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:50,993  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:50,998  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:51,046  INFO StorageService:444 - Loading persisted ring state
18:01:51,050  INFO StorageService:525 - Starting up server gossip
18:01:51,061  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
18:01:51,297  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:01:51,299  INFO Memtable:266 - Writing Memtable-IndexInfo@1235930463(39/48 serialized/live bytes, 1 ops)
18:01:51,301  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:51,582  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:01:51,583  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:51,583  INFO Memtable:266 - Writing Memtable-LocationInfo@1333580125(126/157 serialized/live bytes, 3 ops)
18:01:51,907  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:51,924  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:51,930  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:51,938  WARN StorageService:633 - Generated random token Yjc4V7VqkGbIrHc1. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:51,940  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@598834505(53/66 serialized/live bytes, 2 ops)
18:01:51,941  INFO Memtable:266 - Writing Memtable-LocationInfo@598834505(53/66 serialized/live bytes, 2 ops)
18:01:52,199  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:52,205  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:52,218  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:52,219  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:52,255  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:52,259  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:52,263  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:52,263  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:53,709  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:53,742  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:53,765  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:01:53,980  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.304 sec
18:01:53,999  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:54,001  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:54,002  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.MutatorTest
18:01:55,066  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:01:55,091  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:01:55,287  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:01:55,466  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:01:55,908  INFO EmbeddedServerHelper:66 - Starting executor
18:01:55,909  INFO EmbeddedServerHelper:69 - Started executor
18:01:55,909  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:01:55,910  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:01:55,910  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3883322571861534340.jar
18:01:55,914  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:01:55,930  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:01:55,944  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:01:55,945  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:01:55,949  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:01:56,041  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:01:56,053  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:01:56,097  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:01:56,098  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:01:56,140  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:56,148  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:01:56,151  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:01:56,153  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:01:56,182  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18183743003012.log
18:01:56,185  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18183743003012.log
18:01:56,187  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@830274872(78/97 serialized/live bytes, 2 ops)
18:01:56,189  INFO Memtable:266 - Writing Memtable-IndexInfo@830274872(78/97 serialized/live bytes, 2 ops)
18:01:56,190  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18183743003012.log
18:01:56,191  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:01:56,193  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:01:56,215  INFO StorageService:412 - Cassandra version: 1.1.0
18:01:56,215  INFO StorageService:413 - Thrift API version: 19.30.0
18:01:56,223  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:01:56,271  INFO StorageService:444 - Loading persisted ring state
18:01:56,274  INFO StorageService:525 - Starting up server gossip
18:01:56,285  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@194337136(126/157 serialized/live bytes, 3 ops)
18:01:56,538  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:01:56,540  INFO Memtable:266 - Writing Memtable-LocationInfo@194337136(126/157 serialized/live bytes, 3 ops)
18:01:56,542  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:01:56,788  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:01:56,801  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:01:56,807  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:01:56,819  WARN StorageService:633 - Generated random token h46ncHKPc7gEFqyD. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:01:56,822  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@780298059(53/66 serialized/live bytes, 2 ops)
18:01:56,823  INFO Memtable:266 - Writing Memtable-LocationInfo@780298059(53/66 serialized/live bytes, 2 ops)
18:01:57,202  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:01:57,205  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:01:57,217  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:01:57,218  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:01:57,262  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:01:57,265  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:01:57,269  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:01:57,270  INFO CassandraDaemon:212 - Listening for thrift clients...
18:01:58,910  INFO EmbeddedServerHelper:73 - Done sleeping
18:01:58,947  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:01:58,979  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:01:59,235  INFO NodeId:200 - No saved local node id, using newly generated: 39935130-dbf2-11e1-0000-fe8ebeead9fb
18:01:59,241  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.397 sec
18:01:59,280  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:01:59,284  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:01:59,288  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.model.ColumnSliceTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.111 sec
Running me.prettyprint.cassandra.connection.HConnectionManagerTest
18:02:00,760  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:00,786  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:00,987  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:01,169  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:01,616  INFO EmbeddedServerHelper:66 - Starting executor
18:02:01,618  INFO EmbeddedServerHelper:69 - Started executor
18:02:01,618  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:01,618  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:01,619  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3324329624753825257.jar
18:02:01,622  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:01,638  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:01,652  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:01,653  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:01,657  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:01,751  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:01,768  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:01,818  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:01,819  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:01,874  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:01,882  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:01,886  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:01,888  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:01,907  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18189450469297.log
18:02:01,909  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18189450469297.log
18:02:01,913  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18189450469297.log
18:02:01,913  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:02:01,917  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:02:01,918  INFO Memtable:266 - Writing Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:02:01,924  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1939520811(39/48 serialized/live bytes, 1 ops)
18:02:01,940  INFO StorageService:412 - Cassandra version: 1.1.0
18:02:01,940  INFO StorageService:413 - Thrift API version: 19.30.0
18:02:01,944  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:02:01,996  INFO StorageService:444 - Loading persisted ring state
18:02:01,999  INFO StorageService:525 - Starting up server gossip
18:02:02,011  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:02:02,294  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:02:02,298  INFO Memtable:266 - Writing Memtable-IndexInfo@1939520811(39/48 serialized/live bytes, 1 ops)
18:02:02,299  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:02:02,640  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:02:02,641  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:02:02,641  INFO Memtable:266 - Writing Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:02:02,920  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:02:02,936  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:02:02,943  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:02:02,951  WARN StorageService:633 - Generated random token foU87DoKE3438CES. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:02:02,953  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@197629132(53/66 serialized/live bytes, 2 ops)
18:02:02,954  INFO Memtable:266 - Writing Memtable-LocationInfo@197629132(53/66 serialized/live bytes, 2 ops)
18:02:03,268  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:02:03,271  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:02:03,285  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:02:03,286  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:02:03,330  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:02:03,334  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:02:03,338  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:02:03,338  INFO CassandraDaemon:212 - Listening for thrift clients...
18:02:04,618  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:04,650  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:04,675  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:02:04,680  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
18:02:04,685  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
18:02:04,686  INFO HConnectionManager:172 - Remove status for CassandraHost pool 127.0.0.1(127.0.0.1):9170 was true
18:02:04,698  INFO HConnectionManager:114 - Added host 127.0.0.1(127.0.0.1):9170 to pool
18:02:04,698 ERROR HConnectionManager:421 - MARK HOST AS DOWN TRIGGERED for host 127.0.0.1(127.0.0.1):9170
18:02:04,699 ERROR HConnectionManager:425 - Pool state on shutdown: <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}; IsActive?: true; Active: 0; Blocked: 0; Idle: 16; NumBeforeExhausted: 50
18:02:04,699  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
18:02:04,703  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
18:02:04,704  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:04,705  INFO HConnectionManager:165 - Host 127.0.0.1(127.0.0.1):9170 not in active pools, but found in retry service.
18:02:04,717  INFO HConnectionManager:172 - Remove status for CassandraHost pool 127.0.0.1(127.0.0.1):9170 was true
18:02:04,726  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:04,738 ERROR HConnectionManager:119 - Transport exception host to HConnectionManager: 127.0.0.1(127.0.0.1):9180
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
18:02:04,739  INFO HConnectionManager:114 - Added host 127.0.0.1(127.0.0.1):9170 to pool
18:02:04,748  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:04,755 ERROR HConnectionManager:421 - MARK HOST AS DOWN TRIGGERED for host 127.0.0.1(127.0.0.1):9170
18:02:04,756 ERROR HConnectionManager:425 - Pool state on shutdown: <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}; IsActive?: true; Active: 1; Blocked: 0; Idle: 15; NumBeforeExhausted: 49
18:02:04,756  INFO ConcurrentHClientPool:163 - Shutdown triggered on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
18:02:04,757  INFO ConcurrentHClientPool:171 - Shutdown complete on <ConcurrentCassandraClientPoolByHost>:{127.0.0.1(127.0.0.1):9170}
18:02:04,759  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:04,773  INFO HConnectionManager:191 - Suspend operation status was true for CassandraHost 127.0.0.1(127.0.0.1):9170
18:02:04,774  INFO HConnectionManager:212 - UN-Suspend operation status was true for CassandraHost 127.0.0.1(127.0.0.1):9170
18:02:04,775  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:04,785  WARN HConnectionManager:306 - Could not fullfill request on this host CassandraClient<127.0.0.1:9170-99>
18:02:04,785  WARN HConnectionManager:307 - Exception: 
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
18:02:04,789  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.257 sec
18:02:04,828  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:02:04,829  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:02:04,830  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.connection.ConcurrentHClientPoolTest
18:02:05,796  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:05,823  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:06,063  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:06,274  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:06,762  INFO EmbeddedServerHelper:66 - Starting executor
18:02:06,763  INFO EmbeddedServerHelper:69 - Started executor
18:02:06,763  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:06,763  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:06,763  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter8945871341766861825.jar
18:02:06,766  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:06,787  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:06,806  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:06,808  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:06,814  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:06,921  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:06,937  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:06,995  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:06,996  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:07,049  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:07,059  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:07,062  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:07,065  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:07,093  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@160094946(78/97 serialized/live bytes, 2 ops)
18:02:07,095  INFO Memtable:266 - Writing Memtable-IndexInfo@160094946(78/97 serialized/live bytes, 2 ops)
18:02:07,095  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:02:07,098  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18194600236756.log
18:02:07,101  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18194600236756.log
18:02:07,106  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18194600236756.log
18:02:07,106  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:02:07,124  INFO StorageService:412 - Cassandra version: 1.1.0
18:02:07,124  INFO StorageService:413 - Thrift API version: 19.30.0
18:02:07,131  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:02:07,193  INFO StorageService:444 - Loading persisted ring state
18:02:07,198  INFO StorageService:525 - Starting up server gossip
18:02:07,212  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:02:07,789  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:02:07,792  INFO Memtable:266 - Writing Memtable-LocationInfo@911996452(126/157 serialized/live bytes, 3 ops)
18:02:07,793  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:02:09,202  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:02:09,214  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:02:09,224  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:02:09,236  WARN StorageService:633 - Generated random token DDxf9lKiyP1jJYvm. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:02:09,238  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@540108943(53/66 serialized/live bytes, 2 ops)
18:02:09,239  INFO Memtable:266 - Writing Memtable-LocationInfo@540108943(53/66 serialized/live bytes, 2 ops)
18:02:09,763  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:09,788  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:09,801 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:09,801  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:09,803  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:09,805  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:02:09,818  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:09,818 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:09,819  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:09,820  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:09,822  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:09,823 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:09,823  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:09,824  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:09,826  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 3, Failures: 0, Errors: 3, Skipped: 0, Time elapsed: 4.248 sec <<< FAILURE!
18:02:09,827 ERROR AbstractCassandraDaemon:370 - Exception encountered during startup
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
Exception encountered during startup: java.lang.InterruptedException
18:02:09,882  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:02:09,883  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.connection.HConnectionManagerListenerTest
18:02:11,758  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:11,784  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:12,029  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:12,270  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:12,997  INFO EmbeddedServerHelper:66 - Starting executor
18:02:12,998  INFO EmbeddedServerHelper:69 - Started executor
18:02:12,998  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:12,999  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:12,999  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter4933163825956237450.jar
18:02:13,002  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:13,021  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:13,036  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:13,037  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:13,041  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:13,202  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:13,214  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:13,254  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:13,254  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:13,297  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:13,305  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:13,307  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:13,309  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:13,329  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18200744221601.log
18:02:13,334  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18200744221601.log
18:02:13,337  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18200744221601.log
18:02:13,337  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:02:13,446  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@239228739(39/48 serialized/live bytes, 1 ops)
18:02:13,447  INFO Memtable:266 - Writing Memtable-IndexInfo@239228739(39/48 serialized/live bytes, 1 ops)
18:02:13,455  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1515632954(39/48 serialized/live bytes, 1 ops)
18:02:13,463  INFO StorageService:412 - Cassandra version: 1.1.0
18:02:13,464  INFO StorageService:413 - Thrift API version: 19.30.0
18:02:13,468  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:02:13,536  INFO StorageService:444 - Loading persisted ring state
18:02:13,540  INFO StorageService:525 - Starting up server gossip
18:02:13,551  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1921615168(126/157 serialized/live bytes, 3 ops)
18:02:14,483  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:02:14,486  INFO Memtable:266 - Writing Memtable-IndexInfo@1515632954(39/48 serialized/live bytes, 1 ops)
18:02:14,487  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:02:15,691  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:02:15,692  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:02:15,692  INFO Memtable:266 - Writing Memtable-LocationInfo@1921615168(126/157 serialized/live bytes, 3 ops)
18:02:15,999  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:16,037  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:16,053 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:16,054  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:16,055  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:16,058  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:02:16,232  INFO HConnectionManager:165 - Host 127.0.0.1(127.0.0.1):9170 not in active pools, but found in retry service.
18:02:16,233  INFO HConnectionManager:172 - Remove status for CassandraHost pool 127.0.0.1(127.0.0.1):9170 was true
18:02:16,236  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:16,237 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:16,237  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:16,238  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:16,299 ERROR HConnectionManager:119 - Transport exception host to HConnectionManager: 127.0.0.1(127.0.0.1):9180
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
18:02:16,305  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:16,306 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:16,306  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:16,307  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:16,321 ERROR HConnectionManager:119 - Transport exception host to HConnectionManager: 127.0.0.1(127.0.0.1):9170
me.prettyprint.hector.api.exceptions.HectorTransportException: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
        at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:144)
        at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:26)
        at me.prettyprint.cassandra.connection.ConcurrentHClientPool.createClient(ConcurrentHClientPool.java:147)
        at me.prettyprint.cassandra.connection.ConcurrentHClientPool.<init>(ConcurrentHClientPool.java:53)
        at me.prettyprint.cassandra.connection.RoundRobinBalancingPolicy.createConnection(RoundRobinBalancingPolicy.java:67)
        at me.prettyprint.cassandra.connection.HConnectionManager.addCassandraHost(HConnectionManager.java:112)
        at me.prettyprint.cassandra.connection.HConnectionManagerListenerTest.testOnAddCassandraHostFailExists(HConnectionManagerListenerTest.java:65)
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
18:02:16,327  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:16,329 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:16,329  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:16,330  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:16,376  INFO HConnectionManager:165 - Host 127.0.0.1(127.0.0.1):9170 not in active pools, but found in retry service.
18:02:16,376  INFO HConnectionManager:172 - Remove status for CassandraHost pool 127.0.0.1(127.0.0.1):9170 was true
18:02:16,378 ERROR HConnectionManager:119 - Transport exception host to HConnectionManager: 127.0.0.1(127.0.0.1):9170
me.prettyprint.hector.api.exceptions.HectorTransportException: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
        at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:144)
        at me.prettyprint.cassandra.connection.client.HThriftClient.open(HThriftClient.java:26)
        at me.prettyprint.cassandra.connection.ConcurrentHClientPool.createClient(ConcurrentHClientPool.java:147)
        at me.prettyprint.cassandra.connection.ConcurrentHClientPool.<init>(ConcurrentHClientPool.java:53)
        at me.prettyprint.cassandra.connection.RoundRobinBalancingPolicy.createConnection(RoundRobinBalancingPolicy.java:67)
        at me.prettyprint.cassandra.connection.HConnectionManager.addCassandraHost(HConnectionManager.java:112)
        at me.prettyprint.cassandra.connection.HConnectionManagerListenerTest.testOnAddCassandraHostSuccess(HConnectionManagerListenerTest.java:84)
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
18:02:16,390  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:16,391 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:16,392  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:16,393  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:16,421 ERROR HConnectionManager:421 - MARK HOST AS DOWN TRIGGERED for host 127.0.0.1(127.0.0.1):9170
18:02:16,423  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 1s
18:02:16,424 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:16,425  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:16,426  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:16,476 ERROR HConnectionManager:421 - MARK HOST AS DOWN TRIGGERED for host 127.0.0.1(127.0.0.1):9170
18:02:16,501  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:02:16,513  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:02:16,522  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:02:16,534  WARN StorageService:633 - Generated random token LD6ygoOPcrFLuNgz. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:02:16,536  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1755221044(53/66 serialized/live bytes, 2 ops)
18:02:16,536  INFO Memtable:266 - Writing Memtable-LocationInfo@1755221044(53/66 serialized/live bytes, 2 ops)
18:02:17,423  INFO CassandraHostRetryService:146 - Not checking that 127.0.0.1(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:02:17,425  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:17,425  INFO CassandraHostRetryService:159 - Downed Host retry status false with host: 127.0.0.1(127.0.0.1):9170
18:02:17,579  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:17,580 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:17,581  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:17,581  INFO HConnectionManager:191 - Suspend operation status was false for CassandraHost 127.0.0.1(127.0.0.1):9170
18:02:17,582  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:17,584  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:17,586 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:17,587  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:17,588  INFO HConnectionManager:191 - Suspend operation status was false for CassandraHost 127.0.0.1(127.0.0.1):9170
18:02:17,589  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:17,591 ERROR AbstractCassandraDaemon:370 - Exception encountered during startup
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
Exception encountered during startup: java.lang.InterruptedException
18:02:17,597  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 8, Failures: 4, Errors: 0, Skipped: 0, Time elapsed: 6.125 sec <<< FAILURE!
18:02:17,653  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:02:17,654  INFO MessagingService:695 - MessagingService shutting down server thread.
18:02:17,704  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
Running me.prettyprint.cassandra.connection.LeastActiveBalancingPolicyTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.109 sec
Running me.prettyprint.cassandra.connection.RoundRobinBalancingPolicyTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 6.211 sec
Running me.prettyprint.cassandra.connection.DynamicBalancingPolicyTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.896 sec
Running me.prettyprint.cassandra.connection.HostTimeoutTrackerTest
18:02:29,264  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:29,305 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:02:29,306  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:02:29,308  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:29,425  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:02:29,934  INFO HConnectionManager:191 - Suspend operation status was false for CassandraHost localhost(127.0.0.1):9170
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.178 sec
Running me.prettyprint.cassandra.connection.LatencyAwareHClientPoolTest
18:02:30,614  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:30,642  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:30,881  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:31,132  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:31,711  INFO EmbeddedServerHelper:66 - Starting executor
18:02:31,713  INFO EmbeddedServerHelper:69 - Started executor
18:02:31,713  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:31,715  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:31,716  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter4347243897966209257.jar
18:02:31,833  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:32,223  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:32,399  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:32,505  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:32,523  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:33,692  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:33,837  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:33,861  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:34,139  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:34,141  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:34,144  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:34,714  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:34,751  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:34,752  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:34,879  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:34,887  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1519652738(78/97 serialized/live bytes, 2 ops)
18:02:34,889  INFO Memtable:266 - Writing Memtable-IndexInfo@1519652738(78/97 serialized/live bytes, 2 ops)
18:02:34,889  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:02:35,119 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:35,120  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:35,122  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:35,249  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:02:35,413  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:35,414 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:35,415  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:35,419  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:35,421  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:35,422 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:35,422  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:35,428  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 3, Failures: 0, Errors: 3, Skipped: 0, Time elapsed: 5.111 sec <<< FAILURE!
18:02:35,430  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:35,463  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18219511160408.log
18:02:35,463  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18219511160408.log
18:02:35,465 ERROR AbstractCassandraDaemon:370 - Exception encountered during startup
java.nio.channels.ClosedByInterruptException
        at java.nio.channels.spi.AbstractInterruptibleChannel.end(AbstractInterruptibleChannel.java:184)
        at sun.nio.ch.FileChannelImpl.size(FileChannelImpl.java:304)
        at org.apache.cassandra.io.util.RandomAccessReader.<init>(RandomAccessReader.java:81)
        at org.apache.cassandra.io.util.RandomAccessReader.open(RandomAccessReader.java:102)
        at org.apache.cassandra.io.util.RandomAccessReader.open(RandomAccessReader.java:87)
        at org.apache.cassandra.db.commitlog.CommitLog.recover(CommitLog.java:189)
        at org.apache.cassandra.db.commitlog.CommitLog.recover(CommitLog.java:143)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:223)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
java.nio.channels.ClosedByInterruptException
        at java.nio.channels.spi.AbstractInterruptibleChannel.end(AbstractInterruptibleChannel.java:184)
        at sun.nio.ch.FileChannelImpl.size(FileChannelImpl.java:304)
        at org.apache.cassandra.io.util.RandomAccessReader.<init>(RandomAccessReader.java:81)
        at org.apache.cassandra.io.util.RandomAccessReader.open(RandomAccessReader.java:102)
        at org.apache.cassandra.io.util.RandomAccessReader.open(RandomAccessReader.java:87)
        at org.apache.cassandra.db.commitlog.CommitLog.recover(CommitLog.java:189)
        at org.apache.cassandra.db.commitlog.CommitLog.recover(CommitLog.java:143)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:223)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Exception encountered during startup: null
Running me.prettyprint.cassandra.connection.HThriftClientTest
18:02:37,755  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:37,784  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:37,991  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:38,186  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:38,662  INFO EmbeddedServerHelper:66 - Starting executor
18:02:38,663  INFO EmbeddedServerHelper:69 - Started executor
18:02:38,664  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:38,664  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:38,664  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter164474774063028626.jar
18:02:38,666  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:38,683  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:38,696  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:38,697  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:38,701  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:38,800  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:38,815  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:38,854  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:38,855  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:38,897  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:38,905  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:38,907  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:38,909  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:38,935  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18226498674724.log
18:02:38,939  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18226498674724.log
18:02:38,946  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18226498674724.log
18:02:38,946  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:02:38,948  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@81035498(78/97 serialized/live bytes, 2 ops)
18:02:38,949  INFO Memtable:266 - Writing Memtable-IndexInfo@81035498(78/97 serialized/live bytes, 2 ops)
18:02:38,950  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:02:39,721  INFO StorageService:412 - Cassandra version: 1.1.0
18:02:39,722  INFO StorageService:413 - Thrift API version: 19.30.0
18:02:39,725  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:02:40,055  INFO StorageService:444 - Loading persisted ring state
18:02:40,060  INFO StorageService:525 - Starting up server gossip
18:02:40,074  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:02:40,760  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:02:40,763  INFO Memtable:266 - Writing Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:02:40,764  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:02:41,338  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:02:41,480  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:02:41,488  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:02:41,497  WARN StorageService:633 - Generated random token tstALhskiHXZC9Fu. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:02:41,499  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1924003262(53/66 serialized/live bytes, 2 ops)
18:02:41,500  INFO Memtable:266 - Writing Memtable-LocationInfo@1924003262(53/66 serialized/live bytes, 2 ops)
18:02:41,664  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:41,704  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 7, Failures: 0, Errors: 6, Skipped: 0, Time elapsed: 4.213 sec <<< FAILURE!
18:02:41,705 ERROR AbstractCassandraDaemon:370 - Exception encountered during startup
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        ... 11 more
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
Exception encountered during startup: java.lang.InterruptedException
18:02:41,730  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:02:41,731  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.io.StreamTest
18:02:44,236  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:44,265  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:44,473  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:44,652  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:45,207  INFO EmbeddedServerHelper:66 - Starting executor
18:02:45,208  INFO EmbeddedServerHelper:69 - Started executor
18:02:45,208  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:45,209  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:45,209  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter2591591863186191853.jar
18:02:45,211  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:45,229  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:45,245  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:45,246  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:45,250  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:45,331  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:45,345  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:45,385  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:45,386  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:45,429  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:45,438  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:45,440  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:45,441  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:45,463  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18232917848237.log
18:02:45,467  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18232917848237.log
18:02:45,469  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18232917848237.log
18:02:45,469  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:02:46,485  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@643444394(39/48 serialized/live bytes, 1 ops)
18:02:46,486  INFO Memtable:266 - Writing Memtable-IndexInfo@643444394(39/48 serialized/live bytes, 1 ops)
18:02:46,492  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@10202458(39/48 serialized/live bytes, 1 ops)
18:02:46,503  INFO StorageService:412 - Cassandra version: 1.1.0
18:02:46,503  INFO StorageService:413 - Thrift API version: 19.30.0
18:02:46,508  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:02:46,560  INFO StorageService:444 - Loading persisted ring state
18:02:46,563  INFO StorageService:525 - Starting up server gossip
18:02:46,574  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@11555382(126/157 serialized/live bytes, 3 ops)
18:02:48,209  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:48,365  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:48,380 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:48,380  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:48,382  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:48,385  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:02:49,448  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:49,449 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:02:49,449  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:02:49,449  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:02:49,450  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:49,589  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:49,590 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:02:49,590  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:02:49,591  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:49,591  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:49,592 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:02:49,592  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:02:49,593  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:02:49,594  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 2, Failures: 0, Errors: 2, Skipped: 0, Time elapsed: 6.777 sec <<< FAILURE!
18:02:49,595 ERROR AbstractCassandraDaemon:370 - Exception encountered during startup
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.incrementAndGetGeneration(SystemTable.java:350)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:534)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.incrementAndGetGeneration(SystemTable.java:350)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:534)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 10 more
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 10 more
Exception encountered during startup: java.lang.InterruptedException
18:02:49,616 ERROR AbstractCassandraDaemon:134 - Exception in thread Thread[StorageServiceShutdownHook,5,main]
java.lang.NullPointerException
        at org.apache.cassandra.gms.Gossiper.stop(Gossiper.java:1113)
        at org.apache.cassandra.service.StorageService$2.runMayThrow(StorageService.java:478)
        at org.apache.cassandra.utils.WrappedRunnable.run(WrappedRunnable.java:30)
        at java.lang.Thread.run(Thread.java:662)
Running me.prettyprint.cassandra.examples.ExampleDaoV2Test
18:02:52,377  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:52,580  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:52,760  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:02:53,203  INFO EmbeddedServerHelper:66 - Starting executor
18:02:53,206  INFO EmbeddedServerHelper:69 - Started executor
18:02:53,207  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:02:53,207  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:02:53,207  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3406107125145063572.jar
18:02:53,210  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:02:53,235  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:02:53,252  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:02:53,253  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:02:53,257  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:02:53,357  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:02:53,369  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:02:53,420  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:02:53,423  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:02:53,470  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:53,478  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:02:53,480  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:02:53,482  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:02:53,510  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1861413442(39/48 serialized/live bytes, 1 ops)
18:02:53,511  INFO Memtable:266 - Writing Memtable-IndexInfo@1861413442(39/48 serialized/live bytes, 1 ops)
18:02:53,515  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@2066231378(39/48 serialized/live bytes, 1 ops)
18:02:53,518  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18241037640838.log
18:02:53,521  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18241037640838.log
18:02:53,523  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18241037640838.log
18:02:53,524  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:02:53,545  INFO StorageService:412 - Cassandra version: 1.1.0
18:02:53,545  INFO StorageService:413 - Thrift API version: 19.30.0
18:02:53,549  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:02:53,595  INFO StorageService:444 - Loading persisted ring state
18:02:53,599  INFO StorageService:525 - Starting up server gossip
18:02:53,612  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@277818534(126/157 serialized/live bytes, 3 ops)
18:02:54,411  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:02:54,415  INFO Memtable:266 - Writing Memtable-IndexInfo@2066231378(39/48 serialized/live bytes, 1 ops)
18:02:54,416  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:02:55,017  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:02:55,018  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:02:55,018  INFO Memtable:266 - Writing Memtable-LocationInfo@277818534(126/157 serialized/live bytes, 3 ops)
18:02:55,508  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:02:55,522  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:02:55,529  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:02:55,538  WARN StorageService:633 - Generated random token SzypFInRYbd2J4Ia. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:02:55,541  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1255545583(53/66 serialized/live bytes, 2 ops)
18:02:55,541  INFO Memtable:266 - Writing Memtable-LocationInfo@1255545583(53/66 serialized/live bytes, 2 ops)
18:02:56,155  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:02:56,158  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:02:56,167  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:02:56,168  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:02:56,207  INFO EmbeddedServerHelper:73 - Done sleeping
18:02:56,243  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:02:56,249  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:02:56,258  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:02:56,258  INFO CassandraDaemon:212 - Listening for thrift clients...
18:02:56,273  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:02:56,298  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:02:57,253  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 5.23 sec
18:02:57,270  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:02:57,272  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:02:57,272  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.CassandraAuthTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.061 sec
Running me.prettyprint.cassandra.service.template.SuperCfTemplateTest
18:02:59,497  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:02:59,526  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:02:59,757  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:02:59,959  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:03:00,505  INFO EmbeddedServerHelper:66 - Starting executor
18:03:00,507  INFO EmbeddedServerHelper:69 - Started executor
18:03:00,507  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:03:00,508  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:03:00,508  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter6255856131138956688.jar
18:03:00,511  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:03:00,528  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:03:00,541  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:03:00,542  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:03:00,546  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:03:00,638  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:03:00,651  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:03:00,697  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:03:00,698  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:03:00,747  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:00,757  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:03:00,760  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:00,766  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:03:00,791  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18248265824183.log
18:03:00,792  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@628029189(39/48 serialized/live bytes, 1 ops)
18:03:00,793  INFO Memtable:266 - Writing Memtable-IndexInfo@628029189(39/48 serialized/live bytes, 1 ops)
18:03:00,796  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18248265824183.log
18:03:00,801  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1152296720(39/48 serialized/live bytes, 1 ops)
18:03:00,806  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18248265824183.log
18:03:00,806  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:03:00,828  INFO StorageService:412 - Cassandra version: 1.1.0
18:03:00,829  INFO StorageService:413 - Thrift API version: 19.30.0
18:03:00,834  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:03:00,894  INFO StorageService:444 - Loading persisted ring state
18:03:00,898  INFO StorageService:525 - Starting up server gossip
18:03:00,908  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:03:02,110  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:03:02,114  INFO Memtable:266 - Writing Memtable-IndexInfo@1152296720(39/48 serialized/live bytes, 1 ops)
18:03:02,114  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:03:03,136  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:03:03,137  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:03:03,137  INFO Memtable:266 - Writing Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:03:03,507  INFO EmbeddedServerHelper:73 - Done sleeping
18:03:03,549  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:03,566 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:03:03,566  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:03:03,567  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:03,570  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:03:04,052  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:03:04,199  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:03:04,265  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:03:04,398  WARN StorageService:633 - Generated random token hRv7GexehRb1iZhA. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:03:04,401  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@86789207(53/66 serialized/live bytes, 2 ops)
18:03:04,402  INFO Memtable:266 - Writing Memtable-LocationInfo@86789207(53/66 serialized/live bytes, 2 ops)
18:03:04,812  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 13, Failures: 0, Errors: 13, Skipped: 0, Time elapsed: 5.592 sec <<< FAILURE!
18:03:04,813 ERROR AbstractCassandraDaemon:370 - Exception encountered during startup
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
java.lang.AssertionError: java.lang.InterruptedException
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:219)
        at org.apache.cassandra.db.SystemTable.updateToken(SystemTable.java:204)
        at org.apache.cassandra.service.StorageService.setToken(StorageService.java:246)
        at org.apache.cassandra.service.StorageService.joinTokenRing(StorageService.java:651)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:515)
        at org.apache.cassandra.service.StorageService.initServer(StorageService.java:407)
        at org.apache.cassandra.service.AbstractCassandraDaemon.setup(AbstractCassandraDaemon.java:231)
        at org.apache.cassandra.service.AbstractCassandraDaemon.activate(AbstractCassandraDaemon.java:353)
        at me.prettyprint.hector.testutils.EmbeddedServerHelper$CassandraRunner.run(EmbeddedServerHelper.java:189)
        at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:886)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:908)
        at java.lang.Thread.run(Thread.java:662)
Caused by: java.lang.InterruptedException
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:979)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1281)
        at java.util.concurrent.FutureTask$Sync.innerGet(FutureTask.java:218)
        at java.util.concurrent.FutureTask.get(FutureTask.java:83)
        at org.apache.cassandra.db.ColumnFamilyStore.forceBlockingFlush(ColumnFamilyStore.java:699)
        at org.apache.cassandra.db.SystemTable.forceBlockingFlush(SystemTable.java:211)
        ... 11 more
Exception encountered during startup: java.lang.InterruptedException
18:03:04,860  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:03:04,869  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.template.ColumnFamilyTemplateTest
18:03:07,699  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:03:07,797  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:03:08,072  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:03:08,283  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:03:09,006  INFO EmbeddedServerHelper:66 - Starting executor
18:03:09,007  INFO EmbeddedServerHelper:69 - Started executor
18:03:09,008  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:03:09,008  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:03:09,008  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter3355090328383458115.jar
18:03:09,011  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:03:09,032  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:03:09,045  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:03:09,046  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:03:09,050  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:03:09,237  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:03:09,252  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:03:09,291  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:03:09,292  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:03:09,337  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:09,345  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:03:09,347  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:09,349  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:03:09,370  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@545732387(39/48 serialized/live bytes, 1 ops)
18:03:09,372  INFO Memtable:266 - Writing Memtable-IndexInfo@545732387(39/48 serialized/live bytes, 1 ops)
18:03:09,372  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18256799834124.log
18:03:09,380  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@415546420(39/48 serialized/live bytes, 1 ops)
18:03:09,381  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18256799834124.log
18:03:09,386  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18256799834124.log
18:03:09,387  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:03:09,407  INFO StorageService:412 - Cassandra version: 1.1.0
18:03:09,407  INFO StorageService:413 - Thrift API version: 19.30.0
18:03:09,421  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:03:09,479  INFO StorageService:444 - Loading persisted ring state
18:03:09,483  INFO StorageService:525 - Starting up server gossip
18:03:09,494  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
18:03:10,387  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:03:10,390  INFO Memtable:266 - Writing Memtable-IndexInfo@415546420(39/48 serialized/live bytes, 1 ops)
18:03:10,391  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:03:10,945  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:03:10,946  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:03:10,946  INFO Memtable:266 - Writing Memtable-LocationInfo@50503845(126/157 serialized/live bytes, 3 ops)
18:03:11,825  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:03:11,838  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:03:11,846  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:03:11,859  WARN StorageService:633 - Generated random token WCl50r8iEo611Uvw. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:03:11,861  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@1589377628(53/66 serialized/live bytes, 2 ops)
18:03:11,862  INFO Memtable:266 - Writing Memtable-LocationInfo@1589377628(53/66 serialized/live bytes, 2 ops)
18:03:12,008  INFO EmbeddedServerHelper:73 - Done sleeping
18:03:12,044  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:12,059 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:03:12,060  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:03:12,062  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:12,065  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_MyCluster:ServiceType=hector,MonitorType=hector
18:03:14,017  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:03:14,226  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:03:14,372  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:03:14,387  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:03:14,641  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:03:14,644  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:03:14,670  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:03:14,671  INFO CassandraDaemon:212 - Listening for thrift clients...
18:03:14,676  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 9, Failures: 0, Errors: 9, Skipped: 0, Time elapsed: 7.399 sec <<< FAILURE!
18:03:14,759  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:03:14,761  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:03:14,763  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.BatchMutationTest
Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.163 sec
Running me.prettyprint.cassandra.service.CassandraHostConfiguratorTest
18:03:16,947 ERROR CassandraHost:68 - Unable to resolve host h1
18:03:17,563 ERROR CassandraHost:68 - Unable to resolve host h2
18:03:18,220 ERROR CassandraHost:68 - Unable to resolve host h3
Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.218 sec
Running me.prettyprint.cassandra.service.KeyspaceTest
18:03:19,031  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:03:19,055  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:03:19,329  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:03:19,533  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:03:19,967  INFO EmbeddedServerHelper:66 - Starting executor
18:03:19,968  INFO EmbeddedServerHelper:69 - Started executor
18:03:19,968  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:03:19,969  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:03:19,969  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter5786190392443908020.jar
18:03:19,974  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:03:19,989  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:03:20,002  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:03:20,004  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:03:20,008  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:03:20,103  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:03:20,116  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:03:20,156  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:03:20,157  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:03:20,200  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:20,207  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:03:20,210  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:20,213  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:03:20,237  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18267801643939.log
18:03:20,240  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18267801643939.log
18:03:20,243  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18267801643939.log
18:03:20,243  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:03:20,504  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1939520811(78/97 serialized/live bytes, 2 ops)
18:03:20,505  INFO Memtable:266 - Writing Memtable-IndexInfo@1939520811(78/97 serialized/live bytes, 2 ops)
18:03:20,506  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:03:20,519  INFO StorageService:412 - Cassandra version: 1.1.0
18:03:20,519  INFO StorageService:413 - Thrift API version: 19.30.0
18:03:20,524  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:03:20,609  INFO StorageService:444 - Loading persisted ring state
18:03:20,615  INFO StorageService:525 - Starting up server gossip
18:03:20,629  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
18:03:21,097  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:03:21,099  INFO Memtable:266 - Writing Memtable-LocationInfo@562082350(126/157 serialized/live bytes, 3 ops)
18:03:21,100  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:03:21,621  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:03:21,634  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:03:21,643  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:03:21,652  WARN StorageService:633 - Generated random token mQo2gmBTwaZJvRLf. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:03:21,656  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2061551555(53/66 serialized/live bytes, 2 ops)
18:03:21,656  INFO Memtable:266 - Writing Memtable-LocationInfo@2061551555(53/66 serialized/live bytes, 2 ops)
18:03:22,346  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:03:22,348  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:03:22,358  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:03:22,359  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:03:22,397  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:03:22,401  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:03:22,407  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:03:22,407  INFO CassandraDaemon:212 - Listening for thrift clients...
18:03:22,969  INFO EmbeddedServerHelper:73 - Done sleeping
18:03:23,010  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:23,041  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_TestCluster:ServiceType=hector,MonitorType=hector
18:03:23,502  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:23,681  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:23,859  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:23,907  INFO NodeId:200 - No saved local node id, using newly generated: 6c0b3d30-dbf2-11e1-0000-fe8ebeead9de
18:03:24,033  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,059  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,069  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,458  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,751  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,883  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,890  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:24,900  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:25,004  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,073  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:25,303  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,485  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:25,542  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:25,625  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,758  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:25,789  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,801  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,967  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,984  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:25,992  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,070  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,116  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,218  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,246  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,285  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,392  WARN Memtable:164 - MemoryMeter uninitialized (jamm not specified as java agent); assuming liveRatio of 10.0.  Usually this means cassandra-env.sh disabled jamm because you are using a buggy JRE; upgrade to the Sun JRE instead
18:03:26,507  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,543  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:26,565  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 24, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 7.785 sec
18:03:26,687  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:03:26,689  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:03:26,691  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.CassandraClusterTest
18:03:29,450  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:03:29,475  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:03:29,675  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:03:29,864  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:03:30,596  INFO EmbeddedServerHelper:66 - Starting executor
18:03:30,597  INFO EmbeddedServerHelper:69 - Started executor
18:03:30,597  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:03:30,597  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:03:30,597  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter7613313003852012668.jar
18:03:30,599  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:03:30,615  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:03:30,628  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:03:30,629  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:03:30,633  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:03:30,723  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:03:30,735  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:03:30,778  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:03:30,779  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:03:30,822  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:30,829  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:03:30,831  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:30,833  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:03:30,855  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18278286149053.log
18:03:30,857  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:03:30,858  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18278286149053.log
18:03:30,858  INFO Memtable:266 - Writing Memtable-IndexInfo@1508028338(39/48 serialized/live bytes, 1 ops)
18:03:30,861  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18278286149053.log
18:03:30,861  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@643444394(39/48 serialized/live bytes, 1 ops)
18:03:30,862  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:03:30,879  INFO StorageService:412 - Cassandra version: 1.1.0
18:03:30,880  INFO StorageService:413 - Thrift API version: 19.30.0
18:03:30,885  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:03:30,930  INFO StorageService:444 - Loading persisted ring state
18:03:30,933  INFO StorageService:525 - Starting up server gossip
18:03:30,943  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:03:32,087  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:03:32,089  INFO Memtable:266 - Writing Memtable-IndexInfo@643444394(39/48 serialized/live bytes, 1 ops)
18:03:32,091  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:03:32,537  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:03:32,538  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:03:32,538  INFO Memtable:266 - Writing Memtable-LocationInfo@137493297(126/157 serialized/live bytes, 3 ops)
18:03:32,973  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:03:32,986  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:03:32,994  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:03:33,003  WARN StorageService:633 - Generated random token SZTZgpayDJSDsHhd. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:03:33,005  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@120708763(53/66 serialized/live bytes, 2 ops)
18:03:33,006  INFO Memtable:266 - Writing Memtable-LocationInfo@120708763(53/66 serialized/live bytes, 2 ops)
18:03:33,597  INFO EmbeddedServerHelper:73 - Done sleeping
18:03:33,631  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,645 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,645  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,647  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,650  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:03:33,661  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,662 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,662  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,663  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,664  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:03:33,665  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,666 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,666  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,667  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,668  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:03:33,669  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,670 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,670  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,673  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,676  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,678 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,678  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,679  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,680  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:03:33,682  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:03:33,682  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,684 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,688  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,692  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,698  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,700 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,701  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,704  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,884  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,885 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,885  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,886  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,888  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,889 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,889  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,890  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,922  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,923 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,923  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,924  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,926  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,928 ERROR HConnectionManager:70 - Could not start connection pool for host localhost(127.0.0.1):9170
18:03:33,928  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: localhost(127.0.0.1):9170
18:03:33,929  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:03:33,929  WARN CassandraHostRetryService:213 - Downed localhost(127.0.0.1):9170 host still appears to be down: Unable to open transport to localhost(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:03:33,970  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:03:33,972  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:33,974  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:03:33,975  INFO CassandraDaemon:212 - Listening for thrift clients...
18:03:35,847  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1373806697(190/237 serialized/live bytes, 4 ops)
18:03:35,848  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1373806697(190/237 serialized/live bytes, 4 ops)
18:03:36,735  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-1-Data.db (252 bytes)
18:03:36,737  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@557591935(1238/1547 serialized/live bytes, 20 ops)
18:03:36,737  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@557591935(1238/1547 serialized/live bytes, 20 ops)
18:03:37,632  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-1-Data.db (1308 bytes)
18:03:39,106  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1700203556(1238/1547 serialized/live bytes, 20 ops)
18:03:39,107  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1700203556(1238/1547 serialized/live bytes, 20 ops)
18:03:39,631  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-2-Data.db (1308 bytes)
18:03:39,632  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@320803739(815/1018 serialized/live bytes, 10 ops)
18:03:39,633  INFO Memtable:266 - Writing Memtable-schema_columns@320803739(815/1018 serialized/live bytes, 10 ops)
18:03:39,968  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-1-Data.db (877 bytes)
18:03:40,201  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746864617465, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthdate_idx'}
18:03:40,202  INFO SecondaryIndex:159 - Submitting index build of DynamicCF.birthdate_idx for data in 
18:03:40,205  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@891929669(38/47 serialized/live bytes, 1 ops)
18:03:40,206  INFO Memtable:266 - Writing Memtable-IndexInfo@891929669(38/47 serialized/live bytes, 1 ops)
18:03:40,206  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:40,229  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@445099367(194/242 serialized/live bytes, 4 ops)
18:03:40,660  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-3-Data.db (100 bytes)
18:03:40,660  INFO SecondaryIndex:200 - Index build of DynamicCF.birthdate_idx complete
18:03:40,660  INFO Memtable:266 - Writing Memtable-schema_keyspaces@445099367(194/242 serialized/live bytes, 4 ops)
18:03:41,062  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-2-Data.db (260 bytes)
18:03:41,097  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@873551122(0/0 serialized/live bytes, 1 ops)
18:03:41,098  INFO Memtable:266 - Writing Memtable-schema_keyspaces@873551122(0/0 serialized/live bytes, 1 ops)
18:03:41,554  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-3-Data.db (66 bytes)
18:03:41,555  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@897350024(0/0 serialized/live bytes, 1 ops)
18:03:41,556  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@897350024(0/0 serialized/live bytes, 1 ops)
18:03:41,928  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-3-Data.db (66 bytes)
18:03:41,930  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@745557237(0/0 serialized/live bytes, 1 ops)
18:03:41,930  INFO Memtable:266 - Writing Memtable-schema_columns@745557237(0/0 serialized/live bytes, 1 ops)
18:03:42,306  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-2-Data.db (66 bytes)
18:03:42,319  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:42,371  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1980984405(194/242 serialized/live bytes, 4 ops)
18:03:42,372  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1980984405(194/242 serialized/live bytes, 4 ops)
18:03:42,786  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-4-Data.db (260 bytes)
18:03:42,812  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@719148673(0/0 serialized/live bytes, 1 ops)
18:03:42,813  INFO Memtable:266 - Writing Memtable-schema_keyspaces@719148673(0/0 serialized/live bytes, 1 ops)
18:03:42,833  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-1-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-2-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-3-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-4-Data.db')]
18:03:43,277  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-5-Data.db (66 bytes)
18:03:43,279  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@957297868(0/0 serialized/live bytes, 1 ops)
18:03:43,279  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@957297868(0/0 serialized/live bytes, 1 ops)
18:03:43,631  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,661  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,664  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,669  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,672  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,681  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,691  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,884  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,888  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,922  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:43,926  INFO CassandraHostRetryService:146 - Not checking that localhost(127.0.0.1):9170 is a member of the ring since there are no live hosts
18:03:44,500  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-4-Data.db (66 bytes)
18:03:44,501  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@796902479(0/0 serialized/live bytes, 1 ops)
18:03:44,501  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-3-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-1-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-2-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-4-Data.db')]
18:03:44,502  INFO Memtable:266 - Writing Memtable-schema_columns@796902479(0/0 serialized/live bytes, 1 ops)
18:03:44,710  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,711  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,711  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,711  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,711  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,711  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,710  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,712  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,712  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,712  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:44,712  INFO CassandraHostRetryService:159 - Downed Host retry status true with host: localhost(127.0.0.1):9170
18:03:45,527  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-6-Data.db,].  838 to 512 (~61% of original) bytes for 2 keys at 0,000255MB/s.  Time: 1.918ms.
18:03:45,717  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:45,721  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:45,723  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:45,725  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:45,855  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-3-Data.db (66 bytes)
18:03:45,864  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-5-Data.db,].  2.748 to 1.374 (~50% of original) bytes for 2 keys at 0,000965MB/s.  Time: 1.358ms.
18:03:45,869  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:45,878  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1726531547(190/237 serialized/live bytes, 4 ops)
18:03:45,879  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1726531547(190/237 serialized/live bytes, 4 ops)
18:03:46,358  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-7-Data.db (252 bytes)
18:03:46,404  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@866752711(190/237 serialized/live bytes, 4 ops)
18:03:46,404  INFO Memtable:266 - Writing Memtable-schema_keyspaces@866752711(190/237 serialized/live bytes, 4 ops)
18:03:46,728  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,728  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,729  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,729  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,730  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,731  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,731  INFO HConnectionManager:114 - Added host localhost(127.0.0.1):9170 to pool
18:03:46,973  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-8-Data.db (252 bytes)
18:03:46,975  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-5-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-8-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-7-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-6-Data.db')]
18:03:46,996  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1794306256(0/0 serialized/live bytes, 1 ops)
18:03:46,996  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1794306256(0/0 serialized/live bytes, 1 ops)
18:03:47,476  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-10-Data.db (62 bytes)
18:03:47,478  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@976017192(0/0 serialized/live bytes, 1 ops)
18:03:47,478  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@976017192(0/0 serialized/live bytes, 1 ops)
18:03:47,481  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-9-Data.db,].  1.082 to 570 (~52% of original) bytes for 3 keys at 0,001076MB/s.  Time: 505ms.
18:03:47,880  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-6-Data.db (62 bytes)
18:03:47,881  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columns@1343001934(0/0 serialized/live bytes, 1 ops)
18:03:47,882  INFO Memtable:266 - Writing Memtable-schema_columns@1343001934(0/0 serialized/live bytes, 1 ops)
18:03:48,283  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-4-Data.db (62 bytes)
18:03:48,284  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-3-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-2-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-4-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-1-Data.db')]
18:03:48,298  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:48,356  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@905375489(1158/1447 serialized/live bytes, 20 ops)
18:03:48,358  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@905375489(1158/1447 serialized/live bytes, 20 ops)
18:03:48,746  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columns/system-schema_columns-hc-5-Data.db,].  1.071 to 1.005 (~93% of original) bytes for 3 keys at 0,002079MB/s.  Time: 461ms.
18:03:48,811  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-7-Data.db (1225 bytes)
18:03:48,855  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1574355766(957/1196 serialized/live bytes, 21 ops)
18:03:48,856  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1574355766(957/1196 serialized/live bytes, 21 ops)
18:03:49,448  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-8-Data.db (1024 bytes)
18:03:49,449  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-7-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-6-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-5-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-8-Data.db')]
18:03:49,465  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:03:49,475  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_keyspaces@1410209382(187/233 serialized/live bytes, 4 ops)
18:03:49,476  INFO Memtable:266 - Writing Memtable-schema_keyspaces@1410209382(187/233 serialized/live bytes, 4 ops)
18:03:51,431  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_keyspaces/system-schema_keyspaces-hc-11-Data.db (246 bytes)
18:03:51,433  INFO CompactionTask:225 - Compacted to [/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-9-Data.db,].  3.685 to 2.509 (~68% of original) bytes for 4 keys at 0,001207MB/s.  Time: 1.982ms.
18:03:51,457  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@1793061098(1178/1472 serialized/live bytes, 20 ops)
18:03:51,457  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@1793061098(1178/1472 serialized/live bytes, 20 ops)
18:03:52,133  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-10-Data.db (1245 bytes)
18:03:52,173  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@439235775(1177/1471 serialized/live bytes, 20 ops)
18:03:52,173  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@439235775(1177/1471 serialized/live bytes, 20 ops)
18:03:52,704  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-11-Data.db (1244 bytes)
18:03:52,740  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-schema_columnfamilies@320508688(978/1222 serialized/live bytes, 21 ops)
18:03:52,740  INFO Memtable:266 - Writing Memtable-schema_columnfamilies@320508688(978/1222 serialized/live bytes, 21 ops)
18:03:53,328  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-12-Data.db (1045 bytes)
18:03:53,330  INFO CompactionTask:114 - Compacting [SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-11-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-10-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-9-Data.db'), SSTableReader(path='/var/lib/cassandra/data/system/schema_columnfamilies/system-schema_columnfamilies-hc-12-Data.db')]
18:03:53,343  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 17, Failures: 0, Errors: 11, Skipped: 0, Time elapsed: 24.155 sec <<< FAILURE!
18:03:53,376  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:03:53,379  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:03:53,380  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.KeyIteratorTest
18:03:56,295  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:03:56,321  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:03:56,522  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:03:56,700  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:03:57,574  INFO EmbeddedServerHelper:66 - Starting executor
18:03:57,576  INFO EmbeddedServerHelper:69 - Started executor
18:03:57,576  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:03:57,576  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:03:57,576  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter8980463302497951572.jar
18:03:57,578  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:03:57,593  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:03:57,606  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:03:57,607  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:03:57,611  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:03:57,702  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:03:57,715  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:03:57,754  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:03:57,754  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:03:57,797  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:57,805  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:03:57,807  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:03:57,809  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:03:57,831  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18305025177761.log
18:03:57,838  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18305025177761.log
18:03:57,841  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18305025177761.log
18:03:57,841  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:03:58,271  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1821457857(78/97 serialized/live bytes, 2 ops)
18:03:58,272  INFO Memtable:266 - Writing Memtable-IndexInfo@1821457857(78/97 serialized/live bytes, 2 ops)
18:03:58,273  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:03:58,288  INFO StorageService:412 - Cassandra version: 1.1.0
18:03:58,288  INFO StorageService:413 - Thrift API version: 19.30.0
18:03:58,292  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:03:58,338  INFO StorageService:444 - Loading persisted ring state
18:03:58,341  INFO StorageService:525 - Starting up server gossip
18:03:58,351  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@194337136(126/157 serialized/live bytes, 3 ops)
18:03:58,788  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (137 bytes)
18:03:58,790  INFO Memtable:266 - Writing Memtable-LocationInfo@194337136(126/157 serialized/live bytes, 3 ops)
18:03:58,791  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:03:59,800  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:03:59,813  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:03:59,821  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:03:59,830  WARN StorageService:633 - Generated random token Pqac41qD6pIMg4tq. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:03:59,832  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@197629132(53/66 serialized/live bytes, 2 ops)
18:03:59,833  INFO Memtable:266 - Writing Memtable-LocationInfo@197629132(53/66 serialized/live bytes, 2 ops)
18:04:00,470  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:04:00,474  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:04:00,484  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:04:00,485  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:04:00,518  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:04:00,522  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:04:00,526  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:04:00,526  INFO CassandraDaemon:212 - Listening for thrift clients...
18:04:00,576  INFO EmbeddedServerHelper:73 - Done sleeping
18:04:00,614  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:04:00,638  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:04:02,456  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 6.399 sec
18:04:02,479  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:04:02,481  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:04:02,484  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.service.ColumnSliceIteratorTest
18:04:04,054  INFO BaseEmbededServerSetupTest:39 - in setup of BaseEmbedded.Test
18:04:04,082  INFO DatabaseDescriptor:127 - Loading settings from file:tmp/cassandra.yaml
18:04:04,301  INFO DatabaseDescriptor:180 - DiskAccessMode 'auto' determined to be mmap, indexAccessMode is mmap
18:04:04,509  INFO DatabaseDescriptor:245 - Global memtable threshold is enabled at 163MB
18:04:05,000  INFO EmbeddedServerHelper:66 - Starting executor
18:04:05,002  INFO EmbeddedServerHelper:69 - Started executor
18:04:05,002  INFO AbstractCassandraDaemon:121 - JVM vendor/version: Java HotSpot(TM) 64-Bit Server VM/1.6.0_31
18:04:05,003  INFO AbstractCassandraDaemon:122 - Heap size: 514523136/514523136
18:04:05,003  INFO AbstractCassandraDaemon:123 - Classpath: /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire/surefirebooter2194912455261444484.jar
18:04:05,005  INFO CLibrary:66 - JNA not found. Native methods will be disabled.
18:04:05,025  INFO CacheService:96 - Initializing key cache with capacity of 24 MBs.
18:04:05,044  INFO CacheService:107 - Scheduling key cache save to each 14400 seconds (going to save all keys).
18:04:05,045  INFO CacheService:121 - Initializing row cache with capacity of 0 MBs and provider org.apache.cassandra.cache.SerializingCacheProvider
18:04:05,051  INFO CacheService:133 - Scheduling row cache save to each 0 seconds (going to save all keys).
18:04:05,162  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-schema_columns-KeyCache
18:04:05,175  INFO AutoSavingCache:106 - reading saved cache /var/lib/cassandra/saved_caches/system-LocationInfo-KeyCache
18:04:05,231  INFO DatabaseDescriptor:508 - Couldn't detect any schema definitions in local storage.
18:04:05,232  INFO DatabaseDescriptor:511 - Found table data in data directories. Consider using the CLI to define your schema.
18:04:05,287  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:04:05,295  INFO SecondaryIndex:159 - Submitting index build of Indexed2.birthyear_index for data in 
18:04:05,298  INFO SecondaryIndexManager:208 - Creating new index : ColumnDefinition{name=626972746879656172, validator=org.apache.cassandra.db.marshal.LongType, index_type=KEYS, index_name='birthyear_index'}
18:04:05,300  INFO SecondaryIndex:159 - Submitting index build of Indexed1.birthyear_index for data in 
18:04:05,330  INFO CommitLog:142 - Replaying /var/lib/cassandra/commitlog/CommitLog-18312832760891.log
18:04:05,333  INFO CommitLog:185 - Replaying /var/lib/cassandra/commitlog/CommitLog-18312832760891.log
18:04:05,330  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@223489506(39/48 serialized/live bytes, 1 ops)
18:04:05,341  INFO Memtable:266 - Writing Memtable-IndexInfo@223489506(39/48 serialized/live bytes, 1 ops)
18:04:05,356  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-IndexInfo@1226412018(39/48 serialized/live bytes, 1 ops)
18:04:05,359  INFO CommitLog:339 - Finished reading /var/lib/cassandra/commitlog/CommitLog-18312832760891.log
18:04:05,360  INFO CommitLog:144 - Log replay complete, 0 replayed mutations
18:04:05,392  INFO StorageService:412 - Cassandra version: 1.1.0
18:04:05,392  INFO StorageService:413 - Thrift API version: 19.30.0
18:04:05,395  INFO StorageService:414 - CQL supported versions: 2.0.0,3.0.0-beta1 (default: 2.0.0)
18:04:05,444  INFO StorageService:444 - Loading persisted ring state
18:04:05,448  INFO StorageService:525 - Starting up server gossip
18:04:05,460  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
18:04:06,400  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-1-Data.db (98 bytes)
18:04:06,403  INFO Memtable:266 - Writing Memtable-IndexInfo@1226412018(39/48 serialized/live bytes, 1 ops)
18:04:06,404  INFO SecondaryIndex:200 - Index build of Indexed2.birthyear_index complete
18:04:07,259  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/IndexInfo/system-IndexInfo-hc-2-Data.db (98 bytes)
18:04:07,261  INFO SecondaryIndex:200 - Index build of Indexed1.birthyear_index complete
18:04:07,261  INFO Memtable:266 - Writing Memtable-LocationInfo@2049101312(126/157 serialized/live bytes, 3 ops)
18:04:08,003  INFO EmbeddedServerHelper:73 - Done sleeping
18:04:08,062  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-1-Data.db (234 bytes)
18:04:08,148  INFO MessagingService:284 - Starting Messaging Service on port 7070
18:04:08,164  INFO StorageService:552 - This node will not auto bootstrap because it is configured to be a seed node.
18:04:08,182  WARN StorageService:633 - Generated random token rcYkDOBXdQPZ7Itw. Random tokens will result in an unbalanced ring; see http://wiki.apache.org/cassandra/Operations
18:04:08,185  INFO ColumnFamilyStore:634 - Enqueuing flush of Memtable-LocationInfo@63323788(53/66 serialized/live bytes, 2 ops)
18:04:08,186  INFO Memtable:266 - Writing Memtable-LocationInfo@63323788(53/66 serialized/live bytes, 2 ops)
18:04:08,188  INFO CassandraHostRetryService:48 - Downed Host Retry service started with queue size -1 and retry delay 10s
18:04:08,206 ERROR HConnectionManager:70 - Could not start connection pool for host 127.0.0.1(127.0.0.1):9170
18:04:08,207  INFO CassandraHostRetryService:68 - Host detected as down was added to retry queue: 127.0.0.1(127.0.0.1):9170
18:04:08,209  WARN CassandraHostRetryService:213 - Downed 127.0.0.1(127.0.0.1):9170 host still appears to be down: Unable to open transport to 127.0.0.1(127.0.0.1):9170 , java.net.ConnectException: Connection refused
18:04:08,211  INFO JmxMonitor:52 - Registering JMX me.prettyprint.cassandra.service_Test Cluster:ServiceType=hector,MonitorType=hector
18:04:08,943  INFO Memtable:307 - Completed flushing /var/lib/cassandra/data/system/LocationInfo/system-LocationInfo-hc-2-Data.db (163 bytes)
18:04:08,964  INFO StorageService:1085 - Node /127.0.0.1 state jump to normal
18:04:09,086  INFO StorageService:655 - Bootstrap/Replace/Move completed! Now serving reads.
18:04:09,088  INFO Mx4jTool:72 - Will not load MX4J, mx4j-tools.jar is not in the classpath
18:04:09,392  INFO CassandraDaemon:124 - Binding thrift service to localhost/127.0.0.1:9170
18:04:09,395  INFO CassandraDaemon:133 - Using TFastFramedTransport with a max frame size of 15728640 bytes.
18:04:09,401  INFO CassandraDaemon:160 - Using synchronous/threadpool thrift server on localhost/127.0.0.1 : 9170
18:04:09,401  INFO CassandraDaemon:212 - Listening for thrift clients...
18:04:09,582  INFO EmbeddedServerHelper:88 - Teardown complete
Tests run: 4, Failures: 0, Errors: 4, Skipped: 0, Time elapsed: 5.85 sec <<< FAILURE!
18:04:09,648  INFO CassandraDaemon:218 - Stop listening to thrift clients
18:04:09,653  INFO MessagingService:539 - Waiting for messaging service to quiesce
18:04:09,654  INFO MessagingService:695 - MessagingService shutting down server thread.
Running me.prettyprint.cassandra.utils.TimeUUIDUtilsTest
18:04:12,161  INFO TimeUUIDUtilsTest:85 - Original Time: 1343837052160
18:04:12,166  INFO TimeUUIDUtilsTest:86 - ----
18:04:12,166  INFO TimeUUIDUtilsTest:90 - Java UUID: 88ce1000-dbf2-11e1-a61f-5cac4c624515
18:04:12,166  INFO TimeUUIDUtilsTest:91 - Java UUID timestamp: 135631298521600000
18:04:12,355  INFO TimeUUIDUtilsTest:92 - Date: Sat Oct 02 22:26:40 CEST 4299954
18:04:12,356  INFO TimeUUIDUtilsTest:94 - ----
18:04:12,356  INFO TimeUUIDUtilsTest:97 - eaio UUID: 00000138-e2ee-1d00-0000-000000000000
18:04:12,356  INFO TimeUUIDUtilsTest:98 - eaio UUID timestamp: 1343837052160
18:04:12,356  INFO TimeUUIDUtilsTest:99 - Date: Wed Aug 01 18:04:12 CEST 2012
18:04:12,357  INFO TimeUUIDUtilsTest:101 - ----
18:04:12,357  INFO TimeUUIDUtilsTest:104 - Java UUID to bytes to time: 1343837052160
18:04:12,357  INFO TimeUUIDUtilsTest:105 - Java UUID to bytes time to Date: Wed Aug 01 18:04:12 CEST 2012
18:04:12,357  INFO TimeUUIDUtilsTest:107 - ----
18:04:12,358  INFO TimeUUIDUtilsTest:110 - Java UUID to time: 1343837052160
18:04:12,358  INFO TimeUUIDUtilsTest:111 - Java UUID to time to Date: Wed Aug 01 18:04:12 CEST 2012
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.754 sec
Running me.prettyprint.cassandra.utils.ByteBufferOutputStreamTest
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.574 sec

Results :

Failed tests: 

Tests in error: 

Tests run: 270, Failures: 4, Errors: 46, Skipped: 2

[INFO] ------------------------------------------------------------------------
[ERROR] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] There are test failures.

Please refer to /home/cesare/workspace/tesi/CassandraBM/hector/core/target/surefire-reports for the individual test results.
[INFO] ------------------------------------------------------------------------
[INFO] For more information, run Maven with the -e switch
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 3 minutes 50 seconds
[INFO] Finished at: Wed Aug 01 18:04:13 CEST 2012
[INFO] Final Memory: 33M/328M
[INFO] ------------------------------------------------------------------------

