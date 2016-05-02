package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import gui.views.GenerateKeyPanel;

public class MainFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2681336469250233501L;
	
	public MainFrame(String title)
	{
		super(title);
		
		this.setLayout(new BorderLayout());
		
		JTabbedPane tpane = new JTabbedPane();
		
		tpane.addTab("Generate key", new GenerateKeyPanel());
		tpane.addTab("En-/Decrypt file", new GenerateKeyPanel());
		tpane.addTab("Hash file", new GenerateKeyPanel());
		tpane.addTab("Create/Check signature for file", new GenerateKeyPanel());
		
		this.add(tpane, BorderLayout.CENTER);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setMinimumSize(this.getSize());
		this.setResizable(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
