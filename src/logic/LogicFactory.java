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
		
		symAlgorList.add(new CipherType("AES", (short) 128));
		symAlgorList.add(new CipherType("AES", (short) 192));
		symAlgorList.add(new CipherType("AES", (short) 256));
		
		return symAlgorList;
	}
	
	public List<CipherType> getAsymAlgor()
	{
		List<CipherType> asymAlgorList = new ArrayList<CipherType>();
		
		asymAlgorList.add(new CipherType("RSA", (short) 512));
		asymAlgorList.add(new CipherType("RSA", (short) 1024));
		asymAlgorList.add(new CipherType("RSA", (short) 2048));
		
		return asymAlgorList;
	}
}
