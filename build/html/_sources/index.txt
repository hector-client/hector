.. Hector documentation master file, created by
   sphinx-quickstart on Tue Sep 27 11:13:45 2011.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Intro
-----

**Hector** is a high level Java client for Apache Cassandra currently in use on a number of production systems some of which have node counts into the hundreds. Issues generally are fixed as quickly as possbile and releases done frequently. 

Apache `Cassandra <http://cassandra.apache.org>`_ is a highly available column oriented database.

Hector is the greatest warrior in the greek mythology, Troy's builder and brother of Cassandra
        * http://en.wikipedia.org/wiki/Hector
        * http://en.wikipedia.org/wiki/Cassandra

Some features provided by this client
--------------------------------------

	* high level, simple object oriented interface to cassandra
	* failover behavior on the client side
	*  connection pooling for improved performance and scalability
	* JMX conters for monitoring and management
	* configurable and extensible load balancing (round robin (the default), least active, and a phi-accrural style response time detector)
	* complete encapsulation of the underlying Thrift API and structs
	* automatic retry of downed hosts
	* automatic discovery of additional hosts in the cluster
	* suspension of hosts for a short period of time after several timeouts
	* simple ORM layer that works
	* a type-safe approach to dealing with Apache Cassandra's data model

Sources and download
---------------------

https://github.com/rantav/hector
or clone it directly from ``$ git clone git://github.com/rantav/hector``

Mailing lists
-------------

	* **Users**: Ask questions and share your experience. Anyone can post messages and anyone may join.
		hector-users@googlegroups.com (http://groups.google.com/group/hector-users)

	* **Developers**: For cutting edge development of hector itself. Anyone can post messages, but join by invitation only.
		hector-dev@googlegroups.com (http://groups.google.com/group/hector-dev)


Build resources
---------------

	* Maven Central location (includes javadoc): http://repo2.maven.org/maven2/me/prettyprint/
	* `Continuous Integration (Hudson/Jenkins) <https://hector-dev.ci.cloudbees.com>`_
	* `Cloudbees maven repo with nightly snapshots <https://repository-hector-dev.forge.cloudbees.com/snapshot>`_


:ref:`FAQ`
----------

:ref:`Community`
----------------

