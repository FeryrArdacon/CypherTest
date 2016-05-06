package gui.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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

import gui.GUIFactory;
import gui.WordProcessingFactory;
import gui.model.FileSelectionListener;
import logic.CipherType;
import logic.Cypher;
import logic.LogicFactory;

public class CipherPanel extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8597621776818313583L;
	private JTextArea taKey = GUIFactory.getInstance().createJTextArea(15, 120);
	private JComboBox<Object> cbAlgor;
	
	public CipherPanel()
	{
		JPanel panelSourceFile = new JPanel(new FlowLayout(FlowLayout.LEFT)),
				panelTargetFile = new JPanel(
						new FlowLayout(FlowLayout.LEFT)),
				panelCenter = new JPanel(
						new GridBagLayout()),
				panelSouth = new JPanel(new GridLayout(1,
						2)),
				panelKey = new JPanel(new BorderLayout());
		JScrollPane jscp = new JScrollPane(this.taKey);
		JLabel labelKey = new JLabel("Key:");
		JTextField tfSourceFile = GUIFactory.getInstance()
				.createJHintTextField(100, "Source file"),
				tfTargetFile = GUIFactory
						.getInstance().createJHintTextField(100, "Target file");
		cbAlgor = new JComboBox<Object>(LogicFactory.getInstance()
				.getAllAlgor().toArray());
		JButton bttSelSource = new JButton("Search file ..."),
				bttSelTarget = new JButton(
						"Search file ..."),
				bttEncrypt = new JButton("Encrypt"), bttDecrypt = new JButton(
						"Decrypt");
						
		bttSelSource.addActionListener(
				new FileSelectionListener(tfSourceFile, this));
		bttSelTarget.addActionListener(
				new FileSelectionListener(tfTargetFile, this));
		bttEncrypt.addActionListener(new ButtonListener(tfSourceFile,
				tfTargetFile, "ENC"));
		bttDecrypt.addActionListener(new ButtonListener(tfSourceFile,
				tfTargetFile, "DEC"));
				
		GridBagConstraints gbcSource = GUIFactory.getInstance().getConstrains(
				0, 0, 1, 1, 1, 0), gbcTarget = GUIFactory.getInstance()
						.getConstrains(0, 1, 1, 1, 1, 0),
				gbcKey = GUIFactory
						.getInstance().getConstrains(0, 2, 1, 1, 0, 0);
						
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
	
	private class ButtonListener implements ActionListener
	{
		String type = "";
		JTextField source = null, target = null;
		
		public ButtonListener(JTextField source, JTextField target, String type)
		{
			this.source = source;
			this.target = target;
			this.type = type;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			switch (type)
			{
			case "ENC":
				try
				{
					switch (((CipherType) CipherPanel.this.cbAlgor
							.getSelectedItem()).getAlgor())
					{
					case "AES":
						Cypher.getInstance().encryptSymFile(
								new File(source.getText()),
								new File(target.getText()),
								((CipherType) CipherPanel.this.cbAlgor
										.getSelectedItem()).getAlgor(),
								((CipherType) CipherPanel.this.cbAlgor
										.getSelectedItem()).getCypherAlgor(),
								((CipherType) CipherPanel.this.cbAlgor
										.getSelectedItem()).getBits(),
								WordProcessingFactory.getInstance()
										.removeLineBreaks(
												CipherPanel.this.taKey
														.getText()));
						break;
					case "RSA":
						Cypher.getInstance().encryptAsymFile(
								new File(source.getText()),
								new File(target.getText()),
								((CipherType) CipherPanel.this.cbAlgor
										.getSelectedItem()).getAlgor(),
								((CipherType) CipherPanel.this.cbAlgor
										.getSelectedItem()).getCypherAlgor(),
								((CipherType) CipherPanel.this.cbAlgor
										.getSelectedItem()).getBits(),
								WordProcessingFactory.getInstance()
										.removeLineBreaks(
												CipherPanel.this.taKey
														.getText()));
						break;
					}
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
				} catch (InvalidAlgorithmParameterException e)
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
				} catch (InvalidKeySpecException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "DEC":
				try
				{
					switch (((CipherType) CipherPanel.this.cbAlgor
							.getSelectedItem()).getAlgor())
					{
					case "AES":
						Cypher.getInstance().decryptSymFile(
								new File(source.getText()),
								new File(target.getText()),
								((CipherType) CipherPanel.this.cbAlgor
										.getSelectedItem()).getAlgor(),
								((CipherType) CipherPanel.this.cbAlgor
										.getSelectedItem()).getCypherAlgor(),
								((CipherType) CipherPanel.this.cbAlgor
										.getSelectedItem()).getBits(),
								WordProcessingFactory.getInstance()
										.removeLineBreaks(
												CipherPanel.this.taKey
														.getText()));
						break;
					case "RSA":
						Cypher.getInstance().decryptAsymFile(
								new File(source.getText()),
								new File(target.getText()),
								((CipherType) CipherPanel.this.cbAlgor
										.getSelectedItem()).getAlgor(),
								((CipherType) CipherPanel.this.cbAlgor
										.getSelectedItem()).getCypherAlgor(),
								((CipherType) CipherPanel.this.cbAlgor
										.getSelectedItem()).getBits(),
								WordProcessingFactory.getInstance()
										.removeLineBreaks(
												CipherPanel.this.taKey
														.getText()));
						break;
					}
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
				} catch (InvalidAlgorithmParameterException e)
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
				} catch (InvalidKeySpecException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			
		}
		
	}
}
