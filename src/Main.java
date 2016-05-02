import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import logic.Cypher;

public class Main
{
	
	public static void main(String[] args)
			throws NoSuchAlgorithmException, NoSuchPaddingException
	{
		System.out.println(Cypher.getInstance().generateSymKey((short) 128));
		
	}
	
}
