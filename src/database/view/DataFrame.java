package database.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import database.controller.DatabaseAppController;

public class DataFrame extends JFrame
{
	private DataPanel appPanel;
	private DatabaseAppController baseController;
	public DataFrame(DatabaseAppController databaseAppController)
	{
		
		appPanel = new DataPanel(databaseAppController);
		setupFrame();
		setupListeners();
	}
	
	private void setupFrame()
	{
		this.setSize(500,500);
		this.setContentPane(appPanel);
		this.setVisible(true);
	}
	private void setupListeners()
	{
		addWindowListener(new WindowListener()
		{

			@Override
			public void windowOpened(WindowEvent e)
			{
				
				
			}

			@Override
			public void windowActivated(WindowEvent arg0)
			{
				
				
			}

			@Override
			public void windowClosed(WindowEvent arg0)
			{
				
			}

			@Override
			public void windowClosing(WindowEvent arg0)
			{
				baseController.saveTimingInformation();
				
			}

			@Override
			public void windowDeactivated(WindowEvent e)
			{
				
			}

			@Override
			public void windowDeiconified(WindowEvent e)
			{
				
				
			}

			@Override
			public void windowIconified(WindowEvent e)
			{
			
				
			}

		});
	}
	
}
