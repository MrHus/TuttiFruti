package ida1.algorithms.sort;

import ida1.datastructures.BinaryHeap;
import java.util.ArrayList;

/**
 *
 * @author maartenhus
 */
public class HeapSort <E extends Comparable<? super E>> implements SortAlgorithm<E>
{
	public E[] sort(E[] array)
    {
        throw new UnsupportedOperationException("Generic not supported in HeapSort.");
    }

	public int[] sort(int[] array)
	{
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < array.length; i++)
        {
            list.add(new Integer(array[i]));
        }

		BinaryHeap<Integer> heap = new BinaryHeap<Integer>(BinaryHeap.Type.MINHEAP, list);

		ArrayList <Integer> minValues = new ArrayList<Integer>();
		while (!heap.getList().isEmpty())
		{
			minValues.add(heap.delete());
		}

		int[] ret = new int[minValues.size()];
		for (int i=0; i < ret.length; i++)
		{
			ret[i] = minValues.get(i).intValue();
		}
		
		return ret;
	}

	public char[] sort(char[] array)
	{
        ArrayList<Character> list = new ArrayList<Character>();
        for (int i = 0; i < array.length; i++)
        {
            list.add(new Character(array[i]));
        }

		BinaryHeap<Character> heap = new BinaryHeap<Character>(BinaryHeap.Type.MINHEAP, list);

		ArrayList <Character> minValues = new ArrayList<Character>();
		while (!heap.getList().isEmpty())
		{
			minValues.add(heap.delete());
		}

		char[] ret = new char[minValues.size()];
		for (int i=0; i < ret.length; i++)
		{
			ret[i] = minValues.get(i).charValue();
		}

		return ret;
	}

	public static void test()
    {
        HeapSort heapSort = new HeapSort();

        int[] intArray = {7, 8, 3, 5, 3, 10, 1, 9, 0, 2, 4, 6};
		System.out.println("int array input: ");
        for(int i = 0; i < intArray.length; i++)
        {
            System.out.print(intArray[i] + "  ");
        }

		System.out.println("");

        System.out.println("int array output: ");
        for(int i : heapSort.sort(intArray))
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
        for(char c : heapSort.sort(charArray))
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
