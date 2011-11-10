package ida1;

/**
 *
 * @author maartenhus
 */
public class Main
{
    public void performanceTest()
    {
        Chocobo.printValues();
        Chocobo.start();
        TreeWithDictionary.test();
        Chocobo.stop();
        System.out.println("Memory usage in bytes: " + Chocobo.getMemoryUsage());
        System.out.println("Elapsed time in miliseconds: " + Chocobo.getTimeElapsed());
        System.out.println("Elapsed time in nanoseconds: " + Chocobo.getNanoTimeElapsed());
        Chocobo.reset();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
	{
        Main main = new Main();
		main.performanceTest();
    }
}
