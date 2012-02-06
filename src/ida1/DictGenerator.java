package ida1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Random;

/**
 * Generates various dictionaries based on existing dictionaries.
 *
 * @author Cornel Alders
 */
public class DictGenerator
{
    /**
     * Creates a new dictionary with an X amount of random words from a dictionary.
     *
     * @param dictSize Size of dictionary
     * @param fromDict Dictionary to read words from
     * @param intoDict Dictionary to write words in
     * @param overwrite Should existing file be overwritten
     */
    public static void createDictXRandomWords(int dictSize, String fromDict, String intoDict, boolean overwrite)
    {
        try
        {
            File file = new File("src/ida1/" + intoDict);

            if(overwrite)
                file.delete();

			BufferedWriter out = new BufferedWriter(new FileWriter(file.toString()));

            for(int i = 0; i < dictSize;i++)
            {
               out.write(randomWordFromFile(fromDict) +"\n");
            }
            out.close();
			
			System.setOut(System.out);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    /**
     * Creates a new dictionary of size X with Y scrambled fixed words (randomly chosen) from a dictionary.
     *
     * @param dictSize Size of dictionary
     * @param fixedWords Amount of fixed words
     * @param fromDict Dictionary to read words from
     * @param intoDict Dictionary to write words in
     */
    public static void createDictXSizeYFixedWords(int dictSize, int fixedWords, String fromDict, String intoDict, boolean overwrite)
    {
        try
        {
            String[] list = new String[fixedWords];

            for(int i = 0; i < fixedWords;i++)
            {
                list[i] = randomWordFromFile(fromDict);
            }

            File file = new File("src/ida1/" + intoDict);

            if(overwrite)
                file.delete();

            BufferedWriter out = new BufferedWriter(new FileWriter(file.toString()));
            Random r = new Random();
        
            for(int i = 0; i < dictSize;i++)
            {
                out.write(list[r.nextInt(list.length)]+ "\n");
            }
            out.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    /**
     * Reads a random word from a dictionary.
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static String randomWordFromFile(String filename) throws IOException
    {
        Random r = new Random();
        InputStream is = new BufferedInputStream(new FileInputStream("src/ida1/" + filename));

        try
        {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;

            //Counts every line
            while((readChars = is.read(c)) != -1)
            {
                for(int i = 0; i < readChars; ++i)
                {
                    if(c[i] == '\n')
                    {
                        ++count;
                    }
                }
            }
            int lineTarget = r.nextInt(count);
//            System.out.println("linecount = " + lineTarget);

            //Reset stream
            is = new BufferedInputStream(new FileInputStream("src/ida1/" + filename));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //Read every line till we reach the target
            for(int i = 0; i < lineTarget; i++)
            {
                br.readLine();
            }
            String word = br.readLine();
//            System.out.println("Word = " + word);

            br.close();
            return word;
        }
        finally
        {
            is.close();
        }
    }

    public static void main(String[] args)
    {
//        createDictXRandomWords(100, "Dutch.dic", "dict100random.dic");
        createDictXSizeYFixedWords(100, 1, "Dutch.dic", "dict100random1.dic", true);
//        createDictXSizeYFixedWords(100, 10, "Dutch.dic", "dict100random10.dic");
    }
}
