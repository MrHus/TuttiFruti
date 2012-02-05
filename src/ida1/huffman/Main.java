package ida1.huffman;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

		String[] maps = {"map1"};//, "map2", "map3", "map4", "map5"};

		for (String map : maps)
		{
			String documentPath = "src/ida1/huffman/";

			Huffman.encodeToFile(documentPath + map + "encoded.dat", readMapFromFile(documentPath + map +".txt"));

			File f0 = new File(documentPath + map + "encoded.dat");
			long lengthEncoded = f0.length();
			
			File f1 = new File(documentPath + map + ".txt");
			long lengthDecoded = f1.length();

			System.out.println("Encoded length: " + lengthEncoded);
			System.out.println("Decoded length: " + lengthDecoded);

			double compression = (double) lengthDecoded / lengthEncoded * 100;
			System.out.println("compression: "+ Math.round(compression) + "%");

			String decodedMap = Huffman.decodeFromFile(documentPath + map + "encoded.dat");

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
}