.. highlight:: java

.. index:: speed4j

Speed4j
*******

Hector uses `Speed4j <https://github.com/jalkanen/speed4j>`_ to display performance metrics.

Speed4j, a very simple (but fast) Java performance analysis library. It is
designed using Perf4j as a model, but hopefully avoiding the pitfalls inherent
in Perf4j's design. Speed4j has a dependency on SLF4J (see http://slf4j.org), which it uses to log
its workings, but no other dependencies.

How to enable Speed4j?
======================

Three steps are needed.

Step 1: Set the OpTimer
-----------------------

Set the OpTimer in your CassandraHostConfigurator class::

    cassandraHostConfigurator.setOpTimer(new SpeedForJOpTimer("<cluster-name>"));
    Cluster cluster = HFactory.getOrCreateCluster("<cluster-name>", cassandraHostConfigurator);

Step 2: Setting logger
----------------------

Speed4j will log at INFO level, so if needed add a logger entry for this logger class::

    me.prettyprint.cassandra.hector.TimingLogger

Step 3: Configuring speed4j properties file
-------------------------------------------

.. highlight:: properties

``speed4j.properties`` file should be in the root of your classpath.  Pay attention to "&lt;cluster-name&gt;" used in step #1 and make sure it is used as illustrated below.  For instance, if you used "CustomerDB" as your cluster name in step #1, then you would use: speed4j.hector-CustomerDB = com.ecyrd.speed4j.log.PeriodicalLog in the properties file.

Make sure this file is in the path before the hector JARs, as there is one provided by hector that will be used if found first::

    # Which Log instance shall we use?
    speed4j.hector-<cluster-name> = com.ecyrd.speed4j.log.PeriodicalLog

    # How often shall this Log log (in seconds)?
    speed4j.hector-<cluster-name>.period = 10

    # Which of these attributes shall be exposed in JMX?
      speed4j.hector-<cluster-name>.jmx=READ.success_,WRITE.success_,READ.fail_,WRITE.fail_,META_READ.success_,META_READ.fail_

    # Which SLF4J log name shall we output to?  Speed4J uses the INFO log level.
    # Note that this is slightly different from the old Log4J name just to ensure that we
    # don't mess with any existing log4j configurations.
    speed4j.hector-<cluster-name>.slf4jLogname=me.prettyprint.hector.TimingLogger