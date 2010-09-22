package me.prettyprint.cassandra.service;

import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.serializers.StringSerializer;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.Deletion;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SuperColumn;
import org.junit.Before;
import org.junit.Test;

public class BatchMutationTest {

  private List<String> columnFamilies;
  private BatchMutation<String> batchMutate;

  @Before
  public void setup() {
    columnFamilies = new ArrayList<String>();
    columnFamilies.add("Standard1");
    batchMutate = new BatchMutation<String>(StringSerializer.get());
  }

  @Test
  public void testAddInsertion() {
    Column column = new Column(bytes("c_name"), bytes("c_val"), System.currentTimeMillis());
    batchMutate.addInsertion("key1", columnFamilies, column);
    // assert there is one outter map row with 'key' as the key
    Map<String, Map<String, List<Mutation>>> mutationMap = batchMutate.getRawMutationMap();
    
    assertEquals(1, mutationMap.get("key1").size());

    // add again with a different column and verify there is one key and two mutations underneath
    // for "standard1"
    Column column2 = new Column(bytes("c_name2"), bytes("c_val2"), System.currentTimeMillis());
    batchMutate.addInsertion("key1",columnFamilies, column2);
    assertEquals(2, mutationMap.get("key1").get("Standard1").size());
  }

  @Test
  public void testAddSuperInsertion() {
    SuperColumn sc = new SuperColumn(bytes("c_name"),
        Arrays.asList(new Column(bytes("c_name"), bytes("c_val"), System.currentTimeMillis())));
    batchMutate.addSuperInsertion("key1", columnFamilies, sc);
    // assert there is one outter map row with 'key' as the key
    assertEquals(1, batchMutate.getRawMutationMap().get("key1").size());

    // add again with a different column and verify there is one key and two mutations underneath
    // for "standard1"
    SuperColumn sc2 = new SuperColumn(bytes("c_name2"),
        Arrays.asList(new Column(bytes("c_name"), bytes("c_val"), System.currentTimeMillis())));
    batchMutate.addSuperInsertion("key1", columnFamilies, sc2);
    assertEquals(2, batchMutate.getRawMutationMap().get("key1").get("Standard1").size());
  }


  @Test
  public void testAddDeletion() {
    Deletion deletion = new Deletion(System.currentTimeMillis());
    SlicePredicate slicePredicate = new SlicePredicate();
    slicePredicate.addToColumn_names(bytes("c_name"));
    deletion.setPredicate(slicePredicate);
    batchMutate.addDeletion("key1", columnFamilies, deletion);

    assertEquals(1,batchMutate.getRawMutationMap().get("key1").size());

    deletion = new Deletion(System.currentTimeMillis());
    slicePredicate = new SlicePredicate();
    slicePredicate.addToColumn_names(bytes("c_name2"));
    deletion.setPredicate(slicePredicate);
    batchMutate.addDeletion("key1", columnFamilies, deletion);
    assertEquals(2,batchMutate.getRawMutationMap().get("key1").get("Standard1").size());
  }
}
