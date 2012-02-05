package ida1.runlength;

import java.io.File;

/**
 *
 * @author maartenhus
 */
public class Main
{
	public static void main (String[] args)
	{
		/*Runlength rl = new Runlength();
		String map = rl.encode("map5");
		System.out.println(map);

		rl.writeToFile("map", map);

		System.out.println(rl.encodedMapFromFile("map"));
		 */

		Tester();
	}

	public static void Tester()
	{
		String[] maps = {"map1", "map2", "map3", "map4", "map5"};

		Runlength rl = new Runlength();

		for (String map : maps)
		{
			System.out.println(map);

			String prefix = "src/ida1/runlength/";

			String encodedMap = rl.encode(prefix + map + ".txt");
			rl.writeToFile(prefix + map + "encoded.dat", encodedMap );

			File f0 = new File(prefix + map + "encoded.dat");
			long lengthEncoded = f0.length();

			File f1 = new File(prefix + map + ".txt");
			long lengthDecoded = f1.length();

			System.out.println("Encoded length: " + lengthEncoded);
			System.out.println("Decoded length: " + lengthDecoded);

			double compression = (double) lengthDecoded / lengthEncoded * 100;

			System.out.println("compression: "+ Math.round(compression) + "%");

			String encodedString = rl.encodedMapFromFile(prefix + map + "encoded.dat");
			String decodedMap = rl.decode(encodedString);

			System.out.println(decodedMap);
		}
	}
}
