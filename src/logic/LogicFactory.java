package logic;

import java.util.ArrayList;
import java.util.List;

public class LogicFactory {
	private static LogicFactory instance = null;

	public static LogicFactory getInstance() {
		return LogicFactory.instance == null ? LogicFactory.instance = new LogicFactory() : LogicFactory.instance;
	}

	public List<CipherType> getSymAlgor() {
		List<CipherType> symAlgorList = new ArrayList<CipherType>();

		symAlgorList.add(new CipherType("AES", "AES/CBC/PKCS5Padding", (short) 128, ""));
		symAlgorList.add(new CipherType("AES", "AES/CBC/PKCS5Padding", (short) 192, "- (not working)"));
		symAlgorList.add(new CipherType("AES", "AES/CBC/PKCS5Padding", (short) 256, "- (not working)"));

		return symAlgorList;
	}

	public List<CipherType> getAsymAlgor() {
		List<CipherType> asymAlgorList = new ArrayList<CipherType>();

		asymAlgorList.add(new CipherType("RSA", "RSA/ECB/NoPadding", (short) 512, "- (only short files (64 byte max.))"));
		asymAlgorList.add(new CipherType("RSA", "RSA/ECB/NoPadding", (short) 1024, "- (not working) (only short files (64 byte max.))"));
		asymAlgorList.add(new CipherType("RSA", "RSA/ECB/NoPadding", (short) 2048, "- (not working) (only short files (64 byte max.))"));

		return asymAlgorList;
	}

	public List<CipherType> getAllAlgor() {
		List<CipherType> algorList = new ArrayList<CipherType>();

		algorList.add(new CipherType("AES", "AES/CBC/PKCS5Padding", (short) 128, ""));
		algorList.add(new CipherType("AES", "AES/CBC/PKCS5Padding", (short) 192, "- (not working)"));
		algorList.add(new CipherType("AES", "AES/CBC/PKCS5Padding", (short) 256, "- (not working)"));

		algorList.add(new CipherType("RSA", "RSA/ECB/NoPadding", (short) 512, "- (only short files (64 byte max.))"));
		algorList.add(new CipherType("RSA", "RSA/ECB/NoPadding", (short) 1024, "- (not working) (only short files (64 byte max.))"));
		algorList.add(new CipherType("RSA", "RSA/ECB/NoPadding", (short) 2048, "- (not working) (only short files (64 byte max.))"));

		return algorList;
	}

	public List<CipherType> getHashAlgor() {
		List<CipherType> hashAlgorList = new ArrayList<CipherType>();

		hashAlgorList.add(new CipherType("MD5", "", (short) 128, ""));
		hashAlgorList.add(new CipherType("SHA-1", "", (short) 160, ""));
		hashAlgorList.add(new CipherType("SHA-512", "", (short) 512, ""));

		return hashAlgorList;
	}
}
