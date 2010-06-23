package me.prettyprint.cassandra.model;

import java.util.Map;

// multiget_slice
// string will become byte in 0.7.0
public interface Rows<R, K> extends Map<String, Row<R, K>> {

}
