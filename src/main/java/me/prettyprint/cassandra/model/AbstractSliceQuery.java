package me.prettyprint.cassandra.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import me.prettyprint.cassandra.utils.Assert;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.query.Query;
import static me.prettyprint.cassandra.utils.StringUtils.bytes;

import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;


/**
 * base type for SliceQuery, RangeSliceQuery and MultigetSliceQuery
 * @author Ran Tavory
 *
 * @param <N>
 * @param <T>
 */
public abstract class AbstractSliceQuery<N,V,T> extends AbstractQuery<N,V,T> {

  protected Collection<N> columnNames;
  protected N start;
  protected N finish;
  protected boolean reversed;
  protected int count;

  /** Use column names or start/finish? */
  protected enum PredicateType {Unknown, ColumnNames, Range}; 
  protected PredicateType predicateType = PredicateType.Unknown;

  public AbstractSliceQuery(Keyspace ko, Serializer<N> nameSerializer, Serializer<V> valueSerializer) {
    super(ko, nameSerializer, valueSerializer);
  }

  /**
   * Sets the column names to be retrieved by this query
   * @param columns a list of column names
   */
  public Query<T> setColumnNames(N... columnNames) {
    this.columnNames = Arrays.asList(columnNames);
    predicateType = PredicateType.ColumnNames;
    return this;
  }

  public Collection<N> getColumnNames() {
    return Collections.unmodifiableCollection(columnNames);
  }

  /**
   * Set a predicate of start/finish to retrieve a list of columns in this range.
   *
   * @param start Start key
   * @param finish End key
   * @param reversed
   * @param count
   * @return
   */
  public Query<T> setRange(N start, N finish, boolean reversed, int count) {    
    this.start = start;
    this.finish = finish;
    this.reversed = reversed;
    this.count = count;    
    predicateType = PredicateType.Range;
    return this;
  }

  /**
   *
   * @return the thrift representation of the predicate
   */
  public SlicePredicate getPredicate() {
    SlicePredicate pred = new SlicePredicate();
    switch(predicateType) {
    	case ColumnNames:
    		if (columnNames == null || columnNames.isEmpty()) {
    	        return null;
    	    }
    		pred.setColumn_names(toThriftColumnNames(columnNames));
    		break;
    	case Range:
    		byte[] startBytes = (start == null ? bytes("") : columnNameSerializer.toBytes(start));
    	    byte[] finishBytes = (finish == null ? bytes("") : columnNameSerializer.toBytes(finish));
    	    SliceRange range = new SliceRange(startBytes, finishBytes, reversed, count);
    	    pred.setSlice_range(range);
    	    break;    		
    	case Unknown:
    		return null;
    }
    return pred;
  }

  private List<byte[]> toThriftColumnNames(Collection<N> clms) {
    List<byte[]> ret = new ArrayList<byte[]>(clms.size());
    for (N name: clms) {
      ret.add(columnNameSerializer.toBytes(name));
    }
    return ret;
  }

  protected String toStringInternal() {
    return "" + (predicateType == PredicateType.ColumnNames ? columnNames : "cStart:" + start + ",cFinish:" + finish);
  }
}
