package logic;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Cypher
{
	private static Cypher instance = null;
	
	public static Cypher getInstance()
	{
		return Cypher.instance == null ? Cypher.instance = new Cypher()
				: Cypher.instance;
	}
	
	public String generateSymKey(short bits)
			throws NoSuchAlgorithmException, NoSuchPaddingException
	{
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(bits);
		SecretKey secKey = keyGen.generateKey();
		
		return Base64.getEncoder().encodeToString(secKey.getEncoded());
	}
	
	public String[] generateAsymKey(short bits) throws NoSuchAlgorithmException
	{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(bits);
		KeyPair kp = kpg.generateKeyPair();
		PublicKey pubk = kp.getPublic();
		PrivateKey prvk = kp.getPrivate();
		
		String[] strs = { Base64.getEncoder().encodeToString(pubk.getEncoded()),
				Base64.getEncoder().encodeToString(prvk.getEncoded()) };
				
		return strs;
	}
	
	public void encryptSymFile(File file, String algor, short bits, String key)
	{
	
	}
	
	public void decryptSymFile(File file, String algor, short bits, String key)
	{
	
	}
	
	public void encryptAsymFile(File file, String algor, short bits, String key)
	{
	
	}
	
	public void decryptAsymFile(File file, String algor, short bits, String key)
	{
	
	}
}