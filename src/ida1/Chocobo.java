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
    public static long startFreeMemory = 0;
    public static long endFreeMemory = 0;
    public static long startAllocatedMemory = 0;
    public static long endAllocatedMemory = 0;

    public static void start()
    {
        startFreeMemory = Runtime.getRuntime().freeMemory();
        startAllocatedMemory = Runtime.getRuntime().totalMemory();
        startTime = System.currentTimeMillis();
        startNanoTime = System.nanoTime();
    }

    public static void stop()
    {
        endTime = System.currentTimeMillis();
        endNanoTime = System.nanoTime();
        endFreeMemory = Runtime.getRuntime().freeMemory();
        endAllocatedMemory = Runtime.getRuntime().totalMemory();
    }

    public static long getMemoryUsage()
    {
//        System.out.println("StartMemory: " + startMemory);
//        System.out.println("EndMemory: " + endMemory);
        return startFreeMemory - endFreeMemory;
    }

    public static long GetAllocatedMemory()
    {
//        System.out.println("StartMemory: " + startMemory);
//        System.out.println("EndMemory: " + endMemory);
        return endAllocatedMemory - startAllocatedMemory;
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

    public static void printPerformance()
    {
        System.out.println("Memory Usage: " + getMemoryUsage());
        System.out.println("Allocated memory: " + GetAllocatedMemory());
        System.out.println("Time elapsed: " + getTimeElapsed());
        System.out.println("Nanotime elapsed: " + getNanoTimeElapsed());
    }

    public static void printRawValues()
    {
        System.out.println("startFreeMemory: " + startFreeMemory);
        System.out.println("endFreeMemory: " + endFreeMemory);
        System.out.println("startAllocatedMemory: " + startAllocatedMemory);
        System.out.println("endAllocatedMemory: " + endAllocatedMemory);
        System.out.println("startTime: " + startTime);
        System.out.println("endTime: " + endTime);
        System.out.println("startNanoTime: " + startNanoTime);
        System.out.println("endNanoTime: " + endNanoTime);
    }

    public static void printSomeTestValues()
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

    public static void collectGarbage()
    {
        Runtime.getRuntime().gc();
    }

    /**
     * Also clears any garbage around.
     */
    public static void reset()
    {
        startTime = 0;
        endTime = 0;
        startFreeMemory = 0;
        endFreeMemory = 0;
        startAllocatedMemory = 0;
        endAllocatedMemory = 0;
        Runtime.getRuntime().gc();
    }
}
