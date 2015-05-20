package database.model;

import database.controller.DatabaseAppController;

public class QueryInfo
{
	private boolean isLocal;
	/**
		the query being timed 
	 */
	private String query;
	/**
	The length 
 */
	private long queryTime;
	/**
	 * Constructor for a QueryINfo object. Must supply a String and long parameters so 
	 * all needed information is avaliable at construction.
	 * @param query The query being executed 
	 * @param queryTime
	 */
	public QueryInfo(String query, long queryTime)
	{
		this.query = query;
		this.queryTime = queryTime;
		this.isLocal = isLocal;
	}
	
	public QueryInfo(DatabaseAppController databaseAppController)
	{
		
	}

	public String getQuery()
	{
		return query;
	}
	
	public long getQueryTime()
	{
		return queryTime;
	}
}
