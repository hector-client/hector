package me.prettyprint.hom;

import java.util.LinkedList;
import java.util.List;

import me.prettyprint.cassandra.model.HColumnImpl;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;

/**
 * Mock object for constructing column slices for testing.
 *
 * @author Todd Burruss
 */
public class ColumnSliceMockImpl implements ColumnSlice<String, byte[]> {
  private List<HColumn<String, byte[]>> colList = new LinkedList<HColumn<String, byte[]>>();

  @Override
  public HColumn<String, byte[]> getColumnByName(String columnName) {
    for ( HColumn<String, byte[]> col : colList ) {
      if ( col.getName().equals(columnName)) {
        return col;
      }
    }

    return null;
  }

  @Override
  public List<HColumn<String, byte[]>> getColumns() {
    return colList;
  }

  public ColumnSliceMockImpl add( String name, byte[] value ) {
    HColumnImpl<String, byte[]> col = new HColumnImpl<String, byte[]>(StringSerializer.get(), BytesArraySerializer.get());
    col.setName(name);
    col.setValue(value);
    colList.add(col);
    return this;
  }
}
