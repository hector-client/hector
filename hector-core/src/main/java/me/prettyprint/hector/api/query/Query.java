package me.prettyprint.hector.api.query;

import me.prettyprint.cassandra.model.thrift.ThriftColumnQuery;


/**
 * The Query interface defines the common parts of all hector queries, such as {@link ThriftColumnQuery}.
 * <p>
 * The common usage pattern is to create a query, set the required query attributes and invoke
 * {@link Query#execute()} such as in the following example:
 * <pre>
    ColumnQuery<String, String> q = createColumnQuery(keyspace, serializer, serializer);
    Result<HColumn<String, String>> r = q.setKey(key).
        setName(COLUMN_NAME).
        setColumnFamily(CF_NAME).
        execute();
    HColumn<String, String> c = r.get();
    return c.getValue();
 * </pre>
 *
 * Note that all query mutators, such as setName or setColumnFamily always return the Query object
 * so it's easy to write strings such as <code>q.setKey(x).setName(y).setColumnFamily(z).execute();</code>
 *
 * @author Ran Tavory
 *
 * @param <T> Result type. For example Column or SuperColumn
 */
public interface Query<T> {

  QueryResult<T> execute();

}
