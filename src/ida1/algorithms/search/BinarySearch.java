package ida1.algorithms.search;

import ida1.algorithms.sort.QuickSort;

/**
 * Binary search implementation.
 *
 * @author Maarten Hus, Cornel Alders
 */
public class BinarySearch<E extends Comparable<? super E>> implements SearchAlgorithm<E>
{
    public int search(E[] array, E target)
    {
        int top = array.length - 1;
        int bottom = 0;

        while(bottom <= top)
        {
            int mid = (bottom + top) / 2;

            if(array[mid].compareTo(target) == 0)
            {
                return mid;
            }
            else
            {
                if(array[mid].compareTo(target) == -1)
                {
                    bottom = mid + 1;
                }
                else
                {
                    top = mid - 1;
                }
            }
        }

        return -1;
    }

    // als target voor komt in de gesorteerde tabel dan wordt de index van target
    // teruggegeven; als target niet voor komt dan -1.
    public int search(Character[] rij, char target)
    {
        int top = rij.length - 1;
        int bottom = 0;

        while(bottom <= top)
        {
            int mid = (bottom + top) / 2;

            if(rij[mid] == target)
            {
                return mid;
            }
            else
            {
                if(rij[mid] < target)
                {
                    bottom = mid + 1;
                }
                else
                {
                    top = mid - 1;
                }
            }
        }

        return -1;
    }

	public static void main (String [] args)
	{
        BinarySearch<Character> binarySeach = new BinarySearch<Character>();
        QuickSort quickSort = new QuickSort();

		System.out.println("character array:");
		Character[] list = {'M', 'H', 'O', 'G', 'P', 'F', 'Z', 'D', 'A', 'K'};

		System.out.println("Binary Search, unsorted list:");
		char target = 'P';
		int index = binarySeach.search(list, target);
		System.out.println ("Target: " + target + " \nIndex : " + index);

		System.out.println("\nSorting the array using QuickSort.");
		list = quickSort.sort(list);

		System.out.println("Binary Search, sorted list:");
		index = binarySeach.search(list, target);
		System.out.println ("Target: " + target + " \nIndex : " + index);
	}
}
