package ida1.silly_sentences;

/**
 *
 * @author Maarten Hus
 */
public class Main
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        SentenceGenerator generator = new SentenceGenerator(new String[] {"ACTIE", "DOEL", "PERSONEN", "PERSOON"});
//        generator.print();

		for (int i = 0; i < 50; i++)
		{
			System.out.println(generator.parseSentence("ik ACTIE DOEL"));
		}
    }
}
