package database.controller;

import database.model.QueryInfo;
import database.view.DataFrame;

import java.util.ArrayList;
public class DatabaseAppController
{
	private DataFrame appFrame;
	private DatabaseController dataController;
	private ArrayList<QueryInfo> queryList;

	public DatabaseAppController()
	{
		dataController = new DatabaseController(this);
		queryList = new ArrayList<QueryInfo>();
		appFrame = new DataFrame(this);
		
	}
	
	public DataFrame getAppFrame()
	{
		return appFrame;
	}
	
	public DatabaseController getDataController()
	{
		return dataController;
	}
	
	public ArrayList<QueryInfo> getQueryList()
	{
		return queryList;
	}
	
	public void start()
	{
		
	}
}
