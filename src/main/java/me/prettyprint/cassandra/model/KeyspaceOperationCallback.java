package me.prettyprint.cassandra.model;

import me.prettyprint.cassandra.service.Keyspace;

/*package*/ interface KeyspaceOperationCallback<T> {
  
  T doInKeyspace(final Keyspace ks) throws HectorException;


}
