package gui.views;

import gui.GUIFactory;
import gui.WordProcessingFactory;
import gui.model.FileSelectionListener;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import logic.CipherType;
import logic.Cypher;
import logic.LogicFactory;

public class SignaturePanel extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8732217958403251664L;
	private JTextArea taKey = GUIFactory.getInstance().createJTextArea(15, 120),
			taSignature = GUIFactory.getInstance().createJTextArea(10, 120),
			taHashValues = GUIFactory.getInstance().createJTextArea(4, 120);
	private JComboBox<Object> cbAlgorKey, cbAlgorHash;
	
	public SignaturePanel()
	{
		JPanel panelNorth = new JPanel(new GridLayout(2, 1)), panelCenter = new JPanel(
				new GridBagLayout()), panelSourceFile = new JPanel(
				new FlowLayout(FlowLayout.LEFT)), panelTextArea = new JPanel(
				new GridBagLayout()), panelSouth = new JPanel(new GridLayout(1,
				2));
		JScrollPane jscp = new JScrollPane(panelTextArea);
		JLabel labelKey = new JLabel("Key:"), labelSignature = new JLabel(
				"Signature:"), labelHashValues = new JLabel("Hash vaules:");
		JTextField tfSourceFile = GUIFactory.getInstance()
				.createJHintTextField(100, "Source file");
		cbAlgorKey = new JComboBox<Object>(LogicFactory.getInstance()
				.getAsymAlgor().toArray());
		cbAlgorHash = new JComboBox<Object>(LogicFactory.getInstance()
				.getHashAlgor().toArray());
		JButton bttSelSource = new JButton("Search file ..."), bttCreateSignature = new JButton(
				"Create signature"), bttCheckSignature = new JButton(
				"Check signature");
		
		bttSelSource.addActionListener(new FileSelectionListener(tfSourceFile,
				this));
		bttCreateSignature.addActionListener(new ButtonListener(tfSourceFile,
				"CREATE"));
		bttCheckSignature.addActionListener(new ButtonListener(tfSourceFile,
				"CHECK"));
		
		GridBagConstraints gbcSource = GUIFactory.getInstance().getConstrains(
				0, 0, 1, 1, 1, 0), gbcKey = GUIFactory.getInstance()
				.getConstrains(0, 1, 1, 1, 0, 0);
		
		GridBagConstraints gbcLabelKey = GUIFactory.getInstance()
				.getConstrains(0, 0, 1, 1, 1, 0), gbcTaKey = GUIFactory
				.getInstance().getConstrains(1, 0, 1, 1, 0, 0), gbcLabelSignature = GUIFactory
				.getInstance().getConstrains(0, 1, 1, 1, 0, 0), gbcTaSignature = GUIFactory
				.getInstance().getConstrains(1, 1, 1, 1, 0, 0), gbcLabelHashValues = GUIFactory
				.getInstance().getConstrains(0, 2, 1, 1, 0, 0), gbcTaHashValues = GUIFactory
				.getInstance().getConstrains(1, 2, 1, 1, 0, 0);
		
		this.setLayout(new BorderLayout());
		
		panelNorth.add(cbAlgorHash);
		panelNorth.add(cbAlgorKey);
		
		panelCenter.add(panelSourceFile, gbcSource);
		panelCenter.add(jscp, gbcKey);
		
		panelSourceFile.add(tfSourceFile);
		panelSourceFile.add(bttSelSource);
		
		panelTextArea.add(labelKey, gbcLabelKey);
		panelTextArea.add(taKey, gbcTaKey);
		panelTextArea.add(labelSignature, gbcLabelSignature);
		panelTextArea.add(taSignature, gbcTaSignature);
		panelTextArea.add(labelHashValues, gbcLabelHashValues);
		panelTextArea.add(taHashValues, gbcTaHashValues);
		
		panelSouth.add(bttCreateSignature);
		panelSouth.add(bttCheckSignature);
		
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.SOUTH);
	}
	
	private class ButtonListener implements ActionListener
	{
		String action = "";
		JTextField source = null;
		
		public ButtonListener(JTextField source, String action)
		{
			this.source = source;
			this.action = action;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			switch (action)
			{
			case "CREATE":
				try
				{
					SignaturePanel.this.taSignature.setText("");
					
					List<String> list = WordProcessingFactory
							.getInstance()
							.createWordWrap(
									Cypher.getInstance()
											.signateFile(
													new File(this.source
															.getText()),
													((CipherType) SignaturePanel.this.cbAlgorHash
															.getSelectedItem())
															.getAlgor(),
													((CipherType) SignaturePanel.this.cbAlgorKey
															.getSelectedItem())
															.getCypherAlgor(),
													((CipherType) SignaturePanel.this.cbAlgorKey
															.getSelectedItem())
															.getAlgor(),
													WordProcessingFactory
															.getInstance()
															.removeLineBreaks(
																	SignaturePanel.this.taKey
																			.getText())),
									120, new LinkedList<String>());
					
					for (String s : list)
						SignaturePanel.this.taSignature.append(s
								+ System.lineSeparator());
				} catch (InvalidKeyException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "CHECK":
				try
				{
					SignaturePanel.this.taHashValues.setText("");
					
					String[] str = Cypher
							.getInstance()
							.getFileHashAndDecryptSignature(
									new File(this.source.getText()),
									WordProcessingFactory
											.getInstance()
											.removeLineBreaks(
													SignaturePanel.this.taSignature
															.getText()),
									((CipherType) SignaturePanel.this.cbAlgorHash
											.getSelectedItem()).getAlgor(),
									((CipherType) SignaturePanel.this.cbAlgorKey
											.getSelectedItem())
											.getCypherAlgor(),
									((CipherType) SignaturePanel.this.cbAlgorKey
											.getSelectedItem()).getAlgor(),
									WordProcessingFactory.getInstance()
											.removeLineBreaks(
													SignaturePanel.this.taKey
															.getText()));
					
					List<String> list = WordProcessingFactory.getInstance()
							.createWordWrap("File hash value:      " + str[0],
									120, new LinkedList<String>());
					
					for (String s : list)
						SignaturePanel.this.taHashValues.append(s
								+ System.lineSeparator());
					
					list = WordProcessingFactory.getInstance().createWordWrap(
							"Signature hash value: " + str[1], 120,
							new LinkedList<String>());
					
					for (String s : list)
						SignaturePanel.this.taHashValues.append(s
								+ System.lineSeparator());
				} catch (InvalidKeyException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalBlockSizeException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BadPaddingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			
		}
		
	}
}
