package database.controller;

import database.model.QueryInfo;
import database.view.DataFrame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;
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
		loadTimingInformation();
	}
	/**
	 * 
	 */
	private void loadTimingInformation()
	{
		try
		{
			File loadFile = new File("");
			if(loadFile.exists())
			{
				queryList.clear();
				Scanner textScanner = new Scanner(loadFile);
				while(textScanner.hasNext())
				{
					String query = textScanner.nextLine();
					textScanner.next();
					queryList.add(new QueryInfo(query, textScanner.nextLong()));
				}
				textScanner.close();
				JOptionPane.showMessageDialog(getAppFrame(),queryList.size()+ "Query objects were loaded into the application");
			}
			else
			{
				JOptionPane.showMessageDialog(getAppFrame(), "File not present. No QueryInfo objects loaded");
			}
		}
		catch(IOException currentError)
		{
			dataController.displayErrors(currentError);
		}
	}
	/**
	 * 
	 */
	public void saveTimingInformation()
	{
		try
		{
			File saveFile = new File("");
			PrintWriter writer = new PrintWriter(saveFile);
			if(saveFile.exists())
			{
				    for (QueryInfo current : queryList)
				    {
				    	
				         writer.println(current.getQuery()); 
				         writer.println(current.getQueryTime());
				    }
				    writer.close();
					JOptionPane.showMessageDialog(getAppFrame(), queryList.size() + "QueryInfo objects were saved");
			}
			else
			{
				JOptionPane.showMessageDialog(getAppFrame(), "File not present. No QueryInfo objects loaded");
			}
		}
		catch(IOException currentError)
		{
			dataController.displayErrors(currentError);
		}
	}
	
}
