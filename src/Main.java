import gui.LookFeel;
import gui.MainFrame;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class Main
{
	
	public static void main(String[] args) throws NoSuchAlgorithmException,
			NoSuchPaddingException
	{
		new LookFeel();
		new MainFrame("DSDS-Uebung_2");
	}
	
}
