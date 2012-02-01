package ida1.algorithms.sort;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * QuickSort implementation.
 * Has no generics support yet due to a problem with generic array creation.
 *      E[] l = new e[left.size()];
 *  The above statement results in an Uncompilable source code error.
 *
 * It is possible to split the methods into smaller ones that each have their
 * own responsibilities where you have the generic methods with compareTo() and
 * methods that explicitly create the arrays. This would solve the generic array
 * creation problem above however, you won't be able to use the generic sort()
 * method unless you do some typecasting (if that is even possible with generics).
 *
 * @author Maarten Hus
 */
public class QuickSort<E extends Comparable<? super E>> implements SortAlgorithm<E>
{
    public E[] sort(E[] array)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	// quicksort neemt een pivot uit de array, deze wordt gebruikt om de array
	// in tweeen te splitsen in een array met grotere en mindere waardes dan de
	// pivot. Dit process wordt recursief herhaald voor de groter en kleiner dan array.
	// Hij stopt wanneer de array 1 groot is of leeg is.
	//
	// [3 5 1 2 4 6] -> [1 2] + 3 + [5 4 6]
	//					1 + [2]   [4] + 5 + [6]
	//
	// 1 + [2] + 3 + [4] + 5 + [6] = [1 2 3 4 5 6]
 	public Character[] sort(Character[] rij)
	{
		if (rij.length == 1 || rij.length == 0)
		{
			return rij;
		}

		Character pivot = rij[0];

		ArrayList<Character> larger = new ArrayList<Character>();
		ArrayList<Character> smaller = new ArrayList<Character>();

		for (int i = 1; i < rij.length; i++)
		{
			if (rij[i] >= pivot)
			{
				larger.add(rij[i]);
			}
			else
			{
				smaller.add(rij[i]);
			}
		}

		Character[] sml = new Character[smaller.size()];
		Character[] smallerArray = sort(smaller.toArray(sml));

		Character[] lgr = new Character[larger.size()];
		Character[] largerArray = sort(larger.toArray(lgr));

		return mergeLargerPivotAndSmaller(largerArray, pivot, smallerArray);
	}

 	public Integer[] sort(Integer[] rij)
	{
		if (rij.length == 1 || rij.length == 0)
		{
			return rij;
		}

		Integer pivot = rij[0];

		ArrayList<Integer> larger = new ArrayList<Integer>();
		ArrayList<Integer> smaller = new ArrayList<Integer>();

		for (int i = 1; i < rij.length; i++)
		{
			if (rij[i] >= pivot)
			{
				larger.add(rij[i]);
			}
			else
			{
				smaller.add(rij[i]);
			}
		}

		Integer[] sml = new Integer[smaller.size()];
		Integer[] smallerArray = sort(smaller.toArray(sml));

		Integer[] lgr = new Integer[larger.size()];
		Integer[] largerArray = sort(larger.toArray(lgr));

		return mergeLargerPivotAndSmaller(largerArray, pivot, smallerArray);
	}

	/**
     * Merges the smaller, pivot and larger Integer arrays into a single array.
     * The actual merging is moved into another method (buildMegaArrayList)
     * that is generic and creates the Arraylist.
     * This method in turn converts that ArrayList into an Array.
     * In this case, an Integer array.
     *
     * @param larger
     * @param pivot
     * @param smaller
     * @return
     */
	private Integer[] mergeLargerPivotAndSmaller(Integer[] larger, Integer pivot, Integer[] smaller)
	{
        ArrayList<Integer> megaList = buildMegaArrayList(larger, pivot, smaller);
		Integer[] mgl = new Integer[megaList.size()];
		return megaList.toArray(mgl);
	}

	/**
     * Merges the smaller, pivot and larger Character arrays into a single array.
     * The actual merging is moved into another method (buildMegaArrayList)
     * that is generic and creates the Arraylist.
     * This method in turn converts that ArrayList into an Array.
     * In this case, a Character array.
     *
     * @param larger
     * @param pivot
     * @param smaller
     * @return
     */
	private Character[] mergeLargerPivotAndSmaller(Character[] larger, Character pivot, Character[] smaller)
	{
        ArrayList<Character> megaList = buildMegaArrayList(larger, pivot, smaller);
		Character[] mgl = new Character[megaList.size()];
		return megaList.toArray(mgl);
	}

    /**
     * Method that builds the ArrayList.
     *
     * @param <F> Implicit generic, is automatically decided on runtime based
     *            on the given parameters.
     * @param larger
     * @param pivot
     * @param smaller
     * @return
     */
    private <F> ArrayList<F> buildMegaArrayList(F[] larger, F pivot, F[] smaller)
    {
		ArrayList<F> megaList = new ArrayList<F>();
        megaList.addAll(Arrays.asList(smaller));
		megaList.add(pivot);
        megaList.addAll(Arrays.asList(larger));
        return megaList;
    }

	public void print (E[] rij)
	{
		for (int te = 0; te < rij.length; te++)
		{
			System.out.print(rij[te] + "  ");
		}

		System.out.println ("\n");
	}

    public static void test()
    {
        QuickSort quickSort = new QuickSort();
        Character[] list ={'M', 'H', 'O', 'G', 'P', 'F', 'Z', 'D', 'A', 'K'};

        System.out.println("\nCharacter Before Quicksort:");
        quickSort.print(list);

        System.out.println("Character After Quicksort:");
        list = quickSort.sort(list);
        quickSort.print(list);
        Integer[] integerArray = {1, 170, 45, 78, 90, 802, 24, 2, 66, 55, 1, 48, 756, 159198498, 156, 423, 1790, 15684, 489, 38540, 189, 489};

        System.out.println("\nInteger Before Quicksort:");
        quickSort.print(integerArray);

        System.out.println("Integer After Quicksort:");
        integerArray = quickSort.sort(integerArray);
        quickSort.print(integerArray);

        System.out.println("Using Arrays.sort (Which uses a finetuned QuickSort algorithm)");
        int[] testArray = {5, 8, 8, 9, 4, 2, 1, 3, 5, 6, 0, 7};
        System.out.println("int array input:");
        for(Integer i : testArray)
        {
            System.out.print(i + "  ");
        }
        Arrays.sort(testArray);
        System.out.println("\nint array output:");
        for(Integer i : testArray)
        {
            System.out.print(i + "  ");
        }
    }

    public static void main(String[] args)
    {
        test();
    }
}
