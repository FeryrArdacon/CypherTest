package logic;

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

import io.IO;

public class Cypher
{
	private static Cypher instance = null;
	private static final String keyCipherPub =
			"MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKtFEHNqmAwo/ILeljnJ+PHRHkcl9ZnHs3HDisj2nmxiINecDyq2a+ypvPuB3WToNryK8dum4EKj56RVt2rjcyECAwEAAQ==";
	private static final String keyCipherPrv =
			"MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAq0UQc2qYDCj8gt6WOcn48dEeRyX1mcezccOKyPaebGIg15wPKrZr7Km8+4HdZOg2vIrx26bgQqPnpFW3auNzIQIDAQABAkBo4XlKhHsp7yt65N3v85wsUVgOT9OP4Xgk8Y8as9OeIJTNglnS0iMvaJ64NAcQ5xQzJc+64L1ReZ+LQVxirnFxAiEA2HAcsgkG9EMZ+VbSoXo9MRh7MK85jSF2FF13Aqs6ryUCIQDKk2BQ6+ZTMpxzaXjIcR6I7x7mC4DMRJGj91O4izUhTQIgdyv70NeBmbrcsVpp7Xl1+fNl2R+SC7BR6NKxtal8TXUCIQC2E3rZoP5XP0FBtsYaGxpf59U03Zf5gZQJ9S5py62IbQIgDQ8qwlEhnDnwI//qJJ+dY4QaZcB19WoEgpCSOecrKxk=";
			
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
	
	public void saveKey(File fileTarget, String key)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			IllegalBlockSizeException, BadPaddingException, IOException
	{
		byte[] data = Base64.getDecoder().decode(key);
		data = this.encryptAsym("RSA/ECB/NoPadding", "RSA", Cypher.keyCipherPub,
				data);
		IO.getInstance().writeFile(fileTarget, data);
	}
	
	public String loadKey(File fileSource, String key)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeySpecException,
			IllegalBlockSizeException, BadPaddingException
	{
		byte[] data = IO.getInstance().loadFile(fileSource);
		data = this.decryptAsym("RSA/ECB/NoPadding", "RSA", Cypher.keyCipherPrv,
				data);
		return Base64.getEncoder().encodeToString(data);
	}
	
	public void encryptSymFile(File fileSource, File fileTarget, String algor,
			String cypherAlgor, short bits, String key)
					throws NoSuchAlgorithmException, NoSuchPaddingException,
					IOException, InvalidKeyException,
					InvalidAlgorithmParameterException,
					IllegalBlockSizeException,
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
					InvalidAlgorithmParameterException,
					IllegalBlockSizeException,
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
		data = this.encryptAsym(cypherAlgor, algor, key, data);
		IO.getInstance().writeFile(fileTarget, data);
	}
	
	public void decryptAsymFile(File fileSource, File fileTarget, String algor,
			String cypherAlgor, short bits, String key)
					throws InvalidKeyException, IllegalBlockSizeException,
					BadPaddingException, NoSuchAlgorithmException,
					NoSuchPaddingException, IOException, InvalidKeySpecException
	{
		byte[] data = IO.getInstance().loadFile(fileSource);
		data = this.decryptAsym(cypherAlgor, algor, key, data);
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
					throws NoSuchAlgorithmException, IOException,
					InvalidKeyException,
					NoSuchPaddingException, InvalidKeySpecException,
					IllegalBlockSizeException, BadPaddingException
	{
		byte[] fileHash = Base64.getDecoder().decode(
				this.hashFile(fileSource, algorHash));
		fileHash = this.encryptAsymSign(cypherAlgor, algorKey, key, fileHash);
		
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
		signatureBytes = this.decryptAsymSign(cypherAlgor, algorKey, key,
				signatureBytes);
		hashValues[1] = Base64.getEncoder().encodeToString(signatureBytes);
		
		return hashValues;
	}
	
	private byte[] encryptAsym(String cypherAlgor, String algor, String key,
			byte[] data) throws NoSuchAlgorithmException,
					NoSuchPaddingException, InvalidKeySpecException,
					InvalidKeyException, IllegalBlockSizeException,
					BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(cypherAlgor);
		PublicKey pubk = this.getPublicKey(key, algor);
		System.out.println("encrypt");
		cipher.init(Cipher.ENCRYPT_MODE, pubk);
		return cipher.doFinal(data);
	}
	
	private byte[] encryptAsymSign(String cypherAlgor, String algor, String key,
			byte[] data) throws NoSuchAlgorithmException,
					NoSuchPaddingException, InvalidKeySpecException,
					InvalidKeyException, IllegalBlockSizeException,
					BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(cypherAlgor);
		PrivateKey prvk = this.getPrivateKey(key, algor);
		
		cipher.init(Cipher.ENCRYPT_MODE, prvk);
		return cipher.doFinal(data);
	}
	
	private byte[] decryptAsym(String cypherAlgor, String algor, String key,
			byte[] data) throws NoSuchAlgorithmException,
					NoSuchPaddingException, InvalidKeySpecException,
					InvalidKeyException, IllegalBlockSizeException,
					BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(cypherAlgor);
		PrivateKey prvk = this.getPrivateKey(key, algor);
		
		cipher.init(Cipher.DECRYPT_MODE, prvk);
		return cipher.doFinal(data);
	}
	
	private byte[] decryptAsymSign(String cypherAlgor, String algor, String key,
			byte[] data) throws NoSuchAlgorithmException,
					NoSuchPaddingException, InvalidKeySpecException,
					InvalidKeyException, IllegalBlockSizeException,
					BadPaddingException
	{
		Cipher cipher = Cipher.getInstance(cypherAlgor);
		PublicKey pubk = this.getPublicKey(key, algor);
		
		cipher.init(Cipher.DECRYPT_MODE, pubk);
		return cipher.doFinal(data);
	}
	
	private byte[] getIV()
	{
		byte[] iv = { 113, 2, 6, 98, 114, 33, 1, 1, 55, 68, 77, 29, 92, 14, 33,
				127 };
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