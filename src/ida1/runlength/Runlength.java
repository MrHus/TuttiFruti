package ida1.runlength;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
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
	/**
	 *	Encode a string using runlength encoding.
	 *
	 *	====+++=== -> 4=3+3=
	 *  hello	   -> 1h1e2l1o
	 *
	 */
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

			// /n will represent a line break. This is purposely not \n !
			// Since that will put spaces in the file that is going to get written.
			builder.append("/n");
		}

		return builder.toString();
	}	

	/**
	 *	Decode a string that was encoded using runlength
	 *
	 *  4=3+3=   ->  ====+++===
	 *  1h1e2l1o ->  hello
	 *
	 */
	public String decode(String encodedString)
	{
		StringBuilder decodedString = new StringBuilder();

		boolean canExpectNewLine = false;
		int counter = 0;
		
		for (int i = 0; i < encodedString.length(); i++)
		{
			if (Character.isDigit(encodedString.charAt(i)))
			{
				counter += 1;
			}
			else
			{
				// '/n' is code for newline. '/n' can only occur after a 'frequency character' sequence.
				if (encodedString.charAt(i) == '/')
				{
					if (i + 1 < encodedString.length())
					{
						if (encodedString.charAt(i + 1) == 'n')
						{
							decodedString.append("\n");
							i += 1;
						}
						else
						{
							applyFrequency(encodedString, decodedString, i, counter);
							counter = 0;
						}
					}
					else
					{
						applyFrequency(encodedString, decodedString, i, counter);
						counter = 0;
					}
				}
				else
				{
					applyFrequency(encodedString, decodedString, i, counter);
					counter = 0;
				}
			}
		}

		return decodedString.toString();
	}

	/**
	 * Apply a frequency to a character
	 *
	 * 3a -> aaa
	 * 4= -> ===
	 */
	private void applyFrequency(String encodedString, StringBuilder decodedString, int i, int counter)
	{
		//System.out.println("i: " + i + " counter " + counter);

		String frequencyString = encodedString.substring(i - counter, i);

		//System.out.println("frequencyString:" + frequencyString);

		int frequency = Integer.parseInt(frequencyString);

		//System.out.println("Char at index: <" + encodedString.charAt(i) + ">");

		char characterToRepeat = encodedString.charAt(i);
		for (int j = 0; j < frequency; j++)
		{
			decodedString.append(characterToRepeat);
		}

		//System.out.println("decodedString: " + decodedString.toString());
	}

	public void writeToFile(String filename, String encodedString)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(filename);
			DataOutputStream dos = new DataOutputStream(fos);

			for(byte b : encodedString.getBytes())
			{
				dos.writeByte(b);
			}

			dos.close();
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

	public String encodedMapFromFile(String filename)
	{
		StringBuilder builder = new StringBuilder();

		try
        {
			String documentPath = "src/ida1/runlength/";
			InputStream is = new BufferedInputStream(new FileInputStream(filename));
			try
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(is));

				String line;
				while((line = br.readLine()) != null)
				{
					builder.append(line);
				}

				br.close();
			}
			finally
			{
				is.close();
			}
		}
		catch (IOException e){}

		return builder.toString();
	}

	private ArrayList<String> readFile(String filename)
	{
		ArrayList<String> lines = new ArrayList<String>();
		
		try
        {
			String documentPath = "src/ida1/runlength/";
			InputStream is = new BufferedInputStream(new FileInputStream(filename));
			try
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(is));

				String line;
				while((line = br.readLine()) != null)
				{
					// We don't want empty lines.
					if (line.isEmpty() == false)
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

	/**
	 *	Helper class that saves the frequency of a character.
	 */
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

	/*
	 * This function parses frequency and character pairs from a arraylist of lines representing a encoded file.
	 */
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

			// Don't forget to add the last pair. Is skipped in for.
			FrequencyAndCharacter fq = new FrequencyAndCharacter(counter, current);
			encodedLine.add(fq);

			encodedLines.add(encodedLine);
		}

		return encodedLines;
	}
}
