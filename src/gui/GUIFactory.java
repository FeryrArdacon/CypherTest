package gui;

import gui.model.JHintTextField;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;

public class GUIFactory {
	private static GUIFactory instance = null;

	public static GUIFactory getInstance() {
		return GUIFactory.instance == null ? GUIFactory.instance = new GUIFactory() : GUIFactory.instance;
	}

	public GridBagConstraints getConstrains(int x, int y, int width, int height, double weigthx, double weigthy) {
		return new GridBagConstraints(x, y, width, height, weigthx, weigthy, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);
	}

	public JTextArea createJTextArea(int rows, int cols) {
		JTextArea ta = new JTextArea(rows, cols);
		ta.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		return ta;
	}

	public JHintTextField createJHintTextField(int cols, String hint) {
		JHintTextField htf = new JHintTextField(cols, hint);
		htf.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
		return htf;
	}

	public JComboBox<Object> createJComboBox(List<?> list) {
		JComboBox<Object> cb = new JComboBox<>(list.toArray());
		cb.setToolTipText("You can download the JCE (Java(TM) Cryptography Extension) Unlimited Strength from the Oracle website.");
		
		ToolTipManager.sharedInstance().setDismissDelay(12000);
		ToolTipManager.sharedInstance().registerComponent(cb);
		
		return cb;
	}
}
