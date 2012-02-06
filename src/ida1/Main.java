package ida1;

/**
 *
 * @author maartenhus
 */
public class Main
{
    public void performanceTest()
    {
        //SystemAnalyser.printPerformance();
        //SystemAnalyser.start();
        //TreeWithDictionary.test();
        //SystemAnalyser.stop();

		//System.out.println("Memory usage in bytes: " + SystemAnalyser.GetAllocatedMemory());
        //System.out.println("Elapsed time in miliseconds: " + SystemAnalyser.getTimeElapsed());
        //System.out.println("Elapsed time in nanoseconds: " + SystemAnalyser.getNanoTimeElapsed());

		//SystemAnalyser.reset();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
	{
        //RobotRunner.random100Test();
		//RobotRunner.random100SameTest();
		//RobotRunner.random100Same10Test();

		RobotRunner.heapSort();
		System.out.println("\n");

		RobotRunner.treeSort();
		System.out.println("\n");

		RobotRunner.mergeSort();
		System.out.println("\n");

		// Silly sentences run ik vanuit zijn eigen main.
    }
}
