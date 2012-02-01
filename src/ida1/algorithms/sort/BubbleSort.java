package ida1.algorithms.sort;

/**
 * Implementation of the BubbleSort algorithm.
 * Sorts the array in ascending order. (0 to 9, A to Z).
 *
 * @author Maarten Hus
 */
public class BubbleSort<E extends Comparable<? super E>> implements SortAlgorithm<E>
{
    /**
     * This generic implementation uses the compareTo() operation of the
     * Comparable class to decide if elements should be swapped.
     * Therefore, any E must implements Comparable.
     *
     * @param E[] array
     * @return E[] Sorted array
     */
    public E[] sort(E[] array)
    {
        if(array.length == 0 || array.length == 1)
        {
            return array; //No sorting is needed
        }

        for(int i = 0; i < array.length; i++)
        {
            for(int j = array.length - 1; j > i; j--)
            {
                //If array[j] < array[j - 1] then swap the elements.
                if(array[j].compareTo(array[j - 1]) == -1)
                {
                    E old = array[j];

                    array[j] = array[j - 1];
                    array[j - 1] = old;
                }
            }
        }
        return array;
    }

    /**
     * Performs bubble sorting on integer arrays.
     *
     * @param int[] array
     * @return int[] Sorted array
     */
    public int[] sort(int[] array)
    {
        if(array.length == 0 || array.length == 1)
        {
            return array; //No sorting is needed
        }

        for(int i = 0; i < array.length; i++)
        {
            for(int j = array.length - 1; j > i; j--)
            {
                if(array[j] < array[j - 1])
                {
                    int old = array[j];

                    array[j] = array[j - 1];
                    array[j - 1] = old;
                }
            }
        }
        return array;
    }

    /**
     * Performs bubble sorting on character arrays;
     *
     * @param char[] array
     * @return char[] Sorted array
     */
    public char[] sort(char[] array)
    {
        if(array.length == 0 || array.length == 1)
        {
            return array; //No sorting is needed
        }

        for(int i = 0; i < array.length; i++)
        {
            for(int j = array.length - 1; j > i; j--)
            {
                if(array[j] < array[j - 1])
                {
                    char old = array[j];

                    array[j] = array[j - 1];
                    array[j - 1] = old;
                }
            }
        }
        return array;
    }

    public static void test()
    {
        //Using Generics.
        BubbleSort<Integer> bubbleSort = new BubbleSort<Integer>();

        Integer[] intArray = {1, 170, 45, 78, 90, 802, 24, 2, 66, 55, 1, 48, 756, 159198498, 156, 423, 1790, 15684, 489, 38540, 189, 489};
        Character[] charArray = {'M', 'H', 'O', 'G', 'P', 'F', 'Z', 'D', 'A', 'K'};

        System.out.println("Example using generic types.");
        System.out.println("Integer array input:");
        for(Integer i : intArray)
        {
            System.out.print(i + "  ");
        }
        System.out.println("\nInteger array output:");
        for(Integer i : bubbleSort.sort(intArray))
        {
            System.out.print(i + "  ");
        }

        BubbleSort<Character> bubbleSort2 = new BubbleSort<Character>();
        System.out.println("\nCharacter array input:");
        for(Character c : charArray)
        {
            System.out.print(c + "  ");
        }
        System.out.println("\nCharacter array output:");
        for(Character c : bubbleSort2.sort(charArray))
        {
            System.out.print(c + "  ");
        }
        System.out.println("\n");

        //Using primitive type sorting
        System.out.println("Example using primitive types.");

        BubbleSort bubbleSort3 = new BubbleSort();

        int[] intArray2 = {1, 170, 45, 78, 90, 802, 24, 2, 66, 55, 1, 48, 756, 159198498, 156, 423, 1790, 15684, 489, 38540, 189, 489};
        char[] charArray2 = {'M', 'H', 'O', 'G', 'P', 'F', 'Z', 'D', 'A', 'K'};

        System.out.println("int array input:");
        for(Integer i : intArray2)
        {
            System.out.print(i + "  ");
        }
        System.out.println("\nint array output:");
        for(int i : bubbleSort3.sort(intArray2))
        {
            System.out.print(i + "  ");
        }

        System.out.println("\nchar array input:");
        for(char c : charArray2)
        {
            System.out.print(c + "  ");
        }
        System.out.println("\nchar array output:");
        for(char c : bubbleSort3.sort(charArray2))
        {
            System.out.print(c + "  ");
        }

        System.out.println("\n");
    }

    public static void main(String[] args)
    {
        test();
    }
}
