package ida1.silly_sentences;

import ida1.datastructures.HashMap;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Random;

/**
 *
 * @author Cornel Alders
 */
public class SentenceGenerator
{
    private String documentPath = "";
    private HashMap<String, HashMap<String, String>> dictionary;

    public SentenceGenerator(String[] dictionaryFiles)
    {
        dictionary = new HashMap<String, HashMap<String, String>>(dictionaryFiles.length);
        documentPath = "src\\ida1\\silly_sentences\\";

        try
        {
            for(String dictionaryFile : dictionaryFiles)
            {
                fileToHash(dictionaryFile, dictionary);
            }
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    public String parseSentence(String s)
    {
        String[] wordArray = s.split("[\\s . , ! ? : ; ' \" \\ / [ ] { }]");
//        System.out.println(wordArray.toString());
        String tmpWord = "";

        for(int x = 0; x < wordArray.length; x++)
        {
            tmpWord = wordArray[x];
//            System.out.println(tmpWord);
            if(tmpWord.toUpperCase().equals(tmpWord) &&
                (tmpWord.equals("PERSON") || tmpWord.equals("PROPERTY") || tmpWord.equals("ACTIVITY") ||
                 tmpWord.equals("DO") || tmpWord.equals("DOES")))
            {
                String newWord = randomWordFromDictionary(tmpWord);
                s = s.replaceFirst(tmpWord, newWord);
//                System.out.println(tmpWord + " - becomes " + newWord);
            }
//            System.out.println(tmpWord);
        }

        return s;
    }

    public String randomWordFromDictionary(String key)
    {
        Collection k = dictionary.get(key).values();

        Random r = new Random();
        int i = 0;
        int target = r.nextInt(k.size());
        for(Object obj : k)
        {
            if(i == target)
            {
                return (String) obj;
            }
            i++;
        }
        return "";
    }

    public int countLines(String filename) throws IOException
    {
        Random r = new Random();
        InputStream is = new BufferedInputStream(new FileInputStream(documentPath + filename + ".txt"));

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
        is.close();
        return count;
    }

    public void fileToHash(String filename, HashMap map) throws IOException
    {
        HashMap<String, String> innerMap = new HashMap<String, String>();

        InputStream is = new BufferedInputStream(new FileInputStream(documentPath + filename + ".txt"));

        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String word;
            while((word = br.readLine()) != null)
            {
                innerMap.put(word, word);
            }

            br.close();
            map.put(filename, innerMap);
        }
        finally
        {
            is.close();
        }
    }

    public void print()
    {
        for(HashMap.Entry<String, HashMap<String, String>> innerMap : dictionary)
        {
            System.out.println(innerMap.getKey());
            System.out.println(innerMap.getValue().values());
//            for(HashMap.Entry<String, String> entry : innerMap.getValue())
//            {
//                System.out.println("(" + entry.getKey() + ", " + entry.getValue() + ")");
//            }
        }
    }
}
