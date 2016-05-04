package gui;

import gui.views.CipherPanel;
import gui.views.GenerateKeyPanel;
import gui.views.HashingPanel;
import gui.views.SignaturePanel;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

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
		tpane.addTab("En-/Decrypt file", new CipherPanel());
		tpane.addTab("Hash file", new HashingPanel());
		tpane.addTab("Create/Check signature for file", new SignaturePanel());
		
		this.add(tpane, BorderLayout.CENTER);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
