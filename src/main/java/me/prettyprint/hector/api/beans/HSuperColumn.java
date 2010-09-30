package me.prettyprint.hector.api.beans;

import java.util.List;

import me.prettyprint.hector.api.Serializer;

/**
 * Models a SuperColumn in a protocol independant manner
 *
 * @param <SN>
 *          SuperColumn name type
 * @param <N>
 *          Column name type
 * @param <V>
 *          Column value type
 *
 * @author zznate
 */
public interface HSuperColumn<SN, N, V> {

  HSuperColumn<SN, N, V> setName(SN name);

  HSuperColumn<SN, N, V> setSubcolumns(List<HColumn<N, V>> subcolumns);

  HSuperColumn<SN, N, V> setClock(long clock);

  public long getClock();

  int getSize();

  SN getName();

  /**
   *
   * @return an unmodifiable list of columns
   */
  List<HColumn<N, V>> getColumns();

  HColumn<N, V> get(int i);

  Serializer<SN> getNameSerializer();

  byte[] getNameBytes();

  Serializer<SN> getSuperNameSerializer();

  Serializer<V> getValueSerializer();

}