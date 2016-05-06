package gui.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import gui.GUIFactory;
import gui.WordProcessingFactory;
import gui.model.FileSelectionListener;
import logic.CipherType;
import logic.Cypher;
import logic.LogicFactory;

public class HashingPanel extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6816143938760049890L;
	private JTextArea taHash =
			GUIFactory.getInstance().createJTextArea(15, 120);
	private JComboBox<Object> cbAlgor;
	
	public HashingPanel()
	{
		JPanel panelSourceFile = new JPanel(new FlowLayout(FlowLayout.LEFT)),
				panelCenter = new JPanel(
						new GridBagLayout()),
				panelKey = new JPanel(new BorderLayout());
		JScrollPane jscp = new JScrollPane(this.taHash);
		JLabel labelKey = new JLabel("Hash value:");
		JTextField tfSourceFile = GUIFactory.getInstance()
				.createJHintTextField(100, "Source file");
		cbAlgor = new JComboBox<Object>(LogicFactory.getInstance()
				.getHashAlgor().toArray());
		JButton bttSelSource = new JButton("Search file ..."),
				bttHash = new JButton("Hash file");
				
		bttSelSource.addActionListener(
				new FileSelectionListener(tfSourceFile, this));
		bttHash.addActionListener(new ButtonListener(tfSourceFile));
		
		GridBagConstraints gbcSource = GUIFactory.getInstance().getConstrains(
				0, 0, 1, 1, 1, 0),
				gbcKey = GUIFactory
						.getInstance().getConstrains(0, 2, 1, 1, 0, 0);
						
		this.setLayout(new BorderLayout());
		
		panelCenter.add(panelSourceFile, gbcSource);
		panelCenter.add(panelKey, gbcKey);
		
		panelSourceFile.add(tfSourceFile);
		panelSourceFile.add(bttSelSource);
		
		panelKey.add(labelKey, BorderLayout.NORTH);
		panelKey.add(jscp, BorderLayout.CENTER);
		
		this.add(cbAlgor, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(bttHash, BorderLayout.SOUTH);
	}
	
	private class ButtonListener implements ActionListener
	{
		JTextField source = null;
		
		public ButtonListener(JTextField source)
		{
			this.source = source;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			try
			{
				HashingPanel.this.taHash.setText("");
				
				List<String> list = WordProcessingFactory.getInstance()
						.createWordWrap(Cypher.getInstance().hashFile(
								new File(this.source.getText()),
								((CipherType) HashingPanel.this.cbAlgor
										.getSelectedItem())
												.getAlgor()),
								120, new LinkedList<String>());
								
				for (String s : list)
					HashingPanel.this.taHash.append(s);
					
			} catch (NoSuchAlgorithmException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
