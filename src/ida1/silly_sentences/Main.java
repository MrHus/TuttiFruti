package ida1.silly_sentences;

/**
 *
 * @author Cornel Alders
 */
public class Main
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        SentenceGenerator generator = new SentenceGenerator(new String[] {"ACTIVITY", "DO", "DOES", "PERSON", "PROPERTY"});
//        generator.print();
        System.out.println(generator.parseSentence("PERSON DOES ACTIVITY."));
        System.out.println(generator.parseSentence("PERSON DOES ACTIVITY."));
        System.out.println(generator.parseSentence("PERSON DOES ACTIVITY."));
        System.out.println(generator.parseSentence("I have a PROPERTY lawyer called PERSON."));
        System.out.println(generator.parseSentence("PERSON thinks that I am PROPERTY!"));
        System.out.println(generator.parseSentence("PERSON finds ACTIVITY PROPERTY!"));
        System.out.println(generator.parseSentence("I DO ACTIVITY."));
        System.out.println(generator.parseSentence("I DO PERSON who is great at ACTIVITY while I am only good at ACTIVITY."));
        System.out.println(generator.parseSentence("You think that I am PROPERTY?"));
        System.out.println(generator.parseSentence("PERSON DOES ACTIVITY, in a PROPERTY way."));
        System.out.println(generator.parseSentence("PERSON has a friend called PERSON who doesn't like ACTIVITY. Because of this, "
                                                    + "PERSON thinks that he is a PROPERTY person."));
    }
}
