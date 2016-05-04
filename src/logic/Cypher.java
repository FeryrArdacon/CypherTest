package logic;

import io.IO;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cypher
{
	private static Cypher instance = null;
	
	public static Cypher getInstance()
	{
		return Cypher.instance == null ? Cypher.instance = new Cypher()
				: Cypher.instance;
	}
	
	public String generateSymKey(short bits) throws NoSuchAlgorithmException,
			NoSuchPaddingException
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
		
		String[] strs = {
				Base64.getEncoder().encodeToString(pubk.getEncoded()),
				Base64.getEncoder().encodeToString(prvk.getEncoded()) };
		
		return strs;
	}
	
	public void encryptSymFile(File fileSource, File fileTarget, String algor,
			String cypherAlgor, short bits, String key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			IOException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(cypherAlgor);
		byte[] keyBytes = Base64.getDecoder().decode(key);
		byte[] data = IO.getInstance().loadFile(fileSource);
		
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, algor);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec,
				new IvParameterSpec(this.getIV(bits)));
		data = cipher.doFinal(data);
		
		IO.getInstance().writeFile(fileTarget, data);
	}
	
	public void decryptSymFile(File fileSource, File fileTarget, String algor,
			String cypherAlgor, short bits, String key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			IOException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(cypherAlgor);
		byte[] keyBytes = Base64.getDecoder().decode(key);
		byte[] data = IO.getInstance().loadFile(fileSource);
		
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, algor);
		cipher.init(Cipher.DECRYPT_MODE, keySpec,
				new IvParameterSpec(this.getIV(bits)));
		data = cipher.doFinal(data);
		
		IO.getInstance().writeFile(fileTarget, data);
	}
	
	public void encryptAsymFile(File fileSource, File fileTarget, String algor,
			String cypherAlgor, short bits, String key)
	{
		
	}
	
	public void decryptAsymFile(File fileSource, File fileTarget, String algor,
			String cypherAlgor, short bits, String key)
	{
		
	}
	
	private byte[] getIV(short bits)
	{
		int length = bits / 8;
		byte[] iv = new byte[length];
		for (int i = 0; i < length; i++)
			iv[i] = 1;
		return iv;
	}
}