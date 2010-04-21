package me.prettyprint.cassandra.service;

import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.Deletion;
import org.apache.cassandra.thrift.SlicePredicate;
import org.junit.Before;
import org.junit.Test;

public class BatchMutationTest {
  
  private List<String> columnFamilies;
  private BatchMutation batchMutate;
  
  @Before
  public void setup() {
    columnFamilies = new ArrayList<String>();
    columnFamilies.add("Standard1");
    batchMutate = new BatchMutation();
  }
  
  @Test
  public void testAddInsertion() {    
    ColumnOrSuperColumn cosc = new ColumnOrSuperColumn();
    cosc.setColumn(new Column(bytes("c_name"), bytes("c_val"), System.currentTimeMillis()));
    batchMutate.addInsertion("key1",columnFamilies,cosc);
    // assert there is one outter map row with 'key' as the key
    // add again with a different cosc and verify there is one key and two mutations underneath for "standard1" 
    assertEquals(1,batchMutate.getMutationMap().get("key1").size());
    //    
    ColumnOrSuperColumn cosc2 = new ColumnOrSuperColumn();
    cosc2.setColumn(new Column(bytes("c_name2"), bytes("c_val2"), System.currentTimeMillis()));
    batchMutate.addInsertion("key1",columnFamilies,cosc2);
    assertEquals(2,batchMutate.getMutationMap().get("key1").get("Standard1").size());
  }
  
  @Test
  public void testAddDeletion() {
    Deletion deletion = new Deletion(System.currentTimeMillis());
    SlicePredicate slicePredicate = new SlicePredicate();
    slicePredicate.addToColumn_names(bytes("c_name"));
    deletion.setPredicate(slicePredicate);
    batchMutate.addDeletion("key1", columnFamilies, deletion);
    
    assertEquals(1,batchMutate.getMutationMap().get("key1").size());
    
    deletion = new Deletion(System.currentTimeMillis());
    slicePredicate = new SlicePredicate();
    slicePredicate.addToColumn_names(bytes("c_name2"));
    deletion.setPredicate(slicePredicate);
    batchMutate.addDeletion("key1", columnFamilies, deletion);
    assertEquals(2,batchMutate.getMutationMap().get("key1").get("Standard1").size());    
  }
}
