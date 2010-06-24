package me.prettyprint.cassandra.model;

import java.util.Map;

// multiget_slice
// string will become byte in 0.7.0
public interface Rows<K,N,V> extends Map<String, Row<K,N,V>> {

}
