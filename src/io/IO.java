package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class IO
{
	private static IO instance = null;
	
	public static IO getInstance()
	{
		return IO.instance == null ? IO.instance = new IO() : IO.instance;
	}
	
	public byte[] loadFile(File file) throws IOException
	{
		String str = "", tmp = "";
		BufferedReader bffrd = new BufferedReader(new FileReader(file));
		
		while ((tmp = bffrd.readLine()) != null)
		{
			str = str + tmp;
		}
		
		bffrd.close();
		
		return str.getBytes();
	}
	
	public void writeFile(File file, byte[] bytes) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(file);
		
		file.mkdirs();
		
		fos.write(bytes);
		fos.flush();
		fos.close();
	}
}
