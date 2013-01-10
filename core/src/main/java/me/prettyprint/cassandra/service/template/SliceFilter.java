package me.prettyprint.cassandra.service.template;

/**
 *
 * @author thrykol
 */
public interface SliceFilter<C>
{
	/**
	 * Determine if the column should be included in the slice
	 * @param column Column 
	 * @return True if the column should be included, false otherwise
	 */
	boolean accept(C column);
}
