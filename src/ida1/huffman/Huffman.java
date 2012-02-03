package ida1.huffman;

import ida1.trees.BKnoop;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author maartenhus
 */
public class Huffman
{
	public Huffman()
	{

	}

	public HashMap<Character, Integer> frequencyHashMap (String string)
	{
		HashMap<Character, Integer> frequency = new HashMap<Character, Integer>();

		for (char c : string.toCharArray())
		{
			Character ch = new Character(c);

			if (frequency.containsKey(ch))
			{
				frequency.put(ch, (frequency.get(ch) + 1));
			}
			else
			{
				frequency.put(ch, 1);
			}
		}

		return frequency;
	}

	public ArrayList<BKnoop <Map.Entry <Character, Integer>>> frequencyToSortedList(HashMap<Character, Integer> frequencyHashMap)
	{
		ArrayList<BKnoop <Map.Entry <Character, Integer>>> frequencyList = new ArrayList<BKnoop <Map.Entry <Character, Integer>>>();

		for(Map.Entry<Character, Integer> e : frequencyHashMap.entrySet())
		{
			frequencyList.add(new BKnoop(e));
		}

		Collections.sort(frequencyList, new EntryComparator());

		return frequencyList;
	}

	public BKnoop<Map.Entry <Character, Integer>> huffmanTree (ArrayList<BKnoop <Map.Entry <Character, Integer>>> frequencySortedList)
	{
		if (frequencySortedList.isEmpty())				// Edge case should never be true
		{
			return new BKnoop<Map.Entry <Character, Integer>>(new AbstractMap.SimpleEntry<Character, Integer>(null, 0));
		}
		else if(frequencySortedList.size() == 1)		// One and whe are done.
		{
			return frequencySortedList.get(0);
		}
		else											// Create a new BKnoop and add the two lowest als children and as the value the total
		{
			BKnoop <Map.Entry <Character, Integer>> first	= frequencySortedList.get(0);
			BKnoop <Map.Entry <Character, Integer>>	second  = frequencySortedList.get(1);

			Integer total = first.get().getValue() + second.get().getValue();

			Map.Entry<Character, Integer> entry = new AbstractMap.SimpleEntry<Character, Integer>(null, total);

			BKnoop knoop = new BKnoop<Map.Entry <Character, Integer>>(entry);
			knoop.add(first);
			knoop.add(second);

			frequencySortedList.remove(0); // Remove first
			frequencySortedList.remove(0); // Remove second, first is allready removed

			frequencySortedList.add(0, knoop);

			Collections.sort(frequencySortedList, new EntryComparator());

			return huffmanTree(frequencySortedList);
		}
	}

	public HashMap<Character, String> codeMap (BKnoop<Map.Entry <Character, Integer>> huffmanTree)
	{
		HashMap<Character, String> codeMap = new HashMap<Character, String>();

		createCodeMap(codeMap, "", huffmanTree);

		return codeMap;
	}

	private void createCodeMap (HashMap<Character, String> codeMap, String walk, BKnoop<Map.Entry <Character, Integer>> huffmanTree)
	{
		BKnoop<Map.Entry <Character, Integer>> leftChild = huffmanTree.getLeftChild();
		BKnoop<Map.Entry <Character, Integer>> rightChild = huffmanTree.getRightChild();

		if(leftChild != null)
		{
			createCodeMap(codeMap, walk + "0", leftChild);
		}

		if(rightChild != null)
		{
			createCodeMap(codeMap, walk + "1", rightChild);
		}

		if (leftChild == null && rightChild == null)
		{
			codeMap.put(huffmanTree.get().getKey(), walk);
		}
	}

	public String encodeString(String plain)
	{
		HashMap<Character, String> codeMap = codeMap(huffmanTree(frequencyToSortedList(frequencyHashMap(plain))));

		StringBuilder buffer = new StringBuilder();

		for (char c : plain.toCharArray())
		{
			buffer.append(codeMap.get(c));
		}

		return buffer.toString();
	}

	public String decodeString(String code, BKnoop<Map.Entry <Character, Integer>> huffmanTree)
	{
		BKnoop<Map.Entry <Character, Integer>> currentRoot = huffmanTree;
		StringBuilder buffer = new StringBuilder();

		for (char c : code.toCharArray())
		{
			if (c == '0')
			{
				currentRoot = currentRoot.getLeftChild();
			}
			else
			{
				currentRoot = currentRoot.getRightChild();
			}

			if (currentRoot.getLeftChild() == null && currentRoot.getRightChild() == null)
			{
				buffer.append(currentRoot.get().getKey().toString());
				currentRoot = huffmanTree;
			}
		}

		return buffer.toString();
	}

	static class EntryComparator implements Comparator<BKnoop <Map.Entry <Character, Integer>>>
	{
		public int compare(BKnoop <Map.Entry <Character, Integer>> k1, BKnoop <Map.Entry <Character, Integer>> k2)
		{
			if (k1.get().getValue() > k2.get().getValue())
			{
				return 1;
			}
			else if(k1.get().getValue() < k2.get().getValue())
			{
				return -1;
			}
			else // Values are equal, now sort on key so they are alpabetical. The key can be null.
			{
				if (k1.get().getKey() == null)
				{
					return 0;
				}
				else if(k1.get().getKey() > k2.get().getKey())
				{
					return 1;
				}
				else if (k1.get().getKey() < k2.get().getKey())
				{
					return -1;
				}
				else
				{
					return 0;
				}
			}
		}
	}
}
