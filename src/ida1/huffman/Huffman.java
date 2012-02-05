package ida1.huffman;

import ida1.trees.BKnoop;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.BitSet;
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

	public static HashMap<Character, Integer> frequencyHashMap (String string)
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

		//System.out.println(frequency);
		return frequency;
	}

	public static ArrayList<BKnoop <AbstractMap.SimpleEntry <Character, Integer>>> frequencyToSortedList(HashMap<Character, Integer> frequencyHashMap)
	{
		ArrayList<BKnoop <AbstractMap.SimpleEntry <Character, Integer>>> frequencyList = new ArrayList<BKnoop <AbstractMap.SimpleEntry <Character, Integer>>>();

		for(Map.Entry<Character, Integer> e : frequencyHashMap.entrySet())
		{
			frequencyList.add(new BKnoop(new AbstractMap.SimpleEntry<Character, Integer>(e.getKey(), e.getValue())));
		}

		Collections.sort(frequencyList, new EntryComparator());

		//System.out.println(frequencyList);

		return frequencyList;
	}

	public static BKnoop<AbstractMap.SimpleEntry <Character, Integer>> huffmanTree (ArrayList<BKnoop <AbstractMap.SimpleEntry <Character, Integer>>> frequencySortedList)
	{
		if (frequencySortedList.isEmpty())				// Edge case should never be true
		{
			return new BKnoop<AbstractMap.SimpleEntry <Character, Integer>>(new AbstractMap.SimpleEntry<Character, Integer>(null, 0));
		}
		else if(frequencySortedList.size() == 1)		// One and whe are done.
		{
			return frequencySortedList.get(0);
		}
		else											// Create a new BKnoop and add the two lowest als children and as the value the total
		{
			BKnoop <AbstractMap.SimpleEntry <Character, Integer>>   first	= frequencySortedList.get(0);
			BKnoop <AbstractMap.SimpleEntry <Character, Integer>>	second  = frequencySortedList.get(1);

			Integer total = first.get().getValue() + second.get().getValue();

			AbstractMap.SimpleEntry<Character, Integer> entry = new AbstractMap.SimpleEntry<Character, Integer>(null, total);

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

	public static HashMap<Character, String> codeMap (BKnoop<AbstractMap.SimpleEntry <Character, Integer>> huffmanTree)
	{
		HashMap<Character, String> codeMap = new HashMap<Character, String>();

		createCodeMap(codeMap, "", huffmanTree);

		//System.out.println(codeMap);

		return codeMap;
	}

	private static void createCodeMap (HashMap<Character, String> codeMap, String walk, BKnoop<AbstractMap.SimpleEntry <Character, Integer>> huffmanTree)
	{
		BKnoop<AbstractMap.SimpleEntry <Character, Integer>> leftChild = huffmanTree.getLeftChild();
		BKnoop<AbstractMap.SimpleEntry <Character, Integer>> rightChild = huffmanTree.getRightChild();

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

	public static String encodeString(String plain)
	{
		HashMap<Character, String> codeMap = codeMap(huffmanTree(frequencyToSortedList(frequencyHashMap(plain))));

		StringBuilder buffer = new StringBuilder();

		for (char c : plain.toCharArray())
		{
			buffer.append(codeMap.get(c));
		}

		return buffer.toString();
	}

	public static String decodeString(String code, BKnoop<AbstractMap.SimpleEntry <Character, Integer>> huffmanTree)
	{
		BKnoop<AbstractMap.SimpleEntry <Character, Integer>> currentRoot = huffmanTree;
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

	public static String decodeString(BKnoop<Character> huffmanTree, String code)
	{
		//System.out.println("The code " + code);
		BKnoop<Character> currentRoot = huffmanTree;
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
				if (currentRoot.get() != null)
				{
					buffer.append(currentRoot.get());
				}
				
				currentRoot = huffmanTree;
			}
		}

		return buffer.toString();
	}

	public static void writeToFile(String filename, String encodedString, BKnoop<AbstractMap.SimpleEntry <Character, Integer>> huffManTree)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			ArrayList<Character> hufflist = new ArrayList<Character>();

			for(AbstractMap.SimpleEntry <Character, Integer> entry : huffManTree.levelOrder())
			{
				//System.out.println(entry.getKey());
				hufflist.add(entry.getKey());
			}

			//System.out.println("Tree level order " + huffManTree.levelOrderToString());

			oos.writeObject(hufflist);
			oos.reset();

			final int length = encodedString.length();

			final BitSet bitSet = new BitSet(length);
			for(int i = length - 1; i >= 0; i--)
			{
				// anything that's not a 1 is a zero, per convention
				bitSet.set(i, encodedString.charAt(i) == '1');
			}

			oos.writeObject(bitSet);
			oos.reset();

			// Write length as a string since Integer is cast to int by writeObject which
			// causes the reader to not read it.
			oos.writeObject("" + length);
			oos.reset();

			oos.flush();
			oos.close();
			fos.close();
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("FileNotFoundException : " + ex);
		}
		catch (IOException ioe)
		{
			System.out.println("IOException : " + ioe);
		}
	}

	public static void encodeToFile(String fileName, String plain)
	{
		BKnoop<AbstractMap.SimpleEntry <Character, Integer>> huffmanTree = huffmanTree(frequencyToSortedList(frequencyHashMap(plain)));

		String encoded = encodeString(plain);

		writeToFile(fileName, encoded, huffmanTree);
	}

	public static String decodeFromFile(String filename)
	{
		ArrayList<Character> hufflist = null;
		String encodedString = null;
		int lengthOfBitSet = 0;

		ObjectInputStream inputStream = null;

        try
		{
            //Construct the ObjectInputStream object
            inputStream = new ObjectInputStream(new FileInputStream(filename));

			hufflist = (ArrayList<Character>)inputStream.readObject();
			BitSet bs = ((BitSet)inputStream.readObject());

			lengthOfBitSet = Integer.parseInt((String)inputStream.readObject());
	
			StringBuilder builder = new StringBuilder();

			for (int i = 0; i < lengthOfBitSet; i++)
			{
				if (bs.get(i))
				{
					builder.append("1");
				}
				else
				{
					builder.append("0");
				}
			}

			encodedString = builder.toString();
        }
		catch (EOFException ex)
		{
        }
		catch (ClassNotFoundException ex)
		{
            ex.printStackTrace();
        }
		catch (FileNotFoundException ex)
		{
            ex.printStackTrace();
        }
		catch (IOException ex)
		{
            ex.printStackTrace();
        }
		finally
		{
            //Close the ObjectInputStream
            try
			{
                if (inputStream != null)
				{
                    inputStream.close();
                }
            }
			catch (IOException ex)
			{
                ex.printStackTrace();
            }
        }

		if (hufflist != null && encodedString != null)
		{
			BKnoop<Character> huffmanTree = huffmanTreeFromLevelOrderList(hufflist);

			System.out.println("Tree level order " + huffmanTree.levelOrderToString());

			return decodeString(huffmanTree, encodedString);
		}
		
		return "Error";
	}

	public static BKnoop<Character> huffmanTreeFromLevelOrderList(ArrayList<Character> hufflist)
	{
		BKnoop<Character> huffmanTree = new BKnoop<Character>(hufflist.get(0));
		BKnoop<Character> current = huffmanTree;

		// Which element should have stuff apended?
		int index = 0;
		int left = 1;
		int right = 2;
		boolean shouldGoRight = true;

		while (index < hufflist.size())
		{
			BKnoop<Character> leftChild  = null;
			BKnoop<Character> rightChild = null;

			if (left < hufflist.size())
			{
				leftChild = new BKnoop<Character>(hufflist.get(left));
				current.insert(leftChild, BKnoop.LEFT);
			}

			if (right < hufflist.size())
			{
				rightChild = new BKnoop<Character>(hufflist.get(right));
				current.insert(rightChild, BKnoop.RIGHT);
			}

			left  += 2;
			right += 2;

			if (shouldGoRight)
			{
				index = right;
				current = rightChild;
				shouldGoRight = false;
			}
			else
			{
				index = left;
				current = leftChild;
				shouldGoRight = true;
			}
		}

		return huffmanTree;
	}

	static class EntryComparator implements Comparator<BKnoop <AbstractMap.SimpleEntry <Character, Integer>>>
	{
		public int compare(BKnoop <AbstractMap.SimpleEntry <Character, Integer>> k1, BKnoop <AbstractMap.SimpleEntry <Character, Integer>> k2)
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
