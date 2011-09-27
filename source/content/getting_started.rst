.. highlight:: java

.. index:: maven, template

.. _getting_started:


***************
Getting started
***************


Fully Mavenized
=============================

To make use of this repository, add the following dependency (modifying version as necessary) declaration to your POM file::

    <dependency>
        <groupId>me.prettyprint</groupId>
        <artifactId>hector-core</artifactId>
        <version>0.8.0-2</version>
    </dependency>

.. _Initializing-a-cluster:

Initializing a cluster
======================

Let's first create our Cluster object which represent a Cassandra cluster. Note that the name is only for Hector to identify it and it is not linked to the real Cassandra cluster name. In order to make the code clear, let's also import the whole API package.::


    import me.prettyprint.hector.api.*;

        .....

    Cluster myCluster = HFactory.getOrCreateCluster("test-cluster","localhost:9160");


Let's set up the schema::


    ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition("MyKeyspace",                              
                                                                         "ColumnFamilyName", 
                                                                         ComparatorType.BYTESTYPE);

    KeyspaceDefinition newKeyspace = HFactory.createKeyspaceDefinition("MyKeyspace",                 
                                                                       ThriftKsDef.DEF_STRATEGY_CLASS,  
                                                                       replicationFactor, 
                                                                       Arrays.asList(cfDef));
    // Add the schema to the cluster.
    // "true" as the second param means that Hector will block until all nodes see the change.
    cluster.addKeyspace(newKeyspace, true);


Once the schema is created, the previous call will throw an exception saying that the Keyspace we are trying to create already exists. In order to solve that, you can wrap the previous code in a method called for example: "createSchema();" and add the following lines::

    KeyspaceDefinition keyspaceDef = cluster.describeKeyspace("MyKeyspace");
    			
    // If keyspace does not exist, the CFs don't exist either. => create them.
    if (keyspaceDef == null) {
        createSchema();
    }


Finally, we'll create a Keyspace object which is a long life component and represents the Cassandra keyspace under which we will perform operations::

    Keyspace ksp = HFactory.createKeyspace("MyKeyspace", myCluster);

"MyKeyspace" must be the name you defined the keyspace name with previously.

Creating a template
===================
Template like keyspace are both long life object. Ideally you would want to keep the template object in a DAO to facilitate the access to your business model::


    import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;

        ......

    ColumnFamilyTemplate<String, String> template = 
                              new ThriftColumnFamilyTemplate<String, String>(ksp,
                                                                             columnFamily, 
                                                                             StringSerializer.get(),        
                                                                             StringSerializer.get());

Accessing data
==============

Update
------

::

    // <String, String> correspond to key and Column name.
    ColumnFamilyUpdater<String, String> updater = template.createUpdater("a key");
    updater.setString("domain", "www.datastax.com");
    updater.setLong("time", System.currentTimeMillis());

    try {
        template.update(updater);
    } catch (HectorException e) {
        // do something ...
    }

Read
----

::

    try {
        ColumnFamilyResult<String, String> res = template.queryColumns("a key");
        String value = res.getString("domain");
        // value should be "www.datastax.com" as per our previous insertion.
    } catch (HectorException e) {
        // do something ...
    }


Delete
------

::

    try {
        template.deleteColumn("key", "column name");
    } catch (HectorException e) {
        // do something
    }

