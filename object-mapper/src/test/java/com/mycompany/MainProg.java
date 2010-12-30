package com.mycompany;

import java.util.UUID;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hom.Colors;
import me.prettyprint.hom.EntityManagerImpl;


public class MainProg {

  public static void main(String[] args) {
    Cluster cluster = HFactory.getOrCreateCluster("TestPool", "localhost:9160");
    Keyspace keyspace = HFactory.createKeyspace("TestKeyspace", cluster);

    EntityManagerImpl em = new EntityManagerImpl(keyspace, "com.mycompany");

    MyPojo pojo1 = new MyPojo();
    pojo1.setId(UUID.randomUUID());
    pojo1.setLongProp1(123L);
    pojo1.setColor(Colors.RED);

    em.save(pojo1);

    // do some stuff

    MyPojo pojo2 = em.load(MyPojo.class, pojo1.getId());

    // do some more stuff

    System.out.println( "Color = " + pojo2.getColor() );
  }
}
