package database.controller;

import java.sql.*;

import javax.swing.JOptionPane;

import database.model.QueryInfo;

public class DatabaseController
{
	
	private String connectionString;
	private Connection databaseConnection;
	private DatabaseAppController baseController;
	private String query;
	private long queryTime;
	private long startTime;
	private long endTime;
	private boolean isLocal;
	public long getqueryTime()
	{
		return queryTime;
	}
	
	public DatabaseController(DatabaseAppController baseController)
	{
		this.baseController = baseController;
		this.connectionString ="jdbc:mysql://localhost";
		queryTime = 0;
		checkDriver();
		setupConncetion();
		isLocal = false;
		if(connectionString.contains("LocalHost"))
		{
			isLocal = true;
		}
	}
	public String getQuery()
	{
		return query;
	}
	
	public void connectionStringBuilder(String pathToDBServer, String databaseName, String userName, String password)
	{
		connectionString = "jdbc:mysql://localhost";
		connectionString += pathToDBServer;
		connectionString += "/" + databaseName;
		connectionString += "?user=" +userName;
		connectionString += "&password=" + password;
	}
	private void checkDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception currentException)
		{
			displayErrors(currentException);
			System.exit(1);
		}
	}
	public void closeConnection()
	{
		try
		{
			databaseConnection.close();
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
	public void setupConncetion()
	{
		try
		{
			databaseConnection = DriverManager.getConnection(connectionString);
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
			
		}
	}
	/**
	 * Gets the MetaData that finds the column names and the columns.
	 */
	public String[] getMetaDataTitles()
	{
		String [] columns;
		query = "SHOW TABLES";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try 
		{

			/**
			 * One spot you can put the isLocal 
			 */
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			ResultSetMetaData answerData = answers.getMetaData();
			/**
			 * Gets the column count
			 */
			columns = new String[answerData.getColumnCount()];
			
			for(int column = 0; column < answerData.getColumnCount(); column++)
			{
				columns [column] = answerData.getColumnName(column+1);
			}
			
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}
		/**
		 * Catches the currentException and then displays that the array is empty. 
		 */
		catch(SQLException currentException)
		{
			endTime = System.currentTimeMillis();
			columns = new String[] {"empty"};
			displayErrors(currentException);
		}
		queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(query, queryTime));
		return columns;
	}
	/**
	 * Checks the data member query for a potential violation of database structure/content removal.
	 * Not the spaces around the SQL commands and the use of .toUpperCase to make the check effective 
	 * @return True if the query could remove/destory data, false otherwise
	 */
	private boolean checkQueryForDataViolation()
	{
		if(query.toUpperCase().contains("DROP")
		
			|| query.toUpperCase().contains("TRUNCATE")
			|| query.toUpperCase().contains("SET")
			|| query.toUpperCase().contains("ALTER"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * Generic version of the select query method that will work with any database specified by the current connetionString value.
	 * @param query
	 * @return The 2D array of results from the select query 
	 * @throws A SQLException if a potential data violation is detected in the supplied query
	 */
	public String[][] selectQueryResults(String query)
	{
		String [][] results;
		this.query = query;
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try 
		{
			if(checkQueryForDataViolation())
			{
				throw new SQLException("There was an attempt at a data violation",
						"(you dont get to mess wit da data in here :(",Integer.MIN_VALUE);
			}

			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			/**
			 * Gets the rows
			 */
			answers.last();// finds the last row in the number of rows
			int numberOfRows = answers.getRow();
			answers.beforeFirst();// goes back to the beginning 
			results = new String[numberOfRows][1];	// results is now a new String 2D array that has the numbers of rows and 1 column 
			
			while(answers.next())// if there is another row it moves to that one after getting the data from the previous one. the NOM NOM part
			{
				results[answers.getRow()-1][0] = answers.getString(1);
			}
			/**
			 * closes the answers and the statement, do this to always keep your database nice and safe and not open
			 */
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
			}
		
		catch(SQLException currentException)
		{endTime = System.currentTimeMillis();
			results = new String[][] {{"The Query was unsuccessful"},
								{currentException.getMessage()}
									 };
			displayErrors(currentException);
			}
		queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(query, queryTime));
		return results;
	}
	
	public void submitUpdateQuery(String query)
	{
		this.query = query;
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		try
		{
			Statement updateStatement = databaseConnection.createStatement();
			updateStatement.executeUpdate(query);
			endTime = System.currentTimeMillis();
		}
		catch(SQLException currentError)
		{
			endTime = System.currentTimeMillis();
			displayErrors(currentError);
		}
		baseController.getQueryList().add(new QueryInfo(query, endTime - startTime));
		
	}
	
	
	/**
	 * The results, is a 2D array, has the try catch setup. 
	 */
	public String [][] realResults()
	{
		String [][] results;//2D array 
		String query = "SHOW TABLES";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try 
		{

			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			/**
			 * Gets the rows
			 */
			answers.last();// finds the last row in the number of rows
			int numberOfRows = answers.getRow();
			answers.beforeFirst();// goes back to the beginning 
			results = new String[numberOfRows][1];	// results is now a new String 2D array that has the numbers of rows and 1 column 
			
			while(answers.next())// if there is another row it moves to that one after getting the data from the previous one. the NOM NOM part
			{
				results[answers.getRow()-1][0] = answers.getString(1);
			}
			/**
			 * closes the answers and the statement, do this to always keep your database nice and safe and not open
			 */
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}
		/**
		 * Catches the currentException and then displays that the array is empty. 
		 */
		catch(SQLException currentException)
		{
			results = new String[][] {{"empty"}};
			displayErrors(currentException);
			endTime = System.currentTimeMillis();
		}
		queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(query, queryTime));
		return results;
	}
	/**
	 * displays the tables
	 * @return
	 */
	public String displayTables()
	{
		String tableNames = ""; 
		String query = "SHOW TABLES";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			
			while(answers.next())
			{
				tableNames += answers.getString(1) + "\n";
			}
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}	
		catch(SQLException currentError)
		{endTime = System.currentTimeMillis();
			displayErrors(currentError);
		}
		queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(query, queryTime));
		return tableNames;
		
	}
	/**
	 * the sample data that we got from our tables made in PHPmyadmin 
	 * @return
	 */
	public int insertSample()
	{
		int rowsAffected = -1;
		String query = "INSERT INTO `shin_megami_tensei`.`game_information`"
				+ "(`ID`, `Game Title`, `Main Mythology`, `Number of Demons/Personas`) "
				+ "VALUES (NULL,'HAUPO', 'Chinese', '2');";
		try
		{
			Statement insertStatement = databaseConnection.createStatement();
			rowsAffected = insertStatement.executeUpdate(query);
		}
		
		catch(SQLException currentErorr)
		{
			displayErrors(currentErorr);
		}
				
		return rowsAffected;
	}
	/**
	 * the display error method that enables that the error can be conveyed to the user.
	 * @param currentException
	 */
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(), "Exception"+ currentException.getMessage());
		if(currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL State:" + ((SQLException) currentException).getSQLState());
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL Error Code:" + ((SQLException) currentException).getErrorCode());
		}
	}
	private boolean checkForStructureViolation()
	{
		if(query.toUpperCase().contains("DATABASE"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void dropStatment(String query)
	{
		query = query;
		String results;
		try
		{
			
			if(query.toUpperCase().contains("INDEX"))
			{
				results ="";
			}
			else
			{
				results ="";
			}
			Statement dropStatement = databaseConnection.createStatement();
			int affected = dropStatement.executeUpdate(query);
			
			dropStatement.close();
			
			if(affected==0)
			{
				results +="dropped";
			}
			JOptionPane.showMessageDialog(baseController.getAppFrame(), results);
		}
		catch(SQLException dropEror)
		{
			displayErrors(dropEror);
		}
	}
	public String[] getDatabaseColumnNames(String tableName)
	{
		String[] columns;
		query = "SELECT * FROM`"+ tableName + "`";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			ResultSetMetaData answerData = answers.getMetaData();
			
			columns = new String [answerData.getColumnCount()];
			for(int column = 0; column < answerData.getColumnCount();column++)
			{
				columns[column] = answerData.getColumnName(column+1);
			}
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}
		catch(SQLException currentException)
		{
			endTime = System.currentTimeMillis();
			columns = new String[] {"empty"};
			displayErrors(currentException);
		}
		queryTime = endTime - startTime;
		baseController.getQueryList().add(new QueryInfo(query, queryTime));
		return columns;
	}
}
