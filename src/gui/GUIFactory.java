package gui;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GUIFactory
{
	private static GUIFactory instance = null;
	
	public static GUIFactory getInstance()
	{
		return GUIFactory.instance == null ? GUIFactory.instance = new GUIFactory()
				: GUIFactory.instance;
	}
	
	public GridBagConstraints getConstrains(int x, int y, int width,
			int height, double weigthx, double weigthy)
	{
		return new GridBagConstraints(x, y, width, height, weigthx, weigthy,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						5, 5, 5, 5), 0, 0);
	}
}
