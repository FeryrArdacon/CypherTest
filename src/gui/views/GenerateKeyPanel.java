package gui.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
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
	
	private JTextArea taSymKey = new JTextArea(10, 70),
			taAsymPubKey = new JTextArea(4, 70),
			taAsymPrvKey = new JTextArea(6, 70);
			
	public GenerateKeyPanel()
	{
		JTabbedPane tpane = new JTabbedPane();
		JPanel panelSym = new JPanel(new BorderLayout()),
				panelAsym = new JPanel(new BorderLayout()),
				panelSymKey = new JPanel(new GridLayout(1, 1)),
				panelAsymKey = new JPanel(new GridLayout(2, 1));
				
		tpane.add("Sym. key", panelSym);
		tpane.add("Asym. keys", panelAsym);
		
		List<CipherType> symAlgorList =
				LogicFactory.getInstance().getSymAlgor();
		List<CipherType> asymAlgorList =
				LogicFactory.getInstance().getAsymAlgor();
				
		cbSym =
				new JComboBox<>(symAlgorList.toArray());
		cbAsym =
				new JComboBox<>(asymAlgorList.toArray());
				
		panelSym.add(cbSym, BorderLayout.NORTH);
		panelAsym.add(cbAsym, BorderLayout.NORTH);
		
		panelSym.add(panelSymKey, BorderLayout.CENTER);
		panelAsym.add(panelAsymKey, BorderLayout.CENTER);
		
		panelSymKey.add(taSymKey);
		panelAsymKey.add(taAsymPubKey);
		panelAsymKey.add(taAsymPrvKey);
		
		JButton bttSym = new JButton("Generate sym. key"),
				bttAsym = new JButton("Generate asym. keys");
				
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
					GenerateKeyPanel.this.taSymKey
							.setText(Cypher.getInstance().generateSymKey(
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
