package me.prettyprint.hector.api.beans;

import java.util.List;

public interface OrderedCounterSuperRows<K, SN, N> extends CounterSuperRows<K, SN, N>{

  /**
   * Preserves rows order
   * @return an unmodifiable list of Rows
   */
  List<CounterSuperRow<K, SN, N>> getList();

}