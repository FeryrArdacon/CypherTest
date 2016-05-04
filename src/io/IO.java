package io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class IO
{
	private static IO instance = null;
	
	public static IO getInstance()
	{
		return IO.instance == null ? IO.instance = new IO() : IO.instance;
	}
	
	public byte[] loadFile(File file) throws IOException
	{
		LinkedList<Byte> listOfBytes = new LinkedList<Byte>();
		int tmp = -1;
		BufferedInputStream bffis =
				new BufferedInputStream(new FileInputStream(file));
				
		while ((tmp = bffis.read()) > -1)
		{
			listOfBytes.add(new Byte((byte) tmp));
		}
		
		bffis.close();
		
		byte[] bytes = new byte[listOfBytes.size()];
		Iterator<Byte> iterator = listOfBytes.iterator();
		
		for (int i = 0; i < bytes.length; i++)
			bytes[i] = iterator.next().byteValue();
			
		return bytes;
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
