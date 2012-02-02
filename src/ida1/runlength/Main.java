package ida1.runlength;

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
			String encodedMap = rl.encode(map);
			System.out.println("encodedLength: " + encodedMap.length());
			rl.writeToFile(map + "encoded", encodedMap );

			String encodedString = rl.encodedMapFromFile(map + "encoded");
			String decodedMap = rl.decode(encodedString);
			System.out.println("decodedLength: " + decodedMap.length());

			double compression = (double) decodedMap.length() / encodedMap.length() * 100;

			System.out.println("compression: "+ Math.round(compression) + "%");
		}
	}
}
