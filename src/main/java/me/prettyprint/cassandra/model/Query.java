package me.prettyprint.cassandra.model;

public interface Query {

  <T extends Query> T setColumnFamily(String cf);

  Result execute();

}
