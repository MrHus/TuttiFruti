package ida1;

import ida1.datastructures.BinaryHeap;
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
import java.util.Arrays;
import java.util.Random;

/**
 * Testcases work great on smallDutch.dic and Dutch.dic.
 * Using Dutch.dic displays some memory magic which results in more free memory
 * available after the deed is done.
 *
 * @author Maarten Hus
 */
public class RobotRunner
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
            SystemAnalyser.start();
            try
            {
                RobotRunner.loadDictionaryIntoTree("dict.txt", t);
//                RobotRunner.loadDictionaryIntoTree("smallDutch.dic", t);
//                RobotRunner.loadDictionaryIntoTree("Dutch.dic", t); //Won't work with BinaryTree, too many items == StackOverflow
//                RobotRunner.loadDictionaryIntoTree("dict.txt", t); //Won't work with BinaryTree, too many items == StackOverflow
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
            SystemAnalyser.stop();
            SystemAnalyser.printPerformance();
            System.out.println("Stop Testcase\n");
        }
        System.out.println("Start TreeInsert test - insertElementPhase");
        for(Tree<String> t : treelist)
        {
            System.out.println("\nStart Testcase");
            SystemAnalyser.start();
            t.insert("aanklotsten");
            SystemAnalyser.stop();
            SystemAnalyser.printPerformance();
            System.out.println("Stop Testcase\n");
        }
        System.out.println("Values after test.");
        SystemAnalyser.printSomeTestValues();
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
                RobotRunner.loadDictionaryIntoTree("newDutch.dic", t);
//                RobotRunner.loadDictionaryIntoTree("smallDutch.dic", t);
//                RobotRunner.loadDictionaryIntoTree("Dutch.dic", t); //Won't work with BinaryTree and SplayTree, too many items == StackOverflow
//                RobotRunner.loadDictionaryIntoTree("dict.txt", t); //Won't work with BinaryTree and SplayTree, too many items == StackOverflow
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
                SystemAnalyser.start();
                t.contains("aanklotsten");
                SystemAnalyser.stop();
                nanoTime += SystemAnalyser.getNanoTimeElapsed();
                SystemAnalyser.printPerformance();
            }
            System.out.println("Total nanotime: " + nanoTime);
            System.out.println("Average nanotime per run: " + (nanoTime / testruns));
            System.out.println("Stop Testcase\n");
        }
        System.out.println("Values after test.");
        SystemAnalyser.printSomeTestValues();
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
                RobotRunner.loadDictionaryIntoTree("newDutch.dic", t);
//                RobotRunner.loadDictionaryIntoTree("smallDutch.dic", t);
//                RobotRunner.loadDictionaryIntoTree("Dutch.dic", t); //Won't work with BinaryTree and SplayTree, too many items == StackOverflow
//                RobotRunner.loadDictionaryIntoTree("dict.txt", t); //Won't work with BinaryTree and SplayTree, too many items == StackOverflow
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
            System.out.println("\nStart Testcase");

            SystemAnalyser.start();
            t.delete("aanklotsten");
            SystemAnalyser.stop();

            SystemAnalyser.printPerformance();

            System.out.println("Stop Testcase\n");
        }
        System.out.println("Values after test.");
        SystemAnalyser.printSomeTestValues();
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

	public static void random100Test()
	{

		String[] random100 = new String[100];
        InputStream is;
		try
        {
			is = new BufferedInputStream(new FileInputStream("src/ida1/dict.txt"));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String word;

			int i = 0;
			while (i < 100 && (word = br.readLine()) != null)
			{
				Random r = new Random();

				if(r.nextBoolean())
				{
					random100[i] = word;
					i++;
				}
			}

            br.close();
			is.close();
        }
		catch(IOException e)
        {
                System.out.println(e);
        }

		//System.out.println(random100);

		ArrayList<Tree<String>> treelist = new ArrayList<Tree<String>>();
        treelist.add(new BinaryTree<String>());
        treelist.add(new AVLTree<String>());
        //treelist.add(new RBTree<String>());
        treelist.add(new SplayTree<String>());

        System.out.println("Start elapsed test");
        for(Tree<String> t : treelist)
        {
			System.out.println(t);

			try
            {
                RobotRunner.loadDictionaryIntoTree("dict.txt", t);
//                RobotRunner.loadDictionaryIntoTree("smallDutch.dic", t);
//                RobotRunner.loadDictionaryIntoTree("Dutch.dic", t); //Won't work with BinaryTree, too many items == StackOverflow
//                RobotRunner.loadDictionaryIntoTree("dict.txt", t); //Won't work with BinaryTree, too many items == StackOverflow
            }
            catch(IOException e)
            {
                System.out.println(e);
            }

			SystemAnalyser.start();

			for (int i = 0; i < random100.length; i++)
			{
				t.contains(random100[i]);
			}

			SystemAnalyser.stop();
            SystemAnalyser.printPerformance();
			SystemAnalyser.reset();
		}
	}

	public static void random100SameTest()
	{
		ArrayList<Tree<String>> treelist = new ArrayList<Tree<String>>();
        treelist.add(new BinaryTree<String>());
        treelist.add(new AVLTree<String>());
        //treelist.add(new RBTree<String>());
        treelist.add(new SplayTree<String>());

        System.out.println("Start elapsed test 100 same");
        for(Tree<String> t : treelist)
        {
			System.out.println(t);

			try
            {
                RobotRunner.loadDictionaryIntoTree("dict.txt", t);
//                RobotRunner.loadDictionaryIntoTree("smallDutch.dic", t);
//                RobotRunner.loadDictionaryIntoTree("Dutch.dic", t); //Won't work with BinaryTree, too many items == StackOverflow
//                RobotRunner.loadDictionaryIntoTree("dict.txt", t); //Won't work with BinaryTree, too many items == StackOverflow
            }
            catch(IOException e)
            {
                System.out.println(e);
            }

			SystemAnalyser.start();

			for (int i = 0; i < 100; i++)
			{
				t.contains("Yosemite");
			}

			SystemAnalyser.stop();
            SystemAnalyser.printPerformance();
			SystemAnalyser.reset();
		}
	}

	public static void random100Same10Test()
	{
		ArrayList<Tree<String>> treelist = new ArrayList<Tree<String>>();
        treelist.add(new BinaryTree<String>());
        treelist.add(new AVLTree<String>());
        //treelist.add(new RBTree<String>());
        treelist.add(new SplayTree<String>());

        System.out.println("Start elapsed test 100 same");
        for(Tree<String> t : treelist)
        {
			System.out.println(t);

			try
            {
                RobotRunner.loadDictionaryIntoTree("dict.txt", t);
//                RobotRunner.loadDictionaryIntoTree("smallDutch.dic", t);
//                RobotRunner.loadDictionaryIntoTree("Dutch.dic", t); //Won't work with BinaryTree, too many items == StackOverflow
//                RobotRunner.loadDictionaryIntoTree("dict.txt", t); //Won't work with BinaryTree, too many items == StackOverflow
            }
            catch(IOException e)
            {
                System.out.println(e);
            }

			SystemAnalyser.start();

			String[] words = {"wrought","Yosemite", "wrought", "tupelo", "tupelo", "tupelo", "Xavier", "Xavier", "Xavier", "Xavier", "Xavier", "Xavier", "Xavier", "Xavier", "Xavier", "Tulsa", "Tulsa", "Tulsa", "Tulsa", "Tulsa", "wolfish", "wolfish", "Zorn", "Zorn", "Zorn", "wolfish", "urbanite", "urbanite", "urbanite", "urbanite", "tupelo", "tupelo", "tupelo", "tupelo", "tupelo", "verisimilitude", "verisimilitude", "verisimilitude", "tupelo", "tupelo", "Voltaire", "Yosemite", "Yosemite", "Yosemite", "Voltaire", "Voltaire", "wolfish", "wolfish", "wolfish", "wolfish", "wolfish", "verisimilitude", "verisimilitude", "verisimilitude", "verisimilitude", "Voltaire", "Tulsa", "Zorn", "Zorn", "wrought", "wrought", "Zorn", "Zorn", "Zorn", "Zorn", "Zorn", "Voltaire", "Voltaire", "Voltaire", "Voltaire", "Voltaire", "wrought", "wrought", "wrought", "wrought", "wrought", "wrought", "urbanite", "urbanite", "urbanite", "urbanite", "urbanite", "urbanite", "wolfish", "wolfish", "Voltaire", "verisimilitude", "verisimilitude", "verisimilitude", "Xavier", "Tulsa", "Tulsa", "Tulsa", "Tulsa", "Yosemite", "Yosemite", "Yosemite", "Yosemite", "Yosemite", "Yosemite"};

			for (int i = 0; i < 100; i++)
			{
				t.contains(words[i]);
			}

			
		}
	}

	public static void heapSort()
	{
		System.out.println("HeapSort");

		SystemAnalyser.start();

		ArrayList<String> list = new ArrayList<String>();

		try
        {
			InputStream is = new BufferedInputStream(new FileInputStream("src/ida1/dict.txt"));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String word;
            while((word = br.readLine()) != null)
            {
                list.add(word);
            }

            br.close();
			is.close();
        }
		catch(IOException e)
        {
                System.out.println(e);
        }	
		
		BinaryHeap<String> heap = new BinaryHeap<String>(BinaryHeap.Type.MINHEAP, list);

		ArrayList <String> minValues = new ArrayList<String>();
		while (!heap.getList().isEmpty())
		{
			minValues.add(heap.delete());
		}
		
		System.out.println(minValues);
		
		SystemAnalyser.stop();
        SystemAnalyser.printPerformance();
		SystemAnalyser.reset();
	}

	public static void treeSort()
	{
		System.out.println("TreeSort");

		SystemAnalyser.start();

		//RBTree<String> tree = new RBTree<String>();
		AVLTree<String> tree = new AVLTree<String>();

		try
        {
			InputStream is = new BufferedInputStream(new FileInputStream("src/ida1/dict.txt"));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String word;
            while((word = br.readLine()) != null)
            {
				tree.insert(word);
            }

            br.close();
			is.close();
        }
		catch(IOException e)
        {
                System.out.println(e);
        }

		System.out.println(tree.getRoot().inOrder());

		SystemAnalyser.stop();
        SystemAnalyser.printPerformance();
		SystemAnalyser.reset();
	}

	public static void mergeSort()
	{
		System.out.println("MergeSort");

		SystemAnalyser.start();

		ArrayList<String> list = new ArrayList<String>();

		try
        {
			InputStream is = new BufferedInputStream(new FileInputStream("src/ida1/dict.txt"));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String word;
            while((word = br.readLine()) != null)
            {
                list.add(word);
            }

            br.close();
			is.close();
        }
		catch(IOException e)
        {
                System.out.println(e);
        }

		String[] array = list.toArray(new String[list.size()]);
		Arrays.sort(array);

		System.out.println(array);

		SystemAnalyser.stop();
        SystemAnalyser.printPerformance();
		SystemAnalyser.reset();
	}

    public static void test()
    {

        testTreeInsert();
        testTreeContains();
        testTreeDelete();
    }

    public static void main(String[] args)
    {
		random100Test();
    }
}
