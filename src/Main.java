import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import gui.MainFrame;
import logic.Cypher;

public class Main
{
	
	public static void main(String[] args)
			throws NoSuchAlgorithmException, NoSuchPaddingException
	{
		System.out.println(
				"SymKey: " + Cypher.getInstance().generateSymKey((short) 128));
		String[] strs = Cypher.getInstance().generateAsymKey((short) 512);
		
		System.out.println("AsymPubKey: " + strs[0]);
		System.out.println("AsymPrvKey: " + strs[1]);
		
		new MainFrame("DSDS-Uebung_2");
	}
	
}
