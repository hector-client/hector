.. highlight:: java

.. index:: cql, conversion

Using CQL
*********

CQL queries are an interesting new feature for Apache Cassandra 0.8.0. The initial implementation in Hector deals simply with the single execute_cql_query thrift method and is largely intended as a means to test drive query functionality and behavior. 

As such, you are expected to know what you are getting into if you plan on using CQL queries in your application. Spend some time looking through the unit tests here in Hector and the Cassandra source tree. For a number of detailed examples, see test_cql.py in the test/system folder of the Apache Cassandra source distribution.

Note: if you immediately get an exception such as:
"InvalidRequestException(why:cannot parse 'foo' as hex bytes)"
It means one of two things:

* You have not formatted your query correctly
* You have not configured the correct validators (any combination of key, comparator, default, and column metadata) on your column family

In both cases, even though the query is most likely a string, it is up to you to format
this query according to the column family configuration. This can be a little confusing as only the comparator is required when defining a column family which itself will default to BytesType.

If you get an exception such as: ``InvalidRequestException(why:line 1:22 mismatched character '6' expecting '-')``

It means that cassandra thought your column family name looked like a hex string but it was not.  If you want to ensure that it is a literal string you have to single quote the column family name.

The following break down details these configuration options and their usage in the Hector API and the Cassandra CLI respectively.

Comparator
----------

Defines how to store, compare and validate the column names

* BasicColumnFamilyDefinition#setComparatorType
* comparator


Key Validator
-------------
Validator to use for keys

* BasicColumnFamilyDefinition#setKeyValidationClass 
* key_validation_class 

Default Validator
-----------------
Validator to use for values in columns

* BasicColumnFamilyDefinition#setDefaultValidationClass
* default_validation_class

Column Metadata
---------------
Validation and, optionally, index definition for known columns

``BasicColumnDefinition#setName`` and ``BasicColumnDefinition#setValidationClass`` (both required)

::

    column_metadata = [{
        column_name : 'other_name',
        validation_class : LongType
    }];


Some Examples
=============

Basic Hector API Usage
----------------------

The current implementation of the execute_cql_query thrift method follows a similar flow as the rest of the Hector API. The following is an example of constructing a basic query expression and retrieving the results::

    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    cqlQuery.setQuery("select * from StandardLong1");
    QueryResult<CqlRows<String,String,Long>> result = cqlQuery.execute();

Like the other types of queries, CqlQuery provides a result of CqlRows (which itself extends OrderedRowsImpl) so columns are available via familiar operations. The only real difference revolves around the execution of count queries. The CqlRows provides a getAsCount method that will return the result directly when a query containing the "count()" function was executed. Here is an example from the CqlQuery test case::

    CqlQuery<String,String,Long> cqlQuery = new CqlQuery<String,String,Long>(keyspace, se, se, le);
    cqlQuery.setQuery("SELECT COUNT(*) FROM StandardLong1 WHERE KEY = 'cqlQueryTest_key1'");
    QueryResult<CqlRows<String,String,Long>> result = cqlQuery.execute();
    assertEquals(2, result.get().getAsCount());


Configuring the Column Family for Ease of Use
---------------------------------------------
To set a column with named "birthyear" to the value "1976" (treating these as a String and a long respectively) we would have following update statement::

    update StandardLong1 set 'birthyear' = '1976' WHERE KEY = 'mykey1'

We can pass this statement as a String to CqlQuery#setQuery if the Following conditions are true on the column family StandardLong1:

* Comparator is *UTF8Type*
* Key validator is *UTF8Type*
* We have column metadata which defines the column "birthyear" accepts values of *LongType*

Via the Hector API, this column family would be constructed thusly::

    BasicColumnFamilyDefinition columnFamilyDefinition = new BasicColumnFamilyDefinition();
    columnFamilyDefinition.setKeyspaceName("Keyspace1");
    columnFamilyDefinition.setName("StandardLong1");
    columnFamilyDefinition.setKeyValidationClass(ComparatorType.UTF8TYPE.getClassName());
    columnFamilyDefinition.setComparatorType(ComparatorType.UTF8TYPE.getClassName());

    BasicColumnDefinition columnDefinition = new BasicColumnDefinition();
    columnDefinition.setName(StringSerializer.get().toByteBuffer("birthyear"));    
    columnDefinition.setValidationClass(ComparatorType.LONGTYPE.getClassName());
    
    columnFamilyDefinition.addColumnDefinition(columnDefinition);

And via the Casssandra CLI, the script would be::

    create column family StandardLong1
        with comparator = 'UTF8Type'
        and key_validation_class = 'UTF8Type'
        and column_metadata = [{
            column_name : 'birthyear',
            validation_class : LongType
        }];


Dealing With Conversion Manually
--------------------------------
To insert the same values as a single CQL String into another column family with no additional configuration options, you are responsible for the bytes (hexadecimal) conversion yourself::

    update Standard1 set '626972746879656172' = '31393736' WHERE KEY = '6d796b657931'

References
----------

http://www.datastax.com/dev/blog/what%E2%80%99s-new-in-cassandra-0-8-part-1-cql-the-cassandra-query-language
https://github.com/rantav/hector/blob/master/core/src/test/java/me/prettyprint/cassandra/model/CqlQueryTest.java 
http://www.datastax.com/docs/0.8/api/using_cql 
http://www.datastax.com/docs/0.8/api/cql_ref 