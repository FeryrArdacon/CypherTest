package gui.views;

import gui.GUIFactory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import logic.LogicFactory;

public class CipherPanel extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8597621776818313583L;
	private JTextArea taKey = new JTextArea(15, 120);
	
	public CipherPanel()
	{
		JPanel panelSourceFile = new JPanel(new FlowLayout(FlowLayout.LEFT)), panelTargetFile = new JPanel(
				new FlowLayout(FlowLayout.LEFT)), panelCenter = new JPanel(
				new GridBagLayout()), panelSouth = new JPanel(new GridLayout(1,
				2)), panelKey = new JPanel(new BorderLayout());
		JScrollPane jscp = new JScrollPane(this.taKey);
		JLabel labelKey = new JLabel("Key:");
		JTextField tfSourceFile = new JTextField(100), tfTargetFile = new JTextField(
				100);
		JComboBox<Object> cbAlgor = new JComboBox<Object>(LogicFactory
				.getInstance().getAllAlgor().toArray());
		JButton bttSelSource = new JButton("Search file ..."), bttSelTarget = new JButton(
				"Search file ..."), bttEncrypt = new JButton("Encrypt"), bttDecrypt = new JButton(
				"Decrypt");
		
		GridBagConstraints gbcSource = GUIFactory.getInstance().getConstrains(
				0, 0, 1, 1, 1, 0), gbcTarget = GUIFactory.getInstance()
				.getConstrains(0, 1, 1, 1, 1, 0), gbcKey = GUIFactory
				.getInstance().getConstrains(0, 2, 1, 1, 0, 1);
		
		this.setLayout(new BorderLayout());
		
		panelCenter.add(panelSourceFile, gbcSource);
		panelCenter.add(panelTargetFile, gbcTarget);
		panelCenter.add(panelKey, gbcKey);
		
		panelSourceFile.add(tfSourceFile);
		panelSourceFile.add(bttSelSource);
		
		panelTargetFile.add(tfTargetFile);
		panelTargetFile.add(bttSelTarget);
		
		panelKey.add(labelKey, BorderLayout.NORTH);
		panelKey.add(jscp, BorderLayout.CENTER);
		
		panelSouth.add(bttEncrypt);
		panelSouth.add(bttDecrypt);
		
		this.add(cbAlgor, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.SOUTH);
	}
}
