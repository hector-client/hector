.. highlight:: java

.. index:: Consistency Level, Consistency

Defining Consistency Levels
***************************

Hector like Cassandra lets you use different consistency levels. These are:

	* **ANY**: Wait until some replica has responded.
	* **ONE**: Wait until one replica has responded.
	* **TWO**: Wait until two replicas have responded.
	* **THREE**: Wait until three replicas have responded.
	* **LOCAL_QUORUM**: Wait for quorum on the datacenter the connection was stablished.
	* **EACH_QUORUM**: Wait for quorum on each datacenter.
	* **QUORUM**: Wait for a quorum of replicas (no matter which datacenter).
	* **ALL**: Blocks for all the replicas before returning to the client.

In Hector, the *Consistency Level* can be set per *Column Family* and per operation type (Read, Write)

**Note**: The default value is **Quorum** for both read and write operations. 

It is set by passing a `ConfigurableConsistencyLevel <https://github.com/rantav/hector/blob/master/core/src/main/java/me/prettyprint/cassandra/model/ConfigurableConsistencyLevel.java>`_ object right when the *Keyspace* is created. 

The following code assumes you have already created a *Cluster* like indicated at :ref:`Initializing a cluster <initializing_a_cluster>`.

Let's see a sample code::


	// Create a customized Consistency Level
	ConfigurableConsistencyLevel configurableConsistencyLevel = new ConfigurableConsistencyLevel();
	Map<String, HConsistencyLevel> clmap = new HashMap<String, HConsistencyLevel>();

	// Define CL.ONE for ColumnFamily "MyColumnFamily"
	clmap.put("MyColumnFamily", HConsistencyLevel.ONE);

	// In this we use CL.ONE for read and writes. But you can use different CLs if needed.
	configurableConsistencyLevel.setReadCfConsistencyLevels(clmap);
	configurableConsistencyLevel.setWriteCfConsistencyLevels(clmap);

	// Then let the keyspace know
	HFactory.createKeyspace("MyKeyspace", myCluster, configurableConsistencyLevel);

And that's it.
