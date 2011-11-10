.. highlight:: java

.. index:: autodiscovery, discovery, service, downhost

.. _services:

***********
Services
***********

Hector has two backgroud services that let it discover new hosts as they join the ring as well as detect when a host is temporarily down (a host that is down but still belongs to the ring) and keep trying to connect to them until they either come back up or get decommisioned (leave the ring).

These two services are:

	* CassandraHostRetryService, and
	* NodeAutoDiscoverService

Both services are configured through ``CassandraHostConfigurator``.

CassandraHostRetryService
==========================

This service is in charge to check when a host marked as down has come back.
By default this service is **enabled** and has few other parameters that can be set:

	* **retryDownedHosts**: whether this service is enabled or not (true/false). Default is true.
	* **retryDownedHostsQueueSize**: it's the amount of host to monitor. The default is -1 which means unlimited.
	* **retryDownedHostsDelayInSeconds**: How frequently to run the service. The default is 10 seconds.

An example to set up this service with just space to check 10 hosts and check those every 30 seconds would be ::

	// This first line is not really needed but it makes things clear :)
	cassandraHostConfigurator.setRetryDownedHosts(true);
	cassandraHostConfigurator.setRetryDownedHostsQueueSize(10);
	cassandraHostConfigurator.retryDownedHostsDelayInSeconds(30);


NodeAutoDiscoverService
==========================

This service takes care off adding new host to the pool as soon as they join the ring. By default this service is **disabled**.

The parameters are:

	* **autoDiscoverHosts**: whether this service is enabled or not (true/false). Default is false.
	* **autoDiscoveryDelayInSeconds**: How frequently to run the service. The default is 30 seconds.
	* **autoDiscoveryDataCenters**: Specifies what datacenters(DC) should be consider as valid  **(1)**

**(1)** Sometimes a deployment may involved several DCs but only the nodes that are part of a certain DC should be consider as valid in order to be added to the pool. A typical example is having a cluster or ring composed by two DCs one on each continent, and you want that Hector connects to nodes that are in the same region.

In order to enable this service for a DC called *"WEST-COAST"* and with a frequency of 1 minute, we do ::

	// Enable the service
	cassandraHostConfigurator.setAutoDiscoverHosts(true);
	// 60 seconds == 1 minute
	cassandraHostConfigurator.setAutoDiscoveryDelayInSeconds(60);
	// It can alse receive a list of DCs
	cassandraHostConfigurator.setAutoDiscoveryDataCenter("WEST-COAST");