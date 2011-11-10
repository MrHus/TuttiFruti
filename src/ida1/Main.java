package ida1;

/**
 *
 * @author maartenhus
 */
public class Main
{
    public void performanceTest()
    {
        Chocobo.printPerformance();
        Chocobo.start();
        TreeWithDictionary.test();
        Chocobo.stop();
        System.out.println("Memory usage in bytes: " + Chocobo.GetAllocatedMemory());
        System.out.println("Elapsed time in miliseconds: " + Chocobo.getTimeElapsed());
        System.out.println("Elapsed time in nanoseconds: " + Chocobo.getNanoTimeElapsed());
        Chocobo.reset();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
	{
        MrRoboto.test();
    }
}
