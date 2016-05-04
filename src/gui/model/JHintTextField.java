package gui.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * Realisierung einer Textbox mit Hinweistext im Hintergrund.
 * 
 * @author Marv
 *
 */
public class JHintTextField extends JTextField implements FocusListener
{
	private static final long serialVersionUID = 1L;
	
	private boolean showHint;
	private String hint;
	
	public JHintTextField(int columns, String hint)
	{
		super(columns);
		init(hint);
	}
	
	public JHintTextField(int columns, String text, String hint)
	{
		this(columns, hint);
		setText(text);
	}
	
	private void init(String hint)
	{
		this.addFocusListener(this);
		this.hint = hint;
		showHint();
	}
	
	public void setHint(String hint)
	{
		this.hint = hint;
		repaint();
	}
	
	public String getHint()
	{
		return hint;
	}
	
	private void showHint()
	{
		showHint = true;
		repaint();
	}
	
	private void hideHint()
	{
		showHint = false;
		repaint();
	}
	
	@Override
	public void setText(String t)
	{
		super.setText(t);
		if (t == null || t.equals(""))
			showHint();
		else
			hideHint();
	}
	
	@Override
	public void focusGained(FocusEvent e)
	{
		if (showHint)
		{
			hideHint();
		}
	}
	
	@Override
	public void focusLost(FocusEvent e)
	{
		if (this.getText().isEmpty())
		{
			showHint();
		}
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (showHint)
		{
			g.setColor(Color.LIGHT_GRAY);
			g.drawString(hint, this.getBorder().getBorderInsets(null).left,
					(int) Math.round(this.getHeight() * .72));
		}
	}
}