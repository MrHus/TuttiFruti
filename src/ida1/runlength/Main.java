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
		String encodedMap1 = rl.encode("map1");
		System.out.println(encodedMap1);

		rl.writeToFile("map1encoded", encodedMap1);
	}
}
