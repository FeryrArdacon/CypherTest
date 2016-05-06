package gui.model;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

public class FileSelectionListener implements ActionListener
{
	JTextField tf = null;
	Component comp = null;
	
	public FileSelectionListener(JTextField tf, Component parent)
	{
		this.tf = tf;
		this.comp = parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser jfc = new JFileChooser(FileSystemView
				.getFileSystemView().getHomeDirectory());
				
		int rc = jfc.showOpenDialog(this.comp);
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			tf.setText(jfc.getSelectedFile().getAbsolutePath());
		}
	}
}
