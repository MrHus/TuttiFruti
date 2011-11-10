package ida1;

import ida1.trees.AVLTree;
import ida1.trees.BinaryTree;
import ida1.trees.RBTree;
import ida1.trees.SplayTree;
import ida1.trees.Tree;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Cornel Alders
 */
public class TreeWithDictionary
{
    public void loadDictionaryIntoTree(String file, Tree tree) throws IOException
    {
        InputStream is = new BufferedInputStream(new FileInputStream("src/ida1/" + file));

        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String word;
            while((word = br.readLine()) != null)
            {
//                System.out.println("Adding " + word + " to dictionary.");
                tree.insert(word);
//                System.out.println(tree.getRoot());
            }

            br.close();
        }
        finally
        {
            is.close();
        }
    }

    public static void printInFile()
    {
        try
        {
            File file = new File("src/ida1/Numbers.dic");
            PrintStream pin = new PrintStream(new BufferedOutputStream(new FileOutputStream(file.toString(), true)), true);
            System.setOut(pin);
            int count = 0;
            Random r = new Random();
            while(count <= 10000)
//            while(count <= (Integer.MAX_VALUE / 1000))
            {
                System.out.println(count);
//                System.out.println(r.nextInt(Integer.MAX_VALUE / 4));
                count++;
            }
            pin.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    public static void test()
    {
        TreeWithDictionary loader = new TreeWithDictionary();
        ArrayList<Tree<String>> treelist = new ArrayList<Tree<String>>();
       
		treelist.add(new BinaryTree<String>()); //Doesn't work with Dutch.dic, has insert problem.
		treelist.add(new AVLTree<String>());
        treelist.add(new RBTree<String>());
        treelist.add(new SplayTree<String>());

        boolean stringDict = true;

        for(Tree<String> t : treelist)
        {
			Chocobo.start();

            System.out.println("Start Testcase\n\n");
            try
            {
                loader.loadDictionaryIntoTree("smallDutch.dic", t);
            }
            catch(IOException e)
            {
                System.out.println(e);
            }

			if (t instanceof AVLTree)
			{
				System.out.println("AVLTree");
			}
			else if (t instanceof RBTree)
			{
				System.out.println("RBTree");
			}
			else if (t instanceof SplayTree)
			{
				System.out.println("SplayTree");
			}
			else
			{
				System.out.println("BinaryTree");
			}

            System.out.println("Get counter");
            System.out.println(t.getCounter());

            System.out.println("Get root");
            System.out.println(t.getRoot());

            System.out.println("Get root left child");
            System.out.println(t.getRoot().getLeftChild());

            System.out.println("Get diepte");
            
            System.out.println(t.getRoot().diepte());

            System.out.println("Get aantalBlad");
            System.out.println(t.getRoot().aantalBlad());

            System.out.println("Get aantalKnopen");
            
            System.out.println(t.getRoot().aantalKnopen()); 

            System.out.println("Find word: aanvullingsbegroting");
           
            System.out.println(t.contains("aanvullingsbegroting"));

            System.out.println("Find non existing word: Cornel");
           
            System.out.println(t.contains("Cornel"));
            
            System.out.println("Current root");
            System.out.println(t.getRoot());

            System.out.println("\nEnd testcase\n");
			Chocobo.stop();
			Chocobo.printPerformance();
			Chocobo.reset();
        }
    }

    public static void main(String[] args)
    {
        test();
//        printInFile();
    }
}
