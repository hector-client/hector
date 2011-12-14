package me.prettyprint.hom;

import java.util.List;

/**
 * Keys in Cassandra cannot inherently be multi-field, so a strategy must be employed to concatenate fields together.
 *
 * @author B. Todd Burruss
 */
public interface KeyConcatenationStrategy {

  /**
   * Concatenate the byte[] List into a single byte[].
   *
   * @param segmentList
   * @return concatenated byte[]
   */
  byte[] concat(List<byte[]> segmentList);

  /**
   * Split the byte[] into its fields (or segments).
   *
   * @param colFamKey
   * @return List of fields, or segments
   */
  List<byte[]> split(byte[] colFamKey);

}