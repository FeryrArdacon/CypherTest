package gui;

import java.util.List;

public class WordProcessingFactory
{
	private static WordProcessingFactory instance;
	
	public static WordProcessingFactory getInstance()
	{
		return instance == null ? new WordProcessingFactory() : instance;
	}
	
	public List<String> createWordWrap(String sentence, int wrap,
			List<String> text)
	{
		String teil1 = "", teil2 = "";
		
		if (sentence.length() <= wrap)
		{
			text.add(sentence);
			return text;
		} else
		{
			
			teil1 = sentence.substring(0, wrap);
			teil2 = sentence.substring(wrap);
			text.add(teil1);
			return this.createWordWrap(teil2, wrap, text);
			
		}
	}
	
	public String removeLineBreaks(String sentence)
	{
		return sentence
				.replaceAll(System.lineSeparator(), "")
				.replaceAll("\\r", "")
				.replaceAll("\\n", "");
	}
}
