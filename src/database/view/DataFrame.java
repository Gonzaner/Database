package database.view;

import javax.swing.JFrame;

import database.controller.DatabaseAppController;

public class DataFrame extends JFrame
{
	private DataPanel appPanel;
	
	public DataFrame(DatabaseAppController databaseAppController)
	{
		appPanel = new DataPanel(databaseAppController);
		setupFrame();
	}
	
	private void setupFrame()
	{
		this.setSize(500,500);
		this.setContentPane(appPanel);
		this.setVisible(true);
	}
}
