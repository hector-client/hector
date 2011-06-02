package me.prettyprint.hector.api.beans;


/**
 * A SuperRow is a touple consisting of a Key and a SuperSlice.
 *
 * A Row may be used to hold the returned value from queries such as get_range_slices.
 *
 * @author Ran Tavory
 *
 * @param <N>
 *          Column name type
 *
 */
public interface CounterSuperRow<K, SN, N> {

  K getKey();

  CounterSuperSlice<SN, N> getSuperSlice();

}