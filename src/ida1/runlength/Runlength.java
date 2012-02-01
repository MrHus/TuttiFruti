package ida1.runlength;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author maartenhus
 */
public class Runlength
{
	public String encode(String textfile)
	{
		ArrayList<String> lines = readFile(textfile);
		//System.out.println(lines);

		ArrayList<ArrayList<FrequencyAndCharacter>> encodedLines = getFrequencyCharacterPairs(lines);
		//System.out.println(encodedLines);

		StringBuilder builder = new StringBuilder();
		for (ArrayList<FrequencyAndCharacter> encodedLine : encodedLines)
		{
			for (FrequencyAndCharacter fq : encodedLine)
			{
				builder.append(fq.frequency);
				builder.append(fq.character);
			}

			// 00 will represent a line break;
			builder.append(0);
			builder.append(0);
		}

		return builder.toString();
	}

	private ArrayList<ArrayList<FrequencyAndCharacter>> getFrequencyCharacterPairs(ArrayList<String> lines)
	{
		ArrayList<ArrayList<FrequencyAndCharacter>> encodedLines = new ArrayList<ArrayList<FrequencyAndCharacter>>();

		for (String line : lines)
		{
			ArrayList<FrequencyAndCharacter> encodedLine = new ArrayList<FrequencyAndCharacter>();

			int index = 0;
			char current = line.charAt(index);
			int counter = 0;

			for (int i = index; i < line.length(); i++)
			{
				 char nextchar = line.charAt(i);
				 if (current == nextchar)
				 {
					 counter += 1;
				 }
				 else
				 {
					 FrequencyAndCharacter fq = new FrequencyAndCharacter(counter, current);
					 encodedLine.add(fq);

					 current = nextchar;
					 counter = 1;
				 }
			}

			FrequencyAndCharacter fq = new FrequencyAndCharacter(counter, current);
			encodedLine.add(fq);

			encodedLines.add(encodedLine);
		}

		return encodedLines;
	}

	public String decode()
	{
		return "FUCK YOU";
	}

	public void writeToFile(String filename, String encodedString)
	{
		try
		{
			String documentPath = "src/ida1/runlength/";
			FileOutputStream fos = new FileOutputStream(documentPath + filename +".dat");

			/*
			 * To write byte array to a file, use
			 * void write(byte[] bArray) method of Java FileOutputStream class.
			 *
			 * This method writes given byte array to a file.
			 */

			byte[] bytes = encodedString.getBytes();
			System.out.println("Bytes: " + bytes);
			fos.write(bytes);

			/*
			 * Close FileOutputStream using,
			 * void close() method of Java FileOutputStream class.
			 *
			 */

			fos.close();
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("FileNotFoundException : " + ex);
		}
		catch (IOException ioe)
		{
			System.out.println("IOException : " + ioe);
		}
	}

	private ArrayList<String> readFile(String textfile)
	{
		ArrayList<String> lines = new ArrayList<String>();
		
		try
        {
			String documentPath = "src/ida1/runlength/";
			InputStream is = new BufferedInputStream(new FileInputStream(documentPath + textfile + ".txt"));
			try
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(is));

				String line;
				while((line = br.readLine()) != null)
				{
					// We don't want empty lines.
					if (line.equals("") == false)
					{
						lines.add(line);
					}
				}

				br.close();
			}
			finally
			{
				is.close();
			}
		}
		catch (IOException e){}

		return lines;
	}

	private class FrequencyAndCharacter
	{
		public final int  frequency;
		public final char character;

		public FrequencyAndCharacter (int frequency, char character)
		{
			this.frequency = frequency;
			this.character = character;
		}

		@Override
		public String toString()
		{
			return "(" + character + " " + frequency + ")";
		}
	}
}
