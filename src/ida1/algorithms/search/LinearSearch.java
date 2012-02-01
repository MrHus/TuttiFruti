package ida1.algorithms.search;

/**
 * Linear search implementation.
 * The generic implementation uses compareTo() to find the target element
 * in the array.
 *
 * @author Maarten Hus
 */
public class LinearSearch<E extends Comparable<? super E>> implements SearchAlgorithm<E>
{
    public int search(E[] array, E target)
    {
		for (int i = 0; i < array.length; i++)
		{
			if (array[i].compareTo(target) == 0)
			{
				return i;
			}
		}

		return -1; // als er niets gevonden wordt.
    }

	// als target voor komt in de tabel dan wordt de index van target teruggegeven
	// als target niet voor komt dan wordt -1 teruggegeven.
	public int search(Character[] rij, char target)
	{
		for (int i = 0; i < rij.length; i++)
		{
			if (rij[i] == target)
			{
				return i;
			}
		}

		return -1; // als er niets gevonden wordt.
	}
    
    public static void test()
    {
        LinearSearch<Character> linearSearch = new LinearSearch<Character>();
		System.out.println("Character array:");
		Character[] characterArray = {'M', 'H', 'O', 'G', 'P', 'F', 'Z', 'D', 'A', 'K'};
        for(Character c : characterArray) System.out.print(c + "  ");

		System.out.println("\nLinear Search, generic type:");
		char target = 'P';
		int index = linearSearch.search(characterArray, target);
		System.out.println ("Target: " + target + " \nIndex : " + index);

        LinearSearch<Integer> linearSearch2 = new LinearSearch<Integer>();
		System.out.println("Integer array:");
		Integer[] integerArray = {8, 4, 6, 1, 9, 7, 3, 2, 6, 5};
        for(Integer i : integerArray) System.out.print(i + "  ");

		System.out.println("\nLinear Search, generic type:");
		int target2 = 2;
		int index2 = linearSearch2.search(integerArray, target2);
		System.out.println ("Target: " + target2 + " \nIndex : " + index2);

        LinearSearch linearSearch3 = new LinearSearch();
		System.out.println("Character array:");
		Character[] characterArray3 = {'M', 'H', 'O', 'G', 'P', 'F', 'Z', 'D', 'A', 'K'};
        for(Character c : characterArray3) System.out.print(c + "  ");

		System.out.println("\nLinear Search, non generic:");
		char target3 = 'P';
		int index3 = linearSearch3.search(characterArray3, target3);
		System.out.println ("Target: " + target3 + " \nIndex : " + index3);
    }

	public static void main (String [] args)
	{
        test();
	}
}
