package gui.views;

import gui.GUIFactory;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import logic.CipherType;
import logic.Cypher;
import logic.LogicFactory;

public class GenerateKeyPanel extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5560449122308025616L;
	
	JComboBox<Object> cbSym, cbAsym;
	
	private JTextArea taSymKey = new JTextArea(25, 120),
			taAsymPubKey = new JTextArea(10, 120),
			taAsymPrvKey = new JTextArea(15, 120);
	
	public GenerateKeyPanel()
	{
		JTabbedPane tpane = new JTabbedPane();
		JPanel panelSym = new JPanel(new BorderLayout()), panelAsym = new JPanel(
				new BorderLayout()), panelSymKey = new JPanel(
				new GridBagLayout()), panelAsymKey = new JPanel(
				new GridBagLayout());
		JScrollPane jscpSym = new JScrollPane(panelSymKey), jscpAsym = new JScrollPane(
				panelAsymKey);
		JLabel labelSymKey = new JLabel("Sym. key:"), labelAsymPubKey = new JLabel(
				"Asym. pub. key:"), labelAsymPrvKey = new JLabel(
				"Asym. prv. key:");
		
		GridBagConstraints gbcSymLab = GUIFactory.getInstance().getConstrains(
				0, 0, 1, 1, 0, 0), gbcSymKey = GUIFactory.getInstance()
				.getConstrains(1, 0, 1, 1, 0, 0), gbcAsymPubLab = GUIFactory
				.getInstance().getConstrains(0, 0, 1, 1, 0, 0), gbcAsymPrvLab = GUIFactory
				.getInstance().getConstrains(0, 1, 1, 1, 0, 0), gbcAsymPubKey = GUIFactory
				.getInstance().getConstrains(1, 0, 1, 1, 0, 0), gbcAsymPrvKey = GUIFactory
				.getInstance().getConstrains(1, 1, 1, 1, 0, 0);
		
		tpane.add("Sym. key", panelSym);
		tpane.add("Asym. keys", panelAsym);
		
		cbSym = new JComboBox<>(LogicFactory.getInstance().getSymAlgor()
				.toArray());
		cbAsym = new JComboBox<>(LogicFactory.getInstance().getAsymAlgor()
				.toArray());
		
		panelSym.add(cbSym, BorderLayout.NORTH);
		panelAsym.add(cbAsym, BorderLayout.NORTH);
		
		panelSym.add(jscpSym, BorderLayout.CENTER);
		panelAsym.add(jscpAsym, BorderLayout.CENTER);
		
		panelSymKey.add(labelSymKey, gbcSymLab);
		panelSymKey.add(taSymKey, gbcSymKey);
		panelAsymKey.add(labelAsymPubKey, gbcAsymPubLab);
		panelAsymKey.add(taAsymPubKey, gbcAsymPubKey);
		panelAsymKey.add(labelAsymPrvKey, gbcAsymPrvLab);
		panelAsymKey.add(taAsymPrvKey, gbcAsymPrvKey);
		
		taSymKey.setLineWrap(true);
		taSymKey.setWrapStyleWord(false);
		taSymKey.setEditable(false);
		taAsymPubKey.setLineWrap(true);
		taAsymPubKey.setWrapStyleWord(false);
		taAsymPubKey.setEditable(false);
		taAsymPubKey.setLineWrap(true);
		taAsymPrvKey.setWrapStyleWord(false);
		taAsymPrvKey.setEditable(false);
		
		JButton bttSym = new JButton("Generate sym. key"), bttAsym = new JButton(
				"Generate asym. keys");
		
		bttSym.addActionListener(new ButtonListener("SYM"));
		bttAsym.addActionListener(new ButtonListener("ASYM"));
		
		panelSym.add(bttSym, BorderLayout.SOUTH);
		panelAsym.add(bttAsym, BorderLayout.SOUTH);
		
		this.add(tpane);
	}
	
	private class ButtonListener implements ActionListener
	{
		private String type = "";
		
		public ButtonListener(String type)
		{
			this.type = type;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			switch (type)
			{
			case "SYM":
				try
				{
					GenerateKeyPanel.this.taSymKey.setText(Cypher.getInstance()
							.generateSymKey(
									((CipherType) GenerateKeyPanel.this.cbSym
											.getSelectedItem()).getBits()));
				} catch (NoSuchAlgorithmException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchPaddingException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "ASYM":
				String[] str = null;
				try
				{
					str = Cypher.getInstance().generateAsymKey(
							((CipherType) GenerateKeyPanel.this.cbAsym
									.getSelectedItem()).getBits());
					GenerateKeyPanel.this.taAsymPubKey.setText(str[0]);
					GenerateKeyPanel.this.taAsymPrvKey.setText(str[1]);
				} catch (NoSuchAlgorithmException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		
	}
}
