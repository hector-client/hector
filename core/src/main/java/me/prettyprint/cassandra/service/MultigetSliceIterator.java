package me.prettyprint.cassandra.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.Lists;

import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;

/**
 * Iterates over the MultigetSliceQuery result set, refreshing until all
 * qualifying rows are retrieved based on input keys.&nbsp; This iterator is
 * optimized for parallelism with the help of maxThreadCount option provided. If
 * maxThreadCount is not provided, it calls Cassandra with the
 * set(maxRowCountPerQuery) of row keys at a time unless all keys are queried.
 * E.g., maxRowCountPerQuery is 100 and maxThreadCount 5, it calls Cassandra 5
 * times using 5 threads/ parallelism for total of 500 keys. You can also
 * configure it not to use Threads and call Cassandra 5 time sequentially
 * instead of parallelism by not setting maxThreadCount or setting it 0 
 * 
 * 
 * @author vchella
 * @param <K>
 *            The type of the row key
 * @param <N>
 *            Column name type
 * @param <V>
 *            Column value type
 */
public class MultigetSliceIterator<K, N, V> implements Iterator<Row<K, N, V>> {

	/**
	 * DEFAULT constant variable to store and use when maxColumnCountPerRow is
	 * not specified
	 */
	private static final int DEFAULT_MAXCOL_COUNT = 100;

	/**
	 * DEFAULT constant variable to store and use when maxRowCountPerQuery is
	 * not specified
	 */
	private static final int DEFAULT_MAXROW_COUNT_PERQUERY = 0;

	/**
	 * DEFAULT constant variable to store and use when maxThreadCount is not
	 * specified
	 */
	private static final int DEFAULT_MAX_THREAD_COUNT = 0;

	/**
	 * "Row" Iterator to hold the result of MultigetSliceQuery
	 */
	private Iterator<Row<K, N, V>> iterator;

	/**
	 * "Start" key predicate to retrieve a list of columns in the range of
	 * start-finish Either start and or finish can be null which will toggle the
	 * underlying predicate to use an empty byte[]
	 */
	private N start;

	/**
	 * "End" key predicate to retrieve a list of columns in the range of
	 * start-finish Either start and or finish can be null which will toggle the
	 * underlying predicate to use an empty byte[]
	 */
	private N finish;

	/**
	 * Sets the return order of the columns to be reversed. NOTE: this is
	 * slightly less efficient than reading in comparator order.
	 */
	private boolean reversed;

	/**
	 * private internal variable to hold the current index in list of rows
	 */
	private int rowKeysIndex = 0;

	/**
	 * Generic List to hold row keys "List<K>" which can be used in MultigetSliceQuery
	 */
	private List<List<K>> rowKeysList = new LinkedList<List<K>>();

	/**
	 * Generic List to hold "K" (keys) which are passed to
	 * MultigetSliceIterator
	 */
	private List<K> rowKeys = new LinkedList<K>();

	/**
	 * internal variable to hold maxRowCountPerQuery which is passed to
	 * MultigetSliceIterator. When it is defaulted to 0, all keys will be
	 * queried at once. This setting gives the flexibility to limit the result
	 * size to be in allowed limit of Thrift library. Use this if size of rows
	 * which will be returned is greater than default
	 * thrift_max_message_length_in_mb (16 MB)
	 */
	private int maxRowCountPerQuery = DEFAULT_MAXROW_COUNT_PERQUERY;

	/**
	 * keyspace to be queried
	 */
	private Keyspace keyspace;

	/**
	 * keySerializer to be used in query
	 */
	private Serializer<K> keySerializer;

	/**
	 * nameSerializer to be used in query
	 */
	private Serializer<N> nameSerializer;

	/**
	 * valueSerializer to be used in query
	 */
	private Serializer<V> valueSerializer;

	/**
	 * keyspace to be queried
	 */
	private String columnFamily;

	/**
	 * How long the operation took to execute in MICRO-seconds. 
	 */
	private AtomicLong  totalExecutionTimeMicro = new AtomicLong(0);

	/**
	 * How long the operation took to execute in NANO-seconds. 
	 */
	private AtomicLong totalExecutionTimeNano = new AtomicLong(0);

	/**
	 * internal variable to hold maxThreadCount which is passed. It is defaulted
	 * to 0 if none specified
	 */
	private int maxThreads = DEFAULT_MAX_THREAD_COUNT;

	/**
	 * internal variable to hold maxColumnCountPerRow which is passed. It is
	 * defaulted to 100.
	 */
	private int maxColumnCount = DEFAULT_MAXCOL_COUNT;

	/**
	 * internal variable to hold thread count which is calculated through
	 * prepareKeysForParallelism().
	 */
	private int threadCount = DEFAULT_MAX_THREAD_COUNT;
	
	
	/**
	 * internal variable to hold RowCountPerQuery, 
	 * calculated based on numThreads allowed and numKeys provided
	 */
	private int numKeysPerThread; 
	
	/**
	 * List of Hosts used for execution. This Map is synchronized for thread safety
	 */
	private Map<String,CassandraHost> m_hostsUsed = Collections.synchronizedMap(new HashMap<String, CassandraHost>());

	/**
	 * List<Rows<K,N,V>> to hold the result. This collection is synchronized for thread safety
	 */
	private List<Rows<K, N, V>> queryResult = Collections.synchronizedList(new LinkedList<Rows<K, N, V>>());

	/**
	 * Constructor with the required parameters. Below are default parameter values 
	 * int maxThreadCount = 0; //Disable parallelism 
	 * int maxRowCountPerQuery = 0; // Query all keys at a time. 
	 * int maxColumnCountPerRow = 100;// Limit columns count to 100 in each row
	 * 
	 * @param reversed
	 * @param maxColsCountPerQuery
	 * @param maxColCount
	 * @param maxRowCountPerQuery
	 * @param keyspace
	 * @param keySerializer
	 * @param nameSerializer
	 * @param valueSerializer
	 */
	public MultigetSliceIterator(boolean reversed, Keyspace keyspace,
			Serializer<K> keySerializer, Serializer<N> nameSerializer,
			Serializer<V> valueSerializer, String columnFamily,
			List<K> rowKeys, N start, N finish) {

		this(reversed, keyspace, keySerializer, nameSerializer,
				valueSerializer, columnFamily, rowKeys, start, finish,
				DEFAULT_MAX_THREAD_COUNT, DEFAULT_MAXROW_COUNT_PERQUERY,
				DEFAULT_MAXCOL_COUNT);
	}

	/**
	 * Constructor with the required parameters Constructor with the required
	 * parameters. Below are default parameter values 
	 * int maxThreadCount 0; //Disable parallelism 
	 * int maxRowCountPerQuery = 0; // Query all keys at a time.
	 * 
	 * @param reversed
	 * @param maxColsCountPerQuery
	 * @param maxColCount
	 * @param maxRowCountPerQuery
	 * @param keyspace
	 * @param keySerializer
	 * @param nameSerializer
	 * @param valueSerializer
	 */
	public MultigetSliceIterator(boolean reversed, Keyspace keyspace,
			Serializer<K> keySerializer, Serializer<N> nameSerializer,
			Serializer<V> valueSerializer, String columnFamily,
			List<K> rowKeys, N start, N finish, int maxColumnCountPerRow) {

		this(reversed, keyspace, keySerializer, nameSerializer,
				valueSerializer, columnFamily, rowKeys, start, finish,
				DEFAULT_MAX_THREAD_COUNT, DEFAULT_MAXROW_COUNT_PERQUERY,
				maxColumnCountPerRow);
	}

	/**
	 * Constructor with the required parameters. Below are default parameter values
	 *  int maxThreadCount = 0; //Disable parallelism
	 * 
	 * @param reversed
	 * @param maxColsCountPerQuery
	 * @param maxColCount
	 * @param maxRowCountPerQuery
	 * @param keyspace
	 * @param keySerializer
	 * @param nameSerializer
	 * @param valueSerializer
	 */
	public MultigetSliceIterator(boolean reversed, int maxRowCountPerQuery,
			Keyspace keyspace, Serializer<K> keySerializer,
			Serializer<N> nameSerializer, Serializer<V> valueSerializer,
			String columnFamily, List<K> rowKeys, N start, N finish,
			int maxColumnCountPerRow) {

		this(reversed, keyspace, keySerializer, nameSerializer,
				valueSerializer, columnFamily, rowKeys, start, finish,
				DEFAULT_MAX_THREAD_COUNT, maxRowCountPerQuery,
				maxColumnCountPerRow);
	}

	/**
	 * Constructor with the required parameters. Below are default parameter values 
	 * int maxThreadCount = 0; //Disable parallelism 
	 * int maxColumnCountPerRow = 100;// Limit columns count to 100 in each row
	 * 
	 * @param reversed
	 * @param maxColsCountPerQuery
	 * @param maxColCount
	 * @param maxRowCountPerQuery
	 * @param keyspace
	 * @param keySerializer
	 * @param nameSerializer
	 * @param valueSerializer
	 */
	public MultigetSliceIterator(boolean reversed, int maxRowCountPerQuery,
			Keyspace keyspace, Serializer<K> keySerializer,
			Serializer<N> nameSerializer, Serializer<V> valueSerializer,
			String columnFamily, List<K> rowKeys, N start, N finish) {

		this(reversed, keyspace, keySerializer, nameSerializer,
				valueSerializer, columnFamily, rowKeys, start, finish,
				DEFAULT_MAX_THREAD_COUNT, maxRowCountPerQuery,
				DEFAULT_MAXCOL_COUNT);
	}

	/**
	 * Constructor with all required parameters. No default values will be used
	 * 
	 * @param reversed
	 * @param maxColsCountPerQuery
	 * @param maxColCount
	 * @param maxRowCountPerQuery
	 * @param keyspace
	 * @param keySerializer
	 * @param nameSerializer
	 * @param valueSerializer
	 */
	public MultigetSliceIterator(boolean reversed, Keyspace keyspace,
			Serializer<K> keySerializer, Serializer<N> nameSerializer,
			Serializer<V> valueSerializer, String columnFamily,
			List<K> rowKeys, N start, N finish, int maxThreadCount,
			int maxRowCountPerQuery, int maxColumnCountPerRow) {

		this.reversed = reversed;

		this.maxRowCountPerQuery = maxRowCountPerQuery;
		this.keyspace = keyspace;
		this.keySerializer = keySerializer;
		this.nameSerializer = nameSerializer;
		this.valueSerializer = valueSerializer;
		this.columnFamily = columnFamily;
		this.start = start;
		this.finish = finish;
		this.rowKeys = rowKeys;
		this.maxColumnCount = maxColumnCountPerRow;

		this.maxThreads = maxThreadCount;

		this.rowKeysList = prepareKeysForParallelism();

	}

	/**
	 * This method prepares keys for execution, determines whether to use
	 * parallelism or not to query Cassandra, executes the query and collects the result
	 */
	private void runQuery() {
		if(this.rowKeysList != null && this.rowKeysList.size() > 0) { // Check if there are rowkeys to query Cassandra
			if (threadCount > 1) { // When thread count greater than 1 enables parallelism, use threads to query Cassandra
				// multiple times
				
				ExecutorService executor = Executors
						.newFixedThreadPool(threadCount);
				List<Future<?>> futures = new LinkedList<Future<?>>();

				for (final List<K> param : this.rowKeysList) {
					Future<?> future = executor.submit(new Runnable() {
						public void run() {
							// Query Cassandra with the input keys provided
							runMultigetSliceQuery(param); 
							
						}
					});

					futures.add(future);
				}

				for (Future<?> f : futures) {// iterate through thread results
					try {
						f.get(); // wait for thread to complete

					} catch (InterruptedException e) {
						throw new HectorException("Failed to retrieve rows from Cassandra.",e);
					} 
					catch (ExecutionException e) {
						throw new HectorException("Failed to retrieve rows from Cassandra.",e);
					} 
				}
				// Safe to shutdown the threadpool and release the resources
				executor.shutdown(); 

				// set the rowKeysIndex to size of input keys so as no further calls
				// will be made to Cassandra.
				// This ensures iterator.hasNext() returns false when all keys are
				// queried
				rowKeysIndex = this.rowKeysList.size();

			}
			else {// When thread count less than or equal to 1 (0 or negative) disables
				// parallelism, set of(maxRowCountPerQuery) keys queries
				// Cassandra at a time
			
				runMultigetSliceQuery(this.rowKeysList.get(rowKeysIndex));
				// Increment the rowKeyIndex instead of setting it to  this.rowKeysList.size(); 
				rowKeysIndex++; 
				
			}
		}

		ArrayList<Row<K, N, V>> resultList = new ArrayList<Row<K, N, V>>(queryResult.size());

		synchronized (queryResult) {
			// Ensure that runMultigetSliceQuery() method call updates global
			// variable queryResult with query result (if exists)
			if (queryResult != null && queryResult.size() > 0)	{
				for (Rows<K, N, V> rows : queryResult) {
					if (rows != null && rows.getCount() > 0) {
						for (Row<K, N, V> row : rows) {
							// prepare List<Row<K, N, V>> to return
							// the iterator of <Row<K,N,V>> to the caller
							resultList.add(row);
						}
					}

				}
			}
		}

		// assign global iterator with the result of multigetSliceQuery
		iterator = resultList.iterator(); 
										

	}

	/**
	 * Execute MultigetSliceQuery with the set of (maxRowCountPerQuery) keys
	 * provided and keep the result in global variable queryResult (List<Rows<K,
	 * N, V>>)
	 * 
	 * @param param
	 */
	private void runMultigetSliceQuery(final List<K> param) {
		MultigetSliceQuery<K, N, V> multigetSliceQuery = HFactory
				.createMultigetSliceQuery(keyspace, keySerializer,
						nameSerializer, valueSerializer);

		multigetSliceQuery.setColumnFamily(columnFamily);

		multigetSliceQuery.setKeys(param);

		multigetSliceQuery.setRange(start, finish, reversed, maxColumnCount);

		QueryResult<Rows<K, N, V>> result = multigetSliceQuery.execute();

		queryResult.add(result.get());

		// Add current query execution time to internal variable
		// totalExecutionTimeMicro. When parallelism is enabled, this value might not be correct 
		// always due to available system resources and thread implementation
		totalExecutionTimeMicro.addAndGet(result.getExecutionTimeMicro());
		// Add current query execution time to internal variable
		// totalExecutionTimeNano. When parallelism is enabled, this value might not be correct 
		// always due to available system resources and thread implementation
		totalExecutionTimeNano.addAndGet(result.getExecutionTimeNano());
		
		//Add host used to the list
		m_hostsUsed.put(result.getHostUsed().getIp(), result.getHostUsed());

	}

	@Override
	public boolean hasNext() {
		if (iterator == null) {// if iterator is null, call runQuery
			runQuery();
		} 
		else if (!iterator.hasNext()
				&& rowKeysIndex < this.rowKeysList.size())	{ 
			// only need to do another query if all keys were not queried retrieved
			runQuery();
		}

		return iterator.hasNext();
	}

	@Override
	public Row<K, N, V> next() {
		return iterator.next();
	}

	@Override
	public void remove() {
		iterator.remove();
	}

	/**
	 * How long the operation took to execute in MICRO-seconds. When parallelism
	 * is enabled, this value might not be correct always due to available system resources and thread implementation
	 * 
	 * @return the totalExecutionTimeMicro
	 */
	public long getTotalExecutionTimeMicro() {
		return this.totalExecutionTimeMicro.get();
	}

	/**
	 * How long the operation took to execute in NANO-seconds. When parallelism
	 * is enabled, this value might not be correct always due to available system resources and thread implementation
	 * 
	 * @return the totalExecutionTimeNano
	 */
	public long getTotalExecutionTimeNano() {
		return this.totalExecutionTimeNano.get();
	}
	

	  /**
	   * The {@link CassandraHost} on which this operation
	   * was successful
	   */
	public String getHostsUsed() {
		String hostsUsed= new String();
		StringBuilder strBldr = new StringBuilder();
	
		Set<Entry<String,CassandraHost>> se= m_hostsUsed.entrySet();
		
		for (Entry<String, CassandraHost> entry : se) 
		{
			strBldr.append(entry.getValue().toString());
			strBldr.append(';');	
		}
		if(se.size()>0 && strBldr.length()>0)
		{
			hostsUsed=strBldr.substring(0, strBldr.length()-1);
		}
		return hostsUsed;
	}
	
	
	/**
	 * Number of threads used to call Cassandra
	 * @return Thread count used
	 */
	public int getThreadCountUsed()	{
		return threadCount;
	}

	/**
	 * Returns the RowCount per query used in this operation 
	 * @return
	 */
	public int getRowCountPerQueryUsed()	{
		return numKeysPerThread;
	}
	/**
	 * prepare row Keys For Parallelism by considering maxRowCountPerQuery,
	 * m_maxThreads and numKeys
	 * 
	 * @param m_maxThreads
	 * @return
	 */
	private List<List<K>> prepareKeysForParallelism() {
		// Calculate the number of row keys to be queried at a time.
		// Consider whether parallelism is enabled or not.
		// When numThreads is calculated, the int truncation
		// causes one fewer thread to be used if numKeys isn't evenly divided
		// into maxRowCountPerQuery. Thus,
		// the keys are divided among threads/ calls evenly with each thread
		// getting up to maxRowCountPerQuery

		List<List<K>> returnKeys = new LinkedList<List<K>>();

		int numKeys = rowKeys.size();

		// Calculate how many thread are required based on input # keys and each
		// time rowkeys limit of maxRowCountPerQuery
		int numThreads = 1;

		if(maxRowCountPerQuery>0){
			numThreads=(int) Math.ceil((numKeys / (double) maxRowCountPerQuery));
			numThreads=	Math.max(numThreads, 1);
		}

		// if number of threads required is more than the maximum limit of
		// allowable threads then cap the number of threads

		threadCount=Math.min(numThreads, maxThreads);
		
		// We get the ceiling of numKeys/numThreads in order to spread out the
		// row keys evenly.
		// e.g. if numKeys=101 and maxRowCountPerQuery=50, it makes set of 34
		// keys to be queried at a time. numThreads= Ceil(101/50)=>3

		numKeysPerThread = (int) Math
				.ceil(numKeys / (double) numThreads);

		 // Default it to 1, so that all keys will be passed once, instead of breaking it
		numKeysPerThread=Math.max(numKeysPerThread, 1);
		
		//Check if there are any rowkeys 
		if(this.rowKeys!=null && this.rowKeys.size()>0)		{
			// split keys into subsets based on the above calculation
			returnKeys = Lists.partition(rowKeys, numKeysPerThread);
		}

		return returnKeys;

	}
}
