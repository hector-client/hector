package me.prettyprint.hector.api.beans;

import me.prettyprint.cassandra.model.SuperSlice;

/**
 * A SuperRow is a touple consisting of a Key and a SuperSlice.
 *
 * A Row may be used to hold the returned value from queries such as get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N>
 *          Column name type
 * @param <V>
 *          Column value type
 *
 */
public interface SuperRow<SN, N, V> {

  String getKey();

  SuperSlice<SN, N, V> getSuperSlice();

}