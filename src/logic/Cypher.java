package logic;

import io.IO;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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
	
	public String generateSymKey(String algor, short bits)
			throws NoSuchAlgorithmException, NoSuchPaddingException
	{
		KeyGenerator keyGen = KeyGenerator.getInstance(algor);
		keyGen.init(bits);
		SecretKey secKey = keyGen.generateKey();
		
		return Base64.getEncoder().encodeToString(secKey.getEncoded());
	}
	
	public String[] generateAsymKey(String algor, short bits)
			throws NoSuchAlgorithmException
	{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance(algor);
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
				new IvParameterSpec(this.getIV()));
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
				new IvParameterSpec(this.getIV()));
		data = cipher.doFinal(data);
		
		IO.getInstance().writeFile(fileTarget, data);
	}
	
	public void encryptAsymFile(File fileSource, File fileTarget, String algor,
			String cypherAlgor, short bits, String key)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException, IOException, InvalidKeySpecException
	{
		byte[] data = IO.getInstance().loadFile(fileSource);
		this.encryptAsym(cypherAlgor, cypherAlgor, key, data);
		IO.getInstance().writeFile(fileTarget, data);
	}
	
	public void decryptAsymFile(File fileSource, File fileTarget, String algor,
			String cypherAlgor, short bits, String key)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException,
			NoSuchPaddingException, IOException, InvalidKeySpecException
	{
		byte[] data = IO.getInstance().loadFile(fileSource);
		this.decryptAsym(cypherAlgor, cypherAlgor, key, data);
		IO.getInstance().writeFile(fileTarget, data);
	}
	
	public String hashFile(File fileSource, String algor)
			throws NoSuchAlgorithmException, IOException
	{
		MessageDigest digest = MessageDigest.getInstance(algor);
		byte[] data = IO.getInstance().loadFile(fileSource);
		
		return Base64.getEncoder().encodeToString(digest.digest(data));
	}
	
	public String signateFile(File fileSource, String algorHash,
			String cypherAlgor, String algorKey, String key)
			throws NoSuchAlgorithmException, IOException, InvalidKeyException,
			NoSuchPaddingException, InvalidKeySpecException,
			IllegalBlockSizeException, BadPaddingException
	{
		byte[] fileHash = Base64.getDecoder().decode(
				this.hashFile(fileSource, algorHash));
		this.encryptAsymSign(cypherAlgor, algorKey, key, fileHash);
		
		return Base64.getEncoder().encodeToString(fileHash);
	}
	
	public String[] getFileHashAndDecryptSignature(File fileSource,
			String signature, String algorHash, String cypherAlgor,
			String algorKey, String key) throws NoSuchAlgorithmException,
			IOException, InvalidKeyException, NoSuchPaddingException,
			InvalidKeySpecException, IllegalBlockSizeException,
			BadPaddingException
	{
		String[] hashValues = new String[2];
		byte[] signatureBytes = Base64.getDecoder().decode(signature);
		
		hashValues[0] = this.hashFile(fileSource, algorHash);
		this.decryptAsymSign(cypherAlgor, algorKey, key, signatureBytes);
		hashValues[1] = Base64.getEncoder().encodeToString(signatureBytes);
		
		return hashValues;
	}
	
	private void encryptAsym(String cypherAlgor, String algor, String key,
			byte[] data) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(cypherAlgor);
		PublicKey pubk = this.getPublicKey(key, algor);
		
		cipher.init(Cipher.ENCRYPT_MODE, pubk);
		data = cipher.doFinal(data);
	}
	
	private void encryptAsymSign(String cypherAlgor, String algor, String key,
			byte[] data) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(cypherAlgor);
		PrivateKey prvk = this.getPrivateKey(key, algor);
		
		cipher.init(Cipher.ENCRYPT_MODE, prvk);
		data = cipher.doFinal(data);
	}
	
	private void decryptAsym(String cypherAlgor, String algor, String key,
			byte[] data) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(cypherAlgor);
		PrivateKey prvk = this.getPrivateKey(key, algor);
		
		cipher.init(Cipher.DECRYPT_MODE, prvk);
		data = cipher.doFinal(data);
	}
	
	private void decryptAsymSign(String cypherAlgor, String algor, String key,
			byte[] data) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(cypherAlgor);
		PublicKey pubk = this.getPublicKey(key, algor);
		
		cipher.init(Cipher.DECRYPT_MODE, pubk);
		data = cipher.doFinal(data);
	}
	
	private byte[] getIV()
	{
		int length = 16;
		byte[] iv = new byte[length];
		for (int i = 0; i < length; i++)
			iv[i] = 1;
		return iv;
	}
	
	private PublicKey getPublicKey(String key, String algor)
			throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(Base64
				.getDecoder().decode(key));
		KeyFactory keyFactory = KeyFactory.getInstance(algor);
		return keyFactory.generatePublic(publicSpec);
	}
	
	private PrivateKey getPrivateKey(String key, String algor)
			throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64
				.getDecoder().decode(key));
		KeyFactory keyFactory = KeyFactory.getInstance(algor);
		return keyFactory.generatePrivate(keySpec);
	}
}