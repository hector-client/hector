package me.prettyprint.cassandra.service;

public class BatchSizeHint
{
	private int numOfRows;
	private int numOfColumns;

	public BatchSizeHint(int numOfRows, int numOfColumns)
	{
		this.numOfRows = numOfRows;
		this.numOfColumns = numOfColumns;
	}

	public int getNumOfColumns()
	{
		return numOfColumns;
	}

	public int getNumOfRows()
	{
		return numOfRows;
	}
	
}
