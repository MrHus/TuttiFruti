package ida1.huffman;

import ida1.trees.BKnoop;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractMap;

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
		String[] maps = {"map1", "map2", "map3", "map4", "map5"};

		for (String map : maps)
		{
			System.out.println(map);

			String prefix = "src/ida1/huffman/";
			String mapString = readMapFromFile(prefix + map + ".txt");
			BKnoop<AbstractMap.SimpleEntry <Character, Integer>> huffmanTree = Huffman.huffmanTree(Huffman.frequencyToSortedList(Huffman.frequencyHashMap(mapString)));
			String encodedMap = Huffman.encodeString(mapString);

			Huffman.writeToFile(map + "encoded", encodedMap, huffmanTree);

			File f0 = new File(prefix + map + "encoded.dat");
			long lengthEncoded = f0.length();

			File f1 = new File(prefix + map + ".txt");
			long lengthDecoded = f1.length();

			System.out.println("Encoded length: " + lengthEncoded);
			System.out.println("Decoded length: " + lengthDecoded);

			String decodedMap = Huffman.decodeString(encodedMap, huffmanTree);

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
			InputStream is = new BufferedInputStream(new FileInputStream(textfile));
			try
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(is));

				String line;
				while((line = br.readLine()) != null)
				{
					// No empty line
					if (line.isEmpty() == false)
					{
						builder.append(line + "\n");
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

		return builder.toString();
	}
}