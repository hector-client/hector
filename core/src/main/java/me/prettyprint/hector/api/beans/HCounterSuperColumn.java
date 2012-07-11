package me.prettyprint.hector.api.beans;

import java.nio.ByteBuffer;
import java.util.List;

import me.prettyprint.hector.api.Serializer;

/**
 * Models a Counter SuperColumn.
 *
 * @param <SN>
 *          SuperColumn name type
 * @param <N>
 *          Column name type
 *
 * @author patricioe
 */
public interface HCounterSuperColumn<SN, N> {

  HCounterSuperColumn<SN, N> setName(SN name);

  HCounterSuperColumn<SN, N> addSubCounterColumn(HCounterColumn<N> column);

  HCounterSuperColumn<SN, N> setSubcolumns(List<HCounterColumn<N>> subcolumns);

  int getSize();

  SN getName();

  /**
   *
   * @return an unmodifiable list of columns
   */
  List<HCounterColumn<N>> getColumns();

  HCounterColumn<N> get(int i);

  Serializer<SN> getNameSerializer();

  byte[] getNameBytes();
  
  ByteBuffer getNameByteBuffer();

  Serializer<SN> getSuperNameSerializer();

}