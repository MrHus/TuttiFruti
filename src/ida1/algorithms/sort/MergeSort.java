package ida1.algorithms.sort;

import java.util.ArrayList;

/**
 * MergeSort implementation.
 * Has no generics support yet due to a problem with generic array creation.
 *      E[] l = new e[left.size()];
 *  The above statement results in an Uncompiable source code error.
 *
 * @wiki http://en.wikipedia.org/wiki/Merge_sort
 * @author Maarten Hus, Cornel Alders
 */
public class MergeSort<E extends Comparable<? super E>> implements SortAlgorithm<E>
{

    public E[] sort(E[] array)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }


	public Integer[] merge_sort(Integer[] array)
	{
		//allready sorted
		if (array.length <= 1)
			return array;

		ArrayList<Integer> left = new ArrayList<Integer>();
		ArrayList<Integer> right = new ArrayList<Integer>();

		int middle = array.length / 2;

		// This is to make sure the center is consistently chosen if the array is odd.
		if (middle % 2 == 1 && middle != 1)
			middle += 1;

		// Put left side of array in left, the right side of the array in right.
		int counter = 0;
		for(Integer i : array)
		{
			if (counter < middle)
				left.add (i);
			else
				right.add (i);

			counter++;
		}

		// sort the right and left side.

		// To array hack
		Integer[] l = new Integer[left.size()];
		l 	= merge_sort(left.toArray(l));

		// To array hack
		Integer[] r = new Integer[right.size()];
		r 	= merge_sort(right.toArray(r));

		// merge them together.
		return merge (l, r);
	}

	public Integer[] merge(Integer[] l, Integer[] r)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> left = new ArrayList<Integer>();
		ArrayList<Integer> right = new ArrayList<Integer>();

		// put l and r in arraylist left and right.
		for (Integer i : l)
		{
			left.add(i);
		}

		for (Integer i : r)
		{
			right.add(i);
		}

		while (left.size() > 0 || right.size() > 0)
		{
			if (left.size() > 0 && right.size() > 0)
			{
				if (left.get(0) <= right.get(0)) // This is where the sorting actually happens.
				{
					result.add(left.get(0));
					left.remove(0);
				}
				else
				{
					result.add(right.get(0));
					right.remove(0);
				}
			}
			else if (left.size() > 0)
			{
				result.add(left.get(0));
				left.remove(0);
			}
			else if (right.size() > 0)
			{
				result.add(right.get(0));
				right.remove(0);
			}
		}

		Integer[] res = new Integer[result.size()];
		return result.toArray(res);
	}

    public static void test()
    {
        MergeSort mergeSort = new MergeSort();
		Integer[] list = {38, 27, 43, 3, 9, 82, 10};

		System.out.print("Unsorted array: ");

		for(Integer i : list)
		{
			System.out.print(i + " ");
		}

		System.out.print(" \nSorted array: ");

		for(Integer i : mergeSort.merge_sort(list))
		{
			System.out.print(i + " ");
		}
    }

	public static void main(String[] args)
	{
        test();
	}
}