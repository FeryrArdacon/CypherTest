import gui.LookFeel;
import gui.MainFrame;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class Main
{
	private static final String COPYRIGHT = "\u00a9";
	
	public static void main(String[] args) throws NoSuchAlgorithmException,
			NoSuchPaddingException
	{
		new LookFeel();
		new MainFrame("Cypher - Patrick Siegmund" + COPYRIGHT);
	}
	
}
