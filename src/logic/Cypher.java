package logic;

import java.security.NoSuchAlgorithmException;
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
		String base64 = "";
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(bits);
		SecretKey secKey = keyGen.generateKey();
		base64 = Base64.getEncoder().encodeToString(secKey.getEncoded());
		System.out.println(
				secKey.getEncoded() == Base64.getDecoder().decode(base64));
		return Base64.getEncoder().encodeToString(secKey.getEncoded());
	}
	
	public String[] generateAsymKey()
	{
		return null;
	}
}
