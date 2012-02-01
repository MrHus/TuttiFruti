package ida1.algorithms.sort;

import java.util.ArrayList;
import ida1.datastructures.queue.ListDeque;

/**
 * RadixSort implementation.
 * There is no generic support for now, but I don't think this is possible.
 * How could we define the correct buckets?
 *
 * @author Maarten Hus
 */
public class RadixSort<E extends Comparable<? super E>> implements SortAlgorithm<E>
{
    public E[] sort(E[] array)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * BucketSort implementation for RadixSort.
     *
     * *Details*
     * This implementation of RadixSort sorts integers according to the rule of
     * Least Significant Digit (LSD), which means that we sort first on the
     * leftmost digit first, the one digits, then the ten digits,
     * then the hundred digits and so on.
     *
     * *Detailed details*
     * First we create 10 buckets (Which are deques) in which we will store the
     * appropriate integers. These range from 0 - 9.
     * In bucketSort fashion, we create an integer array that will contain the
     * number of occurences of a particular digit. These also range from 0 - 9.
     * These arrays do not need to be recreated because they are emptied during
     * the sorting algoritm.
     * Before we even attempt to sort an array, we need to know if it isn't
     * sorted already, this is checked by called isSorted().
     *
     * And now we can loop through the list of integers.
     * We temporarily store the current integer so that we can safely perform
     * math operations (The division operation) without changing the original.
     *
     * Then we create a new loop in order to find the correct digit of an int.
     * Here finds place the division and modulo operations and each value is
     * stored for future use in case we need a digit on a higher index position.
     * The first time it will get the rightmost digit (the ones digit) which
     * we identity as digitIndex 1. The second time, the tens digit will be used
     * (digitIndex 2), third time, the hundred digit (digitIndex 3) and so on.
     *
     * Quick example:
     * ----------
     * tmpInt = 15742
     * digitIndex = 3
     *
     * First time : 15742 / 10 = 1574,2 (tmpInt = 1572, remainder = 2)
     * Second time: 1574  / 10 =  157,4 (tmpInt =  157, remainder = 4)
     * Third time : 157   / 10 =   15,7 (tmpInt =   15, remainder = 7)
     * ----------
     *
     * When the correct digit is found, the corresponding index in the
     * count array will be incremented by one and the original integer
     * is added to corresponding bucket.
     *
     * Using the above example, we increment the value in count[7] and
     * add 15742 in the 'sevens' bucket.
     *
     * After we have looped through every integer and added them to
     * the buckets, we emty every bucket starting from the 'zeroes'
     * bucket and create a new list.
     *
     * At the end, another check will occur the verify if the array is sorted.
     * If not, repeat the process but with digitIndex++ otherwise return the
     * sorted list.
     *
     * @todo Remove the count array since it is redundant to keep track of the
     *       total amount of occurences of a digit. We already have a deque
     *       that contains the integers so we can just use
     *       while(!deque.isEmpty()) as the loop.
     *
     * @param int[] list The list to be sorted
     * @return int[] A sorted list
     */
    public int[] sort(int[] list)
    {
        int digitIndex = 1;
        int i, j;
        ArrayList<ListDeque> buckets = createBuckets();
        int[] count = new int[10];

        while(!isSorted(list))
        {
//            System.out.println("Start sorting with radix: " + digitIndex);

            for(i = 0; i < list.length; i++)
            {
//                System.out.println("------------- i: " + i + " with value: " + list[i]);
                int remainder = 0; //What is left after the modulo operation.
                int tmpInt = list[i]; //Starts as the original integer.
                                      //Reduces in value after each pass.

                //This loop gets the correct digit
                for(int x = 1; x <= digitIndex;x++)
                {
//                    System.out.println("Pass: " + x);
                    remainder = tmpInt % 10; //Get the remainder of the devision.
                    tmpInt /= 10; //Store the value after division.
//                    System.out.println("tmpInt " + tmpInt);
//                    System.out.println("remainder after mod " + remainder);
                }

//                System.out.println("-------- " + list[i] + " into bucket " + remainder);
                count[remainder]++;
                //The integer is added as the last entry in order to preserve
                //any previous sorting that has been done.
                buckets.get(remainder).addLast(list[i]);
            }

            //Now empty every bucket starting at 'zeroes' bucket and add it to
            //the list.
            for(i = 0, j = 0; i < count.length; i++)
            {
                for(; count[i] > 0; (count[i])--)
                {
//                    System.out.println("Removing int from bucket: " + i);

                    //The first in the bucket is removed to preserve
                    //any previous sorting that has been done.
                    list[j++] = (Integer) buckets.get(i).removeFirst();
                }
            }
            digitIndex++;
        }
        return list;
    }

    /**
     * Creates 10 buckets of deques and stows it in an ArrayList.
     *
     * @return ArrayList<ListDeque>
     */
    private ArrayList<ListDeque> createBuckets()
    {
        ArrayList<ListDeque> list = new ArrayList<ListDeque>();
        for(int i = 0; i < 10; i++)
        {
            list.add(new ListDeque());
        }
        return list;
    }

    /**
     * Checks if an array is sorted based on A < B.
     *
     * @param list
     * @return
     */
    public boolean isSorted(int[] list)
    {
//        System.out.println("check sorted");
//        System.out.print("Current list: ");
//        String intString = "";
//        for(int i = 0; i < list.length; i++)
//        {
//            intString += list[i] + ", ";
//        }
//        System.out.println(intString);
        if(list.length == 0 || list.length == 1)
        {
            return true;
        }

        for(int i = 0; i < list.length - 1; i++)
        {
//            System.out.println(list[i] + " < " + list[(i + 1)]);
            if(list[i] > list[(i + 1)])
            {
//                System.out.println("not sorted");
                return false;
            }
        }
        return true;
    }

    public static void test()
    {
        RadixSort radixSort = new RadixSort();

        //RadixSort, Least Significant digit first.
        int[] list = {1, 170, 45, 78, 90, 802, 24, 2, 66, 55, 1, 48, 756, 159198498, 156, 423, 1790, 15684, 489, 38540, 189, 489};
        System.out.println("int array input: ");
        for(int i = 0; i < list.length; i++)
        {
            System.out.print(list[i] + " ");
        }
        System.out.println("\nint array output: ");
        int[] sortedList = radixSort.sort(list);

        for(int i = 0; i < sortedList.length; i++)
        {
            System.out.print(sortedList[i] + " ");
        }
        System.out.println("");
    }

    public static void main(String[] args)
    {
        test();
    }
}
