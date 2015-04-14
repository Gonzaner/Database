package database.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.controller.DatabaseAppController;

public class DataPanel extends JPanel
{
	private DatabaseAppController baseController;
	private SpringLayout baseLayout;
	private JButton queryButton;
	private JScrollPane displayPane;
	private JTextArea displayArea;
	private JTable resultsTable;
	private JPasswordField samplePassword;

	public DataPanel(DatabaseAppController baseController)
	{
		this.baseController = baseController;
		baseLayout = new SpringLayout();
		queryButton = new JButton("CLick here to test the query");
		displayArea = new JTextArea(10,30);
		samplePassword = new JPasswordField(null, 20);
	/*
	 * Don't forget to setup them up next time 
	 */
		//setupDisplayPane();
		setupTable();
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	private void setupDisplayPane()
	{
		displayArea.setWrapStyleWord(true);
		displayArea.setLineWrap(true);
		displayArea.setEditable(false);
		displayArea.setBackground(Color.cyan);
		
	}
	
	private void setupTable()
	{
		// One D array for column titles
		//2D array for contents 
		DefaultTableModel basicData = new DefaultTableModel(baseController.getDataController().realResults(), baseController.getDataController().getMetaDataTitles());
		resultsTable = new JTable(basicData);
		displayPane = new JScrollPane(resultsTable);
	}
	private void setupPanel()
	{
		this.setBackground(Color.GREEN);
		this.setSize(1000, 1000);
		this.setLayout(baseLayout);
		this.add(displayPane);
		this.add(queryButton);
		
		samplePassword.setEchoChar((char) 0);
		samplePassword.setFont(new Font("Serif", Font.BOLD, 32));
		samplePassword.setForeground(Color.magenta);
	}
	private void setupListeners()
	{
		queryButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
					{
						String[] temp = baseController.getDataController().getMetaDataTitles();
						for(String current : temp)
						{
							displayArea.setText(displayArea.getText() +"Column : " + current + "\n");
						}
						
					}
		});
		
	}

	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.WEST, queryButton, 142, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, queryButton, 6, SpringLayout.SOUTH, displayPane);
		baseLayout.putConstraint(SpringLayout.NORTH, displayPane, 200, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, displayPane, 100, SpringLayout.WEST, this);
		
	}

	

	
}
