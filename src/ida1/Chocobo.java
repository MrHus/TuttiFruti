package ida1;


/**
 *
 * @author Cornel Alders
 */
public class Chocobo
{
    public static long startTime = 0;
    public static long endTime = 0;
    public static long startNanoTime = 0;
    public static long endNanoTime = 0;
    public static long startMemory = 0;
    public static long endMemory = 0;

    public static void start()
    {
        startMemory = Runtime.getRuntime().freeMemory();
        startTime = System.currentTimeMillis();
        startNanoTime = System.nanoTime();
    }

    public static void stop()
    {
        endTime = System.currentTimeMillis();
        endNanoTime = System.nanoTime();
        endMemory = Runtime.getRuntime().freeMemory();
    }

    public static long getMemoryUsage()
    {
//        System.out.println("StartMemory: " + startMemory);
//        System.out.println("EndMemory: " + endMemory);
        return startMemory - endMemory;
    }

    public static long getTimeElapsed()
    {
//        System.out.println("StartTime: " + startTime);
//        System.out.println("EndTime: " + endTime);
        return endTime - startTime;
    }

    public static long getNanoTimeElapsed()
    {
//        System.out.println("StartTime: " + startNanoTime);
//        System.out.println("EndTime: " + endNanoTime);
        return endNanoTime - startNanoTime;
    }

    public static void collectGarbage()
    {
        Runtime.getRuntime().gc();
    }

    public static void printValues()
    {
        System.out.println("MaxMemory: " + Runtime.getRuntime().maxMemory());
        System.out.println("AllocatedMemory: " + Runtime.getRuntime().totalMemory());
        System.out.println("FreeMemory: " + Runtime.getRuntime().freeMemory());
        System.out.println("Available Processors: " + Runtime.getRuntime().availableProcessors());
        Runtime.getRuntime().gc();
        System.out.println("FreeMemory after gc: " + Runtime.getRuntime().freeMemory());
        System.out.println("AllocatedMemory after gc: " + Runtime.getRuntime().totalMemory());
        System.out.println("NanoTime: " + System.nanoTime());
        System.out.println("Time:" + System.currentTimeMillis());
    }

    /**
     * Also clears any garbage around.
     */
    public static void reset()
    {
        startTime = 0;
        endTime = 0;
        startMemory = 0;
        endMemory = 0;
        Runtime.getRuntime().gc();
    }
}
