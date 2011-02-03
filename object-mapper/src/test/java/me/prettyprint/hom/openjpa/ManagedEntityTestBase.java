package me.prettyprint.hom.openjpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;

import me.prettyprint.hom.CassandraTestBase;

public abstract class ManagedEntityTestBase extends CassandraTestBase {
  
  protected static EntityManagerFactory entityManagerFactory;
  
  @BeforeClass
  public static void setup() {
    entityManagerFactory = Persistence.createEntityManagerFactory("openjpa");  
  }
}
