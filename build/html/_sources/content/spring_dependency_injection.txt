.. highlight:: xml

.. index:: spring, "dependency injection"

************************************
Dependency injection through Spring
************************************

Injecting dependencies for API V2
==================================

::

	 <?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	    xsi:schemaLocation="http://www.springframework.org/schema/beans
	           http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
	           http://www.springframework.org/schema/context
	           http://www.springframework.org/schema/context/spring-context-3.0.xsd">

	    <bean id="cassandraHostConfigurator" class="me.prettyprint.cassandra.service.CassandraHostConfigurator">
	        <constructor-arg value="localhost:9170"/>
	    </bean>

	    <bean id="cluster" class="me.prettyprint.cassandra.service.ThriftCluster">
	        <constructor-arg value="TestCluster"/>
	        <constructor-arg ref="cassandraHostConfigurator"/>
	    </bean>

	    <bean id="consistencyLevelPolicy" class="me.prettyprint.cassandra.model.ConfigurableConsistencyLevel"> 
	        <property name="defaultReadConsistencyLevel" value="ONE"/>
	    </bean>
	    
	    <bean id="keyspaceOperator" class="me.prettyprint.hector.api.factory.HFactory" factory-method="createKeyspace">
	        <constructor-arg value="Keyspace1"/>
	        <constructor-arg ref="cluster"/>
	        <constructor-arg ref="consistencyLevelPolicy"/>
	    </bean>


	    <bean id="simpleCassandraDao" class="me.prettyprint.cassandra.dao.SimpleCassandraDao">
	        <property name="keyspace" ref="keyspaceOperator"/>
	        <property name="columnFamilyName" value="Standard1"/>
	    </bean>

	</beans>

The ``cassandraHostConfigurator`` bean is created first. The contructor parameters can be used as well as ``<property name="...name..." value="...value..."/>`` to set up more properties.
The ``keyspaceOperator`` receives in this case, a name, the ``cluster`` object and the ``consistencyLevelPolicy`` bean.
This model assumes you have a *DAO* object but that is not a restriction.

Injecting dependencies for Templates
====================================

::

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	  xsi:schemaLocation="http://www.springframework.org/schema/beans
	           http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
	           http://www.springframework.org/schema/context
	           http://www.springframework.org/schema/context/spring-context-3.0.xsd">

	  <bean id="hectorTemplate"
	    class="me.prettyprint.cassandra.service.spring.HectorTemplateImpl"
	    init-method="init">
	    <property name="cluster">
	      <bean class="me.prettyprint.cassandra.service.Cluster">
	        <constructor-arg value="TestCluster" />
	        <constructor-arg>
	          <bean class="me.prettyprint.cassandra.service.CassandraHostConfigurator">
	            <constructor-arg value="localhost:9170" />
	          </bean>
	        </constructor-arg>
	      </bean>
	    </property>
	    <property name="keyspace" value="Keyspace1" />
	    <property name="replicationStrategyClass" value="org.apache.cassandra.locator.SimpleStrategy" />
	    <property name="replicationFactor" value="1" />
	  </bean>
	</beans>