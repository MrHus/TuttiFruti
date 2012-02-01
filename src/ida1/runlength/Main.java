package ida1.runlength;

/**
 *
 * @author maartenhus
 */
public class Main
{
	public static void main (String[] args)
	{
		Runlength rl = new Runlength();
		String map = rl.encode("map2");
		System.out.println(map);

		rl.writeToFile("map", map);

		System.out.println(rl.decode(map));
	}
}
