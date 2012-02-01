package stack;

import stack.linkedlist.Lijst;

/**
 *
 * @author Maarten Hus
 */
public class ListStack implements Stack
{
	private Lijst lijst = new Lijst();
	
    public ListStack()
    {
        
    }

	public void push(Object o)
	{
		lijst.addFirst(o);
	}

	public Object pop()
	{
		return lijst.removeFirst();
	}

	public Object peek()
	{
		return lijst.getFirst();
	}

	public int size()
	{
		return lijst.size();
	}

	public boolean isEmpty()
	{
		return lijst.isEmpty();
	}
}
