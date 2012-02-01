package ida1.algorithms.sort;

import ida1.trees.AVLTree;
import ida1.trees.BinaryTree;
import ida1.trees.RBTree;
import ida1.trees.SplayTree;
import ida1.trees.Tree;
import java.util.ArrayList;

/**
 *
 * @author Maarten Hus
 */
public class TreeSort<E extends Comparable<? super E>> implements SortAlgorithm<E>
{
    public E[] sort(E[] array)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Integer> sort(Tree<Integer> tree)
    {
        return tree.getRoot().inOrder();
    }

//    public ArrayList<String> sort(Tree<String> tree)
//    {
//        return tree.getRoot().inOrder();
//    }

    public static void test()
    {
		BinaryTree<Integer> tree = new BinaryTree<Integer>();
        Integer[] integerArray = {5, 8, 8, 9, 4, 2, 1, 3, 5, 6, 0, 7};
        for(Integer i : integerArray)
        {
            tree.insert(i);
        }
        System.out.println("Expected lines to be the same");
        System.out.println(tree.getRoot().inOrder());
		AVLTree<Integer> tree2 = new AVLTree<Integer>();
        for(Integer i : integerArray)
        {
            tree2.insert(i);
        }
        System.out.println("Expected lines to be the same");
        System.out.println(tree2.getRoot().inOrder());
		RBTree<Integer> tree3 = new RBTree<Integer>();
        for(Integer i : integerArray)
        {
            tree3.insert(i);
        }
        System.out.println("Expected lines to be the same");
        System.out.println(tree3.getRoot().inOrder());
		SplayTree<Integer> tree4 = new SplayTree<Integer>();
        for(Integer i : integerArray)
        {
            tree4.insert(i);
        }
        System.out.println("Expected lines to be the same");
        System.out.println(tree4.getRoot().inOrder());
        System.out.println("Expected lines to be the same");
    }

    public static void main(String[] args)
    {
        test();
    }
}
