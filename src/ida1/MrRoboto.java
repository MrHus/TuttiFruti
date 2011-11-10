package ida1;

import ida1.trees.AVLTree;
import ida1.trees.BinaryTree;
import ida1.trees.RBTree;
import ida1.trees.SplayTree;
import ida1.trees.Tree;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Testcases work great on smallDutch.dic and Dutch.dic.
 * Using Dutch.dic displays some memory magic which results in more free memory
 * available after the deed is done.
 *
 * @author Cornel Alders
 */
public class MrRoboto
{
    public static void testTreeInsert()
    {
        ArrayList<Tree<String>> treelist = new ArrayList<Tree<String>>();
        treelist.add(new BinaryTree<String>());
        treelist.add(new AVLTree<String>());
        treelist.add(new RBTree<String>());
        treelist.add(new SplayTree<String>());

        System.out.println("Start TreeInsert test - buildPhase");
        for(Tree<String> t : treelist)
        {
            System.out.println("\nStart Testcase");
            Chocobo.start();
            try
            {
                MrRoboto.loadDictionaryIntoTree("smallDutch.dic", t);
//                MrRoboto.loadDictionaryIntoTree("Dutch.dic", t); //Won't work with BinaryTree, too many items == StackOverflow
//                MrRoboto.loadDictionaryIntoTree("dict.txt", t); //Won't work with BinaryTree, too many items == StackOverflow
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
            Chocobo.stop();
            Chocobo.printPerformance();
            System.out.println("Stop Testcase\n");
        }
        System.out.println("Start TreeInsert test - insertElementPhase");
        for(Tree<String> t : treelist)
        {
            System.out.println("\nStart Testcase");
            Chocobo.start();
            t.insert("aanklotsten");
            Chocobo.stop();
            Chocobo.printPerformance();
            System.out.println("Stop Testcase\n");
        }
        System.out.println("Values after test.");
        Chocobo.printSomeTestValues();
    }

    /**
     * Finds the same word in the tree 20 times.
     * This word is located somewhere around the middle of the dictionary.
     */
    public static void testTreeContains()
    {
        ArrayList<Tree<String>> treelist = new ArrayList<Tree<String>>();
        treelist.add(new BinaryTree<String>());
        treelist.add(new AVLTree<String>());
        treelist.add(new RBTree<String>());
        treelist.add(new SplayTree<String>());

        int testruns = 20;

        System.out.println("Start TreeContains test");
        for(Tree<String> t : treelist)
        {
            try
            {
                MrRoboto.loadDictionaryIntoTree("smallDutch.dic", t);
//                MrRoboto.loadDictionaryIntoTree("Dutch.dic", t); //Won't work with BinaryTree and SplayTree, too many items == StackOverflow
//                MrRoboto.loadDictionaryIntoTree("dict.txt", t); //Won't work with BinaryTree and SplayTree, too many items == StackOverflow
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
            System.out.println("\nStart Testcase");
            long nanoTime = 0;
            for(int i = 1; i <= testruns; i++)
            {
                System.out.println("Run " + i);
                Chocobo.start();
                t.contains("aanklotsten");
                Chocobo.stop();
                nanoTime += Chocobo.getNanoTimeElapsed();
                Chocobo.printPerformance();
            }
            System.out.println("Total nanotime: " + nanoTime);
            System.out.println("Average nanotime per run: " + (nanoTime / testruns));
            System.out.println("Stop Testcase\n");
        }
        System.out.println("Values after test.");
        Chocobo.printSomeTestValues();
    }

    /**
     * Deletes a single element in the tree.
     */
    public static void testTreeDelete()
    {
        ArrayList<Tree<String>> treelist = new ArrayList<Tree<String>>();
        treelist.add(new BinaryTree<String>());
        treelist.add(new AVLTree<String>());
        treelist.add(new RBTree<String>());
        treelist.add(new SplayTree<String>());

        System.out.println("Start TreeDelete test");
        for(Tree<String> t : treelist)
        {
            try
            {
                MrRoboto.loadDictionaryIntoTree("smallDutch.dic", t);
//                MrRoboto.loadDictionaryIntoTree("Dutch.dic", t); //Won't work with BinaryTree and SplayTree, too many items == StackOverflow
//                MrRoboto.loadDictionaryIntoTree("dict.txt", t); //Won't work with BinaryTree and SplayTree, too many items == StackOverflow
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
            System.out.println("\nStart Testcase");

            Chocobo.start();
            t.delete("aanklotsten");
            Chocobo.stop();

            Chocobo.printPerformance();

            System.out.println("Stop Testcase\n");
        }
        System.out.println("Values after test.");
        Chocobo.printSomeTestValues();
    }

    public static void loadDictionaryIntoTree(String file, Tree tree) throws IOException
    {
        InputStream is = new BufferedInputStream(new FileInputStream("src/ida1/" + file));

        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String word;
            while((word = br.readLine()) != null)
            {
                tree.insert(word);
            }

            br.close();
        }
        finally
        {
            is.close();
        }
    }

    public static void test()
    {
        testTreeInsert();
        testTreeContains();
        testTreeDelete();
    }

    public static void main(String[] args)
    {
        test();
    }
}
