package ida1.huffman;

import ida1.trees.BKnoop;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.BitSet;
import java.util.Map;

/**
 *
 * @author maartenhus
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
	{
        /*Huffman huffman = new Huffman();

		System.out.println(huffman.frequencyHashMap("bananen"));

		System.out.println(huffman.frequencyToSortedList(huffman.frequencyHashMap("bananen")));

		BKnoop<Map.Entry <Character, Integer>> huffmanTree = huffman.huffmanTree(huffman.frequencyToSortedList(huffman.frequencyHashMap("bananen")));

		System.out.println(huffmanTree.preOrderToString());

		System.out.println(huffman.codeMap(huffmanTree));

		String encoded = huffman.encodeString("bananen");

		System.out.println(encoded);

		System.out.println(huffman.decodeString(encoded, huffmanTree));*/

		Tester();
    }

	public static void Tester()
	{
		Huffman huffman = new Huffman();

		String[] maps = {"map1", "map2", "map3", "map4", "map5"};

		for (String map : maps)
		{
			System.out.println(map);
			String mapString = readMapFromFile(map);
			BKnoop<Map.Entry <Character, Integer>> huffmanTree = huffman.huffmanTree(huffman.frequencyToSortedList(huffman.frequencyHashMap(mapString)));
			String encodedMap = huffman.encodeString(mapString);

			writeToFile(map + "encoded", encodedMap, huffmanTree);

			File f0 = new File("src/ida1/huffman/" + map + "encoded.dat");
			long lengthEncoded = f0.length();
			
			File f1 = new File("src/ida1/huffman/" + map + ".txt");
			long lengthDecoded = f1.length();

			System.out.println("Encoded length: " + lengthEncoded);
			System.out.println("Decoded length: " + lengthDecoded);

			String decodedMap = huffman.decodeString(encodedMap, huffmanTree);

			double compression = (double) lengthDecoded / lengthEncoded * 100;

			System.out.println("compression: "+ Math.round(compression) + "%");

			System.out.println(decodedMap);
		}
	}

	public static String readMapFromFile(String textfile)
	{
		StringBuilder builder = new StringBuilder();

		try
        {
			String documentPath = "src/ida1/huffman/";
			InputStream is = new BufferedInputStream(new FileInputStream(documentPath + textfile + ".txt"));
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

	public static void writeToFile(String filename, String encodedString, BKnoop<Map.Entry <Character, Integer>> huffManTree)
	{
		try
		{
			String documentPath = "src/ida1/huffman/";
			FileOutputStream fos = new FileOutputStream(documentPath + filename +".dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			final int length = encodedString.length();
			final BitSet bitSet = new BitSet(length);
			for(int i = length - 1; i >= 0; i--)
			{
				// anything that's not a 1 is a zero, per convention
				bitSet.set(i, encodedString.charAt(i) == '1');
			}

			//System.out.println(huffManTree.inOrderToString());

			oos.writeObject(bitSet);
			//oos.reset();
			//oos.writeObject(huffManTree);

			oos.close();
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
}