package ida1.huffman;

import ida1.trees.BKnoop;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
			BKnoop<Map.Entry <Character, Integer>> huffmanTree = huffman.huffmanTree(huffman.frequencyToSortedList(huffman.frequencyHashMap("bananen")));
			String encodedMap = huffman.encodeString(mapString);

			writeToFile(map + "encoded", encodedMap);

			System.out.println("Encoded length: " + encodedMap.length());

			String decodedMap = huffman.decodeString(encodedMap, huffmanTree);

			System.out.println("Decoded lenght: " + decodedMap.length());

			double compression = (double) decodedMap.length() / encodedMap.length() * 100;

			System.out.println("compression: "+ Math.round(compression) + "%");
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

	public static void writeToFile(String filename, String encodedString)
	{
		try
		{
			String documentPath = "src/ida1/huffman/";
			FileOutputStream fos = new FileOutputStream(documentPath + filename +".txt");
			DataOutputStream dos = new DataOutputStream(fos);

			dos.writeBytes(encodedString);

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
}