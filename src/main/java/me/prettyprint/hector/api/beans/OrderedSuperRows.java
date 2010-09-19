package me.prettyprint.hector.api.beans;

import java.util.List;

public interface OrderedSuperRows<SN, N, V> extends SuperRows<SN, N, V>{

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  List<SuperRow<SN, N, V>> getList();

}