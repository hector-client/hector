package me.prettyprint.hector.api.beans;

import java.util.List;

public interface OrderedSuperRows<K, SN, N, V> extends SuperRows<K, SN, N, V>{

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  List<SuperRow<K, SN, N, V>> getList();

}