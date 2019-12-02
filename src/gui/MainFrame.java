package gui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import gui.views.CipherPanel;
import gui.views.GenerateKeyPanel;
import gui.views.HashingPanel;
import gui.views.SignaturePanel;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2681336469250233501L;

	public MainFrame(String title) {
		super(title);

		this.setIconImage(new ImageIcon(getClass().getResource("/gui/resources/programm_icon.png")).getImage());

		this.setLayout(new BorderLayout());

		JTabbedPane tpane = new JTabbedPane();

		tpane.addTab("Generate key", new GenerateKeyPanel());
		tpane.addTab("En-/Decrypt file", new CipherPanel());
		tpane.addTab("Hash file", new HashingPanel());
		tpane.addTab("Create/Check signature for file", new SignaturePanel());

		this.add(tpane, BorderLayout.CENTER);

		this.pack();
		this.setMinimumSize(this.getSize());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
