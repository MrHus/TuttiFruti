package ida1.algorithms.sort;

/**
 * BucketSort implementation.
 * There is no generic support for now, but I don't think this is possible.
 * How could we define the correct buckets?
 *
 * @author Maarten Hus
 */
public class BucketSort<E extends Comparable<? super E>> implements SortAlgorithm<E>
{
    public E[] sort(E[] array)
    {
        throw new UnsupportedOperationException("Generic not supported in BucketSort.");
    }

    /**
     * Sorts an int array.
     *
     * @param int[] list
     * @param int[] maximumValue Determines how many buckets should be created.
     *                           Due to certain restrictions, this must be the
     *                           maximum value in the array.
     * @return
     */
    public int[] sort(int[] array, int maximumValue)
    {
        int i, j;
        //Note that we do maximumValue+1 here to specify size of the bucket array.
        //Therefore, when the maximumValue is 14, we create 15 buckets;
        //bucket[0] all the way to bucket[14].
        int[] bucket = new int[maximumValue+1];

        for(i = 0; i < array.length; i++)
        {
            bucket[array[i]]++;
        }

//        for(i = 0; i < bucket.length; i++)
//        {
//            System.out.println("count[" + i + "]: " + bucket[i]);
//        }

        for(i = 0, j = 0; i < bucket.length; i++)
        {
            for(; bucket[i] > 0; (bucket[i])--)
            {
                array[j++] = i;
            }
        }

        return array;
    }

    /**
     * Sorts an int array.
     * Very experimental, does not work 100% yet.
     * Can sort characters A-Za-z but always return lowercase letters.
     * (Need to find a way to get the correct int values of each char.
     *  Character.getNumericValue() return 10 for 'A' and 'a')
     *
     * @param int[] list
     * @return
     */
    public char[] sort(char[] array)
    {
        int i, j;
        int[] bucket = new int[36];

        for(i = 0; i < array.length; i++)
        {
            bucket[Character.getNumericValue(array[i])]++;
        }

        for(i = 0, j = 0; i < bucket.length; i++)
        {
            for(; bucket[i] > 0; (bucket[i])--)
            {
                array[j++] = Character.forDigit(i, 36);
            }
        }

        return array;
    }

    public static void test()
    {
        BucketSort bucketSort = new BucketSort();

        int[] intArray = {7, 8, 3, 5, 3, 10, 1, 9, 0, 2, 4, 6};
        System.out.println("int array input: ");
        for(int i = 0; i < intArray.length; i++)
        {
            System.out.print(intArray[i] + "  ");
        }

        System.out.println("");

        System.out.println("int array output: ");
        for(int i : bucketSort.sort(intArray, 10))
        {
            System.out.print(i + "  ");
        }
        System.out.println("");

		char[] charArray = {'b', 'D', 'c', 'k', 'H', 's', 'A', 'W', 'V', 'q', 'L', 'Z', 'a'};
//        char[] charArray = {'z', 'c', 'A', 'b'};
        System.out.println("char array input: ");
        for(char c : charArray)
        {
            System.out.print(c + "  ");
        }

        System.out.println("");

        System.out.println("char array output: ");
        for(char c : bucketSort.sort(charArray))
        {
            System.out.print(c + "  ");
        }

        System.out.println("\nEnd of Stuff");
    }

    public static void main(String[] args)
    {
        test();
    }
}
