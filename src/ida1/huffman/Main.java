package ida1.huffman;

import ida1.trees.BKnoop;
import java.util.Map;

/**
 *
 * @author maartenhus
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
	{
        Huffman huffman = new Huffman();

		System.out.println(huffman.frequencyHashMap("bananen"));

		System.out.println(huffman.frequencyToSortedList(huffman.frequencyHashMap("bananen")));

		BKnoop<Map.Entry <Character, Integer>> huffmanTree = huffman.huffmanTree(huffman.frequencyToSortedList(huffman.frequencyHashMap("bananen")));

		System.out.println(huffmanTree.preOrderToString());

		System.out.println(huffman.codeMap(huffmanTree));

		String encoded = huffman.encodeString("bananen");

		System.out.println(encoded);

		System.out.println(huffman.decodeString(encoded, huffmanTree));
    }
}