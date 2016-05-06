package logic;

import java.util.ArrayList;
import java.util.List;

public class LogicFactory
{
	private static LogicFactory instance = null;
	
	public static LogicFactory getInstance()
	{
		return LogicFactory.instance == null
				? LogicFactory.instance = new LogicFactory()
				: LogicFactory.instance;
	}
	
	public List<CipherType> getSymAlgor()
	{
		List<CipherType> symAlgorList = new ArrayList<CipherType>();
		
		symAlgorList.add(new CipherType("AES", "AES/CBC/PKCS5Padding",
				(short) 128));
				
		return symAlgorList;
	}
	
	public List<CipherType> getAsymAlgor()
	{
		List<CipherType> asymAlgorList = new ArrayList<CipherType>();
		
		asymAlgorList
				.add(new CipherType("RSA", "RSA/ECB/NoPadding", (short) 512));
				
		return asymAlgorList;
	}
	
	public List<CipherType> getAllAlgor()
	{
		List<CipherType> algorList = new ArrayList<CipherType>();
		
		algorList
				.add(new CipherType("AES", "AES/CBC/PKCS5Padding",
						(short) 128));
						
		algorList.add(new CipherType("RSA", "RSA/ECB/NoPadding", (short) 512));
		
		return algorList;
	}
	
	public List<CipherType> getHashAlgor()
	{
		List<CipherType> hashAlgorList = new ArrayList<CipherType>();
		
		hashAlgorList
				.add(new CipherType("SHA-1", "", (short) 0));
				
		return hashAlgorList;
	}
}
